#!/usr/bin/env python3
"""
Generador del primer nivel submarino completo (nivel1.tmx) para Pez-De-Pecera.
Dimensiones ampliadas: 150 x 35 tiles de 64x64 píxeles (9600 x 2240 píxeles).
Incluye:
- Capas de tiles: agua cristalina abierta (transparente), terreno sólido (arrecifes de coral, arena dorada, cavernas) y primer plano.
- Colisiones Box2D perimetrales y formaciones geológicas a lo largo de 4 zonas.
- Corrientes de agua dinámicas y géiseres térmicos.
- Spawns: Jugador, Coleccionables (Perlas), Salud (Burbujas de oxígeno), Power-ups (Turbo, Escudo, Linterna Abisal) y fauna hostil (Tiburones, Pulpos, Peces Globo, Minas).
- Iluminación bioluminiscente orgánica con Box2DLights.
"""

def generate_tmx():
    width = 150
    height = 35
    tile_size = 64

    # 1. Capa fondo_cueva (abierto y transparente para visibilidad del fondo marino)
    fondo_grid = [[0 for _ in range(width)] for _ in range(height)]
    for x in range(width):
        fondo_grid[0][x] = 7  # Sombra rocosa en el borde superior
        fondo_grid[34][x] = 7 # Sombra rocosa en el lecho abisal

    # 2. Capa terreno_solido (arrecifes de coral, arena y cavernas físicas)
    terreno_grid = [[0 for _ in range(width)] for _ in range(height)]
    
    # Techo de cueva (filas 0 y 1)
    for x in range(width):
        terreno_grid[0][x] = 4 # Roca sólida
        terreno_grid[1][x] = 3 # Techo con estalactitas

    # Suelo marino arenoso (filas 33 y 34)
    for x in range(width):
        terreno_grid[33][x] = 1 # Arena marina dorada
        terreno_grid[34][x] = 2 # Lecho rocoso profundo

    # Arrecife de coral y flora marina viva en el fondo (fila 32 sobre la arena)
    corales_patron = [9, 10, 11, 12, 10, 15, 9, 11, 16, 12, 10, 9]
    for x in range(width):
        terreno_grid[32][x] = corales_patron[x % len(corales_patron)]

    # Estructuras Zona 1: Arrecife Inicial (0-40)
    # Estructura 1: Pilar ancestral (x: 12-14, y: 22-31)
    for y in range(22, 32):
        for x in range(12, 15):
            terreno_grid[y][x] = 14
    terreno_grid[21][13] = 15

    # Estructura 2: Estalactita descendente (x: 23-25, y: 2-9)
    for y in range(2, 9):
        for x in range(23, 26):
            terreno_grid[y][x] = 4
    terreno_grid[9][24] = 3

    # Estructura 3: Arrecife de Coral central (x: 35-38, y: 24-31)
    for y in range(24, 32):
        for x in range(35, 39):
            terreno_grid[y][x] = 13
    terreno_grid[23][35] = 9
    terreno_grid[23][36] = 10
    terreno_grid[23][37] = 15
    terreno_grid[23][38] = 12

    # Estructuras Zona 2: Caverna y Corrientes (40-80)
    # Estructura 4: Formación rocosa colgante (x: 48-51, y: 2-11)
    for y in range(2, 11):
        for x in range(48, 52):
            terreno_grid[y][x] = 4
    terreno_grid[11][49] = 11
    terreno_grid[11][50] = 15

    # Estructura 5: Gran arco submarino (x: 62-66, y: 21-31)
    for y in range(21, 32):
        for x in range(62, 67):
            terreno_grid[y][x] = 13
    terreno_grid[20][63] = 9
    terreno_grid[20][65] = 10

    # Estructura 6: Estalactitas profundas (x: 74-77, y: 2-10)
    for y in range(2, 10):
        for x in range(74, 78):
            terreno_grid[y][x] = 4
    terreno_grid[10][75] = 3

    # Estructuras Zona 3: Ruinas del Kraken (80-120)
    # Estructura 7: Columnas sumergidas ancestrales (x: 88-91, y: 20-31)
    for y in range(20, 32):
        for x in range(88, 92):
            terreno_grid[y][x] = 14
    terreno_grid[19][89] = 15
    terreno_grid[19][90] = 12

    # Estructura 8: Formación de cueva media (x: 102-106, y: 6-16)
    for y in range(6, 17):
        for x in range(102, 107):
            terreno_grid[y][x] = 4
    terreno_grid[17][104] = 11

    # Estructuras Zona 4: El Gran Abismo y Santuario (120-150)
    # Estructura 9: Arrecife de corales gigantes (x: 118-122, y: 22-31)
    for y in range(22, 32):
        for x in range(118, 123):
            terreno_grid[y][x] = 13
    terreno_grid[21][119] = 9
    terreno_grid[21][120] = 10
    terreno_grid[21][121] = 15

    # Estructura 10: Bóveda del Santuario Final (x: 135-139, y: 2-12)
    for y in range(2, 13):
        for x in range(135, 140):
            terreno_grid[y][x] = 4
    terreno_grid[13][137] = 15

    # Estructura 11: Pedestal de meta (x: 144-147, y: 25-31)
    for y in range(25, 32):
        for x in range(144, 148):
            terreno_grid[y][x] = 14
    terreno_grid[24][145] = 15
    terreno_grid[24][146] = 12

    # 3. Capa primer_plano (algas y corales decorativos frente al pez)
    frente_grid = [[0 for _ in range(width)] for _ in range(height)]
    for x in range(3, width, 5):
        frente_grid[31][x] = 11
    for x in range(6, width, 8):
        frente_grid[31][x] = 9
    for x in range(10, width, 8):
        frente_grid[31][x] = 10

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
  <!-- Perímetro del escenario submarino ampliado (9600 x 2240) -->
  <object id="1" name="Techo" x="0" y="0" width="{total_px_width}" height="128"/>
  <object id="2" name="Suelo" x="0" y="2112" width="{total_px_width}" height="128"/>
  <object id="3" name="ParedIzquierda" x="0" y="0" width="64" height="{total_px_height}"/>
  <object id="4" name="ParedDerecha" x="{total_px_width - 64}" y="0" width="64" height="{total_px_height}"/>

  <!-- Formaciones geológicas sólidas -->
  <object id="5" name="Pilar1" x="768" y="1408" width="192" height="704"/>
  <object id="6" name="Estalactita1" x="1472" y="128" width="192" height="512"/>
  <object id="7" name="Arrecife1" x="2240" y="1536" width="256" height="576"/>
  <object id="8" name="Colgante1" x="3072" y="128" width="256" height="640"/>
  <object id="9" name="ArcoMarino" x="3968" y="1344" width="320" height="768"/>
  <object id="10" name="Estalactita2" x="4736" y="128" width="256" height="576"/>
  <object id="11" name="RuinasAncestrales" x="5632" y="1280" width="256" height="832"/>
  <object id="12" name="CuevaMedia" x="6528" y="384" width="320" height="704"/>
  <object id="13" name="ArrecifeGigante" x="7552" y="1408" width="320" height="704"/>
  <object id="14" name="SantuarioBoveda" x="8640" y="128" width="320" height="704"/>
  <object id="15" name="PedestalFinal" x="9216" y="1600" width="256" height="512"/>
 </objectgroup>

 <objectgroup id="5" name="corrientes">
  <object id="20" name="CorrienteTunel1" x="1600" y="350" width="900" height="350">
   <properties>
    <property name="forceX" type="float" value="18.0"/>
    <property name="forceY" type="float" value="0.0"/>
   </properties>
  </object>
  <object id="21" name="GeiserAscendente1" x="3450" y="1250" width="400" height="850">
   <properties>
    <property name="forceX" type="float" value="0.0"/>
    <property name="forceY" type="float" value="20.0"/>
   </properties>
  </object>
  <object id="22" name="CorrienteFosa" x="5100" y="1100" width="1000" height="450">
   <properties>
    <property name="forceX" type="float" value="-15.0"/>
    <property name="forceY" type="float" value="3.0"/>
   </properties>
  </object>
  <object id="23" name="GeiserAscendente2" x="7100" y="1200" width="400" height="900">
   <properties>
    <property name="forceX" type="float" value="0.0"/>
    <property name="forceY" type="float" value="22.0"/>
   </properties>
  </object>
 </objectgroup>

 <objectgroup id="6" name="spawns">
  <!-- Spawn Jugador -->
  <object id="30" name="SpawnJugador" x="200" y="1150" width="32" height="32"/>

  <!-- Perlas coleccionables (+100 pts) distribuidas por todo el recorrido -->
  <!-- Zona 1 -->
  <object id="31" name="Perla" x="350" y="1150" width="24" height="24"/>
  <object id="32" name="Perla" x="450" y="1100" width="24" height="24"/>
  <object id="33" name="Perla" x="550" y="1050" width="24" height="24"/>
  <object id="34" name="Perla" x="750" y="550" width="24" height="24"/>
  <object id="35" name="Perla" x="900" y="500" width="24" height="24"/>
  <object id="36" name="Perla" x="1050" y="550" width="24" height="24"/>
  <object id="37" name="Perla" x="1200" y="600" width="24" height="24"/>
  <object id="38" name="Perla" x="1400" y="1000" width="24" height="24"/>
  <object id="39" name="Perla" x="1800" y="1200" width="24" height="24"/>
  <object id="40" name="Perla" x="2000" y="1250" width="24" height="24"/>
  <!-- Zona 2 -->
  <object id="41" name="Perla" x="2600" y="700" width="24" height="24"/>
  <object id="42" name="Perla" x="2750" y="700" width="24" height="24"/>
  <object id="43" name="Perla" x="2900" y="750" width="24" height="24"/>
  <object id="44" name="Perla" x="3300" y="1500" width="24" height="24"/>
  <object id="45" name="Perla" x="3500" y="1450" width="24" height="24"/>
  <object id="46" name="Perla" x="3700" y="1500" width="24" height="24"/>
  <object id="47" name="Perla" x="4300" y="800" width="24" height="24"/>
  <object id="48" name="Perla" x="4450" y="750" width="24" height="24"/>
  <object id="49" name="Perla" x="4600" y="800" width="24" height="24"/>
  <!-- Zona 3 -->
  <object id="50" name="Perla" x="5400" y="650" width="24" height="24"/>
  <object id="51" name="Perla" x="5550" y="600" width="24" height="24"/>
  <object id="52" name="Perla" x="5700" y="650" width="24" height="24"/>
  <object id="53" name="Perla" x="6100" y="1300" width="24" height="24"/>
  <object id="54" name="Perla" x="6250" y="1350" width="24" height="24"/>
  <object id="55" name="Perla" x="6400" y="1300" width="24" height="24"/>
  <object id="56" name="Perla" x="6900" y="900" width="24" height="24"/>
  <object id="57" name="Perla" x="7050" y="850" width="24" height="24"/>
  <!-- Zona 4 -->
  <object id="58" name="Perla" x="7900" y="1000" width="24" height="24"/>
  <object id="59" name="Perla" x="8050" y="950" width="24" height="24"/>
  <object id="60" name="Perla" x="8200" y="900" width="24" height="24"/>
  <object id="61" name="Perla" x="8400" y="1100" width="24" height="24"/>
  <object id="62" name="Perla" x="8700" y="1300" width="24" height="24"/>
  <object id="63" name="Perla" x="8900" y="1350" width="24" height="24"/>
  <object id="64" name="Perla" x="9100" y="1400" width="24" height="24"/>
  <object id="65" name="Perla" x="9300" y="1350" width="24" height="24"/>

  <!-- Restauradores de Salud (Burbujas de Oxígeno) -->
  <object id="70" name="Burbuja" x="1100" y="1400" width="28" height="28"/>
  <object id="71" name="Burbuja" x="2700" y="1100" width="28" height="28"/>
  <object id="72" name="Burbuja" x="4500" y="1400" width="28" height="28"/>
  <object id="73" name="Burbuja" x="6300" y="800" width="28" height="28"/>
  <object id="74" name="Burbuja" x="8100" y="1300" width="28" height="28"/>

  <!-- Power-Ups Submarinos -->
  <object id="75" name="PowerUp_Turbo" x="850" y="450" width="36" height="36"/>
  <object id="76" name="PowerUp_Escudo" x="2100" y="1050" width="36" height="36"/>
  <object id="77" name="PowerUp_Turbo" x="3800" y="650" width="36" height="36"/>
  <object id="78" name="PowerUp_Linterna" x="5200" y="850" width="36" height="36"/>
  <object id="79" name="PowerUp_Escudo" x="7200" y="1050" width="36" height="36"/>
  <object id="80" name="PowerUp_Turbo" x="8500" y="900" width="36" height="36"/>

  <!-- Fauna Hostil y Peligros -->
  <!-- Zona 1 -->
  <object id="81" name="PezAngel" x="950" y="800" width="64" height="32"/>
  <object id="82" name="PezAngel" x="1500" y="950" width="64" height="32"/>
  <object id="83" name="PezGloboAmarillo" x="1250" y="1300" width="64" height="32"/>
  <object id="84" name="Bomba" x="1750" y="1050" width="64" height="64"/>
  <!-- Zona 2 -->
  <object id="85" name="PezGloboNaranja" x="2500" y="850" width="96" height="64"/>
  <object id="86" name="TiburonAzul" x="3100" y="1100" width="192" height="192"/>
  <object id="87" name="Bomba" x="3600" y="750" width="64" height="64"/>
  <object id="88" name="Pulpo" x="4100" y="1400" width="48" height="96"/>
  <object id="89" name="PezAngel" x="4400" y="600" width="64" height="32"/>
  <!-- Zona 3 -->
  <object id="90" name="PezGloboAmarillo" x="5000" y="1250" width="64" height="32"/>
  <object id="91" name="TiburonAzul" x="5800" y="950" width="192" height="192"/>
  <object id="92" name="Pulpo" x="6200" y="1500" width="48" height="96"/>
  <object id="93" name="Bomba" x="6600" y="900" width="64" height="64"/>
  <object id="94" name="PezGloboNaranja" x="7000" y="700" width="96" height="64"/>
  <!-- Zona 4 -->
  <object id="95" name="Pulpo" x="7800" y="1450" width="48" height="96"/>
  <object id="96" name="TiburonAzul" x="8400" y="1050" width="192" height="192"/>
  <object id="97" name="Bomba" x="8800" y="850" width="64" height="64"/>
  <object id="98" name="TiburonAzul" x="9100" y="1200" width="192" height="192"/>
 </objectgroup>

 <objectgroup id="7" name="luces">
  <!-- Iluminación bioluminiscente de cavernas y ruinas -->
  <object id="100" name="LuzArrecife" x="400" y="1100">
   <properties>
    <property name="color" value="#44ccff"/>
    <property name="distancia" type="float" value="6.0"/>
   </properties>
  </object>
  <object id="101" name="LuzGeiser1" x="3500" y="1400">
   <properties>
    <property name="color" value="#ff7722"/>
    <property name="distancia" type="float" value="8.0"/>
   </properties>
  </object>
  <object id="102" name="LuzArco" x="4100" y="1200">
   <properties>
    <property name="color" value="#22aaff"/>
    <property name="distancia" type="float" value="7.5"/>
   </properties>
  </object>
  <object id="103" name="LuzRuinas" x="5750" y="1100">
   <properties>
    <property name="color" value="#bb44ff"/>
    <property name="distancia" type="float" value="8.5"/>
   </properties>
  </object>
  <object id="104" name="LuzGeiser2" x="7250" y="1350">
   <properties>
    <property name="color" value="#ff5511"/>
    <property name="distancia" type="float" value="8.0"/>
   </properties>
  </object>
  <object id="105" name="LuzSantuario" x="9250" y="1400">
   <properties>
    <property name="color" value="#ffd700"/>
    <property name="distancia" type="float" value="9.5"/>
   </properties>
  </object>
 </objectgroup>
</map>
"""
    with open("assets/mapas/nivel1.tmx", "w", encoding="utf-8") as f:
        f.write(tmx_content)
    print(f"Mapa TMX de 150x35 ({total_px_width}x{total_px_height} px) generado exitosamente en assets/mapas/nivel1.tmx")

if __name__ == "__main__":
    generate_tmx()
