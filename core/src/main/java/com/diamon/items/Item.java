package com.diamon.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.diamon.nucleo.Constantes;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;
import com.diamon.personajes.Jugador;

import box2dLight.PointLight;
import box2dLight.RayHandler;

/**
 * Clase base abstracta para coleccionables, perlas y power-ups submarinos.
 * Gestiona físicas como sensor de Box2D, flotación sinusoidal y punto de luz propio.
 */
public abstract class Item extends Personaje {

    protected float tiempoFlotacion;
    protected float baseYMetros;
    protected PointLight luzItem;
    protected boolean recolectado;

    public Item(Texture textura, Pantalla pantalla, float anchoPx, float altoPx, RayHandler rayHandler, Color colorLuz, float radioLuz) {
        super(textura, pantalla, anchoPx, altoPx, Personaje.ESTATICO);
        configurarSensor();
        crearLuz(rayHandler, colorLuz, radioLuz);
    }

    public Item(TextureRegion region, Pantalla pantalla, float anchoPx, float altoPx, RayHandler rayHandler, Color colorLuz, float radioLuz) {
        super(region.getTexture(), pantalla, anchoPx, altoPx, Personaje.ESTATICO);
        setRegion(region);
        configurarSensor();
        crearLuz(rayHandler, colorLuz, radioLuz);
    }

    private void configurarSensor() {
        this.recolectado = false;
        this.tiempoFlotacion = MathUtils.random(0.0f, 6.28f);

        if (cuerpo != null) {
            for (Fixture f : cuerpo.getFixtureList()) {
                f.setSensor(true);
                f.getFilterData().categoryBits = Constantes.CAT_ITEM;
                f.getFilterData().maskBits = Constantes.CAT_JUGADOR;
            }
        }
    }

    private void crearLuz(RayHandler rayHandler, Color colorLuz, float radioLuz) {
        if (rayHandler != null && colorLuz != null && radioLuz > 0) {
            luzItem = new PointLight(rayHandler, 16, colorLuz, radioLuz, getX(), getY());
            luzItem.setSoft(true);
            luzItem.setXray(true); // Atraviesa obstáculos para resaltar el coleccionable
        }
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        this.baseYMetros = this.y;
        if (luzItem != null) {
            luzItem.setPosition(this.x + getWidth() / 2f, this.y + getHeight() / 2f);
        }
    }

    @Override
    public void actualizar(float delta) {
        super.actualizar(delta);

        if (recolectado) {
            return;
        }

        // Efecto suave de flotación hidrodinámica (vaivén vertical)
        tiempoFlotacion += delta * 2.5f;
        float offsetFlotacion = MathUtils.sin(tiempoFlotacion) * 0.06f;
        this.y = baseYMetros + offsetFlotacion;
        setY(this.y);

        if (cuerpo != null) {
            cuerpo.setTransform(this.x + getWidth() / 2f, this.y + getHeight() / 2f, 0);
        }

        if (luzItem != null) {
            luzItem.setPosition(this.x + getWidth() / 2f, this.y + getHeight() / 2f);
        }
    }

    @Override
    public void dibujar(Batch pincel, float delta) {
        if (!recolectado) {
            super.dibujar(pincel, delta);
        }
    }

    @Override
    public void colision(Personaje personaje) {
        if (!recolectado && personaje instanceof Jugador) {
            recolectado = true;
            aplicar((Jugador) personaje);
            destruirLuz();
            remover();
        }
    }

    public void destruirLuz() {
        if (luzItem != null) {
            luzItem.remove();
            luzItem = null;
        }
    }

    @Override
    public void destruirCuerpo(World mundo) {
        destruirLuz();
        super.destruirCuerpo(mundo);
    }

    /**
     * Aplica el efecto del ítem sobre el jugador cuando entra en contacto.
     */
    public abstract void aplicar(Jugador jugador);
}
