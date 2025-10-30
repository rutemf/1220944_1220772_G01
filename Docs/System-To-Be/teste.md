# Quality Attribute Scenarios (QAS)

---

---

## 2. External API Extensibility
- *Element Statement*: The system must allow the integration of new external services without changes to the core.
- *Stimulus*: A new API for books/events is added.
- *Stimulus Source*: Developer.
- *Environment*: Development environment.
- *Artifact*: API Gateway, configuration.
- *Response*: The new API is integrated by adding a new connector.
- *Response Measure*: Integration completed until the deadline of the Project 1.

---

---

## 4. Runtime Configurability
- *Element Statement*: The system must allow runtime reconfiguration (persistence, APIs, ID generator).
- *Stimulus*: Admin switches from Redis+SQL to Redis+MongoDB at runtime.
- *Stimulus Source*: Administrator.
- *Environment*: Production.
- *Artifact*: Dynamic configuration.
- *Response*: The system applies the new configuration without downtime.
- *Response Measure*: Reconfiguration within acceptable time limits.

---

## 5. Testability
- *Element Statement*: The system must support multiple testing approaches (opaque-box, transparent-box, mutation testing).
- *Stimulus*: Developer runs automated test suite.
- *Stimulus Source*: Developer / CI/CD pipeline.
- *Environment*: Testing environment or pipeline execution.
- *Artifact*: Test framework, system under test.
- *Response*: The system executes unit, integration, and mutation tests, producing reliable reports.
- *Response Measure*: Test execution within acceptable time limits.

---

## 6. Environment Deployment
- *Element Statement*: The system must be deployed across multiple environments (local, DEI server, Docker, Cloud).
- *Stimulus*: Jenkins triggers deployment to a new environment.
- *Stimulus Source*: CI/CD pipeline.
- *Environment*: New deployment target (e.g., Cloud).
- *Artifact*: Build artifacts, deployment scripts.
- *Response*: System installs and runs successfully without code changes.
- *Response Measure*: Deployment completed within acceptable time limits.

## 4. Runtime Configurability
- *Element Statement*: The system must allow runtime reconfiguration (persistence, APIs, ID generator).
- *Stimulus*: Admin switches from Redis+SQL to Redis+MongoDB at runtime.
- *Stimulus Source*: Administrator.
- *Environment*: Production.
- *Artifact*: Dynamic configuration.
- *Response*: The system applies the new configuration without downtime.
- *Response Measure*: Reconfiguration within acceptable time limits.

---

## 10. Runtime Change
- *Element Statement*: The system must support live changes in configuration.
- *Stimulus*: Admin switches persistence model at runtime.
- *Stimulus Source*: Administrator.
- *Environment*: Production.
- *Artifact*: Runtime configuration engine.
- *Response*: New configuration is applied immediately.
- *Response Measure*: Change applied within acceptable time limits.

---

## 12. External API Interoperability
- *Element Statement*: The system must interoperate with multiple external APIs of similar type.
- *Stimulus*: A new external API is integrated.
- *Stimulus Source*: Developer.
- *Environment*: Development/production.
- *Artifact*: API Gateway.
- *Response*: The system consumes the new API without redesign.
- *Response Measure*: Integration completed until the deadline of the Project 1.

---

## Quality Attribute Scenario Synthesis

| Quality Attribute                | Scenario                                                                                                    | Importance   | Risk |
|----------------------------------|-------------------------------------------------------------------------------------------------------------|--------------|------|
| *Performance, Reliability*     | CI/CD pipeline executes build, tests, and deployment successfully within acceptable time limits.            | H            | M    |
| *Functionality, Modifiability* | New external API (books/events) integrated without changes to the core system.                              | H            | M    |
| *Functionality, Performance*   | System generates valid IDs in configured formats (e.g., Random base65, Timestamp+Hex).                      | H            | L    |
| *Modifiability, Availability*  | System allows runtime reconfiguration (persistence, APIs, ID generator) without downtime.                   | H            | M    |
| *Testability*                  | Automated test suite (unit, integration, mutation) executes and produces reliable reports.                  | H            | M    |
| *Portability*                  | System deploys successfully across environments (local, DEI server, Docker, Cloud) without code changes.    | H            | M    |
| *Performance*                  | CI/CD pipeline full execution (build, test, deploy) completes within acceptable time limits.                | H            | M    |
| *Performance*                  | ID generation service handles thousands of simultaneous requests with generation time within limits.        | M            | M    |
| *Usability*                    | Admins recognize valid runtime configuration options quickly and clearly.                                   | H            | L    |
| *Usability, Modifiability*     | Admin applies runtime configuration changes (e.g., persistence switch) with zero downtime.                  | H            | M    |
| *Compatibility, Reliability*   | System supports SQL+Redis and MongoDB+Redis interoperability without functional errors.                     | H            | M    |
| *Compatibility, Modifiability* | System integrates new external APIs without architectural redesign within project deadline.                 | H            | M    |