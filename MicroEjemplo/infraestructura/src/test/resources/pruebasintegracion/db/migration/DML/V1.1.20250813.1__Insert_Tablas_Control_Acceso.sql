-- HU466771
-- Creación de permisos para gestionar ejecución proceso masivo
--
SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
VALUES (186, 'TAREA_VER', '186', 1);

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
VALUES (187, 'CABECERA_PROCESO_MASIVO_VER', '187', 1);

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
VALUES (188, 'CABECERA_PROCESO_MASIVO_CREAR', '188', 1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF
--
-- Asignación de los permisos creados a los roles de ideal y técnico.
--
SET IDENTITY_INSERT controlAcceso.RolPermiso ON

--- TAREA_VER - XM_G_SO-DespIdeal
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (920, 9, 186, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

--- CABECERA_PROCESO_MASIVO_VER - XM_G_SO-DespIdeal
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (921, 9, 187, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

--- CABECERA_PROCESO_MASIVO_VER - XM_G_SO-DespIdeal
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (922, 9, 188, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF