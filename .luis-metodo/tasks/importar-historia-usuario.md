<!-- Powered by Método luis -->

# Importar Historia de Usuario - Validación y Completitud

## Purpose

Importar una historia de usuario existente validando su completitud y claridad mediante análisis de documentación previa y código. Asegurar que la historia esté completa antes de ser integrada al flujo de trabajo, realizando preguntas de aclaración si es necesario para completar información faltante.

## When to Use This Task

**Use this task when:**

- Se tiene una historia de usuario externa que se quiere integrar
- Se necesita validar que una historia importada esté completa y clara
- Se requiere verificar consistencia con la arquitectura y documentación existente
- Se debe asegurar que no falte información crítica antes de proceder al flujo

**Prerequisites:**

- Historia de usuario existente para importar
- Configuración del proyecto (`devStoryLocation` en core-config.yaml)
- Acceso al usuario para aclaraciones si es necesario

## 🎯 RESPONSABILIDADES DEL PRODUCT OWNER

**IMPORTANTE:** Como PO, tu enfoque debe ser 100% de NEGOCIO:

- ✅ **PUEDES hacer internamente**: Revisar código, documentación y arquitectura para validar consistencia
- ✅ **TUS PREGUNTAS AL USUARIO**: Solo aspectos de negocio, completitud funcional, criterios de aceptación
- ❌ **NO preguntes al usuario**: Detalles técnicos, arquitectura, tecnologías específicas

**Las dudas técnicas las resuelves internamente o las delegas al Arquitecto y Scrum Master pero nunca le preguntes nada técnico al usuario**

## Task Execution Instructions

### 0. CAPTURA INICIAL DE LA HISTORIA A IMPORTAR

**🎯 OBJETIVO: Obtener la historia de usuario que se quiere importar**

#### 0.1 Solicitar Historia a Importar y Caracterización del Perfil

**Mensaje:**
"¡Hola! Voy a ayudarte a importar una historia de usuario validando que esté completa y clara.

**Paso 1/6: Caracterización de perfil e información de la historia a importar**

Primero, necesito conocer tu perfil para adaptar mis preguntas de validación de manera adecuada:

**Caracterización de perfil:**

¿Cuál de estas opciones describe mejor tu rol y conocimiento?

**A) Perfil Funcional Puro:**

- Te enfocas exclusivamente en aspectos de negocio
- No manejas conceptos técnicos (APIs, bases de datos, arquitectura)
- Tu expertise está en procesos, flujos de usuario y reglas de negocio

**B) Perfil Técnico-Funcional:**

- Tienes conocimiento tanto de negocio como técnico
- Puedes discutir aspectos de arquitectura, integración y tecnología
- Entiendes conceptos como APIs, microservicios, bases de datos

**Y ahora proporciona la historia a importar:**

1. **El contenido completo** de la historia de usuario que quieres importar
2. **El número** que quieres asignarle (ejemplo: 5)
3. **El nombre** del archivo (ejemplo: login-usuarios)

**Formato de respuesta:**

```
Perfil: A o B
Número: 5
Nombre: login-usuarios
Historia:
[contenido completo de la historia aquí]
```

Una vez que tengas esto listo, validaré que la historia esté completa basándome en la documentación y arquitectura existente."

**REGISTRAR INTERNAMENTE la respuesta del perfil para condicionar todas las preguntas posteriores:**

- **Si responde A (Funcional Puro)**: SOLO hacer preguntas de negocio, completitud funcional y criterios de aceptación
- **Si responde B (Técnico-Funcional)**: Permitir preguntas técnicas cuando sea necesario para validar la historia

#### 0.2 Validar Información Mínima

**BLOQUEO OBLIGATORIO:** No continuar hasta obtener:

- **Caracterización del perfil**: A o B claramente especificado
- **Número de historia**: Número que se asignará
- **Nombre del archivo**: Nombre para el archivo
- **Contenido completo de la historia**: Historia completa a importar

**Si la información es insuficiente:**
"Necesito toda la información para poder validar correctamente. Por favor proporciona:

1. **Perfil:** ¿A (Funcional Puro) o B (Técnico-Funcional)?
2. **Número:** ¿Qué número le asignas a esta historia?
3. **Nombre:** ¿Cómo se llamará el archivo?
4. **Historia completa:** El contenido completo de la historia de usuario a importar"

### 1. ANÁLISIS OBLIGATORIO DE CONTEXTO Y VALIDACIÓN

**🛑 PASO OBLIGATORIO - NO CONTINUAR SIN COMPLETAR**

**EJECUTAR INMEDIATAMENTE después de recibir la historia a importar:**

#### 1.1 Cargar Configuración del Proyecto

**OBLIGATORIO antes de cualquier análisis:**

- Cargar `.luis-metodo/core-config.yaml`
- Extraer configuraciones: `architectureShardedLocation`, `architectureFile`, `devStoryLocation`
- Si no existe core-config.yaml, HALT: "core-config.yaml no encontrado. Ejecuta la instalación de BMad primero."

#### 1.2 Análisis Obligatorio de Documentación Arquitectónica

**BLOQUEO TOTAL: Este análisis es OBLIGATORIO antes de continuar al paso 2**

**1. Documentación arquitectónica existente (OBLIGATORIO):**

- **DEBE revisar** si existe arquitectura base (`{architectureShardedLocation}/index.md`)
- **DEBE buscar** documentación de componentes relacionados con la historia (`{architectureShardedLocation}/architecture-*.md`)
- **DEBE revisar** flujos de negocio existentes que puedan estar relacionados (`{architectureShardedLocation}/flujo-*.md`)

**2. Historias de usuario existentes (OBLIGATORIO):**

- **DEBE revisar** historias existentes en `{devStoryLocation}` para identificar:
  - Funcionalidades similares o relacionadas con la historia a importar
  - Patrones de criterios de aceptación ya establecidos
  - Actores y roles ya definidos
  - Componentes ya mencionados
  - Estándares de formato y estructura utilizados
- **DEBE buscar** historias que involucren las mismas entidades de negocio
- **DEBE identificar** dependencias potenciales
- **DEBE extraer** patrones de nomenclatura y estructura

**3. Análisis de funcionalidades existentes (OBLIGATORIO si aplica):**

- Buscar funcionalidades similares para entender patrones de negocio
- Identificar procesos de negocio existentes mencionados en la historia
- Validar que las referencias funcionales sean consistentes

#### 1.3 Extracción de Conceptos de la Historia Importada

**DEBE extraer de la historia importada:**

- 🎯 **Actores y roles** mencionados
- 🔄 **Procesos y flujos** descritos
- 📋 **Entidades y datos** referenciados
- 🏗️ **Módulos y componentes** mencionados
- 🔗 **Integraciones** descritas
- 📝 **Criterios de aceptación** existentes
- ⚠️ **Términos técnicos** utilizados

### 2. ANÁLISIS EXHAUSTIVO DE COMPLETITUD DE LA HISTORIA IMPORTADA

**🔍 EVALUACIÓN CRÍTICA OBLIGATORIA: Revisar si la historia importada tiene TODA la información necesaria para que un desarrollador pueda trabajar SIN ASUMIR NADA.**

**CRITERIOS OBJETIVOS DE SUFICIENCIA - TODOS deben estar claros :**

#### 2.1 Información de Usuario y Contexto (OBLIGATORIO)

- ❓ **¿Está claro QUIÉN específicamente puede realizar esta acción?** (rol exacto)
- ❓ **¿Están definidos los PERMISOS específicos necesarios?**
- ❓ **¿Es evidente el VALOR DE NEGOCIO concreto?**

#### 2.2 Funcionalidad Específica (OBLIGATORIO)

- ❓ **¿Está clara la ACCIÓN EXACTA que realiza el usuario?**
- ❓ **¿Están definidos los DATOS de entrada con tipos y formatos?**
- ❓ **¿Están claras las VALIDACIONES y reglas de negocio?**

#### 2.3 Criterios de Aceptación (OBLIGATORIO)

- ❓ **¿Existen criterios de aceptación específicos y medibles?**
- ❓ **¿Están definidos los escenarios de éxito y fallo?**
- ❓ **¿Son verificables sin ambigüedad?**

#### 2.4 Interfaz y Comportamiento (OBLIGATORIO)

- ❓ **¿Está claro DÓNDE se ubica en el sistema?**
- ❓ **¿Están claros los MENSAJES específicos?**

