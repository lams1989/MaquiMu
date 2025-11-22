<!-- Powered by Método luis -->

# Análisis y Diseño Arquitectónico - Historia de Usuario

## Purpose

Analizar una historia de usuario desde la perspectiva arquitectónica y proponer un diseño de implementación validado por el usuario humano. Esta tarea se enfoca exclusivamente en decisiones arquitectónicas y de diseño, dejando la descomposición técnica detallada para el refinamiento y estimación posterior por medio del agente SM.

## When to Use This Task

**Use this task when:**

- Existe una historia creada por el PO en estado "Borrador (PO)"
- Se necesita determinar el impacto arquitectónico de la funcionalidad
- Se requiere validación de decisiones de diseño antes del refinamiento técnico
- La historia puede afectar componentes existentes o requerir nuevos componentes
- Se necesita asegurar coherencia con la arquitectura existente

**Prerequisites:**

- Historia de usuario existente creada por PO
- Documentación de arquitectura actualizada
- Acceso al usuario humano para validación
- Core-config.yaml configurado correctamente

## Task Execution Instructions

### 0. CONFIGURACIÓN Y VALIDACIÓN INICIAL

#### 0.1 Cargar Configuración

- Cargar `.luis-metodo/core-config.yaml`
- Extraer configuraciones: `devStoryLocation`, `architecture.*`, `prd.*`
- Si no existe core-config.yaml, HALT: "core-config.yaml no encontrado"

#### 0.2 Solicitar Historia a Analizar

**Preguntar al usuario:**
"¿Qué historia de usuario requiere análisis arquitectónico?

- Proporciona el número de la historia (ejemplo: 5 para analizar `5.{nombre}.story.md`)
- O especifica la ruta completa del archivo de historia"

#### 0.3 Validar Historia Existente

- Verificar que existe el archivo de historia en `{devStoryLocation}`
- Cargar contenido completo de la historia
- Verificar estado: debe ser "Borrador (PO)" o sin análisis arquitectónico previo
- Si no existe o no es apropiada para análisis, HALT con mensaje apropiado

#### 0.4 Consulta Obligatoria de Enfoque Arquitectónico

**🚨 OBLIGATORIO: Consultar consideraciones del usuario antes de iniciar análisis**

**Preguntar al usuario:**
"**🏗️ CONSULTA DE ENFOQUE ARQUITECTÓNICO**

Antes de iniciar el análisis arquitectónico de la Historia #{número}: {título de la historia}

**¿Tienes alguna consideración, restricción o planteamiento inicial que deba tener en cuenta?**

**Opciones de trabajo:**

**A) ENFOQUE DIRIGIDO** - Tienes consideraciones específicas:

- ¿Hay patrones arquitectónicos específicos que prefieres?
- ¿Existen restricciones técnicas o de negocio particulares?
- ¿Hay componentes específicos que debo considerar o evitar?
- ¿Tienes algún planteamiento inicial de diseño?
- ¿Existen componentes, flujos, o código que deba reutilizar?

**B) ENFOQUE EXPLORATORIO** - Quieres que el agente analice primero:

- El agente realizará el análisis completo de arquitectura disponible
- Presentará una propuesta fundamentada basada en la documentación existente
- Luego recibirás la propuesta para validación y ajustes

**Por favor responde:**

1. **Opción preferida:** A (Dirigido) o B (Exploratorio)
2. **Si eliges A:** Detalla tus consideraciones específicas
3. **Si eliges B:** Confirma que proceda con análisis exploratorio

**Cualquier consideración adicional que deba tener presente:**"

**⚠️ NO CONTINUAR hasta recibir respuesta del usuario**

**Documentar respuesta:**

- Si es enfoque dirigido: registrar todas las consideraciones específicas
- Si es enfoque exploratorio: confirmar libertad total de análisis
- Usar esta información como base para todo el análisis posterior

### 1. ANÁLISIS ARQUITECTÓNICO PROFUNDO

#### 1.1 Revisión Integral de Documentación Arquitectónica

**⚠️ Considerar siempre las especificaciones del usuario del paso 0.4**

**Análisis integral del ecosistema arquitectónico:**

**Enfoque según respuesta del usuario:**

- **Si DIRIGIDO:** Priorizar análisis en las áreas específicas mencionadas por el usuario
- **Si EXPLORATORIO:** Realizar análisis comprehensivo sin sesgos predefinidos
- **En ambos casos:** Documentar cómo las consideraciones del usuario influencian el análisis

**GPS Arquitectónico (`{architectureShardedLocation}/index.md`):**

- Revisar overview del sistema y identificar módulos relevantes
- Mapear la funcionalidad de la historia contra componentes existentes
- Identificar patrones arquitectónicos aplicables
- Evaluar impacto en la arquitectura general

**Documentación de Componentes (`{architectureShardedLocation}/architecture-*.md`):**

- Analizar componentes que podrían verse afectados por la funcionalidad
- Revisar APIs, interfaces y responsabilidades de componentes existentes
- Identificar capacidades y limitaciones actuales
- Evaluar necesidad de extensiones o modificaciones
  para este paso usa varios comodines de busqueda para encontrar los flujos y la documentación de cada componente

**Flujos de Negocio (`{architectureShardedLocation}/flujo-*.md`):**

- Identificar flujos existentes relacionados con la nueva funcionalidad
- Analizar puntos de integración y extensión en flujos actuales
- Evaluar impacto en flujos existentes
- Determinar si se requieren nuevos flujos o modificaciones
  para este paso usa varios comodines de busqueda para encontrar los flujos y la documentación de cada componente

**Historias Arquitectónicas Previas (`{devStoryLocation}/*.story.md`):**

- **DEBE revisar** historias con análisis arquitectónico previo para:
  - **Decisiones arquitectónicas** tomadas en funcionalidades similares
  - **Patrones de implementación** aplicados exitosamente
  - **Componentes identificados** para funcionalidades relacionadas
  - **Estrategias de integración** utilizadas anteriormente
  - **Lecciones arquitectónicas** documentadas en historias previas

- **DEBE buscar** historias que hayan involucrado:
  - Los mismos dominios de negocio
  - Componentes arquitectónicos similares
  - Patrones de integración comparables
  - Decisiones de diseño relacionadas

#### 1.2 Análisis de Impacto Arquitectónico

**Evaluar impacto específico de la historia:**

**Componentes Afectados:**

- Identificar qué componentes existentes se verán modificados
- Evaluar la capacidad actual vs. requerimientos de la historia
- Determinar nivel de modificación necesario (menor, mayor, reestructuración)

**Nuevos Componentes:**

- Evaluar si se requieren componentes completamente nuevos
- Justificar la necesidad de nuevos componentes vs. extensión de existentes
- Proponer responsabilidades y ubicación en la arquitectura

**Integraciones:**

- Identificar nuevas integraciones requeridas entre componentes
- Evaluar impacto en interfaces existentes
- Proponer estrategias de integración coherentes con patrones actuales

### 2. PROPUESTA DE DISEÑO ARQUITECTÓNICO

#### 2.1 Estrategia de Implementación

**⚠️ Incorporar consideraciones específicas del usuario (paso 0.4)**

**Definir enfoque arquitectónico:**

**Consideraciones del Usuario:**

- **Si DIRIGIDO:** Aplicar restricciones, patrones o preferencias específicas mencionadas
- **Si EXPLORATORIO:** Proponer la mejor opción basada en análisis técnico puro
- **En ambos casos:** Justificar cómo se incorporan (o por qué se descartan) las consideraciones del usuario

**Patrón Arquitectónico Principal:**

- Seleccionar patrón arquitectónico apropiado basado en documentación existente Y consideraciones del usuario
- Justificar selección contra alternativas consideradas (incluyendo sugerencias del usuario si aplica)
- Asegurar coherencia con patrones ya establecidos en el sistema

**Distribución de Responsabilidades:**

