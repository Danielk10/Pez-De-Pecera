package com.diamon.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;

public class PantallaMenu extends Pantalla {

	private TextButton jugar;

	private TextButton opciones;

	private TextButton puntuaciones;

	private TextButton creditos;

	private TextButton salir;

	private Image titulo;

	private Music musica;

	public PantallaMenu(Juego juego) {
		super(juego);

	}

	@SuppressWarnings("static-access")
	@Override
	public void mostrar() {

		if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

			Gdx.graphics.setCursor(Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("texturas/cursor.png")), 0, 0));

		}

		musica = recurso.get("audios/creditos.ogg", Music.class);

		if (dato.isSonido())

		{

			if (!musica.isPlaying()) {

				musica.setLooping(true);

				musica.play();

			}

		}

		jugar = new TextButton("Jugar", recurso.get("uis/general/uiskin.json", Skin.class));

		jugar.setSize(213, 32);

		jugar.setPosition(212, 240);

		opciones = new TextButton("Opciones", recurso.get("uis/general/uiskin.json", Skin.class));

		opciones.setSize(213.0F, 32);

		opciones.setPosition(212, 192);

		puntuaciones = new TextButton("Puntuaciones", recurso.get("uis/general/uiskin.json", Skin.class));

		puntuaciones.setSize(213.0f, 32);

		puntuaciones.setPosition(212, 144);

		creditos = new TextButton("Creditos", recurso.get("uis/general/uiskin.json", Skin.class));

		creditos.setSize(213, 32);

		creditos.setPosition(212.0f, 96.0f);

		salir = new TextButton("Salir", recurso.get("uis/general/uiskin.json", Skin.class));

		salir.setSize(Juego.ANCHO_PANTALLA / 8, 32);

		salir.setPosition(32, 32);

		titulo = new Image(recurso.get("texturas/titulo.png", Texture.class));

		titulo.setSize(320, 320);

		titulo.setPosition(164.0f, 230.0f);

		nivelMenu.addActor(titulo);

		nivelMenu.addActor(jugar);

		nivelMenu.addActor(opciones);

		nivelMenu.addActor(puntuaciones);

		nivelMenu.addActor(creditos);

		nivelMenu.addActor(salir);

	}

	@Override
	public void eventos() {

		jugar.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				juego.setScreen(new PantallaSeleccion(juego));

				super.clicked(event, x, y);
			}

		});

		opciones.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				juego.setScreen(new PantallaOpciones(juego));

				super.clicked(event, x, y);
			}
		});

		puntuaciones.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				juego.setScreen(new PantallaPuntuaciones(juego));

				super.clicked(event, x, y);
			}
		});

		creditos.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				juego.setScreen(new PantallaCreditos(juego));

				super.clicked(event, x, y);
			}
		});

		salir.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				Gdx.app.exit();

				super.clicked(event, x, y);
			}
		});

	}

	@Override
	public void colisiones() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actualizar(float delta) {

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

}
