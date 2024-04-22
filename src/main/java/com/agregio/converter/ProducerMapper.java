package com.agregio.converter;

import com.agregio.dto.request.ProducerCreateRequest;
import com.agregio.model.Producer;
import org.springframework.stereotype.Component;

@Component
public class ProducerMapper {
    public Producer toEntity(ProducerCreateRequest request) {
        return Producer.builder()
                .name(request.getName()).build();
    }
}
