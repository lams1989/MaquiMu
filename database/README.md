# MaquiMu Database - Gestión de Base de Datos

## 📋 Descripción

Scripts de base de datos, migraciones y datos de prueba para el sistema MaquiMu.

## 🗄️ Motor de Base de Datos

**MySQL 8.0**

## 📁 Estructura

```
database/
├── migrations/          # Migraciones Flyway
│   └── V1__create_initial_schema.sql
└── seeds/              # Datos de prueba
    └── test_data.sql
```

## ⚙️ Configuración

### Prerrequisitos

- MySQL 8.0 instalado
- Cliente MySQL (mysql-cli, MySQL Workbench, etc.)

### Instalación de MySQL

#### Windows
```bash
# Descargar desde: https://dev.mysql.com/downloads/mysql/
# Instalar MySQL Server 8.0
# Configurar contraseña de root durante instalación
```

#### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install mysql-server
sudo mysql_secure_installation
```

#### macOS
```bash
brew install mysql@8.0
brew services start mysql@8.0
```

## 🚀 Crear Base de Datos

### Opción 1: Desde MySQL CLI

```bash
# Conectar a MySQL
mysql -u root -p

# Crear base de datos
CREATE DATABASE maquimu_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# Crear usuario (opcional)
CREATE USER 'maquimu_user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON maquimu_db.* TO 'maquimu_user'@'localhost';
FLUSH PRIVILEGES;

# Salir
EXIT;
```

### Opción 2: Desde MySQL Workbench

1. Abrir MySQL Workbench
2. Conectar al servidor local
3. Ejecutar: `CREATE DATABASE maquimu_db;`

## 📊 Esquema de Base de Datos

### Tablas Principales

#### usuarios
Empleados del sistema (ADMIN, OPERARIO)
```sql
- id (PK)
- nombre
- email (UNIQUE)
- password (hash)
- rol (ADMIN, OPERARIO)
- activo
- created_at
- updated_at
```

#### clientes
Clientes que alquilan maquinaria
```sql
- id (PK)
- nombre
- email (UNIQUE)
- telefono
- direccion
- nit
- password (hash)
- activo
- created_at
- updated_at
```

#### maquinaria
Inventario de maquinaria
```sql
- id (PK)
- nombre
- tipo
- marca
- modelo
- año
- tarifa_dia (DECIMAL)
- estado (DISPONIBLE, ALQUILADA, MANTENIMIENTO)
- descripcion
- imagen_url
- created_at
- updated_at
```

#### alquileres
Contratos de alquiler
```sql
- id (PK)
- cliente_id (FK)
- maquinaria_id (FK)
- fecha_inicio
- fecha_fin
- fecha_devolucion_real
- tarifa_acordada (DECIMAL)
- total (DECIMAL)
- estado (PENDIENTE, ACTIVO, FINALIZADO, CANCELADO)
- observaciones
- created_at
- updated_at
```

#### facturas
Facturación
```sql
- id (PK)
- alquiler_id (FK)
- numero_factura (UNIQUE)
- fecha_emision
- subtotal (DECIMAL)
- iva (DECIMAL)
- total (DECIMAL)
- estado (PENDIENTE, PAGADA, ANULADA)
- created_at
- updated_at
```

## 🔄 Migraciones con Flyway

Las migraciones se ejecutan automáticamente al iniciar el backend de Spring Boot.

### Convenciones de Nombres

```
V{version}__{description}.sql
```

Ejemplos:
- `V1__create_initial_schema.sql`
- `V2__add_facturas_table.sql`
- `V3__add_indices.sql`

### Crear Nueva Migración

1. Crear archivo en `migrations/` con el siguiente número de versión
2. Escribir SQL DDL (CREATE, ALTER, etc.)
3. La migración se ejecutará automáticamente al iniciar el backend

### Verificar Migraciones

```sql
-- Ver historial de migraciones
SELECT * FROM flyway_schema_history;
```

## 🌱 Datos de Prueba (Seeds)

### Cargar Datos de Prueba

```bash
# Desde MySQL CLI
mysql -u root -p maquimu_db < seeds/test_data.sql
```

### Contenido de test_data.sql

- Usuarios de prueba (admin, operario)
- Clientes de prueba
- Maquinaria de ejemplo
- Alquileres de ejemplo

## 🔐 Seguridad

### Contraseñas

- **Nunca** almacenar contraseñas en texto plano
- Usar BCrypt para hash de contraseñas (manejado por Spring Security)
- Cambiar contraseñas por defecto en producción

### Conexión Segura

```properties
# Producción - usar SSL
spring.datasource.url=jdbc:mysql://localhost:3306/maquimu_db?useSSL=true
```

## 🔧 Mantenimiento

### Backup

```bash
# Crear backup
mysqldump -u root -p maquimu_db > backup_$(date +%Y%m%d).sql

# Restaurar backup
mysql -u root -p maquimu_db < backup_20251122.sql
```

### Optimización

```sql
-- Analizar tablas
ANALYZE TABLE maquinaria, alquileres, clientes;

-- Optimizar tablas
OPTIMIZE TABLE maquinaria, alquileres, clientes;
```

## 📝 Configuración del Backend

El backend se conecta a la base de datos mediante:

**`maquimu-backend/src/main/resources/application.properties`**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/maquimu_db
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=validate
```

## 🐛 Troubleshooting

### Error: "Access denied for user"

```bash
# Verificar usuario y contraseña
mysql -u root -p

# Resetear contraseña de root si es necesario
```

### Error: "Unknown database 'maquimu_db'"

```bash
# Crear la base de datos
mysql -u root -p -e "CREATE DATABASE maquimu_db;"
```

### Error de conexión desde backend

```bash
# Verificar que MySQL esté corriendo
# Windows
net start MySQL80

# Linux
sudo systemctl status mysql

# macOS
brew services list
```

## 📊 Diagrama ER

Ver [`docs/architecture/BASE_DATOS.md`](../docs/architecture/BASE_DATOS.md) para el diagrama entidad-relación completo.

## 🚧 Próximos Pasos

- [ ] Crear migración V1 con esquema inicial
- [ ] Crear datos de prueba (seeds)
- [ ] Agregar índices para optimización
- [ ] Documentar stored procedures (si se usan)
- [ ] Configurar backups automáticos

## 📞 Soporte

Para dudas o problemas, consultar:
- [Documentación del proyecto](../docs/)
- [Backend README](../maquimu-backend/README.md)
- [Arquitectura de BD](../docs/architecture/BASE_DATOS.md)

---

**Última actualización:** 2025-11-22
