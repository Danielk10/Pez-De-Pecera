package com.diamon.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.diamon.nucleo.Juego;

/**
 * Fondo vivo y dinámico para la pantalla de inicio (menú principal).
 * Simula un ecosistema submarino con:
 * - Burbujas ambientales ascendentes con oscilación hidrodinámica.
 * - Peces y criaturas marinas nadando en múltiples planos de profundidad
 *   (pez payaso protagonista, peces ángel aliados, peces globo, medusas y tiburón en el fondo).
 */
public class FondoMenuMarino extends Actor {

    private static class Burbuja {
        float x;
        float y;
        float velocidadY;
        float tamano;
        float faseOndulacion;
        float amplitudOndulacion;
        float alpha;

        void reset(boolean inicioAleatorio) {
            this.x = MathUtils.random(10f, Juego.ANCHO_PANTALLA - 10f);
            this.y = inicioAleatorio ? MathUtils.random(-20f, Juego.ALTO_PANTALLA + 20f) : -MathUtils.random(15f, 60f);
            this.velocidadY = MathUtils.random(25f, 65f);
            this.tamano = MathUtils.random(10f, 26f);
            this.faseOndulacion = MathUtils.random(0f, 6.28f);
            this.amplitudOndulacion = MathUtils.random(0.8f, 2.2f);
            this.alpha = MathUtils.random(0.35f, 0.75f);
        }
    }

    private static class CriaturaMenu {
        Animation<TextureRegion> animacion;
        float x;
        float y;
        float baseY;
        float velocidadX;
        float anchoBase;
        float altoBase;
        float escala;
        float faseOndulacion;
        float amplitudOndulacion;
        float tiempoAnim;
        boolean miraDerechaPorDefecto;
        boolean haciaDerecha;
        Color tinteProfundidad = new Color(1f, 1f, 1f, 1f);

        void actualizar(float delta) {
            tiempoAnim += delta;
            x += velocidadX * delta;
            faseOndulacion += delta * 2.2f;
            y = baseY + MathUtils.sin(faseOndulacion) * amplitudOndulacion;

            // Al salir de los límites de la pantalla, reiniciar en el extremo opuesto
            if (haciaDerecha && x > Juego.ANCHO_PANTALLA + anchoBase * escala + 60f) {
                reiniciar(false);
            } else if (!haciaDerecha && x < -anchoBase * escala - 60f) {
                reiniciar(true);
            }
        }

        void reiniciar(boolean reaparecerPorLaDerecha) {
            haciaDerecha = !reaparecerPorLaDerecha;
            float dir = haciaDerecha ? 1f : -1f;
            velocidadX = dir * MathUtils.random(28f, 75f);
            baseY = MathUtils.random(35f, Juego.ALTO_PANTALLA - 90f);
            y = baseY;
            faseOndulacion = MathUtils.random(0f, 6.28f);
            amplitudOndulacion = MathUtils.random(12f, 28f);
            x = haciaDerecha ? (-anchoBase * escala - MathUtils.random(20f, 120f))
                    : (Juego.ANCHO_PANTALLA + anchoBase * escala + MathUtils.random(20f, 120f));
        }
    }

    private final Texture texturaBurbuja;
    private final Array<Burbuja> burbujas = new Array<Burbuja>(true, 32);
    private final Array<CriaturaMenu> criaturas = new Array<CriaturaMenu>(true, 10);

    public FondoMenuMarino(AssetManager recurso) {
        setBounds(0, 0, Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA);
        setTouchable(Touchable.disabled);

        // 1. Textura de burbujas
        if (recurso.isLoaded("particulas/circle4.png", Texture.class)) {
            texturaBurbuja = recurso.get("particulas/circle4.png", Texture.class);
        } else {
            texturaBurbuja = null;
        }

        // Inicializar 28 burbujas dispersas
        for (int i = 0; i < 28; i++) {
            Burbuja b = new Burbuja();
            b.reset(true);
            burbujas.add(b);
        }

        // 2. Criaturas marinas nadando en el fondo
        inicializarCriaturas(recurso);
    }

    private void inicializarCriaturas(AssetManager recurso) {
        // --- 1. Tiburón Azul (en el fondo, majestuoso y grande pero más tenue) ---
        if (recurso.isLoaded("texturas/tiburon.atlas", TextureAtlas.class)) {
            TextureAtlas atlas = recurso.get("texturas/tiburon.atlas", TextureAtlas.class);
            CriaturaMenu tiburon = new CriaturaMenu();
            tiburon.animacion = new Animation<TextureRegion>(0.14f, atlas.getRegions(), Animation.PlayMode.LOOP);
            tiburon.anchoBase = 180f;
            tiburon.altoBase = 110f;
            tiburon.escala = 0.85f;
            tiburon.miraDerechaPorDefecto = true;
            tiburon.tinteProfundidad.set(0.6f, 0.8f, 0.95f, 0.55f); // Ilusión de profundidad marina
            tiburon.reiniciar(false);
            tiburon.baseY = MathUtils.random(180f, 320f);
            tiburon.velocidadX = 35f;
            criaturas.add(tiburon);
        }

        // --- 2. Pez Payaso protagonista (primer plano, nado alegre) ---
        if (recurso.isLoaded("texturas/pez.atlas", TextureAtlas.class)) {
            TextureAtlas atlas = recurso.get("texturas/pez.atlas", TextureAtlas.class);
            CriaturaMenu payaso = new CriaturaMenu();
            payaso.animacion = new Animation<TextureRegion>(0.12f, atlas.getRegions(), Animation.PlayMode.LOOP);
            payaso.anchoBase = 64f;
            payaso.altoBase = 64f;
            payaso.escala = 0.95f;
            payaso.miraDerechaPorDefecto = true;
            payaso.tinteProfundidad.set(1f, 1f, 1f, 0.95f);
            payaso.reiniciar(true);
            payaso.x = 200f;
            payaso.baseY = 220f;
            payaso.velocidadX = -50f;
            criaturas.add(payaso);
        }

        // --- 3. Cardumen de Peces Ángel aliados ---
        if (recurso.isLoaded("texturas/pez1.atlas", TextureAtlas.class)) {
            TextureAtlas atlas = recurso.get("texturas/pez1.atlas", TextureAtlas.class);
            Array<TextureAtlas.AtlasRegion> framesValidos = new Array<TextureAtlas.AtlasRegion>();
            for (TextureAtlas.AtlasRegion r : atlas.getRegions()) {
                if (r.name == null || !r.name.toLowerCase().contains("dead")) {
                    framesValidos.add(r);
                }
            }
            if (framesValidos.size == 0) {
                framesValidos = atlas.getRegions();
            }

            for (int i = 0; i < 2; i++) {
                CriaturaMenu angel = new CriaturaMenu();
                angel.animacion = new Animation<TextureRegion>(0.12f, framesValidos, Animation.PlayMode.LOOP);
                angel.anchoBase = 64f;
                angel.altoBase = 40f;
                angel.escala = 0.80f;
                angel.miraDerechaPorDefecto = true;
                angel.tinteProfundidad.set(0.85f, 0.95f, 1f, 0.80f);
                angel.reiniciar(false);
                angel.x = 80f + i * 90f;
                angel.baseY = 120f + i * 70f;
                angel.velocidadX = 42f + i * 6f;
                criaturas.add(angel);
            }
        }

        // --- 4. Pez Globo Amarillo ---
        if (recurso.isLoaded("texturas/pezG.atlas", TextureAtlas.class)) {
            TextureAtlas atlas = recurso.get("texturas/pezG.atlas", TextureAtlas.class);
            CriaturaMenu pezGlobo = new CriaturaMenu();
            pezGlobo.animacion = new Animation<TextureRegion>(0.15f, atlas.getRegions(), Animation.PlayMode.LOOP);
            pezGlobo.anchoBase = 60f;
            pezGlobo.altoBase = 45f;
            pezGlobo.escala = 0.85f;
            pezGlobo.miraDerechaPorDefecto = false; // Sprite mira a la izquierda
            pezGlobo.tinteProfundidad.set(1f, 1f, 0.85f, 0.85f);
            pezGlobo.reiniciar(true);
            pezGlobo.x = 600f;
            pezGlobo.baseY = 160f;
            pezGlobo.velocidadX = -32f;
            criaturas.add(pezGlobo);
        }

        // --- 5. Medusa / Pulpo (movimiento vertical flotante) ---
        if (recurso.isLoaded("texturas/pulpo.atlas", TextureAtlas.class)) {
            TextureAtlas atlas = recurso.get("texturas/pulpo.atlas", TextureAtlas.class);
            CriaturaMenu medusa = new CriaturaMenu();
            medusa.animacion = new Animation<TextureRegion>(0.12f, atlas.getRegions(), Animation.PlayMode.LOOP);
            medusa.anchoBase = 46f;
            medusa.altoBase = 84f;
            medusa.escala = 0.75f;
            medusa.miraDerechaPorDefecto = true;
            medusa.tinteProfundidad.set(0.8f, 0.85f, 1f, 0.65f);
            medusa.reiniciar(false);
            medusa.x = 420f;
            medusa.baseY = 190f;
            medusa.velocidadX = 14f;
            medusa.amplitudOndulacion = 35f;
            criaturas.add(medusa);
        }

        // --- 6. Tortuga Marina (deslizándose plácidamente en las profundidades medias) ---
        if (recurso.isLoaded("texturas/tortuga.atlas", TextureAtlas.class)) {
            TextureAtlas atlas = recurso.get("texturas/tortuga.atlas", TextureAtlas.class);
            CriaturaMenu tortuga = new CriaturaMenu();
            tortuga.animacion = new Animation<TextureRegion>(0.16f, atlas.getRegions(), Animation.PlayMode.LOOP);
            tortuga.anchoBase = 96f;
            tortuga.altoBase = 72f;
            tortuga.escala = 0.85f;
            tortuga.miraDerechaPorDefecto = true;
            tortuga.tinteProfundidad.set(0.9f, 1f, 0.9f, 0.85f);
            tortuga.reiniciar(false);
            tortuga.x = 150f;
            tortuga.baseY = 90f;
            tortuga.velocidadX = 24f;
            tortuga.amplitudOndulacion = 18f;
            criaturas.add(tortuga);
        }

        // --- 7. Ballena Azul (en la lejanía profunda, majestuosa y colosal) ---
        if (recurso.isLoaded("texturas/ballena.atlas", TextureAtlas.class)) {
            TextureAtlas atlas = recurso.get("texturas/ballena.atlas", TextureAtlas.class);
            CriaturaMenu ballena = new CriaturaMenu();
            ballena.animacion = new Animation<TextureRegion>(0.22f, atlas.getRegions(), Animation.PlayMode.LOOP);
            ballena.anchoBase = 240f;
            ballena.altoBase = 120f;
            ballena.escala = 0.90f;
            ballena.miraDerechaPorDefecto = true;
            ballena.tinteProfundidad.set(0.45f, 0.65f, 0.90f, 0.45f); // Muy profunda en el océano
            ballena.reiniciar(false);
            ballena.x = -250f;
            ballena.baseY = 310f;
            ballena.velocidadX = 18f;
            ballena.amplitudOndulacion = 14f;
            criaturas.insert(0, ballena); // Renderizar detrás de todas las demás
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // 1. Actualizar burbujas
        for (int i = 0; i < burbujas.size; i++) {
            Burbuja b = burbujas.get(i);
            b.y += b.velocidadY * delta;
            b.faseOndulacion += delta * 2.8f;
            b.x += MathUtils.sin(b.faseOndulacion) * b.amplitudOndulacion * delta * 12f;

            if (b.y > Juego.ALTO_PANTALLA + 25f) {
                b.reset(false);
            }
        }

        // 2. Actualizar criaturas marinas
        for (int i = 0; i < criaturas.size; i++) {
            criaturas.get(i).actualizar(delta);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color colorOriginal = batch.getColor();

        // 1. Dibujar criaturas marinas en orden de profundidad
        for (int i = 0; i < criaturas.size; i++) {
            CriaturaMenu c = criaturas.get(i);
            TextureRegion frame = c.animacion.getKeyFrame(c.tiempoAnim, true);
            if (frame == null) continue;

            float drawWidth = c.anchoBase * c.escala;
            float drawHeight = c.altoBase * c.escala;

            // Determinar si hay que voltear horizontalmente el sprite
            boolean voltear = c.miraDerechaPorDefecto ? !c.haciaDerecha : c.haciaDerecha;

            batch.setColor(c.tinteProfundidad.r, c.tinteProfundidad.g, c.tinteProfundidad.b,
                    c.tinteProfundidad.a * parentAlpha);

            if (voltear) {
                batch.draw(frame, c.x + drawWidth, c.y, -drawWidth, drawHeight);
            } else {
                batch.draw(frame, c.x, c.y, drawWidth, drawHeight);
            }
        }

        // 2. Dibujar burbujas ascendentes
        if (texturaBurbuja != null) {
            for (int i = 0; i < burbujas.size; i++) {
                Burbuja b = burbujas.get(i);
                batch.setColor(0.75f, 0.95f, 1.0f, b.alpha * parentAlpha);
                batch.draw(texturaBurbuja, b.x - b.tamano / 2f, b.y - b.tamano / 2f, b.tamano, b.tamano);
            }
        }

        batch.setColor(colorOriginal);
    }
}
