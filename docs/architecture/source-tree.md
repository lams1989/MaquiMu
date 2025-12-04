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
│       │   └── MaquimuBackendApplication.java # Main Class (en infraestructura)
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
│       ├── maquinaria/                   # Módulo de Maquinaria
│       │   ├── modelo/
│       │   │   ├── Maquinaria.java
│       │   │   └── EstadoMaquinaria.java
│       │   ├── puerto/
│       │   │   ├── dao/
│       │   │   │   └── MaquinariaDao.java
│       │   │   └── repositorio/
│       │   │       └── MaquinariaRepositorio.java
│       │   └── servicio/
│       │       ├── MaquinariaComandoServicio.java
│       │       └── MaquinariaConsultaServicio.java
│       │
│       ├── cliente/                      # Módulo de Cliente
│       │   ├── modelo/
│       │   │   └── Cliente.java
│       │   ├── puerto/
│       │   │   ├── dao/
│       │   │   │   └── ClienteDao.java
│       │   │   └── repositorio/
│       │   │       └── ClienteRepositorio.java
│       │   └── servicio/
│       │       ├── ClienteComandoServicio.java
│       │       └── ClienteConsultaServicio.java
│       │
│       ├── alquiler/                     # Módulo de Alquiler
│       │   ├── modelo/
│       │   │   ├── Alquiler.java
│       │   │   ├── EstadoAlquiler.java
│       │   │   └── TipoTarifa.java
│       │   ├── puerto/
│       │   │   ├── dao/
│       │   │   │   └── AlquilerDao.java
│       │   │   └── repositorio/
│       │   │       └── AlquilerRepositorio.java
│       │   └── servicio/
│       │       ├── AlquilerComandoServicio.java
│       │       └── AlquilerConsultaServicio.java
│       │
│       ├── autenticacion/                # Módulo de Autenticación
│       │   ├── modelo/
│       │   │   ├── Usuario.java
│       │   │   └── RolUsuario.java
│       │   ├── puerto/
│       │   │   ├── dao/
│       │   │   │   └── UsuarioDao.java
│       │   │   └── repositorio/
│       │   │       └── UsuarioRepositorio.java
│       │   └── servicio/
│       │       ├── UsuarioComandoServicio.java
│       │       └── UsuarioConsultaServicio.java
│       │
│       ├── factura/                      # Módulo de Factura
│       │   ├── modelo/
│       │   │   ├── Factura.java
│       │   │   └── EstadoFactura.java
│       │   ├── puerto/
│       │   │   ├── dao/
│       │   │   │   └── FacturaDao.java
│       │   │   └── repositorio/
│       │   │       └── FacturaRepositorio.java
│       │   └── servicio/
│       │       ├── FacturaComandoServicio.java
│       │       └── FacturaConsultaServicio.java
│       │
│       └── compartido/                   # Componentes Compartidos
│           ├── excepcion/
│           │   ├── RecursoNoEncontradoException.java
│           │   ├── DuplicadoException.java
│           │   └── ValidacionException.java
│           └── valor/                    # Value Objects
│               ├── Email.java
│               ├── Telefono.java
│               └── Dinero.java
│
├── aplicacion/ (Module)                  # 🟡 CAPA DE APLICACIÓN (Orquestación)
│   ├── build.gradle
│   └── src/main/java/com/maquimu/aplicacion/
│       ├── maquinaria/
│       │   ├── comando/
│       │   │   ├── ComandoCrearMaquinaria.java
│       │   │   ├── ComandoActualizarMaquinaria.java
│       │   │   ├── ComandoEliminarMaquinaria.java
│       │   │   ├── fabrica/
│       │   │   │   └── FabricaMaquinaria.java
│       │   │   └── manejador/
│       │   │       ├── ManejadorCrearMaquinaria.java
│       │   │       ├── ManejadorActualizarMaquinaria.java
│       │   │       └── ManejadorEliminarMaquinaria.java
│       │   └── consulta/
│       │       ├── ConsultaListarMaquinaria.java
│       │       ├── ConsultaBuscarMaquinaria.java
│       │       └── manejador/
│       │           ├── ManejadorListarMaquinaria.java
│       │           └── ManejadorBuscarMaquinaria.java
│       │
│       ├── cliente/
│       │   ├── comando/
│       │   │   ├── ComandoCrearCliente.java
│       │   │   ├── ComandoActualizarCliente.java
│       │   │   ├── ComandoEliminarCliente.java
│       │   │   ├── fabrica/
│       │   │   │   └── FabricaCliente.java
│       │   │   └── manejador/
│       │   │       ├── ManejadorCrearCliente.java
│       │   │       ├── ManejadorActualizarCliente.java
│       │   │       └── ManejadorEliminarCliente.java
│       │   └── consulta/
│       │       ├── ConsultaListarClientes.java
│       │       ├── ConsultaBuscarCliente.java
│       │       └── manejador/
│       │           ├── ManejadorListarClientes.java
│       │           └── ManejadorBuscarCliente.java
│       │
│       ├── alquiler/
│       │   ├── comando/
│       │   │   ├── ComandoSolicitarAlquiler.java
│       │   │   ├── ComandoAprobarAlquiler.java
│       │   │   ├── ComandoFinalizarAlquiler.java
│       │   │   ├── fabrica/
│       │   │   │   └── FabricaAlquiler.java
│       │   │   └── manejador/
│       │   │       ├── ManejadorSolicitarAlquiler.java
│       │   │       ├── ManejadorAprobarAlquiler.java
│       │   │       └── ManejadorFinalizarAlquiler.java
│       │   └── consulta/
│       │       ├── ConsultaListarAlquileres.java
│       │       ├── ConsultaBuscarAlquiler.java
│       │       └── manejador/
│       │           ├── ManejadorListarAlquileres.java
│       │           └── ManejadorBuscarAlquiler.java
│       │
│       ├── autenticacion/
│       │   ├── comando/
│       │   │   ├── ComandoRegistrarUsuario.java
│       │   │   ├── fabrica/
│       │   │   │   └── FabricaUsuario.java
│       │   │   └── manejador/
│       │   │       └── ManejadorRegistrarUsuario.java
│       │   └── consulta/
│       │       ├── ConsultaAutenticarUsuario.java
│       │       └── manejador/
│       │           └── ManejadorAutenticarUsuario.java
│       │
│       ├── factura/
│       │   ├── comando/
│       │   │   ├── ComandoGenerarFactura.java
│       │   │   ├── fabrica/
│       │   │   │   └── FabricaFactura.java
│       │   │   └── manejador/
│       │   │       └── ManejadorGenerarFactura.java
│       │   └── consulta/
│       │       ├── ConsultaListarFacturas.java
│       │       ├── ConsultaBuscarFactura.java
│       │       └── manejador/
│       │           ├── ManejadorListarFacturas.java
│       │           └── ManejadorBuscarFactura.java
│       │
│       └── compartido/
│           └── servicio/
│               └── GeneradorJwt.java
│
└── infraestructura/ (Module)             # 🔴 CAPA DE INFRAESTRUCTURA (Spring Boot)
    ├── build.gradle
    └── src/main/java/com/maquimu/infraestructura/
        ├── maquinaria/
        │   ├── adaptador/
        │   │   ├── entidad/
        │   │   │   └── MaquinariaEntity.java
        │   │   ├── dao/
        │   │   │   ├── JpaMaquinariaRepository.java  # Spring Data JPA
        │   │   │   └── JpaMaquinariaDao.java         # Implementación puerto
        │   │   └── repositorio/
        │   │       └── JpaMaquinariaRepositorio.java # Implementación puerto
        │   └── controlador/
        │       ├── ComandoControladorMaquinaria.java
        │       └── ConsultaControladorMaquinaria.java
        │
        ├── cliente/
        │   ├── adaptador/
        │   │   ├── entidad/
        │   │   │   └── ClienteEntity.java
        │   │   ├── dao/
        │   │   │   ├── JpaClienteRepository.java
        │   │   │   └── JpaClienteDao.java
        │   │   └── repositorio/
        │   │       └── JpaClienteRepositorio.java
        │   └── controlador/
        │       ├── ComandoControladorCliente.java
        │       └── ConsultaControladorCliente.java
        │
        ├── alquiler/
        │   ├── adaptador/
        │   │   ├── entidad/
        │   │   │   └── AlquilerEntity.java
        │   │   ├── dao/
        │   │   │   ├── JpaAlquilerRepository.java
        │   │   │   └── JpaAlquilerDao.java
        │   │   └── repositorio/
        │   │       └── JpaAlquilerRepositorio.java
        │   └── controlador/
        │       ├── ComandoControladorAlquiler.java
        │       └── ConsultaControladorAlquiler.java
        │
        ├── autenticacion/
        │   ├── adaptador/
        │   │   ├── entidad/
        │   │   │   └── UsuarioEntity.java
        │   │   ├── dao/
        │   │   │   ├── JpaUsuarioRepository.java
        │   │   │   └── JpaUsuarioDao.java
        │   │   └── repositorio/
        │   │       └── JpaUsuarioRepositorio.java
        │   └── controlador/
        │       └── AuthController.java
        │
        ├── factura/
        │   ├── adaptador/
        │   │   ├── entidad/
        │   │   │   └── FacturaEntity.java
        │   │   ├── dao/
        │   │   │   ├── JpaFacturaRepository.java
        │   │   │   └── JpaFacturaDao.java
        │   │   └── repositorio/
        │   │       └── JpaFacturaRepositorio.java
        │   └── controlador/
        │       ├── ComandoControladorFactura.java
        │       └── ConsultaControladorFactura.java
        │
        ├── configuracion/
        │   ├── SeguridadConfig.java
        │   ├── JwtAuthenticationFilter.java
        │   ├── AuthEntryPoint.java
        │   ├── BeanConfig.java
        │   └── CorsConfig.java
        │
        └── MaquimuBackendApplication.java
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

