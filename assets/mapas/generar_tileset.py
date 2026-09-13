#!/usr/bin/env python3
"""
Script para generar el tileset submarino para Tiled en Pez-De-Pecera.
Dimensiones: 256x256 píxeles (16 tiles de 64x64, 4 columnas x 4 filas).
Temática submarina: arena, lecho marino, roca de cueva profunda, techo con estalactitas,
paredes rocosas, fondo marino, corales azul y rosa, algas oscuras, anémona bioluminiscente,
ruinas sumergidas talladas, pilares ceremoniales, cristales luminosos y respiraderos de burbujas.
"""

import math
import random
from PIL import Image, ImageDraw, ImageFilter

def create_tileset():
    # Fijamos semilla para reproducibilidad estética
    random.seed(42)

    tile_size = 64
    cols = 4
    rows = 4
    img_width = cols * tile_size
    img_height = rows * tile_size

    # Imagen completa en RGBA
    atlas = Image.new("RGBA", (img_width, img_height), (0, 0, 0, 0))

    tiles = {}

    # -------------------------------------------------------------
    # Helper: Tile 0 (0,0) - Suelo de arena superficial / lecho marino
    # -------------------------------------------------------------
    def draw_sand_top():
        t = Image.new("RGBA", (64, 64), (0, 0, 0, 0))
        d = ImageDraw.Draw(t)
        # Ondulaciones superficiales en la parte superior
        for y in range(64):
            for x in range(64):
                wave = math.sin(x * 0.15) * 2.5 + math.cos(x * 0.3) * 1.5
                surface_y = 12 + wave
                if y >= surface_y:
                    # Gradiente de arena
                    depth_ratio = min(1.0, (y - surface_y) / 50.0)
                    r = int(212 * (1.0 - 0.15 * depth_ratio))
                    g = int(170 * (1.0 - 0.18 * depth_ratio))
                    b = int(95 * (1.0 - 0.25 * depth_ratio))
                    
                    # Ruido fino de granos de arena
                    noise = random.randint(-10, 10)
                    r = max(0, min(255, r + noise))
                    g = max(0, min(255, g + noise))
                    b = max(0, min(255, b + noise))

                    # Líneas de sedimentación onduladas
                    layer_mod = math.sin(y * 0.5 + math.sin(x * 0.1) * 3)
                    if abs(layer_mod) > 0.85:
                        r = max(0, r - 15)
                        g = max(0, g - 15)
                        b = max(0, b - 15)

                    # Borde superior iluminado por agua
                    if y == int(surface_y) or y == int(surface_y) + 1:
                        r = min(255, r + 35)
                        g = min(255, g + 35)
                        b = min(255, b + 25)

                    d.point((x, y), fill=(r, g, b, 255))
        
        # Pequeñas conchitas y guijarros
        for _ in range(5):
            px = random.randint(6, 58)
            py = random.randint(22, 58)
            d.ellipse([px, py, px + 2, py + 1], fill=(245, 230, 200, 240))
            d.point((px + 1, py + 2), fill=(130, 95, 45, 200))
        return t

    # -------------------------------------------------------------
    # Helper: Tile 1 (1,0) - Arena profunda / estrato sedimentario
    # -------------------------------------------------------------
    def draw_sand_deep():
        t = Image.new("RGBA", (64, 64), (165, 128, 68, 255))
        d = ImageDraw.Draw(t)
        for y in range(64):
            # Capas sedimentarias onduladas
            band = math.sin(y * 0.35 + math.sin(math.pi * y / 32)) * 14
            for x in range(64):
                n = random.randint(-8, 8)
                shade = math.sin(x * 0.1 + y * 0.2) * 8
                r = int(max(0, min(255, 175 + band + shade + n)))
                g = int(max(0, min(255, 135 + band * 0.85 + shade + n)))
                b = int(max(0, min(255, 75 + band * 0.6 + shade * 0.5 + n)))
                d.point((x, y), fill=(r, g, b, 255))
        
        # Vetas de minerales oscuros y fósiles
        for i in range(3):
            vy = 16 * i + 10
            for vx in range(0, 64, 2):
                offset = int(math.sin(vx * 0.15) * 3)
                d.point((vx, vy + offset), fill=(120, 90, 40, 230))
                d.point((vx, vy + offset + 1), fill=(90, 65, 25, 200))
        return t

    # -------------------------------------------------------------
    # Helper: Tile 2 (2,0) - Techo de cueva rocoso con estalactitas
    # -------------------------------------------------------------
    def draw_cave_ceiling():
        t = Image.new("RGBA", (64, 64), (0, 0, 0, 0))
        d = ImageDraw.Draw(t)
        # Techo sólido arriba, estalactitas colgando
        stalactites = [(10, 42), (24, 52), (38, 36), (52, 48)]
        
        for y in range(64):
            for x in range(64):
                # Calcular perfil inferior del techo
                base_depth = 18 + math.sin(x * 0.2) * 4
                is_rock = (y <= base_depth)
                
                # Revisar estalactitas
                for sx, max_len in stalactites:
                    dist_x = abs(x - sx)
                    if dist_x < 7:
                        stal_len = max_len * (1.0 - (dist_x / 7.0) ** 1.5)
                        if y <= stal_len:
                            is_rock = True

                if is_rock:
                    # Textura rocosa oscura submarina
                    noise = random.randint(-12, 12)
                    crag = math.cos(x * 0.4 + y * 0.3) * 15
                    r = int(max(0, min(255, 34 + crag + noise)))
                    g = int(max(0, min(255, 48 + crag * 1.1 + noise)))
                    b = int(max(0, min(255, 68 + crag * 1.3 + noise)))
                    
                    # Borde inferior con sombreado de cueva
                    d.point((x, y), fill=(r, g, b, 255))
        
        # Gotas de agua / humedad azulada en puntas de estalactitas
        for sx, max_len in stalactites:
            d.ellipse([sx - 1, int(max_len) - 2, sx + 1, int(max_len) + 1], fill=(120, 200, 255, 230))
            d.point((sx, int(max_len) + 2), fill=(180, 230, 255, 180))
        return t

    # -------------------------------------------------------------
    # Helper: Tile 3 (3,0) - Roca de cueva profunda / roca sólida
    # -------------------------------------------------------------
    def draw_cave_rock_deep():
        t = Image.new("RGBA", (64, 64), (24, 34, 48, 255))
        d = ImageDraw.Draw(t)
        for y in range(64):
            for x in range(64):
                # Facetas hexagonales/rocosas
                facet1 = math.sin(x * 0.18 + y * 0.12) * 14
                facet2 = math.cos(x * 0.12 - y * 0.22) * 12
                noise = random.randint(-6, 6)
                r = int(max(0, min(255, 28 + facet1 + facet2 + noise)))
                g = int(max(0, min(255, 40 + facet1 * 1.1 + facet2 + noise)))
                b = int(max(0, min(255, 58 + facet1 * 1.3 + facet2 * 1.2 + noise)))
                d.point((x, y), fill=(r, g, b, 255))

        # Grietas tectónicas en la roca
        cracks = [
            [(8, 12), (20, 22), (32, 26), (44, 40), (56, 44)],
            [(40, 6), (36, 18), (28, 28), (18, 48), (14, 60)]
        ]
        for crack in cracks:
            d.line(crack, fill=(12, 18, 26, 255), width=2)
            # Resalte de borde de la grieta
            highlight = [(px + 1, py + 1) for px, py in crack]
            d.line(highlight, fill=(55, 75, 105, 160), width=1)
        return t

    # -------------------------------------------------------------
    # Helper: Tile 4 (0,1) - Pared izquierda de cueva rocosa
    # -------------------------------------------------------------
    def draw_cave_wall_left():
        t = Image.new("RGBA", (64, 64), (0, 0, 0, 0))
        d = ImageDraw.Draw(t)
        for y in range(64):
            wall_width = 46 + math.sin(y * 0.25) * 8 + math.cos(y * 0.5) * 4
            for x in range(64):
                if x <= wall_width:
                    dist_to_edge = wall_width - x
                    noise = random.randint(-8, 8)
                    shade = math.sin(x * 0.2 + y * 0.3) * 10
                    r = int(max(0, min(255, 26 + shade + noise)))
                    g = int(max(0, min(255, 38 + shade + noise)))
                    b = int(max(0, min(255, 54 + shade * 1.2 + noise)))
                    
                    # Borde rocoso exterior iluminado por agua
                    if dist_to_edge <= 3:
                        r = min(255, r + 25)
                        g = min(255, g + 35)
                        b = min(255, b + 50)
                    
                    d.point((x, y), fill=(r, g, b, 255))
        
        # Musgo marino verde-azulado incrustado
        for my in range(8, 56, 14):
            mx = int(32 + math.sin(my * 0.3) * 6)
            d.ellipse([mx - 4, my - 3, mx + 4, my + 3], fill=(25, 75, 60, 200))
        return t

    # -------------------------------------------------------------
    # Helper: Tile 5 (1,1) - Pared derecha de cueva rocosa
    # -------------------------------------------------------------
    def draw_cave_wall_right():
        t = Image.new("RGBA", (64, 64), (0, 0, 0, 0))
        d = ImageDraw.Draw(t)
        for y in range(64):
            wall_start = 18 + math.sin(y * 0.25) * 8 + math.cos(y * 0.5) * 4
            for x in range(64):
                if x >= wall_start:
                    dist_to_edge = x - wall_start
                    noise = random.randint(-8, 8)
                    shade = math.sin(x * 0.2 + y * 0.3) * 10
                    r = int(max(0, min(255, 26 + shade + noise)))
                    g = int(max(0, min(255, 38 + shade + noise)))
                    b = int(max(0, min(255, 54 + shade * 1.2 + noise)))
                    
                    if dist_to_edge <= 3:
                        r = min(255, r + 25)
                        g = min(255, g + 35)
                        b = min(255, b + 50)
                    
                    d.point((x, y), fill=(r, g, b, 255))
        
        for my in range(12, 58, 14):
            mx = int(28 + math.sin(my * 0.3) * 6)
            d.ellipse([mx - 4, my - 3, mx + 4, my + 3], fill=(25, 75, 60, 200))
        return t

    # -------------------------------------------------------------
    # Helper: Tile 6 (2,1) - Fondo de cueva submarina profunda
    # -------------------------------------------------------------
    def draw_cave_bg_dark():
        t = Image.new("RGBA", (64, 64), (10, 24, 40, 255))
        d = ImageDraw.Draw(t)
        for y in range(64):
            for x in range(64):
                # Ondas sutiles de agua profunda / caústicas tenues
                wave1 = math.sin((x + y) * 0.08) * 6
                wave2 = math.cos((x - y) * 0.12) * 5
                n = random.randint(-3, 3)
                r = int(max(0, min(255, 8 + wave1 + n)))
                g = int(max(0, min(255, 22 + wave1 + wave2 + n)))
                b = int(max(0, min(255, 38 + wave1 * 1.5 + wave2 + n)))
                d.point((x, y), fill=(r, g, b, 255))
        return t

    # -------------------------------------------------------------
    # Helper: Tile 7 (3,1) - Fondo de cueva bioluminiscente / vetas
    # -------------------------------------------------------------
    def draw_cave_bg_biolum():
        t = draw_cave_bg_dark()
        d = ImageDraw.Draw(t)
        # Vetas onduladas de energía y plancton brillante
        for vx in range(0, 64, 2):
            vy = int(32 + math.sin(vx * 0.12) * 12 + math.cos(vx * 0.25) * 4)
            d.point((vx, vy), fill=(0, 220, 235, 180))
            d.point((vx, vy - 1), fill=(50, 150, 255, 140))
            d.point((vx, vy + 1), fill=(50, 150, 255, 140))
        
        # Mota de plancton brillante
        plankton = [(12, 14), (28, 48), (44, 20), (54, 52), (36, 32)]
        for px, py in plankton:
            d.ellipse([px - 2, py - 2, px + 2, py + 2], fill=(0, 255, 230, 100))
            d.point((px, py), fill=(220, 255, 255, 240))
        return t

    # -------------------------------------------------------------
    # Helper: Tile 8 (0,2) - Coral rosa / púrpura ramificado
    # -------------------------------------------------------------
    def draw_coral_pink():
        t = Image.new("RGBA", (64, 64), (0, 0, 0, 0))
        d = ImageDraw.Draw(t)
        # Base rocosa pequeña abajo
        d.ellipse([14, 54, 50, 64], fill=(40, 48, 58, 255))
        
        # Ramas del coral rosa
        branches = [
            # Tallo central y ramificaciones
            [(32, 58), (32, 40), (24, 26), (18, 14)],
            [(32, 40), (40, 28), (46, 16)],
            [(24, 26), (28, 16), (26, 8)],
            [(40, 28), (36, 18), (38, 10)],
            [(32, 46), (16, 36), (12, 24)]
        ]
        # Dibujar ramas con grosor
        for b in branches:
            d.line(b, fill=(180, 40, 95, 255), width=5)
            d.line(b, fill=(235, 75, 145, 255), width=3)
            d.line([(px - 1, py) for px, py in b], fill=(255, 160, 205, 220), width=1)
            # Puntas bulbosas del coral
            tip = b[-1]
            d.ellipse([tip[0] - 3, tip[1] - 3, tip[0] + 3, tip[1] + 3], fill=(255, 130, 185, 255))
            d.ellipse([tip[0] - 1, tip[1] - 1, tip[0] + 1, tip[1] + 1], fill=(255, 225, 240, 255))
        return t

    # -------------------------------------------------------------
    # Helper: Tile 9 (1,2) - Coral azul / turquesa marino
    # -------------------------------------------------------------
    def draw_coral_blue():
        t = Image.new("RGBA", (64, 64), (0, 0, 0, 0))
        d = ImageDraw.Draw(t)
        # Base pequeña
        d.ellipse([16, 54, 48, 64], fill=(36, 44, 56, 255))
        
        branches = [
            [(32, 58), (32, 38), (38, 24), (46, 12)],
            [(32, 38), (22, 28), (14, 18)],
            [(22, 28), (26, 18), (22, 10)],
            [(38, 24), (32, 16), (34, 8)],
            [(32, 48), (46, 36), (52, 26)]
        ]
        for b in branches:
            d.line(b, fill=(15, 80, 160, 255), width=5)
            d.line(b, fill=(0, 175, 215, 255), width=3)
            d.line([(px - 1, py) for px, py in b], fill=(130, 235, 255, 220), width=1)
            tip = b[-1]
            d.ellipse([tip[0] - 3, tip[1] - 3, tip[0] + 3, tip[1] + 3], fill=(0, 210, 255, 255))
            d.ellipse([tip[0] - 1, tip[1] - 1, tip[0] + 1, tip[1] + 1], fill=(210, 255, 255, 255))
        return t

    # -------------------------------------------------------------
    # Helper: Tile 10 (2,2) - Algas marinas oscuras ondulantes
    # -------------------------------------------------------------
    def draw_dark_kelp():
        t = Image.new("RGBA", (64, 64), (0, 0, 0, 0))
        d = ImageDraw.Draw(t)
        # Fondo transparente, varias hebras de algas que suben
        stalks = [18, 32, 46]
        for idx, base_x in enumerate(stalks):
            curve = []
            phase = idx * 1.5
            for y in range(64, 2, -2):
                x = base_x + math.sin(y * 0.1 + phase) * 7
                curve.append((x, y))
            
            # Sombra y cuerpo principal de la hoja
            d.line(curve, fill=(12, 50, 36, 240), width=5)
            d.line(curve, fill=(22, 98, 68, 255), width=3)
            d.line([(px + 1, py) for px, py in curve], fill=(46, 170, 115, 220), width=1)
            
            # Folíolos / hojas laterales de alga
            for py in range(12, 54, 8):
                pt = curve[(64 - py) // 2]
                side = 1 if (py // 8) % 2 == 0 else -1
                leaf = [(pt[0], pt[1]), (pt[0] + side * 8, pt[1] - 4), (pt[0] + side * 4, pt[1] - 8)]
                d.polygon(leaf, fill=(24, 110, 75, 230))
        return t

    # -------------------------------------------------------------
    # Helper: Tile 11 (3,2) - Anémona bioluminiscente / flora marina
    # -------------------------------------------------------------
    def draw_anemone_gold():
        t = Image.new("RGBA", (64, 64), (0, 0, 0, 0))
        d = ImageDraw.Draw(t)
        # Base conica
        d.polygon([(24, 64), (40, 64), (36, 44), (28, 44)], fill=(160, 60, 15, 255))
        d.polygon([(26, 64), (38, 64), (34, 46), (30, 46)], fill=(210, 85, 25, 255))
        
        # Tentáculos radiantes que se abren hacia arriba
        tentacles = []
        for angle_deg in range(-75, 80, 15):
            rad = math.radians(angle_deg)
            t_pts = []
            for r in range(0, 34, 3):
                tx = 32 + math.sin(rad) * r + math.sin(r * 0.2) * 3
                ty = 44 - math.cos(rad) * r
                t_pts.append((tx, ty))
            tentacles.append(t_pts)
        
        for t_line in tentacles:
            d.line(t_line, fill=(210, 105, 10, 240), width=3)
            d.line(t_line, fill=(245, 165, 20, 255), width=2)
            d.line([(px, py) for px, py in t_line], fill=(255, 220, 70, 220), width=1)
            tip = t_line[-1]
            d.ellipse([tip[0] - 2, tip[1] - 2, tip[0] + 2, tip[1] + 2], fill=(255, 255, 140, 255))
        return t

    # -------------------------------------------------------------
    # Helper: Tile 12 (0,3) - Ruina sumergida / Bloque tallado
    # -------------------------------------------------------------
    def draw_sunken_brick():
        t = Image.new("RGBA", (64, 64), (55, 71, 79, 255))
        d = ImageDraw.Draw(t)
        # Biseles de piedra tallada
        d.rectangle([2, 2, 61, 61], outline=(38, 50, 56, 255), width=2)
        d.line([(3, 3), (60, 3)], fill=(120, 144, 156, 220), width=2)
        d.line([(3, 3), (3, 60)], fill=(120, 144, 156, 220), width=2)
        d.line([(3, 60), (60, 60)], fill=(25, 35, 40, 255), width=2)
        d.line([(60, 3), (60, 60)], fill=(25, 35, 40, 255), width=2)
        
        # Símbolo / glifo grabado antiguo en espiral
        spiral = []
        for a in range(0, 720, 20):
            r = 3 + a * 0.025
            rad = math.radians(a)
            sx = 32 + math.cos(rad) * r
            sy = 32 + math.sin(rad) * r
            spiral.append((sx, sy))
        d.line(spiral, fill=(30, 42, 48, 255), width=2)
        d.line([(px + 1, py + 1) for px, py in spiral], fill=(90, 115, 125, 180), width=1)

        # Musgo marino adherido
        for _ in range(4):
            mx = random.randint(8, 54)
            my = random.randint(8, 54)
            d.ellipse([mx, my, mx + 5, my + 3], fill=(35, 95, 65, 210))
        return t

    # -------------------------------------------------------------
    # Helper: Tile 13 (1,3) - Ruina sumergida / Pilar ceremonial
    # -------------------------------------------------------------
    def draw_sunken_pillar():
        t = Image.new("RGBA", (64, 64), (0, 0, 0, 0))
        d = ImageDraw.Draw(t)
        # Fuste de columna central (x: 14 a 50)
        d.rectangle([14, 0, 50, 63], fill=(69, 90, 100, 255))
        
        # Ranuras verticales / acanaladuras
        flutes = [20, 28, 36, 44]
        for fx in flutes:
            d.line([(fx, 0), (fx, 63)], fill=(38, 50, 56, 255), width=2)
            d.line([(fx + 2, 0), (fx + 2, 63)], fill=(120, 144, 156, 180), width=1)
        
        # Relieves horizontales en capitel y basa
        for hy in [0, 8, 55, 63]:
            d.line([(10, hy), (54, hy)], fill=(30, 40, 45, 255), width=2)
            d.line([(10, hy - 1 if hy > 0 else hy + 1), (54, hy - 1 if hy > 0 else hy + 1)], fill=(140, 165, 175, 200), width=1)
        
        # Fisuras por erosión acuática
        crack = [(24, 18), (28, 28), (22, 38), (26, 48)]
        d.line(crack, fill=(20, 30, 35, 255), width=2)
        return t

    # -------------------------------------------------------------
    # Helper: Tile 14 (2,3) - Cristales bioluminiscentes submarinos
    # -------------------------------------------------------------
    def draw_glowing_crystals():
        t = Image.new("RGBA", (64, 64), (0, 0, 0, 0))
        d = ImageDraw.Draw(t)
        # Base de roca oscura
        d.polygon([(10, 64), (54, 64), (48, 48), (16, 50)], fill=(32, 40, 52, 255))
        
        # Agujas de cristal prismático
        crystals = [
            # [(puntos prisma), color_sombra, color_medio, color_brillo]
            ([(32, 10), (25, 48), (32, 54), (39, 48)], (0, 120, 160), (0, 200, 235), (200, 255, 255)),
            ([(18, 22), (12, 52), (18, 56), (24, 50)], (10, 90, 140), (0, 170, 210), (180, 245, 255)),
            ([(46, 18), (39, 50), (46, 55), (52, 48)], (20, 100, 150), (0, 180, 225), (190, 250, 255)),
            ([(26, 28), (22, 50), (28, 54), (32, 48)], (60, 40, 120), (140, 90, 220), (230, 210, 255))
        ]
        
        for poly, c_dark, c_mid, c_high in crystals:
            # Cara izquierda sombreada
            d.polygon([poly[0], poly[1], poly[2]], fill=(*c_dark, 245))
            # Cara derecha brillante
            d.polygon([poly[0], poly[2], poly[3]], fill=(*c_mid, 245))
            # Línea de arista brillante
            d.line([poly[0], poly[2]], fill=(*c_high, 255), width=1)
            # Resplandor en la punta
            d.ellipse([poly[0][0] - 2, poly[0][1] - 2, poly[0][0] + 2, poly[0][1] + 2], fill=(255, 255, 255, 255))
        return t

    # -------------------------------------------------------------
    # Helper: Tile 15 (3,3) - Respiradero submarino y burbujas
    # -------------------------------------------------------------
    def draw_bubble_vent():
        t = Image.new("RGBA", (64, 64), (0, 0, 0, 0))
        d = ImageDraw.Draw(t)
        # Cráter de ventilación hidrotermal en la parte inferior
        d.polygon([(8, 64), (56, 64), (44, 46), (20, 46)], fill=(28, 36, 44, 255))
        d.ellipse([20, 44, 44, 50], fill=(12, 16, 22, 255)) # Boca del respiradero
        
        # Columna de burbujas que ascienden hacia arriba
        bubbles = [
            (32, 40, 4),
            (26, 30, 6),
            (38, 24, 7),
            (30, 14, 8),
            (42, 8, 5),
            (22, 6, 4)
        ]
        for bx, by, radius in bubbles:
            # Borde exterior de burbuja
            d.ellipse([bx - radius, by - radius, bx + radius, by + radius], 
                      outline=(120, 220, 255, 220), width=1, fill=(70, 180, 230, 70))
            # Destello especular de luz
            d.ellipse([bx - radius + 1, by - radius + 1, bx - radius + 3, by - radius + 3], 
                      fill=(255, 255, 255, 240))
        return t

    # Ensamblar los 16 tiles en el atlas
    tile_builders = [
        # Fila 0
        (0, 0, draw_sand_top),
        (1, 0, draw_sand_deep),
        (2, 0, draw_cave_ceiling),
        (3, 0, draw_cave_rock_deep),
        # Fila 1
        (0, 1, draw_cave_wall_left),
        (1, 1, draw_cave_wall_right),
        (2, 1, draw_cave_bg_dark),
        (3, 1, draw_cave_bg_biolum),
        # Fila 2
        (0, 2, draw_coral_pink),
        (1, 2, draw_coral_blue),
        (2, 2, draw_dark_kelp),
        (3, 2, draw_anemone_gold),
        # Fila 3
        (0, 3, draw_sunken_brick),
        (1, 3, draw_sunken_pillar),
        (2, 3, draw_glowing_crystals),
        (3, 3, draw_bubble_vent),
    ]

    for col, row, builder_fn in tile_builders:
        tile_img = builder_fn()
        pos_x = col * tile_size
        pos_y = row * tile_size
        atlas.paste(tile_img, (pos_x, pos_y), tile_img)

    output_path = "assets/mapas/tileset_submarino.png"
    atlas.save(output_path, "PNG")
    print(f"Tileset submarino generado exitosamente en: {output_path} ({img_width}x{img_height})")

if __name__ == "__main__":
    create_tileset()
