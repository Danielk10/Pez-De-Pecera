package com.diamon.pez.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.diamon.pez.PezDePecera;
import com.diamon.pez.publicidad.Publicidad;

/** Launches the Android application. */
public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
        configuration.useImmersiveMode = true; // Recommended, but not required.

        initialize(
                new PezDePecera(
                        new Publicidad() {

                            @Override
                            public void mostrarInterstitial() {
                                // TODO: Implement this method
                            }

                            @Override
                            public void botonAtrasInterstitial() {
                                // TODO: Implement this method
                            }

                            @Override
                            public void cargarBanner() {
                                // TODO: Implement this method
                            }

                            @Override
                            public void mostrarBanner() {
                                // TODO: Implement this method
                            }

                            @Override
                            public void ocultarBanner() {
                                // TODO: Implement this method
                            }

                            @Override
                            public void iniciarActividad() {
                                // TODO: Implement this method
                            }
                        }),
                configuration);
    }
}
