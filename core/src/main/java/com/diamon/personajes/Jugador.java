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

	private float dirTactilX = 0f;

	private float dirTactilY = 0f;

	private float dirTeclasX = 0f;

	private float dirTeclasY = 0f;

	private float tiempoIdle = 0f;

	private boolean tocando = false;

	private float toqueActualX = 0f;

	private float toqueActualY = 0f;

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
		case Keys.A:

			izquierda = true;

			break;

		case Keys.RIGHT:
		case Keys.D:

			derecha = true;

			break;

		case Keys.UP:
		case Keys.W:

			arriba = true;

			break;

		case Keys.DOWN:
		case Keys.S:

			abajo = true;

			break;

		case Keys.SPACE:
		case Keys.SHIFT_LEFT:
		case Keys.SHIFT_RIGHT:

			darImpulso();

			break;

		case Keys.E:

			activarSonar();

			break;

		case Keys.Q:

			activarEscudo();

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
		case Keys.A:

			izquierda = false;

			break;

		case Keys.RIGHT:
		case Keys.D:

			derecha = false;

			break;

		case Keys.UP:
		case Keys.W:

			arriba = false;

			break;

		case Keys.DOWN:
		case Keys.S:

			abajo = false;

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

	private void actualizarVectorTactil(float screenX, float screenY) {
		if (camara == null) return;
		float fishScreenX = (this.x - camara.position.x) * Juego.UNIDAD_DEL_MUNDO + Juego.ANCHO_PANTALLA / 2f;
		float fishScreenY = (this.y - camara.position.y) * Juego.UNIDAD_DEL_MUNDO + Juego.ALTO_PANTALLA / 2f;

		float dx = screenX - fishScreenX;
		float dy = screenY - fishScreenY;
		float dist = (float) Math.hypot(dx, dy);

		if (dist > 18f) {
			float maxPull = 140f;
			float intensidad = Math.min(1.0f, dist / maxPull);
			dirTactilX = (dx / dist) * intensidad;
			dirTactilY = (dy / dist) * intensidad;
		} else {
			dirTactilX = 0f;
			dirTactilY = 0f;
		}
	}

	public void toqueDeslizando(InputEvent ev, float x, float y, int puntero) {
		if (!finNivel) {
			tocando = true;
			toqueActualX = x;
			toqueActualY = y;
			actualizarVectorTactil(x, y);
		}
	}

	public boolean toqueLevantado(InputEvent ev, float x, float y, int puntero, int boton) {
		tocando = false;
		dirTactilX = 0f;
		dirTactilY = 0f;
		return true;
	}

	public boolean toquePresionado(InputEvent ev, float x, float y, int puntero, int boton) {
		if (!finNivel) {
			tocando = true;
			toqueActualX = x;
			toqueActualY = y;
			actualizarVectorTactil(x, y);
		}
		return true;
	}

	@Override
	public void actualizar(float delta) {
		super.actualizar(delta);

		if (tiempoTurbo > 0) tiempoTurbo = Math.max(0f, tiempoTurbo - delta);
		if (tiempoEscudo > 0) tiempoEscudo = Math.max(0f, tiempoEscudo - delta);
		if (tiempoLinterna > 0) tiempoLinterna = Math.max(0f, tiempoLinterna - delta);

		// Corrientes marinas activas
		for (int i = 0; i < corrientesActivas.size; i++) {
			ZonaCorriente corriente = corrientesActivas.get(i);
			x += (corriente.getFuerza().x / com.diamon.nucleo.Constantes.PPM) * delta * 2.0f;
			y += (corriente.getFuerza().y / com.diamon.nucleo.Constantes.PPM) * delta * 2.0f;
		}

		if (!finNivel) {
			if (tocando) {
				actualizarVectorTactil(toqueActualX, toqueActualY);
			}

			float inputX = (dirTactilX != 0f) ? dirTactilX : dirTeclasX;
			float inputY = (dirTactilY != 0f) ? dirTactilY : dirTeclasY;

			float multTurbo = isTurboActivo() ? 1.85f : 1.0f;
			float velMax = 5.2f * multTurbo;
			float targetVx = inputX * velMax;
			float targetVy = inputY * velMax;

			float aceleracion = (inputX != 0 || inputY != 0) ? 8.0f : 3.5f;
			velocidadX = com.badlogic.gdx.math.MathUtils.lerp(velocidadX, targetVx, aceleracion * delta);
			velocidadY = com.badlogic.gdx.math.MathUtils.lerp(velocidadY, targetVy, aceleracion * delta);

			if (inputX == 0 && inputY == 0) {
				velocidadX *= Math.pow(0.88, delta * 60);
				velocidadY *= Math.pow(0.88, delta * 60);
			}

			// Desplazamiento por velocidad propia
			x += velocidadX * delta;
			y += velocidadY * delta;

			// Micro-flotabilidad en reposo (respiración acuática de Little Nemo)
			if (Math.hypot(velocidadX, velocidadY) < 0.25f) {
				tiempoIdle += delta;
				y += com.badlogic.gdx.math.MathUtils.sin(tiempoIdle * 2.2f) * 0.035f * delta * 60f;
			} else {
				tiempoIdle = 0f;
			}

			// Orientación e inclinación 360° fluida (estilo Hungry Shark)
			orientarHaciaDireccion(velocidadX, velocidadY, true, delta);

			// Delimitación libre en el océano abierto (sin atrapar al jugador en un recuadro de cámara)
			float anchoOceano = 300f;
			float altoOceano = 68f;
			x = com.badlogic.gdx.math.MathUtils.clamp(x, 0.5f, anchoOceano - getWidth() - 0.5f);
			y = com.badlogic.gdx.math.MathUtils.clamp(y, 0.8f, altoOceano - getHeight() - 0.5f);

			if (cuerpo != null) {
				cuerpo.setTransform(x + getWidth() / 2f, y + getHeight() / 2f, getRotation() * com.badlogic.gdx.math.MathUtils.degreesToRadians);
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
				if (tiempoCuadroInmune / 2.5f >= 1) {
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

	public float getVelocidadX() {
		if (cuerpo != null) {
			return cuerpo.getLinearVelocity().x;
		}
		return velocidadX;
	}

	public float getVelocidadY() {
		if (cuerpo != null) {
			return cuerpo.getLinearVelocity().y;
		}
		return velocidadY;
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

	public void darImpulso() {
		activarPowerUp(TipoPowerUp.TURBO, 2.0f);
		float dir = isFlipX() ? -1f : 1f;
		velocidadX = dir * 8.5f;
		activarHitFlash(0.16f, new Color(1.0f, 0.8f, 0.2f, 1.0f));
	}

	public void activarSonar() {
		activarPowerUp(TipoPowerUp.LINTERNA, 8.0f);
		activarHitFlash(0.25f, new Color(0.3f, 0.85f, 1.0f, 1.0f));
		if (personajes != null) {
			for (Personaje p : personajes) {
				if (p instanceof TiburonAzul) {
					float d = (float) Math.hypot(p.getX() - x, p.getY() - y);
					if (d < 10.0f) {
						((TiburonAzul) p).aturdir(3.5f);
					}
				}
			}
		}
		if (pantalla instanceof com.diamon.pantallas.PantallaJuego) {
			((com.diamon.pantallas.PantallaJuego) pantalla).emitirSonar(x + getWidth() / 2f, y + getHeight() / 2f);
		}
	}

	public void activarEscudo() {
		activarPowerUp(TipoPowerUp.ESCUDO, 15.0f);
		activarHitFlash(0.2f, new Color(0.25f, 0.75f, 1.0f, 1.0f));
	}

	public Array<ZonaCorriente> getCorrientesActivas() {
		return corrientesActivas;
	}

}
