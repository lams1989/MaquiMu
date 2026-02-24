# Plan de Pruebas Manuales — MaquiMu

> **Proyecto:** MaquiMu — Sistema de Gestión de Alquiler de Maquinaria  
> **Fecha:** 2026-02-24  
> **Cobertura:** HU 01 – HU 12, HU 15  
> **Entornos:** Backend `http://localhost:8080/api`, Frontend `http://localhost:4200`

---

## Índice

1. [Prerrequisitos](#1-prerrequisitos)
2. [HU-01 — Configuración Inicial Backend](#2-hu-01--configuración-inicial-backend)
3. [HU-02 — Configuración Inicial Frontend Web](#3-hu-02--configuración-inicial-frontend-web)
4. [HU-03 — Autenticación de Usuario](#4-hu-03--autenticación-de-usuario)
5. [HU-04 — Registro de Maquinaria (CRUD)](#5-hu-04--registro-de-maquinaria-crud)
6. [HU-05 — Registro de Clientes (CRUD)](#6-hu-05--registro-de-clientes-crud)
7. [HU-06 — Solicitar Alquiler (Cliente)](#7-hu-06--solicitar-alquiler-cliente)
8. [HU-07 — Gestionar Alquileres (Operario)](#8-hu-07--gestionar-alquileres-operario)
9. [HU-08 — Consultar Alquileres (Cliente)](#9-hu-08--consultar-alquileres-cliente)
10. [HU-09 — Generación de Factura](#10-hu-09--generación-de-factura)
11. [HU-10 — Consultar Facturas (Cliente)](#11-hu-10--consultar-facturas-cliente)
12. [HU-11 — Dashboard Empleado (Operario)](#12-hu-11--dashboard-empleado-operario)
13. [HU-12 — Dashboard Cliente (Portal)](#13-hu-12--dashboard-cliente-portal)
14. [HU-15 — Estilos UI y Notificaciones](#14-hu-15--estilos-ui-y-notificaciones)
15. [Pruebas Transversales (Seguridad, UX, Navegación)](#15-pruebas-transversales)
16. [Matriz de Trazabilidad](#16-matriz-de-trazabilidad)

---

## 1. Prerrequisitos

| # | Paso | Verificar |
|---|------|-----------|
| 1 | MySQL activo, base de datos `maquimu_db` creada | `SHOW DATABASES;` muestra `maquimu_db` |
| 2 | Backend corriendo | `http://localhost:8080/api` responde (no 404) |
| 3 | Frontend corriendo | `http://localhost:4200` muestra la página de login |
| 4 | Flyway migraciones aplicadas | Tablas `usuario`, `maquinaria`, `cliente`, `alquiler`, `factura` existen |
| 5 | Datos semilla: al menos 1 usuario OPERARIO y 1 CLIENTE registrados | Consultar tabla `usuario` |
| 6 | Navegador: Chrome (recomendado) con DevTools disponibles | F12 abre las herramientas de desarrollo |

**Cuentas de prueba sugeridas:**

| Rol | Email | Contraseña |
|-----|-------|------------|
| Operario (migración Flyway) | `operario@maquimu.com` | `password` |
| Cliente | *(registrar en HU-03 / TC-03.01)* | *(la definida durante el registro)* |

---

## 2. HU-01 — Configuración Inicial Backend

### TC-01.01 — Proyecto compila y arranca correctamente

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Abrir terminal en `maquimu-backend/` | — |
| 2 | Ejecutar `./gradlew build` | BUILD SUCCESSFUL, sin errores de compilación |
| 3 | Ejecutar `./gradlew bootRun` | Aplicación arranca en puerto 8080, log muestra "Started MaquimuApplication" |
| 4 | En navegador ir a `http://localhost:8080/api` | Responde (puede ser 401/403 si no hay token, pero NO timeout ni connection refused) |

### TC-01.02 — Estructura hexagonal multi-módulo

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Verificar carpetas `dominio/`, `aplicacion/`, `infraestructura/` | Las 3 carpetas existen en `maquimu-backend/` |
| 2 | Revisar `settings.gradle` | Incluye los 3 módulos: `dominio`, `aplicacion`, `infraestructura` |
| 3 | `./gradlew dominio:build` | Compila sin errores |
| 4 | `./gradlew aplicacion:build` | Compila sin errores |
| 5 | `./gradlew infraestructura:build` | Compila sin errores |

### TC-01.03 — Conexión a base de datos MySQL

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Ejecutar backend | Al iniciar, log muestra "HikariPool-1 - Starting..." |
| 2 | En MySQL ejecutar `USE maquimu_db; SHOW TABLES;` | Tablas: `usuario`, `maquinaria`, `cliente`, `alquiler`, `factura`, `flyway_schema_history` |
| 3 | Ejecutar `SELECT * FROM flyway_schema_history;` | Existen múltiples migraciones aplicadas exitosamente (success = 1) |

---

## 3. HU-02 — Configuración Inicial Frontend Web

### TC-02.01 — Proyecto Angular compila y arranca

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Abrir terminal en `maquimu-frontend/` | — |
| 2 | Ejecutar `npm install` | Dependencias instaladas sin errores fatales |
| 3 | Ejecutar `npx ng build` | "Application bundle generation complete" sin errores |
| 4 | Ejecutar `npx ng serve` | "Compiled successfully", servidor en `http://localhost:4200` |
| 5 | Abrir `http://localhost:4200` en navegador | Redirige a `/auth/login` (página de inicio de sesión) |

### TC-02.02 — Estructura modular y lazy loading

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Verificar que existen `src/app/auth/`, `src/app/admin/`, `src/app/client/`, `src/app/core/`, `src/app/shared/` | Las 5 carpetas existen |
| 2 | Inspeccionar Network (DevTools) al navegar a `/admin/dashboard` | Se carga un chunk separado (lazy-loaded module `admin-module`) |
| 3 | Inspeccionar Network al navegar a `/client/portal` | Se carga un chunk separado (lazy-loaded module `client-module`) |

### TC-02.03 — Tests unitarios pasan

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Ejecutar `npx ng test --watch=false --browsers=ChromeHeadless` | **157 de 157 tests SUCCESS** |

---

## 4. HU-03 — Autenticación de Usuario

### TC-03.01 — Registro de nuevo usuario (Cliente)

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Navegar a `http://localhost:4200/auth/register` | Formulario de registro visible |
| 2 | Llenar: Nombre completo, Email nuevo, Contraseña (min. 6 caracteres) | Campos editables |
| 3 | Click en "Registrarse" | Mensaje de éxito, redirige a login |
| 4 | Intentar registrar con **mismo email** | Error: "El email ya está registrado" o similar |
| 5 | Dejar campos vacíos y enviar | Validaciones impiden envío (botón deshabilitado o mensajes de error) |

### TC-03.02 — Login exitoso con Operario

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Navegar a `http://localhost:4200/auth/login` | Formulario de login visible |
| 2 | Ingresar email y contraseña del **Operario** | — |
| 3 | Click en "Iniciar Sesión" | Redirige a `/admin/dashboard` |
| 4 | Verificar en DevTools → Application → localStorage | Existe key `token` con un JWT válido |
| 5 | Verificar navbar | Muestra nombre/email del usuario logueado, chip de rol "OPERARIO" |

### TC-03.03 — Login exitoso con Cliente

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Navegar a `http://localhost:4200/auth/login` | Formulario de login |
| 2 | Ingresar email y contraseña del **Cliente** | — |
| 3 | Click en "Iniciar Sesión" | Redirige a `/client/portal` |
| 4 | Verificar navbar | Muestra nombre del usuario, avatar verde |

### TC-03.04 — Login fallido (credenciales inválidas)

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | En login, ingresar email correcto + **contraseña incorrecta** | Mensaje de error: "Credenciales inválidas" o similar |
| 2 | Ingresar **email no registrado** + cualquier contraseña | Mensaje de error similar |
| 3 | Verificar que NO se generó token en localStorage | `token` no existe o permanece sin cambiar |

### TC-03.05 — Logout

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Loguearse como cualquier usuario | Sesión activa, JWT almacenado |
| 2 | Click en botón "Cerrar Sesión" (en navbar) | Redirige a `/auth/login` |
| 3 | Verificar localStorage | Key `token` eliminada |
| 4 | Intentar navegar manualmente a `/admin/dashboard` | Redirige automáticamente a `/auth/login` (AuthGuard) |

### TC-03.06 — Protección de rutas por rol (RoleGuard)

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Loguearse como **Cliente** | — |
| 2 | Navegar manualmente a `/admin/dashboard` | Redirige a `/client/portal` (acceso denegado) |
| 3 | Loguearse como **Operario** | — |
| 4 | Navegar manualmente a `/client/portal` | Redirige a `/admin/dashboard` (acceso denegado) |

### TC-03.07 — Interceptor JWT

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Loguearse exitosamente | — |
| 2 | Abrir DevTools → Network | — |
| 3 | Realizar cualquier acción que llame al API (ej: cargar dashboard) | Las peticiones HTTP llevan header `Authorization: Bearer <token>` |

---

## 5. HU-04 — Registro de Maquinaria (CRUD)

### TC-04.01 — Crear nueva maquinaria

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Login como Operario → ir a `/admin/inventory` | Lista de maquinaria visible (puede estar vacía) |
| 2 | Click en botón "Nueva Maquinaria" | Se abre modal con diseño moderno (header gradiente oscuro, icono de engranaje, subtítulo) |
| 3 | Verificar diseño del modal | Header con gradiente `#0f172a → #1e3a5f`, iconos en cada campo, botón guardar naranja |
| 4 | Llenar todos los campos obligatorios: Nombre, Marca, Modelo, Serial (único), Tarifa/Día, Tarifa/Hora | Campos aceptan la entrada |
| 5 | Dejar Descripción vacía (opcional) | No bloquea el guardado |
| 6 | Click "Guardar Maquinaria" | Modal se cierra, la maquinaria aparece en la lista con estado `DISPONIBLE` |
| 7 | Verificar en BD: `SELECT * FROM maquinaria ORDER BY id DESC LIMIT 1;` | Registrada con los datos ingresados, estado = `DISPONIBLE` |

### TC-04.02 — Validaciones de creación

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Abrir modal de nueva maquinaria | — |
| 2 | No llenar ningún campo y hacer click fuera de cada input | Mensajes de error por campo: "requerido" |
| 3 | Botón "Guardar Maquinaria" | Está **deshabilitado** (formulario inválido) |
| 4 | En Tarifa por Día ingresar `0` o `-5` | Validación: "debe ser mayor que cero" |
| 5 | Ingresar un serial que **ya existe** en BD | Al guardar, error del backend: "Serial duplicado" |

### TC-04.03 — Editar maquinaria existente

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | En la lista de inventario, click en botón "Editar" de una maquinaria | Modal se abre con título "Editar Maquinaria" y datos precargados |
| 2 | Verificar que el campo **Estado** aparece (solo en edición) | Select con opciones: DISPONIBLE, ALQUILADO, EN_MANTENIMIENTO |
| 3 | Cambiar el campo "Marca" a un valor nuevo | — |
| 4 | Click "Actualizar Maquinaria" | Modal se cierra, lista se actualiza con nuevo valor |
| 5 | Verificar en BD | Registro actualizado correctamente |

### TC-04.04 — Eliminar maquinaria

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | En la lista, click en botón "Eliminar" de una maquinaria | Confirmación solicitada (modal/alerta) |
| 2 | Confirmar la eliminación | La maquinaria desaparece de la lista |
| 3 | Verificar en BD | Registro eliminado (eliminación física) |

### TC-04.05 — Listar maquinaria

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Navegar a `/admin/inventory` | Se muestra tabla/lista de toda la maquinaria registrada |
| 2 | Verificar columnas: Nombre, Marca, Modelo, Serial, Tarifa/Día, Tarifa/Hora, Estado, Acciones | Todas las columnas presentes |
| 3 | Verificar que cada fila tiene botones de Editar y Eliminar | Botones funcionales |

---

## 6. HU-05 — Registro de Clientes (CRUD)

### TC-05.01 — Crear nuevo cliente

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Login como Operario → ir a `/admin/clients` | Lista de clientes visible |
| 2 | Click en "Nuevo Cliente" | Modal moderno se abre con header gradiente, subtítulo, icono de persona |
| 3 | Verificar diseño: título en **blanco** sobre fondo oscuro | Título "Registrar Nuevo Cliente" visible claramente en blanco |
| 4 | Seleccionar **Tipo de Cliente**: "Persona Jurídica" o "Persona Natural" | Radios funcionales, label del nombre cambia según tipo |
| 5 | Llenar: Nombre/Razón Social, Identificación, Email, Teléfono, Dirección | Campos con iconos (persona, tarjeta, sobre, teléfono, ubicación) |
| 6 | Verificar checkbox **"El cliente autoriza el tratamiento de datos personales"** | Checkbox visible en caja destacada |
| 7 | **NO** marcar el checkbox e intentar guardar | Botón "Guardar Cliente" está **deshabilitado** |
| 8 | Marcar el checkbox | Botón se habilita |
| 9 | Click "Guardar Cliente" | Modal cierra, cliente aparece en la lista |

### TC-05.02 — Validaciones de creación de cliente

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Abrir modal, dejar todos los campos vacíos, tocar y salir de cada campo | Mensajes de validación: "requerido" en nombre, identificación, email, teléfono, dirección |
| 2 | Ingresar email inválido (ej: `test@`) | Mensaje: "correo electrónico válido" |
| 3 | Ingresar una **identificación ya existente** y guardar | Error del backend: "Identificación duplicada" |

### TC-05.03 — Editar cliente existente

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Click en "Editar" de un cliente existente | Modal con título "Editar Cliente", datos precargados |
| 2 | Verificar que el **tipo de cliente** NO aparece (solo en creación) | Radio de tipo oculto |
| 3 | Verificar que la **casilla de autorización** NO aparece (solo en creación) | Checkbox oculto |
| 4 | Modificar el teléfono y guardar | Lista actualizada con nuevo teléfono |

### TC-05.04 — Eliminar cliente

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Click en "Eliminar" de un cliente | Confirmación |
| 2 | Confirmar | Cliente removido de la lista |

### TC-05.05 — Listar clientes

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Navegar a `/admin/clients` | Tabla con clientes: Nombre, Identificación, Email, Teléfono, Dirección, Acciones |
| 2 | Verificar que hay al menos un cliente | Datos visibles y correctos |

---

## 7. HU-06 — Solicitar Alquiler (Cliente)

### TC-06.01 — Flujo completo de solicitud de alquiler (3 pasos)

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Login como **Cliente** → navegar a `/client/request-rental` | Formulario de solicitud visible, paso 1: Fechas |
| 2 | **Paso 1 — Fechas:** Seleccionar fecha de inicio (futura) y fecha de fin (posterior a inicio) | Campos aceptan fechas válidas |
| 3 | Intentar poner fecha fin **anterior** a fecha inicio | Validación: fechas incoherentes |
| 4 | Click "Siguiente" o "Continuar" | Avanza al Paso 2: Selección de maquinaria |
| 5 | **Paso 2 — Selección:** Se muestran las maquinarias **disponibles** para esas fechas | Solo maquinaria con estado compatible |
| 6 | Seleccionar una maquinaria | Se muestra el cálculo automático del costo (basado en tarifa × días) |
| 7 | Click "Siguiente" | Avanza al Paso 3: Confirmación |
| 8 | **Paso 3 — Confirmación:** Resumen con máquina, fechas, costo estimado | Datos correctos |
| 9 | Click "Confirmar Solicitud" | Mensaje de éxito, redirige a "Mis Alquileres" |
| 10 | Verificar en BD: `SELECT * FROM alquiler ORDER BY id DESC LIMIT 1;` | Estado = `PENDIENTE`, costos calculados correctamente |

### TC-06.02 — Cálculo automático de costo

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Seleccionar fechas que abarquen **3 días** | — |
| 2 | Seleccionar maquinaria con tarifa/día = $100.000 | — |
| 3 | Verificar costo estimado en paso de confirmación | Costo = $300.000 (3 × $100.000) |

### TC-06.03 — No mostrar maquinaria no disponible

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Si hay una maquinaria en estado `EN_MANTENIMIENTO` | — |
| 2 | Ir al paso de selección de maquinaria | Esa maquinaria **NO aparece** en la lista de disponibles |

---

## 8. HU-07 — Gestionar Alquileres (Operario)

### TC-07.01 — Listar alquileres por estado

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Login como Operario → ir a `/admin/rentals` | Lista de alquileres visible |
| 2 | Filtrar por estado "PENDIENTE" | Solo se muestran alquileres con estado PENDIENTE |
| 3 | Filtrar por "APROBADO" | Solo APROBADOS |
| 4 | Filtrar por "ACTIVO" | Solo ACTIVOS |
| 5 | Sin filtro | Todos los alquileres |

### TC-07.02 — Aprobar alquiler PENDIENTE

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Ubicar un alquiler en estado `PENDIENTE` | Botones visibles: "Aprobar" y "Rechazar" |
| 2 | Click "Aprobar" | Confirmación solicitada |
| 3 | Confirmar | Estado cambia a `APROBADO` |
| 4 | Verificar en BD | `estado = 'APROBADO'` en tabla `alquiler` |

### TC-07.03 — Rechazar alquiler PENDIENTE

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Ubicar un alquiler en estado `PENDIENTE` | — |
| 2 | Click "Rechazar" | Modal de rechazo con campo de motivo (opcional) |
| 3 | Ingresar motivo: "Maquinaria en mantenimiento" y confirmar | Estado cambia a `RECHAZADO` o `CANCELADO` |
| 4 | Verificar que el motivo se guardó | Visible en detalle del alquiler |

### TC-07.04 — Entregar maquinaria (APROBADO → ACTIVO)

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Ubicar un alquiler en estado `APROBADO` | Botón "Entregar" visible |
| 2 | Click "Entregar" | Confirmación |
| 3 | Confirmar | Alquiler cambia a `ACTIVO` |
| 4 | Verificar maquinaria asociada | Estado de la maquinaria cambia a `ALQUILADO` |

### TC-07.05 — Finalizar alquiler (ACTIVO → FINALIZADO)

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Ubicar un alquiler en estado `ACTIVO` | Botón "Finalizar" visible |
| 2 | Click "Finalizar" | Confirmación |
| 3 | Confirmar | Alquiler cambia a `FINALIZADO` |
| 4 | Verificar maquinaria | Estado vuelve a `DISPONIBLE` |
| 5 | Verificar que aparece botón "Facturar" | Sí, visible para alquileres FINALIZADOS |

### TC-07.06 — Máquina de estados: acciones contextuales

| Estado Inicial | Acciones Permitidas | Acciones NO visibles |
|----------------|--------------------|-----------------------|
| `PENDIENTE` | Aprobar, Rechazar | Entregar, Finalizar, Facturar |
| `APROBADO` | Entregar | Aprobar, Rechazar, Finalizar, Facturar |
| `ACTIVO` | Finalizar | Aprobar, Rechazar, Entregar, Facturar |
| `FINALIZADO` | Facturar | Aprobar, Rechazar, Entregar, Finalizar |
| `CANCELADO` | (ninguna) | Todas |

---

## 9. HU-08 — Consultar Alquileres (Cliente)

### TC-08.01 — Listar mis alquileres

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Login como **Cliente** → ir a `/client/my-rentals` | Lista de alquileres **propios** del cliente |
| 2 | Verificar que NO se muestran alquileres de otros clientes | Solo alquileres vinculados al `clienteId` del JWT |
| 3 | Verificar columnas: Maquinaria, Fecha Inicio, Fecha Fin, Costo, Estado | Datos visibles y correctos |

### TC-08.02 — Filtrar por estado

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Usar filtro de estado (si existe) | Solo se muestran alquileres del estado seleccionado |
| 2 | Seleccionar "Todos" | Se muestran todos los alquileres (activos + históricos) |

### TC-08.03 — Ver detalle de alquiler

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Click en un alquiler de la lista (o botón "Ver detalle") | Modal de detalle con información completa |
| 2 | Verificar datos: Maquinaria, Marca, Modelo, Fechas, Costo Total, Estado | Todos los campos correctos |
| 3 | Si fue rechazado, verificar que se muestra el **motivo de rechazo** | Motivo visible |

### TC-08.04 — Seguridad: no acceder a alquileres ajenos

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Con Postman/curl, hacer `GET /alquileres/mis-alquileres/{id}` con ID de alquiler de **otro** cliente | Respuesta `403 Forbidden` |
| 2 | Usar el JWT de ClienteA para consultar alquiler de ClienteB | Acceso denegado |

---

## 10. HU-09 — Generación de Factura

### TC-09.01 — Generar factura desde alquiler finalizado

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Login como Operario → `/admin/rentals` | — |
| 2 | Ubicar un alquiler en estado `FINALIZADO` | Botón "Facturar" visible |
| 3 | Click "Facturar" | Factura generada exitosamente |
| 4 | Navegar a `/admin/financial` | Factura aparece en la lista con estado `PENDIENTE` |
| 5 | Verificar en BD: `SELECT * FROM factura ORDER BY id DESC LIMIT 1;` | `estado = 'PENDIENTE'`, `alquiler_id` correcto, monto correcto |

### TC-09.02 — Intentar facturar alquiler NO finalizado

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Verificar que alquileres en estado PENDIENTE/APROBADO/ACTIVO | **NO tienen** botón "Facturar" |
| 2 | Con Postman, `POST /facturas` con alquiler_id de un alquiler ACTIVO | Error 400/422: "El alquiler debe estar FINALIZADO" |

### TC-09.03 — Listar facturas (módulo financiero)

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Navegar a `/admin/financial` | Lista de facturas con: ID, Cliente, Monto, Fecha, Estado |
| 2 | Verificar filtros (si existen) | Filtrado por estado funcional |

### TC-09.04 — Cambiar estado de factura

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | En la lista de facturas, ubicar una con estado `PENDIENTE` | — |
| 2 | Cambiar estado a `PAGADO` | Estado se actualiza en la interfaz |
| 3 | Verificar en BD | `estado = 'PAGADO'` |
| 4 | Cambiar a `ANULADO` | Estado cambia correctamente |

### TC-09.05 — Descargar factura en PDF

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | En la lista de facturas, click en botón "Descargar PDF" de una factura | Se descarga un archivo `.pdf` |
| 2 | Abrir el PDF | Contiene datos de la factura: cliente, maquinaria, fechas, monto, estado |

### TC-09.06 — Ver detalle de factura

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Click en una factura para ver detalle | Modal con información: Número, Cliente, Alquiler, Monto, Fecha, Estado |
| 2 | Verificar que los datos coinciden con el alquiler asociado | Monto = costo del alquiler |

---

## 11. HU-10 — Consultar Facturas (Cliente)

### TC-10.01 — Listar mis facturas

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Login como **Cliente** → ir a `/client/my-invoices` | Lista de facturas **propias** del cliente |
| 2 | Verificar que solo se muestran facturas del cliente logueado | No aparecen facturas de otros clientes |
| 3 | Verificar columnas: Número, Monto, Fecha Emisión, Estado de Pago | Datos correctos |

### TC-10.02 — Ver detalle de factura como cliente

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Click en una factura | Modal detalle con toda la información |
| 2 | Verificar datos del alquiler asociado | Maquinaria, fechas, costo |

### TC-10.03 — Descargar PDF como cliente

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Click en "Descargar PDF" en una factura propia | PDF se descarga correctamente |
| 2 | Contenido del PDF | Datos de la factura correctos |

### TC-10.04 — Seguridad: no descargar PDF de otro cliente

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Con Postman, `GET /facturas/mis-facturas/{id}/pdf` con ID de factura de **otro** cliente | Respuesta `403 Forbidden` |

---

## 12. HU-11 — Dashboard Empleado (Operario)

### TC-11.01 — Visualizar KPIs del dashboard

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Login como **Operario** → redirige a `/admin/dashboard` | Dashboard visible con tarjetas KPI |
| 2 | Verificar tarjeta **"Maquinaria Disponible"** | Número = cantidad de maquinaria con estado `DISPONIBLE` en BD |
| 3 | Verificar tarjeta **"Alquileres Activos"** | Número = cantidad de alquileres con estado `ACTIVO` en BD |
| 4 | Verificar tarjeta **"Facturas Pendientes"** | Número = cantidad de facturas con estado `PENDIENTE` en BD |

### TC-11.02 — Datos se actualizan al refrescar

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Anotar los valores actuales del dashboard | — |
| 2 | En otra pestaña (o BD directamente), crear una nueva maquinaria | — |
| 3 | Refrescar la página del dashboard (F5) | El conteo de "Maquinaria Disponible" se incrementó en 1 |

### TC-11.03 — Enlaces de acceso rápido

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Verificar que el dashboard tiene enlaces a Inventario, Alquileres, Financiero | Links visibles |
| 2 | Click en enlace de Inventario | Navega a `/admin/inventory` |
| 3 | Click en enlace de Alquileres | Navega a `/admin/rentals` |

### TC-11.04 — Manejo de errores

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Detener el backend | — |
| 2 | Refrescar el dashboard | Se muestra un mensaje de error (no pantalla rota), spinner desaparece |

---

## 13. HU-12 — Dashboard Cliente (Portal)

### TC-12.01 — Visualizar portal del cliente

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Login como **Cliente** → redirige a `/client/portal` | Portal visible con mensaje de bienvenida |
| 2 | Verificar tarjeta **"Alquileres Activos"** | Número = alquileres ACTIVOS del cliente logueado |
| 3 | Verificar tarjeta **"Facturas Pendientes"** | Número = facturas PENDIENTES del cliente logueado |

### TC-12.02 — Enlaces de acceso rápido

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Verificar enlace a "Mis Alquileres" | Navega a `/client/my-rentals` |
| 2 | Verificar enlace a "Mis Facturas" | Navega a `/client/my-invoices` |
| 3 | Verificar enlace a "Solicitar Alquiler" | Navega a `/client/request-rental` |

### TC-12.03 — Datos exclusivos del cliente logueado

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Verificar en BD cuántos alquileres activos tiene el cliente actual | — |
| 2 | Comparar con el portal | Número coincide exactamente |
| 3 | Loguearse con **otro** cliente | Números reflejan los datos de ese otro cliente |

---

## 14. HU-15 — Estilos UI y Notificaciones

### TC-15.01 — Navbar Admin: diseño dark profesional

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Login como Operario | — |
| 2 | Verificar navbar superior | Fondo oscuro (`#0f172a`), no azul Bootstrap |
| 3 | Verificar logo "MaquiMu" | Gradiente oscuro (`#1e3a5f → #2d5a87`), NO azul brillante |
| 4 | Verificar chip de rol | Badge con texto "OPERARIO" visible |
| 5 | Verificar avatar/nombre de usuario | Pill con nombre/email del usuario logueado, avatar con iniciales |
| 6 | Verificar botón de logout | Visible y funcional |

### TC-15.02 — Navbar Cliente: diseño dark profesional

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Login como Cliente | — |
| 2 | Verificar navbar | Mismo estilo dark, logo con gradiente oscuro |
| 3 | Verificar pill de usuario | Nombre visible, avatar verde (diferente al azul/morado del admin) |

### TC-15.03 — Sidebar Admin: secciones categorizadas

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Login como Operario | — |
| 2 | Verificar sidebar izquierdo | Fondo oscuro (`#1e293b`) |
| 3 | Verificar etiquetas de sección | "Principal", "Gestión", "Finanzas" (u otros labels de sección) |
| 4 | Verificar iconos con colores | Cada item tiene icono en wrapper coloreado |
| 5 | Navegar a "Inventario" | Barra azul a la izquierda del item activo (indicador de ruta) |
| 6 | Navegar a "Dashboard" | El indicador azul se mueve al item correcto |

### TC-15.04 — Campana de notificaciones: apertura y cierre

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Login como Operario | — |
| 2 | Verificar icono de campana (🔔) en navbar | Visible |
| 3 | Click en la campana | Dropdown de notificaciones se abre |
| 4 | Click fuera del dropdown o click de nuevo en la campana | Se cierra |

### TC-15.05 — Notificaciones generadas automáticamente (Operario)

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Login como Operario en una pestaña | Dashboard visible |
| 2 | En otra pestaña, login como Cliente y **solicitar un nuevo alquiler** | Alquiler creado con estado PENDIENTE |
| 3 | Esperar ~30 segundos (ciclo de polling) | — |
| 4 | Volver a la pestaña del Operario, verificar campana | **Badge con número** de notificaciones no leídas aparece |
| 5 | Click en campana | Notificación: "Nueva solicitud de alquiler pendiente" o similar |

### TC-15.06 — Notificaciones generadas automáticamente (Cliente)

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Login como Cliente en una pestaña | — |
| 2 | En otra pestaña como Operario, **aprobar** un alquiler del cliente | — |
| 3 | Esperar ~30 segundos | — |
| 4 | Volver a pestaña del Cliente | Badge de notificación aparece |
| 5 | Verificar notificación | "Tu alquiler ha sido aprobado" o cambio de estado |
| 6 | Repetir: Operario **genera una factura** para un alquiler del cliente | — |
| 7 | Esperar y verificar | Notificación de nueva factura |

### TC-15.07 — Badge con contador de no leídas

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Generar 3 notificaciones nuevas | — |
| 2 | Verificar badge en la campana | Muestra "3" (o el número correcto) |
| 3 | Abrir dropdown y marcar una como leída | Badge baja a "2" |
| 4 | Click "Marcar todas como leídas" | Badge desaparece (0 no leídas) |

### TC-15.08 — Persistencia en localStorage

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Generar algunas notificaciones | — |
| 2 | Verificar en DevTools → Application → localStorage | Existe key con las notificaciones almacenadas |
| 3 | Refrescar la página (F5) | Las notificaciones **persisten** y se muestran de nuevo |
| 4 | Cerrar sesión y volver a iniciar | Las notificaciones siguen ahí |

### TC-15.09 — Navegación desde notificación

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Tener una notificación de alquiler | — |
| 2 | Click sobre la notificación | Navega a la vista de alquileres (Operario: `/admin/rentals`, Cliente: `/client/my-rentals`) |
| 3 | Tener una notificación de factura | — |
| 4 | Click sobre ella | Navega a facturas (Operario: `/admin/financial`, Cliente: `/client/my-invoices`) |

### TC-15.10 — Modal cliente: título visible

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Ir a `/admin/clients`, click "Nuevo Cliente" | Modal se abre |
| 2 | Verificar título "Registrar Nuevo Cliente" | **Texto en color blanco**, claramente legible sobre fondo oscuro |
| 3 | Verificar subtítulo "Ingrese los datos legales y de contacto." | Texto blanco semi-transparente, legible |

### TC-15.11 — Modal maquinaria: diseño moderno

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Ir a `/admin/inventory`, click "Nueva Maquinaria" | Modal moderno se abre |
| 2 | Verificar header | Gradiente oscuro (`#0f172a → #1e3a5f`), icono de engranaje azul claro |
| 3 | Verificar título | "Registrar Nueva Maquinaria" en **blanco** |
| 4 | Verificar subtítulo | "Ingrese la información técnica y tarifas." legible |
| 5 | Verificar campos con iconos | Camión (nombre), Etiqueta (marca), Caja (modelo), Escáner (serial), Moneda (tarifas), Reloj (tarifa hora), Texto (descripción) |
| 6 | Verificar footer | Botón "Cancelar" gris, botón "Guardar Maquinaria" naranja gradiente |
| 7 | Hover sobre botón guardar | Efecto visual (sombra, ligero movimiento hacia arriba) |

---

## 15. Pruebas Transversales

### TC-T.01 — Seguridad: acceso sin autenticar

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Cerrar sesión (o abrir navegador incógnito) | — |
| 2 | Navegar directamente a `/admin/dashboard` | Redirige a `/auth/login` |
| 3 | Navegar directamente a `/client/portal` | Redirige a `/auth/login` |
| 4 | Navegar directamente a `/admin/inventory` | Redirige a `/auth/login` |

### TC-T.02 — Seguridad: API protegida

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Con Postman, `GET /maquinaria` **sin header Authorization** | `401 Unauthorized` o `403 Forbidden` |
| 2 | `GET /maquinaria` con token expirado/inválido | `401 Unauthorized` |
| 3 | `GET /maquinaria` con token válido | `200 OK` con datos |

### TC-T.03 — Responsive: navegación en pantalla pequeña

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Redimensionar navegador a 768px de ancho (tablet) | Layout se adapta, sidebar se colapsa o se oculta |
| 2 | Redimensionar a 375px (móvil) | Formularios y tablas se adaptan, sin scroll horizontal excesivo |
| 3 | Verificar modales en pantalla pequeña | Se muestran centrados y son usables |

### TC-T.04 — Navegación con sidebar

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Login como Operario, verificar sidebar | Items: Dashboard, Inventario, Clientes, Alquileres, Financiero |
| 2 | Click en cada item | Navega a la ruta correcta, indicador azul se actualiza |
| 3 | Login como Cliente | Sidebar: Portal, Solicitar Alquiler, Mis Alquileres, Mis Facturas (o equivalente) |

### TC-T.05 — Manejo de errores de red

| Paso | Acción | Resultado Esperado |
|------|--------|--------------------|
| 1 | Apagar el backend | — |
| 2 | Intentar cargar cualquier módulo | Mensaje de error amigable (no pantalla blanca) |
| 3 | Intentar guardar un formulario | Error mostrado al usuario (no se queda cargando infinitamente) |

---

## 16. Matriz de Trazabilidad

| HU | Caso de Prueba | Criterios de Aceptación Cubiertos |
|----|----------------|-----------------------------------|
| HU-01 | TC-01.01, TC-01.02, TC-01.03 | Proyecto compila, hexagonal, MySQL conectado, migraciones |
| HU-02 | TC-02.01, TC-02.02, TC-02.03 | Angular compila, lazy loading, estructura modular, tests |
| HU-03 | TC-03.01 – TC-03.07 | Registro, login, logout, JWT, RoleGuard, interceptor |
| HU-04 | TC-04.01 – TC-04.05 | CRUD maquinaria, validaciones, serial único, estados |
| HU-05 | TC-05.01 – TC-05.05 | CRUD clientes, autorización datos, validaciones, ID única |
| HU-06 | TC-06.01 – TC-06.03 | Solicitud 3 pasos, cálculo costo, disponibilidad |
| HU-07 | TC-07.01 – TC-07.06 | Listado/filtro, aprobar, rechazar, entregar, finalizar |
| HU-08 | TC-08.01 – TC-08.04 | Mis alquileres, detalle, filtro, seguridad por cliente |
| HU-09 | TC-09.01 – TC-09.06 | Generar, listar, cambiar estado, PDF, detalle |
| HU-10 | TC-10.01 – TC-10.04 | Mis facturas, detalle, PDF, seguridad |
| HU-11 | TC-11.01 – TC-11.04 | KPIs operario, actualización, accesos rápidos, errores |
| HU-12 | TC-12.01 – TC-12.03 | Portal cliente, KPIs, accesos rápidos, datos exclusivos |
| HU-15 | TC-15.01 – TC-15.11 | Navbar dark, sidebar, notificaciones, badges, localStorage, modales |
| Transversal | TC-T.01 – TC-T.05 | Seguridad, responsive, navegación, errores red |

---

> **Total de casos de prueba: 60**  
> **Tiempo estimado de ejecución: 3–4 horas (ejecución completa)**
