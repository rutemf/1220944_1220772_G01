# Technical Memos

### Issue: The system must adopt a polyglot data persistence with SQL and NoSQL

**Problem:** Using a single database technology represent a fault between different microservice since some require strong consistency and relational structures, while others benefit from flexible schemas and high write throughput

**Summary of Solution:** Adoption of distinct technologies, SQL and NoSQL, respectively, MySQL and MongoDB

**Factors:** Data consistency requirements and integration with persistence connectors

**Solution:** Adopt MySQL databases for the services that require strongly-consistent storage and MongoDB to services with schema-less storage and high write/read throughput

**Motivation:** Improves performance, scalability, fault tolerance, supports independent deployment, maintainability, and aligns with microservices architecture principles.

**Alternatives:** Use a single relational database for all services - not recommended

**Pending Issues:** Operational complexity of managing multiple database technologies 


### Issue: The system must automatically execute builds, tests, and deployments

**Problem:** How to automatically execute builds, tests, and deployments

**Summary of Solution:** Create a CI/CD pipeline in Jenkins to test the application.

**Factors:** The pipeline must be executed upon every Git commit to automatically build, test, and containerize the application and the execution time must be under 8 minutes.

**Solution:** Create Pipeline as Code (Jenkinsfile), Maven parallelization tactics, and pre-configured Docker agents in the Jenkins environment.

**Motivation:** Increases the testability and reliability of the application and its security as well as simplifying developer's work.

**Alternatives:** Deploy SW to better hardware and always operate in that hardware, even when is not necessary.

**Pending Issues:** Requires observability.


### Issue: The system must support multiple testing approaches: static, unit, mutation, consumer-driven contract testing).

**Problem:** How to implement automated static, unit, mutation and CDC testing 

**Summary of Solution:** Implementation of automated static, unit, mutation, consumer-driven contract testing executed on the pipeline

**Factors:** Maintainability of test suite and reliability of the code

**Solution:** Usage of static code analysis tool (SpotBugs), mutation testing generating PIP as pipeline runs, unit tests, CDC testing with Pact.

**Motivation:** Improvement of code quality, system's reliability and early defect detection.

**Alternatives:** There are no better alternatives to cover a test suite of a microservices architecture than CDC testing for validating the contract between two services.

**Pending Issues:** N/A

### Issue: The system must improve its availability

**Problem:** Microservice instances or their components may fail due to hardware faults, software defects and other issues, potentially causing service downtime

**Summary of Solution:** Implement redundancy, fault-tolerance, and automatic failover mechanisms so that client requests are always served by healthy instances

**Factors:** Required uptime/SLA targets and deployment architecture

**Solution:** Implement health checks to detect failures quickly, rollback strategies, multiple service instances and monitoring to maintain availability above SLA targets.

**Motivation:** Ensures continuous service delivery, minimizes user downtime, and builds trust in the system.

**Alternatives:** Relying solely on infrastructure-level redundancy without service-level health checks

**Pending Issues:** Increased cost and complexity due to redundancy

### Issue: The system must increase the performance by 25% when in high demand (>Y requests/period)

**Problem:** When the incoming request rate exceeds Y requests per period, performance-critical microservices may experience increased response times and reduced throughput

**Summary of Solution:** Increase system throughput and reduce response times by at least 25% under high load.

**Factors:** Scalability characteristics of microservices, consistency and data access patterns and Baseline throughput

**Solution:** Enable horizontal auto-scaling of performance-critical microservices based on load metrics and optimize database access

**Motivation:** Ensures acceptable user experience, prevents request backlogs, and allows the system to handle traffic spikes efficiently

**Alternatives:** Manual scaling based on expected traffic peaks

**Pending Issues:** Need for accurate performance baselines and load testing

### Issue: The system must use hardware parsimoniously, according to the runtime demanding of the system. Demanding peaks of >Y requests/period occur seldom

**Problem:** Keeping resources permanently provisioned for peak load leads to inefficient hardware usage and unnecessary infrastructure costs, while under-provisioning risks performance degradation during demand spikes

**Summary of Solution:** Implement elastic resource management that automatically scales infrastructure and microservice instances up or down depending on current demand

**Factors:** Scalability limits of microservices, infrastructure and reaction time of scaling mechanisms

**Solution:** Definition of minimum and maximum instance thresholds to avoid over- or under-provisioning

**Motivation:** Elasticity ensures that the system meets performance targets during rare demand peaks while avoiding unnecessary costs and underutilization during normal operation

**Alternatives:** Manual scaling based on predicted traffic 

**Pending Issues:** Monitoring and alerting need to be precise to trigger scaling accurately


### Issue: Tests must demonstrate scalability, multiple instances, and system behavior under load

**Problem:** When the number of concurrent requests rises above normal, stateless microservices may experience resource saturation, latency spikes, or throughput degradation if the system cannot scale efficiently. It is essential to validate that additional instances can handle load without impacting performance.

**Summary of Solution:** Conduct systematic performance and load tests to verify that throughput and response times remain within target limits as instances are added.

**Factors:** Maximum expected request rate and peak load patterns and infrastructure limits

**Solution:** Conduct load and stress tests in a controlled environment to measure throughput and latency as instances are added.

