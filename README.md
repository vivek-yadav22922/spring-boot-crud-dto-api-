# Spring Boot CRUD DTO API

A Spring Boot REST API for managing student data using DTOs, validation, layered architecture, MySQL, and global exception handling.

## Features

- Create student
- Get student details
- Update student
- Delete student
- DTO-based request and response handling
- Input validation
- Duplicate resource handling
- Resource-not-found handling
- Global exception handling
- MySQL database integration
- RESTful API architecture

## Tech Stack

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Bean Validation

## Project Structure

```text
src/main/java/Core/demo/crudDtoDemo
│
├── controller
│   └── StudentController
│
├── dto
│   ├── CreateStudentRequestDTO
│   ├── CreateStudentResponseDTO
│   ├── UpdateStudentRequestDto
│   ├── UpdateStudentResponseDto
│   ├── ExceptionResponseDto
│   └── ValidationExceptionResponseDto
│
├── entity
│   ├── Student
│   └── StudentService
│
├── exception
│   ├── DuplicateResourceException
│   ├── ResourceNotFoundException
│   └── GlobalExceptionHandler
│
├── repository
│   └── StudentRepository
│
└── service
