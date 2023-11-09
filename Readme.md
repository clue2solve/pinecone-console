# Pinecone Console

The Pinecone Console is a Spring Boot web application designed to demonstrate the capabilities of the [pinecone-java-client](https://github.com/clue2solve/pinecone-java-client) library. It serves as an example of how to integrate Pinecone's vector database operations within a Java-based web environment.

## Features

- **Spring Boot Web Application**: A ready-to-use template for integrating Pinecone with Spring Boot.
- **CRUD Operations**: Examples of Create (Upsert), Read (Query and Fetch), and Delete operations.
- **Easy Configuration**: Application properties can be set for Pinecone API interactions.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- JDK 1.8 or later.
- Maven.
- An active Pinecone account with an API key.

### Installation

1. **Clone the repository**:

```bash
git clone https://github.com/clue2solve/pinecone-console.git
cd pinecone-console
```

### Configure `application properties`:
Open `src/main/resources/application.properties` and set your Pinecone project details:

**properties**

```bash
pinecone.environment=your-environment
pinecone.projectId=your-project-id
pinecone.apiKey=your-api-key
```

### Build the project:
```bash
mvn clean install
```

### Run the application:
```bash
mvn spring-boot:run
```

### Swagger UI
The Pinecone Console is equipped with a Swagger UI, which provides a web-based interface to explore and test the API endpoints. After running the application, you can access the Swagger UI at:

```bash
http://localhost:8080/swagger-ui/index.html
```

Through the Swagger UI, you can try out all the APIs, view the expected request formats, and interact with the Pinecone database directly from your browser.


The application should now be running on http://localhost:8080.

### Usage
The Pinecone Console exposes several REST endpoints to interact with the Pinecone database:

Example Request

```bash
curl -X POST http://localhost:8080/query \
-H "Content-Type: application/json" \
-d '{"vector":"example-vector"}'
```

**Replace the example payload with your actual query.**

### Documentation
For more detailed documentation, visit the Pinecone Documentation.

### Contributing
Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are greatly appreciated.


### License
Distributed under the MIT License. See LICENSE for more information.

### Acknowledgements
Pinecone Systems Inc. for providing the vector database platform.
Contributors who have participated in making this application.