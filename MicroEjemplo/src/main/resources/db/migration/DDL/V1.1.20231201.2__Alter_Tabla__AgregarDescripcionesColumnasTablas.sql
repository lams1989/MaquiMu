/*
 * Descripciones de las columnas faltantes de las tablas de ControlAcceso
 *
 */

EXEC sp_addextendedproperty
    @name = N'MS_Description', @value = 'ID del escenario al que aplica el rol',
    @level0type = N'Schema', @level0name = 'controlAcceso',
    @level1type = N'Table', @level1name = 'Rol',
    @level2type = N'Column', @level2name = 'escenarioID'

GO