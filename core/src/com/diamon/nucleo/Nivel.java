package com.diamon.nucleo;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.diamon.datos.Configuraciones;
import com.diamon.datos.Dato;
import com.diamon.personajes.Fondo;
import com.diamon.personajes.Jugador;

public abstract class Nivel {

	protected Pantalla pantalla;

	protected Jugador jugador;

	protected Juego juego;

	protected Array<Personaje> personajes;

	protected AssetManager recurso;

	protected Stage nivel;

	protected boolean moverCamara;

	protected OrthographicCamera camara;

	protected ShapeRenderer pincelPrueba;

	protected SpriteBatch pincel;

	protected TiledMap mapa;

	protected Dato dato;

	protected Configuraciones configuracion;

	protected Fondo[] fondo;

	protected boolean intro;

	public Nivel(Pantalla pantalla, Jugador jugador) {

		this.pantalla = pantalla;

		this.jugador = jugador;

		juego = pantalla.juego;

		personajes = pantalla.personajes;

		recurso = pantalla.recurso;

		nivel = pantalla.nivel;

		camara = pantalla.camara;

		pincelPrueba = pantalla.pincelPrueba;

		pincel = pantalla.pincel;

		moverCamara = false;

		intro = false;

		mapa = new TiledMap();

		configuracion = pantalla.configuracion;

		dato = pantalla.dato;

		fondo = new Fondo[2];

		iniciar();
	}

	protected abstract void iniciar();

	public abstract void actualizar(float delta);

	public abstract void dibujar(Batch pincel, float delta);

	public abstract void guardarDatos();

	public abstract void liberarRecursos();

	public void setMoverCamara(boolean moverCamara) {

		this.moverCamara = moverCamara;
	}

	public boolean isIntro() {
		return intro;
	}

	public void setIntro(boolean intro) {
		this.intro = intro;
	}

}
