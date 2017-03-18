package com.gamedev.dreamteam.graphicTricks.primitives;

import com.gamedev.dreamteam.graphicTricks.engine.Direction;

public class CircleSectorSimple extends CircleSector {

    private int numberVertices;
    public CircleSectorSimple(float x_center, float y_center, float z_center,
                              float radius, float angle_sector, Direction direction) {

        vertices = new float[222];
        vertices[0] = x_center;
        vertices[1] = y_center;
        vertices[2] = z_center;

        int step = 5;
        int index = 3;

        switch (direction) {

            case CLOCKWISE:

                for (float angle = 0; angle <= angle_sector; angle+=step) {
                    float angleRad = (float) angle * (float) Math.PI / 180;
                    vertices[index] = x_center + radius * (float) Math.cos(angleRad);
                    index++;
                    vertices[index] = -(y_center + radius * (float) Math.sin(angleRad));
                    index++;
                    vertices[index] = z_center;
                    index++;
                }
                break;

            case COUNTERCLOCKWISE:

                for (float angle = 0; angle <= angle_sector; angle+=step) {
                    float angleRad = (float) angle * (float) Math.PI / 180;
                    vertices[index] = x_center + radius * (float) Math.cos(angleRad);
                    index++;
                    vertices[index] = y_center + radius * (float) Math.sin(angleRad);
                    index++;
                    vertices[index] = z_center;
                    index++;
                }
                break;
        }

        numberVertices = index-1;
        numberVertices /= 3;
        numberVertices++;

        prepareBuffer();
    }

    public int getNumberVertices() {
        return numberVertices;
    }
}
