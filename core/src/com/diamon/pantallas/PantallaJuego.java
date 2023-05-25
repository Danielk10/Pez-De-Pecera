package com.diamon.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.diamon.escenarios.Niveles;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Nivel;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;
import com.diamon.personajes.Cursor;
import com.diamon.personajes.Jugador;
import com.diamon.utilidades.EditorNivel;

import box2dLight.Light;

public class PantallaJuego extends Pantalla {

	private Nivel mundo;

	private Jugador jugador;

	private Image pausa;

	private TextButton reanudar;

	private Image menuPausa;

	private TextButton menu;

	private TextButton editarNivel;

	private TextButton terminarEdicion;

	private volatile boolean pausar;

	private Music[] musica;

	private Music musicaGefe;

	private int numeroNivel;

	private float tiempoCuadro;

	private boolean editar;

	private EditorNivel editor;

	private Cursor cursor;

	private int puntos;

	private boolean escape;

	private Label fps;

	private Label textoPuntos;

	private Label textoNumeroNivel;

	private Label textoVida;

	private Label textoBomba;

	private Label textoMisil;

	private Label textoPausa;

	private Label gameOver;

	private boolean game;

	private int indice;

	private boolean gefe;

	private float tiempoEspera;

	private boolean inmunidadJugador;

	public PantallaJuego(Juego juego) {
		super(juego);

	}

	@SuppressWarnings("static-access")
	@Override
	public void mostrar() {

		if (publicidad != null) {

			publicidad.mostrarBanner();
		}

		indice = 0;

		musica = new Music[4];

		escape = true;

		game = true;

		if (!dato.isDiparoAutomatico()) {

			if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

				Gdx.graphics.setCursor(Gdx.graphics.newCursor(new Pixmap(1, 1, Pixmap.Format.RGBA8888), 0, 0));

			}

		}

		if (dato.isPartida()) {

			dato.setNumeroNivel(1);

		}

		numeroNivel = dato.getNumeroNivel();

		fps = new Label("", recurso.get("uis/general/uiskin.json", Skin.class));

		fps.setPosition(580, 12);

		nivel.addActor(fps);

		gameOver = new Label("Fin del Juego", recurso.get("uis/general/uiskin.json", Skin.class));

		gameOver.setPosition((Juego.ANCHO_PANTALLA / 2 - 64), Juego.ALTO_PANTALLA / 2);

		textoPuntos = new Label("", recurso.get("uis/general/uiskin.json", Skin.class));

		textoPuntos.setPosition(8, 12);

		nivel.addActor(textoPuntos);

		textoNumeroNivel = new Label("", recurso.get("uis/general/uiskin.json", Skin.class));

		textoNumeroNivel.setPosition(112, 12);

		nivel.addActor(textoNumeroNivel);

		textoVida = new Label("", recurso.get("uis/general/uiskin.json", Skin.class));

		textoVida.setSize(32, 32);

		textoVida.setPosition(66, 450);

		nivel.addActor(textoVida);

		textoBomba = new Label("", recurso.get("uis/general/uiskin.json", Skin.class));

		textoBomba.setSize(32, 32);

		textoBomba.setPosition(115, 450);

		nivel.addActor(textoBomba);

		textoMisil = new Label("", recurso.get("uis/general/uiskin.json", Skin.class));

		textoMisil.setSize(32, 32);

		textoMisil.setPosition(148, 450);

		nivel.addActor(textoMisil);

		pausa = new Image(recurso.get("texturas/pausa.png", Texture.class));

		pausa.setSize(64, 64);

		pausa.setPosition(576, 416);

		textoPausa = new Label("Pausado", recurso.get("uis/general/uiskin.json", Skin.class));

		textoPausa.setPosition(264, 300);

		editarNivel = new TextButton("Editar Niveles", recurso.get("uis/general/uiskin.json", Skin.class));

		editarNivel.setSize(213.0F, 32);

		editarNivel.setPosition(213.0F, 260);

		editarNivel.setColor(1.0F, 1.0F, 1.0F, 0.0F);

		reanudar = new TextButton("Reanudar", recurso.get("uis/general/uiskin.json", Skin.class));

