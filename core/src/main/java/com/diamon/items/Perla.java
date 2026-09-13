package com.diamon.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.diamon.nucleo.Pantalla;
import com.diamon.personajes.Jugador;

import box2dLight.RayHandler;

/**
 * Perla marina coleccionable.
 * Otorga 100 puntos y emite un suave brillo bioluminiscente en las profundidades.
 */
public class Perla extends Item {

    public static final int PUNTOS_PERLA = 100;

    public Perla(Texture textura, Pantalla pantalla, RayHandler rayHandler) {
        super(textura, pantalla, 24, 24, rayHandler, new Color(1.0f, 0.9f, 0.35f, 0.8f), 1.8f);
        setColor(1.0f, 0.95f, 0.5f, 0.95f);
    }

    public Perla(TextureRegion region, Pantalla pantalla, RayHandler rayHandler) {
        super(region, pantalla, 24, 24, rayHandler, new Color(1.0f, 0.9f, 0.35f, 0.8f), 1.8f);
        setColor(1.0f, 0.95f, 0.5f, 0.95f);
    }

    @Override
    public void aplicar(Jugador jugador) {
        jugador.agregarPuntos(PUNTOS_PERLA);
    }
}
