package com.diamon.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;
import com.diamon.personajes.Fondo;
import com.diamon.personajes.Jugador;

public class PantallaJuego extends Pantalla {

	private Jugador jugador;
	private Fondo[] fondo;
	private float xFondo = camara.position.x - Juego.ANCHO_PANTALLA / 2;

	public PantallaJuego(Juego juego) {
		super(juego);

	}

	@Override
	public void mostrar() {

		fondo = new Fondo[2];

		for (int i = 0; i < fondo.length; i++) {

			fondo[i] = new Fondo(new TextureRegion(recurso.get("texturas/fondo4.png", Texture.class)), this);

			fondo[i].setSize(Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA);

			fondo[i].setPosition(0, 0);

			personajes.add(fondo[i]);
		}

		jugador = new Jugador(recurso.get("texturas/pez.atlas", TextureAtlas.class).getRegions(), 0.3f,
				Animation.PlayMode.LOOP, this);

		jugador.setSize(64, 64);

		personajes.add(jugador);

	}

	@Override
	public void eventos() {
		// TODO Auto-generated method stub

	}

	@Override
	public void colisiones() {

		for (int i = 0; i < personajes.size; i++) {

			Personaje personaje1 = personajes.get(i);

			Rectangle rectangulo1 = personaje1.getBoundingRectangle();

			for (int j = i + 1; j < personajes.size; j++) {

				Personaje personaje2 = personajes.get(j);

				Rectangle rectangulo2 = personaje2.getBoundingRectangle();

				if (rectangulo1.overlaps(rectangulo2)) {

					personaje1.colision(personaje2);

					personaje2.colision(personaje1);

				}
			}

			Personaje personaje = personajes.get(i);

			if (personaje.isRemover()) {

				personajes.removeIndex(i);
			}
		}

	}

	@Override
	public void actualizar(float delta) {

		xFondo -= 0.5f / Juego.DELTA_A_PIXEL * Gdx.graphics.getDeltaTime();

		if (xFondo <= -Juego.ANCHO_PANTALLA) {

			xFondo = 0;
		}

		fondo[0].setPosition(xFondo, 0);

		fondo[1].setPosition(xFondo + Juego.ANCHO_PANTALLA, 0);

		for (Personaje personaje : personajes) {

			personaje.actualizar(delta);
		}

	}

	@Override
	public void dibujar(Batch pincel, float delta) {

		pincel.begin();

		for (Personaje personaje : personajes) {

			personaje.dibujar(pincel, delta);

		}

		pincel.end();

	}

	@Override
	public void guardarDatos() {
		// TODO Auto-generated method stub

	}

	@Override
	public void liberarRecursos() {

		personajes.clear();

	}

}
