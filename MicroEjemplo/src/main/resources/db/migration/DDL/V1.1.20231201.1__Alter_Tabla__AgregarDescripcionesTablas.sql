/*
 * Descripciones de todas las tablas de ControlAcceso
 *
 */

EXEC sp_addextendedproperty
    @name = N'MS_Description', @value = N'Libertades disponibles para desarrollar en simplex operativo xm.',
    @level0type = N'SCHEMA', @level0name = N'controlAcceso',
    @level1type = N'TABLE', @level1name = N'Permiso';

EXEC sp_addextendedproperty
    @name = N'MS_Description', @value = N'Conjunto de permisos disponibles por rol.',
    @level0type = N'SCHEMA', @level0name = N'controlAcceso',
    @level1type = N'TABLE', @level1name = N'RolPermiso';

EXEC sp_addextendedproperty
    @name = N'MS_Description', @value = N'Funciones disponibles para desarrollar como usuario.',
    @level0type = N'SCHEMA', @level0name = N'controlAcceso',
    @level1type = N'TABLE', @level1name = N'Rol';

EXEC sp_addextendedproperty
    @name = N'MS_Description', @value = N'Conjunto de escenarios posibles sobre los que es válido un rol.',
    @level0type = N'SCHEMA', @level0name = N'controlAcceso',
    @level1type = N'TABLE', @level1name = N'Escenario';

GO