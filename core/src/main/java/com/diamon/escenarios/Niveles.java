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

/**
 * La clase {@code Niveles} se encarga de construir y gestionar los niveles específicos del juego.
 * Esto incluye la creación de fondos, personajes (enemigos, obstáculos), jefes,
 * el sistema de iluminación (Box2DLights), efectos de partículas y el renderizado
 * del mapa Tiled (si se utiliza). Extiende la clase base {@link com.diamon.nucleo.Nivel},
 * heredando funcionalidades comunes de gestión de niveles.
 */
public class Niveles extends Nivel {

	/**
	 * Renderizador para el mapa Tiled del nivel.
	 */
	private TiledMapRenderer render;

	/**
	 * Coordenada X para el control del scroll del fondo. Se inicializa basada en la posición de la cámara.
	 */
	private float xFondo = camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2;

	/**
	 * Instancia del primer jefe del juego.
	 */
	private JefeUno gefeUno;

	/**
	 * Instancia del segundo jefe del juego.
	 */
	private JefeDos gefeDos;

	/**
	 * Instancia del tercer jefe del juego.
	 */
	private JefeTres gefeTres;

	/**
	 * Instancia del cuarto jefe del juego.
	 */
	private JefeCuatro gefeCuatro;

	/**
	 * Referencia genérica al jefe del nivel actual.
	 */
	private Personaje jefeNivel;

	/**
	 * Luz puntual principal asociada al jugador, utilizada por el sistema Box2DLights.
	 */
	private PointLight puntoDeLuz;

	/**
	 * Efecto de partícula personalizado utilizado en el nivel.
	 */
	private Particula particuala;

	/**
	 * Constructor de la clase {@code Niveles}.
	 * Configura el entorno del nivel, incluyendo fondos, personajes basados en los datos del nivel,
	 * iluminación, efectos de partículas y el listener de colisiones.
	 *
	 * @param pantalla La pantalla ({@link Pantalla}) a la que este nivel pertenece.
	 * @param jugador El personaje jugador ({@link Jugador}) que participará en este nivel.
	 */
	public Niveles(Pantalla pantalla, Jugador jugador) {
		super(pantalla, jugador); // Llama al constructor de la clase base Nivel.

		int indice = 1; // Índice para seleccionar la textura del fondo.

		// Inicializa el renderizador para el mapa Tiled. El '1' podría ser la escala unitaria.
		render = new OrthogonalTiledMapRenderer(mapa, 1);

		// Selecciona y añade los fondos al nivel basados en el número de nivel actual.
		// Diferentes rangos de niveles pueden tener diferentes fondos.
		if ((datosNiveles.getNumeroNivel() <= 10)) {
			// Para niveles 1-10
			for (int i = 0; i < fondo.length; i++) {
				indice = 1;
				fondo[i] = new Fondo(new TextureRegion(recurso.get("texturas/fondo" + indice + ".png", Texture.class)),
						pantalla, Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA, Fondo.ESTATICO);
				fondo[i].setPosition(0, 0);
				personajes.add(fondo[i]); // Añade el fondo a la lista de personajes para ser dibujado.
			}
		}

		if ((datosNiveles.getNumeroNivel() >= 11) && (datosNiveles.getNumeroNivel() <= 20)) {
			// Para niveles 11-20
			indice = 2;
			for (int i = 0; i < fondo.length; i++) {
				fondo[i] = new Fondo(new TextureRegion(recurso.get("texturas/fondo" + indice + ".png", Texture.class)),
						pantalla, Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA, Fondo.ESTATICO);
				fondo[i].setPosition(0, 0);
				personajes.add(fondo[i]);
			}
		}

		if ((datosNiveles.getNumeroNivel() >= 21) && (datosNiveles.getNumeroNivel() <= 30)) {
			// Para niveles 21-30
			indice = 3;
			for (int i = 0; i < fondo.length; i++) {
				fondo[i] = new Fondo(new TextureRegion(recurso.get("texturas/fondo" + indice + ".png", Texture.class)),
						pantalla, Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA, Fondo.ESTATICO);
				fondo[i].setPosition(0, 0);
				personajes.add(fondo[i]);
			}
		}

		if ((datosNiveles.getNumeroNivel() >= 31) && (datosNiveles.getNumeroNivel() <= 40)) {
			// Para niveles 31-40
			indice = 4;
			for (int i = 0; i < fondo.length; i++) {
				fondo[i] = new Fondo(new TextureRegion(recurso.get("texturas/fondo" + indice + ".png", Texture.class)),
						pantalla, Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA, Fondo.ESTATICO);
				fondo[i].setPosition(0, 0);
				personajes.add(fondo[i]);
			}
		}

		// Añade un personaje TiburonAzul (decorativo o enemigo, según su implementación).
		TiburonAzul tiburon = new TiburonAzul(recurso.get("texturas/tiburon.atlas", TextureAtlas.class).getRegions(),
				0.3f, Animation.PlayMode.LOOP, pantalla, 192, 192, TiburonAzul.ESTATICO);
		tiburon.setPosition(50, 200);
		personajes.add(tiburon);

		// Configuración del sistema de iluminación Box2DLights.
		RayHandler.setGammaCorrection(true); // Habilita la corrección gamma para una iluminación más realista.
		luz.setAmbientLight(0f, 0f, 0f, 0.3f); // Establece una luz ambiental oscura (azulada/grisácea).

		// Crea una luz puntual principal que se asociará al jugador.
		puntoDeLuz = new PointLight(luz, 1000, Color.BLACK, 2, 2, 4); // 1000 rayos, color, distancia, posX, posY
		luz.setShadows(true); // Habilita las sombras para el RayHandler.
		puntoDeLuz.setStaticLight(false); // La luz no es estática, se moverá con el jugador.
		puntoDeLuz.setSoft(true); // Habilita bordes suaves para la luz.
		luces.add(puntoDeLuz); // Añade la luz a la lista de luces de la pantalla.

		// Limpia cuerpos físicos existentes (excepto el jugador) y asocia la luz principal al jugador.
		mundoVirtual.getBodies(cuerpos); // Obtiene todos los cuerpos del mundo físico.
		if (cuerpos.size > 0) {
			for (Body cuerpo : cuerpos) {
				// Si el cuerpo no pertenece al jugador, lo destruye.
				// Esto es para limpiar cuerpos de un nivel anterior o de configuraciones previas.
				if (!(cuerpo.getUserData() instanceof Jugador)) {
					mundoVirtual.destroyBody(cuerpo);
				} else {
					// Si es el cuerpo del jugador, le asocia la luz puntual principal.
					puntoDeLuz.attachToBody(cuerpo);
				}
			}
		}

		// Crea luces puntuales aleatorias como decoración o para iluminar áreas específicas.
		for (int i = 0; i < 10; i++) {
			PointLight punto = new PointLight(luz, 1000, Color.BLACK, 600 / Juego.UNIDAD_DEL_MUNDO,
					MathUtils.random() * 13440 / Juego.UNIDAD_DEL_MUNDO - 2, 4); // Posiciones aleatorias.
			luces.add(punto);
		}

		// Configuración del efecto de partículas.
		particuala = new Particula(recurso.get("particulas/Particle Park Flame.p", ParticleEffect.class), pantalla);
		particuala.setPosicion(400 / Juego.UNIDAD_DEL_MUNDO, 10 / Juego.UNIDAD_DEL_MUNDO); // Posición inicial.
		particuala.setEscala(3f); // Escala del efecto.
		// Crea una luz puntual asociada al efecto de partículas.
		PointLight puntoL = new PointLight(luz, 1000, Color.BLACK, 8, 2, 4);
		luces.add(puntoL);
		particuala.setPuntoLuz(puntoL); // Vincula la luz al efecto de partícula.
		particuala.iniciar(); // Inicia el efecto de partícula.

		// Establece el listener de colisiones para el mundo Box2D.
		// Nota: El nombre de la clase "ColicionBox2DListener" tiene una errata ("Colicion" en lugar de "Colision").
		this.mundoVirtual.setContactListener(new ColicionBox2DListener());

		// Carga y posiciona los diferentes tipos de personajes (enemigos, obstáculos)
		// basados en los datos almacenados en 'datosNiveles' para el nivel actual.
		String numeroNivel = "Nivel " + datosNiveles.getNumeroNivel();

		// Carga Pulpos.
		for (Vector2 posicion : datosNiveles.getPosicionActores(DatosNiveles.PULPO, numeroNivel)) {
			Pulpo actor = new Pulpo(recurso.get("texturas/pulpo.atlas", TextureAtlas.class).getRegions(), 0.07f,
					Animation.PlayMode.LOOP, pantalla, 32, 64, Pulpo.ESTATICO);
			actor.setPosition(posicion.x * Juego.UNIDAD_DEL_MUNDO, posicion.y * Juego.UNIDAD_DEL_MUNDO);
			personajes.add(actor);
		}

		// Carga PezGloboAmarillo.
		for (Vector2 posicion : datosNiveles.getPosicionActores(DatosNiveles.PEZ_GLOBO_AMARILLO, numeroNivel)) {
			PezGloboAmarillo actor = new PezGloboAmarillo(
					recurso.get("texturas/pezG.atlas", TextureAtlas.class).getRegions(), 0.1f, Animation.PlayMode.LOOP,
					pantalla, 64, 32, PezGloboAmarillo.ESTATICO);
			actor.setPosition(posicion.x * Juego.UNIDAD_DEL_MUNDO, posicion.y * Juego.UNIDAD_DEL_MUNDO);
			personajes.add(actor);
		}

		// Carga PezGloboNaranja.
		for (Vector2 posicion : datosNiveles.getPosicionActores(DatosNiveles.PEZ_GOBO_NARANJA, numeroNivel)) {
			PezGloboNaranja actor = new PezGloboNaranja(
					recurso.get("texturas/pezGlobo.atlas", TextureAtlas.class).getRegions(), 0.1f,
					Animation.PlayMode.LOOP, pantalla, 96, 64, PezGloboNaranja.ESTATICO);
			actor.setPosition(posicion.x * Juego.UNIDAD_DEL_MUNDO, posicion.y * Juego.UNIDAD_DEL_MUNDO);
			personajes.add(actor);
		}

		// Carga PezAngel.
		for (Vector2 posicion : datosNiveles.getPosicionActores(DatosNiveles.PEZ_ANGEL, numeroNivel)) {
			PezAngel actor = new PezAngel(recurso.get("texturas/pez1.atlas", TextureAtlas.class).getRegions(), 0.1f,
					Animation.PlayMode.LOOP, pantalla, 64, 32, PezAngel.ESTATICO);
			actor.setPosition(posicion.x * Juego.UNIDAD_DEL_MUNDO, posicion.y * Juego.UNIDAD_DEL_MUNDO);
			personajes.add(actor);
		}

		// Carga Bombas.
		for (Vector2 posicion : datosNiveles.getPosicionActores(DatosNiveles.BOMBA, numeroNivel)) {
			Bomba actor = new Bomba(recurso.get("texturas/bomba.png", Texture.class), pantalla, 64, 64,
					Bomba.DIANAMICO);
			actor.setPosition(posicion.x * Juego.UNIDAD_DEL_MUNDO, posicion.y * Juego.UNIDAD_DEL_MUNDO);
			personajes.add(actor);
		}

		// Carga Algas.
		for (Vector2 posicion : datosNiveles.getPosicionActores(DatosNiveles.ALGAS, numeroNivel)) {
			Algas actor = new Algas(recurso.get("texturas/algas.png", Texture.class), pantalla, 96, 64,
					Algas.CINESTECICO);
			actor.setPosition(posicion.x * Juego.UNIDAD_DEL_MUNDO, posicion.y * Juego.UNIDAD_DEL_MUNDO);
			personajes.add(actor);
		}

		// Añade una luz puntual a cada cuerpo físico recién creado.
		mundoVirtual.getBodies(cuerpos); // Actualiza la lista de cuerpos.
		if (cuerpos.size > 0) {
			for (Body cuerpo : cuerpos) {
				// Crea una luz pequeña y la asocia al cuerpo.
				PointLight punto = new PointLight(luz, 1000, Color.BLACK, 2, 2, 2);
				punto.attachToBody(cuerpo);
				luces.add(punto); // Añade a la lista de luces gestionadas.
			}
		}

		// Agrega el jefe correspondiente al nivel actual.
		agregarGefe(datosNiveles.getNumeroNivel());

		// Inicia la secuencia de introducción del nivel si aplica.
		intro();

		// ---------------------------------------------------------------------------------
		// ADVERTENCIA: El siguiente bloque de código que crea RevoluteJoints entre todos
		// los cuerpos físicos del mundo es altamente inusual y su propósito no está claro.
		// Podría ser código experimental, un error, o tener un objetivo muy específico
		// no evidente. Unir todos los cuerpos en una cadena de esta manera generalmente
		// no es un comportamiento deseado en la mayoría de los juegos y puede llevar
		// a físicas impredecibles o no deseadas.
		// SE RECOMIENDA REVISAR ESTE BLOQUE DETENIDAMENTE. SI SU FUNCIONALIDAD
		// NO ES ESENCIAL O CLARA, CONSIDERAR SU ELIMINACIÓN.
		// ---------------------------------------------------------------------------------
		mundoVirtual.getBodies(cuerpos); // Obtiene todos los cuerpos nuevamente.
		if (cuerpos.size > 0) {
			RevoluteJointDef jointDef = new RevoluteJointDef(); // Definición de la articulación de revolución.
			// Itera sobre los cuerpos para crear articulaciones entre pares consecutivos.
			for (int i = 0; i < cuerpos.size; i++) {
				jointDef.localAnchorA.set(0.8f, 0.8f); // Punto de anclaje en el cuerpo A.
				jointDef.localAnchorB.set(0.8f, 0.8f); // Punto de anclaje en el cuerpo B.

				// Asegura que haya al menos dos cuerpos para formar una articulación.
				if (i >= 0 && i <= cuerpos.size - 2) {
					jointDef.bodyB = cuerpos.get(i);     // Cuerpo B de la articulación.
					jointDef.bodyA = cuerpos.get(i + 1); // Cuerpo A de la articulación.
					mundoVirtual.createJoint(jointDef);  // Crea la articulación en el mundo físico.
				}
			}
		}
	}

	/**
	 * Obtiene el personaje jefe del nivel actual.
	 * @return El personaje jefe ({@link Personaje}), o {@code null} si no hay jefe en este nivel.
	 */
	public Personaje getJefeNivel() {
		return jefeNivel;
	}

	/**
	 * Gestiona la secuencia de introducción del nivel.
	 * Para el nivel 1, posiciona al jugador y retrasa el inicio del movimiento de la cámara
	 * y la interactividad del jugador.
	 */
	public void intro() {
		// La introducción solo se activa para el nivel 1.
		intro = (datosNiveles.getNumeroNivel() == 1);
		// La cámara no se mueve durante la introducción.
		this.moverCamara = !intro;

		if (intro) {
			// Posiciona al jugador al inicio de la pantalla para la intro.
			jugador.setPosition(0, 384);
			// Informa al jugador que está en la secuencia de introducción.
			jugador.setIntro(!intro); // True si está en intro

			// Acción vacía con retraso (posiblemente para sincronizar con otros eventos o animaciones).
			nivel.addAction(Actions.sequence(Actions.delay(3f), Actions.run(new Runnable() {
				public void run() {
					// No realiza ninguna acción específica aquí.
				}
			})));

			// Acción para finalizar la introducción después de 4 segundos.
			nivel.addAction(Actions.sequence(Actions.delay(4f), Actions.run(new Runnable() {
				public void run() {
					intro = false; // Finaliza la bandera de introducción.
					moverCamara = !intro; // Permite que la cámara se mueva.
					jugador.setIntro(!intro); // Informa al jugador que la intro ha terminado.
				}
			})));
		} else {
			// Si no es el nivel 1, informa al jugador que no hay introducción.
			jugador.setIntro(!intro);
		}
	}

