-- Flyway migration script para actualizar los roles de usuario
-- Mapea los roles antiguos a los nuevos roles de dominio

-- Asumimos que 'admin' corresponde a 'OPERARIO'
UPDATE usuarios SET rol = 'OPERARIO' WHERE rol = 'admin';

-- Asumimos que 'agente' puede corresponder a 'OPERARIO' o a un rol a definir.
-- Por ahora, lo mapeamos a OPERARIO como una acción segura.
-- Si 'agente' debía ser 'CLIENTE', este script se puede ajustar.
UPDATE usuarios SET rol = 'agente' WHERE rol = 'agente';

-- Nota: El script de creación original usa VARCHAR(50) para el rol,
-- por lo que no es necesario un ALTER TABLE para cambiar el tipo de dato.
-- Esta migración solo se encarga de la consistencia de los datos.
