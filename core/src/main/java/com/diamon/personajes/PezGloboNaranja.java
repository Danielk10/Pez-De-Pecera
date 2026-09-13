package com.diamon.personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;

public class PezGloboNaranja extends Personaje {

	public static final int VELOCIDAD_PEZ = 1;

	public PezGloboNaranja(Array<AtlasRegion> texturaRegion, float tiempoAnimacion, PlayMode modo, Pantalla pantalla,
			float ancho, float alto, int tipoDeCuerpo) {
		super(texturaRegion, tiempoAnimacion, modo, pantalla, ancho, alto, tipoDeCuerpo);
		// TODO Auto-generated constructor stub
	}

	public PezGloboNaranja(Texture textura, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
		super(textura, pantalla, ancho, alto, tipoDeCuerpo);
		// TODO Auto-generated constructor stub
	}

	public PezGloboNaranja(TextureRegion texturaRegion, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
		super(texturaRegion, pantalla, ancho, alto, tipoDeCuerpo);
		// TODO Auto-generated constructor stub
	}

	private float spawnY = -1;
	private float rangoPatrulla = 2.0f;
	private int direccionY = 1;

	@Override
	public void actualizar(float delta) {
		super.actualizar(delta);
		if (spawnY < 0) {
			spawnY = y;
		}

		y += direccionY * 1.0f * delta;
		if (Math.abs(y - spawnY) > rangoPatrulla) {
			direccionY = -direccionY;
		}
		setY(y);
	}

	@Override
	public void colision(Personaje personaje) {
		if (personaje instanceof Jugador) {
			((Jugador) personaje).recibirDanio(1);
		}
	}

}