	/**
	 * Agrega el jefe correspondiente al número de nivel especificado.
	 * Cada jefe se instancia, posiciona y se le asigna una dureza (vida).
	 *
	 * @param numero El número del nivel para el cual se agregará el jefe.
	 */
	private void agregarGefe(int numero) {
		// Jefe para el nivel 10.
		if (numero == 10) {
			gefeUno = new JefeUno(recurso.get("texturas/pezGlobo.atlas", TextureAtlas.class).getRegions(), 0.1f,
					Animation.PlayMode.LOOP, pantalla, 128, 128, JefeUno.ESTATICO);
			gefeUno.setPosition(13440 - gefeUno.getWidth() * Juego.UNIDAD_DEL_MUNDO, 240); // Posición al final del nivel.
			gefeUno.setDureza(300); // Vida del jefe.
			personajes.add(gefeUno); // Añade el jefe a la lista de personajes.
			jefeNivel = gefeUno; // Establece como el jefe actual del nivel.
		}

		// Jefe para el nivel 20.
		if (numero == 20) {
			gefeDos = new JefeDos(recurso.get("texturas/pulpo.atlas", TextureAtlas.class).getRegions(), 0.1f,
					Animation.PlayMode.LOOP, pantalla, 128, 128, JefeDos.ESTATICO);
			gefeDos.setPosition(13440 - gefeDos.getWidth() * Juego.UNIDAD_DEL_MUNDO, 240);
			gefeDos.setDureza(500);
			personajes.add(gefeDos);
			jefeNivel = gefeDos;
		}

		// Jefe para el nivel 30.
		if (numero == 30) {
			gefeTres = new JefeTres(recurso.get("texturas/pezGlobo.atlas", TextureAtlas.class).getRegions(), 0.1f,
					Animation.PlayMode.LOOP, pantalla, 128, 128, JefeTres.ESTATICO);
			gefeTres.setPosition(13440 - gefeTres.getWidth() * Juego.UNIDAD_DEL_MUNDO, 240);
			gefeTres.setDureza(700);
			personajes.add(gefeTres);
			jefeNivel = gefeTres;
		}

		// Jefe para el nivel 40 (jefe final).
		if (numero == 40) {
			gefeCuatro = new JefeCuatro(recurso.get("texturas/pezGlobo.atlas", TextureAtlas.class).getRegions(), 0.1f,
					Animation.PlayMode.LOOP, pantalla, 128, 128, JefeCuatro.ESTATICO);
			gefeCuatro.setPosition(13440 - gefeCuatro.getWidth() * Juego.UNIDAD_DEL_MUNDO, 240);
			gefeCuatro.setDureza(900);
			personajes.add(gefeCuatro);
			jefeNivel = gefeCuatro;
		}
	}

