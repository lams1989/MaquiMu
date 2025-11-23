-- HU415285
-- Creación de permisos para actualizar el estado y la observación de los mensajes de las colas muertas
--
SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
    VALUES (178, 'MENSAJE_EVENTO_PENDIENTE_MOD_ADMIN', '178', 1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF
--
-- Asignación de los permisos de para editar información de Mensajes de colas Muertas
--
SET IDENTITY_INSERT controlAcceso.RolPermiso ON

--- MENSAJE_EVENTO_PENDIENTE_MOD_ADMIN
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (844, 3, 178, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);


SET IDENTITY_INSERT controlAcceso.RolPermiso OFF