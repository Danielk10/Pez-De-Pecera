package com.diamon.nucleo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.diamon.datos.InformacionNiveles;
import com.diamon.datos.Configuraciones;
import com.diamon.datos.Datos;
import com.diamon.datos.DatosNiveles;
import com.diamon.pez.publicidad.Publicidad;

import box2dLight.Light;
import box2dLight.RayHandler;

/**
 * Clase abstracta base para todas las pantallas del juego. Implementa la interfaz
 * {@link com.badlogic.gdx.Screen} de LibGDX y se encarga de gestionar elementos
 * comunes a todas las pantallas, así como los métodos del ciclo de vida de una pantalla.
 * Las clases concretas que heredan de {@code Pantalla} deben implementar los métodos
 * abstractos para definir el comportamiento específico de cada pantalla.
 */
public abstract class Pantalla implements Screen {

	/**
	 * Referencia a la instancia principal del juego ({@link Juego}), que gestiona
	 * recursos globales y transiciones de pantalla.
	 */
	protected final Juego juego;

	/**
	 * Colección de todos los personajes ({@link Personaje}) activos en la pantalla.
	 */
	protected Array<Personaje> personajes;

	/**
	 * Administrador de activos ({@link AssetManager}) heredado del {@link Juego},
	 * utilizado para cargar y gestionar recursos como texturas, sonidos, etc.
	 */
	protected AssetManager recurso;

	/**
	 * Escenario ({@link Stage}) principal de la pantalla, donde se añaden y gestionan
	 * los actores ({@link com.badlogic.gdx.scenes.scene2d.Actor}) de la interfaz de usuario
	 * o elementos del juego que no son personajes físicos.
	 */
	protected Stage nivel;

	/**
	 * Referencia al escenario del menú ({@link Stage}) del {@link Juego}, utilizado
	 * para mostrar elementos de menú superpuestos o fondos.
	 */
	protected Stage nivelMenu;

	/**
	 * Cámara ortográfica ({@link OrthographicCamera}) específica de la pantalla,
	 * utilizada para definir la vista del mundo del juego.
	 */
	protected OrthographicCamera camara;

	/**
	 * Objeto {@link SpriteBatch} utilizado para dibujar sprites y texturas en la pantalla.
	 */
	protected SpriteBatch pincel;

	/**
	 * Objeto {@link ShapeRenderer} utilizado para dibujar formas geométricas primitivas,
	 * útil para depuración o elementos visuales simples.
	 */
	protected ShapeRenderer pincelPrueba;

	/**
	 * Datos de configuración general del juego ({@link Datos}), heredados del {@link Juego}.
	 */
	protected Datos dato;

	/**
	 * Ayudante para leer y escribir las configuraciones generales del juego ({@link Configuraciones}),
	 * heredado del {@link Juego}.
	 */
	protected Configuraciones configuracion;

	/**
	 * Datos de los niveles del juego ({@link DatosNiveles}), heredados del {@link Juego}.
	 */
	protected DatosNiveles datosNiveles;

	/**
	 * Ayudante para leer y escribir los datos de los niveles ({@link InformacionNiveles}),
	 * heredado del {@link Juego}.
	 */
	protected InformacionNiveles informacionNiveles;

	/**
	 * Objeto para manejar la publicidad ({@link Publicidad}) dentro del juego,
	 * heredado del {@link Juego}.
	 */
	protected Publicidad publicidad;

	/**
	 * Mundo de físicas Box2D ({@link World}) donde interactúan los objetos del juego,
	 * heredado del {@link Juego}.
	 */
	protected World mundoVirtual;

	/**
	 * Colección de cuerpos ({@link Body}) de Box2D que se encuentran actualmente en el {@link #mundoVirtual}.
	 * Se utiliza para facilitar la eliminación de cuerpos al ocultar o desechar la pantalla.
	 */
	protected Array<Body> cuerpos = new Array<Body>();

