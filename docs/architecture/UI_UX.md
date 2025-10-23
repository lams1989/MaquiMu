# Guía de Diseño de Interfaz (UI/UX) - MaquiMu

[cite_start]Este documento define los lineamientos visuales y de experiencia de usuario para las aplicaciones Web (Angular) y Móvil (Android), basados en la fase de maquetación[cite: 19, 1930].

## 1. Guía de Estilo Visual

### Paleta de Colores
[cite_start]La paleta de colores es consistente entre la web y la app móvil para mantener la identidad de marca[cite: 48, 1974].

* [cite_start]**Primario (Corporativo):** `#0D47A1` (Azul) [cite: 49, 1982]
* [cite_start]**Acento (Call-to-Actions):** `#FF6F00` (Naranja) [cite: 50, 1984]
* [cite_start]**Neutros (Texto):** `#424242` (Gris Oscuro) [cite: 51, 1986]
* [cite_start]**Fondos (Web):** `#F5F5F5` (Gris Claro) [cite: 51]
* [cite_start]**Fondos (Móvil):** `#FAFAFA` (Blanco Hueso) [cite: 2002]

### Tipografía (Web)
* [cite_start]**Encabezados:** Montserrat [cite: 58]
* [cite_start]**Cuerpo de Texto:** Lato [cite: 59]

## 2. Maquetas (Mockups) Clave

### Web: Inicio de Sesión (Empleado/Cliente)
* [cite_start]**Diseño:** Formulario simple y centrado[cite: 94].
* [cite_start]**Componentes:** Logo "MU MACHINERY", Título "Iniciar Sesión", campos para "Correo Electrónico" y "Contraseña", y botón "Ingresar" (color Acento Naranja) [cite: 98-103].

### Web: Dashboard de Empleado
* [cite_start]**Layout:** Plantilla de gestión interna con barra de navegación lateral fija (Sidebar) y área de contenido principal [cite: 80-82].
* [cite_start]**Sidebar:** Enlaces a "Dashboard", "Clientes", "Maquinaria", "Alquileres", "Finanzas" [cite: 170-174].
* [cite_start]**Contenido:** Tarjetas (KPIs) para "Maquinaria Disponible", "Alquileres Activos", "Facturas Pendientes" [cite: 178-183].

### Web: Portal de Cliente
* [cite_start]**Layout:** Plantilla simple con encabezado superior (Logo, Nombre de Cliente, "Cerrar Sesión") [cite: 458-460].
* [cite_start]**Contenido:** Título "Su Portal de Cliente"[cite: 461]. [cite_start]Tarjetas de acceso para "Mis Alquileres" [cite: 463][cite_start], "Mis Facturas" [cite: 466] [cite_start]y "Solicitar Nuevo Alquiler" (botón de acento)[cite: 470].

### Móvil (Android): Inicio de Sesión
* [cite_start]**Diseño:** Vertical y limpio, con campos de texto y botón principal grandes para facilitar el tacto[cite: 2042, 2750].

### Móvil (Android): Dashboard de Cliente
* [cite_start]**Layout:** Vista principal con tarjetas de resumen y una barra de navegación inferior (Tab Bar)[cite: 2753].
* [cite_start]**Contenido:** Tarjetas para "Alquileres Activos" y "Facturas Pendientes"[cite: 2752].
* [cite_start]**Navegación:** Tab Bar con iconos para (Inicio, Alquileres, Facturas, Perfil)[cite: 2786, 2789, 2791, 2794].
* [cite_start]**Acción Rápida:** Un Botón de Acción Flotante (FAB) para "Nueva Solicitud"[cite: 2754, 2788].