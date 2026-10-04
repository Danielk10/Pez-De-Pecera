#!/usr/bin/env python3
"""
Generador de sprites y atlases marinos para Pez-De-Pecera.
Crea texturas de alta calidad para:
1. Ballena Azul (ballena.png / ballena.atlas) - 4 frames de nado elegante
2. Ostra Gigante con Perla (ostra.png / ostra.atlas) - 3 frames (cerrada, abriendo, abierta)
3. Tortuga Marina (tortuga.png / tortuga.atlas) - 4 frames de aleteo
4. Krill Luminiscente (krill.png)
5. Erizo de Mar (erizo.png)
"""
import math
import os
from PIL import Image, ImageDraw, ImageFilter

def create_whale_atlas():
    # 4 frames de 384x192 cada uno (total 768x384 en 2x2)
    fw, fh = 384, 192
    sheet = Image.new("RGBA", (fw * 2, fh * 2), (0, 0, 0, 0))
    frames = []

    for i in range(4):
        img = Image.new("RGBA", (fw, fh), (0, 0, 0, 0))
        d = ImageDraw.Draw(img)
        t = i * (math.pi / 2.0)

        # Cuerpo hidrodinámico de ballena azul (curvas bezier/polígono)
        # Cabeza redondeada a la izquierda (x: 20-100), vientre y lomo hasta cola (x: 340)
        fluke_angle = math.sin(t) * 14.0 # oscilación de aleta caudal
        flipper_angle = math.cos(t) * 8.0

        # Lomo (azul pizarra oscuro) y vientre (azul pálido estriado)
        # Puntos del contorno
        spine = []
        belly = []
        for x in range(30, 320, 8):
            nx = (x - 30) / 290.0
            # Altura lomo y vientre
            h = math.sin(nx * math.pi) * 44.0
            wave_y = math.sin(nx * 3.0 - t) * (nx * 12.0)
            spine.append((x, 96 - h + wave_y))
            belly.append((x, 96 + h * 0.75 + wave_y))

        # Dibujar silueta de la ballena
        poly = [(25, 96)] + spine + [(330, 96 + fluke_angle)] + list(reversed(belly))
        d.polygon(poly, fill=(35, 65, 105, 245), outline=(20, 40, 70, 255))

        # Vientre claro estriado
        belly_poly = [(35, 98)] + [(x, y - 6) for x, y in belly if x < 240] + [(240, 98)]
        d.polygon(belly_poly, fill=(110, 155, 195, 230))

        # Estrías ventrales (pliegues gulares característicos de los rorcuales)
        for gy in range(92, 125, 6):
            d.line([(60, gy), (210, gy + math.sin(gy)*2)], fill=(75, 115, 155, 200), width=2)

        # Ojo de la ballena (amable y sereno)
        d.ellipse([58, 86, 68, 96], fill=(15, 25, 45, 255))
        d.ellipse([63, 89, 66, 92], fill=(255, 255, 255, 240))

        # Aleta pectoral (flipper)
        flip_x = 120
        flip_y = 105
        flip_poly = [
            (flip_x, flip_y),
            (flip_x + 35, flip_y + 40 + flipper_angle),
            (flip_x + 18, flip_y + 55 + flipper_angle),
            (flip_x - 10, flip_y + 20)
        ]
        d.polygon(flip_poly, fill=(30, 55, 90, 255), outline=(15, 30, 55, 255))

        # Aleta dorsal pequeña
        d.polygon([(260, 68 + wave_y), (275, 52 + wave_y), (285, 72 + wave_y)], fill=(30, 55, 90, 255))

        # Aleta caudal (fluke) bifurcada
        tail_x = 325
        tail_y = 96 + fluke_angle
        fluke_poly = [
            (tail_x - 10, tail_y),
            (tail_x + 45, tail_y - 32),
            (tail_x + 35, tail_y - 6),
            (tail_x + 45, tail_y + 26),
            (tail_x - 5, tail_y + 4)
        ]
        d.polygon(fluke_poly, fill=(28, 52, 86, 255), outline=(18, 35, 60, 255))

        # Manchas/patrón orgánico de moteado azul claro
        for mx, my, mr in [(90, 75, 4), (140, 80, 5), (170, 72, 6), (210, 82, 4), (250, 88, 3)]:
            d.ellipse([mx - mr, my - mr, mx + mr, my + mr], fill=(85, 135, 180, 160))

        # Espiráculo con suave halo de burbujas en el lomo
        d.ellipse([76, 60, 84, 66], fill=(20, 35, 60, 220))
        d.ellipse([74, 52, 78, 56], fill=(200, 240, 255, 180))
        d.ellipse([80, 46, 86, 52], fill=(200, 240, 255, 190))

        # Suavizado de bordes
        img = img.filter(ImageFilter.SMOOTH_MORE)
        row = i // 2
        col = i % 2
        sheet.paste(img, (col * fw, row * fh))

    sheet.save("assets/texturas/ballena.png", "PNG")

    # Archivo ballena.atlas
    atlas_content = f"""
ballena.png
size: 768, 384
format: RGBA8888
filter: Linear, Linear
repeat: none
ballena1
  rotate: false
  xy: 0, 0
  size: 384, 192
  orig: 384, 192
  offset: 0, 0
  index: -1
ballena2
  rotate: false
  xy: 384, 0
  size: 384, 192
  orig: 384, 192
  offset: 0, 0
  index: -1
ballena3
  rotate: false
  xy: 0, 192
  size: 384, 192
  orig: 384, 192
  offset: 0, 0
  index: -1
ballena4
  rotate: false
  xy: 384, 192
  size: 384, 192
  orig: 384, 192
  offset: 0, 0
  index: -1
"""
    with open("assets/texturas/ballena.atlas", "w", encoding="utf-8") as f:
        f.write(atlas_content.strip())
    print("Ballena atlas y textura generados exitosamente.")

def create_clam_atlas():
    # Ostra Gigante (ostra.png / ostra.atlas)
    # 3 frames de 96x96 en horizontal (288x96)
    fw, fh = 96, 96
    sheet = Image.new("RGBA", (fw * 3, fh), (0, 0, 0, 0))

    # Frame 1: Cerrada
    img1 = Image.new("RGBA", (fw, fh), (0, 0, 0, 0))
    d1 = ImageDraw.Draw(img1)
    # Concha inferior
    d1.pieslice([10, 40, 86, 90], 0, 180, fill=(75, 60, 90, 255), outline=(40, 30, 50, 255), width=3)
    # Concha superior cerrada
    d1.pieslice([10, 36, 86, 84], 180, 360, fill=(105, 85, 125, 255), outline=(40, 30, 50, 255), width=3)
    # Costillas radiales
    for ang in [200, 225, 250, 270, 290, 315, 340]:
        rad = math.radians(ang)
        x2 = 48 + math.cos(rad) * 36
        y2 = 60 + math.sin(rad) * 24
        d1.line([(48, 62), (x2, y2)], fill=(55, 40, 70, 220), width=2)
    sheet.paste(img1, (0, 0))

    # Frame 2: Semi-abierta (asoma el brillo de la perla)
    img2 = Image.new("RGBA", (fw, fh), (0, 0, 0, 0))
    d2 = ImageDraw.Draw(img2)
    # Concha inferior
    d2.pieslice([10, 48, 86, 92], 0, 180, fill=(75, 60, 90, 255), outline=(40, 30, 50, 255), width=3)
    # Interior carnoso
    d2.ellipse([20, 46, 76, 68], fill=(225, 130, 150, 255))
    # Brillo perla
    d2.ellipse([40, 48, 56, 64], fill=(255, 255, 240, 255), outline=(255, 215, 0, 255), width=2)
    # Concha superior semiabierta inclinada
    d2.pieslice([10, 18, 86, 66], 190, 350, fill=(105, 85, 125, 255), outline=(40, 30, 50, 255), width=3)
    sheet.paste(img2, (fw, 0))

    # Frame 3: Totalmente abierta con Perla Dorada radiante
    img3 = Image.new("RGBA", (fw, fh), (0, 0, 0, 0))
    d3 = ImageDraw.Draw(img3)
    # Concha inferior
    d3.pieslice([10, 52, 86, 94], 0, 180, fill=(75, 60, 90, 255), outline=(40, 30, 50, 255), width=3)
    # Manto nacarado y carne
    d3.ellipse([18, 48, 78, 78], fill=(240, 140, 165, 255))
    d3.ellipse([26, 52, 70, 74], fill=(255, 220, 230, 255))
    # Concha superior bien alzada
    d3.pieslice([10, 4, 86, 52], 180, 360, fill=(115, 95, 135, 255), outline=(40, 30, 50, 255), width=3)
    for ang in [200, 225, 250, 270, 290, 315, 340]:
        rad = math.radians(ang)
        x2 = 48 + math.cos(rad) * 36
        y2 = 28 + math.sin(rad) * 24
        d3.line([(48, 30), (x2, y2)], fill=(65, 48, 80, 220), width=2)
    # Perla luminosa con resplandor dorado
    d3.ellipse([34, 46, 62, 74], fill=(255, 250, 205, 255), outline=(255, 215, 0, 255), width=2)
    d3.ellipse([40, 50, 48, 58], fill=(255, 255, 255, 255)) # destello
    sheet.paste(img3, (fw * 2, 0))

    sheet.save("assets/texturas/ostra.png", "PNG")

    atlas_content = f"""
ostra.png
size: 288, 96
format: RGBA8888
filter: Linear, Linear
repeat: none
ostra1
  rotate: false
  xy: 0, 0
  size: 96, 96
  orig: 96, 96
  offset: 0, 0
  index: -1
ostra2
  rotate: false
  xy: 96, 0
  size: 96, 96
  orig: 96, 96
  offset: 0, 0
  index: -1
ostra3
  rotate: false
  xy: 192, 0
  size: 96, 96
  orig: 96, 96
  offset: 0, 0
  index: -1
"""
    with open("assets/texturas/ostra.atlas", "w", encoding="utf-8") as f:
        f.write(atlas_content.strip())
    print("Ostra atlas y textura generados exitosamente.")

