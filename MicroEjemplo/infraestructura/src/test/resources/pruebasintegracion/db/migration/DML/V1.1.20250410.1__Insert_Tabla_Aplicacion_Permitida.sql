--TASK431338: Adición de aplicación permitida para que enrutador de CAL pueda consumir modelo optimización de PRB

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID,nombre,clienteIdJwt,tipoIntegracion,tenantId,ambiente,estado)
VALUES (46,'SIMPLEX_OPERATIVO_XM','41440e32-e5f9-4aa8-a95e-cd4deb4d9b6a','SIMPLEX_OPERATIVO','c980e410-0b5c-48bc-bd1a-8b91cabc84bc','PRB',1);
