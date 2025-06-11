package com.diamon.personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;

public class JefeTres extends Personaje {

	private Jugador jugador;

	public JefeTres(Array<AtlasRegion> texturaRegion, float tiempoAnimacion, PlayMode modo, Pantalla pantalla,
			float ancho, float alto, int tipoDeCuerpo) {
		super(texturaRegion, tiempoAnimacion, modo, pantalla, ancho, alto, tipoDeCuerpo);
		// TODO Auto-generated constructor stub

		// El jefe es un cuerpo dinámico pero no debe ser afectado por la gravedad del mundo,
		// ya que tendrá su propio patrón de movimiento (ej. volar).
		if (this.cuerpo != null) {
			this.cuerpo.setGravityScale(0.0f);
		}

		obtenerJugador();
	}

	public JefeTres(Texture textura, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
		super(textura, pantalla, ancho, alto, tipoDeCuerpo);
		// TODO Auto-generated constructor stub

		// El jefe es un cuerpo dinámico pero no debe ser afectado por la gravedad del mundo,
		// ya que tendrá su propio patrón de movimiento (ej. volar).
		if (this.cuerpo != null) {
			this.cuerpo.setGravityScale(0.0f);
		}

		obtenerJugador();
	}

	public JefeTres(TextureRegion texturaRegion, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
		super(texturaRegion, pantalla, ancho, alto, tipoDeCuerpo);
		// TODO Auto-generated constructor stub

		// El jefe es un cuerpo dinámico pero no debe ser afectado por la gravedad del mundo,
		// ya que tendrá su propio patrón de movimiento (ej. volar).
		if (this.cuerpo != null) {
			this.cuerpo.setGravityScale(0.0f);
		}

		obtenerJugador();
	}

	private void obtenerJugador() {

		for (int i = 0; i < personajes.size; i++) {

			if (personajes.get(i) instanceof Jugador) {

				jugador = (Jugador) personajes.get(i);

			}

		}

	}

	@Override
	public void actualizar(float delta) {

		if (x <= camara.position.x + Juego.ANCHO_PANTALLA / 2/Juego.UNIDAD_DEL_MUNDO) {

			super.actualizar(delta);

		}

		if (x <= camara.position.x - (Juego.ANCHO_PANTALLA / 2/ Juego.UNIDAD_DEL_MUNDO + getWidth()) ) {

			remover = true;

		}

	}

	@Override
	public Rectangle getBoundingRectangle() {

		Rectangle r = super.getBoundingRectangle();

		return r;
	}

	@Override
	public void colision(Personaje actor) {

	}

}
