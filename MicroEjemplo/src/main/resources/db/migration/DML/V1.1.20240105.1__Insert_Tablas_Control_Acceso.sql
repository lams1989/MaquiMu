SET IDENTITY_INSERT controlAcceso.RolPermiso ON
-- PERMISO PLANTA_VER
INSERT INTO [controlAcceso].[RolPermiso](rolPermisoID,rolID,permisoID,fechaInicio,fechaFinal)
VALUES (649,9,6,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null)
INSERT INTO [controlAcceso].[RolPermiso](rolPermisoID,rolID,permisoID,fechaInicio,fechaFinal)
VALUES (650,11,6,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null)
GO

-- PERMISO PLANTA_MODIFICAR
INSERT INTO [controlAcceso].[RolPermiso](rolPermisoID,rolID,permisoID,fechaInicio,fechaFinal)
VALUES (651,9,7,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null)
INSERT INTO [controlAcceso].[RolPermiso](rolPermisoID,rolID,permisoID,fechaInicio,fechaFinal)
VALUES (652,11,7,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null)
GO

-- PERMISO PLANTA_MODIFICAR_INFO_BASICA
INSERT INTO [controlAcceso].[RolPermiso](rolPermisoID,rolID,permisoID,fechaInicio,fechaFinal)
VALUES (653,9,126,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null)
INSERT INTO [controlAcceso].[RolPermiso](rolPermisoID,rolID,permisoID,fechaInicio,fechaFinal)
VALUES (654,11,126,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null)
GO

-- PERMISO GRUPO_CORREO_CREAR
INSERT INTO [controlAcceso].[RolPermiso](rolPermisoID,rolID,permisoID,fechaInicio,fechaFinal)
VALUES (655,9,116,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null)
INSERT INTO [controlAcceso].[RolPermiso](rolPermisoID,rolID,permisoID,fechaInicio,fechaFinal)
VALUES (656,11,116,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null)
GO

-- PERMISO GRUPO_CORREO_MODIFICAR
INSERT INTO [controlAcceso].[RolPermiso](rolPermisoID,rolID,permisoID,fechaInicio,fechaFinal)
VALUES (657,9,117,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null)
INSERT INTO [controlAcceso].[RolPermiso](rolPermisoID,rolID,permisoID,fechaInicio,fechaFinal)
VALUES (658,11,117,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null)
GO

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF