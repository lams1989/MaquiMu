-- Se agrega aplicación RIO_XM para calidad

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado)
VALUES (16, 'XM_SCADA', '7730f2e7-46ce-4a36-9982-ea83d4ddef88', 'SIMPLEX_OPERATIVO','c980e410-0b5c-48bc-bd1a-8b91cabc84bc', 'CAL',1);

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado)
VALUES (17, 'XM_SCADA', '6f6b3275-d2e7-49f1-9c83-a40a68e8e54c', 'SIMPLEX_OPERATIVO','c980e410-0b5c-48bc-bd1a-8b91cabc84bc', 'PRB',1);

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado)
VALUES (18, 'XM_SCADA', '39446593-e01f-4b9b-bad9-4a44a56dee0a', 'SIMPLEX_OPERATIVO','c980e410-0b5c-48bc-bd1a-8b91cabc84bc', 'PRD',1);

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado)
VALUES (19, 'XM_INDENER', 'ee2526ff-9a05-4733-b565-31e229d847f3', 'SIMPLEX_OPERATIVO','c980e410-0b5c-48bc-bd1a-8b91cabc84bc', 'CAL',1);

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado)
VALUES (20, 'XM_INDENER', 'ff1eebf2-f46f-49e6-8f11-d7b7449cd2a2', 'SIMPLEX_OPERATIVO','c980e410-0b5c-48bc-bd1a-8b91cabc84bc', 'PRB',1);

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado)
VALUES (21, 'XM_INDENER', '840ff314-0449-4065-9a4b-f945e1508561', 'SIMPLEX_OPERATIVO','c980e410-0b5c-48bc-bd1a-8b91cabc84bc', 'PRD',1);
