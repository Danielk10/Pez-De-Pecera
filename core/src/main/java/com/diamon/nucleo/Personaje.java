package com.diamon.nucleo;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.diamon.datos.InformacionNiveles;
import com.diamon.datos.Configuraciones;
import com.diamon.datos.Datos;
import com.diamon.datos.DatosNiveles;

/**
 * Clase abstracta base para todas las entidades o personajes del juego.
 * Extiende {@link Sprite} de LibGDX para la representación visual y se integra
 * con Box2D para la simulación de físicas. Cada personaje tiene un cuerpo físico
 * asociado ({@link Body}) que interactúa en el mundo virtual ({@link World}).
 */
public abstract class Personaje extends Sprite {

	/**
	 * Constante para definir un tipo de cuerpo Box2D estático.
	 * Los cuerpos estáticos no se mueven bajo simulación y son ideales para el suelo, paredes, etc.
	 */
	public static final int ESTATICO = 0;

	/**
	 * Constante para definir un tipo de cuerpo Box2D cinemático.
	 * Los cuerpos cinemáticos no son afectados por fuerzas, pero pueden moverse
	 * estableciendo su velocidad. Útil para plataformas móviles o personajes controlados directamente.
	 */
	public static final int CINESTECICO = 1;

	/**
	 * Constante para definir un tipo de cuerpo Box2D dinámico.
	 * Los cuerpos dinámicos son completamente simulados, afectados por fuerzas, gravedad y colisiones.
	 * Ideal para el jugador, enemigos, objetos interactuables, etc.
	 */
	public static final int DIANAMICO = 2;

	/**
	 * Bandera que indica si el personaje debe ser eliminado del juego en el próximo ciclo de actualización.
	 */
	protected boolean remover;

	/**
	 * Animación ({@link Animation}) del personaje. Se utiliza si el personaje tiene múltiples frames.
	 */
	protected Animation<TextureRegion> animacion;

	/**
	 * Tiempo transcurrido para controlar el frame actual de la {@link #animacion}.
	 */
	private float tiempo;

	/**
	 * Bandera que indica si la {@link #animacion} debe actualizarse y mostrarse.
	 */
	private boolean animar;

	/**
	 * Referencia a la pantalla ({@link Pantalla}) a la que pertenece este personaje.
	 */
	protected Pantalla pantalla;

	/**
	 * Coordenada X de la posición del personaje en unidades del mundo Box2D.
	 * El {@link Sprite#getX()} heredado almacena la posición en unidades del mundo.
	 */
	protected float x;

	/**
	 * Coordenada Y de la posición del personaje en unidades del mundo Box2D.
	 * El {@link Sprite#getY()} heredado almacena la posición en unidades del mundo.
	 */
	protected float y;

	/**
	 * Resistencia o "salud" del personaje. Puede usarse para mecánicas de daño.
	 */
	protected int dureza;

	/**
	 * Referencia al arreglo de todos los personajes en la {@link #pantalla} actual.
	 */
	protected Array<Personaje> personajes;

	/**
	 * Referencia al administrador de activos ({@link AssetManager}) del juego.
	 */
	protected AssetManager recurso;

	/**
	 * Referencia a la cámara principal ({@link OrthographicCamera}) de la {@link #pantalla}.
	 */
	protected OrthographicCamera camara;

	/**
	 * Bandera que indica si el personaje está vivo o no.
	 */
	protected boolean vivo;

	/**
	 * Referencia a los datos de niveles ({@link DatosNiveles}) del juego.
	 */
	protected DatosNiveles datosNiveles;

	/**
	 * Referencia al manejador de información de niveles ({@link InformacionNiveles}).
	 */
	protected InformacionNiveles informacionNiveles;

	/**
	 * Referencia a los datos de configuración general ({@link Datos}) del juego.
	 */
	protected Datos dato;

	/**
	 * Referencia al manejador de configuraciones ({@link Configuraciones}) del juego.
	 */
	protected Configuraciones configuracion;

	/**
	 * Referencia al mundo virtual Box2D ({@link World}) donde reside el cuerpo físico de este personaje.
	 */
	protected World mundoVirtual;

	/**
	 * Cuerpo físico Box2D ({@link Body}) asociado a este personaje.
	 */
	protected Body cuerpo;

	/**
	 * Tipo de cuerpo Box2D ({@link #ESTATICO}, {@link #CINESTECICO}, o {@link #DIANAMICO}) de este personaje.
	 */
	protected int tipoDeCuerpo;