	/**
	 * Renderizador de depuración para Box2D ({@link Box2DDebugRenderer}), que permite
	 * visualizar las formas de los cuerpos físicos y sus estados.
	 */
	protected Box2DDebugRenderer debugRenderer;

	/**
	 * Manejador de luces ({@link RayHandler}) para el sistema de iluminación Box2DLights.
	 * Permite crear y renderizar efectos de luz en el mundo de físicas.
	 */
	protected RayHandler luz;

	/**
	 * Colección de luces ({@link Light}) activas en la pantalla, gestionadas por {@link #luz}.
	 */
	protected Array<Light> luces;

	/**
	 * Constructor de la clase {@code Pantalla}. Inicializa los recursos compartidos
	 * desde el objeto {@link Juego}, configura la cámara específica de la pantalla,
	 * el escenario principal ({@link #nivel}), el renderizador de depuración de Box2D
	 * y el sistema de iluminación ({@link RayHandler}).
	 *
	 * @param juego La instancia principal del juego ({@link Juego}) que esta pantalla pertenece.
	 */
	protected Pantalla(Juego juego) {

		// Obtiene la referencia al mundo virtual (físicas) desde la clase Juego.
		mundoVirtual = juego.mundoVirtual;

		// Inicializa el manejador de luces para el mundo virtual.
		luz = new RayHandler(mundoVirtual);

		// Inicializa el arreglo para almacenar las luces de la pantalla.
		luces = new Array<Light>();

		this.juego = juego;

		// Obtiene la referencia al manejador de publicidad.
		publicidad = juego.publicidad;

		// Obtiene la referencia al escenario del menú principal.
		nivelMenu = juego.nivelMenu;

		// Crea el escenario principal de esta pantalla con un viewport que estira.
		nivel = new Stage(new StretchViewport(Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA));

		// Configura la cámara del escenario principal.
		((OrthographicCamera) nivel.getCamera()).setToOrtho(false, Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA);

		// Crea y configura la cámara principal del juego para esta pantalla.
		// Las dimensiones se ajustan según las unidades del mundo definidas en Juego.
		camara = new OrthographicCamera();

		camara.setToOrtho(false, Juego.ANCHO_PANTALLA / Juego.UNIDAD_DEL_MUNDO,
				Juego.ALTO_PANTALLA / Juego.UNIDAD_DEL_MUNDO);

		camara.update();

		// Inicializa el SpriteBatch para dibujar texturas.
		pincel = new SpriteBatch();

		// Inicializa el ShapeRenderer para dibujar formas (útil para debug).
		pincelPrueba = new ShapeRenderer();

		// Inicializa el renderizador de debug para Box2D.
		debugRenderer = new Box2DDebugRenderer();

		// Inicializa el arreglo para almacenar los personajes de la pantalla.
		personajes = new Array<Personaje>();

		// Obtiene referencias a recursos y datos globales desde la clase Juego.
		recurso = juego.recurso;

		configuracion = juego.configuracion;

		dato = juego.dato;

		informacionNiveles = juego.informacionNiveles;

		datosNiveles = juego.datosNiveles;

	}

	/**
	 * Método abstracto llamado cuando la pantalla se muestra por primera vez o se
	 * vuelve a mostrar después de estar oculta. Aquí se deben cargar los recursos
	 * específicos de la pantalla y configurar su estado inicial.
	 */
	public abstract void mostrar();

	/**
	 * Método abstracto para manejar los eventos de entrada (teclado, ratón, táctil)
	 * específicos de esta pantalla. Se llama después de {@link #mostrar()}.
	 */
	public abstract void eventos();

	/**
	 * Método abstracto para detectar y manejar las colisiones entre los personajes
	 * y otros objetos del juego en el mundo de físicas.
	 */
	public abstract void colisiones();

	/**
	 * Método abstracto llamado en cada cuadro del juego para actualizar la lógica
	 * de la pantalla, como el movimiento de personajes, la inteligencia artificial, etc.
	 *
	 * @param delta El tiempo transcurrido en segundos desde el último cuadro.
	 */
	public abstract void actualizar(float delta);

