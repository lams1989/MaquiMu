-- Se agrega aplicacion XM_RIO para pdn

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado)
VALUES (11, 'SIMPLEX_OPERATIVO_PRUEBAS', '3ea793ef-6adc-432e-ae26-28bf691d6634', 'SIMPLEX_OPERATIVO','c980e410-0b5c-48bc-bd1a-8b91cabc84bc', 'PRD',1);
