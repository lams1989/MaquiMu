SELECT R.rolID, R.nombre nombreRol, ESC.escenarioID, ESC.nombre nombreEscenario
  FROM controlAcceso.Rol R WITH(NOLOCK)
  JOIN controlAcceso.Escenario ESC WITH(NOLOCK) ON R.escenarioID  = ESC.escenarioID
   AND R.nombre IN (:nombres)