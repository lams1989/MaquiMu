
SET IDENTITY_INSERT controlAcceso.Escenario ON

INSERT INTO controlAcceso.Escenario(escenarioID,nombre) VALUES (3,'Despacho Ideal')

SET IDENTITY_INSERT controlAcceso.Escenario OFF


SET IDENTITY_INSERT controlAcceso.Rol ON

INSERT INTO controlAcceso.Rol (rolID,objectID, nombre, estado, escenarioID) VALUES(9,'4dbd1781-77b0-4ff2-9b6d-55c16f5873f7', 'XM_G_SO-DespIdeal', 1, 3);
INSERT INTO controlAcceso.Rol (rolID,objectID, nombre, estado, escenarioID) VALUES(10,'9638e813-96e1-4b33-853b-c45c452aebb5', 'XM_G_SO-DespIdealAdmin', 1, 3);
INSERT INTO controlAcceso.Rol (rolID,objectID, nombre, estado, escenarioID) VALUES(11,'81c7d328-392b-421b-9de5-e9608d29a904', 'XM_G_SO-DespIdealCons', 1, 3);

SET IDENTITY_INSERT controlAcceso.Rol OFF


SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado) VALUES (131,'CASO_CREAR_DESPACHO_IDEAL','131',1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF


SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (575, 9, 131, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (576, 10, 131, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF