package com.gamedev.dreamteam.graphicTricks.primitives;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class OctahedronColor extends Octahedron {
    private FloatBuffer vertexColorBuffer;
    protected float[] verticesColor;

    public OctahedronColor(float top_x, float top_y, float top_z,
                           float top_x_color, float top_y_color, float top_z_color,
                           float middle_x_color, float middle_y_color, float middle_z_color,
                           float bottom_x_color, float bottom_y_color, float bottom_z_color,
                           float height, float radius) {

        int ALL_COORDS = 36;

        vertices = new float[ALL_COORDS];
        vertices[0] = top_x;
        vertices[1] = top_y;
        vertices[2] = top_z;

        int step = 90;
        int index = 3;

        verticesColor = new float[ALL_COORDS];

        verticesColor[0] = top_x_color;
        verticesColor[1] = top_y_color;
        verticesColor[2] = top_z_color;

        for (int angle = 0; angle <= 360; angle+=step) {
            float angleRad = (float) angle * (float) Math.PI / 180;
            vertices[index] = top_x + radius * (float) Math.cos(angleRad);
            verticesColor[index]   = middle_x_color;
            index++;
            vertices[index] = top_y - height;
            verticesColor[index]   = middle_y_color;
            index++;
            vertices[index] = top_z + radius * (float) Math.sin(angleRad);
            verticesColor[index]   = middle_z_color;
            index++;
        }

        vertices[index] = top_x;
        verticesColor[index] = bottom_x_color;
        index++;
        vertices[index] = top_y - 2 * height;
        verticesColor[index] = bottom_y_color;
        index++;
        vertices[index] = top_z;
        verticesColor[index] = bottom_z_color;
        index++;

        for (int angle = 0; angle <= 360; angle+=step) {
            float angleRad = (float) angle * (float) Math.PI / 180;
            vertices[index] = top_x + radius * (float) Math.cos(angleRad);
            verticesColor[index] = middle_x_color;
            index++;
            vertices[index] = top_y - height;
            verticesColor[index] = middle_y_color;
            index++;
            vertices[index] = top_z + radius * (float) Math.sin(angleRad);
            verticesColor[index] = middle_z_color;
            index++;
        }


        prepareBuffer();

        vertexColorBuffer = ByteBuffer.allocateDirect(ALL_COORDS * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        vertexColorBuffer.put(verticesColor);
        vertexColorBuffer.position(0);

    }

    public FloatBuffer getVertexColorBuffer() {
        return vertexColorBuffer;
    }
}
