package com.agregio.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MarketBlockUpdateRequest {
    final BigDecimal price;
}
