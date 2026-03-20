# Modelo de Datos: MaquiMu

Este documento describe el modelo lógico de datos final (Versión 1.0, validado en Doc 15 y 20), que sirve como base para la base de datos MySQL.

## 1. Diagrama Entidad-Relación (Conceptual)

* [cite_start]Un `Cliente` puede tener muchos `Alquileres` (1 a N)[cite: 1473].
* [cite_start]Un `Usuario` (agente) puede gestionar muchos `Alquileres` (1 a N)[cite: 1475].
* [cite_start]Una `Maquinaria` puede estar en muchos `Alquileres` (1 a N)[cite: 1474].
* [cite_start]Un `Alquiler` genera una o más `Facturas` (1 a N)[cite: 1476].

## 2. Diccionario de Datos (Tablas)

### Tabla: `usuarios`
Almacena las cuentas de los empleados que operan el sistema.

| Nombre del Campo | Tipo de Dato | Descripción | Restricciones |
| :--- | :--- | :--- | :--- |
| `usuario_id` | BIGINT | Identificador único del usuario | PRIMARY KEY, AUTO_INCREMENT |
| `nombre_completo` | VARCHAR(150) | Nombre del empleado | NOT NULL |
| `email` | VARCHAR(100) | Correo electrónico para login | NOT NULL, UNIQUE |
| `password_hash` | VARCHAR(255) | Contraseña cifrada (bcrypt) | NOT NULL |
| `rol` | ENUM('admin', 'agente') | Rol del usuario en el sistema | NOT NULL |
| `fecha_creacion` | TIMESTAMP | Fecha de creación del registro | DEFAULT CURRENT_TIMESTAMP |

[cite_start][cite: 1494, 1500]

### Tabla: `clientes`
Almacena la información de las personas o empresas que alquilan.

| Nombre del Campo | Tipo de Dato | Descripción | Restricciones |
| :--- | :--- | :--- | :--- |
| `cliente_id` | BIGINT | Identificador único del cliente | PRIMARY KEY, AUTO_INCREMENT |
| `nombre_cliente` | VARCHAR(150) | Nombre de la persona o empresa | NOT NULL |
| `identificacion` | VARCHAR(20) | Cédula o NIT del cliente | NOT NULL, UNIQUE |
| `telefono` | VARCHAR(20) | Número de contacto | NOT NULL |
| `email` | VARCHAR(100) | Correo electrónico de contacto | NOT NULL, UNIQUE |
| `direccion` | VARCHAR(255) | Dirección de facturación | NOT NULL |
| `fecha_registro` | TIMESTAMP | Fecha de creación del registro | DEFAULT CURRENT_TIMESTAMP |

[cite_start][cite: 1502]

### Tabla: `maquinaria`
Contiene el inventario de todos los equipos disponibles.

| Nombre del Campo | Tipo de Dato | Descripción | Restricciones |
| :--- | :--- | :--- | :--- |
| `maquinaria_id` | BIGINT | Identificador único del equipo | PRIMARY KEY, AUTO_INCREMENT |
| `nombre_equipo` | VARCHAR(100) | Nombre o descripción | NOT NULL |
| `marca` | VARCHAR(50) | Marca del equipo | NOT NULL |
| `serial` | VARCHAR(50) | Número de serie único | NOT NULL, UNIQUE |
| `modelo` | VARCHAR(50) | Modelo del equipo | NOT NULL |
| `tarifa_por_dia` | DECIMAL(10, 2) | Costo del alquiler diario | NOT NULL |
| `tarifa_por_hora` | DECIMAL(10, 2) | Costo del alquiler por hora | NOT NULL |
| `estado` | ENUM('disponible', 'alquilado', 'en_mantenimiento') | Estado actual del equipo | NOT NULL |

[cite_start][cite: 1508, 3958]

### Tabla: `alquileres`
Registra cada operación de alquiler (contrato).

| Nombre del Campo | Tipo de Dato | Descripción | Restricciones |
| :--- | :--- | :--- | :--- |
| `alquiler_id` | BIGINT | Identificador único del alquiler | PRIMARY KEY, AUTO_INCREMENT |
| `cliente_id` | BIGINT | Referencia al cliente | NOT NULL, FOREIGN KEY (clientes) |
| `maquinaria_id` | BIGINT | Referencia a la maquinaria | NOT NULL, FOREIGN KEY (maquinaria) |
| `usuario_id` | BIGINT | Referencia al agente que aprobó | NULL (opcional), FOREIGN KEY (usuarios) |
| `fecha_inicio` | DATETIME | Fecha y hora de inicio | NOT NULL |
| `fecha_fin` | DATETIME | Fecha y hora de finalización | NOT NULL |
| `costo_total` | DECIMAL(12, 2) | Costo total calculado | NOT NULL |
| `estado_alquiler` | ENUM('PENDIENTE', 'APROBADO', 'ACTIVO', 'FINALIZADO', 'CANCELADO') | Estado actual del contrato | NOT NULL |

> **Nota HU #06:** La migración V13 modificó `usuario_id` para permitir NULL (solicitudes pendientes de aprobación) y expandió los estados del alquiler para soportar el flujo completo de solicitudes.

[cite_start][cite: 1509, 1515]

### Tabla: `facturas`
Guarda la información financiera de cada alquiler.

| Nombre del Campo | Tipo de Dato | Descripción | Restricciones |
| :--- | :--- | :--- | :--- |
| `factura_id` | BIGINT | Identificador único de la factura | PRIMARY KEY, AUTO_INCREMENT |
| `alquiler_id` | BIGINT | Alquiler asociado | NOT NULL, FOREIGN KEY (alquileres) |
| `fecha_emision` | DATE | Fecha de emisión | NOT NULL |
| `monto` | DECIMAL(12, 2) | Monto total de la factura | NOT NULL |
| `estado_pago` | ENUM('pendiente', 'pagada', 'vencida') | Estado del pago | NOT NULL |

[cite_start][cite: 1517, 1523]

---

---

## 3. Mapeo JPA (Entidades)

**Importante:** Todas las entidades JPA en el módulo `infraestructura` utilizan el tipo `Long` para los identificadores, que mapea correctamente con `BIGINT` en MySQL:

- `UsuarioEntity`: `private Long id`
- `ClienteEntity`: `private Long clienteId`
- `MaquinariaEntity`: `private Long maquinariaId`
- `AlquilerEntity`: `private Long alquilerId`
- `FacturaEntity`: `private Long facturaId`

Esto garantiza la consistencia entre el esquema de base de datos y las entidades JPA, evitando errores de validación de esquema de Hibernate.

---

## 4. Historial de Migraciones (Flyway)

Las migraciones se organizan en dos carpetas dentro de `infraestructura/src/main/resources/db/migration/`:

### DDL (Estructura)
| Versión | Archivo | Descripción |
| :--- | :--- | :--- |
| V1 | `V1__create_usuarios.sql` | Tabla usuarios con roles |
| V2 | `V2__create_clientes.sql` | Tabla clientes |
| V3 | `V3__create_maquinaria.sql` | Tabla maquinaria |
| V4 | `V4__create_alquileres.sql` | Tabla alquileres inicial |
| V5 | `V5__create_facturas.sql` | Tabla facturas |
| V13 | `V13__update_alquileres_for_solicitudes.sql` | ✅ HU #06: usuario_id nullable + nuevos estados |

### DML (Datos iniciales)
| Versión | Archivo | Descripción |
| :--- | :--- | :--- |
| V10 | `V10__insert_usuarios.sql` | Usuarios de prueba |
| V11 | `V11__insert_clientes.sql` | Clientes de prueba |
| V12 | `V12__insert_maquinaria.sql` | Maquinaria de prueba |

---

## 5. Flujo de Estados del Alquiler (HU #06)

```
┌──────────────┐
│  PENDIENTE   │ ← Cliente solicita (usuario_id = NULL)
└──────┬───────┘
       │ Operario aprueba
       ▼
┌──────────────┐
│   APROBADO   │ ← Operario asignado (usuario_id set)
└──────┬───────┘
       │ Fecha inicio alcanzada
       ▼
┌──────────────┐
│    ACTIVO    │ ← Alquiler en curso
└──────┬───────┘
       │ Fecha fin alcanzada
       ▼
┌──────────────┐
│  FINALIZADO  │ ← Alquiler completado
└──────────────┘

       ↓ (desde cualquier estado excepto FINALIZADO)
┌──────────────┐
│  CANCELADO   │ ← Cancelación manual
└──────────────┘
```