#### 2.5 Casos Extremos y Errores (OBLIGATORIO)

- ❓ **¿Están definidos los comportamientos en CASOS DE FALLO?**
- ❓ **¿Están claros los escenarios con DATOS EXTREMOS?**

#### 2.6 Impacto en el Sistema (OBLIGATORIO si aplica)

- ❓ **¿Están claros los MÓDULOS o PROCESOS que se ven afectados?** (basado en análisis interno)
- ❓ **¿Están definidas las INTEGRACIONES con otros procesos de negocio?**

**🚨 REGLA CRÍTICA: Si hay CUALQUIER "❓" sin respuesta clara, la historia es INCOMPLETA**

**DECISIÓN ESTRICTA:**

- **✅ TODOS los criterios claros + consistente con documentación** → Saltar al paso 5 (Importar Historia)
- **❌ CUALQUIER criterio unclear o inconsistencia** → OBLIGATORIO continuar al paso 3 (Preguntas de Aclaración)

### 3. PREGUNTAS DE ACLARACIÓN (CUANDO LA HISTORIA ESTÁ INCOMPLETA)

**🎯 OBJETIVO: Completar la información faltante mediante preguntas específicas**

#### 3.1 Preparar Preguntas Específicas

**⚠️ FILTRO OBLIGATORIO POR PERFIL TÉCNICO:**

**🎯 PARA PERFIL FUNCIONAL PURO (opción A):**

- ✅ **PERMITIDAS**: Preguntas de negocio, criterios de aceptación, completitud funcional, validaciones de negocio
- ❌ **PROHIBIDAS**: Arquitectura, APIs, tecnologías, integraciones técnicas, estructura de datos, términos técnicos

**🔧 PARA PERFIL TÉCNICO-FUNCIONAL (opción B):**

- ✅ **PERMITIDAS**: Todas las preguntas, incluyendo aspectos técnicos cuando sea necesario para validar completitud

**DEBE generar preguntas específicas basadas en:**

- Gaps identificados en el análisis de completitud (filtradas por perfil de usuario identificado)
- Inconsistencias con documentación existente
- Información faltante según los criterios de suficiencia
- Diferencias con patrones establecidos

#### 3.2 Solicitar Aclaraciones al Usuario

**Mensaje estructurado:**

"**📋 VALIDACIÓN DE HISTORIA COMPLETADA**

He analizado la historia que quieres importar y encontré algunos puntos que necesitan aclaración para asegurar que esté completa:

**❌ INFORMACIÓN FALTANTE IDENTIFICADA:**

[Lista específica de gaps encontrados]

**❌ INCONSISTENCIAS CON DOCUMENTACIÓN EXISTENTE:**

[Lista de inconsistencias si las hay]

**🔍 PREGUNTAS DE ACLARACIÓN:**

[Preguntas específicas numeradas]

**Por favor responde estas preguntas para completar la historia antes de importarla.**"

#### 3.3 Esperar Respuestas del Usuario

**BLOQUEO OBLIGATORIO:** No continuar hasta recibir respuestas a todas las preguntas.

**Si las respuestas son insuficientes:**
"Necesito más detalles en las siguientes respuestas: [especificar cuáles]"

### 4. INTEGRACIÓN DE MEJORAS

**🔧 OBJETIVO: Integrar las aclaraciones recibidas en la historia**

#### 4.1 Actualizar Historia con Aclaraciones

- Integrar las respuestas del usuario en la historia original
- Asegurar consistencia con documentación existente
- Aplicar patrones y nomenclatura establecidos
- Mantener la estructura de historias del proyecto

#### 4.2 Validación Final de Completitud

**Revisar nuevamente todos los criterios de suficiencia:**

- Verificar que todas las preguntas fueron respondidas
- Confirmar que no quedan gaps de información
- Validar consistencia total con documentación
- Asegurar que sigue los patrones establecidos

#### 4.3 Solicitar Confirmación Final

**Mensaje:**

"**✅ HISTORIA ACTUALIZADA CON TUS ACLARACIONES**

He integrado tus respuestas en la historia. Esta es la versión final que se importará:

```markdown
[Historia completa actualizada]
```

