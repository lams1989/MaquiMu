CREATE TABLE controlAcceso.Escenario(
    escenarioID INT IDENTITY(1,1) NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    CONSTRAINT PK_Escenario PRIMARY KEY (escenarioID)
)
GO