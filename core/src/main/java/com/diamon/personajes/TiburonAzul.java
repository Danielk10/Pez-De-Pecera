package com.diamon.personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;

public class TiburonAzul extends Personaje {

	boolean muerde = false;

	Animation<TextureRegion> animacion1;
	Animation<TextureRegion> animacion2;

	public TiburonAzul(Texture textura, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
		super(textura, pantalla, ancho, alto, tipoDeCuerpo);
		// TODO Auto-generated constructor stub
	}

	public TiburonAzul(TextureRegion texturaRegion, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
		super(texturaRegion, pantalla, ancho, alto, tipoDeCuerpo);
		// TODO Auto-generated constructor stub
	}

	public TiburonAzul(Array<AtlasRegion> texturaRegion, float tiempoAnimacion, PlayMode modo, Pantalla pantalla,
			float ancho, float alto, int tipoDeCuerpo) {
		super(texturaRegion, tiempoAnimacion, modo, pantalla, ancho, alto, tipoDeCuerpo);

		animacion1 = new Animation<TextureRegion>(tiempoAnimacion,
				new TextureRegion[] { texturaRegion.get(0), texturaRegion.get(1) });

		animacion1.setPlayMode(modo);

		animacion2 = new Animation<TextureRegion>(tiempoAnimacion,
				new TextureRegion[] { texturaRegion.get(2), texturaRegion.get(3),texturaRegion.get(4) });

		animacion2.setPlayMode(modo);

		animacion = animacion1;

	}

	@Override
	public void actualizar(float delta) {

		if (x <= camara.position.x + Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO) {

			super.actualizar(delta);

			x += 1 / Juego.DELTA_A_PIXEL * delta / Juego.UNIDAD_DEL_MUNDO;

		}

		if (x <= camara.position.x - (Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO + getWidth())) {

			remover = true;

		}

		if (muerde) {

			animacion = animacion2;

			muerde = false;

		} else {

			animacion = animacion1;

		}

	}

	@Override
	public void colision(Personaje personaje) {

		if (personaje instanceof Jugador) {

			muerde = true;
		}

	}

}
