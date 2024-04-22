package com.agregio.controller;

import com.agregio.repository.MarketBlockRepository;
import com.agregio.repository.ProducerRepository;
import com.agregio.repository.ProductionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

@SpringBootTest
@Transactional
public abstract class DBTest {

    static final UUID LOT_OF_F = UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff");
    static final UUID LOT_OF_0 = UUID.fromString("00000000-0000-0000-0000-000000000000");
    @Container
    static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.1")
            .withDatabaseName("agregio")
            .withUsername("postgres")
            .withPassword("postgres");
    @Autowired
    ProductionRepository productionRepository;
    @Autowired
    ProductionController productionController;
    @Autowired
    ProducerRepository producerRepository;
    @Autowired
    ProducerController producerController;
    @Autowired
    MarketController marketController;
    @Autowired
    MarketBlockRepository marketBlockRepository;
    @Autowired
    EntityManager entityManager;

    final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @BeforeAll
    public static void setUp() {
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }


    @SneakyThrows
    protected String fromFileToString(final String fileName) {
        return IOUtils.toString(requireNonNull(this.getClass().getResourceAsStream(fileName)));
    }


    @SneakyThrows
    protected <T> T fromFileToObject(final String fileName, TypeReference<T> typeref) {
        return objectMapper.readValue(fromFileToString(fileName), typeref);
    }
}
