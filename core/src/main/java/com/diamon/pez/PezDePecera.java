package com.diamon.pez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.diamon.nucleo.Juego;
import com.diamon.pantallas.PantallaPrecentacion;
import com.diamon.pez.publicidad.Publicidad;

/**
 * Clase principal de la aplicación para el juego "Pez De Pecera".
 * Extiende la clase {@link com.diamon.nucleo.Juego} que proporciona la
 * estructura base y la gestión de recursos comunes del juego.
 */
public class PezDePecera extends Juego {

    /**
     * Constructor de la clase PezDePecera.
     *
     * @param publicidad Objeto para manejar la publicidad dentro del juego.
     *                    Se pasa al constructor de la clase base {@link Juego}.
     */
    public PezDePecera(Publicidad publicidad) {
        super(publicidad);
    }

    /**
     * Método llamado una vez cuando la aplicación es creada.
     * Se encarga de inicializar la configuración de la pantalla (pantalla completa, VSync),
     * ocultar el cursor del sistema en escritorio, cargar todos los activos del juego
     * (texturas, sonidos, música, etc.) y finalmente, establecer la pantalla de presentación
     * inicial del juego.
     */
    @SuppressWarnings("static-access") // Se mantiene para compatibilidad con el código original donde Gdx.app.getType() puede ser accedido estáticamente.
    @Override
    public void create() {

        // Llama al método create de la clase base (Juego) para inicializar recursos comunes
        // como el mundo de físicas Box2D, el AssetManager y cargar datos de configuración/niveles.
        super.create();

        // Configura el modo de pantalla completa si está activado en los datos de configuración.
        // Esto solo aplica a aplicaciones de escritorio.
        if (dato.isPantallaCompleta()) {
            if (Gdx.app.getType() == Gdx.app.getType().Desktop) {
                // Establece el modo de pantalla completa utilizando la resolución actual del monitor.
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
        }

        // Configura el modo de ventana si la pantalla completa no está activada.
        // Esto solo aplica a aplicaciones de escritorio.
        if (!dato.isPantallaCompleta()) {
            if (Gdx.app.getType() == Gdx.app.getType().Desktop) {
                // Establece el modo de ventana con las dimensiones definidas en la clase Juego.
                Gdx.graphics.setWindowedMode((int) Juego.ANCHO_PANTALLA, (int) Juego.ALTO_PANTALLA);
            }
        }

        // Habilita o deshabilita la sincronización vertical (VSync) según la configuración.
        // VSync puede ayudar a prevenir el "tearing" de la pantalla.
        if (dato.isSincronizacionVertical()) {
            Gdx.graphics.setVSync(true);
        }

        // Deshabilita VSync si así está configurado.
        if (!dato.isSincronizacionVertical()) {
            Gdx.graphics.setVSync(false);
        }

        // Oculta el cursor del sistema en aplicaciones de escritorio para una experiencia de juego más inmersiva.
        // Se reemplaza por un cursor personalizado o se oculta completamente si el juego no lo necesita.
        if (Gdx.app.getType() == Gdx.app.getType().Desktop) {
            // Crea un Pixmap transparente de 1x1 para hacer el cursor invisible.
            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            Gdx.graphics.setCursor(Gdx.graphics.newCursor(pixmap, 0, 0));
            // No olvides liberar el pixmap si ya no se necesita, aunque en este caso,
            // el cursor se mantiene durante toda la vida de la aplicación.
            // pixmap.dispose(); // Comentado ya que el cursor se usa continuamente.
        }

        // Inicio de la carga de todos los activos del juego utilizando el AssetManager (recurso).
        // Esto incluye texturas individuales, atlas de texturas, efectos de sonido, música,
        // skins para la interfaz de usuario y efectos de partículas.
        recurso.load("texturas/bomba.png", Texture.class);
        recurso.load("texturas/algas.png", Texture.class);
        recurso.load("texturas/invisible.png", Texture.class);
        recurso.load("texturas/fondo1.png", Texture.class);
        recurso.load("texturas/fondo2.png", Texture.class);
        recurso.load("texturas/fondo3.png", Texture.class);
        recurso.load("texturas/fondo4.png", Texture.class);
        recurso.load("texturas/inicio.png", Texture.class);
        recurso.load("texturas/cursor.png", Texture.class);
        recurso.load("texturas/badlogic.jpg", Texture.class);
        recurso.load("texturas/titulo.png", Texture.class);
       // recurso.load("texturas/diamondBlack.png", Texture.class); // Textura comentada en el código original.
        recurso.load("texturas/icono.png", Texture.class);
        recurso.load("texturas/menu.png", Texture.class);
        recurso.load("texturas/pausa.png", Texture.class);

        recurso.load("texturas/pez.atlas", TextureAtlas.class);
        recurso.load("texturas/pez1.atlas", TextureAtlas.class);
        recurso.load("texturas/pezG.atlas", TextureAtlas.class);
        recurso.load("texturas/pulpo.atlas", TextureAtlas.class);
        recurso.load("texturas/pezGlobo.atlas", TextureAtlas.class);
        recurso.load("texturas/iconos.atlas", TextureAtlas.class);
        recurso.load("texturas/controles.atlas", TextureAtlas.class);
        recurso.load("texturas/dedos.atlas", TextureAtlas.class);
        recurso.load("texturas/tiburon.atlas", TextureAtlas.class);

        recurso.load("audios/explosion.ogg", Sound.class);
        recurso.load("audios/musica.ogg", Music.class);
        recurso.load("audios/moustro.ogg", Music.class);
        recurso.load("audios/creditos.ogg", Music.class);

        recurso.load("uis/general/uiskin.json", Skin.class);
        recurso.load("uis/carga/neon-ui.json", Skin.class);

        recurso.load("particulas/Particle Park Flame.p", ParticleEffect.class);

        // Una vez que la configuración inicial está completa y se han puesto en cola
        // todos los activos para cargar, se establece la primera pantalla del juego,
        // que es la pantalla de presentación (PantallaPrecentacion).
        // La PantallaPrecentacion generalmente muestra un logo o animación mientras
        // el AssetManager carga los activos en segundo plano.
        setScreen(new PantallaPrecentacion(this));
    }
}