	/**
	 * Método abstracto llamado en cada cuadro del juego para dibujar todos los
	 * elementos visuales de la pantalla.
	 *
	 * @param pincel El {@link Batch} (usualmente un {@link SpriteBatch}) utilizado para dibujar.
	 * @param delta  El tiempo transcurrido en segundos desde el último cuadro.
	 */
	public abstract void dibujar(Batch pincel, float delta);

	/**
	 * Método abstracto para guardar cualquier dato persistente de la pantalla,
	 * como el progreso del jugador o configuraciones específicas. Se llama
	 * típicamente cuando la pantalla se oculta o se pausa.
	 */
	public abstract void guardarDatos();

	/**
	 * Método abstracto para liberar los recursos específicos de la pantalla que
	 * fueron cargados en {@link #mostrar()}. Se llama cuando la pantalla se
	 * oculta o se destruye.
	 */
	public abstract void liberarRecursos();

	/**
	 * Llamado cuando esta pantalla se convierte en la pantalla actual para un {@link Juego}.
	 * Establece el procesador de entrada al escenario de esta pantalla y luego llama
	 * a los métodos {@link #mostrar()} y {@link #eventos()} de la subclase.
	 */
	@Override
	public void show() {

		// Establece el escenario 'nivel' como el procesador de entrada principal.
		Gdx.input.setInputProcessor(nivel);

		// Llama al método mostrar() de la subclase para configurar la pantalla.
		mostrar();

		// Llama al método eventos() de la subclase para configurar los manejadores de eventos.
		eventos();
	}

	/**
	 * Llamado por el método de renderizado del {@link Juego} en cada cuadro.
	 * Se encarga de limpiar la pantalla, configurar las matrices de proyección,
	 * llamar a los métodos de lógica y dibujo de la subclase, y renderizar
	 * el escenario y las líneas de depuración de Box2D si está activado.
	 *
	 * @param delta El tiempo en segundos desde el último renderizado.
	 */
	@Override
	public void render(float delta) {

		// Limpia la pantalla con un color azul. El último argumento 'true' indica si se limpia el buffer de profundidad.
		ScreenUtils.clear(0.0F, 0.0F, 1.0F, 1.0F, true);

		// Establece la matriz de proyección del SpriteBatch a la combinada de la cámara.
		pincel.setProjectionMatrix(camara.combined);

		// Establece la matriz de proyección del ShapeRenderer a la combinada de la cámara.
		pincelPrueba.setProjectionMatrix(camara.combined);

		// Establece el color para el ShapeRenderer (usado para debug).
		pincelPrueba.setColor(Color.RED);

		// Llama al método colisiones() de la subclase para manejar la lógica de colisiones.
		colisiones();

		// Llama al método actualizar() de la subclase para actualizar la lógica del juego.
		actualizar(delta);

		// Actualiza la cámara del juego.
		camara.update();

		// Llama al método dibujar() de la subclase para renderizar los elementos del juego.
		dibujar(pincel, delta);

		// Dibuja el escenario 'nivel' (UI, etc.).
		nivel.draw();

		// Actualiza la lógica de los actores dentro del escenario 'nivel'.
		nivel.act();

		// Si el modo de prueba está activado en los datos de configuración, renderiza el debug de Box2D.
		if (dato.isPrueba()) {

			debugRenderer.render(mundoVirtual, camara.combined);
		}

	}

	/**
	 * Llamado cuando la ventana del juego cambia de tamaño.
	 * Actualiza el viewport del escenario {@link #nivel}.
	 * @see com.badlogic.gdx.ApplicationListener#resize(int, int)
	 */
	@Override
	public void resize(int ancho, int alto) {

		// Actualiza el viewport del escenario 'nivel' para que coincida con el nuevo tamaño de la ventana.
		nivel.getViewport().update(ancho, alto);

	}

	public World getMundoVirtual() {
		return mundoVirtual;
	}

	public void setMundoVirtual(World mundoVirtual) {
		this.mundoVirtual = mundoVirtual;
	}

