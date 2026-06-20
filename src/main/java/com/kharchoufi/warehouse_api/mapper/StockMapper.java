package com.kharchoufi.warehouse_api.mapper;

import com.kharchoufi.warehouse_api.dto.StockResponse;
import com.kharchoufi.warehouse_api.model.Stock;

public class StockMapper {

    public static StockResponse toResponse(Stock stock) {
        return new StockResponse(
                stock.getId(),
                ProductMapper.toResponse(stock.getProduct()),
                WarehouseMapper.toResponse(stock.getWarehouse()),
                stock.getQuantity()
        );
    }
}