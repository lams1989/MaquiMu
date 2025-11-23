# MaquiMu Backend - API REST

## 📋 Descripción

Backend del sistema MaquiMu desarrollado con **Spring Boot 3** y **Java 17**, siguiendo el patrón de **Arquitectura Hexagonal (Puertos y Adaptadores)** para garantizar la separación de responsabilidades y facilitar el mantenimiento.

## 🏗️ Arquitectura Hexagonal

```
backend/
├── domain/              # 🔵 NÚCLEO DE NEGOCIO
│   ├── model/          # Entidades de dominio
│   └── port/
│       ├── in/         # Casos de uso (interfaces)
│       └── out/        # Repositorios (interfaces)
├── application/         # 🟢 LÓGICA DE APLICACIÓN
│   ├── service/        # Implementación de casos de uso
│   └── dto/            # Data Transfer Objects
└── infrastructure/      # 🟡 ADAPTADORES EXTERNOS
    ├── adapter/
    │   ├── in/         # REST Controllers
    │   └── out/        # JPA Repositories
    └── config/         # Configuración Spring
```

### Capas

- **Dominio:** Lógica de negocio pura, independiente de frameworks
- **Aplicación:** Orquestación de casos de uso
- **Infraestructura:** Adaptadores para tecnologías externas (REST, JPA, etc.)

## 🚀 Tecnologías

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Spring Security + JWT**
- **MySQL 8.0**
- **Flyway** (migraciones)
- **Lombok** (reducir boilerplate)
- **MapStruct** (mapeo DTO-Entity)
- **Swagger/OpenAPI** (documentación API)
- **Gradle** (build tool)

## 📦 Dependencias Principales

Ver [`build.gradle`](./build.gradle) para la lista completa de dependencias.

## ⚙️ Configuración

### Prerrequisitos

- Java 17 o superior
- MySQL 8.0
- Gradle (incluido con wrapper)

### Variables de Entorno

Configurar en `src/main/resources/application.properties`:

```properties
# Base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/maquimu_db
spring.datasource.username=root
spring.datasource.password=

# JWT
jwt.secret=tu-clave-secreta
jwt.expiration=86400000
```

### Base de Datos

1. Crear base de datos:
   ```sql
   CREATE DATABASE maquimu_db;
   ```

2. Las migraciones de Flyway se ejecutarán automáticamente al iniciar la aplicación.

## 🏃 Ejecutar la Aplicación

### Modo Desarrollo

```bash
# Con Gradle Wrapper
./gradlew bootRun

# Con perfil de desarrollo
./gradlew bootRun --args='--spring.profiles.active=dev'
```

La aplicación estará disponible en: `http://localhost:8080/api`

### Compilar

```bash
./gradlew build
```

El JAR se generará en: `build/libs/maquimu-backend-0.0.1-SNAPSHOT.jar`

### Ejecutar JAR

```bash
java -jar build/libs/maquimu-backend-0.0.1-SNAPSHOT.jar
```

## 📚 Documentación API

Una vez iniciada la aplicación, la documentación Swagger estará disponible en:

- **Swagger UI:** http://localhost:8080/api/swagger-ui.html
- **API Docs (JSON):** http://localhost:8080/api/api-docs

## 🧪 Testing

### Ejecutar Tests

```bash
# Todos los tests
./gradlew test

# Tests con reporte
./gradlew test --info
```

### Cobertura

```bash
./gradlew jacocoTestReport
```

El reporte se generará en: `build/reports/jacoco/test/html/index.html`

## 🔐 Seguridad

### Autenticación

- **JWT (JSON Web Tokens)** para autenticación stateless
- Tokens con expiración configurable
- Refresh tokens (pendiente de implementación)

### Autorización

Roles disponibles:
- `ADMIN` - Acceso completo al sistema
- `OPERARIO` - Gestión de operaciones
- `CLIENTE` - Acceso limitado a portal de clientes

### Endpoints Públicos

- `POST /api/auth/login` - Iniciar sesión
- `POST /api/auth/register` - Registro de clientes

## 📁 Estructura de Paquetes

```
com.maquimu.backend/
├── domain/
│   ├── model/
│   │   ├── Maquinaria.java
│   │   ├── Cliente.java
│   │   ├── Alquiler.java
│   │   ├── Usuario.java
│   │   └── Factura.java
│   └── port/
│       ├── in/
│       │   ├── MaquinariaUseCase.java
│       │   ├── ClienteUseCase.java
│       │   └── AlquilerUseCase.java
│       └── out/
│           ├── MaquinariaRepositoryPort.java
│           ├── ClienteRepositoryPort.java
│           └── AlquilerRepositoryPort.java
├── application/
│   ├── service/
│   │   ├── MaquinariaService.java
│   │   ├── ClienteService.java
│   │   ├── AlquilerService.java
│   │   └── AuthService.java
│   └── dto/
│       ├── MaquinariaDTO.java
│       ├── ClienteDTO.java
│       └── AlquilerDTO.java
└── infrastructure/
    ├── adapter/
    │   ├── in/
    │   │   └── rest/
    │   │       ├── MaquinariaController.java
    │   │       ├── ClienteController.java
    │   │       ├── AlquilerController.java
    │   │       └── AuthController.java
    │   └── out/
    │       └── persistence/
    │           ├── entity/
    │           ├── repository/
    │           └── adapter/
    └── config/
        ├── SecurityConfig.java
        ├── CorsConfig.java
        └── JwtUtils.java
```

## 🔧 Convenciones de Código

- **Nomenclatura:** PascalCase para clases, camelCase para métodos
- **Paquetes:** Organización por capas (domain, application, infrastructure)
- **DTOs:** Sufijo `DTO` para objetos de transferencia
- **Entities:** Sufijo `Entity` para entidades JPA
- **Ports:** Sufijo `Port` para interfaces de puertos

Ver [coding-standards.md](../docs/architecture/coding-standards.md) para más detalles.

## 🐛 Debugging

### Logs

Los logs se configuran en `application.properties`:

```properties
logging.level.com.maquimu.backend=DEBUG
logging.level.org.springframework.web=DEBUG
```

### Perfiles

- **default:** Producción
- **dev:** Desarrollo (logs más verbosos)

Activar perfil:
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

## 📝 Notas de Desarrollo

- Las migraciones de base de datos están en `src/main/resources/db/migration/`
- Usar Lombok para reducir boilerplate (`@Data`, `@Builder`, etc.)
- MapStruct para mapeo automático entre DTOs y Entities
- Validaciones con Bean Validation (`@Valid`, `@NotNull`, etc.)

## 🚧 Pendientes

- [ ] Implementar refresh tokens
- [ ] Agregar tests de integración
- [ ] Configurar CI/CD
- [ ] Dockerizar aplicación
- [ ] Implementar rate limiting
- [ ] Agregar métricas con Actuator

## 📞 Soporte

Para dudas o problemas, consultar:
- [Documentación del proyecto](../docs/)
- [Historias de usuario](../docs/stories/)
- [Arquitectura](../docs/architecture/)

---

**Última actualización:** 2025-11-22
