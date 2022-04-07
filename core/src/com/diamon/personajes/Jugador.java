package com.diamon.personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Array;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;

public class Jugador extends Personaje {

	private float deltaXTactil;

	private float deltaYTactil;

	private float x1;

	private float y1;

	private float velocidadX;

	private float velocidadY;

	private float velocidad;

	private static final int VELOCIDAD_JUGADOR = 5;

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

	public Jugador(Texture texture, Pantalla pantalla) {
		super(texture, pantalla);

		misil = dato.getMisiles();

		bomba = dato.getBombas();

		vida = dato.getVidas();

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

		numeroDeSatelites = dato.getNumeroSatelite();

	}

	public Jugador(TextureRegion region, Pantalla pantalla) {
		super(region, pantalla);

		misil = dato.getMisiles();

		bomba = dato.getBombas();

		vida = dato.getVidas();

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

		numeroDeSatelites = dato.getNumeroSatelite();

	}

	public Jugador(Array<TextureAtlas.AtlasRegion> texturaRegion, float tiempoAnimacion, Animation.PlayMode modo,
			Pantalla pantalla) {
		super(texturaRegion, tiempoAnimacion, modo, pantalla);

		misil = dato.getMisiles();

		bomba = dato.getBombas();

		vida = dato.getVidas();

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

		numeroDeSatelites = dato.getNumeroSatelite();

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

		switch (ev.getKeyCode()) {

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

		switch (ev.getKeyCode()) {

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

				x1 = x - deltaXTactil;

				y1 = y - deltaYTactil;

				if (x1 <= camara.position.x - Juego.ANCHO_PANTALLA / 2) {

					x1 = camara.position.x - Juego.ANCHO_PANTALLA / 2;

				}

				if (x1 >= camara.position.x + (Juego.ANCHO_PANTALLA / 2 - getWidth())) {

					x1 = camara.position.x + (Juego.ANCHO_PANTALLA / 2 - getWidth());
				}

				if (y1 >= camara.position.y + (Juego.ALTO_PANTALLA / 2 - getHeight() - 32)) {

					y1 = camara.position.y + (Juego.ALTO_PANTALLA / 2 - getHeight() - 32);

				}

				if (y1 <= camara.position.y - Juego.ALTO_PANTALLA / 2 + 32) {

					y1 = camara.position.y - Juego.ALTO_PANTALLA / 2 + 32;

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

			if (Gdx.app.getType() == Gdx.app.getType().Android) {

				if (dedos == 0) {

					if (deltaToque) {

						x1 = this.getX();

						y1 = this.getY();

						deltaXTactil = x - x1;

						deltaYTactil = y - y1;

						deltaToque = false;
					}

					x1 = x - deltaXTactil;

					y1 = y - deltaYTactil;

					if (x1 <= camara.position.x - Juego.ANCHO_PANTALLA / 2) {

						x1 = camara.position.x - Juego.ANCHO_PANTALLA / 2;

					}

					if (x1 >= camara.position.x + (Juego.ANCHO_PANTALLA / 2 - getWidth())) {

						x1 = camara.position.x + (Juego.ANCHO_PANTALLA / 2 - getWidth());
					}

					if (y1 >= camara.position.y + (Juego.ALTO_PANTALLA / 2 - getHeight() - 32)) {

						y1 = camara.position.y + (Juego.ALTO_PANTALLA / 2 - getHeight() - 32);

					}

					if (y1 <= camara.position.y - Juego.ALTO_PANTALLA / 2 + 32) {

						y1 = camara.position.y - Juego.ALTO_PANTALLA / 2 + 32;

					}

					this.x = x1;

					this.y = y1;

				}

			}

			if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

				x1 = x - deltaXTactil;

				y1 = y - deltaYTactil;

				if (x1 <= camara.position.x - Juego.ANCHO_PANTALLA / 2) {

					x1 = camara.position.x - Juego.ANCHO_PANTALLA / 2;

				}

				if (x1 >= camara.position.x + (Juego.ANCHO_PANTALLA / 2 - getWidth())) {

					x1 = camara.position.x + (Juego.ANCHO_PANTALLA / 2 - getWidth());
				}

				if (y1 >= camara.position.y + (Juego.ALTO_PANTALLA / 2 - getHeight() - 32)) {

					y1 = camara.position.y + (Juego.ALTO_PANTALLA / 2 - getHeight() - 32);

				}

				if (y1 <= camara.position.y - Juego.ALTO_PANTALLA / 2 + 32) {

					y1 = camara.position.y - Juego.ALTO_PANTALLA / 2 + 32;

				}

				this.x = x1;

				this.y = y1;

			}

		}

	}

	@SuppressWarnings("static-access")
	public boolean toqueLevantado(InputEvent ev, float x, float y, int puntero, int boton) {

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

				if (dedos == -1) {

					disparar = false;

				}

			}

		}

