-- HU 391357
-- Creación de rol y permisos para administrador modelo
--

-- Creación de escenario de modelo de optimización
SET IDENTITY_INSERT controlAcceso.Escenario ON
INSERT INTO controlAcceso.Escenario (escenarioID, nombre) VALUES(4, 'Modelo optimización');
SET IDENTITY_INSERT controlAcceso.Escenario OFF

-- Creación de rol administrador modelo
SET IDENTITY_INSERT controlAcceso.rol ON
INSERT INTO controlAcceso.Rol (rolID, objectID, nombre, estado, escenarioID) VALUES(12, '252f99b4-0518-459c-80ac-d79455e0ec72', 'XM_G_SO_ADMIN_MODELO', 1, 4);
SET IDENTITY_INSERT controlAcceso.rol OFF


--Creación de permisos
SET IDENTITY_INSERT controlAcceso.Permiso ON

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
    VALUES (166, 'HISTORICO_MODELO_VER', '166', 1);

INSERT INTO controlAcceso.Permiso (permisoID, nombre, codigo, estado)
    VALUES (167, 'HISTORICO_MODELO_MODIFICAR', '167', 1);

SET IDENTITY_INSERT controlAcceso.Permiso OFF

--
-- Asignación de los permisos
--
SET IDENTITY_INSERT controlAcceso.RolPermiso ON

---HISTORICO_MODELO_VER
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (801, 12, 166, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

---HISTORICO_MODELO_MODIFICAR
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (802, 12, 167, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF