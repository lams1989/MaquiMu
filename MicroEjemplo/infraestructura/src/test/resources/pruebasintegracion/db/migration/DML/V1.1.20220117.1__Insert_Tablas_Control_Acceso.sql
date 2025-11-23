SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (43,'PARAMETROS_CORREO_VER','043',1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF

SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (179,1,43,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (180,2,43,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (181,3,43,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (182,4,43,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF