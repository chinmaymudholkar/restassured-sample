package io.github.chinmaymudholkar.base;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.*;

public class ApiResponse {
    private final Response response;

    public ApiResponse(Response response) {
        this.response = response;
    }

    public ApiResponse assertStatusCode(int expected) {
        response.then().statusCode(expected);
        return this;
    }

    public ApiResponse assertContentType(ContentType expected) {
        response.then().contentType(expected);
        return this;
    }

    public ApiResponse assertBody(String path, Matcher<?> matcher) {
        response.then().body(path, matcher);
        return this;
    }

    public Response getRawResponse() {
        return response;
    }

    public ApiResponse assertTimeLessThan(long maxMs) {
        response.then().time(lessThan(maxMs));
        return this;
    }
}