package com.diamon.nucleo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

	public static final int ANCHO_PANTALLA = 640;

	public static final int ALTO_PANTALLA = 480;

	public static final int LARGO_NIVEL = 13440;

	public static final float GRAVEDAD = -10.0F;

	public static final float VELOCIDAD_CAMARA = 1;

	public static final float DELTA_A_PIXEL = 0.0166666666666667F;

	public static final int FPS = 60;

	protected Dato dato;

	protected Configuraciones configuracion;

	private Image[] fondo = new Image[2];

	private float posicionFondoX;

	protected Stage nivelMenu;

	private boolean renderizar;

	protected Publicidad publicidad;

	public Juego(Publicidad publicidad) {
		super();

		this.publicidad = publicidad;

	}

	@Override
	public void create() {

		recurso = new AssetManager();

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

	}

}
