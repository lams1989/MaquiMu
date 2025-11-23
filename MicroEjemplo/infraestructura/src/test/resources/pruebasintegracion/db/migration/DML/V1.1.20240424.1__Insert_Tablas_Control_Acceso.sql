-- HU327296 - Manejo de errores en Carga de datos
--
-- Asignación del permiso REGISTRO_PROCESO_VER a el rol de despacho ideal.
--
SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) 
    VALUES (754,9,155,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (755,10,155,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (756,11,155,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF
