<!-- Powered by Método luis -->

# Generar Estándares de Código

## Propósito

Crear o actualizar el documento de estándares de código (`docs/architecture/coding-standards.md`) basándose en análisis del código existente, documentación previa y mejores prácticas específicas del proyecto. Esta tarea identifica patrones, convenciones y reglas de desarrollo que deben ser seguidas por el equipo.

## Cuándo Usar Esta Tarea

**Usa esta tarea cuando:**

- Necesitas crear estándares de código para un proyecto existente
- Quieres formalizar las convenciones no documentadas del equipo
- Requieres actualizar estándares obsoletos o incompletos
- El proyecto no tiene documentación de estándares pero sí código establecido
- Nuevos miembros del equipo necesitan guías claras de desarrollo

**Prerequisites:**

- Proyecto con código base existente (preferiblemente)
- Configuración de `core-config.yaml` con `architectureShardedLocation`
- Acceso al código fuente para análisis

## Instrucciones de Ejecución de la Tarea

### 0. CONFIGURACIÓN Y CONTEXTO INICIAL

#### 0.1 Cargar Configuración del Proyecto

- Verificar que existe `.luis-metodo/core-config.yaml`
- Confirmar configuración de `architectureShardedLocation: {architectureShardedLocation}`
- Si no existe, usar `docs/architecture` como ubicación por defecto

#### 0.2 Elicitación Inicial de Contexto

**Realizar las siguientes preguntas obligatorias al usuario:**

1. **Documentación Existente:**
   - "¿Existe algún documento de estándares de desarrollo que pueda usar como base?"
   - "¿Hay algún README, CONTRIBUTING.md, o guía de estilos que deba considerar?"

2. **Estándares No Documentados:**
   - "¿Hay convenciones o reglas de desarrollo que el equipo sigue pero no están documentadas?"
   - "¿Existen patrones específicos que quieres que se incluyan obligatoriamente?"

3. **Tecnologías y Herramientas:**
   - "¿Usan algún linter específico (ESLint, Prettier, SonarQube, etc.)?"

### 1. ANÁLISIS DE DOCUMENTACIÓN EXISTENTE

#### 1.1 Revisar Documentación de Estándares

**Buscar y analizar documentación existente:**

- Buscar archivos como `CONTRIBUTING.md`, `README.md`, `.eslintrc`, `prettier.config.js`
- Revisar si existe `{architectureShardedLocation}/coding-standards.md`
- Analizar configuraciones de linters y herramientas de calidad
- Revisar documentación de arquitectura existente si está disponible

#### 1.2 Extraer Información Existente

**Documentar hallazgos:**

- **Reglas ya documentadas**: Extraer estándares explícitos existentes
- **Configuraciones de herramientas**: Analizar configuraciones de linters, formatters
- **Convenciones implícitas**: Identificar patrones en documentación existente

### 2. ANÁLISIS DEL CÓDIGO BASE

#### 2.1 Análisis de Patrones de Código

**Analizar la estructura y patrones del código existente:**

- Revisar estructura de directorios y organización de archivos
- Identificar patrones de nomenclatura (variables, funciones, clases, archivos)
- Analizar estilos de comentarios y documentación en código
- Examinar patrones arquitectónicos utilizados (MVC, componentes, servicios)

#### 2.2 Análisis de Tecnologías y Herramientas

**Identificar stack tecnológico y herramientas:**

- Detectar lenguajes principales y versiones
- Identificar frameworks y librerías principales
- Analizar herramientas de build y testing
- Revisar configuraciones de CI/CD si están disponibles

#### 2.3 Identificación de Convenciones

**Extraer convenciones implícitas del código:**

- **Nombres**: Patrones de nomenclatura para variables, funciones, clases
- **Estructura**: Organización de archivos y directorios
- **Comentarios**: Estilos de documentación en código
- **Imports**: Convenciones de importación y organización
- **Testing**: Patrones de naming y organización de tests

### 3. CONSOLIDACIÓN DE ESTÁNDARES

#### 3.1 Combinar Fuentes de Información

**Consolidar información de:**

- Documentación existente analizada
- Respuestas del usuario en elicitación
- Patrones identificados en el código base
- Mejores prácticas generales para las tecnologías detectadas

#### 3.2 Categorizar Estándares

**Organizar en categorías:**

- **Estándares obligatorios**: Reglas críticas que deben seguirse
- **Convenciones recomendadas**: Patrones sugeridos pero no obligatorios
- **Herramientas y configuración**: Setup de linters y formatters
- **Organización**: Estructura de archivos y directorios

### 4. CREACIÓN DEL DOCUMENTO DE ESTÁNDARES

#### 4.1 Crear Estructura de Archivo

**Crear el archivo de estándares:**

```
{architectureShardedLocation}/
└── coding-standards.md    # Documento de estándares de código
```

#### 4.2 Generar `coding-standards.md` - Estándares de Código

**Crear documento completo basado en el análisis realizado:**

**Template de estándares:**

````markdown
# {Nombre del Proyecto} - Estándares de Código 📝

## 📋 **Información General**

### Propósito del Documento

Este documento define los estándares de código obligatorios y recomendados para el desarrollo en {Nombre del Proyecto}. Estos estándares garantizan consistencia, legibilidad y mantenibilidad del código.

**Audiencia**: Desarrolladores, Code Reviewers, DevOps  
**Última Actualización**: {fecha actual}  
**Estado**: {Activo/En Revisión}

---

## 🚨 **Estándares Obligatorios**

### 1. Nomenclatura

#### Variables y Funciones

```{lenguaje principal}
// ✅ CORRECTO
const userAccountBalance = 1500;
function calculateTotalPrice(items) { ... }

// ❌ INCORRECTO
const bal = 1500;
function calc(items) { ... }
```

#### Clases y Componentes

```{lenguaje principal}
// ✅ CORRECTO
class PaymentProcessor { ... }
const UserProfileComponent = () => { ... }

// ❌ INCORRECTO
class payment { ... }
const userprofile = () => { ... }
```

#### Archivos y Directorios

```bash
# ✅ CORRECTO
src/
├── components/
│   ├── UserProfile.{extensión}
│   └── PaymentForm.{extensión}
├── services/
│   └── payment-service.{extensión}

# ❌ INCORRECTO
src/
├── comp/
│   ├── userprofile.{extensión}
│   └── paymentform.{extensión}
```

### 2. Estructura de Código

#### Organización de Imports

```{lenguaje principal}
// ✅ CORRECTO - Orden de imports
// 1. Librerías externas
import React from 'react';
import axios from 'axios';

// 2. Imports internos (servicios, utils)
import { PaymentService } from '../services/payment-service';
import { formatCurrency } from '../utils/formatters';

// 3. Imports de componentes
import UserProfile from './UserProfile';
```

#### Estructura de Funciones

```{lenguaje principal}
// ✅ CORRECTO
/**
 * Calcula el precio total incluyendo impuestos
 * @param {Object[]} items - Lista de items del carrito
 * @param {number} taxRate - Tasa de impuesto (0-1)
 * @returns {number} Precio total con impuestos
 */
function calculateTotalWithTax(items, taxRate) {
  if (!items || !Array.isArray(items)) {
    throw new Error('Items debe ser un array válido');
  }

  const subtotal = items.reduce((sum, item) => sum + item.price, 0);
  return subtotal * (1 + taxRate);
}
```

### 3. Manejo de Errores

#### Manejo de Errores Obligatorio

```{lenguaje principal}
// ✅ CORRECTO
try {
  const result = await apiCall();
  return result;
} catch (error) {
  logger.error('Error en apiCall:', error);
  throw new ApiError('Falló la llamada a la API', error);
}

// ❌ INCORRECTO
const result = await apiCall(); // Sin manejo de errores
```

### 4. Comentarios y Documentación

#### Comentarios Obligatorios

