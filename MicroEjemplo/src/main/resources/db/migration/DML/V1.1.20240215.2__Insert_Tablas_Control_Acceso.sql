/**
 * HU278684: Ejecución modelo.
 * Se asigna permiso de ver resultado modelo a roles de ideal.
 **/
SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (699,9,107,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (700,11,107,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF
