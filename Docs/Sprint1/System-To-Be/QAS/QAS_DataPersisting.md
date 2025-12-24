# QAS - Data Persistence

## 1. Introduction
This document aims to explain the adoption of the Attribute-Driven Design (ADD) design method to the Quality Attribute Scenario (QAS) of Data Persistence.

It is intended to persist data in two different types of data models: relational and document, using MySQL and MongoDB respectively, with Redis cache.

## 2. Step 1: Confirm there is sufficient requirements information

### 2.1 Objective
Ensure requirements and constraints are clearly identified and aligned with project goals.

### 2.2 Problem Statement
The system must support persisting data in different data models and Data Base Management System (DBMS): MySQL+Redis and MongoDB+Redis.

### 2.3 Architecturally Significant Requirements (ASR)

Persistence of relational and document data models, MySQL and MongoDB respectively, Database Management System (DBMS).

### 2.4 Requirements (SMART)

| Requirement    | Description                                                            |
|----------------|------------------------------------------------------------------------|
| **Specific**   | Data must be persisted with MySQL and MongoDB, both with Redis caching |
| **Measurable** | Must not affect the functionalities                                    |
| **Attainable** | Possible through runtime configuration                                 |
| **Relevant**   | Scalability and flexibility for different deployment environments      |
| **Time-bound** | Must be operational by deployment to multi-environment CI/CD pipeline  |

### 2.5 Variation Points  
- DBMS configurations: MySQL + Redis and  MongoDB + Redis
- Data model types: Relational and Document-based

### 2.6 Evolution Points
- Support for hybrid persistence strategies

## 3. Step 2: Establish goals and select inputs to be considered in the iteration

### 3.1 Iteration Goal
Ensure data is persisted in different data models that:
- Supports switching between MySQL+Redis and MongoDB+Redis
- Preserves data integrity and system behavior across models

### 3.2 Drivers

| Type                  | Description                                                         |
|-----------------------|---------------------------------------------------------------------|
| **Functional**        | Persist data using MySQL+Redis and MongoDB+Redis                    |
| **Quality Attribute** | Data consistency, fault tolerance, and efficient model switching    |
| **Constraint**        | Must operate within production environments with minimal downtime   |
| **Business**          | Enhances flexibility and extensibility for future system expansion. |

## 4. Step 3: Choose Element(s) of the System to Decompose

### 4.1 Selected Element

Implementation of GenreSQL, GenreNoSQL, GenreRepositorySQL and GenreRepositoryNoSQL.

## 5. Step 4: Choose one or more design concepts that satisfy the inputs of the iteration

### 5.1 Applied Tactics

| Quality Attribute | Tactic                       | Description                                                                                                                  |
|-------------------|------------------------------|------------------------------------------------------------------------------------------------------------------------------|
| **Availability**  | **Replication and Caching**  | Use Redis caching and database replication to improve data availability and reduce downtime.                                 |
| **Modifiability** | **Encapsulation**            | Isolate ID logic in a single service (IDGeneratorService) so algorithm changes do not affect entity creation or persistence. | 
| **Performance**   | **Pre-computation**          | Generate IDs and cache frequent queries in memory to reduce database latency and improve response time.                      | 
| **Portability**   | **Abstraction**              | Eliminate dependency on specific DB vendors by abstracting persistence logic behind repository interfaces.                   | 
| **Scalability**   | **Stateless Design**         | Maintain repositories and services as stateless components to enable horizontal scaling and distributed deployments.         | 
| **Reliability**   | **Timestamp Inclusion**      | Embed time-based components in IDs to ensure global uniqueness and traceability across distributed systems.                  | 
| **Extensibility** | **Plug-in Architecture**     | Allow new persistence strategies or ID generation algorithms to be integrated dynamically without major refactoring.         |

### 5.2 Reference Architectures and Patterns

- Layered Architecture
- Service Layer

## 6. Step 5 – Instantiate architectural elements, allocate responsibilities, and define interfaces

### 6.1 Main components

For Genre example:

| Component                | Responsibility                        |
|--------------------------|---------------------------------------|
| **GenreSQL**             | Maps domain entity to SQL database    |
| **GenreNoSQL**           | Maps domain entity to NoSQL database  |
| **GenreRepositorySQL**   | Manage Genre entity in SQL database   | 
| **GenreRepositoryNoSQL** | Manage Genre entity in NoSQL database |

## 7. Step 6 – Evaluate and Refine the Architecture

### 7.1 Outcome
- Architecture is ready for future integration with other databases.

## 8. Step 7 – Iteration Closure and Refinement

The problem statement has been solved, respecting the requirements goals established.



