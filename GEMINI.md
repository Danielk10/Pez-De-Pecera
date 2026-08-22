# Pez-De-Pecera 🐟

Juego interactivo desarrollado con **libGDX**, compatible con Android (API 23 a 37) y Escritorio (LWJGL3).

---

## 📋 Información General del Proyecto

- **Versión Actual**: 1.0.0 (Code 1).
- **Lenguaje**: Java 17.
- **Framework**: libGDX (1.14.2).
- **Herramientas de Compilación**: Gradle 9.6.0 / AGP (Android Gradle Plugin) 9.2.1.
- **SDK / NDK Android**:
  - `compileSdk` / `targetSdkVersion`: 37 (Android 15+)
  - `minSdkVersion`: 23 (Android 6.0+)
  - `buildToolsVersion`: 37.0.0
  - `cmake`: 4.1.2
  - `ndk`: 30.0.14904198 (NDK 30)

---

## 🏗️ Aislamiento del Entorno de Compilación en `/tmp`

Para evitar el consumo de espacio persistente en `/home`, todas las dependencias, SDK, NDK, cachés y artefactos se redirigen al directorio temporal `/tmp`:

- **SDK & NDK Root**: `/tmp/android-sdk`
- **Configuración de Android (`ANDROID_USER_HOME`)**: `/tmp/.android`
- **Preferencias de Android (`ANDROID_PREFS_ROOT`)**: `/tmp`
- **Cache Global de Gradle (`GRADLE_USER_HOME`)**: `/tmp/.gradle`
- **Cache de Proyecto de Gradle**: `/tmp/pez-de-pecera/.gradle`
- **Directorio de Compilación (Build Outputs)**: `/tmp/pez-de-pecera`

---

## 🚀 Instalación y Compilación

### 1. Inicialización del SDK y NDK
Antes de compilar, es obligatorio ejecutar el script de aprovisionamiento automatizado:
```bash
bash setup-sdk.sh
```
*Este script descarga `cmdline-tools`, instala automáticamente las `platform-tools`, `platforms;android-23`, `platforms;android-37.0`, `build-tools;37.0.0`, `cmake;4.1.2` y `ndk;30.0.14904198` en `/tmp/android-sdk`, y genera `local.properties`.*

### 2. Comandos de Compilación

```bash
# Compilar APK Debug
./gradlew :android:assembleDebug

# Compilar APK Release (firmado si existe keystore.properties o variables SIGNING_*)
./gradlew :android:assembleRelease

# Compilar AAB (App Bundle) Release para Google Play Store
./gradlew :android:bundleRelease

# Ejecutar versión de escritorio
./gradlew lwjgl3:run
```

---

## 📍 Ubicación de Artefactos de Salida (Outputs)

Los archivos compilados se generan directamente en `/tmp/pez-de-pecera/outputs/`:

- **APK Debug**: `/tmp/pez-de-pecera/outputs/apk/debug/android-debug.apk`
- **APK Release**: `/tmp/pez-de-pecera/outputs/apk/release/android-release.apk`
- **AAB Release (Google Play)**: `/tmp/pez-de-pecera/outputs/bundle/release/android-release.aab`

### Limpieza de Artefactos Temporales
```bash
rm -rf /tmp/pez-de-pecera/outputs/
```

---

## 📂 Estructura de Módulos

- `core/`: Lógica central del juego compartida entre plataformas.
- `android/`: Implementación nativa para Android y recursos.
- `assets/`: Texturas, sonidos, fuentes y mapas compartidos.
- `lwjgl3/`: Lanzador para escritorio (Desktop).
- `setup-sdk.sh`: Script de configuración y descarga del entorno Android/NDK.
- `upload_play_store.py`: Script para subida automatizada de AAB a Google Play Store.

---

## 🔧 Protocolo de Desarrollo y Git

- **Validación Obligatoria**: Verificar siempre las modificaciones con `./gradlew :android:assembleDebug` antes de realizar un commit.
- **Autoría de Commits**:
  - Nombre: `Danielk10`
  - Email: `danielpdiamon@gmail.com`
- **Formato de Mensajes**: Usar prefijos estándar (`feat:`, `fix:`, `docs:`, `chore:`).
- **Publicación en GitHub**:
  ```bash
  gh release create v1.0.0-beta /tmp/pez-de-pecera/outputs/apk/debug/android-debug.apk --title "Versión Alfa 1.0.0" --notes "Compilación de prueba del juego." --prerelease
  ```

