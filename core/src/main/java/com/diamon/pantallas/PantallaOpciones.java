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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;

public class PantallaOpciones extends Pantalla {

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

        atras = new TextButton("Atras", recurso.get("uis/general/uiskin.json", Skin.class));

        atras.setSize(Juego.ANCHO_PANTALLA / 8, 32);

        atras.setPosition(32, 32);

        titulo = new Label("Opciones", recurso.get("uis/general/uiskin.json", Skin.class));

        titulo.setSize(Juego.ANCHO_PANTALLA / 3, 32);

        titulo.setPosition((Juego.ANCHO_PANTALLA / 3) + 50, Juego.ALTO_PANTALLA - 64);

        partida = new TextButton("Partida", recurso.get("uis/general/uiskin.json", Skin.class));

        partida.setSize(Juego.ANCHO_PANTALLA / 3, 32);

        partida.setPosition(Juego.ANCHO_PANTALLA / 3, 240);

        controles = new TextButton("Controles", recurso.get("uis/general/uiskin.json", Skin.class));

        controles.setSize(Juego.ANCHO_PANTALLA / 3, 32);

        graficos = new TextButton("Graficos", recurso.get("uis/general/uiskin.json", Skin.class));

        graficos.setSize(Juego.ANCHO_PANTALLA / 3, 32);

        sonido = new TextButton("Sonido", recurso.get("uis/general/uiskin.json", Skin.class));

        sonido.setSize(Juego.ANCHO_PANTALLA / 3, 32);

        if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

            controles.setPosition(Juego.ANCHO_PANTALLA / 3, 192);

            graficos.setPosition(Juego.ANCHO_PANTALLA / 3, 144);

