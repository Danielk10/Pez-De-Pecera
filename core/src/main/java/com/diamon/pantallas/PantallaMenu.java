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
import com.badlogic.gdx.scenes.scene2d.ui.Value;
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

        // Configuración del cursor para escritorio
        if (Gdx.app.getType() == Gdx.app.getType().Desktop) {
            Gdx.graphics.setCursor(
                    Gdx.graphics.newCursor(
                            new Pixmap(Gdx.files.internal("texturas/cursor.png")), 0, 0));
        }

        // Carga y reproducción de la música del menú
        musica = recurso.get("audios/creditos.ogg", Music.class);
        if (dato.isSonido()) {
            if (!musica.isPlaying()) {
                musica.setLooping(true);
                musica.play();
            }
        }

        // Crear botones e imagen de título
        Skin skin = recurso.get("uis/general/uiskin.json", Skin.class);
        titulo = new Image(recurso.get("texturas/titulo.png", Texture.class));
        jugar = new TextButton("Jugar", skin);
        opciones = new TextButton("Opciones", skin);
        puntuaciones = new TextButton("Puntuaciones", skin);
        creditos = new TextButton("Creditos", skin);
        salir = new TextButton("Salir", skin);

        // Crear una tabla para organizar los elementos de la UI
        Table tabla = new Table();
        tabla.setFillParent(true); // Hacer que la tabla ocupe todo el escenario

        // Añadir el título a la tabla
        // .padBottom(...) añade un espacio debajo del título
        // Value.percentHeight(0.1f, tabla) hace que el padding sea el 10% de la altura de la tabla
        tabla.add(titulo).padBottom(Value.percentHeight(0.1f, tabla)).row(); // .row() finaliza la fila actual y comienza una nueva

        // Añadir botones del menú principal a la tabla
        // .width(...) establece el ancho del botón al 30% del ancho de la tabla
        // .pad(...) añade un padding alrededor del botón (2% de la altura de la tabla)
        tabla.add(jugar).width(Value.percentWidth(0.3f, tabla)).pad(Value.percentHeight(0.02f, tabla)).row();
        tabla.add(opciones).width(Value.percentWidth(0.3f, tabla)).pad(Value.percentHeight(0.02f, tabla)).row();
        tabla.add(puntuaciones).width(Value.percentWidth(0.3f, tabla)).pad(Value.percentHeight(0.02f, tabla)).row();
        tabla.add(creditos).width(Value.percentWidth(0.3f, tabla)).pad(Value.percentHeight(0.02f, tabla)).row();

        // Añadir el botón de salir con un padding superior mayor para separarlo
        tabla.add(salir).width(Value.percentWidth(0.3f, tabla)).padTop(Value.percentHeight(0.05f, tabla)).row();

        // Activar el debug de la tabla (dibuja líneas alrededor de las celdas y widgets)
        // Esto es útil para desarrollo y debe comentarse o eliminarse para la versión final.
        // tabla.setDebug(true);

        // Añadir la tabla al escenario del menú
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
