# Guía de Diseño de Interfaz (UI/UX) - MaquiMu

[cite_start]Este documento define los lineamientos visuales y de experiencia de usuario para las aplicaciones Web (Angular) y Móvil (Android), basados en la fase de maquetación[cite: 2854, 2856].

## 1. Guía de Estilo Visual (Web y Móvil)

### Paleta de Colores
[cite_start]La paleta de colores es consistente para mantener la identidad de marca[cite: 2882, 3457].

* [cite_start]**Primario (Corporativo):** `#0D47A1` (Azul) [cite: 2883, 3465]
* [cite_start]**Acento (Call-to-Actions):** `#FF6F00` (Naranja) [cite: 2884, 3467]
* [cite_start]**Neutros (Texto):** `#424242` (Gris Oscuro) [cite: 2885, 3469]
* [cite_start]**Fondos (Web):** `#F5F5F5` (Gris Claro) [cite: 2885]
* [cite_start]**Fondos (Móvil):** `#FAFAFA` (Blanco Hueso) [cite: 3485]

### Tipografía (Web)
* [cite_start]**Encabezados:** Montserrat [cite: 2892]
* [cite_start]**Cuerpo de Texto:** Lato [cite: 2893]

---

## 2. Diseño Web (Angular)

### Layouts (Plantillas)

1.  **Plantilla de Gestión Interna:** Usada por Empleados (Admin/Agente).
    * [cite_start]**Estructura:** Barra de navegación lateral (Sidebar) fija y un área de contenido principal[cite: 2916, 2919].
    * [cite_start]**Sidebar:** Enlaces a "Dashboard", "Clientes", "Maquinaria", "Alquileres", "Finanzas" [cite: 3004-3008].

2.  **Plantilla de Acceso Público / Portal Cliente:** Usada para Login y el portal de autoservicio del cliente.
    * [cite_start]**Estructura (Login):** Diseño simple y centrado con el formulario[cite: 2928, 2929].
    * [cite_start]**Estructura (Portal):** Encabezado superior simple (Logo, Nombre Cliente, "Cerrar Sesión") y área de contenido[cite: 1373, 3293, 3294].

### Maquetas (Mockups) Clave

* **Web: Inicio de Sesión (Empleado/Cliente)**
    * [cite_start]**Diseño:** Formulario simple y centrado[cite: 2934].
    * [cite_start]**Componentes:** Logo "MU MACHINERY", campos para "Correo Electrónico" y "Contraseña", y botón "Ingresar" (color Acento Naranja) [cite: 2932-2937].

* **Web: Dashboard de Empleado**
    * [cite_start]**Contenido:** Tarjetas (KPIs) para "Maquinaria Disponible", "Alquileres Activos", "Facturas Pendientes" [cite: 3012-3017].

* **Web: Portal de Cliente**
    * [cite_start]**Contenido:** Título "Su Portal de Cliente"[cite: 3295]. [cite_start]Tarjetas de acceso para "Mis Alquileres" [cite: 3297][cite_start], "Mis Facturas" [cite: 3300] [cite_start]y "Solicitar Nuevo Alquiler" (botón de acento)[cite: 3304].

---

## 3. Diseño Móvil (Android)

### Principios de Usabilidad y Accesibilidad
* **Usabilidad:**
    * [cite_start]**"Thumb-Friendly":** Elementos de interacción principal (como la Tab Bar) ubicados en la zona de alcance del pulgar[cite: 457].
    * [cite_start]**Touch Targets:** Botones y elementos táctiles con un tamaño mínimo de 44dp para evitar toques accidentales[cite: 458].
* **Accesibilidad:**
    * [cite_start]**Texto Escalable (sp):** La app respetará el tamaño de fuente definido por el usuario en el sistema operativo[cite: 462, 3438].
    * [cite_start]**Lectores de Pantalla:** Todos los elementos interactivos estarán etiquetados para VoiceOver y TalkBack[cite: 463].

### Layouts y Navegación
* [cite_start]**Maquetación:** Se utiliza XML para definir las vistas de forma declarativa[cite: 3441].
    * [cite_start]`ConstraintLayout`: Para pantallas complejas como el Login[cite: 3451].
    * [cite_start]`LinearLayout`: Para formularios simples[cite: 3453].
    * [cite_start]`RecyclerView`: Para listas como el historial de alquileres[cite: 3454].
* [cite_start]**Navegación Principal:** Se implementa mediante una **Barra de Navegación Inferior (Tab Bar / BottomNavigationView)**[cite: 459, 3506].
    * [cite_start]Tab 1: Inicio (Dashboard del Cliente)[cite: 478].
    * [cite_start]Tab 2: Alquileres (Lista de alquileres)[cite: 480].
    * [cite_start]Tab 3: Facturas (Lista de facturas)[cite: 482].
    * [cite_start]Tab 4: Perfil (Mis Datos, Cerrar Sesión)[cite: 485].
* [cite_start]**Acción Rápida:** Un **Botón de Acción Flotante (FAB)** en el dashboard permite iniciar una "Nueva Solicitud"[cite: 445, 479, 3513].