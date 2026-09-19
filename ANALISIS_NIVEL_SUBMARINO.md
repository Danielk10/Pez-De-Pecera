# 🌊 Análisis de Física y Diseño de Niveles Submarinos
**Referencia:** *Little Nemo: The Dream Master* (NES, Capcom 1990) — *Dream 4: Night Sea*  
**Proyecto:** *Pez-De-Pecera* (libGDX / Android / Desktop)

---

## 📌 1. Introducción y Propósito

El objetivo de este documento es registrar el análisis técnico y artístico del nivel submarino de referencia (*Dream 4: Night Sea* de *Little Nemo*, URL: `https://youtu.be/sEpg_9hd_ak`), contrastándolo con la jugabilidad y arquitectura de **Pez-De-Pecera** para definir la dirección de físicas, mecánicas de control y texturas modernas.

---

## 🐟 2. Desglose de la Física Submarina (Little Nemo)

En *Little Nemo*, al alimentar al pez con caramelos y tomar su control, el juego altera drásticamente el modelo de plataformas tradicional introduciendo un entorno de fluido viscoso con las siguientes características:

### A. Resistencia de Fluidos e Inercia (*Water Drag & Glide*)
- **Sin parada instantánea**: Al cesar el input direccional, el personaje no se detiene en seco ni cae abruptamente por gravedad; la fricción viscosa del agua desacelera el cuerpo suavemente mediante amortiguamiento exponencial (*damping*).
- **Sensación de peso y sustentación**: Se percibe una masa acuática que amortigua los giros y cambios bruscos de vector.

### B. Cadencia Rítmica de Aleteo (*Stroke & Propulsion*)
- En lugar de una traslación a velocidad constante y robótica, la propulsión ocurre en ráfagas: un aletazo inicial con aceleración rápida, seguido de un deslizamiento o planeo hidrodinámico.
- La frecuencia de las aletas responde a la intensidad o repetición de la entrada.

### C. Flotabilidad Neutra con Micro-Oscilación (*Buoyancy*)
- En reposo (*idle*), el pez permanece suspendido sin descender como una piedra ni elevarse sin control.
- Posee una micro-oscilación senoidal vertical continua ("respiración acuática") que mantiene vivo al personaje incluso cuando no hay interacción del jugador.

### D. Corrientes Marinas y Columnas de Turbulencia
- El agua no es un medio estático: existen corrientes marinas direccionales que arrastran de forma sutil o intensa al pez, requiriendo esfuerzo para nadar contra corriente.
- Géiseres y respiraderos hidrotermales expulsan columnas de burbujas verticales que aportan empuje adicional y señalización visual.

### E. Inclinación Hidrodinámica (*Pitch & Direction*)
- Al ascender o descender, el cuerpo del pez se inclina ligeramente en el ángulo del vector de movimiento (rotación angular suave en el eje Z), volviendo a la posición horizontal de reposo al nivelarse el desplazamiento.

---

## 🎨 3. Diseño de Escenario, Estética y Texturas

### A. Coherencia del Medio Acuático (Superficie vs. Fondo)
- **Sin estructuras artificiales en el cielo**: No existen techos de ladrillos flotantes sobre el agua. El límite superior es la superficie libre del mar, con gradiente lumínico y rayos solares/lunares descendentes.
- **Transición vertical de profundidad**: A mayor profundidad, menor luminosidad y mayor densidad en la paleta de azules y cianes (alineado exactamente con `fondo4.png`).

### B. Topografía Submarina Orgánica
- El lecho marino está compuesto por formaciones erosionadas:
  - Rocas sedimentarias y cantos rodados redondeados por el oleaje.
  - Arcos de piedra natural y pilares sumergidos que delimitan cavernas y zonas de paso estrecho.
  - Arenales con dunas onduladas y vegetación bentónica (algas, anémonas y corales ramificados).

### C. Partículas y Efectos de Ambiente
- Estela de microburbujas emanando de las aletas o branquias durante desplazamientos rápidos.
- Burbujas ambientales ascendentes dispersas a diferentes velocidades y escalas para enriquecer la profundidad de campo.

---

## 🛠️ 4. Diagnóstico y Mejoras Aplicadas en *Pez-De-Pecera*

| Aspecto | Estado Anterior (Problemático) | Estado Rediseñado (Actual) |
| :--- | :--- | :--- |
| **Física de Movimiento** | Cuerpo `DINAMICO` de Box2D con fuerzas y D-Pad/Touchpad virtual. Las fuerzas de Box2D sobreescribían la posición generando vibraciones incontroladas y pérdida de control. | Cuerpo `ESTATICO` gobernado por deltas táctiles directos (`Touch Delta`). Cero vibración, respuesta 1:1 inmediata con límites de cámara respetados y collider sincronizado. |
| **Arsenal y Temática** | Botones e iconos militares (pistola, bombas con mecha, aviones, acorazados) incoherentes con un juego de temática marina. | Iconografía marina orgánica: Vida (Corazón de arrecife), Perlas doradas, Burbuja protectora, Faro de luz e Impulso/Turbo de aleta. |
| **Diseño del Nivel 1** | Mapa con techo de bloques de ladrillo aéreo flotando en el cielo sobre el mar sin lógica física ni estética. | Nivel abierto con superficie despejada, suelo de arena y guijarros, arcos de roca natural, corales y pilares laterales acordes a `fondo4.png`. |
| **Interfaz (UI) y Skin** | Skin genérica y botones desproporcionados para pantallas táctiles. | Skin con paleta marina profunda (`TintedDrawable`: azul abisal, cian, perla y coral) y botones táctiles ergonómicos. |

---

## 🗺️ 5. Hoja de Ruta para Modernización Visual y Físicas Avanzadas

1. **Micro-oscilación en reposo (*Idle Buoyancy*)**:
   - Integrar un sutil ciclo senoidal `MathUtils.sin(tiempo * frecuencia) * amplitud` sobre la coordenada Y cuando el pez esté quieto, simulando flotabilidad natural.
2. **Sistema de Partículas de Burbujas**:
   - Conectar un emisor de partículas continuo de libGDX a la cola del pez que incremente su tasa de emisión al acelerar o presionar `darImpulso()`.
3. **Zonas de Corrientes Marinas con Feedback**:
   - Aprovechar la clase existente `ZonaCorriente` en `Jugador.java` y renderizar columnas de partículas que indiquen la dirección del flujo de agua.
4. **Texturas HD y Shaders Cáusticos**:
   - Incorporar cáusticas de agua animadas mediante shader o texturas superpuestas con modo de mezcla aditivo (*blend mode*).
