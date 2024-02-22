
# VinoVibes API & UI

Welcome to the VinoVibes project! This repository contains the source code for the VinoVibes API and its corresponding user interface. VinoVibes is a platform for wine enthusiasts, enabling users to rate, review, and discover new wines.

## Structure

The project is divided into two main directories:

- `vino-api`: Contains the backend code, written in Java with Spring Boot.
- `vino-ui`: Contains the frontend code, developed with React.

### Vino-API

The API serves as the backend of the project, responsible for data processing, authentication, and data management. It provides endpoints for authentication, user management, wine data, and ratings.

#### Key Directories and Files:

- `src/main/java/com/vinovibes/vinoapi`: Contains the Java source code of the application.
- `src/main/resources`: Configuration files and resources.
- `pom.xml`: Maven project file with dependencies and build configuration.

### Vino-UI

The user interface offers an interactive experience for users to browse, rate, and review wines. It is developed in React and utilizes modern web technologies.

#### Key Directories and Files:

- `src`: Contains the React source code of the application.
- `public`: Public assets such as images and HTML files.
- `package.json`: Defines dependencies and scripts for the Node.js project.

## Setup and Installation

### Prerequisites

- Java JDK 17 or higher
- Maven
- Node.js and npm

### Starting Vino-API

1. Navigate to the `vino-api` directory.
2. Run `./mvnw spring-boot:run -Dspring-boot.run.profiles=local` to start the API.

### Starting Vino-UI

1. Navigate to the `vino-ui` directory.
2. Install dependencies with `npm install`.
3. Start the application with `npm start`.