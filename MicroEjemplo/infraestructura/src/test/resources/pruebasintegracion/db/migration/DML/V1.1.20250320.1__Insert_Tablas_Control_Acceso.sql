--- HU425470: Se agregan relaciones para funcionalidad re-intentos PDN1 legados

SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (845, 1, 176, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (846, 2, 176, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (847, 3, 176, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (848, 4, 176, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (849, 5, 176, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (850, 6, 176, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF
