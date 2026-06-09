package com.diamon.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;

public class PantallaMenu extends Pantalla {

    private TextButton jugar;

    private TextButton opciones;

    private TextButton puntuaciones;

    private TextButton creditos;

    private TextButton salir;

    private Image titulo;

    private Music musica;

    public PantallaMenu(Juego juego) {
        super(juego);
    }

    @SuppressWarnings("static-access")
    @Override
    public void mostrar() {
        if (Gdx.app.getType() == Gdx.app.getType().Desktop) {
            Gdx.graphics.setCursor(
                    Gdx.graphics.newCursor(
                            new Pixmap(Gdx.files.internal("texturas/cursor.png")), 0, 0));
        }

        musica = recurso.get("audios/creditos.ogg", Music.class);

        if (dato.isSonido()) {
            if (!musica.isPlaying()) {
                musica.setLooping(true);
                musica.play();
            }
        }

        Skin skin = recurso.get("uis/general/uiskin.json", Skin.class);

        Table tabla = new Table();
        tabla.setFillParent(true);
        // tabla.setDebug(true); // Útil para depurar el diseño

        titulo = new Image(recurso.get("texturas/titulo.png", Texture.class));
        
        jugar = new TextButton("Jugar", skin);
        opciones = new TextButton("Opciones", skin);
        puntuaciones = new TextButton("Puntuaciones", skin);
        creditos = new TextButton("Creditos", skin);
        salir = new TextButton("Salir", skin);

        // Diseño de la UI con Table
        tabla.add(titulo).size(320, 320).padBottom(20);
        tabla.row();
        
        Table menuBotones = new Table();
        menuBotones.add(jugar).size(213, 32).padBottom(10).row();
        menuBotones.add(opciones).size(213, 32).padBottom(10).row();
        menuBotones.add(puntuaciones).size(213, 32).padBottom(10).row();
        menuBotones.add(creditos).size(213, 32).padBottom(10).row();
        
        tabla.add(menuBotones).expandX();
        tabla.row();
        tabla.add(salir).size(213, 32).left().bottom().expand().pad(32);

        nivelMenu.addActor(tabla);
    }

    @Override
    public void eventos() {

        jugar.addListener(
                new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        juego.setScreen(new PantallaSeleccion(juego));

                        super.clicked(event, x, y);
                    }
                });

        opciones.addListener(
                new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        juego.setScreen(new PantallaOpciones(juego));

                        super.clicked(event, x, y);
                    }
                });

        puntuaciones.addListener(
                new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        juego.setScreen(new PantallaPuntuaciones(juego));

                        super.clicked(event, x, y);
                    }
                });

        creditos.addListener(
                new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        juego.setScreen(new PantallaCreditos(juego));

                        super.clicked(event, x, y);
                    }
                });

        salir.addListener(
                new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        Gdx.app.exit();

                        super.clicked(event, x, y);
                    }
                });
    }

    @Override
    public void colisiones() {
        // TODO Auto-generated method stub

    }

    @Override
    public void actualizar(float delta) {}

    @Override
    public void dibujar(Batch pincel, float delta) {
        // TODO Auto-generated method stub

    }

    @Override
    public void guardarDatos() {
        // TODO Auto-generated method stub

    }

    @Override
    public void liberarRecursos() {
        // TODO Auto-generated method stub

    }
}
