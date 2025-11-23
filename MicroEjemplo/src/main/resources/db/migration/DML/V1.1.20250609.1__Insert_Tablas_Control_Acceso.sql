-- HU 447807
-- Creación de permisos para gestionar la carga archivos modelo agente
--
SET IDENTITY_INSERT controlAcceso.Permiso ON


INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
VALUES (183, 'CARGA_ARCHIVOS_MODEL_AGENTE', '183', 1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF
--
-- Asignación de los permisos creados a los roles de despacho admin y modelo admin
--
SET IDENTITY_INSERT controlAcceso.RolPermiso ON

--- CARGA_ARCHIVOS_MODEL_AGENTE - DESPACHO ADMIN
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (908, 3, 183, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);


--- CARGA_ARCHIVOS_MODEL_AGENTE - XM_G_SO_ADMIN_MODELO
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (909, 12, 183, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF