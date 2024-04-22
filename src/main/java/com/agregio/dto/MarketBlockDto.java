package com.agregio.dto;

import com.agregio.model.Slot;
import com.agregio.model.Type;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SuperBuilder
@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MarketBlockDto {
    private final UUID uuid;
    private final LocalDate day;
    private final Slot slot;
    private final BigDecimal price;
    private final BigDecimal quantity;
    private final Type type;
}
