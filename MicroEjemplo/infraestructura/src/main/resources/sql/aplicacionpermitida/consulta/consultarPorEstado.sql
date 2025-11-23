SELECT
   aplicacionPermitidaID,
   nombre,
   clienteIdJwt,
   tipoIntegracion,
   tenantId,
   ambiente,
   estado,
   urlWebhook
FROM
   controlAcceso.AplicacionPermitida WITH (NOLOCK)
WHERE
   ambiente = :ambiente
   AND estado = :estado