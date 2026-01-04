# Quality Attribute Scenarios (QAS)

## 1. Polyglot Data Persistence

The system must adopt a polyglot data persistence with SQL and NoSQL, respectively, MySQL and MongoDB.

| Element            | Statement                                                                                        |
|--------------------|--------------------------------------------------------------------------------------------------|
| Stimulus           | Persist and access data with different requirements of consistency, structure and performance    |
| Stimulus source    | Developer                                                                                        |
| Environment        | Development, Stage and Production                                                                | 
| Artifact           | Persistence layer of the microservices: persistence connectors                                   |
| Response           | Each microservice has an independent database and the application supports different data models |
| Response measure   | Better performance, releasability, availability and fault tolerance                              | 


## 2. CI/CD Pipeline Execution

The system must automatically execute builds, tests, and deployments.

| Element            | Statement                                                              |
|--------------------|------------------------------------------------------------------------|
| Stimulus           | A commit is pushed to the repository                                   |
| Stimulus source    | Developer                                                              |
| Environment        | CI/CD pipeline                                                         | 
| Artifact           | Jenkins pipeline                                                       |
| Response           | The pipeline compiles, runs tests, and successfully deploys the system |
| Response measure   | Execution completed within acceptable time limits.                     |

## 3. Testability

The system must support multiple testing approaches: static, unit, mutation, consumer-driven contract testing).

| Element            | Statement                                                                            |
|--------------------|--------------------------------------------------------------------------------------|
| Stimulus           | Developer runs automated test suite                                                  |
| Stimulus source    | Developer / CI/CD pipeline                                                           |
| Environment        | Testing environment or pipeline execution                                            | 
| Artifact           | Test framework, system under test                                                    |
| Response           | The system executes static, unit, mutation and CDC tests, producing reliable reports |
| Response measure   | Tests reflect consistency and reliability within acceptable time limits              | 

## 4. Availability

The system must improve its availability.

| Element            | Statement                                                                                         |
|--------------------|---------------------------------------------------------------------------------------------------|
| Stimulus           | Failure of a microservice instance or its underlying infrastructure component                     |
| Stimulus source    | Hardware fault, software defect, or infrastructure issue                                          |
| Environment        | Production                                                                                        | 
| Artifact           | A single microservice instance and its runtime environment                                        |
| Response           | The system continues to serve client requests by routing traffic to healthy instances or services |
| Response measure   | No perceived downtime for users; service availability maintained above the defined SLA            | 

## 5. Performance

The system must increase the performance by 25% when in high demand (>Y requests/period).

| Element            | Statement                                                                                                  |
|--------------------|------------------------------------------------------------------------------------------------------------|
| Stimulus           | Incoming request rate exceeds Y requests per period                                                        |
| Stimulus source    | Concurrent clients issuing requests                                                                        |
| Environment        | Production                                                                                                 | 
| Artifact           | Performance-critical microservices and their execution infrastructure                                      |
| Response           | The system scales and processes requests with improved throughput and reduced response time                |
| Response measure   | At least 25% increase in throughput or equivalent reduction in average response time compared to baseline  | 

## 6. Elasticity

The system must use hardware parsimoniously, according to the runtime demanding of the system. Demanding peaks of >Y requests/period occur seldom.

| Element            | Statement                                                                                                         |
|--------------------|-------------------------------------------------------------------------------------------------------------------|
| Stimulus           | Variation in workload between normal operation and occasional high-demand peaks (>Y requests/period)              |
| Stimulus source    | System load generated by clients                                                                                  |
| Environment        | Production                                                                                                        | 
| Artifact           | Microservices and underlying execution infrastructure                                                             |
| Response           | The system dynamically adjusts allocated resources based on current demand                                        |
| Response measure   | Low average CPU and memory utilization outside peak periods while still meeting performance targets during peaks  | 


## 7. Scalability

Tests must demonstrate scalability, multiple instances, and system behavior under load.

| Element            | Statement                                                                                           |
|--------------------|-----------------------------------------------------------------------------------------------------|
| Stimulus           | Increase in workload or number of concurrent requests                                               |
| Stimulus source    | Clients creating many requests than usual                                                           |
| Environment        | Production or performance testing environment                                                       | 
| Artifact           | Stateless microservices and supporting infrastructure                                               |
| Response           | The system scales by deploying additional instances and maintains acceptable performance under load |
| Response measure   | Throughput and response time remain within target limits while adding multiple instances            | 

