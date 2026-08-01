package io.github.chinmaymudholkar.config;

import org.junit.jupiter.api.extension.*;

import java.time.Duration;
import java.time.Instant;

import static java.time.Instant.*;

public class TestListeners implements BeforeEachCallback, AfterEachCallback, TestWatcher {

    // Thread-local to track start time for duration calculation
    private static final ThreadLocal<Instant> testStartTime = ThreadLocal.withInitial(() -> now());

    @Override
    public void beforeEach(ExtensionContext context) {
        String testName = context.getDisplayName();
        Instant start = now();
        testStartTime.set(start);

        System.out.println("\n" + "=".repeat(60));
        System.out.println("STARTING: " + testName);
        System.out.println("=".repeat(60));
    }

    @Override
    public void afterEach(ExtensionContext context) {
        Instant start = testStartTime.get();
        Instant end = now();
        Duration duration = Duration.between(start, end);

        String testName = context.getDisplayName();
        boolean success = context.getExecutionException().isEmpty();

        if (success) {
            System.out.println("PASSED: " + testName + " (" + duration.toMillis() + " ms)");
        } else {
            System.out.println("FAILED: " + testName + " (" + duration.toMillis() + " ms)");
            context.getExecutionException().ifPresent(ex -> {
                System.out.println("Error: " + ex.getMessage());
                // Print stack trace only on failure
                ex.printStackTrace(System.out);
            });
        }
        System.out.println("=".repeat(60) + "\n");

        testStartTime.remove();
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        // This is called if a test fails.
        // The 'afterEach' method above already handles logging,
        // but this hook allows for custom cleanup (e.g., screenshots, logs) if needed.
        System.err.println("Test Failed Hook Triggered: " + context.getDisplayName());
    }
}
