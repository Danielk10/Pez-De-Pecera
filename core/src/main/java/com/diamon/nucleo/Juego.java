package com.diamon.nucleo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.diamon.datos.Configuraciones;
import com.diamon.datos.Datos;
import com.diamon.datos.DatosNiveles;
import com.diamon.datos.InformacionNiveles;
import com.diamon.pantallas.PantallaCarga;
import com.diamon.pantallas.PantallaJuego;
import com.diamon.pantallas.PantallaPrecentacion;
import com.diamon.pez.publicidad.Publicidad;

/**
 * La clase {@code Juego} es la clase central del juego. Se encarga de gestionar
 * los recursos globales como el administrador de activos ({@link AssetManager}),
 * el mundo de físicas ({@link World}), las configuraciones del juego y de los niveles.
 * También maneja las transiciones entre las diferentes pantallas ({@link Screen})
 * del juego.
 */
public abstract class Juego extends Game {

    /**
     * Administrador de activos para cargar y gestionar texturas, sonidos, etc.
     */
    protected AssetManager recurso;

    /**
     * Ancho de la pantalla en unidades del mundo.
     */
    public static final float ANCHO_PANTALLA = 1280f;

    /**
     * Alto de la pantalla en unidades del mundo.
     */
    public static final float ALTO_PANTALLA = 720f;

    /**
     * Factor de conversión entre unidades del mundo y píxeles.
     */
    public static final float UNIDAD_DEL_MUNDO = 100f;

    /**
     * Largo total de un nivel del juego.
     */
    public static final float LARGO_NIVEL = 6400.0f;

    /**
     * Vector de gravedad aplicado al mundo de físicas.
     */
    public static final float GRAVEDAD = -10.0f;

    /**
     * Velocidad a la que se mueve la cámara del juego.
     */
    public static final float VELOCIDAD_CAMARA = 1f;

    /**
     * Factor de conversión de delta time a píxeles, usado para movimientos consistentes.
     */
    public static final float DELTA_A_PIXEL = 0.0166666666666667f;

    /**
     * Cuadros por segundo (FPS) objetivo para el juego.
     */
    public static final float FPS = 60f;

    /**
     * Datos de los niveles del juego, cargados desde almacenamiento.
     */
    protected DatosNiveles datosNiveles;

    /**
     * Ayudante para leer y escribir los datos de los niveles.
     */
    protected InformacionNiveles informacionNiveles;

    /**
     * Datos de configuración general del juego.
     */
    protected Datos dato;

    /**
     * Ayudante para leer y escribir las configuraciones generales del juego.
     */
    protected Configuraciones configuracion;

    private Image[] fondo;

    private float posicionFondoX;

    /**
     * Escenario ({@link Stage}) utilizado para renderizar el menú principal y sus elementos,
     * incluyendo el fondo con efecto de scroll.
     */
    protected Stage nivelMenu;

    private boolean renderizar;

    /**
     * Objeto para manejar la publicidad dentro del juego.
     */
    protected Publicidad publicidad;

    /**
     * Mundo de físicas Box2D donde interactúan los objetos del juego.
     */
    protected World mundoVirtual;

    /**
     * Paso de tiempo fijo para la simulación de físicas, asegura consistencia.
     * (1 / 60 cuadros por segundo)
     */
    protected static final float STEP_TIME = 1f / 60f;

    /**
     * Número de iteraciones para la fase de velocidad en la simulación de físicas.
     */
    protected static final int VELOCITY_ITERATIONS = 6;

    /**
     * Número de iteraciones para la fase de posición en la simulación de físicas.
     */
    protected static final int POSITION_ITERATIONS = 2;

    private float accumulator = 0;

    /**
     * Constructor de la clase {@code Juego}.
     *
     * @param publicidad Objeto para manejar la publicidad en el juego.
     */
    public Juego(Publicidad publicidad) {
        super();

        this.publicidad = publicidad;
    }

