package com.diamon.pantallas;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;

public class PantallaCarga extends Pantalla {

	public PantallaCarga(Juego juego) {
		super(juego);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void mostrar() {
		// TODO Auto-generated method stub

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

					juego.setScreen(new PantallaMenu(juego));

				}
			})));

		} else {

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
