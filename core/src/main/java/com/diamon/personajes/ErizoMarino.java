package com.diamon.personajes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;

/**
 * Erizo de Mar puntiagudo adherido al lecho rocoso o paredes de cuevas.
 * Obstáculo natural del océano: daña al jugador si no lleva Escudo Burbuja.
 */
public class ErizoMarino extends Personaje {

    private float tiempoPulsacion = 0f;

    public ErizoMarino(Texture textura, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
        super(new TextureRegion(textura), pantalla, ancho, alto, tipoDeCuerpo);
    }

    public ErizoMarino(TextureRegion texturaRegion, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
        super(texturaRegion, pantalla, ancho, alto, tipoDeCuerpo);
    }

    @Override
    public void actualizar(float delta) {
        super.actualizar(delta);
        tiempoPulsacion += delta * 2.5f;

        // Leve respiración / movimiento sutil de las púas
        float factor = 1.0f + MathUtils.sin(tiempoPulsacion) * 0.05f;
        setScale(factor);
    }

    @Override
    public void colision(Personaje actor) {
        if (actor instanceof Jugador) {
            Jugador j = (Jugador) actor;
            j.recibirDanio(1);
            activarHitFlash(0.15f, new Color(0.8f, 0.2f, 0.9f, 1f));
        }
    }
}
