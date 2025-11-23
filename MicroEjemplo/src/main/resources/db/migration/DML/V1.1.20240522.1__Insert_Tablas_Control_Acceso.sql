-- HU212230 - Carga manual de archivos.

--
-- Creación de Permiso para la carga manual.
--
SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
    VALUES (158, 'CARGA_MANUAL_ARCHIVO', '158', 1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF

--
-- Asignación del permiso CARGA_MANUAL_ARCHIVO a los roles Admin de despacho y redespacho.
--
SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (759,3,158,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (760,4,158,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF
