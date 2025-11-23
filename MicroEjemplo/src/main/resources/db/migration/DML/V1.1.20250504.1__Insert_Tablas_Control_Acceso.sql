--- INC11224222 Se asigna permisos CONTROL_RESTRICCIONES_MODIFICAR a rol despacho y redespacho segun HU

SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (851, 2, 25, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (852, 1, 25, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF
