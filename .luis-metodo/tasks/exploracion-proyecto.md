<!-- Powered by Método luis -->

# Exploración de Proyecto - Búsqueda Inteligente de Información

## Purpose

Ayudar al usuario a explorar y entender cualquier aspecto del proyecto mediante búsqueda inteligente en documentación, código y configuración. Proporcionar respuestas contextualizadas y completas basadas en la información disponible del proyecto, facilitando la comprensión de arquitectura, funcionalidades, procesos y estructura del sistema.

## When to Use This Task

**Use this task when:**

- Se necesita entender un aspecto específico del proyecto
- Se busca información sobre arquitectura, componentes o funcionalidades
- Se requiere análisis de dependencias entre módulos
- Se quiere mapear flujos de negocio o procesos técnicos
- Se necesita encontrar documentación relacionada con un tema
- Se busca código específico relacionado con una funcionalidad

**Prerequisites:**

- Configuración del proyecto (`core-config.yaml`)
- Documentación del proyecto disponible
- Acceso a la estructura de archivos del proyecto

## Task Execution Instructions

### 0. CAPTURA DE INTENCIÓN DE EXPLORACIÓN

**🎯 OBJETIVO: Entender qué quiere explorar el usuario**

#### 0.1 Solicitar Tema de Exploración

**Mensaje:**
"¡Hola! Soy tu asistente. Te ayudo a encontrar y entender cualquier aspecto del proyecto de manera rápida y completa.

**¿Qué quieres explorar hoy?**

Puedes preguntarme sobre:

🏗️ **Arquitectura y Componentes:**

- "¿Cómo está estructurado el sistema?"
- "¿Qué componentes maneja el módulo X?"
- "¿Cuáles son las dependencias entre módulos?"

📋 **Funcionalidades y Procesos:**

- "¿Cómo funciona el proceso de login?"
- "¿Qué hace el sistema de pagos?"
- "¿Cuáles son los flujos de usuario para X?"

💻 **Código y Implementación:**

- "¿Dónde está implementada la funcionalidad Y?"
- "¿Qué archivos están relacionados con Z?"
- "¿Cómo se conecta el frontend con el backend?"

📖 **Documentación y Configuración:**

- "¿Qué documentación existe sobre X?"
- "¿Cómo está configurado el proyecto?"
- "¿Dónde están definidas las reglas de negocio?"

**Escribe tu pregunta o tema de interés:**"

#### 0.2 Clasificar Tipo de Consulta

**ANÁLISIS AUTOMÁTICO del tipo de consulta:**

- **🏗️ Arquitectura:** Estructura, componentes, módulos, diagramas
- **📋 Funcional:** Procesos, flujos, reglas de negocio, historias
- **💻 Técnico:** Código, implementación, APIs, integraciones
- **📖 Documentación:** Guías, manuales, configuración, estándares
- **🔍 General:** Exploración amplia o múltiples aspectos

#### 0.3 Refinar Pregunta si es Necesario

**Si la pregunta es muy vaga o ambigua:**

"Tu pregunta es muy interesante. Para darte la mejor respuesta, necesito un poco más de contexto:

**Tu pregunta:** [pregunta original]

**¿Podrías especificar más sobre:**

- ¿Qué aspecto específico te interesa más? (técnico, funcional, arquitectónico)
- ¿Es para entender, modificar, o documentar algo?
- ¿Hay algún módulo o área en particular?

Por ejemplo:

- En lugar de "¿Cómo funciona el sistema?" → "¿Cómo funciona el proceso de autenticación?"
- En lugar de "¿Dónde está el código?" → "¿Dónde está el código del carrito de compras?"

**Pregunta refinada:**"

### 1. CARGA OBLIGATORIA DE CONFIGURACIÓN Y CONTEXTO

**🛑 PASO OBLIGATORIO - Ejecutar antes de cualquier búsqueda**

#### 1.1 Cargar Configuración del Proyecto

**OBLIGATORIO:**

- Cargar `.luis-metodo/core-config.yaml`
- Extraer todas las configuraciones de ubicaciones:
  - `architectureShardedLocation` (documentación arquitectónica)
  - `devStoryLocation` (historias de usuario)
  - `qa.qaLocation` (documentación de QA)
  - Otras ubicaciones configuradas
- Si no existe core-config.yaml, HALT: "Configuración no encontrada. Ejecuta la instalación de BMad primero."

### 2. BÚSQUEDA INTELIGENTE BASADA EN TIPO DE CONSULTA

**🔍 ESTRATEGIA DE BÚSQUEDA ADAPTATIVA según el tipo identificado**

#### 2.1 Para Consultas de ARQUITECTURA (🏗️)

**Fuentes prioritarias:**

1. **Documentación arquitectónica** (`{architectureShardedLocation}/**`)
2. **Diagramas y diseños** (archivos con 'architecture', 'design', 'diagram')
3. **Configuración de proyecto** (package.json, core-config.yaml)
4. **Estructura de carpetas** (análisis de organización)

**Búsquedas específicas:**

- Términos: arquitectura, componentes, módulos, servicios, capas, patrones
- Archivos: `architecture-*.md`, `index.md`, `*-design.md`
- Configuraciones: dependencias, estructura de proyecto

#### 2.2 Para Consultas FUNCIONALES (📋)

**Fuentes prioritarias:**

1. **Historias de usuario** (`{devStoryLocation}/**`)
2. **Flujos de negocio** (`flujo-*.md`, `*-flow.md`)
3. **Documentación de procesos** (docs/processes/, workflows/)
4. **Reglas de negocio** (business-rules, validations)
5. **Código fuente** (src/, components/, lib/)
6. **APIs y servicios** (api/, services/, controllers/)

**Búsquedas específicas:**

- Términos relacionados con la funcionalidad consultada
- Actores y roles involucrados
- Criterios de aceptación y validaciones
- Flujos de trabajo y procesos

#### 2.3 Para Consultas TÉCNICAS (💻)

**Fuentes prioritarias:**

1. **Código fuente** (src/, components/, lib/)
2. **APIs y servicios** (api/, services/, controllers/)
3. **Configuración técnica** (webpack, babel, tsconfig, etc.)
4. **Documentación técnica** (tech-docs/, api-docs/)

**Búsquedas específicas:**

- Nombres de archivos relacionados
- Funciones, clases, y métodos
- Integraciones y conexiones
- Configuraciones técnicas

#### 2.4 Para Consultas de DOCUMENTACIÓN (📖)

**Fuentes prioritarias:**

1. **Documentación general** (docs/, README.md)
2. **Guías y manuales** (guides/, manuals/, how-to/)
3. **Configuración y setup** (installation, configuration)
4. **Estándares y convenciones** (coding-standards, conventions)

### 3. EJECUCIÓN DE BÚSQUEDA Y ANÁLISIS

**🎯 BÚSQUEDA SISTEMÁTICA en fuentes identificadas**

#### 3.1 Búsqueda Primaria

**Para cada fuente identificada:**

1. **Buscar archivos relevantes** usando nombres y patrones
2. **Buscar contenido específico** usando términos de la consulta
3. **Identificar conexiones** entre documentos
4. **Extraer información clave** de cada fuente encontrada

#### 3.2 Análisis de Dependencias

**Identificar relaciones:**

- Referencias entre documentos
- Dependencias entre componentes
- Flujos que conectan múltiples módulos
- Configuraciones que afectan múltiples áreas

#### 3.3 Detección de Gaps

**Identificar información faltante:**

- Documentación mencionada pero no encontrada
- Código sin documentación asociada
- Procesos parcialmente documentados
- Referencias rotas o desactualizadas

### 4. RESPUESTA ESTRUCTURADA Y COMPLETA

**📝 PRESENTACIÓN DE RESULTADOS de manera organizada**

#### 4.1 Respuesta Principal

**Estructura de respuesta:**

"**🔍 EXPLORACIÓN COMPLETADA: [Tema Consultado]**

**📋 RESUMEN EJECUTIVO:**
[Respuesta directa y clara a la pregunta original]

**🏗️ INFORMACIÓN ENCONTRADA:**

**1. [Categoría Principal]**

- **📁 Fuente:** `[archivo o ubicación]`
- **📝 Contenido:** [información relevante extraída]
- **🔗 Ubicación:** [ruta específica/líneas si aplica]

**2. [Categoría Secundaria]**

- **📁 Fuente:** `[archivo o ubicación]`
- **📝 Contenido:** [información relevante extraída]
- **🔗 Ubicación:** [ruta específica/líneas si aplica]

[Repetir para todas las categorías encontradas]

**🔗 CONEXIONES IDENTIFICADAS:**

- [Cómo se relaciona con otros componentes/procesos]
- [Dependencias importantes]
- [Flujos de interacción]

#### 4.2 Detección de Gaps y Sugerencias

**Si hay información faltante:**

"**⚠️ GAPS DE INFORMACIÓN DETECTADOS:**

**📋 Información Mencionada pero No Encontrada:**

- [Item A]: Mencionado en [ubicación] pero sin documentación detallada
- [Item B]: Referenciado en código pero sin documentación

**💡 SUGERENCIAS:**

- Revisar [ubicación específica] para más detalles sobre [aspecto]
- Consultar con el equipo sobre [tema no documentado]
- Considerar crear documentación para [área faltante]"

#### 4.3 Seguimiento Interactivo

**Preguntas de seguimiento:**

"**🤔 ¿HAY ALGO MÁS QUE QUIERAS EXPLORAR?**

**Puedes preguntarme sobre:**

- Detalles específicos de [componentes mencionados]
- Implementación técnica de [procesos identificados]
- Otras funcionalidades relacionadas con [tema principal]
- Cualquier aspecto que no haya quedado claro

**¿Qué más te gustaría saber sobre el proyecto?**"

## Reglas Obligatorias

1. **CARGA COMPLETA:** Siempre cargar configuración antes de buscar
2. **BÚSQUEDA EXHAUSTIVA:** Revisar todas las fuentes relevantes identificadas
3. **RESPUESTA ESTRUCTURADA:** Presentar información de manera organizada
4. **REFERENCIAS ESPECÍFICAS:** Incluir ubicaciones exactas de la información
5. **DETECTAR GAPS:** Identificar información faltante o inconsistente
6. **SEGUIMIENTO:** Ofrecer opciones de exploración adicional
7. **CONTEXTUALIZAR:** Explicar cómo se relaciona con el ecosistema general
8. **ADAPTARSE:** Ajustar profundidad según el tipo de consulta

## Criterios de Éxito

- **✅ Pregunta Respondida:** La consulta original está completamente respondida
- **✅ Fuentes Identificadas:** Se encontraron y revisaron las fuentes relevantes
- **✅ Información Completa:** Se extrajo toda la información disponible relacionada
- **✅ Referencias Precisas:** Se proporcionaron ubicaciones específicas
- **✅ Contexto Proporcionado:** Se explicaron las relaciones y dependencias
- **✅ Gaps Identificados:** Se detectó información faltante si la hay
- **✅ Seguimiento Ofrecido:** Se facilitaron opciones para exploración adicional
- **✅ Respuesta Estructurada:** La información se presentó de manera clara y organizada
