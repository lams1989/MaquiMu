# Árbol de Código Fuente - MaquiMu

## 📂 Estructura General del Proyecto

```
MaquiMu/
├── .luis-metodo/              # Método de desarrollo
├── docs/                      # Documentación del proyecto
├── maquimu-backend/           # Aplicación Backend (Spring Boot)
├── maquimu-frontend/          # Aplicación Frontend (Angular)
├── maquimu-mobile/            # Aplicación Móvil (Android)
└── database/                  # Scripts de base de datos
```

---

## 🗂️ Documentación (`docs/`)

```
docs/
├── architecture/              # Documentación arquitectónica
│   ├── README.md             # Visión general de arquitectura
│   ├── coding-standards.md   # Estándares de código
│   ├── tech-stack.md         # Stack tecnológico
│   └── source-tree.md        # Este archivo
│
├── stories/                   # Historias de Usuario
│   ├── HISTORIAS_DE_USUARIO.md
│   ├── 01.configuracion-inicial-backend.story.md
│   ├── 04.registro-maquinaria.story.md
│   ├── 06.registro-clientes.story.md
│   ├── 07.solicitar-alquiler-cliente.story.md
│   └── 08.consultar-alquileres-cliente.story.md
│
├── diseños/                   # Diseños y mockups
│   ├── Script-BaseDatosMaquiMu.sql
│   └── *.html                # Maquetaciones HTML
│
└── qa/                        # Quality Assurance
    └── gates/                # Gates de calidad por historia
│   │   │   │           ├── ClienteRepositoryPort.java
│   │   │   │           └── AlquilerRepositoryPort.java
│   │   │   │
│   │   │   ├── application/               # 🟢 CAPA DE APLICACIÓN
│   │   │   │   ├── service/              # Implementación de casos de uso
│   │   │   │   │   ├── MaquinariaService.java
│   │   │   │   │   ├── ClienteService.java
│   │   │   │   │   ├── AlquilerService.java
│   │   │   │   │   └── AuthService.java
│   │   │   │   │
│   │   │   │   └── dto/                  # Data Transfer Objects
│   │   │   │       ├── MaquinariaDTO.java
│   │   │   │       ├── ClienteDTO.java
│   │   │   │       ├── AlquilerDTO.java
│   │   │   │       └── LoginDTO.java
│   │   │   │
│   │   │   └── infrastructure/            # 🟡 CAPA DE INFRAESTRUCTURA
│   │   │       ├── adapter/
│   │   │       │   ├── in/               # Adaptadores de entrada
│   │   │       │   │   ├── rest/         # Controllers REST
│   │   │       │   │   │   ├── MaquinariaController.java
│   │   │       │   │   │   ├── ClienteController.java
│   │   │       │   │   │   ├── AlquilerController.java
│   │   │       │   │   │   └── AuthController.java
│   │   │       │   │   │
│   │   │       │   │   └── exception/    # Manejo de excepciones
│   │   │       │   │       ├── GlobalExceptionHandler.java
│   │   │       │   │       └── ErrorResponse.java
│   │   │       │   │
│   │   │       │   └── out/              # Adaptadores de salida
│   │   │       │       ├── persistence/  # JPA
│   │   │       │       │   ├── entity/   # Entidades JPA
│   │   │       │       │   │   ├── MaquinariaEntity.java
│   │   │       │       │   │   ├── ClienteEntity.java
│   │   │       │       │   │   └── AlquilerEntity.java
│   │   │       │       │   │
│   │   │       │       │   ├── repository/ # Repositorios Spring Data
│   │   │       │       │   │   ├── JpaMaquinariaRepository.java
│   │   │       │       │   │   ├── JpaClienteRepository.java
│   │   │       │       │   │   └── JpaAlquilerRepository.java
│   │   │       │       │   │
│   │   │       │       │   └── adapter/  # Implementación de ports
│   │   │       │       │       ├── MaquinariaRepositoryAdapter.java
│   │   │       │       │       └── ClienteRepositoryAdapter.java
│   │   │       │       │
│   │   │       │       └── mapper/       # Mappers Entity <-> Domain
│   │   │       │           └── MaquinariaMapper.java
│   │   │       │
│   │   │       └── config/               # Configuración
│   │   │           ├── SecurityConfig.java
│   │   │           ├── CorsConfig.java
│   │   │           └── JwtUtils.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties    # Configuración de aplicación
│   │       └── application-dev.properties
│   │
│   └── test/                             # Tests
│       └── java/com/maquimu/backend/
│           ├── service/
│           └── controller/
│
├── build.gradle                          # Configuración Gradle
└── settings.gradle
```

