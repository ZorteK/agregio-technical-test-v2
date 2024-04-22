package com.agregio.converter;

import com.agregio.dto.ProductionDto;
import com.agregio.dto.request.ProductionCreateRequest;
import com.agregio.exception.NotFoundException;
import com.agregio.model.MarketBlock;
import com.agregio.model.Producer;
import com.agregio.model.Production;
import com.agregio.repository.MarketBlockRepository;
import com.agregio.repository.ProducerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@AllArgsConstructor
public class ProductionMapper {
    private final ProducerRepository producerRepository;
    private final MarketBlockRepository marketBlockRepository;


    public Production toEntity(ProductionCreateRequest request) {
        Producer producer = producerRepository.findById(request.getProducer())
                .orElseThrow(() -> new NotFoundException("No Producer found with id: " + request.getProducer()));
        MarketBlock bloc = marketBlockRepository.findByDayAndSlotAndType(request.getDate(), request.getSlot(), request.getType()).orElseThrow(() -> new NoSuchElementException("No bloc found with date and type: " + request.getDate() + "  " + request.getType()));
        Production production = Production.builder()
                .quantity(request.getQuantity())
                .marketBlock(bloc)
                .producer(producer)
                .build();
        bloc.getProductions().add(production);
        return production;
    }

    public ProductionDto toDto(Production production) {
        return ProductionDto
                .builder()
                .uuid(production.getUuid())
                .quantity(production.getQuantity())
                .producer(production.getProducer().getName())
                .type(production.getMarketBlock().getType())
                .day(production.getMarketBlock().getDay())
                .slot(production.getMarketBlock().getSlot())
                .build();
    }
}

