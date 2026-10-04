package com.diamon.personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
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
	private float rangoPatrulla = 5.0f;
	private int direccionY = 1;
	private float escalaInflado = 1.0f;
	private boolean inflado = false;
	private float anchoBase = 96f;
	private float altoBase = 64f;

	@Override
	public void actualizar(float delta) {
		super.actualizar(delta);
		if (spawnY < 0) {
			spawnY = y;
			anchoBase = getWidth() * com.diamon.nucleo.Juego.UNIDAD_DEL_MUNDO;
			altoBase = getHeight() * com.diamon.nucleo.Juego.UNIDAD_DEL_MUNDO;
		}

		// Detección de proximidad del jugador
		inflado = false;
		if (personajes != null) {
			for (Personaje p : personajes) {
				if (p instanceof Jugador && ((Jugador) p).isVivo()) {
					float dist = (float) Math.hypot(p.getX() - x, p.getY() - y);
					if (dist < 3.5f) {
						inflado = true;
						break;
					}
				}
			}
		}

		float escalaObjetivo = inflado ? 1.5f : 1.0f;
		escalaInflado = com.badlogic.gdx.math.MathUtils.lerp(escalaInflado, escalaObjetivo, 5.0f * delta);
		setSize(anchoBase * escalaInflado, altoBase * escalaInflado);

		float vel = inflado ? 1.2f : 2.2f;
		float vy = direccionY * vel;
		float vx = com.badlogic.gdx.math.MathUtils.sin(tiempo * 1.5f) * 0.7f;
		x += vx * delta;
		y += vy * delta;
		if (Math.abs(y - spawnY) > rangoPatrulla) {
			direccionY = (y > spawnY) ? -1 : 1;
		}

		setX(x);
		setY(y);

		// Orientación suave: la textura natural del pez globo mira hacia la IZQUIERDA
		orientarHaciaDireccion(vx, vy, false, delta);
		setMultiplicadorVelocidadAnimacion(inflado ? 1.75f : 0.85f);

		if (cuerpo != null) {
			cuerpo.setTransform(x + getWidth() / 2f, y + getHeight() / 2f, getRotation() * com.badlogic.gdx.math.MathUtils.degreesToRadians);
		}
	}

	@Override
	public void colision(Personaje personaje) {
		if (personaje instanceof Jugador) {
			int danio = inflado ? 2 : 1;
			((Jugador) personaje).recibirDanio(danio);
		}
	}

}