```{lenguaje principal}
// ✅ CORRECTO - Funciones públicas documentadas
/**
 * Función que procesa pagos de usuarios
 * @param {Object} paymentData - Datos del pago
 * @param {string} paymentData.amount - Monto en centavos
 * @param {string} paymentData.currency - Código de moneda ISO
 * @returns {Promise<Object>} Resultado del procesamiento
 */

// ✅ CORRECTO - Lógica compleja explicada
// Aplicamos descuento escalonado: 5% > $100, 10% > $500, 15% > $1000
if (amount > 100000) { // $1000 en centavos
  discount = 0.15;
}
```

---

## 💡 **Convenciones Recomendadas**

### 1. Organización de Archivos

```
src/
├── components/           # Componentes reutilizables
│   ├── common/          # Componentes compartidos
│   └── pages/           # Componentes específicos de página
├── services/            # Lógica de negocio y APIs
├── utils/               # Funciones utilitarias
├── hooks/               # Custom hooks (si aplica)
├── types/               # Definiciones de tipos
└── __tests__/           # Tests organizados por módulo
```

### 2. Patrones de Código

#### Destructuring y Spread

```{lenguaje principal}
// ✅ RECOMENDADO
const { name, email, address } = user;
const newUser = { ...user, lastLogin: new Date() };

// 👌 ACEPTABLE pero menos preferido
const name = user.name;
const email = user.email;
```

#### Funciones Puras cuando sea Posible

```{lenguaje principal}
// ✅ RECOMENDADO - Función pura
function formatPrice(amount, currency) {
  return new Intl.NumberFormat('es-ES', {
    style: 'currency',
    currency: currency
  }).format(amount);
}
```

---

## 🔧 **Configuración de Herramientas**

### ESLint/Linter Configuration

```json
{
  "extends": ["{configuración base identificada}"],
  "rules": {
    "{regla 1}": "{valor}",
    "{regla 2}": "{valor}"
  }
}
```

### Prettier/Formatter Configuration

```json
{
  "printWidth": {valor identificado},
  "tabWidth": {valor identificado},
  "singleQuote": {true/false},
  "trailingComma": "{configuración}"
}
```

### Scripts Recomendados

```json
{
  "scripts": {
    "lint": "{comando de linting}",
    "lint:fix": "{comando de auto-fix}",
    "format": "{comando de formateo}",
    "test": "{comando de testing}"
  }
}
```

---

## 🧪 **Estándares de Testing**

### Nomenclatura de Tests

```{lenguaje de testing}
// ✅ CORRECTO
describe('PaymentProcessor', () => {
  describe('processPayment', () => {
    test('should process valid payment successfully', () => {
      // Test implementation
    });

    test('should throw error when amount is negative', () => {
      // Test implementation
    });
  });
});
```

### Estructura de Tests

```{lenguaje de testing}
// ✅ PATRÓN AAA (Arrange, Act, Assert)
test('should calculate discount correctly', () => {
  // Arrange
  const amount = 1000;
  const discountRate = 0.1;

  // Act
  const result = calculateDiscount(amount, discountRate);

  // Assert
  expect(result).toBe(100);
});
```

---

## 📊 **Métricas y Calidad**

### Umbrales de Calidad

| Métrica                     | Umbral Mínimo   | Herramienta   |
| --------------------------- | --------------- | ------------- |
| **Cobertura de Tests**      | {umbral}%       | {herramienta} |
| **Complejidad Ciclomática** | < {valor}       | {herramienta} |
| **Duplicación de Código**   | < {porcentaje}% | {herramienta} |
| **Deuda Técnica**           | < {tiempo}      | {herramienta} |

### Code Review Checklist

- [ ] ✅ Nomenclatura sigue convenciones
- [ ] ✅ Manejo de errores implementado
- [ ] ✅ Tests cubren casos principales
- [ ] ✅ Sin código duplicado
- [ ] ✅ Performance considerada
- [ ] ✅ Seguridad validada

---

## 🚀 **Mejores Prácticas Específicas**

### {Tecnología Específica 1}

{Mejores prácticas específicas identificadas del código}

### {Tecnología Específica 2}

{Mejores prácticas específicas identificadas del código}

### Performance

{Prácticas de performance identificadas}

### Seguridad

{Prácticas de seguridad identificadas}

