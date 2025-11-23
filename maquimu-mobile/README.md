# MaquiMu Mobile - Aplicación Android

## 📋 Descripción

Aplicación móvil nativa de Android para el sistema MaquiMu, enfocada en proporcionar un **portal de autoservicio para clientes** que permite consultar maquinaria disponible, solicitar alquileres y gestionar sus contratos.

## 🏗️ Arquitectura

**Patrón MVVM (Model-View-ViewModel)**

```
app/src/main/java/com/maquimu/mobile/
├── ui/                   # Capa de presentación
│   ├── auth/            # Autenticación
│   ├── rental/          # Alquileres
│   └── dashboard/       # Dashboard
├── data/                # Capa de datos
│   ├── repository/      # Repositorios
│   ├── api/             # Cliente HTTP (Retrofit)
│   └── model/           # Modelos de datos
└── di/                  # Inyección de dependencias
```

## 🚀 Tecnologías

- **Lenguaje:** Kotlin
- **IDE:** Android Studio
- **Min SDK:** API 24 (Android 7.0)
- **Target SDK:** API 34 (Android 14)
- **Arquitectura:** MVVM
- **HTTP Client:** Retrofit
- **Async:** Coroutines + Flow
- **DI:** Hilt (recomendado) o Koin
- **UI:** XML Layouts / Jetpack Compose (opcional)

## ⚙️ Configuración

### Prerrequisitos

- Android Studio Hedgehog o superior
- JDK 17
- Android SDK (API 24-34)
- Dispositivo Android o emulador

### Instalación

1. Abrir el proyecto en Android Studio:
   ```
   File > Open > Seleccionar carpeta maquimu-mobile
   ```

2. Sincronizar Gradle:
   ```
   File > Sync Project with Gradle Files
   ```

3. Configurar emulador o conectar dispositivo físico

## 🏃 Ejecutar la Aplicación

### Desde Android Studio

1. Seleccionar dispositivo/emulador
2. Click en el botón "Run" (▶️) o `Shift + F10`

### Desde Línea de Comandos

```bash
# Compilar
./gradlew assembleDebug

# Instalar en dispositivo conectado
./gradlew installDebug

# Ejecutar
adb shell am start -n com.maquimu.mobile/.MainActivity
```

## 📁 Estructura del Proyecto

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/maquimu/mobile/
│   │   │   ├── ui/
│   │   │   │   ├── auth/
│   │   │   │   │   ├── LoginActivity.kt
│   │   │   │   │   ├── LoginViewModel.kt
│   │   │   │   │   └── RegisterActivity.kt
│   │   │   │   ├── rental/
│   │   │   │   │   ├── RentalListActivity.kt
│   │   │   │   │   ├── RentalDetailActivity.kt
│   │   │   │   │   └── RentalViewModel.kt
│   │   │   │   └── dashboard/
│   │   │   │       ├── DashboardActivity.kt
│   │   │   │       └── DashboardViewModel.kt
│   │   │   ├── data/
│   │   │   │   ├── repository/
│   │   │   │   │   ├── AuthRepository.kt
│   │   │   │   │   └── RentalRepository.kt
│   │   │   │   ├── api/
│   │   │   │   │   ├── ApiService.kt
│   │   │   │   │   └── RetrofitClient.kt
│   │   │   │   └── model/
│   │   │   │       ├── Maquinaria.kt
│   │   │   │       ├── Alquiler.kt
│   │   │   │       └── Cliente.kt
│   │   │   └── di/
│   │   │       └── AppModule.kt
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   ├── values/
│   │   │   └── drawable/
│   │   └── AndroidManifest.xml
│   └── test/
└── build.gradle.kts
```

## 🔐 Seguridad

### Autenticación
- JWT almacenado en SharedPreferences (encriptado)
- Interceptor Retrofit para inyectar token
- Manejo de refresh tokens

### Permisos
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## 📡 Comunicación con Backend

### Configuración de Retrofit

```kotlin
// RetrofitClient.kt
object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/api/" // Emulador
    // private const val BASE_URL = "http://192.168.1.X:8080/api/" // Dispositivo físico
    
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
```

### Endpoints Principales

- `POST /auth/login` - Iniciar sesión
- `GET /maquinaria` - Listar maquinaria disponible
- `POST /alquileres` - Solicitar alquiler
- `GET /alquileres/cliente/{id}` - Mis alquileres

## 🎨 UI/UX

### Material Design
- Uso de Material Components
- Temas y colores corporativos
- Navegación con Bottom Navigation o Navigation Drawer

### Layouts Responsivos
- Soporte para diferentes tamaños de pantalla
- Orientación portrait y landscape

## 🧪 Testing

### Tests Unitarios

```bash
# Ejecutar tests unitarios
./gradlew test

# Con cobertura
./gradlew testDebugUnitTest jacocoTestReport
```

### Tests de Instrumentación

```bash
# Ejecutar tests en dispositivo/emulador
./gradlew connectedAndroidTest
```

## 📦 Dependencias Principales

```kotlin
dependencies {
    // Android Core
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    
    // Material Design
    implementation("com.google.android.material:material:1.11.0")
    
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    
    // ViewModel & LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    
    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
```

## 🔧 Convenciones de Código

- **Activities:** `PascalCase` + `Activity` (ej. `LoginActivity`)
- **ViewModels:** `PascalCase` + `ViewModel` (ej. `LoginViewModel`)
- **Layouts:** `snake_case` con prefijo (ej. `activity_login.xml`)
- **IDs:** `camelCase` (ej. `btnLogin`, `tvUsername`)

Ver [coding-standards.md](../docs/architecture/coding-standards.md) para más detalles.

## 🚧 Estado Actual

**✅ PROYECTO INICIALIZADO**

El proyecto Android ya está creado y configurado. Próximos pasos:

- [ ] Configurar Retrofit y API client
- [ ] Implementar autenticación con JWT
- [ ] Crear ViewModels y Repositories
- [ ] Desarrollar UI según especificaciones
- [ ] Implementar navegación
- [ ] Agregar tests

## 📝 Build Variants

```kotlin
buildTypes {
    debug {
        applicationIdSuffix = ".debug"
        isDebuggable = true
    }
    release {
        isMinifyEnabled = true
        proguardFiles(...)
    }
}
```

## 🐛 Debugging

### Logs

```kotlin
import android.util.Log

Log.d("TAG", "Debug message")
Log.e("TAG", "Error message", exception)
```

### Logcat Filters
- `package:com.maquimu.mobile` - Solo logs de la app
- `level:error` - Solo errores

## 📞 Soporte

Para dudas o problemas, consultar:
- [Documentación del proyecto](../docs/)
- [API Backend](../maquimu-backend/README.md)
- [Historias de usuario](../docs/stories/)

---

**Última actualización:** 2025-11-22
