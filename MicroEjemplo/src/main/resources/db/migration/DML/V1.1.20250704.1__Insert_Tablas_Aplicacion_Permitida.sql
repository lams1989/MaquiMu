-- TASK170151: Inserts de aplicaciones permitidas para SIO

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado)
    VALUES (51, 'XM-SIO-PRB', '66300aa0-65fd-4026-ac2f-c30d61fe3bc3', 'SIMPLEX_OPERATIVO', 'c980e410-0b5c-48bc-bd1a-8b91cabc84bc', 'PRB', 1);

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado)
    VALUES (52, 'XM-SIO-CAL', '66300aa0-65fd-4026-ac2f-c30d61fe3bc3', 'SIMPLEX_OPERATIVO', 'c980e410-0b5c-48bc-bd1a-8b91cabc84bc', 'CAL', 1);

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado)
    VALUES (53, 'XM-SIO-PRD', 'b79570e8-d7ac-4fea-b6af-8c1f58b91d2f', 'SIMPLEX_OPERATIVO', 'c980e410-0b5c-48bc-bd1a-8b91cabc84bc', 'PRD', 1);

