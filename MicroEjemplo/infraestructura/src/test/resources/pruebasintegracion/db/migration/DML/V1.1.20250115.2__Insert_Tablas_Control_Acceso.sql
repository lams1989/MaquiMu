-- HU 391358
-- Creación de permisos para gestionar el historico del modelo
--
SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
    VALUES (175, 'REGISTRO_EJECUCION_MODELO_VER', '175', 1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF
--
-- Asignación de los permisos de historico modelo a los roles de depacho, redespacho y ideal
--
SET IDENTITY_INSERT controlAcceso.RolPermiso ON

--- REGISTRO_EJECUCION_MODELO_VER para SO-Despacho
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (831, 1, 175, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

--- REGISTRO_EJECUCION_MODELO_VER para SO-Redespacho
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (832, 2, 175, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

--- REGISTRO_EJECUCION_MODELO_VER para SO-DespachoAdmin
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (833, 3, 175, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

--- REGISTRO_EJECUCION_MODELO_VER para SO-RedespachoAdmin
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (834, 4, 175, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

--- REGISTRO_EJECUCION_MODELO_VER para SO-DespachoConsulta
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (835, 5, 175, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

--- REGISTRO_EJECUCION_MODELO_VER para SO-RedespachoConsulta
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (836, 6, 175, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

--- REGISTRO_EJECUCION_MODELO_VER para XM_G_SO-DespIdeal
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (837, 9, 175, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

--- REGISTRO_EJECUCION_MODELO_VER para XM_G_SO-DespIdealAdmin
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (838, 10, 175, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

--- REGISTRO_EJECUCION_MODELO_VER para XM_G_SO-DespIdealCons
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (839, 11, 175, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);


SET IDENTITY_INSERT controlAcceso.RolPermiso OFF