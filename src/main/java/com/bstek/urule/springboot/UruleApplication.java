package com.bstek.urule.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource({"classpath:context.xml"})
public class UruleApplication {
    public static void main(String[] args) {
        SpringApplication.run(UruleApplication.class, args);
    }
}