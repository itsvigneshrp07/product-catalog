
# Product Catalog API

A Spring Boot 3 (Java 17) application implementing a Product Catalog with an in-memory Category Cache.

## 🚀 Tech Stack
- Java 17
- Spring Boot 3.3.x
- Spring Data JPA (Hibernate)
- H2 in-memory database
- Gradle build system

---

## 🛠️ Build & Run

### Using Gradle Wrapper
```bash
# Clean and build
./gradlew clean build

# Run the application
./gradlew bootRun
```

### Or Run from IntelliJ IDEA
- Open the project as a Gradle project.
- Run the main class: `com.example.catalog.CatalogApplication`.

The app will start at: `http://localhost:8080`.

### H2 Database Console
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:catalogdb`
- User: `sa`
- Password: (leave empty)

---

## 📌 API Examples

### 1. Create Category
**POST** `/api/categories`
```json
{
  "name": "Electronics"
}
```
Response (201 Created):
```json
{
  "id": 1,
  "name": "Electronics"
}
```

### 2. Create Product under Category
**POST** `/api/categories/1/products`
```json
{
  "name": "USB Cable",
  "price": 199.99
}
```
Response (201 Created):
```json
{
  "id": 1,
  "name": "USB Cable",
  "price": 199.99,
  "categoryId": 1
}
```

### 3. Get All Products in Category
**GET** `/api/categories/1/products`

Response (200 OK):
```json
[
  {
    "id": 1,
    "name": "USB Cable",
    "price": 199.99,
    "categoryId": 1
  }
]
```

### 4. Get Product by ID
**GET** `/api/products/1`

Response (200 OK):
```json
{
  "id": 1,
  "name": "USB Cable",
  "price": 199.99,
  "categoryId": 1
}
```

### 5. Update Product Price
**PATCH** `/api/products/1/price`
```json
{
  "price": 249.99
}
```
Response (200 OK):
```json
{
  "id": 1,
  "name": "USB Cable",
  "price": 249.99,
  "categoryId": 1
}
```

### 6. Delete Product
**DELETE** `/api/products/1`

Response: (204 No Content)

### 7. Update Category Name
**PUT** `/api/categories/1`
```json
{
  "name": "Gadgets"
}
```
Response (200 OK):
```json
{
  "id": 1,
  "name": "Gadgets"
}
```

---

## 🧪 Tests

Run unit tests with:
```bash
./gradlew test
```

Tests include validation of Category Cache (first DB hit vs subsequent cache hits, and cache refresh on update).
