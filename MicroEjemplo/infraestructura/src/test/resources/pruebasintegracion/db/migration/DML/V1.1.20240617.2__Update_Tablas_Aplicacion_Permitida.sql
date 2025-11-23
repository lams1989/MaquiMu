-- Se actualizan nombres de aplicacion

UPDATE controlAcceso.AplicacionPermitida
SET nombre = 'POWER_FACTORY'
WHERE aplicacionPermitidaID = 9;

UPDATE controlAcceso.AplicacionPermitida
SET nombre = 'SIMPLEX_OPERATIVO_XM'
WHERE aplicacionPermitidaID = 11;
