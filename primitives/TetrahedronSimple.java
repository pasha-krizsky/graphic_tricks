package com.gamedev.dreamteam.graphicTricks.primitives;

public class TetrahedronSimple extends Tetrahedron {

    private int amountOfAngles;

    public TetrahedronSimple(float top_x, float top_y, float top_z,
                             float height, float radius) {

        int ALL_COORDS = 15;

        vertices = new float[ALL_COORDS];
        vertices[0] = top_x;
        vertices[1] = top_y;
        vertices[2] = top_z;

        int step = 120;
        int index = 3;

        for (int angle = 0; angle <= 360; angle+=step) {
            float angleRad = (float) angle * (float) Math.PI / 180;
            vertices[index] = top_x + radius * (float) Math.cos(angleRad);
            index++;
            vertices[index] = top_y - height;
            index++;
            vertices[index] = top_z + radius * (float) Math.sin(angleRad);
            index++;
        }

        prepareBuffer();

    }
}
