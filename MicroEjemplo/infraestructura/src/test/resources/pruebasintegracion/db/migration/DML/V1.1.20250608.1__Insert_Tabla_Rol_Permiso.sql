-- ASIGNACION PERMISO CABECERA_CALCULO_MODIFICAR DESPACHO IDEAL

SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio) VALUES (906, 9, 161, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time');
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio) VALUES (907, 10, 161, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time');

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF