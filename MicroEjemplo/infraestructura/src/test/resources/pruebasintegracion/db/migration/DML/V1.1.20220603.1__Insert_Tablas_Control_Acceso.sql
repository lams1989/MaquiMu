SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (59,'MENSAJE_EVENTO_PENDIENTE_CREAR','059',1);
INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (60,'MENSAJE_EVENTO_PENDIENTE_VER','060',1);
INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (61,'MENSAJE_EVENTO_PENDIENTE_MOD','061',1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF

SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (253,7,59,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (254,2,60,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (255,4,60,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (256,6,60,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (257,2,61,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (258,4,61,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (259,6,61,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);


SET IDENTITY_INSERT controlAcceso.RolPermiso OFF