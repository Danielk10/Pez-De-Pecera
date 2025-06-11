package com.diamon.utilidades;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.diamon.datos.InformacionNiveles;
import com.diamon.datos.Configuraciones;
import com.diamon.datos.Datos;
import com.diamon.datos.DatosNiveles;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;
import com.diamon.personajes.Algas;
import com.diamon.personajes.Bomba;
import com.diamon.personajes.Cursor;
import com.diamon.personajes.Fondo;
import com.diamon.personajes.Jugador;
import com.diamon.personajes.PezAngel;
import com.diamon.personajes.PezGloboAmarillo;
import com.diamon.personajes.PezGloboNaranja;
import com.diamon.personajes.Pulpo;
import com.diamon.personajes.Terreno;

/**
 * Controlador para el editor de niveles en el juego.
 * Gestiona la interfaz de usuario (UI) del editor, el estado de edición,
 * el manejo de la entrada del usuario para operaciones de edición (agregar, mover, borrar actores),
 * y la interacción con los datos de los niveles (guardar/cargar posiciones de actores).
 */
public class EditorNivel {

	/**
	 * Cámara ortográfica utilizada para visualizar y manipular el nivel en el editor.
	 */
	private OrthographicCamera camara;

	/**
	 * Vector utilizado en la lógica de paneo de la cámara con entrada táctil/ratón (coordenada actual).
	 */
	private Vector3 corriendo;

	/**
	 * Vector utilizado en la lógica de paneo de la cámara con entrada táctil/ratón (coordenada anterior).
	 */
	private Vector3 detras;

	/**
	 * Vector utilizado para calcular el desplazamiento en el paneo de la cámara.
	 */
	private Vector3 delta;

	/**
	 * Referencia al arreglo de personajes de la pantalla actual, usado para mostrar/manipular actores.
	 */
	private Array<Personaje> personajes;

	/**
	 * Referencia a la pantalla que contiene este editor.
	 */
	private Pantalla pantalla;

	/**
	 * Administrador de activos para cargar recursos necesarios para el editor (ej. skins de UI).
	 */
	private AssetManager recurso;

	/**
	 * Bandera que indica si el editor está en modo "agregar actor".
	 */
	private boolean agregar;

	/**
	 * Bandera que indica si el movimiento del escenario (cámara) está restringido al eje Y.
	 */
	private boolean moverEnY;

	/**
	 * Bandera que indica si el editor ha terminado su operación actual o está inactivo.
	 */
	private boolean terminar;

	/**
	 * Escenario (Stage) de LibGDX donde se colocan los elementos de la UI del editor.
	 */
	protected Stage nivel;

	/**
	 * Objeto para manejar la información de los niveles (probablemente para leer/escribir datos de niveles).
	 */
	private InformacionNiveles informacionNiveles;

	/**
	 * Objeto que contiene los datos específicos de los niveles (posiciones de actores, etc.).
	 */
	private DatosNiveles datosNiveles;

	/**
	 * Objeto para manejar las configuraciones generales del juego.
	 */
	protected Configuraciones configuracion;

	/**
	 * Objeto que contiene los datos de configuración general del juego.
	 */
	protected Datos dato;

	/**
	 * Cadena que representa el tipo de actor actualmente seleccionado para agregar.
	 * Usualmente el nombre completo de la clase del actor.
	 */
	protected String tipo;

	/**
	 * Botón para borrar todos los actores del nivel actual.
	 */
	private TextButton borrarActores;

	/**
	 * Botón para activar/desactivar el modo de agregar actores.
	 */
	private TextButton agreagarActores; // Nota: posible errata "agreagar" en lugar de "agregar"

	/**
	 * Botón para activar/desactivar el modo de mover el escenario (cámara) en el eje X.
	 */
	private TextButton moverEsenario; // Nota: posible errata "Esenario"

	/**
	 * Botón para mover la cámara hacia la derecha.
	 */
	private TextButton derecha;

	/**
	 * Botón para mover la cámara hacia la izquierda.
	 */
	private TextButton izquierda;

	/**
	 * Bandera que indica si la tecla/botón de mover cámara a la derecha está presionado.
	 */
	private boolean pD;
	/**
	 * Bandera que indica si la tecla/botón de mover cámara a la izquierda está presionado.
	 */
	private boolean pI;

	/**
	 * Botón para activar/desactivar el modo de mover el escenario (cámara) en el eje Y (y X).
	 */
	private TextButton moverEsenarioEnY;

	/**
	 * Etiqueta para mostrar información sobre el zoom de la cámara.
	 */
	private Label zoomCamara;

	/**
	 * Etiqueta para mostrar el número del nivel actual.
	 */
	private Label mundo;

	/**
	 * Botón para aumentar el zoom de la cámara.
	 */
	private TextButton zoomCamaraMas;

	/**
	 * Botón para disminuir el zoom de la cámara.
	 */
	private TextButton zoomCamaraMenos;

	/**
	 * Botón para activar/desactivar el efecto de scroll del fondo.
	 */
	private TextButton fondoScroll;

	/**
	 * Botón para activar/desactivar el efecto de parallax del fondo.
	 */
	private TextButton fondoParallax;

	/**
	 * Botón para activar/desactivar el modo de actualizar/borrar actores individuales.
	 */
	private TextButton actualizarActores;

	/**
	 * Caja de selección para elegir el tipo de actor a agregar.
	 */
	private SelectBox<String> tipoActor;

	/**
	 * Caja de selección para elegir el número de nivel a editar.
	 */
	private SelectBox<String> numeroNivel;

	/**
	 * Cursor utilizado en el editor para indicar la posición de acción.
	 */
	private Cursor cursor;

	/**
	 * Bandera que indica si el editor está en modo "actualizar/borrar actor individual".
	 */
	private boolean actualizar;

	/**
	 * Bandera para controlar la lógica de toque, especialmente para agregar actores (evita agregar múltiples con un solo toque largo).
	 */
	private boolean toque;

