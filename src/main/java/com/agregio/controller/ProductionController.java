package com.agregio.controller;

import com.agregio.converter.ProductionMapper;
import com.agregio.dto.ProductionDto;
import com.agregio.dto.request.ProductionCreateRequest;
import com.agregio.service.ProductionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Transactional
@Controller
public class ProductionController {
    private final ProductionMapper productionMapper;
    private final ProductionService productionService;

    @PostMapping("/productions")
    public ProductionDto create(@RequestBody ProductionCreateRequest request) {
        return productionMapper.toDto(productionService.create(productionMapper.toEntity(request)));
    }
}
