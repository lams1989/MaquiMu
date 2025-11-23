-- HU 391357
-- Asignación de los permisos
--
SET IDENTITY_INSERT controlAcceso.RolPermiso ON

---CUENTA_TECNICA - HISTORICO_MODELO_VER
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (803, 7, 166, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

---CUENTA_TECNICA - HISTORICO_MODELO_MODIFICAR
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (804, 7, 167, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

---ADMIN_MODELO - CONSULTA_MODELO_VER
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (805, 12, 101, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time',null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF