#!/usr/bin/env python3
"""
Generador de iconos marinos de alta calidad para Pez-De-Pecera.
Crea iconos apropiados para un juego de aventura submarina:
1. iconocorazon (Salud / Vida marina)
2. iconovelocidad / iconoimpulso (Coletazo rápido / Turbo con burbujas)
3. iconoburbuja (Burbuja de agua / Oxígeno)
4. iconoescudo (Escudo burbuja protectora)
5. iconofaro (Linterna abisal / Haz de luz)
6. iconoperla (Perla brillante coleccionable)
7. iconopausa (Pausa marina estilizada)
"""
import math
from PIL import Image, ImageDraw, ImageFilter

# Dibujaremos a resolución 4x (256x256 por celda) y reduciremos a 64x64 con Lanczos
ICON_SIZE = 64
SCALE = 4
CELL = ICON_SIZE * SCALE  # 256x256
NUM_ICONS = 7

sheet_w = NUM_ICONS * CELL
sheet_h = CELL

sheet_hr = Image.new("RGBA", (sheet_w, sheet_h), (0, 0, 0, 0))

def draw_squircle_bg(draw, x_offset, color_top, color_bot, border_color):
    # Fondo de botón con borde redondeado estilo cristal marino
    pad = 16
    r = 50
    # Degradado vertical
    for y in range(pad, CELL - pad):
        t = (y - pad) / (CELL - 2 * pad)
        r_c = int(color_top[0] * (1 - t) + color_bot[0] * t)
        g_c = int(color_top[1] * (1 - t) + color_bot[1] * t)
        b_c = int(color_top[2] * (1 - t) + color_bot[2] * t)
        a_c = int(color_top[3] * (1 - t) + color_bot[3] * t)
        # Línea horizontal con esquinas redondeadas
        y_rel = y - pad
        h_box = CELL - 2 * pad
        # Radio de esquinas
        dx = 0
        if y_rel < r:
            dx = int(r - math.sqrt(r*r - (r - y_rel)**2))
        elif y_rel > h_box - r:
            dy = y_rel - (h_box - r)
            dx = int(r - math.sqrt(r*r - dy**2))
        x1 = x_offset + pad + dx
        x2 = x_offset + CELL - pad - dx
        if x2 > x1:
            draw.line([(x1, y), (x2, y)], fill=(r_c, g_c, b_c, a_c), width=1)
            
    # Borde redondeado sutil
    draw.rounded_rectangle([x_offset + pad, pad, x_offset + CELL - pad, CELL - pad], 
                           radius=r, outline=border_color, width=6)

# 1. ICONO CORAZON (Vida marina)
img1 = Image.new("RGBA", (CELL, CELL), (0, 0, 0, 0))
d1 = ImageDraw.Draw(img1)
draw_squircle_bg(d1, 0, (180, 20, 50, 230), (70, 5, 20, 240), (255, 120, 150, 220))

# Corazón central
cx, cy = 128, 130
size = 75
pts = []
for t_deg in range(0, 360, 2):
    t = math.radians(t_deg)
    # Fórmula paramétrica clásica de corazón
    x = 16 * (math.sin(t)**3)
    y = -(13 * math.cos(t) - 5 * math.cos(2*t) - 2 * math.cos(3*t) - math.cos(4*t))
    pts.append((cx + x * (size / 16), cy + y * (size / 16)))

d1.polygon(pts, fill=(255, 60, 90, 255))
# Brillo interior superior
d1.ellipse([cx - 45, cy - 40, cx - 10, cy - 10], fill=(255, 200, 215, 200))
sheet_hr.paste(img1, (0, 0), img1)

# 2. ICONO IMPULSO / COLETAZO (Velocidad acuática con aletas y burbujas)
img2 = Image.new("RGBA", (CELL, CELL), (0, 0, 0, 0))
d2 = ImageDraw.Draw(img2)
draw_squircle_bg(d2, 0, (10, 130, 200, 230), (5, 45, 80, 240), (80, 220, 255, 220))

# Cola de pez veloz hacia la derecha
tail_pts = [
    (70, 60), (140, 115), (190, 80), (160, 128), (190, 176), (140, 141), (70, 196), (110, 128)
]
d2.polygon(tail_pts, fill=(0, 230, 255, 255), outline=(255, 255, 255, 240), width=6)
# Rayas de velocidad de agua
for y_offset in [100, 128, 156]:
    d2.line([(40, y_offset), (75, y_offset)], fill=(200, 250, 255, 220), width=8)
# Burbujitas de acelerón
d2.ellipse([50, 70, 68, 88], fill=(220, 255, 255, 220), outline=(255, 255, 255, 255), width=3)
d2.ellipse([35, 170, 48, 183], fill=(220, 255, 255, 200), outline=(255, 255, 255, 255), width=3)
sheet_hr.paste(img2, (CELL, 0), img2)

# 3. ICONO BURBUJA / OXIGENO (Esfera de agua cristalina)
img3 = Image.new("RGBA", (CELL, CELL), (0, 0, 0, 0))
d3 = ImageDraw.Draw(img3)
draw_squircle_bg(d3, 0, (20, 160, 200, 220), (5, 60, 90, 240), (120, 240, 255, 220))

# Burbuja grande
bcx, bcy, br = 128, 128, 62
d3.ellipse([bcx - br, bcy - br, bcx + br, bcy + br], 
           fill=(60, 190, 240, 160), outline=(220, 255, 255, 250), width=7)
# Reflejo curvo brillante
d3.arc([bcx - br + 12, bcy - br + 12, bcx + br - 12, bcy + br - 12], 
       start=200, end=290, fill=(255, 255, 255, 255), width=10)
d3.ellipse([bcx - 25, bcy - 35, bcx - 12, bcy - 22], fill=(255, 255, 255, 240))
# Burbuja acompañante menor
d3.ellipse([180, 75, 205, 100], fill=(100, 210, 255, 180), outline=(255, 255, 255, 230), width=4)
sheet_hr.paste(img3, (2 * CELL, 0), img3)

# 4. ICONO ESCUDO BURBUJA (Burbuja defensiva bioluminiscente)
img4 = Image.new("RGBA", (CELL, CELL), (0, 0, 0, 0))
d4 = ImageDraw.Draw(img4)
draw_squircle_bg(d4, 0, (10, 80, 180, 230), (5, 25, 70, 240), (60, 180, 255, 220))

# Forma de escudo marino
shield_pts = [
    (128, 55), (185, 80), (185, 145), (128, 205), (71, 145), (71, 80)
]
d4.polygon(shield_pts, fill=(20, 140, 230, 220), outline=(150, 235, 255, 255), width=8)
# Anillos de energía dentro del escudo
inner_shield = [
    (128, 75), (168, 95), (168, 138), (128, 182), (88, 138), (88, 95)
]
d4.polygon(inner_shield, fill=(60, 190, 255, 160), outline=(255, 255, 255, 230), width=5)
# Estrella / destello central
d4.line([(128, 110), (128, 150)], fill=(255, 255, 255, 255), width=6)
d4.line([(108, 130), (148, 130)], fill=(255, 255, 255, 255), width=6)
sheet_hr.paste(img4, (3 * CELL, 0), img4)

# 5. ICONO LINTERNA ABISAL / FARO SUBMARINO
img5 = Image.new("RGBA", (CELL, CELL), (0, 0, 0, 0))
d5 = ImageDraw.Draw(img5)
draw_squircle_bg(d5, 0, (180, 140, 10, 230), (70, 50, 5, 240), (255, 220, 80, 220))

# Haz de luz divergente
cone_pts = [(90, 128), (210, 65), (210, 191)]
d5.polygon(cone_pts, fill=(255, 240, 120, 140))
# Linterna / Lámpara esférica abisal
lx, ly = 85, 128
d5.ellipse([lx - 32, ly - 32, lx + 32, ly + 32], fill=(255, 230, 80, 255), outline=(255, 255, 200, 255), width=6)
# Soporte / tallo bioluminiscente arqueado
d5.arc([40, 80, 110, 160], start=100, end=260, fill=(200, 160, 40, 255), width=8)
# Destellos
d5.line([(225, 100), (240, 100)], fill=(255, 255, 200, 240), width=5)
d5.line([(225, 128), (245, 128)], fill=(255, 255, 200, 240), width=6)
d5.line([(225, 156), (240, 156)], fill=(255, 255, 200, 240), width=5)
sheet_hr.paste(img5, (4 * CELL, 0), img5)

# 6. ICONO PERLA MARINA (Ostra con perla luminosa y brillo dorado)
img6 = Image.new("RGBA", (CELL, CELL), (0, 0, 0, 0))
d6 = ImageDraw.Draw(img6)
draw_squircle_bg(d6, 0, (140, 100, 190, 230), (50, 25, 80, 240), (220, 180, 255, 220))

# Concha / Ostra inferior
concha_pts = [(65, 145), (80, 185), (128, 200), (176, 185), (191, 145), (128, 160)]
d6.polygon(concha_pts, fill=(100, 60, 140, 255), outline=(200, 160, 240, 255), width=6)
# Perla brillante central
px, py, pr = 128, 130, 40
d6.ellipse([px - pr, py - pr, px + pr, py + pr], 
           fill=(255, 250, 225, 255), outline=(255, 220, 120, 255), width=6)
# Brillo nacarado
d6.ellipse([px - 22, py - 24, px - 6, py - 8], fill=(255, 255, 255, 255))
# Destellos dorados alrededor de la perla
d6.line([(128, 72), (128, 84)], fill=(255, 235, 120, 240), width=5)
d6.line([(172, 92), (182, 85)], fill=(255, 235, 120, 240), width=5)
d6.line([(84, 92), (74, 85)], fill=(255, 235, 120, 240), width=5)
sheet_hr.paste(img6, (5 * CELL, 0), img6)

# 7. ICONO PAUSA MARINA (Barras de cristal marino)
img7 = Image.new("RGBA", (CELL, CELL), (0, 0, 0, 0))
d7 = ImageDraw.Draw(img7)
draw_squircle_bg(d7, 0, (15, 90, 140, 230), (5, 35, 60, 240), (90, 210, 255, 220))

# Dos barras redondeadas
d7.rounded_rectangle([92, 75, 116, 181], radius=12, fill=(255, 255, 255, 255), outline=(130, 230, 255, 240), width=4)
d7.rounded_rectangle([140, 75, 164, 181], radius=12, fill=(255, 255, 255, 255), outline=(130, 230, 255, 240), width=4)
sheet_hr.paste(img7, (6 * CELL, 0), img7)

# Redimensionar la hoja final a 448x64 (7 iconos de 64x64)
target_w = NUM_ICONS * ICON_SIZE
target_h = ICON_SIZE
sheet_final = sheet_hr.resize((target_w, target_h), Image.Resampling.LANCZOS)

# Guardar iconos.png
output_png = "assets/texturas/iconos.png"
sheet_final.save(output_png, "PNG")
print(f"Hoja de iconos guardada en: {output_png} ({target_w}x{target_h})")

# Crear iconos.atlas compatible con libGDX
atlas_content = f"""
iconos.png
size: {target_w}, {target_h}
format: RGBA8888
filter: Linear, Linear
repeat: none
iconocorazon
  rotate: false
  xy: 0, 0
  size: 64, 64
  orig: 64, 64
  offset: 0, 0
  index: -1
iconovelocidad
  rotate: false
  xy: 64, 0
  size: 64, 64
  orig: 64, 64
  offset: 0, 0
  index: -1
iconoimpulso
  rotate: false
  xy: 64, 0
  size: 64, 64
  orig: 64, 64
  offset: 0, 0
  index: -1
iconoburbuja
  rotate: false
  xy: 128, 0
  size: 64, 64
  orig: 64, 64
  offset: 0, 0
  index: -1
iconoescudo
  rotate: false
  xy: 192, 0
  size: 64, 64
  orig: 64, 64
  offset: 0, 0
  index: -1
iconofaro
  rotate: false
  xy: 256, 0
  size: 64, 64
  orig: 64, 64
  offset: 0, 0
  index: -1
iconoperla
  rotate: false
  xy: 320, 0
  size: 64, 64
  orig: 64, 64
  offset: 0, 0
  index: -1
iconopausa
  rotate: false
  xy: 384, 0
  size: 64, 64
  orig: 64, 64
  offset: 0, 0
  index: -1
"""

output_atlas = "assets/texturas/iconos.atlas"
with open(output_atlas, "w", encoding="utf-8") as f:
    f.write(atlas_content.strip() + "\n")
print(f"Atlas de iconos guardado en: {output_atlas}")
