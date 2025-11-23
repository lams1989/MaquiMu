/**
 * Se le asigna el permiso de ver grupo de correos a los roles del ideal
 * 
 **/
SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (696,9,115,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (697,10,115,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (698,11,115,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF
