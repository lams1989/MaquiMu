# Modelo de Datos: MaquiMu

[cite_start]Este documento describe el modelo lógico de datos final (Versión 1.0, validado en Doc 15 y 20)[cite: 667, 3537], que sirve como base para la base de datos MySQL.

## 1. Diagrama Entidad-Relación (Conceptual)

* [cite_start]Un `Cliente` puede tener muchos `Alquileres` (1 a N)[cite: 690].
* [cite_start]Un `Usuario` (agente) puede gestionar muchos `Alquileres` (1 a N)[cite: 692].
* [cite_start]Una `Maquinaria` puede estar en muchos `Alquileres` (1 a N)[cite: 691].
* [cite_start]Un `Alquiler` genera una o más `Facturas` (1 a N)[cite: 693].

## 2. Diccionario de Datos (Tablas)

### Tabla: `usuarios`
[cite_start]Almacena las cuentas de los empleados que operan el sistema[cite: 680, 711].

| Nombre del Campo | Tipo de Dato | Descripción | Restricciones |
| :--- | :--- | :--- | :--- |
| `usuario_id` | INT | Identificador único del usuario | [cite_start]PRIMARY KEY, AUTO_INCREMENT [cite: 711] |
| `nombre_completo` | VARCHAR(150) | Nombre del empleado | [cite_start]NOT NULL [cite: 711] |
| `email` | VARCHAR(100) | Correo electrónico para login | [cite_start]NOT NULL, UNIQUE [cite: 711] |
| `password_hash` | VARCHAR(255) | Contraseña cifrada (bcrypt) | [cite_start]NOT NULL [cite: 717] |
| `rol` | ENUM('admin', 'agente') | Rol del usuario en el sistema | [cite_start]NOT NULL [cite: 717] |
| `fecha_creacion` | TIMESTAMP | Fecha de creación del registro | [cite_start]DEFAULT CURRENT_TIMESTAMP [cite: 717] |

### Tabla: `clientes`
[cite_start]Almacena la información de las personas o empresas que alquilan[cite: 676, 718].

| Nombre del Campo | Tipo de Dato | Descripción | Restricciones |
| :--- | :--- | :--- | :--- |
| `cliente_id` | INT | Identificador único del cliente | [cite_start]PRIMARY KEY, AUTO_INCREMENT [cite: 719] |
| `nombre_cliente` | VARCHAR(150) | Nombre de la persona o empresa | [cite_start]NOT NULL [cite: 719] |
| `identificacion` | VARCHAR(20) | Cédula o NIT del cliente | [cite_start]NOT NULL, UNIQUE [cite: 719] |
| `telefono` | VARCHAR(20) | Número de contacto | [cite_start]NOT NULL [cite: 719] |
| `email` | VARCHAR(100) | Correo electrónico de contacto | [cite_start]NOT NULL, UNIQUE [cite: 719] |
| `direccion` | VARCHAR(255) | Dirección de facturación | [cite_start]NOT NULL [cite: 719] |
| `fecha_registro` | TIMESTAMP | Fecha de creación del registro | [cite_start]DEFAULT CURRENT_TIMESTAMP [cite: 719] |

### Tabla: `maquinaria`
[cite_start]Contiene el inventario de todos los equipos disponibles[cite: 677, 725].

| Nombre del Campo | Tipo de Dato | Descripción | Restricciones |
| :--- | :--- | :--- | :--- |
| `maquinaria_id` | INT | Identificador único del equipo | [cite_start]PRIMARY KEY, AUTO_INCREMENT [cite: 725, 3591] |
| `nombre_equipo` | VARCHAR(100) | Nombre o descripción | [cite_start]NOT NULL [cite: 725] |
| `marca` | VARCHAR(50) | Marca del equipo | [cite_start]NOT NULL [cite: 725] |
| `serial` | VARCHAR(50) | Número de serie único | [cite_start]NOT NULL, UNIQUE [cite: 725] |
| `modelo` | VARCHAR(50) | Modelo del equipo | [cite_start]NOT NULL [cite: 725] |
| `tarifa_por_dia` | DECIMAL(10, 2) | Costo del alquiler diario | [cite_start]NOT NULL [cite: 725] |
| `tarifa_por_hora` | DECIMAL(10, 2) | Costo del alquiler por hora | [cite_start]NOT NULL [cite: 725] |
| `estado` | ENUM('disponible', 'alquilado', 'en_mantenimiento') | Estado actual del equipo | [cite_start]NOT NULL [cite: 3592, 1072] |

### Tabla: `alquileres`
[cite_start]Registra cada operación de alquiler (contrato)[cite: 678, 726].

| Nombre del Campo | Tipo de Dato | Descripción | Restricciones |
| :--- | :--- | :--- | :--- |
| `alquiler_id` | INT | Identificador único del alquiler | [cite_start]PRIMARY KEY, AUTO_INCREMENT [cite: 726] |
| `cliente_id` | INT | Referencia al cliente | [cite_start]NOT NULL, FOREIGN KEY (clientes) [cite: 726] |
| `maquinaria_id` | INT | Referencia a la maquinaria | [cite_start]NOT NULL, FOREIGN KEY (maquinaria) [cite: 732] |
| `usuario_id` | INT | Referencia al agente que gestionó | [cite_start]NOT NULL, FOREIGN KEY (usuarios) [cite: 732] |
| `fecha_inicio` | DATETIME | Fecha y hora de inicio | [cite_start]NOT NULL [cite: 732] |
| `fecha_fin` | DATETIME | Fecha y hora de finalización | [cite_start]NOT NULL [cite: 732] |
| `costo_total` | DECIMAL(12, 2) | Costo total calculado | [cite_start]NOT NULL [cite: 732] |
| `estado_alquiler` | ENUM('activo', 'finalizado', 'cancelado') | Estado actual del contrato | [cite_start]NOT NULL [cite: 732, 3599] |

### Tabla: `facturas`
[cite_start]Guarda la información financiera de cada alquiler[cite: 679, 733].

| Nombre del Campo | Tipo de Dato | Descripción | Restricciones |
| :--- | :--- | :--- | :--- |
| `factura_id` | INT | Identificador único de la factura | [cite_start]PRIMARY KEY, AUTO_INCREMENT [cite: 733] |
| `alquiler_id` | INT | Alquiler asociado | [cite_start]NOT NULL, FOREIGN KEY (alquileres) [cite: 733] |
| `fecha_emision` | DATE | Fecha de emisión | [cite_start]NOT NULL [cite: 733] |
| `monto` | DECIMAL(12, 2) | Monto total de la factura | [cite_start]NOT NULL [cite: 733] |
| `estado_pago` | ENUM('pendiente', 'pagada', 'vencida') | Estado del pago | [cite_start]NOT NULL [cite: 740, 3599] |