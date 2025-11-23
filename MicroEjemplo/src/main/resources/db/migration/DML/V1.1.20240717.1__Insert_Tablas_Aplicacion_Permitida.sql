-- Se agrega aplicación SIMPLEX_OPERATIVO_XM para pruebas, calidad y producción

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado)
VALUES (12, 'SIMPLEX_OPERATIVO_XM', '4dee8bbc-59f7-4c4f-9196-ca152b2f4c17', 'SIMPLEX_OPERATIVO','c980e410-0b5c-48bc-bd1a-8b91cabc84bc', 'PRB',1);

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado)
VALUES (13, 'SIMPLEX_OPERATIVO_XM', '4dee8bbc-59f7-4c4f-9196-ca152b2f4c17', 'SIMPLEX_OPERATIVO','c980e410-0b5c-48bc-bd1a-8b91cabc84bc', 'CAL',1);

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado)
VALUES (14, 'SIMPLEX_OPERATIVO_XM', '3ed81388-726d-4ae4-b397-a8c35afc1c9f', 'SIMPLEX_OPERATIVO','c980e410-0b5c-48bc-bd1a-8b91cabc84bc', 'PRD',1);