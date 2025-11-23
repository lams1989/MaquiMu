-- CREACIÓN PERMISO SAEB_VER_DESPACHO_IDEALL

SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (146,'SAEB_VER_DESPACHO_IDEAL','146',1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF

-- ASIGNACIÓN PERMISO SAEB_VER_IDEAL A ROLES DE DESPACHO IDEAL XM_G_SO-DespIdeal && XM_G_SO-DespIdealCons

SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (674,9,146,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (675,11,146,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF