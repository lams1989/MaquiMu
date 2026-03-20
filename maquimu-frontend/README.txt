# MaquiMu Frontend - Aplicación Web

## 📋 Descripción

Frontend web del sistema MaquiMu desarrollado con **Angular 17**, proporcionando dos interfaces principales:
- **Gestión Interna:** Panel administrativo para empleados
- **Portal de Clientes:** Autoservicio para clientes

## 🏗️ Arquitectura

Aplicación SPA (Single Page Application) con arquitectura modular:

```
src/app/
├── core/                 # Servicios singleton y configuración
│   ├── services/        # HTTP, Auth, etc.
│   ├── guards/          # Route guards
│   └── interceptors/    # HTTP interceptors
├── shared/              # Componentes reutilizables
│   ├── components/      # navbar, footer, modal
│   └── models/          # Modelos TypeScript
└── features/            # Módulos funcionales
    ├── auth/           # Login/Register
    ├── admin/          # Gestión (empleados)
    └── client/         # Portal clientes
```

## 🚀 Tecnologías

- **Angular 17**
- **TypeScript 5.x**
- **RxJS 7.x** (programación reactiva)
- **Bootstrap 5.3** (UI framework)
- **Bootstrap Icons**

## ⚙️ Configuración

### Prerrequisitos

- Node.js 18+ y npm
- Angular CLI 17

### Instalación

```bash
# Instalar Angular CLI globalmente (si no está instalado)
npm install -g @angular/cli@17

# Instalar dependencias del proyecto
npm install
```

## 🏃 Ejecutar la Aplicación

### Modo Desarrollo

```bash
# Servidor de desarrollo
ng serve

# Con puerto específico
ng serve --port 4200

# Abrir automáticamente en navegador
ng serve --open
```

La aplicación estará disponible en: `http://localhost:4200`

### Build de Producción

```bash
# Build optimizado
ng build --configuration production

# Los archivos se generarán en: dist/maquimu-frontend/
```

## 📁 Estructura de Módulos

### Core Module
Servicios singleton y configuración global:
- `AuthService` - Autenticación y gestión de tokens
- `MaquinariaService` - Gestión de maquinaria
- `ClienteService` - Gestión de clientes
- `AlquilerService` - Gestión de alquileres
- `AuthGuard` - Protección de rutas
- `JwtInterceptor` - Inyección de tokens JWT

### Shared Module
Componentes y utilidades compartidas:
- Navbar
- Footer
- Modales
- Modelos TypeScript

### Feature Modules

#### Auth Module
- Login
- Registro de clientes

#### Admin Module (Empleados)
- Dashboard
- Gestión de inventario (maquinaria)
- Gestión de clientes
- Gestión de alquileres
- Gestión financiera

#### Client Module (Clientes)
- Dashboard
- Solicitar alquiler
- Mis alquileres
- Mis facturas

## 🎨 UI/UX

### Bootstrap 5.3
Framework CSS utilizado para componentes y layout responsivo.

### Diseños de Referencia
Los mockups HTML están disponibles en: `../docs/diseños/maquetacion/`

### Temas
- Tema principal con colores corporativos
- Modo responsive para móviles y tablets

## 🔐 Seguridad

### Autenticación
- JWT almacenado en localStorage
- Interceptor HTTP para inyectar token en requests
- Guards para proteger rutas

### Autorización
- Guards basados en roles (ADMIN, OPERARIO, CLIENTE)
- Rutas protegidas según permisos

## 🧪 Testing

### Tests Unitarios

```bash
# Ejecutar tests
ng test

# Con cobertura
ng test --code-coverage
```

### Tests E2E

```bash
# Ejecutar tests end-to-end
ng e2e
```

## 📡 Comunicación con Backend

### API Base URL

Configurar en `src/environments/environment.ts`:

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};
```

### Servicios HTTP

Todos los servicios HTTP están en `src/app/core/services/`:
- Uso de `HttpClient` de Angular
- Manejo de errores centralizado
- Transformación de respuestas con RxJS

## 🔧 Convenciones de Código

- **Componentes:** kebab-case (ej. `inventory-list.component.ts`)
- **Servicios:** kebab-case.service.ts (ej. `maquinaria.service.ts`)
- **Modelos:** kebab-case.model.ts (ej. `maquinaria.model.ts`)
- **Interfaces:** PascalCase con prefijo `I` (ej. `IMaquinaria`)

Ver [coding-standards.md](../docs/architecture/coding-standards.md) para más detalles.

## 📦 Scripts Disponibles

```json
{
  "start": "ng serve",
  "build": "ng build",
  "watch": "ng build --watch --configuration development",
  "test": "ng test",
  "lint": "ng lint"
}
```

## 🚧 Estado Actual

**⚠️ PENDIENTE DE INICIALIZACIÓN**

Este módulo aún no ha sido inicializado con Angular CLI. Para crear el proyecto:

```bash
# Desde la raíz del repositorio
cd maquimu-frontend
ng new . --directory ./ --routing --style=css --skip-git
```

Luego instalar dependencias adicionales:

```bash
npm install bootstrap bootstrap-icons
npm install @types/bootstrap --save-dev
```

## 📞 Soporte

Para dudas o problemas, consultar:
- [Documentación del proyecto](../docs/)
- [Mockups de diseño](../docs/diseños/maquetacion/)
- [Historias de usuario](../docs/stories/)

---

**Última actualización:** 2025-11-22
