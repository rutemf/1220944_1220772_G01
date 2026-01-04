# Microservice Patterns – Design Rationale

## Introduction

This section describes the **microservice patterns adopted** in the system and provides a clear **justification for each architectural decision**, 
aligned with business requirements, quality attributes, and the **progressive migration strategy** from the legacy monolithic system.

---

## 1. Strangler Fig Pattern

This pattern is used to gradually replace or refactor a legacy system without doing a full rewrite all at once.
To adopt this pattern, the team gradually implemented the new microservices applications by replacing each matter (books, authors, genres, readers and users)
at a time with their own microservice.
This way, it was possible to implement the new system, while the old one still ran, once the new system was working as expected, the legacy system was safely decommissioned.
This allowed no downtime while gradually migrating from a monolith system to a microservices one.


---

## 2. Database-per-Service

In this pattern, ech microservice owns and manages its database with no direct access from other services.
This particularity adds value to the system because it solves problems like tight coupling and difficult independent deployments, and
also allows the database technology to differ per service.
Therefore, the system has a database for each service, Users, Authors, Genres, Books and Readers.

---

## 3. Polyglot Persistence

This data management pattern allows an application to use different types of database technologies, considering the different data needs.
The team made the following choices:
  - **Books**: MySQL
  - **Readers**: MySQL
  - **Genres**: MongoDB
  - **Auth-Users**: MySQL
  - **Authors**: MySQL

---

## 4. Command-Query Responsibility Segregation (CQRS)

This patterns separates write operations (commands) from read operations (queries).
The commands change the system state (CREATE, UPDATE, DELETE) and the queries return data and don't modify the state, having in mind that
reads and writes are handled by separate models

---

## 5. Messaging via Message Broker

In this asynchronous communication pattern, the services communicate by sending messages through an intermediary, in this case RabbitMQ, (the broker) instead of calling each other directly.
The producers send messages to the broker and the consumers receive them from the broker.
RabbitMQ is the technology that implements Messaging via Message Broker

---

## 6. Domain Events

This pattern is used to capture and react to main business events in the system. Its intention is to capture
and communicate the significant state changes in the domain as discrete events so that other parts of the system can react without tight coupling.

---

## 7. Outbox Pattern

This consistency pattern ensures atomicity between database changes and messages/events sent to other services.
The Outbox Pattern stores the events in a table in the same database as the local transaction and then publishes it reliably in case some trouble happens.

---

## 8. Saga Pattern

This pattern is used to manage long-running business transactions that span multiple services while maintaining data consistency without using distributed ACID transactions.
Specially in a microservices architecture, one business process may involve multiple services, each with its own database, so Saga Pattern splits the transaction into a series of local transactions, coordinated via events or orchestration.

The type of Saga used is Choreography-based because it is decentralized. The services communicate by publishing and listening to events.

---

## Conclusion

The adoption of these microservice patterns results in a **scalable and decoupled architecture**, aligned with the project’s 
goals of progressive migration and maintainability.

All patterns were selected through explicit trade-off analysis, balancing architectural complexity with long-term benefits.