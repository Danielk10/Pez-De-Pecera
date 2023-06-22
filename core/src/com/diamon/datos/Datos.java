package com.diamon.datos;

public class Datos {

	private boolean partida;

	private boolean continuar;

	private boolean leerDatosAsset;

	private boolean editor;

	private float volumenMusica;

	private float volumenSonido;

	private boolean diparoAutomatico;

	// configuraciones del juego
	private boolean sonido;

	private boolean mostrarFPS;

	private boolean prueba;

	private boolean fondoScroll;

	private boolean fondoParallax;

	private boolean pantallaCompleta;

	private boolean sincronizacionVertical;

	private boolean filtradoBilineal;

	public Datos() {

		sonido = true;

		mostrarFPS = false;

		editor = true;

		prueba = false;

		partida = false;

		continuar = false;

		fondoScroll = true;

		fondoParallax = false;

		pantallaCompleta = true;

		sincronizacionVertical = true;

		filtradoBilineal = true;

		diparoAutomatico = false;

		leerDatosAsset = true;

		volumenMusica = 0.5f;

		volumenSonido = 0.5f;
	}

	public boolean isEditor() {
		return editor;
	}

	public void setEditor(boolean editor) {
		this.editor = editor;
	}

	public boolean isContinuar() {
		return continuar;
	}

	public void setContinuar(boolean continuar) {
		this.continuar = continuar;
	}

	public boolean isPartida() {
		return partida;
	}

	public void setPartida(boolean partida) {
		this.partida = partida;
	}

	public boolean isSonido() {
		return sonido;
	}

	public void setSonido(boolean sonido) {
		this.sonido = sonido;
	}

	public boolean isMostrarFPS() {
		return mostrarFPS;
	}

	public void setMostrarFPS(boolean mostrarFPS) {
		this.mostrarFPS = mostrarFPS;
	}

	public boolean isPrueba() {
		return prueba;
	}

	public void setPrueba(boolean prueba) {
		this.prueba = prueba;
	}

	public boolean isFondoScroll() {
		return fondoScroll;
	}

	public void setFondoScroll(boolean fondoScroll) {
		this.fondoScroll = fondoScroll;
	}

	public boolean isFondoParallax() {
		return fondoParallax;
	}

	public void setFondoParallax(boolean fondoParallax) {
		this.fondoParallax = fondoParallax;
	}

	public boolean isPantallaCompleta() {
		return pantallaCompleta;
	}

	public void setPantallaCompleta(boolean pantallaCompleta) {
		this.pantallaCompleta = pantallaCompleta;
	}

	public boolean isSincronizacionVertical() {
		return sincronizacionVertical;
	}

	public void setSincronizacionVertical(boolean sincronizacionVertical) {
		this.sincronizacionVertical = sincronizacionVertical;
	}

	public boolean isFiltradoBilineal() {
		return filtradoBilineal;
	}

	public void setFiltradoBilineal(boolean filtradoBilineal) {
		this.filtradoBilineal = filtradoBilineal;
	}

	public float getVolumenMusica() {
		return volumenMusica;
	}

	public void setVolumenMusica(float volumenMusica) {
		this.volumenMusica = volumenMusica;
	}

	public float getVolumenSonido() {
		return volumenSonido;
	}

	public void setVolumenSonido(float volumenSonido) {
		this.volumenSonido = volumenSonido;
	}

	public boolean isDiparoAutomatico() {
		return diparoAutomatico;
	}

	public void setDiparoAutomatico(boolean diparoAutomatico) {
		this.diparoAutomatico = diparoAutomatico;
	}

	public boolean isLeerDatosAsset() {
		return leerDatosAsset;
	}

	public void setLeerDatosAsset(boolean leerDatosAsset) {
		this.leerDatosAsset = leerDatosAsset;
	}

}