**¿Confirmas que esta versión final es correcta y está completa para proceder con la importación?**

**Opciones:**

- ✅ **SÍ** - Proceder con la importación
- ❌ **NO** - Hacer ajustes adicionales"

**BLOQUEO:** Esperar confirmación del usuario.

### 5. IMPORTACIÓN FINAL DE LA HISTORIA

**💾 OBJETIVO: Crear el archivo final de la historia validada**

#### 5.1 Crear Archivo de Historia

**Archivo:** `{devStoryLocation}/{número}.{nombre}.story.md`

**Estructura del archivo:**

```markdown
# Historia #{número}: {nombre}

**Estado:** Importada
**Fecha:** {fecha actual}
**Origen:** Historia importada y validada

---

{contenido completo de la historia validada y mejorada}

---

## Información de Importación

**Proceso completado:** ✅ Validación de completitud
**Análisis realizado:** ✅ Consistencia con documentación
**Preguntas de aclaración:** {SÍ/NO según si hubo preguntas}
**Versión final confirmada:** ✅ Usuario confirmó

**Archivo creado:** {número}.{nombre}.story.md
```

#### 5.2 Confirmación Final

**Mensaje exacto:**

"**✅ HISTORIA IMPORTADA EXITOSAMENTE**

**Archivo creado:** `{número}.{nombre}.story.md`
**Ubicación:** `{devStoryLocation}/{número}.{nombre}.story.md`

**Resumen del proceso:**

- ✅ **Validación de completitud:** Realizada
- ✅ **Análisis de consistencia:** Verificada con documentación existente
- ✅ **Aclaraciones integradas:** {número de preguntas respondidas si aplica}
- ✅ **Confirmación final:** Recibida del usuario

**La historia está lista para continuar en el flujo de trabajo:**

**Próximos pasos:**

1. **🏗️ ANÁLISIS ARQUITECTÓNICO PRIMERO:** El Arquitecto debe usar el comando `*analisis-y-diseno` para evaluar el impacto de esta historia
2. **🔧 Refinamiento técnico:** El Scrum Master usar `refine-story` para añadir contexto técnico
3. **📊 Estimación y desarrollo:** Continuar con `estimate-story` y desarrollo

¿Hay algo específico que quieras revisar o ajustar en la historia importada?"

## Reglas Obligatorias

1. **CARACTERIZACIÓN DEL PERFIL TÉCNICO:** OBLIGATORIAMENTE capturar en el paso 1/6 si el usuario es perfil A (Funcional Puro) o B (Técnico-Funcional) y registrar internamente para filtrar todas las preguntas de validación posteriores. Para perfil A: SOLO preguntas de negocio y completitud funcional, PROHIBIDAS preguntas técnicas como por ejemplo: (arquitectura, APIs, tecnologías, integraciones técnicas, estructura de datos). Para perfil B: permitir preguntas técnicas cuando sea necesario para validar completitud
2. **VALIDACIÓN OBLIGATORIA:** Siempre analizar completitud antes de importar
3. **CONSISTENCIA:** Verificar que sea consistente con documentación existente
4. **FILTRADO POR PERFIL:** Aplicar filtro por perfil técnico en todas las preguntas de aclaración del paso 3
5. **BLOQUEO DE INFORMACIÓN:** No continuar sin la caracterización del perfil técnico completa
6. **PREGUNTAS ESPECÍFICAS:** Hacer preguntas puntuales para completar gaps
7. **CONFIRMACIÓN:** Siempre solicitar confirmación final del usuario
8. **PRESERVAR INTENCIÓN:** Mantener la intención original pero mejorar claridad
9. **DOCUMENTAR PROCESO:** Registrar que fue una historia importada y validada
10. **ESPERAR RESPUESTAS:** No continuar sin respuestas completas del usuario
11. **APLICAR PATRONES:** Usar nomenclatura y estructura del proyecto existente

## Criterios de Éxito

- **✅ Historia completa:** Toda la información necesaria para desarrollo
- **✅ Consistente:** Alineada con documentación y arquitectura existente
- **✅ Verificada:** Usuario confirmó la versión final
- **✅ Estándar:** Sigue patrones y estructura del proyecto
- **✅ Trazable:** Documentado el proceso de validación e importación
