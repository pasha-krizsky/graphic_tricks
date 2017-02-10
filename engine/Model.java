package com.gamedev.dreamteam.graphicTricks.engine;

/**
 * Класс, который позволяет работать с камерой
 */
public class Model {

    // Для смещения по трем координатам
    private float dx;
    private float dy;
    private float dz;

    // Для масштабирования по трем координатам
    private float kx;
    private float ky;
    private float kz;

    // Для вращения на угол вокруг оси (0,0,0) - (rx, ry, rz)
    private float angle;
    private float rx;
    private float ry;
    private float rz;

    public Model() {}

    public void drop() {

        dx = 0;
        dy = 0;
        dz = 0;

        kx = 1;
        ky = 1;
        kz = 1;

        angle = 0;
        rx = 0;
        ry = 0;
        rz = 1;

        GraphicTricks.dropModelMatrix();
    }

    public void moveLeft(float value) {
        dx = -value;
        GraphicTricks.translateModelMatrix(dx, dy, dz);
    }

    public void moveRight(float value) {
        dx = value;
        GraphicTricks.translateModelMatrix(dx, dy, dz);
    }

    public void moveTop(float value) {
        dy = value;
        GraphicTricks.translateModelMatrix(dx, dy, dz);
    }

    public void moveBottom(float value) {
        dy = -value;
        GraphicTricks.translateModelMatrix(dx, dy, dz);
    }

    public void moveAhead(float value) {
        dz = value;
        GraphicTricks.translateModelMatrix(dx, dy, dz);
    }

    public void moveBackward(float value) {
        dz = value;
        GraphicTricks.translateModelMatrix(dx, dy, dz);
    }

    public void rotateAroundX(float angleValue) {
        angle = angleValue;
        rx = 1;
        ry = 0;
        rz = 0;
        GraphicTricks.rotateModelMatrix(angle, rx, ry, rz);
    }

    public void rotateAroundY(float angleValue) {
        angle = angleValue;
        rx = 0;
        ry = 1;
        rz = 0;
        GraphicTricks.rotateModelMatrix(angle, rx, ry, rz);
    }

    public void rotateAroundZ(float angleValue) {
        angle = angleValue;
        rx = 0;
        ry = 0;
        rz = 1;
        GraphicTricks.rotateModelMatrix(angle, rx, ry, rz);
    }

    public void rotate(float angleValue, float x, float y, float z) {
        angle = angleValue;
        rx = x;
        ry = y;
        rz = z;
        GraphicTricks.rotateModelMatrix(angle, rx, ry, rz);
    }

    public void scaleX(float x) {
        kx = x;
        GraphicTricks.scaleModelMatrix(kx, ky, kz);
    }

    public void scaleY(float y) {
        ky = y;
        GraphicTricks.scaleModelMatrix(kx, ky, kz);
    }

    public void scaleZ(float z) {
        kz = z;
        GraphicTricks.scaleModelMatrix(kx, ky, kz);
    }

    public void scale(float x, float y, float z) {
        kx = x;
        ky = y;
        kz = z;
        GraphicTricks.scaleModelMatrix(kx, ky, kz);
    }
}