	/**
	 * Llamado cuando el {@link com.badlogic.gdx.Application} es pausado, usualmente cuando pierde el foco.
	 * Guarda los datos de la pantalla y anula el procesador de entrada.
	 */
	@Override
	public void pause() {

		// Guarda los datos actuales de la pantalla.
		guardarDatos();

		// Anula el procesador de entrada para evitar interacciones mientras está pausado.
		Gdx.input.setInputProcessor(null);
	}

	/**
	 * Llamado cuando el {@link com.badlogic.gdx.Application} es reanudado desde el estado de pausa.
	 * Restaura el procesador de entrada al escenario de esta pantalla.
	 */
	@Override
	public void resume() {

		// Restaura el escenario 'nivel' como el procesador de entrada.
		Gdx.input.setInputProcessor(nivel);

	}

	/**
	 * Llamado cuando esta pantalla ya no es la pantalla actual para un {@link Juego}.
	 * Guarda los datos, limpia la lista de personajes, libera recursos específicos,
	 * y desecha objetos de LibGDX y Box2D para prevenir fugas de memoria.
	 */
	@Override
	public void hide() {

		// Guarda los datos actuales de la pantalla.
		guardarDatos();

		// Limpia la lista de personajes activos en la pantalla.
		personajes.clear();

		// Llama al método de la subclase para liberar recursos específicos de la pantalla.
		liberarRecursos();

		// Desecha el SpriteBatch.
		pincel.dispose();

		// Desecha el ShapeRenderer.
		pincelPrueba.dispose();

		// Anula el procesador de entrada.
		Gdx.input.setInputProcessor(null);

		// Desecha el escenario principal de la pantalla.
		nivel.dispose();

		// Desecha el manejador de luces.
		luz.dispose();

		// Obtiene todos los cuerpos del mundo de físicas y los almacena en el array 'cuerpos'.
		mundoVirtual.getBodies(cuerpos);

		// Si hay cuerpos en el mundo virtual, los destruye uno por uno.
		// Esto es crucial para liberar la memoria usada por Box2D.
		if (cuerpos.size > 0) {

			for (Body cuerpo : cuerpos) {

				mundoVirtual.destroyBody(cuerpo);

			}

		}

		// Limpia el array de cuerpos después de destruirlos.
		cuerpos.clear();

		// Limpia el array de luces. Las luces individuales son usualmente desechadas por el RayHandler.
		luces.clear();

	}

	/**
	 * Llamado cuando esta pantalla debe desechar todos sus recursos.
	 * Similar a {@link #hide()}, pero usualmente implica una limpieza más profunda
	 * ya que la pantalla no se reutilizará.
	 */
	@Override
	public void dispose() {

		// Guarda los datos actuales de la pantalla.
		guardarDatos();

		// Limpia la lista de personajes activos en la pantalla.
		personajes.clear();

		// Llama al método de la subclase para liberar recursos específicos de la pantalla.
		liberarRecursos();

		// Desecha el SpriteBatch.
		pincel.dispose();

		// Desecha el ShapeRenderer.
		pincelPrueba.dispose();

		// Anula el procesador de entrada.
		Gdx.input.setInputProcessor(null);

		// Desecha el escenario principal de la pantalla.
		nivel.dispose();

		// Obtiene todos los cuerpos del mundo de físicas y los almacena en el array 'cuerpos'.
		mundoVirtual.getBodies(cuerpos);

		// Si hay cuerpos en el mundo virtual, los destruye uno por uno.
		if (cuerpos.size > 0) {

			for (Body cuerpo : cuerpos) {

				mundoVirtual.destroyBody(cuerpo);

			}

		}
		// Limpia el array de cuerpos después de destruirlos.
		cuerpos.clear();

		// Limpia el array de luces.
		luces.clear();

		// Desecha el manejador de luces.
		luz.dispose();

		// Desecha el renderizador de depuración de Box2D.
		debugRenderer.dispose();

	}

}
