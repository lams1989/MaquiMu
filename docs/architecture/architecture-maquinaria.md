# Arquitectura del Módulo de Maquinaria

## 1. Visión General

El módulo de Maquinaria gestiona el ciclo de vida de los equipos disponibles para alquiler en el sistema MaquiMu. Implementa un patrón CRUD (Crear, Leer, Actualizar, Eliminar) utilizando la arquitectura hexagonal con CQRS (Command Query Responsibility Segregation) para mantener la separación de responsabilidades entre las operaciones de escritura (comandos) y lectura (consultas).

Este módulo es fundamental para el inventario de activos de la empresa, permitiendo a los operadores mantener un registro actualizado de la maquinaria, sus estados y tarifas.

## 2. Componentes Clave

La implementación del módulo de Maquinaria se distribuye en las tres capas arquitectónicas principales: Dominio, Aplicación e Infraestructura.

### 2.1 Capa de Dominio (`dominio/maquinaria`)

Contiene la lógica de negocio pura y las interfaces (puertos) que definen las interacciones con el exterior.

*   **`modelo/Maquinaria.java`**: Entidad de dominio que representa un equipo. Incluye atributos como nombre, marca, modelo, serial, estado, tarifas y descripción. Contiene la lógica de validación de tarifas y métodos para cambiar su estado (`alquilar()`, `devolver()`).
*   **`modelo/EstadoMaquinaria.java`**: Enumeración que define los posibles estados de una maquinaria: `DISPONIBLE`, `ALQUILADO`, `EN_MANTENIMIENTO`.
*   **`puerto/dao/MaquinariaDao.java`**: Interfaz (puerto de entrada/salida) para operaciones de consulta de maquinaria. Define métodos para listar todas las maquinarias, buscar por ID, buscar por serial y verificar la existencia de un serial. Incluye también un método para contar maquinarias por estado (usado en módulos de dashboard).
*   **`puerto/repositorio/MaquinariaRepositorio.java`**: Interfaz (puerto de entrada/salida) para operaciones de escritura de maquinaria. Define métodos para guardar (crear/actualizar) y eliminar maquinaria.

### 2.2 Capa de Aplicación (`aplicacion/maquinaria`)

Orquesta los casos de uso, utilizando los puertos definidos en el dominio. Aquí se implementa el patrón CQRS con comandos y consultas.

*   **`comando/ComandoCrearMaquinaria.java`**: DTO (Data Transfer Object) para la creación de una nueva maquinaria.
*   **`comando/ComandoActualizarMaquinaria.java`**: DTO para la actualización de una maquinaria existente.
*   **`comando/ComandoEliminarMaquinaria.java`**: DTO para la eliminación de una maquinaria por su ID.
*   **`comando/fabrica/FabricaMaquinaria.java`**: Componente encargado de construir o actualizar objetos de dominio `Maquinaria` a partir de los comandos DTO, aplicando reglas de negocio como el estado inicial (`DISPONIBLE`).
*   **`comando/manejador/ManejadorCrearMaquinaria.java`**: Manejador para el `ComandoCrearMaquinaria`. Valida la unicidad del serial y persiste la nueva maquinaria a través del `MaquinariaRepositorio`.
*   **`comando/manejador/ManejadorActualizarMaquinaria.java`**: Manejador para el `ComandoActualizarMaquinaria`. Verifica la existencia de la maquinaria, valida la unicidad del serial si cambia y actualiza la entidad persistiendo los cambios.
*   **`comando/manejador/ManejadorEliminarMaquinaria.java`**: Manejador para el `ComandoEliminarMaquinaria`. Verifica la existencia de la maquinaria y procede con su eliminación física.
*   **`consulta/ConsultaListarMaquinaria.java`**: DTO vacío para solicitar la lista completa de maquinarias.
*   **`consulta/ConsultaBuscarMaquinaria.java`**: DTO para buscar una maquinaria por su ID.
*   **`consulta/manejador/ManejadorListarMaquinaria.java`**: Manejador para el `ConsultaListarMaquinaria`. Obtiene todas las maquinarias a través del `MaquinariaDao`.
*   **`consulta/manejador/ManejadorBuscarMaquinaria.java`**: Manejador para el `ConsultaBuscarMaquinaria`. Busca una maquinaria específica por su ID a través del `MaquinariaDao`.

### 2.3 Capa de Infraestructura (`infraestructura/maquinaria`)

