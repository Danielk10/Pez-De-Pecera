package com.diamon.pantallas;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;

public class PantallaMenu extends Pantalla {

	public PantallaMenu(Juego juego) {
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

		juego.setScreen(new PantallaJuego(juego));

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
