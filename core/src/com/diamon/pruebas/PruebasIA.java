package com.diamon.pruebas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.CpuSpriteBatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PruebasIA extends Game implements InputProcessor {

	public static final float ANCHO_PANTALLA = 6.4f;

	public static final float ALTO_PANTALLA = 4.8f;

	private OrthographicCamera camara;

	private Viewport vista;

	private Batch pincel;

	private Pixmap bitmap;

	private Actor2D jugador;

	private Texture textura;

	private AssetManager recurso;

	private Array<ParticleEffect> particulas;

	private Array<Sprite> actores;

	private BitmapFont letra;

	private ShapeRenderer pincelPrueba;

	@Override
	public void create() {

		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

		Gdx.graphics.setCursor(Gdx.graphics.newCursor(pixmap, 0, 0));

		pincelPrueba = new ShapeRenderer();

		particulas = new Array<ParticleEffect>();

		actores = new Array<Sprite>();

		recurso = new AssetManager();

		letra = new BitmapFont();

		letra.getData().scaleX = 0.08f;

		letra.getData().scaleY = 0.08f;

		recurso.load("particulas/Particle Park Flame.p", ParticleEffect.class);

		recurso.finishLoading();

		ParticleEffect efectoParticula = recurso.get("particulas/Particle Park Flame.p", ParticleEffect.class);

		efectoParticula.setPosition(2, 2);

		for (ParticleEmitter emisor : efectoParticula.getEmitters()) {

			emisor.scaleMotion(0.03f);
			emisor.scaleSize(0.03f, 0.03f);

		}

		// efectoParticula.scaleEffect(0.003f, 0.003f, 0.003f);

		efectoParticula.scaleEffect(0.01f, 0.01f);

		particulas.add(efectoParticula);

		pincel = new CpuSpriteBatch();

		camara = new OrthographicCamera(ANCHO_PANTALLA, ALTO_PANTALLA);

		camara.setToOrtho(false, ANCHO_PANTALLA, ALTO_PANTALLA);

		vista = new StretchViewport(ANCHO_PANTALLA, ALTO_PANTALLA, camara);

		bitmap = new Pixmap(128, 128, Format.RGBA8888);

		textura = new Texture(128, 128, Format.RGBA8888);

		bitmap.setColor(1.0f, 1.0f, 1.0f, 1.0f);

		bitmap.fillRectangle(0, 0, 128, 128);

		textura.draw(bitmap, 0, 0);

		jugador = new Actor2D(textura);

		jugador.setPosition(0, 0);

		jugador.setSize(0.2f, 0.2f);

		jugador.setNombre("Jugador");

		camara.update();

		Gdx.input.setInputProcessor(this);

		actores.add(jugador);

		for (int i = 0; i < 100; i++) {

			Actor2D actor = new Actor2D(textura);

			actor.setPosition(MathUtils.random(6.4f), MathUtils.random(4.8f));

			actor.setSize(0.08f, 0.08f);

			actor.setColor(MathUtils.random(1.0f), MathUtils.random(1.0f), MathUtils.random(1.0f), 1f);

			actores.add(actor);

		}

	}

	@Override
	public void render() {

		ScreenUtils.clear(0.0F, 0.0F, 1.0F, 1.0f, true);

		pincel.setProjectionMatrix(camara.combined);

		camara.update();

		pincelPrueba.setProjectionMatrix(camara.combined);

		pincelPrueba.setColor(Color.RED);

		colisiones();

		actualizar(Gdx.graphics.getDeltaTime());

		dibujar(pincel, Gdx.graphics.getDeltaTime());

	}

	public void dibujar(Batch pincel, float delta) {

		pincel.begin();

		for (Sprite actor : actores) {

			actor.setColor(MathUtils.random(1.0f), MathUtils.random(1.0f), MathUtils.random(1.0f), 1f);

			((Actor2D) actor).draw(pincel);

		}

		for (ParticleEffect particula : particulas) {

			particula.draw(pincel, delta);

		}

		letra.draw(pincel, "" + Gdx.graphics.getFramesPerSecond(), 1, 1);

		pincel.end();

		prueba();

	}

	public void actualizar(float delta) {

		for (Sprite actor : actores) {

			if (!((Actor2D) actor).getNombre().equals("Jugador")) {

				actor.setPosition(MathUtils.random(6.4f), MathUtils.random(4.8f));

			}

			((Actor2D) actor).actualizar(delta);

		}

		for (ParticleEffect particula : particulas) {

			particula.update(Gdx.graphics.getDeltaTime());

		}

	}

	public void colisiones() {

		for (int i = 0; i < actores.size; i++) {

			Sprite actor1 = actores.get(i);

			Rectangle rectangulo1 = actor1.getBoundingRectangle();

			for (int j = i + 1; j < actores.size; j++) {

				Sprite actor2 = actores.get(j);

				Rectangle rectangulo2 = actor2.getBoundingRectangle();

				if (rectangulo1.overlaps(rectangulo2)) {

					((Actor2D) actor1).colision(actor2);

					((Actor2D) actor2).colision(actor1);

				}
			}

			Sprite actor = actores.get(i);

			if (((Actor2D) actor).isRemover()) {

				if (((Actor2D) actor).getNombre().equals("Actor")) {

					actores.removeIndex(i);

				}

			}
		}

	}

	private void prueba() {

		for (Sprite actor : actores) {

			pincelPrueba.begin(ShapeRenderer.ShapeType.Line);

			pincelPrueba.rect(actor.getX(), actor.getY(), actor.getWidth() / 2, actor.getHeight() / 2, actor.getWidth(),
					actor.getHeight(), 1, 1, actor.getRotation());

			pincelPrueba.end();

		}

	}

	@Override
	public void dispose() {

		bitmap.dispose();

		textura.dispose();

		for (ParticleEffect particula : particulas) {

			particula.dispose();

		}

		particulas.clear();

		pincel.dispose();

		pincelPrueba.dispose();

		Gdx.input.setInputProcessor(null);

		super.dispose();
	}

	@Override
	public void resize(int ancho, int alto) {

		vista.update(ancho, alto);

		super.resize(ancho, alto);
	}

	@Override
	public boolean keyDown(int keycode) {

		return jugador.teclaAbajo(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		Vector3 po3 = new Vector3(screenX, screenY, 0);

		vista.getCamera().unproject(po3);

		jugador.setPosition(po3.x, po3.y);

		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {

		Vector3 po3 = new Vector3(screenX, screenY, 0);

		vista.getCamera().unproject(po3);

		jugador.setPosition(po3.x, po3.y);

		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}

}
