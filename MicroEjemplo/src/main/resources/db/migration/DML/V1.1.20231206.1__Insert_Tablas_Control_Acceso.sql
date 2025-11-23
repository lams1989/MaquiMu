-- PERMISO MODIFICAR ZONASEGURIDAD_UNIDADES_MODIFICAR_INFO_BASICA

SET IDENTITY_INSERT controlAcceso.Permiso ON
INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (137,'ZONASEGURIDAD_MODIFICAR_INFO_BASICA','137',1);
SET IDENTITY_INSERT controlAcceso.Permiso OFF

SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (592,1,137,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (593,3,137,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (594,7,137,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF