package com.diamon.escenarios;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.diamon.datos.Dato;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Nivel;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;
import com.diamon.personajes.Algas;
import com.diamon.personajes.Bomba;
import com.diamon.personajes.Fondo;
import com.diamon.personajes.JefeCuatro;
import com.diamon.personajes.JefeDos;
import com.diamon.personajes.JefeTres;
import com.diamon.personajes.JefeUno;
import com.diamon.personajes.Jugador;
import com.diamon.personajes.PezAngel;
import com.diamon.personajes.PezGloboAmarillo;
import com.diamon.personajes.PezGloboNaranja;
import com.diamon.personajes.Pulpo;

public class Niveles extends Nivel {

	private TiledMapRenderer render;

	private float xFondo = camara.position.x - Juego.ANCHO_PANTALLA / 2;

	private JefeUno gefeUno;

	private JefeDos gefeDos;

	private JefeTres gefeTres;

	private JefeCuatro gefeCuatro;

	private Personaje jefeNivel;

	public Niveles(Pantalla pantalla, Jugador jugador) {
		super(pantalla, jugador);

		int indice = 1;

		render = new OrthogonalTiledMapRenderer(mapa, 1);

		if ((dato.getNumeroNivel() <= 10)) {

			for (int i = 0; i < fondo.length; i++) {

				indice = 1;

				fondo[i] = new Fondo(new TextureRegion(recurso.get("texturas/fondo" + indice + ".png", Texture.class)),
						pantalla);

				fondo[i].setSize(Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA);

				fondo[i].setPosition(0, 0);

				personajes.add(fondo[i]);
			}

		}

		if ((dato.getNumeroNivel() >= 11) && (dato.getNumeroNivel() <= 20)) {

			indice = 2;

			for (int i = 0; i < fondo.length; i++) {

				fondo[i] = new Fondo(new TextureRegion(recurso.get("texturas/fondo" + indice + ".png", Texture.class)),
						pantalla);

				fondo[i].setSize(Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA);

				fondo[i].setPosition(0, 0);

				personajes.add(fondo[i]);
			}

		}

		if ((dato.getNumeroNivel() >= 21) && (dato.getNumeroNivel() <= 30)) {

			indice = 3;

			for (int i = 0; i < fondo.length; i++) {

				fondo[i] = new Fondo(new TextureRegion(recurso.get("texturas/fondo" + indice + ".png", Texture.class)),
						pantalla);

				fondo[i].setSize(Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA);

				fondo[i].setPosition(0, 0);

				personajes.add(fondo[i]);
			}

		}

		if ((dato.getNumeroNivel() >= 31) && (dato.getNumeroNivel() <= 40)) {

			indice = 4;

			for (int i = 0; i < fondo.length; i++) {

				fondo[i] = new Fondo(new TextureRegion(recurso.get("texturas/fondo" + indice + ".png", Texture.class)),
						pantalla);

				fondo[i].setSize(Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA);

				fondo[i].setPosition(0, 0);

				personajes.add(fondo[i]);
			}

		}

		String numeroNivel = "Nivel " + dato.getNumeroNivel();

		for (Vector2 posicion : dato.getPosicionActores(Dato.PULPO, numeroNivel))

		{
			Pulpo actor = new Pulpo(recurso.get("texturas/pulpo.atlas", TextureAtlas.class).getRegions(), 0.07f,
					Animation.PlayMode.LOOP, pantalla);

			actor.setSize(32, 64);

			actor.setPosition(posicion.x, posicion.y);

			personajes.add(actor);
		}

		for (Vector2 posicion : dato.getPosicionActores(Dato.PEZ_GLOBO_AMARILLO, numeroNivel))

		{
			PezGloboAmarillo actor = new PezGloboAmarillo(
					recurso.get("texturas/pezG.atlas", TextureAtlas.class).getRegions(), 0.1f, Animation.PlayMode.LOOP,
					pantalla);

			actor.setSize(64, 32);

			actor.setPosition(posicion.x, posicion.y);

			personajes.add(actor);
		}

		for (Vector2 posicion : dato.getPosicionActores(Dato.PEZ_GOBO_NARANJA, numeroNivel))

		{
			PezGloboNaranja actor = new PezGloboNaranja(
					recurso.get("texturas/pezGlobo.atlas", TextureAtlas.class).getRegions(), 0.1f,
					Animation.PlayMode.LOOP, pantalla);

			actor.setSize(96, 64);

			actor.setPosition(posicion.x, posicion.y);

			personajes.add(actor);
		}

		for (Vector2 posicion : dato.getPosicionActores(Dato.PEZ_ANGEL, numeroNivel))

		{
			PezAngel actor = new PezAngel(recurso.get("texturas/pez1.atlas", TextureAtlas.class).getRegions(), 0.1f,
					Animation.PlayMode.LOOP, pantalla);

			actor.setSize(64, 32);

			actor.setPosition(posicion.x, posicion.y);

			personajes.add(actor);
		}

		for (Vector2 posicion : dato.getPosicionActores(Dato.BOMBA, numeroNivel)) {

			Bomba actor = new Bomba(recurso.get("texturas/bomba.png", Texture.class), pantalla);

			actor.setSize(64, 64);

			actor.setPosition(posicion.x, posicion.y);

			personajes.add(actor);

		}

		for (Vector2 posicion : dato.getPosicionActores(Dato.ALGAS, numeroNivel)) {

			Algas actor = new Algas(recurso.get("texturas/algas.png", Texture.class), pantalla);

			actor.setSize(96, 64);

			actor.setPosition(posicion.x, posicion.y);

			personajes.add(actor);

		}

		agregarGefe(dato.getNumeroNivel());

		intro();
	}

	public Personaje getJefeNivel() {
		return jefeNivel;
	}

	public void intro() {

		intro = (dato.getNumeroNivel() == 1);

		this.moverCamara = !intro;

		if (intro) {

			jugador.setPosition(0, 384);

			jugador.setIntro(!intro);

			nivel.addAction(Actions.sequence(Actions.delay(3f), Actions.run(new Runnable() {

				public void run() {

				}
			})));

			nivel.addAction(Actions.sequence(Actions.delay(4f), Actions.run(new Runnable() {

				public void run() {

					intro = false;

					moverCamara = !intro;

					jugador.setIntro(!intro);

				}
			})));

		} else {

			jugador.setIntro(!intro);

		}

	}

	private void agregarGefe(int numero) {

		if (numero == 10) {

			gefeUno = new JefeUno(recurso.get("texturas/pezGlobo.atlas", TextureAtlas.class).getRegions(), 0.1f,
					Animation.PlayMode.LOOP, pantalla);

			gefeUno.setSize(128, 128);

			gefeUno.setPosition(13440 - gefeUno.getWidth(), 240);

			gefeUno.setDureza(300);

			personajes.add(gefeUno);

			jefeNivel = gefeUno;

		}

		if (numero == 20) {

			gefeDos = new JefeDos(recurso.get("texturas/pulpo.atlas", TextureAtlas.class).getRegions(), 0.1f,
					Animation.PlayMode.LOOP, pantalla);

			gefeDos.setSize(128, 128);

			gefeDos.setPosition(13440 - gefeDos.getWidth(), 240);

			gefeDos.setDureza(500);

			personajes.add(gefeDos);

			jefeNivel = gefeDos;

		}

		if (numero == 30) {

			gefeTres = new JefeTres(recurso.get("texturas/pezGlobo.atlas", TextureAtlas.class).getRegions(), 0.1f,
					Animation.PlayMode.LOOP, pantalla);

			gefeTres.setSize(128, 128);

			gefeTres.setPosition(13440 - gefeTres.getWidth(), 240);

			gefeTres.setDureza(700);

			personajes.add(gefeTres);

			jefeNivel = gefeTres;

		}

		if (numero == 40) {

			gefeCuatro = new JefeCuatro(recurso.get("texturas/pezGlobo.atlas", TextureAtlas.class).getRegions(), 0.1f,
					Animation.PlayMode.LOOP, pantalla);

			gefeCuatro.setSize(128, 128);

			gefeCuatro.setPosition(13440 - gefeCuatro.getWidth(), 240);

			gefeCuatro.setDureza(900);

			personajes.add(gefeCuatro);

			jefeNivel = gefeCuatro;

		}

	}

