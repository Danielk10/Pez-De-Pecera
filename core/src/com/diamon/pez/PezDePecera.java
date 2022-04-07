package com.diamon.pez;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

		recurso.load("texturas/pez.atlas", TextureAtlas.class);

		recurso.load("texturas/fondo4.png", Texture.class);

		setScreen(new PantallaPrecentacion(this));

	}

}
