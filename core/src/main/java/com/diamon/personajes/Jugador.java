package com.diamon.personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Array;
import com.diamon.items.TipoPowerUp;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;
import com.diamon.nucleo.ZonaCorriente;

public class Jugador extends Personaje {

	private final Array<ZonaCorriente> corrientesActivas = new Array<ZonaCorriente>();

	private float deltaXTactil;

	private float deltaYTactil;

	private float x1;

	private float y1;

	private float velocidadX;

	private float velocidadY;

	private float velocidad;

	private static final float VELOCIDAD_JUGADOR = 5 / Juego.UNIDAD_DEL_MUNDO;

	private boolean arriba, abajo, izquierda, derecha, disparar, dispararBomba, dispararMisil;

	private int vida;

	private int misil;

	private int bomba;

	private int numeroDeSatelites;

	private float tiempoCuadroInmune;

	private float tiempoCuadroParpadeo;

	private boolean finNivel;

	private boolean terminarNivel;

	private boolean inmune;

	private boolean choque;

	private boolean intro;

	private boolean gefe;

	private int puntos;

	private int tipo;

	private boolean cambioTipo;

	private boolean itemVelocidad;

	private float velocidadCamaraItem;

	private int dedos;

	private boolean deltaToque;

	private boolean toqueActivo = false;

	private float toqueObjetivoX = 0f;

	private float toqueObjetivoY = 0f;

	private float entradaVirtualX = 0f;

	private float entradaVirtualY = 0f;

	private float tiempoTurbo;

	private float tiempoEscudo;

	private float tiempoLinterna;

	public Jugador(Array<AtlasRegion> texturaRegion, float tiempoAnimacion, PlayMode modo, Pantalla pantalla,
			float ancho, float alto, int tipoDeCuerpo) {
		super(texturaRegion, tiempoAnimacion, modo, pantalla, ancho, alto, tipoDeCuerpo);
		// TODO Auto-generated constructor stub

		misil = datosNiveles.getMisiles();

		bomba = datosNiveles.getBombas();

		vida = datosNiveles.getVidas();

		deltaToque = false;

		dedos = -1;

		tipo = 1;

		x1 = 0;

		y1 = 0;

		velocidadX = 0;

		velocidadY = 0;

		arriba = abajo = izquierda = derecha = disparar = dispararBomba = dispararMisil = false;

		velocidad = VELOCIDAD_JUGADOR;

		choque = false;

		inmune = false;

		finNivel = false;

		terminarNivel = false;

		intro = true;

		cambioTipo = false;

		itemVelocidad = false;

		velocidadCamaraItem = 1;

		numeroDeSatelites = datosNiveles.getNumeroSatelite();
	}

	public Jugador(Texture textura, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
		super(textura, pantalla, ancho, alto, tipoDeCuerpo);
		// TODO Auto-generated constructor stub

		misil = datosNiveles.getMisiles();

		bomba = datosNiveles.getBombas();

		vida = datosNiveles.getVidas();

		deltaToque = false;

		dedos = -1;

		tipo = 1;

		x1 = 0;

		y1 = 0;

		velocidadX = 0;

		velocidadY = 0;

		arriba = abajo = izquierda = derecha = disparar = dispararBomba = dispararMisil = false;

		velocidad = VELOCIDAD_JUGADOR;

		choque = false;

		inmune = false;

		finNivel = false;

		terminarNivel = false;

		intro = true;

		cambioTipo = false;

		itemVelocidad = false;

		velocidadCamaraItem = 1;

		numeroDeSatelites = datosNiveles.getNumeroSatelite();
	}

	public Jugador(TextureRegion texturaRegion, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
		super(texturaRegion, pantalla, ancho, alto, tipoDeCuerpo);

		misil = datosNiveles.getMisiles();

		bomba = datosNiveles.getBombas();

		vida = datosNiveles.getVidas();

		deltaToque = false;

		dedos = -1;

		tipo = 1;

		x1 = 0;

		y1 = 0;

		velocidadX = 0;

		velocidadY = 0;

		arriba = abajo = izquierda = derecha = disparar = dispararBomba = dispararMisil = false;

		velocidad = VELOCIDAD_JUGADOR;

		choque = false;

		inmune = false;

		finNivel = false;

		terminarNivel = false;

		intro = true;

		cambioTipo = false;

		itemVelocidad = false;

		velocidadCamaraItem = 1;

		numeroDeSatelites = datosNiveles.getNumeroSatelite();

	}

