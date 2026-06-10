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
        Skin skin = recurso.get("uis/general/uiskin.json", Skin.class);

        atrasMenu = new TextButton("Atras", skin);

        titulo = new Label("Opciones de Partida", skin);

        nuevaPartida = new TextButton("Nueva Partida", skin);

        continuarJuego = new TextButton("Continuar Partida", skin);

        Table tabla = new Table();

        tabla.setFillParent(true);

        tabla.add(titulo).colspan(1).padBottom(40).row();

        if (dato.isContinuar()) {

            tabla.add(continuarJuego).size(213, 32).padBottom(10).row();

            tabla.add(nuevaPartida).size(213, 32).padBottom(10).row();

        } else {

            tabla.add(nuevaPartida).size(213, 32).padBottom(10).row();
        }

        tabla.add(atrasMenu).size(128, 32).expand().bottom().left().pad(32);

        nivelMenu.addActor(tabla);
    }

    private void anadirBotonesPartida(boolean anadir) {

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

					recurso.get("audios/creditos.ogg", Music.class).stop();

				}

				if (!dato.isContinuar())

				{

					dato.setContinuar(true);

				}

				dato.setPartida(true);

				datosNiveles.setPuntos(0);

				datosNiveles.setVidas(3);

				datosNiveles.setMisiles(10);

				datosNiveles.setBombas(10);

				datosNiveles.setPez(1);

				datosNiveles.setNumeroSatelite(0);

				juego.setScreen(new PantallaJuego(juego));

				super.clicked(event, x, y);
			}

		});

		continuarJuego.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				if (dato.isSonido())

				{

					recurso.get("audios/creditos.ogg", Music.class).stop();

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
