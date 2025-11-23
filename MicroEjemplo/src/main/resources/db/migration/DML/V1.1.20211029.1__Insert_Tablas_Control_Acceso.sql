SET IDENTITY_INSERT controlAcceso.RolPermiso ON
--EJECUTAR_MODELO_GAMS
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (155,7,36,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
SET IDENTITY_INSERT controlAcceso.RolPermiso OFF