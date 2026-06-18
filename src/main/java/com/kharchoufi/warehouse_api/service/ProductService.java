package com.kharchoufi.warehouse_api.service;

import com.kharchoufi.warehouse_api.dto.ProductRequest;
import com.kharchoufi.warehouse_api.dto.ProductResponse;
import com.kharchoufi.warehouse_api.mapper.ProductMapper;
import com.kharchoufi.warehouse_api.model.Product;
import com.kharchoufi.warehouse_api.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    public ProductResponse findById(Long id) {
        return ProductMapper.toResponse(getProductOrThrow(id));
    }

    public ProductResponse create(ProductRequest request) {
        if (productRepository.existsBySku(request.sku())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Product with sku " + request.sku() + " already exists");
        }
        Product saved = productRepository.save(ProductMapper.toEntity(request));
        return ProductMapper.toResponse(saved);
    }

    public ProductResponse update(Long id, ProductRequest request) {
        Product product = getProductOrThrow(id);
        product.setSku(request.sku());
        product.setName(request.name());
        product.setDescription(request.description());
        return ProductMapper.toResponse(productRepository.save(product));
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    private Product getProductOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id " + id));
    }

}
