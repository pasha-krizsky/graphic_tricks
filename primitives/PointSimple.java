package com.gamedev.dreamteam.graphicTricks.primitives;

/**
 * Класс для точек, предоставляет float buffer,
 * хранящий 3 координаты одной точки.
 */
public class PointSimple extends Point {

    public PointSimple(float x1, float y1, float z1) {
        vertices = new float[] {x1, y1, z1};
        prepareBuffer();
    }
}
