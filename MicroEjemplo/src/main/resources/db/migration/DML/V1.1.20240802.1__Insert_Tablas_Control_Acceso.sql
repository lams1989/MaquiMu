-- hu 357334
-- Creación de Permiso para el cálculo techo enficc. Roles Admin Despacho, Despacho
--
SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
    VALUES (162, 'CALCULAR_TECHO_ENFICC', '162', 1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF
--
-- Asignación del permiso cálculo techo enfic a los roles Admin Despacho y Despacho.
--
SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (782, 1, 162, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (783, 3, 162, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF