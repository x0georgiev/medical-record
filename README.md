# Medical Record System

A Spring Boot application for managing medical records, including doctors, patients, diagnoses, examinations, medicines, and sick leaves.

## Technologies

- **Java 21**
- **Spring Boot 4.0.2**
- **Spring Security** (Database Authentication)
- **Spring Data JPA** (Hibernate)
- **MySQL 8**
- **Thymeleaf** (Frontend)
- **Bootstrap 5**
- **Docker & Docker Compose**

## Getting Started

### Prerequisites

- Java 21 (for local development)
- Docker Desktop (for containerized deployment)

### Default Credentials

The application comes pre-loaded with the following users (Password for all is `password123`):

| Role | Username | Password |
|------|----------|----------|
| **Admin** | `admin` | `password123` |
| **Doctor** | `doctor1` | `password123` |
| **Patient** | `patient1` | `password123` |

### Running with Docker (Recommended)

You can run the entire application (App + Database) with a single command.

1.  Make sure Docker Desktop is running.
2.  Run the following command in the project root:

    ```bash
    docker-compose up --build
    ```

3.  Wait for the containers to start. The application will be available at [http://localhost:8080](http://localhost:8080).
    - Note: The database will be exposed on port `3307` locally to avoid conflicts with any local MySQL installation.

4.  To stop the application:
    ```bash
    docker-compose down
    ```

### Running Locally

If you prefer to run it without Docker:

1.  Ensure you have a MySQL database running on `localhost:3306`.
2.  Update `src/main/resources/application.properties` if your DB credentials differ from `root/password`.
3.  Run the application:

    ```bash
    ./gradlew bootRun
    ```

4.  Access the app at [http://localhost:8080](http://localhost:8080).

## Features

- **Authentication**: Secure login with role-based access control (Admin, Doctor, Patient).
- **Dashboard**: Quick overview of all sections.
- **Doctors**: Manage GP and Specialist records.
- **Patients**: Manage patient data and assign GPs.
- **Examinations**: Record patient visits, diagnoses, and treatments.
- **Medicines**: Manage the catalog of available medicines.
- **Sick Leaves**: Issue and track sick leaves linked to examinations.
- **Diagnoses**: ICD-10 compatible diagnosis management.

## Project Structure

- `src/main/java`: Backend source code (Controllers, Services, Repositories, Entities).
- `src/main/resources/templates`: Thymeleaf HTML views.
- `src/main/resources/static`: Static assets (CSS/JS).
- `src/test`: Unit and Integration tests.
- `Dockerfile` & `docker-compose.yml`: Container configuration.
