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

## 7. Environment Deployment

The system must be deployed in two hosting environments: Local and Docker Container.

| Element            | Statement                                                     |
|--------------------|---------------------------------------------------------------|
| Stimulus           | Jenkins triggers deployment to the new environments           |
| Stimulus source    | CI/CD pipeline                                                |
| Environment        | Local and Docker Container                                    | 
| Artifact           | Build artifacts and deployment scripts                        |
| Response           | The system is installed and runs successfully without changes |
| Response measure   | Deployment finish within acceptable time limits               |

