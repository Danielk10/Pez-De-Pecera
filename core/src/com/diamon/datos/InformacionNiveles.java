package com.diamon.datos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class InformacionNiveles {

	private static final String DATOS_DE_NIVELES = ".pezdepecera/datosNiveles.dat";

	private DatosNiveles datos;

	public static final int INTERNO = 0;

	public static final int LOCAL = 1;

	public DatosNiveles leerDatos(int tipoDato) {

		if (datos != null) {

			return datos;
		}

		FileHandle configuracionDato = null;

		if (tipoDato == InformacionNiveles.INTERNO) {

			configuracionDato = Gdx.files.internal("datos/datosNiveles.dat");

		}

		if (tipoDato == InformacionNiveles.LOCAL) {

			configuracionDato = Gdx.files.local(InformacionNiveles.DATOS_DE_NIVELES);

		}

		Json json = new Json();

		if (configuracionDato.exists()) {

			try {

				String archivoCodificado = configuracionDato.readString();

				String archivoDecodificado = Base64Coder.decodeString(archivoCodificado);

				datos = json.fromJson(DatosNiveles.class, archivoDecodificado);

			} catch (Exception e) {

				datos = new DatosNiveles();

				escribirDatos(datos);

			}

		} else {

			datos = new DatosNiveles();

			escribirDatos(datos);

		}

		return datos;
	}

	public void escribirDatos(DatosNiveles dato) {

		Json json = new Json();

		FileHandle configuracionDato = null;

		configuracionDato = Gdx.files.local(InformacionNiveles.DATOS_DE_NIVELES);

		String archivoDecodifcado = json.toJson(dato);

		String archivoCodificado = Base64Coder.encodeString(archivoDecodifcado);

		configuracionDato.writeString(archivoCodificado, false);
	}
}
