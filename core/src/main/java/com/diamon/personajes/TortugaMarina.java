package com.diamon.personajes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.diamon.nucleo.Constantes;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;

/**
 * Tortuga Marina (Carey / Laúd).
 * Nada tranquilamente por arrecifes y praderas de algas.
 * Es un aliado pacífico que otorga un resguardo visual y sensación
 * viva de navegación submarina.
 */
public class TortugaMarina extends Personaje {

    private float velocidadNado = 1.8f;
    private float tiempoOndulacion = 0f;
    private float baseY;
    private boolean direccionDerecha = true;
    private float limiteIzquierdo;
    private float limiteDerecho;

    public TortugaMarina(Array<TextureAtlas.AtlasRegion> texturas, float tiempoAnimacion,
                         Animation.PlayMode modo, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
        super(texturas, tiempoAnimacion, modo, pantalla, ancho, alto, tipoDeCuerpo);
        this.baseY = y;
        this.limiteIzquierdo = x - 400f / Constantes.PPM;
        this.limiteDerecho = x + 600f / Constantes.PPM;
    }

    public TortugaMarina(TextureRegion texturaRegion, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
        super(texturaRegion, pantalla, ancho, alto, tipoDeCuerpo);
        this.baseY = y;
        this.limiteIzquierdo = x - 400f / Constantes.PPM;
        this.limiteDerecho = x + 600f / Constantes.PPM;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        this.baseY = y;
        this.limiteIzquierdo = x - 400f / Constantes.PPM;
        this.limiteDerecho = x + 600f / Constantes.PPM;
    }

    @Override
    public void actualizar(float delta) {
        super.actualizar(delta);

        tiempoOndulacion += delta;

        // Desplazamiento horizontal
        float dir = direccionDerecha ? 1f : -1f;
        float vx = dir * velocidadNado;
        x += vx * delta;

        // Ondulación vertical rítmica
        float vy = MathUtils.cos(tiempoOndulacion * 1.4f) * 0.5f;
        y = baseY + MathUtils.sin(tiempoOndulacion * 1.4f) * (20f / Constantes.PPM);

        // Patrullaje entre límites
        if (direccionDerecha && x >= limiteDerecho) {
            direccionDerecha = false;
        } else if (!direccionDerecha && x <= limiteIzquierdo) {
            direccionDerecha = true;
        }

        // Orientación suave: la textura original mira a la DERECHA
        orientarHaciaDireccion(vx, vy, true, delta);

        if (cuerpo != null) {
            cuerpo.setTransform(x + getWidth() / 2f, y + getHeight() / 2f, getRotation() * MathUtils.degreesToRadians);
        }
    }

    @Override
    public void colision(Personaje actor) {
        if (actor instanceof Jugador) {
            Jugador j = (Jugador) actor;
            // Saludo sutil y empuje suave
            float pushX = direccionDerecha ? 1.5f : -1.5f;
            if (j.getCuerpo() != null) {
                j.getCuerpo().applyLinearImpulse(pushX, 0.2f, j.getX(), j.getY(), true);
            }
        }
    }
}
