package com.diamon.pez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.diamon.nucleo.Juego;
import com.diamon.pantallas.PantallaPrecentacion;
import com.diamon.pez.publicidad.Publicidad;

public class PezDePecera extends Juego {

    public PezDePecera(Publicidad publicidad) {
        super(publicidad);
    }

    @SuppressWarnings("static-access")
    @Override
    public void create() {

        super.create();

        if (dato.isPantallaCompleta()) {

            if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
        }

        if (!dato.isPantallaCompleta()) {

            if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

                Gdx.graphics.setWindowedMode((int) Juego.ANCHO_PANTALLA, (int) Juego.ALTO_PANTALLA);
            }
        }

        if (dato.isSincronizacionVertical()) {

            Gdx.graphics.setVSync(true);
        }

        if (!dato.isSincronizacionVertical()) {

            Gdx.graphics.setVSync(false);
        }

        if (Gdx.app.getType() == Gdx.app.getType().Desktop) {

            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

            Gdx.graphics.setCursor(Gdx.graphics.newCursor(pixmap, 0, 0));
        }

        recurso.load("texturas/bomba.png", Texture.class);

        recurso.load("texturas/algas.png", Texture.class);

        recurso.load("texturas/invisible.png", Texture.class);

        recurso.load("texturas/fondo1.png", Texture.class);

        recurso.load("texturas/fondo2.png", Texture.class);

        recurso.load("texturas/fondo3.png", Texture.class);

        recurso.load("texturas/fondo4.png", Texture.class);

        recurso.load("texturas/inicio.png", Texture.class);

        recurso.load("texturas/cursor.png", Texture.class);

        recurso.load("texturas/badlogic.jpg", Texture.class);

        recurso.load("texturas/titulo.png", Texture.class);

       // recurso.load("texturas/diamondBlack.png", Texture.class);

        recurso.load("texturas/icono.png", Texture.class);

        recurso.load("texturas/menu.png", Texture.class);

        recurso.load("texturas/pausa.png", Texture.class);

        recurso.load("texturas/pez.atlas", TextureAtlas.class);

        recurso.load("texturas/pez1.atlas", TextureAtlas.class);

        recurso.load("texturas/pezG.atlas", TextureAtlas.class);

        recurso.load("texturas/pulpo.atlas", TextureAtlas.class);

        recurso.load("texturas/pezGlobo.atlas", TextureAtlas.class);

        recurso.load("texturas/iconos.atlas", TextureAtlas.class);

        recurso.load("texturas/controles.atlas", TextureAtlas.class);

        recurso.load("texturas/dedos.atlas", TextureAtlas.class);

        recurso.load("texturas/tiburon.atlas", TextureAtlas.class);

        recurso.load("audios/explosion.ogg", Sound.class);

        recurso.load("audios/musica.ogg", Music.class);

        recurso.load("audios/moustro.ogg", Music.class);

        recurso.load("audios/creditos.ogg", Music.class);

        recurso.load("uis/general/uiskin.json", Skin.class);

        recurso.load("uis/carga/neon-ui.json", Skin.class);

        recurso.load("particulas/Particle Park Flame.p", ParticleEffect.class);

        setScreen(new PantallaPrecentacion(this));
    }
}
