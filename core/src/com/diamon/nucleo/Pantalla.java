package com.diamon.nucleo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.diamon.datos.Configuraciones;
import com.diamon.datos.Dato;
import com.diamon.pez.publicidad.Publicidad;

import box2dLight.RayHandler;

public abstract class Pantalla implements Screen {

	protected final Juego juego;

	protected Array<Personaje> personajes;

	protected AssetManager recurso;

	protected Stage nivel;

	protected Stage nivelMenu;

	protected OrthographicCamera camara;

	protected SpriteBatch pincel;

	protected ShapeRenderer pincelPrueba;

	protected Dato dato;

	protected Configuraciones configuracion;

	protected Publicidad publicidad;

	protected World mundoVirtual;

	protected Array<Body> cuerpos = new Array<Body>();

	public World getMundoVirtual() {
		return mundoVirtual;
	}

	public void setMundoVirtual(World mundoVirtual) {
		this.mundoVirtual = mundoVirtual;
	}

	public Array<Body> getCuerpos() {
		return cuerpos;
	}

	public void setCuerpos(Array<Body> cuerpos) {
		this.cuerpos = cuerpos;
	}

	protected Box2DDebugRenderer debugRenderer;

	protected RayHandler luz;

	public Pantalla(Juego juego) {

		mundoVirtual = juego.mundoVirtual;

		luz = new RayHandler(mundoVirtual);

		this.juego = juego;

		publicidad = juego.publicidad;

		nivelMenu = juego.nivelMenu;

		nivel = new Stage(new StretchViewport(Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA));

		((OrthographicCamera) nivel.getCamera()).setToOrtho(false, Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA);

		camara = new OrthographicCamera();

		camara.setToOrtho(false, Juego.ANCHO_PANTALLA / Juego.UNIDAD_DEL_MUNDO,
				Juego.ALTO_PANTALLA / Juego.UNIDAD_DEL_MUNDO);

		camara.update();

		pincel = new SpriteBatch();

		pincelPrueba = new ShapeRenderer();

		debugRenderer = new Box2DDebugRenderer();

		personajes = new Array<Personaje>();

		recurso = juego.recurso;

		configuracion = juego.configuracion;

		dato = juego.dato;

	}

	public abstract void mostrar();

	public abstract void eventos();

	public abstract void colisiones();

	public abstract void actualizar(float delta);

	public abstract void dibujar(Batch pincel, float delta);

	public abstract void guardarDatos();

	public abstract void liberarRecursos();

	@Override
	public void show() {

		Gdx.input.setInputProcessor(nivel);

		mostrar();

		eventos();
	}

	@Override
	public void render(float delta) {

		ScreenUtils.clear(0.0F, 0.0F, 1.0F, 1.0F, true);

		pincel.setProjectionMatrix(camara.combined);

		pincelPrueba.setProjectionMatrix(camara.combined);

		pincelPrueba.setColor(Color.RED);

		colisiones();

		actualizar(delta);

		camara.update();

		dibujar(pincel, delta);

		nivel.draw();

		nivel.act();

		debugRenderer.render(mundoVirtual, camara.combined);
	}

	@Override
	public void resize(int ancho, int alto) {

		nivel.getViewport().update(ancho, alto);

	}

	@Override
	public void pause() {

		guardarDatos();

		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void resume() {

		Gdx.input.setInputProcessor(nivel);

	}

	@Override
	public void hide() {

		guardarDatos();

		personajes.clear();

		liberarRecursos();

		pincel.dispose();

		pincelPrueba.dispose();

		Gdx.input.setInputProcessor(null);

		nivel.dispose();

		luz.dispose();

		mundoVirtual.getBodies(cuerpos);

		if (cuerpos.size > 0) {

			for (Body cuerpo : cuerpos) {

				mundoVirtual.destroyBody(cuerpo);

			}

		}

	}

	@Override
	public void dispose() {

		guardarDatos();

		personajes.clear();

		liberarRecursos();

		pincel.dispose();

		pincelPrueba.dispose();

		Gdx.input.setInputProcessor(null);

		nivel.dispose();

		mundoVirtual.getBodies(cuerpos);

		if (cuerpos.size > 0) {

			for (Body cuerpo : cuerpos) {

				mundoVirtual.destroyBody(cuerpo);

			}

		}

		luz.dispose();

		debugRenderer.dispose();

	}

}
