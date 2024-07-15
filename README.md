# BookSocialNetwork-WebApplication

## Overview

**Book Social Network** is a full-stack application designed for book enthusiasts to manage their personal book collections and connect with other book lovers. The application supports a range of features including user registration, secure email validation, comprehensive book management, and book borrowing with functionality for returns and approvals.

## Features

- **User Registration**: Create and manage user accounts with secure email validation.
- **Book Management**: Add, update, share, and archive books in your collection.
- **Book Borrowing**: Check book availability and borrow books from other users.
- **Book Returns**: Return borrowed books and manage return approvals.
- **Security**: JWT token-based authentication for secure user sessions.
- **REST API**: Follows best practices in REST API design for robust and scalable interactions.

## Technologies Used

### Backend (book-network)

- **Spring Boot 3**: Framework for building the backend services.
- **Spring Security 6**: Security framework for authentication and authorization.
- **JWT Token Authentication**: Secure token-based authentication for user sessions.
- **Spring Data JPA**: ORM for database interactions.
- **JSR-303 and Spring Validation**: Bean validation for input data.
- **OpenAPI and Swagger UI**: API documentation and testing.
- **Docker**: Containerization for consistent development and deployment environments.
- **GitHub Actions**: CI/CD for automated testing and deployment.
- **Keycloak**: Identity and access management solution for secure authentication.

### Frontend (book-network-ui)

- **Angular**: Framework for building the user interface.
- **Component-Based Architecture**: Modular design for scalable and maintainable code.
- **Lazy Loading**: Efficient loading of application modules.
- **Authentication Guard**: Protection of routes based on user authentication status.
- **OpenAPI Generator for Angular**: Auto-generated API clients for seamless integration.
- **Bootstrap**: CSS framework for responsive and visually appealing design.

## Getting Started

### Prerequisites

- **Backend**:
  - Java 17 or higher
  - Docker (for containerization)
  - Maven (for dependency management)

- **Frontend**:
  - Node.js (for package management)
  - Angular CLI (for Angular project management)

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/BookSocialNetwork-WebApplication.git
   cd BookSocialNetwork-WebApplication
