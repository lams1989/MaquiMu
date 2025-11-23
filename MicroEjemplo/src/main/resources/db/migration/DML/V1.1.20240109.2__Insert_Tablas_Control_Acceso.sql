SET IDENTITY_INSERT controlAcceso.RolPermiso ON

---CONDICIONES_INICIALES_VER
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (661, 9, 14, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (662, 10, 14, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

---CONDICIONES_INICIALES_MODIFICAR
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (663, 9, 15, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);
INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal) VALUES (664, 10, 15, SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time', null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF