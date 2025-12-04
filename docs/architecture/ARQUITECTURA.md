# Arquitectura del Sistema MaquiMu

## 1. Visión General

La arquitectura de **MaquiMu** sigue un enfoque **Hexagonal (Puertos y Adaptadores)** implementado sobre un proyecto **Multi-módulo con Gradle**. Este diseño busca desacoplar completamente la lógica de negocio (Dominio) de los detalles técnicos (Infraestructura), facilitando la mantenibilidad, el testing y la evolución del sistema.

Además, se aplica el patrón **CQRS (Command Query Responsibility Segregation)** para separar las operaciones de lectura (Consultas) de las de escritura (Comandos), optimizando el rendimiento y la claridad del código.

---

## 2. Estructura de Módulos

El backend se divide en tres módulos principales, respetando estrictamente la regla de dependencia:
`Infraestructura -> Aplicación -> Dominio`.

### 🟢 Módulo: Dominio (`dominio`)
**Responsabilidad:** Contener la lógica de negocio pura y las reglas del sistema.
**Dependencias:** Ninguna (Puro Java).

*   **Modelo (`modelo`):** Entidades de dominio y Value Objects.
*   **Puertos (`puerto`):** Interfaces que definen los contratos para interactuar con el exterior.
    *   `dao`: Puertos para lectura de datos.
    *   `repositorio`: Puertos para persistencia y modificación de estado.
*   **Servicio (`servicio`):** Lógica de negocio que no pertenece a una entidad específica.

### 🟡 Módulo: Aplicación (`aplicacion`)
**Responsabilidad:** Orquestar los casos de uso del sistema.
**Dependencias:** `dominio`.

*   **CQRS:**
    *   **Comandos (`comando`):** Operaciones que modifican el estado (Crear, Actualizar, Eliminar).
        *   `fabrica`: Construcción de entidades de dominio a partir de comandos.
        *   `manejador`: Orquestación y lógica de ejecución del comando.
    *   **Consultas (`consulta`):** Operaciones de solo lectura.
        *   `fabrica`: Construcción de DTOs de respuesta (opcional).
        *   `manejador`: Orquestación y lógica de ejecución de la consulta.

### 🔴 Módulo: Infraestructura (`infraestructura`)
**Responsabilidad:** Implementar los adaptadores técnicos (BD, API REST, etc.).
**Dependencias:** `aplicacion`, `dominio`, Spring Boot.

*   **Adaptadores (`adaptador`):**
    *   `entidad`: Entidades JPA para mapeo ORM.
    *   `dao`: Implementación de puertos de lectura.
    *   `repositorio`: Implementación de puertos de escritura.
*   **Controladores (`controlador`):**
    *   Controladores REST separados por CQRS (Comando/Consulta).
*   **Configuración (`configuracion`):**
    *   Configuración de Spring (Security, Beans, etc.).

---

## 3. Stack Tecnológico

*   **Lenguaje:** Java 17
*   **Framework:** Spring Boot 3.x
*   **Build Tool:** Gradle (Multi-módulo)
*   **Base de Datos:** MySQL 8.0+
*   **Migraciones:** Flyway
*   **Seguridad:** Spring Security + JWT

---

## 4. Diagrama de Flujo de Datos

1.  **Petición HTTP** llega al `Controlador` (Infraestructura).
2.  El Controlador crea un **Comando/Consulta** (Aplicación).
3.  El Controlador invoca al **Manejador** correspondiente (Aplicación).
4.  El Manejador usa una **Fábrica** (Aplicación) para construir entidades/DTOs a partir del Comando/Consulta.
5.  El Manejador aplica **validaciones de negocio** usando el Dominio.
6.  El Manejador usa un **Puerto** (Dominio) para persistir/leer datos.
7.  El **Adaptador** (Infraestructura) implementa el Puerto y accede a la **Base de Datos**.

**Flujo Simplificado:**
```
HTTP Request → Controlador → Comando/Consulta → Manejador → Fábrica → Entidad/DTO → Puerto → Adaptador → BD
```

---

## 5. Principios Clave

*   **Independencia del Framework:** El dominio no conoce a Spring Boot.
*   **Independencia de la UI:** La API REST es solo un adaptador más.
*   **Independencia de la BD:** El dominio define interfaces, la infraestructura implementa el acceso a MySQL.
*   **Separación de Responsabilidades:** CQRS separa claramente lectura de escritura.
*   **Patrón de Fábricas:** Centraliza la construcción de objetos complejos en la capa de aplicación.