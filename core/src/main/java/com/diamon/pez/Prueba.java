package com.diamon.pez;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;
import com.badlogic.gdx.Gdx; 

public class Prueba extends Pantalla {

    public Prueba(final Juego juego) {
        super(juego);
        
    Texture t = new Texture(Gdx.files.internal("texturas/badlogic.jpg"));

    }

    @Override
    public void mostrar() {}

    @Override
    public void eventos() {}

    @Override
    public void colisiones() {}

    @Override
    public void actualizar(float delta) {}

    @Override
    public void dibujar(Batch pincel, float delta) {
        
       
    }

    @Override
    public void guardarDatos() {}

    @Override
    public void liberarRecursos() {}
}
