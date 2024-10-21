package com.diamon.utilidades;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.diamon.datos.InformacionNiveles;
import com.diamon.datos.Configuraciones;
import com.diamon.datos.Datos;
import com.diamon.datos.DatosNiveles;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;
import com.diamon.personajes.Algas;
import com.diamon.personajes.Bomba;
import com.diamon.personajes.Cursor;
import com.diamon.personajes.Fondo;
import com.diamon.personajes.Jugador;
import com.diamon.personajes.PezAngel;
import com.diamon.personajes.PezGloboAmarillo;
import com.diamon.personajes.PezGloboNaranja;
import com.diamon.personajes.Pulpo;
import com.diamon.personajes.Terreno;

public class EditorNivel {

	private OrthographicCamera camara;

	private Vector3 corriendo;

	private Vector3 detras;

	private Vector3 delta;

	private Array<Personaje> personajes;

	private Pantalla pantalla;

	private AssetManager recurso;

	private boolean agregar;

	private boolean moverEnY;

	private boolean terminar;

	protected Stage nivel;

	private InformacionNiveles informacionNiveles;

	private DatosNiveles datosNiveles;

	protected Configuraciones configuracion;

	protected Datos dato;

	protected String tipo;

	private TextButton borrarActores;

	private TextButton agreagarActores;

	private TextButton moverEsenario;

	private TextButton derecha;

	private TextButton izquierda;

	private boolean pD, pI;

	private TextButton moverEsenarioEnY;

	private Label zoomCamara;

	private Label mundo;

	private TextButton zoomCamaraMas;

	private TextButton zoomCamaraMenos;

	private TextButton fondoScroll;

	private TextButton fondoParallax;

	private TextButton actualizarActores;

	private SelectBox<String> tipoActor;

	private SelectBox<String> numeroNivel;

	private Cursor cursor;

	private boolean actualizar;

	private boolean toque;

	private int velocidadCamara;

	public EditorNivel(final Stage nivel, final InformacionNiveles informacionNiveles, final DatosNiveles datosNiveles,
			Configuraciones configuracion, Datos dato, final OrthographicCamera camara,
			final Array<Personaje> personajes, final Pantalla pantalla, final AssetManager recurso, Cursor cursor) {

		this.camara = camara;

		this.informacionNiveles = informacionNiveles;

		this.datosNiveles = datosNiveles;

		this.configuracion = configuracion;

		this.dato = dato;

		this.personajes = personajes;

		this.recurso = recurso;

		this.pantalla = pantalla;

		this.nivel = nivel;

		this.cursor = cursor;

		velocidadCamara = 5;

		pD = pI = false;

		actualizar = false;

		toque = false;

		agregar = false;

		moverEnY = false;

		terminar = true;

		tipo = "";

		corriendo = new Vector3();

		detras = new Vector3(-1, -1, -1);

		delta = new Vector3();

		ui();

		eventos();

	}

