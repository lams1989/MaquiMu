-- HU466777
-- Creación de permisos para gestionar ejecución proceso masivo
--
SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
VALUES (191, 'CABECERA_PROCESO_MASIVO_CANCELAR', '191', 1);

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
VALUES (192, 'CABECERA_PROCESO_MASIVO_REINTENTAR', '192', 1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF

--
-- Asignación de los permisos creados a los roles de ideal y técnico.
--
SET IDENTITY_INSERT controlAcceso.RolPermiso ON

--- CABECERA_PROCESO_MASIVO_CANCELAR - XM_G_SO-DespIdeal
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (931, 9, 191, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

--- CABECERA_PROCESO_MASIVO_REINTENTAR - XM_G_SO-DespIdeal
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (932, 9, 192, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF
