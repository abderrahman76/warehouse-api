package com.kharchoufi.warehouse_api.mapper;

import com.kharchoufi.warehouse_api.dto.WarehouseRequest;
import com.kharchoufi.warehouse_api.dto.WarehouseResponse;
import com.kharchoufi.warehouse_api.model.Warehouse;

public class WarehouseMapper {

    public static Warehouse toEntity(WarehouseRequest request) {
        Warehouse warehouse = new Warehouse();
        warehouse.setCode(request.code());
        warehouse.setName(request.name());
        warehouse.setCity(request.city());
        return warehouse;
    }

    public static WarehouseResponse toResponse(Warehouse warehouse) {
        return new WarehouseResponse(
                warehouse.getId(),
                warehouse.getCode(),
                warehouse.getName(),
                warehouse.getCity(),
                warehouse.getCreatedAt()
        );
    }
}