		return true;

	}

	@SuppressWarnings("static-access")
	public boolean toquePresionado(InputEvent ev, float x, float y, int puntero, int boton) {

		if (!finNivel) {

			if (Gdx.app.getType() == Gdx.app.getType().Android) {

				dedos++;

				if (dedos == 0) {

					x1 = this.getX();

					y1 = this.getY();

					deltaXTactil = x - x1;

					deltaYTactil = y - y1;

					if (x1 <= camara.position.x - Juego.ANCHO_PANTALLA / 2) {

						x1 = camara.position.x - Juego.ANCHO_PANTALLA / 2;

					}

					if (y1 >= camara.position.y + (Juego.ALTO_PANTALLA / 2 - getHeight() - 32)) {

						y1 = camara.position.y + (Juego.ALTO_PANTALLA / 2 - getHeight() - 32);

					}

					if (y1 <= camara.position.y - Juego.ALTO_PANTALLA / 2 + 32) {

						y1 = camara.position.y - Juego.ALTO_PANTALLA / 2 + 32;

					}
					if (x1 >= camara.position.x + (Juego.ANCHO_PANTALLA / 2 - getWidth())) {

						x1 = camara.position.x + (Juego.ANCHO_PANTALLA / 2 - getWidth());
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

					deltaXTactil = x - x1;

					deltaYTactil = y - y1;

				}

				if (x1 <= camara.position.x - Juego.ANCHO_PANTALLA / 2) {

					x1 = camara.position.x - Juego.ANCHO_PANTALLA / 2;

				}

				if (y1 >= camara.position.y + (Juego.ALTO_PANTALLA / 2 - getHeight() - 32)) {

					y1 = camara.position.y + (Juego.ALTO_PANTALLA / 2 - getHeight() - 32);

				}

				if (y1 <= camara.position.y - Juego.ALTO_PANTALLA / 2 + 32) {

					y1 = camara.position.y - Juego.ALTO_PANTALLA / 2 + 32;

				}
				if (x1 >= camara.position.x + (Juego.ANCHO_PANTALLA / 2 - getWidth())) {

					x1 = camara.position.x + (Juego.ANCHO_PANTALLA / 2 - getWidth());
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

		if (!finNivel) {

			if (intro) {

				if (!gefe) {

					if (itemVelocidad) {

						deltaXTactil -= velocidadCamaraItem / Juego.DELTA_A_PIXEL * delta;

					} else {

						deltaXTactil -= Juego.VELOCIDAD_CAMARA / Juego.DELTA_A_PIXEL * delta;

					}

				}

			}

			if (!gefe) {

				if (itemVelocidad) {

					x += velocidadCamaraItem / Juego.DELTA_A_PIXEL * delta;

				} else {

					x += Juego.VELOCIDAD_CAMARA / Juego.DELTA_A_PIXEL * delta;

				}

			}

			x += velocidadX / Juego.DELTA_A_PIXEL * delta;

			y += velocidadY / Juego.DELTA_A_PIXEL * delta;

			if (x >= camara.position.x + (Juego.ANCHO_PANTALLA / 2 - getWidth())) {

				x = camara.position.x + (Juego.ANCHO_PANTALLA / 2 - getWidth());

			}

			if (x <= camara.position.x - Juego.ANCHO_PANTALLA / 2) {

				if (intro) {

					x = camara.position.x - Juego.ANCHO_PANTALLA / 2;

				}

			}

			if (y >= camara.position.y + (Juego.ALTO_PANTALLA / 2 - getHeight() - 32)) {

				y = camara.position.y + (Juego.ALTO_PANTALLA / 2 - getHeight() - 32);

			}

			if (y <= camara.position.y - Juego.ALTO_PANTALLA / 2 + 32) {

				y = camara.position.y - Juego.ALTO_PANTALLA / 2 + 32;

			}

			for (int i = 0; i < personajes.size; i++) {

				if (personajes.get(i) instanceof Terreno) {

					if (getBoundingRectangle().overlaps(personajes.get(i).getBoundingRectangle())) {

						if (velocidadX > 0) {

							if (getX() + this.getWidth() - velocidadX - Juego.VELOCIDAD_CAMARA <= personajes.get(i)
									.getX()) {

								x = getX() - velocidadX;

								velocidadX = 0;

								break;

							}

						} else

						{

							if (getX() + this.getWidth() - Juego.VELOCIDAD_CAMARA - velocidadX <= personajes.get(i)
									.getX()) {

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

			x += Juego.VELOCIDAD_CAMARA / Juego.DELTA_A_PIXEL * delta;

			if (x >= camara.position.x + (Juego.ANCHO_PANTALLA / 2 - getWidth())) {

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

}
