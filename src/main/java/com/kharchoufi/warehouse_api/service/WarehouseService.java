package com.kharchoufi.warehouse_api.service;

import com.kharchoufi.warehouse_api.dto.WarehouseRequest;
import com.kharchoufi.warehouse_api.dto.WarehouseResponse;
import com.kharchoufi.warehouse_api.mapper.WarehouseMapper;
import com.kharchoufi.warehouse_api.model.Warehouse;
import com.kharchoufi.warehouse_api.repository.WarehouseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public List<WarehouseResponse> findAll() {
        return warehouseRepository.findAll()
                .stream()
                .map(WarehouseMapper::toResponse)
                .toList();
    }

    public WarehouseResponse findById(Long id) {
        return WarehouseMapper.toResponse(getWarehouseOrThrow(id));
    }

    public WarehouseResponse create(WarehouseRequest request) {
        if (warehouseRepository.existsByCode(request.code())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "A warehouse with code '" + request.code() + "' already exists");
        }
        Warehouse saved = warehouseRepository.save(WarehouseMapper.toEntity(request));
        return WarehouseMapper.toResponse(saved);
    }

    public WarehouseResponse update(Long id, WarehouseRequest request) {
        Warehouse warehouse = getWarehouseOrThrow(id);
        warehouse.setCode(request.code());
        warehouse.setName(request.name());
        warehouse.setCity(request.city());
        return WarehouseMapper.toResponse(warehouseRepository.save(warehouse));
    }

    public void delete(Long id) {
        warehouseRepository.delete(getWarehouseOrThrow(id));
    }

    private Warehouse getWarehouseOrThrow(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Warehouse not found with id " + id));
    }
}