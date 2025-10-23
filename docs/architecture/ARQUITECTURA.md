# Arquitectura de Software: MaquiMu

## 1. Arquitectura General

[cite_start]La arquitectura seleccionada para el proyecto "MaquiMu" es la **Arquitectura Hexagonal (Puertos y Adaptadores)** [cite: 2571-2572].

Este patrón aísla la lógica de negocio central (el núcleo) de las tecnologías externas (UI, base de datos, APIs). Esto se logra mediante:
* [cite_start]**Núcleo (Dominio y Aplicación):** Contiene las entidades (`Cliente`, `Maquinaria`) y los casos de uso (`AlquilerService`)[cite: 2578]. No depende de nada externo.
* [cite_start]**Puertos:** Interfaces definidas por el núcleo (ej. `AlquilerRepositoryPort`) [cite: 2580-2581].
* **Adaptadores:** Implementaciones concretas de los puertos.
    * [cite_start]*Adaptadores Primarios (Entrada):* Controladores API REST (Spring Boot)[cite: 2583], UI (Angular).
    * [cite_start]*Adaptadores Secundarios (Salida):* Clases de persistencia (Spring Data JPA) que implementan los puertos de repositorio[cite: 2584, 2594].

## 2. Pila Tecnológica (Tech Stack)

[cite_start]La pila tecnológica final (Versión 2.0) es [cite: 915-919, 2459-2465, 2471-2474]:

* **Backend:** Java 17, Spring Boot 3
* **Frontend (Web):** Angular 16 (SPA)
* **Base de Datos:** MySQL 8.0
* **Móvil:** Android (XML Nativo)
* **Seguridad:** OAuth 2.0 y HTTPS

## 3. Patrones de Diseño

Dentro de la arquitectura, se aplicarán los siguientes patrones de diseño:

* **Patrón Repositorio (Repository):** Abstrae el acceso a datos. [cite_start]Las interfaces del repositorio (`ClienteRepository`) son los "Puertos" del núcleo, y las implementaciones de Spring Data JPA son los "Adaptadores" [cite: 2593-2594].
* [cite_start]**Capa de Servicio (Service Layer):** Implementa los casos de uso (ej. `AlquilerService`) y orquesta la lógica de negocio, coordinando los repositorios [cite: 2596-2597].
* [cite_start]**DTO (Data Transfer Object):** Se usarán DTOs (ej. `ClienteCreacionDTO`) para la comunicación entre el frontend y la API REST, evitando exponer las entidades del dominio [cite: 2598-2605].

## 4. Vista de Componentes

[cite_start]El sistema se divide en los siguientes componentes lógicos [cite: 2608-2616]:

1.  **Componente Web (Frontend):** Aplicación Angular (SPA) que consume la API REST. [cite_start]Responsable de la UI y UX [cite: 2609-2610].
2.  **Componente API REST (Backend):** Aplicación Spring Boot que expone los endpoints HTTP. [cite_start]Sirve como adaptador primario [cite: 2611-2612].
3.  [cite_start]**Componente Núcleo de Negocio (Backend):** Lógica de negocio pura (Servicios y Entidades), aislada de la tecnología [cite: 2613-2614].
4.  [cite_start]**Componente de Persistencia (Backend):** Implementación de los repositorios usando Spring Data JPA para interactuar con MySQL [cite: 2615-2616].
5.  **Componente Móvil (Android):** Aplicación nativa que consume la misma API REST que el frontend web.

## 5. Vista de Despliegue (AWS Cloud-Native)

[cite_start]El despliegue se realizará en Amazon Web Services (AWS)[cite: 2640]:

* [cite_start]**Frontend (Angular):** Alojado como sitio web estático en **Amazon S3** y distribuido globalmente con **Amazon CloudFront (CDN)**[cite: 2647].
* [cite_start]**Backend (Spring Boot):** Empaquetado en un contenedor **Docker** [cite: 918, 2673] [cite_start]y desplegado en **Amazon ECS (Elastic Container Service)** para escalabilidad [cite: 2648-2649].
* [cite_start]**Base de Datos (MySQL):** Desplegada como un servicio gestionado en **Amazon RDS (Relational Database Service)** para alta disponibilidad y backups automáticos[cite: 917, 2650].
* [cite_start]**Comunicación:** Toda la comunicación se asegurará mediante HTTPS[cite: 806].