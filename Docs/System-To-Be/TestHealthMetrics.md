# Test Health Metrics (System *To Be*)

## 1. Overview

This document presents the current assessment of the system’s **test health metrics**, focusing on both **quantity** and **quality** of the existing automated tests.  
The goal is to understand the new testing landscape, and compare it to the previous one.

---

## 2. Test Quantity Metrics

The following metrics evaluate the **volume** and **coverage** of the new tests in the project.

| **Metric**                                   | **Description**                          | **Current Value** |
|----------------------------------------------|------------------------------------------|-------------------|
| **Number of Unit Tests**                     | Total number of unit-level tests         | 400               |
| **Number of Integration Tests**              | Tests verifying component interactions   | 15                |
| **Number of End-to-End Tests**               | System-level functional tests            | 32                |
| **Code Coverage (%)**                        | Lines of code covered by automated tests | **46%**           |
| **Build/Test Execution Frequency**           | Frequency of test runs in CI/CD          | In every commit   |

---

## 3. Test Quality Metrics

| **Metric**                        | **Description**                           | **Current Status** | **Observations**                           |
|-----------------------------------|-------------------------------------------|--------------------|--------------------------------------------|
| **Assertion Density**             | Average number of assertions per test     | ~3–5               | Most tests validate several conditions     |
| **Test Readability**              | Ease of understanding test logic          | High               | Naming consistent, little test duplication |
| **Isolation / Determinism**       | Tests run independently and consistently  | Good               | No flaky tests detected                    |
| **Mocking / Stubbing Usage**      | Proper handling of external dependencies  | Low                | Many tests use real data or DB connections |
| **Naming Convention Consistency** | Test names are descriptive and consistent | Most               | Most of the test follow conventions        |
| **CI Feedback Time**              | Time between commit and feedback          | 6 min              | Pipeline takes 6 min to run                |
| **Test Maintenance Effort**       | Effort required to update tests           | Low                | Tests highly extendable                    |

---

## 4. Mutation Testing

PITest (Mutation Testing) was used to assess the **quality (kill power)** of the existing tests, checking if they can detect minor changes (mutations) in the codebase. A high score indicates that tests are effectively and assertively written.

| **Metric**                | **Description**                               | **Current Value** |
|:--------------------------|:----------------------------------------------|:------------------|
| **Mutation Coverage (%)** | Percentage of mutations detected by the tests | **25%**           |
| **Killed Mutants**        | Number of mutations that failed the tests     | 852               |
| **All Mutants**           | Number of mutations that were created         | 3412              |

### Observations on Mutation Testing

* **Low Mutation Score:** The score of **25%** is significantly below the ideal target, indicating that a large part of the tested code has line coverage but **lacks coverage of the underlying logic**.
* **Survived Mutants:** The high count of *Survived Mutants* suggests that many tests do not have sufficient **assertion density** and fail when logical operators are altered.
* **Focus Areas:** PITest identified specific classes and methods where tests are covering only execution, but not the exact value or result (*value coverage*).

---

## 5. Comparison Between Old Test and New Test

[Open New Test](../../src/test/java/bookManagement/model/IsbnTest.java)

#### a. Structure & Organization

| Aspect                  | Old Version                                                | New Version                                                                                                                 |
|-------------------------|------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------|
| **Package**             | `pt.psoft.g1.psoftg1.bookmanagement.model`                 | `bookManagement.model`                                                                                                      |
| **Test Framework**      | JUnit 5                                                    | JUnit 5                                                                                                                     |
| **Imports**             | Only `org.junit.jupiter.api.Test` and `assertions`         | Includes `java.lang.reflect`, `java.util.Set`, `HashSet`                                                                    |
| **Test Naming**         | Verbose but inconsistent (`ensureXyz...`)                  | Clear, consistent, and descriptive (`validIsbn10_constructsSuccessfully`, etc.)                                             |
| **Test Classification** | Not explicitly categorized                                 | Explicitly divided into *Black Box* and *White Box* tests                                                                   |
| **Test Coverage**       | Focuses only on validation exceptions and correct checksum | Expands coverage to include equality, hashCode, toString, reflection, constructor visibility, and detailed validation cases |

#### b. Validation Logic Tested

| Case                                   | Old Test         | New Test                                                |
|----------------------------------------|------------------|---------------------------------------------------------|
| `null` ISBN                            | ✅ Tested         | ✅ Tested with explicit message assertion                |
| Blank ISBN                             | ✅ Tested         | ✅ Tested                                                |
| Oversized string                       | ✅ Tested         | ✅ Tested                                                |
| ISBN-13 checksum                       | ✅ Tested         | ✅ Tested                                                |
| ISBN-10 checksum                       | ✅ Tested         | ✅ Tested                                                |
| Lowercase 'x' in ISBN-10               | ❌ Not tested     | ✅ Explicitly tested                                     |
| Non-digit characters (`-`, space)      | ❌ Not tested     | ✅ Explicitly tested                                     |
| Invalid lengths (≠10, ≠13)             | ❌ Not tested     | ✅ Explicitly tested                                     |
| Special branch: computed 10→0 checksum | ❌ Not tested     | ✅ Tested with `"9780000000040"`                         |
| Constructor visibility                 | ❌ Not tested     | ✅ Reflection-based test ensures `protected` constructor |
| Equality & Hashing                     | ❌ Not tested     | ✅ Comprehensive tests for `equals` and `hashCode`       |
| `toString` behavior                    | ✅ Tested (basic) | ✅ Re-tested with both ISBN-10 and ISBN-13               |
| Exception messages                     | ❌ Not checked    | ✅ Verified for null case                                |

#### c. Overall Improvements

| Category               | Why It’s Better                                                                       |
|------------------------|---------------------------------------------------------------------------------------|
| **Coverage**           | Broader — now tests structural integrity, equality, and reflection aspects            |
| **Maintainability**    | Cleaner naming and test grouping make future updates easier                           |
| **Readability**        | Descriptive and consistent test names clarify purpose                                 |
| **Design Alignment**   | Reflection-based tests ensure domain model constraints (e.g., constructor visibility) |
| **Error Verification** | Validates exception messages, ensuring consistency in thrown errors                   |
| **Performance**        | Removed oversized Lorem Ipsum input, reducing runtime and noise                       |

---

## 6. Comparison Summary

| **Area**                      | **Old System**                          | **New System**                                |
|:------------------------------|:----------------------------------------|-----------------------------------------------|
| **Test Quantity**             | Bad volume.                             | Good volume.                                  |
| **Code Coverage**             | 17%                                     | 46%                                           |
| **Test Quality (Mocking)**    | Mocking/Stubbing usage is **Low**.      | Mocks are used frequently.                    |
| **Test Quality (Assertions)** | **Assertion Density** is medium (~1–2). | **Assertion Density** is medium (~3-5).       |
| **CI Feedback Time**          | No Pipeline.                            | **6 min** is acceptable, but can be improved. | 
| **Mutation Coverage**         | No Mutation Testing.                    | 25%                                           |