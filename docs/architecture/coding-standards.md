# Estándares de Código - MaquiMu

## 🎯 Objetivo

Este documento define los estándares de código para el proyecto MaquiMu, asegurando consistencia, mantenibilidad y calidad en todo el código base.

---

## 📋 Principios Generales

1. **Claridad sobre Brevedad**: El código debe ser fácil de entender
2. **DRY (Don't Repeat Yourself)**: Evitar duplicación de código
3. **SOLID**: Seguir los principios SOLID de diseño orientado a objetos
4. **Separation of Concerns**: Separar responsabilidades claramente

---

## ☕ Backend (Java/Spring Boot)

### Nomenclatura

**Clases:**
```java
// PascalCase para clases
public class MaquinariaService { }
public class ClienteController { }
```

**Métodos y Variables:**
```java
// camelCase para métodos y variables
private String nombreCliente;
public void calcularCostoTotal() { }
```

**Constantes:**
```java
// UPPER_SNAKE_CASE para constantes
public static final String ESTADO_DISPONIBLE = "DISPONIBLE";
```

### Arquitectura Hexagonal

**Estructura Multi-módulo (Gradle):**
```
maquimu-backend/
├── dominio/ (Java Puro - Sin dependencias de Framework)
│   └── src/main/java/com/maquimu/dominio/
│       ├── maquinaria/           # Módulo de Maquinaria
│       │   ├── modelo/           # Entidades (Maquinaria, EstadoMaquinaria)
│       │   ├── puerto/
│       │   │   ├── dao/          # MaquinariaDao
│       │   │   └── repositorio/  # MaquinariaRepositorio
│       │   └── servicio/         # MaquinariaComandoServicio, MaquinariaConsultaServicio
│       ├── cliente/              # Módulo de Cliente
│       │   ├── modelo/
│       │   ├── puerto/dao/
│       │   ├── puerto/repositorio/
│       │   └── servicio/
│       ├── alquiler/             # Módulo de Alquiler
│       │   ├── modelo/
│       │   ├── puerto/dao/
│       │   ├── puerto/repositorio/
│       │   └── servicio/
│       ├── autenticacion/        # Módulo de Autenticación
│       │   ├── modelo/
│       │   ├── puerto/dao/
│       │   ├── puerto/repositorio/
│       │   └── servicio/
│       └── compartido/           # Componentes compartidos
│           ├── excepcion/
│           └── valor/            # Value Objects
│
├── aplicacion/ (Casos de Uso - CQRS)
│   └── src/main/java/com/maquimu/aplicacion/
│       ├── maquinaria/
│       │   ├── comando/
│       │   │   ├── fabrica/      # FabricaMaquinaria
│       │   │   └── manejador/    # Handlers de comandos
│       │   └── consulta/
│       │       └── manejador/    # Handlers de consultas
│       ├── cliente/
│       │   ├── comando/fabrica/
│       │   ├── comando/manejador/
│       │   └── consulta/manejador/
│       ├── alquiler/
│       │   ├── comando/fabrica/
│       │   ├── comando/manejador/
│       │   └── consulta/manejador/
│       ├── autenticacion/
│       │   ├── comando/fabrica/
│       │   ├── comando/manejador/
│       │   └── consulta/manejador/
│       └── compartido/
│           └── servicio/         # GeneradorJwt
│
└── infraestructura/ (Framework - Spring Boot)
    └── src/main/java/com/maquimu/infraestructura/
        ├── maquinaria/
        │   ├── adaptador/
        │   │   ├── entidad/      # MaquinariaEntity
        │   │   ├── dao/          # JpaMaquinariaDao
        │   │   └── repositorio/  # JpaMaquinariaRepositorio
        │   └── controlador/      # ComandoControlador, ConsultaControlador
        ├── cliente/
        │   ├── adaptador/entidad/
        │   ├── adaptador/dao/
        │   ├── adaptador/repositorio/
        │   └── controlador/
        ├── alquiler/
        │   ├── adaptador/entidad/
        │   ├── adaptador/dao/
        │   ├── adaptador/repositorio/
        │   └── controlador/
        ├── autenticacion/
        │   ├── adaptador/entidad/
        │   ├── adaptador/dao/
        │   ├── adaptador/repositorio/
        │   └── controlador/
        ├── configuracion/        # Spring Security, CORS, Beans
        └── MaquimuBackendApplication.java
```

### Validaciones

```java
// Usar Bean Validation
public class ClienteDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    @Email(message = "Email inválido")
    private String email;
}
```

### Manejo de Errores

```java
// Usar excepciones personalizadas
public class MaquinariaNotFoundException extends RuntimeException {
    public MaquinariaNotFoundException(Long id) {
        super("Maquinaria no encontrada: " + id);
    }
}

// Global Exception Handler
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MaquinariaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(MaquinariaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(ex.getMessage()));
    }
}
```

### Patrón de Fábricas (Aplicación)

**Responsabilidad:** Construcción de entidades de dominio y DTOs a partir de comandos/consultas.

**Ubicación:** `aplicacion/comando/fabrica/` y `aplicacion/consulta/fabrica/`

**Cuándo usar Fábricas:**
- ✅ Comandos/Consultas con **múltiples parámetros**
- ✅ Lógica de construcción **compleja** (estado inicial, validaciones)
- ✅ Transformación **Comando → Entidad de Dominio**
- ✅ Transformación **Entidad → DTO de Respuesta** (si es compleja)

**Cuándo NO usar Fábricas:**
- ❌ Consultas con **un solo parámetro** (ej: buscar por ID)
- ❌ Comandos de **eliminación** (solo ID)
- ❌ Transformaciones **triviales**

**Flujo de Datos:**
```
Controlador → Manejador → Fábrica → Entidad/DTO → Servicio/Repositorio/DAO
```

**Ejemplo - Fábrica de Comando:**
```java
// Fábrica para crear entidades de dominio
public class FabricaMaquinaria {
    
    /**
     * Crea una nueva Maquinaria a partir de un comando.
     * Establece el estado inicial como DISPONIBLE.
     */
    public Maquinaria crear(ComandoCrearMaquinaria comando) {
        return new Maquinaria(
            null, // ID se genera en BD
            comando.getNombre(),
            comando.getMarca(),
            comando.getModelo(),
            comando.getSerial(),
            EstadoMaquinaria.DISPONIBLE, // Estado inicial
            comando.getTarifaDia(),
            comando.getTarifaHora(),
            comando.getDescripcion()
        );
    }
    
    /**
     * Actualiza una Maquinaria existente con datos del comando.
     */
    public Maquinaria actualizar(Maquinaria existente, ComandoActualizarMaquinaria comando) {
        existente.setNombre(comando.getNombre());
        existente.setMarca(comando.getMarca());
        existente.setModelo(comando.getModelo());
        existente.setSerial(comando.getSerial());
        existente.setEstado(comando.getEstado());
        existente.setTarifaDia(comando.getTarifaDia());
        existente.setTarifaHora(comando.getTarifaHora());
        existente.setDescripcion(comando.getDescripcion());
        return existente;
    }
}
```

**Ejemplo - Uso en Manejador:**
```java
public class ManejadorCrearMaquinaria {
    private final FabricaMaquinaria fabrica;
    private final MaquinariaDao dao;
    private final MaquinariaRepositorio repositorio;
    
    public Maquinaria manejar(ComandoCrearMaquinaria comando) {
        // 1. Validaciones de negocio
        if (dao.existePorSerial(comando.getSerial())) {
            throw new SerialDuplicadoException(comando.getSerial());
        }
        
        // 2. Fábrica crea la entidad de dominio
        Maquinaria maquinaria = fabrica.crear(comando);
        
        // 3. Persistir usando repositorio
        return repositorio.guardar(maquinaria);
    }
}
```

**Ejemplo - Consulta Simple SIN Fábrica:**
```java
// Para consultas simples (un parámetro), NO usar fábrica
public class ManejadorBuscarMaquinaria {
    private final MaquinariaDao dao;
    
    public Maquinaria manejar(Long id) {
        return dao.buscarPorId(id)
            .orElseThrow(() -> new MaquinariaNotFoundException(id));
    }
}
```

---

## 🅰️ Frontend (Angular)


### Nomenclatura

**Componentes:**
```typescript
// kebab-case para selectores
@Component({
  selector: 'app-inventory-list',
  templateUrl: './inventory-list.component.html'
})
export class InventoryListComponent { }
```

**Servicios:**
```typescript
// PascalCase con sufijo Service
export class MaquinariaService { }
```

**Variables y Métodos:**
```typescript
// camelCase
private maquinarias: Maquinaria[];
public getMaquinarias(): void { }
```

### Estructura de Componentes

```typescript
@Component({...})
export class InventoryComponent implements OnInit, OnDestroy {
  // 1. Propiedades públicas
  public maquinarias: Maquinaria[] = [];
  
  // 2. Propiedades privadas
  private destroy$ = new Subject<void>();
  
  // 3. Constructor (solo inyección de dependencias)
  constructor(private maquinariaService: MaquinariaService) {}
  
  // 4. Lifecycle hooks
  ngOnInit(): void {
    this.loadMaquinarias();
  }
  
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
  
  // 5. Métodos públicos
  public loadMaquinarias(): void { }
  
  // 6. Métodos privados
  private handleError(error: any): void { }
}
```

### Reactive Forms

```typescript
// Usar FormBuilder y validaciones
this.maquinariaForm = this.fb.group({
  nombre: ['', [Validators.required, Validators.minLength(3)]],
  serial: ['', [Validators.required]],
  tarifaDia: [0, [Validators.required, Validators.min(0)]]
});
```

### Observables

```typescript
// Usar pipe y takeUntil para evitar memory leaks
this.maquinariaService.getMaquinarias()
  .pipe(takeUntil(this.destroy$))
  .subscribe({
    next: (data) => this.maquinarias = data,
    error: (error) => this.handleError(error)
  });
```

---

## 🗄️ Base de Datos

### Nomenclatura de Tablas

```sql
-- snake_case para tablas y columnas
CREATE TABLE maquinaria (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre_maquina VARCHAR(100) NOT NULL,
    serial VARCHAR(50) UNIQUE NOT NULL,
    tarifa_dia DECIMAL(10,2)
);
```

### Constraints

```sql
-- Siempre definir constraints con nombres descriptivos
ALTER TABLE alquileres
ADD CONSTRAINT fk_alquiler_cliente
FOREIGN KEY (cliente_id) REFERENCES clientes(cliente_id);

ALTER TABLE maquinaria
ADD CONSTRAINT uk_maquinaria_serial UNIQUE (serial);
```

---

## 📝 Comentarios y Documentación

### JavaDoc (Backend)

```java
/**
 * Calcula el costo total de un alquiler basado en las fechas.
 *
 * @param fechaInicio Fecha de inicio del alquiler
 * @param fechaFin Fecha de fin del alquiler
 * @param tarifaDia Tarifa diaria de la maquinaria
 * @return Costo total calculado
 * @throws IllegalArgumentException si fechaFin es anterior a fechaInicio
 */
public BigDecimal calcularCostoTotal(LocalDate fechaInicio, 
                                     LocalDate fechaFin, 
                                     BigDecimal tarifaDia) {
    // Implementación
}
```

### TSDoc (Frontend)

```typescript
/**
 * Carga la lista de maquinarias disponibles desde el servidor.
 * Actualiza la propiedad maquinarias con los datos recibidos.
 */
public loadMaquinarias(): void {
    // Implementación
}
```

---

## ✅ Testing

### Backend (JUnit)

```java
@SpringBootTest
class MaquinariaServiceTest {
    
    @Test
    void deberiaCrearMaquinariaCuandoDatosValidos() {
        // Given
        MaquinariaDTO dto = new MaquinariaDTO("Excavadora", "EXC-001");
        
        // When
        Maquinaria result = service.crear(dto);
        
        // Then
        assertNotNull(result.getId());
        assertEquals("Excavadora", result.getNombre());
    }
}
```

### Frontend (Jasmine/Karma)

```typescript
describe('InventoryComponent', () => {
  it('should load maquinarias on init', () => {
    // Arrange
    const mockData = [{ id: 1, nombre: 'Test' }];
    spyOn(service, 'getMaquinarias').and.returnValue(of(mockData));
    
    // Act
    component.ngOnInit();
    
    // Assert
    expect(component.maquinarias).toEqual(mockData);
  });
});
```

---

## 🔒 Seguridad

1. **Nunca** hardcodear credenciales en el código
2. Usar variables de entorno para configuración sensible
3. Validar **siempre** en el backend, nunca confiar solo en validación frontend
4. Sanitizar inputs para prevenir SQL Injection y XSS
5. Usar HTTPS en producción

---

## 📦 Control de Versiones (Git)

### Commits

```bash
# Formato: <tipo>: <descripción>
feat: agregar endpoint de creación de maquinaria
fix: corregir cálculo de costo en alquileres
docs: actualizar README con instrucciones de instalación
refactor: extraer lógica de validación a servicio separado
test: agregar tests unitarios para ClienteService
```

### Branches

```bash
main           # Producción
develop        # Desarrollo
feature/HU-04  # Nueva funcionalidad (Historia de Usuario #04)
fix/bug-123    # Corrección de bug
```

---

## 🎨 Formato de Código

- **Indentación**: 4 espacios (Java), 2 espacios (TypeScript/HTML)
- **Línea máxima**: 120 caracteres
- **Usar** herramientas de formateo automático:
  - Java: IntelliJ Formatter / Google Java Format
  - TypeScript: Prettier

---

**Última actualización:** 2025-11-22
