--
-- Eliminación del permiso para modificar planta redespacho
--

DELETE controlAcceso.RolPermiso WHERE rolPermisoID = 784
DELETE controlAcceso.RolPermiso WHERE rolPermisoID = 785
GO
