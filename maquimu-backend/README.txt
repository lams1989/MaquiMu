================================================================================
                    MaquiMu Backend - API REST
================================================================================

  Sistema de Gestion y Alquiler de Maquinaria Pesada
  Modulo: Backend API REST

================================================================================
  STACK TECNOLOGICO
================================================================================

  - Lenguaje:        Java 17
  - Framework:       Spring Boot 3.2.0
  - Build:           Gradle (Groovy DSL)
  - Base de Datos:   MySQL 8.0
  - Migraciones:     Flyway
  - Seguridad:       Spring Security + JWT (jjwt 0.11.5)
  - PDF:             OpenPDF 1.3.30
  - Email:           Spring Boot Starter Mail
  - Testing:         JUnit 5 + Spring Boot Test

================================================================================
  ARQUITECTURA
================================================================================

  Arquitectura Hexagonal (Puertos y Adaptadores) con CQRS.

  maquimu-backend/
  |
  |-- dominio/            Capa de dominio (entidades, puertos, reglas de negocio)
  |   |-- alquiler/
  |   |-- autenticacion/
  |   |-- cliente/
  |   |-- factura/
  |   +-- maquinaria/
  |
  |-- aplicacion/         Capa de aplicacion (casos de uso, comandos, consultas)
  |   |-- alquiler/
  |   |-- autenticacion/
  |   |-- cliente/
  |   |-- dashboard/
  |   |-- factura/
  |   +-- maquinaria/
  |
  |-- infraestructura/    Capa de infraestructura (adaptadores, controladores, BD)
  |   |-- alquiler/
  |   |-- autenticacion/
  |   |-- cliente/
  |   |-- configuracion/
  |   |-- dashboard/
  |   |-- factura/
  |   |-- maquinaria/
  |   +-- utilidades/
  |
  +-- src/main/           Clase principal MaquimuApplication.java

================================================================================
  MODULOS DEL DOMINIO
================================================================================

  Autenticacion     Login, registro, JWT, roles (OPERARIO/CLIENTE),
                    estados (ACTIVO, PENDIENTE_APROBACION, RECHAZADO, RESTABLECER),
                    restablecimiento de contrasena.

  Maquinaria        CRUD de maquinaria pesada (nombre, tipo, costo diario,
                    estado, descripcion).

  Clientes          Registro y gestion de clientes, aprobacion por operario,
                    identificacion unica, nombre y apellido.

  Alquileres        Solicitud, aprobacion, rechazo, activacion, finalizacion
                    y extension de alquileres. Calculo de costos.

  Facturas          Generacion automatica de facturas al finalizar alquileres,
                    descarga de PDF, estados de pago.

  Dashboard         Metricas para operarios (totales, estados, ingresos) y
                    para clientes (alquileres activos, facturas pendientes).

================================================================================
  ENDPOINTS PRINCIPALES
================================================================================

  Auth:
    POST   /api/maquimu/v1/auth/login
    POST   /api/maquimu/v1/auth/register
    POST   /api/maquimu/v1/auth/solicitar-restablecimiento

  Maquinaria:
    GET    /api/maquimu/v1/maquinarias
    POST   /api/maquimu/v1/maquinarias
    PUT    /api/maquimu/v1/maquinarias/{id}
    DELETE /api/maquimu/v1/maquinarias/{id}

  Clientes:
    GET    /api/maquimu/v1/clientes
    GET    /api/maquimu/v1/clientes/{id}
    PUT    /api/maquimu/v1/clientes/{id}
    PUT    /api/maquimu/v1/clientes/{id}/aprobar
    PUT    /api/maquimu/v1/clientes/{id}/rechazar

  Alquileres:
    GET    /api/maquimu/v1/alquileres
    POST   /api/maquimu/v1/alquileres
    PUT    /api/maquimu/v1/alquileres/{id}/aprobar
    PUT    /api/maquimu/v1/alquileres/{id}/rechazar
    PUT    /api/maquimu/v1/alquileres/{id}/activar
    PUT    /api/maquimu/v1/alquileres/{id}/finalizar
    PUT    /api/maquimu/v1/alquileres/{id}/solicitar-extension
    PUT    /api/maquimu/v1/alquileres/{id}/aprobar-extension
    PUT    /api/maquimu/v1/alquileres/{id}/rechazar-extension
    GET    /api/maquimu/v1/alquileres/mis-alquileres

  Facturas:
    GET    /api/maquimu/v1/facturas
    GET    /api/maquimu/v1/facturas/mis-facturas
    GET    /api/maquimu/v1/facturas/mis-facturas/{id}/pdf
    PUT    /api/maquimu/v1/facturas/{id}/pagar

  Dashboard:
    GET    /api/maquimu/v1/dashboard/operario
    GET    /api/maquimu/v1/dashboard/cliente

================================================================================
  REQUISITOS PREVIOS
================================================================================

  - Java 17 (JDK)
  - MySQL 8.0
  - Gradle (incluido via gradlew)

================================================================================
  CONFIGURACION DE BASE DE DATOS
================================================================================

  1. Crear la base de datos:

     mysql -u root -p
     CREATE DATABASE maquimu_db;

  2. Configurar credenciales en:
     infraestructura/src/main/resources/application.properties

     spring.datasource.url=jdbc:mysql://localhost:3306/maquimu_db
     spring.datasource.username=root
     spring.datasource.password=<tu_password>

  Las migraciones de Flyway se ejecutan automaticamente al iniciar.

================================================================================
  EJECUCION
================================================================================

  Desde la raiz del backend:

    cd maquimu-backend

  Compilar:

    ./gradlew build            (Linux/Mac)
    gradlew.bat build          (Windows)

  Ejecutar:

    ./gradlew bootRun          (Linux/Mac)
    gradlew.bat bootRun        (Windows)

  El servidor inicia en: http://localhost:8080/api

================================================================================
  SEGURIDAD
================================================================================

  - Autenticacion mediante JWT (JSON Web Token).
  - Contrasenas encriptadas con BCrypt.
  - Roles: OPERARIO (gestion completa) y CLIENTE (autoservicio).
  - Interceptor de seguridad en todas las rutas excepto /auth/**.
  - Tokens con expiracion configurable.

================================================================================
  ESTRUCTURA DE BUILD
================================================================================

  settings.gradle define 3 subproyectos:

    rootProject.name = 'maquimu-backend'
    include 'dominio'
    include 'aplicacion'
    include 'infraestructura'

  Dependencias entre modulos:

    infraestructura --> aplicacion --> dominio

  El modulo infraestructura es el que contiene el plugin
  Spring Boot y genera el JAR ejecutable.

================================================================================
  DEPENDENCIAS PRINCIPALES
================================================================================

  spring-boot-starter-web          Servidor web embebido (Tomcat)
  spring-boot-starter-data-jpa     JPA + Hibernate
  spring-boot-starter-security     Spring Security
  spring-boot-starter-validation   Validacion de DTOs
  spring-boot-starter-mail         Envio de emails
  mysql-connector-j                Driver MySQL
  flyway-core + flyway-mysql       Migraciones de BD
  jjwt-api/impl/jackson            JSON Web Tokens
  openpdf                          Generacion de PDFs
  lombok                           Reduccion de boilerplate

================================================================================
