package com.gamedev.dreamteam.graphicTricks.primitives;

/** Класс простых непренымных линий */
public class LineContinuousSimple extends Line {

    protected int numberOfVertices;

    public LineContinuousSimple(float... coords) {
        vertices = new float[coords.length];
        vertices = coords;
        numberOfVertices = coords.length / 2;
        prepareBuffer();

    }

    public int getNumberOfVertices() {
        return numberOfVertices;
    }
}