- Definir qué componente maneja cada aspecto de la funcionalidad
- Asegurar adhesión al principio de responsabilidad única
- Mantener cohesión con responsabilidades existentes

**Flujo de Datos:**

- Proponer cómo fluirán los datos a través de los componentes
- Definir transformaciones y validaciones necesarias
- Identificar puntos de persistencia y recuperación

#### 2.2 Especificación de Componentes

**Para cada componente afectado/nuevo:**

**Responsabilidades Específicas:**

- Definir qué funcionalidades específicas manejará
- Establecer contratos con otros componentes

**Modificaciones Requeridas:**

- Detallar cambios específicos en componentes existentes
- Proponer nuevas operaciones o endpoints
- Identificar deprecaciones o refactorizaciones necesarias

**Dependencias:**

- Mapear dependencias con otros componentes
- Identificar nuevas dependencias introducidas
- Evaluar impacto de dependencias en testabilidad y mantenibilidad

### 3. VALIDACIÓN HUMANA OBLIGATORIA

#### 3.1 Preparación para Validación

**Documento de Propuesta Arquitectónica:**

"**ANÁLISIS ARQUITECTÓNICO - Historia #{número}**

**Historia:** {título de la historia}

**CONSIDERACIONES DEL USUARIO INCORPORADAS:**
{resumen de cómo se aplicaron las especificaciones del usuario del paso 0.4}

**COMPONENTES AFECTADOS:**
{lista detallada con justificaciones}

**NUEVOS COMPONENTES PROPUESTOS:**
{especificación completa si aplica}

**PATRÓN ARQUITECTÓNICO SELECCIONADO:**
{patrón + justificación (si aplica)}

**FLUJO DE IMPLEMENTACIÓN:**
{secuencia de pasos arquitectónicos}

**INTEGRACIONES REQUERIDAS:**
{interfaces y contratos propuestos (si aplica)}

**IMPACTO EN ARQUITECTURA EXISTENTE:**
{evaluación de cambios y riesgos (si aplica)}

**PUNTOS DE VALIDACIÓN ESPECÍFICOS:**

1. ¿La distribución de responsabilidades es apropiada?
2. ¿El patrón seleccionado es consistente con la arquitectura?
3. ¿Las integraciones propuestas son eficientes y mantenibles?
4. ¿Hay alternativas arquitectónicas preferibles?
5. ¿El diseño facilita testing y evolución futura?"

#### 3.2 Solicitud de Validación

**BLOQUEO OBLIGATORIO:**

**Mensaje exacto:**
"**🏗️ VALIDACIÓN ARQUITECTÓNICA REQUERIDA**

He completado el análisis arquitectónico para la Historia #{número}: {título}

**PROPUESTA PRESENTADA ARRIBA ↑**

**Necesito tu validación como usuario humano antes de proceder:**

1. **¿Apruebas** la propuesta arquitectónica presentada?
2. **¿Hay ajustes** que debo realizar en el diseño?
3. **¿Existen alternativas** que debo considerar?
4. **¿Falta información** arquitectónica importante?

**Opciones de respuesta:**

- **APROBADO** - Proceder con el diseño tal como está propuesto
- **AJUSTES** - Realizar modificaciones específicas (detalla los cambios)
- **REPLANTEAR** - Reconsiderar el enfoque arquitectónico completo

Por favor, proporciona tu feedback detallado."

**NO CONTINUAR hasta recibir validación del humano**

#### 3.3 Iteración Basada en Feedback

**Si se requieren ajustes:**

- Incorporar feedback específico del usuario humano
- Revisar decisiones según orientaciones recibidas
- Actualizar propuesta arquitectónica con cambios
- Solicitar nueva validación si los cambios son significativos

**Documentar iteraciones:**

- Registrar feedback recibido
- Documentar cambios realizados y justificaciones
- Mantener trazabilidad de decisiones tomadas

**🚨 CRÍTICO: CAPTURAR DECISIÓN FINAL APROBADA**

**Una vez obtenida la aprobación del usuario humano:**

