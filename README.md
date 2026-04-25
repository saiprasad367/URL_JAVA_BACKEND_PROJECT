<div align="center">

# 🚀 Shortify - Powerful URL Shortener (Spring Boot Edition)

<img src="https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB" alt="React" />
<img src="https://img.shields.io/badge/TypeScript-007ACC?style=for-the-badge&logo=typescript&logoColor=white" alt="TypeScript" />
<img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" />
<img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot" />
<img src="https://img.shields.io/badge/H2_Database-004B8D?style=for-the-badge&logo=h2&logoColor=white" alt="H2 Database" />
<img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white" alt="Maven" />
<img src="https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=vite&logoColor=white" alt="Vite" />
<img src="https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white" alt="Tailwind CSS" />

### *Transform long URLs into powerful, trackable short links with enterprise-grade performance* ⚡

[Features](#-features) • [Tech Stack](#-tech-stack) • [Quick Start](#-quick-start) • [API Documentation](#-api-documentation) • [Architecture](#-architecture)

---

</div>

## 📖 Overview

**Shortify** is a modern, full-stack URL shortening platform engineered for performance and scalability. This version features a robust **Java Spring Boot** backend, replacing the original Node.js implementation to provide enterprise-grade stability and JPA-powered data persistence (currently using an H2 in-memory database for rapid MVP deployment).

### ✨ Key Capabilities

- 🔗 **Intelligent URL Shortening** - Generate compact short URLs using a custom Base62 encoding algorithm
- 📊 **Real-time Analytics** - Track clicks, creation dates, and expiry status for every link
- 🛡️ **Spring Security & CORS** - Pre-configured secure resource sharing for frontend integration
- 🎨 **Beautiful UI/UX** - Modern, responsive interface built with React and Tailwind CSS
- 🔄 **RESTful API** - Clean, Java-powered API endpoints for all URL management tasks

---

## 🛠️ Tech Stack

### **Frontend**
| Technology | Purpose |
|------------|---------|
| **React 18** | Modern UI library with functional components |
| **TypeScript** | Type-safe development for complex UI logic |
| **Vite** | Next-generation frontend tooling |
| **Tailwind CSS** | Utility-first CSS framework for premium aesthetics |
| **Shadcn/UI** | High-end component library for professional design |

### **Backend (Newly Migrated)**
| Technology | Purpose |
|------------|---------|
| **Java 17/22** | Robust, type-safe programming language |
| **Spring Boot 3.2.3** | Leading Java framework for microservices and REST APIs |
| **Spring Data JPA** | Powerful data access layer with repository patterns |
| **H2 Database** | Fast, in-memory relational database for MVP speed |
| **Maven** | Reliable dependency management and build automation |

---

## 🎯 Features

### Core Functionality

#### 1️⃣ **URL Shortening Engine**
- **Base62 Encoding**: Custom algorithm to transform numeric IDs into short `[0-9a-zA-Z]` strings.
- **Custom Alias Support**: Create branded links (e.g., `shortify.com/my-link`).
- **Expiration Management**: Automatic link invalidation based on user-defined expiry dates.

#### 2️⃣ **Analytics Dashboard**
- **Click Tracking**: Every redirection is logged and counted.
- **Last Accessed Monitoring**: Know exactly when your link was last used.
- **Link Management**: Delete, list, and view detailed stats for all generated links.

---

## 🚀 Quick Start

### **Prerequisites**
- **Java JDK 17+**
- **Node.js & npm**
- **Git**

### **Step 1: Clone & Navigate**
```bash
git clone https://github.com/saiprasad367/URL_JAVA_BACKEND_PROJECT.git
cd URL_JAVA_BACKEND_PROJECT
```

### **Step 2: Backend Setup**
```bash
cd backend
# Build and run using Maven Wrapper
./mvnw.cmd spring-boot:run
```
*Backend will run on `http://localhost:8081`*

### **Step 3: Frontend Setup**
```bash
cd ../shortify-your-link-amplified-main
npm install
npm run dev
```
*Frontend will run on `http://localhost:8082` (or next available port)*

---

## 📡 API Documentation

### **Base URL**
`http://localhost:8081`

### **Endpoints**
- `POST /api/v1/shorten` - Create a short URL
- `GET /api/v1/urls` - List all URLs
- `GET /api/v1/analytics/{shortCode}` - Get detailed stats
- `DELETE /api/v1/{shortCode}` - Remove a link
- `GET /{shortCode}` - Redirect to the long URL

---

## 👨‍💻 Author

### **Saiprasad**
**Full-Stack Developer | Spring Boot Enthusiast**

[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/saiprasad367)