		reanudar.setSize(213.0F, 32);

		menu = new TextButton("Menu", recurso.get("uis/general/uiskin.json", Skin.class));

		menu.setSize(213.0F, 32);

		if (dato.isEditor()) {

			reanudar.setPosition(213.0F, 212);

			menu.setPosition(213.0F, 164);

		}

		if (!dato.isEditor()) {

			reanudar.setPosition(213.0F, 248);

			menu.setPosition(213.0F, 200);

		}

		menuPausa = new Image(recurso.get("texturas/menu.png", Texture.class));

		menuPausa.setSize(320.0F, 240.0F);

		menuPausa.setPosition(160.0F, 120.0F);

		terminarEdicion = new TextButton("Terminar", recurso.get("uis/general/uiskin.json", Skin.class));

		terminarEdicion.setSize(96, 32);

		terminarEdicion.setPosition(528, 16);

		terminarEdicion.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		menuPausa.setColor(1.0F, 1.0F, 1.0F, 0.0F);

		reanudar.setColor(1.0F, 1.0F, 1.0F, 0.0F);

		menu.setColor(1.0F, 1.0F, 1.0F, 0.0F);

		pausa.setColor(1.0F, 1.0F, 1.0F, 0.9F);

		textoPausa.setColor(1.0F, 1.0F, 1.0F, 0.0F);

		ventanaDePausa(false, false);

		if (Gdx.app.getType() == Gdx.app.getType().Android) {

			nivel.addActor(pausa);

		}

		nivel.addActor(menuPausa);

		nivel.addActor(reanudar);

		nivel.addActor(menu);

		if (dato.isEditor()) {

			nivel.addActor(editarNivel);

		}

		nivel.addActor(textoPausa);

		jugador = new Jugador(recurso.get("texturas/pez.atlas", TextureAtlas.class).getRegions(), 0.3f,
				Animation.PlayMode.LOOP, this, 64, 64, Jugador.ESTATICO);

		mundo = new Niveles(this, jugador);

		jugador.setTerminarNivel(true);

		cursor = new Cursor(recurso.get("texturas/invisible.png", Texture.class), this, 16, 16, Cursor.ESTATICO);

		cursor.setPosition(0, 0);

		pausar = false;

		editar = false;

		musica[0] = recurso.get("audios/musica.ogg", Music.class);
		musica[1] = recurso.get("audios/musica.ogg", Music.class);
		musica[2] = recurso.get("audios/musica.ogg", Music.class);
		musica[3] = recurso.get("audios/musica.ogg", Music.class);

		musicaGefe = recurso.get("audios/moustro.ogg", Music.class);

		if (dato.isSonido()) {

			AgregarMusica();

		}

		editor = new EditorNivel(nivel, configuracion, dato, camara, personajes, this, recurso, cursor);

