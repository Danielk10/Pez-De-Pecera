package com.diamon.utilidades;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.diamon.nucleo.Personaje;
import com.diamon.nucleo.ZonaCorriente;
import com.diamon.personajes.Jugador;

/**
 * Gestor de colisiones Box2D para físicas submarinas.
 * Maneja corrientes marinas (sensores), impactos contra rocas/terreno y contacto entre personajes.
 */
public class ColisionSubmarinaListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        Object dataA = fixA.getUserData() != null ? fixA.getUserData() : fixA.getBody().getUserData();
        Object dataB = fixB.getUserData() != null ? fixB.getUserData() : fixB.getBody().getUserData();

        // 1. Detección de Corrientes Marinas
        if (dataA instanceof Jugador && dataB instanceof ZonaCorriente) {
            ((Jugador) dataA).agregarCorriente((ZonaCorriente) dataB);
        } else if (dataB instanceof Jugador && dataA instanceof ZonaCorriente) {
            ((Jugador) dataB).agregarCorriente((ZonaCorriente) dataA);
        }

        // 2. Colisión entre Personajes (Jugador vs Enemigos / Proyectiles)
        if (dataA instanceof Personaje && dataB instanceof Personaje) {
            Personaje pA = (Personaje) dataA;
            Personaje pB = (Personaje) dataB;
            pA.colision(pB);
            pB.colision(pA);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        Object dataA = fixA.getUserData() != null ? fixA.getUserData() : fixA.getBody().getUserData();
        Object dataB = fixB.getUserData() != null ? fixB.getUserData() : fixB.getBody().getUserData();

        // Salida de zona de corriente marina
        if (dataA instanceof Jugador && dataB instanceof ZonaCorriente) {
            ((Jugador) dataA).removerCorriente((ZonaCorriente) dataB);
        } else if (dataB instanceof Jugador && dataA instanceof ZonaCorriente) {
            ((Jugador) dataB).removerCorriente((ZonaCorriente) dataA);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
