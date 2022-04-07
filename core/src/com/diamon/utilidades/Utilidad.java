package com.diamon.utilidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class Utilidad {

	public static TextureRegion[] getPorcionesImagenes(Texture textura, int ancho, int alto, int columnas, int filas) {

		TextureRegion[][] porciones;

		TextureRegion[] imagenes = new TextureRegion[filas * columnas];

		int indice = 0;

		porciones = TextureRegion.split(textura, ancho / columnas, alto / filas);

		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {

				imagenes[indice++] = porciones[i][j];

			}

		}

		return imagenes;

	}

	public static TextureRegion[] getPorcionesImagenesEspecificas(Texture textura, int ancho, int alto, int columnas,
			int filas, int cantidad, int inicio, int fin) {

		TextureRegion[][] porciones;

		TextureRegion[] imagenes = new TextureRegion[filas * columnas];

		TextureRegion[] cantidadImagenes = new TextureRegion[cantidad];

		int indice = 0;

		porciones = TextureRegion.split(textura, ancho / columnas, alto / filas);

		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {

				imagenes[indice++] = porciones[i][j];

			}

		}

		inicio = inicio - 1;

		int in = 0;

		for (int i = inicio; i < fin; i++) {

			cantidadImagenes[in++] = imagenes[i];
		}

		return cantidadImagenes;

	}

}
