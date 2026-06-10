package com.diamon.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;

public class PantallaOpciones extends Pantalla {

    private Table tablaPrincipal;

    private Table tablaOpciones;

    private Table tablaPartida;

    private Table tablaGraficos;

    private Table tablaSonido;

    private Table tablaControles;

    private TextButton atras;

    private Label titulo;

    private TextButton partida;

    private TextButton controles;

    private TextButton graficos;

    private TextButton sonido;

    private Label tituloPantallaCompleta;

    private Label tituloSincronizacionVertical;

    private Label tituloFiltradoBilineal;

    private Label tituloMostrarFPS;

    private Label tituloPrueba;

    private CheckBox pantallaCompleta;

    private CheckBox sincronizacionVertical;

    private CheckBox filtradoBilineal;

    private CheckBox mostrarFPS;

    private CheckBox prueba;

    private TextButton aceptarGraficos;

    private TextButton cancelarGraficos;

    private Label tituloOpcionesGraficos;

    private Label tituloOpcionesPartida;

    private TextButton aceptarPartida;

    private TextButton cancelarPartida;

    private Label tituloAutoDisparo;

    private CheckBox autoDisparo;

    private Label tituloOpcionesControles;

    private TextButton atrasControles;

    private Label tituloMusica;

    private Label tituloSonido;

    private Slider volumenMusica;

    private Slider volumenSonido;

    private CheckBox activarSonido;

    private Label tituloactivarSonido;

    private TextButton aceptarSonido;

    private TextButton cancelarSonido;

    private Label tituloOpcionesSonido;

    private Label textoArriba;

    private Label textoAbajo;

    private Label textoIzquierda;

    private Label textoDerecha;

    private Label textoDisparo;

    private Label textoDisparoMisil;

    private Label textoDisparoBomba;

    private Label textoPausaJuego;

    private Image arriba;

    private Image abajo;

    private Image izquierda;

    private Image derecha;

    private Image disparo;

    private Image disparoMisil;

    private Image disparoBomba;

    private Image pausaJuego;

    private Image clicIzquierdo;

    private Image clicDerecho;

    public PantallaOpciones(Juego juego) {
        super(juego);
        // TODO Auto-generated constructor stub
    }

