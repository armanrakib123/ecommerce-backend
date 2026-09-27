# E-Commerce Backend

A RESTful E-Commerce Backend application built using Java, Spring Boot,
Spring Security, JWT, JPA/Hibernate, MySQL, and Swagger/OpenAPI.

## Features

- User registration and management
- BCrypt password encryption
- JWT-based authentication
- Role-based authorization
- USER and ADMIN roles
- Product CRUD operations
- Product search and filtering
- Product pagination
- Category management
- Shopping cart
- Order placement
- Order cancellation
- Automatic stock management
- Payment processing
- Order status management
- User data isolation
- Global exception handling
- Input validation
- Swagger/OpenAPI documentation

## Technologies

- Java 21
- Spring Boot 4
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- MySQL 8
- Maven
- Swagger / OpenAPI
- Postman

## Architecture

The project follows a layered architecture:

Controller → Service → Repository → Database

### Main Packages

- `controller` - REST API controllers
- `service` - Business logic
- `repository` - Database access
- `entity` - JPA entities
- `dto` - Request and response DTOs
- `security` - JWT and Spring Security
- `exception` - Custom exceptions and global exception handling
- `config` - Application configuration
- `category` - Category management
- `user` - User management
- `cart` - Shopping cart
- `order` - Order management
- `payment` - Payment management

## Authentication

The application uses JWT authentication.

### Login

```http
POST /api/auth/login