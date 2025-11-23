-- HU 430678
-- Creación de permisos para gestionar la configuración de archivos modelo agentes
--
SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
VALUES (179, 'CONFIGURACION_MODELO_AGENTE_VER', '179', 1);

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
VALUES (180, 'CONFIGURACION_MODELO_AGENTE_CREAR', '180', 1);

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
VALUES (181, 'CONFIGURACION_MODELO_AGENTE_MODIFICAR', '181', 1);

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
VALUES (182, 'CONFIGURACION_MODELO_AGENTE_ELIMINAR', '182', 1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF
--
-- Asignación de los permisos creados a los roles de despacho admin y modelo admin
--
SET IDENTITY_INSERT controlAcceso.RolPermiso ON

--- CONFIGURACION_MODELO_AGENTE_VER - DESPACHO ADMIN
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (853, 3, 179, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

--- CONFIGURACION_MODELO_AGENTE_CREAR - DESPACHO ADMIN
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (854, 3, 180, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

--- CONFIGURACION_MODELO_AGENTE_MODIFICAR - DESPACHO ADMIN
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (855, 3, 181, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

--- CONFIGURACION_MODELO_AGENTE_ELIMINAR - DESPACHO ADMIN
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (856, 3, 182, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

--- CONFIGURACION_MODELO_AGENTE_VER - XM_G_SO_ADMIN_MODELO
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (857, 12, 179, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

--- CONFIGURACION_MODELO_AGENTE_CREAR - XM_G_SO_ADMIN_MODELO
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (858, 12, 180, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

--- CONFIGURACION_MODELO_AGENTE_MODIFICAR - XM_G_SO_ADMIN_MODELO
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (859, 12, 181, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

--- CONFIGURACION_MODELO_AGENTE_ELIMINAR - XM_G_SO_ADMIN_MODELO
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (860, 12, 182, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF