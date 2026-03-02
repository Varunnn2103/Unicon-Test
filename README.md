# 🐛 Issue Tracker — Unico Connect

A full-stack issue tracking application built with **Spring Boot** and **React**, featuring real-time updates via WebSocket, Kanban board, analytics dashboard, and Swagger API documentation.

---

## 📸 Screenshots

### Dashboard
> Displays all issues with status cards, filters, search, and CSV export.

### Kanban Board
> Drag-and-drop interface to move issues across status columns.

### Analytics
> Visual breakdown of issues by status, priority, project, assignee, and a priority × project heatmap.

### Swagger UI
> Interactive API testing at `/swagger-ui/index.html`

---

## 🏗 Tech Stack

| Layer | Technology |
|-------|-----------|
| **Backend** | Spring Boot 3.2.3, Java 17 |
| **Database** | PostgreSQL |
| **ORM** | Spring Data JPA / Hibernate |
| **API Docs** | springdoc-openapi 2.3.0 (Swagger UI) |
| **WebSocket** | Spring STOMP |
| **Frontend** | React 18 + Vite |
| **Styling** | Tailwind CSS |
| **HTTP Client** | Axios |

---

## 📁 Project Structure

```
issue-tracker/
├── issue-tracker-backend/          # Spring Boot REST API
│   ├── src/main/java/com/unicoconnect/issuetracker/
│   │   ├── config/                 # CORS, WebSocket, OpenAPI configs
│   │   ├── controller/             # IssueController, CommentController
│   │   ├── dto/                    # Request/Response DTOs
│   │   ├── exception/              # Global error handler
│   │   ├── model/                  # JPA entities (Issue, Comment)
│   │   ├── repository/             # JPA repositories + specifications
│   │   └── service/                # Business logic
│   └── src/main/resources/
│       ├── application.properties  # DB config, server port
│       └── data.sql                # Seed data (14 issues)
│
└── issue-tracker-frontend/         # React + Vite SPA
    └── src/
        ├── components/             # Reusable UI components
        ├── data/                   # Constants, seed data
        ├── pages/                  # Dashboard, Kanban, Charts, CreateIssue, IssueDetail
        └── services/               # Axios API client
```

---

## 🚀 Getting Started

### Prerequisites
- **Java 17+** (JDK)
- **Node.js 18+** & npm
- **PostgreSQL 14+**

### 1. Database Setup

```sql
CREATE DATABASE issue_tracker;
```

### 2. Backend Setup

```bash
cd issue-tracker-backend

# Configure database connection
# Edit src/main/resources/application.properties:
#   spring.datasource.url=jdbc:postgresql://localhost:5432/issue_tracker
#   spring.datasource.username=postgres
#   spring.datasource.password=YOUR_PASSWORD

# Run the backend
./mvnw spring-boot:run        # Linux/Mac
mvnw.cmd spring-boot:run      # Windows
```

The backend starts on **http://localhost:8080**.

### 3. Frontend Setup

```bash
cd issue-tracker-frontend

npm install
npm run dev
```

The frontend starts on **http://localhost:3000**.

---

## 📡 API Endpoints

### Issues

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/issues` | List all issues (supports filtering) |
| `GET` | `/api/issues/{id}` | Get issue by ID |
| `POST` | `/api/issues` | Create a new issue |
| `PATCH` | `/api/issues/{id}/status` | Update issue status |
| `DELETE` | `/api/issues/{id}` | Delete an issue |
| `GET` | `/api/issues/export` | Export all issues as CSV |

#### Query Parameters for Filtering
| Param | Example | Description |
|-------|---------|-------------|
| `project` | `?project=Alpha` | Filter by project name |
| `priority` | `?priority=CRITICAL` | `LOW`, `MEDIUM`, `HIGH`, `CRITICAL` |
| `status` | `?status=OPEN` | `OPEN`, `IN_PROGRESS`, `RESOLVED`, `CLOSED` |
| `assignee` | `?assignee=Alice` | Filter by assignee |
| `search` | `?search=login` | Search in title & description |

### Comments

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/issues/{issueId}/comments` | Get comments for an issue |
| `POST` | `/api/issues/{issueId}/comments` | Add a comment |

#### Example: Create Issue
```bash
curl -X POST http://localhost:8080/api/issues \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Login page crashes on invalid email",
    "description": "Server returns 500 instead of 422",
    "project": "Alpha",
    "priority": "CRITICAL",
    "status": "OPEN",
    "assignee": "Alice"
  }'
```

#### Example: Add Comment
```bash
curl -X POST http://localhost:8080/api/issues/1/comments \
  -H "Content-Type: application/json" \
  -d '{"text": "Reproduced on Chrome 121. Investigating."}'
```

---

## 📖 Swagger UI

Interactive API documentation is available at:

```
http://localhost:8080/swagger-ui/index.html
```

OpenAPI 3.0 JSON spec:
```
http://localhost:8080/v3/api-docs
```

---

## 🎨 Frontend Features

| Feature | Description |
|---------|-------------|
| **Dashboard** | Issue list with status summary cards, search, filters, CSV export |
| **Kanban Board** | Drag-and-drop to change issue status |
| **Analytics** | Donut chart, priority bars, project health, heatmap, team workload |
| **Create Issue** | Form with validation (title, description, project, priority, assignee) |
| **Issue Detail** | Full view with status update, comments, and delete |
| **Dark Mode** | Toggle via navbar |
| **Real-time** | WebSocket "Live" indicator for real-time updates |
| **Toast Notifications** | Feedback on all operations |

---

## 🗄 Data Model

### Issue
| Field | Type | Constraints |
|-------|------|------------|
| `id` | Long | Auto-generated |
| `title` | String | Required, max 255 chars |
| `description` | String | Required |
| `project` | String | Required |
| `priority` | Enum | `LOW`, `MEDIUM`, `HIGH`, `CRITICAL` |
| `status` | Enum | `OPEN`, `IN_PROGRESS`, `RESOLVED`, `CLOSED` |
| `assignee` | String | Required |
| `createdAt` | Timestamp | Auto-generated |
| `updatedAt` | Timestamp | Auto-updated |

### Comment
| Field | Type | Constraints |
|-------|------|------------|
| `id` | Long | Auto-generated |
| `text` | String | Required |
| `issueId` | Long | FK → Issue |
| `createdAt` | Timestamp | Auto-generated |

---

## ⚙️ Configuration

### application.properties
```properties
server.port=8080
spring.datasource.url=jdbc:postgresql://localhost:5432/issue_tracker
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.sql.init.mode=always
```

### CORS
Allowed origins: `http://localhost:5173`, `http://localhost:3000`  
Configured in `CorsConfig.java` and controller `@CrossOrigin` annotations.

---

## 🧪 Testing

All 12 API endpoints have been tested and verified:
- ✅ CRUD operations for issues
- ✅ Filtering by project, priority, status, assignee
- ✅ Full-text search
- ✅ CSV export
- ✅ Comment creation and retrieval
- ✅ Proper error handling (404, 422)
- ✅ CORS preflight verification
- ✅ Frontend integration (all pages use real API)

---

## 📋 Seed Data

The database is seeded with 14 sample issues via `data.sql` on startup. Uses `ON CONFLICT (title) DO NOTHING` to prevent duplicates on restart.

**Projects:** Alpha, Beta, Gamma, Delta  
**Assignees:** Alice, Bob, Carol, David, Eve  

---

## 📄 License

This project is part of the Unico Connect AI Full Stack Intern Assessment.
