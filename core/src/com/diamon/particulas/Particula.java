package com.diamon.particulas;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.diamon.nucleo.Juego;

public class Particula {

	private ParticleEffect efectoParticula;

	private float x, y;

	public Particula(ParticleEffect efectoParticula) {

		this.efectoParticula = efectoParticula;

	}

	public ParticleEffect getEfectoParticula() {
		return efectoParticula;
	}

	public void setEfectoParticula(ParticleEffect efectoParticula) {
		this.efectoParticula = efectoParticula;
	}

	public void setPosicion(float x, float y) {

		efectoParticula.setPosition(x, y);

		this.x = x;

		this.y = y;

	}

	public void resetear() {

		efectoParticula.reset();

	}

	public void setDuracion(int duracion) {
		efectoParticula.setDuration(duracion);

	}

	public void actualizar(float delta) {

		efectoParticula.update(delta);

		x += 1 / Juego.DELTA_A_PIXEL * delta;

		efectoParticula.setPosition(x, y);

	}

	public void dibujar(Batch pincel, float delta) {

		efectoParticula.draw(pincel, delta);

	}

	public void liberarRecursos() {

		efectoParticula.dispose();

	}

}
