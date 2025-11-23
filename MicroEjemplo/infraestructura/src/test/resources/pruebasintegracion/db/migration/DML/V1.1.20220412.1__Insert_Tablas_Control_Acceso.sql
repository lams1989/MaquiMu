SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (56,'SOLICITUD_CND_VER','056',1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF

SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (242,2,56,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (243,3,56,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (244,4,56,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (245,6,56,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF