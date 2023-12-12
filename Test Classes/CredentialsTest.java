package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.Credentials;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CredentialsTest {

    @Autowired
    private Credentials credentials;

    @Test
    void getAccessToken()
    {
        // Act
        String accessToken = credentials.getAccessToken();

        // Assert
        assertNotNull(accessToken);
    }
}