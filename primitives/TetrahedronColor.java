package com.gamedev.dreamteam.graphicTricks.primitives;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class TetrahedronColor extends Tetrahedron {

    private FloatBuffer vertexColorBuffer;
    protected float[] verticesColor;

    public TetrahedronColor(float top_x, float top_y, float top_z,
                            float top_x_color, float top_y_color, float top_z_color,
                            float bottom_x_color, float bottom_y_color, float bottom_z_color,
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

        verticesColor = new float[ALL_COORDS];

        // Заполнение массива с цветом
        verticesColor[0] = top_x_color;
        verticesColor[1] = top_y_color;
        verticesColor[2] = top_z_color;

        // Заполнение массива с цветом
        for (int i = 3; i <= 12; i+=3) {
            verticesColor[i]   = bottom_x_color;
            verticesColor[i+1] = bottom_y_color;
            verticesColor[i+2] = bottom_z_color;
        }

        //System.out.println(bottom_x_color + " " + bottom_y_color + " " + bottom_z_color);
        for (int i = 0; i <= 12; i+=1) {
            System.out.print(verticesColor[i] + " ");
            System.out.print(verticesColor[i+1] + " ");
            System.out.println(verticesColor[i+2]
            );
        }

        // Подготовка буфера цвета
        vertexColorBuffer = ByteBuffer.allocateDirect(15 * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        vertexColorBuffer.put(verticesColor);
        vertexColorBuffer.position(0);

    }

    public FloatBuffer getVertexColorBuffer() {
        return vertexColorBuffer;
    }

}
