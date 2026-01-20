
<div align="center">

<h1>Jet Notifier - Notification System</h1>

**A high-performance, fault-tolerant microservice for enterprise notification management**

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-3.x-black.svg)](https://kafka.apache.org/)
[![MongoDB](https://img.shields.io/badge/MongoDB-6.0-green.svg)](https://www.mongodb.com/)
[![Docker](https://img.shields.io/badge/Docker-ready-blue.svg)](https://www.docker.com/)

</div>

---

## Table of Contents

- [Overview](#overview)
- [Key Features](#key-features)
- [Architecture](#architecture)
- [Technology Stack](#technology-stack)
- [Use Cases](#use-cases)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Configuration](#configuration)
- [API Documentation](#api-documentation)
- [Deployment](#deployment)
- [Monitoring & Logging](#monitoring--logging)
- [Contributing](#contributing)

---

## Overview

The **Notification System** is a production-ready microservice designed to centralize and streamline all notification operations for enterprise backends. Built with **Java Spring Boot** and powered by **Apache Kafka**, this system provides asynchronous, scalable, and reliable notification delivery across multiple channels including Email, SMS, Push Notifications, and Webhooks.

### Why This System is Different?

- **Decouple notification logic** from your core application
- **Scale independently** to handle millions of notifications
- **Guarantee delivery** with fault-tolerant architecture
- **Track and audit** every notification lifecycle
- **Reduce development time** by reusing notification infrastructure

---

## Key Features

### Multi-Channel Support
- **Email** - SMTP-based email delivery with HTML template support
- **SMS** - SMS gateway integration for text notifications
- **Push Notifications** - Mobile and web push notification delivery
- **Webhooks** - HTTP callback integration for external systems

### High Performance & Reliability
- **Apache Kafka** integration for asynchronous, distributed message processing
- **Event-driven architecture** ensuring non-blocking operations
- **Automatic retry mechanism** with configurable retry policies
- **Dead Letter Queue (DLQ)** for failed message handling

### Advanced Notification Management
- **Priority-based delivery** (LOW, MEDIUM, HIGH, URGENT)
- **Scheduled notifications** with precise timing control
- **Template management** for reusable notification formats
- **User preference management** for channel-specific opt-in/opt-out
- **Broadcast notifications** to all users simultaneously

### Enterprise-Grade Features
- **Comprehensive tracking** with delivery status and audit trails
- **Real-time statistics** and analytics dashboard
- **Admin panel** for notification monitoring and management
- **Quiet hours** support respecting user time zones
- **Frequency limiting** to prevent notification fatigue

### Scalability & DevOps
- **Docker & Docker Compose** ready for containerized deployment
- **Jenkins pipeline** for CI/CD automation
- **MongoDB** for flexible data persistence
- **Health checks** and monitoring endpoints
- **Structured logging** with Logback integration

---

## Architecture

The system follows a microservices architecture pattern with event-driven communication:

```
┌─────────────────┐      ┌──────────────────┐      ┌─────────────────┐
│   Client App    │─────▶│  REST API        │─────▶│  Kafka Producer │
│  (Your Backend) │      │  (Controller)    │      │                 │
└─────────────────┘      └──────────────────┘      └────────┬────────┘
                                                             │
                                                             ▼
                         ┌──────────────────────────────────────────┐
                         │         Apache Kafka Topic               │
                         │      (notification-events)               │
                         └────────┬─────────────────────────────────┘
                                  │
                                  ▼
                         ┌──────────────────┐
                         │ Kafka Consumer   │
                         │  (Event Handler) │
                         └────────┬─────────┘
                                  │
                ┌─────────────────┼─────────────────┐
                ▼                 ▼                 ▼
        ┌──────────────┐  ┌──────────────┐  ┌──────────────┐
        │ Email Channel│  │  SMS Channel │  │ Push Channel │
        └──────┬───────┘  └──────┬───────┘  └──────┬───────┘
               │                 │                  │
               └─────────────────┴──────────────────┘
                                 │
                                 ▼
                         ┌──────────────────┐
                         │    MongoDB       │
                         │ (Persistence)    │
                         └──────────────────┘
```

### Core Components

1. **REST API Layer** - Spring Boot controllers exposing notification endpoints
2. **Kafka Producer** - Publishes notification events to Kafka topics
3. **Kafka Consumer** - Processes events asynchronously and routes to channels
4. **Channel Registry** - Strategy pattern implementation for pluggable notification channels
5. **MongoDB Repository** - Stores notification data, user preferences, and audit logs
6. **Admin Service** - Management interface for monitoring and operations

---

## Technology Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| **Runtime** | Java | 17+ |
| **Framework** | Spring Boot | 3.x |
| **Message Broker** | Apache Kafka | 7.4.0 (KRaft mode) |
| **Database** | MongoDB | 6.0 |
| **Email** | JavaMail API | - |
| **Build Tool** | Maven | 3.x |
| **Containerization** | Docker & Docker Compose | - |
| **CI/CD** | Jenkins | - |
| **Logging** | Logback | - |

---

## Use Cases

### E-Commerce Platform
- Order confirmation emails
- Shipping notifications via SMS
- Abandoned cart reminders
- Flash sale push notifications

### Financial Services
- Transaction alerts via SMS
- Account activity emails
- Payment reminders
- Security alerts (high priority)

### SaaS Applications
- User onboarding emails
- Feature announcements
- System maintenance notifications
- Usage limit warnings

### Healthcare Systems
- Appointment reminders
- Lab result notifications
- Medication reminders
- Emergency alerts

### Social Media & Community Platforms
- Comment and mention notifications
- Friend requests
- Content moderation alerts
- Daily digest emails

---

## Getting Started

### Prerequisites

Before running the application, ensure you have the following installed: 

- **Java 17+** - [Download](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.x** - [Download](https://maven.apache.org/download.cgi)
- **Docker & Docker Compose** - [Download](https://www.docker.com/get-started)
- **Git** - [Download](https://git-scm.com/downloads)

### Installation

#### 1. Clone the Repository

```bash
git clone https://github.com/siddhantkore/Notification-System. git
cd Notification-System
```

#### 2. Start Infrastructure Services

Start MongoDB and Kafka using Docker Compose:

```bash
docker-compose up -d
```

This will start:
- **Apache Kafka** on `localhost:9092` (KRaft mode, no Zookeeper required)
- **MongoDB** on `localhost:27017` (credentials: `admin/password123`)

Verify services are running:

```bash
docker-compose ps
```

#### 3. Build the Application

```bash
./mvnw clean install
```

#### 4. Run the Application

```bash
./mvnw spring-boot:run
```

Or use the convenience script:

```bash
chmod +x local-run.sh
./local-run. sh
```

The application will start on `http://localhost:8080`

---

### Configuration

#### Application Properties

Create `src/main/resources/application. properties` or `application.yml` with your configuration:

```properties
# Server Configuration
server.port=8080

# MongoDB Configuration
spring.data.mongodb. host=localhost
spring.data.mongodb.port=27017
spring. data.mongodb.database=notification
spring.data.mongodb.username=admin
spring.data.mongodb.password=password123
spring.data.mongodb.authentication-database=admin

# Kafka Configuration
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.producer.key-serializer=org.apache. kafka.common.serialization.StringSerializer
spring.kafka.producer. value-serializer=org.springframework.kafka.support.serializer. JsonSerializer
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.springframework. kafka.support.serializer.JsonDeserializer
spring.kafka.consumer.group-id=notification-consumer-group
spring.kafka.consumer.auto-offset-reset=earliest

# Email Configuration (Update with your SMTP settings)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring. mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# Logging
logging.level.com.jetnotifier=INFO
logging.level. org.apache.kafka=WARN
logging.level.org. springframework.kafka=WARN
```

#### Environment Variables

Alternatively, configure using environment variables:

```bash
export MONGODB_HOST=localhost
export MONGODB_PORT=27017
export KAFKA_BOOTSTRAP_SERVERS=localhost:9092
export SMTP_HOST=smtp.gmail.com
export SMTP_PORT=587
export SMTP_USERNAME=your-email@gmail.com
export SMTP_PASSWORD=your-app-password
```

---

## API Documentation

### Create Notification

**Endpoint:** `POST /api/notifications`

**Request Body:**
```json
{
  "userId": "user123",
  "title": "Order Shipped",
  "message": "Your order #12345 has been shipped! ",
  "type": "EMAIL",
  "priority": "HIGH",
  "metadata": {
    "orderId": "12345",
    "trackingNumber": "TRK-9876"
  }
}
```

**Response:**
```json
{
  "id": "notification-id",
  "status": "PENDING",
  "createdAt": "2026-01-20T10:30:00"
}
```

### Get User Notifications

**Endpoint:** `GET /api/notifications/user/{userId}`

**Query Parameters:**
- `page` - Page number (default: 0)
- `size` - Page size (default: 20)
- `status` - Filter by status (PENDING, SENT, FAILED)

### Get Notification Status

**Endpoint:** `GET /api/notifications/{id}`

**Response:**
```json
{
  "id": "notification-id",
  "userId": "user123",
  "title": "Order Shipped",
  "type": "EMAIL",
  "status": "SENT",
  "sentAt": "2026-01-20T10:31:05",
  "retryCount": 0
}
```

### Admin:  Broadcast Notification

**Endpoint:** `POST /api/admin/broadcast`

Send notification to all users:

```json
{
  "title": "System Maintenance",
  "message":  "Scheduled maintenance on Jan 25, 2026",
  "type": "EMAIL",
  "priority": "MEDIUM"
}
```

### Admin: Get System Statistics

**Endpoint:** `GET /api/admin/stats`

**Response:**
```json
{
  "total": 15420,
  "sent": 14892,
  "failed": 123,
  "pending": 405,
  "successRate": 96.58
}
```

### User Preferences

**Endpoint:** `PUT /api/users/{userId}/preferences`

```json
{
  "emailEnabled": true,
  "smsEnabled": false,
  "pushEnabled": true,
  "quietHoursStart": "22:00",
  "quietHoursEnd": "08:00",
  "timezone": "America/New_York"
}
```

---

## Deployment

### Docker Deployment

#### Build Docker Image

```bash
docker build -t notification-system: latest .
```

#### Run with Docker Compose

The application includes a complete `docker-compose.yml` for production deployment:

```bash
docker-compose up -d
```

This orchestrates:
- Notification System application
- Apache Kafka (KRaft mode)
- MongoDB

#### Docker Environment Variables

```yaml
environment:
  - SPRING_DATA_MONGODB_HOST=mongodb
  - SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:29092
  - SPRING_MAIL_HOST=smtp.gmail.com
  - SPRING_MAIL_USERNAME=${SMTP_USERNAME}
  - SPRING_MAIL_PASSWORD=${SMTP_PASSWORD}
```

## Monitoring & Logging

### Health Check Endpoints

- **Application Health:** `GET /actuator/health`
- **Kafka Health:** Auto-configured via Docker healthcheck
- **MongoDB Health:** Auto-configured via Docker healthcheck

### Logging

Logs are stored in `logs/application.log` with daily rotation: 

```xml
<rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
    <fileNamePattern>logs/archived/notification_system_%d{yyyy-MM-dd}.log</fileNamePattern>
    <maxHistory>30</maxHistory>
</rollingPolicy>
```

### Metrics & Analytics

Access the admin dashboard at `http://localhost:8080/` for: 
- Real-time notification statistics
- Success/failure rates
- Channel-wise metrics
- User engagement analytics

---

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

**Siddhant Kore**

- Email: [Siddhant Kore](mailto:siddhskore@gmail.com)
- GitHub: [@siddhantkore](https://github.com/siddhantkore)
- Project Link: [https://github.com/siddhantkore/Notification-System](https://github.com/siddhantkore/Notification-System)

---

<div align="center">

**Built using Spring Boot and Apache Kafka**

⭐ Star this repository if you find it helpful!

</div>
