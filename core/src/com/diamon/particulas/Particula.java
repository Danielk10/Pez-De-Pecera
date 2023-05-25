package com.diamon.particulas;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.graphics.ParticleEmitterBox2D;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;

import box2dLight.Light;

public class Particula {

	private ParticleEffect efectoParticula;

	protected World mundoVirtual;

	private Vector2 posicion;

	protected Pantalla pantalla;

	private Light puntoLuz;

	public Particula(ParticleEffect efectoParticula, Pantalla pantalla) {

		this.efectoParticula = efectoParticula;

		this.pantalla = pantalla;

		mundoVirtual = pantalla.getMundoVirtual();

		ParticleEmitterBox2D emisorBox2D = new ParticleEmitterBox2D(mundoVirtual,
				efectoParticula.getEmitters().first());

		this.efectoParticula.getEmitters().add(emisorBox2D);

		this.efectoParticula.getEmitters().removeIndex(0);

	}

	public ParticleEffect getEfectoParticula() {

		return efectoParticula;
	}

	public void iniciar() {

		this.efectoParticula.start();
	}

	public Light getPuntoLuz() {
		return puntoLuz;
	}

	public void setPuntoLuz(Light puntoLuz) {

		this.puntoLuz = puntoLuz;
	}

	public void setEfectoParticula(ParticleEffect efectoParticula) {

		this.efectoParticula = efectoParticula;
	}

	public void setPosicion(float x, float y) {

		efectoParticula.setPosition(x, y);

		posicion = new Vector2(x, y);

		if (puntoLuz != null) {

			puntoLuz.setPosition(posicion);

		}

	}

	public void resetear() {

		efectoParticula.reset();

	}

	public void setDuracion(int duracion) {

		efectoParticula.setDuration(duracion);

	}

	public void setEscala(float proporsion) {

		efectoParticula.scaleEffect(proporsion / Juego.UNIDAD_DEL_MUNDO, proporsion / Juego.UNIDAD_DEL_MUNDO);

	}

	public void actualizar(float delta) {

		efectoParticula.update(delta);

		posicion.x += (1 / Juego.DELTA_A_PIXEL * delta) / Juego.UNIDAD_DEL_MUNDO;

		this.efectoParticula.setPosition(posicion.x, posicion.y);

		if (puntoLuz != null) {

			puntoLuz.setPosition(posicion);

		}

	}

	public void dibujar(Batch pincel, float delta) {

		efectoParticula.draw(pincel);

	}

	public void liberarRecursos() {
		
		

	}

}
