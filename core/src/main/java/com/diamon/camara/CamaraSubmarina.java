package com.diamon.camara;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

/**
 * Controlador de cámara submarina con seguimiento suave (Lerp), Deadzone,
 * delimitación estricta a los límites del mapa TMX (Clamping) y zoom adaptativo.
 */
public class CamaraSubmarina {

    private final OrthographicCamera camara;

    private float mapaAnchoMetros;
    private float mapaAltoMetros;

    private float velocidadLerp = 3.5f;

    private float deadzoneAncho = 1.5f;
    private float deadzoneAlto = 1.0f;

    private float zoomObjetivo = 1.0f;
    private float velocidadZoom = 2.0f;

    public CamaraSubmarina(float anchoMetros, float altoMetros) {
        camara = new OrthographicCamera();
        camara.setToOrtho(false, anchoMetros, altoMetros);
        camara.position.set(anchoMetros / 2.0f, altoMetros / 2.0f, 0.0f);
        camara.update();

        this.mapaAnchoMetros = anchoMetros;
        this.mapaAltoMetros = altoMetros;
    }

    public void setDimensionesMapa(float anchoMetros, float altoMetros) {
        this.mapaAnchoMetros = anchoMetros;
        this.mapaAltoMetros = altoMetros;
    }

    /**
     * Actualiza el seguimiento hacia el objetivo (pez payaso) con suavizado e interpolación.
     */
    public void actualizar(Vector2 posicionObjetivo, float delta) {
        if (delta <= 0) {
            return;
        }

        // Suavizado de zoom
        if (Math.abs(camara.zoom - zoomObjetivo) > 0.001f) {
            camara.zoom = MathUtils.lerp(camara.zoom, zoomObjetivo, velocidadZoom * delta);
        }

        // Mitades del viewport con zoom aplicado
        float medioAnchoVisible = (camara.viewportWidth * camara.zoom) / 2.0f;
        float medioAltoVisible = (camara.viewportHeight * camara.zoom) / 2.0f;

        // Cálculo de desplazamiento fuera de la zona muerta (Deadzone)
        float diffX = posicionObjetivo.x - camara.position.x;
        float diffY = posicionObjetivo.y - camara.position.y;

        float targetX = camara.position.x;
        float targetY = camara.position.y;

        if (Math.abs(diffX) > deadzoneAncho) {
            targetX = posicionObjetivo.x - Math.signum(diffX) * deadzoneAncho;
        }

        if (Math.abs(diffY) > deadzoneAlto) {
            targetY = posicionObjetivo.y - Math.signum(diffY) * deadzoneAlto;
        }

        // Interpolación lineal suave (Lerp)
        camara.position.x += (targetX - camara.position.x) * velocidadLerp * delta;
        camara.position.y += (targetY - camara.position.y) * velocidadLerp * delta;

        // Delimitación estricta a los bordes del mapa (Clamping)
        if (mapaAnchoMetros > camara.viewportWidth * camara.zoom) {
            camara.position.x = MathUtils.clamp(camara.position.x, medioAnchoVisible, mapaAnchoMetros - medioAnchoVisible);
        } else {
            camara.position.x = mapaAnchoMetros / 2.0f;
        }

        if (mapaAltoMetros > camara.viewportHeight * camara.zoom) {
            camara.position.y = MathUtils.clamp(camara.position.y, medioAltoVisible, mapaAltoMetros - medioAltoVisible);
        } else {
            camara.position.y = mapaAltoMetros / 2.0f;
        }

        camara.update();
    }

    public void setZoomObjetivo(float zoom) {
        this.zoomObjetivo = MathUtils.clamp(zoom, 0.5f, 2.0f);
    }

    public OrthographicCamera getCamara() {
        return camara;
    }

    public Matrix4 getCombined() {
        return camara.combined;
    }

    public float getX() {
        return camara.position.x;
    }

    public float getY() {
        return camara.position.y;
    }

    public void setPosicion(float x, float y) {
        camara.position.set(x, y, 0);
        camara.update();
    }
}
