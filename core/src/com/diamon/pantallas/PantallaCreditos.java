package com.diamon.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;

public class PantallaCreditos extends Pantalla {

	private static final float VELOCIDAD_CREDITOS = 0.5f;

	private TextButton aceptar;

	private Label titulo;

	private Label version;

	private TextButton politicaDePrivacidad;

	private Label[] creditosAgredecimientosNombre;

	private Label[] creditosAutores;

	private Label[] creditosDiseno;

	private Label[] creditosDisenoNombres;

	private Label[] creditosArtistasNombres;

	private Label[] creditosArtistasDeMusicaNombres;

	private Label creditosArtistas;

	private Label creditosArtistasDeMusica;

	private Label creditosAgredecimientos;

	private Label creditosEspecial;

	private Label creditoMotor;

	private Label creditoMotorNombre;

	private Music musica;

	private float mover[];

	private float moverEspecial;

	private Array<Label> creditos;

	private float[] posiciones;

	private float posicionEspecial;

	public PantallaCreditos(Juego juego) {
		super(juego);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("static-access")
	@Override
	public void mostrar() {

		creditos = new Array<Label>();

		posicionEspecial = -1450.0f;

		musica = recurso.get("audios/musica.ogg", Music.class);

		if (dato.isSonido())

		{

			if (!musica.isPlaying()) {

				musica.setLooping(true);

				musica.play();

			}

		}

		String[] autores = { "Pez De Pecera", "2022  Daniel Diamon y Diamond Black" };

		String[] titulosDesarrolladores = { "Diseno del Juego", "y Programacion", "Disenador Grafico" };

		String[] nombresDesarrolladores = { "Daniel Diamon", "Jesus Mnedez", "Numa" };

		String[] nombresArtistas = { "Zuhria Alfitra - www.gameart2d.com",
				"Carlos Alface - www.carlosalface.blogspot.pt", "cemkalyoncu - www.opengameart.org",
				"yd - www.opengameart.org", "mishonis - www.opengameart.org", "2dGameCreation - www.opengameart.org",
				"knik1985 - www.opengameart.org", "www.enchantjs.com", "Tiled - www.mapeditor.org",
				"paint.net - www.getpaint.net", "Skin Composer - www.ray3k.wordpress.com",
				"GDX Texture Packer - www. crashinvaders.com", "Hiero - www.libgdx.com", "www.libgdx.com",
				"www.reygif.com", "RAY3K", "Jesus Mendez", "Daniel Diamon" };

		String[] nombresArtitasDeMusica = { "sfxr - www.drpetter.se", "Jobromedia - www.opengameart.org",
				"Alexandr Zhelanov - www.opengameart.org", "Tozan - www.opengameart.org",
				"Matthew Pablo - www.opengameart.org", "Zydro - www.opengameart.org", "Daniel Diamon" };

		String[] nombres = { "Alexander Hristov", "Jesus Code", "Nacho Cabanes", "Angel Navarro Baeza", "Yayo Arellano",
				"Luis Diamon", "Carlos Calanche", "Alfredo Casas", "Kelwins Mosquera", "Natalia Mosquera",
				"Herith Ducey", "Lucino Dulcey", "Ruth Celis", "Anye Mosquera", "Yelitza Vazquez", "Dannys Diamon",
				"Jorge Diamon", "Darwin Diamon", "Vanessa Diamon", "Jordan Rivas", "Angel Leonardo Reina",
				"Javier Narea", "Jefferson Cuellar", "Merlyannis Garcia", "Jennifer Rivero", "Luis Jose Garcia",
				"Veronica Mendez", "Ricardo Gonzalez Santamaria", "Jenina Estefania Santamaria" };

		creditosAutores = new Label[autores.length];

		creditosAgredecimientosNombre = new Label[nombres.length];

		creditosDiseno = new Label[titulosDesarrolladores.length];

		creditosDisenoNombres = new Label[nombresDesarrolladores.length];

		creditosArtistasNombres = new Label[nombresArtistas.length];

		creditosArtistasDeMusicaNombres = new Label[nombresArtitasDeMusica.length];

		int indice = 230;

		for (int i = 0; i < creditosAutores.length; i++) {

			creditosAutores[i] = new Label(autores[i], recurso.get("uis/general/uiskin.json", Skin.class));

			creditosAutores[i].setPosition(Juego.ANCHO_PANTALLA / 2 - 50, indice);

			if (i == 1) {

				indice = indice - 24;

				creditosAutores[i].setPosition(Juego.ANCHO_PANTALLA / 4 - 20, indice);

			}

			creditos.add(creditosAutores[i]);

			nivelMenu.addActor(creditosAutores[i]);

			indice -= 24;
		}

		indice = indice - 24;

		for (int i = 0; i < creditosDiseno.length; i++) {

			creditosDiseno[i] = new Label(titulosDesarrolladores[i],
					recurso.get("uis/general/uiskin.json", Skin.class));

			creditosDiseno[i].setPosition(Juego.ANCHO_PANTALLA / 4 - 20, indice);

			if (i == 2) {

				indice = indice - 24;

				creditosDiseno[i].setPosition(Juego.ANCHO_PANTALLA / 4 - 20, indice);

			}

			creditos.add(creditosDiseno[i]);

			nivelMenu.addActor(creditosDiseno[i]);

			indice -= 24;

		}

		indice = indice + 72;

		for (int i = 0; i < creditosDisenoNombres.length; i++) {

			creditosDisenoNombres[i] = new Label(nombresDesarrolladores[i],
					recurso.get("uis/general/uiskin.json", Skin.class));

			creditosDisenoNombres[i].setPosition(Juego.ANCHO_PANTALLA / 2 - 20, indice);

			if (i == 1) {

				indice = indice - 24;

				creditosDisenoNombres[i].setPosition(Juego.ANCHO_PANTALLA / 2 - 20, indice);

			}

			creditos.add(creditosDisenoNombres[i]);

			nivelMenu.addActor(creditosDisenoNombres[i]);

			indice -= 24;

		}

		indice = indice - 24;

		creditosArtistas = new Label("Arte y Graficos", recurso.get("uis/general/uiskin.json", Skin.class));

		creditosArtistas.setPosition(Juego.ANCHO_PANTALLA / 4 - 20, indice);

		creditos.add(creditosArtistas);

		nivelMenu.addActor(creditosArtistas);

		for (int i = 0; i < creditosArtistasNombres.length; i++) {

			creditosArtistasNombres[i] = new Label(nombresArtistas[i],
					recurso.get("uis/general/uiskin.json", Skin.class));

			creditosArtistasNombres[i].setPosition(Juego.ANCHO_PANTALLA / 2 - 20, indice);

			creditos.add(creditosArtistasNombres[i]);

			nivelMenu.addActor(creditosArtistasNombres[i]);

			indice -= 24;

		}

		indice = indice - 24;

		creditosArtistasDeMusica = new Label("Musica", recurso.get("uis/general/uiskin.json", Skin.class));

		creditosArtistasDeMusica.setPosition(Juego.ANCHO_PANTALLA / 4 - 20, indice);

		creditos.add(creditosArtistasDeMusica);

		nivelMenu.addActor(creditosArtistasDeMusica);

		for (int i = 0; i < creditosArtistasDeMusicaNombres.length; i++) {

			creditosArtistasDeMusicaNombres[i] = new Label(nombresArtitasDeMusica[i],
					recurso.get("uis/general/uiskin.json", Skin.class));

			creditosArtistasDeMusicaNombres[i].setPosition(Juego.ANCHO_PANTALLA / 2 - 20, indice);

			creditos.add(creditosArtistasDeMusicaNombres[i]);

			nivelMenu.addActor(creditosArtistasDeMusicaNombres[i]);

			indice -= 24;

		}

		indice = indice - 24;

		creditoMotor = new Label("Motor Grafico", recurso.get("uis/general/uiskin.json", Skin.class));

		creditoMotor.setPosition(Juego.ANCHO_PANTALLA / 4 - 20, indice);

		creditos.add(creditoMotor);

		nivelMenu.addActor(creditoMotor);

		creditoMotorNombre = new Label("LibGDX - www.libgdx.com", recurso.get("uis/general/uiskin.json", Skin.class));

		creditoMotorNombre.setPosition(Juego.ANCHO_PANTALLA / 2 - 20, indice);

		creditos.add(creditoMotorNombre);

		nivelMenu.addActor(creditoMotorNombre);

		indice = indice - 48;

		creditosAgredecimientos = new Label("Agradecimientos", recurso.get("uis/general/uiskin.json", Skin.class));

		creditosAgredecimientos.setPosition(Juego.ANCHO_PANTALLA / 2 - 12, indice);

		creditos.add(creditosAgredecimientos);

		nivelMenu.addActor(creditosAgredecimientos);

		indice = indice - 48;

		creditosEspecial = new Label("Gracias a Dios por todo y a mi Familia",
				recurso.get("uis/general/uiskin.json", Skin.class));

		creditosEspecial.setPosition(Juego.ANCHO_PANTALLA / 3, indice);

		nivelMenu.addActor(creditosEspecial);

		indice = indice - 48;

		for (int i = 0; i < creditosAgredecimientosNombre.length; i++) {

			creditosAgredecimientosNombre[i] = new Label(nombres[i],
					recurso.get("uis/general/uiskin.json", Skin.class));

			creditosAgredecimientosNombre[i].setPosition(Juego.ANCHO_PANTALLA / 2 - 20, indice);

			creditos.add(creditosAgredecimientosNombre[i]);

			nivelMenu.addActor(creditosAgredecimientosNombre[i]);

			indice -= 24;

		}

		titulo = new Label("Creditos", recurso.get("uis/general/uiskin.json", Skin.class));

		titulo.setPosition((Juego.ANCHO_PANTALLA / 2) - 64, Juego.ALTO_PANTALLA - 64);

		aceptar = new TextButton("Aceptar", recurso.get("uis/general/uiskin.json", Skin.class));

		aceptar.setSize(Juego.ANCHO_PANTALLA / 8, 32);

		aceptar.setPosition(Juego.ANCHO_PANTALLA / 2 - 40, 32);

		politicaDePrivacidad = new TextButton("Politica de privacidad",
				recurso.get("uis/general/uiskin.json", Skin.class));

		politicaDePrivacidad.setPosition(32, 32);

		politicaDePrivacidad.setSize(170, 32);

		version = new Label("Version 1.0.0", recurso.get("uis/general/uiskin.json", Skin.class));

		version.setPosition(608 - version.getWidth(), 32);

		version.setColor(0, 1, 0, 1f);

		nivelMenu.addActor(titulo);

		nivelMenu.addActor(aceptar);

		if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

			nivelMenu.addActor(version);
		}

		if (Gdx.app.getType() == Gdx.app.getType().Android) {

			nivelMenu.addActor(politicaDePrivacidad);

		}

		mover = new float[creditos.size];

		for (int i = 0; i < creditos.size; i++) {

			mover[i] = creditos.get(i).getY();

		}

		posiciones = new float[creditos.size];

		for (int i = 0; i < creditos.size; i++) {

			float posicion = mover[i];

			posiciones[i] = posicion;

		}

		moverEspecial = creditosEspecial.getY();

	}

	@Override
	public void eventos() {

		aceptar.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				juego.setScreen(new PantallaMenu(juego));

				super.clicked(event, x, y);
			}

		});

		politicaDePrivacidad.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				if (publicidad != null) {

					publicidad.iniciarActividad();
				}

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

		for (int i = 0; i < creditos.size; i++) {

			mover[i] += VELOCIDAD_CREDITOS / Juego.DELTA_A_PIXEL * delta;

			creditos.get(i).setY(mover[i]);

			if (creditos.get(i).getY() >= Juego.ALTO_PANTALLA - 120) {

				creditos.get(i).setColor(1, 1, 1, 0);

				mover[i] = posiciones[posiciones.length - 1];

				creditos.get(i).setColor(1, 1, 1, 1);

			}

		}

		moverEspecial += VELOCIDAD_CREDITOS / Juego.DELTA_A_PIXEL * delta;

		creditosEspecial.setY(moverEspecial);

		if (creditosEspecial.getY() >= Juego.ALTO_PANTALLA - 120) {

			creditosEspecial.setColor(1, 1, 1, 0);

			moverEspecial = posicionEspecial;

			creditosEspecial.setColor(1, 1, 1, 1);

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

}