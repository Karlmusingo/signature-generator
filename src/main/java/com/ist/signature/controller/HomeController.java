package com.ist.signature.controller;

import com.ist.signature.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_V1)
@RequiredArgsConstructor
public class HomeController {

    @Value("${spring.host}")
    private String apiHost;

    @Value("${spring.mail.mailjet.api.key}")
    private String emailApiKey;

    @Value("${spring.mail.mailjet.api.secret}")
    private String emailApiSecret;

    @Value("${spring.mail.mailjet.from.address}")
    private String emailFromAddress;

    @GetMapping("/")
    public ResponseEntity<String> helloWorld() {
        System.out.println(apiHost);
        System.out.println(emailFromAddress);
        return ResponseEntity.ok("Hello World!");
    }
}
