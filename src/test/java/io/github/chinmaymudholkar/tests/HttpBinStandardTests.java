package io.github.chinmaymudholkar.tests;

import io.github.chinmaymudholkar.base.ApiBase;
import io.github.chinmaymudholkar.base.ApiResponse;
import io.github.chinmaymudholkar.config.TestListeners;
import io.github.chinmaymudholkar.services.HttpBinService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import io.qameta.allure.*;


@ExtendWith(TestListeners.class)
class HttpBinStandardTests extends ApiBase {

    private final HttpBinService service = new HttpBinService();

    @Test
    @Feature("StandardTests")
    @Story("GET Request")
    void testGetRequest() {
        ApiResponse response = service.getEndpoint("/get?key1=value1");
        response.assertStatusCode(200)
                .assertContentType(JSON)
                .assertBody("args.key1", equalTo("value1"));
    }

    @Test
    @Feature("StandardTests")
    @Story("POST Request")
    void testPostJsonRequest() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", "Architect");
        payload.put("experience", 20);

        ApiResponse response = service.postJsonEndpoint("/post", payload);
        response.assertStatusCode(200)
                .assertBody("json.name", equalTo("Architect"))
                .assertBody("json.experience", equalTo(20));
    }

    @Test
    @Feature("StandardTests")
    @Story("PUT Request")
    void testPutRequest() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", 101);
        payload.put("status", "active");

        ApiResponse response = service.putJsonEndpoint("/put", payload);
        response.assertStatusCode(200)
                .assertBody("json.id", equalTo(101));
    }

    @Test
    @Feature("StandardTests")
    @Story("DELETE Request")
    void testDeleteRequest() {
        ApiResponse response = service.deleteEndpoint("/delete");
        response.assertStatusCode(200);
//                .assertBody("data",  equalTo(""));
    }
}