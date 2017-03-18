package com.gamedev.dreamteam.graphicTricks.primitives;

public class CylinderSimple extends GeometricPrimitive {

    public CylinderSimple(
            float x_start, float y_start, float z_start,
            float r_bottom, float r_top, float h) {

        int ALL_COORDS = 882;
        vertices = new float[ALL_COORDS];
        vertices[0] = x_start;
        vertices[1] = y_start;
        vertices[2] = z_start;

        int step = 5;
        int index = 3;

        for (int angle = 0; angle <= 360; angle+=step) {
            float angleRad = (float) angle * (float) Math.PI / 180;
            vertices[index] = x_start + r_bottom * (float) Math.cos(angleRad);
            index++;
            vertices[index] = y_start;
            index++;
            vertices[index] = z_start + r_bottom * (float) Math.sin(angleRad);
            index++;
        }

        vertices[index] = x_start;
        index++;
        vertices[index] = h;
        index++;
        vertices[index] = z_start;
        index++;

        for (int angle = 0; angle <= 360; angle+=step) {
            float angleRad = (float) angle * (float) Math.PI / 180;
            vertices[index] = x_start + r_top * (float) Math.cos(angleRad);
            index++;
            vertices[index] = h;
            index++;
            vertices[index] = z_start + r_top * (float) Math.sin(angleRad);
            index++;
        }

        int SIZE = index;
        for (int i = 3; i <= SIZE-6; i+=3) {
            if (i == 222 || i == 223 || i == 224) {
                continue;
            }
            if (i % 2 == 0) {
                vertices[index] = vertices[i];
                index++;
                vertices[index] = vertices[i+1];
                index++;
                vertices[index] = vertices[i+2];
            }
            else {
                vertices[index] = vertices[i+222];
                index++;
                vertices[index] = vertices[i+223];
                index++;
                vertices[index] = vertices[i+224];
            }
            index++;
        }

        prepareBuffer();
    }
}
