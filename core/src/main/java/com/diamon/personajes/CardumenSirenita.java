package com.diamon.personajes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.diamon.nucleo.Constantes;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;

/**
 * Cardumen de peces de arrecife tipo "La Sirenita" (The Little Mermaid).
 * Simula un cardumen numeroso (30-40 peces) de colores tropicales vibrantes
 * que nadan en ondas sincronizadas formando cintas de seda en el agua.
 * 
 * Comportamiento dinámico:
 * - Ondulación colectiva armónica (propagación de onda senoidal de proa a popa).
 * - Dispersión hidrodinámica realista al paso del Pez payaso o Tiburón (los peces
 *   se abren en abanico y esquivan al jugador fluidamente).
 * - Reagrupación orgánica suave mediante reglas de cohesión y alineamiento.
 * - Variedad cromática: Cirujano azul, pez mariposa dorado, damisela esmeralda y antias coral.
 */
public class CardumenSirenita extends Personaje {

    public static class PezEnCardumen {
        public float relX;
        public float relY;
        public float vx;
        public float vy;
        public float targetOffsetX;
        public float targetOffsetY;
        public float dispersionX = 0f;
        public float dispersionY = 0f;
        public int especie; // 0=azul, 1=amarillo, 2=turquesa, 3=coral
        public float tiempoAnim;
        public float escala;
        public float faseOndulacion;
        public float rotacion;
        public boolean miraDerecha = true;
    }

    private final Array<PezEnCardumen> peces = new Array<PezEnCardumen>(true, 36);
    @SuppressWarnings("unchecked")
    private final Animation<TextureRegion>[] animacionesEspecies = new Animation[4];

    private float centroX;
    private float centroY;
    private float baseY;
    private float velocidadCrucero = 2.2f;
    private boolean direccionDerecha = true;
    private float limiteIzquierdo;
    private float limiteDerecho;
    private float tiempoGlobal = 0f;
    private float cooldownInteraccion = 0f;

    public CardumenSirenita(TextureAtlas atlas, Pantalla pantalla, float ancho, float alto, int tipoDeCuerpo) {
        super(obtenerRegionInicial(atlas), pantalla, ancho, alto, tipoDeCuerpo);
        inicializarAnimaciones(atlas);
        inicializarCardumen(32);
    }

    private static TextureRegion obtenerRegionInicial(TextureAtlas atlas) {
        if (atlas != null && atlas.getRegions().size > 0) {
            return atlas.getRegions().get(0);
        }
        return new TextureRegion();
    }

    private void inicializarAnimaciones(TextureAtlas atlas) {
        if (atlas == null) return;

        String[] nombres = {"azul", "amarillo", "turquesa", "coral"};
        for (int i = 0; i < 4; i++) {
            Array<AtlasRegion> frames = new Array<AtlasRegion>(4);
            for (int f = 1; f <= 4; f++) {
                AtlasRegion reg = atlas.findRegion("cardumen_" + nombres[i] + "_" + f);
                if (reg != null) {
                    frames.add(reg);
                }
            }
            if (frames.size == 0) {
                frames = atlas.getRegions();
            }
            animacionesEspecies[i] = new Animation<TextureRegion>(0.11f, frames, Animation.PlayMode.LOOP);
        }
    }

    private void inicializarCardumen(int cantidad) {
        peces.clear();
        for (int i = 0; i < cantidad; i++) {
            PezEnCardumen p = new PezEnCardumen();
            p.especie = i % 4;

            // Formación en huso/cinta orgánica (lagrima hidrodinámica)
            float t = (float) i / (float) cantidad;
            float cintaX = (t - 0.5f) * 4.8f; // extensión horizontal del cardumen
            float anchoCinta = MathUtils.sin(t * MathUtils.PI) * 1.6f;
            float cintaY = MathUtils.random(-anchoCinta, anchoCinta);

            p.targetOffsetX = cintaX;
            p.targetOffsetY = cintaY;
            p.relX = cintaX;
            p.relY = cintaY;
            p.vx = 0f;
            p.vy = 0f;
            p.tiempoAnim = MathUtils.random(0f, 1f);
            p.escala = MathUtils.random(0.72f, 1.05f);
            p.faseOndulacion = MathUtils.random(0f, MathUtils.PI2);
            p.rotacion = 0f;

            peces.add(p);
        }
    }

    @Override
    public void setPosition(float pixelX, float pixelY) {
        super.setPosition(pixelX, pixelY);
        this.centroX = x;
        this.centroY = y;
        this.baseY = y;
        this.limiteIzquierdo = x - 18.0f;
        this.limiteDerecho = x + 24.0f;
    }

    @Override
    public void actualizar(float delta) {
        super.actualizar(delta);
        tiempoGlobal += delta;
        if (cooldownInteraccion > 0) {
            cooldownInteraccion -= delta;
        }

        // 1. Navegación del cardumen como un todo
        float dirSigno = direccionDerecha ? 1.0f : -1.0f;
        centroX += dirSigno * velocidadCrucero * delta;

        // Ondulación vertical senoidal del colectivo
        centroY = baseY + MathUtils.sin(tiempoGlobal * 1.4f) * 1.2f;

        // Reversión suave en los bordes del hábitat de arrecife
        if (direccionDerecha && centroX >= limiteDerecho) {
            direccionDerecha = false;
        } else if (!direccionDerecha && centroX <= limiteIzquierdo) {
            direccionDerecha = true;
        }

        setX(centroX);
        setY(centroY);

        // 2. Detección de intrusos (Jugador o Tiburón) para dispersión estilo "La Sirenita"
        float intrusoX = -999f;
        float intrusoY = -999f;
        boolean intrusoCerca = false;

        if (personajes != null) {
            for (Personaje p : personajes) {
                if ((p instanceof Jugador && ((Jugador) p).isVivo()) || p instanceof TiburonAzul) {
                    float distCentro = (float) Math.hypot(p.getX() - centroX, p.getY() - centroY);
                    if (distCentro < 6.5f) {
                        intrusoX = p.getX() + p.getWidth() / 2f;
                        intrusoY = p.getY() + p.getHeight() / 2f;
                        intrusoCerca = true;
                        break;
                    }
                }
            }
        }

        // 3. Actualizar cada pez en el cardumen (propagación de onda + evasión + cohesión)
        float factorVelAnimBase = 1.0f;
        for (int i = 0; i < peces.size; i++) {
            PezEnCardumen p = peces.get(i);

            // A. Onda senoidal sincronizada de cardumen ("Little Mermaid ribbon wave")
            // La fase se retrasa según la posición longitudinal (offsetX), creando una cinta ondulante
            float ondaCinta = MathUtils.sin(tiempoGlobal * 3.2f - p.targetOffsetX * 0.9f) * 0.45f;
            float destinoRelX = direccionDerecha ? p.targetOffsetX : -p.targetOffsetX;
            float destinoRelY = p.targetOffsetY + ondaCinta;

            // B. Respuesta al intruso (dispersión en abanico)
            float pezMundoX = centroX + p.relX;
            float pezMundoY = centroY + p.relY;

            if (intrusoCerca) {
                float dX = pezMundoX - intrusoX;
                float dY = pezMundoY - intrusoY;
                float distIntruso = (float) Math.hypot(dX, dY);
                float radioAlarma = 4.2f;

                if (distIntruso < radioAlarma) {
                    float fuerzaDispersion = (1.0f - distIntruso / radioAlarma) * 7.5f;
                    float nX = dX / Math.max(0.1f, distIntruso);
                    float nY = dY / Math.max(0.1f, distIntruso);

                    // Si viene directo de frente, desviar hacia arriba o abajo (abrir el cardumen)
                    if (Math.abs(nY) < 0.2f) {
                        nY = (p.targetOffsetY >= 0) ? 0.8f : -0.8f;
                    }

                    p.dispersionX += nX * fuerzaDispersion * delta * 4.5f;
                    p.dispersionY += nY * fuerzaDispersion * delta * 4.5f;
                }
            }

            // Amortiguación gradual de la dispersión para retornar a la formación
            p.dispersionX *= (float) Math.pow(0.85, delta * 60);
            p.dispersionY *= (float) Math.pow(0.85, delta * 60);

            // C. Movimiento hacia la posición objetivo en el cardumen
            float objX = destinoRelX + p.dispersionX;
            float objY = destinoRelY + p.dispersionY;

            float velocidadAlineamiento = intrusoCerca ? 5.0f : 3.8f;
            float prevRelX = p.relX;
            float prevRelY = p.relY;
            p.relX = MathUtils.lerp(p.relX, objX, velocidadAlineamiento * delta);
            p.relY = MathUtils.lerp(p.relY, objY, velocidadAlineamiento * delta);

            // D. Vector de velocidad resultante del pez
            p.vx = (dirSigno * velocidadCrucero) + ((p.relX - prevRelX) / Math.max(0.001f, delta));
            p.vy = ((p.relY - prevRelY) / Math.max(0.001f, delta));

            // E. Orientación e inclinación hidrodinámica 360° del individuo
            float rapidez = (float) Math.hypot(p.vx, p.vy);
            if (p.vx > 0.2f) {
                p.miraDerecha = true;
            } else if (p.vx < -0.2f) {
                p.miraDerecha = false;
            }

            float anguloPitch = MathUtils.atan2(p.vy, Math.max(0.001f, Math.abs(p.vx))) * MathUtils.radDeg;
            float rotObjetivo;
            if (p.miraDerecha) {
                rotObjetivo = MathUtils.clamp(anguloPitch, -55f, 55f);
            } else {
                rotObjetivo = MathUtils.clamp(-anguloPitch, -55f, 55f);
            }
            p.rotacion = MathUtils.lerpAngleDeg(p.rotacion, rotObjetivo, 9.0f * delta);

            // F. Velocidad de animación de aleteo proporcional a la rapidez
            float factorAleteo = MathUtils.clamp(rapidez * 0.45f, 0.5f, 2.8f);
            p.tiempoAnim += delta * factorAleteo;
        }

        // Sincronizar cuerpo Box2D si existe
        if (cuerpo != null) {
            cuerpo.setTransform(centroX, centroY, 0f);
        }
    }

    @Override
    public void colision(Personaje actor) {
        if (actor instanceof Jugador && cooldownInteraccion <= 0f) {
            Jugador j = (Jugador) actor;
            // Nadar a través del cardumen otorga puntos de bonificación de estilo y estela de nado
            j.agregarPuntos(25);
            cooldownInteraccion = 2.0f;
            activarHitFlash(0.2f, new Color(0.35f, 0.95f, 1.0f, 0.75f));
        }
    }

    @Override
    public void dibujar(Batch pincel, float delta) {
        Color colorOrig = pincel.getColor();

        float spriteAnchoMetros = 48f / Constantes.PPM;
        float spriteAltoMetros = 32f / Constantes.PPM;

        for (int i = 0; i < peces.size; i++) {
            PezEnCardumen p = peces.get(i);
            Animation<TextureRegion> anim = animacionesEspecies[p.especie];
            if (anim == null) continue;

            TextureRegion frame = anim.getKeyFrame(p.tiempoAnim, true);
            if (frame == null) continue;

            float pezX = centroX + p.relX;
            float pezY = centroY + p.relY;

            float w = spriteAnchoMetros * p.escala;
            float h = spriteAltoMetros * p.escala;
            float scaleX = p.miraDerecha ? 1.0f : -1.0f;

            // Ligero tinte de profundidad y translucidez marina
            pincel.setColor(1.0f, 1.0f, 1.0f, 0.96f);
            pincel.draw(frame,
                    pezX - w / 2f, pezY - h / 2f,
                    w / 2f, h / 2f,
                    w, h,
                    scaleX, 1.0f,
                    p.rotacion);
        }

        pincel.setColor(colorOrig);
    }
}
