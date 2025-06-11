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
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;

public class PantallaOpciones extends Pantalla {

    private Table tablaPrincipal;
    private Table tablaCategorias;
    private Table tablaContenidoOpciones;

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

        // Crear tabla principal que ocupará toda la pantalla
        tablaPrincipal = new Table();
        tablaPrincipal.setFillParent(true);
        // tablaPrincipal.setDebug(true); // Descomentar para depuración visual

        // Añadir la tabla principal al escenario del menú
        nivelMenu.addActor(tablaPrincipal);

        // Inicializar las otras tablas que serán parte de tablaPrincipal
        tablaCategorias = new Table();
        tablaContenidoOpciones = new Table();

        // Inicializar los elementos de la UI
        Skin skin = recurso.get("uis/general/uiskin.json", Skin.class);
        titulo = new Label("Opciones", skin);
        partida = new TextButton("Partida", skin);
        controles = new TextButton("Controles", skin);
        graficos = new TextButton("Graficos", skin);
        sonido = new TextButton("Sonido", skin);
        atras = new TextButton("Atras", skin);

        // Inicializar elementos de la sección de gráficos (se usarán en anadirBotonesGraficos)
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

        // Añadir el título principal a la tabla principal
        tablaPrincipal.add(titulo).center().padTop(Value.percentHeight(0.05f, tablaPrincipal)).padBottom(Value.percentHeight(0.05f, tablaPrincipal)).row();

        // Configurar y añadir tablaCategorias
        // tablaCategorias.setDebug(true); // Para depuración de esta tabla anidada
        if (Gdx.app.getType() == Gdx.app.Application.ApplicationType.Desktop) {
            tablaCategorias.add(partida).width(Value.percentWidth(0.4f, tablaPrincipal)).pad(Value.percentHeight(0.01f, tablaPrincipal)).row();
        }
        tablaCategorias.add(controles).width(Value.percentWidth(0.4f, tablaPrincipal)).pad(Value.percentHeight(0.01f, tablaPrincipal)).row();
        tablaCategorias.add(graficos).width(Value.percentWidth(0.4f, tablaPrincipal)).pad(Value.percentHeight(0.01f, tablaPrincipal)).row();
        tablaCategorias.add(sonido).width(Value.percentWidth(0.4f, tablaPrincipal)).pad(Value.percentHeight(0.01f, tablaPrincipal)).row();

        tablaPrincipal.add(tablaCategorias).top().padRight(Value.percentWidth(0.02f, tablaPrincipal)); // Añade la tabla de categorías a la izquierda

        // Añadir tablaContenidoOpciones, se expandirá para llenar el espacio restante
        tablaPrincipal.add(tablaContenidoOpciones).expand().fill().row();

        // Añadir botón 'Atrás' en la parte inferior, ocupando el ancho de la tabla principal
        tablaPrincipal.add(atras).colspan(2).padTop(Value.percentHeight(0.05f, tablaPrincipal)).width(Value.percentWidth(0.2f, tablaPrincipal)).center().row();


        // La lógica de música y cursor se mantiene
        Music musica = recurso.get("audios/creditos.ogg", Music.class);
        if (dato.isSonido()) {
            if (!musica.isPlaying()) {
                musica.setLooping(true);
                musica.play();
            }
        }

        if (Gdx.app.getType() == Gdx.app.getType().Desktop) {
            Gdx.graphics.setCursor(
                    Gdx.graphics.newCursor(
                            new Pixmap(Gdx.files.internal("texturas/cursor.png")), 0, 0));
        }

        // Inicializar los demás elementos de UI que no se han movido a tablas aún
        // (Se refactorizarán en los métodos anadirBotones<Categoria> correspondientes)
        textoPausaJuego = new Label("Pausa", skin);

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

        // Mostrar la vista de categorías de opciones por defecto
        anadirBotonesOpciones(true);
        // Asegurarse de que las otras vistas de contenido no estén visibles inicialmente
        anadirBotonesGraficos(false);
        anadirBotonesSonido(false);
        anadirBotonesPartida(false);
        anadirBotonesControles(false);
    }

    @SuppressWarnings("static-access")
    private void anadirBotonesOpciones(boolean anadir) {
        // Este método ahora controla la visibilidad de los elementos principales de la pantalla de opciones.
        // Cuando 'anadir' es true, se muestran las categorías de opciones y se limpia el contenido específico de una opción.
        // Cuando 'anadir' es false, se ocultan las categorías para dar paso al contenido de una opción específica.
        if (anadir) {
            // Asegurar que la tabla principal y sus componentes principales sean visibles
            // (titulo y atras son parte de tablaPrincipal y su visibilidad se maneja ahí directamente o no necesitan cambiar)
            // Lo principal es mostrar la tabla de categorías y limpiar el contenido de opciones.
            if (tablaCategorias != null) tablaCategorias.setVisible(true);

            if (tablaContenidoOpciones != null) tablaContenidoOpciones.clear();

            // Aseguramos que los elementos que son hijos directos de tablaPrincipal y deben estar siempre
            // visibles con las categorías, lo estén. 'titulo' y 'atras' ya están en tablaPrincipal.
            // Si otros elementos fueran directamente hijos de nivelMenu y necesitaran gestionarse aquí, se haría.

        } else {
            // Ocultar los elementos de categorías cuando se muestra una sub-sección de opciones
            if (tablaCategorias != null) tablaCategorias.setVisible(false);
            // El título principal y el botón 'atras' generalmente permanecen visibles.
            // tablaContenidoOpciones se llenará por el método específico (ej. anadirBotonesGraficos)
        }
    }

    private void anadirBotonesSonido(boolean anadir) {
        // Este método ahora puebla tablaContenidoOpciones con los controles de sonido.
        if (anadir) {
            tablaContenidoOpciones.clear(); // Limpiar contenido anterior
            // tablaContenidoOpciones.setDebug(true); // Para depuración

            // Crear una sub-tabla para los elementos de la sección de sonido
            Table subTablaSonido = new Table();
            // subTablaSonido.setDebug(true); // Para depuración
            Skin skin = recurso.get("uis/general/uiskin.json", Skin.class); // Obtener skin para consistencia

            // Título de la sección de sonido
            tituloOpcionesSonido.setVisible(true); // Asegurarse de que el título específico sea visible
            subTablaSonido.add(tituloOpcionesSonido).colspan(2).padBottom(Value.percentHeight(0.03f, tablaContenidoOpciones)).row();

            // Opciones de sonido
            float labelWidth = Value.percentWidth(0.4f, subTablaSonido); // Ancho para etiquetas
            float controlWidth = Value.percentWidth(0.4f, subTablaSonido); // Ancho para sliders/checkboxes
            float padLeftPercent = 0.05f; // Padding izquierdo para etiquetas

            subTablaSonido.add(tituloMusica).width(labelWidth).left().padLeft(Value.percentWidth(padLeftPercent, subTablaSonido));
            subTablaSonido.add(volumenMusica).width(controlWidth).fillX().row();

            subTablaSonido.add(tituloSonido).width(labelWidth).left().padLeft(Value.percentWidth(padLeftPercent, subTablaSonido));
            subTablaSonido.add(volumenSonido).width(controlWidth).fillX().row();

            subTablaSonido.add(tituloactivarSonido).width(labelWidth).left().padLeft(Value.percentWidth(padLeftPercent, subTablaSonido));
            subTablaSonido.add(activarSonido).row();

            // Botones Aceptar y Cancelar para sonido
            Table botonesAccionSonido = new Table();
            botonesAccionSonido.add(aceptarSonido).width(Value.percentWidth(0.25f, subTablaSonido)).pad(Value.percentHeight(0.02f, subTablaSonido));
            botonesAccionSonido.add(cancelarSonido).width(Value.percentWidth(0.25f, subTablaSonido)).pad(Value.percentHeight(0.02f, subTablaSonido));
            subTablaSonido.add(botonesAccionSonido).colspan(2).padTop(Value.percentHeight(0.05f, subTablaSonido)).row();

            tablaContenidoOpciones.add(subTablaSonido).expand().fill();

        } else {
            tablaContenidoOpciones.clear(); // Limpiar la tabla de contenido si anadir es false
            if (tituloOpcionesSonido != null) tituloOpcionesSonido.setVisible(false);
        }
    }

    @SuppressWarnings("static-access")
    private void anadirBotonesGraficos(boolean anadir) {
        // Este método ahora puebla tablaContenidoOpciones con los controles de gráficos.
        if (anadir) {
            tablaContenidoOpciones.clear(); // Limpiar contenido anterior
            // tablaContenidoOpciones.setDebug(true); // Para depuración de esta tabla

            // Crear una sub-tabla para los elementos de la sección de gráficos
            Table subTablaGraficos = new Table();
            // subTablaGraficos.setDebug(true); // Para depuración de la sub-tabla
            // subTablaGraficos.defaults().pad(Value.percentHeight(0.01f, tablaContenidoOpciones)); // Pad general

            // Título de la sección de gráficos
            // Asegurarse de que el título específico de la sección sea visible y se añada a la subTabla
            tituloOpcionesGraficos.setVisible(true);
            subTablaGraficos.add(tituloOpcionesGraficos).colspan(2).padBottom(Value.percentHeight(0.03f, tablaContenidoOpciones)).row();


            // Opciones de gráficos
            float labelWidth = Value.percentWidth(0.4f, subTablaGraficos); // Ancho para etiquetas
            float checkWidth = Value.percentWidth(0.1f, subTablaGraficos); // Ancho para checkboxes
            float padLeftPercent = 0.1f; // Padding izquierdo para etiquetas

            if (Gdx.app.getType() == Gdx.app.Application.ApplicationType.Desktop) {
                subTablaGraficos.add(tituloPantallaCompleta).width(labelWidth).left().padLeft(Value.percentWidth(padLeftPercent, subTablaGraficos));
                subTablaGraficos.add(pantallaCompleta).width(checkWidth).row();
            }

            subTablaGraficos.add(tituloSincronizacionVertical).width(labelWidth).left().padLeft(Value.percentWidth(padLeftPercent, subTablaGraficos));
            subTablaGraficos.add(sincronizacionVertical).width(checkWidth).row();

            subTablaGraficos.add(tituloFiltradoBilineal).width(labelWidth).left().padLeft(Value.percentWidth(padLeftPercent, subTablaGraficos));
            subTablaGraficos.add(filtradoBilineal).width(checkWidth).row();

            subTablaGraficos.add(tituloMostrarFPS).width(labelWidth).left().padLeft(Value.percentWidth(padLeftPercent, subTablaGraficos));
            subTablaGraficos.add(mostrarFPS).width(checkWidth).row();

            subTablaGraficos.add(tituloPrueba).width(labelWidth).left().padLeft(Value.percentWidth(padLeftPercent, subTablaGraficos));
            subTablaGraficos.add(prueba).width(checkWidth).row();

            // Botones Aceptar y Cancelar para gráficos
            Table botonesAccionGraficos = new Table();
            botonesAccionGraficos.add(aceptarGraficos).width(Value.percentWidth(0.25f, subTablaGraficos)).pad(Value.percentHeight(0.02f, subTablaGraficos));
            botonesAccionGraficos.add(cancelarGraficos).width(Value.percentWidth(0.25f, subTablaGraficos)).pad(Value.percentHeight(0.02f, subTablaGraficos));
            subTablaGraficos.add(botonesAccionGraficos).colspan(2).padTop(Value.percentHeight(0.05f, subTablaGraficos)).row();

            tablaContenidoOpciones.add(subTablaGraficos).expand().fill();

        } else {
            tablaContenidoOpciones.clear(); // Limpiar la tabla de contenido si anadir es false
            // Ocultar el título específico de la sección si es necesario, aunque limpiar la tabla ya lo hace.
            if (tituloOpcionesGraficos != null) tituloOpcionesGraficos.setVisible(false);
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
                        // Si la tabla de categorías está visible, significa que estamos en la vista principal de opciones.
                        // En este caso, el botón "Atrás" debería llevar al menú principal del juego.
                        if (tablaCategorias.isVisible()) {
                            juego.setScreen(new PantallaMenu(juego));
                        } else {
                            // Si la tabla de categorías no está visible, significa que estamos en una sub-pantalla de opciones (ej. Gráficos, Sonido).
                            // En este caso, "Atrás" debería volver a la vista de categorías.
                            anadirBotonesGraficos(false); // Ocultar la sección de gráficos si estaba visible
                            anadirBotonesSonido(false); // Ocultar la sección de sonido si estaba visible
                            anadirBotonesPartida(false); // Ocultar la sección de partida si estaba visible
                            anadirBotonesControles(false); // Ocultar la sección de controles si estaba visible
                            anadirBotonesOpciones(true); // Mostrar la lista de categorías de opciones
                        }
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
                        // Al aceptar, se guardan los cambios y se vuelve a la vista de categorías.
                        anadirBotonesSonido(false); // Limpiar la tabla de contenido de sonido
                        anadirBotonesOpciones(true); // Mostrar las categorías principales

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
                        // Al cancelar, se vuelve a la vista de categorías sin guardar cambios.
                        anadirBotonesSonido(false); // Limpia la tabla de contenido de sonido
                        anadirBotonesOpciones(true); // Muestra las categorías principales

                        // Restablecer los controles a los valores guardados
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
                        // Cuando se aceptan los cambios gráficos, se vuelve a la vista de categorías.
                        anadirBotonesGraficos(false); // Limpia la tabla de contenido de gráficos
                        anadirBotonesOpciones(true); // Muestra las categorías principales

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
                        // Al cancelar, se vuelve a la vista de categorías sin guardar cambios.
                        anadirBotonesGraficos(false); // Limpia la tabla de contenido de gráficos
                        anadirBotonesOpciones(true); // Muestra las categorías principales

                        // Restablecer los checkboxes a los valores guardados
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
                        // Ocultar los botones de categoría principales
                        anadirBotonesOpciones(false);
                        // Mostrar los botones/opciones de gráficos
                        anadirBotonesGraficos(true);

                        super.clicked(event, x, y);
                    }
                });

        sonido.addListener(
                new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        // Ocultar los botones de categoría principales
                        anadirBotonesOpciones(false);
                        // Mostrar los botones/opciones de sonido
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
