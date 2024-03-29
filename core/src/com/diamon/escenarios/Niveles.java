package com.diamon.escenarios;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.diamon.datos.DatosNiveles;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Nivel;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;
import com.diamon.particulas.Particula;
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
import com.diamon.personajes.TiburonAzul;
import com.diamon.utilidades.ColicionBox2DListener;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public class Niveles extends Nivel {

	private TiledMapRenderer render;

	private float xFondo = camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2;

	private JefeUno gefeUno;

	private JefeDos gefeDos;

	private JefeTres gefeTres;

	private JefeCuatro gefeCuatro;

	private Personaje jefeNivel;

	private PointLight puntoDeLuz;

	private Particula particuala;

	public Niveles(Pantalla pantalla, Jugador jugador) {
		super(pantalla, jugador);

		int indice = 1;

		render = new OrthogonalTiledMapRenderer(mapa, 1);

		if ((datosNiveles.getNumeroNivel() <= 10)) {

			for (int i = 0; i < fondo.length; i++) {

				indice = 1;

				fondo[i] = new Fondo(new TextureRegion(recurso.get("texturas/fondo" + indice + ".png", Texture.class)),
						pantalla, Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA, Fondo.ESTATICO);

				fondo[i].setPosition(0, 0);

				personajes.add(fondo[i]);
			}

		}

		if ((datosNiveles.getNumeroNivel() >= 11) && (datosNiveles.getNumeroNivel() <= 20)) {

			indice = 2;

			for (int i = 0; i < fondo.length; i++) {

				fondo[i] = new Fondo(new TextureRegion(recurso.get("texturas/fondo" + indice + ".png", Texture.class)),
						pantalla, Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA, Fondo.ESTATICO);

				fondo[i].setPosition(0, 0);

				personajes.add(fondo[i]);
			}

		}

		if ((datosNiveles.getNumeroNivel() >= 21) && (datosNiveles.getNumeroNivel() <= 30)) {

			indice = 3;

			for (int i = 0; i < fondo.length; i++) {

				fondo[i] = new Fondo(new TextureRegion(recurso.get("texturas/fondo" + indice + ".png", Texture.class)),
						pantalla, Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA, Fondo.ESTATICO);

				fondo[i].setPosition(0, 0);

				personajes.add(fondo[i]);
			}

		}

		if ((datosNiveles.getNumeroNivel() >= 31) && (datosNiveles.getNumeroNivel() <= 40)) {

			indice = 4;

			for (int i = 0; i < fondo.length; i++) {

				fondo[i] = new Fondo(new TextureRegion(recurso.get("texturas/fondo" + indice + ".png", Texture.class)),
						pantalla, Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA, Fondo.ESTATICO);

				fondo[i].setPosition(0, 0);

				personajes.add(fondo[i]);
			}

		}

		TiburonAzul tiburon = new TiburonAzul(recurso.get("texturas/tiburon.atlas", TextureAtlas.class).getRegions(),
				0.3f, Animation.PlayMode.LOOP, pantalla, 192, 192, TiburonAzul.ESTATICO);

		tiburon.setPosition(50, 200);

		personajes.add(tiburon);

		///////////////////////

		RayHandler.setGammaCorrection(true);

		luz.setAmbientLight(0f, 0f, 0f, 0.3f);

		puntoDeLuz = new PointLight(luz, 1000, Color.BLACK, 2, 2, 4);

		luz.setShadows(true);
		puntoDeLuz.setStaticLight(false);
		puntoDeLuz.setSoft(true);

		luces.add(puntoDeLuz);

		mundoVirtual.getBodies(cuerpos);

		if (cuerpos.size > 0) {

			for (Body cuerpo : cuerpos) {

				if (!(cuerpo.getUserData() instanceof Jugador)) {

					mundoVirtual.destroyBody(cuerpo);

				} else {

					puntoDeLuz.attachToBody(cuerpo);
				}

			}

		}

		for (int i = 0; i < 10; i++) {

			PointLight punto = new PointLight(luz, 1000, Color.BLACK, 600 / Juego.UNIDAD_DEL_MUNDO,
					MathUtils.random() * 13440 / Juego.UNIDAD_DEL_MUNDO - 2, 4);

			luces.add(punto);

		}

		particuala = new Particula(recurso.get("particulas/Particle Park Flame.p", ParticleEffect.class), pantalla);

		particuala.setPosicion(400 / Juego.UNIDAD_DEL_MUNDO, 10 / Juego.UNIDAD_DEL_MUNDO);

		particuala.setEscala(3f);

		PointLight puntoL = new PointLight(luz, 1000, Color.BLACK, 8, 2, 4);

		luces.add(puntoL);

		particuala.setPuntoLuz(puntoL);

		particuala.iniciar();

		this.mundoVirtual.setContactListener(new ColicionBox2DListener());

		String numeroNivel = "Nivel " + datosNiveles.getNumeroNivel();

		for (Vector2 posicion : datosNiveles.getPosicionActores(DatosNiveles.PULPO, numeroNivel))

		{
			Pulpo actor = new Pulpo(recurso.get("texturas/pulpo.atlas", TextureAtlas.class).getRegions(), 0.07f,
					Animation.PlayMode.LOOP, pantalla, 32, 64, Pulpo.ESTATICO);

			actor.setPosition(posicion.x * Juego.UNIDAD_DEL_MUNDO, posicion.y * Juego.UNIDAD_DEL_MUNDO);

			personajes.add(actor);
		}

		for (Vector2 posicion : datosNiveles.getPosicionActores(DatosNiveles.PEZ_GLOBO_AMARILLO, numeroNivel))

		{
			PezGloboAmarillo actor = new PezGloboAmarillo(
					recurso.get("texturas/pezG.atlas", TextureAtlas.class).getRegions(), 0.1f, Animation.PlayMode.LOOP,
					pantalla, 64, 32, PezGloboAmarillo.ESTATICO);

			actor.setPosition(posicion.x * Juego.UNIDAD_DEL_MUNDO, posicion.y * Juego.UNIDAD_DEL_MUNDO);

			personajes.add(actor);
		}

		for (Vector2 posicion : datosNiveles.getPosicionActores(DatosNiveles.PEZ_GOBO_NARANJA, numeroNivel))

		{
			PezGloboNaranja actor = new PezGloboNaranja(
					recurso.get("texturas/pezGlobo.atlas", TextureAtlas.class).getRegions(), 0.1f,
					Animation.PlayMode.LOOP, pantalla, 96, 64, PezGloboNaranja.ESTATICO);

			actor.setPosition(posicion.x * Juego.UNIDAD_DEL_MUNDO, posicion.y * Juego.UNIDAD_DEL_MUNDO);

			personajes.add(actor);
		}

		for (Vector2 posicion : datosNiveles.getPosicionActores(DatosNiveles.PEZ_ANGEL, numeroNivel))

		{
			PezAngel actor = new PezAngel(recurso.get("texturas/pez1.atlas", TextureAtlas.class).getRegions(), 0.1f,
					Animation.PlayMode.LOOP, pantalla, 64, 32, PezAngel.ESTATICO);

			actor.setPosition(posicion.x * Juego.UNIDAD_DEL_MUNDO, posicion.y * Juego.UNIDAD_DEL_MUNDO);

			personajes.add(actor);
		}

		for (Vector2 posicion : datosNiveles.getPosicionActores(DatosNiveles.BOMBA, numeroNivel)) {

			Bomba actor = new Bomba(recurso.get("texturas/bomba.png", Texture.class), pantalla, 64, 64,
					Bomba.DIANAMICO);

			actor.setPosition(posicion.x * Juego.UNIDAD_DEL_MUNDO, posicion.y * Juego.UNIDAD_DEL_MUNDO);

			personajes.add(actor);

		}

		for (Vector2 posicion : datosNiveles.getPosicionActores(DatosNiveles.ALGAS, numeroNivel)) {

			Algas actor = new Algas(recurso.get("texturas/algas.png", Texture.class), pantalla, 96, 64,
					Algas.CINESTECICO);

			actor.setPosition(posicion.x * Juego.UNIDAD_DEL_MUNDO, posicion.y * Juego.UNIDAD_DEL_MUNDO);

			personajes.add(actor);

		}

		mundoVirtual.getBodies(cuerpos);

		if (cuerpos.size > 0) {

			for (Body cuerpo : cuerpos) {

				PointLight punto = new PointLight(luz, 1000, Color.BLACK, 2, 2, 2);

				punto.attachToBody(cuerpo);

				luces.add(punto);
			}

		}

		agregarGefe(datosNiveles.getNumeroNivel());

		intro();

		///////////////////////////////////// Union

		mundoVirtual.getBodies(cuerpos);

		if (cuerpos.size > 0) {

			RevoluteJointDef jointDef = new RevoluteJointDef();

			for (int i = 0; i < cuerpos.size; i++) {

				jointDef.localAnchorA.set(0.8f, 0.8f);

				jointDef.localAnchorB.set(0.8f, 0.8f);

				if (i >= 0 && i <= cuerpos.size - 2) {

					jointDef.bodyB = cuerpos.get(i);

					jointDef.bodyA = cuerpos.get(i + 1);

					mundoVirtual.createJoint(jointDef);
				}

			}

		}

	}

	public Personaje getJefeNivel() {
		return jefeNivel;
	}

	public void intro() {

		intro = (datosNiveles.getNumeroNivel() == 1);

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
					Animation.PlayMode.LOOP, pantalla, 128, 128, JefeUno.ESTATICO);

			gefeUno.setPosition(13440 - gefeUno.getWidth() * Juego.UNIDAD_DEL_MUNDO, 240);

			gefeUno.setDureza(300);

			personajes.add(gefeUno);

			jefeNivel = gefeUno;

		}

		if (numero == 20) {

			gefeDos = new JefeDos(recurso.get("texturas/pulpo.atlas", TextureAtlas.class).getRegions(), 0.1f,
					Animation.PlayMode.LOOP, pantalla, 128, 128, JefeDos.ESTATICO);

			gefeDos.setPosition(13440 - gefeDos.getWidth() * Juego.UNIDAD_DEL_MUNDO, 240);

			gefeDos.setDureza(500);

			personajes.add(gefeDos);

			jefeNivel = gefeDos;

		}

		if (numero == 30) {

			gefeTres = new JefeTres(recurso.get("texturas/pezGlobo.atlas", TextureAtlas.class).getRegions(), 0.1f,
					Animation.PlayMode.LOOP, pantalla, 128, 128, JefeTres.ESTATICO);

			gefeTres.setPosition(13440 - gefeTres.getWidth() * Juego.UNIDAD_DEL_MUNDO, 240);

			gefeTres.setDureza(700);

			personajes.add(gefeTres);

			jefeNivel = gefeTres;

		}

		if (numero == 40) {

			gefeCuatro = new JefeCuatro(recurso.get("texturas/pezGlobo.atlas", TextureAtlas.class).getRegions(), 0.1f,
					Animation.PlayMode.LOOP, pantalla, 128, 128, JefeCuatro.ESTATICO);

			gefeCuatro.setPosition(13440 - gefeCuatro.getWidth() * Juego.UNIDAD_DEL_MUNDO, 240);

			gefeCuatro.setDureza(900);

			personajes.add(gefeCuatro);

			jefeNivel = gefeCuatro;

		}

	}

	@Override
	protected void iniciar() {

		camara.position.x = Juego.ANCHO_PANTALLA / 2f / Juego.UNIDAD_DEL_MUNDO;

		camara.position.y = Juego.ANCHO_PANTALLA / 2f / Juego.UNIDAD_DEL_MUNDO;

		camara.zoom = 1;

		camara.setToOrtho(false, Juego.ANCHO_PANTALLA / Juego.UNIDAD_DEL_MUNDO,
				Juego.ALTO_PANTALLA / Juego.UNIDAD_DEL_MUNDO);

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

				if (xFondo <= camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2
						- Juego.ANCHO_PANTALLA) {

					xFondo = camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2;
				}

				fondo[0].setPosition(xFondo, 0);

				fondo[1].setPosition(xFondo + Juego.ANCHO_PANTALLA, 0);

			}

			if (dato.isFondoParallax()) {

				fondo[1].setPosition((camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);

			}

		}

		for (Personaje personaje : personajes) {

			personaje.actualizar(delta);
		}

		if (Juego.ANCHO_PANTALLA / 2 + camara.position.x * Juego.UNIDAD_DEL_MUNDO >= Juego.LARGO_NIVEL) {

			if (datosNiveles.getNumeroNivel() == 10 || datosNiveles.getNumeroNivel() == 20
					|| datosNiveles.getNumeroNivel() == 30 || datosNiveles.getNumeroNivel() == 40) {

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

				camara.position.x += (jugador.getVelocidadCamaraItem() / Juego.DELTA_A_PIXEL * delta)
						/ Juego.UNIDAD_DEL_MUNDO;

			} else {

				camara.position.x += (Juego.VELOCIDAD_CAMARA / Juego.DELTA_A_PIXEL * delta) / Juego.UNIDAD_DEL_MUNDO;

			}

		}

		luz.update();

		particuala.actualizar(delta);

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

		particuala.dibujar(pincel, delta);

		pincel.end();

		luz.setCombinedMatrix(camara);

		luz.render();

	}

	@Override
	public void guardarDatos() {
		// TODO Auto-generated method stub

	}

	@Override
	public void liberarRecursos() {

		mundoVirtual.getBodies(cuerpos);

		luces.clear();

		luz.removeAll();

		cuerpos.clear();

		personajes.clear();

		mapa.dispose();

		particuala.liberarRecursos();

	}

}