def create_krill():
    # Krill brillante (32x32)
    img = Image.new("RGBA", (32, 32), (0, 0, 0, 0))
    d = ImageDraw.Draw(img)
    # Cuerpo arqueado de camarón traslúcido
    # Cefalotórax y abdomen segmentado
    d.ellipse([6, 10, 24, 20], fill=(255, 110, 80, 220))
    d.ellipse([14, 12, 28, 18], fill=(255, 150, 120, 240))
    # Ojo negro prominente
    d.ellipse([6, 12, 9, 15], fill=(20, 20, 20, 255))
    # Antenas largas y estilizadas
    d.line([(6, 13), (1, 6)], fill=(255, 180, 150, 200), width=1)
    d.line([(6, 14), (0, 16)], fill=(255, 180, 150, 200), width=1)
    # Pleópodos (patitas natatorias)
    for px in [10, 14, 18, 22]:
        d.line([(px, 18), (px - 2, 24)], fill=(255, 140, 110, 190), width=1)
    # Cola (urópodos)
    d.polygon([(26, 15), (31, 12), (31, 18)], fill=(255, 90, 60, 230))
    # Punto bioluminiscente fosforescente
    d.ellipse([12, 13, 16, 17], fill=(120, 255, 230, 255))

    img.save("assets/texturas/krill.png", "PNG")
    print("Krill generado exitosamente.")

