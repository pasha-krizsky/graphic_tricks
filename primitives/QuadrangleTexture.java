package com.gamedev.dreamteam.graphicTricks.primitives;

/** Класс четырехугольников, на которые накладываем текстуры
 *  Сопоставление координат:
 *  (x1, y1, z1) - (0, 1) - левый нижний угол
 *  (x2, y2, z2) - (1, 1) - правый нижний угол
 *  (x3, y3, z3) - (0, 0) - левый верхний угол
 *  (x4, y4, z4) - (1, 0) - правый верхний угол
 *  Либо явное указание координат s и t
 */
public class QuadrangleTexture extends Quadrangle {

    /** Конструктор, растягивающий текстуру на весь четырехугольник */
    public QuadrangleTexture(float x1, float y1, float z1, float x2, float y2, float z2,
                             float x3, float y3, float z3, float x4, float y4, float z4) {

        vertices = new float[]{
                x1, y1, z1, 0f, 1f, x2, y2, z2, 1f, 1f, x3, y3, z3, 0f, 0f, x4, y4, z4, 1f, 0f};
        prepareBuffer();
    }

    /** Конструктор с явным указанием координат текстуры */
    public QuadrangleTexture(float x1, float y1, float z1, float s1, float t1,
                             float x2, float y2, float z2, float s2, float t2,
                             float x3, float y3, float z3, float s3, float t3,
                             float x4, float y4, float z4, float s4, float t4) {

        vertices = new float[]{
                x1, y1, z1, s1, t1, x2, y2, z2, s2, t2, x3, y3, z3, s3, t3, x4, y4, z4, s4, t4};
        prepareBuffer();
    }
}