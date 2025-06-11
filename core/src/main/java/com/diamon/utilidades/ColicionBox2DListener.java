package com.diamon.utilidades;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.diamon.nucleo.Personaje;

/**
 * Maneja los eventos de inicio de contacto para la simulación de físicas Box2D.
 * Cuando dos cuerpos físicos comienzan a colisionar, esta clase es notificada
 * y delega la lógica de respuesta de la colisión a los objetos {@link Personaje}
 * involucrados.
 */
public class ColicionBox2DListener implements ContactListener {

	public ColicionBox2DListener() {
		// Constructor por defecto.
	}

	/**
	 * Se llama cuando dos fixtures (formas) de Box2D comienzan a superponerse (colisionar).
	 * Este método extrae los objetos {@link Personaje} asociados a los cuerpos en colisión
	 * y llama a sus respectivos métodos {@code colision()} para manejar la lógica específica
	 * de la interacción.
	 *
	 * @param contact El objeto {@link Contact} que contiene información sobre la colisión.
	 */
	@Override
	public void beginContact(Contact contact) {
		// Verifica que ambos cuerpos involucrados en la colisión tengan datos de usuario de tipo Personaje.
		if (contact.getFixtureA().getBody().getUserData() instanceof Personaje
				&& contact.getFixtureB().getBody().getUserData() instanceof Personaje) {

			// Obtiene las instancias de Personaje de los datos de usuario de los cuerpos.
			Personaje persoanje1 = (Personaje) contact.getFixtureA().getBody().getUserData();
			Personaje persoanje2 = (Personaje) contact.getFixtureB().getBody().getUserData();

			// Delega la lógica de colisión a cada personaje.
			// Cada personaje sabrá cómo reaccionar a una colisión con el otro.
			persoanje1.colision(persoanje2);
			persoanje2.colision(persoanje1);
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
