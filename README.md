# My Java SDK

## Overview
This project is a Java SDK designed to simplify integration with APIs and services. It includes caching mechanisms, API client utilities, and a lightweight HTTP server for serving cached responses.

---

## Features

- **API Client:** Simplifies HTTP requests with caching using Redis and Caffeine.
- **Caching:** Supports near-cache (Caffeine) and distributed cache (Redis).
- **HTTP Server:** Lightweight server for serving cached API responses.
- **Annotations:** Custom annotations for REST controllers and mappings.
- **Multithreading:** Background cache refresh mechanism.
- **Extensibility:** Modular design for easy integration.

---

## Technologies

- **Language:** Java
- **Caching:** Redis, Caffeine
- **HTTP Server:** NanoHTTPD
- **Build Tool:** Maven
- **Utilities:** Jedis, Threading

---

## Installation

### Prerequisites

- JDK 8 or higher
- Maven
- Redis server running locally on port 6379

### Build & Run

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/my-java-sdk.git
   cd my-java-sdk
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   java -cp target/my-java-sdk-1.0.jar com.mysdk.ApiServer
   ```

---

## Usage

### API Client

Use the `ApiClient` class to make HTTP requests with caching:
```java
ApiClient client = new ApiClient("http://localhost:3002");
ApiResponse response = client.getWithCache("/rule-engine/api/v1/groups/78/rules");
System.out.println(response.getBody());
```

### HTTP Server

Start the `ApiServer` to serve cached responses:
```java
ApiServer server = new ApiServer(8080, "http://localhost:3002");
server.start();
```

Access the server at `http://localhost:8080/api/rules`.

---

## Project Structure

```
my-java-sdk/
├── lib/
│   ├── caffeine-3.1.8.jar
│   ├── demo-0.0.1-SNAPSHOT.jar
│   ├── demo-0.0.1-SNAPSHOT-plain.jar
│   ├── jedis-4.4.3.jar
│   └── nanohttpd.jar
├── src/
│   ├── main/
│   │   ├── java/com/mysdk/
│   │   │   ├── ApiClient.java
│   │   │   ├── ApiResponse.java
│   │   │   ├── ApiServer.java
│   │   │   ├── CacheRefresher.java
│   │   │   ├── TestApiClient.java
│   │   │   ├── annotations/
│   │   │   │   ├── GetMapping.java
│   │   │   │   ├── RequestMapping.java
│   │   │   │   └── RestController.java
│   │   │   └── controllers/
│   │   │       └── ApiController.java
│   │   └── resources/
│   └── test/
│       └── java/com/mysdk/
├── README.md
├── pom.xml
└── sources.txt

```

---

## Additional References

- [NanoHTTPD Documentation](https://nanohttpd.org/)
- [Redis Java Client (Jedis)](https://github.com/redis/jedis)
- [Caffeine Cache](https://github.com/ben-manes/caffeine)

---

## Author

**Gopi Chand Gopu**  
[gopichand915915@example.com](mailto:gopichand915915@example.com)
