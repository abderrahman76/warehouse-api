package com.kharchoufi.warehouse_api.mapper;

import com.kharchoufi.warehouse_api.dto.ProductRequest;
import com.kharchoufi.warehouse_api.dto.ProductResponse;
import com.kharchoufi.warehouse_api.model.Product;

public class ProductMapper {
    public static Product toEntity(ProductRequest request){
        Product product = new Product();
        product.setSku(request.sku());
        product.setName(request.name());
        product.setDescription(request.description());
        return product;
    }
    public static ProductResponse toResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getDescription(),
                product.getCreatedAt()
        );
    }
}
