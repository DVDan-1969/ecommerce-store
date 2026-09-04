#E-commerce Store

E-commerce web application developed with Spring Boot.

##Technologies

-Java 17
-Spring Boot 4.1.0
-Maven
-Spring Data JPA
-Hibernate
-MySQL
-Spring Security
-Thymeleaf
-JUnit 5
-Mockito

##Features

-User authentication and authorization
-ADMIN and USER roles
-Product management
-Category management
-User management
-Order management
-Order item management
-CRUD operations through REST API
-Product search
-Product sorting and pagination
-Request data validation
-Global exception handling
-Thymeleaf web interface

##Database

The application uses MySQL for data persistence.
The database schema is managed automatically by Hibernate
using:'spring.jpa.hibernate.dll-auto=update'.

##Testing

The project includes automated tests using:

-JUnit 5
-Mockito
-MockMvc
-DataJpaTest

The tests cover service,controller and repository layers.

##Running the Application

1.Make sure MySQL Server is running.
2.Create the MySQL database 'ecommerce-store'.
3.Configure the database connection in 'application.properties'.
4.Run the Spring Boot application.
5.Open the application in the browser.

##Author

Viorel Dan Dudas

