# Community Ride-Sharing Platform

This project is a microservices-based ride-sharing platform designed for small towns. It enables vehicle owners to offer rides to residents, helping to reduce commute times and create a local transportation network.

## Project Overview

The application is built using a microservices architecture, with each service responsible for a specific domain. The services are containerized using Docker and orchestrated with Docker Compose for local development.

### Technology Stack

- **Backend Services**: Spring Boot (Java 17)
- **API Gateway**: Spring Cloud Gateway
- **Databases**: PostgreSQL
- **Containerization**: Docker & Docker Compose

### Services

- `auth-service`: Handles user registration, login, and JWT-based authentication.
- `user-service`: Manages user profiles and vehicle information.
- `ride-service`: Manages ride requests, matching, and tracking.
- `payment-service`: Handles payment tracking and earnings.
- `rating-service`: Manages ratings and feedback.
- `api-gateway`: The single entry point for all client requests, routing them to the appropriate backend service.
- `notification-service`: (To be implemented) Sends notifications to users.

## Getting Started

Follow these instructions to get the project running on your local machine.

### Prerequisites

- Java 17 or later
- Maven 3.8 or later
- Docker and Docker Compose (V2)

### Installation & Running the Application

1.  **Clone the repository**

    ```bash
    git clone <repository-url>
    cd <project-directory>
    ```

2.  **Build and run the services using Docker Compose**

    From the root of the project, run the following command:

    ```bash
    docker compose up --build
    ```

    This command will:
    - Build the Docker images for the `auth-service`, `user-service`, and `api-gateway`.
    - Start the containers for all services and their respective PostgreSQL databases.
    - Create a shared network for the services to communicate with each other.

    **Note on Docker Permissions:** If you encounter a "permission denied" error when running this command, it's likely that your user does not have the necessary permissions to interact with the Docker daemon. You can typically resolve this by:
    - Running the command with `sudo`: `sudo docker compose up --build`
    - Or, by adding your user to the `docker` group (requires a logout/login to take effect): `sudo usermod -aG docker $USER`

3.  **Verify the services are running**

    You can check the logs of the running containers to ensure everything started correctly:

    ```bash
    docker compose logs -f
    ```

    You should see output from all the services, indicating that they have started successfully.

### API Endpoints

The API Gateway is the single entry point for all requests. It is available at `http://localhost:8080`. All API routes are prefixed with `/api`.

#### Auth Service (`/api/auth`)

-   **Sign Up**: `POST /api/auth/signup`
    -   Body: `{ "email": "user@example.com", "password": "password" }`
-   **Login**: `POST /api/auth/login`
    -   Body: `{ "email": "user@example.com", "password": "password" }`

#### User Service (`/api/users`)

-   **Create Profile**: `POST /api/users/{userId}`
    -   **Note**: `{userId}` must match the ID from the Auth service.
    -   Body: `{ "fullName": "John Doe", "phone": "123-456-7890", "role": "PASSENGER" }`
-   **Get Profile**: `GET /api/users/{userId}`
-   **Update Profile**: `PUT /api/users/{userId}`
    -   Body: (Same as create profile)
-   **Add Vehicle**: `POST /api/users/{userId}/vehicles`
    -   Body: `{ "make": "Toyota", "model": "Camry", "licensePlate": "123-ABC", "seatsAvailable": 4 }`
-   **Get Vehicles**: `GET /api/users/{userId}/vehicles`

#### Ride Service (`/api/rides`)

-   **Request a Ride**: `POST /api/rides`
    -   Body: `{ "passengerId": "...", "pickupLocation": { "latitude": ..., "longitude": ... }, "dropoffLocation": { "latitude": ..., "longitude": ... } }`
-   **Get Ride Details**: `GET /api/rides/{rideId}`
-   **Get Available Ride Requests**: `GET /api/rides/requests` (For drivers)
-   **Accept a Ride**: `POST /api/rides/{rideId}/accept` (For drivers)
    -   Body: `{ "driverId": "..." }`

#### Payment Service (`/api/payments`)

-   **Process a Transaction**: `POST /api/payments`
    -   **Note**: For MVP, this just logs a transaction and updates earnings.
    -   Body: `{ "rideId": "...", "payerId": "...", "payeeId": "...", "amount": ... }`

#### Rating Service (`/api/ratings`)

-   **Submit a Rating**: `POST /api/ratings`
    -   Body: `{ "rideId": "...", "ratedBy": "...", "ratedUser": "...", "rating": 5, "comment": "Great driver!" }`

### Connecting to the Databases

The services use PostgreSQL databases running in Docker containers. You can connect to them directly from your host machine using any standard PostgreSQL client (e.g., DBeaver, DataGrip, `psql`).

Here are the connection details for each database:

| Service         | Host        | Port | Database        | Username      | Password        |
|-----------------|-------------|------|-----------------|---------------|-----------------|
| **auth-db**     | `localhost` | 5431 | `auth_db`       | `authuser`    | `authpassword`  |
| **user-db**     | `localhost` | 5433 | `user_db`       | `useruser`    | `userpassword`  |
| **ride-db**     | `localhost` | 5434 | `ride_db`       | `rideuser`    | `ridepassword`  |
| **payment-db**  | `localhost` | 5435 | `payment_db`    | `paymentuser` | `paymentpassword` |
| **rating-db**   | `localhost` | 5436 | `rating_db`     | `ratinguser`  | `ratingpassword`|

**Example using `psql`:**

To connect to the `auth-db` using the `psql` command-line tool, you can run:

```bash
psql -h localhost -p 5431 -U authuser -d auth_db
```

You will be prompted for the password (`authpassword`). You can use similar commands to connect to the other databases by changing the port, username, and database name accordingly.
