package com.agregio.dto;

import com.agregio.model.Slot;
import com.agregio.model.Type;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class ProductionDto {
    private final UUID uuid;
    private final BigDecimal quantity;
    private final String producer;
    private final Type type;
    private final LocalDate day;
    private final Slot slot;

}