	@Override
	protected void iniciar() {

		camara.position.x = Juego.ANCHO_PANTALLA / 2;

		camara.position.y = Juego.ANCHO_PANTALLA / 2;

		camara.zoom = 1;

		camara.setToOrtho(false, Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA);

		camara.update();

		pincel.setProjectionMatrix(camara.combined);

		moverCamara = true;

		jugador.setPosition(0, 384);

		jugador.setVivo(true);

		jugador.setFinNivel(false);

		jugador.setTerminarNivel(false);

		jugador.resetearJugador();

		personajes.add(jugador);
	}

	@Override
	public void actualizar(float delta) {

		if (!jugador.isFinNivel() && jugador.isVivo()) {

			if (dato.isFondoScroll()) {

				if (xFondo <= camara.position.x - Juego.ANCHO_PANTALLA / 2 - Juego.ANCHO_PANTALLA) {

					xFondo = camara.position.x - Juego.ANCHO_PANTALLA / 2;
				}

				fondo[0].setPosition(xFondo, 0);

				fondo[1].setPosition(xFondo + Juego.ANCHO_PANTALLA, 0);

			}

			if (dato.isFondoParallax()) {

				fondo[1].setPosition((camara.position.x - Juego.ANCHO_PANTALLA / 2), 0);

			}

		}

		for (Personaje personaje : personajes) {

			personaje.actualizar(delta);
		}

		if (Juego.ANCHO_PANTALLA / 2 + camara.position.x >= Juego.LARGO_NIVEL) {

			if (dato.getNumeroNivel() == 10 || dato.getNumeroNivel() == 20 || dato.getNumeroNivel() == 30
					|| dato.getNumeroNivel() == 40) {

				jugador.setGefe(true);

				moverCamara = false;

				if (gefeUno != null) {

					if (gefeUno.getDureza() <= 0) {

						gefeUno.setVivo(false);

						if (!gefeUno.isVivo()) {

							jugador.setGefe(false);

							jugador.setFinNivel(true);
						}

					}

				}

				if (gefeDos != null) {

					if (gefeDos.getDureza() <= 0) {

						gefeDos.setVivo(false);

						if (!gefeDos.isVivo()) {

							jugador.setGefe(false);

							jugador.setFinNivel(true);
						}

					}

				}

				if (gefeTres != null) {

					if (gefeTres.getDureza() <= 0) {

						gefeTres.setVivo(false);

						if (!gefeTres.isVivo()) {

							jugador.setGefe(false);

							jugador.setFinNivel(true);
						}

					}

				}

				if (gefeCuatro != null) {

					if (gefeCuatro.getDureza() <= 0) {

						gefeCuatro.setVivo(false);

						if (!gefeCuatro.isVivo()) {

							jugador.setGefe(false);

							jugador.setFinNivel(true);
						}

					}

				}

			} else {

				moverCamara = false;

				jugador.setFinNivel(true);

			}

		}

		if (!jugador.isVivo()) {

			moverCamara = false;
		}

		if (moverCamara) {

			if (jugador.isItemVelocidad()) {

				camara.position.x += jugador.getVelocidadCamaraItem() / Juego.DELTA_A_PIXEL * delta;

			} else {

				camara.position.x += Juego.VELOCIDAD_CAMARA / Juego.DELTA_A_PIXEL * delta;

			}

		}

	}

	@Override
	public void dibujar(Batch pincel, float delta) {

		pincel.begin();

		for (Personaje personaje : personajes) {

			if (personaje instanceof Fondo) {

				personaje.dibujar(pincel, delta);
			}
		}

		pincel.end();

		render.setView(camara);

		render.render();

		pincel.begin();

		for (Personaje personaje : personajes) {

			if (!(personaje instanceof Fondo)) {

				personaje.dibujar(pincel, delta);
			}
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

		mapa.dispose();

	}

}
