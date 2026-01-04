# QAS - Performance

## 1. Introduction
This document aims to detail the adoption of the Attribute-Driven Design (ADD) design method to the Quality Attribute Scenario (QAS) of Performance.

## 2. Step 1: Confirm there is sufficient requirements information

### 2.1 Objective
Ensure requirements and constraints are clearly identified and aligned with project goals.

### 2.2 Problem Statement

The system must increase the performance by 25% when in high demand (>Y requests/period).

### 2.3 Architecturally Significant Requirements (ASR)

- Performance-critical microservices must scale dynamically under high load.
- The system must reduce response time and increase throughput without compromising availability.

### 2.4 Requirements (SMART)

| Requirement    | Description                                                                                      |
|----------------|--------------------------------------------------------------------------------------------------|
| **Specific**   | Performance-critical microservices must handle >Y requests per period with 25% higher throughput |
| **Measurable** | Throughput and response time can be measured and compared to baseline metrics                    |
| **Attainable** | Infrastructure supports scaling, caching, and optimization mechanisms                            |
| **Relevant**   | Ensures satisfactory user experience under peak loads and meets SLA expectations                 |
| **Time-bound** | Performance improvements must be implemented before production deployment of high-load features  |

### 2.5 Variation Points
- Request routing strategies.

### 2.6 Evolution Points

- Introduction of auto-scaling policies and performance monitoring tools.
- Future optimization of database queries or microservice workflows.

## 3. Step 2: Establish goals and select inputs for iteration

### 3.1 Iteration Goal

Enable the system to process high request volumes efficiently, achieving at least 25% improvement in throughput or equivalent reduction in response time.

### 3.2 Drivers

| Type                  | Description                                                                |
|-----------------------|----------------------------------------------------------------------------|
| **Functional**        | Handle incoming requests efficiently during peak load                      |
| **Quality Attribute** | Performance, scalability, and responsiveness                               |
| **Constraint**        | Must maintain availability and fault tolerance while improving performance |
| **Business**          | Ensure user satisfaction and SLA compliance under high demand conditions   |

## 4. Step 3: Choose element(s) of the system to decompose

### 4.1 Selected Element

Performance-critical microservices, their execution environment, and supporting infrastructure such as caches, load balancers, and databases.

## 5. Step 4: Choose one or more design concepts that satisfy the inputs of the iteration

### 5.1 Applied Tactics

| Quality Attribute | Tactic                     | Description                                                                                   |
|-------------------|----------------------------|-----------------------------------------------------------------------------------------------|
| Performance       | Load Balancing             | Distribute requests across multiple instances to prevent bottlenecks                          |
| Performance       | Optimized Database Access  | Use indexing, query optimization, and database replication to improve throughput              |

### 5.2 Reference Architectures and Patterns

- Microservices with Database-per-Service pattern
- Load Balancer pattern
- CQRS (Command Query Responsibility Segregation) for heavy read workloads

## 6. Step 5 – Instantiate architectural elements, allocate responsibilities, and define interfaces

### 6.1 Main components

| Component             | Responsibility                               |
|-----------------------|----------------------------------------------|
| Load Balancer         | Distribute requests across healthy instances |               
| Microservice Instance | Handle requests with optimized performance   | 


## 7. Step 6 – Evaluate and Refine the Architecture

### 7.1 Outcome

- System throughput improved by at least 25% under high load.

## 8. Step 7 – Iteration Closure and Refinement

This QAS ensures higher system throughput, reduced response times, and improved user experience under peak load, supporting SLA compliance and scalable, maintainable microservices