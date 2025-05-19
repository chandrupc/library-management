# Library Management System

A Spring Boot application for managing a library's book inventory and borrowing system.

## Overview

This Library Management System allows librarians to:
- Add and view books in the library inventory
- Register borrowers
- Track book borrowing and returns
- Manage the complete lending lifecycle

## Architecture

The application follows a standard Spring Boot architecture with:
- Controllers for handling HTTP requests
- DTOs for data transfer
- Service layer for business logic
- Repository layer for data access
- Model layer for domain entities

## Components

### Controllers

#### BookController

Handles all book-related operations:
- `POST /book/add`: Add a new book to the library
- `GET /book/`: Get a paginated list of all books
- `POST /book/borrow`: Register when a book is borrowed
- `POST /book/return`: Register when a book is returned

#### BorrowerController

Handles borrower-related operations:
- `POST /borrower/add`: Register a new borrower

### DTOs (Data Transfer Objects)

#### BookDTO

Represents book data for API requests and responses:
- `id`: Unique identifier for the book
- `title`: Book title (mandatory, 2-255 characters)
- `author`: Author's name (mandatory, 2-50 characters)
- `isbnNo`: International Standard Book Number (mandatory, 3-50 characters)
- `version`: Version of the book

#### BorrowerDTO

Represents borrower data for API requests and responses:
- `id`: Unique identifier for the borrower
- `name`: Borrower's name (mandatory, 2-50 characters)
- `email`: Borrower's email (mandatory, valid email format, 2-50 characters)

#### ErrorDetailsDTO

Generic DTO for exception handling:
- `timestamp`: When the error occurred
- `messages`: List of error messages
- `details`: Additional error details
- `errorCode`: Error classification code

### Service Implementations

#### BookServiceImpl

Implements business logic for book operations:
- `addBook()`: Adds a new book after checking for duplicates
- `getBooks()`: Retrieves paginated list of books

#### BorrowerServiceImpl

Implements business logic for borrower operations:
- `addBorrower()`: Registers a new borrower after checking for duplicates

#### LedgerServiceImpl

Implements business logic for borrowing/returning books:
- `handleLedger()`: Processes book borrows and returns with validation

### Models (not included in files but referenced)

- `Book`: Entity representing a book
- `Borrower`: Entity representing a borrower
- `Ledger`: Entity tracking borrowing transactions

### Repositories (not included in files but referenced)

- `BookRepository`: Data access for books
- `BorrowerRepository`: Data access for borrowers
- `LedgerRepository`: Data access for ledger entries

## Validation

The system includes validation to ensure:
- No duplicate books (based on ISBN, title, and author)
- No duplicate borrowers (based on name and email)
- Books can only be borrowed if they are available
- Books can only be returned if they are currently borrowed

## Error Handling

Custom exception handling with:
- `ConflictException`: For business logic violations (duplicates, invalid operations)
- Detailed error responses with timestamps and messages

## Database

This project uses PostgreSQL as the relational database management system. PostgreSQL was chosen for the following reasons:

### Why PostgreSQL?

1. **ACID Compliance**: PostgreSQL provides full ACID (Atomicity, Consistency, Isolation, Durability) compliance, which is essential for a library management system where data integrity is critical - especially for book lending transactions.

2. **Advanced Data Types**: PostgreSQL supports a wide range of data types including JSON, arrays, and enums. This is particularly useful for storing complex data like book metadata or managing the LedgerStatus enum.

3. **Scalability**: As your library collection grows, PostgreSQL scales efficiently to handle larger datasets while maintaining performance.

4. **Robust Transactions**: The library system relies on accurate transaction handling when books are borrowed and returned. PostgreSQL excels at handling concurrent transactions safely.

5. **Full-Text Search**: For future enhancements like book search functionality, PostgreSQL offers powerful built-in text search capabilities.

6. **Open Source**: PostgreSQL is free, open-source software with a strong community, reducing licensing costs while ensuring good support.

7. **Reliability**: Known for its reliability and data integrity, PostgreSQL minimizes the risk of data corruption in your library records.

8. **JPA/Hibernate Compatibility**: Works seamlessly with Spring Data JPA and Hibernate, which this project leverages.

### Database Schema

The core tables in the database are:
- `book`: Stores book information
- `borrower`: Stores borrower information 
- `ledger`: Tracks all borrowing and returning transactions

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- Spring Boot 3.x
- PostgreSQL 13+ installed and running locally

### Running the Application

```bash
# Clone the repository
git clone https://github.com/chandrupc/library-management.git

# Navigate to the project directory
cd library-management

# Configure database connection
# Update application.properties with your PostgreSQL connection details:
# spring.datasource.url=jdbc:postgresql://localhost:5432/library_db
# spring.datasource.username=postgres
# spring.datasource.password=yourpassword
# spring.jpa.hibernate.ddl-auto=update
# spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

### API Usage Examples

#### Adding a Book

```bash
curl -X POST http://localhost:8080/book/add \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Clean Code",
    "author": "Robert C. Martin",
    "isbnNo": "978-0132350884"
  }'
```

#### Adding a Borrower

```bash
curl -X POST http://localhost:8080/borrower/add \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com"
  }'
```

#### Borrowing a Book

```bash
curl -X POST "http://localhost:8080/book/borrow?bookId=1&borrowerId=1"
```

#### Returning a Book

```bash
curl -X POST "http://localhost:8080/book/return?bookId=1&borrowerId=1"
```

## Project Structure

```
com.library.management/
├── controller/
│   ├── BookController.java
│   └── BorrowerController.java
├── dto/
│   ├── BookDTO.java
│   ├── BorrowerDTO.java
│   └── ErrorDetailsDTO.java
├── enums/
│   └── LedgerStatus.java
├── exception/
│   └── ConflictException.java
├── model/
│   ├── Book.java
│   ├── Borrower.java
│   └── Ledger.java
├── repository/
│   ├── BookRepository.java
│   ├── BorrowerRepository.java
│   └── LedgerRepository.java
├── service/
│   ├── BookService.java
│   ├── BorrowerService.java
│   └── LedgerService.java
└── service/impl/
    ├── BookServiceImpl.java
    ├── BorrowerServiceImpl.java
    └── LedgerServiceImpl.java
```

## Testing

The project includes comprehensive JUnit tests for service implementations to verify:
- Adding books and borrowers
- Handling duplicate entities
- Borrowing and returning books
- Data mapping between DTOs and entities

## Future Enhancements

- User authentication and authorization
- Book categories and search functionality
- Fine calculation for late returns
- Book reservation system
- Email notifications for borrowing/returning
