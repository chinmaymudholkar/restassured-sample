package io.github.chinmaymudholkar.tests;

import io.github.chinmaymudholkar.base.ApiBase;
import io.github.chinmaymudholkar.base.ApiResponse;
import io.github.chinmaymudholkar.config.TestListeners;
import io.github.chinmaymudholkar.services.HttpBinService;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(TestListeners.class)
class HttpBinNegativeTests extends ApiBase {

    private final HttpBinService service = new HttpBinService();

    @Test
    @Feature("NegativeTests")
    @Story("NotFound")
    void test404Handling() {
        ApiResponse response = service.getEndpoint("/status/404");
        response.assertStatusCode(404);
    }

    @Test
    @Feature("NegativeTests")
    @Story("CustomHeader")
    void testCustomHeaderEcho() {
        ApiResponse response = service.getEndpointWithHeader("/headers", "X-Custom-Header", "ArchitectValue");
        response.assertStatusCode(200)
                .assertBody("headers.X-Custom-Header", equalTo("ArchitectValue"));
    }
}