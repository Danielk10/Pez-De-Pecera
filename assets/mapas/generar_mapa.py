#!/usr/bin/env python3
"""
Generador del primer nivel submarino (nivel1.tmx) para Pez-De-Pecera.
Inspirado en la atmósfera y diseño de Little Nemo: The Dream Master (Fase Marina):
- Lecho marino con arena dorada real, ondulaciones de dunas, conchas y algas.
- Gran precipicio vertical (abyssal drop-off) con paredes rocosas escarpadas.
- Cañones submarinos profundos con ostras gigantes de perlas, géiseres hidrotermales y erizos.
- Mar abierto amplio y libre donde navegan ballenas colosales, cardúmenes de krill y tortugas.
- Cero obstáculos cuadrados irreales en el medio del agua: nado 360° totalmente libre.
"""
import os

def generate_tmx():
    width = 150
    height = 35
    tile_size = 64

    # 1. Capa fondo_cueva (transparente en aguas abiertas, vetas bioluminiscentes en el fondo abisal)
    fondo_grid = [[0 for _ in range(width)] for _ in range(height)]
    for x in range(width):
        fondo_grid[34][x] = 7

    # 2. Capa terreno_solido: Relieve orgánico con arena real y precipicios tipo Little Nemo
    terreno_grid = [[0 for _ in range(width)] for _ in range(height)]

    # Definir la altura del lecho marino por columna x:
    # 0 a 34: Meseta arenosa somera (fila 27)
    # 35: Precipicio izquierdo (caída vertical a fila 33)
    # 36 a 58: Cañón submarino profundo (fila 33)
    # 59: Precipicio derecho (ascenso a fila 28)
    # 60 a 72: Meseta de coral intermedia (fila 28)
    # 73: Precipicio izquierdo (caída a fila 33)
    # 74 a 96: Fosa abisal del tiburón (fila 33)
    # 97 a 101: Rampa de ascenso suave a mar abierto (filas 33 -> 32)
    # 102 a 149: Mar abierto con lecho de dunas de arena (fila 32)
    floor_rows = [33] * width

    for x in range(0, 35):
        floor_rows[x] = 27
    floor_rows[35] = 33
    for x in range(36, 59):
        floor_rows[x] = 33
    floor_rows[59] = 28
    for x in range(60, 73):
        floor_rows[x] = 28
    floor_rows[73] = 33
    for x in range(74, 97):
        floor_rows[x] = 33
    for x in range(97, 102):
        floor_rows[x] = 33 - (x - 97) // 2 # 33, 33, 32, 32, 31
    for x in range(102, width):
        floor_rows[x] = 32

    # Rellenar terreno según la cota de suelo
    for x in range(width):
        fr = floor_rows[x]
        # Superficie de arena dorada real (Tile 1: draw_sand_top)
        if fr < height:
            terreno_grid[fr][x] = 1
        # Estrato sedimentario profundo de arena (Tile 2: draw_sand_deep)
        for y in range(fr + 1, height):
            terreno_grid[y][x] = 2

    # Construir paredes rocosas de los precipicios (estilo Little Nemo)
    # Precipicio 1: Caída vertical en x = 35 (de fila 27 a 32)
    for y in range(27, 33):
        terreno_grid[y][34] = 4 # Roca profunda detrás
        terreno_grid[y][35] = 5 # Tile 5: Pared rocosa izquierda con musgo

    # Precipicio 1: Muro derecho ascendente en x = 59 (de fila 28 a 32)
    for y in range(28, 33):
        terreno_grid[y][59] = 6 # Tile 6: Pared rocosa derecha
        terreno_grid[y][60] = 4

    # Precipicio 2: Caída vertical en x = 73 (de fila 28 a 32)
    for y in range(28, 33):
        terreno_grid[y][72] = 4
        terreno_grid[y][73] = 5

    # Precipicio 2: Muro derecho ascendente en x = 97
    for y in range(29, 33):
        terreno_grid[y][96] = 6

    # Flora marina y formaciones sobre la arena dorada (1 baldosa arriba del suelo)
    flora_marina = [11, 0, 9, 11, 12, 0, 10, 11, 0, 16, 11, 12, 10, 0, 15]
    for x in range(width):
        fr = floor_rows[x]
        if fr > 0 and x != 35 and x != 59 and x != 73 and x != 96:
            terreno_grid[fr - 1][x] = flora_marina[x % len(flora_marina)]

    # Añadir respiraderos hidrotermales (Tile 16) y cristales (Tile 15) en el fondo de los cañones
    terreno_grid[32][45] = 16 # Respiradero de burbujas
    terreno_grid[32][46] = 15 # Cristales luminosos
    terreno_grid[32][85] = 16 # Respiradero abisal
    terreno_grid[32][86] = 15 # Cristales abisales

    # 3. Capa primer_plano (plantas marinas ondulantes en primer término)
    frente_grid = [[0 for _ in range(width)] for _ in range(height)]
    for x in range(4, width - 4, 5):
        fr = floor_rows[x]
        if fr > 1 and x != 35 and x != 59 and x != 73:
            frente_grid[fr - 2][x] = 11

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
<map version="1.10" tiledversion="1.10.2" orientation="orthogonal" renderorder="right-down" width="{width}" height="{height}" tilewidth="{tile_size}" tileheight="{tile_size}" infinite="0" nextlayerid="8" nextobjectid="150">
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
  <!-- Límites perimetrales del océano abierto (sin obstáculos cuadrados flotantes) -->
  <object id="1" name="TechoSuperficie" x="0" y="0" width="{total_px_width}" height="64"/>
  <object id="2" name="ParedIzquierda" x="0" y="0" width="64" height="{total_px_height}"/>
  <object id="3" name="ParedDerecha" x="{total_px_width - 64}" y="0" width="64" height="{total_px_height}"/>

  <!-- Relieve orgánico del lecho marino con precipicios de Little Nemo -->
  <!-- Meseta 1: Arena dorada somera (x: 0 a 2240) -->
  <object id="10" name="Meseta1_Arena" x="0" y="1728" width="2240" height="512"/>
  <!-- Cañón 1: Suelo profundo tras el gran precipicio (x: 2240 a 3776) -->
  <object id="11" name="Canon1_Suelo" x="2240" y="2112" width="1536" height="128"/>
  <!-- Meseta 2: Arrecife intermedio (x: 3840 a 4672) -->
  <object id="12" name="Meseta2_Arena" x="3840" y="1792" width="832" height="448"/>
  <!-- Fosa 2: Abismo del tiburón (x: 4672 a 6208) -->
  <object id="13" name="Fosa2_Suelo" x="4672" y="2112" width="1536" height="128"/>
  <!-- Mar Abierto: Gran lecho de arena continua (x: 6208 a 9600) -->
  <object id="14" name="MarAbierto_Arena" x="6208" y="2048" width="3392" height="192"/>
 </objectgroup>

 <objectgroup id="5" name="corrientes">
  <object id="20" name="CorrienteAguasAbiertas" x="1800" y="400" width="1400" height="500">
   <properties>
    <property name="forceX" type="float" value="14.0"/>
    <property name="forceY" type="float" value="0.0"/>
   </properties>
  </object>
  <!-- Géiser hidrotermal en el fondo del cañón que expulsa al pez hacia la superficie -->
  <object id="21" name="GeiserSubmarino" x="2900" y="1100" width="300" height="1000">
   <properties>
    <property name="forceX" type="float" value="0.0"/>
    <property name="forceY" type="float" value="18.0"/>
   </properties>
  </object>
  <object id="22" name="CorrienteFosaFinal" x="6600" y="500" width="1200" height="450">
   <properties>
    <property name="forceX" type="float" value="12.0"/>
    <property name="forceY" type="float" value="2.5"/>
   </properties>
  </object>
 </objectgroup>

 <objectgroup id="6" name="spawns">
  <!-- Spawn Jugador en aguas cristalinas sobre la meseta de arena dorada -->
  <object id="30" name="SpawnJugador" x="350" y="1500" width="32" height="32"/>

  <!-- Algas enraizadas en el lecho arenoso -->
  <object name="Algas" x="180" y="1664" width="96" height="64"/>
  <object name="Algas" x="480" y="1664" width="96" height="64"/>
  <object name="Algas" x="850" y="1664" width="96" height="64"/>
  <object name="Algas" x="1250" y="1664" width="96" height="64"/>
  <object name="Algas" x="1750" y="1664" width="96" height="64"/>
  <object name="Algas" x="2450" y="2048" width="96" height="64"/>
  <object name="Algas" x="3150" y="2048" width="96" height="64"/>
  <object name="Algas" x="4050" y="1728" width="96" height="64"/>
  <object name="Algas" x="4950" y="2048" width="96" height="64"/>
  <object name="Algas" x="5650" y="2048" width="96" height="64"/>
  <object name="Algas" x="6650" y="1984" width="96" height="64"/>
  <object name="Algas" x="7450" y="1984" width="96" height="64"/>
  <object name="Algas" x="8350" y="1984" width="96" height="64"/>
  <object name="Algas" x="9150" y="1984" width="96" height="64"/>

  <!-- Perlas en arcos de exploración marina -->
  <!-- Zona 1: Meseta de Arena Dorada -->
  <object name="Perla" x="500" y="1450" width="24" height="24"/>
  <object name="Perla" x="700" y="1380" width="24" height="24"/>
  <object name="Perla" x="900" y="1450" width="24" height="24"/>
  <object name="Perla" x="1200" y="1400" width="24" height="24"/>
  <object name="Perla" x="1500" y="1350" width="24" height="24"/>
  <object name="Perla" x="1800" y="1400" width="24" height="24"/>
  <!-- Curva de descenso al Gran Precipicio de Nemo -->
  <object name="Perla" x="2150" y="1600" width="24" height="24"/>
  <object name="Perla" x="2280" y="1750" width="24" height="24"/>
  <object name="Perla" x="2380" y="1920" width="24" height="24"/>
  <!-- Zona 2: En el fondo del Cañón de las Conchas -->
  <object name="Perla" x="2700" y="1950" width="24" height="24"/>
  <object name="Perla" x="3100" y="1900" width="24" height="24"/>
  <object name="Perla" x="3500" y="1950" width="24" height="24"/>
  <!-- Zona 3: Fosa Abisal y Cueva del Tiburón -->
  <object name="Perla" x="4850" y="1950" width="24" height="24"/>
  <object name="Perla" x="5150" y="1850" width="24" height="24"/>
  <object name="Perla" x="5550" y="1900" width="24" height="24"/>
  <object name="Perla" x="5950" y="1850" width="24" height="24"/>
  <!-- Zona 4: Santuario de Mar Abierto -->
  <object name="Perla" x="6800" y="1500" width="24" height="24"/>
  <object name="Perla" x="7100" y="1400" width="24" height="24"/>
  <object name="Perla" x="7400" y="1350" width="24" height="24"/>
  <object name="Perla" x="7800" y="1400" width="24" height="24"/>
  <object name="Perla" x="8200" y="1350" width="24" height="24"/>
  <object name="Perla" x="8600" y="1400" width="24" height="24"/>
  <object name="Perla" x="9000" y="1450" width="24" height="24"/>

  <!-- Burbujas de oxígeno y vitalidad marina -->
  <object name="Burbuja" x="950" y="1500" width="28" height="28"/>
  <object name="Burbuja" x="2800" y="1850" width="28" height="28"/>
  <object name="Burbuja" x="4300" y="1600" width="28" height="28"/>
  <object name="Burbuja" x="5700" y="1850" width="28" height="28"/>
  <object name="Burbuja" x="7900" y="1500" width="28" height="28"/>

  <!-- Power-Ups submarinos -->
  <object name="PowerUp_Turbo" x="1100" y="1350" width="36" height="36"/>
  <object name="PowerUp_Escudo" x="2500" y="1800" width="36" height="36"/>
  <object name="PowerUp_Linterna" x="4800" y="1750" width="36" height="36"/>
  <object name="PowerUp_Escudo" x="6900" y="1450" width="36" height="36"/>
  <object name="PowerUp_Turbo" x="8400" y="1350" width="36" height="36"/>

  <!-- Fauna y Criaturas Marinas con IA libre (sin patrones estáticos) -->
  <!-- SECTOR 1: Meseta de Arena Dorada y Arrecife Somero -->
  <object name="PezAngel" x="650" y="1550" width="64" height="32"/>
  <object name="CardumenKrill" x="800" y="1450" width="48" height="48"/>
  <object name="PezAngel" x="1150" y="1500" width="64" height="32"/>
  <object name="TortugaMarina" x="1450" y="1380" width="96" height="72"/>
  <object name="PezGloboAmarillo" x="1750" y="1520" width="64" height="32"/>

  <!-- SECTOR 2: El Gran Precipicio y Cañón de las Conchas Gigantes -->
  <!-- Erizos pegados en el muro vertical del precipicio -->
  <object name="ErizoMarino" x="2200" y="1850" width="40" height="40"/>
  <object name="OstraGigante" x="2550" y="2048" width="64" height="64"/>
  <object name="CardumenKrill" x="2850" y="1750" width="48" height="48"/>
  <object name="OstraGigante" x="3350" y="2048" width="64" height="64"/>
  <object name="ErizoMarino" x="3760" y="1880" width="40" height="40"/>
  <object name="Bomba" x="3500" y="1600" width="64" height="64"/>

  <!-- SECTOR 3: Meseta Intermedia y Fosa del Tiburón -->
  <object name="TortugaMarina" x="4100" y="1500" width="96" height="72"/>
  <object name="PezGloboNaranja" x="4400" y="1550" width="96" height="64"/>
  <!-- Segundo Precipicio y Cañón Abisal -->
  <object name="ErizoMarino" x="4680" y="1900" width="40" height="40"/>
  <object name="TiburonAzul" x="5100" y="1750" width="192" height="192"/>
  <object name="OstraGigante" x="5450" y="2048" width="64" height="64"/>
  <object name="CardumenKrill" x="5750" y="1700" width="48" height="48"/>
  <object name="Pulpo" x="5900" y="1750" width="48" height="96"/>
  <object name="Bomba" x="6150" y="1650" width="64" height="64"/>

  <!-- SECTOR 4: Santuario de Mar Abierto y Ballena Azul -->
  <object name="PezAngel" x="6600" y="1450" width="64" height="32"/>
  <object name="TortugaMarina" x="6950" y="1350" width="96" height="72"/>
  <!-- La gran ballena navegando en aguas abiertas -->
  <object name="Ballena" x="7300" y="850" width="240" height="120"/>
  <object name="CardumenKrill" x="7700" y="1400" width="48" height="48"/>
  <object name="OstraGigante" x="8050" y="1984" width="64" height="64"/>
  <object name="PezGloboAmarillo" x="8350" y="1500" width="64" height="32"/>
  <object name="Ballena" x="8700" y="1000" width="240" height="120"/>
  <object name="CardumenKrill" x="9050" y="1380" width="48" height="48"/>
  <object name="OstraGigante" x="9250" y="1984" width="64" height="64"/>
 </objectgroup>

 <objectgroup id="7" name="luces">
  <object name="LuzMeseta1" x="500" y="1450">
   <properties>
    <property name="color" value="#66ddff"/>
    <property name="distancia" type="float" value="7.5"/>
   </properties>
  </object>
  <object name="LuzPrecipicio1" x="2250" y="1800">
   <properties>
    <property name="color" value="#33aaff"/>
    <property name="distancia" type="float" value="8.0"/>
   </properties>
  </object>
  <object name="LuzGeiser" x="2950" y="1500">
   <properties>
    <property name="color" value="#ff8833"/>
    <property name="distancia" type="float" value="9.0"/>
   </properties>
  </object>
  <object name="LuzFosaTiburon" x="5100" y="1750">
   <properties>
    <property name="color" value="#2277bb"/>
    <property name="distancia" type="float" value="8.5"/>
   </properties>
  </object>
  <object name="LuzSantuarioBallena" x="8200" y="1200">
   <properties>
    <property name="color" value="#ffe066"/>
    <property name="distancia" type="float" value="10.5"/>
   </properties>
  </object>
 </objectgroup>
</map>
"""
    output_path = os.path.join(os.path.dirname(__file__), "nivel1.tmx")
    with open(output_path, "w", encoding="utf-8") as f:
        f.write(tmx_content)
    print(f"Mapa TMX con arena real y precipicios tipo Little Nemo generado exitosamente en: {output_path} ({width}x{height} tiles)")

if __name__ == "__main__":
    generate_tmx()
