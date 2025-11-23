-- CREACIÓN PERMISO SAEB_VER_DESPACHO_IDEALL

SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (149,'RECELE_ZONAS','149',1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF

-- ASIGNACIÓN PERMISO RECELE_ZONAS A ROLES DE DESPACHO && SO-DespachoAdmin

SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (701,1,149,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (702,3,149,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF
