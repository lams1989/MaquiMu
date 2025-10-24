# Arquitectura de Software: MaquiMu

## 1. Arquitectura General

[cite_start]La arquitectura seleccionada para el proyecto "MaquiMu" es la **Arquitectura Hexagonal (Puertos y Adaptadores)**[cite: 2654].

Este patrón aísla la lógica de negocio central (el núcleo) de las tecnologías externas (UI, base de datos, APIs). Esto se logra mediante:
* **Núcleo (Dominio y Aplicación):** Contiene las entidades (`Cliente`, `Maquinaria`) y los casos de uso (`AlquilerService`). [cite_start]No depende de nada externo[cite: 2660].
* [cite_start]**Puertos:** Interfaces definidas por el núcleo (ej. `AlquilerRepositoryPort`)[cite: 2662].
* [cite_start]**Adaptadores:** Implementaciones concretas de los puertos[cite: 2664].
    * [cite_start]*Adaptadores Primarios (Entrada):* Controladores API REST (Spring Boot), UI (Angular), UI Móvil (Android)[cite: 2665].
    * [cite_start]*Adaptadores Secundarios (Salida):* Clases de persistencia (Spring Data JPA) que implementan los puertos de repositorio[cite: 2666].

## 2. Pila Tecnológica (Tech Stack)

[cite_start]La pila tecnológica final (Versión 2.0) es [cite: 681-685]:

* [cite_start]**Backend:** Java 17, Spring Boot 3[cite: 681, 2486].
* [cite_start]**Frontend (Web):** Angular 16 (SPA)[cite: 682, 2488].
* [cite_start]**Móvil:** Android (XML Nativo)[cite: 3441].
* [cite_start]**Base de Datos:** MySQL 8.0[cite: 683, 2490].
* [cite_start]**Seguridad:** OAuth 2.0 y HTTPS[cite: 685, 2500].

## 3. Patrones de Diseño

Dentro de la arquitectura, se aplicarán los siguientes patrones de diseño:

* **Patrón Repositorio (Repository):** Abstrae el acceso a datos. [cite_start]Las interfaces del repositorio (`ClienteRepository`) son los "Puertos" del núcleo, y las implementaciones de Spring Data JPA son los "Adaptadores"[cite: 2675, 2676].
* [cite_start]**Capa de Servicio (Service Layer):** Implementa los casos de uso (ej. `AlquilerService`) y orquesta la lógica de negocio, coordinando los repositorios[cite: 2678].
* [cite_start]**DTO (Data Transfer Object):** Se usarán DTOs (ej. `ClienteCreacionDTO`) para la comunicación entre los clientes (web/móvil) y la API REST, evitando exponer las entidades del dominio[cite: 2680, 2681].

## 4. Vista de Componentes

[cite_start]El sistema se divide en los siguientes componentes lógicos[cite: 2689]:

1.  **Componente Web (Frontend):** Aplicación Angular (SPA) que consume la API REST. [cite_start]Responsable de la UI y UX (Gestión Interna y Portal Cliente)[cite: 2691].
2.  **Componente API REST (Backend):** Aplicación Spring Boot que expone los endpoints HTTP. [cite_start]Sirve como adaptador primario[cite: 2693].
3.  [cite_start]**Componente Núcleo de Negocio (Backend):** Lógica de negocio pura (Servicios y Entidades), aislada de la tecnología[cite: 2695].
4.  [cite_start]**Componente de Persistencia (Backend):** Implementación de los repositorios usando Spring Data JPA para interactuar con MySQL[cite: 2697].
5.  **Componente Móvil (Android):** Aplicación nativa que consume la misma API REST que el frontend web. [cite_start]Es un cliente enfocado en el autoservicio[cite: 3414, 3424].

## 5. Vista de Despliegue (AWS Cloud-Native)

[cite_start]El despliegue se realizará en Amazon Web Services (AWS)[cite: 684]:

* [cite_start]**Frontend (Angular):** Alojado como sitio web estático en **Amazon S3** y distribuido globalmente con **Amazon CloudFront (CDN)**[cite: 2729].
* [cite_start]**Backend (Spring Boot):** Empaquetado en un contenedor **Docker** y desplegado en **Amazon ECS (Elastic Container Service)** para escalabilidad[cite: 2730, 2731].
* [cite_start]**Base de Datos (MySQL):** Desplegada como un servicio gestionado en **Amazon RDS (Relational Database Service)** para alta disponibilidad y backups automáticos[cite: 683, 2732].
* **Comunicación:** Toda la comunicación se asegurará mediante HTTPS.