**Motivation:** Validating scalability ensures that the system can handle growth in user demand without service degradation.

**Alternatives:** Horizontal/Vertical scaling

**Pending Issues:** Maintaining consistency in metrics and monitoring across multiple instances


### Issue: The software clients should not be affected by changes in the API, except in extreme cases, and must adhere to the company’s SOA strategy of API-led connectivity

**Problem:** API changes in microservices can potentially break client applications or dependent services if contracts are not properly managed

**Summary of Solution:** Implement API versioning, contract management and CDC testing.

**Factors:** Stability, reusability and dependency chains between microservices and clients

**Solution:** API versioning and CDC testing

**Motivation:** Preserves client functionality, reduces risk of runtime failures, and allows services to evolve safely.

**Alternatives:** Global versioning - not recommended

**Pending Issues:** Overhead


### Issue: The system must adopt the microservices patterns Strangler Fig, Events, Messaging (via Message Broker), CQRS, Database-per-Service, Polyglot Persistence, Outbox and Saga

**Problem:** How to evolve a monolithic system into microservices while ensuring reliable communication, consistent data across distributed services, maintainability, and incremental migration without disrupting existing functionality.

**Summary of Solution:** Apply established microservices patterns to structure the system for reliability and maintainability: use Strangler Fig for incremental migration, Events and Messaging for asynchronous communication, CQRS for separation of read/write concerns, Database-per-Service and Polyglot Persistence for data ownership and technology diversity, and Outbox + Saga patterns to ensure eventual consistency across services.

**Factors:** Need for incremental migration from monolith, consistency requirements across distributed services, choice of databases and technologies per service, communication reliability between services and observability and traceability of operations

**Solution:** Implement Strangler Fig (migrate functionality incrementally from the monolith to microservices, replacing modules gradually.), Events and Messaging (Use a message broker RabbitMQ to decouple services and enable asynchronous 
communication), CQRS (separate read and write models to optimize performance), Database-per-Service, Polyglot Persistence, Outbox (ensure reliable delivery of events in coordination with local database transactions) and
Saga (Coordinate distributed transactions and maintain eventual consistency across multiple services)

**Motivation:** Increases system reliability, maintainability, and scalability, allows incremental migration without downtime, and ensures that distributed services communicate and maintain consistency correctly.

**Alternatives:** Big Bang rewrite, shared databases - not recommended

**Pending Issues:** Complexity and Maintenance cost


### Issue: The independent deployment of each application must be possible.

**Problem:** How to assure the independent deployment of each application

**Summary of Solution:** Enable independent deployment by designing each microservice as a loosely coupled, self-contained unit with its own deployment pipeline, versioned contracts, and automated validation to ensure changes do not break other services.

**Factors:** CI/CD automation and pipeline maturity, API and message contract stability and deployment mechanisms

**Solution:** CDC testing, database per service, asynchronous communication and event-driven integration

**Motivation:** Reduces coordination overhead, minimizes the risk of production incidents, and allows teams to release features and fixes faster. Aligns with continuous delivery and microservices best practices, improving system agility and reliability.

**Alternatives:** Coordinated releases and shared database architecture

**Pending Issues:** Maintenance cost and operational overhead from multiple pipelines


### Issue: Each application must maintain (or improve) releasability

**Problem:** As the system evolves, microservices may become tightly coupled. This reduces release frequency, making it difficult to deliver new features or bug fixes quickly and safely

**Summary of Solution:** Adopt independent deployment pipelines per microservice, enforce loose coupling through well-defined contracts, and automate build, test, and deployment processes to enable fast, safe, and isolated releases

**Factors:** CI/CD pipeline maturity and automation level, deployment strategies and rollback capabilities

**Solution:** Ensure each microservice has its own CI/CD pipeline and versioning, usage of CDC and automated testing, safe deployment strategies, fast rollback in case of failures 

**Motivation:** High releasability reduces lead time, lowers deployment risk, and improve system agility and support DevOps and continuous delivery practices

**Alternatives:** Manual deployment and testing processes

**Pending Issues:** Operational complexity with many independent pipelines


### Issue: The system must automatically roll back any production deployed service to its previous version in case of health check failures.

**Problem:** How to make the system automatically roll back any production deployed service to its previous version in case of health check failures.

**Summary of Solution:** Implementation of automated rollback strategies and health checks

**Factors:** Availability, data consistency, observability and backwards compatibility

**Solution:** Implementation of health checks using XXXX and rollback strategies canary and blue/green.

**Motivation:** Minimizes MTTR(Mean Time To Recovery) and assures there will be no repercussions in the other services

**Alternatives:** Manual rollback or feature toggling

**Pending Issues:** Database Migrations


### Issue: Development, Staging and Production environments should be adopted.

**Problem:** How to adopt Development, Staging and Production environments

**Summary of Solution:** Without separating the three different environments, deploys might behave differently and issues/defects might reach Production environment.

**Factors:** Environment parity, security and compliance and cost management

**Solution:** Definition of the three environments through IaC and deploy using the same CI/CD pipelines and artifacts across all environments

**Motivation:** ReduceS deployment risk, improves observability, and allows early error detection. This increases system reliability, simplifies maintenance, and supports DevOps practices.

**Alternatives:** Single environment

**Pending Issues:** Maintenance cost