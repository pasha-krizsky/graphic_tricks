package com.gamedev.dreamteam.graphicTricks.primitives;

/** Класс многоугольников */
public abstract class Polygon extends GeometricPrimitive {
    /** Кол-во вершин */
    protected int numberOfVertices;

    /** Вернуть кол-во вершин */
    public int getNumberOfVertices() {
        return numberOfVertices;
    }

}
