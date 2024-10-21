package com.diamon.datos;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.diamon.nucleo.Personaje;

public class DatosNiveles {

	// item del juego
	public final static String BALA = "com.diamon.personajes.Bala";

	// efectos especiales del juego
	public final static String EXPLOSION = "com.diamon.personajes.Explosion";

	// escenario del juego
	public final static String FONDO = "com.diamon.personajes.Fondo";
	public final static String TERRENO = "com.diamon.personajes.Terreno";
	public final static String BOMBA = "com.diamon.personajes.Bomba";
	public final static String ALGAS = "com.diamon.personajes.Algas";

	// UI del juego
	public final static String CURSOR = "com.diamon.personajes.Cursor";
	// personajes del juego
	public final static String JUGADOR = "com.diamon.personajes.Jugador";
	public final static String PULPO = "com.diamon.personajes.Pulpo";
	public final static String PEZ_ANGEL = "com.diamon.personajes.PezAngel";
	public final static String PEZ_GOBO_NARANJA = "com.diamon.personajes.PezGloboNaranja";
	public final static String PEZ_GLOBO_AMARILLO = "com.diamon.personajes.PezGloboAmarillo";

	// helicopteros del juego
	public final static int PEZ_PAYASO = 1;

	private final static short NUMERO_NIVELES = 40;

	private int numeroNivel;

	private boolean leerDatosAsset;

	private int[] puntuaciones;

	private String[] estadoPuntuaciones;

	private String[] numeroNivelPuntuaciones;

	private int puntos;

	private int pez;

	private int misiles;

	private int bombas;

	private int vidas;

	private int numeroSatelite;

	private Array<Vector2>[] posicionActores;

	private Array<String>[] tipoActores;

	@SuppressWarnings("unchecked")
	public DatosNiveles() {

		pez = 1;

		misiles = 10;

		bombas = 10;

		vidas = 3;

		puntos = 0;

		numeroSatelite = 0;

		leerDatosAsset = true;

		numeroNivel = 1;

		puntuaciones = new int[10];

		estadoPuntuaciones = new String[10];

		numeroNivelPuntuaciones = new String[10];

		for (int i = 0; i < puntuaciones.length; i++) {

			estadoPuntuaciones[i] = "----";

			numeroNivelPuntuaciones[i] = "----";

		}

		posicionActores = new Array[DatosNiveles.NUMERO_NIVELES];

		tipoActores = new Array[DatosNiveles.NUMERO_NIVELES];

		for (int i = 0; i < posicionActores.length; i++) {

			posicionActores[i] = new Array<Vector2>();

		}

		for (int i = 0; i < tipoActores.length; i++) {

			tipoActores[i] = new Array<String>();

		}

	}

	public int getNumeroSatelite() {
		return numeroSatelite;
	}

	public void setNumeroSatelite(int numeroSatelite) {
		this.numeroSatelite = numeroSatelite;
	}

	public int getMisiles() {
		return misiles;
	}

	public void setMisiles(int misiles) {
		this.misiles = misiles;
	}

	public int getBombas() {
		return bombas;
	}

	public void setBombas(int bombas) {
		this.bombas = bombas;
	}

	public int getVidas() {
		return vidas;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
	}

	public String[] getEstadoPuntuaciones() {
		return estadoPuntuaciones;
	}

	public void setEstadoPuntuaciones(String[] estadoPuntuaciones) {
		this.estadoPuntuaciones = estadoPuntuaciones;
	}

	public String[] getNumeroNivelPuntuaciones() {
		return numeroNivelPuntuaciones;
	}

	public void setNumeroNivelPuntuaciones(String[] numeroNivelPuntuaciones) {
		this.numeroNivelPuntuaciones = numeroNivelPuntuaciones;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int[] getPuntuaciones() {
		return puntuaciones;
	}

	public int getPez() {
		return pez;
	}

	public void setPez(int pez) {
		this.pez = pez;
	}

	public int getNumeroNivel() {
		return numeroNivel;
	}

	public void setNumeroNivel(int numeroNivel) {
		this.numeroNivel = numeroNivel;
	}

	public boolean isLeerDatosAsset() {
		return leerDatosAsset;
	}

	public void setLeerDatosAsset(boolean leerDatosAsset) {
		this.leerDatosAsset = leerDatosAsset;
	}

	public void anadirPuntuaciones(int puntuacion, int numeroNiivel, String texto) {

		for (int i = 0; i < puntuaciones.length; i++) {
			if (puntuaciones[i] < puntuacion) {
				for (int j = (puntuaciones.length - 1); j > i; j--) {

					puntuaciones[j] = puntuaciones[j - 1];

					estadoPuntuaciones[j] = estadoPuntuaciones[j - 1];

					numeroNivelPuntuaciones[j] = numeroNivelPuntuaciones[j - 1];

				}
				puntuaciones[i] = puntuacion;

				estadoPuntuaciones[i] = texto;

				numeroNivelPuntuaciones[i] = "Nivel " + numeroNiivel;

				break;
			}

		}

	}

	public void gurdarActores(Array<Personaje> personajes, String tipoActor, String nivel) {

		String ni = "";

		int n = 1;

		for (int i = 0; i < posicionActores.length; i++) {

			ni = "Nivel " + n;

			if (nivel.equals(ni)) {

				for (int j = 0; j < personajes.size; j++) {

					if (tipoActor.equals(personajes.get(j).getClass().getName().toString())) {

						posicionActores[i].add(new Vector2(personajes.get(j).getX(), personajes.get(j).getY()));

						tipoActores[i].add(personajes.get(j).getClass().getName().toString());

					}

				}

			}

			n++;
		}

	}

	public Array<Vector2> getPosicionActores(String tipoActor, String nivel) {

		Array<Vector2> posicion = new Array<Vector2>();

		String nombreNivel = "";

		int numeroNivel = 1;

		for (int i = 0; i < posicionActores.length; i++) {

			nombreNivel = "Nivel " + numeroNivel;

			if (nivel.equals(nombreNivel)) {

				for (int j = 0; j < tipoActores[i].size; j++) {

					if (tipoActor.equals(tipoActores[i].get(j))) {

						posicion.add(posicionActores[i].get(j));

					}

				}

			}

			numeroNivel++;

		}

		return posicion;

	}

	public Array<Vector2> getTamanoArray(String nivel) {

		Array<Vector2> posicion = new Array<Vector2>();

		String nombreNivel = "";

		int numeroNivel = 1;

		for (int i = 0; i < posicionActores.length; i++) {

			nombreNivel = "Nivel " + numeroNivel;

			if (nivel.equals(nombreNivel)) {

				for (int j = 0; j < posicionActores[i].size; j++) {

					posicion.add(posicionActores[i].get(j));

				}

			}

			numeroNivel++;
		}

		return posicion;

	}

	public void eliminarActores(String nivel) {

		String nombreNivel = "";

		int numeroNivel = 1;

		for (int i = 0; i < posicionActores.length; i++) {

			nombreNivel = "Nivel " + numeroNivel;

			if (nivel.equals(nombreNivel)) {

				posicionActores[i].clear();

				tipoActores[i].clear();

			}

			numeroNivel++;

		}

	}

	public void eliminarActor(String nivel, String tipoActor, int indice) {

		String nombreNivel = "";

		int numeroNivel = 1;

		for (int i = 0; i < posicionActores.length; i++) {

			nombreNivel = "Nivel " + numeroNivel;

			if (nivel.equals(nombreNivel)) {

				for (int j = 0; j < tipoActores[i].size; j++) {

					if (tipoActor.equals(tipoActores[i].get(j))) {

						if (indice == j) {

							posicionActores[i].removeIndex(j);

							tipoActores[i].removeIndex(j);

						}

					}

				}

			}

			numeroNivel++;

		}

	}

}
