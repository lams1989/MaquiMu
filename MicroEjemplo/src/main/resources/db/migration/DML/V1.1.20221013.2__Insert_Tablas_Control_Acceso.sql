SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (82,'CABECERA_CALCULO_VER','082',1);
INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (83,'CABECERA_CALCULO_CREAR','083',1);
INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (84,'CALCULAR_MODELO_GAMS','084',1);
INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (85,'CALCULAR_PONER','085',1);
INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (86,'CALCULAR_PRECIO_BOLSA_NACIONAL','086',1);
INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (87,'CALCULAR_MODELO_SEGMENTOS_TIES','087',1);
INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (88,'DETALLE_CALCULO_MODIFICAR','088',1);
INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (89,'RES_GENERAL_VER','089',1);
INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (90,'DETALLE_CALCULO_VER','090',1);
INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (91,'CALCULO_ULTIMA_EJECUCION_VER','091',1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF


SET IDENTITY_INSERT controlAcceso.RolPermiso ON

--PERMISO VER CABECERA CALCULO
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (359,1,82,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (360,2,82,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (361,3,82,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (362,4,82,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (363,5,82,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (364,6,82,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

--PERMISO CREAR CABECERA CALCULO
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (365,1,83,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (366,3,83,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (367,7,83,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

--PERMISO CALCULAR DESDE MODELO GAMS
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (368,1,84,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (369,3,84,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (370,7,84,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

--PERMISO CALCULAR DESDE PONER
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (371,2,85,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (372,4,85,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (373,7,85,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

--PERMISO CALCULAR PRECIO BOLSA NACIONAL
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (374,1,86,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (375,3,86,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (376,7,86,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

--PERMISO CALCULAR MODELO SEGMENTOS TIES
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (377,1,87,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (378,3,87,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (379,7,87,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

--PERMISO CREAR Y ACTUALIZAR DETALLE CALCULO
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (380,1,88,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (381,3,88,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (382,7,88,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

--PERMISO VER RES GENERAL
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (383,1,89,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (384,3,89,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (385,7,89,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

--PERMISO VER DETALLE CALCULO
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (386,1,90,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (387,3,90,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (388,7,90,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

--PERMISO VER CALCULO ULTIMA EJECUCION
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (389,1,91,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (390,3,91,SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF