-- HU 400333
-- Creación de permisos para procesar modelo y gsa
--
SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
    VALUES (176, 'PROCESAR_MODELO_GSA', '176', 1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF

--
-- Asignación de los permisos de procesar modelo y gsa a los roles de ideal
--
SET IDENTITY_INSERT controlAcceso.RolPermiso ON

--- REGISTRO_EJECUCION_MODELO_VER para SO-DespIdeal
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (840, 9, 176, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

--- REGISTRO_EJECUCION_MODELO_VER para SO-DespIdealAdmin
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (841, 10, 176, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

--- REGISTRO_EJECUCION_MODELO_VER para SO-DespIdealCons
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (842, 11, 176, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF