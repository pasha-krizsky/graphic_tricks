package com.gamedev.dreamteam.graphicTricks.primitives;

/**
 * Класс для однотонных линий, предоставляет float buffer,
 * хранящий 6 координаты, по 3 для каждой точки.
 */
public class LineSimple extends Line {

    public LineSimple(float x1, float y1, float z1, float x2, float y2, float z2) {
        vertices = new float[] {x1, y1, z1, x2, y2, z2};
        prepareBuffer();
    }
}
