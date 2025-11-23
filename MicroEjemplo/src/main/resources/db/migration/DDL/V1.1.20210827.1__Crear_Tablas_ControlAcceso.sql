CREATE TABLE controlAcceso.Rol(
	rolID INT IDENTITY(1,1) NOT NULL,
	objectID VARCHAR (50) NOT NULL,
	nombre VARCHAR (50) NOT NULL,
    estado BIT NOT NULL,
    CONSTRAINT PK_Rol PRIMARY KEY (rolID),
    CONSTRAINT UQ_RolNombre UNIQUE(nombre)
)
GO

CREATE TABLE controlAcceso.Permiso(
    permisoID INT IDENTITY(1,1) NOT NULL,
    nombre VARCHAR (50) NOT NULL,
	codigo VARCHAR (3) NOT NULL,
	estado BIT NOT NULL,
    CONSTRAINT PK_Permiso PRIMARY KEY (permisoID),
    CONSTRAINT UQ_PermisoNombre UNIQUE(nombre),
	CONSTRAINT UQ_PermisoCodigo UNIQUE(codigo)
)
GO

CREATE TABLE controlAcceso.RolPermiso (
    rolPermisoID INT IDENTITY(1,1) NOT NULL,
	rolID INT NOT NULL,
	permisoID INT NOT NULL,
    fechaInicio DATETIME NOT NULL,
    fechaFinal DATETIME NULL,
    CONSTRAINT PK_RolPermiso PRIMARY KEY (RolPermisoID),
	CONSTRAINT FK_RolID_RolPermiso FOREIGN KEY (rolID) REFERENCES controlAcceso.Rol(rolID),
	CONSTRAINT FK_PermisoID_RolPermiso FOREIGN KEY (permisoID) REFERENCES controlAcceso.Permiso(permisoID),
	CONSTRAINT UQ_PermisoRol UNIQUE (rolID, permisoID)
)
GO