	/**
	 * Velocidad a la que se mueve la cámara en el editor.
	 */
	private int velocidadCamara;

	/**
	 * Constructor del editor de niveles.
	 * Inicializa las referencias a objetos del juego (cámara, datos, pantalla, etc.),
	 * configura el estado inicial del editor y llama a los métodos para crear la UI y
	 * configurar los manejadores de eventos.
	 *
	 * @param nivel El escenario (Stage) donde se añadirán los elementos de UI del editor.
	 * @param informacionNiveles Manejador de información de niveles.
	 * @param datosNiveles Datos específicos de los niveles.
	 * @param configuracion Configuraciones generales del juego.
	 * @param dato Datos de configuración general.
	 * @param camara Cámara principal del juego, usada por el editor.
	 * @param personajes Arreglo de personajes de la pantalla actual.
	 * @param pantalla Pantalla que contiene el editor.
	 * @param recurso Administrador de activos para cargar recursos de UI.
	 * @param cursor Cursor del editor.
	 */
	public EditorNivel(final Stage nivel, final InformacionNiveles informacionNiveles, final DatosNiveles datosNiveles,
			Configuraciones configuracion, Datos dato, final OrthographicCamera camara,
			final Array<Personaje> personajes, final Pantalla pantalla, final AssetManager recurso, Cursor cursor) {

		// Inicialización de referencias a objetos pasados como parámetros.
		this.camara = camara;
		this.informacionNiveles = informacionNiveles;
		this.datosNiveles = datosNiveles;
		this.configuracion = configuracion;
		this.dato = dato;
		this.personajes = personajes;
		this.recurso = recurso;
		this.pantalla = pantalla;
		this.nivel = nivel; // Escenario para la UI del editor.
		this.cursor = cursor;

		// Inicialización de variables de estado y control del editor.
		velocidadCamara = 5; // Velocidad de movimiento de la cámara en el editor.
		pD = pI = false;     // Banderas para movimiento de cámara con teclas/botones (derecha/izquierda).
		actualizar = false;  // Modo de actualizar/borrar actor individual desactivado inicialmente.
		toque = false;       // Control de eventos de toque.
		agregar = false;     // Modo de agregar actor desactivado inicialmente.
		moverEnY = false;    // Movimiento de cámara solo en X inicialmente.
		terminar = true;     // Editor inactivo/terminado inicialmente.
		tipo = "";           // Tipo de actor a agregar vacío inicialmente.

		// Inicialización de vectores para el paneo de cámara.
		corriendo = new Vector3();
		detras = new Vector3(-1, -1, -1); // Valor inicial que indica que no hay toque previo.
		delta = new Vector3();

		// Creación de la interfaz de usuario del editor.
		ui();
		// Configuración de los manejadores de eventos para la UI y entrada general.
		eventos();
	}

	/**
	 * Crea y configura los elementos de la interfaz de usuario (UI) del editor.
	 * Esto incluye botones, etiquetas, cajas de selección, etc.
	 */
	private void ui() {

		// Botón para borrar todos los actores del nivel actual.
		borrarActores = new TextButton("Borrar Actores", recurso.get("uis/general/uiskin.json", Skin.class));
		borrarActores.setSize(160, 32); // Define tamaño.
		borrarActores.setPosition(16, 16); // Define posición.
		borrarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F); // Establece color y transparencia.

		// Botón para mover la cámara hacia la derecha.
		derecha = new TextButton("Derecha +", recurso.get("uis/general/uiskin.json", Skin.class));
		derecha.setSize(96, 32);
		derecha.setPosition(432, 16);
		derecha.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		// Botón para mover la cámara hacia la izquierda.
		izquierda = new TextButton("Izquierda -", recurso.get("uis/general/uiskin.json", Skin.class));
		izquierda.setSize(96, 32);
		izquierda.setPosition(336, 16);
		izquierda.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		// Botón para activar el modo de actualizar/borrar actores individuales.
		actualizarActores = new TextButton("Actualizar Actores", recurso.get("uis/general/uiskin.json", Skin.class));
		actualizarActores.setSize(160, 32);
		actualizarActores.setPosition(176, 16);
		actualizarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		// Caja de selección para el número de nivel.
		numeroNivel = new SelectBox<String>(recurso.get("uis/general/uiskin.json", Skin.class));
		numeroNivel.setSize(64, 32);
		numeroNivel.setPosition(64, 416);
		numeroNivel.setColor(1.0F, 1.0F, 1.0F, 0.7F);
		// Pobla la caja de selección con números de nivel (1 a 40).
		String[] niveles = new String[40];
		for (int i = 0; i < niveles.length; i++) {
			niveles[i] = "" + (i + 1);
		}
		numeroNivel.setItems(niveles);
		// Selecciona el nivel actual almacenado en datosNiveles.
		numeroNivel.setSelectedIndex(datosNiveles.getNumeroNivel() - 1);

		// Etiqueta "Nivel: ".
		mundo = new Label("Nivel: ", recurso.get("uis/general/uiskin.json", Skin.class), "default-font", Color.GREEN);
		mundo.setSize(96, 32);
		mundo.setPosition(16, 416);
		mundo.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		// Botón para activar el modo de agregar actores.
		agreagarActores = new TextButton("Agregar Actores", recurso.get("uis/general/uiskin.json", Skin.class));
		agreagarActores.setSize(144, 32);
		agreagarActores.setPosition(16, 352);
		agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		// Botón para activar el movimiento del escenario (cámara) en X.
		moverEsenario = new TextButton("Mover Escenario", recurso.get("uis/general/uiskin.json", Skin.class));
		moverEsenario.setSize(144, 32);
		moverEsenario.setPosition(16, 448);
		moverEsenario.setColor(1, 0, 0, 1); // Color rojo indica modo activo por defecto.

