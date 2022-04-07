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

public class PantallaSeleccion extends Pantalla {

	private TextButton atrasMenu;

	private Label titulo;

	private TextButton nuevaPartida;

	private TextButton continuarJuego;

	public PantallaSeleccion(Juego juego) {
		super(juego);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void mostrar() {

		atrasMenu = new TextButton("Atras", recurso.get("uis/general/uiskin.json", Skin.class));

		atrasMenu.setSize(Juego.ANCHO_PANTALLA / 8, 32);

		atrasMenu.setPosition(32, 32);

		titulo = new Label("Opciones de Partida", recurso.get("uis/general/uiskin.json", Skin.class));

		titulo.setPosition((Juego.ANCHO_PANTALLA / 3) - 27, Juego.ALTO_PANTALLA - 64);

		nuevaPartida = new TextButton("Nueva Partida", recurso.get("uis/general/uiskin.json", Skin.class));

		nuevaPartida.setSize(213, 32);

		nuevaPartida.setPosition(Juego.ANCHO_PANTALLA / 3, 240);

		continuarJuego = new TextButton("Continuar Partida", recurso.get("uis/general/uiskin.json", Skin.class));

		continuarJuego.setSize(213, 32);

		if (!dato.isContinuar())

		{

			nuevaPartida.setPosition(Juego.ANCHO_PANTALLA / 3, 240);

			continuarJuego.setPosition(Juego.ANCHO_PANTALLA / 3, 192);

		} else {

			nuevaPartida.setPosition(Juego.ANCHO_PANTALLA / 3, 192);

			continuarJuego.setPosition(Juego.ANCHO_PANTALLA / 3, 240);

		}

		nivelMenu.addActor(titulo);

		nivelMenu.addActor(atrasMenu);

		anadirBotonesPartida(true);

	}

	private void anadirBotonesPartida(boolean anadir) {

		if (anadir) {

			nivelMenu.addActor(nuevaPartida);

			if (dato.isContinuar())

			{

				nivelMenu.addActor(continuarJuego);

			}

			nivelMenu.addActor(atrasMenu);

		} else {

			nuevaPartida.remove();

			if (dato.isContinuar())

			{

				continuarJuego.remove();

			}

			atrasMenu.remove();

		}

	}

	@Override
	public void eventos() {

		atrasMenu.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				juego.setScreen(new PantallaMenu(juego));

				super.clicked(event, x, y);
			}

		});

		nuevaPartida.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				if (dato.isSonido())

				{

					recurso.get("audios/musica.ogg", Music.class).stop();

				}

				if (!dato.isContinuar())

				{

					dato.setContinuar(true);

				}

				dato.setPartida(true);

				dato.setPuntos(0);

				dato.setVidas(3);

				dato.setMisiles(10);

				dato.setBombas(10);

				dato.setHelicoptero(1);

				dato.setNumeroSatelite(0);

				juego.setScreen(new PantallaJuego(juego));

				super.clicked(event, x, y);
			}

		});

		continuarJuego.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				if (dato.isSonido())

				{

					recurso.get("audios/musica.ogg", Music.class).stop();

				}

				dato.setPartida(false);

				juego.setScreen(new PantallaJuego(juego));

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
