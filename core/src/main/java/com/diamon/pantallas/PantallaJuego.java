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

/**
 * Pantalla principal del juego donde se desarrolla la jugabilidad.
 * Gestiona el jugador, los niveles, la interfaz de usuario (UI),
 * el estado del juego (pausa, modo edición) y las interacciones entre
 * los diferentes elementos del juego.
 */
public class PantallaJuego extends Pantalla {

	/**
	 * Instancia del nivel actual que se está jugando. Contiene los elementos del escenario, enemigos, etc.
	 */
	private Nivel mundo;

	/**
	 * Instancia del personaje jugador principal.
	 */
	private Jugador jugador;

	/**
	 * Botón (imagen) para pausar el juego.
	 */
	private Image pausa;

	/**
	 * Botón de texto para reanudar el juego desde el menú de pausa.
	 */
	private TextButton reanudar;

	/**
	 * Imagen de fondo para el menú de pausa.
	 */
	private Image menuPausa;

	/**
	 * Botón de texto para volver al menú principal desde el menú de pausa.
	 */
	private TextButton menu;

	/**
	 * Botón de texto para activar el modo de edición de niveles (si está disponible).
	 */
	private TextButton editarNivel;

	/**
	 * Botón de texto para terminar el modo de edición de niveles.
	 */
	private TextButton terminarEdicion;

	/**
	 * Indicador de estado de pausa del juego. {@code true} si el juego está pausado, {@code false} en caso contrario.
	 * Es volátil porque puede ser modificado por diferentes hilos de eventos de UI.
	 */
	private volatile boolean pausar;

	/**
	 * Arreglo de pistas de música para los diferentes niveles o secciones del juego.
	 */
	private Music[] musica;

	/**
	 * Pista de música específica para las batallas contra jefes.
	 */
	private Music musicaGefe;

	/**
	 * Número del nivel actual que se está jugando o editando.
	 */
	private int numeroNivel;

	/**
	 * Acumulador de tiempo para controlar la lógica de "game over" o transiciones.
	 */
	private float tiempoCuadro;

	/**
	 * Indicador de estado del modo edición. {@code true} si el modo edición está activo, {@code false} en caso contrario.
	 */
	private boolean editar;

	/**
	 * Instancia del editor de niveles, utilizado si el modo edición está activo.
	 */
	private EditorNivel editor;

	/**
	 * Cursor personalizado del juego, especialmente visible en modo de disparo no automático o en menús.
	 */
	private Cursor cursor;

	/**
	 * Puntuación actual del jugador.
	 */
	private int puntos;

	/**
	 * Bandera para controlar el comportamiento de la tecla ESCAPE, para alternar la pausa.
	 */
	private boolean escape;

	/**
	 * Etiqueta para mostrar los cuadros por segundo (FPS), si está habilitado.
	 */
	private Label fps;

	/**
	 * Etiqueta para mostrar la puntuación del jugador.
	 */
	private Label textoPuntos;

	/**
	 * Etiqueta para mostrar el número del nivel actual.
	 */
	private Label textoNumeroNivel;

	/**
	 * Etiqueta para mostrar la vida restante del jugador.
	 */
	private Label textoVida;

	/**
	 * Etiqueta para mostrar la cantidad de bombas disponibles para el jugador.
	 */
	private Label textoBomba;

	/**
	 * Etiqueta para mostrar la cantidad de misiles disponibles para el jugador.
	 */
	private Label textoMisil;

	/**
	 * Etiqueta para mostrar el texto "Pausado" cuando el juego está en pausa.
	 */
	private Label textoPausa;

	/**
	 * Etiqueta para mostrar el texto "Fin del Juego" cuando el jugador pierde.
	 */
	private Label gameOver;

	/**
	 * Bandera para controlar la lógica de "game over" y evitar que se ejecute múltiples veces.
	 */
	private boolean game;

	/**
	 * Índice actual para seleccionar la pista de música del arreglo {@link #musica}.
	 */
	private int indice;

	/**
	 * Bandera que indica si el jugador está actualmente en una batalla contra un jefe.
	 */
	private boolean gefe;

	/**
	 * Temporizador para controlar la duración de la inmunidad del jugador después de pasar un nivel o revivir.
	 */
	private float tiempoEspera;

	/**
	 * Bandera que indica si el jugador es actualmente inmune al daño.
	 */
	private boolean inmunidadJugador;

	/**
	 * Constructor de la pantalla de juego.
	 *
	 * @param juego La instancia principal del juego ({@link Juego}) a la que esta pantalla pertenece.
	 */
	public PantallaJuego(Juego juego) {
		super(juego);
		// La inicialización principal ocurre en el método mostrar().
	}

	/**
	 * Se llama cuando esta pantalla se muestra por primera vez.
	 * Inicializa el estado del juego, la UI (botones, etiquetas), el jugador,
	 * los niveles, la música y el editor de niveles si corresponde.
	 */
	@SuppressWarnings("static-access") // Para Gdx.app.getType() y Gdx.graphics.setCursor
	@Override
	public void mostrar() {

		// Muestra el banner de publicidad si está disponible.
		if (publicidad != null) {
			publicidad.mostrarBanner();
		}

		// Inicializa el índice de la música.
		indice = 0;
		// Crea el arreglo para las pistas de música de niveles normales.
		musica = new Music[4];

		// Inicializa banderas de estado del juego.
		escape = true; // Permite pausar con ESCAPE inicialmente.
		game = true;   // Indica que el juego está activo (no en game over).

		// Configura el cursor del sistema si el disparo automático no está activado y es una aplicación de escritorio.
		// Si el disparo es automático, el cursor personalizado del juego podría no ser necesario.
		if (!dato.isDiparoAutomatico()) {
			if (Gdx.app.getType() == Gdx.app.getType().Desktop) {
				// Hace el cursor del sistema invisible para usar un cursor personalizado o ninguno.
				Gdx.graphics.setCursor(Gdx.graphics.newCursor(new Pixmap(1, 1, Pixmap.Format.RGBA8888), 0, 0));
			}
		}

		// Si es una nueva partida (dato.isPartida() es true), reinicia el número de nivel a 1.
		if (dato.isPartida()) {
			datosNiveles.setNumeroNivel(1);
		}

		// Obtiene el número de nivel actual desde los datos guardados.
		numeroNivel = datosNiveles.getNumeroNivel();

		// Creación y configuración de las etiquetas de la UI.
		// Etiqueta para mostrar FPS.
		fps = new Label("", recurso.get("uis/general/uiskin.json", Skin.class));
		fps.setPosition(580, 12); // Posición en la pantalla.
		nivel.addActor(fps); // Añade la etiqueta al escenario principal de la pantalla.

		// Etiqueta "Fin del Juego".
		gameOver = new Label("Fin del Juego", recurso.get("uis/general/uiskin.json", Skin.class));
		gameOver.setPosition((Juego.ANCHO_PANTALLA / 2 - 64), Juego.ALTO_PANTALLA / 2);
		// No se añade al nivel aquí, se añade dinámicamente cuando el juego termina.

		// Etiqueta para puntos.
		textoPuntos = new Label("", recurso.get("uis/general/uiskin.json", Skin.class));
		textoPuntos.setPosition(8, 12);
		nivel.addActor(textoPuntos);

		// Etiqueta para número de nivel.
		textoNumeroNivel = new Label("", recurso.get("uis/general/uiskin.json", Skin.class));
		textoNumeroNivel.setPosition(112, 12);
		nivel.addActor(textoNumeroNivel);

		// Etiqueta para vidas del jugador.
		textoVida = new Label("", recurso.get("uis/general/uiskin.json", Skin.class));
		textoVida.setSize(32, 32);
		textoVida.setPosition(66, 450); // Ajustar según la UI final.
		nivel.addActor(textoVida);

		// Etiqueta para bombas del jugador.
		textoBomba = new Label("", recurso.get("uis/general/uiskin.json", Skin.class));
		textoBomba.setSize(32, 32);
		textoBomba.setPosition(115, 450); // Ajustar según la UI final.
		nivel.addActor(textoBomba);

		// Etiqueta para misiles del jugador.
		textoMisil = new Label("", recurso.get("uis/general/uiskin.json", Skin.class));
		textoMisil.setSize(32, 32);
		textoMisil.setPosition(148, 450); // Ajustar según la UI final.
		nivel.addActor(textoMisil);

		// Creación y configuración de los botones e imágenes de la UI de pausa.
		// Botón de pausa (imagen).
		pausa = new Image(recurso.get("texturas/pausa.png", Texture.class));
		pausa.setSize(64, 64);
		pausa.setPosition(576, 416); // Posición en la esquina superior derecha (aproximadamente).

		// Etiqueta "Pausado".
		textoPausa = new Label("Pausado", recurso.get("uis/general/uiskin.json", Skin.class));
		textoPausa.setPosition(264, 300); // Centrado en el menú de pausa.

		// Botón "Editar Niveles".
		editarNivel = new TextButton("Editar Niveles", recurso.get("uis/general/uiskin.json", Skin.class));
		editarNivel.setSize(213.0F, 32);
		editarNivel.setPosition(213.0F, 260); // Posición dentro del menú de pausa.
		editarNivel.setColor(1.0F, 1.0F, 1.0F, 0.0F); // Inicialmente invisible.

		// Botón "Reanudar".
		reanudar = new TextButton("Reanudar", recurso.get("uis/general/uiskin.json", Skin.class));
		reanudar.setSize(213.0F, 32);

		// Botón "Menu".
		menu = new TextButton("Menu", recurso.get("uis/general/uiskin.json", Skin.class));
		menu.setSize(213.0F, 32);

		// Ajusta la posición de los botones "Reanudar" y "Menu" si el modo editor está habilitado o no.
		if (dato.isEditor()) {
			reanudar.setPosition(213.0F, 212);
			menu.setPosition(213.0F, 164);
		} else {
			reanudar.setPosition(213.0F, 248);
			menu.setPosition(213.0F, 200);
		}

		// Fondo del menú de pausa.
		menuPausa = new Image(recurso.get("texturas/menu.png", Texture.class));
		menuPausa.setSize(320.0F, 240.0F);
		menuPausa.setPosition(160.0F, 120.0F); // Centrado en la pantalla.

		// Botón "Terminar Edición".
		terminarEdicion = new TextButton("Terminar", recurso.get("uis/general/uiskin.json", Skin.class));
		terminarEdicion.setSize(96, 32);
		terminarEdicion.setPosition(528, 16); // Posición en la esquina inferior derecha (aproximadamente).
		terminarEdicion.setColor(1.0F, 1.0F, 1.0F, 0.7F); // Ligeramente transparente.
		// Este botón no se añade al nivel aquí, se añade cuando se activa el modo edición.

		// Establece la visibilidad inicial (alpha) de los elementos del menú de pausa a 0 (invisibles).
		menuPausa.setColor(1.0F, 1.0F, 1.0F, 0.0F);
		reanudar.setColor(1.0F, 1.0F, 1.0F, 0.0F);
		menu.setColor(1.0F, 1.0F, 1.0F, 0.0F);
		pausa.setColor(1.0F, 1.0F, 1.0F, 0.9F); // El botón de pausa principal es visible.
		textoPausa.setColor(1.0F, 1.0F, 1.0F, 0.0F);

		// Llama a ventanaDePausa para asegurar que los elementos del menú de pausa estén inicialmente no interactuables.
		ventanaDePausa(false, false);

		// Añade el botón de pausa al escenario si es una aplicación Android (en escritorio se usa la tecla ESCAPE).
		if (Gdx.app.getType() == Gdx.app.getType().Android) {
			nivel.addActor(pausa);
		}

		// Añade los elementos del menú de pausa al escenario.
		nivel.addActor(menuPausa);
		nivel.addActor(reanudar);
		nivel.addActor(menu);

		// Añade el botón "Editar Nivel" si el modo editor está habilitado en la configuración.
		if (dato.isEditor()) {
			nivel.addActor(editarNivel);
		}
		nivel.addActor(textoPausa);

		// Instancia el jugador.
		jugador = new Jugador(recurso.get("texturas/pez.atlas", TextureAtlas.class).getRegions(), 0.3f,
				Animation.PlayMode.LOOP, this, 64, 64, Jugador.CINESTECICO);

		// Instancia el manejador de niveles (mundo).
		mundo = new Niveles(this, jugador);
		// Marca al jugador para que el primer nivel se cargue.
		jugador.setTerminarNivel(true);

		// Instancia el cursor del juego (usado si el disparo no es automático o en modo edición).
		cursor = new Cursor(recurso.get("texturas/invisible.png", Texture.class), this, 16, 16, Cursor.ESTATICO);
		cursor.setPosition(0, 0); // Posición inicial del cursor.

		// Inicializa más estados del juego.
		pausar = false; // El juego no comienza pausado.
		editar = false; // El juego no comienza en modo edición.

		// Carga las pistas de música en el arreglo.
		musica[0] = recurso.get("audios/musica.ogg", Music.class);
		musica[1] = recurso.get("audios/musica.ogg", Music.class); // Podrían ser diferentes pistas por rangos de niveles.
		musica[2] = recurso.get("audios/musica.ogg", Music.class);
		musica[3] = recurso.get("audios/musica.ogg", Music.class);

		// Carga la música para los jefes.
		musicaGefe = recurso.get("audios/moustro.ogg", Music.class);

		// Si el sonido está habilitado, inicia la música del nivel.
		if (dato.isSonido()) {
			AgregarMusica();
		}

		// Instancia el editor de niveles.
		editor = new EditorNivel(nivel, informacionNiveles, datosNiveles, configuracion, dato, camara, personajes, this,
				recurso, cursor);

		// Carga los puntos guardados del jugador.
		puntos = datosNiveles.getPuntos();
	}

	/**
	 * Gestiona la música del juego. Detiene la música actual y reproduce la música
	 * correspondiente al nivel actual o si es una batalla contra jefe.
	 */
	private void AgregarMusica() {

		// Detiene la música de jefe si se estaba reproduciendo.
		if (musicaGefe != null) {
			musicaGefe.stop();
		}

		// Detiene la música de nivel normal actual si se estaba reproduciendo.
		if (musica[indice] != null) {
			musica[indice].stop();
		}

		// Determina el índice de la pista de música basado en el número de nivel actual.
		// Esto permite tener diferentes músicas para diferentes rangos de niveles.
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

		// Solo reproduce la música si no estamos en la intro del nivel.
		// La intro podría tener su propia música o silencio.
		if (!mundo.isIntro()) {
			musica[indice].setLooping(true); // Configura la música para que se repita.
			musica[indice].play();          // Reproduce la música.
		}
	}

	/**
	 * Muestra u oculta la ventana de pausa con una animación de fade.
	 * Habilita o deshabilita la interacción con los botones del menú de pausa.
	 *
	 * @param visible {@code true} para mostrar la ventana de pausa, {@code false} para ocultarla.
	 * @param animated {@code true} para animar la transición, {@code false} para un cambio instantáneo.
	 */
	@SuppressWarnings("static-access") // Para Gdx.app.getType()
	private void ventanaDePausa(boolean visible, boolean animated) {

		// Determina el valor alfa final y la duración de la animación.
		float alphaTo = visible ? 0.8f : 0; // Alfa 0.8 para visible, 0 para invisible.
		float duration = animated ? 1 : 0;  // Duración de 1 segundo si es animado, 0 si no.

		// Determina si los elementos deben ser tocables.
		Touchable touchEnabled = visible ? Touchable.enabled : Touchable.disabled;

		// Aplica acciones de fade y cambio de estado "touchable" a los elementos del menú de pausa.
		// Actions.sequence permite ejecutar acciones en orden.
		// Actions.touchable cambia el estado de interacción del actor.
		// Actions.alpha cambia la transparencia del actor.
		menuPausa.addAction(Actions.sequence(Actions.touchable(touchEnabled), Actions.alpha(alphaTo, duration)));
		menu.addAction(Actions.sequence(Actions.touchable(touchEnabled), Actions.alpha(alphaTo, duration)));
		reanudar.addAction(Actions.sequence(Actions.touchable(touchEnabled), Actions.alpha(alphaTo, duration)));
		editarNivel.addAction(Actions.sequence(Actions.touchable(touchEnabled), Actions.alpha(alphaTo, duration)));
		textoPausa.addAction(Actions.sequence(Actions.touchable(touchEnabled), Actions.alpha(alphaTo, duration)));

		// Si se está mostrando la ventana de pausa y es Android, remueve el botón de pausa principal.
		if (visible) {
			if (Gdx.app.getType() == Gdx.app.getType().Android) {
				pausa.remove();
			}
		} else {
			// Si se está ocultando la ventana de pausa, añade una acción para que después de la duración de la animación:
			// - Se vuelva a añadir el botón de pausa principal en Android.
			// - Se resetee la bandera 'escape' para permitir pausar con la tecla ESCAPE nuevamente.
			nivel.addAction(Actions.sequence(Actions.delay(duration), Actions.run(new Runnable() {
				public void run() {
					if (Gdx.app.getType() == Gdx.app.getType().Android) {
						nivel.addActor(pausa);
					}
					escape = true; // Restablece la bandera para la tecla ESCAPE.
				}
			})));
		}
	}

