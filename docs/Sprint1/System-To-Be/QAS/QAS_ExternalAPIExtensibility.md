# QAS - Data Persistence

## 1. Introduction
This document aims to explain the adoption of the Attribute-Driven Design (ADD) design method to the Quality Attribute Scenario (QAS) of External API Extensibility.

It is intended retrieve a book's ISBN by title using different external systems: Google Books API and Open Library Search API.

## 2. Step 1: Confirm there is sufficient requirements information

### 2.1 Objective
Ensure requirements and constraints are clearly identified and aligned with project goals.

### 2.2 Problem Statement
The system must support runtime configuration regarding ID generation, persistence and APIs.

### 2.3 Architecturally Significant Requirements (ASR)

Retrieve a book’s ISBN by title using different external systems: Google Books API and Open Library Search API

### 2.4 Requirements (SMART)

| Requirement    | Description                                                                                         |
|----------------|-----------------------------------------------------------------------------------------------------|
| **Specific**   | The system must retrieve a book’s ISBN by title using Google Books API and Open Library Search API. |
| **Measurable** | N/A                                                                                                 |
| **Attainable** | Possible through modular services.                                                                  |
| **Relevant**   | Ensures flexibility, fault tolerance, and extensibility when adding or replacing external APIs.     |
| **Time-bound** | N/A                                                                                                 |


### 2.5 Variation Points
- External API providers: Google Books API, Open Library API, and potential future APIs.

### 2.6 Evolution Points
- Support for new external APIs (e.g., ISBNdb).

## 3. Step 2: Establish goals and select inputs to be considered in the iteration

### 3.1 Iteration Goal
- Both APIs are integrated
- Supports switching between the API to get isbns

### 3.2 Drivers

| Type                  | Description                                                      |
|-----------------------|------------------------------------------------------------------|
| **Functional**        | Retrieve ISBNs by book title using   external APIs               |
| **Quality Attribute** | Retrieve ISBNs by book title using multiple external APIs        |
| **Constraint**        | Must use external APIs and maintain consistent return structures |
| **Business**          | Increase integration options, and support future APIs            |

## 4. Step 3: Choose Element(s) of the System to Decompose

### 4.1 Selected Element

Implementation of IsbnService, GoogleBooksService and OpenLibraryService.

## 5. Step 4: Choose one or more design concepts that satisfy the inputs of the iteration

### 5.1 Applied Tactics

| Quality Attribute | Tactic                         | Description                                                                                   |
|-------------------|--------------------------------|-----------------------------------------------------------------------------------------------|
| **Extensibility** | **Plug-in Architecture**       | Use a shared interface  allowing new API adapters to be added without core changes.           |
| **Modifiability** | **Encapsulation**              | Isolate each API’s logic within its own adapter class to prevent cascading changes.           |
| **Availability**  | **Redundancy / Failover**      | Switch to other API in case one fails.                                                        |
| **Reliability**   | **Error Handling & Fallback**  | Gracefully handle API errors and provide fallback responses to maintain system stability.     |
| **Scalability**   | **Stateless Design**           | Keep API adapter classes stateless to allow easy scaling and parallel API calls.              |

### 5.2 Reference Architectures and Patterns

- Layered Architecture
- Service Layer
- Adapter Pattern

## 6. Step 5 – Instantiate architectural elements, allocate responsibilities, and define interfaces

### 6.1 Main components

| Component                | Responsibility                                            |
|--------------------------|-----------------------------------------------------------|
| **IsbnService**          | Defines the adapter contract                              |
| **GoogleBooksService**   | Fetch book's isbn by title though Google Books API        |
| **OpenLibraryService**   | Fetch book's isbn by title though Open Library Search API | 

## 7. Step 6 – Evaluate and Refine the Architecture

### 7.1 Outcome
- Architecture is ready for future integration with other external APIs.

## 8. Step 7 – Iteration Closure and Refinement

The problem statement has been solved, respecting the requirements goals established.



