package io.github.chinmaymudholkar.services;

import io.github.chinmaymudholkar.base.ApiResponse;
import io.github.chinmaymudholkar.config.ApiConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class HttpBinService {

    private final String basePath;

    public HttpBinService() {
        this.basePath = "";
    }

    public ApiResponse getEndpoint(String endpoint) {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(basePath + endpoint);
        return new ApiResponse(response);
    }

    public ApiResponse postJsonEndpoint(String endpoint, Map<String, Object> payload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(basePath + endpoint);
        return new ApiResponse(response);
    }

    public ApiResponse putJsonEndpoint(String endpoint, Map<String, Object> payload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .put(basePath + endpoint);
        return new ApiResponse(response);
    }

    public ApiResponse deleteEndpoint(String endpoint) {
        Response response = given()
                .when()
                .delete(basePath + endpoint);
        return new ApiResponse(response);
    }

    public ApiResponse getEndpointWithHeader(String endpoint, String headerName, String headerValue) {
        Response response = given()
                .header(headerName, headerValue)
                .when()
                .get(basePath + endpoint);
        return new ApiResponse(response);
    }
}