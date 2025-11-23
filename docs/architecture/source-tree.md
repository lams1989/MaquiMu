# Árbol de Código Fuente - MaquiMu

## 📂 Estructura General del Proyecto

```
MaquiMu/
├── .luis-metodo/              # Método de desarrollo
├── docs/                      # Documentación del proyecto
├── maquimu-backend/ (Root Project)
├── build.gradle                          # Configuración Root
├── settings.gradle                       # Definición de módulos
├── src/
│   └── main/
│       ├── java/com/maquimu/
│       │   └── MaquimuBackendApplication.java # Main Class
│       └── resources/
│           ├── application.yml           # Configuración Global
│           ├── application-local.yaml    # Configuración Local (DB)
│           └── db/migration/             # Flyway
│               ├── DDL/                  # Scripts de Estructura
│               └── DML/                  # Scripts de Datos
│
├── dominio/ (Module)                     # 🟢 CAPA DE DOMINIO (Java Puro)
│   ├── build.gradle
│   └── src/main/java/com/maquimu/dominio/
│       ├── modelo/                       # Entidades de Dominio
│       │   ├── Maquinaria.java
│       │   ├── Cliente.java
│       │   └── Alquiler.java
│       ├── puerto/                       # Interfaces (Puertos)
│       │   ├── dao/                      # Puertos de Lectura
│       │   │   ├── MaquinariaDao.java
│       │   │   └── ClienteDao.java
│       │   └── repositorio/              # Puertos de Escritura
│       │       ├── MaquinariaRepositorio.java
│       │       └── ClienteRepositorio.java
│       └── servicio/                     # Lógica de Negocio
│           └── ValidadorAlquiler.java
│
├── aplicacion/ (Module)                  # 🟡 CAPA DE APLICACIÓN (Orquestación)
│   ├── build.gradle
│   └── src/main/java/com/maquimu/aplicacion/
│       ├── comando/                      # CQRS: Comandos (Escritura)
│       │   ├── fabrica/                  # Factories
│       │   │   └── FabricaMaquinaria.java
│       │   └── manejador/                # Handlers
│       │       ├── ComandoCrearMaquinaria.java
│       │       └── ManejadorCrearMaquinaria.java
│       └── consulta/                     # CQRS: Consultas (Lectura)
│           ├── fabrica/                  # Factories
│           └── manejador/                # Handlers
│               ├── ConsultaListarMaquinaria.java
│               └── ManejadorListarMaquinaria.java
│
└── infraestructura/ (Module)             # 🔴 CAPA DE INFRAESTRUCTURA (Spring Boot)
    ├── build.gradle
    └── src/main/java/com/maquimu/infraestructura/
        ├── adaptador/                    # Implementación de Puertos
        │   ├── dao/                      # Implementación DAOs (MySQL)
        │   │   └── JpaMaquinariaDao.java
        │   └── repositorio/              # Implementación Repositorios (MySQL)
        │       └── JpaMaquinariaRepositorio.java
        ├── controlador/                  # REST Controllers
        │   ├── ComandoControladorMaquinaria.java
        │   └── ConsultaControladorMaquinaria.java
        └── configuracion/                # Configuración Spring
            ├── BeanConfig.java
            └── SeguridadConfig.java
```
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
