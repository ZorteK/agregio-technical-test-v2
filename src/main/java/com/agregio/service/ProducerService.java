package com.agregio.service;

import com.agregio.model.Producer;
import com.agregio.repository.ProducerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProducerService {
    private final ProducerRepository producerRepository;

    public Producer create(Producer producer) {
        return producerRepository.save(producer);
    }

}
