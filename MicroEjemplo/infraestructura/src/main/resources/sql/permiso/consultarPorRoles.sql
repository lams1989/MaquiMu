SELECT DISTINCT PE.permisoID, PE.nombre, PE.codigo, PE.estado
	  FROM controlAcceso.Rol RO WITH (NOLOCK)
	  JOIN controlAcceso.RolPermiso RP ON RP.rolID = RO.rolID
	   AND CAST(SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time' AS DATE) 
   BETWEEN CAST(RP.fechaInicio AS DATE) 
	   AND ISNULL(CAST(RP.fechaFinal AS DATE), CAST(SYSDATETIMEOFFSET()  AT TIME ZONE 'SA Pacific Standard Time' AS DATE))
	  JOIN controlAcceso.Permiso PE ON PE.permisoID = RP.permisoID
 WHERE RO.nombre IN (:roles) AND PE.estado = 1