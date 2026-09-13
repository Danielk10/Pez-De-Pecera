package com.diamon.personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;

public class PezGloboAmarillo extends Personaje {

	public static final int VELOCIDAD_PEZ = 1;

	public PezGloboAmarillo(Array<AtlasRegion> texturaRegion, float tiempoAnimacion, PlayMode modo, Pantalla pantalla,
			float ancho, float alto, int tipoDeCuerpo) {
		super(texturaRegion, tiempoAnimacion, modo, pantalla, ancho, alto, tipoDeCuerpo);
		// TODO Auto-generated constructor stub
	}

	public PezGloboAmarillo(Texture textura, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
		super(textura, pantalla, ancho, alto, tipoDeCuerpo);
		// TODO Auto-generated constructor stub
	}

	public PezGloboAmarillo(TextureRegion texturaRegion, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
		super(texturaRegion, pantalla, ancho, alto, tipoDeCuerpo);
		// TODO Auto-generated constructor stub
	}

	private float spawnX = -1;
	private float rangoPatrulla = 6.0f;
	private int direccion = -1;

	@Override
	public void actualizar(float delta) {
		super.actualizar(delta);
		if (spawnX < 0) {
			spawnX = x;
		}

		x += direccion * 2.8f * delta;
		if (Math.abs(x - spawnX) > rangoPatrulla) {
			direccion = -direccion;
			setFlip(direccion > 0, false);
		}
		setX(x);
	}

	@Override
	public void colision(Personaje personaje) {
		if (personaje instanceof Jugador) {
			((Jugador) personaje).recibirDanio(1);
		}
	}

}
