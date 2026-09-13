package com.diamon.utilidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.diamon.datos.Configuraciones;
import com.diamon.datos.Datos;
import com.diamon.datos.DatosNiveles;
import com.diamon.datos.InformacionNiveles;
import com.diamon.items.PowerUpSubmarino;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;
import com.diamon.personajes.Cursor;
import com.diamon.personajes.Fondo;
import com.diamon.personajes.Jugador;
import com.diamon.personajes.Terreno;

/**
 * Editor de niveles interactivo con interfaz Scene2D ergonómica a 1280x720.
 * Soporta colocación y borrado de todos los personajes, enemigos, ítems y power-ups,
 * navegación por arrastre (pan) con conversión unproject precisa y zoom de cámara.
 */
public class EditorNivel {

    public enum ModoEditor {
        MOVER, AGREGAR, BORRAR
    }

    private final Stage nivel;
    private final InformacionNiveles informacionNiveles;
    private final DatosNiveles datosNiveles;
    private final Configuraciones configuracion;
    private final Datos dato;
    private final OrthographicCamera camara;
    private final Array<Personaje> personajes;
    private final Pantalla pantalla;
    private final AssetManager recurso;
    private final Cursor cursor;

    private ModoEditor modoActual = ModoEditor.MOVER;
    private boolean terminar = true;

    private Table panelPrincipal;
    private SelectBox<String> selectorActor;
    private SelectBox<String> selectorNivel;

    private TextButton btnMover;
    private TextButton btnAgregar;
    private TextButton btnBorrar;
    private TextButton btnZoomMas;
    private TextButton btnZoomMenos;
    private TextButton btnResetZoom;
    private TextButton btnFondoScroll;
    private TextButton btnFondoParallax;
    private TextButton btnBorrarTodo;
    private TextButton btnPanIzq;
    private TextButton btnPanDer;
    private TextButton btnGuardarSalir;

    private boolean panIzqPresionado = false;
    private boolean panDerPresionado = false;

    private final Vector3 arrastreAnterior = new Vector3(-1, -1, -1);
    private final Vector3 coordMundo = new Vector3();

    private Runnable onGuardarSalir;
    private Runnable onCambioNivel;

    public EditorNivel(Stage nivel, InformacionNiveles informacionNiveles, DatosNiveles datosNiveles,
                       Configuraciones configuracion, Datos dato, OrthographicCamera camara,
                       Array<Personaje> personajes, Pantalla pantalla, AssetManager recurso, Cursor cursor) {
        this.nivel = nivel;
        this.informacionNiveles = informacionNiveles;
        this.datosNiveles = datosNiveles;
        this.configuracion = configuracion;
        this.dato = dato;
        this.camara = camara;
        this.personajes = personajes;
        this.pantalla = pantalla;
        this.recurso = recurso;
        this.cursor = cursor;

        construirUI();
    }

    public void setOnGuardarSalir(Runnable onGuardarSalir) {
        this.onGuardarSalir = onGuardarSalir;
    }

    public void setOnCambioNivel(Runnable onCambioNivel) {
        this.onCambioNivel = onCambioNivel;
    }

