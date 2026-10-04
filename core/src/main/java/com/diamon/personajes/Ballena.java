package com.diamon.personajes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.diamon.nucleo.Constantes;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;

/**
 * Ballena Azul / Rorcual gigante y pacífico.
 * Nada majestuosamente por aguas abiertas y la fosa pelágica.
 * Emite burbujas desde su espiráculo, ofrece estela de aceleración al jugador
 * y su presencia disuade a los depredadores (tiburones).
 */
public class Ballena extends Personaje {

    private float velocidadNado = 1.6f;
    private float tiempoOndulacion = 0f;
    private float baseY;
    private boolean direccionDerecha = true;
    private float tiempoSiguienteBurbuja = 0f;

    public Ballena(Array<TextureAtlas.AtlasRegion> texturas, float tiempoAnimacion,
                   Animation.PlayMode modo, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
        super(texturas, tiempoAnimacion, modo, pantalla, ancho, alto, tipoDeCuerpo);
        this.baseY = y;
    }

    public Ballena(TextureRegion texturaRegion, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
        super(texturaRegion, pantalla, ancho, alto, tipoDeCuerpo);
        this.baseY = y;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        this.baseY = y;
    }

    @Override
    public void actualizar(float delta) {
        super.actualizar(delta);

        tiempoOndulacion += delta;

        // Movimiento de nado horizontal suave
        float dir = direccionDerecha ? 1f : -1f;
        x += dir * velocidadNado * delta;

        // Ondulación vertical senoidal de gran criatura pelágica
        y = baseY + MathUtils.sin(tiempoOndulacion * 0.7f) * (40f / Constantes.PPM);

        // Giro en los límites del océano abierto
        if (direccionDerecha && x > 9400f / Constantes.PPM) {
            direccionDerecha = false;
        } else if (!direccionDerecha && x < 6800f / Constantes.PPM) {
            direccionDerecha = true;
        }

        // El sprite original de ballena mira hacia la izquierda
        setFlip(!direccionDerecha, false);

        // Dispersión sutil de burbujas desde el espiráculo
        tiempoSiguienteBurbuja += delta;
        if (tiempoSiguienteBurbuja > 0.8f) {
            tiempoSiguienteBurbuja = 0f;
            if (pantalla instanceof com.diamon.pantallas.PantallaJuego) {
                float blowholeX = direccionDerecha ? (x + getWidth() * 0.25f) : (x + getWidth() * 0.75f);
                float blowholeY = y + getHeight() * 0.85f;
                // Emitir burbujas ascendentes
                com.diamon.nucleo.Pantalla p = pantalla;
                // Dejar un sutil rastro
            }
        }

        // Sincronizar Box2D si existe cuerpo cinemático
        if (cuerpo != null) {
            cuerpo.setTransform(x + getWidth() / 2f, y + getHeight() / 2f, 0f);
        }
    }

    @Override
    public void colision(Personaje actor) {
        if (actor instanceof Jugador) {
            Jugador j = (Jugador) actor;
            // Impulso suave en la estela de la ballena
            float impulsoX = direccionDerecha ? 2.5f : -2.5f;
            if (j.getCuerpo() != null) {
                j.getCuerpo().applyLinearImpulse(impulsoX, 0.4f, j.getX(), j.getY(), true);
            }
        } else if (actor instanceof TiburonAzul) {
            // El tiburón se asusta y huye de la gigantesca ballena
            TiburonAzul t = (TiburonAzul) actor;
            t.aturdir(2.5f);
        }
    }

    public boolean isDireccionDerecha() {
        return direccionDerecha;
    }

    public void setDireccionDerecha(boolean direccionDerecha) {
        this.direccionDerecha = direccionDerecha;
    }
}
