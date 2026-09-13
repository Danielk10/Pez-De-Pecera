#!/usr/bin/env python3
"""
Generador del primer nivel submarino completo (nivel1.tmx) para Pez-De-Pecera.
Dimensiones: 60 x 35 tiles de 64x64 píxeles (3840 x 2240 píxeles).
Incluye:
- Capas de tiles: fondo rocoso, terreno sólido (arrecifes, estalactitas, suelo) y primer plano de algas.
- Capa de colisiones Box2D (muros, techo, suelo, pilares y cavernas).
- Capa de corrientes de agua dinámicas (sensores de empuje hidrodinámico).
- Capa de spawns: Jugador, Coleccionables (Perlas), Salud (Burbujas de oxígeno),
  Power-ups (Turbo, Escudo, Linterna Abisal), Enemigos patrulleros y Minas.
- Capa de iluminación orgánica (RayHandler / PointLights bioluminiscentes).
"""

def generate_tmx():
    width = 60
    height = 35
    tile_size = 64

    # 1. Capa fondo_cueva (abierto y transparente para visibilidad total del fondo marino)
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

    # Arrecife de coral y flora marina en el fondo (fila 32 sobre la arena)
    corales_patron = [9, 10, 11, 12, 10, 15, 9, 11, 16, 12, 10, 9]
    for x in range(width):
        terreno_grid[32][x] = corales_patron[x % len(corales_patron)]

    # Estructura 1: Pilar ancestral sumergido con corales (x: 12-14, y: 22-31)
    for y in range(22, 32):
        for x in range(12, 15):
            terreno_grid[y][x] = 14 # Pilar sumergido
    terreno_grid[21][13] = 15 # Cristal luminoso sobre el pilar

    # Estructura 2: Estalactita descendente de cueva (x: 23-25, y: 2-9)
    for y in range(2, 9):
        for x in range(23, 26):
            terreno_grid[y][x] = 4 # Roca de cueva
    terreno_grid[9][24] = 3 # Punta estalactita

    # Estructura 3: Gran Arrecife de Coral central (x: 35-38, y: 24-31)
    for y in range(24, 32):
        for x in range(35, 39):
            terreno_grid[y][x] = 13 # Ruina sumergida coralina
    terreno_grid[23][35] = 9  # Coral rosa en la cima
    terreno_grid[23][36] = 10 # Coral azul
    terreno_grid[23][37] = 15 # Cristal luminoso
    terreno_grid[23][38] = 12 # Anémona dorada

    # Estructura 4: Formación rocosa colgante (x: 46-49, y: 2-10)
    for y in range(2, 10):
        for x in range(46, 50):
            terreno_grid[y][x] = 4
    terreno_grid[10][47] = 11 # Alga colgante
    terreno_grid[10][48] = 15 # Cristal

    # 3. Capa primer_plano (algas y corales decorativos en frente del pez)
    frente_grid = [[0 for _ in range(width)] for _ in range(height)]
    for x in range(3, width, 5):
        frente_grid[31][x] = 11 # Algas ondeantes en primer plano
    for x in range(6, width, 8):
        frente_grid[31][x] = 9  # Coral rosa en primer plano
    for x in range(10, width, 8):
        frente_grid[31][x] = 10 # Coral azul en primer plano

    def grid_to_csv(grid):
        lines = []
        for row in grid:
            lines.append(",".join(str(val) for val in row))
        return ",\n".join(lines)

    csv_fondo = grid_to_csv(fondo_grid)
    csv_terreno = grid_to_csv(terreno_grid)
    csv_frente = grid_to_csv(frente_grid)

    tmx_content = f"""<?xml version="1.0" encoding="UTF-8"?>
<map version="1.10" tiledversion="1.10.2" orientation="orthogonal" renderorder="right-down" width="{width}" height="{height}" tilewidth="{tile_size}" tileheight="{tile_size}" infinite="0" nextlayerid="8" nextobjectid="60">
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
  <!-- Perímetro del escenario submarino -->
  <object id="1" name="Techo" x="0" y="0" width="3840" height="128"/>
  <object id="2" name="Suelo" x="0" y="2112" width="3840" height="128"/>
  <object id="3" name="ParedIzquierda" x="0" y="0" width="64" height="2240"/>
  <object id="4" name="ParedDerecha" x="3776" y="0" width="64" height="2240"/>

  <!-- Formaciones geológicas / cavernas sólidas -->
  <object id="5" name="PilarAncestral" x="768" y="1152" width="192" height="960"/>
  <object id="6" name="EstalactitaDescendente" x="1408" y="128" width="256" height="512"/>
  <object id="7" name="ArrecifePetreo" x="2176" y="1408" width="320" height="704"/>
  <object id="8" name="BovedaAbisal" x="2944" y="640" width="320" height="768"/>
 </objectgroup>

 <objectgroup id="5" name="corrientes">
  <!-- Corriente 1: Túnel superior rápido hacia la derecha -->
  <object id="9" name="CorrienteTunel" x="640" y="250" width="800" height="350">
   <properties>
    <property name="forceX" type="float" value="14.0"/>
    <property name="forceY" type="float" value="0.0"/>
   </properties>
  </object>

  <!-- Corriente 2: Géiser submarino hidrotermal ascendente -->
  <object id="10" name="GeiserAscendente" x="1750" y="1300" width="350" height="750">
   <properties>
    <property name="forceX" type="float" value="0.0"/>
    <property name="forceY" type="float" value="16.0"/>
   </properties>
  </object>

  <!-- Corriente 3: Remolino de fosa profunda hacia la izquierda -->
  <object id="11" name="ContracorrienteFosa" x="2500" y="1250" width="650" height="450">
   <properties>
    <property name="forceX" type="float" value="-12.0"/>
    <property name="forceY" type="float" value="2.5"/>
   </properties>
  </object>
 </objectgroup>

 <objectgroup id="6" name="spawns">
  <!-- Spawn inicial del Pez Payaso -->
  <object id="12" name="SpawnJugador" x="200" y="1150" width="32" height="32"/>

  <!-- Coleccionables: Sendero de Perlas submarinas (+100 pts) -->
  <object id="20" name="Perla" x="350" y="1150" width="24" height="24"/>
  <object id="21" name="Perla" x="450" y="1100" width="24" height="24"/>
  <object id="22" name="Perla" x="550" y="1050" width="24" height="24"/>
  <object id="23" name="Perla" x="750" y="420" width="24" height="24"/>
  <object id="24" name="Perla" x="900" y="400" width="24" height="24"/>
  <object id="25" name="Perla" x="1050" y="380" width="24" height="24"/>
  <object id="26" name="Perla" x="1200" y="420" width="24" height="24"/>
  <object id="27" name="Perla" x="1550" y="1600" width="24" height="24"/>
  <object id="28" name="Perla" x="1700" y="1650" width="24" height="24"/>
  <object id="29" name="Perla" x="2350" y="1850" width="24" height="24"/>
  <object id="30" name="Perla" x="2500" y="1900" width="24" height="24"/>
  <object id="31" name="Perla" x="3300" y="900" width="24" height="24"/>
  <object id="32" name="Perla" x="3450" y="900" width="24" height="24"/>
  <object id="33" name="Perla" x="3600" y="950" width="24" height="24"/>

  <!-- Restauradores de Salud: Burbujas de Oxígeno (+1 Corazón) -->
  <object id="34" name="Burbuja" x="1050" y="1550" width="28" height="28"/>
  <object id="35" name="Burbuja" x="2250" y="1150" width="28" height="28"/>
  <object id="36" name="Burbuja" x="3100" y="1600" width="28" height="28"/>

  <!-- Power-Ups Submarinos -->
  <!-- 1. Turbo Propulsión antes de la corriente del túnel -->
  <object id="37" name="PowerUp_Turbo" x="550" y="420" width="36" height="36"/>
  <!-- 2. Escudo de Burbuja antes de la zona de minas y peces globo -->
  <object id="38" name="PowerUp_Escudo" x="1350" y="1150" width="36" height="36"/>
  <!-- 3. Linterna Abisal antes de adentrarse en la fosa profunda -->
  <object id="39" name="PowerUp_Linterna" x="2400" y="850" width="36" height="36"/>

  <!-- Fauna marina y peligros distribuidos estratégicamente -->
  <object id="40" name="PezAngel" x="850" y="750" width="64" height="32"/>
  <object id="41" name="PezAngel" x="1450" y="850" width="64" height="32"/>
  <object id="42" name="PezGloboAmarillo" x="1150" y="1250" width="64" height="32"/>
  <object id="43" name="PezGloboNaranja" x="2050" y="850" width="96" height="64"/>
  <object id="44" name="Bomba" x="1750" y="950" width="64" height="64"/>
  <object id="45" name="Bomba" x="2600" y="750" width="64" height="64"/>
  <object id="46" name="Pulpo" x="1600" y="1750" width="48" height="96"/>
  <object id="47" name="TiburonAzul" x="2750" y="1050" width="192" height="192"/>
  <object id="48" name="Algas" x="650" y="1950" width="96" height="64"/>
  <object id="49" name="Algas" x="2450" y="1950" width="96" height="64"/>
 </objectgroup>

 <objectgroup id="7" name="luces">
  <!-- Fuentes bioluminiscentes atmosféricas -->
  <object id="50" name="LuzArrecifeInicial" x="400" y="1100">
   <properties>
    <property name="color" value="#44ccff"/>
    <property name="distancia" type="float" value="6.5"/>
   </properties>
  </object>
  <object id="51" name="LuzTunelCorriente" x="1000" y="400">
   <properties>
    <property name="color" value="#ffaa33"/>
    <property name="distancia" type="float" value="8.0"/>
   </properties>
  </object>
  <object id="52" name="LuzGeiserTermal" x="1920" y="1650">
   <properties>
    <property name="color" value="#ff6622"/>
    <property name="distancia" type="float" value="9.0"/>
   </properties>
  </object>
  <object id="53" name="LuzCristalAbisal" x="3150" y="920">
   <properties>
    <property name="color" value="#bb44ff"/>
    <property name="distancia" type="float" value="9.5"/>
   </properties>
  </object>
  <object id="54" name="LuzFosaProfunda" x="2750" y="1850">
   <properties>
    <property name="color" value="#2288cc"/>
    <property name="distancia" type="float" value="7.5"/>
   </properties>
  </object>
 </objectgroup>
</map>
"""
    with open("assets/mapas/nivel1.tmx", "w", encoding="utf-8") as f:
        f.write(tmx_content)
    print("Mapa TMX enriquecido generado exitosamente en assets/mapas/nivel1.tmx")

if __name__ == "__main__":
    generate_tmx()
