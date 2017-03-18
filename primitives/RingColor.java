package com.gamedev.dreamteam.graphicTricks.primitives;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class RingColor extends Ring {

    private FloatBuffer vertexColorBuffer;
    protected float[] verticesColor;

    public RingColor(float x_center, float y_center, float z_center,
                     float r_small, float g_small, float b_small,
                     float r_large, float g_large, float b_large,
                     float radius_small, float radius_large) {

        vertices = new float[432];

        int step = 5;
        int angle = 0;
        int index = 0;

        // X Y Z
        while (angle <= 360) {
            float angleRad = (float) angle * (float) Math.PI / 180;
            // X
            vertices[index] = x_center + radius_large * (float) Math.cos(angleRad);
            index++;
            vertices[index] = y_center + radius_large * (float) Math.sin(angleRad);
            index++;
            vertices[index] = 0;
            index++;
            angle += step;
            angleRad = (float) angle * (float) Math.PI / 180;
            // Y
            vertices[index] = x_center + radius_small * (float) Math.cos(angleRad);
            index++;
            vertices[index] = y_center + radius_small * (float) Math.sin(angleRad);
            index++;
            // Z
            vertices[index] = 0;
            index++;
            angle += step;
        }

        verticesColor = new float[432];

        // R G B
        for (int i = 0; i < 426; i += 6) {

            verticesColor[i] = r_small;
            verticesColor[i+1] = g_small;
            verticesColor[i+2] = b_small;

            verticesColor[i+3] = r_large;
            verticesColor[i+4] = g_large;
            verticesColor[i+5] = b_large;
        }

        // Подготовка буфера цвета
        vertexColorBuffer = ByteBuffer.allocateDirect(verticesColor.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        vertexColorBuffer.put(verticesColor);
        vertexColorBuffer.position(0);
        verticesColor = null;

        prepareBuffer();
    }

    public FloatBuffer getVertexColorBuffer() {
        return vertexColorBuffer;
    }

}