def create_sea_urchin():
    # Erizo de Mar (erizo.png, 48x48)
    img = Image.new("RGBA", (48, 48), (0, 0, 0, 0))
    d = ImageDraw.Draw(img)
    cx, cy = 24, 24
    # Púas radiantes puntiagudas
    for a_deg in range(0, 360, 15):
        rad = math.radians(a_deg)
        r_out = 22 if (a_deg % 30 == 0) else 17
        x2 = cx + math.cos(rad) * r_out
        y2 = cy + math.sin(rad) * r_out
        d.line([(cx, cy), (x2, y2)], fill=(30, 15, 45, 255), width=2)
        d.line([(cx, cy), (x2*0.8 + cx*0.2, y2*0.8 + cy*0.2)], fill=(120, 40, 160, 230), width=3)
    # Centro globoso
    d.ellipse([cx - 10, cy - 10, cx + 10, cy + 10], fill=(45, 20, 65, 255), outline=(160, 60, 220, 255), width=2)
    d.ellipse([cx - 4, cy - 4, cx + 4, cy + 4], fill=(220, 80, 180, 255))
    img.save("assets/texturas/erizo.png", "PNG")
    print("Erizo generado exitosamente.")

def create_turtle_atlas():
    # Tortuga marina (tortuga.png / tortuga.atlas)
    # 4 frames de 128x96 (512x96)
    fw, fh = 128, 96
    sheet = Image.new("RGBA", (fw * 4, fh), (0, 0, 0, 0))

    for i in range(4):
        img = Image.new("RGBA", (fw, fh), (0, 0, 0, 0))
        d = ImageDraw.Draw(img)
        t = i * (math.pi / 2.0)
        flipper_flap = math.sin(t) * 16.0

        # Caparazón ovalado (verde oliva con polígonos marrones)
        d.ellipse([34, 24, 94, 72], fill=(65, 105, 55, 255), outline=(35, 65, 30, 255), width=3)
        # Patrón escutelos del caparazón
        for cx_scute in [50, 64, 78]:
            d.ellipse([cx_scute - 6, 40, cx_scute + 6, 56], fill=(115, 85, 45, 230), outline=(40, 60, 30, 220), width=1)

        # Cabeza hidrodinámica hacia la derecha
        d.ellipse([88, 38, 114, 58], fill=(85, 140, 75, 255), outline=(35, 65, 30, 255), width=2)
        d.ellipse([102, 42, 106, 46], fill=(20, 30, 15, 255)) # ojo

        # Aletas delanteras amplias (estilo alas de nado)
        f_pts = [
            (62, 38),
            (78, 10 + flipper_flap),
            (98, 22 + flipper_flap),
            (70, 50)
        ]
        d.polygon(f_pts, fill=(80, 135, 70, 255), outline=(35, 65, 30, 255))

        # Aletas traseras timón
        d.polygon([(36, 56), (18, 68), (28, 76), (42, 64)], fill=(75, 125, 65, 255), outline=(35, 65, 30, 255))

        img = img.filter(ImageFilter.SMOOTH_MORE)
        sheet.paste(img, (i * fw, 0))

    sheet.save("assets/texturas/tortuga.png", "PNG")

    atlas_content = f"""
tortuga.png
size: 512, 96
format: RGBA8888
filter: Linear, Linear
repeat: none
tortuga1
  rotate: false
  xy: 0, 0
  size: 128, 96
  orig: 128, 96
  offset: 0, 0
  index: -1
tortuga2
  rotate: false
  xy: 128, 0
  size: 128, 96
  orig: 128, 96
  offset: 0, 0
  index: -1
tortuga3
  rotate: false
  xy: 256, 0
  size: 128, 96
  orig: 128, 96
  offset: 0, 0
  index: -1
tortuga4
  rotate: false
  xy: 384, 0
  size: 128, 96
  orig: 128, 96
  offset: 0, 0
  index: -1
"""
    with open("assets/texturas/tortuga.atlas", "w", encoding="utf-8") as f:
        f.write(atlas_content.strip())
    print("Tortuga atlas y textura generados exitosamente.")

if __name__ == "__main__":
    create_whale_atlas()
    create_clam_atlas()
    create_krill()
    create_sea_urchin()
    create_turtle_atlas()
