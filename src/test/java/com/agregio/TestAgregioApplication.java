package com.agregio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestAgregioApplication {

    public static void main(String[] args) {
        SpringApplication.from(AgregioApplication::main).with(TestAgregioApplication.class).run(args);
    }

}
