# Pez-De-Pecera

A libGDX game project targeting Android and Desktop.

## 📋 Project Overview

- **Purpose**: Interactive game developed with libGDX.
- **Target Platform**: Android (API 23 to 37) and Desktop (LWJGL3).
- **Current Version**: 1.2.0 (Code 3).
- **Language**: Java 17.
- **Architecture**: libGDX Multi-module project.

## 🏗️ Architecture & Technologies

- **Android SDK**: Target SDK 37, Minimum SDK 23.
- **Languages**: Java 17.
- **Build System**: Gradle 8.x.
- **Framework**: libGDX.
- **Modules**:
    - `core`: Shared game logic.
    - `android`: Android-specific implementation and assets.
    - `lwjgl3`: Desktop implementation.

## 🚀 Building and Running

### Prerequisites
- Java 17 JDK.
- Android SDK (can be configured using `setup-sdk.sh`).

### Setup
Para configurar el entorno de desarrollo y las herramientas de compilación de Android, ejecuta:
```bash
./setup-sdk.sh
```
**Nota Crucial para Agentes**: Este script es obligatorio antes de compilar. 
1.  Descarga las `cmdline-tools` si no existen.
2.  Instala automáticamente las `build-tools` y `platforms` requeridas (API 37).
3.  Crea o sobreescribe `local.properties` con la ruta correcta (`/tmp/android-sdk` por defecto).
4.  **No omitir**: Sin este paso, `./gradlew` fallará al no encontrar el SDK de Android.

### Build Commands
- **Assemble Android Debug APK**:
  ```bash
  ./gradlew android:assembleDebug
  ```
- **Run Desktop Version**:
  ```bash
  ./gradlew lwjgl3:run
  ```

### GitHub Releases (Prerelease)
To create a new pre-release on GitHub:
```bash
gh release create v0.1.0-beta android/build/outputs/apk/debug/android-debug.apk --title "Versión Alfa 0.1.0" --notes "Primera compilación de prueba del juego." --prerelease
```
*Note: Always increment the version tag and title before running.*

## 📂 Project Structure

- `core/src/main/java/`: Main game logic.
- `android/`: Android-specific code and resources.
- `android/assets/`: Shared game assets (linked from root).
- `lwjgl3/`: Desktop launcher.
- `setup-sdk.sh`: Script to automate Android development environment setup.

## 🔧 Development Conventions & AGENT Instructions

Este proyecto utiliza guías estrictas para agentes de IA definidas en `AGENT_INSTRUCTIONS.md`.

### Flujo Obligatorio
1.  **Contexto**: Siempre leer `GEMINI.md`, `AGENT_INSTRUCTIONS.md` y `ACTIVITY_LOG.md` al inicio de la sesión.
2.  **Registro**: Cada acción realizada debe ser anotada detalladamente en `ACTIVITY_LOG.md`.
3.  **Validation**: Siempre verificar cambios con `./gradlew android:assembleDebug`.
4.  **Git Protocol**: Usar autor `Danielk10 <danielpdiamon@gmail.com>` y prefijos en los commits.

## 📝 Key Documentation Files

- `README.md`: Public documentation.
- `ACTIVITY_LOG.md`: Mandatory log of all agent activities.
- `guia_uso_sdk.md`: Reference guide for Android SDK tools.
- `LICENSE`: Apache License 2.0 details.
- `AGENT_INSTRUCTIONS.md`: Mandatory workflow guidelines for AI agents.
