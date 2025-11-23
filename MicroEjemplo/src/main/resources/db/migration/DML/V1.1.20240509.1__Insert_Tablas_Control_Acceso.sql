-- HU315319 - Carga Masiva de consultas modelo.

--
-- Creación de Permiso para la carga masiva. Roles Admin Despacho/Redespacho.
--
SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
    VALUES (157, 'CARGA_CONSULTA_MODELO', '157', 1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF

--
-- Asignación del permiso CARGA_CONSULTA_MODELO a los roles Admin de despacho y redespacho.
--
SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (757,3,157,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (758,4,157,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF
