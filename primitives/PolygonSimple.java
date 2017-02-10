package com.gamedev.dreamteam.graphicTricks.primitives;

/** Класс простых многоугольников */
public class PolygonSimple extends Polygon {

    public PolygonSimple(float x_center, float y_center, float z_center, float... coodrs) {

        // Точки, составляющие многоугольник и его центр
        vertices = new float[coodrs.length + 6];
        vertices[0] = x_center;
        vertices[1] = y_center;
        vertices[2] = z_center;

        int index = 3;
        for (int i = 0; i < coodrs.length; i++) {
            vertices[index] = coodrs[i];
            index++;
        }

        // Для замыкания полигона
        vertices[vertices.length-3] = vertices[3];
        vertices[vertices.length-2] = vertices[4];
        vertices[vertices.length-1] = vertices[5];
        // Количество вершин
        numberOfVertices = vertices.length / 3;

        prepareBuffer();

    }
}
