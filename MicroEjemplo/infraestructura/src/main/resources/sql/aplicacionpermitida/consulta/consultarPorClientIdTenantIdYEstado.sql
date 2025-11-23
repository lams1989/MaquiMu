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
   AND clienteIdJwt = :clienteIdJwt
   AND tenantId = :tenantId
   AND estado = :estado