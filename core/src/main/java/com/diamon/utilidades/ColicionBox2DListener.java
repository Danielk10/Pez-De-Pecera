package com.diamon.utilidades;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.diamon.nucleo.Personaje;

public class ColicionBox2DListener implements ContactListener {

	public ColicionBox2DListener() {

	}

	@Override
	public void beginContact(Contact contact) {

		if (contact.getFixtureA().getBody().getUserData() instanceof Personaje
				&& contact.getFixtureB().getBody().getUserData() instanceof Personaje) {

			Personaje persoanje1 = (Personaje) contact.getFixtureA().getBody().getUserData();

			Personaje persoanje2 = (Personaje) contact.getFixtureB().getBody().getUserData();

			// persoanje1.setRemover(true);

			// persoanje2.setRemover(true);

		}

	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

}
