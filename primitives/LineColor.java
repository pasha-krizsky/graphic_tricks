package com.gamedev.dreamteam.graphicTricks.primitives;

/**
 * Класс цветных линий, предоставляет float buffer,
 * хранящий 10 координат, по 2 для положения точки, по 3 для цвета точки
 */
public class LineColor extends Line {
    public LineColor (float x1, float y1,
                      float r1, float g1, float b1,
                      float x2, float y2,
                      float r2, float g2, float b2) {

        vertices = new float[] {x1, y1, r1, g1, b1, x2, y2, r2, g2, b2};
        prepareBuffer();
    }

}