    /**
     * Método llamado cuando se crea el juego. Aquí se inicializan los recursos
     * globales, el mundo de físicas, se cargan las configuraciones y se prepara
     * el menú principal.
     */
    @Override
    public void create() {

        // Inicializa el motor de físicas Box2D.
        Box2D.init();

        // Crea el mundo virtual con la gravedad definida.
        mundoVirtual = new World(new Vector2(0, Juego.GRAVEDAD), true);

        // Crea el administrador de activos.
        recurso = new AssetManager();

        fondo = new Image[2];

        informacionNiveles = new InformacionNiveles();

        // Intenta leer los datos de los niveles desde el almacenamiento local.
        datosNiveles = informacionNiveles.leerDatos(InformacionNiveles.LOCAL);

        // Si no se pueden leer los datos de los niveles desde el almacenamiento local (o es la primera vez),
        // se leen desde los assets internos y se guardan en el almacenamiento local para futuras sesiones.
        if (datosNiveles.isLeerDatosAsset()) {

            InformacionNiveles informacionNivelesInterna = new InformacionNiveles();

            datosNiveles = informacionNivelesInterna.leerDatos(InformacionNiveles.INTERNO);

            datosNiveles.setLeerDatosAsset(false);

            informacionNivelesInterna.escribirDatos(datosNiveles);
        }

        configuracion = new Configuraciones();

        // Intenta leer los datos de configuración general desde el almacenamiento local.
        dato = configuracion.leerDatos(Configuraciones.LOCAL);

        // Si no se pueden leer los datos de configuración desde el almacenamiento local (o es la primera vez),
        // se leen desde los assets internos y se guardan en el almacenamiento local para futuras sesiones.
        if (dato.isLeerDatosAsset()) {

            Configuraciones configuracionInterna = new Configuraciones();

            dato = configuracionInterna.leerDatos(Configuraciones.INTERNO);

            dato.setLeerDatosAsset(false);

            configuracionInterna.escribirDatos(dato);
        }

        posicionFondoX = 0;

        renderizar = false;

        // Crea el escenario para el menú principal con un viewport que estira el contenido.
        nivelMenu = new Stage(new StretchViewport(Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA));

        // Configura la cámara ortográfica del escenario del menú.
        ((OrthographicCamera) nivelMenu.getCamera())
                .setToOrtho(false, Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA);

        // Crea y configura las imágenes de fondo para el efecto de scroll en el menú.
        for (int i = 0; i < fondo.length; i++) {

            fondo[i] = new Image(new Texture(Gdx.files.internal("texturas/fondo4.png")));

            fondo[i].setSize(Juego.ANCHO_PANTALLA + 213, Juego.ALTO_PANTALLA);

            fondo[i].setPosition(0, 0);

            nivelMenu.addActor(fondo[i]);
        }

        // Establece el escenario del menú como el procesador de entrada.
        Gdx.input.setInputProcessor(nivelMenu);
    }

    /**
     * Método llamado continuamente para renderizar el juego. Actualiza la lógica
     * del mundo de físicas y renderiza la pantalla actual. También maneja el
     * scroll del fondo del menú si es necesario.
     */
    @Override
    public void render() {

        float delta = Gdx.graphics.getDeltaTime();

        // Acumula el tiempo delta para el paso de tiempo fijo de Box2D.
        accumulator += Math.min(delta, 0.25f); // Limita el delta máximo para evitar saltos grandes.

        // Si el acumulador es suficiente para un paso de tiempo, actualiza el mundo de físicas.
        if (accumulator >= STEP_TIME) {

            accumulator -= STEP_TIME;

            // Realiza un paso en la simulación del mundo de físicas.
            mundoVirtual.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }

        // Limpia la pantalla con un color azul.
        ScreenUtils.clear(0.0F, 0.0F, 1.0F, 1.0f, true);

        // Llama al método render de la pantalla actual.
        super.render();

        // Si la variable 'renderizar' es verdadera, significa que estamos en una pantalla de menú.
        if (renderizar) {

            // Mueve la posición X del fondo para crear el efecto de scroll.
            posicionFondoX -= 0.5f / Juego.DELTA_A_PIXEL * Gdx.graphics.getDeltaTime();

            // Si el fondo se ha desplazado completamente fuera de la pantalla, reinicia su posición.
            if (posicionFondoX <= -Juego.ANCHO_PANTALLA) {

                posicionFondoX = 0;
            }

            // Actualiza la posición de las dos imágenes de fondo para el scroll continuo.
            fondo[0].setPosition(posicionFondoX, 0);

            fondo[1].setPosition(posicionFondoX + Juego.ANCHO_PANTALLA, 0);

            // Dibuja y actualiza el escenario del menú.
            nivelMenu.draw();

            nivelMenu.act();
        }
    }