### Backend (Java) - Estructura Modular

**Packages por Módulo:**
- `com.maquimu.dominio.{modulo}.{tipo}`
- `com.maquimu.aplicacion.{modulo}.{tipo}`
- `com.maquimu.infraestructura.{modulo}.{tipo}`

**Ejemplos:**
```java
// Dominio
package com.maquimu.dominio.maquinaria.modelo;
package com.maquimu.dominio.maquinaria.puerto.dao;
package com.maquimu.dominio.maquinaria.servicio;

package com.maquimu.dominio.alquiler.modelo;
package com.maquimu.dominio.alquiler.puerto.repositorio;

// Aplicación
package com.maquimu.aplicacion.maquinaria.comando.fabrica;
package com.maquimu.aplicacion.maquinaria.comando.manejador;
package com.maquimu.aplicacion.maquinaria.consulta.manejador;

package com.maquimu.aplicacion.cliente.comando.fabrica;

// Infraestructura
package com.maquimu.infraestructura.maquinaria.adaptador.entidad;
package com.maquimu.infraestructura.maquinaria.adaptador.dao;
package com.maquimu.infraestructura.maquinaria.controlador;

package com.maquimu.infraestructura.autenticacion.controlador;
package com.maquimu.infraestructura.configuracion;
```

### Frontend (Angular)
- **Componentes**: `kebab-case` (ej. `inventory-list.component.ts`)
- **Servicios**: `kebab-case.service.ts` (ej. `maquinaria.service.ts`)
- **Modelos**: `kebab-case.model.ts` (ej. `maquinaria.model.ts`)

### Base de Datos
- **Tablas**: `snake_case` (ej. `maquinaria`, `alquileres`)
- **Columnas**: `snake_case` (ej. `nombre_cliente`, `tarifa_dia`)

---

**Última actualización:** 2025-12-04
