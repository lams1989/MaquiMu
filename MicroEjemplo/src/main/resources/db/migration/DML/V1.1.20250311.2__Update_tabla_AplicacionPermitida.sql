--HU-418753
--UPDATE AMBIENTE CAL
UPDATE controlAcceso.AplicacionPermitida
    SET urlWebhook=N'http://modelo-optimizacion/modelo-optimizacion/v1/modelo-gams/respuesta-webhook-enrutador'
    WHERE nombre = 'SIMPLEX_OPERATIVO_XM' and clienteIdJwt = '41440e32-e5f9-4aa8-a95e-cd4deb4d9b6a' and ambiente = 'CAL';
--UPDATE AMBIENTE CAL CON APLICACION DESDE PRUEBAS
UPDATE controlAcceso.AplicacionPermitida
    SET urlWebhook=N'https://serviciossimplexoperativoprb.xm.com.co/modelo-optimizacion/v1/modelo-gams/respuesta-webhook-enrutador'
    WHERE nombre = 'SIMPLEX_OPERATIVO_XM' and clienteIdJwt = '3ea793ef-6adc-432e-ae26-28bf691d6634' and ambiente = 'CAL'

--UPDATE AMBIENTE PRB
UPDATE controlAcceso.AplicacionPermitida
    SET urlWebhook=N'http://modelo-optimizacion/modelo-optimizacion/v1/modelo-gams/respuesta-webhook-enrutador'
    WHERE nombre = 'SIMPLEX_OPERATIVO_XM' and clienteIdJwt = '3ea793ef-6adc-432e-ae26-28bf691d6634' and ambiente = 'PRB'

--UPDATE AMBIENTE PRD CON APLICACION DESDE PRUEBAS
UPDATE controlAcceso.AplicacionPermitida
    SET urlWebhook=N'https://serviciossimplexoperativoprb.xm.com.co/modelo-optimizacion/v1/modelo-gams/respuesta-webhook-enrutador'
    WHERE nombre = 'SIMPLEX_OPERATIVO_XM' and clienteIdJwt = '3ea793ef-6adc-432e-ae26-28bf691d6634' and ambiente = 'PRD'

--UPDATE AMBIENTE PRD
UPDATE controlAcceso.AplicacionPermitida
    SET urlWebhook=N'http://modelo-optimizacion/modelo-optimizacion/v1/modelo-gams/respuesta-webhook-enrutador'
    WHERE nombre = 'SIMPLEX_OPERATIVO_XM' and clienteIdJwt = '3e7d8eb5-5ef3-4e28-af8e-03013bb7c9c7' and ambiente = 'PRD'

--update prb CAL
UPDATE controlAcceso.AplicacionPermitida
    SET urlWebhook=N'https://rio-prb-drh3f5gkc6asfqcg.z01.azurefd.net/servicios/modelooptimizacion/api/gams/motor/respuesta'
    WHERE nombre = 'RIO_XM' and clienteIdJwt = '11e0ae9e-2306-48c9-8acd-e0c998e00175' and ambiente = 'CAL'


UPDATE controlAcceso.AplicacionPermitida
    SET urlWebhook=N'https://api-riocal-bqc6a4b4bhfcfvg2.z01.azurefd.net/modelooptimizacion/api/gams/motor/respuesta'
    WHERE nombre = 'RIO_XM' and clienteIdJwt = '3247581a-5f95-4356-a8a6-efdd4e1e574f' and ambiente = 'CAL'

--update prb RIO
UPDATE controlAcceso.AplicacionPermitida
    SET urlWebhook=N'https://rio-prb-drh3f5gkc6asfqcg.z01.azurefd.net/servicios/modelooptimizacion/api/gams/motor/respuesta'
    WHERE nombre = 'RIO_XM' and clienteIdJwt = '11e0ae9e-2306-48c9-8acd-e0c998e00175' and ambiente = 'PRB'


--update prod RIO
UPDATE controlAcceso.AplicacionPermitida
    SET urlWebhook=N'https://rio.xm.com.co/servicios/modelooptimizacion/api/gams/motor/respuesta'
    WHERE nombre = 'RIO_XM' and clienteIdJwt = '2a9c4424-c6f7-4d44-a6c6-972e6d79f991' and ambiente = 'PRD'

UPDATE controlAcceso.AplicacionPermitida
    SET urlWebhook=N'https://rio-prb-drh3f5gkc6asfqcg.z01.azurefd.net/servicios/modelooptimizacion/api/gams/motor/respuesta'
    WHERE nombre = 'RIO_XM' and clienteIdJwt = '11e0ae9e-2306-48c9-8acd-e0c998e00175' and ambiente = 'PRD'


