package com.diamon.pez;

import com.diamon.nucleo.Juego;
import com.diamon.pantallas.PantallaPrecentacion;
import com.diamon.pez.publicidad.Publicidad;

public class PezDePecera extends Juego {

	public PezDePecera(Publicidad publicidad) {
		super(publicidad);

	}

	@Override
	public void create() {

		super.create();

		setScreen(new PantallaPrecentacion(this));

	}

}
