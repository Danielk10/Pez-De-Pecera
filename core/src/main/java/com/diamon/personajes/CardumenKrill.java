package com.diamon.personajes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.diamon.nucleo.Constantes;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;

/**
 * Enjambre o cardumen denso de Krill bioluminiscente.
 * Se desplaza en círculos y vórtices orgánicos en aguas abiertas.
 * Cuando el pez payaso nada a través de ellos, se dispersan velozmente;
 * el jugador puede alimentarse de ellos para recuperar energía y oxígeno.
 */
public class CardumenKrill extends Personaje {

    private static class IndividuoKrill {
        float relX;
        float relY;
        float vx;
        float vy;
        float fase;
        float velocidadRotacion;
        float escala;
        boolean comido = false;
    }

    private final TextureRegion texturaKrill;
    private final Array<IndividuoKrill> enjambre = new Array<IndividuoKrill>(true, 24);
    private float tiempoNado = 0f;
    private int krillRestantes = 20;

    public CardumenKrill(Texture textura, Pantalla pantalla, int cantidad) {
        this(textura, pantalla, 48f, 48f, Personaje.ESTATICO);
    }

    public CardumenKrill(Texture textura, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
        super(new TextureRegion(textura), pantalla, ancho, alto, tipoDeCuerpo);
        this.texturaKrill = new TextureRegion(textura);

        // Inicializar 20 krill en el cardumen
        for (int i = 0; i < 20; i++) {
            IndividuoKrill k = new IndividuoKrill();
            float angulo = MathUtils.random(0f, 6.28f);
            float dist = MathUtils.random(0.1f, (ancho / 2f) * 0.85f);
            k.relX = MathUtils.cos(angulo) * dist;
            k.relY = MathUtils.sin(angulo) * dist;
            k.vx = MathUtils.random(-0.5f, 0.5f);
            k.vy = MathUtils.random(-0.5f, 0.5f);
            k.fase = MathUtils.random(0f, 6.28f);
            k.velocidadRotacion = MathUtils.random(1.5f, 3.5f);
            k.escala = MathUtils.random(0.55f, 0.85f);
            enjambre.add(k);
        }
    }

    @Override
    public void actualizar(float delta) {
        super.actualizar(delta);
        tiempoNado += delta;

        // Vórtice orgánico del enjambre
        for (int i = 0; i < enjambre.size; i++) {
            IndividuoKrill k = enjambre.get(i);
            if (k.comido) continue;

            k.fase += delta * k.velocidadRotacion;
            float radioMax = getWidth() * 0.45f;

            // Nado circular y vaivén
            k.relX += (MathUtils.cos(k.fase) * 0.6f + k.vx) * delta;
            k.relY += (MathUtils.sin(k.fase) * 0.6f + k.vy) * delta;

            // Retener dentro del núcleo del cardumen
            float dist = (float) Math.hypot(k.relX, k.relY);
            if (dist > radioMax) {
                k.relX *= 0.95f;
                k.relY *= 0.95f;
                k.vx = -k.vx * 0.8f;
                k.vy = -k.vy * 0.8f;
            }
        }

        // Si todos los krill fueron consumidos, remover el cardumen
        if (krillRestantes <= 0) {
            remover();
        }
    }

    @Override
    public void colision(Personaje actor) {
        if (actor instanceof Jugador) {
            Jugador j = (Jugador) actor;

            // El jugador se alimenta del krill al cruzar el cardumen
            int comidosEnColision = 0;
            for (int i = 0; i < enjambre.size; i++) {
                IndividuoKrill k = enjambre.get(i);
                if (!k.comido) {
                    k.comido = true;
                    krillRestantes--;
                    comidosEnColision++;
                    if (comidosEnColision >= 5) break;
                }
            }

            if (comidosEnColision > 0) {
                j.agregarPuntos(15 * comidosEnColision);
                j.recuperarVida(1);
                activarHitFlash(0.12f, new Color(1f, 0.65f, 0.45f, 0.8f));
            }
        }
    }

    @Override
    public void dibujar(Batch pincel, float delta) {
        if (texturaKrill == null) return;

        float centroX = x + getWidth() / 2f;
        float centroY = y + getHeight() / 2f;

        Color original = pincel.getColor();

        for (int i = 0; i < enjambre.size; i++) {
            IndividuoKrill k = enjambre.get(i);
            if (k.comido) continue;

            float drawX = centroX + k.relX;
            float drawY = centroY + k.relY;
            float kw = (32f / Constantes.PPM) * k.escala;
            float kh = (32f / Constantes.PPM) * k.escala;

            // Tinte translúcido bioluminiscente
            pincel.setColor(1.0f, 0.75f, 0.60f, 0.90f);
            pincel.draw(texturaKrill, drawX - kw / 2f, drawY - kh / 2f, kw, kh);
        }

        pincel.setColor(original);
    }
}
