package com.diamon.nucleo;

/**
 * Constantes globales de configuración, físicas, unidades métricas y categorías Box2D.
 */
public final class Constantes {

    private Constantes() {
    }

    /**
     * Píxeles por metro (PPM) para conversión exacta entre coordenadas gráficas y Box2D.
     * 64 píxeles = 1 metro en el mundo de físicas.
     */
    public static final float PPM = 64.0f;

    /**
     * Resolución virtual de pantalla (base 16:9).
     */
    public static final float ANCHO_VIRTUAL = 1280.0f;
    public static final float ALTO_VIRTUAL = 720.0f;

    /**
     * Dimensiones del Viewport de la cámara de físicas en metros (1280/64 = 20m, 720/64 = 11.25m).
     */
    public static final float ANCHO_METROS = ANCHO_VIRTUAL / PPM;
    public static final float ALTO_METROS = ALTO_VIRTUAL / PPM;

    /**
     * Categorías de colisión para filtros de Box2D (bits de categoría).
     */
    public static final short CAT_JUGADOR = 0x0001;
    public static final short CAT_TERRENO = 0x0002;
    public static final short CAT_ENEMIGO = 0x0004;
    public static final short CAT_CORRIENTE = 0x0008;
    public static final short CAT_ITEM = 0x0010;
    public static final short CAT_BALA = 0x0020;

    /**
     * Parámetros de física hidrodinámica submarina.
     */
    public static final float AMORTIGUACION_AGUA = 2.2f;
    public static final float GRAVEDAD_SUBMARINA = 0.0f; // Flotabilidad neutra por defecto
    public static final float VELOCIDAD_ROTACION_PEZ = 6.0f;
}
