-- HU472373 - features/TASK472373
-- Creación de permisos para VER y CREAR control estado proceso
--
SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
VALUES (189, 'CONTROL_ESTADO_PROCESO_CREAR', '189', 1);

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
VALUES (190, 'CONTROL_ESTADO_PROCESO_VER', '190', 1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF
--
-- Asignación de los permisos creados a los roles de ideal y técnico.
--
SET IDENTITY_INSERT controlAcceso.RolPermiso ON


INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (923, 9, 189, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (924, 10, 189, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (925, 11, 189, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (926, 7, 189, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (927, 9, 190, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (928, 10, 190, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (929, 11, 190, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (930, 7, 190, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);


SET IDENTITY_INSERT controlAcceso.RolPermiso OFF