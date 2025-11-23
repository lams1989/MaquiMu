--
-- Asignación del permiso para modificar planta redespacho
--

SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES(784, 2, 126, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES(785, 4, 126, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF