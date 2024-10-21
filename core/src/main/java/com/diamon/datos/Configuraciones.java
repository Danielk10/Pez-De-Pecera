package com.diamon.datos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class Configuraciones {

	private static final String DATOS_DE_CONFIGURACIONES = ".pezdepecera/datosConfiguraciones.dat";

	private Datos datos;

	public static final int INTERNO = 0;

	public static final int LOCAL = 1;

	public Datos leerDatos(int tipoDato) {

		if (datos != null) {

			return datos;
		}

		FileHandle configuracionDato = null;

		if (tipoDato == InformacionNiveles.INTERNO) {

			configuracionDato = Gdx.files.internal("datos/datosConfiguraciones.dat");

		}

		if (tipoDato == InformacionNiveles.LOCAL) {

			configuracionDato = Gdx.files.local(Configuraciones.DATOS_DE_CONFIGURACIONES);

		}

		Json json = new Json();

		if (configuracionDato.exists()) {

			try {

				String archivoCodificado = configuracionDato.readString();

				String archivoDecodificado = Base64Coder.decodeString(archivoCodificado);

				datos = json.fromJson(Datos.class, archivoDecodificado);

			} catch (Exception e) {

				datos = new Datos();

				escribirDatos(datos);

			}

		} else {

			datos = new Datos();

			escribirDatos(datos);

		}

		return datos;
	}

	public void escribirDatos(Datos dato) {

		Json json = new Json();

		FileHandle configuracionDato = null;

		configuracionDato = Gdx.files.local(Configuraciones.DATOS_DE_CONFIGURACIONES);

		String archivoDecodifcado = json.toJson(dato);

		String archivoCodificado = Base64Coder.encodeString(archivoDecodifcado);

		configuracionDato.writeString(archivoCodificado, false);
	}
}
