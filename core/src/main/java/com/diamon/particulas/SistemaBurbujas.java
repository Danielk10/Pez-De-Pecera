package com.diamon.particulas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.diamon.nucleo.Constantes;

/**
 * Sistema de partículas submarino de alto rendimiento.
 * Gestiona:
 * - Burbujas ambientales ascendentes con vaivén hidrodinámico.
 * - Estela de burbujas de propulsión emanadas de la cola del pez.
 * - Columnas de burbujas en respiraderos hidrotermales / géiseres.
 * - Anillo expansivo de Sonar (eco-localización submarina).
 * - Estallidos de microburbujas al recolectar perlas y objetos.
 */
public class SistemaBurbujas {

    public static class Burbuja {
        public float x;
        public float y;
        public float vx;
        public float vy;
        public float tamano;
        public float vida;
        public float vidaMax;
        public float faseOndulacion;
        public float amplitudOndulacion;
        public Color color = new Color(1f, 1f, 1f, 0.85f);
        public boolean viva;
    }

    public static class OndaSonar {
        public float x;
        public float y;
        public float radio;
        public float radioMax;
        public float velocidad;
        public float alpha;
        public boolean activa;
    }

    private final Texture texturaBurbuja;
    private final Array<Burbuja> burbujas = new Array<Burbuja>(true, 256);
    private final Array<OndaSonar> ondasSonar = new Array<OndaSonar>(true, 8);

    private float tiempoGeneracionAmbiental = 0f;
    private float limiteYSuperficie = 35.0f * (64f / Constantes.PPM);

    public SistemaBurbujas(Texture texturaBurbuja) {
        this.texturaBurbuja = texturaBurbuja;
    }

    public void setLimiteYSuperficie(float y) {
        this.limiteYSuperficie = y;
    }

    /**
     * Genera una burbuja individual con parámetros hidrodinámicos.
     */
    public void spawnBurbuja(float x, float y, float vx, float vy, float tamanoMetros, float vidaMax, Color color) {
        Burbuja b = null;
        for (int i = 0; i < burbujas.size; i++) {
            if (!burbujas.get(i).viva) {
                b = burbujas.get(i);
                break;
            }
        }
        if (b == null) {
            if (burbujas.size >= 400) return;
            b = new Burbuja();
            burbujas.add(b);
        }

        b.x = x;
        b.y = y;
        b.vx = vx;
        b.vy = vy;
        b.tamano = tamanoMetros;
        b.vida = 0;
        b.vidaMax = vidaMax;
        b.faseOndulacion = MathUtils.random(0f, 6.28f);
        b.amplitudOndulacion = MathUtils.random(0.15f, 0.45f);
        if (color != null) {
            b.color.set(color);
        } else {
            b.color.set(0.6f, 0.9f, 1.0f, MathUtils.random(0.5f, 0.85f));
        }
        b.viva = true;
    }

    /**
     * Emite estela de propulsión desde la aleta caudal del pez.
     */
    public void emitirEstelaPropulsion(float colaX, float colaY, boolean turbo, boolean mirandoDerecha) {
        int cantidad = turbo ? 3 : 1;
        float dirX = mirandoDerecha ? -1.0f : 1.0f;
        for (int i = 0; i < cantidad; i++) {
            float vx = dirX * MathUtils.random(0.8f, 2.2f);
            float vy = MathUtils.random(0.2f, 1.2f);
            float tam = turbo ? MathUtils.random(0.22f, 0.38f) : MathUtils.random(0.12f, 0.22f);
            Color c = turbo ? new Color(1.0f, 0.8f, 0.4f, 0.85f) : new Color(0.7f, 0.95f, 1.0f, 0.75f);
            spawnBurbuja(colaX + MathUtils.random(-0.1f, 0.1f), colaY + MathUtils.random(-0.1f, 0.1f), vx, vy, tam, MathUtils.random(1.2f, 2.4f), c);
        }
    }

    /**
     * Estallido de burbujas al recolectar perla, oxígeno o recibir impacto.
     */
    public void estallidoBurbujas(float x, float y, int cantidad, Color color) {
        for (int i = 0; i < cantidad; i++) {
            float angulo = MathUtils.random(0f, 6.28f);
            float rapidez = MathUtils.random(1.0f, 3.5f);
            float vx = MathUtils.cos(angulo) * rapidez;
            float vy = MathUtils.sin(angulo) * rapidez + 0.8f;
            float tam = MathUtils.random(0.15f, 0.35f);
            spawnBurbuja(x, y, vx, vy, tam, MathUtils.random(0.8f, 1.8f), color);
        }
    }

    /**
     * Activa el pulso de eco-localización (Sonar).
     */
    public void activarSonar(float centroX, float centroY) {
        OndaSonar onda = null;
        for (int i = 0; i < ondasSonar.size; i++) {
            if (!ondasSonar.get(i).activa) {
                onda = ondasSonar.get(i);
                break;
            }
        }
        if (onda == null) {
            onda = new OndaSonar();
            ondasSonar.add(onda);
        }
        onda.x = centroX;
        onda.y = centroY;
        onda.radio = 0.5f;
        onda.radioMax = 9.5f;
        onda.velocidad = 9.0f;
        onda.alpha = 0.9f;
        onda.activa = true;

        // Estallido sutil de microburbujas en el emisor
        estallidoBurbujas(centroX, centroY, 8, new Color(0.3f, 0.9f, 1.0f, 0.9f));
    }

    public void actualizar(float delta, float camX, float camY, float viewportWidthMetros, float viewportHeightMetros) {
        if (delta <= 0) return;

        // 1. Generación ambiental en el área visible de la cámara
        tiempoGeneracionAmbiental += delta;
        if (tiempoGeneracionAmbiental > 0.12f) {
            tiempoGeneracionAmbiental = 0f;
            float spawnX = camX + MathUtils.random(-viewportWidthMetros / 2f - 2f, viewportWidthMetros / 2f + 2f);
            float spawnY = camY - viewportHeightMetros / 2f - 1f;
            float tam = MathUtils.random(0.08f, 0.26f);
            float vy = MathUtils.random(1.2f, 2.8f);
            spawnBurbuja(spawnX, spawnY, MathUtils.random(-0.2f, 0.2f), vy, tam, MathUtils.random(4f, 8f), null);
        }

        // 2. Actualizar burbujas activas
        for (int i = 0; i < burbujas.size; i++) {
            Burbuja b = burbujas.get(i);
            if (!b.viva) continue;

            b.vida += delta;
            if (b.vida >= b.vidaMax || b.y >= limiteYSuperficie) {
                b.viva = false;
                continue;
            }

            b.faseOndulacion += delta * 3.5f;
            float oscilacionX = MathUtils.sin(b.faseOndulacion) * b.amplitudOndulacion;
            b.x += (b.vx + oscilacionX) * delta;
            b.y += b.vy * delta;

            // Desaceleración horizontal por resistencia viscosa del agua
            b.vx *= Math.pow(0.90, delta * 60);

            // Desvanecimiento suave al final de la vida
            float factorVida = 1.0f - (b.vida / b.vidaMax);
            b.color.a = MathUtils.clamp(factorVida * 0.9f, 0f, 1f);
        }

        // 3. Actualizar ondas de sonar
        for (int i = 0; i < ondasSonar.size; i++) {
            OndaSonar s = ondasSonar.get(i);
            if (!s.activa) continue;

            s.radio += s.velocidad * delta;
            s.alpha = MathUtils.clamp(1.0f - (s.radio / s.radioMax), 0f, 1f);
            if (s.radio >= s.radioMax || s.alpha <= 0.01f) {
                s.activa = false;
            }
        }
    }

    public void dibujar(Batch batch) {
        if (texturaBurbuja == null) return;

        // Dibujar burbujas
        for (int i = 0; i < burbujas.size; i++) {
            Burbuja b = burbujas.get(i);
            if (!b.viva) continue;

            batch.setColor(b.color);
            batch.draw(texturaBurbuja, b.x - b.tamano / 2f, b.y - b.tamano / 2f, b.tamano, b.tamano);
        }

        // Dibujar ondas de eco-localización (anillos tenues concéntricos)
        for (int i = 0; i < ondasSonar.size; i++) {
            OndaSonar s = ondasSonar.get(i);
            if (!s.activa) continue;

            batch.setColor(0.3f, 0.85f, 1.0f, s.alpha * 0.65f);
            float diametro = s.radio * 2.0f;
            batch.draw(texturaBurbuja, s.x - s.radio, s.y - s.radio, diametro, diametro);
        }

        batch.setColor(Color.WHITE);
    }

    public Array<OndaSonar> getOndasSonar() {
        return ondasSonar;
    }
}
