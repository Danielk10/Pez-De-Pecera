# Pez-De-Pecera

A libGDX game project targeting Android and Desktop.

## 📋 Project Overview

- **Purpose**: Interactive game developed with libGDX.
- **Target Platform**: Android (API 23 to 37) and Desktop (LWJGL3).
- **Current Version**: 1.0.0 (Code 1).
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
Run the included script to configure the Android SDK environment:
```bash
./setup-sdk.sh
```

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

## 🔧 Development Conventions

- **Coding Style**: Standard Java coding conventions.
- **UI**: libGDX Scene2D/UI components.
- **Testing**: Tests located in respective module test folders.

## 📝 Key Documentation Files

- `README.md`: Public documentation.
- `guia_uso_sdk.md`: Reference guide for Android SDK tools.
- `LICENSE`: Apache License 2.0 details.
- `AGENT_INSTRUCTIONS.md`: Mandatory workflow guidelines for AI agents.
