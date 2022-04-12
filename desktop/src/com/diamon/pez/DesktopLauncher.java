package com.diamon.pez;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.diamon.pez.PezDePecera;
import com.diamon.pez.publicidad.Publicidad;
import com.diamon.pruebas.Box2dLightTest;
import com.diamon.pruebas.Pruebas;
import com.diamon.pruebas.SimpleTest;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowIcon(FileType.Internal, "texturas/icono.png");
		config.setTitle("Pez De Pecera");
		config.setResizable(false);

		// new Lwjgl3Application(new Pruebas(),config);
		
		// new Lwjgl3Application(new Box2dLightTest(),config);

		new Lwjgl3Application(new PezDePecera(new Publicidad() {

			@Override
			public void mostrarInterstitial() {
				// TODO Auto-generated method stub

			}

			@Override
			public void botonAtrasInterstitial() {
				// TODO Auto-generated method stub

			}

			@Override
			public void cargarBanner() {
				// TODO Auto-generated method stub

			}

			@Override
			public void mostrarBanner() {
				// TODO Auto-generated method stub

			}

			@Override
			public void ocultarBanner() {
				// TODO Auto-generated method stub

			}

			@Override
			public void iniciarActividad() {
				// TODO Auto-generated method stub

			}
		}), config);
	}
}
