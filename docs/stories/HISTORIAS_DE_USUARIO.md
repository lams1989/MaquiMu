# Historias de Usuario (MaquiMu)

Esta lista representa las funcionalidades clave para la versión final del proyecto.

---

### Módulo: Gestión de Inventario y Seguridad

**HU-01: Registro de Maquinaria**
* **Como:** Administrador
* [cite_start]**Quiero:** Registrar nueva maquinaria en el sistema con sus detalles técnicos[cite: 30].
* **Para:** Mantener el inventario actualizado.
* **Criterios de Aceptación:**
    * [cite_start]La maquinaria queda registrada con marca, modelo, serial y tarifas[cite: 1508].
    * La nueva maquinaria es visible en la lista de inventario.
    * El campo `estado` se asigna por defecto (ej. "disponible").

**HU-02: Autenticación de Usuario**
* **Como:** Usuario del sistema (Empleado o Cliente)
* [cite_start]**Quiero:** Iniciar sesión de forma segura con mi email y contraseña[cite: 2259].
* **Para:** Acceder a las funciones y vistas correspondientes a mi rol.
* **Criterios de Aceptación:**
    * [cite_start]Si las credenciales son válidas, soy redirigido a mi dashboard (Dashboard de Empleado [cite: 2271] [cite_start]o Portal de Cliente [cite: 2331]).
    * [cite_start]Si las credenciales son incorrectas, se muestra un mensaje de error[cite: 2265].
    * [cite_start]La comunicación está cifrada (HTTPS)[cite: 2274].

---

### Módulo: Gestión de Clientes y Alquileres

**HU-03: Registro de Clientes**
* **Como:** Agente de Alquiler
* [cite_start]**Quiero:** Registrar y actualizar la información de los clientes[cite: 2298].
* **Para:** Mantener un registro preciso para contratos y facturación.
* **Criterios de Aceptación:**
    * [cite_start]Los datos del cliente (nombre, identificación, email, teléfono, dirección) se guardan en la base de datos[cite: 1502].
    * [cite_start]El sistema no permite registrar un cliente con una identificación (`identificacion`) duplicada[cite: 2303].

**HU-04: Ver Dashboard (Empleado)**
* **Como:** Empleado (Admin o Agente)
* [cite_start]**Quiero:** Ver un panel de control (Dashboard) al iniciar sesión[cite: 2277].
* **Para:** Tener una vista general del estado del negocio.
* **Criterios de Aceptación:**
    * [cite_start]El dashboard muestra KPIs actualizados: Maquinaria Disponible, Contratos Activos y Facturas Pendientes[cite: 2280].

---

### Módulo: Gestión Financiera

**HU-05: Generación de Factura**
* **Como:** Administrador o Agente
* [cite_start]**Quiero:** Generar facturas para los alquileres[cite: 2315].
* **Para:** Controlar los pagos y la contabilidad.
* **Criterios de Aceptación:**
    * [cite_start]La factura se genera asociada a un alquiler (`alquiler_id`)[cite: 1517].
    * [cite_start]La factura se crea con un monto, fecha de emisión y un estado por defecto ('pendiente')[cite: 1517, 1523].
    * [cite_start]La factura generada es visible en el módulo financiero [cite: 2314] [cite_start]y en el portal del cliente[cite: 2334].

---

### Módulo: Portal de Cliente (Web y Móvil)

**HU-06: Ver Dashboard (Cliente)**
* **Como:** Cliente
* [cite_start]**Quiero:** Acceder a mi portal personal (web y móvil) después de iniciar sesión[cite: 428, 2331].
* **Para:** Ver un resumen de mis actividades.
* **Criterios de Aceptación:**
    * El portal me da la bienvenida.
    * [cite_start]Puedo ver un resumen de mis alquileres activos y facturas pendientes[cite: 428, 2332].
    * [cite_start]Tengo acceso a las secciones "Mis Alquileres", "Mis Facturas" y "Solicitar Nuevo Alquiler"[cite: 3297, 3300, 3304].

**HU-07: Solicitar Alquiler (Cliente)**
* **Como:** Cliente
* [cite_start]**Quiero:** Solicitar el alquiler de maquinaria desde el portal web o la app móvil[cite: 431, 2333].
* **Para:** Iniciar un nuevo proyecto de forma rápida y sencilla.
* **Criterios de Aceptación:**
    * [cite_start]Puedo seleccionar el tipo de maquinaria y las fechas deseadas[cite: 2339].
    * El sistema recibe mi solicitud y la registra para aprobación de un agente.
    * Recibo una confirmación de mi solicitud.

**HU-08: Consultar Alquileres (Cliente)**
* **Como:** Cliente
* [cite_start]**Quiero:** Consultar mi historial de alquileres (web y móvil)[cite: 429, 2334].
* **Para:** Tener trazabilidad de mis servicios contratados.
* **Criterios de Aceptación:**
    * [cite_start]Veo una lista de mis alquileres (activos e historial)[cite: 447, 481].
    * Puedo ver los detalles de un alquiler específico.

**HU-09: Consultar Facturas (Cliente)**
* **Como:** Cliente
* [cite_start]**Quiero:** Consultar mis facturas y su estado de pago (web y móvil)[cite: 430, 2334].
* **Para:** Mantener control sobre mis pagos.
* **Criterios de Aceptación:**
    * [cite_start]Veo una lista de mis facturas con su monto y estado de pago[cite: 484, 2338].
    * Puedo ver el detalle de una factura específica.