-- TASK418753
ALTER TABLE controlAcceso.AplicacionPermitida
ADD urlWebhook NVARCHAR(500) NULL;

GO

EXEC sp_addextendedproperty
    @name = N'MS_Description', @value = N'URL del webhook al que se enviará la respuesta.',
    @level0type = N'SCHEMA', @level0name = N'controlAcceso',
    @level1type = N'TABLE', @level1name = N'AplicacionPermitida',
    @level2type = N'COLUMN', @level2name = N'urlWebhook';
GO
