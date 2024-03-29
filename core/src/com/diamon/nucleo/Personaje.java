package com.diamon.nucleo;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.diamon.datos.InformacionNiveles;
import com.diamon.datos.Configuraciones;
import com.diamon.datos.Datos;
import com.diamon.datos.DatosNiveles;

public abstract class Personaje extends Sprite {

	public static final int ESTATICO = 0;

	public static final int CINESTECICO = 1;

	public static final int DIANAMICO = 2;

	protected boolean remover;

	protected Animation<TextureRegion> animacion;

	private float tiempo;

	private boolean animar;

	protected Pantalla pantalla;

	protected float x;

	protected float y;

	protected int dureza;

	protected Array<Personaje> personajes;

	protected AssetManager recurso;

	protected OrthographicCamera camara;

	protected boolean vivo;

	protected DatosNiveles datosNiveles;

	protected InformacionNiveles informacionNiveles;

	protected Datos dato;

	protected Configuraciones configuracion;

	protected World mundoVirtual;

	protected Body cuerpo;

	protected int tipoDeCuerpo;

	public Personaje(Texture textura, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {

		super(textura);

		setSize(ancho, alto);

		setOriginCenter();

		remover = false;

		animar = false;

		vivo = true;

		tiempo = 0;

		this.pantalla = pantalla;

		x = 0;

		y = 0;

		dureza = 3;

		personajes = pantalla.personajes;

		recurso = pantalla.recurso;

		camara = pantalla.camara;

		this.mundoVirtual = pantalla.mundoVirtual;

		dato = pantalla.dato;

		configuracion = pantalla.configuracion;

		datosNiveles = pantalla.datosNiveles;

		informacionNiveles = pantalla.informacionNiveles;

		this.tipoDeCuerpo = tipoDeCuerpo;

		BodyDef bodyDef = new BodyDef();

		FixtureDef fixtureDef = new FixtureDef();

		PolygonShape shape = new PolygonShape();

		shape.setAsBox(this.getWidth() / 2, this.getHeight() / 2);

		fixtureDef.shape = shape;

		if (tipoDeCuerpo == Personaje.ESTATICO) {

			bodyDef.type = BodyDef.BodyType.StaticBody;

			if (mundoVirtual != null) {

				cuerpo = mundoVirtual.createBody(bodyDef);

				cuerpo.setUserData(this);

				cuerpo.createFixture(fixtureDef);

			}

		}

		if (tipoDeCuerpo == Personaje.CINESTECICO) {

			bodyDef.type = BodyDef.BodyType.KinematicBody;

			if (mundoVirtual != null) {

				cuerpo = mundoVirtual.createBody(bodyDef);

				cuerpo.setUserData(this);

				cuerpo.createFixture(fixtureDef);

				cuerpo.setAngularVelocity(MathUtils.degreesToRadians * 360f);

			}

		}

		if (tipoDeCuerpo == Personaje.DIANAMICO) {

			bodyDef.type = BodyDef.BodyType.DynamicBody;

			fixtureDef.density = 1f;

			fixtureDef.friction = 0.5f;

			fixtureDef.restitution = 1f;

			if (mundoVirtual != null) {

				cuerpo = mundoVirtual.createBody(bodyDef);

				cuerpo.setUserData(this);

				cuerpo.createFixture(fixtureDef);

			}

		}

		shape.dispose();

	}

	public Personaje(TextureRegion texturaRegion, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {

		super(texturaRegion);

		setSize(ancho, alto);

		setOriginCenter();

		remover = false;

		animar = false;

		vivo = true;

		tiempo = 0;

		this.pantalla = pantalla;

		x = 0;

		y = 0;

		dureza = 3;

		personajes = pantalla.personajes;

		recurso = pantalla.recurso;

		camara = pantalla.camara;

		this.mundoVirtual = pantalla.mundoVirtual;

		dato = pantalla.dato;

		configuracion = pantalla.configuracion;

		datosNiveles = pantalla.datosNiveles;

		informacionNiveles = pantalla.informacionNiveles;

		this.tipoDeCuerpo = tipoDeCuerpo;

		BodyDef bodyDef = new BodyDef();

		FixtureDef fixtureDef = new FixtureDef();

		PolygonShape shape = new PolygonShape();

		shape.setAsBox(this.getWidth() / 2, this.getHeight() / 2);

		fixtureDef.shape = shape;

		if (tipoDeCuerpo == Personaje.ESTATICO) {

			bodyDef.type = BodyDef.BodyType.StaticBody;

			if (mundoVirtual != null) {

				cuerpo = mundoVirtual.createBody(bodyDef);

				cuerpo.setUserData(this);

				cuerpo.createFixture(fixtureDef);

			}

		}

		if (tipoDeCuerpo == Personaje.CINESTECICO) {

			bodyDef.type = BodyDef.BodyType.KinematicBody;

			if (mundoVirtual != null) {

				cuerpo = mundoVirtual.createBody(bodyDef);

				cuerpo.setUserData(this);

				cuerpo.createFixture(fixtureDef);

				cuerpo.setAngularVelocity(MathUtils.degreesToRadians * 360f);

			}

		}

		if (tipoDeCuerpo == Personaje.DIANAMICO) {

			bodyDef.type = BodyDef.BodyType.DynamicBody;

			fixtureDef.density = 1f;

			fixtureDef.friction = 0.5f;

			fixtureDef.restitution = 1f;

			if (mundoVirtual != null) {

				cuerpo = mundoVirtual.createBody(bodyDef);

				cuerpo.setUserData(this);

				cuerpo.createFixture(fixtureDef);

			}

		}

		shape.dispose();

	}

	public Personaje(Array<AtlasRegion> texturaRegion, float tiempoAnimacion, Animation.PlayMode modo,
			Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {

		setSize(ancho, alto);

		setOriginCenter();

		setRegion(texturaRegion.get(0));

		animacion = new Animation<TextureRegion>(tiempoAnimacion, texturaRegion);

		animacion.setPlayMode(modo);

		remover = false;

		vivo = true;

		animar = true;

		tiempo = 0;

		this.pantalla = pantalla;

		x = 0;

		y = 0;

		dureza = 3;

		personajes = pantalla.personajes;

		recurso = pantalla.recurso;

		camara = pantalla.camara;

		this.mundoVirtual = pantalla.mundoVirtual;

		dato = pantalla.dato;

		configuracion = pantalla.configuracion;

		datosNiveles = pantalla.datosNiveles;

		informacionNiveles = pantalla.informacionNiveles;

		this.tipoDeCuerpo = tipoDeCuerpo;

		BodyDef bodyDef = new BodyDef();

		FixtureDef fixtureDef = new FixtureDef();

		PolygonShape shape = new PolygonShape();

		shape.setAsBox(this.getWidth() / 2, this.getHeight() / 2);

		fixtureDef.shape = shape;

		if (tipoDeCuerpo == Personaje.ESTATICO) {

			bodyDef.type = BodyDef.BodyType.StaticBody;

			if (mundoVirtual != null) {

				cuerpo = mundoVirtual.createBody(bodyDef);

				cuerpo.setUserData(this);

				cuerpo.createFixture(fixtureDef);

			}

		}

		if (tipoDeCuerpo == Personaje.CINESTECICO) {

			bodyDef.type = BodyDef.BodyType.KinematicBody;

			if (mundoVirtual != null) {

				cuerpo = mundoVirtual.createBody(bodyDef);

				cuerpo.setUserData(this);

				cuerpo.createFixture(fixtureDef);

				cuerpo.setAngularVelocity(MathUtils.degreesToRadians * 360f);

			}

		}

		if (tipoDeCuerpo == Personaje.DIANAMICO) {

			bodyDef.type = BodyDef.BodyType.DynamicBody;

			fixtureDef.density = 1f;

			fixtureDef.friction = 0.5f;

			fixtureDef.restitution = 1f;

			if (mundoVirtual != null) {

				cuerpo = mundoVirtual.createBody(bodyDef);

				cuerpo.setUserData(this);

				cuerpo.createFixture(fixtureDef);

			}

		}

		shape.dispose();

	}

	public void dibujar(Batch pincel, float delta) {

		if (animar) {

			setRegion(animacion.getKeyFrame(tiempo, false));
		}

		draw(pincel);

	}

	public boolean isRemover() {

		return remover;
	}

	public void remover() {

		remover = true;
	}

	public void actualizar(float delta) {

		if (animar) {

			if (delta == 0) {

				return;

			}

			if (delta > 0.1f) {

				delta = 0.1f;
			}

			tiempo += delta;

		}

		if (tipoDeCuerpo == Personaje.CINESTECICO) {

			if (cuerpo != null) {

				x = cuerpo.getPosition().x - this.getWidth() / 2;

				y = cuerpo.getPosition().y - this.getHeight() / 2;

				setX(x);

				setY(y);

				setRotation(cuerpo.getAngle() * MathUtils.radiansToDegrees);

			}

		}

		if (tipoDeCuerpo == Personaje.DIANAMICO) {

			if (cuerpo != null) {

				x = cuerpo.getPosition().x - this.getWidth() / 2;

				y = cuerpo.getPosition().y - this.getHeight() / 2;

				setX(x);

				setY(y);

				setRotation(cuerpo.getAngle() * MathUtils.radiansToDegrees);

			}

		}

		if (tipoDeCuerpo == Personaje.ESTATICO) {

			setX(x);

			setY(y);

			if (cuerpo != null) {

				if (this.equals(cuerpo.getUserData())) {

					cuerpo.setTransform(this.x + this.getWidth() / 2, this.y + this.getHeight() / 2, 0);

				}

			}

		}

	}

	@Override
	public void setPosition(float x, float y) {

		super.setPosition(x / Juego.UNIDAD_DEL_MUNDO, y / Juego.UNIDAD_DEL_MUNDO);

		this.x = x / Juego.UNIDAD_DEL_MUNDO;

		this.y = y / Juego.UNIDAD_DEL_MUNDO;

		if (cuerpo != null) {

			if (this.equals(cuerpo.getUserData())) {

				cuerpo.setTransform(this.x + this.getWidth() / 2, this.y + this.getHeight() / 2, 0);
			}

		}

	}

	@Override
	public void setSize(float width, float height) {

		super.setSize(width / Juego.UNIDAD_DEL_MUNDO, height / Juego.UNIDAD_DEL_MUNDO);
	}

	public boolean isVivo() {

		return vivo;
	}

	public void setVivo(boolean vivo) {

		this.vivo = vivo;
	}

	public int getDureza() {
		return dureza;
	}

	public void setDureza(int dureza) {
		this.dureza = dureza;
	}

	public void setRemover(boolean remover) {
		this.remover = remover;
	}

	public abstract void colision(Personaje personaje);
}
