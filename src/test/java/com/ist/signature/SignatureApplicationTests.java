package com.ist.signature;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
class SignatureApplicationTests {

    @Test
    void contextLoads() {
    }

}
