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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
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

	private Touchpad joystickVirtual;

	private Table tablaHUD;

	private Table tablaPausa;

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

	private Label textoPowerUp;

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

			datosNiveles.setNumeroNivel(1);

		}

		numeroNivel = datosNiveles.getNumeroNivel();

		Skin skin = recurso.get("uis/general/uiskin.json", Skin.class);

		tablaHUD = new Table();

		tablaHUD.setFillParent(true);

		fps = new Label("", skin);

		gameOver = new Label("Fin del Juego", skin);

		textoPuntos = new Label("", skin);
		textoPuntos.setColor(com.badlogic.gdx.graphics.Color.GOLD);

		textoNumeroNivel = new Label("", skin);
		textoNumeroNivel.setColor(com.badlogic.gdx.graphics.Color.WHITE);

		textoVida = new Label("", skin);
		textoVida.setColor(com.badlogic.gdx.graphics.Color.CORAL);

		textoBomba = new Label("", skin);
		textoBomba.setColor(com.badlogic.gdx.graphics.Color.ORANGE);

		textoMisil = new Label("", skin);
		textoMisil.setColor(com.badlogic.gdx.graphics.Color.CYAN);

		textoPowerUp = new Label("", skin);

		Table hudSuperior = new Table();

		hudSuperior.add(textoVida).padRight(20);

		hudSuperior.add(textoBomba).padRight(20);

		hudSuperior.add(textoMisil).padRight(20);

		hudSuperior.add(textoPowerUp);

		tablaHUD.add(hudSuperior).expandX().left().pad(20).top().row();

		Table hudInferior = new Table();

		hudInferior.add(textoPuntos).padRight(20);

		hudInferior.add(textoNumeroNivel).expandX().left();

		hudInferior.add(fps);

		tablaHUD.add().expand().row();

		tablaHUD.add(hudInferior).fillX().pad(20).bottom();

		nivel.addActor(tablaHUD);

		pausa = new Image(recurso.get("texturas/pausa.png", Texture.class));

		pausa.setSize(64, 64);

		pausa.setPosition(Juego.ANCHO_PANTALLA - 84, Juego.ALTO_PANTALLA - 84);

		tablaPausa = new Table();

		tablaPausa.setFillParent(true);

		textoPausa = new Label("Pausado", skin);

		editarNivel = new TextButton("Editar Niveles", skin);

		reanudar = new TextButton("Reanudar", skin);

		menu = new TextButton("Menu", skin);

		menuPausa = new Image(recurso.get("texturas/menu.png", Texture.class));

		Table contenidoPausa = new Table();

		contenidoPausa.setBackground(skin.getDrawable("marineWindow"));
		contenidoPausa.pad(24);

		reanudar.getLabel().setFontScale(1.1f);
		menu.getLabel().setFontScale(1.1f);
		editarNivel.getLabel().setFontScale(1.1f);

		contenidoPausa.add(reanudar).size(250, 48).padBottom(14).row();

		contenidoPausa.add(menu).size(250, 48).padBottom(14).row();

		if (dato.isEditor()) {

			contenidoPausa.add(editarNivel).size(250, 48).row();

		}

		tablaPausa.add(contenidoPausa).size(340, 250);

		tablaPausa.setColor(1, 1, 1, 0);

		tablaPausa.setTouchable(Touchable.disabled);

		terminarEdicion = new TextButton("Terminar", skin);

		terminarEdicion.setSize(96, 32);

		terminarEdicion.setPosition(Juego.ANCHO_PANTALLA - 116, 16);

		terminarEdicion.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		if (Gdx.app.getType() == Gdx.app.getType().Android) {

			nivel.addActor(pausa);

		}

		nivel.addActor(tablaPausa);

		jugador = new Jugador(recurso.get("texturas/pez.atlas", TextureAtlas.class).getRegions(), 0.3f,
				Animation.PlayMode.LOOP, this, 64, 64, Jugador.DIANAMICO);

		if (Gdx.files.internal("mapas/nivel1.tmx").exists()) {
			com.badlogic.gdx.maps.tiled.TiledMap mapaTmx = new com.badlogic.gdx.maps.tiled.TmxMapLoader().load("mapas/nivel1.tmx");
			mundo = new com.diamon.escenarios.NivelSubmarino(this, jugador, mapaTmx);
		} else {
			mundo = new Niveles(this, jugador);
		}

		jugador.setTerminarNivel(true);

		cursor = new Cursor(recurso.get("texturas/invisible.png", Texture.class), this, 16, 16, Cursor.ESTATICO);

		cursor.setPosition(0, 0);

		if (Gdx.app.getType() == Gdx.app.getType().Android) {
			TextureAtlas iconosAtlas = recurso.get("texturas/iconos.atlas", TextureAtlas.class);
			
			// Joystick Virtual Analógico 360° (idéntico al sistema de Whisk3D Android)
			Touchpad.TouchpadStyle touchpadStyle = skin.get(Touchpad.TouchpadStyle.class);
			joystickVirtual = new Touchpad(10f, touchpadStyle);
			joystickVirtual.setSize(180, 180);
			joystickVirtual.setPosition(35, 35);
			joystickVirtual.setColor(1f, 1f, 1f, 0.85f);
			nivel.addActor(joystickVirtual);

			// Botón de Disparo Principal (Burbujas / Torpedos)
			final Image btnDisparo = new Image(iconosAtlas.findRegion("iconofaro"));
			btnDisparo.setSize(80, 80);
			btnDisparo.setPosition(Juego.ANCHO_PANTALLA - 115, 85);
			btnDisparo.setColor(0.3f, 0.9f, 1.0f, 0.75f);
			btnDisparo.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					btnDisparo.setColor(1f, 0.6f, 0.2f, 0.98f);
					jugador.teclaPresionada(event, Keys.Z);
					return true;
				}
				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					btnDisparo.setColor(0.3f, 0.9f, 1.0f, 0.75f);
					jugador.teclaLevantada(event, Keys.Z);
				}
			});

			// Botón de Misil
			final Image btnMisil = new Image(iconosAtlas.findRegion("iconoexplosion"));
			btnMisil.setSize(64, 64);
			btnMisil.setPosition(Juego.ANCHO_PANTALLA - 205, 45);
			btnMisil.setColor(0.2f, 1f, 0.6f, 0.75f);
			btnMisil.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					btnMisil.setColor(0.3f, 1f, 0.3f, 0.98f);
					jugador.teclaPresionada(event, Keys.X);
					return true;
				}
				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					btnMisil.setColor(0.2f, 1f, 0.6f, 0.75f);
					jugador.teclaLevantada(event, Keys.X);
				}
			});

			// Botón de Bomba
			final Image btnBomba = new Image(iconosAtlas.findRegion("iconobomba"));
			btnBomba.setSize(64, 64);
			btnBomba.setPosition(Juego.ANCHO_PANTALLA - 115, 185);
			btnBomba.setColor(1f, 0.4f, 0.3f, 0.75f);
			btnBomba.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					btnBomba.setColor(1f, 0.1f, 0.1f, 0.98f);
					jugador.teclaPresionada(event, Keys.SPACE);
					return true;
				}
				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					btnBomba.setColor(1f, 0.4f, 0.3f, 0.75f);
					jugador.teclaLevantada(event, Keys.SPACE);
				}
			});
			
			nivel.addActor(btnDisparo);
			nivel.addActor(btnMisil);
			nivel.addActor(btnBomba);
		}

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

		editor = new EditorNivel(nivel, informacionNiveles, datosNiveles, configuracion, dato, camara, personajes, this,
				recurso, cursor);

		editor.setOnGuardarSalir(new Runnable() {
			@Override
			public void run() {
				salirDelEditor();
			}
		});

		editor.setOnCambioNivel(new Runnable() {
			@Override
			public void run() {
				numeroNivel = datosNiveles.getNumeroNivel();
				jugador.setTerminarNivel(true);
			}
		});

		puntos = datosNiveles.getPuntos();

	}

	private void AgregarMusica() {

		if (musicaGefe != null) {

			musicaGefe.stop();
		}

		if (musica[indice] != null) {

			musica[indice].stop();

		}

		if ((datosNiveles.getNumeroNivel() <= 10)) {

			indice = 0;

		}

		if ((datosNiveles.getNumeroNivel() >= 11) && (datosNiveles.getNumeroNivel() <= 20)) {

			indice = 1;

		}

		if ((datosNiveles.getNumeroNivel() >= 21) && (datosNiveles.getNumeroNivel() <= 30)) {

			indice = 2;

		}

		if ((datosNiveles.getNumeroNivel() >= 31) && (datosNiveles.getNumeroNivel() <= 40)) {

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

		float alphaTo = visible ? 1.0f : 0;

		float duration = animated ? 0.5f : 0;

		Touchable touchEnabled = visible ? Touchable.enabled : Touchable.disabled;

		tablaPausa.addAction(Actions.sequence(Actions.touchable(touchEnabled), Actions.alpha(alphaTo, duration)));

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
			@Override
			public void clicked(InputEvent event, float x, float y) {
				salirDelEditor();
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

	}

	private void salirDelEditor() {
		if (!dato.isDiparoAutomatico()) {
			if (Gdx.app.getType() == Gdx.app.getType().Desktop) {
				Gdx.graphics.setCursor(Gdx.graphics.newCursor(new Pixmap(1, 1, Pixmap.Format.RGBA8888), 0, 0));
			}
		}

		if (Gdx.app.getType() == Gdx.app.getType().Android) {
			nivel.addActor(pausa);
		}

		if (dato.isSonido()) {
			if (musica[indice] != null) {
				musica[indice].play();
			}
		}

		editor.setTerminar(true);
		editar = false;
		configuracion.escribirDatos(dato);
		informacionNiveles.escribirDatos(datosNiveles);
		terminarEdicion.remove();
		editor.eliminarUI();
		numeroNivel = datosNiveles.getNumeroNivel();
		jugador.setTerminarNivel(true);
	}

	private void configurarInputJuego() {
		if (!pausar) {

			nivel.addListener(new InputListener() {

				@Override
				public boolean handle(Event ev) {

					return super.handle(ev);
				}

				@Override
				public boolean touchDown(InputEvent ev, float x, float y, int puntero, int boton) {

					if (editar) {
						editor.toquePresionado(ev, x, y, puntero, boton);
						return true;
					}

					cursor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), y);

					if (mundo.isIntro()) {

						return true;

					} else {

						return jugador.toquePresionado(ev, x, y, puntero, boton);

					}

				}

				@Override
				public void touchUp(InputEvent ev, float x, float y, int puntero, int boton) {

					if (editar) {
						editor.toqueLevantado(ev, x, y, puntero, boton);
						return;
					}

					if (!mundo.isIntro()) {

						jugador.toqueLevantado(ev, x, y, puntero, boton);

					}

				}

				@Override
				public void touchDragged(InputEvent ev, float x, float y, int puntero) {

					if (editar) {
						editor.toqueDeslizando(ev, x, y, puntero);
						return;
					}

					if (!mundo.isIntro()) {

						jugador.toqueDeslizando(ev, x, y, puntero);

					}

					cursor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), y);

				}

				@Override
				public boolean mouseMoved(InputEvent ev, float x, float y) {

					if (editar) {
						editor.ratonMoviendo(ev, x, y);
						return true;
					}

					if (!mundo.isIntro()) {

						jugador.ratonMoviendo(ev, x, y);

					}

					cursor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), y);

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

					if (editar) {
						if (Keys.ESCAPE == codigoTecla) {
							salirDelEditor();
						} else {
							editor.teclaPresionada(ev, codigoTecla);
						}
						return true;
					}

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

					if (editar) {
						editor.teclaLevantada(ev, codigoTecla);
						return true;
					}

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
				personaje.destruirCuerpo(mundoVirtual);
				personajes.removeIndex(i);

			}
		}

	}

	@SuppressWarnings("static-access")
	@Override
	public void actualizar(float delta) {

		if (joystickVirtual != null && jugador != null) {
			float vx = joystickVirtual.getKnobPercentX();
			float vy = joystickVirtual.getKnobPercentY();
			jugador.setEntradaVirtual(vx, vy);
		}

		//////////////////
		// Box2D
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

				if (mundo instanceof Niveles) {

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

				if (datosNiveles.getNumeroNivel() != 40) {

					datosNiveles.anadirPuntuaciones(puntos, datosNiveles.getNumeroNivel(), "Gana");

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

						if (datosNiveles.getNumeroNivel() == 40 && jugador.isFinNivel()) {

							datosNiveles.setPuntos(0);

							datosNiveles.setVidas(3);

							datosNiveles.setMisiles(10);

							datosNiveles.setBombas(10);

							dato.setContinuar(false);

							datosNiveles.setPez(1);

							datosNiveles.setNumeroSatelite(0);

							datosNiveles.anadirPuntuaciones(puntos, datosNiveles.getNumeroNivel(), "Gana");

							datosNiveles.setNumeroNivel(1);

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

							datosNiveles.setPuntos(0);

							datosNiveles.setVidas(3);

							datosNiveles.setMisiles(10);

							datosNiveles.setBombas(10);

							datosNiveles.setPez(1);

							datosNiveles.setNumeroSatelite(0);

							datosNiveles.anadirPuntuaciones(puntos, datosNiveles.getNumeroNivel(), "Pierde");

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

				numeroNivel = datosNiveles.getNumeroNivel();

			}

			for (int i = 0; i < 40; i++) {

				if (numeroNivel == i + 1) {

					jugador.setTerminarNivel(false);

					datosNiveles.setNumeroNivel(numeroNivel);

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

					datosNiveles.setPuntos(puntos);

					datosNiveles.setVidas(jugador.getVida());

					datosNiveles.setMisiles(jugador.getMisil());

					datosNiveles.setBombas(jugador.getBomba());

					datosNiveles.setPez(jugador.getTipo());

					datosNiveles.setNumeroSatelite(jugador.getNumeroDeSatelites());

					gefe = false;

					if (numeroNivel == 1 && Gdx.files.internal("mapas/nivel1.tmx").exists()) {
						com.badlogic.gdx.maps.tiled.TiledMap mapaTmx = new com.badlogic.gdx.maps.tiled.TmxMapLoader().load("mapas/nivel1.tmx");
						mundo = new com.diamon.escenarios.NivelSubmarino(this, jugador, mapaTmx);
					} else {
						mundo = new Niveles(this, jugador);
					}

					inmunidadJugador = true;

					jugador.setInmune(inmunidadJugador);

				}

			}

			if (!editar) {
				numeroNivel++;
			}

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

		puntos = jugador.getPuntos();
		textoPuntos.setText("Perlas: " + puntos);

		textoNumeroNivel.setText("Nivel " + datosNiveles.getNumeroNivel());

		textoVida.setText("Vida: " + jugador.getVida());

		textoBomba.setText("Bombas: " + jugador.getBomba());

		textoMisil.setText("Misiles: " + jugador.getMisil());

		if (textoPowerUp != null) {
			if (jugador.isTurboActivo()) {
				textoPowerUp.setText("TURBO: " + String.format("%.1fs", jugador.getTiempoRestantePowerUp(com.diamon.items.TipoPowerUp.TURBO)));
				textoPowerUp.setColor(com.badlogic.gdx.graphics.Color.ORANGE);
			} else if (jugador.isEscudoActivo()) {
				textoPowerUp.setText("ESCUDO BURBUJA");
				textoPowerUp.setColor(com.badlogic.gdx.graphics.Color.CYAN);
			} else if (jugador.isLinternaActiva()) {
				textoPowerUp.setText("LINTERNA: " + String.format("%.1fs", jugador.getTiempoRestantePowerUp(com.diamon.items.TipoPowerUp.LINTERNA)));
				textoPowerUp.setColor(com.badlogic.gdx.graphics.Color.YELLOW);
			} else {
				textoPowerUp.setText("");
			}
		}

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

		informacionNiveles.escribirDatos(datosNiveles);

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