---

## 🔄 **Proceso de Actualización**

### Cuándo Actualizar estos Estándares

1. **Cambios de tecnología** - Nuevas versiones principales de frameworks
2. **Lecciones aprendidas** - Problemas recurrentes identificados
3. **Revisión trimestral** - Evaluación periódica de efectividad
4. **Onboarding feedback** - Comentarios de nuevos desarrolladores

### Proponer Cambios

1. Crear issue/ticket describiendo la propuesta
2. Discutir en review de equipo
3. Implementar en branch de prueba
4. Actualizar este documento
5. Comunicar cambios al equipo

---

## 📚 **Referencias y Recursos**

### Documentación Oficial

- [{Tecnología 1} Style Guide]({enlace})
- [{Tecnología 2} Best Practices]({enlace})

### Herramientas Útiles

- **Linting**: {herramientas configuradas}
- **Formatting**: {herramientas configuradas}
- **Testing**: {herramientas configuradas}

### Recursos de Aprendizaje

- [{Recurso 1}]({enlace})
- [{Recurso 2}]({enlace})

---

**NOTA IMPORTANTE**: Estos estándares fueron generados analizando el código base existente y las prácticas del equipo. Deben evolucionar con el proyecto y ser revisados periódicamente.

---

_Documento generado con Método luis - Arquitecto_  
_Última actualización: {fecha}_  
_Versión: 1.0_
````

#### 4.3 Validación y Refinamiento

**Revisar el documento generado:**

- Verificar que los estándares reflejen realmente el código existente
- Asegurar que las reglas sean prácticas y aplicables
- Validar que las configuraciones de herramientas sean correctas
- Confirmar que los ejemplos de código sean precisos

**Preguntar al usuario:**

- "¿Los estándares capturan correctamente las convenciones del proyecto?"
- "¿Hay alguna regla específica que quieras agregar o modificar?"
- "¿La configuración de herramientas refleja lo que actualmente usan?"

### 5. ENTREGA Y COMUNICACIÓN

**Mensaje final obligatorio:**

```
Estándares de código generados exitosamente.

**Archivo creado:** `{architectureShardedLocation}/coding-standards.md`

**Análisis completado:**
✅ Documentación existente revisada
✅ Código base analizado para identificar patrones
✅ {número} tecnologías principales documentadas
✅ {número} reglas obligatorias definidas
✅ {número} convenciones recomendadas incluidas
✅ Configuración de herramientas especificada

**Contenido generado:**
- Stack tecnológico identificado
- Estándares obligatorios basados en análisis de código
- Convenciones recomendadas del equipo
- Configuración de linters y formatters
- Umbrales de calidad y métricas
- Proceso de actualización definido

**Próximos pasos:**
1. Revisar el documento con el equipo de desarrollo
2. Ajustar configuraciones de herramientas si es necesario
3. Integrar en el flujo de CI/CD
4. Incluir en proceso de onboarding

¿El documento refleja correctamente los estándares del proyecto?
```

## Criterios de Calidad para los Estándares

- **✅ Basado en realidad**: Estándares extraídos del código existente
- **✅ Práctico**: Reglas aplicables y verificables
- **✅ Completo**: Cubre tecnologías principales del proyecto
- **✅ Específico**: Ejemplos concretos de código correcto e incorrecto
- **✅ Herramientas**: Configuración de linters y formatters incluida
- **✅ Evolutivo**: Proceso de actualización definido

## Reglas de Comportamiento OBLIGATORIAS

1. **ANÁLISIS PRIMERO**: Analizar código existente antes de crear estándares teóricos
2. **ELICITACIÓN COMPLETA**: Obtener toda la información del usuario antes de proceder
3. **BASADO EN EVIDENCIA**: Estándares deben reflejar patrones reales del código
4. **EJEMPLOS PRÁCTICOS**: Incluir código de ejemplo extraído del proyecto
5. **HERRAMIENTAS REALES**: Configuraciones basadas en lo que realmente usa el proyecto
6. **VALIDACIÓN FINAL**: Confirmar con el usuario que los estándares son correctos
