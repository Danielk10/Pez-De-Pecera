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
	private float empujeEstela = 0f;

	@Override
	public void actualizar(float delta) {
		super.actualizar(delta);

		tiempoSway += delta;
		if (excitacionSway > 0) {
			excitacionSway = Math.max(0f, excitacionSway - delta * 3.5f);
		}
		empujeEstela = com.badlogic.gdx.math.MathUtils.lerp(empujeEstela, 0f, 3.0f * delta);

		// Reacción a estelas de criaturas marinas que pasen cerca
		if (personajes != null) {
			for (int i = 0; i < personajes.size; i++) {
				Personaje p = personajes.get(i);
				if (p instanceof Jugador || p instanceof TiburonAzul || p instanceof Ballena) {
					float dX = p.getX() - x;
					float dY = p.getY() - y;
					float dist = (float) Math.hypot(dX, dY);
					if (dist < 2.4f) {
						excitacionSway = Math.min(20f, excitacionSway + 14.0f * delta);
						empujeEstela = (dX < 0) ? 8.5f : -8.5f;
						break;
					}
				}
			}
		}

		// Pivotar desde la base del alga (anclada a la arena o roca)
		setOrigin(getWidth() / 2f, 0f);

		// Ondulación armónica simulando corriente marina en múltiples frecuencias
		float anguloBase = com.badlogic.gdx.math.MathUtils.sin(tiempoSway * 1.5f + x * 2.2f) * 7.5f;
		float ondaSecundaria = com.badlogic.gdx.math.MathUtils.sin(tiempoSway * 3.1f + x * 4.0f) * 3.0f;
		float extraSway = com.badlogic.gdx.math.MathUtils.sin(tiempoSway * 9.0f) * excitacionSway;
		setRotation(anguloBase + ondaSecundaria + extraSway + empujeEstela);

		// Elasticidad hidrodinámica sutil (respiración del alga)
		float estiramientoY = 1.0f + com.badlogic.gdx.math.MathUtils.sin(tiempoSway * 1.8f + x * 1.5f) * 0.045f;
		setScale(1.0f, estiramientoY);
	}

	@Override
	public void colision(Personaje personaje) {
		if (personaje instanceof Jugador) {
			// El roce del pez agita el alga con fuerza
			excitacionSway = 18.0f;
			empujeEstela = (((Jugador) personaje).getVelocidadX() >= 0) ? 12.0f : -12.0f;
		}
	}

}
