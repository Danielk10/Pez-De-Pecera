package com.diamon.particulas;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;

public class Particula {

	private ParticleEffect efectoParticula;

	protected World mundoVirtual;

	protected Body cuerpo;

	private float x, y;

	protected Pantalla pantalla;

	public Particula(ParticleEffect efectoParticula, Pantalla pantalla) {

		this.efectoParticula = efectoParticula;

		this.pantalla = pantalla;

		this.mundoVirtual = pantalla.getMundoVirtual();

		BodyDef bodyDef = new BodyDef();

		bodyDef.type = BodyDef.BodyType.StaticBody;

		FixtureDef fixtureDef = new FixtureDef();

		PolygonShape shape = new PolygonShape();

		shape.setAsBox(efectoParticula.getBoundingBox().getWidth() / 2,
				efectoParticula.getBoundingBox().getHeight() / 2);

		fixtureDef.shape = shape;

		if (mundoVirtual != null) {

			cuerpo = mundoVirtual.createBody(bodyDef);

			cuerpo.setUserData(this);

			cuerpo.createFixture(fixtureDef);

		}

		shape.dispose();

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

		if (cuerpo != null) {

			cuerpo.setTransform(this.x + efectoParticula.getBoundingBox().getWidth() / 2,
					this.y + efectoParticula.getBoundingBox().getHeight() / 2, 0);

		}

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

		if (cuerpo != null) {

			cuerpo.setTransform(this.x + efectoParticula.getBoundingBox().getWidth() / 2,
					efectoParticula.getBoundingBox().getHeight() / 2, 0);

		}

	}

	public void dibujar(Batch pincel, float delta) {

		efectoParticula.draw(pincel, delta);

	}

	public void liberarRecursos() {

		efectoParticula.dispose();

	}

}
