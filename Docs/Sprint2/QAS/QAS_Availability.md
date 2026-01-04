# QAS - Availability

## 1. Introduction
This document aims to detail the adoption of the Attribute-Driven Design (ADD) design method to the Quality Attribute Scenario (QAS) of Availability.

## 2. Step 1: Confirm there is sufficient requirements information

### 2.1 Objective
Ensure requirements and constraints are clearly identified and aligned with project goals.

### 2.2 Problem Statement

The system must improve its availability.

### 2.3 Architecturally Significant Requirements (ASR)

- The system must tolerate microservice and infrastructure failures without impacting users.
- Failover mechanisms must be implemented for all critical services.

### 2.4 Requirements (SMART)

| Requirement    | Description                                                                     |
|----------------|---------------------------------------------------------------------------------|
| **Specific**   | The system must route traffic to healthy instances in case of failure           |
| **Measurable** | Service uptime should remain above the SLA threshold                            |
| **Attainable** | Redundant instances and failover mechanisms are supported by the infrastructure |
| **Relevant**   | Ensures continuous service delivery and user satisfaction                       |
| **Time-bound** | Availability improvements must be in place before production deployment         |

### 2.5 Variation Points
- Failover mechanisms for different types of databases or services

### 2.6 Evolution Points
- Expansion to multi-region or multi-cloud deployments for higher resilience
- Integration of automated self-healing or scaling mechanisms in the future

## 3. Step 2: Establish goals and select inputs for iteration

### 3.1 Iteration Goal

Ensure continuous service availability by implementing replication, failover, and traffic routing mechanisms for all critical services and databases.

### 3.2 Drivers

| Type                  | Description                                                                   |
|-----------------------|-------------------------------------------------------------------------------|
| **Functional**        | System continues serving requests despite instance or infrastructure failures |
| **Quality Attribute** | Availability, reliability, fault tolerance                                    |
| **Constraint**        | Must operate within existing infrastructure and cloud environment             |
| **Business**          | Maintain SLA commitments and minimize user downtime                           |

## 4. Step 3: Choose element(s) of the system to decompose

Critical microservices and their persistence layer (MySQL and MongoDB instances) responsible for handling client requests

### 4.1 Selected Element

## 5. Step 4: Choose one or more design concepts that satisfy the inputs of the iteration

### 5.1 Applied Tactics

| Quality Attribute | Tactic                   | Description                                                                                                      |
|-------------------|--------------------------|------------------------------------------------------------------------------------------------------------------|
| Availability      | Replication and Failover | Use MySQL replicas and MongoDB replica sets to ensure continued service during node failures.                    |
| Interoperability  | Repository Abstraction   | Define common repository interfaces to allow both SQL and NoSQL backends to interact seamlessly with the domain. |
| Fault Tolerance   | Load Balancing           | Route client requests to healthy instances automatically when failures occur                                     |

### 5.2 Reference Architectures and Patterns

- Microservices with Database-per-Service pattern
- Load Balancer pattern

## 6. Step 5 – Instantiate architectural elements, allocate responsibilities, and define interfaces

### 6.1 Main components

| Component             | Responsibility                                 | 
|-----------------------|------------------------------------------------|
| Load Balancer         | Route traffic to healthy instances             | 
| Microservice Instance | Serve client requests and failover seamlessly  | 


## 7. Step 6 – Evaluate and Refine the Architecture

### 7.1 Outcome
- Critical services remain available despite instance or infrastructure failures.
- Load balancing improves overall fault tolerance.

## 8. Step 7 – Iteration Closure and Refinement

This QAS ensures high system availability, minimizing downtime, maintaining SLA compliance, and improving user satisfaction and trust in the system.

