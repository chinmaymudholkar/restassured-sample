package io.github.chinmaymudholkar.base;

import io.github.chinmaymudholkar.config.ApiConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static io.restassured.RestAssured.given;

public abstract class ApiBase {

    @BeforeAll
    static void globalSetup() {
        ApiConfig.init();
    }

    @BeforeEach
    void setupTest() {
        // Reset specific test state if needed
    }
}