package io.github.chinmaymudholkar.tests;

import io.github.chinmaymudholkar.base.ApiBase;
import io.github.chinmaymudholkar.base.ApiResponse;
import io.github.chinmaymudholkar.services.HttpBinService;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;


class HttpBinNegativeTests extends ApiBase {

    private final HttpBinService service = new HttpBinService();

    @Test
    void test404Handling() {
        ApiResponse response = service.getEndpoint("/status/404");
        response.assertStatusCode(404);
    }

    @Test
    void testCustomHeaderEcho() {
        ApiResponse response = service.getEndpointWithHeader("/headers", "X-Custom-Header", "ArchitectValue");
        response.assertStatusCode(200)
                .assertBody("headers.X-Custom-Header", equalTo("ArchitectValue"));
    }
}