package com.agregio.dto.request;

import com.agregio.model.Slot;
import com.agregio.model.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
@ParameterObject
@NoArgsConstructor
@AllArgsConstructor
public class MarketBlockRequest {
    java.time.LocalDate from;
    LocalDate to;
    Set<Slot> slots;
    List<Type> types;
}
