# Proyecto: MaquiMu - Gestión y Alquiler de Maquinaria Pesada

[cite_start]Este proyecto tiene como objetivo el desarrollo de un sistema integral de software para la gestión administrativa y operativa del alquiler de maquinaria pesada[cite: 560, 571]. [cite_start]El sistema, llamado "MaquiMu", optimizará los procesos de inventario, alquileres, clientes y facturación [cite: 864-867].

La solución final consta de tres componentes principales:
1.  [cite_start]**Aplicación Web (Gestión Interna):** Un panel administrativo para que los empleados (Administradores y Agentes) gestionen las operaciones[cite: 3036, 3138].
2.  [cite_start]**Aplicación Web (Portal de Clientes):** Un portal de autoservicio para que los clientes soliciten alquileres, vean su historial y consulten facturas[cite: 3087, 3154].
3.  [cite_start]**Aplicación Móvil (Android):** Una aplicación nativa para clientes con funcionalidades de autoservicio (consultas y solicitudes) [cite: 2725, 2736-2740].

Este repositorio contiene la definición del proyecto, el contexto, la arquitectura, el modelo de datos y las historias de usuario necesarias para guiar el desarrollo.

## Resumen Técnico

* [cite_start]**Metodología:** Cascada (Waterfall) 
* [cite_start]**Backend:** Java 17 / Spring Boot 3 [cite: 2459]
* [cite_start]**Frontend (Web):** Angular 16 [cite: 2461]
* [cite_start]**Móvil:** Android (XML Nativo) [cite: 1931, 1958]
* [cite_start]**Base de Datos:** MySQL 8.0 [cite: 2463]
* [cite_start]**Arquitectura:** Hexagonal (Puertos y Adaptadores) [cite: 2571-2572]
* [cite_start]**Despliegue:** AWS (ECS, RDS, S3/CloudFront) [cite: 2471, 2647-2650]