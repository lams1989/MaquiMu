-- TASK485596: Se registra aplicación para DGP

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado)
    VALUES (58, 'XM-DGP-PRB', '82fd6830-d5b8-4597-95cb-e542640c9b35', 'SIMPLEX_OPERATIVO', 'c980e410-0b5c-48bc-bd1a-8b91cabc84bc', 'PRB', 1);

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado)
    VALUES (59, 'XM-DGP-CAL', '82fd6830-d5b8-4597-95cb-e542640c9b35', 'SIMPLEX_OPERATIVO', 'c980e410-0b5c-48bc-bd1a-8b91cabc84bc', 'CAL', 1);

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado)
    VALUES (60, 'XM-DGP-PRD', '82fd6830-d5b8-4597-95cb-e542640c9b35', 'SIMPLEX_OPERATIVO', 'c980e410-0b5c-48bc-bd1a-8b91cabc84bc', 'PRD', 1);

