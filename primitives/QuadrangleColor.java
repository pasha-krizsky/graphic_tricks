package com.gamedev.dreamteam.graphicTricks.primitives;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/** Класс цветных четырехугольников */
public class QuadrangleColor extends Quadrangle {

    private float[] normal;
    protected FloatBuffer normalBuffer;

    public QuadrangleColor(float x1, float y1, float z1, float r1, float g1, float b1,
                           float x2, float y2, float z2, float r2, float g2, float b2,
                           float x3, float y3, float z3, float r3, float g3, float b3,
                           float x4, float y4, float z4, float r4, float g4, float b4) {

        vertices = new float[] {
                x1, y1, z1, r1, g1, b1,
                x2, y2, z2, r2, g2, b2,
                x3, y3, z3, r3, g3, b3,
                x4, y4, z4, r4, g4, b4};

        prepareBuffer();
    }

    public void addNormal(float x, float y, float z) {
        normal = new float[] {
                x, y, z,
                x, y, z,
                x, y, z,
                x, y, z
        };
        prepareNormalBuffer();
    }

    public FloatBuffer getNormalData() {
        return normalBuffer;
    }

    /** Метод, преобразующий заполненный массив вершин в буфер вершиню
     *  Вызывается в подклассах
     */
    private void prepareNormalBuffer() {
        normalBuffer = ByteBuffer.allocateDirect(normal.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        normalBuffer.put(normal);
        normalBuffer.position(0);
    }
}