	public void resetearJugador() {

		x1 = 0;

		y1 = 0;

		velocidadX = 0;

		velocidadY = 0;

		deltaXTactil = 0;

		deltaYTactil = 0;

		disparar = false;

		dispararMisil = false;

		dispararBomba = false;

		deltaToque = false;

		inmune = false;

		velocidadCamaraItem = 1;

		dedos = -1;

	}

	public int getNumeroDeSatelites() {
		return numeroDeSatelites;
	}

	public void setNumeroDeSatelites(int numeroDeSatelites) {
		this.numeroDeSatelites = numeroDeSatelites;
	}

	public boolean isCambioTipo() {
		return cambioTipo;
	}

	public void setCambioTipo(boolean cambioTipo) {
		this.cambioTipo = cambioTipo;
	}

	public boolean isInmune() {
		return inmune;
	}

	public void setInmune(boolean inmune) {
		this.inmune = inmune;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public boolean isIntro() {
		return intro;
	}

	public void setIntro(boolean intro) {
		this.intro = intro;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public boolean isTerminarNivel() {

		return terminarNivel;
	}

	public void setTerminarNivel(boolean terminarNivel) {

		this.terminarNivel = terminarNivel;
	}

	public int getVida() {

		return vida;
	}

	public void setVida(int vida) {

		this.vida = vida;
	}

	public int getMisil() {
		return misil;
	}

	public void setMisil(int misil) {
		this.misil = misil;
	}

	public int getBomba() {
		return bomba;
	}

	public void setBomba(int bomba) {
		this.bomba = bomba;
	}

	public boolean teclaPresionada(InputEvent ev, int codigoTecla) {

		int key = (codigoTecla != 0) ? codigoTecla : (ev != null ? ev.getKeyCode() : 0);

		switch (key) {

		case Keys.LEFT:

			izquierda = true;

			break;

		case Keys.RIGHT:

			derecha = true;

			break;

		case Keys.UP:

			arriba = true;

			break;

		case Keys.DOWN:

			abajo = true;

			break;

		case Keys.Z:

			disparar = true;

			break;

		case Keys.X:

			dispararMisil = true;

			break;

		case Keys.SPACE:

			dispararBomba = true;

			break;

		default:

			break;

		}

		actualizarVelocidad();

		return true;
	}

	public boolean teclaLevantada(InputEvent ev, int codigoTecla) {

		int key = (codigoTecla != 0) ? codigoTecla : (ev != null ? ev.getKeyCode() : 0);

		switch (key) {

		case Keys.LEFT:

			izquierda = false;

			break;

		case Keys.RIGHT:

			derecha = false;

			break;

		case Keys.UP:

			arriba = false;

			break;

		case Keys.DOWN:

			abajo = false;

			break;

		case Keys.Z:

			disparar = false;

			break;

		case Keys.X:

			dispararMisil = false;

			break;

		case Keys.SPACE:

			dispararBomba = false;

			break;

		default:

			break;

		}

		actualizarVelocidad();

		return true;
	}

	public boolean teclaTipo(InputEvent ev, char caracter) {

		return true;

	}

	public boolean ratonMoviendo(InputEvent ev, float x, float y) {

		if (!finNivel) {

			if (!dato.isDiparoAutomatico()) {

				x1 = x / Juego.UNIDAD_DEL_MUNDO - deltaXTactil;

				y1 = y / Juego.UNIDAD_DEL_MUNDO - deltaYTactil;

				if (x1 <= camara.position.x - Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO) {

					x1 = camara.position.x - Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO;

				}

				if (x1 >= camara.position.x + (Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getWidth())) {

					x1 = camara.position.x + (Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getWidth());
				}

				if (y1 >= camara.position.y + (Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getHeight()
						- 32 / Juego.UNIDAD_DEL_MUNDO)) {

					y1 = camara.position.y + (Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getHeight()
							- 32 / Juego.UNIDAD_DEL_MUNDO);

				}

				if (y1 <= camara.position.y - Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO
						+ 32 / Juego.UNIDAD_DEL_MUNDO) {

					y1 = camara.position.y - Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO
							+ 32 / Juego.UNIDAD_DEL_MUNDO;

				}

				this.x = x1;

				this.y = y1;

			}

		}

		return true;

	}

	@SuppressWarnings("static-access")
	public void toqueDeslizando(InputEvent ev, float x, float y, int puntero) {

		if (!finNivel) {

			toqueActivo = true;
			toqueObjetivoX = camara.position.x + (x - Juego.ANCHO_PANTALLA / 2f) / Juego.UNIDAD_DEL_MUNDO;
			toqueObjetivoY = camara.position.y + (y - Juego.ALTO_PANTALLA / 2f) / Juego.UNIDAD_DEL_MUNDO;

			if (Gdx.app.getType() == Gdx.app.getType().Android) {

				if (dedos == 0) {

					if (deltaToque) {

						x1 = this.getX();

						y1 = this.getY();

						deltaXTactil = x / Juego.UNIDAD_DEL_MUNDO - x1;

						deltaYTactil = y / Juego.UNIDAD_DEL_MUNDO - y1;

						deltaToque = false;
					}

					x1 = x / Juego.UNIDAD_DEL_MUNDO - deltaXTactil;

					y1 = y / Juego.UNIDAD_DEL_MUNDO - deltaYTactil;

					if (x1 <= camara.position.x - Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO) {

						x1 = camara.position.x - Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO;

					}

					if (x1 >= camara.position.x + (Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getWidth())) {

						x1 = camara.position.x + (Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getWidth());
					}

					if (y1 >= camara.position.y + (Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getHeight()
							- 32 / Juego.UNIDAD_DEL_MUNDO)) {

						y1 = camara.position.y + (Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getHeight()
								- 32 / Juego.UNIDAD_DEL_MUNDO);

					}

					if (y1 <= camara.position.y - Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO
							+ 32 / Juego.UNIDAD_DEL_MUNDO) {

						y1 = camara.position.y - Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO
								+ 32 / Juego.UNIDAD_DEL_MUNDO;

					}

					this.x = x1;

					this.y = y1;

				}

			}

			if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

				x1 = x / Juego.UNIDAD_DEL_MUNDO - deltaXTactil;

				y1 = y / Juego.UNIDAD_DEL_MUNDO - deltaYTactil;

				if (x1 <= camara.position.x - Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO) {

					x1 = camara.position.x - Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO;

				}

				if (x1 >= camara.position.x + (Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getWidth())) {

					x1 = camara.position.x + (Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getWidth());
				}

				if (y1 >= camara.position.y + (Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getHeight()
						- 32 / Juego.UNIDAD_DEL_MUNDO)) {

					y1 = camara.position.y + (Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getHeight()
							- 32 / Juego.UNIDAD_DEL_MUNDO);

				}

				if (y1 <= camara.position.y - Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO
						+ 32 / Juego.UNIDAD_DEL_MUNDO) {

					y1 = camara.position.y - Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO
							+ 32 / Juego.UNIDAD_DEL_MUNDO;

				}

				this.x = x1;

				this.y = y1;

			}

		}

	}

	@SuppressWarnings("static-access")
	public boolean toqueLevantado(InputEvent ev, float x, float y, int puntero, int boton) {

		toqueActivo = false;

		if (!finNivel) {

			if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

				if (boton == 0) {

					disparar = false;

				}

				if (boton == 1) {

					dispararMisil = false;

				}

			}

			if (Gdx.app.getType() == Gdx.app.getType().Android) {

				deltaToque = true;

				dedos--;

				if (dedos <= 0) {

					disparar = false;

				}

			}

		}

		return true;

	}

