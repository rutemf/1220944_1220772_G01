# Microservice Patterns – Design Rationale

## Introduction

This section describes the **microservice patterns adopted** in the system and provides a clear **justification for each architectural decision**, 
aligned with business requirements, quality attributes, and the **progressive migration strategy** from the legacy monolithic system.

---

## 1. Strangler Fig Pattern

---

## 2. Database-per-Service

- Each microservice owns its database with no direct access from other services.

---

## 3. Polyglot Persistence

- We have chosen different database technologies based on service requirements:
  - **Books**: MySQL
  - **Readers**: MySQL
  - **Genres**: MongoDB
  - **Auth-Users**: MySQL
  - **Authors**: MySQL

---

## 4. Command-Query Responsibility Segregation (CQRS)

---

## 5. Messaging via Message Broker

---

## 6. Domain Events

---

## 7. Outbox Pattern

---

## 8. Saga Pattern

---

## Conclusion

The adoption of these microservice patterns results in a **scalable and decoupled architecture**, aligned with the project’s 
goals of progressive migration and maintainability.

All patterns were selected through explicit trade-off analysis, balancing architectural complexity with long-term benefits.