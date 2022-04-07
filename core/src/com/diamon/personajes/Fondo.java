package com.diamon.personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;

public class Fondo extends Personaje {

	public Fondo(Texture textura, Pantalla pantalla) {
		super(textura, pantalla);
		// TODO Auto-generated constructor stub
	}

	public Fondo(TextureRegion texturaRegion, Pantalla pantalla) {
		super(texturaRegion, pantalla);
		// TODO Auto-generated constructor stub
	}

	public Fondo(Array<AtlasRegion> texturaRegion, float tiempoAnimacion, PlayMode modo, Pantalla pantalla) {
		super(texturaRegion, tiempoAnimacion, modo, pantalla);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void colision(Personaje personaje) {
		// TODO Auto-generated method stub

	}

}