            sonido.setPosition(Juego.ANCHO_PANTALLA / 3, 96);
        }

        if (Gdx.app.getType() == Gdx.app.getType().Android) {

            controles.setPosition(Juego.ANCHO_PANTALLA / 3, 240);

            graficos.setPosition(Juego.ANCHO_PANTALLA / 3, 144);

            sonido.setPosition(Juego.ANCHO_PANTALLA / 3, 192);
        }

        textoPausaJuego = new Label("Pausa", recurso.get("uis/general/uiskin.json", Skin.class));

        textoPausaJuego.setPosition(Juego.ANCHO_PANTALLA / 4, 320);

        if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

            pausaJuego =
                    new Image(
                            recurso.get("texturas/controles.atlas", TextureAtlas.class)
                                    .findRegion("controlEscape"));

            pausaJuego.setSize(128, 24);
        }

        if (Gdx.app.getType() == Gdx.app.getType().Android) {

            pausaJuego = new Image(recurso.get("texturas/pausa.png", Texture.class));

            pausaJuego.setSize(32, 32);
        }

        pausaJuego.setPosition(Juego.ANCHO_PANTALLA / 2, 320);

        textoArriba = new Label("Arriba", recurso.get("uis/general/uiskin.json", Skin.class));

        textoArriba.setPosition(Juego.ANCHO_PANTALLA / 4, 288);

        if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

            arriba =
                    new Image(
                            recurso.get("texturas/controles.atlas", TextureAtlas.class)
                                    .findRegion("controlArriba"));

            arriba.setSize(128, 24);
        }

        if (Gdx.app.getType() == Gdx.app.getType().Android) {

            arriba =
                    new Image(
                            recurso.get("texturas/dedos.atlas", TextureAtlas.class)
                                    .findRegion("arriba"));

            arriba.setSize(32, 32);
        }

        arriba.setPosition(Juego.ANCHO_PANTALLA / 2, 288);

        textoAbajo = new Label("Abajo", recurso.get("uis/general/uiskin.json", Skin.class));

        textoAbajo.setPosition(Juego.ANCHO_PANTALLA / 4, 256);

        if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

            abajo =
                    new Image(
                            recurso.get("texturas/controles.atlas", TextureAtlas.class)
                                    .findRegion("controlAbajo"));

            abajo.setSize(128, 24);
        }

        if (Gdx.app.getType() == Gdx.app.getType().Android) {

            abajo =
                    new Image(
                            recurso.get("texturas/dedos.atlas", TextureAtlas.class)
                                    .findRegion("abajo"));

            abajo.setSize(32, 32);
        }

        abajo.setPosition(Juego.ANCHO_PANTALLA / 2, 256);

        textoIzquierda = new Label("Izquierda", recurso.get("uis/general/uiskin.json", Skin.class));

        textoIzquierda.setPosition(Juego.ANCHO_PANTALLA / 4, 224);

        if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

            izquierda =
                    new Image(
                            recurso.get("texturas/controles.atlas", TextureAtlas.class)
                                    .findRegion("controlIzquierdo"));

            izquierda.setSize(128, 24);
        }

        if (Gdx.app.getType() == Gdx.app.getType().Android) {

            izquierda =
                    new Image(
                            recurso.get("texturas/dedos.atlas", TextureAtlas.class)
                                    .findRegion("izquierda"));

            izquierda.setSize(32, 32);
        }

        izquierda.setPosition(Juego.ANCHO_PANTALLA / 2, 224);

        textoDerecha = new Label("Derecha", recurso.get("uis/general/uiskin.json", Skin.class));

        textoDerecha.setPosition(Juego.ANCHO_PANTALLA / 4, 192);

        if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

            derecha =
                    new Image(
                            recurso.get("texturas/controles.atlas", TextureAtlas.class)
                                    .findRegion("controlDerecho"));

            derecha.setSize(128, 24);
        }

        if (Gdx.app.getType() == Gdx.app.getType().Android) {

            derecha =
                    new Image(
                            recurso.get("texturas/dedos.atlas", TextureAtlas.class)
                                    .findRegion("derecha"));

            derecha.setSize(32, 32);
        }

        derecha.setPosition(Juego.ANCHO_PANTALLA / 2, 192);

        textoDisparo = new Label("Disparar", recurso.get("uis/general/uiskin.json", Skin.class));

        textoDisparo.setPosition(Juego.ANCHO_PANTALLA / 4, 160);

        if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

            disparo =
                    new Image(
                            recurso.get("texturas/controles.atlas", TextureAtlas.class)
                                    .findRegion("controlZ"));

            disparo.setSize(128, 24);
        }

        if (Gdx.app.getType() == Gdx.app.getType().Android) {

            disparo =
                    new Image(
                            recurso.get("texturas/dedos.atlas", TextureAtlas.class)
                                    .findRegion("precionado"));

            disparo.setSize(32, 32);
        }

        disparo.setPosition(Juego.ANCHO_PANTALLA / 2, 160);

        textoDisparoMisil =
                new Label("Disparar Misil", recurso.get("uis/general/uiskin.json", Skin.class));

        textoDisparoMisil.setPosition(Juego.ANCHO_PANTALLA / 4, 128);

        if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

            disparoMisil =
                    new Image(
                            recurso.get("texturas/controles.atlas", TextureAtlas.class)
                                    .findRegion("controlX"));

            disparoMisil.setSize(128, 24);
        }

        if (Gdx.app.getType() == Gdx.app.getType().Android) {

            disparoMisil =
                    new Image(
                            recurso.get("texturas/iconos.atlas", TextureAtlas.class)
                                    .findRegion("iconoexplosion"));

            disparoMisil.setSize(32, 32);
        }

        disparoMisil.setPosition(Juego.ANCHO_PANTALLA / 2, 128);

        textoDisparoBomba =
                new Label("Disparar Bomba", recurso.get("uis/general/uiskin.json", Skin.class));

        textoDisparoBomba.setPosition(Juego.ANCHO_PANTALLA / 4, 96);

        if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

            disparoBomba =
                    new Image(
                            recurso.get("texturas/controles.atlas", TextureAtlas.class)
                                    .findRegion("controlEspacio"));

            disparoBomba.setSize(128, 24);
        }

        if (Gdx.app.getType() == Gdx.app.getType().Android) {

            disparoBomba =
                    new Image(
                            recurso.get("texturas/iconos.atlas", TextureAtlas.class)
                                    .findRegion("iconobomba"));

            disparoBomba.setSize(32, 32);
        }

        disparoBomba.setPosition(Juego.ANCHO_PANTALLA / 2, 96);

        clicIzquierdo =
                new Image(
                        recurso.get("texturas/controles.atlas", TextureAtlas.class)
                                .findRegion("clicIzquierdo"));

        clicIzquierdo.setSize(128, 24);

        clicIzquierdo.setPosition(Juego.ANCHO_PANTALLA / 2 + 160, 160);

        clicDerecho =
                new Image(
                        recurso.get("texturas/controles.atlas", TextureAtlas.class)
                                .findRegion("clicDerecho"));

        clicDerecho.setSize(128, 24);

        clicDerecho.setPosition(Juego.ANCHO_PANTALLA / 2 + 160, 128);

        tituloOpcionesControles =
                new Label("Controles", recurso.get("uis/general/uiskin.json", Skin.class));

        tituloOpcionesControles.setPosition(
                (Juego.ANCHO_PANTALLA / 3) + 50, Juego.ALTO_PANTALLA - 64);

        atrasControles =
                new TextButton("Atras", recurso.get("uis/general/uiskin.json", Skin.class));

        atrasControles.setSize(Juego.ANCHO_PANTALLA / 7, 32);

        atrasControles.setPosition(32, 32);

        tituloOpcionesPartida =
                new Label("Partida", recurso.get("uis/general/uiskin.json", Skin.class));

        tituloOpcionesPartida.setPosition(
                (Juego.ANCHO_PANTALLA / 3) + 54, Juego.ALTO_PANTALLA - 64);

        tituloAutoDisparo =
                new Label("Auto Disparo", recurso.get("uis/general/uiskin.json", Skin.class));

        tituloAutoDisparo.setPosition(Juego.ANCHO_PANTALLA / 4, 288);

        autoDisparo = new CheckBox("", recurso.get("uis/general/uiskin.json", Skin.class));

        autoDisparo.setPosition(Juego.ANCHO_PANTALLA / 2 + 128, 288);

        cancelarPartida =
                new TextButton("Cancelar", recurso.get("uis/general/uiskin.json", Skin.class));

        cancelarPartida.setSize(Juego.ANCHO_PANTALLA / 7, 32);

        cancelarPartida.setPosition(32, 32);

        aceptarPartida =
                new TextButton("Aceptar", recurso.get("uis/general/uiskin.json", Skin.class));

        aceptarPartida.setSize(Juego.ANCHO_PANTALLA / 7, 32);

        aceptarPartida.setPosition(608 - aceptarPartida.getWidth(), 32);

        autoDisparo.setChecked(dato.isDiparoAutomatico());

        tituloPantallaCompleta =
                new Label("Pantalla Completa", recurso.get("uis/general/uiskin.json", Skin.class));

        pantallaCompleta = new CheckBox("", recurso.get("uis/general/uiskin.json", Skin.class));

        tituloSincronizacionVertical =
                new Label("V-Sync", recurso.get("uis/general/uiskin.json", Skin.class));

        sincronizacionVertical =
                new CheckBox("", recurso.get("uis/general/uiskin.json", Skin.class));

        tituloFiltradoBilineal =
                new Label("Filtrado Bilineal", recurso.get("uis/general/uiskin.json", Skin.class));

        filtradoBilineal = new CheckBox("", recurso.get("uis/general/uiskin.json", Skin.class));

        tituloMostrarFPS =
                new Label("Mostrar FPS", recurso.get("uis/general/uiskin.json", Skin.class));

        mostrarFPS = new CheckBox("", recurso.get("uis/general/uiskin.json", Skin.class));

        if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

            tituloPantallaCompleta.setPosition(Juego.ANCHO_PANTALLA / 4, 288);

            pantallaCompleta.setPosition(Juego.ANCHO_PANTALLA / 2 + 128, 288);

            tituloSincronizacionVertical.setPosition(Juego.ANCHO_PANTALLA / 4, 240);

            sincronizacionVertical.setPosition(Juego.ANCHO_PANTALLA / 2 + 128, 240);

            tituloFiltradoBilineal.setPosition(Juego.ANCHO_PANTALLA / 4, 192);

            filtradoBilineal.setPosition(Juego.ANCHO_PANTALLA / 2 + 128, 192);

            tituloMostrarFPS.setPosition(Juego.ANCHO_PANTALLA / 4, 144);

            mostrarFPS.setPosition(Juego.ANCHO_PANTALLA / 2 + 128, 144);
        }

        if (Gdx.app.getType() == Gdx.app.getType().Android) {

            tituloPantallaCompleta.setPosition(Juego.ANCHO_PANTALLA / 4, 336);

            pantallaCompleta.setPosition(Juego.ANCHO_PANTALLA / 2 + 128, 336);

            tituloSincronizacionVertical.setPosition(Juego.ANCHO_PANTALLA / 4, 288);

            sincronizacionVertical.setPosition(Juego.ANCHO_PANTALLA / 2 + 128, 288);

            tituloFiltradoBilineal.setPosition(Juego.ANCHO_PANTALLA / 4, 240);

            filtradoBilineal.setPosition(Juego.ANCHO_PANTALLA / 2 + 128, 240);

            tituloMostrarFPS.setPosition(Juego.ANCHO_PANTALLA / 4, 192);

            mostrarFPS.setPosition(Juego.ANCHO_PANTALLA / 2 + 128, 192);
        }

        tituloPrueba = new Label("Prueba", recurso.get("uis/general/uiskin.json", Skin.class));

        tituloPrueba.setPosition(Juego.ANCHO_PANTALLA / 4, 96);

        prueba = new CheckBox("", recurso.get("uis/general/uiskin.json", Skin.class));

        prueba.setPosition(Juego.ANCHO_PANTALLA / 2 + 128, 96);

        cancelarGraficos =
                new TextButton("Cancelar", recurso.get("uis/general/uiskin.json", Skin.class));

        cancelarGraficos.setSize(Juego.ANCHO_PANTALLA / 7, 32);

        cancelarGraficos.setPosition(32, 32);

        aceptarGraficos =
                new TextButton("Aceptar", recurso.get("uis/general/uiskin.json", Skin.class));

        aceptarGraficos.setSize(Juego.ANCHO_PANTALLA / 7, 32);

        aceptarGraficos.setPosition(608 - aceptarGraficos.getWidth(), 32);

        tituloOpcionesGraficos =
                new Label("Graficos", recurso.get("uis/general/uiskin.json", Skin.class));

        tituloOpcionesGraficos.setPosition(
                (Juego.ANCHO_PANTALLA / 3) + 50, Juego.ALTO_PANTALLA - 64);

        pantallaCompleta.setChecked(dato.isPantallaCompleta());

        sincronizacionVertical.setChecked(dato.isSincronizacionVertical());

        filtradoBilineal.setChecked(dato.isFiltradoBilineal());

        mostrarFPS.setChecked(dato.isMostrarFPS());

        prueba.setChecked(dato.isPrueba());

        tituloMusica =
                new Label(
                        "Volumen de la Musica", recurso.get("uis/general/uiskin.json", Skin.class));

        tituloMusica.setPosition(Juego.ANCHO_PANTALLA / 8, 240);

        volumenMusica =
                new Slider(
                        0.0f,
                        1.0f,
                        0.1f,
                        false,
                        new Skin(
                                Gdx.files.internal("uis/general/uiskin.json"),
                                new TextureAtlas("uis/general/uiskin.atlas")));

        volumenMusica.setPosition(Juego.ANCHO_PANTALLA / 2 - 64, 240);

        tituloSonido =
                new Label("Volumen del Sonido", recurso.get("uis/general/uiskin.json", Skin.class));

        tituloSonido.setPosition(Juego.ANCHO_PANTALLA / 8, 192);

        volumenSonido =
                new Slider(
                        0.0f,
                        1.0f,
                        0.1f,
                        false,
                        new Skin(
                                Gdx.files.internal("uis/general/uiskin.json"),
                                new TextureAtlas("uis/general/uiskin.atlas")));

        volumenSonido.setPosition(Juego.ANCHO_PANTALLA / 2 - 64, 192);

        tituloactivarSonido =
                new Label("Musica del Juego", recurso.get("uis/general/uiskin.json", Skin.class));

        tituloactivarSonido.setPosition(Juego.ANCHO_PANTALLA / 8, 144);

        activarSonido = new CheckBox("", recurso.get("uis/general/uiskin.json", Skin.class));

        activarSonido.setPosition(Juego.ANCHO_PANTALLA / 2 - 64, 144);

        cancelarSonido =
                new TextButton("Cancelar", recurso.get("uis/general/uiskin.json", Skin.class));

        cancelarSonido.setSize(Juego.ANCHO_PANTALLA / 7, 32);

        cancelarSonido.setPosition(32, 32);

        aceptarSonido =
                new TextButton("Aceptar", recurso.get("uis/general/uiskin.json", Skin.class));

        aceptarSonido.setSize(Juego.ANCHO_PANTALLA / 7, 32);

        aceptarSonido.setPosition(608 - cancelarSonido.getWidth(), 32);

        tituloOpcionesSonido =
                new Label("Sonido", recurso.get("uis/general/uiskin.json", Skin.class));

        tituloOpcionesSonido.setPosition((Juego.ANCHO_PANTALLA / 3) + 56, Juego.ALTO_PANTALLA - 64);

        activarSonido.setChecked(dato.isSonido());

        volumenMusica.setValue(dato.getVolumenMusica());

        volumenSonido.setValue(dato.getVolumenSonido());

        anadirBotonesOpciones(true);
    }

    @SuppressWarnings("static-access")
    private void anadirBotonesOpciones(boolean anadir) {

        if (anadir) {

            nivelMenu.addActor(titulo);

            if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

                nivelMenu.addActor(partida);
            }

            nivelMenu.addActor(controles);

            nivelMenu.addActor(graficos);

            nivelMenu.addActor(sonido);

            nivelMenu.addActor(atras);

        } else {
            titulo.remove();

            if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

                partida.remove();
            }

            controles.remove();

            graficos.remove();

            sonido.remove();

            atras.remove();
        }
    }

    private void anadirBotonesSonido(boolean anadir) {

        if (anadir) {
            nivelMenu.addActor(tituloMusica);

            nivelMenu.addActor(volumenMusica);

            nivelMenu.addActor(tituloSonido);

            nivelMenu.addActor(volumenSonido);

            nivelMenu.addActor(tituloactivarSonido);

            nivelMenu.addActor(activarSonido);

            nivelMenu.addActor(aceptarSonido);

            nivelMenu.addActor(cancelarSonido);

            nivelMenu.addActor(tituloOpcionesSonido);

        } else {

            tituloMusica.remove();

            volumenMusica.remove();

            tituloSonido.remove();

            volumenSonido.remove();

            tituloactivarSonido.remove();

            activarSonido.remove();

            aceptarSonido.remove();

            cancelarSonido.remove();

            tituloOpcionesSonido.remove();
        }
    }

    @SuppressWarnings("static-access")
    private void anadirBotonesGraficos(boolean anadir) {

        if (anadir) {

            if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

                nivelMenu.addActor(tituloPantallaCompleta);

                nivelMenu.addActor(pantallaCompleta);
            }

            nivelMenu.addActor(tituloSincronizacionVertical);

            nivelMenu.addActor(sincronizacionVertical);

            nivelMenu.addActor(tituloFiltradoBilineal);

            nivelMenu.addActor(filtradoBilineal);

            nivelMenu.addActor(tituloMostrarFPS);

            nivelMenu.addActor(mostrarFPS);

            nivelMenu.addActor(tituloPrueba);

            nivelMenu.addActor(prueba);

            nivelMenu.addActor(aceptarGraficos);

            nivelMenu.addActor(cancelarGraficos);

            nivelMenu.addActor(tituloOpcionesGraficos);

        } else {

            if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

                tituloPantallaCompleta.remove();

                pantallaCompleta.remove();
            }

            tituloSincronizacionVertical.remove();

            sincronizacionVertical.remove();

            tituloFiltradoBilineal.remove();

            filtradoBilineal.remove();

            tituloMostrarFPS.remove();

            mostrarFPS.remove();

            tituloPrueba.remove();

            prueba.remove();

            aceptarGraficos.remove();

            cancelarGraficos.remove();

            tituloOpcionesGraficos.remove();
        }
    }

    private void anadirBotonesPartida(boolean anadir) {

        if (anadir) {

            nivelMenu.addActor(tituloOpcionesPartida);

            nivelMenu.addActor(tituloAutoDisparo);

            nivelMenu.addActor(autoDisparo);

            nivelMenu.addActor(aceptarPartida);

            nivelMenu.addActor(cancelarPartida);

        } else {

            tituloOpcionesPartida.remove();

            tituloAutoDisparo.remove();

            autoDisparo.remove();

            aceptarPartida.remove();

            cancelarPartida.remove();
        }
    }

    @SuppressWarnings("static-access")
    private void anadirBotonesControles(boolean anadir) {

        if (anadir) {

            nivelMenu.addActor(tituloOpcionesControles);

            nivelMenu.addActor(atrasControles);

            nivelMenu.addActor(textoArriba);

            nivelMenu.addActor(arriba);

            nivelMenu.addActor(textoAbajo);

            nivelMenu.addActor(abajo);

            nivelMenu.addActor(textoIzquierda);

            nivelMenu.addActor(izquierda);

            nivelMenu.addActor(textoDerecha);

            nivelMenu.addActor(derecha);

            nivelMenu.addActor(textoDisparo);

            nivelMenu.addActor(disparo);

            nivelMenu.addActor(textoDisparoMisil);

            nivelMenu.addActor(disparoMisil);

            nivelMenu.addActor(textoDisparoBomba);

            nivelMenu.addActor(disparoBomba);

            nivelMenu.addActor(textoPausaJuego);

            nivelMenu.addActor(pausaJuego);

            if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

                nivelMenu.addActor(clicIzquierdo);

                nivelMenu.addActor(clicDerecho);
            }

        } else {

            tituloOpcionesControles.remove();

            atrasControles.remove();

            textoArriba.remove();

            arriba.remove();

            textoAbajo.remove();

            abajo.remove();

            textoIzquierda.remove();

            izquierda.remove();

            textoDerecha.remove();

            derecha.remove();

            textoDisparo.remove();

            disparo.remove();

            textoDisparoMisil.remove();

            disparoMisil.remove();

            textoDisparoBomba.remove();

            disparoBomba.remove();

            textoPausaJuego.remove();

            pausaJuego.remove();

            if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

                clicDerecho.remove();

                clicIzquierdo.remove();
            }
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
