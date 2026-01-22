# Notification System APIs Documentation

## Table of Contents
1. [Accessing Kafka Messages](#accessing-kafka-messages)
2. [Accessing MongoDB Data](#accessing-mongodb-data)
3. [API Endpoints](#api-endpoints)

---

## Accessing Kafka Messages

### Using Docker Compose

When running with `docker-compose`, Kafka is accessible at `localhost:9092`. Here are several ways to view messages:

#### Option 1: Using Kafka Console Consumer (Inside Container)

```bash
# Access Kafka container
docker exec -it kafka bash

# List all topics
kafka-topics --bootstrap-server localhost:29092 --list

# Consume messages from a specific topic
kafka-console-consumer --bootstrap-server localhost:29092 --topic notification-email --from-beginning
kafka-console-consumer --bootstrap-server localhost:29092 --topic notification-sms --from-beginning
kafka-console-consumer --bootstrap-server localhost:29092 --topic notification-push --from-beginning
kafka-console-consumer --bootstrap-server localhost:29092 --topic notification-webhook --from-beginning
kafka-console-consumer --bootstrap-server localhost:29092 --topic notification-in-app --from-beginning
kafka-console-consumer --bootstrap-server localhost:29092 --topic notification-dlq --from-beginning
```

#### Option 2: Using Kafka Console Consumer (From Host)

```bash
# List topics
docker exec kafka kafka-topics --bootstrap-server localhost:29092 --list

# Consume messages
docker exec -it kafka kafka-console-consumer --bootstrap-server localhost:29092 --topic notification-email --from-beginning
```

#### Option 3: Using Kafka UI (Web Interface)

Kafka UI is available at `http://localhost:8081` when running with docker-compose. You can:
- View all topics
- Browse messages
- Monitor consumer groups
- View topic configurations

Simply open your browser and navigate to: `http://localhost:8081`

---

## Accessing MongoDB Data

### Using Docker Compose

MongoDB is accessible at `localhost:27017` with the following credentials:
- **Username**: `admin`
- **Password**: `password123`
- **Database**: `notification`
- **Auth Source**: `admin`

#### Option 1: Using MongoDB Shell (mongosh) - Inside Container

```bash
# Access MongoDB container
docker exec -it mongodb mongosh -u admin -p password123 --authenticationDatabase admin

# Switch to notification database
use notification

# List all collections
show collections

# View documents in a collection
db.notifications.find().pretty()
db.templates.find().pretty()
db.notification_preferences.find().pretty()
db.users.find().pretty()

# Count documents
db.notifications.countDocuments()
db.templates.countDocuments()
```

#### Option 2: Using MongoDB Shell (mongosh) - From Host

```bash
# Connect to MongoDB
docker exec -it mongodb mongosh -u admin -p password123 --authenticationDatabase admin notification

# Or using connection string
docker exec -it mongodb mongosh "mongodb://admin:password123@localhost:27017/notification?authSource=admin"
```

#### Option 3: Using MongoDB Compass (GUI Tool)

1. Download MongoDB Compass from https://www.mongodb.com/try/download/compass
2. Connect using connection string:
   ```
   mongodb://admin:password123@localhost:27017/notification?authSource=admin
   ```
3. Browse collections and documents visually

#### Option 4: Using mongoimport/mongoexport

```bash
# Export a collection
docker exec mongodb mongosh -u admin -p password123 --authenticationDatabase admin notification --eval "db.notifications.find().forEach(printjson)"

# Import data (if needed)
docker exec -i mongodb mongosh -u admin -p password123 --authenticationDatabase admin notification < data.json
```

---

## API Endpoints

Base URL: `http://localhost:8080`

### Health Check

#### GET /api/health/

**Description**: Check the health status of the service, MongoDB, and Kafka connections.

**Request Type**: `GET`

**Required Args/Val**: None

**cURL Command**:
```bash
curl -X GET http://localhost:8080/api/health/
```

---

### Landing Page

#### GET /

**Description**: Get the landing page HTML.

**Request Type**: `GET`

**Required Args/Val**: None

**cURL Command**:
```bash
curl -X GET http://localhost:8080/
```

---

### Notification APIs

#### POST /api/notifications

**Description**: Create a new notification.

**Request Type**: `POST`

**Required Args/Val**:
- `userId` (String, required): User ID
- `title` (String, required): Notification title
- `message` (String, required): Notification message
- `type` (Enum, required): Notification type (EMAIL, SMS, PUSH, WEBHOOK, IN_APP)
- `priority` (Enum, optional): Priority level (LOW, MEDIUM, HIGH, URGENT) - defaults to MEDIUM
- `templateId` (String, optional): Template ID
- `channelConfig` (Map, optional): Channel-specific configuration
- `metadata` (Map, optional): Additional metadata
- `scheduledAt` (DateTime, optional): Scheduled send time

**cURL Command**:
```bash
curl -X POST http://localhost:8080/api/notifications \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "user123",
    "title": "Order Shipped",
    "message": "Your order has been shipped and will arrive soon.",
    "type": "EMAIL",
    "priority": "HIGH",
    "templateId": "order-shipped-template",
    "channelConfig": {
      "email": "user@example.com"
    },
    "metadata": {
      "orderNumber": "ORD-12345",
      "trackingNumber": "TRK-9876"
    }
  }'
```

#### GET /api/notifications/{id}

**Description**: Get a notification by ID.

**Request Type**: `GET`

**Required Args/Val**:
- `id` (Path parameter): Notification ID

**cURL Command**:
```bash
curl -X GET http://localhost:8080/api/notifications/507f1f77bcf86cd799439011
```

#### GET /api/notifications/user/{userId}

**Description**: Get all notifications for a specific user.

**Request Type**: `GET`

**Required Args/Val**:
- `userId` (Path parameter): User ID

**cURL Command**:
```bash
curl -X GET http://localhost:8080/api/notifications/user/user123
```

#### GET /api/notifications

**Description**: Get all notifications with pagination.

**Request Type**: `GET`

**Required Args/Val**: None (pagination is optional)

**Query Parameters** (optional):
- `page`: Page number (default: 0)
- `size`: Page size (default: 20)
- `sort`: Sort field and direction (e.g., `createdAt,desc`)

**cURL Command**:
```bash
# Get first page
curl -X GET "http://localhost:8080/api/notifications?page=0&size=10"

# Get with sorting
curl -X GET "http://localhost:8080/api/notifications?page=0&size=10&sort=createdAt,desc"
```

#### PUT /api/notifications/{id}/status

**Description**: Update the status of a notification.

**Request Type**: `PUT`

**Required Args/Val**:
- `id` (Path parameter): Notification ID
- `status` (Query parameter): New status (e.g., SENT, FAILED, PENDING)

**cURL Command**:
```bash
curl -X PUT "http://localhost:8080/api/notifications/507f1f77bcf86cd799439011/status?status=SENT"
```

#### DELETE /api/notifications/{id}

**Description**: Delete a notification by ID.

**Request Type**: `DELETE`

**Required Args/Val**:
- `id` (Path parameter): Notification ID

**cURL Command**:
```bash
curl -X DELETE http://localhost:8080/api/notifications/507f1f77bcf86cd799439011
```

---

### Admin APIs

#### GET /api/admin/notifications

**Description**: Get all notifications with pagination (admin view).

**Request Type**: `GET`

**Required Args/Val**: None (pagination is optional)

**Query Parameters** (optional):
- `page`: Page number (default: 0)
- `size`: Page size (default: 20)
- `sort`: Sort field and direction

**cURL Command**:
```bash
curl -X GET "http://localhost:8080/api/admin/notifications?page=0&size=20"
```

#### GET /api/admin/notifications/{id}

**Description**: Get a notification by ID (admin view).

**Request Type**: `GET`

**Required Args/Val**:
- `id` (Path parameter): Notification ID

**cURL Command**:
```bash
curl -X GET http://localhost:8080/api/admin/notifications/507f1f77bcf86cd799439011
```

#### POST /api/admin/notifications/broadcast

**Description**: Broadcast a notification to multiple users.

**Request Type**: `POST`

**Required Args/Val**:
- `userId` (String, required): User ID
- `title` (String, required): Notification title
- `message` (String, required): Notification message
- `type` (Enum, required): Notification type (EMAIL, SMS, PUSH, WEBHOOK, IN_APP)
- `priority` (Enum, optional): Priority level
- `templateId` (String, optional): Template ID
- `channelConfig` (Map, optional): Channel-specific configuration
- `metadata` (Map, optional): Additional metadata
- `scheduledAt` (DateTime, optional): Scheduled send time

**cURL Command**:
```bash
curl -X POST http://localhost:8080/api/admin/notifications/broadcast \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "user123",
    "title": "System Maintenance",
    "message": "System will be under maintenance from 2 AM to 4 AM.",
    "type": "EMAIL",
    "priority": "HIGH",
    "channelConfig": {
      "email": "user@example.com"
    }
  }'
```

#### GET /api/admin/notifications/stats

**Description**: Get notification statistics.

**Request Type**: `GET`

**Required Args/Val**: None

**cURL Command**:
```bash
curl -X GET http://localhost:8080/api/admin/notifications/stats
```

#### POST /api/admin/notifications/{id}/retry

**Description**: Retry sending a failed notification.

**Request Type**: `POST`

**Required Args/Val**:
- `id` (Path parameter): Notification ID

**cURL Command**:
```bash
curl -X POST http://localhost:8080/api/admin/notifications/507f1f77bcf86cd799439011/retry
```

#### DELETE /api/admin/notifications/{id}

**Description**: Delete a notification by ID (admin).

**Request Type**: `DELETE`

**Required Args/Val**:
- `id` (Path parameter): Notification ID

**cURL Command**:
```bash
curl -X DELETE http://localhost:8080/api/admin/notifications/507f1f77bcf86cd799439011
```

---

### Preference APIs

#### GET /api/preferences/user/{userId}

**Description**: Get all notification preferences for a user.

**Request Type**: `GET`

**Required Args/Val**:
- `userId` (Path parameter): User ID

**cURL Command**:
```bash
curl -X GET http://localhost:8080/api/preferences/user/user123
```

#### POST /api/preferences

**Description**: Create a new notification preference.

**Request Type**: `POST`

**Required Args/Val**:
- `userId` (String, required): User ID
- `type` (Enum, required): Notification type (EMAIL, SMS, PUSH, WEBHOOK, IN_APP)
- `emailEnabled` (Boolean, optional): Enable email notifications (default: true)
- `smsEnabled` (Boolean, optional): Enable SMS notifications (default: true)
- `pushEnabled` (Boolean, optional): Enable push notifications (default: true)
- `webhookEnabled` (Boolean, optional): Enable webhook notifications (default: false)
- `quietHoursStart` (String, optional): Quiet hours start time
- `quietHoursEnd` (String, optional): Quiet hours end time
- `timezone` (String, optional): Timezone (default: UTC)
- `frequencyLimit` (Map, optional): Frequency limits

**cURL Command**:
```bash
curl -X POST http://localhost:8080/api/preferences \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "user123",
    "type": "EMAIL",
    "emailEnabled": true,
    "smsEnabled": false,
    "pushEnabled": true,
    "webhookEnabled": false,
    "quietHoursStart": "22:00",
    "quietHoursEnd": "08:00",
    "timezone": "America/New_York",
    "frequencyLimit": {
      "daily": 10,
      "hourly": 3
    }
  }'
```

#### PUT /api/preferences/{id}

**Description**: Update an existing notification preference.

**Request Type**: `PUT`

**Required Args/Val**:
- `id` (Path parameter): Preference ID
- Same body fields as POST (all fields are optional for update)

**cURL Command**:
```bash
curl -X PUT http://localhost:8080/api/preferences/507f1f77bcf86cd799439011 \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "user123",
    "type": "EMAIL",
    "emailEnabled": false,
    "smsEnabled": true,
    "pushEnabled": true,
    "webhookEnabled": false,
    "timezone": "UTC"
  }'
```

#### DELETE /api/preferences/{id}

**Description**: Delete a notification preference.

**Request Type**: `DELETE`

**Required Args/Val**:
- `id` (Path parameter): Preference ID

**cURL Command**:
```bash
curl -X DELETE http://localhost:8080/api/preferences/507f1f77bcf86cd799439011
```

---

### Status APIs

#### GET /api/status/notification/{id}

**Description**: Get the status of a specific notification.

**Request Type**: `GET`

**Required Args/Val**:
- `id` (Path parameter): Notification ID

**cURL Command**:
```bash
curl -X GET http://localhost:8080/api/status/notification/507f1f77bcf86cd799439011
```

#### GET /api/status/user/{userId}

**Description**: Get notification statistics for a specific user.

**Request Type**: `GET`

**Required Args/Val**:
- `userId` (Path parameter): User ID

**cURL Command**:
```bash
curl -X GET http://localhost:8080/api/status/user/user123
```

#### GET /api/status/system

**Description**: Get system-wide notification status and statistics.

**Request Type**: `GET`

**Required Args/Val**: None

**cURL Command**:
```bash
curl -X GET http://localhost:8080/api/status/system
```

---

### Template APIs

#### GET /api/templates

**Description**: Get all templates with pagination.

**Request Type**: `GET`

**Required Args/Val**: None (pagination is optional)

**Query Parameters** (optional):
- `page`: Page number (default: 0)
- `size`: Page size (default: 20)
- `sort`: Sort field and direction

**cURL Command**:
```bash
curl -X GET "http://localhost:8080/api/templates?page=0&size=10"
```

#### GET /api/templates/{id}

**Description**: Get a template by ID.

**Request Type**: `GET`

**Required Args/Val**:
- `id` (Path parameter): Template ID

**cURL Command**:
```bash
curl -X GET http://localhost:8080/api/templates/507f1f77bcf86cd799439011
```

#### POST /api/templates

**Description**: Create a new notification template.

**Request Type**: `POST`

**Required Args/Val**:
- `name` (String, required): Template name
- `subject` (String, required): Email subject or SMS title
- `body` (String, required): Template body/content
- `type` (Enum, required): Notification type (EMAIL, SMS, PUSH, WEBHOOK, IN_APP)
- `variables` (Map, optional): Template variables
- `isActive` (Boolean, optional): Whether template is active (default: true)

**cURL Command**:
```bash
curl -X POST http://localhost:8080/api/templates \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Order Shipped Template",
    "subject": "Your Order Has Been Shipped",
    "body": "Hello {{userName}}, your order {{orderNumber}} has been shipped. Tracking: {{trackingNumber}}",
    "type": "EMAIL",
    "variables": {
      "userName": "string",
      "orderNumber": "string",
      "trackingNumber": "string"
    },
    "isActive": true
  }'
```

#### PUT /api/templates/{id}

**Description**: Update an existing template.

**Request Type**: `PUT`

**Required Args/Val**:
- `id` (Path parameter): Template ID
- Same body fields as POST (all fields are optional for update)

**cURL Command**:
```bash
curl -X PUT http://localhost:8080/api/templates/507f1f77bcf86cd799439011 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Order Shipped Template",
    "subject": "Your Order Has Been Shipped - Updated",
    "body": "Hello {{userName}}, your order {{orderNumber}} has been shipped successfully!",
    "type": "EMAIL",
    "isActive": true
  }'
```

#### DELETE /api/templates/{id}

**Description**: Delete a template by ID.

**Request Type**: `DELETE`

**Required Args/Val**:
- `id` (Path parameter): Template ID

**cURL Command**:
```bash
curl -X DELETE http://localhost:8080/api/templates/507f1f77bcf86cd799439011
```

#### GET /api/templates/type/{type}

**Description**: Get templates filtered by notification type.

**Request Type**: `GET`

**Required Args/Val**:
- `type` (Path parameter): Notification type (EMAIL, SMS, PUSH, WEBHOOK, IN_APP)

**Query Parameters** (optional):
- `page`: Page number (default: 0)
- `size`: Page size (default: 20)
- `sort`: Sort field and direction

**cURL Command**:
```bash
curl -X GET "http://localhost:8080/api/templates/type/EMAIL?page=0&size=10"
```

---

## Imp to consideration while testing using above curl commands

(Try using Postman with automated variables)

- All timestamps should be in ISO 8601 format (e.g `2025-01-15T10:30:00`)
- Notification types: `EMAIL`, `SMS`, `PUSH`, `WEBHOOK`, `IN_APP`
- Priority levels: `LOW`, `MEDIUM`, `HIGH`, `URGENT`
- Replace placeholder IDs (like `507f1f77bcf86cd799439011`) with actual IDs from db or response 
- Replace `user123` with actual user IDs from your or create one using
- All endpoints support CORS with `*` origin
- Pagination parameters are optional and use Spring Data defaults if not provided

* (Generated By AI for testing only)

