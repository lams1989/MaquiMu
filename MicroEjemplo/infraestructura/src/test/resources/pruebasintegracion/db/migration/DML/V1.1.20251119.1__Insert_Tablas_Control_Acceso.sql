-- HU503848 - Ajuste copias legados masivas - Parte 2
-- Creación de permiso COPIA_LEGADOS_CREAR para permitir la creación de copias de legados

SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
VALUES (193, 'COPIA_LEGADOS_CREAR', '193', 1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF

--
-- Asignación del permiso al rol MDC-INTEGRACIONES (rolID 7)
--
SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (941, 7, 193, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF
