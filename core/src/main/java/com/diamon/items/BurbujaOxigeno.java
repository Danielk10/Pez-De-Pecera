package com.diamon.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.diamon.nucleo.Pantalla;
import com.diamon.personajes.Jugador;

import box2dLight.RayHandler;

/**
 * Burbuja de oxígeno restauradora.
 * Cura 1 punto de vida / aire al pez payaso y asciende lentamente por flotabilidad.
 */
public class BurbujaOxigeno extends Item {

    public BurbujaOxigeno(Texture textura, Pantalla pantalla, RayHandler rayHandler) {
        super(textura, pantalla, 28, 28, rayHandler, new Color(0.25f, 0.85f, 1.0f, 0.75f), 1.5f);
        setColor(0.3f, 0.9f, 1.0f, 0.85f);
    }

    public BurbujaOxigeno(TextureRegion region, Pantalla pantalla, RayHandler rayHandler) {
        super(region, pantalla, 28, 28, rayHandler, new Color(0.25f, 0.85f, 1.0f, 0.75f), 1.5f);
        setColor(0.3f, 0.9f, 1.0f, 0.85f);
    }

    @Override
    public void actualizar(float delta) {
        // Ascenso gradual por flotabilidad positiva
        this.baseYMetros += delta * 0.45f;
        super.actualizar(delta);
    }

    @Override
    public void aplicar(Jugador jugador) {
        jugador.recuperarVida(1);
    }
}
