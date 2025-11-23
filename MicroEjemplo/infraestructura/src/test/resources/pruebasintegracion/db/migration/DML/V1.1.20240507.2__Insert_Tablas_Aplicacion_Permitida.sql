-- HU326403 - Ajuste login para integraciones
--
-- Inserts de aplicaciones permitidas
--

INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado) 
    VALUES (1, 'APP_SIO', 'clienteIdJwt', 'INTERNA_XM', 'tenantId', 'PRB', 1);
    
INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado) 
    VALUES (2, 'APP_SIO', 'clienteIdJwt', 'INTERNA_XM', 'tenantId', 'CAL', 1);
    
INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre, clienteIdJwt, tipoIntegracion, tenantId, ambiente, estado) 
    VALUES (3, 'APP_SIO', 'clienteIdJwt', 'INTERNA_XM', 'tenantId', 'PRD', 1);    
    