package com.gamedev.dreamteam.graphicTricks.primitives;

/**
 * Класс простых кругов. Для создания простого круга нужно указать координаты
 * центра круга и радиус.
 */
public class CircleSimple extends Circle {

    /**
     * Конструктор простого круга.
     * @param x_center - X центра круга
     * @param y_center - Y центра круга
     * @param radius - ридиус круга
     * @param width - ширина экрана
     * @param height - высота экрана
     */
    public CircleSimple(float x_center, float y_center, float radius, int width, int height) {

        vertices = new float[148];
        vertices[0] = x_center;
        vertices[1] = y_center*width/height;

        int step = 5;
        int index = 2;
        for (int angle = 0; angle <= 360; angle+=step) {
            float angleRad = (float) angle * (float) Math.PI / 180;
            vertices[index] = x_center + radius * (float) Math.cos(angleRad);
            index++;
            vertices[index] = y_center*width/height
                    + radius*width/height
                    * (float) Math.sin(angleRad);
            index++;
        }

        prepareBuffer();
    }
}
