# Historias de Usuario (MaquiMu)

Esta lista representa las funcionalidades clave para la versión final del proyecto, excluyendo el módulo de mantenimiento.

---

### Módulo: Gestión de Inventario y Seguridad

**HU-01: Registro de Maquinaria**
* [cite_start]**Como:** Administrador [cite: 614]
* **Quiero:** Registrar nueva maquinaria en el sistema con sus detalles técnicos.
* [cite_start]**Para:** Mantener el inventario actualizado[cite: 616].
* **Criterios de Aceptación:**
    * [cite_start]La maquinaria queda registrada con marca, modelo, serial y tarifas[cite: 725].
    * [cite_start]La nueva maquinaria es visible en la lista de inventario[cite: 618].
    * El campo `estado` se asigna por defecto (ej. "disponible").

**HU-02: Autenticación de Usuario**
* [cite_start]**Como:** Usuario del sistema (Empleado o Cliente) [cite: 3018, 3093]
* [cite_start]**Quiero:** Iniciar sesión de forma segura con mi email y contraseña[cite: 3017].
* [cite_start]**Para:** Acceder a las funciones y vistas correspondientes a mi rol[cite: 3015].
* **Criterios de Aceptación:**
    * [cite_start]Si las credenciales son válidas, soy redirigido a mi dashboard (Dashboard de Empleado o Portal de Cliente)[cite: 3029, 3087].
    * [cite_start]Si las credenciales son incorrectas, se muestra un mensaje de error[cite: 3023].
    * [cite_start]La comunicación está cifrada (HTTPS)[cite: 3032].

---

### Módulo: Gestión de Clientes y Alquileres

**HU-03: Registro de Clientes**
* [cite_start]**Como:** Agente de Alquiler [cite: 2274]
* [cite_start]**Quiero:** Registrar y actualizar la información de los clientes [cite: 2276-2277].
* **Para:** Mantener un registro preciso para contratos y facturación.
* **Criterios de Aceptación:**
    * [cite_start]Los datos del cliente (nombre, identificación, email, teléfono, dirección) se guardan en la base de datos[cite: 719].
    * [cite_start]El sistema no permite registrar un cliente con una identificación (`identificacion`) duplicada[cite: 3061].

**HU-04: Ver Dashboard (Empleado)**
* [cite_start]**Como:** Empleado (Admin o Agente) [cite: 3040]
* [cite_start]**Quiero:** Ver un panel de control (Dashboard) al iniciar sesión[cite: 3036].
* **Para:** Tener una vista general del estado del negocio.
* **Criterios de Aceptación:**
    * [cite_start]El dashboard muestra KPIs actualizados: Maquinaria Disponible, Alquileres Activos y Facturas Pendientes [cite: 3038, 178-183].

---

### Módulo: Gestión Financiera

**HU-05: Generación de Factura**
* [cite_start]**Como:** Administrador o Agente [cite: 627]
* [cite_start]**Quiero:** Generar facturas para los alquileres finalizados o en curso[cite: 629].
* [cite_start]**Para:** Controlar los pagos y la contabilidad[cite: 629].
* **Criterios de Aceptación:**
    * [cite_start]La factura se genera asociada a un alquiler (`alquiler_id`)[cite: 733].
    * [cite_start]La factura se crea con un monto, fecha de emisión y un estado por defecto ('pendiente')[cite: 733, 740].
    * [cite_start]La factura generada es visible en el módulo financiero y en el portal del cliente[cite: 632, 3096].

---

### Módulo: Portal de Cliente (Web y Móvil)

**HU-06: Ver Dashboard (Cliente)**
* [cite_start]**Como:** Cliente [cite: 3093, 2737]
* **Quiero:** Acceder a mi portal personal (web y móvil) después de iniciar sesión.
* **Para:** Ver un resumen de mis actividades.
* **Criterios de Aceptación:**
    * [cite_start]El portal me da la bienvenida[cite: 459].
    * [cite_start]Puedo ver un resumen de mis alquileres activos y facturas pendientes[cite: 2737, 2752].
    * [cite_start]Tengo acceso a las secciones "Mis Alquileres", "Mis Facturas" y "Solicitar Nuevo Alquiler"[cite: 463, 466, 470].

**HU-07: Solicitar Alquiler (Cliente)**
* [cite_start]**Como:** Cliente [cite: 621]
* [cite_start]**Quiero:** Solicitar el alquiler de maquinaria desde el portal web o la app móvil[cite: 623, 2740].
* [cite_start]**Para:** Iniciar un nuevo proyecto de forma rápida y sencilla[cite: 471].
* **Criterios de Aceptación:**
    * [cite_start]Puedo seleccionar el tipo de maquinaria y las fechas deseadas[cite: 3097].
    * [cite_start]El sistema recibe mi solicitud y la registra para aprobación de un agente[cite: 624].
    * Recibo una confirmación de mi solicitud.

**HU-08: Consultar Alquileres (Cliente)**
* [cite_start]**Como:** Cliente [cite: 2738]
* [cite_start]**Quiero:** Consultar mi historial de alquileres y el estado de mis alquileres activos[cite: 2738, 3092].
* **Para:** Tener trazabilidad de mis servicios contratados.
* **Criterios de Aceptación:**
    * [cite_start]Veo una lista de mis alquileres (activos e historial)[cite: 2756, 464].
    * [cite_start]Puedo ver los detalles de un alquiler específico (máquina, fechas, costo total)[cite: 2756].

**HU-09: Consultar Facturas (Cliente)**
* [cite_start]**Como:** Cliente [cite: 2739]
* [cite_start]**Quiero:** Consultar mis facturas y su estado de pago (pendiente, pagada)[cite: 2739, 3096].
* **Para:** Mantener control sobre mis pagos.
* **Criterios de Aceptación:**
    * [cite_start]Veo una lista de mis facturas con su monto y estado de pago[cite: 3096, 467].
    * Puedo ver el detalle de una factura específica.