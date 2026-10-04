#!/usr/bin/env python3
"""
Generador del primer nivel submarino (nivel1.tmx) para Pez-De-Pecera.
Inspirado en la atmósfera y diseño de Little Nemo: The Dream Master (Fase Marina):
- Lecho marino con arena dorada real, ondulaciones naturales de dunas y pendientes graduales.
- Grandes precipicios verticales escarpados (abyssal drop-offs) con paredes rocosas de cueva.
- Cañón submarino profundo con ostras gigantes de perlas, géiseres hidrotermales y erizos marinos.
- Gran santuario de mar abierto libre donde navegan ballenas colosales, cardúmenes tipo La Sirenita y tortugas.
- Cero obstáculos cuadrados artificiales: terreno orgánico y nado 360° totalmente libre.
- Colisión Box2D generada como cadena continua (ChainShape) que calza píxel por píxel con las dunas de arena.
"""
import os
import math

def generate_tmx():
    width = 150
    height = 35
    tile_size = 64
    total_px_width = width * tile_size
    total_px_height = height * tile_size

    # Capa 1: fondo_cueva (transparente en aguas abiertas, roca y bioluminiscencia en fosa abisal)
    fondo_grid = [[0 for _ in range(width)] for _ in range(height)]
    for x in range(width):
        fondo_grid[34][x] = 7 # Roca base en el estrato más profundo

    # Añadir bioluminiscencia de fondo en las fosas abisales (cañón y cueva del tiburón)
    for x in range(36, 64):
        fondo_grid[32][x] = 8
        fondo_grid[33][x] = 8
    for x in range(91, 98):
        fondo_grid[32][x] = 8
        fondo_grid[33][x] = 8

    # Capa 2: terreno_solido (arena real, pendientes y precipicios de Little Nemo)
    terreno_grid = [[0 for _ in range(width)] for _ in range(height)]

    # Definir el perfil orgánico del lecho marino:
    # (columna x -> (row, tipo_tile: 1=arena_plana, 13=rampa_baja, 14=rampa_sube, 5=muro_izq, 6=muro_der))
    floor_profile = {}

    # --- SECTOR 1: Arrecife Somero y Dunas de Arena Dorada (x: 0 a 33) ---
    for x in range(0, 7):
        floor_profile[x] = (27, 1)
    floor_profile[7] = (27, 13) # Rampa suave descendente a fila 28
    for x in range(8, 15):
        floor_profile[x] = (28, 1)
    floor_profile[15] = (28, 14) # Rampa suave ascendente a fila 27
    for x in range(16, 23):
        floor_profile[x] = (27, 1)
    floor_profile[23] = (27, 14) # Rampa suave ascendente a cresta en fila 26
    for x in range(24, 34):
        floor_profile[x] = (26, 1) # Cresta del precipicio somero

    # --- PRECIPICIO 1: El Gran Abismo de Little Nemo (x: 34 a 39) ---
    floor_profile[34] = (26, 1)
    # x = 35: Pared rocosa vertical que cae desde fila 26 hasta fila 32
    floor_profile[35] = (33, 5) # Muro vertical en x=35
    floor_profile[36] = (32, 13) # Rampa inferior de llegada a la fosa

    # --- SECTOR 2: Cañón de las Conchas y Fosas Hidrotermales (x: 37 a 64) ---
    for x in range(37, 65):
        floor_profile[x] = (33, 1)

    # --- TRANSICIÓN: Rampa de ascenso escalonada a meseta intermedia (x: 65 a 70) ---
    floor_profile[65] = (33, 14) # Ascenso a 32
    floor_profile[66] = (32, 1)
    floor_profile[67] = (32, 14) # Ascenso a 31
    floor_profile[68] = (31, 14) # Ascenso a 30
    floor_profile[69] = (30, 1)
    floor_profile[70] = (30, 14) # Ascenso a 29

    # --- SECTOR 3: Arrecife Crepuscular de Coral y Fosa del Tiburón (x: 71 a 98) ---
    for x in range(71, 77):
        floor_profile[x] = (29, 1)
    floor_profile[77] = (29, 13) # Rampa descendente
    for x in range(78, 85):
        floor_profile[x] = (30, 1)
    floor_profile[85] = (30, 14) # Rampa ascendente
    for x in range(86, 89):
        floor_profile[x] = (29, 1)

    # Precipicio 2: Caída vertical a la fosa abisal del tiburón (x: 89 a 90)
    floor_profile[89] = (29, 13)
    floor_profile[90] = (33, 5) # Muro rocoso vertical
    for x in range(91, 99):
        floor_profile[x] = (33, 1) # Fosa profunda del tiburón

    # --- ASCENSO A MAR ABIERTO (x: 99 a 104) ---
    floor_profile[99] = (33, 14) # Ascenso a 32
    for x in range(100, 103):
        floor_profile[x] = (32, 1)
    floor_profile[103] = (32, 14) # Ascenso a 31
    floor_profile[104] = (31, 1)

    # --- SECTOR 4: Santuario de Mar Abierto y Ballena Azul (x: 105 a 149) ---
    # Ondulaciones amplias y suaves de dunas doradas
    for x in range(105, 112):
        floor_profile[x] = (31, 1)
    floor_profile[112] = (31, 13) # Duna baja a 32
    for x in range(113, 121):
        floor_profile[x] = (32, 1)
    floor_profile[121] = (32, 14) # Duna sube a 31
    for x in range(122, 130):
        floor_profile[x] = (31, 1)
    floor_profile[130] = (31, 13) # Duna baja a 32
    for x in range(131, 139):
        floor_profile[x] = (32, 1)
    floor_profile[139] = (32, 14) # Duna sube a 31
    for x in range(140, 147):
        floor_profile[x] = (31, 1)
    floor_profile[147] = (31, 13) # Duna baja a 32
    for x in range(148, width):
        floor_profile[x] = (32, 1)

    # Rellenar la cuadrícula del terreno según el perfil
    for x in range(width):
        row, tile_id = floor_profile[x]
        terreno_grid[row][x] = tile_id
        # Estrato sedimentario profundo debajo de la superficie (Tile 2: draw_sand_deep)
        for y in range(row + 1, height):
            terreno_grid[y][x] = 2

    # Construir las paredes rocosas de los precipicios verticales de Little Nemo
    # Precipicio 1 en x = 35: Muro rocoso desde fila 26 a 32
    for y in range(26, 33):
        terreno_grid[y][34] = 4 # Roca profunda detrás
        terreno_grid[y][35] = 5 # Pared rocosa con musgo marino

    # Precipicio 2 en x = 90: Muro rocoso desde fila 30 a 33
    for y in range(29, 33):
        terreno_grid[y][89] = 4
        terreno_grid[y][90] = 5

    # Flora marina natural y decoraciones sobre la arena dorada (corales, anémonas, algas, cristales)
    # GIDs: 9=coral rosa, 10=coral azul, 11=alga oscura, 12=anemona dorada bioluminiscente, 15=cristales luminosos, 16=respiradero burbujas
    flora_marina = [11, 0, 9, 12, 11, 0, 10, 12, 0, 11, 9, 12, 0, 10, 11]
    for x in range(width):
        row, tile_id = floor_profile[x]
        if row > 0 and x not in (34, 35, 36, 89, 90, 91):
            if tile_id == 1: # Solo sobre arena plana para perfecta estética
                dec = flora_marina[x % len(flora_marina)]
                terreno_grid[row - 1][x] = dec

    # Añadir respiraderos hidrotermales (Tile 16) y cristales abisales (Tile 15) en los cañones
    terreno_grid[33][45] = 16 # Respiradero activo de burbujas
    terreno_grid[32][46] = 15 # Aguja de cristal bioluminiscente
    terreno_grid[33][57] = 16 # Respiradero secundario
    terreno_grid[32][58] = 15
    terreno_grid[33][94] = 16 # Respiradero abisal del tiburón
    terreno_grid[32][95] = 15

    # Capa 3: primer_plano (algas marinas ondulantes en primer término)
    frente_grid = [[0 for _ in range(width)] for _ in range(height)]
    for x in range(3, width - 3, 6):
        row, tile_id = floor_profile[x]
        if row > 2 and x not in (34, 35, 36, 89, 90, 91):
            frente_grid[row - 2][x] = 11

    def grid_to_csv(grid):
        lines = []
        for r in grid:
            lines.append(",".join(str(val) for val in r))
        return ",\n".join(lines)

    csv_fondo = grid_to_csv(fondo_grid)
    csv_terreno = grid_to_csv(terreno_grid)
    csv_frente = grid_to_csv(frente_grid)

    # --- GENERAR POLILÍNEAS DE COLISIÓN BOX2D (ChainShape continua sin aristas cuadradas) ---
    def compute_sector_polyline(start_col, end_col):
        pts = []
        for x in range(start_col, end_col + 1):
            row, tile_id = floor_profile[x]
            left_px = x * tile_size
            right_px = (x + 1) * tile_size

            if tile_id == 1: # Arena plana: superficie a 14 px del borde superior del tile
                surface_y = row * tile_size + 14
                pts.append((left_px, surface_y))
                if x == end_col:
                    pts.append((right_px, surface_y))
            elif tile_id == 13: # Pendiente descendente: de 14 px a 50 px
                y_start = row * tile_size + 14
                y_end = row * tile_size + 50
                pts.append((left_px, y_start))
                pts.append((right_px, y_end))
            elif tile_id == 14: # Pendiente ascendente: de 50 px a 14 px
                y_start = row * tile_size + 50
                y_end = row * tile_size + 14
                pts.append((left_px, y_start))
                pts.append((right_px, y_end))
            elif tile_id == 5: # Muro rocoso vertical
                if x == 35:
                    pts.append((35 * tile_size, 26 * tile_size + 14))
                    pts.append((35 * tile_size, 33 * tile_size + 14))
                elif x == 90:
                    pts.append((90 * tile_size, 29 * tile_size + 14))
                    pts.append((90 * tile_size, 33 * tile_size + 14))
                else:
                    pts.append((left_px, row * tile_size + 14))

        # Compactar puntos redundantes colineales
        compact = []
        for pt in pts:
            if not compact or compact[-1] != pt:
                compact.append(pt)
        return " ".join(f"{px},{py}" for px, py in compact)

    # Dividimos en 4 sectores continuos con solapamiento de 1 punto para máxima solidez en Box2D
    pts_s1 = compute_sector_polyline(0, 36)
    pts_s2 = compute_sector_polyline(36, 71)
    pts_s3 = compute_sector_polyline(71, 104)
    pts_s4 = compute_sector_polyline(104, 149)

    tmx_content = f"""<?xml version="1.0" encoding="UTF-8"?>
<map version="1.10" tiledversion="1.10.2" orientation="orthogonal" renderorder="right-down" width="{width}" height="{height}" tilewidth="{tile_size}" tileheight="{tile_size}" infinite="0" nextlayerid="8" nextobjectid="160">
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
  <!-- Límites perimetrales del océano abierto -->
  <object id="1" name="TechoSuperficie" x="0" y="0" width="{total_px_width}" height="64"/>
  <object id="2" name="ParedIzquierda" x="0" y="0" width="64" height="{total_px_height}"/>
  <object id="3" name="ParedDerecha" x="{total_px_width - 64}" y="0" width="64" height="{total_px_height}"/>

  <!-- Relieve orgánico continuo del lecho marino con arena real y precipicios de Little Nemo -->
  <!-- SECTOR 1: Arrecife Somero y Gran Precipicio (x: 0 a 2304) -->
  <object id="10" name="LechoMarino_Sector1" x="0" y="0">
   <polyline points="{pts_s1}"/>
  </object>
  <!-- SECTOR 2: Cañón Abisal y Fosa de Perlas (x: 2304 a 4544) -->
  <object id="11" name="LechoMarino_Sector2" x="0" y="0">
   <polyline points="{pts_s2}"/>
  </object>
  <!-- SECTOR 3: Arrecife Crepuscular y Fosa del Tiburón (x: 4544 a 6656) -->
  <object id="12" name="LechoMarino_Sector3" x="0" y="0">
   <polyline points="{pts_s3}"/>
  </object>
  <!-- SECTOR 4: Santuario de Mar Abierto y Ballena Azul (x: 6656 a 9600) -->
  <object id="13" name="LechoMarino_Sector4" x="0" y="0">
   <polyline points="{pts_s4}"/>
  </object>
 </objectgroup>

 <objectgroup id="5" name="corrientes">
  <object id="20" name="CorrienteAguasAbiertas" x="1800" y="400" width="1400" height="500">
   <properties>
    <property name="forceX" type="float" value="14.0"/>
    <property name="forceY" type="float" value="0.0"/>
   </properties>
  </object>
  <!-- Géiser hidrotermal en el cañón que eleva al pez hacia la superficie -->
  <object id="21" name="GeiserSubmarino" x="2880" y="900" width="320" height="1200">
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
  <!-- Spawn Jugador en aguas cristalinas sobre la duna dorada inicial -->
  <object id="30" name="SpawnJugador" x="350" y="1500" width="32" height="32"/>

  <!-- Algas enraizadas en el lecho arenoso -->
  <object name="Algas" x="180" y="1664" width="96" height="64"/>
  <object name="Algas" x="520" y="1664" width="96" height="64"/>
  <object name="Algas" x="920" y="1600" width="96" height="64"/>
  <object name="Algas" x="1350" y="1664" width="96" height="64"/>
  <object name="Algas" x="1800" y="1728" width="96" height="64"/>
  <object name="Algas" x="2600" y="2048" width="96" height="64"/>
  <object name="Algas" x="3300" y="2048" width="96" height="64"/>
  <object name="Algas" x="4150" y="1984" width="96" height="64"/>
  <object name="Algas" x="4800" y="1856" width="96" height="64"/>
  <object name="Algas" x="5450" y="1792" width="96" height="64"/>
  <object name="Algas" x="6200" y="2048" width="96" height="64"/>
  <object name="Algas" x="7100" y="1920" width="96" height="64"/>
  <object name="Algas" x="8000" y="1984" width="96" height="64"/>
  <object name="Algas" x="8950" y="1920" width="96" height="64"/>

  <!-- Arcos de Perlas marinas para guiar la exploración fluida -->
  <!-- Zona 1: Dunas de Arena Dorada -->
  <object name="Perla" x="500" y="1450" width="24" height="24"/>
  <object name="Perla" x="700" y="1380" width="24" height="24"/>
  <object name="Perla" x="900" y="1450" width="24" height="24"/>
  <object name="Perla" x="1200" y="1400" width="24" height="24"/>
  <object name="Perla" x="1500" y="1350" width="24" height="24"/>
  <object name="Perla" x="1850" y="1420" width="24" height="24"/>
  <!-- Arco de descenso hacia el Gran Abismo de Little Nemo -->
  <object name="Perla" x="2150" y="1580" width="24" height="24"/>
  <object name="Perla" x="2260" y="1720" width="24" height="24"/>
  <object name="Perla" x="2360" y="1900" width="24" height="24"/>
  <!-- Zona 2: En el fondo del Cañón de las Conchas -->
  <object name="Perla" x="2700" y="1950" width="24" height="24"/>
  <object name="Perla" x="3150" y="1900" width="24" height="24"/>
  <object name="Perla" x="3600" y="1950" width="24" height="24"/>
  <object name="Perla" x="4050" y="1880" width="24" height="24"/>
  <!-- Zona 3: Arrecife de Coral y Fosa del Tiburón -->
  <object name="Perla" x="4900" y="1650" width="24" height="24"/>
  <object name="Perla" x="5250" y="1580" width="24" height="24"/>
  <object name="Perla" x="5600" y="1650" width="24" height="24"/>
  <object name="Perla" x="6050" y="1900" width="24" height="24"/>
  <!-- Zona 4: Santuario de Mar Abierto -->
  <object name="Perla" x="6800" y="1500" width="24" height="24"/>
  <object name="Perla" x="7200" y="1380" width="24" height="24"/>
  <object name="Perla" x="7600" y="1320" width="24" height="24"/>
  <object name="Perla" x="8050" y="1380" width="24" height="24"/>
  <object name="Perla" x="8500" y="1320" width="24" height="24"/>
  <object name="Perla" x="8950" y="1380" width="24" height="24"/>
  <object name="Perla" x="9300" y="1450" width="24" height="24"/>

  <!-- Burbujas de oxígeno y vitalidad marina -->
  <object name="Burbuja" x="950" y="1500" width="28" height="28"/>
  <object name="Burbuja" x="2800" y="1850" width="28" height="28"/>
  <object name="Burbuja" x="4350" y="1650" width="28" height="28"/>
  <object name="Burbuja" x="5800" y="1850" width="28" height="28"/>
  <object name="Burbuja" x="7850" y="1500" width="28" height="28"/>

  <!-- Power-Ups submarinos (Turbo Dash, Escudo Burbuja, Sonar Abisal) -->
  <object name="PowerUp_Turbo" x="1100" y="1350" width="36" height="36"/>
  <object name="PowerUp_Escudo" x="2500" y="1800" width="36" height="36"/>
  <object name="PowerUp_Linterna" x="4750" y="1600" width="36" height="36"/>
  <object name="PowerUp_Escudo" x="6900" y="1450" width="36" height="36"/>
  <object name="PowerUp_Turbo" x="8400" y="1350" width="36" height="36"/>

  <!-- FAUNA MARINA VIVA (Comportamiento autónomo, nado libre 360°, cero bombas) -->
  <!-- SECTOR 1: Arrecife Somero y Dunas Doradas -->
  <object name="CardumenSirenita" x="900" y="1420" width="120" height="80"/>
  <object name="PezAngel" x="650" y="1550" width="64" height="32"/>
  <object name="CardumenKrill" x="800" y="1450" width="48" height="48"/>
  <object name="PezAngel" x="1200" y="1500" width="64" height="32"/>
  <object name="TortugaMarina" x="1500" y="1380" width="96" height="72"/>
  <object name="CardumenSirenita" x="1700" y="1320" width="120" height="80"/>
  <object name="PezGloboAmarillo" x="1800" y="1520" width="64" height="32"/>

  <!-- SECTOR 2: El Gran Abismo y Cañón de las Conchas de Little Nemo -->
  <!-- Erizos pegados en el muro vertical del precipicio -->
  <object name="ErizoMarino" x="2210" y="1850" width="40" height="40"/>
  <object name="OstraGigante" x="2600" y="2048" width="64" height="64"/>
  <object name="CardumenKrill" x="2900" y="1750" width="48" height="48"/>
  <object name="CardumenSirenita" x="3200" y="1550" width="120" height="80"/>
  <object name="OstraGigante" x="3500" y="2048" width="64" height="64"/>
  <object name="ErizoMarino" x="3800" y="2048" width="40" height="40"/>

  <!-- SECTOR 3: Arrecife Crepuscular y Fosa del Tiburón -->
  <object name="TortugaMarina" x="4200" y="1500" width="96" height="72"/>
  <object name="CardumenSirenita" x="4500" y="1380" width="120" height="80"/>
  <object name="PezGloboNaranja" x="4650" y="1550" width="96" height="64"/>
  <object name="OstraGigante" x="5100" y="1856" width="64" height="64"/>
  <!-- Tiburón cazador en la fosa profunda -->
  <object name="ErizoMarino" x="5700" y="1850" width="40" height="40"/>
  <object name="TiburonAzul" x="5900" y="1950" width="192" height="192"/>
  <object name="OstraGigante" x="6100" y="2048" width="64" height="64"/>
  <object name="Pulpo" x="6250" y="1920" width="48" height="96"/>
  <object name="CardumenKrill" x="6400" y="1700" width="48" height="48"/>

  <!-- SECTOR 4: Santuario de Mar Abierto y Ballena Azul Colosal -->
  <object name="PezAngel" x="6700" y="1450" width="64" height="32"/>
  <object name="CardumenSirenita" x="6900" y="1250" width="120" height="80"/>
  <object name="TortugaMarina" x="7150" y="1350" width="96" height="72"/>
  <!-- Ballena majestuosa navegando en aguas abiertas -->
  <object name="Ballena" x="7500" y="850" width="240" height="120"/>
  <object name="CardumenKrill" x="7850" y="1400" width="48" height="48"/>
  <object name="OstraGigante" x="8150" y="1984" width="64" height="64"/>
  <object name="CardumenSirenita" x="8400" y="1200" width="120" height="80"/>
  <object name="PezGloboAmarillo" x="8550" y="1500" width="64" height="32"/>
  <object name="Ballena" x="8850" y="980" width="240" height="120"/>
  <object name="CardumenSirenita" x="9100" y="1150" width="120" height="80"/>
  <object name="CardumenKrill" x="9250" y="1380" width="48" height="48"/>
  <object name="OstraGigante" x="9400" y="1984" width="64" height="64"/>
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
  <object name="LuzFosaTiburon" x="5900" y="1900">
   <properties>
    <property name="color" value="#2277bb"/>
    <property name="distancia" type="float" value="8.5"/>
   </properties>
  </object>
  <object name="LuzSantuarioBallena" x="8300" y="1200">
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
    print(f"Mapa TMX con arena real, pendientes y precipicios de Little Nemo generado exitosamente en: {output_path} ({width}x{height} tiles)")

if __name__ == "__main__":
    generate_tmx()
