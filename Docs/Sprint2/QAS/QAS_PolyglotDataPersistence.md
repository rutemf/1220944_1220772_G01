# QAS - Polyglot Data Persistence

## 1. Introduction
This document aims to detail the adoption of the Attribute-Driven Design (ADD) design method to the Quality Attribute Scenario (QAS) of Polyglot Data Persistence.

## 2. Step 1: Confirm there is sufficient requirements information

### 2.1 Objective
Ensure requirements and constraints are clearly identified and aligned with project goals.

### 2.2 Problem Statement

The system must adopt a polyglot data persistence with SQL and NoSQL, respectively, MySQL and MongoDB.

### 2.3 Architecturally Significant Requirements (ASR)
- Each microservice must have its own database (Database-per-Service pattern).
- Support polyglot persistence: relational and document-based. 
- Ensure database selection aligns with consistency, throughput, and schema flexibility requirements.

### 2.4 Requirements (SMART)

| Requirement    | Description                                                                                                                 |
|----------------|-----------------------------------------------------------------------------------------------------------------------------|
| **Specific**   | Each microservice must have an independent database using either MySQL (SQL) or MongoDB (NoSQL) according to its data model |
| **Measurable** | Database usage can be verified by service monitoring and testing for throughput, latency, and consistency                   |
| **Attainable** | Both MySQL and MongoDB are supported in the deployment environment and accessible by all services                           |
| **Relevant**   | Ensures performance, fault tolerance, and maintainability for microservices architecture                                    |
| **Time-bound** | Polyglot persistence must be implemented before production deployment of all microservices                                  |

### 2.5 Variation Points
-Choice of SQL vs NoSQL for new microservices

### 2.6 Evolution Points
- Introduction of additional database technologies in the future

## 3. Step 2: Establish goals and select inputs for iteration

### 3.1 Iteration Goal

Design a persistence layer that allows microservice to use different database types while ensuring performance, consistency, and fault tolerance.

### 3.2 Drivers

| Type                  | Description                                                                  |
|-----------------------|------------------------------------------------------------------------------|
| **Functional**        | Each microservice must persist and retrieve data independently               |
| **Quality Attribute** | Performance, scalability, availability, fault tolerance, and maintainability |
| **Constraint**        | According to the microservice requirements                                   |
| **Business**          | Support faster release cycles and reduce operational overhead                |

## 4. Step 3: Choose element(s) of the system to decompose

### 4.1 Selected Element

Persistence Layer of the microservices architecture, including database connectors, repositories, and data access interfaces.

## 5. Step 4: Choose one or more design concepts that satisfy the inputs of the iteration

### 5.1 Applied Tactics

| Quality Attribute    | Tactic                 | Description                                                                                          |
|----------------------|------------------------|------------------------------------------------------------------------------------------------------|
| Performance          | Database-per-Service   | Each microservice has its own database to avoid contention and improve throughput                    |
| Availability         | Polyglot persistence   | Use the appropriate database type to maximize uptime and reliability                                 |
| Fault Tolerance      | Replication            | Enable database-level replication and partitioning to handle failures and maintain data availability |
| Scalability          | Independent scaling    | Each microservice can scale its persistence layer independently based on workload                    |


### 5.2 Reference Architectures and Patterns

- Database-per-Service Pattern (from Microservices Architecture)
- Polyglot Persistence Pattern (from Data Architecture Patterns)
- Repository Pattern for abstracting database access

## 6. Step 5 – Instantiate architectural elements, allocate responsibilities, and define interfaces

### 6.1 Main components

| Component         | Responsibility                                                | 
|-------------------|---------------------------------------------------------------|
| MySQL Connector   | Connect to relational database and execute queries            |
| MongoDB Connector | Connect to document database and perform operations           |
| Repository Layer  | Abstract persistence operations from microservices            |
| Microservice      | Uses repository to persist and retrieve domain-specific data  |

## 7. Step 6 – Evaluate and Refine the Architecture

### 7.1 Outcome

- Each microservice successfully interacts with its designated database type.
- The system supports independent scaling of services and databases.
- Performance and fault tolerance meet specified quality attributes.
- Future database technologies can be integrated with minimal changes to microservices.

## 8. Step 7 – Iteration Closure and Refinement

Through this QAS, it is possible to enable each microservice to independently persist and access data using the most 
suitable database (MySQL or MongoDB) while ensuring performance, scalability, and reliability.

