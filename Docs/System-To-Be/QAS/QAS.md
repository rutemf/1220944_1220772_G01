# Quality Attribute Scenarios (QAS)

## 1. ID Generation

The system must support distinct ID generation schemes in different formats based on varying specifications.

| Element            | Statement                                                        |
|--------------------|------------------------------------------------------------------|
| Stimulus           | A new entity object is created (book, lending, genre, fine, ...) |
| Stimulus source    | Internal system process                                          |
| Environment        | Development                                                      | 
| Artifact           | ID generation service                                            |
| Response           | A ID is generated according to the required format               |
| Response measure   | Generation time within acceptable time limits                    | 

## 2. Data persistence

The system must support persisting data in different data models and Data Base Management System (DBMS): MySQL+Redis and MongoDB+Redis.

| Element            | Statement                                             |
|--------------------|-------------------------------------------------------|
| Stimulus           | Switch between the different data models              |
| Stimulus source    | Administrator                                         |
| Environment        | Production                                            |
| Artifact           | Persistence connectors                                |
| Response           | The system maintains its features and characteristics |
| Response measure   | Switch completed within acceptable time limits        |

## 3. External API Extensibility

The system must support the integration of new external APIs without changing the core.

| Element            | Statement                                                                             |
|--------------------|---------------------------------------------------------------------------------------|
| Stimulus           | Integration of Open Library Search API and Google Books API to retrieve a book's ISBN |
| Stimulus source    | Developer                                                                             |
| Environment        | Development                                                                           |
| Artifact           | API Gateway, configuration                                                            |
| Response           | A book's ISBN                                                                         |
| Response measure   | Functional integration                                                                |

## 4. Runtime Configurability 

The system must support runtime configuration regarding ID generation, persistence and APIs.

| Element            | Statement                                    |
|--------------------|----------------------------------------------|
| Stimulus           | Exchange between data models at runtime      |
| Stimulus source    | Administrator                                |
| Environment        | Production                                   | 
| Artifact           | Dynamic configuration                        |
| Response           | New configuration is applied immediately     |
| Response measure   | Change applied within acceptable time limits | 

## 5. CI/CD Pipeline Execution

The system must automatically execute builds, tests, and deployments

| Element            | Statement                                                              |
|--------------------|------------------------------------------------------------------------|
| Stimulus           | A commit is pushed to the repository                                   |
| Stimulus source    | Developer                                                              |
| Environment        | CI/CD pipeline                                                         | 
| Artifact           | Jenkins pipeline                                                       |
| Response           | The pipeline compiles, runs tests, and successfully deploys the system |
| Response measure   | Execution completed within acceptable time limits.                     |

## 6. Testability

The system must support various testing approaches: functional opaque and transparent boxes, with different SUTs, and mutation

| Element            | Statement                                                                            |
|--------------------|--------------------------------------------------------------------------------------|
| Stimulus           | Execution of automated tests                                                         |
| Stimulus source    | Developer and CI/CD pipeline                                                         |
| Environment        | Testing and pipeline                                                                 | 
| Artifact           | Framework and SUT                                                                    |
| Response           | The system executes unit, integration and mutation tests, producing valuable reports |
| Response measure   | Test execution within acceptable time limits and good coverage                       |

## 7. Deployment Environment

The system must be deployed in two hosting environments: Local and Docker Container.

| Element            | Statement                                                     |
|--------------------|---------------------------------------------------------------|
| Stimulus           | Jenkins triggers deployment to the new environments           |
| Stimulus source    | CI/CD pipeline                                                |
| Environment        | Local and Docker Container                                    | 
| Artifact           | Build artifacts and deployment scripts                        |
| Response           | The system is installed and runs successfully without changes |
| Response measure   | Deployment finish within acceptable time limits               |

## Quality Attribute Scenario Synthesis

| Quality Attribute              | Scenario                                                                                                             | Importance | Risk |
|--------------------------------|----------------------------------------------------------------------------------------------------------------------|------------|------|
| *Functionality, Performance*   | System generates distinct IDs for different entities (books, lending, genre, fines) according to required formats.   | H          | L    |
| *Modifiability, Availability*  | Runtime configuration allows switching between ID generation schemes, persistence models, and APIs without downtime. | H          | M    |
| *Functionality, Modifiability* | New external APIs (Open Library, Google Books) integrated without changing the core system.                          | H          | M    |
| *Performance, Reliability*     | CI/CD pipeline executes builds, tests, and deployment successfully within acceptable time limits.                    | H          | M    |
| *Testability*                  | Automated test suite executes unit, integration, and mutation tests, producing valuable reports.                     | H          | M    |
| *Portability*                  | System deploys successfully in Local and Docker environments without code changes.                                   | H          | M    |
| *Performance*                  | ID generation service handles requests within acceptable time limits.                                                | M          | M    |
| *Usability*                    | Admins recognize and apply valid runtime configuration options quickly and clearly.                                  | H          | L    |
| *Compatibility, Reliability*   | System supports MySQL+Redis and MongoDB+Redis interoperability without functional errors.                            | H          | M    |
| *Compatibility, Modifiability* | System integrates new external APIs without architectural redesign within project deadlines.                         | H          | M    |
| *Performance*                  | CI/CD pipeline full execution (build, test, deploy) completes within acceptable time limits.                         | H          | M    |
| *Modifiability, Reliability*   | Persistence connectors maintain system features and characteristics when switching between data models.              | H          | M    |

