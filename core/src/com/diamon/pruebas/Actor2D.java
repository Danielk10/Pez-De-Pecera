package com.diamon.pruebas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Actor2D extends Sprite {

	private static final float VELOCIDAD = 500 / 100f;

	private static final float GRAVEDAD = -10 / 100f;

	private static final float SALTO = 700 / 100f;

	private Vector2 posicion;

	private Vector2 velocidad;

	private float tiempo;

	private float duracion;

	private Interpolation interpolacion;

	private boolean remover;

	private String nombre;

	private float lugar;

	public Actor2D() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Actor2D(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}

	public Actor2D(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
		super(texture, srcX, srcY, srcWidth, srcHeight);
		// TODO Auto-generated constructor stub
	}

	public Actor2D(Texture texture, int srcWidth, int srcHeight) {
		super(texture, srcWidth, srcHeight);
		// TODO Auto-generated constructor stub
	}

	public Actor2D(Texture texture) {
		super(texture);

		tiempo = 0;

		duracion = 2;

		interpolacion = Interpolation.smooth;

		velocidad = new Vector2();

		posicion = new Vector2();

		remover = false;

		nombre = "Actor";
	}

	public Actor2D(TextureRegion region, int srcX, int srcY, int srcWidth, int srcHeight) {
		super(region, srcX, srcY, srcWidth, srcHeight);
		// TODO Auto-generated constructor stub
	}

	public Actor2D(TextureRegion region) {
		super(region);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setPosition(float x, float y) {

		posicion.x = x;

		posicion.y = y;

		lugar = x;

		super.setPosition(x, y);
	}

	public boolean isRemover() {
		return remover;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setRemover(boolean remover) {
		this.remover = remover;
	}

	public void colision(Sprite actor) {

		remover = true;

	}

	public void actualizar(float delta) {

		if (delta == 0) {

			return;

		}

		if (delta > 0.1f) {

			delta = 0.1f;

		}

		tiempo += delta;

		float x = lugar + interpolacion.apply(tiempo / duracion);

		if (tiempo >= duracion) {

			tiempo = 0;

		}

		if (Gdx.input.isKeyPressed(Keys.UP)) {

			velocidad.y += SALTO;

		} else

		if (Gdx.input.isKeyPressed(Keys.DOWN)) {

			velocidad.y -= VELOCIDAD;

		}

		else {

			// velocidad.y = 0;
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT)) {

			velocidad.x -= VELOCIDAD;

		} else

		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {

			velocidad.x += VELOCIDAD;

		}

		else {

			// velocidad.x = 0;
		}

		velocidad.x = MathUtils.clamp(velocidad.x, -VELOCIDAD, VELOCIDAD);

		velocidad.add(x, GRAVEDAD);

		if (velocidad.y > 0) {

			velocidad.y = 0;

		} else

		{

		}

		if (Math.abs(velocidad.x) < 1) {

			velocidad.x = 0;

		}

		// System.out.println(velocidad.x);

		velocidad.scl(delta);

		 //posicion.add(velocidad);

		velocidad.scl(1 / delta);

		velocidad.x *= 0.1f;

		//posicion.x = x;

		setX(posicion.x);

		setY(posicion.y);

	}

	public boolean teclaAbajo(int codigoTecla) {

		switch (codigoTecla) {

		case Keys.UP:

			velocidad.y += VELOCIDAD;

			break;

		case Keys.DOWN:

			velocidad.y -= VELOCIDAD;

			break;

		case Keys.LEFT:

			velocidad.x -= VELOCIDAD;

			break;

		case Keys.RIGHT:

			velocidad.x += VELOCIDAD;

			break;

		default:

			break;

		}

		return true;
	}
}
