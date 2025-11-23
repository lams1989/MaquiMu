SET IDENTITY_INSERT controlAcceso.RolPermiso ON

-- PERMISO CASO_VER
INSERT INTO [controlAcceso].[RolPermiso](rolPermisoID,rolID,permisoID,fechaInicio,fechaFinal)
VALUES (637,9,2,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null)
INSERT INTO [controlAcceso].[RolPermiso](rolPermisoID,rolID,permisoID,fechaInicio,fechaFinal)
VALUES (638,10,2,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null)
INSERT INTO [controlAcceso].[RolPermiso](rolPermisoID,rolID,permisoID,fechaInicio,fechaFinal)
VALUES (639,11,2,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null)
GO

-- PERMISO CONSULTA_SIGNAL_R
INSERT INTO [controlAcceso].[RolPermiso](rolPermisoID,rolID,permisoID,fechaInicio,fechaFinal)
VALUES (640,9,105,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null)
INSERT INTO [controlAcceso].[RolPermiso](rolPermisoID,rolID,permisoID,fechaInicio,fechaFinal)
VALUES (641,10,105,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null)
INSERT INTO [controlAcceso].[RolPermiso](rolPermisoID,rolID,permisoID,fechaInicio,fechaFinal)
VALUES (642,11,105,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null)
GO

-- PERMISO INTERCONEXION_VER
INSERT INTO [controlAcceso].[RolPermiso](rolPermisoID,rolID,permisoID,fechaInicio,fechaFinal)
VALUES (643,9,42,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null)
INSERT INTO [controlAcceso].[RolPermiso](rolPermisoID,rolID,permisoID,fechaInicio,fechaFinal)
VALUES (644,10,42,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null)
INSERT INTO [controlAcceso].[RolPermiso](rolPermisoID,rolID,permisoID,fechaInicio,fechaFinal)
VALUES (645,11,42,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null)
GO

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF