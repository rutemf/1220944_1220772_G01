# QAS - CI/CD Pipeline Execution

## 1. Introduction
This document aims to detail the adoption of the Attribute-Driven Design (ADD) design method to the Quality Attribute Scenario (QAS) of the CI/CD Pipeline for the System To-Be architecture.
It is required to implement a mechanism that runs a CI/CD pipeline that runs and tests the application.

## 2. Step 1: Confirm there is sufficient requirements information

### 2.1 Objective
Ensure requirements and constraints are clearly identified and aligned with project goals.

### 2.2 Problem Statement
A CI/CD pipeline must be created in Jenkins to test and deploy the application.

### 2.3 Architecturally Significant Requirements (ASR)

Adoption of a CI/CD automation process using Jenkins

### 2.4 Requirements (SMART)

| Requirement    | Description                                                                                                                                                         |
|----------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Specific**   | The CI/CD pipeline must be defined via a declarative Jenkinsfile and executed upon every Git commit to automatically build, test, and containerize the application. |
| **Measurable** | The total pipeline execution time must be less than 8 minutes. Test coverage must not decrease with new commits.                                                    |
| **Attainable** | Achievable using Pipeline as Code (Jenkinsfile), Maven parallelization tactics, and pre-configured Docker agents in the Jenkins environment.                        |
| **Relevant**   | It is essential for enforcing code quality standards, enabling rapid feedback to developers, and ensuring reliable, continuous integration.                         |
| **Time-bound** | The pipeline's core test and build stages must be operational and integrated into the main repository branch by the end of the current Sprint.                      |

### 2.5 Variation Points
- Pipeline Environment: Using Jenkins versus using other pipeline managers like GitHub Actions.
- Test Type Inclusion: Running only Unit Tests versus including time-consuming End-to-End (E2E) tests in the main pipeline.

### 2.6 Evolution Points
- Security Scanning: Integrate tools for static code analysis and dependency vulnerability scanning as mandatory early pipeline stages.
- Autoscaling and Orchestration: Integrate the environment with an orchestrator for automatic horizontal scaling of the application based on traffic load, moving beyond the single Virtual Machine constraint.

## 3. Step 2: Establish goals and select inputs for iteration

### 3.1 Iteration Goal
To design an automated, fast, and repeatable CI/CD pipeline that rigorously tests the application and ensures artifacts are deployable.

### 3.2 Drivers

| Type                  | Description                                                                      |
|-----------------------|----------------------------------------------------------------------------------|
| **Functional**        | Automate the build, test (Unit/Integration), and containerization processes.     |
| **Quality Attribute** | Pipeline speed and Testability                                                   |
| **Constraint**        | The pipeline must be run by Jenkins using a declarative Jenkinsfile.             |
| **Business**          | Maintain high developer productivity by providing fast feedback on code quality. |

## 4. Step 3: Choose element(s) of the system to decompose

### 4.1 Selected Element
The *Test Stage* within the Jenkins Pipeline, which contains the most critical logic for ensuring quality and often dictates the pipeline's overall Performance constraint.

## 5. Step 4: Choose one or more design concepts that satisfy the inputs of the iteration

### 5.1 Applied Tactics

| Quality Attribute | Tactic                       | Description                                                                                                                                             |
|:------------------|:-----------------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Deployability** | **Containerization**         | Package the application (Docker) and all dependencies to ensure a consistent environment across development, test, and VM production.                   |
| **Availability**  | **Redundancy**               | Implement a basic *rollback* mechanism in the deployment script to revert to the last known stable image if health checks fail.                         |
| **Modifiability** | **Configuration Management** | Externalize all environment-specific settings (database credentials, port numbers) using environment variables (in `docker-compose.yml` or VM profile). |
| **Testability**   | **Automated Testing**        | Integrate Unit, Integration, and System Tests into dedicated pipeline stages and enforce reporting of test results.                                     |

### 5.2 Reference Architectures and Patterns
- Pipeline Pattern
- Container Pattern

## 6. Step 5 – Instantiate architectural elements, allocate responsibilities, and define interfaces

### 6.1 Main components

| Component                  | Responsibility                                                     | File          |
|----------------------------|--------------------------------------------------------------------|---------------|
| **CI/CD Orchestrator**     | Manage and execute the deployment pipeline.                        | `Jenkinsfile` |
| **Deployment Script (VM)** | Executed on the VM; stops the old instance and starts the new one. | `Dockerfile`  |

## 7. Step 6 – Evaluate and Refine the Architecture

### 7.1 Outcome
- A developer pushes a change and the pipeline runs.
- When a new build passes tests it is deployed.

## 8. Step 7 – Iteration Closure and Refinement

The primary goal of automating the test and deployment process is achieved. The design utilizes a Declarative Pipeline
(Pipeline as Code) to enforce Testability and Performance through parallelism, while maintaining Availability via conditional deployment. 
The architecture is ready for the implementation of the specific Jenkinsfile.
