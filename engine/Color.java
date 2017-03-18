package com.gamedev.dreamteam.graphicTricks.engine;

/**
 * Stores basic colors
 */
public enum Color {

    BLACK(0.0f, 0.0f, 0.0f, 1.0f),
    WHITE(1.0f, 1.0f, 1.0f, 1.0f),
    RED(1.0f, 0.0f, 0.0f, 1.0f),
    LIME(0.0f, 1.0f, 0.0f, 1.0f),
    BLUE(0.0f, 0.0f, 1.0f, 1.0f),
    YELLOW(1.0f, 1.0f, 0.0f, 1.0f),
    CYAN(0.0f, 1.0f, 1.0f, 1.0f),
    MAGENTA(1.0f, 0.0f, 1.0f, 1.0f),
    SILVER(0.75f, 0.75f, 0.75f, 1.0f),
    GRAY(0.5f, 0.5f, 0.5f, 1.0f),
    MAROON(0.5f, 0.0f, 0.0f, 1.0f),
    OLIVE(0.5f, 0.5f, 0.0f, 1.0f),
    GREEN(0.0f, 0.5f, 0.0f, 1.0f),
    PURPLE(0.5f, 0.0f, 0.5f, 1.0f),
    TEAL(0.0f, 0.5f, 0.5f, 1.0f),
    NAVY(0.0f, 0.0f, 0.5f, 1.0f);

    private float r;
    private float g;
    private float b;
    private float a;

    private Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
        return b;
    }

    public float getA() {
        return a;
    }
}
