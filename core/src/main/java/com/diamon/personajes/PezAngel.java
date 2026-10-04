package com.diamon.personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;

public class PezAngel extends Personaje {

	private float spawnX = -1;
	private float spawnY = -1;
	private float tiempoNado = 0f;
	private boolean ayudado = false;
	private int direccionX = -1;

	public PezAngel(Array<AtlasRegion> texturaRegion, float tiempoAnimacion, PlayMode modo, Pantalla pantalla,
			float ancho, float alto, int tipoDeCuerpo) {
		super(filtrarRegionesVivas(texturaRegion), tiempoAnimacion, modo, pantalla, ancho, alto, tipoDeCuerpo);
	}

	private static Array<AtlasRegion> filtrarRegionesVivas(Array<AtlasRegion> regiones) {
		Array<AtlasRegion> vivas = new Array<AtlasRegion>();
		for (AtlasRegion r : regiones) {
			if (!r.name.toLowerCase().contains("dead")) {
				vivas.add(r);
			}
		}
		if (vivas.size == 0 && regiones.size > 0) {
			vivas.add(regiones.get(0));
		}
		return vivas;
	}

	public PezAngel(Texture textura, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
		super(textura, pantalla, ancho, alto, tipoDeCuerpo);
	}

	public PezAngel(TextureRegion texturaRegion, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
		super(texturaRegion, pantalla, ancho, alto, tipoDeCuerpo);
	}

	private float vx = 0f;
	private float vy = 0f;

	@Override
	public void actualizar(float delta) {
		super.actualizar(delta);

		if (spawnX < 0) {
			spawnX = x;
			spawnY = y;
		}

		tiempoNado += delta * 2.2f;

		// 1. Huir si hay un tiburón cerca
		boolean huyendoDeTiburon = false;
		if (personajes != null) {
			for (Personaje p : personajes) {
				if (p instanceof TiburonAzul && !((TiburonAzul) p).isAturdido()) {
					float distX = x - p.getX();
					float distY = y - p.getY();
					float distT = (float) Math.hypot(distX, distY);
					if (distT < 6.5f) {
						huyendoDeTiburon = true;
						float norm = Math.max(0.1f, distT);
						float escapeVx = (distX / norm) * 3.6f;
						float escapeVy = (distY / norm) * 2.2f;
						vx = com.badlogic.gdx.math.MathUtils.lerp(vx, escapeVx, 5.0f * delta);
						vy = com.badlogic.gdx.math.MathUtils.lerp(vy, escapeVy, 5.0f * delta);
						break;
					}
				}
			}
		}

		// 2. Si no huye, nado libre con curiosidad hacia el jugador
		if (!huyendoDeTiburon) {
			boolean siguiendoJugador = false;
			if (personajes != null) {
				for (Personaje p : personajes) {
					if (p instanceof Jugador && ((Jugador) p).isVivo()) {
						float distX = p.getX() - x;
						float distY = p.getY() - y;
						float distJ = (float) Math.hypot(distX, distY);
						if (distJ < 5.0f) {
							siguiendoJugador = true;
							float norm = Math.max(0.1f, distJ);
							float targetVx = (distX / norm) * 2.2f;
							float targetVy = (distY / norm) * 1.8f;
							vx = com.badlogic.gdx.math.MathUtils.lerp(vx, targetVx, 3.5f * delta);
							vy = com.badlogic.gdx.math.MathUtils.lerp(vy, targetVy, 3.5f * delta);

							if (distJ < 2.2f && !ayudado) {
								((Jugador) p).agregarPuntos(50);
								((Jugador) p).recuperarVida(1);
								ayudado = true;
								activarHitFlash(0.4f, new com.badlogic.gdx.graphics.Color(0.4f, 1.0f, 0.6f, 1.0f));
							}
							break;
						}
					}
				}
			}

			if (!siguiendoJugador) {
				// Nado orgánico de arrecife con pulso y deslizamiento (burst-and-glide)
				if (Math.abs(x - spawnX) > 6.0f) {
					direccionX = (x > spawnX) ? -1 : 1;
				}
				float fasePulso = (tiempoNado % 2.0f);
				float impulso = (fasePulso < 0.6f) ? 1.6f : 0.65f;
				float targetVx = direccionX * 1.8f * impulso;
				float targetVy = com.badlogic.gdx.math.MathUtils.sin(tiempoNado * 1.5f) * 0.8f;
				vx = com.badlogic.gdx.math.MathUtils.lerp(vx, targetVx, 3.2f * delta);
				vy = com.badlogic.gdx.math.MathUtils.lerp(vy, targetVy, 3.2f * delta);
			}
		}

		x += vx * delta;
		y += vy * delta;
		setX(x);
		setY(y);

		// Orientación correcta: la textura pez1.png mira a la DERECHA por defecto
		orientarHaciaDireccion(vx, vy, true, delta);

		float rapidezPez = (float) Math.hypot(vx, vy);
		setMultiplicadorVelocidadAnimacion(com.badlogic.gdx.math.MathUtils.clamp(rapidezPez * 0.75f, 0.45f, 2.4f));

		if (cuerpo != null) {
			cuerpo.setTransform(x + getWidth() / 2f, y + getHeight() / 2f, getRotation() * com.badlogic.gdx.math.MathUtils.degreesToRadians);
		}
		if (personajes != null) {
			for (Personaje p : personajes) {
				if (p instanceof Jugador && ((Jugador) p).isVivo()) {
					float dist = Math.abs(p.getX() - x);
					if (dist < 3.0f && !ayudado) {
						// Amistoso: acompaña y otorga perlas de regalo
						((Jugador) p).agregarPuntos(50);
						((Jugador) p).recuperarVida(1);
						ayudado = true;
						activarHitFlash(0.4f, new com.badlogic.gdx.graphics.Color(0.4f, 1.0f, 0.6f, 1.0f));
						break;
					}
				}
			}
		}
	}

	@Override
	public void colision(Personaje personaje) {
		if (personaje instanceof TiburonAzul) {
			// El tiburón es depredador: pez ángel huye
			direccionX = (personaje.getX() < x) ? 1 : -1;
		}
	}

}
