# Configuración de API Endpoints - MaquiMu

## 🌐 Base URL

**Ruta Base de la API:**
```
http://localhost:8080/api/maquimu/v1
```

### Estructura de la Ruta

La ruta sigue el patrón híbrido recomendado:
- `/api` - Estándar de industria para APIs REST
- `/maquimu` - Identificador específico de la aplicación
- `/v1` - Versionado de la API

---

## 📍 Endpoints Principales

### Autenticación
```
POST   /api/maquimu/v1/auth/login
POST   /api/maquimu/v1/auth/register
POST   /api/maquimu/v1/auth/refresh
```

### Maquinaria
```
GET    /api/maquimu/v1/maquinaria
GET    /api/maquimu/v1/maquinaria/{id}
POST   /api/maquimu/v1/maquinaria
PUT    /api/maquimu/v1/maquinaria/{id}
DELETE /api/maquimu/v1/maquinaria/{id}
```

### Clientes
```
GET    /api/maquimu/v1/clientes
GET    /api/maquimu/v1/clientes/{id}
POST   /api/maquimu/v1/clientes
PUT    /api/maquimu/v1/clientes/{id}
DELETE /api/maquimu/v1/clientes/{id}
```

### Alquileres
```
GET    /api/maquimu/v1/alquileres                  (Admin: Listar todos)
GET    /api/maquimu/v1/alquileres/mis-alquileres   (Cliente: Listar propios)
GET    /api/maquimu/v1/alquileres/{id}             (Detalle)
POST   /api/maquimu/v1/alquileres                  (Crear solicitud)
PATCH  /api/maquimu/v1/alquileres/{id}/aprobar     (Operario: Aprobar)
PATCH  /api/maquimu/v1/alquileres/{id}/rechazar    (Operario: Rechazar)
PATCH  /api/maquimu/v1/alquileres/{id}/entregar    (Operario: Entregar/Activar)
PATCH  /api/maquimu/v1/alquileres/{id}/finalizar   (Operario: Finalizar/Devolver)
```

### Facturas
```
GET    /api/maquimu/v1/facturas                    (Admin: Listar todas)
GET    /api/maquimu/v1/facturas/mis-facturas       (Cliente: Listar propias)
GET    /api/maquimu/v1/facturas/{id}               (Detalle)
GET    /api/maquimu/v1/facturas/{id}/descargar     (Descargar PDF)
POST   /api/maquimu/v1/facturas                    (Generar factura)
```

### Dashboards
```
GET    /api/maquimu/v1/dashboard/operario          (KPIs Operario)
GET    /api/maquimu/v1/dashboard/cliente           (KPIs Cliente)
```

---

## 🔧 Configuración

### Backend (Spring Boot)

**Archivo:** `maquimu-backend/src/main/resources/application.properties`

```properties
server.servlet.context-path=/api/maquimu/v1
```

### Frontend (Angular)

**Archivo:** `maquimu-frontend/src/app/core/services/api.service.ts`

```typescript
private baseUrl = 'http://localhost:8080/api/maquimu/v1';
```

---

## 🔒 Rutas Públicas (Sin Autenticación)

Las siguientes rutas están configuradas para acceso público en `SecurityConfig.java`:

- `/api/maquimu/v1/auth/**` - Endpoints de autenticación
- `/api/maquimu/v1/test/**` - Endpoints de prueba
- `/api-docs/**` - Documentación OpenAPI
- `/swagger-ui/**` - Interfaz Swagger UI

---

## 📚 Documentación Interactiva

### Swagger UI
```
http://localhost:8080/swagger-ui.html
```

### OpenAPI JSON
```
http://localhost:8080/api-docs
```

---

## 🚀 Versionado Futuro

Cuando sea necesario crear una nueva versión de la API:

1. Mantener `/api/maquimu/v1` para clientes existentes
2. Crear nueva ruta `/api/maquimu/v2` con cambios
3. Documentar breaking changes entre versiones
4. Establecer período de deprecación para v1

---

## 💡 Ventajas de esta Estructura

✅ **Estándar de Industria** - Usa `/api` reconocido universalmente  
✅ **Identificación Clara** - `/maquimu` identifica la aplicación  
✅ **Versionado** - `/v1` permite evolución sin romper clientes  
✅ **Escalabilidad** - Fácil agregar nuevas APIs o versiones  
✅ **Profesional** - Estructura esperada por desarrolladores  

---

**Última actualización:** 2025-11-23
