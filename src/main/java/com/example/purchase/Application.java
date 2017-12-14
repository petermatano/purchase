package com.example.purchase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableReactiveMongoRepositories
@RequiredArgsConstructor
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
