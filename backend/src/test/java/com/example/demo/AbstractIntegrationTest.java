package com.example.demo;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@SpringBootTest
@Transactional
@ActiveProfiles("unit-test")
@ExtendWith(SpringExtension.class)
public class AbstractIntegrationTest {

    private static PostgreSQLContainer POSTGRES;

    // Postgres container opzetten
    static {
        POSTGRES = new PostgreSQLContainer("postgres:latest")
                .withDatabaseName("postgrestest")
                .withUsername("postgrestest")
                .withPassword("postgrestest");
        POSTGRES.start();
    }

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
        dynamicPropertyRegistry.add("sping.datasource.username", () -> "postgrestest");
        dynamicPropertyRegistry.add("sping.datasource.password", () -> "postgrestest");
        dynamicPropertyRegistry.add("sping.datasource.url",
                () -> String.format("jdbc:postgresql://localhost:%d/%s",
                POSTGRES.getMappedPort(5050),POSTGRES.getDatabaseName()
                )
        );
    }

    // Object to Json
    public static String toJson(final Object obj){
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            ObjectMapper mapper = new ObjectMapper().setDateFormat(df);
            return mapper.writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    // Json to Object
    public static <T> T fromJson(String input, Class<T> tClass){
        try {
            return new ObjectMapper().readValue(input,tClass);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromMvcResult(MvcResult result, Class<T> clazz) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().readValue(result.getResponse().getContentAsString(), clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
