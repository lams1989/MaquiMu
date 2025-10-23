# Contexto del Proyecto MaquiMu

## 1. Objetivo del Proyecto

[cite_start]El objetivo general es desarrollar un software de gestión administrativa para una empresa de alquiler de maquinaria pesada que optimice la gestión de inventario, alquiler, finanzas y clientes, mejorando la eficiencia operativa y la satisfacción del cliente[cite: 1755].

## 2. Alcance del Proyecto

[cite_start]La versión final del proyecto incluye los siguientes módulos y componentes [cite: 863-868]:

### Dentro del Alcance

* [cite_start]**Gestión de Inventario:** CRUD para maquinaria, incluyendo detalles técnicos y estado[cite: 864, 2446].
* [cite_start]**Gestión de Alquileres:** Procesar solicitudes, verificar disponibilidad y generar contratos[cite: 865, 2446].
* [cite_start]**Gestión de Clientes:** CRUD para la información de clientes[cite: 866, 2446].
* [cite_start]**Gestión Financiera:** Generación automática y seguimiento de facturas y pagos[cite: 867, 2446].
* [cite_start]**Seguridad y Usuarios:** Autenticación (OAuth 2.0) [cite: 919] [cite_start]y control de acceso basado en roles (RBAC)[cite: 2448].
* [cite_start]**Portal de Clientes (Web):** Portal de autoservicio para solicitudes e historial[cite: 3087, 3091, 3092].
* [cite_start]**Aplicación Móvil (Android):** Aplicación nativa para clientes con funciones de consulta y solicitud [cite: 2736-2740].

### Fuera del Alcance (Fase Actual)

* [cite_start]**Módulo de Mantenimiento:** La gestión de mantenimientos preventivos y correctivos ha sido explícitamente **excluida** de esta fase del proyecto.
* [cite_start]**Integración ERP/CRM:** La integración con sistemas ERP y CRM externos ha sido retirada de la propuesta final[cite: 875].

## 3. Actores y Roles

El sistema identifica tres roles de usuario principales:

1.  **Administrador (`admin`):** Usuario interno con control total. [cite_start]Gestiona la configuración del sistema y los roles de otros usuarios[cite: 717, 2448].
2.  [cite_start]**Agente (`agente`):** Usuario interno (empleado) que gestiona las operaciones diarias, como el registro de clientes, la gestión de inventario y la creación de alquileres[cite: 717, 2448, 3057].
3.  **Cliente:** Usuario externo (cliente de la empresa). [cite_start]Puede acceder al portal web y la aplicación móvil para[cite: 3093, 2736]:
    * [cite_start]Solicitar nuevos alquileres[cite: 3091, 2740].
    * [cite_start]Consultar su historial de alquileres[cite: 3092, 2738].
    * [cite_start]Consultar sus facturas y estados de pago[cite: 3092, 2739].