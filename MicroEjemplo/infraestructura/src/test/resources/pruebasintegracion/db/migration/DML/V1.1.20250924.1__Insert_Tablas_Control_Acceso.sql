-- BUG483436
-- Creación de permisos para control estado proceso ver
--
--
SET IDENTITY_INSERT controlAcceso.RolPermiso ON


INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (933, 1, 190, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (934, 2, 190, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (935, 3, 190, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (936, 4, 190, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (937, 5, 190, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (938, 6, 190, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (939, 12, 190, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);


SET IDENTITY_INSERT controlAcceso.RolPermiso OFF