- **INMEDIATAMENTE** documentar la decisión arquitectónica final aprobada
- **CONSERVAR** exactamente las especificaciones validadas por el arquitecto
- **REGISTRAR** cualquier condición o consideración específica mencionada en la aprobación
- **NO MODIFICAR** las decisiones aprobadas sin nueva validación
- **PREPARAR** la sección completa de "Análisis Arquitectónico" con los detalles finales aprobados

### 4. ACTUALIZACIÓN DE HISTORIA CON DECISIONES ARQUITECTÓNICAS

**🚨 OBLIGATORIO: Esta sección es CRÍTICA y NO puede omitirse**

#### 4.1 Añadir Sección de Análisis Arquitectónico

**⚠️ IMPORTANTE: Insertar nueva sección con las decisiones FINALES APROBADAS**

**DEBE incluir únicamente las decisiones que fueron validadas y aprobadas por el humano**

(agrega solo algunas de estas sesiones que apliquen, no tienes que agregar todas, solo las que apliquen y den valor)

```markdown
## Análisis Arquitectónico (Arquitecto)

### Decisiones de Diseño

**Patrón Arquitectónico:** {patrón seleccionado y justificación}

**Componentes Principales:**

- **{Componente A}:** {responsabilidades específicas para esta historia}
- **{Componente B}:** {modificaciones requeridas}
- **{Nuevo Componente X}:** {justificación y responsabilidades} (si aplica)

**Estrategia de Implementación:**

1. **Fase 1:** {primera etapa de implementación}
2. **Fase 2:** {etapas subsecuentes}
3. **Integración:** {estrategia de integración con sistema existente}

### Especificaciones Técnicas

**Interfaces Requeridas:**

- **{Componente → Componente}:** {contrato de interfaz}
- **APIs Nuevas:** {especificación de endpoints si aplica}
- **Modelos de Datos:** {estructuras requeridas}

**Integraciones:**

- **Sistemas Externos:** {integraciones con terceros si aplica}
- **Componentes Internos:** {puntos de integración}
- **Flujos de Datos:** {transformaciones y validaciones}

### Impacto Arquitectónico

**Componentes Modificados:** {lista con nivel de impacto}

**Documentación a Actualizar:** {arquitectura, flujos, componentes que requieren actualización}

**Riesgos Arquitectónicos:** {riesgos identificados y mitigaciones propuestas}

### Validación Arquitectónica

**Validado por:** {nombre del humano}
**Fecha de validación:** {fecha}
**Feedback incorporado:** {resumen de ajustes realizados}

### Referencias Arquitectónicas

**Documentación consultada:** {lista de documentos arquitectónicos revisados}
**Historias relacionadas:** {historias con decisiones arquitectónicas similares}
**Patrones aplicados:** {patrones arquitectónicos utilizados del sistema}
```

#### 4.2 Actualizar Estado de Historia

- Cambiar estado de "Borrador (PO)" a "Analizado (Arquitecto)"
- Agregar fecha de análisis arquitectónico
- Documentar arquitecto responsable del análisis

### 5. CONFIRMACIÓN FINAL Y ENTREGA

**Mensaje exacto obligatorio:**
"**✅ Análisis Arquitectónico Completado**

Historia #{número} analizada arquitectónicamente y actualizada en `{archivo de historia}`

**Resumen del Análisis:**

- **Patrón arquitectónico:** {patrón seleccionado}
- **Componentes afectados:** {número} componentes modificados
- **Nuevos componentes:** {número} componentes nuevos (si aplica)
- **Validación:** ✅ Aprobado por el humano
- **Estado:** Analizado (Arquitecto) - Listo para refinamiento técnico

**Decisiones Arquitectónicas Clave:**

- {decisión 1}
- {decisión 2}
- {decisión 3}

**Próximos pasos:**

1. El Scrum Master debe usar `refine-story` para descomposición técnica basada en este análisis
2. Posterior estimación con `estimate-story`
3. Desarrollo siguiendo las decisiones arquitectónicas documentadas

