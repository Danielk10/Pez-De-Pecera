package com.diamon.personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;

public class Bomba extends Personaje {

	public Bomba(Array<AtlasRegion> texturaRegion, float tiempoAnimacion, PlayMode modo, Pantalla pantalla, float ancho,
			float alto, int tipoDeCuerpo) {
		super(texturaRegion, tiempoAnimacion, modo, pantalla, ancho, alto, tipoDeCuerpo);
		// TODO Auto-generated constructor stub
	}

	public Bomba(Texture textura, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
		super(textura, pantalla, ancho, alto, tipoDeCuerpo);
		// TODO Auto-generated constructor stub
	}

	public Bomba(TextureRegion texturaRegion, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
		super(texturaRegion, pantalla, ancho, alto, tipoDeCuerpo);
		// TODO Auto-generated constructor stub
	}

	private float spawnY = -1;
	private float tiempoOscilacion = 0f;

	@Override
	public void actualizar(float delta) {
		super.actualizar(delta);
		if (spawnY < 0) {
			spawnY = y;
		}

		tiempoOscilacion += delta * 1.5f;
		y = spawnY + com.badlogic.gdx.math.MathUtils.sin(tiempoOscilacion) * 0.3f;
		setY(y);
	}

	@Override
	public void colision(Personaje personaje) {
		if (personaje instanceof Jugador) {
			((Jugador) personaje).recibirDanio(2);
			remover();
		}
	}

}
