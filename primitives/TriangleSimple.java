package com.gamedev.dreamteam.graphicTricks.primitives;

/**
 * Класс для однотонных треугольиков, предоставляет float buffer,
 * хранящий 6 координат, по 2 для каждой точки.
 */
public class TriangleSimple extends Triangle {

    public TriangleSimple(float x1, float y1, float x2, float y2, float x3, float y3) {
        vertices = new float[] {x1, y1, x2, y2, x3, y3};
        prepareBuffer();
    }
}