## 8. Interoperability / Architectural Compliance

The software clients should not be affected by changes in the API, except in extreme cases, and must adhere to the company’s SOA strategy of API-led connectivity.

| Element            | Statement                                                                                                                                                                                                              |
|--------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Stimulus           | A change or update is made to a microservice API                                                                                                                                                                       |
| Stimulus source    | Developer                                                                                                                                                                                                              |
| Environment        | Development, Staging, or Production                                                                                                                                                                                    | 
| Artifact           | Microservices and their exposed APIs                                                                                                                                                                                   |
| Response           | The microservice is updated or modified without breaking client functionality, interoperability with other services is preserved, and all communication complies with the company’s SOA/API-led connectivity strategy  |
| Response measure   | No runtime errors in clients or dependent services                                                                                                                                                                     | 

## 9. Software Patterns / Reliability

The system must adopt the microservices patterns Strangler Fig, Events, Messaging (via Message Broker), CQRS, Database-per-Service, Polyglot Persistence, Outbox and Saga.

| Element            | Statement                                                                                                                                                                         |
|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Stimulus           | Need to evolve the monolithic system, coordinate distributed services, and maintain consistency across microservices                                                              |
| Stimulus source    | Developer                                                                                                                                                                         |
| Environment        | Development                                                                                                                                                                       | 
| Artifact           | Microservices, databases, messaging infrastructure, and architectural patterns                                                                                                    |
| Response           | The system applies appropriate patterns to ensure maintainability, reliability, and evolution without breaking other services                                                     |
| Response measure   | Patterns are correctly applied; services communicate reliably; database and messaging operations maintain consistency; migration occurs incrementally without service disruption  | 

## 10. Independent Deployability

The independent deployment of each application must be possible.

| Element            | Statement                                                                                                     |
|--------------------|---------------------------------------------------------------------------------------------------------------|
| Stimulus           | A new version of a microservice is ready to be deployed                                                       |
| Stimulus source    | Developer                                                                                                     |
| Environment        | Stage or Production                                                                                           | 
| Artifact           | Individual microservice and its deployment pipeline                                                           |
| Response           | The microservice is deployed independently without affecting other services or requiring system-wide downtime |
| Response measure   | Successful deployment of the service while all other services remain operational: no cross-service failures   | 

## 11. Releasability

Each application must maintain (or improve) releasability.

| Element            | Statement                                                                                        |
|--------------------|--------------------------------------------------------------------------------------------------|
| Stimulus           | A new feature or bug fix is ready to be released                                                 |
| Stimulus source    | Developer                                                                                        |
| Environment        | Development, Staging, or Production                                                              | 
| Artifact           | Individual microservice and its deployment pipeline                                              |
| Response           | The service can be released independently, quickly, and safely without disrupting other services |
| Response measure   | Lead time for release is minimized and all dependent services continue to function               |

## 12. Fault Tolerance

The system must automatically roll back any production deployed service to its previous version in case of health check failures.

| Element            | Statement                                                                        |
|--------------------|----------------------------------------------------------------------------------|
| Stimulus           | A service health check fails during operation                                    |
| Stimulus source    | Infrastructure issue, defect, ...                                                |
| Environment        | Production                                                                       | 
| Artifact           | Microservices, message broker, and associated message/event handling mechanisms  |
| Response           | The system automatically rolls back the failed operation to maintain consistency |
| Response measure   | No lost data neither impacts on other services                                   |

## 13. Operability

Development, Staging and Production environments should be adopted.

| Element            | Statement                                                                               |
|--------------------|-----------------------------------------------------------------------------------------|
| Stimulus           | Need to deploy, monitor, or maintain the system across different environments           |
| Stimulus source    | Operations/DevOps                                                                       |
| Environment        | Development, Staging, and Production                                                    | 
| Artifact           | Microservices, deployment pipelines and monitoring infrastructure                       |
| Response           | The system can be deployed, monitored, and managed consistently across all environments |
| Response measure   | Issues are detected early in staging, deployments succeed without errors                |