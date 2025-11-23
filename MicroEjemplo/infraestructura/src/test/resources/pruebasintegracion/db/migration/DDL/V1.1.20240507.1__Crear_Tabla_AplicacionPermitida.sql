CREATE TABLE controlAcceso.AplicacionPermitida (
    aplicacionPermitidaID INT NOT NULL,
	nombre NVARCHAR(100) NOT NULL,
	clienteIdJwt NVARCHAR(50) NOT NULL,
	tipoIntegracion NVARCHAR(50) NOT NULL,
	tenantId NVARCHAR(50) NOT NULL,
	ambiente NVARCHAR(3) NOT NULL,
    estado BIT NOT NULL,

    CONSTRAINT PK_aplicacionPermitidaID PRIMARY KEY (aplicacionPermitidaID),
    CONSTRAINT UQ_AplicacionPermitida UNIQUE(nombre, clienteIdJwt, ambiente),
    CONSTRAINT CK_ApPer_TipoIntegracion_VE CHECK (tipoIntegracion IN ('INTERNA_XM', 'SIMPLEX_OPERATIVO')),
    CONSTRAINT CK_ApPer_Ambiente_VE CHECK (ambiente IN ('PRB', 'CAL', 'PRD')),
    )
GO

/****** Object:  Table [controlAcceso].[AplicacionPermitida]     ******/

EXEC sp_addextendedproperty
    @name = N'MS_Description', @value = N'Información detallada de los permisos para aplicaciones permitidas.',
    @level0type = N'SCHEMA', @level0name = N'controlAcceso',
    @level1type = N'TABLE', @level1name = N'AplicacionPermitida';
GO

EXEC sp_addextendedproperty 
    @name = N'MS_Description', @value = 'ID consecutivo de Aplicación permitida.',
    @level0type = N'Schema', @level0name = 'controlAcceso',
    @level1type = N'Table', @level1name = 'AplicacionPermitida',
    @level2type = N'Column', @level2name = 'aplicacionPermitidaID'
GO

EXEC sp_addextendedproperty 
    @name = N'MS_Description', @value = 'Nombre de la aplicación permitida.',
    @level0type = N'Schema', @level0name = 'controlAcceso',
    @level1type = N'Table', @level1name = 'AplicacionPermitida',
    @level2type = N'Column', @level2name = 'nombre'
GO

EXEC sp_addextendedproperty
    @name = N'MS_Description', @value = 'Cliente Id Jwt de aplicación.',
    @level0type = N'Schema', @level0name = 'controlAcceso',
    @level1type = N'Table', @level1name = 'AplicacionPermitida',
    @level2type = N'Column', @level2name = 'clienteIdJwt'
GO

EXEC sp_addextendedproperty
    @name = N'MS_Description', @value = 'Tipo Integración (INTERNA_XM, SIMPLEX_OPERATIVO).',
    @level0type = N'Schema', @level0name = 'controlAcceso',
    @level1type = N'Table', @level1name = 'AplicacionPermitida',
    @level2type = N'Column', @level2name = 'tipoIntegracion'
GO

EXEC sp_addextendedproperty
    @name = N'MS_Description', @value = 'Tenant Id de aplicación.',
    @level0type = N'Schema', @level0name = 'controlAcceso',
    @level1type = N'Table', @level1name = 'AplicacionPermitida',
    @level2type = N'Column', @level2name = 'tenantId'
GO

EXEC sp_addextendedproperty
    @name = N'MS_Description', @value = 'Ambiente (PRB, CAL, PRD).',
    @level0type = N'Schema', @level0name = 'controlAcceso',
    @level1type = N'Table', @level1name = 'AplicacionPermitida',
    @level2type = N'Column', @level2name = 'ambiente'
GO

EXEC sp_addextendedproperty 
    @name = N'MS_Description', @value = 'Estado de permiso externo.',
    @level0type = N'Schema', @level0name = 'controlAcceso',
    @level1type = N'Table', @level1name = 'AplicacionPermitida',
    @level2type = N'Column', @level2name = 'estado'
GO