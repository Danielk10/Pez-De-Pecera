package com.diamon.personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;

public class Pulpo extends Personaje {

	public static final int VELOCIDAD_PEZ = 1;

	public Pulpo(Array<AtlasRegion> texturaRegion, float tiempoAnimacion, PlayMode modo, Pantalla pantalla, float ancho,
			float alto, int tipoDeCuerpo) {
		super(texturaRegion, tiempoAnimacion, modo, pantalla, ancho, alto, tipoDeCuerpo);
		// TODO Auto-generated constructor stub
	}

	public Pulpo(Texture textura, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
		super(textura, pantalla, ancho, alto, tipoDeCuerpo);
		// TODO Auto-generated constructor stub
	}

	public Pulpo(TextureRegion texturaRegion, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
		super(texturaRegion, pantalla, ancho, alto, tipoDeCuerpo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actualizar(float delta) {

		if (x <= camara.position.x + Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO) {

			super.actualizar(delta);

			y += Pulpo.VELOCIDAD_PEZ / Juego.DELTA_A_PIXEL * delta / Juego.UNIDAD_DEL_MUNDO;

		}

		if (y >= camara.position.y + (Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO + getHeight())) {

			remover = true;

		}

		if (x <= camara.position.x - (Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO + getWidth())) {

			remover = true;

		}

	}

	@Override
	public void colision(Personaje personaje) {
		// TODO Auto-generated method stub

	}

}