	private void ui() {

		borrarActores = new TextButton("Borrar Actores", recurso.get("uis/general/uiskin.json", Skin.class));

		borrarActores.setSize(160, 32);

		borrarActores.setPosition(16, 16);

		borrarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		derecha = new TextButton("Derecha +", recurso.get("uis/general/uiskin.json", Skin.class));

		derecha.setSize(96, 32);

		derecha.setPosition(432, 16);

		derecha.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		izquierda = new TextButton("Izquierda -", recurso.get("uis/general/uiskin.json", Skin.class));

		izquierda.setSize(96, 32);

		izquierda.setPosition(336, 16);

		izquierda.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		actualizarActores = new TextButton("Actualizar Actores", recurso.get("uis/general/uiskin.json", Skin.class));

		actualizarActores.setSize(160, 32);

		actualizarActores.setPosition(176, 16);

		actualizarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		numeroNivel = new SelectBox<String>(recurso.get("uis/general/uiskin.json", Skin.class));

		numeroNivel.setSize(64, 32);

		numeroNivel.setPosition(64, 416);

		numeroNivel.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		String[] niveles = new String[40];

		for (int i = 0; i < niveles.length; i++) {

			niveles[i] = "" + (i + 1);

		}

		numeroNivel.setItems(niveles);

		numeroNivel.setSelectedIndex(datosNiveles.getNumeroNivel() - 1);

		mundo = new Label("Nivel: ", recurso.get("uis/general/uiskin.json", Skin.class), "default-font", Color.GREEN);

		mundo.setSize(96, 32);

		mundo.setPosition(16, 416);

		mundo.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		agreagarActores = new TextButton("Agregar Actores", recurso.get("uis/general/uiskin.json", Skin.class));

		agreagarActores.setSize(144, 32);

		agreagarActores.setPosition(16, 352);

		agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		moverEsenario = new TextButton("Mover Escenario", recurso.get("uis/general/uiskin.json", Skin.class));

		moverEsenario.setSize(144, 32);

		moverEsenario.setPosition(16, 448);

		moverEsenario.setColor(1, 0, 0, 1);

		moverEsenarioEnY = new TextButton("Mover Escenario Y", recurso.get("uis/general/uiskin.json", Skin.class));

		moverEsenarioEnY.setSize(144, 32);

		moverEsenarioEnY.setPosition(160, 448);

		moverEsenarioEnY.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		fondoScroll = new TextButton("Fondo Scroll", recurso.get("uis/general/uiskin.json", Skin.class));

		fondoScroll.setSize(144, 32);

		fondoScroll.setPosition(160, 416);

		if (dato.isFondoScroll()) {

			fondoScroll.setColor(1, 0, 0, 1);

		} else {

			fondoScroll.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		}

		fondoParallax = new TextButton("Fondo Parallax", recurso.get("uis/general/uiskin.json", Skin.class));

		fondoParallax.setSize(144, 32);

		fondoParallax.setPosition(160, 384);

		if (dato.isFondoParallax()) {

			fondoParallax.setColor(1, 0, 0, 1);

		} else {

			fondoParallax.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		}

		zoomCamara = new Label("Zoom: ", recurso.get("uis/general/uiskin.json", Skin.class), "default-font",
				Color.GREEN);

		zoomCamara.setSize(96, 32);

		zoomCamara.setPosition(16, 384);

		zoomCamara.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		zoomCamaraMas = new TextButton("+", recurso.get("uis/general/uiskin.json", Skin.class));

		zoomCamaraMas.setSize(32, 32);

		zoomCamaraMas.setPosition(96, 384);

		zoomCamaraMas.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		zoomCamaraMenos = new TextButton("-", recurso.get("uis/general/uiskin.json", Skin.class));

		zoomCamaraMenos.setSize(32, 32);

		zoomCamaraMenos.setPosition(64, 384);

		zoomCamaraMenos.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		tipoActor = new SelectBox<String>(recurso.get("uis/general/uiskin.json", Skin.class));

		tipoActor.setSize(144, 32);

		tipoActor.setPosition(160, 352);

		tipoActor.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		tipoActor.setItems("Pulpo", "PezAngel", "PezGloboNaranja", "PezGloboAmarillo", "Bomba", "Algas");

		tipo = "com.diamon.personajes." + tipoActor.getSelected();

	}

	public void agregarUI() {

		agregar = false;

		moverEnY = false;

		actualizar = false;

		tipo = "com.diamon.personajes." + tipoActor.getSelected();

		moverEsenario.setColor(1, 0, 0, 1);

		moverEsenarioEnY.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		actualizarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		if (dato.isFondoScroll()) {

			fondoScroll.setColor(1, 0, 0, 1);

		} else {

			fondoScroll.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		}

		if (dato.isFondoParallax()) {

			fondoParallax.setColor(1, 0, 0, 1);

		} else {

			fondoParallax.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		}

		nivel.addActor(borrarActores);

		nivel.addActor(moverEsenario);

		nivel.addActor(moverEsenarioEnY);

		nivel.addActor(agreagarActores);

		nivel.addActor(zoomCamara);

		nivel.addActor(zoomCamaraMenos);

		nivel.addActor(zoomCamaraMas);

		nivel.addActor(mundo);

		nivel.addActor(fondoScroll);

		nivel.addActor(fondoParallax);

		nivel.addActor(tipoActor);

		nivel.addActor(numeroNivel);

		nivel.addActor(actualizarActores);

		nivel.addActor(derecha);

		nivel.addActor(izquierda);

	}

	public void eliminarUI() {

		agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);

