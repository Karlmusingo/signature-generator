package com.ist.signature;

import org.springframework.boot.SpringApplication;

public class TestSignatureApplication {

    public static void main(String[] args) {
        SpringApplication.from(SignatureApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
