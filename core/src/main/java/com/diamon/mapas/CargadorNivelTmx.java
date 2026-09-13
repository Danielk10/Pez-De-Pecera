package com.diamon.mapas;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.diamon.items.BurbujaOxigeno;
import com.diamon.items.Perla;
import com.diamon.items.PowerUpSubmarino;
import com.diamon.items.TipoPowerUp;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.diamon.nucleo.Constantes;
import com.diamon.nucleo.Pantalla;
import com.diamon.nucleo.Personaje;
import com.diamon.nucleo.ZonaCorriente;
import com.diamon.personajes.Algas;
import com.diamon.personajes.Bomba;
import com.diamon.personajes.PezAngel;
import com.diamon.personajes.PezGloboAmarillo;
import com.diamon.personajes.PezGloboNaranja;
import com.diamon.personajes.Pulpo;
import com.diamon.personajes.TiburonAzul;

import box2dLight.PointLight;
import box2dLight.RayHandler;

/**
 * Analizador y cargador de niveles TMX para libGDX.
 * Procesa capas gráficas por orden de profundidad, genera cuerpos físicos en Box2D
 * (terreno y corrientes marinas), spawnea personajes y crea fuentes de iluminación.
 */
public class CargadorNivelTmx {

    private final TiledMap mapa;
    private final World mundo;
    private final RayHandler rayHandler;
    private final AssetManager recurso;
    private final Pantalla pantalla;

    private float anchoMetros;
    private float altoMetros;

    private Vector2 spawnJugador;

    private final Array<Body> cuerposGenerados = new Array<Body>();
    private final Array<PointLight> lucesGeneradas = new Array<PointLight>();

    private int[] capasFondo;
    private int[] capasPrimerPlano;

    public CargadorNivelTmx(TiledMap mapa, World mundo, RayHandler rayHandler, AssetManager recurso, Pantalla pantalla) {
        this.mapa = mapa;
        this.mundo = mundo;
        this.rayHandler = rayHandler;
        this.recurso = recurso;
        this.pantalla = pantalla;
        this.spawnJugador = new Vector2(3.0f, 5.0f);

        calcularDimensiones();
        clasificarCapasDeTiles();
    }

    private void calcularDimensiones() {
        int mapWidthTiles = mapa.getProperties().get("width", Integer.class);
        int mapHeightTiles = mapa.getProperties().get("height", Integer.class);
        int tileWidth = mapa.getProperties().get("tilewidth", Integer.class);
        int tileHeight = mapa.getProperties().get("tileheight", Integer.class);

        anchoMetros = (mapWidthTiles * tileWidth) / Constantes.PPM;
        altoMetros = (mapHeightTiles * tileHeight) / Constantes.PPM;
    }

    private void clasificarCapasDeTiles() {
        Array<Integer> fondoList = new Array<Integer>();
        Array<Integer> frenteList = new Array<Integer>();

        int index = 0;
        for (MapLayer layer : mapa.getLayers()) {
            if (layer instanceof TiledMapTileLayer) {
                String name = layer.getName().toLowerCase();
                if (name.contains("primer_plano") || name.contains("foreground") || name.contains("frente") || name.contains("delantero")) {
                    frenteList.add(index);
                } else {
                    fondoList.add(index);
                }
            }
            index++;
        }

        capasFondo = new int[fondoList.size];
        for (int i = 0; i < fondoList.size; i++) {
            capasFondo[i] = fondoList.get(i);
        }

        capasPrimerPlano = new int[frenteList.size];
        for (int i = 0; i < frenteList.size; i++) {
            capasPrimerPlano[i] = frenteList.get(i);
        }
    }

    /**
     * Procesa todas las capas de objetos: colisiones sólidas, corrientes, spawns y luces.
     */
    public void procesarObjetos(Array<Personaje> listaPersonajes) {
        for (MapLayer layer : mapa.getLayers()) {
            String layerName = layer.getName().toLowerCase();

            if (layerName.contains("colision") || layerName.contains("solido") || layerName.contains("terreno")) {
                procesarCapaColisiones(layer);
            } else if (layerName.contains("corriente")) {
                procesarCapaCorrientes(layer);
            } else if (layerName.contains("spawn") || layerName.contains("entidad") || layerName.contains("actor")) {
                procesarCapaSpawns(layer, listaPersonajes);
            } else if (layerName.contains("luz") || layerName.contains("luces")) {
                procesarCapaLuces(layer);
            }
        }
    }

    private void procesarCapaColisiones(MapLayer layer) {
        for (MapObject object : layer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set((rect.x + rect.width / 2f) / Constantes.PPM, (rect.y + rect.height / 2f) / Constantes.PPM);

                Body body = mundo.createBody(bodyDef);
                body.setUserData("TERRENO_SOLIDO");

                PolygonShape shape = new PolygonShape();
                shape.setAsBox((rect.width / 2f) / Constantes.PPM, (rect.height / 2f) / Constantes.PPM);

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;
                fixtureDef.density = 1.0f;
                fixtureDef.friction = 0.4f;
                fixtureDef.filter.categoryBits = Constantes.CAT_TERRENO;
                fixtureDef.filter.maskBits = Constantes.CAT_JUGADOR | Constantes.CAT_ENEMIGO | Constantes.CAT_BALA;

                body.createFixture(fixtureDef);
                shape.dispose();
                cuerposGenerados.add(body);

            } else if (object instanceof PolygonMapObject) {
                Polygon polygon = ((PolygonMapObject) object).getPolygon();

                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set(polygon.getX() / Constantes.PPM, polygon.getY() / Constantes.PPM);

                Body body = mundo.createBody(bodyDef);
                body.setUserData("TERRENO_SOLIDO");

                float[] vertices = polygon.getVertices();
                float[] verticesEscalados = new float[vertices.length];
                for (int i = 0; i < vertices.length; i++) {
                    verticesEscalados[i] = vertices[i] / Constantes.PPM;
                }

                PolygonShape shape = new PolygonShape();
                shape.set(verticesEscalados);

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;
                fixtureDef.density = 1.0f;
                fixtureDef.friction = 0.4f;
                fixtureDef.filter.categoryBits = Constantes.CAT_TERRENO;

                body.createFixture(fixtureDef);
                shape.dispose();
                cuerposGenerados.add(body);

            } else if (object instanceof PolylineMapObject) {
                Polyline polyline = ((PolylineMapObject) object).getPolyline();

                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set(polyline.getX() / Constantes.PPM, polyline.getY() / Constantes.PPM);

                Body body = mundo.createBody(bodyDef);
                body.setUserData("TERRENO_SOLIDO");

                float[] vertices = polyline.getVertices();
                float[] verticesEscalados = new float[vertices.length];
                for (int i = 0; i < vertices.length; i++) {
                    verticesEscalados[i] = vertices[i] / Constantes.PPM;
                }

                ChainShape shape = new ChainShape();
                shape.createChain(verticesEscalados);

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;
                fixtureDef.density = 1.0f;
                fixtureDef.friction = 0.4f;
                fixtureDef.filter.categoryBits = Constantes.CAT_TERRENO;

                body.createFixture(fixtureDef);
                shape.dispose();
                cuerposGenerados.add(body);
            }
        }
    }

    private void procesarCapaCorrientes(MapLayer layer) {
        for (MapObject object : layer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                float forceX = obtenerFloatPropiedad(object, "forceX", -8.0f);
                float forceY = obtenerFloatPropiedad(object, "forceY", 0.0f);

                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set((rect.x + rect.width / 2f) / Constantes.PPM, (rect.y + rect.height / 2f) / Constantes.PPM);

                ZonaCorriente zona = new ZonaCorriente(forceX, forceY);
                Body body = mundo.createBody(bodyDef);
                body.setUserData(zona);

                PolygonShape shape = new PolygonShape();
                shape.setAsBox((rect.width / 2f) / Constantes.PPM, (rect.height / 2f) / Constantes.PPM);

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;
                fixtureDef.isSensor = true;
                fixtureDef.filter.categoryBits = Constantes.CAT_CORRIENTE;
                fixtureDef.filter.maskBits = Constantes.CAT_JUGADOR;

                body.createFixture(fixtureDef).setUserData(zona);
                shape.dispose();
                cuerposGenerados.add(body);
            }
        }
    }

    private void procesarCapaSpawns(MapLayer layer, Array<Personaje> listaPersonajes) {
        for (MapObject object : layer.getObjects()) {
            String name = object.getName() != null ? object.getName() : "";
            float x = obtenerFloatPropiedad(object, "x", 0f);
            float y = obtenerFloatPropiedad(object, "y", 0f);

            if (name.equalsIgnoreCase("SpawnJugador") || name.equalsIgnoreCase("Jugador")) {
                spawnJugador.set(x / Constantes.PPM, y / Constantes.PPM);
            } else if (name.equalsIgnoreCase("TiburonAzul") || name.equalsIgnoreCase("Tiburon")) {
                if (recurso.isLoaded("texturas/tiburon.atlas", TextureAtlas.class)) {
                    TiburonAzul tiburon = new TiburonAzul(
                            recurso.get("texturas/tiburon.atlas", TextureAtlas.class).getRegions(),
                            0.15f, Animation.PlayMode.LOOP, pantalla, 192, 192, Personaje.ESTATICO);
                    tiburon.setPosition(x, y);
                    listaPersonajes.add(tiburon);
                }
            } else if (name.equalsIgnoreCase("Pulpo")) {
                if (recurso.isLoaded("texturas/pulpo.atlas", TextureAtlas.class)) {
                    Pulpo pulpo = new Pulpo(
                            recurso.get("texturas/pulpo.atlas", TextureAtlas.class).getRegions(),
                            0.1f, Animation.PlayMode.LOOP, pantalla, 48, 96, Personaje.ESTATICO);
                    pulpo.setPosition(x, y);
                    listaPersonajes.add(pulpo);
                }
            } else if (name.equalsIgnoreCase("PezAngel")) {
                if (recurso.isLoaded("texturas/pez1.atlas", TextureAtlas.class)) {
                    PezAngel pez = new PezAngel(
                            recurso.get("texturas/pez1.atlas", TextureAtlas.class).getRegions(),
                            0.1f, Animation.PlayMode.LOOP, pantalla, 64, 32, Personaje.ESTATICO);
                    pez.setPosition(x, y);
                    listaPersonajes.add(pez);
                }
            } else if (name.equalsIgnoreCase("PezGloboAmarillo")) {
                if (recurso.isLoaded("texturas/pezG.atlas", TextureAtlas.class)) {
                    PezGloboAmarillo pez = new PezGloboAmarillo(
                            recurso.get("texturas/pezG.atlas", TextureAtlas.class).getRegions(),
                            0.1f, Animation.PlayMode.LOOP, pantalla, 64, 32, Personaje.ESTATICO);
                    pez.setPosition(x, y);
                    listaPersonajes.add(pez);
                }
            } else if (name.equalsIgnoreCase("PezGloboNaranja")) {
                if (recurso.isLoaded("texturas/pezGlobo.atlas", TextureAtlas.class)) {
                    PezGloboNaranja pez = new PezGloboNaranja(
                            recurso.get("texturas/pezGlobo.atlas", TextureAtlas.class).getRegions(),
                            0.1f, Animation.PlayMode.LOOP, pantalla, 96, 64, Personaje.ESTATICO);
                    pez.setPosition(x, y);
                    listaPersonajes.add(pez);
                }
            } else if (name.equalsIgnoreCase("Bomba")) {
                if (recurso.isLoaded("texturas/bomba.png", Texture.class)) {
                    Bomba bomba = new Bomba(recurso.get("texturas/bomba.png", Texture.class), pantalla, 64, 64, Personaje.DIANAMICO);
                    bomba.setPosition(x, y);
                    listaPersonajes.add(bomba);
                }
            } else if (name.equalsIgnoreCase("Algas")) {
                if (recurso.isLoaded("texturas/algas.png", Texture.class)) {
                    Algas algas = new Algas(recurso.get("texturas/algas.png", Texture.class), pantalla, 96, 64, Personaje.CINESTECICO);
                    algas.setPosition(x, y);
                    listaPersonajes.add(algas);
                }
            } else if (name.equalsIgnoreCase("Perla") || name.equalsIgnoreCase("Coin") || name.equalsIgnoreCase("Moneda")) {
                TextureRegion region = null;
                if (recurso.isLoaded("particulas/circle3.png", Texture.class)) {
                    region = new TextureRegion(recurso.get("particulas/circle3.png", Texture.class));
                } else if (recurso.isLoaded("texturas/iconos.atlas", TextureAtlas.class)) {
                    region = recurso.get("texturas/iconos.atlas", TextureAtlas.class).findRegion("iconofaro");
                }
                if (region != null) {
                    Perla perla = new Perla(region, pantalla, rayHandler);
                    perla.setPosition(x, y);
                    listaPersonajes.add(perla);
                }
            } else if (name.equalsIgnoreCase("Burbuja") || name.equalsIgnoreCase("Oxigeno") || name.equalsIgnoreCase("Vida")) {
                TextureRegion region = null;
                if (recurso.isLoaded("particulas/circle4.png", Texture.class)) {
                    region = new TextureRegion(recurso.get("particulas/circle4.png", Texture.class));
                } else if (recurso.isLoaded("texturas/iconos.atlas", TextureAtlas.class)) {
                    region = recurso.get("texturas/iconos.atlas", TextureAtlas.class).findRegion("iconocorazon");
                }
                if (region != null) {
                    BurbujaOxigeno burbuja = new BurbujaOxigeno(region, pantalla, rayHandler);
                    burbuja.setPosition(x, y);
                    listaPersonajes.add(burbuja);
                }
            } else if (name.startsWith("PowerUp") || name.equalsIgnoreCase("Turbo") || name.equalsIgnoreCase("Escudo") || name.equalsIgnoreCase("Linterna")) {
                if (recurso.isLoaded("texturas/iconos.atlas", TextureAtlas.class)) {
                    TextureAtlas atlas = recurso.get("texturas/iconos.atlas", TextureAtlas.class);
                    TipoPowerUp tipo = TipoPowerUp.TURBO;
                    TextureRegion reg = atlas.findRegion("iconovelocidad");
                    if (name.toLowerCase().contains("escudo")) {
                        tipo = TipoPowerUp.ESCUDO;
                        reg = atlas.findRegion("iconocorazon");
                    } else if (name.toLowerCase().contains("linterna") || name.toLowerCase().contains("luz")) {
                        tipo = TipoPowerUp.LINTERNA;
                        reg = atlas.findRegion("iconofaro");
                    }
                    if (reg != null) {
                        PowerUpSubmarino pu = new PowerUpSubmarino(tipo, reg, pantalla, rayHandler);
                        pu.setPosition(x, y);
                        listaPersonajes.add(pu);
                    }
                }
            }
        }
    }

    private void procesarCapaLuces(MapLayer layer) {
        if (rayHandler == null) {
            return;
        }

        for (MapObject object : layer.getObjects()) {
            float x = obtenerFloatPropiedad(object, "x", 0f) / Constantes.PPM;
            float y = obtenerFloatPropiedad(object, "y", 0f) / Constantes.PPM;
            float distancia = obtenerFloatPropiedad(object, "distancia", 6.0f);
            String colorHex = object.getProperties().get("color", String.class);

            Color color = Color.CYAN;
            if (colorHex != null) {
                try {
                    color = Color.valueOf(colorHex);
                } catch (Exception ignored) {
                }
            }

            PointLight light = new PointLight(rayHandler, 300, color, distancia, x, y);
            light.setSoft(true);
            lucesGeneradas.add(light);
        }
    }

    private float obtenerFloatPropiedad(MapObject obj, String prop, float defaultVal) {
        Object val = obj.getProperties().get(prop);
        if (val instanceof Number) {
            return ((Number) val).floatValue();
        }
        if (val instanceof String) {
            try {
                return Float.parseFloat((String) val);
            } catch (NumberFormatException ignored) {
            }
        }
        return defaultVal;
    }

    public void liberar() {
        for (Body body : cuerposGenerados) {
            if (body != null) {
                mundo.destroyBody(body);
            }
        }
        cuerposGenerados.clear();

        for (PointLight light : lucesGeneradas) {
            if (light != null) {
                light.remove();
            }
        }
        lucesGeneradas.clear();
    }

    public float getAnchoMetros() {
        return anchoMetros;
    }

    public float getAltoMetros() {
        return altoMetros;
    }

    public Vector2 getSpawnJugador() {
        return spawnJugador;
    }

    public int[] getCapasFondo() {
        return capasFondo;
    }

    public int[] getCapasPrimerPlano() {
        return capasPrimerPlano;
    }
}
