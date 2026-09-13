package com.diamon.datos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Gestor de configuraciones del juego utilizando libGDX Preferences.
 * Almacena de forma nativa las opciones del usuario en Android (SharedPreferences)
 * y en Desktop (directorio .prefs de libGDX).
 */
public class Configuraciones {

	private static final String PREFS_NOMBRE = "com.diamon.pez.configuraciones";

	private Datos datos;

	public static final int INTERNO = 0;

	public static final int LOCAL = 1;

	public Datos leerDatos(int tipoDato) {
		if (datos != null) {
			return datos;
		}

		datos = new Datos();
		Preferences prefs = Gdx.app.getPreferences(PREFS_NOMBRE);

		datos.setSonido(prefs.getBoolean("sonido", true));
		datos.setMostrarFPS(prefs.getBoolean("mostrarFPS", false));
		datos.setEditor(prefs.getBoolean("editor", false));
		datos.setPrueba(prefs.getBoolean("prueba", false));
		datos.setPartida(prefs.getBoolean("partida", false));
		datos.setContinuar(prefs.getBoolean("continuar", false));
		datos.setFondoScroll(prefs.getBoolean("fondoScroll", false));
		datos.setFondoParallax(prefs.getBoolean("fondoParallax", true));
		datos.setPantallaCompleta(prefs.getBoolean("pantallaCompleta", true));
		datos.setSincronizacionVertical(prefs.getBoolean("sincronizacionVertical", true));
		datos.setFiltradoBilineal(prefs.getBoolean("filtradoBilineal", true));
		datos.setDiparoAutomatico(prefs.getBoolean("diparoAutomatico", false));
		datos.setLeerDatosAsset(prefs.getBoolean("leerDatosAsset", true));
		datos.setVolumenMusica(prefs.getFloat("volumenMusica", 0.5f));
		datos.setVolumenSonido(prefs.getFloat("volumenSonido", 0.5f));

		return datos;
	}

	public void escribirDatos(Datos dato) {
		if (dato == null) {
			return;
		}

		Preferences prefs = Gdx.app.getPreferences(PREFS_NOMBRE);

		prefs.putBoolean("sonido", dato.isSonido());
		prefs.putBoolean("mostrarFPS", dato.isMostrarFPS());
		prefs.putBoolean("editor", dato.isEditor());
		prefs.putBoolean("prueba", dato.isPrueba());
		prefs.putBoolean("partida", dato.isPartida());
		prefs.putBoolean("continuar", dato.isContinuar());
		prefs.putBoolean("fondoScroll", dato.isFondoScroll());
		prefs.putBoolean("fondoParallax", dato.isFondoParallax());
		prefs.putBoolean("pantallaCompleta", dato.isPantallaCompleta());
		prefs.putBoolean("sincronizacionVertical", dato.isSincronizacionVertical());
		prefs.putBoolean("filtradoBilineal", dato.isFiltradoBilineal());
		prefs.putBoolean("diparoAutomatico", dato.isDiparoAutomatico());
		prefs.putBoolean("leerDatosAsset", dato.isLeerDatosAsset());
		prefs.putFloat("volumenMusica", dato.getVolumenMusica());
		prefs.putFloat("volumenSonido", dato.getVolumenSonido());

		prefs.flush();
	}
}
