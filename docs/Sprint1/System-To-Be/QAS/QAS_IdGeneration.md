# QAS - Id Generation

## 1. Introduction
This document aims to detail the adoption of the Attribute-Driven Design (ADD) design method to the Quality Attribute Scenario (QAS) of ID Generation for the System To-Be architecture.
It is required to implement a mechanism to generate Ids in different formats based on the following formats: random Base 65 and Timestamp with the suffix of 6 hexadecimal digits, separated by a hyphen.

## 2. Step 1: Confirm there is sufficient requirements information

### 2.1 Objective
Ensure requirements and constraints are clearly identified and aligned with project goals.

### 2.2 Problem Statement
The system must generate IDs for other entities in different formats based on varying specifications.

### 2.3 Architecturally Significant Requirements (ASR)

Generate IDs for other enSSes in different formats based on varying specifications.

### 2.4 Requirements (SMART)

| Requirement    | Description                                                                                                                 |
|----------------|-----------------------------------------------------------------------------------------------------------------------------|
| **Specific**   | Ids must be generated in different formats: random Base65 and timestamp suffixed by hexadecimal digits, separated by hyphen |
| **Measurable** | Id generation time in acceptable time limits                                                                                |
| **Attainable** | Possible through modular Id generation mechanisms                                                                           |
| **Relevant**   | Supports future extensibility for new entities and formats                                                                  |
| **Time-bound** | Must be operational by deployment to multi-environment CI/CD pipeline                                                       |

### 2.5 Variation Points
- Id formats: Random Base65 and Timestamp - 6 Hexadecimal digits
- Invocation: Local cached generation

### 2.6 Evolution Points
- Plug-in model for integrating new generation algorithms

## 3. Step 2: Establish goals and select inputs for iteration

### 3.1 Iteration Goal
Design an ID generation mechanism that allows multiple formats
- Ensures format consistency per entity type
- Allows runtime configurability to add new formats

### 3.2 Drivers

| Type                  | Description                                                                    |
|-----------------------|--------------------------------------------------------------------------------|
| **Functional**        | Generate IDs for entities in different formats based on varying specifications |
| **Quality Attribute** | Maintain uniqueness and configurability                                        |
| **Constraint**        | Must run in multi-environment deployments                                      |
| **Business**          | Enhances flexibility and extensibility for future system expansion.            |

## 4. Step 3: Choose element(s) of the system to decompose

### 4.1 Selected Element
The **IDGeneratorService** is a new service to handle the ID generation logic, having in mind the ID generation mechanism.

## 5. Step 4: Choose one or more design concepts that satisfy the inputs of the iteration

### 5.1 Applied Tactics

| Quality Attribute    | Tactic                          | Description                                                                                                         |
|----------------------|---------------------------------|---------------------------------------------------------------------------------------------------------------------|
| **Availability**     | **Replication and Failover**    | Use MySQL replicas and MongoDB replica sets to ensure continued service during node failures.                       |
| **Interoperability** | **Repository Abstraction**      | Define common repository interfaces to allow both SQL and NoSQL backends to interact seamlessly with the domain.     |
| **Flexibility**      | **Runtime Configuration**       | Enable switching between MySQL+Redis and MongoDB+Redis via Spring profiles or configuration files.                   |
| **Maintainability**  | **Layered Architecture**        | Separate domain, service, and persistence layers to simplify updates and debugging.                                 |
| **Modifiability**    | **Schema Versioning**           | Introduce schema version fields and migration scripts to evolve data structures safely.                             |
| **Portability**      | **Technology Abstraction**      | Use JPA for SQL and Spring Data MongoDB for NoSQL to minimize vendor lock-in.                                       |
| **Performance**      | **Caching and Indexing**        | Use Redis for caching and add indexes on frequently queried fields to improve response time.                         |
| **Scalability**      | **Sharding and Read Replicas**  | Distribute data horizontally in MongoDB and scale MySQL reads with replicas.                                        |
| **Testability**      | **Contract Testing**            | Use shared tests to ensure both SQL and NoSQL implementations behave consistently.                                  |

### 5.2 Reference Architectures and Patterns
- Layered Architecture
- Service Layer

## 6. Step 5 – Instantiate architectural elements, allocate responsibilities, and define interfaces

### 6.1 Main components

| Component                 | Responsibility                   | Interface / Methods               |
|---------------------------|----------------------------------|-----------------------------------|
| **IDGeneratorService**    | Central service for ID creation. | `generateIdSQL` `generateIdNoSQL` |

## 7. Step 6 – Evaluate and Refine the Architecture

### 7.1 Outcome
- Architecture is ready for future integration with distributed ID services if scalability demands increase.

## 8. Step 7 – Iteration Closure and Refinement

The problem statement has been solved, respecting the requirements goals established.
