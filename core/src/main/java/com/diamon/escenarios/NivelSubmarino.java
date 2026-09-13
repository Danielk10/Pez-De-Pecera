package com.diamon.escenarios;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.diamon.camara.CamaraSubmarina;
import com.diamon.mapas.CargadorNivelTmx;
import com.diamon.nucleo.Constantes;
import com.diamon.nucleo.Nivel;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;
import com.diamon.particulas.Particula;
import com.diamon.personajes.Fondo;
import com.diamon.personajes.Jugador;
import com.diamon.utilidades.ColisionSubmarinaListener;

import box2dLight.PointLight;
import box2dLight.RayHandler;

/**
 * Escenario submarino de exploración libre con soporte nativo de mapas TMX,
 * físicas de Box2D con corrientes, iluminación dinámica por profundidad (RayHandler)
 * y renderizado por capas con paralaje.
 */
public class NivelSubmarino extends Nivel {

    private OrthogonalTiledMapRenderer renderTiled;
    private CargadorNivelTmx cargadorTmx;
    private CamaraSubmarina camaraSubmarina;

    private PointLight luzJugador;
    private Particula particulaBurbujas;

    private Texture texturaFondoParalaje;
    private float anchoMapaMetros;
    private float altoMapaMetros;

    public NivelSubmarino(Pantalla pantalla, Jugador jugador, TiledMap tiledMap) {
        super(pantalla, jugador);

        this.mapa = tiledMap;
        inicializarMundoTmx();
    }

    @Override
    protected void iniciar() {
        // La inicialización se delega a inicializarMundoTmx una vez se asigna el TiledMap
    }

    private void inicializarMundoTmx() {
        // 1. Configurar físicas Box2D y ContactListener submarino
        mundoVirtual.setContactListener(new ColisionSubmarinaListener());

        // 2. Cargador TMX y procesamiento de capas
        renderTiled = new OrthogonalTiledMapRenderer(mapa, 1.0f / Constantes.PPM);
        cargadorTmx = new CargadorNivelTmx(mapa, mundoVirtual, luz, recurso, pantalla);
        cargadorTmx.procesarObjetos(personajes);

        this.anchoMapaMetros = cargadorTmx.getAnchoMetros();
        this.altoMapaMetros = cargadorTmx.getAltoMetros();

        // 3. Posicionar al Jugador en el punto de Spawn del TMX
        Vector2 spawn = cargadorTmx.getSpawnJugador();
        jugador.setPosition(spawn.x * Constantes.PPM, spawn.y * Constantes.PPM);
        jugador.setVivo(true);
        jugador.setFinNivel(false);
        jugador.setTerminarNivel(false);
        personajes.add(jugador);

        // 4. Controlador de Cámara Submarina
        camaraSubmarina = new CamaraSubmarina(Constantes.ANCHO_METROS, Constantes.ALTO_METROS);
        camaraSubmarina.setDimensionesMapa(anchoMapaMetros, altoMapaMetros);
        camaraSubmarina.setPosicion(spawn.x, spawn.y);

        // 5. Configuración de iluminación orgánica (Box2DLight)
        RayHandler.setGammaCorrection(true);
        luz.setAmbientLight(0.08f, 0.25f, 0.45f, 0.6f);
        luz.setShadows(true);

        // Luz propia que emana del Pez Payaso
        luzJugador = new PointLight(luz, 300, new Color(1.0f, 0.95f, 0.7f, 0.85f), 5.5f, spawn.x, spawn.y);
        luzJugador.setSoft(true);
        if (jugador.getCuerpo() != null) {
            luzJugador.attachToBody(jugador.getCuerpo());
        }
        luces.add(luzJugador);

        // 6. Efecto de partículas submarinas
        if (recurso.isLoaded("particulas/Particle Park Flame.p", ParticleEffect.class)) {
            particulaBurbujas = new Particula(recurso.get("particulas/Particle Park Flame.p", ParticleEffect.class), pantalla);
            particulaBurbujas.setEscala(1.5f);
            particulaBurbujas.iniciar();
        }

        // 7. Fondo de paralaje
        if (recurso.isLoaded("texturas/fondo1.png", Texture.class)) {
            texturaFondoParalaje = recurso.get("texturas/fondo1.png", Texture.class);
        }
    }

    @Override
    public void actualizar(float delta) {
        if (delta <= 0) {
            return;
        }

        // 1. Paso fijo de físicas Box2D (60 Hz con sub-stepping)
        mundoVirtual.step(1.0f / 60.0f, 6, 2);

        // 2. Actualizar todas las entidades activas
        Vector2 posJugador = new Vector2(jugador.getX(), jugador.getY());
        for (int i = personajes.size - 1; i >= 0; i--) {
            Personaje p = personajes.get(i);
            p.actualizar(delta);

            if (p.isRemover()) {
                p.destruirCuerpo(mundoVirtual);
                personajes.removeIndex(i);
            }
        }

        // 3. Seguimiento suave de cámara hacia el pez payaso
        camaraSubmarina.actualizar(posJugador, delta);
        camara.position.set(camaraSubmarina.getX(), camaraSubmarina.getY(), 0);
        camara.zoom = camaraSubmarina.getCamara().zoom;
        camara.update();

        // 4. Gradiente de luz según la profundidad (eje Y)
        actualizarGradienteProfundidad();

        // 5. Actualizar luces dinámicas y respuesta a power-ups
        if (luzJugador != null) {
            float radioDeseado = jugador.isLinternaActiva() ? 11.0f : 5.5f;
            luzJugador.setDistance(MathUtils.lerp(luzJugador.getDistance(), radioDeseado, 0.1f));
            if (jugador.isEscudoActivo()) {
                luzJugador.setColor(0.25f, 0.85f, 1.0f, 0.9f);
            } else if (jugador.isTurboActivo()) {
                luzJugador.setColor(1.0f, 0.6f, 0.2f, 0.9f);
            } else {
                luzJugador.setColor(1.0f, 0.95f, 0.7f, 0.85f);
            }
        }

        luz.update();
        if (particulaBurbujas != null) {
            particulaBurbujas.setPosicion(jugador.getX(), jugador.getY());
            particulaBurbujas.actualizar(delta);
        }
    }

    private void actualizarGradienteProfundidad() {
        if (altoMapaMetros <= 0) {
            return;
        }

        // Factor 1.0 en superficie, 0.0 en el fondo abisal
        float factor = MathUtils.clamp(jugador.getY() / altoMapaMetros, 0.0f, 1.0f);

        // En superficie: azul translúcido con visibilidad alta
        // En el fondo: penumbra densa / negro casi total
        float r = MathUtils.lerp(0.01f, 0.12f, factor);
        float g = MathUtils.lerp(0.03f, 0.28f, factor);
        float b = MathUtils.lerp(0.08f, 0.55f, factor);
        float a = MathUtils.lerp(0.10f, 0.75f, factor);

        luz.setAmbientLight(r, g, b, a);
    }

    @Override
    public void dibujar(Batch pincel, float delta) {
        // --- FASE 1: Paralaje de Fondo ---
        pincel.setProjectionMatrix(camara.combined);
        pincel.begin();
        if (texturaFondoParalaje != null) {
            float paralajeOffsetX = camara.position.x * 0.2f;
            float paralajeOffsetY = camara.position.y * 0.1f;
            pincel.draw(texturaFondoParalaje,
                    camara.position.x - Constantes.ANCHO_METROS / 2f - paralajeOffsetX,
                    camara.position.y - Constantes.ALTO_METROS / 2f - paralajeOffsetY,
                    Constantes.ANCHO_METROS * 1.5f,
                    Constantes.ALTO_METROS * 1.5f);
        }
        pincel.end();

        // --- FASE 2: Capas de Fondo del Tilemap ---
        renderTiled.setView(camara);
        if (cargadorTmx.getCapasFondo().length > 0) {
            renderTiled.render(cargadorTmx.getCapasFondo());
        }

        // --- FASE 3: Entidades del Juego y Partículas ---
        pincel.begin();
        for (Personaje personaje : personajes) {
            if (!(personaje instanceof Fondo)) {
                personaje.dibujar(pincel, delta);
            }
        }
        if (particulaBurbujas != null) {
            particulaBurbujas.dibujar(pincel, delta);
        }
        pincel.end();

        // --- FASE 4: Capas de Primer Plano del Tilemap (Oclusión frontal) ---
        if (cargadorTmx.getCapasPrimerPlano().length > 0) {
            renderTiled.render(cargadorTmx.getCapasPrimerPlano());
        }

        // --- FASE 5: Iluminación Box2DLight (Sombras volumétricas) ---
        luz.setCombinedMatrix(camara);
        luz.render();
    }

    @Override
    public void guardarDatos() {
    }

    @Override
    public void liberarRecursos() {
        if (cargadorTmx != null) {
            cargadorTmx.liberar();
        }
        if (renderTiled != null) {
            renderTiled.dispose();
        }
        luces.clear();
        luz.removeAll();
        cuerpos.clear();
        personajes.clear();
        if (mapa != null) {
            mapa.dispose();
        }
        if (particulaBurbujas != null) {
            particulaBurbujas.liberarRecursos();
        }
    }

    public CamaraSubmarina getCamaraSubmarina() {
        return camaraSubmarina;
    }
}
