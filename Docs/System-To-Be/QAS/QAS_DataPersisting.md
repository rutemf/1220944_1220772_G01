# QAS - Data Persistence

## 1. Introduction
This document aims to explain the adoption of the Attribute-Driven Design (ADD) design method to the Quality Attribute Scenario (QAS) of Data Persistence.

It is intended to persist data in two different types of data models: relational and document, using MySQL and MongoDB respectively, with Redis cache.

## 2. Step 1: Confirm there is sufficient requirements information

### 2.1 Objective
Ensure requirements and constraints are clearly identified and aligned with project goals.

### 2.2 Problem Statement
The system must support persisting data in different data models and Data Base Management System (DBMS): MySQL+Redis and MongoDB+Redis.

### 2.1 Requirements (SMART)

| Requirement    | Description                                                            |
|----------------|------------------------------------------------------------------------|
| **Specific**   | Data must be persisted with MySQL and MongoDB, both with Redis caching |
| **Measurable** | Must not affect the functionalities                                    |
| **Attainable** | Possible through runtime configuration                                 |
| **Relevant**   | Scalability and flexibility for different deployment environments      |
| **Time-bound** | Must be operational by deployment to multi-environment CI/CD pipeline  |

### 2.4 Variation Points  
- DBMS configurations: MySQL + Redis and  MongoDB + Redis
- Data model types: Relational and Document-based
- Switching trigger: Deployment configuration

### 2.5 Evolution Points
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

### 4.2 Ractionale

### Expected Outcome


## 5. Step 4: Choose one or more design concepts that satisfy the inputs of the iteration

### 5.1 Applied Tactics

### 5.2 Reference Architectures and Patterns

### 5.3 Architectural Design Alternatives and Rationale

### 5.4 Decision Rationale




