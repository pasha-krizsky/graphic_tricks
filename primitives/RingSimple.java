package com.gamedev.dreamteam.graphicTricks.primitives;

public class RingSimple extends Ring {

    public RingSimple(float x_center, float y_center, float z_center,
                      float r_small, float r_large) {

        vertices = new float[432];

        int step = 5;
        int angle = 0;
        int index = 0;

        while (angle <= 360) {
            float angleRad = (float) angle * (float) Math.PI / 180;
            // X
            vertices[index] = x_center + r_large * (float) Math.cos(angleRad);
            index++;
            vertices[index] = y_center + r_large * (float) Math.sin(angleRad);
            index++;
            vertices[index] = 0;
            index++;
            angle += step;
            angleRad = (float) angle * (float) Math.PI / 180;
            // Y
            vertices[index] = x_center + r_small * (float) Math.cos(angleRad);
            index++;
            vertices[index] = y_center + r_small * (float) Math.sin(angleRad);
            index++;
            // Z
            vertices[index] = 0;
            index++;
            angle += step;
        }

        prepareBuffer();
    }
}
