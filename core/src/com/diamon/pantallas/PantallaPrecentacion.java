
package com.diamon.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;

public class PantallaPrecentacion extends Pantalla {

	private Image fondo;

	private Image fondo1;

	public PantallaPrecentacion(Juego juego) {

		super(juego);

	}

	@Override
	public void mostrar() {

		fondo = new Image(new TextureRegion(new Texture(Gdx.files.internal("texturas/badlogic.jpg"))));

		fondo1 = new Image(new TextureRegion(new Texture(Gdx.files.internal("texturas/diamondBlack.png"))));

		fondo.setSize(256, 256);

		fondo.setPosition(192, 112);

		fondo1.setSize(Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA);

		fondo1.setPosition(0, 0);

		nivel.addActor(fondo1);

		float alphaTo = 0;

		float duration = 2;

		fondo1.addAction(Actions.sequence(Actions.alpha(alphaTo, duration)));

		nivel.addAction(Actions.sequence(Actions.delay(2), Actions.run(new Runnable() {

			public void run() {

				float alphaTo = 0;

				float duration = 2;

				nivel.addActor(fondo);

				fondo.addAction(Actions.sequence(Actions.alpha(alphaTo, duration)));

			}
		})));

		nivel.addAction(Actions.sequence(Actions.delay(4), Actions.run(new Runnable() {

			public void run() {

				juego.setScreen(new PantallaCarga(juego));

			}
		})));

	}

	@Override
	public void eventos() {

	}

	@Override
	public void colisiones() {

	}

	@Override
	public void actualizar(float delta) {

	}

	@Override
	public void dibujar(Batch pincel, float delta) {

		ScreenUtils.clear(0.0F, 0.0F, 0.0F, 1.0F, true);

	}

	@Override
	public void guardarDatos() {

	}

	@Override
	public void liberarRecursos() {

	}

}
