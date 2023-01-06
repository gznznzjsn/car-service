package com.gznznzjsn.carservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * todo
 * do not write constraints separately
 * add versions to separate block in pom
 * doBegin ...
 * correct table creation
 * manage entities
 * transactional everywhere
 * не нкадо прокидывать ошибки, обрабатывать по месту возникновения
 * звездочки это плохо в запросах
 * */
@SpringBootApplication
public class CarServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarServiceApplication.class, args);
    }

}
