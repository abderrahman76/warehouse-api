package com.kharchoufi.warehouse_api.service;

import com.kharchoufi.warehouse_api.dto.ProductRequest;
import com.kharchoufi.warehouse_api.dto.ProductResponse;
import com.kharchoufi.warehouse_api.model.Product;
import com.kharchoufi.warehouse_api.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void create_savesAndReturnsProduct_whenSkuIsNew() {
        // Arrange
        ProductRequest request = new ProductRequest("SKU-001", "Drill", "18V drill");
        when(productRepository.existsBySku("SKU-001")).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product p = invocation.getArgument(0);
            p.setId(1L);
            return p;
        });

        // Act
        ProductResponse response = productService.create(request);

        // Assert
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.sku()).isEqualTo("SKU-001");
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void create_throwsConflict_whenSkuAlreadyExists() {
        // Arrange
        ProductRequest request = new ProductRequest("SKU-001", "Drill", "18V drill");
        when(productRepository.existsBySku("SKU-001")).thenReturn(true);

        // Act + Assert
        assertThatThrownBy(() -> productService.create(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("already exists");

        verify(productRepository, never()).save(any());
    }

    @Test
    void findById_throwsNotFound_whenProductMissing() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.findById(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("not found");
    }
}