	/**
	 * Constructor para un personaje con una textura estática (sin animación).
	 * Inicializa el sprite, las propiedades comunes y crea el cuerpo físico Box2D.
	 *
	 * @param textura Textura a usar para el sprite del personaje.
	 * @param pantalla Pantalla a la que pertenece el personaje.
	 * @param ancho Ancho del personaje en unidades de píxeles (se convertirá a unidades del mundo).
	 * @param alto Alto del personaje en unidades de píxeles (se convertirá a unidades del mundo).
	 * @param tipoDeCuerpo Tipo de cuerpo Box2D ({@link #ESTATICO}, {@link #CINESTECICO}, {@link #DIANAMICO}).
	 */
	public Personaje(Texture textura, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {

		super(textura); // Llama al constructor de Sprite con la textura.

		// Establece el tamaño del sprite. La conversión a unidades del mundo se hace en setSize.
		setSize(ancho, alto);
		// Establece el origen del sprite en su centro, importante para rotaciones.
		setOriginCenter();

		// Inicializa propiedades comunes del personaje.
		remover = false; // No se removerá inicialmente.
		animar = false; // No hay animación para este constructor.
		vivo = true;    // El personaje comienza vivo.
		tiempo = 0;     // Tiempo de animación inicial.

		this.pantalla = pantalla; // Asigna la pantalla contenedora.

		// Inicializa las coordenadas (se establecerán correctamente con setPosition).
		x = 0;
		y = 0;

		dureza = 3; // Dureza o "vida" inicial.

		// Obtiene referencias de la pantalla contenedora.
		personajes = pantalla.personajes;
		recurso = pantalla.recurso;
		camara = pantalla.camara;
		this.mundoVirtual = pantalla.mundoVirtual;
		dato = pantalla.dato;
		configuracion = pantalla.configuracion;
		datosNiveles = pantalla.datosNiveles;
		informacionNiveles = pantalla.informacionNiveles;

		this.tipoDeCuerpo = tipoDeCuerpo; // Guarda el tipo de cuerpo.

		// --- Creación del cuerpo físico Box2D ---
		BodyDef bodyDef = new BodyDef(); // Define las propiedades del cuerpo.
		FixtureDef fixtureDef = new FixtureDef(); // Define las propiedades de la forma (fixture).
		PolygonShape shape = new PolygonShape(); // Forma poligonal para el cuerpo (un rectángulo).

		// Define la forma como una caja (rectángulo) usando el tamaño del sprite.
		// getWidth() y getHeight() ya devuelven el tamaño en unidades del mundo debido a la sobreescritura de setSize.
		shape.setAsBox(this.getWidth() / 2, this.getHeight() / 2);

		fixtureDef.shape = shape; // Asigna la forma a la fixture definition.

		// Configura el cuerpo según el tipo especificado.
		if (tipoDeCuerpo == Personaje.ESTATICO) {
			bodyDef.type = BodyDef.BodyType.StaticBody; // Cuerpo estático.
			if (mundoVirtual != null) {
				cuerpo = mundoVirtual.createBody(bodyDef); // Crea el cuerpo en el mundo virtual.
				cuerpo.setUserData(this); // Asocia este objeto Personaje con el cuerpo físico para identificación (ej. en colisiones).
				cuerpo.createFixture(fixtureDef); // Añade la forma (fixture) al cuerpo.
			}
		}

		if (tipoDeCuerpo == Personaje.CINESTECICO) {
			bodyDef.type = BodyDef.BodyType.KinematicBody; // Cuerpo cinemático.
			if (mundoVirtual != null) {
				cuerpo = mundoVirtual.createBody(bodyDef);
				cuerpo.setUserData(this);
				cuerpo.createFixture(fixtureDef);
				// Opcional: los cuerpos cinemáticos pueden tener velocidad angular para rotar.
				cuerpo.setAngularVelocity(MathUtils.degreesToRadians * 360f);
			}
		}

		if (tipoDeCuerpo == Personaje.DIANAMICO) {
			bodyDef.type = BodyDef.BodyType.DynamicBody; // Cuerpo dinámico.
			// Propiedades físicas para cuerpos dinámicos:
			fixtureDef.density = 1f;     // Densidad del cuerpo.
			fixtureDef.friction = 0.5f;  // Fricción con otros cuerpos.
			fixtureDef.restitution = 1f; // Elasticidad o "rebote".
			if (mundoVirtual != null) {
				cuerpo = mundoVirtual.createBody(bodyDef);
				cuerpo.setUserData(this);
				cuerpo.createFixture(fixtureDef);
			}
		}

		shape.dispose(); // Libera la memoria de la forma, ya no es necesaria después de crear la fixture.
	}

	/**
	 * Constructor para un personaje con una región de textura (parte de una textura más grande).
	 * Similar al constructor con {@link Texture}, pero usa {@link TextureRegion}.
	 *
	 * @param texturaRegion Región de textura a usar para el sprite.
	 * @param pantalla Pantalla a la que pertenece el personaje.
	 * @param ancho Ancho del personaje en unidades de píxeles.
	 * @param alto Alto del personaje en unidades de píxeles.
	 * @param tipoDeCuerpo Tipo de cuerpo Box2D.
	 */
	public Personaje(TextureRegion texturaRegion, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {

		super(texturaRegion); // Llama al constructor de Sprite con la TextureRegion.

		setSize(ancho, alto);
		setOriginCenter();

		remover = false;
		animar = false;
		vivo = true;
		tiempo = 0;

		this.pantalla = pantalla;
		x = 0;
		y = 0;
		dureza = 3;

		personajes = pantalla.personajes;
		recurso = pantalla.recurso;
		camara = pantalla.camara;
		this.mundoVirtual = pantalla.mundoVirtual;
		dato = pantalla.dato;
		configuracion = pantalla.configuracion;
		datosNiveles = pantalla.datosNiveles;
		informacionNiveles = pantalla.informacionNiveles;

		this.tipoDeCuerpo = tipoDeCuerpo;

		// --- Creación del cuerpo físico Box2D (idéntico al constructor anterior) ---
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();

		// getWidth()/getHeight() ya están en unidades del mundo.
		shape.setAsBox(this.getWidth() / 2, this.getHeight() / 2);
		fixtureDef.shape = shape;

		if (tipoDeCuerpo == Personaje.ESTATICO) {
			bodyDef.type = BodyDef.BodyType.StaticBody;
			if (mundoVirtual != null) {
				cuerpo = mundoVirtual.createBody(bodyDef);
				cuerpo.setUserData(this);
				cuerpo.createFixture(fixtureDef);
			}
		}

		if (tipoDeCuerpo == Personaje.CINESTECICO) {
			bodyDef.type = BodyDef.BodyType.KinematicBody;
			if (mundoVirtual != null) {
				cuerpo = mundoVirtual.createBody(bodyDef);
				cuerpo.setUserData(this);
				cuerpo.createFixture(fixtureDef);
				cuerpo.setAngularVelocity(MathUtils.degreesToRadians * 360f);
			}
		}

		if (tipoDeCuerpo == Personaje.DIANAMICO) {
			bodyDef.type = BodyDef.BodyType.DynamicBody;
			fixtureDef.density = 1f;
			fixtureDef.friction = 0.5f;
			fixtureDef.restitution = 1f;
			if (mundoVirtual != null) {
				cuerpo = mundoVirtual.createBody(bodyDef);
				cuerpo.setUserData(this);
				cuerpo.createFixture(fixtureDef);
			}
		}

		shape.dispose();
	}

	/**
	 * Constructor para un personaje animado a partir de un arreglo de {@link AtlasRegion}.
	 * Configura la animación y crea el cuerpo físico Box2D.
	 *
	 * @param texturaRegion Arreglo de {@link AtlasRegion} para los frames de la animación.
	 * @param tiempoAnimacion Duración de cada frame de la animación en segundos.
	 * @param modo Modo de reproducción de la animación (e.g., {@link Animation.PlayMode#LOOP}).
	 * @param pantalla Pantalla a la que pertenece el personaje.
	 * @param ancho Ancho del personaje en unidades de píxeles.
	 * @param alto Alto del personaje en unidades de píxeles.
	 * @param tipoDeCuerpo Tipo de cuerpo Box2D.
	 */
	public Personaje(Array<AtlasRegion> texturaRegion, float tiempoAnimacion, Animation.PlayMode modo,
			Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {

		// Establece el tamaño y origen antes de configurar la región inicial del sprite.
		setSize(ancho, alto);
		setOriginCenter();
		// Establece la región inicial del sprite al primer frame de la animación.
		setRegion(texturaRegion.get(0));

		// Crea la animación con los frames, duración y modo de reproducción.
		animacion = new Animation<TextureRegion>(tiempoAnimacion, texturaRegion);
		animacion.setPlayMode(modo);

		// Inicializa propiedades comunes.
		remover = false;
		vivo = true;
		animar = true; // Activa la animación para este constructor.
		tiempo = 0;

		this.pantalla = pantalla;
		x = 0;
		y = 0;
		dureza = 3;

		personajes = pantalla.personajes;
		recurso = pantalla.recurso;
		camara = pantalla.camara;
		this.mundoVirtual = pantalla.mundoVirtual;
		dato = pantalla.dato;
		configuracion = pantalla.configuracion;
		datosNiveles = pantalla.datosNiveles;
		informacionNiveles = pantalla.informacionNiveles;

		this.tipoDeCuerpo = tipoDeCuerpo;

		// --- Creación del cuerpo físico Box2D (idéntico a los constructores anteriores) ---
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();

		// getWidth()/getHeight() ya están en unidades del mundo.
		shape.setAsBox(this.getWidth() / 2, this.getHeight() / 2);
		fixtureDef.shape = shape;

		if (tipoDeCuerpo == Personaje.ESTATICO) {
			bodyDef.type = BodyDef.BodyType.StaticBody;
			if (mundoVirtual != null) {
				cuerpo = mundoVirtual.createBody(bodyDef);
				cuerpo.setUserData(this);
				cuerpo.createFixture(fixtureDef);
			}
		}

		if (tipoDeCuerpo == Personaje.CINESTECICO) {
			bodyDef.type = BodyDef.BodyType.KinematicBody;
			if (mundoVirtual != null) {
				cuerpo = mundoVirtual.createBody(bodyDef);
				cuerpo.setUserData(this);
				cuerpo.createFixture(fixtureDef);
				cuerpo.setAngularVelocity(MathUtils.degreesToRadians * 360f);
			}
		}

		if (tipoDeCuerpo == Personaje.DIANAMICO) {
			bodyDef.type = BodyDef.BodyType.DynamicBody;
			fixtureDef.density = 1f;
			fixtureDef.friction = 0.5f;
			fixtureDef.restitution = 1f;
			if (mundoVirtual != null) {
				cuerpo = mundoVirtual.createBody(bodyDef);
				cuerpo.setUserData(this);
				cuerpo.createFixture(fixtureDef);
			}
		}

		shape.dispose();
	}

	/**
	 * Dibuja el personaje en la pantalla. Si está animado, actualiza la región del sprite
	 * al frame correspondiente de la animación.
	 *
	 * @param pincel El {@link Batch} (usualmente {@link SpriteBatch}) para dibujar.
	 * @param delta Tiempo transcurrido desde el último frame (no se usa directamente aquí, pero es común en métodos de dibujo).
	 */
	public void dibujar(Batch pincel, float delta) {

		// Si el personaje está configurado para animar, actualiza su región de textura
		// al frame actual de la animación basado en el 'tiempo' acumulado.
		if (animar) {
			// El segundo parámetro 'false' en getKeyFrame indica que la animación no debe reiniciarse
			// si el tiempo excede la duración total (se maneja según el PlayMode).
			setRegion(animacion.getKeyFrame(tiempo, false));
		}

		// Llama al método draw de la clase Sprite para dibujar el personaje.
		draw(pincel);
	}

	/**
	 * Verifica si el personaje está marcado para ser removido.
	 * @return {@code true} si el personaje debe ser removido, {@code false} en caso contrario.
	 */
	public boolean isRemover() {
		return remover;
	}

	/**
	 * Marca el personaje para ser removido del juego.
	 * La eliminación real suele ocurrir en un bucle de actualización principal
	 * para evitar problemas de concurrencia.
	 */
	public void remover() {
		remover = true;
	}

	/**
	 * Actualiza el estado del personaje.
	 * Para cuerpos {@code CINESTECICO} y {@code DIANAMICO}, sincroniza la posición y rotación
	 * del sprite con el cuerpo físico de Box2D. También actualiza el tiempo de animación si aplica.
	 * Para cuerpos {@code ESTATICO}, no se realiza sincronización aquí ya que sus cuerpos
	 * de Box2D son inmóviles por definición (su posición inicial se establece en la creación).
	 *
	 * @param delta Tiempo transcurrido desde la última actualización, en segundos.
	 */
	public void actualizar(float delta) {

		// Actualiza el tiempo de animación si el personaje está animado.
		if (animar) {
			if (delta == 0) { // Si no ha pasado tiempo, no hay nada que actualizar.
				return;
			}
			if (delta > 0.1f) { // Limita el delta para evitar saltos grandes en la animación si hay lag.
				delta = 0.1f;
			}
			tiempo += delta; // Acumula el tiempo para la animación.
		}

		// Sincroniza el sprite con el cuerpo Box2D para cuerpos cinemáticos y dinámicos.
		if (tipoDeCuerpo == Personaje.CINESTECICO || tipoDeCuerpo == Personaje.DIANAMICO) {
			if (cuerpo != null) {
				// Obtiene la posición del centro del cuerpo Box2D.
				// Las coordenadas del cuerpo están en unidades del mundo.
				// Para obtener la esquina inferior izquierda del sprite, restamos la mitad del ancho/alto del sprite.
				x = cuerpo.getPosition().x - this.getWidth() / 2;
				y = cuerpo.getPosition().y - this.getHeight() / 2;

				// Establece la posición del sprite. setX/setY heredados de Sprite.
				// this.getWidth()/getHeight() ya están en unidades del mundo.
				setX(x);
				setY(y);
				// Establece la rotación del sprite basada en el ángulo del cuerpo Box2D.
				// El ángulo del cuerpo está en radianes, se convierte a grados.
				setRotation(cuerpo.getAngle() * MathUtils.radiansToDegrees);
			}
		}
	}

	/**
	 * Establece la posición del personaje. Las coordenadas de entrada se asumen en píxeles
	 * y se convierten a unidades del mundo Box2D. Actualiza tanto la posición del sprite
	 * como la del cuerpo físico Box2D.
	 *
	 * @param x Coordenada X en píxeles.
	 * @param y Coordenada Y en píxeles.
	 */
	@Override
	public void setPosition(float x, float y) {
		// Convierte las coordenadas de píxeles a unidades del mundo.
		float mundoX = x / Juego.UNIDAD_DEL_MUNDO;
		float mundoY = y / Juego.UNIDAD_DEL_MUNDO;

		// Llama al setPosition de la clase Sprite, que ahora espera unidades del mundo.
		super.setPosition(mundoX, mundoY);

		// Almacena las coordenadas en unidades del mundo en las variables 'x' e 'y' del personaje.
		this.x = mundoX;
		this.y = mundoY;

		// Si hay un cuerpo físico asociado, actualiza su posición.
		if (cuerpo != null) {
			// Solo actualiza si este Personaje es el UserData del cuerpo.
			if (this.equals(cuerpo.getUserData())) {
				// Establece la posición del centro del cuerpo Box2D.
				// getWidth()/getHeight() ya devuelven unidades del mundo.
				cuerpo.setTransform(this.x + this.getWidth() / 2, this.y + this.getHeight() / 2, 0);
			}
		}
	}

	/**
	 * Establece el tamaño del personaje. Las dimensiones de entrada se asumen en píxeles
	 * y se convierten a unidades del mundo Box2D para el sprite.
	 * El cuerpo Box2D se crea con este tamaño en los constructores.
	 *
	 * @param width Ancho en píxeles.
	 * @param height Alto en píxeles.
	 */
	@Override
	public void setSize(float width, float height) {
		// Convierte las dimensiones de píxeles a unidades del mundo y las establece en el Sprite.
		super.setSize(width / Juego.UNIDAD_DEL_MUNDO, height / Juego.UNIDAD_DEL_MUNDO);
	}

	/**
	 * Verifica si el personaje está vivo.
	 * @return {@code true} si el personaje está vivo, {@code false} en caso contrario.
	 */
	public boolean isVivo() {
		return vivo;
	}

	/**
	 * Establece el estado de vida del personaje.
	 * @param vivo {@code true} para marcar como vivo, {@code false} para marcar como no vivo.
	 */
	public void setVivo(boolean vivo) {
		this.vivo = vivo;
	}

	/**
	 * Obtiene la dureza (o salud) actual del personaje.
	 * @return La dureza actual.
	 */
	public int getDureza() {
		return dureza;
	}

	/**
	 * Establece la dureza (o salud) del personaje.
	 * @param dureza El nuevo valor de dureza.
	 */
	public void setDureza(int dureza) {
		this.dureza = dureza;
	}

	/**
	 * Establece directamente la bandera de remover.
	 * @param remover {@code true} para marcar para remoción, {@code false} en caso contrario.
	 */
	public void setRemover(boolean remover) {
		this.remover = remover;
	}

	/**
	 * Método abstracto que debe ser implementado por las subclases para definir
	 * el comportamiento del personaje cuando colisiona con otro {@link Personaje}.
	 *
	 * @param personaje El otro personaje con el que ha ocurrido la colisión.
	 */
	public abstract void colision(Personaje personaje);
}