		puntos = dato.getPuntos();

	}

	private void AgregarMusica() {

		if (musicaGefe != null) {

			musicaGefe.stop();
		}

		if (musica[indice] != null) {

			musica[indice].stop();

		}

		if ((dato.getNumeroNivel() <= 10)) {

			indice = 0;

		}

		if ((dato.getNumeroNivel() >= 11) && (dato.getNumeroNivel() <= 20)) {

			indice = 1;

		}

		if ((dato.getNumeroNivel() >= 21) && (dato.getNumeroNivel() <= 30)) {

			indice = 2;

		}

		if ((dato.getNumeroNivel() >= 31) && (dato.getNumeroNivel() <= 40)) {

			indice = 3;

		}

		if (!mundo.isIntro())

		{

			musica[indice].setLooping(true);

			musica[indice].play();

		}

	}

	@SuppressWarnings("static-access")
	private void ventanaDePausa(boolean visible, boolean animated) {

		float alphaTo = visible ? 0.8f : 0;

		float duration = animated ? 1 : 0;

		Touchable touchEnabled = visible ? Touchable.enabled : Touchable.disabled;

		menuPausa.addAction(Actions.sequence(Actions.touchable(touchEnabled), Actions.alpha(alphaTo, duration)));

		menu.addAction(Actions.sequence(Actions.touchable(touchEnabled), Actions.alpha(alphaTo, duration)));

		reanudar.addAction(Actions.sequence(Actions.touchable(touchEnabled), Actions.alpha(alphaTo, duration)));

		editarNivel.addAction(Actions.sequence(Actions.touchable(touchEnabled), Actions.alpha(alphaTo, duration)));

		textoPausa.addAction(Actions.sequence(Actions.touchable(touchEnabled), Actions.alpha(alphaTo, duration)));

		if (visible) {

			if (Gdx.app.getType() == Gdx.app.getType().Android) {

				pausa.remove();

			}

		} else {
			nivel.addAction(Actions.sequence(Actions.delay(duration), Actions.run(new Runnable() {

				public void run() {

					if (Gdx.app.getType() == Gdx.app.getType().Android) {

						nivel.addActor(pausa);

					}

					escape = true;

				}
			})));

		}

	}

	@Override
	public void eventos() {

		pausa.addListener(new ClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void clicked(InputEvent event, float x, float y) {

				if (!mundo.isIntro())

				{

					if (!pausar) {

						if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

							Gdx.graphics.setCursor(
									Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("textura/cursor.png")), 0, 0));

						}

						mundo.setMoverCamara(false);

						if (dato.isSonido())

						{

							if (jugador.isGefe())

							{
								if (musicaGefe != null) {

									musicaGefe.pause();
								}

							} else {

								musica[indice].pause();

							}

						}

						ventanaDePausa(true, false);

					}

					pausar = true;

				}

				super.clicked(event, x, y);
			}
		});

		editarNivel.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				pausar = false;

				mundo.setMoverCamara(true);

				editor.agregarUI();

				if (dato.isSonido())

				{

					musica[indice].stop();

				}

				editor.setTerminar(false);

				jugador.setTerminarNivel(true);

				editar = true;

				ventanaDePausa(false, true);

				nivel.addActor(terminarEdicion);

				super.clicked(event, x, y);
			}
		});

		terminarEdicion.addListener(new ClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void clicked(InputEvent event, float x, float y) {

				if (!dato.isDiparoAutomatico()) {

					if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

						Gdx.graphics.setCursor(Gdx.graphics.newCursor(new Pixmap(1, 1, Pixmap.Format.RGBA8888), 0, 0));

					}

				}

				if (Gdx.app.getType() == Gdx.app.getType().Android) {

					nivel.addActor(pausa);

				}

				if (dato.isSonido())

				{

					musica[indice].play();

				}

				editor.setTerminar(true);

				editar = false;

				configuracion.escribirDatos(dato);

				terminarEdicion.remove();

				editor.eliminarUI();

				numeroNivel = dato.getNumeroNivel();

				jugador.setTerminarNivel(true);

				super.clicked(event, x, y);
			}
		});

		reanudar.addListener(new ClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void clicked(InputEvent event, float x, float y) {

				pausar = false;

				if (!pausar) {

					if (!dato.isDiparoAutomatico()) {

						if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

							Gdx.graphics
									.setCursor(Gdx.graphics.newCursor(new Pixmap(1, 1, Pixmap.Format.RGBA8888), 0, 0));

						}

					}
					mundo.setMoverCamara(true);

					if (dato.isSonido())

					{

						if (jugador.isGefe())

						{

							if (musicaGefe != null) {

								musicaGefe.play();

							}

						} else {

							musica[indice].play();

						}

					}

					ventanaDePausa(false, true);
				}

				super.clicked(event, x, y);
			}
		});

		menu.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				puntos = 0;

				if (publicidad != null) {

					publicidad.ocultarBanner();
				}

				juego.setScreen(new PantallaMenu(juego));

				super.clicked(event, x, y);
			}
		});

		if (!pausar) {

			nivel.addListener(new InputListener() {

				@Override
				public boolean handle(Event ev) {

					return super.handle(ev);
				}

				@Override
				public boolean touchDown(InputEvent ev, float x, float y, int puntero, int boton) {

					cursor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), y);

					editor.toquePresionado(ev, x, y, puntero, boton);

					if (mundo.isIntro()) {

						return true;

					} else {

						return jugador.toquePresionado(ev, x, y, puntero, boton);

					}

				}

				@Override
				public void touchUp(InputEvent ev, float x, float y, int puntero, int boton) {

					editor.toqueLevantado(ev, x, y, puntero, boton);

					if (!mundo.isIntro()) {

						jugador.toqueLevantado(ev, x, y, puntero, boton);

					}

				}

				@Override
				public void touchDragged(InputEvent ev, float x, float y, int puntero) {

					if (!mundo.isIntro()) {

						jugador.toqueDeslizando(ev, x, y, puntero);

					}

					editor.toqueDeslizando(ev, x, y, puntero);

					cursor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), y);

				}

				@Override
				public boolean mouseMoved(InputEvent ev, float x, float y) {

					if (!mundo.isIntro()) {

						jugador.ratonMoviendo(ev, x, y);

					}

					cursor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), y);

					editor.ratonMoviendo(ev, x, y);

					return super.mouseMoved(ev, x, y);
				}

				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

					super.enter(event, x, y, pointer, fromActor);
				}

				@Override
				public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

					super.exit(event, x, y, pointer, toActor);
				}

				@SuppressWarnings("static-access")
				@Override
				public boolean keyDown(InputEvent ev, int codigoTecla) {

					editor.teclaPresionada(ev, codigoTecla);

					if (!escape)

					{

						if (Keys.ESCAPE == codigoTecla) {

							pausar = false;

							if (!pausar) {

								if (!dato.isDiparoAutomatico()) {

									if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

										Gdx.graphics.setCursor(
												Gdx.graphics.newCursor(new Pixmap(1, 1, Pixmap.Format.RGBA8888), 0, 0));

									}

								}
								mundo.setMoverCamara(true);

								if (dato.isSonido())

								{

									if (jugador.isGefe())

									{

										if (musicaGefe != null) {

											musicaGefe.play();

										}

									} else {

										musica[indice].play();

									}

								}

								ventanaDePausa(false, true);
							}

						}

					}

					if (escape)

					{

						if (!mundo.isIntro())

						{

							if (Keys.ESCAPE == codigoTecla) {

								escape = false;

								if (!pausar) {

									if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

										Gdx.graphics.setCursor(Gdx.graphics.newCursor(
												new Pixmap(Gdx.files.internal("texturas/cursor.png")), 0, 0));
									}

									mundo.setMoverCamara(false);

									if (dato.isSonido())

									{

										if (jugador.isGefe())

										{

											if (musicaGefe != null) {

												musicaGefe.pause();
											}

										} else {

											musica[indice].pause();

										}

									}

									ventanaDePausa(true, false);

								}
								pausar = true;
							}

						}

					}

					if (mundo.isIntro()) {

						return true;

					} else {

						return jugador.teclaPresionada(ev, codigoTecla);

					}

				}

				@Override
				public boolean keyUp(InputEvent ev, int codigoTecla) {

					editor.teclaLevantada(ev, codigoTecla);

					if (mundo.isIntro()) {

						return true;

					} else {

						return jugador.teclaLevantada(ev, codigoTecla);

					}

				}

				@Override
				public boolean keyTyped(InputEvent event, char character) {

					return super.keyTyped(event, character);
				}
			});
		}

	}

	@Override
	public void colisiones() {

		for (int i = 0; i < personajes.size; i++) {

			if (personajes.get(i) instanceof Jugador) {

				puntos += ((Jugador) personajes.get(i)).getPuntos();

				((Jugador) personajes.get(i)).setPuntos(0);

			}

			Personaje personaje1 = personajes.get(i);

			Rectangle rectangulo1 = personaje1.getBoundingRectangle();

			for (int j = i + 1; j < personajes.size; j++) {

				Personaje personaje2 = personajes.get(j);

				Rectangle rectangulo2 = personaje2.getBoundingRectangle();

				if (rectangulo1.overlaps(rectangulo2)) {

					if (!dato.isPrueba()) {

						if (!jugador.isFinNivel()) {

							personaje1.colision(personaje2);

							personaje2.colision(personaje1);

						}

					}

				}
			}

			Personaje personaje = personajes.get(i);

			if (personaje.isRemover()) {

				// Luz
				for (Light puntoLuz : luces) {

					if (puntoLuz.getBody() != null) {

						Body cu = puntoLuz.getBody();

						if (cu.getUserData() instanceof Personaje) {

							Personaje p = (Personaje) cu.getUserData();

							if (p.isRemover()) {

								puntoLuz.remove();

							}

						}

					}

				}

				// Box2D
				mundoVirtual.getBodies(cuerpos);

				if (cuerpos.size > 0) {

					for (Body cuerpo : cuerpos) {

						if (cuerpo.getUserData() instanceof Personaje) {

							if (((Personaje) cuerpo.getUserData()).isRemover()) {

								mundoVirtual.destroyBody(cuerpo);
							}

						}

					}

				}

				personajes.removeIndex(i);

			}
		}

	}

	@SuppressWarnings("static-access")
	@Override
	public void actualizar(float delta) {

		//////////////////

		mundoVirtual.getBodies(cuerpos); // Ojo recusos

		if (pausar&&!editar) { // Box2D Ojo

			if (cuerpos.size > 0) {

				for (Body cuerpo : cuerpos) {

					cuerpo.setActive(false);

					cuerpo.setBullet(false);

					cuerpo.setAwake(false);

				}

			}

		} else {

			if (cuerpos.size > 0) {

				for (Body cuerpo : cuerpos) {

					cuerpo.setActive(true);

					cuerpo.setBullet(true);

					cuerpo.setAwake(true);

				}

			}

		}

		if (editar&&!pausar) {

			if (cuerpos.size > 0) {

				for (Body cuerpo : cuerpos) {

					cuerpo.setActive(false);

					cuerpo.setBullet(false);

					cuerpo.setAwake(false);


				}

			}

		} else {

			if (cuerpos.size > 0) {

				for (Body cuerpo : cuerpos) {

					cuerpo.setActive(true);

					cuerpo.setBullet(true);

					cuerpo.setAwake(true);

				}

			}

		}

		///////////////////

		if (!pausar) {

			if (!editar) {

				mundo.actualizar(delta);

			}

		}

		if (editar) {

			editor.actualizar();

		}

		if (jugador.isVivo()) {

			if (jugador.isGefe()) {

				if (mundo != null) {

					if (((Niveles) mundo).getJefeNivel() != null) {

						if (((Niveles) mundo).getJefeNivel().getDureza() <= 0) {

						} else {

						}

					}

				}

			} else {

				if (jugador.isFinNivel()) {

				}

			}

		}

		if (!jugador.isVivo()) {

			if (jugador.isFinNivel()) {

				if (dato.getNumeroNivel() != 40) {

					dato.anadirPuntuaciones(puntos, dato.getNumeroNivel(), "Gana");

				}

			}

			if (dato.isSonido())

			{

				if (musica[indice].isPlaying())

				{

					musica[indice].stop();

				}

				if (jugador.isGefe() && musicaGefe.isPlaying())

				{

					if (musicaGefe != null) {

						musicaGefe.stop();

					}

				}

			}

			tiempoCuadro += delta;

			if (game) {

				nivel.addActor(gameOver);

				game = false;
			}

			if (tiempoCuadro / 0.6f >= 1) {

				nivel.addAction(Actions.sequence(Actions.delay(1f), Actions.run(new Runnable() {

					public void run() {

						if (dato.getNumeroNivel() == 40 && jugador.isFinNivel()) {

							dato.setPuntos(0);

							dato.setVidas(3);

							dato.setMisiles(10);

							dato.setBombas(10);

							dato.setContinuar(false);

							dato.setPez(1);

							dato.setNumeroSatelite(0);

							dato.anadirPuntuaciones(puntos, dato.getNumeroNivel(), "Gana");

							dato.setNumeroNivel(1);

							dato.setEditor(true);

							if (publicidad != null) {

								publicidad.ocultarBanner();
							}

							if (dato.isSonido())

							{

							}
							if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

								Gdx.graphics.setCursor(Gdx.graphics
										.newCursor(new Pixmap(Gdx.files.internal("textura/cursor.png")), 0, 0));

							}

							juego.setScreen(new PantallaCreditos(juego));

						} else {

							dato.setPuntos(0);

							dato.setVidas(3);

							dato.setMisiles(10);

							dato.setBombas(10);

							dato.setPez(1);

							dato.setNumeroSatelite(0);

							dato.anadirPuntuaciones(puntos, dato.getNumeroNivel(), "Pierde");

							if (publicidad != null) {

								publicidad.ocultarBanner();
							}

							if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

								Gdx.graphics.setCursor(Gdx.graphics
										.newCursor(new Pixmap(Gdx.files.internal("textura/cursor.png")), 0, 0));

							}

							juego.setScreen(new PantallaPuntuaciones(juego));

						}

					}
				})));

				tiempoCuadro = 0;
			}

		}

		if (jugador.isTerminarNivel()) {

			game = true;

			gameOver.remove();

			if (editar) {

				numeroNivel = dato.getNumeroNivel();

			}

			for (int i = 0; i < 40; i++) {

				if (numeroNivel == i + 1) {

					jugador.setTerminarNivel(false);

					dato.setNumeroNivel(numeroNivel);

					mundo.liberarRecursos();

					if (!editar) {

						if (dato.isSonido())

						{

							if (mundo.isIntro())

							{

								musica[indice].stop();

								nivel.addAction(Actions.sequence(Actions.delay(4.5f), Actions.run(new Runnable() {

									public void run() {

										AgregarMusica();

									}
								})));
							} else

							{

								AgregarMusica();

							}
						}

					}

					dato.setPuntos(puntos);

					dato.setVidas(jugador.getVida());

					dato.setMisiles(jugador.getMisil());

					dato.setBombas(jugador.getBomba());

					dato.setPez(jugador.getTipo());

					dato.setNumeroSatelite(jugador.getNumeroDeSatelites());

					gefe = false;

					mundo = new Niveles(this, jugador);

					inmunidadJugador = true;

					jugador.setInmune(inmunidadJugador);

				}

			}

			numeroNivel++;

		}

		if (dato.isSonido())

		{

			if (jugador.isFinNivel()) {

				if (musica[indice].isPlaying()) {

					musica[indice].stop();

				}

			}

		}

		if (dato.isSonido())

		{

			if (jugador.isGefe()) {

				if (!gefe) {

					musica[indice].stop();

					if (musicaGefe != null) {

						musicaGefe.play();

						musicaGefe.setLooping(true);

					}

					gefe = true;

				}

			} else {

				if (musicaGefe != null) {

					if (musicaGefe.isPlaying()) {

						musicaGefe.stop();

					}

				}

			}

		}

		if (inmunidadJugador) {

			tiempoEspera += delta;

			if (tiempoEspera / 3 >= 1) {

				inmunidadJugador = false;

				jugador.setInmune(inmunidadJugador);

				tiempoEspera = 0;

			}

		}

	}

	@Override
	public void dibujar(Batch pincel, float delta) {

		mundo.dibujar(pincel, delta);

		pincel.begin();

		cursor.dibujar(pincel, delta);

		pincel.end();

		textoPuntos.setText("Puntos " + puntos);

		textoNumeroNivel.setText("Nivel " + dato.getNumeroNivel());

		textoVida.setText("" + jugador.getVida());

		textoBomba.setText("" + jugador.getBomba());

		textoMisil.setText("" + jugador.getMisil());

		if (dato.isPrueba()) {

			test();

		}

		if (dato.isMostrarFPS()) {

			fps.setText("FPS: " + Gdx.graphics.getFramesPerSecond());

		}

	}

	@Override
	public void guardarDatos() {

		mundo.guardarDatos();

		configuracion.escribirDatos(dato);

	}

	@Override
	public void liberarRecursos() {

		mundo.liberarRecursos();

	}

	private void test() {

		for (Personaje personaje : personajes) {

			pincelPrueba.begin(ShapeRenderer.ShapeType.Line);

			pincelPrueba.rect(personaje.getX(), personaje.getY(), personaje.getWidth() / 2, personaje.getHeight() / 2,
					personaje.getWidth(), personaje.getHeight(), 1, 1, personaje.getRotation());

			pincelPrueba.end();

		}

	}

}
