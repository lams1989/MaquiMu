-- TASK485596: Se registra aplicación para Sinergox

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado)
    VALUES (56, 'Sinergox-PRD', '9a0c2014-5cfb-4407-b765-d0ae3b40e6e3', 'INTERNA_XM', 'c980e410-0b5c-48bc-bd1a-8b91cabc84bc', 'PRD', 1);