    /**
     * Método llamado cuando la ventana del juego cambia de tamaño. Actualiza el
     * viewport del escenario del menú.
     *
     * @param ancho El nuevo ancho de la ventana.
     * @param alto  El nuevo alto de la ventana.
     */
    @Override
    public void resize(int ancho, int alto) {

        super.resize(ancho, alto);

        // Actualiza el viewport del escenario del menú para que coincida con el nuevo tamaño.
        nivelMenu.getViewport().update(ancho, alto);
    }

    /**
     * Establece la pantalla actual del juego. Controla si se debe renderizar
     * el menú de fondo y quién maneja la entrada del usuario.
     *
     * @param screen La nueva pantalla a mostrar.
     */
    @Override
    public void setScreen(Screen screen) {

        // Si la nueva pantalla es una pantalla de carga, juego o presentación,
        // significa que no estamos en un menú principal.
        if (screen instanceof PantallaCarga
                || screen instanceof PantallaJuego
                || screen instanceof PantallaPrecentacion) {

            // Limpia el escenario del menú, ya que no se va a mostrar.
            nivelMenu.clear();

            // Desactiva la renderización del fondo del menú.
            renderizar = false;

            // Establece la nueva pantalla.
            super.setScreen(screen);

        } else { // Si la nueva pantalla es un menú (ej. PantallaMenuPrincipal, PantallaSeleccionNivel).
            // Limpia el escenario del menú para reconstruirlo.
            nivelMenu.clear();

            // Re-crea y configura las imágenes de fondo para el menú.
            for (int i = 0; i < fondo.length; i++) {

                fondo[i] = new Image(new Texture(Gdx.files.internal("texturas/fondo4.png")));

                fondo[i].setSize(Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA);

                fondo[i].setPosition(0, 0);

                nivelMenu.addActor(fondo[i]);
            }

            // Establece la nueva pantalla (que será un tipo de menú).
            super.setScreen(screen);

            // Anula el procesador de entrada actual.
            Gdx.input.setInputProcessor(null);
            // Establece el escenario del menú como el procesador de entrada,
            // para que los botones y elementos del menú respondan.
            Gdx.input.setInputProcessor(nivelMenu);

            // Activa la renderización del fondo del menú.
            renderizar = true;
        }
    }

    /**
     * Método llamado cuando el juego se reanuda después de estar pausado.
     * Restaura el procesador de entrada si es necesario.
     */
    @Override
    public void resume() {

        super.resume();

        // Si la pantalla actual no es una de carga, juego o presentación (es decir, es un menú),
        // se asegura de que el escenario del menú sea el procesador de entrada.
        if (screen instanceof PantallaCarga
                || screen instanceof PantallaJuego
                || screen instanceof PantallaPrecentacion) {

            // No hace nada especial para estas pantallas.
        } else {

            // Restablece el procesador de entrada al escenario del menú.
            Gdx.input.setInputProcessor(null);

            Gdx.input.setInputProcessor(nivelMenu);
        }
    }

    /**
     * Método llamado cuando el juego se cierra. Libera todos los recursos
     * utilizados, como el administrador de activos, el escenario del menú y
     * el mundo de físicas.
     */
    @Override
    public void dispose() {

        // Libera los recursos cargados por el AssetManager.
        recurso.dispose();

        // Limpia el procesador de entrada.
        Gdx.input.setInputProcessor(null);

        // Libera los recursos del escenario del menú.
        nivelMenu.dispose();

        // Libera los recursos del mundo de físicas Box2D.
        mundoVirtual.dispose();
    }
}
