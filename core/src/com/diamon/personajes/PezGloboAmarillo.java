package com.diamon.personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;

public class PezGloboAmarillo extends Personaje {
	
	public static final int VELOCIDAD_PEZ = 1;

	public PezGloboAmarillo(Texture textura, Pantalla pantalla) {
		super(textura, pantalla);
		// TODO Auto-generated constructor stub
	}

	public PezGloboAmarillo(TextureRegion texturaRegion, Pantalla pantalla) {
		super(texturaRegion, pantalla);
		// TODO Auto-generated constructor stub
	}

	public PezGloboAmarillo(Array<AtlasRegion> texturaRegion, float tiempoAnimacion, PlayMode modo, Pantalla pantalla) {
		super(texturaRegion, tiempoAnimacion, modo, pantalla);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void actualizar(float delta) {

		if (x <= camara.position.x + Juego.ANCHO_PANTALLA / 2) {

			super.actualizar(delta);

			x -= PezGloboAmarillo.VELOCIDAD_PEZ / Juego.DELTA_A_PIXEL * delta;

		}

		if (x <= camara.position.x - (Juego.ANCHO_PANTALLA / 2 + getWidth())) {

			remover = true;

		}

	}

	@Override
	public void colision(Personaje personaje) {
		// TODO Auto-generated method stub

	}

}
