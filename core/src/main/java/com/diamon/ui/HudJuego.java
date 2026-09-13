package com.diamon.ui;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.diamon.items.TipoPowerUp;
import com.diamon.nucleo.Constantes;
import com.diamon.personajes.Jugador;

/**
 * HUD de juego moderno y ergonómico a resolución fija de 1280x720.
 * Proporciona:
 * - Indicadores visuales de Vidas, Perlas/Puntos, Bombas, Misiles y FPS.
 * - Monitor en tiempo real de Power-Ups activos (Turbo, Escudo, Linterna Abisal).
 * - Controles táctiles virtuales en pantalla para Android con texturas del atlas de controles.
 * - Botón de pausa accesible en la esquina superior derecha.
 */
public class HudJuego {

    private final Stage stage;
    private final Table tablaPrincipal;

    private final Label textoVida;
    private final Label textoPuntos;
    private final Label textoPowerUp;
    private final Label textoFps;

    private boolean pausarSolicitado = false;

    public HudJuego(Batch batch, Skin skin, Texture texturaPausa, TextureAtlas atlasControles, final Jugador jugador, final Runnable onPausaClicked) {
        stage = new Stage(new StretchViewport(Constantes.ANCHO_VIRTUAL, Constantes.ALTO_VIRTUAL), batch);
        ((OrthographicCamera) stage.getCamera()).setToOrtho(false, Constantes.ANCHO_VIRTUAL, Constantes.ALTO_VIRTUAL);

        tablaPrincipal = new Table();
        tablaPrincipal.setFillParent(true);

        textoVida = new Label("Vida: 3/5", skin);
        textoVida.setColor(Color.CORAL);

        textoPuntos = new Label("Perlas: 0", skin);
        textoPuntos.setColor(Color.GOLD);

        textoPowerUp = new Label("", skin);
        textoPowerUp.setColor(Color.CYAN);

        textoFps = new Label("", skin);

        // Barra Superior: Vidas, Perlas, PowerUp activo y Pausa
        Table tablaSuperior = new Table();
        tablaSuperior.add(textoVida).padRight(30);
        tablaSuperior.add(textoPuntos).padRight(30);
        tablaSuperior.add(textoPowerUp).expandX().left();

        if (texturaPausa != null) {
            Image botonPausa = new Image(texturaPausa);
            botonPausa.setSize(48, 48);
            botonPausa.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    pausarSolicitado = true;
                    if (onPausaClicked != null) {
                        onPausaClicked.run();
                    }
                }
            });
            tablaSuperior.add(botonPausa).size(48, 48).right().padRight(10);
        }

        tablaPrincipal.add(tablaSuperior).fillX().pad(16).top().row();

        // Espacio intermedio
        tablaPrincipal.add().expand().row();

        // Controles Táctiles para Android (D-Pad en pantalla inferior)
        if (Gdx.app.getType() == ApplicationType.Android && atlasControles != null && jugador != null) {
            Table tablaControlesTactiles = crearControlesTactiles(atlasControles, jugador);
            tablaPrincipal.add(tablaControlesTactiles).fillX().pad(15).bottom().row();
        }

        // Barra Inferior de Información (FPS)
        Table tablaInferior = new Table();
        tablaInferior.add(textoFps).right().expandX();
        tablaPrincipal.add(tablaInferior).fillX().pad(10).bottom();

        stage.addActor(tablaPrincipal);
    }

    private Table crearControlesTactiles(TextureAtlas atlas, final Jugador jugador) {
        Table tabla = new Table();

        // Botones direccionales para nado submarino
        ImageButton btnIzq = crearBotonDireccion(atlas, "controlIzquierdo", jugador, Keys.LEFT);
        ImageButton btnDer = crearBotonDireccion(atlas, "controlDerecho", jugador, Keys.RIGHT);
        ImageButton btnArr = crearBotonDireccion(atlas, "controlArriba", jugador, Keys.UP);
        ImageButton btnAbj = crearBotonDireccion(atlas, "controlAbajo", jugador, Keys.DOWN);

        Table cruzDireccional = new Table();
        cruzDireccional.add(btnArr).size(64, 48).colspan(2).center().padBottom(4).row();
        cruzDireccional.add(btnIzq).size(64, 48).padRight(4);
        cruzDireccional.add(btnDer).size(64, 48).row();
        cruzDireccional.add(btnAbj).size(64, 48).colspan(2).center().padTop(4);

        tabla.add(cruzDireccional).left().bottom().padLeft(20).expandX();
        return tabla;
    }

    private ImageButton crearBotonDireccion(TextureAtlas atlas, String regionName, final Jugador jugador, final int keyCode) {
        TextureRegionDrawable drawable = new TextureRegionDrawable(atlas.findRegion(regionName));
        ImageButton btn = new ImageButton(drawable);
        btn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                jugador.teclaPresionada(event, keyCode);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                jugador.teclaLevantada(event, keyCode);
            }
        });
        return btn;
    }

    public void actualizar(Jugador jugador, boolean mostrarFps) {
        if (jugador == null) return;

        textoVida.setText("Vida: " + jugador.getDureza() + "/5");
        textoPuntos.setText("Perlas: " + jugador.getPuntos());

        // Actualización de estado de Power-Ups
        if (jugador.isTurboActivo()) {
            textoPowerUp.setText("TURBO: " + String.format("%.1fs", jugador.getTiempoRestantePowerUp(TipoPowerUp.TURBO)));
            textoPowerUp.setColor(Color.ORANGE);
        } else if (jugador.isEscudoActivo()) {
            textoPowerUp.setText("ESCUDO BURBUJA ACTIVO");
            textoPowerUp.setColor(Color.CYAN);
        } else if (jugador.isLinternaActiva()) {
            textoPowerUp.setText("LINTERNA: " + String.format("%.1fs", jugador.getTiempoRestantePowerUp(TipoPowerUp.LINTERNA)));
            textoPowerUp.setColor(Color.YELLOW);
        } else {
            textoPowerUp.setText("");
        }

        if (mostrarFps) {
            textoFps.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
        } else {
            textoFps.setText("");
        }
    }

    public void dibujar(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public Stage getStage() {
        return stage;
    }

    public boolean isPausarSolicitado() {
        return pausarSolicitado;
    }

    public void setPausarSolicitado(boolean pausar) {
        this.pausarSolicitado = pausar;
    }

    public void liberar() {
        stage.dispose();
    }
}
