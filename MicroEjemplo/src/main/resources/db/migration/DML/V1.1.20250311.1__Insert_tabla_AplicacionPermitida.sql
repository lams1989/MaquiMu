--HU-418753
--INSERT APLICACION PERMITIDAD PDN DESDE APLICACION DE CALIDAD
INSERT INTO controlAcceso.AplicacionPermitida (aplicacionPermitidaID, nombre,clienteIdJwt,tipoIntegracion,tenantId,ambiente,estado,urlWebhook)
    VALUES (36, N'SIMPLEX_OPERATIVO_XM',N'41440e32-e5f9-4aa8-a95e-cd4deb4d9b6a',N'SIMPLEX_OPERATIVO',N'c980e410-0b5c-48bc-bd1a-8b91cabc84bc',N'PRD',
    1,N'https://serviciossimplexoperativobackendcl.xm.com.co/modelo-optimizacion/v1/modelo-gams/respuesta-webhook-enrutador');