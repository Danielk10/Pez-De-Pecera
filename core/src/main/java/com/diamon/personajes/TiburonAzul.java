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
	private float rangoPatrulla = 9.0f;
	private int direccion = -1;
	private float tiempoAturdido = 0f;
	private float tiempoMuerde = 0f;

	@Override
	public void actualizar(float delta) {
		super.actualizar(delta);
		if (spawnX < 0) {
			spawnX = x;
		}

		if (tiempoAturdido > 0) {
			tiempoAturdido -= delta;
			// Aturdido: flota levemente sin atacar
			y += com.badlogic.gdx.math.MathUtils.sin(tiempoAturdido * 4f) * 0.02f;
			setY(y);
			return;
		}

		if (tiempoMuerde > 0) {
			tiempoMuerde -= delta;
			if (tiempoMuerde <= 0) {
				muerde = false;
			}
		}

		// Si el jugador está en rango, acechar y perseguir
		float vel = 2.8f;
		if (personajes != null) {
			for (Personaje p : personajes) {
				if (p instanceof Jugador && ((Jugador) p).isVivo()) {
					float distX = p.getX() - x;
					float distY = p.getY() - y;
					float dist = (float) Math.sqrt(distX * distX + distY * distY);
					if (dist < 8.0f) {
						vel = 4.2f;
						direccion = (distX < 0) ? -1 : 1;
						// Desplazamiento vertical suave para cazar al pez en 2D submarino
						y += Math.signum(distY) * 1.5f * delta;
						setY(y);
						if (dist < 2.0f) {
							muerde = true;
							tiempoMuerde = 0.8f;
						}
						break;
					}
				}
			}
		}

		x += direccion * vel * delta;
		if (Math.abs(x - spawnX) > rangoPatrulla) {
			direccion = (x > spawnX) ? -1 : 1;
		}

		// La textura natural del tiburón mira hacia la DERECHA.
		// Si se mueve hacia la izquierda (direccion < 0), debe voltearse (flipX = true).
		setFlip(direccion < 0, false);
		setX(x);

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