		borrarActores.remove();
		moverEsenario.remove();
		moverEsenarioEnY.remove();
		agreagarActores.remove();
		zoomCamara.remove();
		zoomCamaraMas.remove();
		zoomCamaraMenos.remove();
		tipoActor.remove();
		numeroNivel.remove();
		mundo.remove();
		fondoScroll.remove();
		fondoParallax.remove();
		derecha.remove();
		izquierda.remove();

		actualizarActores.remove();

		agregar = false;

		actualizar = false;

	}

	public boolean isTerminar() {
		return terminar;
	}

	public void setTerminar(boolean terminar) {
		this.terminar = terminar;

	}

	public boolean ratonDeslizando(InputEvent ev) {

		return true;

	}

	public boolean ratonMoviendo(InputEvent ev, float x, float y) {

		return true;

	}

	public boolean ratonClick(InputEvent ev) {

		return true;
	}

	public boolean ratonPresionado(InputEvent ev) {

		return true;

	}

	public boolean ratonLevantado(InputEvent ev) {

		return true;
	}

	public boolean teclaPresionada(InputEvent ev, int codigoTecla) {

		switch (codigoTecla) {

		case Keys.RIGHT:

			pD = true;

			break;

		case Keys.LEFT:

			pI = true;

			break;

		case Keys.VOLUME_UP:

			pD = true;

			break;

		case Keys.VOLUME_DOWN:

			pI = true;

			break;

		}

		return true;
	}

	public boolean teclaLevantada(InputEvent ev, int codigoTecla) {

		switch (codigoTecla) {

		case Keys.RIGHT:

			for (int i = 0; i < personajes.size; i++) {

				if ((personajes.get(i) instanceof Fondo)) {

					personajes.get(i)
							.setPosition((camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);

				}

			}

			pD = false;

			break;

		case Keys.LEFT:

			for (int i = 0; i < personajes.size; i++) {

				if ((personajes.get(i) instanceof Fondo)) {

					personajes.get(i)
							.setPosition((camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);

				}

			}

			pI = false;

			break;

		case Keys.VOLUME_UP:

			for (int i = 0; i < personajes.size; i++) {

				if ((personajes.get(i) instanceof Fondo)) {

					personajes.get(i)
							.setPosition((camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);

				}

			}

			pD = false;

			break;

		case Keys.VOLUME_DOWN:

			for (int i = 0; i < personajes.size; i++) {

				if ((personajes.get(i) instanceof Fondo)) {

					personajes.get(i)
							.setPosition((camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);

				}

			}

			pI = false;

			break;

		}

		return true;
	}

	public boolean teclaTipo(InputEvent ev, char caracter) {

		return true;

	}

	public void toqueDeslizando(InputEvent ev, float x, float y, int puntero) {

		if (!terminar) {

			float y1 = Juego.ALTO_PANTALLA - y;

			if (agregar) {

				personajes.get(personajes.size - 1).setPosition(
						x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
						y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));

			}

			if (!agregar) {

				if (!moverEnY) {

					camara.unproject(corriendo.set(x, 0, 0));

					if (!(detras.x == -1 && detras.y == -1 && detras.z == -1)) {

						for (int i = 0; i < personajes.size; i++) {

							if ((personajes.get(i) instanceof Fondo)) {

								personajes.get(i).setPosition(
										(camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);

							}

						}

						camara.unproject(delta.set(detras.x, 0, 0));

						delta.sub(corriendo);

						camara.position.add(delta.x, 0, 0);
					}
					detras.set(x, 0, 0);

				} else {

					for (int i = 0; i < personajes.size; i++) {

						if ((personajes.get(i) instanceof Fondo)) {

							personajes.get(i).setPosition(
									(camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);

						}

					}

					camara.unproject(corriendo.set(x, y1, 0));

					if (!(detras.x == -1 && detras.y == -1 && detras.z == -1)) {

						camara.unproject(delta.set(detras.x, detras.y, 0));

						delta.sub(corriendo);

						camara.position.add(delta.x, delta.y, 0);
					}
					detras.set(x, y1, 0);

				}

			}

		}

	}

	public boolean toqueLevantado(InputEvent ev, float x, float y, int puntero, int boton) {

		if (!terminar) {

			for (int i = 0; i < personajes.size; i++) {

				if ((personajes.get(i) instanceof Fondo)) {

					personajes.get(i)
							.setPosition((camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);

				}

			}

			detras.set(-1, -1, -1);
		}

		if (agregar) {

			if (toque) {

				agregarActor(x, y);

			}

			toque = true;

		} else

		{

			toque = false;

		}
		return true;

	}

	public boolean toquePresionado(InputEvent ev, float x, float y, int puntero, int boton) {

		if (!terminar) {

			if (actualizar) {

				float ancho = 0, alto = 0;

				String numeroNvel = "Nivel " + datosNiveles.getNumeroNivel();

				for (int i = 0; i < personajes.size; i++) {

					if (cursor.getBoundingRectangle().overlaps(personajes.get(i).getBoundingRectangle())) {

						if (!(personajes.get(i) instanceof Fondo) && !(personajes.get(i) instanceof Jugador)
								&& !(personajes.get(i) instanceof Terreno)) {

							if (tipo.contentEquals(personajes.get(i).getClass().getName().toString())) {

								ancho = personajes.get(i).getWidth();

								alto = personajes.get(i).getHeight();

								personajes.removeIndex(i);

							}

						}

					}

				}

				for (int i = 0; i < datosNiveles.getTamanoArray(numeroNvel).size; i++) {

					Rectangle r = new Rectangle(datosNiveles.getTamanoArray(numeroNvel).get(i).x,
							datosNiveles.getTamanoArray(numeroNvel).get(i).y, ancho, alto);

					if (cursor.getBoundingRectangle().overlaps(r)) {

						datosNiveles.eliminarActor(numeroNvel, tipo, i);

					}

				}

			}

			if (agregar) {

				agregarActorTemporal(x, y);

				personajes.get(personajes.size - 1).setPosition(
						x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
						y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));

			}

		}

		return true;

	}

	private void eventos() {

		actualizarActores.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				if (agregar) {

					personajes.removeIndex(personajes.size - 1);

				}

				agregar = false;

				actualizar = true;

				actualizarActores.setColor(1, 0, 0, 1);

				agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);

			}
		});

		borrarActores.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				String numeroNvel = "Nivel " + datosNiveles.getNumeroNivel();

				datosNiveles.eliminarActores(numeroNvel);

				configuracion.escribirDatos(dato);

				informacionNiveles.escribirDatos(datosNiveles);

				if (agregar) {

					personajes.removeIndex(personajes.size - 1);

				}

				agregar = false;

				agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);

				for (int i = 0; i < personajes.size; i++) {

					if (!(personajes.get(i) instanceof Fondo) && !(personajes.get(i) instanceof Jugador)
							&& !(personajes.get(i) instanceof Terreno)) {

						personajes.removeIndex(i);

					}
				}

				actualizar = false;

				actualizarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);

			}
		});

		numeroNivel.addListener(new ChangeListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void changed(ChangeEvent event, Actor actor) {

				datosNiveles.setNumeroNivel(((SelectBox<String>) actor).getSelectedIndex() + 1);

				for (int i = 0; i < personajes.size; i++) {

					if ((personajes.get(i) instanceof Jugador)) {

						Jugador j = (Jugador) personajes.get(i);

						j.setTerminarNivel(true);

					}
				}

			}
		});

		tipoActor.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				if (agregar) {

					personajes.removeIndex(personajes.size - 1);

				}

				agregar = false;

				agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);

				super.clicked(event, x, y);
			}
		});

		derecha.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				if (agregar) {

					personajes.removeIndex(personajes.size - 1);

				}

				agregar = false;

				agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);

				super.clicked(event, x, y);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				pD = true;

				return super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

				for (int i = 0; i < personajes.size; i++) {

					if ((personajes.get(i) instanceof Fondo)) {

						personajes.get(i).setPosition(
								(camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);

					}

				}
				pD = false;

				super.touchUp(event, x, y, pointer, button);
			}

		});

		izquierda.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);

				if (agregar) {

					personajes.removeIndex(personajes.size - 1);

				}

				agregar = false;

				super.clicked(event, x, y);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				pI = true;

				return super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

				for (int i = 0; i < personajes.size; i++) {

					if ((personajes.get(i) instanceof Fondo)) {

						personajes.get(i).setPosition(
								(camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);

					}

				}
				pI = false;

				super.touchUp(event, x, y, pointer, button);
			}
		});

		numeroNivel.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				if (agregar) {

					personajes.removeIndex(personajes.size - 1);

				}

				agregar = false;

				agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);

				moverEsenario.setColor(1, 0, 0, 1);

				moverEsenarioEnY.setColor(1.0F, 1.0F, 1.0F, 0.7F);

				moverEnY = false;

				super.clicked(event, x, y);
			}
		});

		tipoActor.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {

				@SuppressWarnings("unchecked")
				String nombre = ((SelectBox<String>) actor).getItems()
						.get(((SelectBox<String>) actor).getSelectedIndex());

				tipo = "com.diamon.personajes." + nombre;

			}
		});

		moverEsenario.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				if (agregar) {

					personajes.removeIndex(personajes.size - 1);

				}

				agregar = false;

				moverEnY = false;

				moverEsenario.setColor(1, 0, 0, 1);

				moverEsenarioEnY.setColor(1.0F, 1.0F, 1.0F, 0.7F);

				agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);

				super.clicked(event, x, y);
			}
		});

		moverEsenarioEnY.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				if (agregar) {

					personajes.removeIndex(personajes.size - 1);

				}

				agregar = false;

				moverEnY = true;

				moverEsenario.setColor(1.0F, 1.0F, 1.0F, 0.7F);

				moverEsenarioEnY.setColor(1, 0, 0, 1);

				agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);

				super.clicked(event, x, y);
			}
		});

		agreagarActores.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				agregar = true;

				moverEsenario.setColor(1.0F, 1.0F, 1.0F, 0.7F);

				moverEsenarioEnY.setColor(1.0F, 1.0F, 1.0F, 0.7F);

				agreagarActores.setColor(1, 0, 0, 1);

				actualizar = false;

				actualizarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);

				super.clicked(event, x, y);
			}
		});

		zoomCamaraMas.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				if (!terminar) {

					camara.zoom -= 0.1f;

					if (agregar) {

						personajes.removeIndex(personajes.size - 1);

					}

					agregar = false;

					agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);

				}

				super.clicked(event, x, y);
			}
		});

		zoomCamaraMenos.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				if (!terminar) {

					camara.zoom += 0.1f;

					if (agregar) {

						personajes.removeIndex(personajes.size - 1);

					}

					agregar = false;

					agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);

				}

				super.clicked(event, x, y);
			}
		});

		fondoScroll.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				if (!terminar) {

					if (agregar) {

						personajes.removeIndex(personajes.size - 1);

					}

					agregar = false;

					agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);

					dato.setFondoScroll(true);

					dato.setFondoParallax(false);

					fondoParallax.setColor(1.0F, 1.0F, 1.0F, 0.7F);

					fondoScroll.setColor(1, 0, 0, 1);

				}

				super.clicked(event, x, y);
			}
		});

		fondoParallax.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				if (!terminar) {

					fondoScroll.setColor(1.0F, 1.0F, 1.0F, 0.7F);

					fondoParallax.setColor(1, 0, 0, 1);

					agreagarActores.setColor(1.0F, 1.0F, 1.0F, 0.7F);

					dato.setFondoScroll(false);

					dato.setFondoParallax(true);

					if (agregar) {

						personajes.removeIndex(personajes.size - 1);

					}

					agregar = false;

				}

				super.clicked(event, x, y);
			}
		});

	}

	private void agregarActorTemporal(float x, float y) {

		if (tipo.equals(DatosNiveles.PULPO)) {

			Pulpo actor = new Pulpo(recurso.get("texturas/pulpo.atlas", TextureAtlas.class).getRegions().get(0),
					pantalla, 32, 64, Pulpo.ESTATICO);

			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));

			personajes.add(actor);

		}

		if (tipo.equals(DatosNiveles.PEZ_GLOBO_AMARILLO)) {

			PezGloboAmarillo actor = new PezGloboAmarillo(
					recurso.get("texturas/pezG.atlas", TextureAtlas.class).getRegions().get(0), pantalla, 64, 32,
					PezGloboAmarillo.ESTATICO);

			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));

			personajes.add(actor);

		}

		if (tipo.equals(DatosNiveles.PEZ_GOBO_NARANJA)) {

			PezGloboNaranja actor = new PezGloboNaranja(
					recurso.get("texturas/pezGlobo.atlas", TextureAtlas.class).getRegions().get(0), pantalla, 64, 64,
					PezGloboNaranja.ESTATICO);

			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));

			personajes.add(actor);

		}

		if (tipo.equals(DatosNiveles.PEZ_ANGEL)) {

			PezAngel actor = new PezAngel(recurso.get("texturas/pez1.atlas", TextureAtlas.class).getRegions().get(0),
					pantalla, 64, 32, PezAngel.ESTATICO);

			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));

			personajes.add(actor);

		}

		if (tipo.equals(DatosNiveles.BOMBA)) {

			Bomba actor = new Bomba(recurso.get("texturas/bomba.png", Texture.class), pantalla, 64, 64,
					Bomba.DIANAMICO);

			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));

			personajes.add(actor);

		}

		if (tipo.equals(DatosNiveles.ALGAS)) {

			Algas actor = new Algas(recurso.get("texturas/algas.png", Texture.class), pantalla, 96, 64, Algas.ESTATICO);

			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));

			personajes.add(actor);

		}

	}

	private void agregarActor(float x, float y) {

		Array<Personaje> actores = new Array<Personaje>();

		if (tipo.equals(DatosNiveles.PULPO)) {

			Pulpo actor = new Pulpo(recurso.get("texturas/pulpo.atlas", TextureAtlas.class).getRegions().get(0),
					pantalla, 32, 64, Pulpo.ESTATICO);

			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));

			actores.add(actor);

		}

		if (tipo.equals(DatosNiveles.PEZ_GLOBO_AMARILLO)) {

			PezGloboAmarillo actor = new PezGloboAmarillo(
					recurso.get("texturas/pezG.atlas", TextureAtlas.class).getRegions().get(0), pantalla, 64, 32,
					PezGloboAmarillo.ESTATICO);

			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));

			actores.add(actor);

		}

		if (tipo.equals(DatosNiveles.PEZ_GOBO_NARANJA)) {

			PezGloboNaranja actor = new PezGloboNaranja(
					recurso.get("texturas/pezGlobo.atlas", TextureAtlas.class).getRegions().get(0), pantalla, 64, 64,
					PezGloboNaranja.ESTATICO);

			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));

			actores.add(actor);

		}

		if (tipo.equals(DatosNiveles.PEZ_ANGEL)) {

			PezAngel actor = new PezAngel(recurso.get("texturas/pez1.atlas", TextureAtlas.class).getRegions().get(0),
					pantalla, 64, 32, PezAngel.ESTATICO);

			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));

			actores.add(actor);

		}

		if (tipo.equals(DatosNiveles.BOMBA)) {

			Bomba actor = new Bomba(recurso.get("texturas/bomba.png", Texture.class), pantalla, 64, 64,
					Bomba.DIANAMICO);

			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));

			actores.add(actor);

		}

		if (tipo.equals(DatosNiveles.ALGAS)) {

			Algas actor = new Algas(recurso.get("texturas/algas.png", Texture.class), pantalla, 96, 64, Algas.ESTATICO);

			actor.setPosition(x + (camara.position.x * Juego.UNIDAD_DEL_MUNDO - (Juego.ANCHO_PANTALLA / 2)),
					y + (camara.position.y * Juego.UNIDAD_DEL_MUNDO - (Juego.ALTO_PANTALLA / 2)));

			actores.add(actor);

		}

		agregarActor(actores);

	}

	public void actualizar() {

		if (pD) {

			for (int i = 0; i < personajes.size; i++) {

				if ((personajes.get(i) instanceof Fondo)) {

					personajes.get(i)
							.setPosition((camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);

				}

			}

			camara.position.x += velocidadCamara / Juego.UNIDAD_DEL_MUNDO;

		}

		if (pI) {

			for (int i = 0; i < personajes.size; i++) {

				if ((personajes.get(i) instanceof Fondo)) {

					personajes.get(i)
							.setPosition((camara.position.x * Juego.UNIDAD_DEL_MUNDO - Juego.ANCHO_PANTALLA / 2), 0);

				}

			}

			camara.position.x -= velocidadCamara / Juego.UNIDAD_DEL_MUNDO;

		}

	}

	public void agregarActor(Array<Personaje> actores) {

		String nivel = "Nivel " + datosNiveles.getNumeroNivel();

		datosNiveles.gurdarActores(actores, tipo, nivel);

	}

}