    private void construirUI() {
        Skin skin = recurso.get("uis/general/uiskin.json", Skin.class);

        panelPrincipal = new Table();
        panelPrincipal.setFillParent(true);

        // --- BARRA SUPERIOR DE HERRAMIENTAS ---
        Table barraSuperior = new Table();
        barraSuperior.setBackground(skin.newDrawable("white", 0.08f, 0.12f, 0.22f, 0.88f));
        barraSuperior.pad(6);

        Label lblNivel = new Label("Nivel: ", skin);
        lblNivel.setColor(Color.CYAN);

        selectorNivel = new SelectBox<String>(skin);
        String[] niveles = new String[40];
        for (int i = 0; i < 40; i++) {
            niveles[i] = "Nivel " + (i + 1);
        }
        selectorNivel.setItems(niveles);
        selectorNivel.setSelectedIndex(Math.max(0, Math.min(39, datosNiveles.getNumeroNivel() - 1)));

        btnMover = new TextButton("Modo Navegar", skin);
        btnMover.setColor(Color.GREEN);

        btnZoomMas = new TextButton("+ Zoom", skin);
        btnZoomMenos = new TextButton("- Zoom", skin);
        btnResetZoom = new TextButton("1:1", skin);

        btnFondoScroll = new TextButton("Scroll", skin);
        btnFondoParallax = new TextButton("Parallax", skin);
        actualizarColoresFondos();

        btnGuardarSalir = new TextButton("Guardar y Salir", skin);
        btnGuardarSalir.setColor(Color.GOLD);

        barraSuperior.add(lblNivel).padRight(5);
        barraSuperior.add(selectorNivel).size(95, 34).padRight(10);
        barraSuperior.add(btnMover).size(125, 34).padRight(10);
        barraSuperior.add(btnZoomMenos).size(65, 34).padRight(4);
        barraSuperior.add(btnResetZoom).size(50, 34).padRight(4);
        barraSuperior.add(btnZoomMas).size(65, 34).padRight(12);
        barraSuperior.add(btnFondoScroll).size(70, 34).padRight(4);
        barraSuperior.add(btnFondoParallax).size(80, 34).padRight(15);
        barraSuperior.add(btnGuardarSalir).size(140, 34).expandX().right();

        panelPrincipal.add(barraSuperior).fillX().top().row();

        // Espacio central transparente para interactuar directamente con el juego
        panelPrincipal.add().expand().row();

        // --- BARRA INFERIOR DE ACCIONES ---
        Table barraInferior = new Table();
        barraInferior.setBackground(skin.newDrawable("white", 0.08f, 0.12f, 0.22f, 0.88f));
        barraInferior.pad(6);

        btnAgregar = new TextButton("+ Agregar", skin);
        btnBorrar = new TextButton("- Borrar", skin);
        btnBorrarTodo = new TextButton("Vaciar Nivel", skin);

        selectorActor = new SelectBox<String>(skin);
        selectorActor.setItems(
                "Pulpo",
                "TiburonAzul",
                "PezAngel",
                "PezGloboAmarillo",
                "PezGloboNaranja",
                "Bomba",
                "Algas",
                "Perla",
                "BurbujaOxigeno",
                "PowerUp_Turbo",
                "PowerUp_Escudo",
                "PowerUp_Linterna"
        );

        btnPanIzq = new TextButton("<", skin);
        btnPanDer = new TextButton(">", skin);

        barraInferior.add(btnAgregar).size(105, 36).padRight(6);
        barraInferior.add(selectorActor).size(180, 36).padRight(12);
        barraInferior.add(btnBorrar).size(95, 36).padRight(6);
        barraInferior.add(btnBorrarTodo).size(105, 36).padRight(20);
        barraInferior.add(btnPanIzq).size(50, 36).padRight(4);
        barraInferior.add(btnPanDer).size(50, 36).expandX().left();

        panelPrincipal.add(barraInferior).fillX().bottom();

        configurarListeners();
    }

    private void actualizarColoresFondos() {
        if (dato.isFondoScroll()) {
            btnFondoScroll.setColor(Color.CORAL);
            btnFondoParallax.setColor(1.0f, 1.0f, 1.0f, 0.7f);
        } else {
            btnFondoScroll.setColor(1.0f, 1.0f, 1.0f, 0.7f);
            btnFondoParallax.setColor(Color.CORAL);
        }
    }

    private void actualizarColoresModos() {
        btnMover.setColor(modoActual == ModoEditor.MOVER ? Color.GREEN : Color.WHITE);
        btnAgregar.setColor(modoActual == ModoEditor.AGREGAR ? Color.GREEN : Color.WHITE);
        btnBorrar.setColor(modoActual == ModoEditor.BORRAR ? Color.RED : Color.WHITE);
    }

