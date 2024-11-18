package com.ist.signature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
//@EnableJpaRepositories
public class SignatureApplication {

    public static void main(String[] args) {
        SpringApplication.run(SignatureApplication.class, args);
    }

}