¿El análisis arquitectónico es coherente con tu visión del sistema?"

## Criterios de Éxito del Análisis Arquitectónico

- **✅ Coherencia con arquitectura existente**: Decisiones alineadas con patrones actuales
- **✅ Validación humana obtenida**: Usuario ha aprobado la propuesta
- **✅ Documentación completa**: Todas las decisiones justificadas y documentadas
- **✅ Componentes claramente especificados**: Responsabilidades y modificaciones definidas
- **✅ Riesgos identificados**: Problemas potenciales con mitigaciones propuestas
- **✅ Base sólida para refinamiento**: SM tiene información clara para descomposición técnica
- **✅ Trazabilidad de decisiones**: Feedback y iteraciones documentadas
- **✅ Un solo archivo**: Toda la información centralizada en la historia original

## Reglas de Comportamiento OBLIGATORIAS

1. **CONSULTA OBLIGATORIA DE ENFOQUE**: SIEMPRE consultar al usuario sobre consideraciones arquitectónicas antes de iniciar el análisis (paso 0.4)
2. **VALIDACIÓN HUMANA OBLIGATORIA**: NUNCA proceder sin aprobación del usuario humano
3. **PRESERVAR INFORMACIÓN DEL PO**: Mantener intacto todo el trabajo original del PO
4. **UN ARCHIVO**: Trabajar siempre sobre el archivo de historia existente
5. **ENFOQUE ARQUITECTÓNICO PURO**: No descender a detalles de implementación técnica específica
6. **DOCUMENTACIÓN OBLIGATORIA DE DECISIONES**: Justificar todas las decisiones arquitectónicas tomadas
7. **🚨 DOCUMENTAR DECISIONES FINALES APROBADAS**: SIEMPRE actualizar el archivo de historia con las decisiones arquitectónicas finales que fueron validadas y aprobadas por el usuario humano. Este paso es CRÍTICO y NO puede omitirse
8. **ANÁLISIS DE HISTORIAS PREVIAS**: OBLIGATORIAMENTE revisar decisiones arquitectónicas en historias similares
9. **COHERENCIA CON DOCUMENTACIÓN**: Asegurar alineación con GPS arquitectónico y componentes documentados
10. **ITERACIÓN OBLIGATORIA**: Incorporar feedback del usuario humano hasta obtener aprobación
11. **ESPECIFICACIÓN DE INTERFACES**: Definir claramente contratos entre componentes
12. **PREPARACIÓN PARA REFINAMIENTO**: Dejar base sólida para que SM pueda descomponer en tareas técnicas
13. **TRAZABILIDAD COMPLETA**: Documentar referencias a arquitectura y historias consultadas
14. **INCORPORAR CONSIDERACIONES DEL USUARIO**: Aplicar y justificar cómo se incorporan las especificaciones del usuario proporcionadas en el paso 0.4

## Notas Importantes

- Esta tarea se enfoca **exclusivamente en decisiones arquitectónicas** de alto nivel
- **VALIDACIÓN HUMANA es CRÍTICA** - nunca omitir este paso
- **🚨 DOCUMENTACIÓN DE DECISIONES FINALES es OBLIGATORIA** - Las decisiones arquitectónicas aprobadas DEBEN quedar registradas en el archivo de la historia de usuario. Este es un paso CRÍTICO que NO puede omitirse bajo ninguna circunstancia
- **Prepara el terreno** para que el SM pueda hacer refinamiento técnico efectivo
- **Mantiene coherencia** con la arquitectura existente y patrones establecidos
- **Documenta decisiones** para facilitar mantenimiento y evolución futura
- **Análisis de historias previas** es fundamental para mantener consistencia arquitectónica
- La **calidad del análisis** impacta directamente en la eficiencia del desarrollo posterior
- **Separación clara de responsabilidades**: Arquitecto decide diseño, SM descompone tareas
- **Base sólida para estimación**: Decisiones arquitectónicas facilitan estimación precisa
- **Evita retrabajos**: Validación temprana previene problemas arquitectónicos durante desarrollo
