package com.gamedev.dreamteam.graphicTricks.engine;

public class Light {
    // Для смещения по трем координатам
    private float dx;
    private float dy;
    private float dz;

    // Для вращения на угол вокруг оси (0,0,0) - (rx, ry, rz)
    private float angle;
    private float rx;
    private float ry;
    private float rz;

    public Light() {}

    public void drop() {

        dx = 0;
        dy = 0;
        dz = 0;

        angle = 0;
        rx = 0;
        ry = 0;
        rz = 1;

        GraphicTricks.dropLightMatrix();
    }

    public void move(float valueX, float valueY, float valueZ) {
        dx = valueX;
        dy = valueY;
        dz = valueZ;
        GraphicTricks.translateLightMatrix(dx, dy, dz);
    }

    public void rotate(float angleValue, float x, float y, float z) {
        angle = angleValue;
        rx = x;
        ry = y;
        rz = z;
        GraphicTricks.rotateLightMatrix(angle, rx, ry, rz);
    }

    public void on() {
        GraphicTricks.on();
    }
}
