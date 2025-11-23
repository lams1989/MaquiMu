-- hu 367419
-- Creación de Permiso para la ejecución de descarga masiva de reportes. Roles Admin Despacho, Redespacho e ideal
--
SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
    VALUES (163, 'EJECUTAR_DESCARGA_MASIVA_REPORTES', '163', 1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF
--
-- Asignación del permiso para la ejecución de descarga masiva de reportes a los roles Admin Despacho, Redespacho e ideal.
--
SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (785, 1, 163, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (786, 2, 163, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (787, 3, 163, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (788, 4, 163, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (789, 7, 163, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (790, 9, 163, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF