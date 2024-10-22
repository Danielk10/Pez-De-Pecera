package com.diamon.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;

public class PantallaCarga extends Pantalla {

	private Image fondo;

	private ProgressBar barra;

	public PantallaCarga(Juego juego) {
		super(juego);

	}

	@Override
	public void mostrar() {

		barra = new ProgressBar(0.0F, 100.0F, 1.0F, false,
				new Skin(Gdx.files.internal("uis/carga/neon-ui.json"), new TextureAtlas("uis/carga/neon-ui.atlas")));

		barra.setSize(400, 32);

		barra.setPosition(128, 80);

		fondo = new Image(new TextureRegion(new Texture(Gdx.files.internal("texturas/inicio.png"))));

		fondo.setSize(Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA);

		fondo.setPosition(0, 0);

		nivel.addActor(fondo);

		nivel.addActor(barra);

	}

	@Override
	public void eventos() {
		// TODO Auto-generated method stub

	}

	@Override
	public void colisiones() {
		// TODO Auto-generated method stub

	}

	@Override

	public void actualizar(float delta) {

		if (recurso.update()) {

			nivel.addAction(Actions.sequence(Actions.delay(0.2f), Actions.run(new Runnable() {

				public void run() {

					filtradoBilineal();

					sonido();

					juego.setScreen(new PantallaMenu(juego));

				}
			})));

		} else {

			int progreso = (int) (recurso.getProgress() * 100);

			barra.setValue(progreso);
		}

	}

	@Override
	public void dibujar(Batch pincel, float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void guardarDatos() {
		// TODO Auto-generated method stub

	}

	@Override
	public void liberarRecursos() {
		// TODO Auto-generated method stub

	}

	private void filtradoBilineal() {

		if (dato.isFiltradoBilineal()) {

			recurso.get("texturas/invisible.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);

			recurso.get("texturas/bomba.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);

			recurso.get("texturas/algas.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);

			recurso.get("texturas/fondo1.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);

			recurso.get("texturas/fondo2.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);

			recurso.get("texturas/fondo3.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);

			recurso.get("texturas/fondo4.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);

			recurso.get("texturas/pausa.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);
			recurso.get("texturas/cursor.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);
			recurso.get("texturas/menu.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);
			recurso.get("texturas/badlogic.jpg", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);
			recurso.get("texturas/titulo.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);
			recurso.get("texturas/inicio.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);
			recurso.get("texturas/icono.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);
		//	recurso.get("texturas/diamondBlack.png", Texture.class).setFilter(TextureFilter.Linear,
		//			TextureFilter.Linear);

			for (Texture tetura : recurso.get("texturas/pez.atlas", TextureAtlas.class).getTextures()) {

				tetura.setFilter(TextureFilter.Linear, TextureFilter.Linear);

			}

			for (Texture tetura : recurso.get("texturas/iconos.atlas", TextureAtlas.class).getTextures()) {

				tetura.setFilter(TextureFilter.Linear, TextureFilter.Linear);

			}

			for (Texture tetura : recurso.get("texturas/controles.atlas", TextureAtlas.class).getTextures()) {

				tetura.setFilter(TextureFilter.Linear, TextureFilter.Linear);

			}

			for (Texture tetura : recurso.get("texturas/dedos.atlas", TextureAtlas.class).getTextures()) {

				tetura.setFilter(TextureFilter.Linear, TextureFilter.Linear);

			}

			for (Texture tetura : recurso.get("texturas/pez1.atlas", TextureAtlas.class).getTextures()) {

				tetura.setFilter(TextureFilter.Linear, TextureFilter.Linear);

			}

			for (Texture tetura : recurso.get("texturas/pezG.atlas", TextureAtlas.class).getTextures()) {

				tetura.setFilter(TextureFilter.Linear, TextureFilter.Linear);

			}

			for (Texture tetura : recurso.get("texturas/pulpo.atlas", TextureAtlas.class).getTextures()) {

				tetura.setFilter(TextureFilter.Linear, TextureFilter.Linear);

			}

			for (Texture tetura : recurso.get("texturas/pezGlobo.atlas", TextureAtlas.class).getTextures()) {

				tetura.setFilter(TextureFilter.Linear, TextureFilter.Linear);

			}

		}

		if (!dato.isFiltradoBilineal()) {

			recurso.get("texturas/invisible.png", Texture.class).setFilter(TextureFilter.Nearest,
					TextureFilter.Nearest);

			recurso.get("texturas/bomba.png", Texture.class).setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

			recurso.get("texturas/algas.png", Texture.class).setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

			recurso.get("texturas/fondo1.png", Texture.class).setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

			recurso.get("texturas/fondo2.png", Texture.class).setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

			recurso.get("texturas/fondo3.png", Texture.class).setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

			recurso.get("texturas/fondo4.png", Texture.class).setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

			recurso.get("texturas/pausa.png", Texture.class).setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
			recurso.get("texturas/cursor.png", Texture.class).setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
			recurso.get("texturas/menu.png", Texture.class).setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

			recurso.get("texturas/badlogic.jpg", Texture.class).setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
			recurso.get("texturas/titulo.png", Texture.class).setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
			recurso.get("texturas/inicio.png", Texture.class).setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
			recurso.get("texturas/icono.png", Texture.class).setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		//	recurso.get("texturas/diamondBlack.png", Texture.class).setFilter(TextureFilter.Nearest,
		//			TextureFilter.Nearest);

			for (Texture tetura : recurso.get("texturas/pez.atlas", TextureAtlas.class).getTextures()) {

				tetura.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

			}

			for (Texture tetura : recurso.get("texturas/iconos.atlas", TextureAtlas.class).getTextures()) {

				tetura.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

			}

			for (Texture tetura : recurso.get("texturas/controles.atlas", TextureAtlas.class).getTextures()) {

				tetura.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

			}

			for (Texture tetura : recurso.get("texturas/dedos.atlas", TextureAtlas.class).getTextures()) {

				tetura.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

			}

			for (Texture tetura : recurso.get("texturas/pez1.atlas", TextureAtlas.class).getTextures()) {

				tetura.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

			}

			for (Texture tetura : recurso.get("texturas/pezG.atlas", TextureAtlas.class).getTextures()) {

				tetura.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

			}

			for (Texture tetura : recurso.get("texturas/pulpo.atlas", TextureAtlas.class).getTextures()) {

				tetura.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

			}

			for (Texture tetura : recurso.get("texturas/pezGlobo.atlas", TextureAtlas.class).getTextures()) {

				tetura.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

			}

		}

	}

	private void sonido() {

		recurso.get("audios/musica.ogg", Music.class).setVolume(dato.getVolumenMusica());

		recurso.get("audios/moustro.ogg", Music.class).setVolume(dato.getVolumenMusica());

		recurso.get("audios/creditos.ogg", Music.class).setVolume(dato.getVolumenMusica());

	}

}
