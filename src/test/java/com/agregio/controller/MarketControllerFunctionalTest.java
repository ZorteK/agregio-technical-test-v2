package com.agregio.controller;

import com.agregio.dto.MarketBlockDetailDto;
import com.agregio.dto.MarketBlockDto;
import com.agregio.dto.ProductionDto;
import com.agregio.dto.request.*;
import com.agregio.model.Producer;
import com.agregio.model.Type;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.agregio.model.Slot.SLOT_0_3;
import static com.agregio.model.Type.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Pageable.ofSize;

class MarketControllerFunctionalTest extends DBTest {
    @SneakyThrows
    @Test
    void test_nominal_scenario() {

        Producer producer1 = producerController.create(ProducerCreateRequest.builder().name("Antimatter Corp").build());
        Producer producer2 = producerController.create(ProducerCreateRequest.builder().name("Dyson sphere projet").build());

        LocalDate today = LocalDate.parse("2024-04-20");
        MarketBlockCreateRequest request = MarketBlockCreateRequest
                .builder()
                .day(today)
                .slot(SLOT_0_3)
                .price(BigDecimal.valueOf(1.2))
                .type(Type.PRIMARY)
                .build();
        marketController.create(request);
        marketController.create(request.toBuilder().type(SECONDARY).build());
        marketController.create(request.toBuilder().type(FAST_REACTING).build());

        ProductionCreateRequest productionRequest = ProductionCreateRequest.builder()
                .quantity(BigDecimal.valueOf(1000.18))
                .type(PRIMARY)
                .producer(producer1.getUuid())
                .date(today)
                .slot(SLOT_0_3)
                .build();
        productionController.create(productionRequest);
        productionController.create(productionRequest.toBuilder().producer(producer2.getUuid()).quantity(BigDecimal.valueOf(25)).build());


        Page<MarketBlockDto> result = marketController.list(new MarketBlockRequest(), ofSize(10)).getBody();
        assertThat(result).hasSize(3);
        objectMapper.writeValueAsString(result);
        assertThat(result.getContent())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("uuid")
                .isEqualTo(fromFileToObject("/market_block_expected.json", new TypeReference<List<MarketBlockDto>>() {
                }));


        UUID firstBlockId = result.getContent().getFirst().getUuid();
        marketController.patch(firstBlockId, new MarketBlockUpdateRequest(BigDecimal.valueOf(1.3)));

        MarketBlockDetailDto firstBlockWithDetail = marketController.getById(firstBlockId).getBody();
        objectMapper.writeValueAsString(firstBlockWithDetail);
//TODO corriger pb assertJ sur l'ordre ?
//        assertThat(firstBlockWithDetail)
//                .usingRecursiveComparison().ignoringFields("uuid", "productions[].uuid").ignoringCollectionOrderInFields("productions")
//                .isEqualTo(fromFileToObject("/market_price_detail_dto_expected.json",new TypeReference<MarketBlockDetailDto>() {}));

        assertThat(firstBlockWithDetail.getProductions())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("uuid")
                .containsAll(fromFileToObject("/production_expected.json", new TypeReference<List<ProductionDto>>() {
                }));


        marketController.delete(firstBlockId);
        assertThat(marketController.list(new MarketBlockRequest(), ofSize(10)).getBody()).hasSize(2);


    }


}
