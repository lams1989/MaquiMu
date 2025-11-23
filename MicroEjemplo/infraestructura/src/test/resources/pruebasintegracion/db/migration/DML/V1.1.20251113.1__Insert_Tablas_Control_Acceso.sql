-- HU: 497165
-- Adición de permiso CABECERA_PROCESO_MASIVO_CREAR para el rol de MDC-INTEGRACIONES usado por la cuenta técnica de integraciones en procesos asíncronos.

SET IDENTITY_INSERT controlAcceso.RolPermiso ON

INSERT INTO controlAcceso.RolPermiso (rolPermisoID, rolID, permisoID, fechaInicio, fechaFinal)
    VALUES (940, 7, 188, SYSDATETIMEOFFSET() AT TIME ZONE 'SA Pacific Standard Time', null);

SET IDENTITY_INSERT controlAcceso.RolPermiso OFF