	@SuppressWarnings("static-access")
	public boolean toquePresionado(InputEvent ev, float x, float y, int puntero, int boton) {

		if (!finNivel) {

			toqueActivo = true;
			toqueObjetivoX = camara.position.x + (x - Juego.ANCHO_PANTALLA / 2f) / Juego.UNIDAD_DEL_MUNDO;
			toqueObjetivoY = camara.position.y + (y - Juego.ALTO_PANTALLA / 2f) / Juego.UNIDAD_DEL_MUNDO;

			if (Gdx.app.getType() == Gdx.app.getType().Android) {

				dedos++;

				if (dedos == 0) {

					x1 = this.getX();

					y1 = this.getY();

					deltaXTactil = x / Juego.UNIDAD_DEL_MUNDO - x1;

					deltaYTactil = y / Juego.UNIDAD_DEL_MUNDO - y1;

					if (x1 <= camara.position.x - Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO) {

						x1 = camara.position.x - Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO;

					}

					if (y1 >= camara.position.y + (Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getHeight()
							- 32 / Juego.UNIDAD_DEL_MUNDO)) {

						y1 = camara.position.y + (Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getHeight()
								- 32 / Juego.UNIDAD_DEL_MUNDO);

					}

					if (y1 <= camara.position.y - Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO
							+ 32 / Juego.UNIDAD_DEL_MUNDO) {

						y1 = camara.position.y - Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO
								+ 32 / Juego.UNIDAD_DEL_MUNDO;

					}
					if (x1 >= camara.position.x + (Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getWidth())) {

						x1 = camara.position.x + (Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getWidth());
					}

					if (boton == 0) {

						disparar = true;

					}

				}

			}

			if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

				if (dato.isDiparoAutomatico()) {

					x1 = this.getX();

					y1 = this.getY();

					deltaXTactil = x / Juego.UNIDAD_DEL_MUNDO - x1;

					deltaYTactil = y / Juego.UNIDAD_DEL_MUNDO - y1;

				}

				if (x1 <= camara.position.x - Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO) {

					x1 = camara.position.x - Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO;

				}

				if (y1 >= camara.position.y + (Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getHeight()
						- 32 / Juego.UNIDAD_DEL_MUNDO)) {

					y1 = camara.position.y + (Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getHeight()
							- 32 / Juego.UNIDAD_DEL_MUNDO);

				}

				if (y1 <= camara.position.y - Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO
						+ 32 / Juego.UNIDAD_DEL_MUNDO) {

					y1 = camara.position.y - Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO
							+ 32 / Juego.UNIDAD_DEL_MUNDO;

				}
				if (x1 >= camara.position.x + (Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getWidth())) {

					x1 = camara.position.x + (Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getWidth());
				}

				if (boton == 0) {

					disparar = true;

				}

				if (boton == 1) {

					dispararMisil = true;

				}

			}

		}