    @SuppressWarnings("static-access")
    @Override
    public void mostrar() {
        Skin skin = recurso.get("uis/general/uiskin.json", Skin.class);

        tablaPrincipal = new Table();
        tablaPrincipal.setFillParent(true);

        atras = new TextButton("Atras", skin);
        titulo = new Label("Opciones", skin);
        partida = new TextButton("Partida", skin);
        controles = new TextButton("Controles", skin);
        graficos = new TextButton("Graficos", skin);
        sonido = new TextButton("Sonido", skin);

        // --- TABLA OPCIONES PRINCIPALES ---
        tablaOpciones = new Table();
        tablaOpciones.add(titulo).colspan(1).padBottom(40).row();
        if (Gdx.app.getType() == Gdx.app.getType().Desktop) {
            tablaOpciones.add(partida).size(Juego.ANCHO_PANTALLA / 3, 32).padBottom(10).row();
        }
        tablaOpciones.add(controles).size(Juego.ANCHO_PANTALLA / 3, 32).padBottom(10).row();
        tablaOpciones.add(sonido).size(Juego.ANCHO_PANTALLA / 3, 32).padBottom(10).row();
        tablaOpciones.add(graficos).size(Juego.ANCHO_PANTALLA / 3, 32).padBottom(10).row();
        tablaOpciones.add(atras).size(128, 32).expand().bottom().left().pad(32);

        // --- TABLA SONIDO ---
        tituloOpcionesSonido = new Label("Sonido", skin);
        tituloMusica = new Label("Volumen de la Musica", skin);
        volumenMusica = new Slider(0.0f, 1.0f, 0.1f, false, skin);
        tituloSonido = new Label("Volumen del Sonido", skin);
        volumenSonido = new Slider(0.0f, 1.0f, 0.1f, false, skin);
        tituloactivarSonido = new Label("Musica del Juego", skin);
        activarSonido = new CheckBox("", skin);
        aceptarSonido = new TextButton("Aceptar", skin);
        cancelarSonido = new TextButton("Cancelar", skin);

        activarSonido.setChecked(dato.isSonido());
        volumenMusica.setValue(dato.getVolumenMusica());
        volumenSonido.setValue(dato.getVolumenSonido());

        tablaSonido = new Table();
        tablaSonido.add(tituloOpcionesSonido).colspan(2).padBottom(40).row();
        tablaSonido.add(tituloMusica).padBottom(10).left();
        tablaSonido.add(volumenMusica).padBottom(10).row();
        tablaSonido.add(tituloSonido).padBottom(10).left();
        tablaSonido.add(volumenSonido).padBottom(10).row();
        tablaSonido.add(tituloactivarSonido).padBottom(10).left();
        tablaSonido.add(activarSonido).padBottom(10).left().row();

        Table botonesSonido = new Table();
        botonesSonido.add(cancelarSonido).size(160, 32).padRight(20);
        botonesSonido.add(aceptarSonido).size(160, 32);
        tablaSonido.add(botonesSonido).colspan(2).expand().bottom().pad(32);

        // --- TABLA GRAFICOS ---
        tituloOpcionesGraficos = new Label("Graficos", skin);
        tituloPantallaCompleta = new Label("Pantalla Completa", skin);
        pantallaCompleta = new CheckBox("", skin);
        tituloSincronizacionVertical = new Label("V-Sync", skin);
        sincronizacionVertical = new CheckBox("", skin);
        tituloFiltradoBilineal = new Label("Filtrado Bilineal", skin);
        filtradoBilineal = new CheckBox("", skin);
        tituloMostrarFPS = new Label("Mostrar FPS", skin);
        mostrarFPS = new CheckBox("", skin);
        tituloPrueba = new Label("Prueba", skin);
        prueba = new CheckBox("", skin);
        aceptarGraficos = new TextButton("Aceptar", skin);
        cancelarGraficos = new TextButton("Cancelar", skin);

        pantallaCompleta.setChecked(dato.isPantallaCompleta());
        sincronizacionVertical.setChecked(dato.isSincronizacionVertical());
        filtradoBilineal.setChecked(dato.isFiltradoBilineal());
        mostrarFPS.setChecked(dato.isMostrarFPS());
        prueba.setChecked(dato.isPrueba());

        tablaGraficos = new Table();
        tablaGraficos.add(tituloOpcionesGraficos).colspan(2).padBottom(40).row();
        if (Gdx.app.getType() == Gdx.app.getType().Desktop) {
            tablaGraficos.add(tituloPantallaCompleta).padBottom(10).left();
            tablaGraficos.add(pantallaCompleta).padBottom(10).left().row();
        }
        tablaGraficos.add(tituloSincronizacionVertical).padBottom(10).left();
        tablaGraficos.add(sincronizacionVertical).padBottom(10).left().row();
        tablaGraficos.add(tituloFiltradoBilineal).padBottom(10).left();
        tablaGraficos.add(filtradoBilineal).padBottom(10).left().row();
        tablaGraficos.add(tituloMostrarFPS).padBottom(10).left();
        tablaGraficos.add(mostrarFPS).padBottom(10).left().row();
        tablaGraficos.add(tituloPrueba).padBottom(10).left();
        tablaGraficos.add(prueba).padBottom(10).left().row();

        Table botonesGraficos = new Table();
        botonesGraficos.add(cancelarGraficos).size(160, 32).padRight(20);
        botonesGraficos.add(aceptarGraficos).size(160, 32);
        tablaGraficos.add(botonesGraficos).colspan(2).expand().bottom().pad(32);

        // --- TABLA PARTIDA ---
        tituloOpcionesPartida = new Label("Partida", skin);
        tituloAutoDisparo = new Label("Auto Disparo", skin);
        autoDisparo = new CheckBox("", skin);
        aceptarPartida = new TextButton("Aceptar", skin);
        cancelarPartida = new TextButton("Cancelar", skin);

        autoDisparo.setChecked(dato.isDiparoAutomatico());

        tablaPartida = new Table();
        tablaPartida.add(tituloOpcionesPartida).colspan(2).padBottom(40).row();
        tablaPartida.add(tituloAutoDisparo).padBottom(10).left();
        tablaPartida.add(autoDisparo).padBottom(10).left().row();

        Table botonesPartida = new Table();
        botonesPartida.add(cancelarPartida).size(160, 32).padRight(20);
        botonesPartida.add(aceptarPartida).size(160, 32);
        tablaPartida.add(botonesPartida).colspan(2).expand().bottom().pad(32);

        // --- TABLA CONTROLES ---
        tituloOpcionesControles = new Label("Controles", skin);
        atrasControles = new TextButton("Atras", skin);
        textoArriba = new Label("Arriba", skin);
        textoAbajo = new Label("Abajo", skin);
        textoIzquierda = new Label("Izquierda", skin);
        textoDerecha = new Label("Derecha", skin);
        textoDisparo = new Label("Disparar", skin);
        textoDisparoMisil = new Label("Disparar Misil", skin);
        textoDisparoBomba = new Label("Disparar Bomba", skin);
        textoPausaJuego = new Label("Pausa", skin);

        TextureAtlas atlasControles = recurso.get("texturas/controles.atlas", TextureAtlas.class);
        TextureAtlas atlasDedos = recurso.get("texturas/dedos.atlas", TextureAtlas.class);
        TextureAtlas atlasIconos = recurso.get("texturas/iconos.atlas", TextureAtlas.class);

        if (Gdx.app.getType() == Gdx.app.getType().Desktop) {
            pausaJuego = new Image(atlasControles.findRegion("controlEscape"));
            arriba = new Image(atlasControles.findRegion("controlArriba"));
            abajo = new Image(atlasControles.findRegion("controlAbajo"));
            izquierda = new Image(atlasControles.findRegion("controlIzquierdo"));
            derecha = new Image(atlasControles.findRegion("controlDerecho"));
            disparo = new Image(atlasControles.findRegion("controlZ"));
            disparoMisil = new Image(atlasControles.findRegion("controlX"));
            disparoBomba = new Image(atlasControles.findRegion("controlEspacio"));
            clicIzquierdo = new Image(atlasControles.findRegion("clicIzquierdo"));
            clicDerecho = new Image(atlasControles.findRegion("clicDerecho"));
        } else {
            pausaJuego = new Image(recurso.get("texturas/pausa.png", Texture.class));
            arriba = new Image(atlasDedos.findRegion("arriba"));
            abajo = new Image(atlasDedos.findRegion("abajo"));
            izquierda = new Image(atlasDedos.findRegion("izquierda"));
            derecha = new Image(atlasDedos.findRegion("derecha"));
            disparo = new Image(atlasDedos.findRegion("precionado"));
            disparoMisil = new Image(atlasIconos.findRegion("iconoexplosion"));
            disparoBomba = new Image(atlasIconos.findRegion("iconobomba"));
        }

        tablaControles = new Table();
        tablaControles.add(tituloOpcionesControles).colspan(3).padBottom(20).row();
        
        agregarFilaControl(tablaControles, textoArriba, arriba, null);
        agregarFilaControl(tablaControles, textoAbajo, abajo, null);
        agregarFilaControl(tablaControles, textoIzquierda, izquierda, null);
        agregarFilaControl(tablaControles, textoDerecha, derecha, null);
        agregarFilaControl(tablaControles, textoDisparo, disparo, Gdx.app.getType() == Gdx.app.getType().Desktop ? clicIzquierdo : null);
        agregarFilaControl(tablaControles, textoDisparoMisil, disparoMisil, Gdx.app.getType() == Gdx.app.getType().Desktop ? clicDerecho : null);
        agregarFilaControl(tablaControles, textoDisparoBomba, disparoBomba, null);
        agregarFilaControl(tablaControles, textoPausaJuego, pausaJuego, null);

        tablaControles.add(atrasControles).size(160, 32).colspan(3).expand().bottom().left().pad(32);

        nivelMenu.addActor(tablaPrincipal);
        anadirBotonesOpciones(true);
    }

    private void agregarFilaControl(Table tabla, Label texto, Image imagen, Image clic) {
        tabla.add(texto).left().padRight(20);
        if (Gdx.app.getType() == Gdx.app.getType().Desktop) {
            tabla.add(imagen).size(128, 24).padBottom(5);
            if (clic != null) tabla.add(clic).size(128, 24).padLeft(10).padBottom(5);
            else tabla.add().padBottom(5);
        } else {
            tabla.add(imagen).size(32, 32).padBottom(5);
            tabla.add().padBottom(5);
        }
        tabla.row();
    }

    private void anadirBotonesOpciones(boolean anadir) {
        tablaPrincipal.clear();
        if (anadir) {
            tablaPrincipal.add(tablaOpciones).expand().fill();
        }
    }

    private void anadirBotonesSonido(boolean anadir) {
        tablaPrincipal.clear();
        if (anadir) {
            tablaPrincipal.add(tablaSonido).expand().fill();
        }
    }

    private void anadirBotonesGraficos(boolean anadir) {
        tablaPrincipal.clear();
        if (anadir) {
            tablaPrincipal.add(tablaGraficos).expand().fill();
        }
    }

    private void anadirBotonesPartida(boolean anadir) {
        tablaPrincipal.clear();
        if (anadir) {
            tablaPrincipal.add(tablaPartida).expand().fill();
        }
    }

