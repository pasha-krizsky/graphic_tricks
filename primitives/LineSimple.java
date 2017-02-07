package com.gamedev.dreamteam.graphicTricks.primitives;

/**
 * Класс для однотонных линий, предоставляет float buffer,
 * хранящий 4 координаты, по 2 для каждой точки.
 */
public class LineSimple extends Line {

    public LineSimple(float x1, float y1, float x2, float y2) {
        vertices = new float[] {x1, y1, x2, y2};
        prepareBuffer();
    }
}
