## Technical Memos

### Index
1. [Distinct ID Generation Schemes](#issue-the-system-must-support-distinct-id-generation-schemes-in-different-formats-based-on-varying-specifications)
2. [Data Persistence in Different DBMS](#issue-the-system-must-support-persisting-data-in-different-data-models-and-data-base-management-system-dbms-mysqlredis-and-mongodbredis)
3. [External API Integration Extensibility](#issue-the-system-must-support-the-integration-of-new-external-apis-without-changing-the-core)
4. [Runtime Configuration of Persistence and APIs](#issue-the-system-must-support-runtime-configuration-regarding-persistence-and-apis)
5. [Automated Builds, Tests, and Deployments (CI/CD)](#issue-the-system-must-automatically-execute-builds-tests-and-deployments)
6. [Deployment to Local and Docker Environments](#issue-the-system-must-be-deployed-in-two-hosting-environments-local-and-docker-container)

---

### Issue: The system must support distinct ID generation schemes in different formats based on varying specifications

**Problem:** How to address distinct ID generation schemes in different formats based on varying specifications

**Summary of Solution:** Implement an ID generation mechanism to handle the logic of different formats.

**Factors:** Must be in the two formats: random Base65 and timestamp followed by 6 hexadecimal digits, separated by hyphen

**Solution:** Implementation of a service to generate the different types of IDs.

**Motivation:** The solution considers the flexibility, extensibility and configurability the system aims to have.

**Alternatives:** Usage of Strategy Pattern

**Pending Issues:** N/A

### Issue: The system must support persisting data in different data models and Data Base Management System (DBMS): MySQL+Redis and MongoDB+Redis

**Problem:** How to persist data in different data models and Data Base Management System (DBMS): MySQL+Redis and MongoDB+Redis.

**Summary of Solution:** Data must be persisted with MySQL and MongoDB, both with Redis caching.

**Factors:** Maintain consistency and behavior across both persistence types and enable runtime switching.

**Solution:** Adopt a layered architecture with repository abstraction and service encapsulation with distinct entities mapped to their respective data stores.

**Motivation:** Enables maintainability, flexibility, scalability, fault tolerance and future extensibility.

**Alternatives:** Hava a data access microservice per persistence type.

**Pending Issues:** N/A

### Issue: The system must support the integration of new external APIs without changing the core

**Problem:** How to support the integration of new external APIs without changing the core.

**Summary of Solution:** Integrate external API to retrieve a book's ISBN by title.

**Factors:** Through Google Books API and Open Library Search API.

**Solution:** Use Adapter Pattern: Implement an interface and two services, each one for each API.

**Motivation:** Fault tolerance, extensibility, reliability and modifiability.

**Alternatives:** Use an API plugin system

**Pending Issues:** N/A

### Issue: The system must support runtime configuration regarding persistence and APIs

**Problem:** How to support runtime configuration regarding persistence and APIs

**Summary of Solution:** Allow configuration of persistence type (SQL/NoSQL) and external API provider (Google Books / Open Library Search) through the `application.properties` file, enabling flexible runtime behavior without code modification.

**Factors:** - Multiple persistence options (MySQL + Redis, MongoDB + Redis) and multiple API providers (Google Books API, Open Library Search API).

**Solution:** Use runtime configuration through the Spring `application.properties` file

**Motivation:** Use an external configuration service or environment variables and enhance system resilience by switching between providers at setup time.

**Alternatives:** Hardcode configuration in the source code or use an external configuration service or environment variables

**Pending Issues:** N/A

### Issue: The system must automatically execute builds, tests, and deployments

**Problem:** How to automatically execute builds, tests, and deployments

**Summary of Solution:** Create a CI/CD pipeline in Jenkins to test the application.

**Factors:** The pipeline must be executed upon every Git commit to automatically build, test, and containerize the application and the execution time must be under 8 minutes.

**Solution:** Create Pipeline as Code (Jenkinsfile), Maven parallelization tactics, and pre-configured Docker agents in the Jenkins environment.

**Motivation:** Increases the testability and reliability of the application and its security as well as simplifying developer's work.

**Alternatives:** Deploy SW to better hardware and always operate in that hardware, even when is not necessary.

**Pending Issues:** Requires observability.

### Issue: The system must be deployed in two hosting environments: Local and Docker Container

**Problem:** How to deploy in two hosting environments: Local and Docker Container

**Summary of Solution:** Pack the system (container using Docker) and deployed to a Virtual Private Server (VPS) accessible via the public internet, exposing HTTP/HTTPS ports.

**Factors:** The total deployment time (from build completion to public accessibility) must be less than 8 minutes.

**Solution:** Implement a CI/CD pipeline that uses SSH/SCP to transfer the artifact (Docker image) and utilizes a container runtime (Docker/Docker Compose) on the VM.

**Motivation:** It is essential for the public availability of the system, allowing end-users to access the production services.

**Alternatives:** Usage of a Virtual Machine (VM) versus Platform as a Service (PaaS) and exposing API Publicly versus exposing API for certain IPs.

**Pending Issues:** N/A