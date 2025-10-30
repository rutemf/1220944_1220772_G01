# QAS - Id Generation

## 1. Introduction

This document aims to detail the adoption of the Attribute-Driven Design (ADD) design method to the Quality Attribute Scenario (QAS) of ID Generation for the System To-Be architecture.

It is required to implement a mechanism to generate Ids in different formats based on the following formats: random Base 65 and Timestamp with the suffix of 6 hexadecimal digits, separated by a hyphen.

## 2. Step 1: Confirm there is sufficient requirements information


### 2.1 Objective
Ensure requirements and constraints are clearly identified and aligned with project goals.

### 2.2 Problem Statement

The system must generate IDs for other entities in different formats based on varying specifications.

### 2.3 Requirements (SMART)

| Requirement    | Description                                                                                                                 |
|----------------|-----------------------------------------------------------------------------------------------------------------------------|
| **Specific**   | Ids must be generated in different formats: random Base65 and timestamp suffixed by hexadecimal digits, separated by hyphen |
| **Measurable** | Id generation time in acceptable time limits                                                                                |
| **Attainable** | Possible through modular Id generation mechanisms                                                                           |
| **Relevant**   | Supports future extensibility for new entities and formats                                                                  |
| **Time-bound** | Must be operational by deployment to multi-environment CI/CD pipeline                                                       |

### 2.4 Variation Points
- Id formats: Random Base65 and Timestamp - 6 Hexadecimal digits
- Invocation: Local cached generation

### 2.5 Evolution Points
- Plug-in model for integrating new generation algorithms

## 3. Step 2: Establish goals and select inputs for iteration




