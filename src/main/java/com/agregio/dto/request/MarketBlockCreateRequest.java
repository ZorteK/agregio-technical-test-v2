package com.agregio.dto.request;

import com.agregio.model.Slot;
import com.agregio.model.Type;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class MarketBlockCreateRequest {
    @NotNull private LocalDate day;
    @NotNull private Slot slot;
    @NotNull private BigDecimal price;
    @NotNull private Type type;
}
