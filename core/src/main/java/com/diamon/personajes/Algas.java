package com.diamon.personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;

public class Algas extends Personaje {

	public Algas(Array<AtlasRegion> texturaRegion, float tiempoAnimacion, PlayMode modo, Pantalla pantalla, float ancho,
			float alto, int tipoDeCuerpo) {
		super(texturaRegion, tiempoAnimacion, modo, pantalla, ancho, alto, tipoDeCuerpo);
		// TODO Auto-generated constructor stub
	}

	public Algas(Texture textura, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
		super(textura, pantalla, ancho, alto, tipoDeCuerpo);
		// TODO Auto-generated constructor stub
	}

	public Algas(TextureRegion texturaRegion, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
		super(texturaRegion, pantalla, ancho, alto, tipoDeCuerpo);
		// TODO Auto-generated constructor stub
	}
	
	

	private float tiempoSway = 0f;
	private float excitacionSway = 0f;

	@Override
	public void actualizar(float delta) {
		super.actualizar(delta);

		tiempoSway += delta;
		if (excitacionSway > 0) {
			excitacionSway = Math.max(0f, excitacionSway - delta * 3.0f);
		}

		// Pivotar desde la base del alga (anclada a la arena o roca)
		setOrigin(getWidth() / 2f, 0f);

		// Ondulación armónica simulando corriente marina
		float anguloBase = com.badlogic.gdx.math.MathUtils.sin(tiempoSway * 1.6f + x * 2.5f) * 6.5f;
		float extraSway = com.badlogic.gdx.math.MathUtils.sin(tiempoSway * 8.0f) * excitacionSway;
		setRotation(anguloBase + extraSway);
	}

	@Override
	public void colision(Personaje personaje) {
		if (personaje instanceof Jugador) {
			// El roce del pez agita el alga
			excitacionSway = 14.0f;
		}
	}

}
