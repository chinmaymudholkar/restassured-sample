# REST Assured Test Framework (Podman Isolated)

A production-grade API testing framework designed for a **pristine host environment**. All Java compilation, dependency resolution, and test execution occur exclusively within a **Podman container**, leaving the host system completely untouched.

## Architecture Overview

This project follows a **Service Object Pattern** (adapted for APIs) with strict separation of concerns:

- **Host**: Only runs IntelliJ IDEA for editing and Podman for execution. No Java/Maven installed unless required for editing.
- **Container (Podman)**: Acts as the immutable build and test environment (Java 21 + Maven 3.9).
- **Integration**: Source code is mounted via SELinux-safe volume (`:Z`), allowing real-time editing in IntelliJ while the container handles all heavy lifting.

### Key Features
- **Zero-Trace Host**: No residual build artifacts or dependencies on the host.
- **Layered Caching**: Optimized `Dockerfile` ensures dependencies are downloaded only when `pom.xml` changes.
- **Scalable Design**: Base classes, service layers, and configuration modules allow easy expansion.
- **SELinux Compliant**: Uses `:Z` flag for secure volume mounting.

---

## Quick Start

### 1. Prerequisites
Ensure Podman is installed on your host:

``` bash
sudo dnf install podman
```
### 2. Build the Image
From the project root, build the container image:

```bash
podman build -t rest-assured-runner .
```
### 3. Run Tests
Execute the test suite inside the container:

``` bash
podman run --rm -v "$(pwd):/app:Z" rest-assured-runner mvn test
```
---

## IntelliJ IDEA Configuration (optional)

To edit code in IntelliJ while executing tests in the container:

1. Open Project: Open the project root folder in IntelliJ.
1. Create Run Configuration:
- Go to Run > Edit Configurations.
- Click + → Shell Script.
- Name: Run Tests in Podman
- Script Path: ./run-tests.sh (or podman with arguments)
- Working Directory: Project Root
1. Run: Select the configuration and click the green Run button.
Note: If you use the run-tests.sh script, it handles the volume mounting and command execution automatically.

---

## Writing New Tests

1. Extend ApiBase: All test classes should extend this.
1. Use HttpBinService: Do not call RestAssured directly in tests. Use the service layer.
1. Assert via ApiResponse: Use chainable assertions like .assertStatusCode(200).

Example:
```java
@Test
void testNewEndpoint() {
    ApiResponse response = service.getEndpoint("/new");
    response.assertStatusCode(200)
            .assertBody("data.status", equalTo("active"));
}
```

---

## Cleanup

Since the container is ephemeral (--rm), no cleanup is required. To remove the image manually:

```bash
podman rmi rest-assured-runner
```