		return true;

	}

	public void actualizar(float delta) {

		super.actualizar(delta);

		if (tiempoTurbo > 0) tiempoTurbo = Math.max(0f, tiempoTurbo - delta);
		if (tiempoEscudo > 0) tiempoEscudo = Math.max(0f, tiempoEscudo - delta);
		if (tiempoLinterna > 0) tiempoLinterna = Math.max(0f, tiempoLinterna - delta);

		if (cuerpo != null && cuerpo.getType() == com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody) {
			cuerpo.setGravityScale(0f);
			cuerpo.setLinearDamping(com.diamon.nucleo.Constantes.AMORTIGUACION_AGUA);

			for (int i = 0; i < corrientesActivas.size; i++) {
				ZonaCorriente corriente = corrientesActivas.get(i);
				cuerpo.applyForceToCenter(corriente.getFuerza(), true);
			}

			float fuerzaBase = isTurboActivo() ? 70.0f : 38.0f;
			float fx = 0f;
			float fy = 0f;
			if (derecha) fx += fuerzaBase;
			if (izquierda) fx -= fuerzaBase;
			if (arriba) fy += fuerzaBase;
			if (abajo) fy -= fuerzaBase;

			if (entradaVirtualX != 0f || entradaVirtualY != 0f) {
				fx += entradaVirtualX * fuerzaBase;
				fy += entradaVirtualY * fuerzaBase;
			}

			if (toqueActivo) {
				float px = cuerpo.getPosition().x;
				float py = cuerpo.getPosition().y;
				float dx = toqueObjetivoX - px;
				float dy = toqueObjetivoY - py;
				float dist = (float) Math.sqrt(dx * dx + dy * dy);
				if (dist > 0.35f) {
					fx += (dx / dist) * fuerzaBase;
					fy += (dy / dist) * fuerzaBase;
				}
			}

			if (fx != 0 || fy != 0) {
				cuerpo.applyForceToCenter(fx, fy, true);
			}

			com.badlogic.gdx.math.Vector2 vel = cuerpo.getLinearVelocity();
			if (vel.x < -0.15f) {
				setFlip(true, false);
			} else if (vel.x > 0.15f) {
				setFlip(false, false);
			}

			// Inclinación sutil hidrodinámica (el pez se mantiene natural y horizontal)
			float pitchObjetivo = 0f;
			if (Math.abs(vel.y) > 0.2f) {
				pitchObjetivo = com.badlogic.gdx.math.MathUtils.clamp(vel.y * 2.8f, -15f, 15f);
				if (isFlipX()) {
					pitchObjetivo = -pitchObjetivo;
				}
			}
			float rotNueva = com.badlogic.gdx.math.MathUtils.lerp(getRotation(), pitchObjetivo, 0.18f);
			setRotation(rotNueva);
		}

		if (!finNivel) {

			if (intro) {

				if (!gefe) {

					if (itemVelocidad) {

						deltaXTactil -= velocidadCamaraItem / Juego.UNIDAD_DEL_MUNDO / Juego.DELTA_A_PIXEL * delta;

					} else {

						deltaXTactil -= Juego.VELOCIDAD_CAMARA / Juego.UNIDAD_DEL_MUNDO / Juego.DELTA_A_PIXEL * delta;

					}

				}

			}

			if (!gefe) {

				if (itemVelocidad) {

					x += velocidadCamaraItem / Juego.UNIDAD_DEL_MUNDO / Juego.DELTA_A_PIXEL * delta;

				} else {

					x += Juego.VELOCIDAD_CAMARA / Juego.UNIDAD_DEL_MUNDO / Juego.DELTA_A_PIXEL * delta;

				}

			}

			x += velocidadX / Juego.DELTA_A_PIXEL * delta;

			y += velocidadY / Juego.DELTA_A_PIXEL * delta;

			// cuerpo.setGravityScale(0);

			/*
			 * cuerpo.setLinearVelocity((Juego.VELOCIDAD_CAMARA / Juego.UNIDAD_DEL_MUNDO /
			 * Juego.DELTA_A_PIXEL * delta) Juego.UNIDAD_DEL_MUNDO * (Juego.FPS /
			 * Juego.UNIDAD_DEL_MUNDO) + velocidadX * Juego.UNIDAD_DEL_MUNDO, velocidadY *
			 * Juego.UNIDAD_DEL_MUNDO);
			 */

			if (x >= camara.position.x + (Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getWidth())) {

				x = camara.position.x + (Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getWidth());

			}

			if (x <= camara.position.x - Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO) {

				if (intro) {

					x = camara.position.x - Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO;

				}

			}

			if (y >= camara.position.y
					+ (Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getHeight() - 32 / Juego.UNIDAD_DEL_MUNDO)) {

				y = camara.position.y + (Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getHeight()
						- 32 / Juego.UNIDAD_DEL_MUNDO);

			}

			if (y <= camara.position.y - Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO
					+ 32 / Juego.UNIDAD_DEL_MUNDO) {

				y = camara.position.y - Juego.ALTO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO + 32 / Juego.UNIDAD_DEL_MUNDO;

			}

			for (int i = 0; i < personajes.size; i++) {

				if (personajes.get(i) instanceof Terreno) {

					if (getBoundingRectangle().overlaps(personajes.get(i).getBoundingRectangle())) {

						if (velocidadX > 0) {

							if (getX() + this.getWidth() - velocidadX
									- Juego.VELOCIDAD_CAMARA / Juego.UNIDAD_DEL_MUNDO <= personajes.get(i).getX()) {

								x = getX() - velocidadX;

								velocidadX = 0;

								break;

							}

						} else

						{

							if (getX() + this.getWidth() - Juego.VELOCIDAD_CAMARA / Juego.UNIDAD_DEL_MUNDO
									- velocidadX <= personajes.get(i).getX()) {

								x = getX();

								velocidadX = 0;

							}

						}

						if (getX() - velocidadX >= personajes.get(i).getX() + personajes.get(i).getWidth()) {

							x = getX();

							velocidadX = 0;

							break;

						}

						if (getY() + this.getHeight() - velocidadY <= personajes.get(i).getY()) {

							y = getY();

							velocidadY = 0;

							break;

						}

						if (getY() - velocidadY >= personajes.get(i).getY() + personajes.get(i).getHeight()) {

							y = getY();

							velocidadY = 0;

							break;

						}

					}

				}

			}

			if (choque) {

				tiempoCuadroInmune += delta;

				tiempoCuadroParpadeo += delta;

				if (tiempoCuadroParpadeo / 0.08f >= 1) {

					setAlpha(0);

					tiempoCuadroParpadeo = 0;

				} else {

					setAlpha(1);
				}

				if (tiempoCuadroInmune / 3.33f >= 1) {

					setAlpha(1);

					inmune = false;

					choque = false;

					tiempoCuadroInmune = 0;

				}

			}

		} else {

			x += Juego.VELOCIDAD_CAMARA / Juego.DELTA_A_PIXEL * delta / Juego.UNIDAD_DEL_MUNDO;

			if (x >= camara.position.x + (Juego.ANCHO_PANTALLA / 2 / Juego.UNIDAD_DEL_MUNDO - getWidth())) {

				vivo = false;

				terminarNivel = true;
			}
		}

		if (vida <= 0) {

			vivo = false;

			remover = true;
		}

	}

	public boolean isItemVelocidad() {
		return itemVelocidad;
	}

	public void setItemVelocidad(boolean itemVelocidad) {
		this.itemVelocidad = itemVelocidad;
	}

	public float getVelocidadCamaraItem() {
		return velocidadCamaraItem;
	}

	public void setVelocidadCamaraItem(float velocidadCamaraItem) {
		this.velocidadCamaraItem = velocidadCamaraItem;
	}

	public boolean isGefe() {
		return gefe;
	}

	public void setGefe(boolean gefe) {
		this.gefe = gefe;
	}

	public boolean isFinNivel() {

		return finNivel;
	}

	public void setFinNivel(boolean finNivel) {

		this.finNivel = finNivel;
	}

	public boolean isDispararBomba() {
		return dispararBomba;
	}

	public void setDispararBomba(boolean dispararBomba) {
		this.dispararBomba = dispararBomba;
	}

	public boolean isDispararMisil() {
		return dispararMisil;
	}

	public void setDispararMisil(boolean dispararMisil) {
		this.dispararMisil = dispararMisil;
	}

	public void colision(Personaje actor) {

	}

	private void actualizarVelocidad() {

		velocidadX = 0;

		velocidadY = 0;

		if (abajo) {

			velocidadY = -velocidad;
		}

		if (arriba) {

			velocidadY = velocidad;
		}

		if (izquierda) {

			velocidadX = -velocidad;

		}

		if (derecha) {

			velocidadX = velocidad;

		}

	}

	public void agregarCorriente(ZonaCorriente corriente) {
		if (!corrientesActivas.contains(corriente, true)) {
			corrientesActivas.add(corriente);
		}
	}

	public void removerCorriente(ZonaCorriente corriente) {
		corrientesActivas.removeValue(corriente, true);
	}

	public void agregarPuntos(int cantidad) {
		puntos += cantidad;
		if (datosNiveles != null) {
			datosNiveles.setPuntos(puntos);
		}
	}

	public void recuperarVida(int cantidad) {
		vida = Math.min(vida + cantidad, 5);
		if (datosNiveles != null) {
			datosNiveles.setVidas(vida);
		}
	}

	public void recibirDanio(int cantidad) {
		if (inmune) {
			return;
		}
		if (isEscudoActivo()) {
			tiempoEscudo = 0;
			inmune = true;
			tiempoCuadroInmune = 0;
			choque = true;
			activarHitFlash(0.16f, new Color(0.35f, 0.85f, 1.0f, 1.0f));
			if (pantalla != null) {
				pantalla.sacudirCamara(0.25f);
			}
			return;
		}
		vida = Math.max(0, vida - cantidad);
		if (datosNiveles != null) {
			datosNiveles.setVidas(vida);
		}
		inmune = true;
		tiempoCuadroInmune = 0;
		choque = true;
		activarHitFlash(0.16f, new Color(1.0f, 0.35f, 0.35f, 1.0f));
		if (pantalla != null) {
			pantalla.sacudirCamara(0.45f);
		}
		if (vida <= 0) {
			setVivo(false);
		}
	}

	public void activarPowerUp(TipoPowerUp tipo, float duracion) {
		if (tipo == null) return;
		switch (tipo) {
		case TURBO:
			tiempoTurbo = duracion;
			break;
		case ESCUDO:
			tiempoEscudo = duracion;
			break;
		case LINTERNA:
			tiempoLinterna = duracion;
			break;
		}
	}

	public boolean isTurboActivo() {
		return tiempoTurbo > 0;
	}

	public boolean isEscudoActivo() {
		return tiempoEscudo > 0;
	}

	public boolean isLinternaActiva() {
		return tiempoLinterna > 0;
	}

	public float getTiempoRestantePowerUp(TipoPowerUp tipo) {
		if (tipo == null) return 0f;
		switch (tipo) {
		case TURBO:
			return Math.max(0f, tiempoTurbo);
		case ESCUDO:
			return Math.max(0f, tiempoEscudo);
		case LINTERNA:
			return Math.max(0f, tiempoLinterna);
		default:
			return 0f;
		}
	}

	public void setEntradaVirtual(float x, float y) {
		this.entradaVirtualX = x;
		this.entradaVirtualY = y;
	}

	public Array<ZonaCorriente> getCorrientesActivas() {
		return corrientesActivas;
	}

}
