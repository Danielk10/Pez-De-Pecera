package com.diamon.nucleo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.diamon.datos.Configuraciones;
import com.diamon.datos.Dato;
import com.diamon.pantallas.PantallaCarga;
import com.diamon.pantallas.PantallaJuego;
import com.diamon.pantallas.PantallaPrecentacion;
import com.diamon.pez.publicidad.Publicidad;

public abstract class Juego extends Game {

	protected AssetManager recurso;

	public static final float ANCHO_PANTALLA = 640f;

	public static final float ALTO_PANTALLA = 480f;

	public static final float UNIDAD_DEL_MUNDO = 100f;

	public static final float LARGO_NIVEL = 13440f;

	public static final float GRAVEDAD = -10.0f;

	public static final float VELOCIDAD_CAMARA = 1f;

	public static final float DELTA_A_PIXEL = 0.0166666666666667f;

	public static final float FPS = 60f;

	protected Dato dato;

	protected Configuraciones configuracion;

	private Image[] fondo;

	private float posicionFondoX;

	protected Stage nivelMenu;

	private boolean renderizar;

	protected Publicidad publicidad;

	protected World mundoVirtual;

	protected static final float STEP_TIME = 1f / 60f;

	protected static final int VELOCITY_ITERATIONS = 6;

	protected static final int POSITION_ITERATIONS = 2;

	private float accumulator = 0;

	public Juego(Publicidad publicidad) {
		super();

		this.publicidad = publicidad;

	}

	@Override
	public void create() {

		Box2D.init();

		mundoVirtual = new World(new Vector2(0, Juego.GRAVEDAD), true);

		recurso = new AssetManager();

		fondo = new Image[2];

		configuracion = new Configuraciones();

		dato = configuracion.leerDatos(Configuraciones.LOCAL);

		if (dato.isLeerDatosAsset()) {

			Configuraciones configuracionInterna = new Configuraciones();

			dato = configuracionInterna.leerDatos(Configuraciones.INTERNO);

			dato.setLeerDatosAsset(false);

			configuracionInterna.escribirDatos(dato);

		}

		posicionFondoX = 0;

		renderizar = false;

		nivelMenu = new Stage(new StretchViewport(Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA));

		((OrthographicCamera) nivelMenu.getCamera()).setToOrtho(false, Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA);

		for (int i = 0; i < fondo.length; i++) {

			fondo[i] = new Image(new Texture(Gdx.files.internal("texturas/fondo4.png")));

			fondo[i].setSize(Juego.ANCHO_PANTALLA + 213, Juego.ALTO_PANTALLA);

			fondo[i].setPosition(0, 0);

			nivelMenu.addActor(fondo[i]);
		}

		Gdx.input.setInputProcessor(nivelMenu);

	}

	@Override
	public void render() {

		float delta = Gdx.graphics.getDeltaTime();

		accumulator += Math.min(delta, 0.25f);

		if (accumulator >= STEP_TIME) {

			accumulator -= STEP_TIME;

			mundoVirtual.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

		}

		// mundoVirtual.step(delta, 8, 6);

		ScreenUtils.clear(0.0F, 0.0F, 1.0F, 1.0f, true);

		super.render();

		if (renderizar) {

			posicionFondoX -= 0.5f / Juego.DELTA_A_PIXEL * Gdx.graphics.getDeltaTime();

			if (posicionFondoX <= -Juego.ANCHO_PANTALLA) {

				posicionFondoX = 0;
			}

			fondo[0].setPosition(posicionFondoX, 0);

			fondo[1].setPosition(posicionFondoX + Juego.ANCHO_PANTALLA, 0);

			nivelMenu.draw();

			nivelMenu.act();

		}

	}

	@Override
	public void resize(int ancho, int alto) {

		super.resize(ancho, alto);

		nivelMenu.getViewport().update(ancho, alto);

	}

	@Override
	public void setScreen(Screen screen) {

		if (screen instanceof PantallaCarga || screen instanceof PantallaJuego
				|| screen instanceof PantallaPrecentacion) {

			nivelMenu.clear();

			renderizar = false;

			super.setScreen(screen);

		} else

		{
			nivelMenu.clear();

			for (int i = 0; i < fondo.length; i++) {

				fondo[i] = new Image(new Texture(Gdx.files.internal("texturas/fondo4.png")));

				fondo[i].setSize(Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA);

				fondo[i].setPosition(0, 0);

				nivelMenu.addActor(fondo[i]);
			}

			super.setScreen(screen);

			Gdx.input.setInputProcessor(null);

			Gdx.input.setInputProcessor(nivelMenu);

			renderizar = true;

		}

	}

	@Override
	public void resume() {

		super.resume();

		if (screen instanceof PantallaCarga || screen instanceof PantallaJuego
				|| screen instanceof PantallaPrecentacion) {

		} else {

			Gdx.input.setInputProcessor(null);

			Gdx.input.setInputProcessor(nivelMenu);

		}

	}

	@Override
	public void dispose() {

		recurso.dispose();

		Gdx.input.setInputProcessor(null);

		nivelMenu.dispose();

		mundoVirtual.dispose();

	}

}
