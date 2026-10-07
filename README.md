eureka-server is the service registry for the insurance platform demo. Every
other service registers here on startup; the gateway discovers all downstream
services through this registry. It has no business endpoints and no
RestController: it exposes the Eureka dashboard at / and Actuator at
/actuator (health, info).

The dashboard lists every registered instance, which makes it a nice live view
during a demo: push a new version and the registry shows the fresh instance.

Part of the demo platform: Java 8, Spring Boot 2.7.0, Spring Cloud 2021.0.3.

## Run

    ./mvnw spring-boot:run

Listens on port 8080, or the PORT environment variable when deployed to Cloud
Foundry. On Cloud Foundry it gets an internal-only route
(eureka-server.apps.internal); nothing here is exposed publicly.

