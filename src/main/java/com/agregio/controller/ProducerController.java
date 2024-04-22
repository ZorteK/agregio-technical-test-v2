package com.agregio.controller;

import com.agregio.converter.ProducerMapper;
import com.agregio.dto.request.ProducerCreateRequest;
import com.agregio.model.Producer;
import com.agregio.service.ProducerService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Transactional
@Controller
public class ProducerController {
    private final ProducerMapper producerMapper;
    private final ProducerService producerService;

    @PostMapping("/producer")
    public Producer create(@RequestBody ProducerCreateRequest request) {
        return producerService.create(producerMapper.toEntity(request));
    }
}
