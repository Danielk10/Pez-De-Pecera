package com.diamon.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;

/**
 * Pantalla de presentación (Splash Screen) con el logotipo de Diamond Black.
 * 
 * Diseñada para cargar de forma independiente e inmediata sin depender del estado
 * del AssetManager global, resolviendo caídas en dispositivos Android ocasionadas
 * por diferencias entre mayúsculas y minúsculas (case-sensitivity) o carreras de hilos.
 * 
 * Incluye:
 * - Detección defensiva del archivo de textura.
 * - Animación fluida de fundido (Fade In -> Hold -> Fade Out).
 * - Mantenimiento de la relación de aspecto original (640x480 / 4:3).
 * - Capacidad de omitir la presentación al tocar la pantalla o pulsar una tecla.
 * - Liberación inmediata de memoria de video (VRAM) al salir de la pantalla.
 */
public class PantallaPrecentacion extends Pantalla {

    private Texture texturaSplash;
    private Image logoImage;
    private boolean haAvanzado;

    public PantallaPrecentacion(Juego juego) {
        super(juego);
        this.haAvanzado = false;
    }

    @Override
    public void mostrar() {
        // Carga defensiva y segura del archivo de logo soportando ambas nomenclaturas
        FileHandle archivoLogo = Gdx.files.internal("texturas/diamondBlack.png");
        if (!archivoLogo.exists()) {
            archivoLogo = Gdx.files.internal("texturas/DiamondBlack.png");
        }

        if (archivoLogo.exists()) {
            texturaSplash = new Texture(archivoLogo);
            texturaSplash.setFilter(TextureFilter.Linear, TextureFilter.Linear);
            logoImage = new Image(texturaSplash);
            logoImage.setScaling(Scaling.fit);
        }

        Table tabla = new Table();
        tabla.setFillParent(true);
        tabla.center();

        if (logoImage != null) {
            // El logo original es de 640x480 (4:3), centrado en el viewport virtual (1280x720)
            tabla.add(logoImage).size(640, 480).center();
            nivel.addActor(tabla);

            // Iniciar totalmente transparente para el efecto Fade In suave
            logoImage.getColor().a = 0.0f;

            logoImage.addAction(
                    Actions.sequence(
                            Actions.fadeIn(0.8f),
                            Actions.delay(1.2f),
                            Actions.fadeOut(0.6f),
                            Actions.run(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            avanzarACarga();
                                        }
                                    })));
        } else {
            // Si por alguna razón extrema no se encuentra el recurso, no crashea
            avanzarACarga();
        }

        // Permitir omitir la intro al tocar la pantalla o pulsar cualquier tecla
        nivel.addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(
                            InputEvent event, float x, float y, int pointer, int button) {
                        avanzarACarga();
                        return true;
                    }

                    @Override
                    public boolean keyDown(InputEvent event, int keycode) {
                        avanzarACarga();
                        return true;
                    }
                });
    }

    private synchronized void avanzarACarga() {
        if (haAvanzado) {
            return;
        }
        haAvanzado = true;
        juego.setScreen(new PantallaCarga(juego));
    }

    @Override
    public void eventos() {}

    @Override
    public void colisiones() {}

    @Override
    public void actualizar(float delta) {
        if (!haAvanzado
                && (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY))) {
            avanzarACarga();
        }
    }

    @Override
    public void dibujar(Batch pincel, float delta) {
        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1.0f, true);
    }

    @Override
    public void guardarDatos() {}

    @Override
    public void liberarRecursos() {
        // Liberar la textura de presentación para evitar fugas de memoria VRAM en Android
        if (texturaSplash != null) {
            texturaSplash.dispose();
            texturaSplash = null;
        }
    }
}
