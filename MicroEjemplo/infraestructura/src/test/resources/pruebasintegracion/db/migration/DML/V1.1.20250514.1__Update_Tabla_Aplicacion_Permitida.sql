--- TASK440205 Se ajusta la URL del webhook de RIO para Calidad.

UPDATE controlAcceso.AplicacionPermitida
SET urlWebhook='https://rio-cal-dmdwf9gzathxf4ba.z01.azurefd.net/servicios/modelooptimizacion/api/gams/motor/respuesta'
WHERE clienteIdJwt='3247581a-5f95-4356-a8a6-efdd4e1e574f';
