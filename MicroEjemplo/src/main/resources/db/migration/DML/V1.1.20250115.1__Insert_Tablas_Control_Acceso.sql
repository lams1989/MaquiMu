-- HU 391358
-- Asignación de los permisos de exportación de archivo de logs a roles de depacho y redespacho
--
SET IDENTITY_INSERT controlAcceso.RolPermiso ON

--- HISTORICO_EJECUCION_MODELO_LOG_VER para SO-Despacho
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (822, 1, 173, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

--- HISTORICO_EJECUCION_MODELO_LOG_VER para SO-Redespacho
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (823, 2, 173, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

--- HISTORICO_EJECUCION_MODELO_LOG_VER para SO-DespachoAdmin
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (824, 3, 173, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

--- HISTORICO_EJECUCION_MODELO_LOG_VER para SO-RedespachoAdmin
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (825, 4, 173, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

--- HISTORICO_EJECUCION_MODELO_LOG_VER para SO-DespachoConsulta
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (826, 5, 173, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

--- HISTORICO_EJECUCION_MODELO_LOG_VER para SO-RedespachoConsulta
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (827, 6, 173, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

--- HISTORICO_EJECUCION_MODELO_LOG_VER para XM_G_SO-DespIdeal
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (828, 9, 173, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

--- HISTORICO_EJECUCION_MODELO_LOG_VER para XM_G_SO-DespIdealAdmin
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (829, 10, 173, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

--- HISTORICO_EJECUCION_MODELO_LOG_VER para XM_G_SO-DespIdealCons
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (830, 11, 173, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);



SET IDENTITY_INSERT controlAcceso.RolPermiso OFF