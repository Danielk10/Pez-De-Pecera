package com.diamon.items;

/**
 * Tipos de Power-Up submarinos disponibles en el juego.
 */
public enum TipoPowerUp {
    /**
     * Turbo Propulsión: Aumenta la fuerza de nado e inercia x1.8 durante unos segundos.
     */
    TURBO(8.0f),

    /**
     * Escudo de Burbuja: Protege contra 1 impacto de enemigo o mina submarina.
     */
    ESCUDO(15.0f),

    /**
     * Linterna Abisal / Bioluminiscencia: Duplica el radio de luz en profundidades oscuras.
     */
    LINTERNA(12.0f);

    private final float duracionSegundos;

    TipoPowerUp(float duracionSegundos) {
        this.duracionSegundos = duracionSegundos;
    }

    public float getDuracionSegundos() {
        return duracionSegundos;
    }
}
