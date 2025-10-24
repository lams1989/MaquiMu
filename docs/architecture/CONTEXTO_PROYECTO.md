# Contexto del Proyecto MaquiMu

## 1. Objetivo del Proyecto

El objetivo general es desarrollar un software de gestión administrativa para una empresa de alquiler de maquinaria pesada que optimice la gestión de inventario, alquiler, finanzas y clientes, mejorando la eficiencia operativa y la satisfacción del cliente.

## 2. Alcance del Proyecto

[cite_start]La versión final del proyecto incluye los siguientes módulos y componentes[cite: 629]:

### Dentro del Alcance

* [cite_start]**Gestión de Inventario:** CRUD para maquinaria, incluyendo detalles técnicos y estado[cite: 630].
* [cite_start]**Gestión de Alquileres:** Procesar solicitudes, verificar disponibilidad y generar contratos[cite: 631].
* [cite_start]**Gestión de Clientes:** CRUD para la información de clientes[cite: 632].
* [cite_start]**Gestión Financiera:** Generación automática y seguimiento de facturas y pagos[cite: 633].
* [cite_start]**Seguridad y Usuarios:** Autenticación (OAuth 2.0) y control de acceso basado en roles (RBAC)[cite: 634, 685].
* [cite_start]**Portal de Clientes (Web):** Portal de autoservicio para solicitudes e historial[cite: 2329, 2331].
* [cite_start]**Aplicación Móvil (Android):** Aplicación nativa para clientes con funciones de consulta y solicitud[cite: 424, 3424].

### Fuera del Alcance (Fase Actual)

* [cite_start]**Módulo de Mantenimiento:** La gestión de mantenimientos preventivos y correctivos ha sido explícitamente **excluida** de esta fase del proyecto[cite: 193].
* [cite_start]**Integración ERP/CRM:** La integración con sistemas ERP y CRM externos ha sido retirada de la propuesta final[cite: 641].

## 3. Actores y Roles

El sistema identifica tres roles de usuario principales:

1.  **Administrador (`admin`):** Usuario interno con control total. [cite_start]Gestiona la configuración del sistema y los roles de otros usuarios[cite: 1463].
2.  [cite_start]**Agente (`agente`):** Usuario interno (empleado) que gestiona las operaciones diarias, como el registro de clientes, la gestión de inventario y la creación de alquileres[cite: 1463].
3.  **Cliente:** Usuario externo (cliente de la empresa). Puede acceder al **portal web** y la **aplicación móvil** para:
    * [cite_start]Solicitar nuevos alquileres[cite: 431, 2333].
    * [cite_start]Consultar su historial de alquileres[cite: 429, 2334].
    * [cite_start]Consultar sus facturas y estados de pago[cite: 430, 2334].