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

Java 17+
Spring Boot 3.x
PostgreSQL
JPA (Hibernate)
JUnit 5 + Mockito
Lombok
SLF4J (Logging)

## Setup Instructions

### Prerequisites

1. Java 17+
2. Maven
3. PostgreSQL
4. Optional: Postman

### Clone the Repository
[git clone https://github.com/your-username/los-backend.git](https://github.com/Smriti45/loan-origination-system)

### PostgreSQL Setup

-- Create database
CREATE DATABASE postgres; -- use proper name

-- New Connection
Host: localhost
Port: 5432
Database: postgres
Username: postgres
Password: your_password

Test Connection -> Should say “Connected”

### SQL Schema

CREATE TABLE agents (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL,
    manager_id BIGINT,
    FOREIGN KEY (manager_id) REFERENCES agents(id)
);

CREATE TABLE loans (
    id SERIAL PRIMARY KEY,
    loan_id VARCHAR(100) UNIQUE NOT NULL,
    customer_name VARCHAR(255),
    customer_phone VARCHAR(20),
    loan_amount NUMERIC(15,2),
    loan_type VARCHAR(50),
    application_status VARCHAR(50),
    created_at TIMESTAMP,
    assigned_agent_id BIGINT,
    FOREIGN KEY (assigned_agent_id) REFERENCES agents(id)
);


Insert Data into Tables

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

## API Endpoints

POST	/api/v1/loans	: Submit a loan
PUT	/api/v1/agents/{agentId}/loans/{loanId}/decision	: Agent decision
GET	/api/v1/loans/status-count	: Loan counts per status
GET	/api/v1/loans?status=APPLIED&page=0&size=10	: Loans by status
GET	/api/v1/loans/customers/top	: Top 3 customers

## Postman Collection



