# QAS - Data Persistence

## 1. Introduction
This document aims to explain the adoption of the Attribute-Driven Design (ADD) design method to the Quality Attribute Scenario (QAS) of Runtime Configurability.

It is intended the alternatives must be defined during configuration (setup time), which directly impacts runtime behavior.

## 2. Step 1: Confirm there is sufficient requirements information

### 2.1 Objective
Ensure requirements and constraints are clearly identified and aligned with project goals.

### 2.2 Problem Statement
The system must support runtime configuration regarding persistence and APIs.

### 2.3 Architecturally Significant Requirements (ASR)

Definition of the alternatives above during setup time, impacting directly the runtime behavior.

### 2.4 Requirements (SMART)

| Requirement    | Description                                                                                                        |
|----------------|--------------------------------------------------------------------------------------------------------------------|
| **Specific**   | The system must support runtime configuration regarding ID generation, persistence and APIs                        |
| **Measurable** | The alternatives switch must be defined during configuration (setup time), which directly impacts runtime behavior |
| **Attainable** | Possible through modification of the application.properties file                                                   |
| **Relevant**   | Ensures flexibility, fault tolerance, extensibility and modificability                                             |
| **Time-bound** | N/A                                                                                                                |

### 2.5 Variation Points
- Database configuration: MySQL + Redis or MongoDB + Redis
- External API integration: Google Books API or Open Library API

### 2.6 Evolution Points
-Addition of new external APIs

## 3. Step 2: Establish goals and select inputs to be considered in the iteration

### 3.1 Iteration Goal
- Runtime configurability impacts the runtime behaviour

### 3.2 Drivers

| Type                  | Description                                                                 |
|-----------------------|-----------------------------------------------------------------------------|
| **Functional**        | Support dynamic selection of persistence layer and APIs                     |
| **Quality Attribute** | Focus on modifiability, extensibility, and flexibility at runtime           |
| **Constraint**        | Configuration changes must occur before runtime startup (setup-time only)   |
| **Business**          | Reduces maintenance cost and enables faster deployment for multiple clients |

## 4. Step 3: Choose Element(s) of the System to Decompose

### 4.1 Selected Element

The application.properties file

## 5. Step 4: Choose one or more design concepts that satisfy the inputs of the iteration

### 5.1 Applied Tactics

| Quality Attribute  | Tactic                       | Description                                                                                          |
|--------------------|------------------------------|------------------------------------------------------------------------------------------------------|
| **Modifiability**  | **Parameterization**         | Use configuration file (`application.properties`) to modify system behavior without code changes.    |
| **Extensibility**  | **Plug-in Architecture**     | Enable new implementations (e.g., new APIs or databases) to be added without modifying core logic.   |
| **Availability**   | **Redundancy / Failover**    | Switch automatically to alternative configurations if one fails.                                     |
| **Scalability**    | **Stateless Configuration**  | Keep configuration loading independent of runtime state to support distributed deployments.          |

### 5.2 Reference Architectures and Patterns

N/A

## 6. Step 5 – Instantiate architectural elements, allocate responsibilities, and define interfaces

### 6.1 Main components

| Component                  | Responsibility             |
|----------------------------|----------------------------|
| **application.properties** | Defines the options to use |

## 7. Step 6 – Evaluate and Refine the Architecture

### 7.1 Outcome
- Architecture is ready for future for runtime configurability

## 8. Step 7 – Iteration Closure and Refinement

The problem statement has been solved, respecting the requirements goals established.



