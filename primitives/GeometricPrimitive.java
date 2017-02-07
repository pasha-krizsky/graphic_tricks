package com.gamedev.dreamteam.graphicTricks.primitives;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Абстрактный класс, являющийся базовым для всех рисуемых объектов.
 */
public abstract class GeometricPrimitive {

    /** Массив вершин */
    protected float[] vertices;
    /** Буфер вершин для передачи в графический движок */
    protected FloatBuffer vertexBuffer;


    /** Метод, преобразующий заполненный массив вершин в буфер вершиню
     *  Вызывается в подклассах
     */
    protected void prepareBuffer() {
        vertexBuffer = ByteBuffer.allocateDirect(vertices.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
        vertices = null;
    }

    /**
     * Метод, возвращающий заполненный буфер вершин
     * @return - заполненный буфер вершин
     */
    public FloatBuffer getVertexData() {
        return vertexBuffer;
    }
}