	/**
	 * Configura los listeners para los eventos de entrada de la UI (botones) y del juego en general
	 * (teclado, ratón, táctil).
	 */
	@Override
	public void eventos() {

		// Listener para el botón de pausa (imagen).
		pausa.addListener(new ClickListener() {
			@SuppressWarnings("static-access") // Para Gdx.graphics.setCursor y Gdx.app.getType()
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Solo actúa si el nivel no está en su secuencia de introducción.
				if (!mundo.isIntro()) {
					// Si el juego no está ya pausado.
					if (!pausar) {
						// Si es escritorio, cambia el cursor del sistema a uno visible (el cursor del juego).
						if (Gdx.app.getType() == Gdx.app.getType().Desktop) {
							Gdx.graphics.setCursor(
									Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("texturas/cursor.png")), 0, 0));
						}
						// Detiene el movimiento de la cámara del nivel.
						mundo.setMoverCamara(false);
						// Si el sonido está habilitado, pausa la música actual.
						if (dato.isSonido()) {
							if (jugador.isGefe()) { // Si es música de jefe.
								if (musicaGefe != null) {
									musicaGefe.pause();
								}
							} else { // Si es música de nivel normal.
								musica[indice].pause();
							}
						}
						// Muestra la ventana de pausa (sin animación).
						ventanaDePausa(true, false);
					}
					// Establece el estado del juego a pausado.
					pausar = true;
				}
				super.clicked(event, x, y);
			}
		});

		// Listener para el botón "Editar Niveles".
		editarNivel.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				pausar = false; // Desactiva la pausa.
				mundo.setMoverCamara(true); // Permite que la cámara se mueva (para el editor).
				editor.agregarUI(); // Añade la UI específica del editor.
				// Si el sonido está habilitado, detiene la música del nivel.
				if (dato.isSonido()) {
					musica[indice].stop();
				}
				editor.setTerminar(false); // Indica que el editor no ha terminado.
				jugador.setTerminarNivel(true); // Marca el nivel actual como terminado para recargarlo en modo edición.
				editar = true; // Activa el modo edición.
				ventanaDePausa(false, true); // Oculta la ventana de pausa con animación.
				nivel.addActor(terminarEdicion); // Añade el botón "Terminar Edición".
				super.clicked(event, x, y);
			}
		});

		// Listener para el botón "Terminar Edición".
		terminarEdicion.addListener(new ClickListener() {
			@SuppressWarnings("static-access") // Para Gdx.graphics.setCursor y Gdx.app.getType()
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Restaura el cursor invisible si el disparo no es automático y es escritorio.
				if (!dato.isDiparoAutomatico()) {
					if (Gdx.app.getType() == Gdx.app.getType().Desktop) {
						Gdx.graphics.setCursor(Gdx.graphics.newCursor(new Pixmap(1, 1, Pixmap.Format.RGBA8888), 0, 0));
					}
				}
				// Vuelve a añadir el botón de pausa si es Android.
				if (Gdx.app.getType() == Gdx.app.getType().Android) {
					nivel.addActor(pausa);
				}
				// Si el sonido está habilitado, reanuda la música del nivel.
				if (dato.isSonido()) {
					musica[indice].play();
				}
				editor.setTerminar(true); // Indica que el editor ha terminado.
				editar = false; // Desactiva el modo edición.
				configuracion.escribirDatos(dato); // Guarda la configuración general.
				informacionNiveles.escribirDatos(datosNiveles); // Guarda los datos de los niveles.
				terminarEdicion.remove(); // Remueve el botón "Terminar Edición".
				editor.eliminarUI(); // Elimina la UI del editor.
				numeroNivel = datosNiveles.getNumeroNivel(); // Actualiza el número de nivel.
				jugador.setTerminarNivel(true); // Marca el nivel para recargarlo en modo juego.
				super.clicked(event, x, y);
			}
		});

		// Listener para el botón "Reanudar".
		reanudar.addListener(new ClickListener() {
			@SuppressWarnings("static-access") // Para Gdx.graphics.setCursor y Gdx.app.getType()
			@Override
			public void clicked(InputEvent event, float x, float y) {
				pausar = false; // Desactiva la pausa.
				// Si el juego ya no está pausado.
				if (!pausar) {
					// Restaura el cursor invisible si corresponde.
					if (!dato.isDiparoAutomatico()) {
						if (Gdx.app.getType() == Gdx.app.getType().Desktop) {
							Gdx.graphics
									.setCursor(Gdx.graphics.newCursor(new Pixmap(1, 1, Pixmap.Format.RGBA8888), 0, 0));
						}
					}
					// Permite que la cámara del nivel se mueva.
					mundo.setMoverCamara(true);
					// Si el sonido está habilitado, reanuda la música.
					if (dato.isSonido()) {
						if (jugador.isGefe()) { // Música de jefe.
							if (musicaGefe != null) {
								musicaGefe.play();
							}
						} else { // Música de nivel normal.
							musica[indice].play();
						}
					}
					// Oculta la ventana de pausa con animación.
					ventanaDePausa(false, true);
				}
				super.clicked(event, x, y);
			}
		});

		// Listener para el botón "Menu".
		menu.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				puntos = 0; // Resetea los puntos (esto podría ser opcional o manejarse de otra forma).
				// Oculta el banner de publicidad si existe.
				if (publicidad != null) {
					publicidad.ocultarBanner();
				}
				// Cambia a la pantalla del menú principal.
				juego.setScreen(new PantallaMenu(juego));
				super.clicked(event, x, y);
			}
		});

		// Listener de entrada general para el escenario 'nivel' (táctil, ratón, teclado).
		// Solo se activa si el juego no está pausado.
		if (!pausar) {
			nivel.addListener(new InputListener() {
				@Override
				public boolean handle(Event ev) {
					// Permite que otros listeners también manejen el evento si es necesario.
					return super.handle(ev);
				}

				// Evento cuando se presiona la pantalla o un botón del ratón.
				@Override
				public boolean touchDown(InputEvent ev, float x, float y, int puntero, int boton) {
					// Actualiza la posición del cursor del juego a las coordenadas del evento.
					// Se ajusta por la posición de la cámara para coordenadas del mundo.
					cursor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), y);
					// Pasa el evento al editor de niveles.
					editor.toquePresionado(ev, x, y, puntero, boton);
					// Si el nivel está en su introducción, no pasa el evento al jugador.
					if (mundo.isIntro()) {
						return true; // Evento manejado.
					} else {
						// Pasa el evento al jugador.
						return jugador.toquePresionado(ev, x, y, puntero, boton);
					}
				}

				// Evento cuando se levanta el dedo de la pantalla o se suelta un botón del ratón.
				@Override
				public void touchUp(InputEvent ev, float x, float y, int puntero, int boton) {
					editor.toqueLevantado(ev, x, y, puntero, boton);
					if (!mundo.isIntro()) {
						jugador.toqueLevantado(ev, x, y, puntero, boton);
					}
				}

				// Evento cuando se arrastra el dedo por la pantalla o se mueve el ratón con un botón presionado.
				@Override
				public void touchDragged(InputEvent ev, float x, float y, int puntero) {
					if (!mundo.isIntro()) {
						jugador.toqueDeslizando(ev, x, y, puntero);
					}
					editor.toqueDeslizando(ev, x, y, puntero);
					cursor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), y);
				}

				// Evento cuando se mueve el ratón sin botones presionados.
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

				// Evento cuando se presiona una tecla.
				@SuppressWarnings("static-access") // Para Gdx.graphics.setCursor y Gdx.app.getType()
				@Override
				public boolean keyDown(InputEvent ev, int codigoTecla) {
					editor.teclaPresionada(ev, codigoTecla); // Pasa la tecla al editor.

					// Lógica para la tecla ESCAPE cuando el menú de pausa está visible (escape = false).
					if (!escape) {
						if (Keys.ESCAPE == codigoTecla) {
							pausar = false; // Despausa el juego.
							// Si ya no está pausado (debería ser siempre true aquí).
							if (!pausar) {
								// Restaura el cursor invisible si corresponde.
								if (!dato.isDiparoAutomatico()) {
									if (Gdx.app.getType() == Gdx.app.getType().Desktop) {
										Gdx.graphics.setCursor(
												Gdx.graphics.newCursor(new Pixmap(1, 1, Pixmap.Format.RGBA8888), 0, 0));
									}
								}
								mundo.setMoverCamara(true); // Permite mover la cámara.
								// Reanuda la música si el sonido está habilitado.
								if (dato.isSonido()) {
									if (jugador.isGefe()) {
										if (musicaGefe != null) {
											musicaGefe.play();
										}
									} else {
										musica[indice].play();
									}
								}
								// Oculta la ventana de pausa con animación.
								ventanaDePausa(false, true);
							}
						}
					}

					// Lógica para la tecla ESCAPE cuando el menú de pausa no está visible (escape = true).
					if (escape) {
						// Solo si no estamos en la intro del nivel.
						if (!mundo.isIntro()) {
							if (Keys.ESCAPE == codigoTecla) {
								escape = false; // Indica que el menú de pausa se va a mostrar.
								// Si el juego no está ya pausado (debería serlo).
								if (!pausar) {
									// Muestra el cursor del juego si es escritorio.
									if (Gdx.app.getType() == Gdx.app.getType().Desktop) {
										Gdx.graphics.setCursor(Gdx.graphics.newCursor(
												new Pixmap(Gdx.files.internal("texturas/cursor.png")), 0, 0));
									}
									mundo.setMoverCamara(false); // Detiene la cámara.
									// Pausa la música si el sonido está habilitado.
									if (dato.isSonido()) {
										if (jugador.isGefe()) {
											if (musicaGefe != null) {
												musicaGefe.pause();
											}
										} else {
											musica[indice].pause();
										}
									}
									// Muestra la ventana de pausa sin animación.
									ventanaDePausa(true, false);
								}
								pausar = true; // Pausa el juego.
							}
						}
					}

					// Si estamos en la intro, la tecla es manejada (no pasa al jugador).
					if (mundo.isIntro()) {
						return true;
					} else {
						// Pasa la tecla al jugador.
						return jugador.teclaPresionada(ev, codigoTecla);
					}
				}

				// Evento cuando se suelta una tecla.
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

	/**
	 * Maneja la lógica de colisiones entre personajes.
	 * También procesa la eliminación de personajes marcados para ser removidos,
	 * incluyendo la limpieza de sus luces y cuerpos físicos.
	 */
	@Override
	public void colisiones() {
		// La detección de colisiones ahora es manejada por ColicionBox2DListener,
		// que se encarga de llamar a los métodos colision() de los Personajes involucrados
		// cuando Box2D detecta un contacto. Este método ahora solo se encarga de
		// actualizar puntos y procesar la eliminación de personajes.
		for (int i = 0; i < personajes.size; i++) {
			// Si el personaje es el jugador, actualiza los puntos.
			if (personajes.get(i) instanceof Jugador) {
				puntos += ((Jugador) personajes.get(i)).getPuntos(); // Suma los puntos obtenidos por el jugador.
				((Jugador) personajes.get(i)).setPuntos(0); // Resetea los puntos temporales del jugador.
			}

			// Procesa la eliminación de personajes marcados como 'remover'.
			Personaje personaje = personajes.get(i); // Obtiene la referencia al personaje actual.
			if (personaje.isRemover()) {
				// Elimina las luces asociadas al cuerpo del personaje.
				for (Light puntoLuz : luces) {
					if (puntoLuz.getBody() != null) {
						Body cu = puntoLuz.getBody();
						if (cu.getUserData() instanceof Personaje) {
							Personaje p = (Personaje) cu.getUserData();
							if (p.isRemover()) { // Si la luz pertenece al personaje a remover.
								puntoLuz.remove(); // Remueve la luz.
							}
						}
					}
				}

				// Elimina el cuerpo físico Box2D del personaje.
				mundoVirtual.getBodies(cuerpos); // Obtiene todos los cuerpos del mundo.
				if (cuerpos.size > 0) {
					for (Body cuerpo : cuerpos) {
						if (cuerpo.getUserData() instanceof Personaje) {
							if (((Personaje) cuerpo.getUserData()).isRemover()) { // Si el cuerpo pertenece al personaje a remover.
								mundoVirtual.destroyBody(cuerpo); // Destruye el cuerpo.
							}
						}
					}
				}
				// Remueve el personaje del arreglo de personajes activos.
				personajes.removeIndex(i);
			}
		}
	}

	/**
	 * Actualiza la lógica del juego en cada cuadro.
	 * Maneja el estado del juego (activo, editor, game over, fin de nivel),
	 * la música, la inmunidad del jugador y las transiciones de nivel.
	 *
	 * @param delta Tiempo transcurrido desde el último cuadro, en segundos.
	 */
	@SuppressWarnings("static-access") // Para Gdx.graphics.setCursor y Gdx.app.getType()
	@Override
	public void actualizar(float delta) {
		// Si el juego no está pausado.
		if (!pausar) {
			// Si no está en modo edición, actualiza el mundo del juego.
			if (!editar) {
				mundo.actualizar(delta);
			}
		}

		// Si está en modo edición, actualiza el editor.
		if (editar) {
			editor.actualizar();
		}

		// Lógica cuando el jugador está vivo.
		if (jugador.isVivo()) {
			// Lógica específica si el jugador está en una batalla contra jefe.
			if (jugador.isGefe()) {
				if (mundo != null) {
					if (((Niveles) mundo).getJefeNivel() != null) {
						// Podría haber lógica aquí relacionada con la dureza del jefe, etc.
						if (((Niveles) mundo).getJefeNivel().getDureza() <= 0) {
							// Jefe derrotado.
						} else {
							// Jefe aún vivo.
						}
					}
				}
			} else { // Si no es batalla contra jefe.
				// Podría haber lógica aquí si el jugador ha terminado el nivel.
				if (jugador.isFinNivel()) {
					// Nivel terminado.
				}
			}
		}

		// Lógica cuando el jugador NO está vivo (Game Over).
		if (!jugador.isVivo()) {
			// Si el jugador "terminó" el nivel al morir (ej. jefe final derrotado pero jugador muere también).
			if (jugador.isFinNivel()) {
				if (datosNiveles.getNumeroNivel() != 40) { // Si no es el último nivel.
					// Guarda la puntuación como "Gana" aunque haya muerto, si se considera victoria.
					datosNiveles.anadirPuntuaciones(puntos, datosNiveles.getNumeroNivel(), "Gana");
				}
			}

			// Detiene la música si está sonando.
			if (dato.isSonido()) {
				if (musica[indice].isPlaying()) {
					musica[indice].stop();
				}
				if (jugador.isGefe() && musicaGefe.isPlaying()) {
					if (musicaGefe != null) {
						musicaGefe.stop();
					}
				}
			}

			tiempoCuadro += delta; // Acumula tiempo para la pantalla de game over.

			// Muestra la etiqueta "Fin del Juego" si aún no se ha mostrado.
			if (game) {
				nivel.addActor(gameOver);
				game = false; // Evita que se añada múltiples veces.
			}

			// Después de un breve retraso, transiciona a la pantalla de créditos o puntuaciones.
			if (tiempoCuadro / 0.6f >= 1) { // Aproximadamente 0.6 segundos de retraso.
				nivel.addAction(Actions.sequence(Actions.delay(1f), Actions.run(new Runnable() {
					public void run() {
						// Si es el último nivel (40) y el jugador terminó el nivel (ganó el juego).
						if (datosNiveles.getNumeroNivel() == 40 && jugador.isFinNivel()) {
							// Resetea datos para una nueva partida potencial.
							datosNiveles.setPuntos(0);
							datosNiveles.setVidas(3);
							datosNiveles.setMisiles(10);
							datosNiveles.setBombas(10);
							dato.setContinuar(false);
							datosNiveles.setPez(1);
							datosNiveles.setNumeroSatelite(0);
							// Guarda la puntuación final.
							datosNiveles.anadirPuntuaciones(puntos, datosNiveles.getNumeroNivel(), "Gana");
							datosNiveles.setNumeroNivel(1); // Prepara para un posible reinicio.
							dato.setEditor(true); // Habilita el editor (comportamiento original).
							if (publicidad != null) {
								publicidad.ocultarBanner();
							}
							// Restaura el cursor del sistema si es escritorio.
							if (Gdx.app.getType() == Gdx.app.getType().Desktop) {
								Gdx.graphics.setCursor(Gdx.graphics
										.newCursor(new Pixmap(Gdx.files.internal("texturas/cursor.png")), 0, 0));
							}
							juego.setScreen(new PantallaCreditos(juego)); // Va a la pantalla de créditos.
						} else { // Si no es el último nivel o no se ganó.
							// Resetea datos.
							datosNiveles.setPuntos(0);
							datosNiveles.setVidas(3);
							datosNiveles.setMisiles(10);
							datosNiveles.setBombas(10);
							datosNiveles.setPez(1);
							datosNiveles.setNumeroSatelite(0);
							// Guarda la puntuación como "Pierde".
							datosNiveles.anadirPuntuaciones(puntos, datosNiveles.getNumeroNivel(), "Pierde");
							if (publicidad != null) {
								publicidad.ocultarBanner();
							}
							// Restaura el cursor del sistema.
							if (Gdx.app.getType() == Gdx.app.getType().Desktop) {
								Gdx.graphics.setCursor(Gdx.graphics
										.newCursor(new Pixmap(Gdx.files.internal("texturas/cursor.png")), 0, 0));
							}
							juego.setScreen(new PantallaPuntuaciones(juego)); // Va a la pantalla de puntuaciones.
						}
					}
				})));
				tiempoCuadro = 0; // Resetea el temporizador.
			}
		}

		// Lógica cuando el jugador termina un nivel.
		if (jugador.isTerminarNivel()) {
			game = true; // Restablece la bandera de "game over".
			gameOver.remove(); // Remueve la etiqueta "Fin del Juego" si estaba visible.

			// Si está en modo edición, el número de nivel se toma de los datos guardados.
			if (editar) {
				numeroNivel = datosNiveles.getNumeroNivel();
			}

			// Itera hasta 40 (número máximo de niveles asumido).
			for (int i = 0; i < 40; i++) {
				if (numeroNivel == i + 1) { // Si el número de nivel actual coincide.
					jugador.setTerminarNivel(false); // Resetea la bandera de terminar nivel del jugador.
					datosNiveles.setNumeroNivel(numeroNivel); // Asegura que los datos reflejen el nivel actual.
					mundo.liberarRecursos(); // Libera los recursos del nivel anterior.

					// Si no está en modo edición y el sonido está habilitado, maneja la música.
					if (!editar) {
						if (dato.isSonido()) {
							// Si el nivel anterior tenía una intro, detiene la música y la reanuda después de la nueva intro.
							if (mundo.isIntro()) {
								musica[indice].stop();
								// Añade una acción para agregar música después de un retraso (duración de la intro).
								nivel.addAction(Actions.sequence(Actions.delay(4.5f), Actions.run(new Runnable() {
									public void run() {
										AgregarMusica();
									}
								})));
							} else { // Si no había intro, simplemente agrega la música.
								AgregarMusica();
							}
						}
					}

					// Guarda el progreso del jugador.
					datosNiveles.setPuntos(puntos);
					datosNiveles.setVidas(jugador.getVida());
					datosNiveles.setMisiles(jugador.getMisil());
					datosNiveles.setBombas(jugador.getBomba());
					datosNiveles.setPez(jugador.getTipo());
					datosNiveles.setNumeroSatelite(jugador.getNumeroDeSatelites());

					gefe = false; // Resetea la bandera de jefe.
					mundo = new Niveles(this, jugador); // Crea una nueva instancia del siguiente nivel.
					inmunidadJugador = true; // Activa la inmunidad temporal del jugador.
					jugador.setInmune(inmunidadJugador);
				}
			}
			numeroNivel++; // Incrementa el número de nivel para la próxima iteración o carga.
		}

		// Detiene la música si el jugador ha terminado el nivel (y el sonido está habilitado).
		if (dato.isSonido()) {
			if (jugador.isFinNivel()) {
				if (musica[indice].isPlaying()) {
					musica[indice].stop();
				}
			}
		}

		// Maneja la música de jefe.
		if (dato.isSonido()) {
			if (jugador.isGefe()) { // Si el jugador está en una batalla contra jefe.
				if (!gefe) { // Si la música de jefe aún no ha comenzado.
					musica[indice].stop(); // Detiene la música de nivel normal.
					if (musicaGefe != null) {
						musicaGefe.play(); // Reproduce la música de jefe.
						musicaGefe.setLooping(true); // La música de jefe se repite.
					}
					gefe = true; // Indica que la música de jefe está sonando.
				}
			} else { // Si no es una batalla contra jefe.
				if (musicaGefe != null) {
					if (musicaGefe.isPlaying()) {
						musicaGefe.stop(); // Detiene la música de jefe si se estaba reproduciendo.
					}
				}
			}
		}

		// Maneja el temporizador de inmunidad del jugador.
		if (inmunidadJugador) {
			tiempoEspera += delta; // Acumula tiempo.
			if (tiempoEspera / 3 >= 1) { // Si han pasado 3 segundos.
				inmunidadJugador = false; // Desactiva la inmunidad.
				jugador.setInmune(inmunidadJugador);
				tiempoEspera = 0; // Resetea el temporizador.
			}
		}
	}

	/**
	 * Dibuja todos los elementos de la pantalla del juego.
	 *
	 * @param pincel El {@link Batch} (usualmente {@link SpriteBatch}) para dibujar.
	 * @param delta Tiempo transcurrido desde el último cuadro.
	 */
	@Override
	public void dibujar(Batch pincel, float delta) {
		// Dibuja el mundo (nivel, personajes, etc.).
		mundo.dibujar(pincel, delta);

		// Dibuja el cursor del juego.
		pincel.begin(); // Es necesario comenzar el batch si no está activo.
		cursor.dibujar(pincel, delta);
		pincel.end(); // Termina el batch.

		// Actualiza el texto de las etiquetas de la UI.
		textoPuntos.setText("Puntos " + puntos);
		textoNumeroNivel.setText("Nivel " + datosNiveles.getNumeroNivel());
		textoVida.setText("" + jugador.getVida());
		textoBomba.setText("" + jugador.getBomba());
		textoMisil.setText("" + jugador.getMisil());

		// Si el modo prueba está activado, llama al método test para dibujar información de depuración.
		if (dato.isPrueba()) {
			test();
		}

		// Si está configurado para mostrar FPS, actualiza la etiqueta correspondiente.
		if (dato.isMostrarFPS()) {
			fps.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
		}
	}

	/**
	 * Guarda los datos del juego, como el progreso del jugador y las configuraciones.
	 */
	@Override
	public void guardarDatos() {
		mundo.guardarDatos(); // Guarda datos específicos del nivel.
		configuracion.escribirDatos(dato); // Guarda la configuración general.
		informacionNiveles.escribirDatos(datosNiveles); // Guarda los datos de los niveles.
	}

	/**
	 * Libera los recursos utilizados por la pantalla del juego, principalmente los del nivel actual.
	 */
	@Override
	public void liberarRecursos() {
		mundo.liberarRecursos(); // Libera recursos del nivel.
	}

	/**
	 * Método de prueba para dibujar información de depuración, como los rectángulos
	 * de colisión de los personajes.
	 */
	private void test() {
		// Itera sobre todos los personajes y dibuja sus rectángulos de colisión.
		for (Personaje personaje : personajes) {
			pincelPrueba.begin(ShapeRenderer.ShapeType.Line); // Comienza a dibujar líneas.
			// Dibuja un rectángulo con la posición, tamaño y rotación del personaje.
			pincelPrueba.rect(personaje.getX(), personaje.getY(), personaje.getWidth() / 2, personaje.getHeight() / 2,
					personaje.getWidth(), personaje.getHeight(), 1, 1, personaje.getRotation());
			pincelPrueba.end(); // Termina de dibujar líneas.
		}
	}
}
