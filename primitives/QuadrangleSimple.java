package com.gamedev.dreamteam.graphicTricks.primitives;

public class QuadrangleSimple extends Quadrangle {
    public QuadrangleSimple(float x1, float y1, float z1, float x2, float y2, float z2,
                            float x3, float y3, float z3, float x4, float y4, float z4) {

        vertices = new float[]{x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4};
        prepareBuffer();

    }
}
