package com.konasl.tokengeneration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TokenGenerationApplication implements CommandLineRunner {
    private final GenerateToken generateToken;

    public TokenGenerationApplication(GenerateToken generateToken) {
        this.generateToken = generateToken;
    }

    public static void main(String[] args) {
        SpringApplication.run(TokenGenerationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        generateToken.generateTranToken();
    }


}