		// Botón para activar el movimiento del escenario (cámara) en X e Y.
		moverEsenarioEnY = new TextButton("Mover Escenario Y", recurso.get("uis/general/uiskin.json", Skin.class));
		moverEsenarioEnY.setSize(144, 32);
		moverEsenarioEnY.setPosition(160, 448);
		moverEsenarioEnY.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		// Botón para activar el scroll del fondo.
		fondoScroll = new TextButton("Fondo Scroll", recurso.get("uis/general/uiskin.json", Skin.class));
		fondoScroll.setSize(144, 32);
		fondoScroll.setPosition(160, 416);
		// Establece el color del botón según el estado actual de fondoScroll.
		if (dato.isFondoScroll()) {
			fondoScroll.setColor(1, 0, 0, 1); // Activo.
		} else {
			fondoScroll.setColor(1.0F, 1.0F, 1.0F, 0.7F); // Inactivo.
		}

		// Botón para activar el parallax del fondo.
		fondoParallax = new TextButton("Fondo Parallax", recurso.get("uis/general/uiskin.json", Skin.class));
		fondoParallax.setSize(144, 32);
		fondoParallax.setPosition(160, 384);
		// Establece el color del botón según el estado actual de fondoParallax.
		if (dato.isFondoParallax()) {
			fondoParallax.setColor(1, 0, 0, 1); // Activo.
		} else {
			fondoParallax.setColor(1.0F, 1.0F, 1.0F, 0.7F); // Inactivo.
		}

