package com.diamon.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.diamon.nucleo.Pantalla;
import com.diamon.personajes.Jugador;

import box2dLight.RayHandler;

/**
 * Power-Up submarino interactivo (Turbo, Escudo, Linterna Abisal).
 * Proporciona mejoras temporales y emite un halo pulsante de luz.
 */
public class PowerUpSubmarino extends Item {

    private final TipoPowerUp tipo;
    private float tiempoPulso;

    public PowerUpSubmarino(TipoPowerUp tipo, TextureRegion region, Pantalla pantalla, RayHandler rayHandler) {
        super(region, pantalla, 36, 36, rayHandler, obtenerColorTipo(tipo), 2.5f);
        this.tipo = tipo;
        this.tiempoPulso = 0;
    }

    private static Color obtenerColorTipo(TipoPowerUp tipo) {
        switch (tipo) {
            case TURBO:
                return new Color(1.0f, 0.6f, 0.1f, 0.9f); // Naranja ámbar cálido
            case ESCUDO:
                return new Color(0.2f, 0.7f, 1.0f, 0.9f); // Azul cian defensivo
            case LINTERNA:
            default:
                return new Color(1.0f, 1.0f, 0.4f, 0.95f); // Dorado brillante
        }
    }

    @Override
    public void actualizar(float delta) {
        super.actualizar(delta);

        if (!recolectado && luzItem != null) {
            // Efecto de pulso en el halo de luz del power-up
            tiempoPulso += delta * 4.0f;
            float factorPulso = 1.0f + MathUtils.sin(tiempoPulso) * 0.25f;
            luzItem.setDistance(2.5f * factorPulso);
        }
    }

    @Override
    public void aplicar(Jugador jugador) {
        jugador.activarPowerUp(tipo, tipo.getDuracionSegundos());
    }

    public TipoPowerUp getTipo() {
        return tipo;
    }
}
