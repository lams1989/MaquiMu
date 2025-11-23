-- CREACIÓN PERMISO CALCULOS_DESPACHO_IDEAL

SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (151,'CALCULOS_DESPACHO_IDEAL','151',1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF

-- ASIGNACIÓN PERMISO CALCULOS_DESPACHO_IDEAL A ROL DE XM_G_SO-DESPIDEAL

SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) 
VALUES (716,9,151,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF