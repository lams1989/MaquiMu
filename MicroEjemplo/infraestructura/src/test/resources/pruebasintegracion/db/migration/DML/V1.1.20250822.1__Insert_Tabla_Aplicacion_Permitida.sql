-- TASK473524: Se registra aplicación para XM-SIO

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado)
    VALUES (55, 'XM-SIO-PRD', '0976ac24-87e1-4964-82ac-df473b6cb1b4', 'SIMPLEX_OPERATIVO', 'c980e410-0b5c-48bc-bd1a-8b91cabc84bc', 'PRD', 1);