	/**
	 * Inicializa o reinicia el estado del nivel.
	 * Configura la cámara, la posición del jugador y su estado.
	 */
	@Override
	protected void iniciar() {
		// Reinicia la posición y zoom de la cámara.
		camara.position.x = Juego.ANCHO_PANTALLA / 2f / Juego.UNIDAD_DEL_MUNDO;
		camara.position.y = Juego.ANCHO_PANTALLA / 2f / Juego.UNIDAD_DEL_MUNDO; // Parece usar ANCHO_PANTALLA para Y también.
		camara.zoom = 1;
		camara.setToOrtho(false, Juego.ANCHO_PANTALLA / Juego.UNIDAD_DEL_MUNDO,
				Juego.ALTO_PANTALLA / Juego.UNIDAD_DEL_MUNDO);
		camara.update();

		// Establece la matriz de proyección para el batch de dibujo.
		pincel.setProjectionMatrix(camara.combined);

		moverCamara = true; // Permite el movimiento de la cámara.

		// Configura la posición inicial y estado del jugador.
		jugador.setPosition(0, 384); // Posición inicial del jugador.
		jugador.setVivo(true);
		jugador.setFinNivel(false);
		jugador.setTerminarNivel(false);
		jugador.resetearJugador(); // Restablece atributos específicos del jugador.

		personajes.add(jugador); // Añade el jugador a la lista de personajes del nivel.
	}

