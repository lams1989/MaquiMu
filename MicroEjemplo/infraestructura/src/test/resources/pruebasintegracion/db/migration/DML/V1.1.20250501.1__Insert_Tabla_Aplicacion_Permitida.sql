
-- BUG436487 Agrego aplicación permitida GAO

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID,nombre,clienteIdJwt,tipoIntegracion,tenantId,ambiente,estado)
VALUES (44,'XM-GAO-BackEnd-PRB','f6252296-a09c-43cb-9c1f-b66a5bb7fb0f','SIMPLEX_OPERATIVO','c980e410-0b5c-48bc-bd1a-8b91cabc84bc','PRB',1);

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID,nombre,clienteIdJwt,tipoIntegracion,tenantId,ambiente,estado)
VALUES (45,'XM_S_GAO_PRD','199cdfab-c1f8-4af2-81e6-3703e2ebdae4','SIMPLEX_OPERATIVO','c980e410-0b5c-48bc-bd1a-8b91cabc84bc','PRD',1);
