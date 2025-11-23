-- PERMISO MODIFICAR


SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (634, 9, 23, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (635, 10, 23, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (636, 11, 23, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF