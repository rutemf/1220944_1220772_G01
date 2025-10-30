# QAS - Deployment Environment

## 1. Introduction
This document aims to detail the adoption of the Attribute-Driven Design (ADD) design method to the Quality Attribute Scenario (QAS) of the Deployment Environment for the System To-Be architecture.
It is required to implement a mechanism that deploys the application to a virtual machine for public access.

## 2. Step 1: Confirm there is sufficient requirements information

### 2.1 Objective
Ensure requirements and constraints are clearly identified and aligned with project goals.

### 2.2 Problem Statement
The system must be deployed to a virtual machine, so it can be publicly accessed.

### 2.3 Requirements (SMART)

| Requirement    | Description                                                                                                                                                               |
|----------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Specific**   | The system must be packaged (containerized using Docker) and deployed to a Virtual Private Server (VPS) accessible via the public internet, exposing HTTP/HTTPS ports.    |
| **Measurable** | The total deployment time (from build completion to public accessibility) must be less than 8 minutes.                                                                    |
| **Attainable** | Achievable by implementing a CI/CD pipeline that uses SSH/SCP to transfer the artifact (Docker image) and utilizes a container runtime (Docker/Docker Compose) on the VM. |
| **Relevant**   | It is essential for the public availability of the system, allowing end-users to access the production services.                                                          |
| **Time-bound** | The deployment mechanism must be operational and functional by the end of the Sprint.                                                                                     |

### 2.4 Variation Points
- Deployment Target: Usage of a Virtual Machine (VM) versus Platform as a Service (PaaS).
- Exposing API Publicly: Exposing API Publicly versus exposing API for certain IPs.

### 2.5 Evolution Points
- Autoscaling: Integrate the environment with an orchestrator for automatic horizontal scaling.

## 3. Step 2: Establish goals and select inputs for iteration

### 3.1 Iteration Goal
To design an automated deployment mechanism that ensures public availability and low deployment time, facilitating future updates.

### 3.2 Drivers

| Type                  | Description                                                                  |
|-----------------------|------------------------------------------------------------------------------|
| **Functional**        | Automate the build and deployment process for the containerized application. |
| **Quality Attribute** | Deployability and Availability.                                              |
| **Constraint**        | The target must be a public Virtual Machine (VM).                            |
| **Business**          | Minimize downtime and reduce manual operational effort.                      |

## 4. Step 3: Choose element(s) of the system to decompose

### 4.1 Selected Element
The *Deployment Pipeline* within the CI/CD environment, which orchestrates the transition of the Docker image to the production VM.

## 5. Step 4: Choose one or more design concepts that satisfy the inputs of the iteration

### 5.1 Applied Tactics

| Quality Attribute | Tactic                       | Description                                                                                                                                             |
|:------------------|:-----------------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Deployability** | **Containerization**         | Package the application (Docker) and all dependencies to ensure a consistent environment across development, test, and VM production.                   |
| **Availability**  | **Redundancy**               | Implement a basic *rollback* mechanism in the deployment script to revert to the last known stable image if health checks fail.                         |
| **Modifiability** | **Configuration Management** | Externalize all environment-specific settings (database credentials, port numbers) using environment variables (in `docker-compose.yml` or VM profile). |

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
- The architecture provides an automated, repeatable process for pushing updates to the public VM.
- The combination of containerization and CI/CD parallelism meets the < 8 minutes constraint for Deployability.
- Basic Availability is ensured via automated health checks and rollback capability.

## 8. Step 7 – Iteration Closure and Refinement

The problem statement has been solved, respecting the requirements goals established.
