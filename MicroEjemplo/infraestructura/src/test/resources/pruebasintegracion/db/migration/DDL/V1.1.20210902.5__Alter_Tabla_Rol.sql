
ALTER TABLE controlAcceso.Rol ADD escenarioID INT NULL
GO

UPDATE controlAcceso.Rol SET escenarioID = 1 WHERE rolID IN (1,3,5)
GO
UPDATE controlAcceso.Rol SET escenarioID = 2 WHERE rolID IN (2,4,6)
GO

ALTER TABLE controlAcceso.Rol ALTER COLUMN escenarioID INT NOT NULL
GO

ALTER TABLE controlAcceso.Rol ADD CONSTRAINT FK_EscID_Rol 
	FOREIGN KEY (escenarioID) REFERENCES controlAcceso.Escenario(escenarioID)
GO
