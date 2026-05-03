# Fitness Monolith API 🚀

A Spring Boot backend application for fitness tracking with Docker support for easy deployment.

---

## 🛠 Tech Stack
- Java 21
- Spring Boot
- Spring Data JPA
- MySQL
- Docker
- Maven

---

## 📦 Features
- User management
- Activity tracking
- RESTful APIs
- Database integration with MySQL
- Containerized using Docker

---

## 🚀 How to Run Locally

### 1. Build the project

mvn clean package 

### 2. Build Docker image
docker build -t fitness-monolith .

3. Run container
docker run -p 8080:8080 fitness-monolith

Access Application
Once running, open:

http://localhost:8080

Docker Info

This project is containerized using Docker:

Packages the Spring Boot app into a portable image
Ensures consistent runtime environment
Eliminates “works on my machine” issues
