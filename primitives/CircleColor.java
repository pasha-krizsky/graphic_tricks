package com.gamedev.dreamteam.graphicTricks.primitives;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Класс цветных кругов. Для создания цветного круга нужно указать цвет и координаты
 * центра круга, радиус и цвет края круга.
 */
public class CircleColor extends Circle {

    /** Буфер, хранящий цвет центра и цвет края */
    private FloatBuffer vertexColorBuffer;
    /** Массив, хранящий цвет центра и цвет края */
    protected float[] verticesColor;

    /** Конструктор цветного круга. Принимает координаты центра, цветовые координаты центра,
     *  радиус, цветовые координаты края, ширину и высоту экрана. Высота экрана преобразуется
     *  до нового значения, учитывающего соотношение сторон.
     */
    public CircleColor(float x_center, float y_center,
                       float r_center, float g_center, float b_center,
                       float radius,
                       float r_edge, float g_edge, float b_edge,
                       int width, int height) {

        // Выделение памяти под массивы
        vertices = new float[148];
        verticesColor = new float[225];

        // Заполнение массива с цветом
        verticesColor[0] = r_center;
        verticesColor[1] = g_center;
        verticesColor[2] = b_center;

        // Заполнение массива с цветом
        for (int i = 3; i < 222; i+=3) {
            verticesColor[i] = r_edge;
            verticesColor[i+1] = g_edge;
            verticesColor[i+2] = b_edge;
        }

        // Заполнение массива вершин
        vertices[0] = x_center;
        vertices[1] = y_center*width/height;

        // Шаг угла
        int step = 5;
        // Индекс координаты вершины
        int index = 2;
        // Вычисление координат вершин
        for (int angle = 0; angle <= 360; angle+=step) {
            float angleRad = (float) angle * (float) Math.PI / 180;
            vertices[index] = x_center + radius * (float) Math.cos(angleRad);
            index++;
            vertices[index] =
                    y_center*width/height
                    + radius*width/height
                    * (float) Math.sin(angleRad);
            index++;
        }

        prepareBuffer();

        // Подготовка буфера цвета
        vertexColorBuffer = ByteBuffer.allocateDirect(verticesColor.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        vertexColorBuffer.put(verticesColor);
        vertexColorBuffer.position(0);
        verticesColor = null;
    }

    /**
     * Возвращает буфер цвета
     * @return - буфер цвета
     */
    public FloatBuffer getVertexColorBuffer() {
        return vertexColorBuffer;
    }
}
