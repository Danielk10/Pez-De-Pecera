package com.diamon.personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
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

	private float spawnX = -1;
	private float spawnY = -1;
	private float rangoPatrulla = 10.0f;
	private int direccion = -1;
	private float tiempoAturdido = 0f;
	private float tiempoMuerde = 0f;
	private float tiempoPatrulla = 0f;
	private float vx = 0f;
	private float vy = 0f;

	@Override
	public void actualizar(float delta) {
		super.actualizar(delta);
		if (spawnX < 0) {
			spawnX = x;
			spawnY = y;
		}

		if (tiempoAturdido > 0) {
			tiempoAturdido -= delta;
			// Aturdido: retrocede levemente y se balancea
			vx *= Math.pow(0.85, delta * 60);
			vy *= Math.pow(0.85, delta * 60);
			x += vx * delta;
			y += vy * delta + com.badlogic.gdx.math.MathUtils.sin(tiempoAturdido * 4f) * 0.02f;
			setX(x);
			setY(y);
			return;
		}

		if (tiempoMuerde > 0) {
			tiempoMuerde -= delta;
			if (tiempoMuerde <= 0) {
				muerde = false;
			}
		}

		tiempoPatrulla += delta;

		// 1. Verificar si hay una Ballena gigante cerca (temor natural del tiburón)
		boolean huyendoDeBallena = false;
		if (personajes != null) {
			for (Personaje p : personajes) {
				if (p instanceof Ballena) {
					float dX = x - p.getX();
					float dY = y - p.getY();
					float distBallena = (float) Math.hypot(dX, dY);
					if (distBallena < 10.0f) {
						huyendoDeBallena = true;
						float norm = Math.max(0.1f, distBallena);
						float targetVx = (dX / norm) * 3.8f;
						float targetVy = (dY / norm) * 2.2f;
						vx = com.badlogic.gdx.math.MathUtils.lerp(vx, targetVx, 4.0f * delta);
						vy = com.badlogic.gdx.math.MathUtils.lerp(vy, targetVy, 4.0f * delta);
						break;
					}
				}
			}
		}

		// 2. Si no huye, buscar y cazar al jugador o patrullar
		if (!huyendoDeBallena) {
			boolean cazando = false;
			if (personajes != null) {
				for (Personaje p : personajes) {
					if (p instanceof Jugador && ((Jugador) p).isVivo()) {
						float distX = p.getX() - x;
						float distY = p.getY() - y;
						float dist = (float) Math.hypot(distX, distY);
						if (dist < 9.5f) {
							cazando = true;
							float norm = Math.max(0.1f, dist);
							float targetVx = (distX / norm) * 4.2f;
							float targetVy = (distY / norm) * 3.0f;
							vx = com.badlogic.gdx.math.MathUtils.lerp(vx, targetVx, 5.0f * delta);
							vy = com.badlogic.gdx.math.MathUtils.lerp(vy, targetVy, 5.0f * delta);

							if (dist < 2.2f) {
								muerde = true;
								tiempoMuerde = 0.8f;
							}
							break;
						}
					}
				}
			}

			if (!cazando) {
				// Nado patrullaje orgánico con vaivén senoidal
				if (Math.abs(x - spawnX) > rangoPatrulla) {
					direccion = (x > spawnX) ? -1 : 1;
				}
				float targetVx = direccion * 2.5f;
				float targetVy = com.badlogic.gdx.math.MathUtils.sin(tiempoPatrulla * 1.6f) * 1.1f;
				vx = com.badlogic.gdx.math.MathUtils.lerp(vx, targetVx, 3.2f * delta);
				vy = com.badlogic.gdx.math.MathUtils.lerp(vy, targetVy, 3.2f * delta);
			}
		}

		x += vx * delta;
		y += vy * delta;
		setX(x);
		setY(y);

		// Orientación e inclinación 360° fluida (la textura mira a la derecha por defecto)
		orientarHaciaDireccion(vx, vy, true, delta);

		float rapidezTiburon = (float) Math.hypot(vx, vy);
		float factorAnim = isAturdido() ? 0.2f : (muerde ? 1.9f : com.badlogic.gdx.math.MathUtils.clamp(rapidezTiburon * 0.45f, 0.7f, 1.8f));
		setMultiplicadorVelocidadAnimacion(factorAnim);

		if (cuerpo != null) {
			cuerpo.setTransform(x + getWidth() / 2f, y + getHeight() / 2f, getRotation() * com.badlogic.gdx.math.MathUtils.degreesToRadians);
		}

		if (muerde) {
			animacion = animacion2;
		} else {
			animacion = animacion1;
		}
	}

	public void aturdir(float duracion) {
		this.tiempoAturdido = duracion;
		this.muerde = false;
		activarHitFlash(duracion, new com.badlogic.gdx.graphics.Color(0.5f, 0.8f, 1.0f, 1.0f));
	}

	public boolean isAturdido() {
		return tiempoAturdido > 0;
	}

	@Override
	public void colision(Personaje personaje) {
		if (personaje instanceof Jugador && !isAturdido()) {
			muerde = true;
			tiempoMuerde = 0.8f;
			((Jugador) personaje).recibirDanio(2);
		}
	}

}