    private void anadirBotonesControles(boolean anadir) {
        tablaPrincipal.clear();
        if (anadir) {
            tablaPrincipal.add(tablaControles).expand().fill();
        }
    }

    @Override
    public void eventos() {

        atras.addListener(
                new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        juego.setScreen(new PantallaMenu(juego));

                        super.clicked(event, x, y);
                    }
                });

        partida.addListener(
                new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        anadirBotonesOpciones(false);

                        anadirBotonesPartida(true);

                        super.clicked(event, x, y);
                    }
                });

        controles.addListener(
                new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        anadirBotonesOpciones(false);

                        anadirBotonesControles(true);

                        super.clicked(event, x, y);
                    }
                });

        aceptarSonido.addListener(
                new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        anadirBotonesOpciones(true);

                        dato.setVolumenMusica(volumenMusica.getValue());

                        dato.setVolumenSonido(volumenSonido.getValue());

                        dato.setSonido(activarSonido.isChecked());

                        anadirBotonesSonido(false);

                        sonido();

                        configuracion.escribirDatos(dato);

                        super.clicked(event, x, y);
                    }
                });

        cancelarSonido.addListener(
                new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        anadirBotonesOpciones(true);

                        activarSonido.setChecked(dato.isSonido());

                        volumenMusica.setValue(dato.getVolumenMusica());

                        volumenSonido.setValue(dato.getVolumenSonido());

                        anadirBotonesSonido(false);
                    }
                });

        aceptarGraficos.addListener(
                new ClickListener() {

                    @SuppressWarnings("static-access")
                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        anadirBotonesOpciones(true);

                        dato.setPantallaCompleta(pantallaCompleta.isChecked());

                        dato.setSincronizacionVertical(sincronizacionVertical.isChecked());

                        dato.setFiltradoBilineal(filtradoBilineal.isChecked());

                        dato.setMostrarFPS(mostrarFPS.isChecked());

                        dato.setPrueba(prueba.isChecked());

                        anadirBotonesGraficos(false);

                        filtradoBilineal();

                        if (dato.isPantallaCompleta()) {

                            if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

                                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                            }
                        }

                        if (!dato.isPantallaCompleta()) {

                            if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

                                Gdx.graphics.setWindowedMode(
                                        (int) Juego.ANCHO_PANTALLA, (int) Juego.ALTO_PANTALLA);
                            }
                        }

                        configuracion.escribirDatos(dato);

                        super.clicked(event, x, y);
                    }
                });

        cancelarGraficos.addListener(
                new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        anadirBotonesOpciones(true);

                        pantallaCompleta.setChecked(dato.isPantallaCompleta());

                        sincronizacionVertical.setChecked(dato.isSincronizacionVertical());

                        filtradoBilineal.setChecked(dato.isFiltradoBilineal());

                        mostrarFPS.setChecked(dato.isMostrarFPS());

                        prueba.setChecked(dato.isPrueba());

                        anadirBotonesGraficos(false);

                        super.clicked(event, x, y);
                    }
                });

        aceptarPartida.addListener(
                new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        anadirBotonesOpciones(true);

                        dato.setDiparoAutomatico(autoDisparo.isChecked());

                        anadirBotonesPartida(false);

                        configuracion.escribirDatos(dato);

                        super.clicked(event, x, y);
                    }
                });

        cancelarPartida.addListener(
                new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        anadirBotonesOpciones(true);

                        autoDisparo.setChecked(dato.isDiparoAutomatico());

                        anadirBotonesPartida(false);

                        super.clicked(event, x, y);
                    }
                });

        atrasControles.addListener(
                new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        anadirBotonesOpciones(true);

                        anadirBotonesControles(false);

                        super.clicked(event, x, y);
                    }
                });

        graficos.addListener(
                new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        anadirBotonesOpciones(false);

                        anadirBotonesGraficos(true);

                        super.clicked(event, x, y);
                    }
                });

        sonido.addListener(
                new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        anadirBotonesOpciones(false);

                        anadirBotonesSonido(true);

                        super.clicked(event, x, y);
                    }
                });
    }

    @Override
    public void colisiones() {
        // TODO Auto-generated method stub

    }

    @Override
    public void actualizar(float delta) {
        // TODO Auto-generated method stub

    }

    @Override
    public void dibujar(Batch pincel, float delta) {
        // TODO Auto-generated method stub

    }

    @Override
    public void guardarDatos() {

        configuracion.escribirDatos(dato);
    }

    @Override
    public void liberarRecursos() {
        // TODO Auto-generated method stub

    }

    private void filtradoBilineal() {

        if (dato.isFiltradoBilineal()) {

            recurso.get("texturas/invisible.png", Texture.class)
                    .setFilter(TextureFilter.Linear, TextureFilter.Linear);

            recurso.get("texturas/bomba.png", Texture.class)
                    .setFilter(TextureFilter.Linear, TextureFilter.Linear);

            recurso.get("texturas/algas.png", Texture.class)
                    .setFilter(TextureFilter.Linear, TextureFilter.Linear);

            recurso.get("texturas/fondo1.png", Texture.class)
                    .setFilter(TextureFilter.Linear, TextureFilter.Linear);

            recurso.get("texturas/fondo2.png", Texture.class)
                    .setFilter(TextureFilter.Linear, TextureFilter.Linear);

            recurso.get("texturas/fondo3.png", Texture.class)
                    .setFilter(TextureFilter.Linear, TextureFilter.Linear);

            recurso.get("texturas/fondo4.png", Texture.class)
                    .setFilter(TextureFilter.Linear, TextureFilter.Linear);

            recurso.get("texturas/pausa.png", Texture.class)
                    .setFilter(TextureFilter.Linear, TextureFilter.Linear);
            recurso.get("texturas/cursor.png", Texture.class)
                    .setFilter(TextureFilter.Linear, TextureFilter.Linear);
            recurso.get("texturas/menu.png", Texture.class)
                    .setFilter(TextureFilter.Linear, TextureFilter.Linear);
            recurso.get("texturas/badlogic.jpg", Texture.class)
                    .setFilter(TextureFilter.Linear, TextureFilter.Linear);
            recurso.get("texturas/titulo.png", Texture.class)
                    .setFilter(TextureFilter.Linear, TextureFilter.Linear);
            recurso.get("texturas/inicio.png", Texture.class)
                    .setFilter(TextureFilter.Linear, TextureFilter.Linear);
            recurso.get("texturas/icono.png", Texture.class)
                    .setFilter(TextureFilter.Linear, TextureFilter.Linear);
            //	recurso.get("texturas/diamondBlack.png",
            // Texture.class).setFilter(TextureFilter.Linear,
            //			TextureFilter.Linear);

            for (Texture tetura :
                    recurso.get("texturas/pez.atlas", TextureAtlas.class).getTextures()) {

                tetura.setFilter(TextureFilter.Linear, TextureFilter.Linear);
            }

            for (Texture tetura :
                    recurso.get("texturas/iconos.atlas", TextureAtlas.class).getTextures()) {

                tetura.setFilter(TextureFilter.Linear, TextureFilter.Linear);
            }

            for (Texture tetura :
                    recurso.get("texturas/controles.atlas", TextureAtlas.class).getTextures()) {

                tetura.setFilter(TextureFilter.Linear, TextureFilter.Linear);
            }

            for (Texture tetura :
                    recurso.get("texturas/dedos.atlas", TextureAtlas.class).getTextures()) {

                tetura.setFilter(TextureFilter.Linear, TextureFilter.Linear);
            }

            for (Texture tetura :
                    recurso.get("texturas/pez1.atlas", TextureAtlas.class).getTextures()) {

                tetura.setFilter(TextureFilter.Linear, TextureFilter.Linear);
            }

            for (Texture tetura :
                    recurso.get("texturas/pezG.atlas", TextureAtlas.class).getTextures()) {

                tetura.setFilter(TextureFilter.Linear, TextureFilter.Linear);
            }

            for (Texture tetura :
                    recurso.get("texturas/pulpo.atlas", TextureAtlas.class).getTextures()) {

                tetura.setFilter(TextureFilter.Linear, TextureFilter.Linear);
            }

            for (Texture tetura :
                    recurso.get("texturas/pezGlobo.atlas", TextureAtlas.class).getTextures()) {

                tetura.setFilter(TextureFilter.Linear, TextureFilter.Linear);
            }
        }

        if (!dato.isFiltradoBilineal()) {

            recurso.get("texturas/invisible.png", Texture.class)
                    .setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

            recurso.get("texturas/bomba.png", Texture.class)
                    .setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

            recurso.get("texturas/algas.png", Texture.class)
                    .setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

            recurso.get("texturas/fondo1.png", Texture.class)
                    .setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

            recurso.get("texturas/fondo2.png", Texture.class)
                    .setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

            recurso.get("texturas/fondo3.png", Texture.class)
                    .setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

            recurso.get("texturas/fondo4.png", Texture.class)
                    .setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

            recurso.get("texturas/pausa.png", Texture.class)
                    .setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
            recurso.get("texturas/cursor.png", Texture.class)
                    .setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
            recurso.get("texturas/menu.png", Texture.class)
                    .setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

            recurso.get("texturas/badlogic.jpg", Texture.class)
                    .setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
            recurso.get("texturas/titulo.png", Texture.class)
                    .setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
            recurso.get("texturas/inicio.png", Texture.class)
                    .setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
            recurso.get("texturas/icono.png", Texture.class)
                    .setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
            //	recurso.get("texturas/diamondBlack.png",
            // Texture.class).setFilter(TextureFilter.Nearest,
            //			TextureFilter.Nearest);

            for (Texture tetura :
                    recurso.get("texturas/pez.atlas", TextureAtlas.class).getTextures()) {

                tetura.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
            }

            for (Texture tetura :
                    recurso.get("texturas/iconos.atlas", TextureAtlas.class).getTextures()) {

                tetura.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
            }

            for (Texture tetura :
                    recurso.get("texturas/controles.atlas", TextureAtlas.class).getTextures()) {

                tetura.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
            }

            for (Texture tetura :
                    recurso.get("texturas/dedos.atlas", TextureAtlas.class).getTextures()) {

                tetura.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
            }

            for (Texture tetura :
                    recurso.get("texturas/pez1.atlas", TextureAtlas.class).getTextures()) {

                tetura.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
            }

            for (Texture tetura :
                    recurso.get("texturas/pezG.atlas", TextureAtlas.class).getTextures()) {

                tetura.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
            }

            for (Texture tetura :
                    recurso.get("texturas/pulpo.atlas", TextureAtlas.class).getTextures()) {

                tetura.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
            }

            for (Texture tetura :
                    recurso.get("texturas/pezGlobo.atlas", TextureAtlas.class).getTextures()) {

                tetura.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
            }
        }
    }

    private void sonido() {

        recurso.get("audios/musica.ogg", Music.class).setVolume(dato.getVolumenMusica());

        recurso.get("audios/moustro.ogg", Music.class).setVolume(dato.getVolumenMusica());

        recurso.get("audios/creditos.ogg", Music.class).setVolume(dato.getVolumenMusica());

        if (dato.isSonido()) {

            if (!recurso.get("audios/creditos.ogg", Music.class).isPlaying()) {

                recurso.get("audios/creditos.ogg", Music.class).setLooping(true);

                recurso.get("audios/creditos.ogg", Music.class).play();
            }

        } else {

            recurso.get("audios/creditos.ogg", Music.class).stop();
        }
    }
}
