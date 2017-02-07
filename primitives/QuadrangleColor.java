package com.gamedev.dreamteam.graphicTricks.primitives;

/** Класс цветных четырехугольников */
public class QuadrangleColor extends Quadrangle {
    public QuadrangleColor(float x1, float y1, float r1, float g1, float b1,
                           float x2, float y2, float r2, float g2, float b2,
                           float x3, float y3, float r3, float g3, float b3,
                           float x4, float y4, float r4, float g4, float b4) {

        vertices = new float[] {
                x1, y1, r1, g1, b1,
                x2, y2, r2, g2, b2,
                x3, y3, r3, g3, b3,
                x4, y4, r4, g4, b4};

        prepareBuffer();
    }
}
