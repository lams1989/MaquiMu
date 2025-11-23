-- PERMISOS PESTAÑAS DE MÓDULO DE SUBÁREAS PARA DESPACHO IDEAL

SET IDENTITY_INSERT controlAcceso.Permiso ON
INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (144,'INTERCONEXION_VER_DESPACHO_IDEAL','144',1);
INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (145,'INTERCONEXION_MODIFICAR_DESPACHO_IDEAL','145',1);
SET IDENTITY_INSERT controlAcceso.Permiso OFF

SET IDENTITY_INSERT controlAcceso.RolPermiso ON

-- PERMISOS PARA MODULO INTERCONEXION IDEAL
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (671,9,144,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (672,9,145,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (673,11,144,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF