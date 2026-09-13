package com.diamon.nucleo;

/**
 * Representa una zona de corriente marina con vector de fuerza (forceX, forceY).
 * Asociada a sensores Box2D creados desde capas de objetos en Tiled (.tmx).
 */
public class ZonaCorriente {

    private final float forceX;
    private final float forceY;

    public ZonaCorriente(float forceX, float forceY) {
        this.forceX = forceX;
        this.forceY = forceY;
    }

    public float getForceX() {
        return forceX;
    }

    public float getForceY() {
        return forceY;
    }

    public com.badlogic.gdx.math.Vector2 getFuerza() {
        return new com.badlogic.gdx.math.Vector2(forceX, forceY);
    }
}
