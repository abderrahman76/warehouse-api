package com.kharchoufi.warehouse_api.service;

import com.kharchoufi.warehouse_api.AbstractIntegrationTest;
import com.kharchoufi.warehouse_api.dto.MovementRequest;
import com.kharchoufi.warehouse_api.model.*;
import com.kharchoufi.warehouse_api.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class StockConcurrencyIntegrationTest extends AbstractIntegrationTest {

    @Autowired private StockMovementService movementService;
    @Autowired private ProductRepository productRepository;
    @Autowired private WarehouseRepository warehouseRepository;
    @Autowired private StockRepository stockRepository;

    private Long productId;
    private Long warehouseId;

    @BeforeEach
    void setUp() {
        stockRepository.deleteAll();
        productRepository.deleteAll();
        warehouseRepository.deleteAll();

        Product product = new Product();
        product.setSku("SKU-CONC");
        product.setName("Concurrency Test Product");
        product = productRepository.save(product);
        productId = product.getId();

        Warehouse warehouse = new Warehouse();
        warehouse.setCode("WH-CONC");
        warehouse.setName("Concurrency Test Warehouse");
        warehouse = warehouseRepository.save(warehouse);
        warehouseId = warehouse.getId();

        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setWarehouse(warehouse);
        stock.setQuantity(100);   // exactly 100 units to start
        stockRepository.save(stock);
    }

    @Test
    void concurrentOutbounds_neverOversell() throws InterruptedException {
        int threads = 20;         // 20 requests at once
        int quantityEach = 10;    // each tries to pick 10 → 200 total demanded vs 100 available

        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(threads);
        AtomicInteger successes = new AtomicInteger(0);
        AtomicInteger rejections = new AtomicInteger(0);

        for (int i = 0; i < threads; i++) {
            pool.submit(() -> {
                try {
                    startGate.await();   // all threads wait, then fire together
                    movementService.record(
                            new MovementRequest(productId, warehouseId, MovementType.OUTBOUND, quantityEach));
                    successes.incrementAndGet();
                } catch (Exception e) {
                    rejections.incrementAndGet();   // InsufficientStockException lands here
                } finally {
                    done.countDown();
                }
            });
        }

        startGate.countDown();               // release all 20 at the same instant
        done.await(30, TimeUnit.SECONDS);    // wait for all to finish
        pool.shutdown();

        int finalQuantity = stockRepository
                .findByProductIdAndWarehouseId(productId, warehouseId)
                .orElseThrow().getQuantity();

        // The proof:
        assertThat(successes.get()).isEqualTo(10);   // exactly 10 picks of 10 succeeded (100 units)
        assertThat(rejections.get()).isEqualTo(10);  // the other 10 were correctly rejected
        assertThat(finalQuantity).isEqualTo(0);      // stock hit exactly 0 — never negative
    }
}