	/**
	 * Actualiza la lógica del nivel en cada cuadro del juego.
	 * Maneja el scroll del fondo, la actualización de personajes, la detección del fin de nivel,
	 * las batallas contra jefes y el movimiento de la cámara.
	 *
	 * @param delta Tiempo transcurrido desde el último cuadro, en segundos.
	 */
	@Override
	public void actualizar(float delta) {
		// Solo actualiza el fondo si el jugador no ha terminado el nivel y está vivo.
		if (!jugador.isFinNivel() && jugador.isVivo()) {
			// Si el scroll del fondo está habilitado.
			if (dato.isFondoScroll()) {
				// Lógica para el efecto de scroll infinito del fondo.
				if (xFondo <= camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2
						- Juego.ANCHO_PANTALLA) {
					xFondo = camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2;
				}
				fondo[0].setPosition(xFondo, 0); // Posiciona la primera imagen de fondo.
				fondo[1].setPosition(xFondo + Juego.ANCHO_PANTALLA, 0); // Posiciona la segunda imagen de fondo para el scroll.
			}
			// Si el parallax del fondo está habilitado (diferente al scroll).
			if (dato.isFondoParallax()) {
				// La segunda imagen de fondo se mueve con la cámara para un efecto parallax simple.
				fondo[1].setPosition((camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);
			}
		}

		// Actualiza todos los personajes en el nivel.
		for (Personaje personaje : personajes) {
			personaje.actualizar(delta);
		}

		// Comprueba si la cámara ha llegado al final del área jugable del nivel.
		if (Juego.ANCHO_PANTALLA / 2 + camara.position.x * Juego.UNIDAD_DEL_MUNDO >= Juego.LARGO_NIVEL) {
			// Si el nivel actual es un nivel de jefe (10, 20, 30, o 40).
			if (datosNiveles.getNumeroNivel() == 10 || datosNiveles.getNumeroNivel() == 20
					|| datosNiveles.getNumeroNivel() == 30 || datosNiveles.getNumeroNivel() == 40) {
				jugador.setGefe(true); // Activa el modo jefe en el jugador.
				moverCamara = false;  // Detiene el movimiento de la cámara.

				// Comprueba la vida del jefe correspondiente y finaliza el nivel si el jefe es derrotado.
				if (gefeUno != null) {
					if (gefeUno.getDureza() <= 0) { // Si la vida del jefe 1 es cero o menos.
						gefeUno.setVivo(false); // Marca al jefe como no vivo.
						if (!gefeUno.isVivo()) { // Confirmación.
							jugador.setGefe(false); // Desactiva el modo jefe.
							jugador.setFinNivel(true); // Marca el nivel como terminado.
						}
					}
				}
				// Repite la lógica para los otros jefes.
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
			} else { // Si no es un nivel de jefe, termina el nivel normalmente.
				moverCamara = false;
				jugador.setFinNivel(true);
			}
		}

		// Si el jugador no está vivo, detiene el movimiento de la cámara.
		if (!jugador.isVivo()) {
			moverCamara = false;
		}

		// Mueve la cámara si está permitido.
		if (moverCamara) {
			// Si el jugador tiene un ítem de velocidad, la cámara se mueve más rápido.
			if (jugador.isItemVelocidad()) {
				camara.position.x += (jugador.getVelocidadCamaraItem() / Juego.DELTA_A_PIXEL * delta)
						/ Juego.UNIDAD_DEL_MUNDO;
			} else { // Velocidad normal de la cámara.
				camara.position.x += (Juego.VELOCIDAD_CAMARA / Juego.DELTA_A_PIXEL * delta) / Juego.UNIDAD_DEL_MUNDO;
			}
		}

		// Actualiza el sistema de iluminación.
		luz.update();
		// Actualiza el efecto de partículas.
		particuala.actualizar(delta);
	}

	/**
	 * Dibuja todos los elementos del nivel.
	 * Esto se hace en varias pasadas: primero los fondos, luego el mapa Tiled,
	 * después los personajes y partículas, y finalmente las luces.
	 *
	 * @param pincel El {@link Batch} (usualmente {@link SpriteBatch}) para dibujar.
	 * @param delta Tiempo transcurrido desde el último cuadro.
	 */
	@Override
	public void dibujar(Batch pincel, float delta) {
		// Primera pasada: Dibuja los objetos de fondo.
		pincel.begin();
		for (Personaje personaje : personajes) {
			if (personaje instanceof Fondo) {
				personaje.dibujar(pincel, delta);
			}
		}
		pincel.end();

		// Renderiza el mapa Tiled.
		render.setView(camara); // Establece la vista de la cámara para el renderizador del mapa.
		render.render(); // Dibuja el mapa.

		// Segunda pasada: Dibuja todos los demás personajes (no fondos).
		pincel.begin();
		for (Personaje personaje : personajes) {
			if (!(personaje instanceof Fondo)) {
				personaje.dibujar(pincel, delta);
			}
		}
		// Dibuja el efecto de partículas.
		particuala.dibujar(pincel, delta);
		pincel.end();

		// Renderiza el sistema de iluminación.
		luz.setCombinedMatrix(camara); // Establece la matriz combinada de la cámara para las luces.
		luz.render(); // Dibuja las luces.
	}

	/**
	 * Guarda los datos específicos del nivel.
	 * Actualmente está vacío, se podría implementar para guardar estados de enemigos, etc.
	 */
	@Override
	public void guardarDatos() {
		// TODO: Implementar la lógica para guardar datos específicos del nivel si es necesario.
		// Por ejemplo, estado de enemigos persistentes, objetos recogidos, etc.
	}

	/**
	 * Libera los recursos utilizados por el nivel.
	 * Limpia las listas de cuerpos, luces y personajes, y desecha el mapa Tiled
	 * y los efectos de partículas.
	 */
	@Override
	public void liberarRecursos() {
		// Obtiene los cuerpos para asegurar que se manejan correctamente, aunque la destrucción
		// principal de cuerpos suele estar en Pantalla.dispose() o Pantalla.hide().
		mundoVirtual.getBodies(cuerpos);

		// Limpia las colecciones de luces y personajes.
		luces.clear(); // Elimina todas las luces de la lista.
		luz.removeAll(); // Elimina todas las luces del RayHandler.
		cuerpos.clear(); // Limpia la lista temporal de cuerpos.
		personajes.clear(); // Elimina todos los personajes de la lista.

		// Libera los recursos del mapa Tiled.
		mapa.dispose();
		// Libera los recursos del efecto de partículas.
		particuala.liberarRecursos();
	}
}
