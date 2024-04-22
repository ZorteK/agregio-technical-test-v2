package com.agregio.dto.request;

import com.agregio.model.Slot;
import com.agregio.model.Type;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductionCreateRequest {

    @NotNull BigDecimal quantity;
    @NotNull Type type;
    @NotNull UUID producer;
    @NotNull LocalDate date;
    @NotNull Slot slot;

}
