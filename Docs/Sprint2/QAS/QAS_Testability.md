# QAS - Testability

## 1. Introduction
This document aims to detail the adoption of the Attribute-Driven Design (ADD) design method to the Quality Attribute Scenario (QAS) of Testability

## 2. Step 1: Confirm there is sufficient requirements information

### 2.1 Objective
Ensure requirements and constraints are clearly identified and aligned with project goals.

### 2.2 Problem Statement

The system must support multiple testing approaches: static, unit, mutation, consumer-driven contract testing).

### 2.3 Architecturally Significant Requirements (ASR)

- Automated testing frameworks must support static, unit, mutation, and CDC tests.
- Test execution should produce accurate, consistent, and timely reports.

### 2.4 Requirements (SMART)

| Requirement    | Description                                                                            |
|----------------|----------------------------------------------------------------------------------------|
| **Specific**   | The system must support static, unit, mutation, and CDC tests                          |
| **Measurable** | Test coverage and execution results can be verified in CI/CD reports                   |
| **Attainable** | Test frameworks and tools are available in the development and CI/CD environments      |
| **Relevant**   | Ensures quality, reliability, and maintainability of the system                        |
| **Time-bound** | Testable components and automated tests must be in place before production deployment  |

### 2.5 Variation Points
- Choice of testing frameworks or libraries for unit, mutation, or CDC testing.

### 2.6 Evolution Points
- Introduction of new testing tools or approaches in the future.
- Expanding automated tests as new features are added.

## 3. Step 2: Establish goals and select inputs for iteration

### 3.1 Iteration Goal
Enable the system to be reliably and efficiently tested across multiple levels (static, unit, mutation, CDC) and integrated into CI/CD pipelines.

### 3.2 Drivers

| Type                  | Description                                                                      |
|-----------------------|----------------------------------------------------------------------------------|
| **Functional**        | System supports tests executions                                                 |
| **Quality Attribute** | Testability, reliability, maintainability, and accuracy of test results          |
| **Constraint**        | Must integrate with existing CI/CD pipelines and supported testing frameworks    |
| **Business**          | Faster feedback during development and higher confidence in production releases  |

## 4. Step 3: Choose element(s) of the system to decompose

### 4.1 Selected Element

The Testing Layer, including test frameworks, test scripts, testable system components, and CI/CD integration points

## 5. Step 4: Choose one or more design concepts that satisfy the inputs of the iteration

### 5.1 Applied Tactics

| Quality Attribute | Tactic                  | Description                                                                    |
|-------------------|-------------------------|--------------------------------------------------------------------------------|
| Testability       | Automation              | Automate execution of static, unit, mutation, and CDC tests in CI/CD pipelines |
| Testability       | Isolation               | Define clear interfaces and APIs to enable unit and integration testing        |
| Testability       | Observability/Reporting | Ensure tests produce accurate, consistent, and actionable results              |


### 5.2 Reference Architectures and Patterns

- Microservices Testing Patterns (Unit, Contract Testing, Mutation)
- Test Pyramid Pattern
- CI/CD Integration with Automated Test Execution
- 
## 6. Step 5 – Instantiate architectural elements, allocate responsibilities, and define interfaces

### 6.1 Main components

| Component              | Responsibility                                                         | 
|------------------------|------------------------------------------------------------------------|
| Static Analysis Tool   | Analyze code for syntax, style, and potential issues                   |
| Unit Test Framework    | Execute unit tests for individual components                           |
| Mutation Test Engine   | Introduce faults to verify test coverage and quality                   |
| CDC Test Suite         | Execute consumer-driven contract tests to ensure service compatibility |
| CI/CD Pipeline         | Automate test execution and collect results                            |

## 7. Step 6 – Evaluate and Refine the Architecture

### 7.1 Outcome

- The system supports static, unit, mutation, and CDC tests.

- Test execution is automated and integrated with CI/CD pipelines.

- Reports accurately reflect system reliability and maintainability.

- Testable interfaces simplify addition of new tests in the future.

## 8. Step 7 – Iteration Closure and Refinement

This QAS ensures reliable, automated testing across all levels, improving software quality, speeding development feedback, and supporting maintainable, defect-resistant systems.

