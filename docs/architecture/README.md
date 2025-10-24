# Proyecto: MaquiMu - Gestión y Alquiler de Maquinaria Pesada

Este proyecto tiene como objetivo el desarrollo de un sistema integral de software para la gestión administrativa y operativa del alquiler de maquinaria pesada. El sistema, llamado "MaquiMu", optimizará los procesos de inventario, alquileres, clientes y facturación.

La solución final consta de tres componentes principales:
1.  **Aplicación Web (Gestión Interna):** Un panel administrativo (Angular) para que los empleados gestionen las operaciones.
2.  [cite_start]**Aplicación Web (Portal de Clientes):** Un portal de autoservicio (Angular) para que los clientes soliciten alquileres y consulten facturas[cite: 2329, 2331].
3.  [cite_start]**Aplicación Móvil (Android):** Una aplicación nativa (XML/Java) para clientes con funcionalidades de consulta y solicitud[cite: 424, 3424].

Este repositorio contiene la definición del proyecto, el contexto, la arquitectura, el modelo de datos y las historias de usuario necesarias para guiar el desarrollo.

## Resumen Técnico

* **Metodología:** Cascada (Waterfall)
* **Backend:** Java 17 / Spring Boot 3
* **Frontend (Web):** Angular 16
* [cite_start]**Móvil:** Android (XML Nativo) [cite: 3441]
* **Base de Datos:** MySQL 8.0
* [cite_start]**Arquitectura:** Hexagonal (Puertos y Adaptadores) [cite: 2654]
* [cite_start]**Despliegue:** AWS (ECS, RDS, S3/CloudFront) [cite: 2498, 2490]