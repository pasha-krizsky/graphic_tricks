package com.gamedev.dreamteam.graphicTricks.engine;

/**
 * Данный класс позволяет работать с камерой
 */
public class Camera {

    /** Положение камеры по X */
    float eyeX;
    /** Положение камеры по Y */
    float eyeY;
    /** Положение камеры по Z */
    float eyeZ;
    /** Куда смотрим по X */
    float centerX;
    /** Куда смотрим по Y */
    float centerY;
    /** Куда смотрим по Z */
    float centerZ;
    /** X составляющая up-вектора */
    float upX;
    /** Y составляющая up-вектора */
    float upY;
    /** Z составляющая up-вектора */
    float upZ;

    /**
     * Инициализирует матрицу вида. Сбрасывает переменные, отвечающие за матрицу вида
     */
    public void initialize() {

        eyeX = 0;
        eyeY = 0;
        eyeZ = 1;

        centerX = 0;
        centerY = 0;
        centerZ = -10;

        upX = 0;
        upY = 1;
        upZ = 0;

        GraphicTricks.setViewMatrix(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    /**
     * Смещение камеры влево
     * @param value - величина смещения
     */
    public void moveLeft(float value) {
        eyeX = -value;
        GraphicTricks.setViewMatrix(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    /**
     * Смещение камеры вправо
     * @param value - величина смещения
     */
    public void moveRight(float value) {
        eyeX = value;
        GraphicTricks.setViewMatrix(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    /**
     * Смещение камеры вверх
     * @param value - величина смещения
     */
    public void moveTop(float value) {
        eyeY = value;
        GraphicTricks.setViewMatrix(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    /**
     * Смещение камеры вниз
     * @param value - величина смещения
     */
    public void moveBottom(float value) {
        eyeY = -value;
        GraphicTricks.setViewMatrix(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    /**
     * Смещение камеры вперед
     * @param value - величина смещения
     */
    public void moveAhead(float value) {
        eyeZ = value;
        GraphicTricks.setViewMatrix(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    /**
     * Смещение камеры назад
     * @param value - величина смещения
     */
    public void moveBackward(float value) {
        eyeZ = -value;
        GraphicTricks.setViewMatrix(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    /**
     * Смещение камеры в указанную точку
     * @param x - величина смещения по X
     * @param y - величина смещения по Y
     * @param z - величина смещения по Z
     */
    public void move(float x, float y, float z) {
        eyeX = x;
        eyeY = y;
        eyeZ = z;
        GraphicTricks.setViewMatrix(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    /**
     * Смотрим левее
     * @param value - величина смещения
     */
    public void lookLeft(float value) {
        centerX = -value;
        GraphicTricks.setViewMatrix(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    /**
     * Смотрим правее
     * @param value - величина смещения
     */
    public void lookRight(float value) {
        centerX = value;
        GraphicTricks.setViewMatrix(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    /**
     * Смотрим выше
     * @param value - величина смещения
     */
    public void lookTop(float value) {
        centerY = value;
        GraphicTricks.setViewMatrix(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    /**
     * Смотрим ниже
     * @param value - величина смещения
     */
    public void lookBottom(float value) {
        centerY = -value;
        GraphicTricks.setViewMatrix(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    /**
     * Смотрим вперед
     * @param value - величина смещения
     */
    public void lookAhead(float value) {
        centerZ = value;
        GraphicTricks.setViewMatrix(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    /**
     * Смотрим назад
     * @param value - величина смещения
     */
    public void lookBackward(float value) {
        centerZ = -value;
        GraphicTricks.setViewMatrix(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    /**
     * Смотрим в указанную точку
     * @param x - величина смещения по X
     * @param y - величина смещения по Y
     * @param z - величина смещения по Z
     */
    public void look(float x, float y, float z) {
        centerX = x;
        centerY = y;
        centerZ = z;
        GraphicTricks.setViewMatrix(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }
}
