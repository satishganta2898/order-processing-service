#Order Processing Service

INFO
A Spring Boot RESTful backend that lets users create product, orders and store them in a database, and processes orders asynchronously.


#Prerequisites
JDK 17+
Maven 3+


#Application Set-Up

1. Download the Zip or clone the project from the GIT repository.
2. Use IDE like Eclipse or STS.
3. Import the project as Existing Maven Project.
4. After successful import , update the Maven Dependencies.


Use the following options to run this application, please make sure Maven is installed if not please download form here https://maven.apache.org/
1. Run > mvn spring-boot:run
2. Or In Eclipse right click on project -> run as->Spring boot application

The application starts on port 8080


**Access H2 database console** - http://localhost:8080/h2-console - use JDBC URL, satish and password


#Swagger Documentation

After starting the app, go to - http://localhost:8080/swagger-ui.html
Test all the endpoints directly from the browser

- 'POST /products' - add a product
- 'POST /orders' - create new order
- 'GET /orders/{id} - get the order status

POSTMAN CURL's:

Create Product: POST

curl --location 'http://localhost:8080/products' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Rose",
    "price": 20.0
}'

Create Order: POST

curl --location 'http://localhost:8080/orders' \
--header 'Content-Type: application/json' \
--data '{
 "productId": 1
}'

Get OrderStatus: GET

curl --location 'http://localhost:8080/orders/1' \
--header 'Content-Type: application/json'




## Design Principles

- DTO Pattern: Only Data Transfer Objects (DTOs) are exposed in API responses. JPA entities are internal.
- Layered Architecture: Responsibility divided between controllers, services, repositories, and async processing for clarity and maintainability.
- Asynchronous Processing: Order creation triggers a background job for realistic processing simulation.
- Error Handling: Returns 404 for missing resources, 400 for malformed input, with clear messages via a global exception handler.
- Swagger/OpenAPI: Endpoints are documented.
- Unit Testing: controller logic is covered with JUnit 5 and Mockito unit tests.



