SET IDENTITY_INSERT controlAcceso.RolPermiso ON

---UNIDAD_MODIFICAR
INSERT INTO [controlAcceso].[RolPermiso](rolPermisoID,rolID,permisoID,fechaInicio,fechaFinal)
VALUES (659,9,5,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null)
INSERT INTO [controlAcceso].[RolPermiso](rolPermisoID,rolID,permisoID,fechaInicio,fechaFinal)
VALUES (660,10,5,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null)
GO

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF