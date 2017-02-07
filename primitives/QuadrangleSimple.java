package com.gamedev.dreamteam.graphicTricks.primitives;

/** Класс простых четырехугольников */
public class QuadrangleSimple extends Quadrangle {

    public QuadrangleSimple(float x1, float y1, float x2, float y2,
                            float x3, float y3, float x4, float y4) {

        vertices = new float[]{x1, y1, x2, y2, x3, y3, x4, y4};
        prepareBuffer();
    }
}
