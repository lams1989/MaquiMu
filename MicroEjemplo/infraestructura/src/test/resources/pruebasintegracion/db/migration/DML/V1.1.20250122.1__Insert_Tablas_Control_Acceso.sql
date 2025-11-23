-- HU 407310
-- Creación de permisos para modificar la clasificación despues de publicar el redespacho
--
SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
    VALUES (177, 'CLASIFICACION_SEGURIDAD_MODIFICAR_PUBLICADO', '177', 1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF

--
-- Asignación del permiso de modificar la clasificación despues de publicar al rol de RedespachoAdmin
--
SET IDENTITY_INSERT controlAcceso.RolPermiso ON

--- CLASIFICACION_SEGURIDAD_MODIFICAR_PUBLICADO para SO-RedespachoAdmin
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (843, 4, 177, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF