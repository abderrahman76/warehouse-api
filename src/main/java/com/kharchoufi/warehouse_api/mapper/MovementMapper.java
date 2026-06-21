package com.kharchoufi.warehouse_api.mapper;

import com.kharchoufi.warehouse_api.dto.MovementResponse;
import com.kharchoufi.warehouse_api.model.StockMovement;

public class MovementMapper {

    public static MovementResponse toResponse(StockMovement movement) {
        return new MovementResponse(
                movement.getId(),
                ProductMapper.toResponse(movement.getProduct()),
                WarehouseMapper.toResponse(movement.getWarehouse()),
                movement.getType(),
                movement.getQuantity(),
                movement.getCreatedAt()
        );
    }
}