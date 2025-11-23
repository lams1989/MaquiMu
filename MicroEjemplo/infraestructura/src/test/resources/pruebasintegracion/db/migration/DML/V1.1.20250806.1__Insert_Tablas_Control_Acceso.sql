-- HU410462 - TASK462955
-- Creación de permisos para gestionar creación orden proceso
--
SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
VALUES (184, 'CONTROL_ORDEN_PROCESO_CREAR', '184', 1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF
--
-- Asignación de los permisos creados a los roles de ideal y técnico.
--
SET IDENTITY_INSERT controlAcceso.RolPermiso ON

--- CONFIGURACION_MODELO_AGENTE_VER - XM_G_SO-DespIdeal
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (914, 9, 184, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

--- CONFIGURACION_MODELO_AGENTE_VER - XM_G_SO-DespIdealAdmin
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (915, 10, 184, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

--- CONFIGURACION_MODELO_AGENTE_VER - XM_G_SO-DespIdealCons
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (916, 11, 184, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

--- CONFIGURACION_MODELO_AGENTE_VER - MDC-INTEGRACIONES
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (917, 7, 184, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);


SET IDENTITY_INSERT controlAcceso.RolPermiso OFF