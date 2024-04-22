package com.agregio.service;


import com.agregio.model.Production;
import com.agregio.repository.ProductionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductionService {

    private final ProductionRepository productionRepository;

    public Production create(Production production) {
        return productionRepository.save(production);
    }

    public void delete(Production production) {
        productionRepository.delete(production);
    }
}
