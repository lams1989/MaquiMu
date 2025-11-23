-- PERMISO MODIFICAR AREA_MODIFICAR_INFO_BASICA

SET IDENTITY_INSERT controlAcceso.Permiso ON
INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (133,'AREA_MODIFICAR_INFO_BASICA','133',1);
SET IDENTITY_INSERT controlAcceso.Permiso OFF

SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (580,1,133,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (581,3,133,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (582,7,133,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF

-- PERMISO MODIFICAR SAEB_MODIFICAR_INFO_BASICA

SET IDENTITY_INSERT controlAcceso.Permiso ON
INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (134,'SAEB_MODIFICAR_INFO_BASICA','134',1);
SET IDENTITY_INSERT controlAcceso.Permiso OFF

SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (583,1,134,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (584,3,134,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (585,7,134,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF

-- PERMISO MODIFICAR SUBAREA_MODIFICAR_INFO_BASICA

SET IDENTITY_INSERT controlAcceso.Permiso ON
INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (135,'SUBAREA_MODIFICAR_INFO_BASICA','135',1);
SET IDENTITY_INSERT controlAcceso.Permiso OFF

SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (586,1,135,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (587,3,135,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (588,7,135,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF