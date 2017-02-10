package com.gamedev.dreamteam.graphicTricks.primitives;

public class PolygonColor extends Polygon {

    public PolygonColor(float x_center, float y_center, float z_center,
                        float r_center, float g_center, float b_center,
                        float... coords) {

        // Точки, составляющие полигон и его центр
        vertices = new float[coords.length + 12];
        vertices[0] = x_center;
        vertices[1] = y_center;
        vertices[2] = z_center;
        vertices[3] = r_center;
        vertices[4] = g_center;
        vertices[5] = b_center;

        int index = 6;
        for (int i = 0; i < coords.length; i++) {
            vertices[index] = coords[i];
            index++;
        }

        // Для замыкания полигона
        vertices[vertices.length-6] = vertices[6];
        vertices[vertices.length-5] = vertices[7];
        vertices[vertices.length-4] = vertices[8];
        vertices[vertices.length-3] = vertices[9];
        vertices[vertices.length-2] = vertices[10];
        vertices[vertices.length-1] = vertices[11];

        numberOfVertices = vertices.length / 6;
        prepareBuffer();

    }
}
