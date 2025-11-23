# Stack Tecnológico - MaquiMu

## 🎯 Visión General

MaquiMu es una aplicación **Full-Stack** para gestión de alquiler de maquinaria, desarrollada con tecnologías modernas y escalables.

---

## 🏗️ Arquitectura General

```
┌─────────────────────────────────────────────────┐
│           Clientes (Web + Móvil)                │
├─────────────────────────────────────────────────┤
│  Angular 17        │      Android (Kotlin)      │
└─────────────────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────┐
│              API REST (Backend)                  │
│           Spring Boot 3 + Java 17                │
└─────────────────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────┐
│            Base de Datos MySQL                   │
└─────────────────────────────────────────────────┘
```

---

## 💻 Backend

### Core Framework
- **Java 17** - Lenguaje de programación
- **Spring Boot 3.x** - Framework principal
- **Gradle** - Herramienta de construcción

### Dependencias Principales

#### Spring Starters
```gradle
dependencies {
    // Web & REST
    implementation 'org.springframework.boot:spring-boot-starter-web'
    
    // Persistencia
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    
    // Seguridad
    implementation 'org.springframework.boot:spring-boot-starter-security'
    
    // Validación
    implementation 'org.springframework.boot:spring-boot-starter-validation'
}
```

#### Base de Datos
```gradle
// Driver MySQL
runtimeOnly 'com.mysql:mysql-connector-j'

// Migraciones (opcional)
implementation 'org.flywaydb:flyway-core'
```

#### Seguridad & JWT
```gradle
// JWT
implementation 'io.jsonwebtoken:jjwt-api:0.11.5'
runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.11.5'
runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.11.5'
```

#### Utilidades
```gradle
// Lombok (reducir boilerplate)
compileOnly 'org.projectlombok:lombok'
annotationProcessor 'org.projectlombok:lombok'

// MapStruct (mapeo DTO-Entity)
implementation 'org.mapstruct:mapstruct:1.5.5.Final'
annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.5.Final'
```

#### Testing
```gradle
testImplementation 'org.springframework.boot:spring-boot-starter-test'
testImplementation 'org.springframework.security:spring-security-test'
```

### Arquitectura Backend

**Patrón:** Arquitectura Hexagonal (Puertos y Adaptadores)

```
backend/
├── domain/              # Núcleo de negocio
│   ├── model/          # Entidades de dominio
│   └── port/
│       ├── in/         # Casos de uso (interfaces)
│       └── out/        # Repositorios (interfaces)
├── application/         # Lógica de aplicación
│   ├── service/        # Implementación de casos de uso
│   └── dto/            # Data Transfer Objects
└── infrastructure/      # Adaptadores externos
    ├── adapter/
    │   ├── in/         # REST Controllers
    │   └── out/        # JPA Repositories
    └── config/         # Configuración Spring
```

---

## 🌐 Frontend Web

### Core Framework
- **Angular 17** - Framework SPA
- **TypeScript 5.x** - Lenguaje de programación
- **RxJS 7.x** - Programación reactiva

### Dependencias Principales

```json
{
  "dependencies": {
    "@angular/core": "^17.0.0",
    "@angular/common": "^17.0.0",
    "@angular/router": "^17.0.0",
    "@angular/forms": "^17.0.0",
    "rxjs": "^7.8.0",
    "bootstrap": "^5.3.0"
  }
}
```

### Estructura Frontend

```
src/
├── app/
│   ├── core/              # Servicios singleton
│   │   ├── services/      # HTTP, Auth, etc.
│   │   ├── guards/        # Route guards
│   │   └── interceptors/  # HTTP interceptors
│   ├── shared/            # Componentes reutilizables
│   │   ├── components/
│   │   └── models/
│   ├── features/          # Módulos de funcionalidad
│   │   ├── admin/         # Gestión (empleados)
│   │   └── client/        # Portal clientes
│   └── app.routes.ts      # Configuración de rutas
└── assets/                # Recursos estáticos
```

### UI Framework
- **Bootstrap 5.3** - Framework CSS
- **Bootstrap Icons** - Iconografía

---

## 📱 Frontend Móvil

### Core Framework
- **Android Studio** - IDE
- **Kotlin** - Lenguaje de programación
- **Jetpack Compose** (opcional) - UI moderna
- **Retrofit** - Cliente HTTP

### Arquitectura Móvil
- **MVVM** (Model-View-ViewModel)
- **LiveData** / **Flow** para observables
- **Room** (opcional) - Persistencia local

---

## 🗄️ Base de Datos

### Motor
- **MySQL 8.0** - Sistema de gestión de base de datos

### Configuración Local
```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/maquimu_db
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

### Esquema Principal

**Tablas:**
- `usuarios` - Empleados del sistema
- `clientes` - Clientes que alquilan
- `maquinaria` - Inventario de maquinaria
- `alquileres` - Contratos de alquiler
- `facturas` - Facturación

---

## 🔐 Seguridad

### Autenticación
- **JWT (JSON Web Tokens)** - Autenticación stateless
- **Spring Security** - Framework de seguridad

### Autorización
- **Roles**: `ADMIN`, `OPERARIO`, `CLIENTE`
- **Permisos** basados en roles

---

## 🛠️ Herramientas de Desarrollo

### IDEs Recomendados
- **IntelliJ IDEA** - Backend (Java/Spring)
- **Visual Studio Code** - Frontend (Angular)
- **Android Studio** - Móvil (Kotlin)

### Control de Versiones
- **Git** - Sistema de control de versiones
- **GitHub** / **GitLab** - Repositorio remoto

### Testing
- **JUnit 5** - Tests unitarios (Backend)
- **Mockito** - Mocking (Backend)
- **Jasmine/Karma** - Tests unitarios (Frontend)
- **Postman** - Testing de API

### Documentación API
- **Swagger / OpenAPI** - Documentación interactiva de API

```gradle
// Swagger
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0'
```

---

## 🚀 Despliegue

### Desarrollo Local
- **Backend**: `./gradlew bootRun` (puerto 8080)
- **Frontend**: `ng serve` (puerto 4200)
- **MySQL**: Docker o instalación local (puerto 3306)

### Producción (Futuro)
- **Backend**: JAR ejecutable en servidor
- **Frontend**: Build estático en Nginx/Apache
- **Base de Datos**: MySQL en servidor dedicado

---

## 📦 Gestión de Dependencias

### Backend (Gradle)
```bash
./gradlew dependencies  # Ver árbol de dependencias
./gradlew build         # Compilar proyecto
```

### Frontend (npm)
```bash
npm install            # Instalar dependencias
npm run build          # Build de producción
```

---

## 🔄 Versionamiento

- **Backend**: Semantic Versioning (SemVer)
- **Frontend**: Semantic Versioning (SemVer)
- **API**: Versionado en URL (`/api/v1/...`)

---

**Última actualización:** 2025-11-22
