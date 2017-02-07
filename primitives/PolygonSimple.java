package com.gamedev.dreamteam.graphicTricks.primitives;

/** Класс простых многоугольников */
public class PolygonSimple extends Polygon {

    public PolygonSimple(float x_center, float y_center, float... coodrs) {

        // Точки, составляющие многоугольник и его центр
        vertices = new float[coodrs.length + 4];
        vertices[0] = x_center;
        vertices[1] = y_center;

        int index = 2;
        for (int i = 0; i < coodrs.length; i++) {
            vertices[index] = coodrs[i];
            index++;
        }

        // Для замыкания полигона
        vertices[vertices.length-2] = vertices[2];
        vertices[vertices.length-1] = vertices[3];
        // Количество вершин
        numberOfVertices = vertices.length / 2;

        prepareBuffer();

    }
}