		// Etiqueta "Zoom: ".
		zoomCamara = new Label("Zoom: ", recurso.get("uis/general/uiskin.json", Skin.class), "default-font",
				Color.GREEN);
		zoomCamara.setSize(96, 32);
		zoomCamara.setPosition(16, 384);
		zoomCamara.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		// Botón para aumentar zoom.
		zoomCamaraMas = new TextButton("+", recurso.get("uis/general/uiskin.json", Skin.class));
		zoomCamaraMas.setSize(32, 32);
		zoomCamaraMas.setPosition(96, 384);
		zoomCamaraMas.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		// Botón para disminuir zoom.
		zoomCamaraMenos = new TextButton("-", recurso.get("uis/general/uiskin.json", Skin.class));
		zoomCamaraMenos.setSize(32, 32);
		zoomCamaraMenos.setPosition(64, 384);
		zoomCamaraMenos.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		// Caja de selección para el tipo de actor a agregar.
		tipoActor = new SelectBox<String>(recurso.get("uis/general/uiskin.json", Skin.class));
		tipoActor.setSize(144, 32);
		tipoActor.setPosition(160, 352);
		tipoActor.setColor(1.0F, 1.0F, 1.0F, 0.7F);
		// Define los tipos de actores disponibles.
		tipoActor.setItems("Pulpo", "PezAngel", "PezGloboNaranja", "PezGloboAmarillo", "Bomba", "Algas");
		// Establece el tipo de actor seleccionado inicialmente.
		tipo = "com.diamon.personajes." + tipoActor.getSelected();
	}

	/**
	 * Agrega todos los elementos de la UI del editor al escenario (Stage) para hacerlos visibles e interactuables.
	 * También restablece algunos estados iniciales del editor.
	 */
	public void agregarUI() {
		// Restablece estados de edición.
		agregar = false;    // No está en modo agregar actor.
		moverEnY = false;   // Movimiento de cámara solo en X.
		actualizar = false; // No está en modo actualizar/borrar actor.

		// Actualiza el tipo de actor seleccionado.
		tipo = "com.diamon.personajes." + tipoActor.getSelected();

		// Configura colores iniciales de botones para indicar el modo activo.
		moverEsenario.setColor(1, 0, 0, 1); // Movimiento de escenario en X activo.
		moverEsenarioEnY.setColor(1.0F, 1.0F, 1.0F, 0.7F); // Movimiento en Y inactivo.
		actualizarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F); // Actualizar actor inactivo.

		// Configura color de botones de fondo según el estado guardado.
		if (dato.isFondoScroll()) {
			fondoScroll.setColor(1, 0, 0, 1);
		} else {
			fondoScroll.setColor(1.0F, 1.0F, 1.0F, 0.7F);
		}
		if (dato.isFondoParallax()) {
			fondoParallax.setColor(1, 0, 0, 1);
		} else {
			fondoParallax.setColor(1.0F, 1.0F, 1.0F, 0.7F);
		}

		// Añade todos los elementos de UI al escenario 'nivel'.
		nivel.addActor(borrarActores);
		nivel.addActor(moverEsenario);
		nivel.addActor(moverEsenarioEnY);
		nivel.addActor(agreagarActores);
		nivel.addActor(zoomCamara);
		nivel.addActor(zoomCamaraMenos);
		nivel.addActor(zoomCamaraMas);
		nivel.addActor(mundo); // Etiqueta "Nivel: "
		nivel.addActor(fondoScroll);
		nivel.addActor(fondoParallax);
		nivel.addActor(tipoActor); // SelectBox de tipo de actor.
		nivel.addActor(numeroNivel); // SelectBox de número de nivel.
		nivel.addActor(actualizarActores);
		nivel.addActor(derecha); // Botón mover cámara derecha.
		nivel.addActor(izquierda); // Botón mover cámara izquierda.
	}

	/**
	 * Elimina todos los elementos de la UI del editor del escenario (Stage).
	 * También restablece las banderas de estado del editor a sus valores predeterminados.
	 */
	public void eliminarUI() {
		// Restablece el color del botón "Agregar Actores" a inactivo.
		agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		// Remueve todos los actores de UI del escenario.
		borrarActores.remove();
		moverEsenario.remove();
		moverEsenarioEnY.remove();
		agreagarActores.remove();
		zoomCamara.remove();
		zoomCamaraMas.remove();
		zoomCamaraMenos.remove();
		tipoActor.remove();
		numeroNivel.remove();
		mundo.remove();
		fondoScroll.remove();
		fondoParallax.remove();
		derecha.remove();
		izquierda.remove();
		actualizarActores.remove();

		// Restablece las banderas de estado del editor.
		agregar = false;    // Desactiva modo agregar actor.
		actualizar = false; // Desactiva modo actualizar/borrar actor.
	}

	/**
	 * Verifica si el editor está en estado "terminado" o inactivo.
	 * @return {@code true} si el editor está terminado/inactivo, {@code false} en caso contrario.
	 */
	public boolean isTerminar() {
		return terminar;
	}

	/**
	 * Establece el estado "terminado" o inactivo del editor.
	 * @param terminar {@code true} para marcar como terminado/inactivo, {@code false} para activo.
	 */
	public void setTerminar(boolean terminar) {
		this.terminar = terminar;
	}

	// Los siguientes métodos de manejo de entrada del ratón (ratonDeslizando, ratonMoviendo, etc.)
	// parecen ser stubs o no implementados completamente, ya que solo retornan true.
	// Se comentarán como tal si su funcionalidad no es evidente.

	/**
	 * Manejador para el evento de deslizar el ratón (arrastrar con botón presionado).
	 * (Actualmente parece un stub, no implementa lógica específica aquí).
	 * @param ev Evento de entrada.
	 * @return {@code true} si el evento fue manejado.
	 */
	public boolean ratonDeslizando(InputEvent ev) {
		return true; // Implementación actual es un stub.
	}

	/**
	 * Manejador para el evento de mover el ratón (sin botones presionados).
	 * (Actualmente parece un stub, no implementa lógica específica aquí).
	 * @param ev Evento de entrada.
	 * @param x Coordenada X del ratón.
	 * @param y Coordenada Y del ratón.
	 * @return {@code true} si el evento fue manejado.
	 */
	public boolean ratonMoviendo(InputEvent ev, float x, float y) {
		return true; // Implementación actual es un stub.
	}

	/**
	 * Manejador para el evento de clic del ratón.
	 * (Actualmente parece un stub, no implementa lógica específica aquí).
	 * @param ev Evento de entrada.
	 * @return {@code true} si el evento fue manejado.
	 */
	public boolean ratonClick(InputEvent ev) {
		return true; // Implementación actual es un stub.
	}

	/**
	 * Manejador para el evento de presionar un botón del ratón.
	 * (Actualmente parece un stub, no implementa lógica específica aquí).
	 * @param ev Evento de entrada.
	 * @return {@code true} si el evento fue manejado.
	 */
	public boolean ratonPresionado(InputEvent ev) {
		return true; // Implementación actual es un stub.
	}

	/**
	 * Manejador para el evento de soltar un botón del ratón.
	 * (Actualmente parece un stub, no implementa lógica específica aquí).
	 * @param ev Evento de entrada.
	 * @return {@code true} si el evento fue manejado.
	 */
	public boolean ratonLevantado(InputEvent ev) {
		return true; // Implementación actual es un stub.
	}

	/**
	 * Maneja el evento de presionar una tecla.
	 * Utilizado para controlar el movimiento de la cámara en el editor con las teclas de flecha
	 * o las teclas de volumen (en algunos contextos).
	 *
	 * @param ev Evento de entrada.
	 * @param codigoTecla Código de la tecla presionada.
	 * @return {@code true} si el evento fue manejado.
	 */
	public boolean teclaPresionada(InputEvent ev, int codigoTecla) {
		// Activa las banderas de movimiento de cámara según la tecla presionada.
		switch (codigoTecla) {
		case Keys.RIGHT: // Flecha derecha
			pD = true; // Mover a la derecha.
			break;
		case Keys.LEFT: // Flecha izquierda
			pI = true; // Mover a la izquierda.
			break;
		case Keys.VOLUME_UP: // Tecla de subir volumen (puede usarse como alternativa).
			pD = true;
			break;
		case Keys.VOLUME_DOWN: // Tecla de bajar volumen (puede usarse como alternativa).
			pI = true;
			break;
		}
		return true; // Evento manejado.
	}

	/**
	 * Maneja el evento de soltar una tecla.
	 * Desactiva las banderas de movimiento de la cámara y actualiza la posición del fondo
	 * para mantener la coherencia visual con el movimiento de la cámara.
	 *
	 * @param ev Evento de entrada.
	 * @param codigoTecla Código de la tecla soltada.
	 * @return {@code true} si el evento fue manejado.
	 */
	public boolean teclaLevantada(InputEvent ev, int codigoTecla) {
		// Desactiva las banderas de movimiento y actualiza la posición del fondo.
		switch (codigoTecla) {
		case Keys.RIGHT:
			// Actualiza la posición del fondo después de mover la cámara.
			for (int i = 0; i < personajes.size; i++) {
				if ((personajes.get(i) instanceof Fondo)) {
					personajes.get(i)
							.setPosition((camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);
				}
			}
			pD = false; // Desactiva mover a la derecha.
			break;
		case Keys.LEFT:
			for (int i = 0; i < personajes.size; i++) {
				if ((personajes.get(i) instanceof Fondo)) {
					personajes.get(i)
							.setPosition((camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);
				}
			}
			pI = false; // Desactiva mover a la izquierda.
			break;
		case Keys.VOLUME_UP: // Similar para teclas de volumen.
			for (int i = 0; i < personajes.size; i++) {
				if ((personajes.get(i) instanceof Fondo)) {
					personajes.get(i)
							.setPosition((camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);
				}
			}
			pD = false;
			break;
		case Keys.VOLUME_DOWN: // Similar para teclas de volumen.
			for (int i = 0; i < personajes.size; i++) {
				if ((personajes.get(i) instanceof Fondo)) {
					personajes.get(i)
							.setPosition((camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);
				}
			}
			pI = false;
			break;
		}
		return true; // Evento manejado.
	}

	/**
	 * Manejador para el evento de teclear un carácter.
	 * (Actualmente parece un stub, no implementa lógica específica aquí).
	 * @param ev Evento de entrada.
	 * @param caracter Carácter tecleado.
	 * @return {@code true} si el evento fue manejado.
	 */
	public boolean teclaTipo(InputEvent ev, char caracter) {
		return true; // Implementación actual es un stub.
	}

	/**
	 * Maneja el evento de deslizar el dedo sobre la pantalla (entrada táctil).
	 * Controla el paneo de la cámara o el movimiento del actor temporal a agregar.
	 *
	 * @param ev Evento de entrada.
	 * @param x Coordenada X del toque.
	 * @param y Coordenada Y del toque.
	 * @param puntero Índice del puntero (para multitáctil).
	 */
	public void toqueDeslizando(InputEvent ev, float x, float y, int puntero) {
		// Solo procesa si el editor no está en estado "terminado".
		if (!terminar) {
			float y1 = Juego.ALTO_PANTALLA - y; // Invierte la coordenada Y para el sistema de coordenadas de LibGDX.

			// Si está en modo agregar actor, mueve el actor temporal con el cursor/dedo.
			if (agregar) {
				// El último personaje en la lista 'personajes' es el actor temporal.
				// Su posición se actualiza relativa a la cámara.
				personajes.get(personajes.size - 1).setPosition(
						x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
						y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));
			}

			// Si NO está en modo agregar actor, maneja el paneo de la cámara.
			if (!agregar) {
				// Si el movimiento no está restringido solo al eje Y (o sea, movimiento en X o XY).
				if (!moverEnY) { // Movimiento solo en X (o basado en X).
					// Convierte la coordenada X del toque a coordenadas del mundo.
					camara.unproject(corriendo.set(x, 0, 0));
					// Si hay una posición de toque anterior válida.
					if (!(detras.x == -1 && detras.y == -1 && detras.z == -1)) {
						// Actualiza la posición del fondo para el efecto parallax/scroll.
						for (int i = 0; i < personajes.size; i++) {
							if ((personajes.get(i) instanceof Fondo)) {
								personajes.get(i).setPosition(
										(camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);
							}
						}
						// Calcula el delta (cambio) desde la posición anterior del toque.
						camara.unproject(delta.set(detras.x, 0, 0));
						delta.sub(corriendo); // delta = anterior - actual
						camara.position.add(delta.x, 0, 0); // Mueve la cámara por el delta en X.
					}
					detras.set(x, 0, 0); // Guarda la posición actual del toque como la anterior para el próximo evento.
				} else { // Movimiento en X e Y.
					// Actualiza la posición del fondo.
					for (int i = 0; i < personajes.size; i++) {
						if ((personajes.get(i) instanceof Fondo)) {
							personajes.get(i).setPosition(
									(camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);
						}
					}
					// Convierte las coordenadas X e Y del toque a coordenadas del mundo.
					camara.unproject(corriendo.set(x, y1, 0));
					if (!(detras.x == -1 && detras.y == -1 && detras.z == -1)) {
						// Calcula el delta desde la posición anterior.
						camara.unproject(delta.set(detras.x, detras.y, 0));
						delta.sub(corriendo);
						camara.position.add(delta.x, delta.y, 0); // Mueve la cámara por el delta en X e Y.
					}
					detras.set(x, y1, 0); // Guarda la posición actual del toque.
				}
			}
		}
	}

	/**
	 * Maneja el evento de levantar el dedo de la pantalla (entrada táctil).
	 * Finaliza el paneo de la cámara o agrega el actor seleccionado si corresponde.
	 *
	 * @param ev Evento de entrada.
	 * @param x Coordenada X del toque.
	 * @param y Coordenada Y del toque.
	 * @param puntero Índice del puntero.
	 * @param boton Botón presionado (relevante para ratón).
	 * @return {@code true} si el evento fue manejado.
	 */
	public boolean toqueLevantado(InputEvent ev, float x, float y, int puntero, int boton) {
		// Si el editor está activo.
		if (!terminar) {
			// Actualiza la posición del fondo.
			for (int i = 0; i < personajes.size; i++) {
				if ((personajes.get(i) instanceof Fondo)) {
					personajes.get(i)
							.setPosition((camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);
				}
			}
			// Resetea el vector 'detras' para indicar que el paneo ha terminado.
			detras.set(-1, -1, -1);
		}

		// Si está en modo agregar actor.
		if (agregar) {
			// Si la bandera 'toque' es true (evita agregar múltiples actores con un solo toque largo o gestos complejos).
			if (toque) {
				agregarActor(x, y); // Llama al método para agregar el actor permanentemente.
			}
			toque = true; // Restablece la bandera 'toque' para el próximo toque inicial.
		} else {
			toque = false; // Si no está en modo agregar, la bandera 'toque' se mantiene en false.
		}
		return true; // Evento manejado.
	}

	/**
	 * Maneja el evento de presionar la pantalla (inicio de un toque).
	 * Borra actores si está en modo actualizar/borrar, o inicia la adición de un actor temporal.
	 *
	 * @param ev Evento de entrada.
	 * @param x Coordenada X del toque.
	 * @param y Coordenada Y del toque.
	 * @param puntero Índice del puntero.
	 * @param boton Botón presionado.
	 * @return {@code true} si el evento fue manejado.
	 */
	public boolean toquePresionado(InputEvent ev, float x, float y, int puntero, int boton) {
		// Si el editor está activo.
		if (!terminar) {
			// Si está en modo "actualizar" (que aquí parece funcionar como modo "borrar").
			if (actualizar) {
				float ancho = 0, alto = 0; // Variables para el tamaño del actor a borrar (no se usan bien aquí).
				String numeroNvel = "Nivel " + datosNiveles.getNumeroNivel();

				// Itera sobre los personajes en la pantalla para ver si el cursor colisiona con alguno.
				for (int i = 0; i < personajes.size; i++) {
					// Comprueba la colisión del cursor del editor con el rectángulo del personaje.
					if (cursor.getBoundingRectangle().overlaps(personajes.get(i).getBoundingRectangle())) {
						// No borra Fondos, Jugador, ni Terrenos.
						if (!(personajes.get(i) instanceof Fondo) && !(personajes.get(i) instanceof Jugador)
								&& !(personajes.get(i) instanceof Terreno)) {
							// Solo borra si el tipo de actor coincide con el seleccionado en 'tipoActor'.
							if (tipo.contentEquals(personajes.get(i).getClass().getName().toString())) {
								// Estas variables no se usan efectivamente para la lógica de borrado aquí.
								ancho = personajes.get(i).getWidth();
								alto = personajes.get(i).getHeight();
								personajes.removeIndex(i); // Remueve el actor de la lista de la pantalla.
							}
						}
					}
				}

				// Itera sobre los datos de actores guardados para el nivel y borra si hay colisión.
				// Esta parte es un poco extraña porque 'ancho' y 'alto' podrían no estar correctamente definidos
				// si el bucle anterior no encontró una coincidencia o si se borraron múltiples tipos.
				for (int i = 0; i < datosNiveles.getTamanoArray(numeroNvel).size; i++) {
					// Crea un rectángulo temporal basado en la posición guardada y el tamaño (posiblemente incorrecto).
					Rectangle r = new Rectangle(datosNiveles.getTamanoArray(numeroNvel).get(i).x,
							datosNiveles.getTamanoArray(numeroNvel).get(i).y, ancho, alto);
					if (cursor.getBoundingRectangle().overlaps(r)) {
						// Elimina el actor de los datos guardados.
						datosNiveles.eliminarActor(numeroNvel, tipo, i);
					}
				}
			}

			// Si está en modo agregar actor.
			if (agregar) {
				agregarActorTemporal(x, y); // Crea una vista previa visual del actor.
				// Posiciona el actor temporal en el lugar del toque, ajustado por la cámara.
				personajes.get(personajes.size - 1).setPosition(
						x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
						y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));
			}
		}
		return true; // Evento manejado.
	}

	/**
	 * Configura los manejadores de eventos para todos los elementos interactivos de la UI del editor.
	 */
	private void eventos() {
		// Listener para el botón "Actualizar Actores" (realmente funciona como activar modo borrar/seleccionar).
		actualizarActores.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Si se estaba agregando un actor, se cancela la adición del actor temporal.
				if (agregar) {
					personajes.removeIndex(personajes.size - 1);
				}
				agregar = false; // Desactiva modo agregar.
				actualizar = true; // Activa modo actualizar/borrar.
				// Cambia colores de botones para dar feedback visual del modo activo.
				actualizarActores.setColor(1, 0, 0, 1); // Botón actual rojo.
				agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F); // Botón agregar gris.
			}
		});

		// Listener para el botón "Borrar Actores" (borra todos los actores del nivel).
		borrarActores.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				String numeroNvel = "Nivel " + datosNiveles.getNumeroNivel();
				// Elimina todos los actores de los datos guardados para el nivel actual.
				datosNiveles.eliminarActores(numeroNvel);
				// Guarda los cambios en la configuración y datos de niveles.
				configuracion.escribirDatos(dato);
				informacionNiveles.escribirDatos(datosNiveles);

				// Si se estaba agregando un actor, se cancela.
				if (agregar) {
					personajes.removeIndex(personajes.size - 1);
				}
				agregar = false;
				agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F); // Botón agregar gris.

				// Elimina todos los personajes de la pantalla (excepto Fondo, Jugador, Terreno).
				for (int i = 0; i < personajes.size; i++) {
					if (!(personajes.get(i) instanceof Fondo) && !(personajes.get(i) instanceof Jugador)
							&& !(personajes.get(i) instanceof Terreno)) {
						personajes.removeIndex(i);
					}
				}
				// Desactiva el modo actualizar/borrar.
				actualizar = false;
				actualizarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);
			}
		});

		// Listener para la caja de selección del número de nivel.
		numeroNivel.addListener(new ChangeListener() {
			@SuppressWarnings("unchecked") // Para el casteo de SelectBox.
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// Actualiza el número de nivel en los datos según la selección.
				datosNiveles.setNumeroNivel(((SelectBox<String>) actor).getSelectedIndex() + 1);
				// Indica al jugador que el nivel ha terminado para forzar una recarga del nuevo nivel.
				for (int i = 0; i < personajes.size; i++) {
					if ((personajes.get(i) instanceof Jugador)) {
						Jugador j = (Jugador) personajes.get(i);
						j.setTerminarNivel(true);
					}
				}
			}
		});

		// Listener para la caja de selección del tipo de actor (cuando se hace clic).
		tipoActor.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Si se estaba agregando un actor, se cancela.
				if (agregar) {
					personajes.removeIndex(personajes.size - 1);
				}
				agregar = false;
				agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F); // Botón agregar gris.
				super.clicked(event, x, y);
			}
		});

		// Listener para el botón de mover cámara a la derecha.
		derecha.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (agregar) { // Cancela modo agregar si estaba activo.
					personajes.removeIndex(personajes.size - 1);
				}
				agregar = false;
				agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);
				super.clicked(event, x, y);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				pD = true; // Activa bandera para mover cámara a la derecha.
				return super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				// Al soltar, actualiza la posición del fondo.
				for (int i = 0; i < personajes.size; i++) {
					if ((personajes.get(i) instanceof Fondo)) {
						personajes.get(i).setPosition(
								(camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);
					}
				}
				pD = false; // Desactiva bandera.
				super.touchUp(event, x, y, pointer, button);
			}
		});

		// Listener para el botón de mover cámara a la izquierda (similar al de la derecha).
		izquierda.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);
				if (agregar) {
					personajes.removeIndex(personajes.size - 1);
				}
				agregar = false;
				super.clicked(event, x, y);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				pI = true; // Activa bandera para mover cámara a la izquierda.
				return super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				for (int i = 0; i < personajes.size; i++) {
					if ((personajes.get(i) instanceof Fondo)) {
						personajes.get(i).setPosition(
								(camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);
					}
				}
				pI = false; // Desactiva bandera.
				super.touchUp(event, x, y, pointer, button);
			}
		});

		// Listener para la caja de selección de número de nivel (cuando se hace clic).
		numeroNivel.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (agregar) { // Cancela modo agregar.
					personajes.removeIndex(personajes.size - 1);
				}
				agregar = false;
				agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);
				// Activa modo mover escenario en X por defecto.
				moverEsenario.setColor(1, 0, 0, 1);
				moverEsenarioEnY.setColor(1.0F, 1.0F, 1.0F, 0.7F);
				moverEnY = false;
				super.clicked(event, x, y);
			}
		});

		// Listener para la caja de selección de tipo de actor (cuando cambia la selección).
		tipoActor.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// Actualiza la variable 'tipo' con el nombre completo de la clase del actor seleccionado.
				@SuppressWarnings("unchecked")
				String nombre = ((SelectBox<String>) actor).getItems()
						.get(((SelectBox<String>) actor).getSelectedIndex());
				tipo = "com.diamon.personajes." + nombre;
			}
		});

		// Listener para el botón "Mover Escenario" (movimiento en X).
		moverEsenario.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (agregar) { // Cancela modo agregar.
					personajes.removeIndex(personajes.size - 1);
				}
				agregar = false;
				moverEnY = false; // Desactiva movimiento en Y.
				// Actualiza colores de botones para feedback visual.
				moverEsenario.setColor(1, 0, 0, 1);
				moverEsenarioEnY.setColor(1.0F, 1.0F, 1.0F, 0.7F);
				agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);
				super.clicked(event, x, y);
			}
		});

		// Listener para el botón "Mover Escenario Y" (movimiento en XY).
		moverEsenarioEnY.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (agregar) { // Cancela modo agregar.
					personajes.removeIndex(personajes.size - 1);
				}
				agregar = false;
				moverEnY = true; // Activa movimiento en Y (y X).
				// Actualiza colores de botones.
				moverEsenario.setColor(1.0F, 1.0F, 1.0F, 0.7F);
				moverEsenarioEnY.setColor(1, 0, 0, 1);
				agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);
				super.clicked(event, x, y);
			}
		});

		// Listener para el botón "Agregar Actores".
		agreagarActores.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				agregar = true; // Activa modo agregar.
				// Actualiza colores de botones.
				moverEsenario.setColor(1.0F, 1.0F, 1.0F, 0.7F);
				moverEsenarioEnY.setColor(1.0F, 1.0F, 1.0F, 0.7F);
				agreagarActores.setColor(1, 0, 0, 1);
				actualizar = false; // Desactiva modo actualizar/borrar.
				actualizarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);
				super.clicked(event, x, y);
			}
		});

		// Listener para el botón de aumentar zoom.
		zoomCamaraMas.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!terminar) { // Solo si el editor está activo.
					camara.zoom -= 0.1f; // Reduce el valor de zoom (acerca la vista).
					if (agregar) { // Cancela modo agregar.
						personajes.removeIndex(personajes.size - 1);
					}
					agregar = false;
					agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);
				}
				super.clicked(event, x, y);
			}
		});

		// Listener para el botón de disminuir zoom.
		zoomCamaraMenos.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!terminar) { // Solo si el editor está activo.
					camara.zoom += 0.1f; // Aumenta el valor de zoom (aleja la vista).
					if (agregar) { // Cancela modo agregar.
						personajes.removeIndex(personajes.size - 1);
					}
					agregar = false;
					agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);
				}
				super.clicked(event, x, y);
			}
		});

		// Listener para el botón "Fondo Scroll".
		fondoScroll.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!terminar) { // Solo si el editor está activo.
					if (agregar) { // Cancela modo agregar.
						personajes.removeIndex(personajes.size - 1);
					}
					agregar = false;
					agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);
					// Activa fondo scroll y desactiva parallax.
					dato.setFondoScroll(true);
					dato.setFondoParallax(false);
					// Actualiza colores de botones.
					fondoParallax.setColor(1.0F, 1.0F, 1.0F, 0.7F);
					fondoScroll.setColor(1, 0, 0, 1);
				}
				super.clicked(event, x, y);
			}
		});

		// Listener para el botón "Fondo Parallax".
		fondoParallax.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!terminar) { // Solo si el editor está activo.
					// Actualiza colores de botones.
					fondoScroll.setColor(1.0F, 1.0F, 1.0F, 0.7F);
					fondoParallax.setColor(1, 0, 0, 1);
					agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);
					// Desactiva fondo scroll y activa parallax.
					dato.setFondoScroll(false);
					dato.setFondoParallax(true);
					if (agregar) { // Cancela modo agregar.
						personajes.removeIndex(personajes.size - 1);
					}
					agregar = false;
				}
				super.clicked(event, x, y);
			}
		});
	}

	/**
	 * Crea una instancia temporal del actor seleccionado para mostrar una vista previa visual
	 * mientras se arrastra en el editor antes de colocarlo permanentemente.
	 * El actor temporal se añade a la lista de {@link #personajes} y generalmente es el último elemento.
	 *
	 * @param x Coordenada X (en pantalla) donde se inició el toque para agregar.
	 * @param y Coordenada Y (en pantalla) donde se inició el toque para agregar.
	 */
	private void agregarActorTemporal(float x, float y) {
		// Determina el tipo de actor a crear basado en la cadena 'tipo' y lo instancia.
		// La posición se ajusta según la cámara para que aparezca en el lugar correcto del mundo.
		if (tipo.equals(DatosNiveles.PULPO)) {
			Pulpo actor = new Pulpo(recurso.get("texturas/pulpo.atlas", TextureAtlas.class).getRegions().get(0),
					pantalla, 32, 64, Pulpo.ESTATICO); // Usa el primer frame para la vista previa.
			// Calcula la posición en el mundo.
			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));
			personajes.add(actor); // Añade el actor temporal a la lista.
		}

		if (tipo.equals(DatosNiveles.PEZ_GLOBO_AMARILLO)) {
			PezGloboAmarillo actor = new PezGloboAmarillo(
					recurso.get("texturas/pezG.atlas", TextureAtlas.class).getRegions().get(0), pantalla, 64, 32,
					PezGloboAmarillo.ESTATICO);
			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));
			personajes.add(actor);
		}

		if (tipo.equals(DatosNiveles.PEZ_GOBO_NARANJA)) {
			PezGloboNaranja actor = new PezGloboNaranja(
					recurso.get("texturas/pezGlobo.atlas", TextureAtlas.class).getRegions().get(0), pantalla, 64, 64,
					PezGloboNaranja.ESTATICO);
			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));
			personajes.add(actor);
		}

		if (tipo.equals(DatosNiveles.PEZ_ANGEL)) {
			PezAngel actor = new PezAngel(recurso.get("texturas/pez1.atlas", TextureAtlas.class).getRegions().get(0),
					pantalla, 64, 32, PezAngel.ESTATICO);
			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));
			personajes.add(actor);
		}

		if (tipo.equals(DatosNiveles.BOMBA)) {
			Bomba actor = new Bomba(recurso.get("texturas/bomba.png", Texture.class), pantalla, 64, 64,
					Bomba.DIANAMICO); // Las bombas son dinámicas.
			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));
			personajes.add(actor);
		}

		if (tipo.equals(DatosNiveles.ALGAS)) {
			Algas actor = new Algas(recurso.get("texturas/algas.png", Texture.class), pantalla, 96, 64, Algas.ESTATICO);
			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));
			personajes.add(actor);
		}
	}

	/**
	 * Crea una instancia permanente del actor seleccionado en las coordenadas dadas (del mundo)
	 * y luego llama al método {@link #agregarActor(Array)} para guardar sus datos.
	 *
	 * @param x Coordenada X (en pantalla) donde se soltó el toque para agregar el actor.
	 * @param y Coordenada Y (en pantalla) donde se soltó el toque para agregar el actor.
	 */
	private void agregarActor(float x, float y) {
		Array<Personaje> actores = new Array<Personaje>(); // Crea un nuevo arreglo para el actor a guardar.

		// Determina el tipo de actor, lo instancia y lo añade al arreglo 'actores'.
		// La lógica es similar a agregarActorTemporal, pero estos son los actores finales.
		if (tipo.equals(DatosNiveles.PULPO)) {
			Pulpo actor = new Pulpo(recurso.get("texturas/pulpo.atlas", TextureAtlas.class).getRegions().get(0),
					pantalla, 32, 64, Pulpo.ESTATICO);
			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));
			actores.add(actor);
		}

		if (tipo.equals(DatosNiveles.PEZ_GLOBO_AMARILLO)) {
			PezGloboAmarillo actor = new PezGloboAmarillo(
					recurso.get("texturas/pezG.atlas", TextureAtlas.class).getRegions().get(0), pantalla, 64, 32,
					PezGloboAmarillo.ESTATICO);
			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));
			actores.add(actor);
		}

		if (tipo.equals(DatosNiveles.PEZ_GOBO_NARANJA)) {
			PezGloboNaranja actor = new PezGloboNaranja(
					recurso.get("texturas/pezGlobo.atlas", TextureAtlas.class).getRegions().get(0), pantalla, 64, 64,
					PezGloboNaranja.ESTATICO);
			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));
			actores.add(actor);
		}

		if (tipo.equals(DatosNiveles.PEZ_ANGEL)) {
			PezAngel actor = new PezAngel(recurso.get("texturas/pez1.atlas", TextureAtlas.class).getRegions().get(0),
					pantalla, 64, 32, PezAngel.ESTATICO);
			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));
			actores.add(actor);
		}

		if (tipo.equals(DatosNiveles.BOMBA)) {
			Bomba actor = new Bomba(recurso.get("texturas/bomba.png", Texture.class), pantalla, 64, 64,
					Bomba.DIANAMICO);
			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));
			actores.add(actor);
		}

		if (tipo.equals(DatosNiveles.ALGAS)) {
			Algas actor = new Algas(recurso.get("texturas/algas.png", Texture.class), pantalla, 96, 64, Algas.ESTATICO);
			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));
			actores.add(actor);
		}

		// Llama al método para guardar el actor (o actores) en los datos del nivel.
		agregarActor(actores);
	}

	/**
	 * Actualiza la lógica del editor, principalmente el movimiento de la cámara
	 * cuando se usan las teclas/botones de desplazamiento.
	 */
	public void actualizar() {
		// Si la bandera 'pD' (mover derecha) está activa.
		if (pD) {
			// Actualiza la posición del fondo para el efecto parallax/scroll.
			for (int i = 0; i < personajes.size; i++) {
				if ((personajes.get(i) instanceof Fondo)) {
					personajes.get(i)
							.setPosition((camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);
				}
			}
			// Mueve la cámara hacia la derecha.
			camara.position.x += velocidadCamara / Juego.UNIDAD_DEL_MUNDO;
		}

		// Si la bandera 'pI' (mover izquierda) está activa.
		if (pI) {
			// Actualiza la posición del fondo.
			for (int i = 0; i < personajes.size; i++) {
				if ((personajes.get(i) instanceof Fondo)) {
					personajes.get(i)
							.setPosition((camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);
				}
			}
			// Mueve la cámara hacia la izquierda.
			camara.position.x -= velocidadCamara / Juego.UNIDAD_DEL_MUNDO;
		}
	}

	/**
	 * Guarda la información del actor o actores especificados en los datos del nivel.
	 * Utiliza el tipo de actor y el número de nivel actual para almacenar las posiciones.
	 *
	 * @param actores Un arreglo de {@link Personaje}s a guardar. Aunque el nombre es plural,
	 *                en el uso actual dentro de {@link #agregarActor(float, float)},
	 *                este arreglo usualmente contiene un solo actor.
	 */
	public void agregarActor(Array<Personaje> actores) {
		// Construye la cadena identificadora del nivel (ej. "Nivel 1").
		String nivel = "Nivel " + datosNiveles.getNumeroNivel();
		// Llama al método en datosNiveles para guardar los actores.
		datosNiveles.gurdarActores(actores, tipo, nivel);
	}
}
