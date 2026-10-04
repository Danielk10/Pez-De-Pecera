package com.diamon.personajes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;

/**
 * Ostra Marina Gigante interactiva.
 * Reposa sobre la arena del lecho marino y periódicamente se abre
 * revelando una perla brillante. El jugador puede aprovechar cuando
 * está abierta para recolectar la perla antes de que se cierre.
 */
public class OstraGigante extends Personaje {

    public enum EstadoOstra {
        CERRADA, ABRIENDO, ABIERTA, CERRANDO
    }

    private EstadoOstra estado = EstadoOstra.CERRADA;
    private float temporizadorEstado = 0f;
    private boolean perlaRecolectada = false;
    private final TextureRegion frameCerrada;
    private final TextureRegion frameSemiAbierta;
    private final TextureRegion frameAbierta;

    public OstraGigante(Array<TextureAtlas.AtlasRegion> texturas, float duracionFrame,
                        Animation.PlayMode modo, Pantalla pantalla,
                        float ancho, float alto, int tipoDeCuerpo) {
        this(texturas, pantalla, ancho, alto, tipoDeCuerpo);
    }

    public OstraGigante(Array<TextureAtlas.AtlasRegion> texturas, Pantalla pantalla,
                        float ancho, float alto, int tipoDeCuerpo) {
        super(texturas, 0.2f, Animation.PlayMode.NORMAL, pantalla, ancho, alto, tipoDeCuerpo);
        if (texturas != null && texturas.size >= 3) {
            frameCerrada = texturas.get(0);
            frameSemiAbierta = texturas.get(1);
            frameAbierta = texturas.get(2);
        } else {
            frameCerrada = null;
            frameSemiAbierta = null;
            frameAbierta = null;
        }
        setRegionActual(frameCerrada);
    }

    private void setRegionActual(TextureRegion region) {
        if (region != null) {
            setRegion(region);
        }
    }

    @Override
    public void actualizar(float delta) {
        super.actualizar(delta);
        temporizadorEstado += delta;

        switch (estado) {
        case CERRADA:
            setRegionActual(frameCerrada);
            if (temporizadorEstado >= 4.0f) {
                estado = EstadoOstra.ABRIENDO;
                temporizadorEstado = 0f;
            }
            break;

        case ABRIENDO:
            setRegionActual(frameSemiAbierta);
            if (temporizadorEstado >= 0.7f) {
                estado = EstadoOstra.ABIERTA;
                temporizadorEstado = 0f;
            }
            break;

        case ABIERTA:
            setRegionActual(frameAbierta);
            if (temporizadorEstado >= 3.5f) {
                estado = EstadoOstra.CERRANDO;
                temporizadorEstado = 0f;
            }
            break;

        case CERRANDO:
            setRegionActual(frameSemiAbierta);
            if (temporizadorEstado >= 0.35f) {
                estado = EstadoOstra.CERRADA;
                temporizadorEstado = 0f;
            }
            break;
        }
    }

    @Override
    public void colision(Personaje actor) {
        if (actor instanceof Jugador && estado == EstadoOstra.ABIERTA && !perlaRecolectada) {
            Jugador j = (Jugador) actor;
            j.agregarPuntos(250);
            j.recuperarVida(1);
            perlaRecolectada = true;
            activarHitFlash(0.2f, new Color(1f, 0.85f, 0.2f, 1f));

            // La ostra se cierra de inmediato tras tomar la perla
            estado = EstadoOstra.CERRANDO;
            temporizadorEstado = 0f;

            // Ondas de burbujas doradas
            if (pantalla instanceof com.diamon.pantallas.PantallaJuego) {
                ((com.diamon.pantallas.PantallaJuego) pantalla).emitirSonar(x + getWidth() / 2f, y + getHeight() / 2f);
            }
        }
    }

    public boolean isPerlaRecolectada() {
        return perlaRecolectada;
    }

    public EstadoOstra getEstado() {
        return estado;
    }
}
