# Translator System

Translator System is a web-based application designed for managing documents and translators. The system features a backend built with Java and Spring Boot, uses PostgreSQL as its database, and a frontend developed with Vite and Vue.js.

---

## Table of Contents
- [Overview](#overview)
- [Architecture](#architecture)
- [Requirements](#requirements)
- [Setup and Configuration](#setup-and-configuration)
  - [Backend](#backend)
  - [Database](#database)
  - [Frontend](#frontend)
- [API Endpoints](#api-endpoints)
  - [Documents](#documents)
  - [Translators](#translators)
- [Running the Project](#running-the-project)

---

## Overview

The Translator System provides the following functionalities:
- **Document Management:** Create, read, update, and delete documents; supports CSV uploads for bulk document processing.
- **Translator Management:** Create, read, update, and delete translator records.

The backend exposes a RESTful API, while the frontend offers an interactive user interface for managing both documents and translators.

---

## Architecture

- **Backend:** Java with Spring Boot.
- **Database:** PostgreSQL.
- **Frontend:** Vite with Vue.js.

---

## Requirements

- **Java:** Version 11 or higher.
- **Build Tool:** Maven or Gradle.
- **Node.js:** Version 14 or higher.
- **Package Manager:** NPM or Yarn.
- **Database:** PostgreSQL.

---

## Setup and Configuration

### Backend

1. **Clone the Repository:**
   ```bash
   git clone https://your-repository.git
   cd backend-directory
2. **Configure the Database Connection**: Edit the file src/main/resources/application.properties (or application.yml) to configure your PostgreSQL connection:
 ```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
 ```
3. Run the Backend:

 - With Maven:
 ```bash
mvn spring-boot:run
 ```
- Or, use your preferred IDE (e.g., Eclipse, IntelliJ) to run the Spring Boot application.
### Database

1. Install and Configure PostgreSQL:

 - Ensure PostgreSQL is installed and running.
- Create a new database for the system:
```bash
CREATE DATABASE your_database_name;
```
2. Verify Connection Settings: Confirm that the connection details in **application.properties** match your PostgreSQL configuration.

### Frontend
1. Clone the Repository (if the frontend is separate):

```bash
git clonehttps://github.com/VPente/TranslatorSystem.git
cd TranslatorSystem
```
2. Install Dependencies:

```bash
npm install
```
3. Run the Development Server:

```bash
npm run dev
```
The frontend will typically be available at http://localhost:5173.

### API Endpoints
####Documents
 - GET /api/documents:
 
Retrieves a list of all documents.

- GET /api/documents/{id}:

Retrieves a document by its ID.

Example: /api/documents/1

- POST /api/documents:

Creates a new document.

**Request Body (JSON):**

```bash
{
  "subject": "Document Subject",
  "content": "Document Content",
  "locale": "Locale (optional)",
  "author": "email@example.com"
}
```
- PUT /api/documents/{id}:

Updates an existing document.

- DELETE /api/documents/{id}:

Deletes a document by its ID.

- POST /api/documents/upload:

Uploads a CSV file containing document data.

CSV Requirements:

The CSV file must include headers: subject;content;locale;author.

#### Translators
- GET /api/translators:

Retrieves a list of all translators.

- GET /api/translators/{id}:

Retrieves a translator by its ID.

- POST /api/translators:

Creates a new translator.

Request Body (JSON):

Use the structure defined by the Translator entity.

- PUT /api/translators/{id}:

Updates an existing translator.

- DELETE /api/translators/{id}:

Deletes a translator by its ID.

## Running the Project
Start the Database:

Ensure PostgreSQL is running and the database is created.

Start the Backend:

Configure the database connection in application.properties.

Run the backend with:
```bash
mvn spring-boot:run
```
The backend runs on port 8080 by default.

Start the Frontend:

Navigate to the frontend directory.

Install dependencies and start the development server:

```bash
npm install
npm run dev
```
Access the frontend at http://localhost:5173.
