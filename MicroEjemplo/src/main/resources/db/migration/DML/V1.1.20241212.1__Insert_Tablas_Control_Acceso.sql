-- HU 396251
-- Creación de rol y permisos para administrar parámetros modelo

--Creación de permisos
SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
    VALUES (168, 'PARAMETRO_MODELO_OPTIMIZACION_CREAR', '168', 1);

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
    VALUES (169, 'PARAMETRO_MODELO_OPTIMIZACION_MODIFICAR', '169', 1);

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
    VALUES (170, 'PARAMETRO_MODELO_OPTIMIZACION_ELIMINAR', '170', 1);

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
    VALUES (171, 'PARAMETRO_MODELO_OPTIMIZACION_VER', '171', 1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF

--
-- Asignación de los permisos
--
SET IDENTITY_INSERT controlAcceso.RolPermiso ON

---PARAMETRO_MODELO_OPTIMIZACION_CREAR
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (806, 3, 168, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (807, 4, 168, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (808, 9, 168, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

---PARAMETRO_MODELO_OPTIMIZACION_MODIFICAR
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (809, 3, 169, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (810, 4, 169, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (811, 9, 169, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

---PARAMETRO_MODELO_OPTIMIZACION_ELIMINAR
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (812, 3, 170, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (813, 4, 170, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (814, 9, 170, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

---PARAMETRO_MODELO_OPTIMIZACION_VER
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (815, 3, 171, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (816, 4, 171, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (817, 9, 171, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF