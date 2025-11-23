/****** Object:  Table [controlAcceso].[Rol]     ******/
EXEC sp_addextendedproperty 
    @name = N'MS_Description', @value = 'Identificador consecutivo de la tabla',
    @level0type = N'Schema',   @level0name = 'controlAcceso',
    @level1type = N'Table',    @level1name = 'Rol',
    @level2type = N'Column',   @level2name = 'rolID';
GO

EXEC sp_addextendedproperty 
    @name = N'MS_Description', @value = 'Identificador del directorio activo',
    @level0type = N'Schema',   @level0name = 'controlAcceso',
    @level1type = N'Table',    @level1name = 'Rol',
    @level2type = N'Column',   @level2name = 'objectID';
GO

EXEC sp_addextendedproperty 
    @name = N'MS_Description', @value = 'Nombre que identifica el rol',
    @level0type = N'Schema',   @level0name = 'controlAcceso',
    @level1type = N'Table',    @level1name = 'Rol',
    @level2type = N'Column',   @level2name = 'nombre';
GO

EXEC sp_addextendedproperty 
    @name = N'MS_Description', @value = 'Estado del rol',
    @level0type = N'Schema',   @level0name = 'controlAcceso',
    @level1type = N'Table',    @level1name = 'Rol',
    @level2type = N'Column',   @level2name = 'estado';
GO

/****** Object:  Table [controlAcceso].[Permiso]     ******/
EXEC sp_addextendedproperty 
    @name = N'MS_Description', @value = 'Identificador consecutivo de la tabla',
    @level0type = N'Schema',   @level0name = 'controlAcceso',
    @level1type = N'Table',    @level1name = 'Permiso',
    @level2type = N'Column',   @level2name = 'permisoID';
GO

EXEC sp_addextendedproperty 
    @name = N'MS_Description', @value = 'Nombre que identifica el permiso',
    @level0type = N'Schema',   @level0name = 'controlAcceso',
    @level1type = N'Table',    @level1name = 'Permiso',
    @level2type = N'Column',   @level2name = 'nombre';
GO

EXEC sp_addextendedproperty 
    @name = N'MS_Description', @value = 'Identificador del permiso',
    @level0type = N'Schema',   @level0name = 'controlAcceso',
    @level1type = N'Table',    @level1name = 'Permiso',
    @level2type = N'Column',   @level2name = 'codigo';
GO

EXEC sp_addextendedproperty 
    @name = N'MS_Description', @value = 'Estado del permiso',
    @level0type = N'Schema',   @level0name = 'controlAcceso',
    @level1type = N'Table',    @level1name = 'Permiso',
    @level2type = N'Column',   @level2name = 'estado';
GO

/****** Object:  Table [controlAcceso].[RolPermiso]     ******/
EXEC sp_addextendedproperty 
    @name = N'MS_Description', @value = 'Identificador consecutivo de la tabla',
    @level0type = N'Schema',   @level0name = 'controlAcceso',
    @level1type = N'Table',    @level1name = 'RolPermiso',
    @level2type = N'Column',   @level2name = 'rolPermisoID';
GO

EXEC sp_addextendedproperty 
    @name = N'MS_Description', @value = 'Identificador referente al rol',
    @level0type = N'Schema',   @level0name = 'controlAcceso',
    @level1type = N'Table',    @level1name = 'RolPermiso',
    @level2type = N'Column',   @level2name = 'rolID';
GO

EXEC sp_addextendedproperty 
    @name = N'MS_Description', @value = 'Identificador referente al permiso',
    @level0type = N'Schema',   @level0name = 'controlAcceso',
    @level1type = N'Table',    @level1name = 'RolPermiso',
    @level2type = N'Column',   @level2name = 'permisoID';
GO

EXEC sp_addextendedproperty 
    @name = N'MS_Description', @value = 'Fecha inicial que habilita el permiso',
    @level0type = N'Schema',   @level0name = 'controlAcceso',
    @level1type = N'Table',    @level1name = 'RolPermiso',
    @level2type = N'Column',   @level2name = 'fechaInicio';
GO

EXEC sp_addextendedproperty 
    @name = N'MS_Description', @value = 'Fecha final que deshabilita el permiso',
    @level0type = N'Schema',   @level0name = 'controlAcceso',
    @level1type = N'Table',    @level1name = 'RolPermiso',
    @level2type = N'Column',   @level2name = 'fechaFinal';
GO


