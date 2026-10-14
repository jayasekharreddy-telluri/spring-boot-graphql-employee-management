# Spring Boot GraphQL Employee Management API

A Java-based learning project that demonstrates how to build a GraphQL API using Spring Boot, Spring for GraphQL, Spring Data JPA, Hibernate, and an H2 in-memory database.

The application manages employees and departments and supports CRUD operations, filtering, and nested relationship queries through GraphQL.

## Tech Stack

- **Java** — Programming language
- **Spring Boot** — Application framework
- **Spring for GraphQL** — GraphQL API implementation
- **Spring Data JPA & Hibernate** — Data persistence and ORM
- **H2 Database** — In-memory relational database
- **Maven** — Dependency management and build tool
- **Postman** — GraphQL API testing

## Features

- Retrieve all employees
- Retrieve an employee by ID
- Retrieve all departments
- Create departments
- Create, update, and delete employees
- Filter employees by department ID
- Resolve nested relationships between employees and departments
- Handle missing employee errors
- Test queries and mutations using Postman

## Project Structure

```text
src/
└── main/
    ├── java/com/graphql/demo/
    │   ├── controller/
    │   │   └── EmployeeController.java
    │   ├── exception/
    │   │   ├── EmployeeNotFoundException.java
    │   │   └── GraphQLExceptionHandler.java
    │   ├── model/
    │   │   ├── Employee.java
    │   │   └── Department.java
    │   └── repository/
    │       ├── EmployeeRepository.java
    │       └── DepartmentRepository.java
    └── resources/
        ├── graphql/
        │   └── schema.graphqls
        └── application.properties
```

*Note: The structure above is illustrative. Your actual files and package names may differ.*

## Getting Started

### Prerequisites

- Java version compatible with your project
- Maven, or the included Maven Wrapper
- Git
- Postman or another GraphQL client

### 1. Clone the Repository

```bash
git clone https://github.com/jayasekharreddy-telluri/spring-boot-graphql-employee-management.git
```

### 2. Navigate to the Project

```bash
cd spring-boot-graphql-employee-management
```

### 3. Run the Application

On Windows using Git Bash:

```bash
./mvnw spring-boot:run
```

Alternatively, if Maven is installed:

```bash
mvn spring-boot:run
```

You can also import the project into IntelliJ IDEA and run the Spring Boot main application class.

### 4. Access the GraphQL Endpoint

Once the application starts, send GraphQL requests to:

```text
http://localhost:8080/graphql
```

The examples below assume the application runs on port `8080`.

## GraphQL API Examples

Use POST requests in Postman. Select the GraphQL request body type and enter the following operations.

### 1. Get All Employees

```graphql
query {
  employees {
    id
    name
    salary
    department {
      id
      name
    }
  }
}
```

### 2. Get an Employee by ID

```graphql
query {
  employeeById(id: "1") {
    id
    name
    salary
    department {
      id
      name
    }
  }
}
```

### 3. Get Departments with Employees

```graphql
query {
  departments {
    id
    name
    employees {
      id
      name
      salary
    }
  }
}
```

### 4. Create a Department

```graphql
mutation {
  addDepartment(name: "Engineering") {
    id
    name
  }
}
```

Use the department ID returned by this mutation in subsequent employee operations.

### 5. Create an Employee

```graphql
mutation {
  addEmployee(
    name: "Ravi"
    departmentId: "1"
    salary: 75000
  ) {
    id
    name
    salary
    department {
      id
      name
    }
  }
}
```

Replace the sample department ID with an ID that exists in your database.

### 6. Update an Employee

```graphql
mutation {
  updateEmployee(
    id: "1"
    name: "Ravi Kumar"
    departmentId: "1"
    salary: 80000
  ) {
    id
    name
    salary
    department {
      id
      name
    }
  }
}
```

Replace both IDs with valid IDs from your database.

### 7. Delete an Employee

```graphql
mutation {
  deleteEmployee(id: "1")
}
```

The mutation is expected to return a Boolean indicating whether the employee was deleted, based on the implemented resolver.

### 8. Filter Employees by Department

```graphql
query {
  employeesByDepartment(departmentId: "1") {
    id
    name
    salary
  }
}
```

Replace the department ID with a valid ID.

## Database Configuration

This project uses an H2 in-memory database for development and testing.

- Data is not intended to persist after the application stops.
- Recreate departments and employees after restarting the application.
- Use the IDs returned by your mutations rather than assuming IDs will always start at `1`.

For applications requiring persistent storage, the database can be replaced with PostgreSQL, MySQL, or another supported relational database.

## Key Concepts Demonstrated

- GraphQL schemas, queries, and mutations
- GraphQL types, arguments, and nullability
- Spring GraphQL annotations such as `@QueryMapping`, `@MutationMapping`, `@Argument`, and `@SchemaMapping`
- Spring Data JPA repositories
- Entity relationships and nested field resolution
- CRUD operations and relationship-based filtering
- Exception handling in GraphQL

## Future Improvements

- Add pagination and sorting
- Optimize nested queries and address the N+1 query problem using DataLoader
- Implement authentication and authorization
- Add unit and integration tests
- Introduce input validation and consistent error responses
- Add logging, monitoring, and production database configuration

## Learning Purpose

This project was developed for learning and practicing Java, Spring Boot, GraphQL, and database integration. It demonstrates core API development concepts and can be extended with additional features.

## Author

**Jayasekhar Reddy**

GitHub: [@jayasekharreddy-telluri](https://github.com/jayasekharreddy-telluri)

## License

No license has been specified for this project. All rights are reserved unless a license is added to the repository.
