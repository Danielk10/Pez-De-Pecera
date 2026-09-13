package com.diamon.utilidades;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.diamon.items.BurbujaOxigeno;
import com.diamon.items.Perla;
import com.diamon.items.PowerUpSubmarino;
import com.diamon.items.TipoPowerUp;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;
import com.diamon.personajes.Algas;
import com.diamon.personajes.Bomba;
import com.diamon.personajes.PezAngel;
import com.diamon.personajes.PezGloboAmarillo;
import com.diamon.personajes.PezGloboNaranja;
import com.diamon.personajes.Pulpo;
import com.diamon.personajes.TiburonAzul;

import box2dLight.RayHandler;

/**
 * Fábrica centralizada para la instanciación unificada de actores, enemigos, ítems y power-ups.
 * Garantiza consistencia entre el Editor de Niveles, Niveles clásicos y NivelSubmarino TMX.
 */
public class FabricaActores {

    public static Personaje crearActor(String tipo, float pixelX, float pixelY,
                                       Pantalla pantalla, AssetManager recurso, RayHandler luz) {
        if (tipo == null || tipo.trim().isEmpty()) {
            return null;
        }

        String lower = tipo.toLowerCase();
        Personaje p = null;

        if (lower.contains("pulpo")) {
            if (recurso.isLoaded("texturas/pulpo.atlas", TextureAtlas.class)) {
                p = new Pulpo(recurso.get("texturas/pulpo.atlas", TextureAtlas.class).getRegions(),
                        0.07f, Animation.PlayMode.LOOP, pantalla, 48, 96, Personaje.ESTATICO);
            }
        } else if (lower.contains("tiburon")) {
            if (recurso.isLoaded("texturas/tiburon.atlas", TextureAtlas.class)) {
                p = new TiburonAzul(recurso.get("texturas/tiburon.atlas", TextureAtlas.class).getRegions(),
                        0.15f, Animation.PlayMode.LOOP, pantalla, 192, 192, Personaje.ESTATICO);
            }
        } else if (lower.contains("angel")) {
            if (recurso.isLoaded("texturas/pez1.atlas", TextureAtlas.class)) {
                p = new PezAngel(recurso.get("texturas/pez1.atlas", TextureAtlas.class).getRegions(),
                        0.1f, Animation.PlayMode.LOOP, pantalla, 64, 32, Personaje.ESTATICO);
            }
        } else if (lower.contains("globoamarillo") || (lower.contains("globo") && lower.contains("amarillo")) || lower.contains("pezg.")) {
            if (recurso.isLoaded("texturas/pezG.atlas", TextureAtlas.class)) {
                p = new PezGloboAmarillo(recurso.get("texturas/pezG.atlas", TextureAtlas.class).getRegions(),
                        0.1f, Animation.PlayMode.LOOP, pantalla, 64, 32, Personaje.ESTATICO);
            }
        } else if (lower.contains("globonaranja") || (lower.contains("globo") && lower.contains("naranja")) || lower.contains("pezglobo")) {
            if (recurso.isLoaded("texturas/pezGlobo.atlas", TextureAtlas.class)) {
                p = new PezGloboNaranja(recurso.get("texturas/pezGlobo.atlas", TextureAtlas.class).getRegions(),
                        0.1f, Animation.PlayMode.LOOP, pantalla, 96, 64, Personaje.ESTATICO);
            }
        } else if (lower.contains("bomba")) {
            if (recurso.isLoaded("texturas/bomba.png", Texture.class)) {
                p = new Bomba(recurso.get("texturas/bomba.png", Texture.class), pantalla, 64, 64, Personaje.DIANAMICO);
            }
        } else if (lower.contains("algas")) {
            if (recurso.isLoaded("texturas/algas.png", Texture.class)) {
                p = new Algas(recurso.get("texturas/algas.png", Texture.class), pantalla, 96, 64, Personaje.CINESTECICO);
            }
        } else if (lower.contains("perla")) {
            TextureRegion reg = null;
            if (recurso.isLoaded("particulas/circle3.png", Texture.class)) {
                reg = new TextureRegion(recurso.get("particulas/circle3.png", Texture.class));
            } else if (recurso.isLoaded("texturas/iconos.atlas", TextureAtlas.class)) {
                reg = recurso.get("texturas/iconos.atlas", TextureAtlas.class).findRegion("iconofaro");
            }
            if (reg != null) {
                p = new Perla(reg, pantalla, luz);
            }
        } else if (lower.contains("burbuja")) {
            TextureRegion reg = null;
            if (recurso.isLoaded("particulas/circle4.png", Texture.class)) {
                reg = new TextureRegion(recurso.get("particulas/circle4.png", Texture.class));
            } else if (recurso.isLoaded("texturas/iconos.atlas", TextureAtlas.class)) {
                reg = recurso.get("texturas/iconos.atlas", TextureAtlas.class).findRegion("iconocorazon");
            }
            if (reg != null) {
                p = new BurbujaOxigeno(reg, pantalla, luz);
            }
        } else if (lower.contains("powerup")) {
            if (recurso.isLoaded("texturas/iconos.atlas", TextureAtlas.class)) {
                TextureAtlas atlas = recurso.get("texturas/iconos.atlas", TextureAtlas.class);
                TipoPowerUp pTipo = TipoPowerUp.TURBO;
                TextureRegion reg = atlas.findRegion("iconovelocidad");
                if (lower.contains("escudo")) {
                    pTipo = TipoPowerUp.ESCUDO;
                    reg = atlas.findRegion("iconocorazon");
                } else if (lower.contains("linterna") || lower.contains("faro")) {
                    pTipo = TipoPowerUp.LINTERNA;
                    reg = atlas.findRegion("iconofaro");
                }
                if (reg != null) {
                    p = new PowerUpSubmarino(pTipo, reg, pantalla, luz);
                }
            }
        }

        if (p != null) {
            p.setPosition(pixelX, pixelY);
        }
        return p;
    }
}
