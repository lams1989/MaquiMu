SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (34,'MENSAJE_RESPACHO_CREAR','034',1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF

SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (146,7,34,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF