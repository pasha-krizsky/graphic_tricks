package com.gamedev.dreamteam.graphicTricks.primitives;

/**
 * Класс для однотонных треугольиков, предоставляет float buffer,
 * хранящий 9 координат, по 3 для каждой точки.
 */
public class TriangleSimple extends Triangle {

    public TriangleSimple(
            float x1, float y1, float z1,
            float x2, float y2, float z2,
            float x3, float y3, float z3) {

        vertices = new float[] {x1, y1, z1, x2, y2, z2, x3, y3, z3};
        prepareBuffer();
    }
}
