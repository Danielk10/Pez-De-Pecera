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

	@Override
	public void actualizar(float delta) {
		super.actualizar(delta);

		if (spawnX < 0) {
			spawnX = x;
			spawnY = y;
		}

		tiempoNado += delta * 2.2f;

		// Movimiento orgánico en onda senoidal (nado elegante de pez ángel)
		y = spawnY + com.badlogic.gdx.math.MathUtils.sin(tiempoNado) * 0.8f;
		x += direccionX * 1.8f * delta;

		if (Math.abs(x - spawnX) > 5.0f) {
			direccionX = (x > spawnX) ? -1 : 1;
		}

		// La textura natural mira hacia la IZQUIERDA.
		// Si se mueve hacia la derecha (direccionX > 0), debe voltearse (flipX = true).
		setFlip(direccionX > 0, false);
		setX(x);
		setY(y);

		// Si el jugador está muy cerca, orientarse curiosamente hacia él
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
