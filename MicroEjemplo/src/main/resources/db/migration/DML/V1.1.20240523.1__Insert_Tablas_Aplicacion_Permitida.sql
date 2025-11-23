--Se agregan registros para SIMPLEX_OPERATIVO_XM en ambiente CAL y para RIO_XM en ambiente de PRB y CAL

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado) 
VALUES (6, 'SIMPLEX_OPERATIVO_XM', '3ea793ef-6adc-432e-ae26-28bf691d6634', 'SIMPLEX_OPERATIVO','c980e410-0b5c-48bc-bd1a-8b91cabc84bc', 'CAL',1);

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado) 
VALUES (7, 'RIO_XM', '11e0ae9e-2306-48c9-8acd-e0c998e00175', 'SIMPLEX_OPERATIVO','c980e410-0b5c-48bc-bd1a-8b91cabc84bc', 'PRB',1);

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado) 
VALUES (8, 'RIO_XM', '11e0ae9e-2306-48c9-8acd-e0c998e00175', 'SIMPLEX_OPERATIVO','c980e410-0b5c-48bc-bd1a-8b91cabc84bc', 'CAL',1);


--Se elimina registro RIO_XM en ambiente PRB
DELETE FROM controlAcceso.AplicacionPermitida WHERE aplicacionPermitidaID = 1; 
--Se elimina registro RIO_XM en ambiente CAL
DELETE FROM controlAcceso.AplicacionPermitida WHERE aplicacionPermitidaID = 2