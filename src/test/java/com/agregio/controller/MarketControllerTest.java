package com.agregio.controller;

import com.agregio.dto.request.MarketBlockCreateRequest;
import com.agregio.dto.request.MarketBlockRequest;
import com.agregio.model.Slot;
import com.agregio.model.Type;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.agregio.model.Slot.SLOT_18_21;
import static com.agregio.model.Slot.SLOT_9_12;
import static com.agregio.model.Type.FAST_REACTING;
import static java.time.LocalDate.parse;
import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Pageable.ofSize;

class MarketControllerTest extends DBTest {
    @SneakyThrows
    @Test
    void test_nominal_scenario() {

        LocalDate startDate = parse("2024-04-20");
        BigDecimal price = BigDecimal.valueOf(1.2);

        var requests = range(0, 3)
                .mapToObj(startDate::plusDays)
                .flatMap(day -> Stream.of(Slot.values())
                        .flatMap(slot -> Stream.of(Type.values())
                                .map(type -> createRequest(day, slot, price, type))))
                .toList();

        requests.forEach(marketController::create);

        var result = marketController.list(new MarketBlockRequest(), ofSize(10)).getBody();
        assertThat(result.getTotalElements()).isEqualTo(72L);

        result = marketController.list(new MarketBlockRequest(parse("2024-04-21"), parse("2024-04-22"), Set.of(SLOT_18_21, SLOT_9_12), List.of(FAST_REACTING)), ofSize(10)).getBody();
        assertThat(result.getTotalElements()).isEqualTo(4l);
        assertThat(result.getContent()).hasSize(4);

        result = marketController.list(new MarketBlockRequest(parse("2024-04-21"), parse("2024-04-22"), Set.of(SLOT_18_21), List.of(FAST_REACTING)), PageRequest.of(0,1, Sort.Direction.DESC, "day")).getBody();
        assertThat(result.getContent().getFirst().getDay()).isEqualTo(parse("2024-04-22"));
        result = marketController.list(new MarketBlockRequest(parse("2024-04-21"), parse("2024-04-22"), Set.of(SLOT_18_21), List.of(FAST_REACTING)), PageRequest.of(1,1, Sort.Direction.DESC, "day")).getBody();
        assertThat(result.getContent().getFirst().getDay()).isEqualTo(parse("2024-04-21"));

    }

    private MarketBlockCreateRequest createRequest(LocalDate day, Slot slot, BigDecimal price, Type type) {
        return MarketBlockCreateRequest.builder()
                .day(day)
                .slot(slot)
                .price(price)
                .type(type)
                .build();
    }

}
