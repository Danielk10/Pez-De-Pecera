package com.diamon.pantallas;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;

public class PantallaPuntuaciones extends Pantalla {

	private TextButton atras;

	private TextButton ponerACero;

	private Label titulo;

	private Label[] textosPunto;

	private Label[] numeroNivel;

	private Label[] estado;

	private Label[] punto;

	private Music musica;

	public PantallaPuntuaciones(Juego juego) {
		super(juego);

	}

	@Override
	public void mostrar() {

		musica = recurso.get("audios/musica.ogg", Music.class);

		if (dato.isSonido())

		{

			if (!musica.isPlaying()) {

				musica.setLooping(true);

				musica.play();

			}

		}

		titulo = new Label("Puntuaciones", recurso.get("uis/general/uiskin.json", Skin.class));

		titulo.setPosition((Juego.ANCHO_PANTALLA / 3) + 20, Juego.ALTO_PANTALLA - 64);

		atras = new TextButton("Atras", recurso.get("uis/general/uiskin.json", Skin.class));

		atras.setSize(Juego.ANCHO_PANTALLA / 8, 32);

		atras.setPosition(32, 32);

		ponerACero = new TextButton("Poner a Cero", recurso.get("uis/general/uiskin.json", Skin.class));

		ponerACero.setSize(150, 32);

		ponerACero.setPosition(608 - ponerACero.getWidth(), 32);

		nivelMenu.addActor(titulo);

		nivelMenu.addActor(atras);

		nivelMenu.addActor(ponerACero);

		textosPunto = new Label[dato.getPuntuaciones().length];

		punto = new Label[dato.getPuntuaciones().length];

		numeroNivel = new Label[dato.getPuntuaciones().length];

		estado = new Label[dato.getPuntuaciones().length];

		int o = 360;

		for (int i = 0; i < textosPunto.length; i++)

		{

			textosPunto[i] = new Label("Puntos ", recurso.get("uis/general/uiskin.json", Skin.class));

			textosPunto[i].setPosition(Juego.ANCHO_PANTALLA / 5, o);

			numeroNivel[i] = new Label("" + dato.getNumeroNivelPuntuaciones()[i],
					recurso.get("uis/general/uiskin.json", Skin.class));

			numeroNivel[i].setPosition(Juego.ANCHO_PANTALLA / 3 + 48, o);

			estado[i] = new Label("" + dato.getEstadoPuntuaciones()[i],
					recurso.get("uis/general/uiskin.json", Skin.class));

			estado[i].setPosition(Juego.ANCHO_PANTALLA / 2 + 64, o);

			punto[i] = new Label("" + dato.getPuntuaciones()[i], recurso.get("uis/general/uiskin.json", Skin.class));

			punto[i].setPosition(Juego.ANCHO_PANTALLA / 2 + 160, o);

			nivelMenu.addActor(textosPunto[i]);

			nivelMenu.addActor(numeroNivel[i]);

			nivelMenu.addActor(estado[i]);

			nivelMenu.addActor(punto[i]);

			o -= 30;

		}

	}

	@Override
	public void eventos() {

		atras.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				juego.setScreen(new PantallaMenu(juego));

				super.clicked(event, x, y);
			}

		});

		ponerACero.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				for (int i = 0; i < dato.getPuntuaciones().length; i++)

				{

					dato.getPuntuaciones()[i] = 0;

					dato.getNumeroNivelPuntuaciones()[i] = "----";

					dato.getEstadoPuntuaciones()[i] = "----";

					textosPunto[i].setText("Puntos ");

					numeroNivel[i].setText("" + dato.getNumeroNivelPuntuaciones()[i]);

					estado[i].setText("" + dato.getEstadoPuntuaciones()[i]);

					punto[i].setText("" + dato.getPuntuaciones()[i]);

				}

				configuracion.escribirDatos(dato);

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
		// TODO Auto-generated method stub

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