# Plan de Desarrollo del Proyecto MaquiMu

## 1. Visión General y Estrategia

Este plan detalla la hoja de ruta para la construcción del sistema MaquiMu, basándose en la arquitectura hexagonal definida y los activos existentes. El desarrollo se realizará de manera local, priorizando la construcción del núcleo (Backend) seguido por los clientes (Web y Móvil).

**Estrategia de Desarrollo:** Cascada Iterativa (Waterfall with Iterations).
1.  **Fase de Cimientos:** Configuración de proyectos y arquitectura base.
2.  **Fase de Funcionalidad Core:** Implementación historia por historia (Backend -> Web -> Móvil).
3.  **Fase de Estabilización:** Pruebas integrales y correcciones.

---

## 2. Fase 1: Cimientos (Setup & Architecture)

### 1.1 Backend (Spring Boot)
*   **Objetivo:** Crear el proyecto base y configurar la arquitectura hexagonal.
*   **Tecnologías:** Java 17, Spring Boot 3, MySQL, Spring Security.
*   **Tareas:**
    *   [ ] Inicializar proyecto Spring Boot (`maquimu-backend`).
    *   [ ] Configurar conexión a base de datos MySQL (`application.properties`).
    *   [ ] Ejecutar script de base de datos (`docs/diseños/Script-BaseDatosMaquiMu.sql`).
    *   [ ] Crear estructura de paquetes Hexagonal (`domain`, `application`, `infrastructure`).
    *   [ ] Implementar configuración de seguridad base (JWT/OAuth2).

### 1.2 Frontend Web (Angular)
*   **Objetivo:** Inicializar el proyecto SPA y preparar la estructura de componentes.
*   **Tecnologías:** Angular 16+, Bootstrap/Tailwind (según maquetación).
*   **Tareas:**
    *   [ ] Inicializar proyecto Angular (`maquimu-web`) dentro de `MaquiMuWeb`.
    *   [ ] Migrar activos estáticos de `MaquiMuWeb/maquetacion` a `assets`.
    *   [ ] Configurar enrutamiento base (`app-routing.module.ts`).
    *   [ ] Crear servicios base (`AuthService`, `ApiService`).

### 1.3 Frontend Móvil (Android)
*   **Objetivo:** Validar y preparar el proyecto existente.
*   **Tecnologías:** Android SDK, Kotlin/Java, Retrofit.
*   **Tareas:**
    *   [ ] Verificar compilación del proyecto en `MaquiMuMobile`.
    *   [ ] Configurar cliente HTTP (Retrofit) para conectar con el Backend local.

---

## 3. Fase 2: Ejecución por Módulos (Historias de Usuario)

Se implementarán las historias de usuario agrupadas por funcionalidad lógica.

### Ciclo 1: Gestión de Inventario y Seguridad (Core)
*   **Historias:**
    *   `HU-01: Registro de Maquinaria`
    *   `HU-02: Autenticación de Usuario`
*   **Backend:**
    *   Crear Entidades `Usuario`, `Maquinaria`.
    *   Implementar `AuthService` (Login).
    *   Implementar `MaquinariaService` (CRUD).
    *   Exponer Endpoints REST (`/auth/login`, `/api/maquinaria`).
*   **Frontend Web:**
    *   Implementar página de Login (usando maquetación existente).
    *   Implementar Dashboard Admin y vista de Inventario.
*   **Móvil:**
    *   Implementar Pantalla de Login.

### Ciclo 2: Gestión de Clientes y Alquileres
*   **Historias:**
    *   `HU-03: Registro de Clientes`
    *   `HU-07: Solicitar Alquiler (Cliente)`
    *   `HU-08: Consultar Alquileres (Cliente)`
*   **Backend:**
    *   Crear Entidades `Cliente`, `Alquiler`.
    *   Implementar lógica de negocio para crear alquileres y validar disponibilidad.
*   **Frontend Web:**
    *   Formulario de registro de clientes.
    *   Portal de Cliente: Vista de "Solicitar Alquiler".
*   **Móvil:**
    *   Vistas de "Mis Alquileres" y "Nuevo Alquiler".

### Ciclo 3: Gestión Financiera y Dashboards
*   **Historias:**
    *   `HU-05: Generación de Factura`
    *   `HU-09: Consultar Facturas (Cliente)`
    *   `HU-04: Ver Dashboard (Empleado)`
    *   `HU-06: Ver Dashboard (Cliente)`
*   **Backend:**
    *   Crear Entidad `Factura`.
    *   Lógica de generación automática de facturas.
    *   Endpoints de reportes/KPIs.
*   **Frontend Web:**
    *   Vistas de Facturación y Dashboards con gráficos/tarjetas.
*   **Móvil:**
    *   Vista de "Mis Facturas".

---

## 4. Fase 3: Estabilización y Entrega

*   **Pruebas de Integración:** Verificar flujo completo (Login -> Alquiler -> Factura).
*   **Documentación Técnica:** Generar Javadoc/Swagger.
*   **Manual de Despliegue Local:** Instrucciones para levantar todo el entorno (DB + Back + Fronts).

---

## 5. Notas Técnicas
*   **Base de Datos:** Se usará el script existente en `docs/diseños`.
*   **Maquetación:** Se reutilizará el HTML/CSS de `MaquiMuWeb/maquetacion` adaptándolo a componentes Angular.
*   **Móvil:** Se continuará el desarrollo sobre la base existente en `MaquiMuMobile`.
