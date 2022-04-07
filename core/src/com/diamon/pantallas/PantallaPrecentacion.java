
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

	private Image[] fondos;

	public PantallaPrecentacion(Juego juego) {

		super(juego);

	}

	@Override
	public void mostrar() {

		fondos = new Image[2];

		float transparecia = 0;

		float duracion = 2;

		for (int i = 0; i < fondos.length; i++) {

			if (i == 0) {

				fondos[i] = new Image(new TextureRegion(new Texture(Gdx.files.internal("texturas/badlogic.jpg"))));

				fondos[i].setSize(256, 256);

				fondos[i].setPosition(192, 112);

			} else {

				fondos[i] = new Image(new TextureRegion(new Texture(Gdx.files.internal("texturas/diamondBlack.png"))));

				fondos[i].setSize(Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA);

				fondos[i].setPosition(0, 0);

				nivel.addActor(fondos[i]);

				fondos[i].addAction(Actions.sequence(Actions.alpha(transparecia, duracion)));

			}

		}

		nivel.addAction(Actions.sequence(Actions.delay(2), Actions.run(new Runnable() {

			public void run() {

				float transparecia = 0;

				float duracion = 2;

				nivel.addActor(fondos[0]);

				fondos[0].addAction(Actions.sequence(Actions.alpha(transparecia, duracion)));

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
