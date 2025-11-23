-- CREACIÓN PERMISO ESTADO_ARCHIVO_REDESPACHO_HORARIO_VER

SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
    VALUES (154, 'ESTADO_ARCHIVO_REDESPACHO_HORARIO_VER', '154', 1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF

-- ASIGNACIÓN PERMISO ESTADO_ARCHIVO_REDESPACHO_HORARIO_VER A ROLES SO-Redespacho, SO-RedespachoAdmin, SO-RedespachoConsulta

SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (730, 2, 154, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (731, 4, 154, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (732, 6, 154, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF

