-- CREACIÓN PERMISO CARGA_DATOS_MODIFICAR_IDEAL

SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (147,'CARGA_DATOS_MODIFICAR_IDEAL','147',1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF

-- ASIGNACIÓN PERMISO CARGA_DATOS_MODIFICAR_IDEAL A ROL DE DESPACHO IDEAL XM_G_SO-DespIdeal

SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (689, 9, 147, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF