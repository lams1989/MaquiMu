-- HU 391358
-- Creación de permisos para gestionar la exportación de archivo de resultado modelo y logs
--
SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
    VALUES (173, 'HISTORICO_EJECUCION_MODELO_LOG_VER', '173', 1);

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
VALUES (174, 'HISTORICO_EJECUCION_RESULTADO_VER', '174', 1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF
--
-- Asignación de los permisos de exportación de archivo de resultado modelo y logs
--
SET IDENTITY_INSERT controlAcceso.RolPermiso ON

--- HISTORICO_EJECUCION_MODELO_LOG_VER
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (820, 12, 173, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

--- HISTORICO_EJECUCION_RESULTADO_VER
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (821, 12, 174, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);


SET IDENTITY_INSERT controlAcceso.RolPermiso OFF