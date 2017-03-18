package com.gamedev.dreamteam.graphicTricks.primitives;

import java.nio.ByteBuffer;

public class CubeSimple extends Cube {

    private ByteBuffer indexBuffer;
    protected byte[] indexArray;

    public CubeSimple(float left_bottom_x, float left_bottom_y, float left_bottom_z,
                      float edge) {

        int POINTS = 8;
        int ALL_COORDS = 3 * POINTS;

        vertices = new float[ALL_COORDS];

        // Near
        // Left bottom
        vertices[0] = left_bottom_x;
        vertices[1] = left_bottom_y;
        vertices[2] = left_bottom_z;

        // Right bottom
        vertices[3] = left_bottom_x + edge;
        vertices[4] = left_bottom_y;
        vertices[5] = left_bottom_z;

        // Top left
        vertices[6] = left_bottom_x;
        vertices[7] = left_bottom_y + edge;
        vertices[8] = left_bottom_z;

        // Top right
        vertices[9] = left_bottom_x + edge;
        vertices[10] = left_bottom_y + edge;
        vertices[11] = left_bottom_z;

        // Far
        // Left bottom
        vertices[12] = left_bottom_x;
        vertices[13] = left_bottom_y;
        vertices[14] = left_bottom_z - edge;

        // Right bottom
        vertices[15] = left_bottom_x + edge;
        vertices[16] = left_bottom_y;
        vertices[17] = left_bottom_z - edge;

        // Top left
        vertices[18] = left_bottom_x;
        vertices[19] = left_bottom_y + edge;
        vertices[20] = left_bottom_z - edge;

        // Top right
        vertices[21] = left_bottom_x + edge;
        vertices[22] = left_bottom_y + edge;
        vertices[23] = left_bottom_z - edge;

        prepareBuffer();

        indexArray = new byte[] {
                // Near
                0, 1, 2,
                2, 1, 3,

                // Far
                4, 5, 6,
                6, 5, 7,

                // Left
                0, 2, 4,
                4, 2, 7,

                // Right
                1, 3, 5,
                5, 3, 7,

                // Top
                2, 3, 6,
                6, 3, 7,

                // Bottom
                0, 1, 4,
                4, 1, 5
        };

        indexBuffer =  ByteBuffer.allocateDirect(36).put(indexArray);
        indexBuffer.position(0);
    }

    public ByteBuffer getIndexBuffer() {
        return indexBuffer;
    }

}
