package com.example.springboot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;
    private static final GenericContainer<?> appFirst = new GenericContainer<>("devapp:latest")
            .withExposedPorts(8080);
    private static final GenericContainer<?> appSecond = new GenericContainer<>("prodapp:latest")
            .withExposedPorts(8081);;
    @BeforeAll
    public static void setUp() {
        appFirst.start();
        appSecond.start();
    }

    @Test
    void contextLoadsFirst() {
        Integer appFirstPort = appFirst.getMappedPort(8080);
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + appFirstPort
                + "/profile", String.class);
        String expected = "Current profile is production";
        Assertions.assertEquals(expected, forEntity.getBody());
    }

    @Test
    void contextLoadsSecond() {
        Integer appSecondPort = appSecond.getMappedPort(8081);
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + appSecondPort
                + "/profile", String.class);
        String expected = "Current profile is dev!";
        Assertions.assertEquals(expected, forEntity.getBody());
    }

}