    private void configurarListeners() {
        btnMover.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                modoActual = ModoEditor.MOVER;
                actualizarColoresModos();
            }
        });

        btnAgregar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                modoActual = ModoEditor.AGREGAR;
                actualizarColoresModos();
            }
        });

        btnBorrar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                modoActual = ModoEditor.BORRAR;
                actualizarColoresModos();
            }
        });

        btnZoomMas.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                camara.zoom = Math.max(0.4f, camara.zoom - 0.15f);
                camara.update();
            }
        });

        btnZoomMenos.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                camara.zoom = Math.min(2.5f, camara.zoom + 0.15f);
                camara.update();
            }
        });

        btnResetZoom.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                camara.zoom = 1.0f;
                camara.update();
            }
        });

        btnFondoScroll.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dato.setFondoScroll(true);
                dato.setFondoParallax(false);
                actualizarColoresFondos();
            }
        });

        btnFondoParallax.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dato.setFondoScroll(false);
                dato.setFondoParallax(true);
                actualizarColoresFondos();
            }
        });

        btnBorrarTodo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                vaciarActoresNivel();
            }
        });

        btnPanIzq.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                panIzqPresionado = true;
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                panIzqPresionado = false;
                super.touchUp(event, x, y, pointer, button);
            }
        });

        btnPanDer.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                panDerPresionado = true;
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                panDerPresionado = false;
                super.touchUp(event, x, y, pointer, button);
            }
        });

        btnGuardarSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (onGuardarSalir != null) {
                    onGuardarSalir.run();
                }
            }
        });

        selectorNivel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int nuevoNivel = selectorNivel.getSelectedIndex() + 1;
                datosNiveles.setNumeroNivel(nuevoNivel);
                if (onCambioNivel != null) {
                    onCambioNivel.run();
                }
            }
        });
    }

    private void vaciarActoresNivel() {
        String nivelStr = "Nivel " + datosNiveles.getNumeroNivel();
        datosNiveles.eliminarActores(nivelStr);

        for (int i = personajes.size - 1; i >= 0; i--) {
            Personaje p = personajes.get(i);
            if (!(p instanceof Jugador) && !(p instanceof Fondo) && !(p instanceof Terreno)) {
                p.destruirCuerpo(pantalla.getMundoVirtual());
                personajes.removeIndex(i);
            }
        }
    }

    public void agregarUI() {
        terminar = false;
        modoActual = ModoEditor.MOVER;
        actualizarColoresModos();
        selectorNivel.setSelectedIndex(Math.max(0, Math.min(39, datosNiveles.getNumeroNivel() - 1)));
        nivel.addActor(panelPrincipal);
    }

    public void eliminarUI() {
        terminar = true;
        panIzqPresionado = false;
        panDerPresionado = false;
        arrastreAnterior.set(-1, -1, -1);
        camara.zoom = 1.0f;
        camara.update();
        panelPrincipal.remove();
    }

    public void actualizar() {
        if (terminar) return;

        float velocidadPan = (camara.zoom * 15.0f) * Gdx.graphics.getDeltaTime();
        if (panIzqPresionado) {
            camara.position.x -= velocidadPan;
            camara.update();
        }
        if (panDerPresionado) {
            camara.position.x += velocidadPan;
            camara.update();
        }
    }

    public boolean toquePresionado(InputEvent ev, float x, float y, int puntero, int boton) {
        if (terminar) return false;

        // Si el toque se originó en las barras de herramientas superior o inferior, ignorar colocación en el mundo
        if (y > Juego.ALTO_PANTALLA - 52 || y < 52) {
            return false;
        }

        // Convertir coordenadas de pantalla a metros de mundo exactamente con unproject
        coordMundo.set(Gdx.input.getX(puntero), Gdx.input.getY(puntero), 0);
        camara.unproject(coordMundo);

        if (modoActual == ModoEditor.AGREGAR) {
            crearYRegistrarActor(selectorActor.getSelected(), coordMundo.x, coordMundo.y);
            return true;
        } else if (modoActual == ModoEditor.BORRAR) {
            eliminarActorEnPosicion(coordMundo.x, coordMundo.y);
            return true;
        } else if (modoActual == ModoEditor.MOVER) {
            arrastreAnterior.set(Gdx.input.getX(puntero), Gdx.input.getY(puntero), 0);
            return true;
        }
        return false;
    }

    public boolean toqueDeslizando(InputEvent ev, float x, float y, int puntero) {
        if (terminar || modoActual != ModoEditor.MOVER) return false;

        float screenX = Gdx.input.getX(puntero);
        float screenY = Gdx.input.getY(puntero);

        if (arrastreAnterior.x >= 0 && arrastreAnterior.y >= 0) {
            Vector3 actual = camara.unproject(new Vector3(screenX, screenY, 0));
            Vector3 previo = camara.unproject(new Vector3(arrastreAnterior.x, arrastreAnterior.y, 0));
            camara.position.sub(actual.x - previo.x, actual.y - previo.y, 0);
            camara.update();
        }
        arrastreAnterior.set(screenX, screenY, 0);
        return true;
    }

    public boolean toqueLevantado(InputEvent ev, float x, float y, int puntero, int boton) {
        arrastreAnterior.set(-1, -1, -1);
        return true;
    }

    public boolean ratonMoviendo(InputEvent ev, float x, float y) {
        if (terminar || cursor == null) return false;
        coordMundo.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camara.unproject(coordMundo);
        cursor.setPosition(coordMundo.x * Juego.UNIDAD_DEL_MUNDO, coordMundo.y * Juego.UNIDAD_DEL_MUNDO);
        return true;
    }

    public boolean teclaPresionada(InputEvent ev, int codigoTecla) {
        if (terminar) return false;
        if (codigoTecla == Keys.A) {
            modoActual = ModoEditor.AGREGAR;
            actualizarColoresModos();
        } else if (codigoTecla == Keys.D || codigoTecla == Keys.BACKSPACE) {
            modoActual = ModoEditor.BORRAR;
            actualizarColoresModos();
        } else if (codigoTecla == Keys.M) {
            modoActual = ModoEditor.MOVER;
            actualizarColoresModos();
        }
        return true;
    }

    public boolean teclaLevantada(InputEvent ev, int codigoTecla) {
        return true;
    }

    private void crearYRegistrarActor(String nombreTipo, float worldX, float worldY) {
        Personaje nuevo = FabricaActores.crearActor(
                nombreTipo,
                worldX * Juego.UNIDAD_DEL_MUNDO,
                worldY * Juego.UNIDAD_DEL_MUNDO,
                pantalla,
                recurso,
                pantalla.getLuz()
        );

        if (nuevo != null) {
            personajes.add(nuevo);

            String tipoGuardado = nuevo.getClass().getName();
            if (nuevo instanceof PowerUpSubmarino) {
                tipoGuardado += "." + ((PowerUpSubmarino) nuevo).getTipo().name();
            }

            String nivelStr = "Nivel " + datosNiveles.getNumeroNivel();
            Array<Personaje> lista = new Array<Personaje>();
            lista.add(nuevo);
            datosNiveles.gurdarActores(lista, tipoGuardado, nivelStr);
        }
    }

    private void eliminarActorEnPosicion(float worldX, float worldY) {
        String nivelStr = "Nivel " + datosNiveles.getNumeroNivel();

        for (int i = personajes.size - 1; i >= 0; i--) {
            Personaje p = personajes.get(i);
            if (p instanceof Jugador || p instanceof Fondo || p instanceof Terreno) {
                continue;
            }

            Rectangle bounds = new Rectangle(p.getBoundingRectangle());
            // Tolerancia de selección para mejorar ergonomía táctil en móviles
            bounds.x -= 0.4f;
            bounds.y -= 0.4f;
            bounds.width += 0.8f;
            bounds.height += 0.8f;

            if (bounds.contains(worldX, worldY)) {
                datosNiveles.eliminarActorPorPosicion(nivelStr, p.getX(), p.getY(), 1.5f);
                p.destruirCuerpo(pantalla.getMundoVirtual());
                personajes.removeIndex(i);
                break;
            }
        }
    }

    public boolean isTerminar() {
        return terminar;
    }

    public void setTerminar(boolean terminar) {
        this.terminar = terminar;
    }
}
