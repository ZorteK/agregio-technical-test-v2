package com.agregio.converter;

import com.agregio.dto.MarketBlockDetailDto;
import com.agregio.dto.MarketBlockDto;
import com.agregio.dto.ProductionDto;
import com.agregio.dto.request.MarketBlockCreateRequest;
import com.agregio.model.MarketBlock;
import com.agregio.model.Production;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;


@Component
public class MarketMapper {
    public Page<MarketBlockDto> toDto(Page<MarketBlock> marketBlocks) {
        return marketBlocks.map(this::toDto);
    }

    public MarketBlockDto toDto(MarketBlock block) {
        return MarketBlockDto
                .builder()
                .uuid(block.getUuid())
                .day(block.getDay())
                .slot(block.getSlot())
                .price(block.getPrice())
                .quantity(block.getProductions().stream().map(Production::getQuantity).reduce(ZERO, BigDecimal::add))
                .type(block.getType()).build();
    }

    public MarketBlock fromRequest(MarketBlockCreateRequest request) {
        return MarketBlock.builder()
                .day(request.getDay())
                .slot(request.getSlot())
                .price(request.getPrice())
                .type(request.getType())
                .build();
    }

    public MarketBlockDetailDto toDtoWithDetail(MarketBlock block) {
        return MarketBlockDetailDto
                .builder()
                .uuid(block.getUuid())
                .day(block.getDay())
                .slot(block.getSlot())
                .price(block.getPrice())
                .quantity(block.getProductions().stream().map(Production::getQuantity).reduce(ZERO, BigDecimal::add))
                .productions(block.getProductions().stream().map(this::toProductionDto).toList())
                .type(block.getType()).build();
    }

    private ProductionDto toProductionDto(Production it) {
        return ProductionDto
                .builder()
                .uuid(it.getUuid())
                .quantity(it.getQuantity())
                .producer(it.getProducer().getName())
                .build();
    }
}