---

## 🅰️ Frontend Web (`maquimu-frontend/`)

```
maquimu-frontend/
├── src/
│   ├── app/
│   │   ├── core/                         # Servicios singleton
│   │   │   ├── services/
│   │   │   │   ├── auth.service.ts
│   │   │   │   ├── maquinaria.service.ts
│   │   │   │   ├── cliente.service.ts
│   │   │   │   └── alquiler.service.ts
│   │   │   │
│   │   │   ├── guards/
│   │   │   │   ├── auth.guard.ts
│   │   │   │   └── role.guard.ts
│   │   │   │
│   │   │   └── interceptors/
│   │   │       └── jwt.interceptor.ts
│   │   │
│   │   ├── shared/                       # Componentes compartidos
│   │   │   ├── components/
│   │   │   │   ├── navbar/
│   │   │   │   ├── footer/
│   │   │   │   └── modal/
│   │   │   │
│   │   │   └── models/
│   │   │       ├── maquinaria.model.ts
│   │   │       ├── cliente.model.ts
│   │   │       └── alquiler.model.ts
│   │   │
│   │   ├── features/                     # Módulos de funcionalidad
│   │   │   ├── auth/                    # Autenticación
│   │   │   │   ├── login/
│   │   │   │   └── register/
│   │   │   │
│   │   │   ├── admin/                   # Módulo Administrador
│   │   │   │   ├── inventory/           # Gestión de maquinaria
│   │   │   │   │   ├── inventory.component.ts
│   │   │   │   │   ├── inventory.component.html
│   │   │   │   │   └── machine-modal/
│   │   │   │   │
│   │   │   │   ├── clients/             # Gestión de clientes
│   │   │   │   │   ├── client-list.component.ts
│   │   │   │   │   └── client-modal/
│   │   │   │   │
│   │   │   │   └── dashboard/           # Dashboard empleado
│   │   │   │
│   │   │   └── client/                  # Módulo Cliente
│   │   │       ├── rental/              # Solicitar alquiler
│   │   │       ├── my-rentals/          # Mis alquileres
│   │   │       └── dashboard/           # Dashboard cliente
│   │   │
│   │   ├── app.component.ts             # Componente raíz
│   │   ├── app.routes.ts                # Configuración de rutas
│   │   └── app.config.ts                # Configuración de app
│   │
│   ├── assets/                           # Recursos estáticos
│   │   ├── images/
│   │   └── styles/
│   │
│   ├── styles.css                        # Estilos globales
│   └── index.html                        # HTML principal
│
├── angular.json                          # Configuración Angular
├── package.json                          # Dependencias npm
└── tsconfig.json                         # Configuración TypeScript
```

---

## 📱 Frontend Móvil (`maquimu-mobile/`)

```
maquimu-mobile/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/maquimu/mobile/
│   │       │   ├── ui/                   # Capa de presentación
│   │       │   │   ├── auth/
│   │       │   │   ├── rental/
│   │       │   │   └── dashboard/
│   │       │   │
│   │       │   ├── data/                 # Capa de datos
│   │       │   │   ├── repository/
│   │       │   │   ├── api/
│   │       │   │   └── model/
│   │       │   │
│   │       │   └── di/                   # Inyección de dependencias
│   │       │
│   │       └── res/                      # Recursos Android
│   │           ├── layout/
│   │           ├── values/
│   │           └── drawable/
│   │
│   └── build.gradle
│
└── settings.gradle
```

---

## 🗄️ Base de Datos (`database/`)

```
database/
├── migrations/                           # Migraciones (Flyway)
│   ├── V1__create_initial_schema.sql
│   └── V2__add_facturas_table.sql
│
└── seeds/                                # Datos de prueba
    └── test_data.sql
```

---

## 📝 Convenciones de Nombres

### Backend (Java)
- **Paquetes**: `com.maquimu.backend.{capa}.{modulo}`
- **Clases**: `PascalCase` (ej. `MaquinariaService`)
- **Interfaces**: `PascalCase` con sufijo según contexto (ej. `MaquinariaRepositoryPort`)

### Frontend (Angular)
- **Componentes**: `kebab-case` (ej. `inventory-list.component.ts`)
- **Servicios**: `kebab-case.service.ts` (ej. `maquinaria.service.ts`)
- **Modelos**: `kebab-case.model.ts` (ej. `maquinaria.model.ts`)

### Base de Datos
- **Tablas**: `snake_case` (ej. `maquinaria`, `alquileres`)
- **Columnas**: `snake_case` (ej. `nombre_cliente`, `tarifa_dia`)

---

**Última actualización:** 2025-11-22
