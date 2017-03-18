package com.gamedev.dreamteam.graphicTricks.primitives;

public class OctahedronSimple extends Octahedron {

    public OctahedronSimple(float top_x, float top_y, float top_z,
                            float height, float radius) {

        int ALL_COORDS = 36;

        vertices = new float[ALL_COORDS];
        vertices[0] = top_x;
        vertices[1] = top_y;
        vertices[2] = top_z;

        int step = 90;
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

        vertices[index] = top_x;
        index++;
        vertices[index] = top_y - 2 * height;
        index++;
        vertices[index] = top_z;
        index++;

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