Se encarga de la implementación de los puertos del dominio, la persistencia de datos y la exposición de la funcionalidad a través de interfaces externas (controladores REST).

*   **`adaptador/entidad/MaquinariaEntity.java`**: Entidad JPA que mapea la entidad de dominio `Maquinaria` a la tabla `maquinaria` en la base de datos. Incluye la conversión bidireccional entre `Maquinaria` y `MaquinariaEntity`. El campo `estado` se mapea a la columna `estado_maquinaria` como un `ENUM`.
*   **`adaptador/repositorio/JpaMaquinariaRepository.java`**: Interfaz de Spring Data JPA que extiende `JpaRepository` para proporcionar operaciones CRUD básicas con `MaquinariaEntity`. Define métodos específicos como `findBySerial`, `existsBySerial` y `countByEstado`.
*   **`adaptador/dao/JpaMaquinariaDao.java`**: Implementación del puerto `MaquinariaDao` utilizando `JpaMaquinariaRepository`. Realiza las consultas a la base de datos y convierte `MaquinariaEntity` a `Maquinaria` (dominio).
*   **`adaptador/repositorio/JpaMaquinariaRepositorio.java`**: Implementación del puerto `MaquinariaRepositorio` utilizando `JpaMaquinariaRepository`. Realiza las operaciones de guardar y eliminar en la base de datos, convirtiendo `Maquinaria` (dominio) a `MaquinariaEntity`.
*   **`controlador/ComandoControladorMaquinaria.java`**: Controlador REST que expone los endpoints para las operaciones de escritura (POST, PUT, DELETE) de maquinaria. Delega en los manejadores de comandos correspondientes.
    *   `POST /api/maquimu/v1/maquinaria`: Crea una nueva maquinaria.
    *   `PUT /api/maquimu/v1/maquinaria/{id}`: Actualiza una maquinaria existente.
    *   `DELETE /api/maquimu/v1/maquinaria/{id}`: Elimina una maquinaria.
*   **`controlador/ConsultaControladorMaquinaria.java`**: Controlador REST que expone los endpoints para las operaciones de lectura (GET) de maquinaria. Delega en los manejadores de consultas correspondientes.
    *   `GET /api/maquimu/v1/maquinaria`: Lista todas las maquinarias.
    *   `GET /api/maquimu/v1/maquinaria/{id}`: Busca una maquinaria por ID.

## 3. Flujo de Ejemplo: Creación de Maquinaria

1.  Una petición `POST` llega a `/api/maquimu/v1/maquinaria` con los datos en el `ComandoCrearMaquinaria`.
2.  `ComandoControladorMaquinaria` recibe la petición y delega en `ManejadorCrearMaquinaria`.
3.  `ManejadorCrearMaquinaria` consulta `MaquinariaDao` para verificar la unicidad del serial.
4.  Si el serial es único, `ManejadorCrearMaquinaria` usa `FabricaMaquinaria` para crear una entidad `Maquinaria` de dominio.
5.  La entidad `Maquinaria` de dominio se pasa a `MaquinariaRepositorio` para su persistencia.
6.  `JpaMaquinariaRepositorio` convierte la entidad de dominio `Maquinaria` a `MaquinariaEntity` y la guarda en la base de datos a través de `JpaMaquinariaRepository`.
7.  Se retorna la `Maquinaria` creada con su ID generado.

## 4. Consideraciones y Futuras Mejoras

*   **Manejo de Errores:** Actualmente, las excepciones se manejan a nivel de controlador con un `IllegalArgumentException`. Se podría implementar un `@ControllerAdvice` global para un manejo más uniforme y mensajes de error estandarizados.
*   **Validaciones:** Las validaciones de negocio están en el dominio (`Maquinaria.java`) y en los manejadores. Se pueden añadir validaciones a nivel de DTO (con `@Valid` y anotaciones de Jakarta Validation) para una validación temprana.
*   **Paginación y Filtros:** Las consultas actuales solo permiten listar todas o buscar por ID. Para grandes volúmenes de datos, se requerirá implementar paginación y filtros más avanzados.
*   **Eliminación Lógica:** Actualmente, la eliminación es física. Se podría considerar una eliminación lógica (marcar como inactivo) para mantener el historial.
*   **Frontend y Mobile:** Este documento se centra en el backend. Las implementaciones de interfaz de usuario para este módulo en Angular y Android consumirían estos endpoints.

---

**Fecha de Creación:** 2025-12-05
**Última Actualización:** 2025-12-05
**Autor:** Gemini Dev Assistant
