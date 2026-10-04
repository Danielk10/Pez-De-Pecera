#!/usr/bin/env python3
"""
Generador del primer nivel submarino (nivel1.tmx) para Pez-De-Pecera.
Diseñado tomando como referencia visual fondo4.png:
- Parte superior abierta (superficie del agua con rayos de sol, sin bloques flotantes irreales).
- Fondo marino rocoso con guijarros blancos/grises, arena y lecho natural.
- Formaciones rocosas laterales (acantilado/columna a la izquierda, repisas a la derecha).
- Arcos y montículos naturales en el lecho marino con algas y corales.
- Aguas abiertas para el nado libre del pez, recolección de perlas y desafíos.
"""
import os

def generate_tmx():
    width = 150
    height = 35
    tile_size = 64

    # 1. Capa fondo_cueva (completamente transparente en aguas abiertas para ver el fondo4.png de paralaje)
    fondo_grid = [[0 for _ in range(width)] for _ in range(height)]
    # Borde inferior de profundidad abisal
    for x in range(width):
        fondo_grid[34][x] = 7

    # 2. Capa terreno_solido
    terreno_grid = [[0 for _ in range(width)] for _ in range(height)]

    # Suelo marino de guijarros y arena dorada (filas 33 y 34)
    for x in range(width):
        terreno_grid[33][x] = 1 # Arena / guijarros
        terreno_grid[34][x] = 2 # Roca base

    # Algas, corales y anémonas sobre el lecho marino (fila 32)
    flora_marina = [11, 0, 9, 11, 12, 0, 10, 11, 0, 16, 11, 12]
    for x in range(width):
        terreno_grid[32][x] = flora_marina[x % len(flora_marina)]

    # Formación rocosa izquierda estilo fondo4.png (columna natural)
    for y in range(12, 33):
        terreno_grid[y][0] = 4
        terreno_grid[y][1] = 4
    for y in range(18, 33):
        terreno_grid[y][2] = 4
    for y in range(24, 33):
        terreno_grid[y][3] = 4
    # Algas trepando la columna izquierda
    terreno_grid[11][1] = 11
    terreno_grid[17][2] = 11
    terreno_grid[23][3] = 11

    # Formación 1: Arco rocoso submarino en el fondo (como el arco en fondo4.png) (x: 24 a 30)
    for y in range(26, 32):
        terreno_grid[y][24] = 4
        terreno_grid[y][30] = 4
    for x in range(24, 31):
        terreno_grid[25][x] = 4
    terreno_grid[24][26] = 11
    terreno_grid[24][28] = 9

    # Formación 2: Montículo de coral y columna antigua (x: 48 a 54)
    for y in range(25, 32):
        for x in range(49, 54):
            terreno_grid[y][x] = 13
    terreno_grid[24][50] = 10
    terreno_grid[24][51] = 15
    terreno_grid[24][52] = 12

    # Formación 3: Arrecife de cristales y géiser del lecho marino (x: 72 a 77)
    for y in range(24, 32):
        for x in range(73, 77):
            terreno_grid[y][x] = 14
    terreno_grid[23][74] = 15
    terreno_grid[23][75] = 10

    # Formación 4: Gran arco / ruina sumergida (x: 96 a 102)
    for y in range(25, 32):
        terreno_grid[y][96] = 4
        terreno_grid[y][102] = 4
    for x in range(96, 103):
        terreno_grid[24][x] = 4
    terreno_grid[23][98] = 11
    terreno_grid[23][100] = 9

    # Formación 5: Banco de coral y pedestal abisal (x: 120 a 125)
    for y in range(26, 32):
        for x in range(120, 126):
            terreno_grid[y][x] = 13
    terreno_grid[25][122] = 15
    terreno_grid[25][123] = 12

    # Formación rocosa derecha estilo fondo4.png (repisas escalonadas hacia el final)
    for y in range(14, 33):
        terreno_grid[y][149] = 4
        terreno_grid[y][148] = 4
    for y in range(20, 33):
        terreno_grid[y][147] = 4
    for y in range(25, 33):
        terreno_grid[y][146] = 4
    terreno_grid[13][148] = 11
    terreno_grid[19][147] = 10
    terreno_grid[24][146] = 11

    # 3. Capa primer_plano (detalles frente al pez: plantas marinas)
    frente_grid = [[0 for _ in range(width)] for _ in range(height)]
    for x in range(5, width - 5, 7):
        frente_grid[31][x] = 11
    for x in range(9, width - 5, 11):
        frente_grid[31][x] = 9

    def grid_to_csv(grid):
        lines = []
        for row in grid:
            lines.append(",".join(str(val) for val in row))
        return ",\n".join(lines)

    csv_fondo = grid_to_csv(fondo_grid)
    csv_terreno = grid_to_csv(terreno_grid)
    csv_frente = grid_to_csv(frente_grid)

    total_px_width = width * tile_size
    total_px_height = height * tile_size

    tmx_content = f"""<?xml version="1.0" encoding="UTF-8"?>
<map version="1.10" tiledversion="1.10.2" orientation="orthogonal" renderorder="right-down" width="{width}" height="{height}" tilewidth="{tile_size}" tileheight="{tile_size}" infinite="0" nextlayerid="8" nextobjectid="120">
 <tileset firstgid="1" name="tileset_submarino" tilewidth="64" tileheight="64" tilecount="16" columns="4">
  <image source="tileset_submarino.png" width="256" height="256"/>
 </tileset>

 <layer id="1" name="fondo_cueva" width="{width}" height="{height}">
  <data encoding="csv">
{csv_fondo}
  </data>
 </layer>

 <layer id="2" name="terreno_solido" width="{width}" height="{height}">
  <data encoding="csv">
{csv_terreno}
  </data>
 </layer>

 <layer id="3" name="primer_plano" width="{width}" height="{height}">
  <data encoding="csv">
{csv_frente}
  </data>
 </layer>

 <objectgroup id="4" name="colisiones">
  <!-- Límites del escenario: techo de agua superficial invisible, suelo y laterales -->
  <object id="1" name="TechoSuperficie" x="0" y="0" width="{total_px_width}" height="64"/>
  <object id="2" name="SueloMarino" x="0" y="2112" width="{total_px_width}" height="128"/>
  <object id="3" name="ParedIzquierda" x="0" y="0" width="64" height="{total_px_height}"/>
  <object id="4" name="ParedDerecha" x="{total_px_width - 64}" y="0" width="64" height="{total_px_height}"/>

  <!-- Formaciones rocosas naturales del lecho marino (sin bloques aéreos) -->
  <object id="5" name="ColumnaIzquierda" x="0" y="1152" width="256" height="960"/>
  <object id="6" name="ArcoSubmarino1" x="1536" y="1600" width="448" height="512"/>
  <object id="7" name="ArrecifeCentral" x="3136" y="1536" width="320" height="576"/>
  <object id="8" name="CristalesMarinos" x="4672" y="1472" width="256" height="640"/>
  <object id="9" name="ArcoSubmarino2" x="6144" y="1536" width="448" height="576"/>
  <object id="10" name="PedestalAbisal" x="7680" y="1664" width="384" height="448"/>
  <object id="11" name="RepisasDerecha" x="9344" y="1280" width="256" height="832"/>
 </objectgroup>

 <objectgroup id="5" name="corrientes">
  <object id="20" name="CorrienteAguasAbiertas" x="1800" y="400" width="1200" height="400">
   <properties>
    <property name="forceX" type="float" value="12.0"/>
    <property name="forceY" type="float" value="0.0"/>
   </properties>
  </object>
  <object id="21" name="GeiserSubmarino" x="4700" y="1200" width="300" height="800">
   <properties>
    <property name="forceX" type="float" value="0.0"/>
    <property name="forceY" type="float" value="16.0"/>
   </properties>
  </object>
  <object id="22" name="CorrienteFosaFinal" x="6600" y="500" width="1000" height="400">
   <properties>
    <property name="forceX" type="float" value="10.0"/>
    <property name="forceY" type="float" value="2.0"/>
   </properties>
  </object>
 </objectgroup>

 <objectgroup id="6" name="spawns">
  <!-- Spawn Jugador en aguas someras cerca del lecho marino con arena y algas visibles -->
  <object id="30" name="SpawnJugador" x="350" y="1800" width="32" height="32"/>

  <!-- Bosque de algas marinas enraizadas en el lecho arenoso (y=2048 en TMX = lecho marino en LibGDX) -->
  <object name="Algas" x="180" y="2048" width="96" height="64"/>
  <object name="Algas" x="380" y="2048" width="96" height="64"/>
  <object name="Algas" x="580" y="2048" width="96" height="64"/>
  <object name="Algas" x="800" y="2048" width="96" height="64"/>
  <object name="Algas" x="1050" y="2048" width="96" height="64"/>
  <object name="Algas" x="1300" y="2048" width="96" height="64"/>
  <object name="Algas" x="2050" y="2048" width="96" height="64"/>
  <object name="Algas" x="2300" y="2048" width="96" height="64"/>
  <object name="Algas" x="2600" y="2048" width="96" height="64"/>
  <object name="Algas" x="2900" y="2048" width="96" height="64"/>
  <object name="Algas" x="3450" y="2048" width="96" height="64"/>
  <object name="Algas" x="3800" y="2048" width="96" height="64"/>
  <object name="Algas" x="4150" y="2048" width="96" height="64"/>
  <object name="Algas" x="4450" y="2048" width="96" height="64"/>
  <object name="Algas" x="5050" y="2048" width="96" height="64"/>
  <object name="Algas" x="5400" y="2048" width="96" height="64"/>
  <object name="Algas" x="5800" y="2048" width="96" height="64"/>
  <object name="Algas" x="6250" y="2048" width="96" height="64"/>
  <object name="Algas" x="6650" y="2048" width="96" height="64"/>
  <object name="Algas" x="7050" y="2048" width="96" height="64"/>
  <object name="Algas" x="7450" y="2048" width="96" height="64"/>
  <object name="Algas" x="8100" y="2048" width="96" height="64"/>
  <object name="Algas" x="8450" y="2048" width="96" height="64"/>
  <object name="Algas" x="8800" y="2048" width="96" height="64"/>
  <object name="Algas" x="9150" y="2048" width="96" height="64"/>

  <!-- Perlas coleccionables (+100 pts) en curvas naturales de nado -->
  <!-- Zona 1: Lecho de Arena y Arrecife de Entrada -->
  <object name="Perla" x="480" y="1780" width="24" height="24"/>
  <object name="Perla" x="620" y="1720" width="24" height="24"/>
  <object name="Perla" x="760" y="1660" width="24" height="24"/>
  <object name="Perla" x="940" y="1620" width="24" height="24"/>
  <object name="Perla" x="1120" y="1660" width="24" height="24"/>
  <object name="Perla" x="1300" y="1720" width="24" height="24"/>
  <object name="Perla" x="1480" y="1780" width="24" height="24"/>
  <object name="Perla" x="1750" y="1550" width="24" height="24"/>
  <!-- Zona 2: Corrientes de Aguas Abiertas y Gran Banco de Coral -->
  <object name="Perla" x="2150" y="1450" width="24" height="24"/>
  <object name="Perla" x="2350" y="1400" width="24" height="24"/>
  <object name="Perla" x="2550" y="1450" width="24" height="24"/>
  <object name="Perla" x="2750" y="1550" width="24" height="24"/>
  <object name="Perla" x="3250" y="1720" width="24" height="24"/>
  <object name="Perla" x="3450" y="1760" width="24" height="24"/>
  <object name="Perla" x="3650" y="1720" width="24" height="24"/>
  <object name="Perla" x="4050" y="1500" width="24" height="24"/>
  <!-- Zona 3: El Gran Geiser Hidrotermal y Cañón Abisal -->
  <object name="Perla" x="4850" y="1450" width="24" height="24"/>
  <object name="Perla" x="5050" y="1400" width="24" height="24"/>
  <object name="Perla" x="5250" y="1450" width="24" height="24"/>
  <object name="Perla" x="5550" y="1680" width="24" height="24"/>
  <object name="Perla" x="5750" y="1720" width="24" height="24"/>
  <object name="Perla" x="6250" y="1550" width="24" height="24"/>
  <object name="Perla" x="6450" y="1600" width="24" height="24"/>
  <!-- Zona 4: El Santuario Marino y Arrecife Dorado -->
  <object name="Perla" x="7050" y="1500" width="24" height="24"/>
  <object name="Perla" x="7250" y="1450" width="24" height="24"/>
  <object name="Perla" x="7450" y="1500" width="24" height="24"/>
  <object name="Perla" x="7950" y="1750" width="24" height="24"/>
  <object name="Perla" x="8150" y="1780" width="24" height="24"/>
  <object name="Perla" x="8650" y="1620" width="24" height="24"/>
  <object name="Perla" x="8850" y="1680" width="24" height="24"/>
  <object name="Perla" x="9050" y="1720" width="24" height="24"/>

  <!-- Burbujas de oxígeno y vitalidad marina -->
  <object name="Burbuja" x="980" y="1750" width="28" height="28"/>
  <object name="Burbuja" x="2650" y="1600" width="28" height="28"/>
  <object name="Burbuja" x="4450" y="1750" width="28" height="28"/>
  <object name="Burbuja" x="6350" y="1450" width="28" height="28"/>
  <object name="Burbuja" x="8250" y="1720" width="28" height="28"/>

  <!-- Power-Ups de exploración submarina -->
  <object name="PowerUp_Turbo" x="850" y="1550" width="36" height="36"/>
  <object name="PowerUp_Escudo" x="2250" y="1650" width="36" height="36"/>
  <object name="PowerUp_Turbo" x="4150" y="1400" width="36" height="36"/>
  <object name="PowerUp_Linterna" x="5450" y="1550" width="36" height="36"/>
  <object name="PowerUp_Escudo" x="7350" y="1680" width="36" height="36"/>
  <object name="PowerUp_Turbo" x="8750" y="1500" width="36" height="36"/>

  <!-- Fauna marina y Ecosistema Dinámico por Biomas -->
  <!-- BIOMA 1: Arrecife de Coral Luminoso (x: 0 - 2400) -->
  <object name="PezAngel" x="650" y="1750" width="64" height="32"/>
  <object name="CardumenKrill" x="800" y="1600" width="48" height="48"/>
  <object name="PezAngel" x="1150" y="1680" width="64" height="32"/>
  <object name="OstraGigante" x="1200" y="2048" width="64" height="64"/>
  <object name="PezGloboAmarillo" x="1400" y="1800" width="64" height="32"/>
  <object name="ErizoMarino" x="1550" y="2048" width="40" height="40"/>
  <object name="TortugaMarina" x="1650" y="1520" width="96" height="72"/>
  <object name="Bomba" x="1950" y="1450" width="64" height="64"/>

  <!-- BIOMA 2: Bosque de Algas y Arrecife Rocoso (x: 2400 - 4600) -->
  <object name="PezAngel" x="2450" y="1550" width="64" height="32"/>
  <object name="CardumenKrill" x="2700" y="1400" width="48" height="48"/>
  <object name="PezGloboNaranja" x="2850" y="1720" width="96" height="64"/>
  <object name="ErizoMarino" x="3180" y="2048" width="40" height="40"/>
  <object name="TiburonAzul" x="3300" y="1380" width="192" height="192"/>
  <object name="OstraGigante" x="3500" y="2048" width="64" height="64"/>
  <object name="Pulpo" x="3750" y="1600" width="48" height="96"/>
  <object name="Bomba" x="4000" y="1300" width="64" height="64"/>
  <object name="TortugaMarina" x="4300" y="1450" width="96" height="72"/>

  <!-- BIOMA 3: Fosa Abisal y Geiser Hidrotermal (x: 4600 - 6800) -->
  <object name="PezAngel" x="4600" y="1550" width="64" height="32"/>
  <object name="ErizoMarino" x="4720" y="2048" width="40" height="40"/>
  <object name="PezGloboAmarillo" x="5100" y="1750" width="64" height="32"/>
  <object name="CardumenKrill" x="5300" y="1350" width="48" height="48"/>
  <object name="TiburonAzul" x="5750" y="1350" width="192" height="192"/>
  <object name="OstraGigante" x="5900" y="2048" width="64" height="64"/>
  <object name="ErizoMarino" x="6160" y="2048" width="40" height="40"/>
  <object name="Pulpo" x="6250" y="1650" width="48" height="96"/>
  <object name="Bomba" x="6700" y="1400" width="64" height="64"/>

  <!-- BIOMA 4: Santuario de la Ballena y Mar Abierto (x: 6800 - 9600) -->
  <object name="TortugaMarina" x="7100" y="1400" width="96" height="72"/>
  <object name="PezAngel" x="7250" y="1620" width="64" height="32"/>
  <object name="PezGloboNaranja" x="7550" y="1720" width="96" height="64"/>
  <object name="ErizoMarino" x="7720" y="2048" width="40" height="40"/>
  <object name="Ballena" x="7800" y="900" width="240" height="120"/>
  <object name="Pulpo" x="8000" y="1600" width="48" height="96"/>
  <object name="CardumenKrill" x="8300" y="1500" width="48" height="48"/>
  <object name="OstraGigante" x="8450" y="2048" width="64" height="64"/>
  <object name="TiburonAzul" x="8700" y="1380" width="192" height="192"/>
  <object name="Bomba" x="9000" y="1450" width="64" height="64"/>
  <object name="Ballena" x="9150" y="1100" width="240" height="120"/>
 </objectgroup>

 <objectgroup id="7" name="luces">
  <object name="LuzArrecife" x="500" y="1750">
   <properties>
    <property name="color" value="#44ccff"/>
    <property name="distancia" type="float" value="6.5"/>
   </properties>
  </object>
  <object name="LuzArco1" x="1750" y="1650">
   <properties>
    <property name="color" value="#22aaff"/>
    <property name="distancia" type="float" value="7.5"/>
   </properties>
  </object>
  <object name="LuzGeiser" x="4800" y="1500">
   <properties>
    <property name="color" value="#ff7722"/>
    <property name="distancia" type="float" value="8.5"/>
   </properties>
  </object>
  <object name="LuzArco2" x="6300" y="1600">
   <properties>
    <property name="color" value="#44ccff"/>
    <property name="distancia" type="float" value="7.5"/>
   </properties>
  </object>
  <object name="LuzSantuario" x="9200" y="1650">
   <properties>
    <property name="color" value="#ffd700"/>
    <property name="distancia" type="float" value="9.0"/>
   </properties>
  </object>
 </objectgroup>
</map>
"""
    output_path = os.path.join(os.path.dirname(__file__), "nivel1.tmx")
    with open(output_path, "w", encoding="utf-8") as f:
        f.write(tmx_content)
    print(f"Mapa TMX generado exitosamente en: {output_path} ({width}x{height} tiles)")

if __name__ == "__main__":
    generate_tmx()
