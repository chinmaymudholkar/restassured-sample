package io.github.chinmaymudholkar.config;

import io.restassured.RestAssured;

public class ApiConfig {
    public static final String BASE_URL = "https://httpbin.org";
    public static long TIMEOUT_MS = 10000;

    public static void init() {
        RestAssured.baseURI = BASE_URL;
    }
}