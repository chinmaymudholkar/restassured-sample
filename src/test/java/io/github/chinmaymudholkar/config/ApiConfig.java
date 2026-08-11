package io.github.chinmaymudholkar.config;

import io.restassured.RestAssured;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiConfig {
    public static String BASE_URL;

    static {
        loadConfig();
        init();
    }

    private static void loadConfig() {
        Properties props = new Properties();
        String envPath = "/app/.env";

        try (FileInputStream fis = new FileInputStream(envPath)) {
            props.load(fis);
            BASE_URL = props.getProperty("BASE_URL", "https://httpbin.org");
            // Note: We are ignoring TIMEOUT_MS for now to ensure compilation succeeds.
        } catch (IOException e) {
            System.err.println("Warning: Could not load .env file. Using defaults.");
            BASE_URL = "https://httpbin.org";
        }
    }

    public static void init() {
        RestAssured.baseURI = BASE_URL;

        // Removed: RestAssured.config = ... (This was causing compilation errors in 6.0.1)
        // If you need timeouts, you can set them per-request using .config() in individual tests.

        System.out.println("INFO: REST Assured initialized with BASE_URL: " + BASE_URL);
    }
}