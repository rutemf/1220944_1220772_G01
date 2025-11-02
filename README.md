# 📚 Library Management Application

A **Spring Boot application** designed to manage **library resources**, including books, authors, users, lendings, and fines.  
This project was developed as part of the **ARQSOFT** and **ODSOFT** courses to demonstrate software architecture, clean design, and maintainable development practices.

---

## 🚀 Overview

The Library Management Application provides a RESTful API for managing:

- 📖 Books and their metadata (title, author, genre, availability)
- 👤 Users (readers and librarians)
- 🔄 Lendings and returns
- 💰 Fines for overdue items

It is built following **Domain-Driven Design (DDD)** and supports **multiple persistence layers** (SQL, MongoDB, Redis).

---

## 🏗️ Architecture

The application follows a **layered architecture**, separating domain logic from infrastructure concerns.

**Profiles** are used to enable specific configurations:
- `sql` – MySQL persistence
- `nosql` – MongoDB persistence
- `open` – OpenLibrary API
- `google` – GoogleBooks API
- `bootstrap` – Loads sample data

---

## 🧰 Tech Stack

| Layer                  | Technology                    |
|------------------------|-------------------------------|
| **Language**           | Java                          |
| **Build Tool**         | Maven                         |
| **Framework**          | Spring Boot                   |
| **Databases**          | MySQL, MongoDB                |
| **Cache**              | Redis                         |
| **Testing**            | JUnit 5, Mockito, Spring Test |
| **Architecture Tools** | SonarGraph, SpotBugs          |

---

## ⚙️ Installation & Setup

### 1️⃣ Clone the repository
```bash
git clone https://github.com/yourusername/library-management.git
cd library-management
```

### 2️⃣ Build the project
```bash
mvn clean package
```

### 3️⃣ Run the application

You can run the application with specific profiles depending on the persistence and API configuration you want to use by changing the application.properties file.
```bash
mvn spring-boot:run
```

## 🧠 Design Highlights

- ✅ **Domain-Driven Design (DDD)** for rich domain modeling
- 🧩 **Repository pattern** for persistence abstraction
- ⚡ **Caching with Redis** for improved performance
- 🌱 **Spring profiles** for modular and flexible configuration
- 🧼 **Clean architecture principles** ensuring low coupling and high cohesion

---

## 🧩 Example API Endpoints

| Resource | Method | Endpoint        | Description               |
|----------|--------|-----------------|---------------------------|
| Books    | `GET`  | `/api/books`    | Retrieve all books        |
| Books    | `POST` | `/api/books`    | Create a new book         |
| Readers  | `GET`  | `/api/readers`  | List all readers          |
| Lendings | `POST` | `/api/lendings` | Register a new lending    |
| Fines    | `GET`  | `/api/fines`    | Retrieve all active fines |

---

## 👥 Contributors

Developed by students of the **ARQSOFT** and **ODSOFT** courses  
at the **ISEP - Instituto Superior de Engenharia do Porto**.

- Miguel Cardoso
- Rute Ferreira
