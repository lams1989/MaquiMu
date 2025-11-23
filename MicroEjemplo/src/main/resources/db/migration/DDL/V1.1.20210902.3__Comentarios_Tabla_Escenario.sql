EXEC sp_addextendedproperty 
    @name = N'MS_Description', @value = 'ID consecutivo de la tabla',
    @level0type = N'Schema', @level0name = 'controlAcceso',
    @level1type = N'Table', @level1name = 'Escenario', 
    @level2type = N'Column', @level2name = 'escenarioID'
GO

EXEC sp_addextendedproperty 
    @name = N'MS_Description', @value = 'Nombre del escenario',
    @level0type = N'Schema', @level0name = 'controlAcceso',
    @level1type = N'Table', @level1name = 'Escenario', 
    @level2type = N'Column', @level2name = 'nombre'
GO