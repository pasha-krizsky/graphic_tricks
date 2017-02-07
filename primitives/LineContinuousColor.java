package com.gamedev.dreamteam.graphicTricks.primitives;

/** Класс цветных непренымных линий */
public class LineContinuousColor extends Line {

    /** Количество вершин */
    protected int numberOfVertices;

    /** Конструктор, принимающий произвольное кол-во координат
     *  @param coords - координаты в виде (x, y, r, g, b, x2, y2, r2, g2, b2...)
     */
    public LineContinuousColor (float... coords) {

        vertices = new float[coords.length];
        vertices = coords;
        numberOfVertices = coords.length / 5;
        prepareBuffer();
    }

    /**
     * Метод для получения кол-ва вершин
     * @return - кол-во вершин
     */
    public int getNumberOfVertices() {
        return numberOfVertices;
    }
}
