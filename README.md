# MaquiMu - Sistema de Gestión y Alquiler de Maquinaria Pesada

## 📋 Descripción del Proyecto

**MaquiMu** es un sistema integral de software para la gestión administrativa y operativa del alquiler de maquinaria pesada. El sistema optimiza los procesos de inventario, alquileres, clientes y facturación mediante una arquitectura moderna y escalable.

## 🏗️ Arquitectura

El proyecto utiliza **Arquitectura Hexagonal (Puertos y Adaptadores)** para garantizar la separación de responsabilidades y facilitar el mantenimiento y evolución del sistema.

## 📦 Estructura del Monorepo

```
MaquiMu/
├── .luis-metodo/          # Metodología de desarrollo
├── docs/                  # Documentación del proyecto
│   ├── architecture/      # Documentación arquitectónica
│   ├── stories/           # Historias de usuario
│   ├── diseños/           # Diseños y maquetaciones
│   └── qa/                # Quality Assurance
├── maquimu-backend/       # API REST (Spring Boot + Java 17)
├── maquimu-frontend/      # Aplicación Web (Angular 17)
├── maquimu-mobile/        # Aplicación Móvil (Android Kotlin)
└── database/              # Scripts de base de datos
```

## 🚀 Módulos del Sistema

### Backend - API REST
- **Tecnología:** Java 17 + Spring Boot 3
- **Arquitectura:** Hexagonal (Puertos y Adaptadores)
- **Base de Datos:** MySQL 8.0
- **Seguridad:** JWT + Spring Security
- **Documentación:** Swagger/OpenAPI
- [Ver README del Backend](./maquimu-backend/README.md)

### Frontend Web
- **Tecnología:** Angular 17 + TypeScript
- **UI Framework:** Bootstrap 5.3
- **Características:** SPA con routing, guards, interceptors
- **Módulos:** Gestión Interna + Portal de Clientes
- [Ver README del Frontend](./maquimu-frontend/README.md)

### Mobile - Android
- **Tecnología:** Android + Kotlin
- **Arquitectura:** MVVM
- **Cliente HTTP:** Retrofit
- **Enfoque:** Portal de autoservicio para clientes
- [Ver README de Mobile](./maquimu-mobile/README.md)

### Base de Datos
- **Motor:** MySQL 8.0
- **Migraciones:** Flyway
- **Esquema:** Usuarios, Clientes, Maquinaria, Alquileres, Facturas
- [Ver README de Database](./database/README.md)

## 🛠️ Stack Tecnológico

| Componente | Tecnología |
|------------|------------|
| **Backend** | Java 17, Spring Boot 3, Gradle |
| **Frontend Web** | Angular 17, TypeScript, Bootstrap 5 |
| **Mobile** | Android, Kotlin, Jetpack |
| **Base de Datos** | MySQL 8.0 |
| **Seguridad** | JWT, Spring Security, OAuth 2.0 |
| **Migraciones** | Flyway |
| **Documentación API** | Swagger/OpenAPI |
| **Testing** | JUnit 5, Mockito, Jasmine/Karma |

## 📚 Documentación

La documentación completa del proyecto se encuentra en la carpeta [`docs/`](./docs/):

- **[Arquitectura](./docs/architecture/)** - Documentación técnica y arquitectónica
  - [README.md](./docs/architecture/README.md) - Visión general
  - [ARQUITECTURA.md](./docs/architecture/ARQUITECTURA.md) - Detalles arquitectónicos
  - [tech-stack.md](./docs/architecture/tech-stack.md) - Stack tecnológico
  - [source-tree.md](./docs/architecture/source-tree.md) - Estructura del código
  - [coding-standards.md](./docs/architecture/coding-standards.md) - Estándares de código

- **[Historias de Usuario](./docs/stories/)** - Requisitos funcionales
- **[Diseños](./docs/diseños/)** - Mockups y prototipos
- **[QA](./docs/qa/)** - Quality Assurance y gates de calidad

## 🚦 Inicio Rápido

### Prerrequisitos

- **Java 17** o superior
- **Node.js 18+** y npm
- **MySQL 8.0**
- **Android Studio** (para desarrollo móvil)
- **Git**

### Configuración Inicial

1. **Clonar el repositorio**
   ```bash
   git clone <repository-url>
   cd MaquiMu
   ```

2. **Configurar Base de Datos**
   ```bash
   # Crear base de datos
   mysql -u root -p
   CREATE DATABASE maquimu_db;
   ```
   Ver más detalles en [database/README.md](./database/README.md)

3. **Iniciar Backend**
   ```bash
   cd maquimu-backend
   ./gradlew bootRun
   ```
   El backend estará disponible en `http://localhost:8080/api`

4. **Iniciar Frontend** (pendiente de configuración)
   ```bash
   cd maquimu-frontend
   npm install
   ng serve
   ```
   El frontend estará disponible en `http://localhost:4200`

5. **Configurar Mobile** (pendiente de configuración)
   - Abrir `maquimu-mobile` en Android Studio
   - Sincronizar Gradle
   - Ejecutar en emulador o dispositivo

## 🔐 Seguridad

- Autenticación mediante **JWT (JSON Web Tokens)**
- Autorización basada en **roles** (ADMIN, OPERARIO, CLIENTE)
- Comunicación segura mediante **HTTPS**
- Configuración de **CORS** para frontend

## 🧪 Testing

Cada módulo incluye su propia suite de tests:

- **Backend:** JUnit 5 + Mockito
- **Frontend:** Jasmine + Karma
- **Mobile:** JUnit + Espresso

## 📝 Metodología de Desarrollo

Este proyecto utiliza el **Método Luis**, una metodología estructurada de desarrollo que incluye:

- Gestión de historias de usuario
- Estándares de código
- Gates de calidad (QA)
- Documentación arquitectónica

Ver más en [`.luis-metodo/`](./.luis-metodo/)

## 🤝 Contribución

1. Revisar [coding-standards.md](./docs/architecture/coding-standards.md)
2. Crear una rama desde `develop`
3. Implementar cambios siguiendo los estándares
4. Ejecutar tests
5. Crear Pull Request

## 📄 Licencia

[Especificar licencia del proyecto]

## 👥 Equipo

[Información del equipo de desarrollo]

---

**Última actualización:** 2025-01-05
