# Loan Origination System (LOS)

### Features

1. Submit Loan Applications
2. Background Loan Approval Simulation (Threaded)
3. Agent Assignment & Manager Notifications
4. Loan Decision Workflow
5. Loan Status Monitoring & Reporting
6. Top Customer Metrics
7. Pagination Support
8. Mock Notification Integration
9. JUnit + Mockito Test Coverage

### Tech Stack

1. Java 17+
2. Spring Boot 3.x
3. PostgreSQL
4. JPA (Hibernate)
5. JUnit 5 + Mockito
6. Lombok
7. SLF4J (Logging)

## Setup Instructions

### Prerequisites

1. Java 17+
2. Maven
3. PostgreSQL
4. Optional: Postman

### Clone the Repository
https://github.com/Smriti45/loan-origination-system.git

### PostgreSQL Setup

-- Create database
CREATE DATABASE postgres; -- use proper name

-- New Connection
1. Host: localhost
2. Port: 5432
3. Database: postgres
4. Username: postgres
5. Password: your_password

6. Test Connection -> Should say “Connected”

### Update application.properties

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

### Run the App

mvn clean spring-boot:run

Test Coverage is more 85%

## API Endpoints

1. Submit a loan : POST	/api/v1/loans	
2. Agent decision : PUT	/api/v1/agents/{agentId}/loans/{loanId}/decision	
3. Loan counts per status : GET	/api/v1/loans/status-count
4. Loans by status :  GET	/api/v1/loans?status=APPLIED&page=0&size=10	
5. Top 3 customers : GET	/api/v1/loans/customers/top	

## Postman Collection

https://crimson-sunset-859222.postman.co/workspace/abc~6d835d20-7b0a-4a06-b80f-1d12533fa1e1/collection/20941754-f1857cbb-0a88-4983-b637-12b01d77b5fc?action=share&creator=20941754

## Future Scopes

TBU
