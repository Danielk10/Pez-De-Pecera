package com.diamon.pantallas;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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

		musica = recurso.get("audios/creditos.ogg", Music.class);

		if (dato.isSonido())

		{

			if (!musica.isPlaying()) {

				musica.setLooping(true);

				musica.play();

			}

		}

		Skin skin = recurso.get("uis/general/uiskin.json", Skin.class);

		titulo = new Label("Puntuaciones", skin);

		atras = new TextButton("Atras", skin);

		ponerACero = new TextButton("Poner a Cero", skin);

		Table tabla = new Table();

		tabla.setFillParent(true);

		tabla.add(titulo).colspan(4).padBottom(40).row();

		textosPunto = new Label[datosNiveles.getPuntuaciones().length];

		punto = new Label[datosNiveles.getPuntuaciones().length];

		numeroNivel = new Label[datosNiveles.getPuntuaciones().length];

		estado = new Label[datosNiveles.getPuntuaciones().length];

		for (int i = 0; i < textosPunto.length; i++)

		{

			textosPunto[i] = new Label("Puntos ", skin);

			numeroNivel[i] = new Label("" + datosNiveles.getNumeroNivelPuntuaciones()[i], skin);

			estado[i] = new Label("" + datosNiveles.getEstadoPuntuaciones()[i], skin);

			punto[i] = new Label("" + datosNiveles.getPuntuaciones()[i], skin);

			tabla.add(textosPunto[i]).padBottom(10).left();

			tabla.add(numeroNivel[i]).padBottom(10).padLeft(20);

			tabla.add(estado[i]).padBottom(10).padLeft(20);

			tabla.add(punto[i]).padBottom(10).padLeft(20).row();

		}

		Table tablaBotones = new Table();

		tablaBotones.add(atras).size(160, 32).padRight(20);

		tablaBotones.add(ponerACero).size(160, 32);

		tabla.add(tablaBotones).colspan(4).expand().bottom().pad(32);

		nivelMenu.addActor(tabla);

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

				for (int i = 0; i < datosNiveles.getPuntuaciones().length; i++)

				{

					datosNiveles.getPuntuaciones()[i] = 0;

					datosNiveles.getNumeroNivelPuntuaciones()[i] = "----";

					datosNiveles.getEstadoPuntuaciones()[i] = "----";

					textosPunto[i].setText("Puntos ");

					numeroNivel[i].setText("" + datosNiveles.getNumeroNivelPuntuaciones()[i]);

					estado[i].setText("" + datosNiveles.getEstadoPuntuaciones()[i]);

					punto[i].setText("" + datosNiveles.getPuntuaciones()[i]);

				}

				configuracion.escribirDatos(dato);

				informacionNiveles.escribirDatos(datosNiveles);

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