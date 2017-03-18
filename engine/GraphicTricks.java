package com.gamedev.dreamteam.graphicTricks.engine;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

// Импорт примитивов
import com.gamedev.dreamteam.graphicTricks.R;
import com.gamedev.dreamteam.graphicTricks.primitives.*;

// Импорт утилиты для чтения шейдеров
import com.gamedev.dreamteam.graphicTricks.utils.ShaderUtils;
import com.gamedev.dreamteam.graphicTricks.utils.TextureUtils;

// Импорт методов OpenGL
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_LINE_LOOP;
import static android.opengl.GLES20.GL_LINE_STRIP;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_UNSIGNED_BYTE;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniform3f;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;


/**
 * Класс, предоставляющий удобный интерфейс для создания объектов и их отрисовки.
 */
public class GraphicTricks {

    private static GraphicContext context = new GraphicContext();

    public static int loadTexture(Context context, int resourceId) {
        return TextureUtils.loadTexture(context, resourceId);
    }

    /** Фабричный метод для создания простых точек, Z=0 */
    public static PointSimple createPointSimple(float x, float y) {
        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new PointSimple(x, y, 0);
    }

    /** Фабричный метод для создания простых точек */
    public static PointSimple createPointSimple(float x, float y, float z) {
        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new PointSimple(x, y, z);
    }

    /** Фабричный метод для создания простых линий, z1 и z2 = 0 */
    public static LineSimple createLineSimple(float x1, float y1, float x2, float y2) {
        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new LineSimple(x1, y1, 0, x2, y2, 0);
    }

    /** Фабричный метод для создания простых линий */
    public static LineSimple createLineSimple(
            float x1, float y1, float z1,
            float x2, float y2, float z2) {

        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new LineSimple(x1, y1, z1, x2, y2, z2);
    }

    /** Фабричный метод для создания разноцветных линий, z1 and z2 = 0 */
    public static LineColor createLineColor(
            float x1, float y1,
            float r1, float g1, float b1,
            float x2, float y2,
            float r2, float g2, float b2) {

        if (!context.getPrograms().containsKey("color"))
            GraphicTricks.createProgramColor();

        return new LineColor(x1, y1, 0, r1, g1, b1, x2, y2, 0, r2, g2, b2);
    }

    /** Фабричный метод для создания разноцветных линий */
    public static LineColor createLineColor(
            float x1, float y1, float z1,
            float r1, float g1, float b1,
            float x2, float y2, float z2,
            float r2, float g2, float b2) {

        if (!context.getPrograms().containsKey("color"))
            GraphicTricks.createProgramColor();

        return new LineColor(x1, y1, z1, r1, g1, b1, x2, y2, z2, r2, g2, b2);
    }

    /** Фабричный метод для создания простых непрерывных линий, для каждой точки 3 координтаы! */
    public static LineContinuousSimple createLineContinuousSimple(float... coord) {
        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new LineContinuousSimple(coord);
    }

    /** Фабричный метод для создания цветных непрерывных линий, для каждой точки 6 координат! */
    public static LineContinuousColor createLineContinuousColor(float... coord) {
        if (!context.getPrograms().containsKey("color"))
            GraphicTricks.createProgramSimple();

        return new LineContinuousColor(coord);
    }

    /** Фабричный метод для создания простых непрерывных зацикленных линий, 3 координаты на точку */
    public static LineContinuousLoopSimple createLineContinuousLoopSimple(float... coords) {
        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new LineContinuousLoopSimple(coords);
    }

    /** Фабричный метод для создания цветных непрерывных зацикленных линий, 6 координат на точку */
    public static LineContinuousLoopColor createLineContinuousLoopColor(float... coords) {
        if (!context.getPrograms().containsKey("color"))
            GraphicTricks.createProgramSimple();

        return new LineContinuousLoopColor(coords);
    }


    /** Фабричный метод для создания простых треугольников, z1, z2 и z3 =0 */
    public static TriangleSimple createTriangleSimple(
            float x1, float y1,
            float x2, float y2,
            float x3, float y3) {

        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new TriangleSimple(x1, y1, 0, x2, y2, 0, x3, y3, 0);
    }

    /** Фабричный метод для создания простых треугольников */
    public static TriangleSimple createTriangleSimple(
            float x1, float y1, float z1,
            float x2, float y2, float z2,
            float x3, float y3, float z3) {

        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new TriangleSimple(x1, y1, z1, x2, y2, z2, x3, y3, z3);
    }

    /** Фабричный метод для создания разноцветных треугольников, z1, z2, z3 = 0 */
    public static TriangleColor createTriangleColor(
            float x1, float y1,
            float r1, float g1, float b1,
            float x2, float y2,
            float r2, float g2, float b2,
            float x3, float y3,
            float r3, float g3, float b3) {

        if (!context.getPrograms().containsKey("color"))
            GraphicTricks.createProgramColor();

        return new TriangleColor(x1, y1, 0, r1, g1, b1, x2, y2, 0, r2, g2, b2, x3, y3, 0, r3, g3, b3);
    }

    /** Фабричный метод для создания разноцветных треугольников */
    public static TriangleColor createTriangleColor(
            float x1, float y1, float z1,
            float r1, float g1, float b1,
            float x2, float y2, float z2,
            float r2, float g2, float b2,
            float x3, float y3, float z3,
            float r3, float g3, float b3) {

        if (!context.getPrograms().containsKey("color"))
            GraphicTricks.createProgramColor();

        return new TriangleColor(x1, y1, z1, r1, g1, b1, x2, y2, z2, r2, g2, b2, x3, y3, z3, r3, g3, b3);
    }

    /** Фабричный метод для создания простых прямоугольников со скруг. угл. */
    public static RoundRectSimple createRoundRectSimple(
            float x_top_left, float y_top_left,
            float x_bottom_right, float y_bottom_right, float round_radius) {

        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new RoundRectSimple(x_top_left, y_top_left,
                x_bottom_right, y_bottom_right, round_radius);
    }


    /** Фабричный метод для создания простых четырехугольников, z1, z2, z3, z4 = 0 */
    public static QuadrangleSimple createQuadrangleSimple(
            float x1, float y1,
            float x2, float y2,
            float x3, float y3,
            float x4, float y4) {

        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new QuadrangleSimple(x1, y1, 0, x2, y2, 0, x3, y3, 0, x4, y4, 0);
    }

    /** Фабричный метод для создания простых четырехугольников */
    public static QuadrangleSimple createQuadrangleSimple(
            float x1, float y1, float z1,
            float x2, float y2, float z2,
            float x3, float y3, float z3,
            float x4, float y4, float z4) {

        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new QuadrangleSimple(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
    }

    /** Фабричный метод для создания четырехугольников, на которые накладываем текстуры */
    public static QuadrangleTexture createQuadrangleTexture(
            float x1, float y1, float z1,
            float x2, float y2, float z2,
            float x3, float y3, float z3,
            float x4, float y4, float z4) {

        if (!context.getPrograms().containsKey("texture"))
            GraphicTricks.createProgramTexture();

        return new QuadrangleTexture(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
    }

    /** Фабричный метод для создания четырехугольников, на которые накладываем текстуры
        c указанием координат текстуры */
    public static QuadrangleTexture createQuadrangleTexture(
            float x1, float y1, float z1, float s1, float t1,
            float x2, float y2, float z2, float s2, float t2,
            float x3, float y3, float z3, float s3, float t3,
            float x4, float y4, float z4, float s4, float t4) {

        if (!context.getPrograms().containsKey("texture"))
            GraphicTricks.createProgramTexture();

        return new QuadrangleTexture(
                x1, y1, z1, s1, t1, x2, y2, z2, s2, t2, x3, y3, z3, s3, t3, x4, y4, z4, s4, t4);
    }

    /** Фабричный метод для создания разноцветных четырехугольников, z1, z2, z3, z4 = 0 */
    public static QuadrangleColor createQuadrangleColor(
            float x1, float y1,
            float r1, float g1, float b1,
            float x2, float y2,
            float r2, float g2, float b2,
            float x3, float y3,
            float r3, float g3, float b3,
            float x4, float y4,
            float r4, float g4, float b4) {

        if (!context.getPrograms().containsKey("color"))
            GraphicTricks.createProgramColor();

        if (!context.getPrograms().containsKey("light"))
            GraphicTricks.createProgramLight();

        return new QuadrangleColor(
                x1, y1, 0, r1, g1, b1,
                x2, y2, 0, r2, g2, b2,
                x3, y3, 0, r3, g3, b3,
                x4, y4, 0, r4, g4, b4);
    }

    /** Фабричный метод для создания разноцветных четырехугольников */
    public static QuadrangleColor createQuadrangleColor(
            float x1, float y1, float z1,
            float r1, float g1, float b1,
            float x2, float y2, float z2,
            float r2, float g2, float b2,
            float x3, float y3, float z3,
            float r3, float g3, float b3,
            float x4, float y4, float z4,
            float r4, float g4, float b4) {

        if (!context.getPrograms().containsKey("color"))
            GraphicTricks.createProgramColor();

        if (!context.getPrograms().containsKey("light"))
            GraphicTricks.createProgramLight();

        return new QuadrangleColor(
                x1, y1, z1, r1, g1, b1,
                x2, y2, z2, r2, g2, b2,
                x3, y3, z3, r3, g3, b3,
                x4, y4, z4, r4, g4, b4);
    }


    /** Фабричный метод для создания простых многоугольников, z центра = 0,
     *  coords - по 3 координаты на точку!
     */
    public static PolygonSimple createPolygonSimple(
            float x_center,
            float y_center,
            float... coords) {

        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new PolygonSimple(x_center, y_center, 0, coords);
    }

    /** Фабричный метод для создания простых многоугольников, z центра произвольная,
     *  coords - по 3 координаты на точку!
     */
    public static PolygonSimple createPolygonSimple(
            float x_center,
            float y_center,
            float z_center,
            float... coords) {

        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new PolygonSimple(x_center, y_center, z_center, coords);
    }

    /** Фабричный метод для создания цветных многоугольников, z_center = 0 */
    public static PolygonColor createPolygonColor(
            float x_center, float y_center,
            float r_center, float g_center, float b_center,
            float... coords) {

        if (!context.getPrograms().containsKey("color"))
            GraphicTricks.createProgramColor();

        return new PolygonColor(x_center, y_center, 0, r_center, g_center, b_center, coords);
    }

    /** Фабричный метод для создания цветных многоугольников, z_center произвольная */
    public static PolygonColor createPolygonColor(
            float x_center, float y_center, float z_center,
            float r_center, float g_center, float b_center,
            float... coords) {

        if (!context.getPrograms().containsKey("color"))
            GraphicTricks.createProgramColor();

        return new PolygonColor(x_center, y_center, z_center, r_center, g_center, b_center, coords);
    }

    /** Фабричный метод для создания сектора окружности с шагом 5 (по умолчанию), z_center = 0 */
    public static CircleSectorSimple createCircleSectorSimple(float x_center, float y_center,
                                                             float radius, float angle,
                                                             Direction direction) {
        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new CircleSectorSimple(x_center,
                y_center, 0, radius, angle, direction);
    }

    /** Фабричный метод для создания окружности с шагом 5 (по умолчанию), z_center = 0 */
    public static CircleEmptySimple createCircleEmptySimple(float x_center, float y_center, float radius) {
        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new CircleEmptySimple(x_center,
                y_center, 0, radius);
    }


    /** Фабричный метод для создания цилиндра */
    public static CylinderSimple createCylinderSimple(float x_start, float y_start, float z_start,
                                                      float r_bottom, float r_top, float h) {
        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new CylinderSimple(x_start,
                y_start, z_start, r_bottom, r_top, h);
    }

    /** Фабричный метод для создания simple-тетраэдра */
    public static TetrahedronSimple createTetrahedronSimple(float top_x, float top_y, float top_z,
                                                        float height, float radius) {
        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new TetrahedronSimple(top_x, top_y, top_z, height, radius);
    }

    /** Фабричный метод для создания color-тетраэдра */
    public static TetrahedronColor createTetrahedronColor
            (float top_x, float top_y, float top_z,
             float top_x_color, float top_y_color, float top_z_color,
             float bottom_x_color, float bottom_y_color, float bottom_z_color,
             float height, float radius) {

        if (!context.getPrograms().containsKey("color"))
            GraphicTricks.createProgramSimple();

        return new TetrahedronColor
                (top_x, top_y, top_z,
                        top_x_color, top_y_color, top_z_color,
                        bottom_x_color, bottom_y_color, bottom_z_color,
                        height, radius);
    }

    /** Фабричный метод для создания point-сферы */
    public static SphereSimple createSphereSimple(float center_x, float center_y, float center_z,
                                                  float radius) {
        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new SphereSimple(center_x, center_y, center_z, radius) {
        };
    }

    /** Фабричный метод для создания color-октаэдра */
    public static OctahedronColor createOctahedronColor
            (float top_x, float top_y, float top_z,
             float top_x_color, float top_y_color, float top_z_color,
             float middle_x_color, float middle_y_color, float middle_z_color,
             float bottom_x_color, float bottom_y_color, float bottom_z_color,
             float height, float radius) {

        if (!context.getPrograms().containsKey("color"))
            GraphicTricks.createProgramSimple();

        return new OctahedronColor
                (top_x, top_y, top_z,
                 top_x_color, top_y_color, top_z_color,
                 middle_x_color, middle_y_color, middle_z_color,
                 bottom_x_color, bottom_y_color, bottom_z_color,
                 height, radius);
    }

    /** Фабричный метод для создания simple-октаэдра */
    public static OctahedronSimple createOctahedronSimple(float top_x, float top_y, float top_z,
                                                          float height, float radius) {
        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new OctahedronSimple(top_x, top_y, top_z, height, radius);
    }

    /** Фабричный метод для создания simple-куба */
    public static CubeSimple createCubeSimple(float left_bottom_x, float left_bottom_y, float left_bottom_z,
                                              float edge) {
        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new CubeSimple(left_bottom_x, left_bottom_y, left_bottom_z, edge);
    }

    /** Фабричный метод для создания простого круга с шагом 5 (по умолчанию), z_center = 0 */
    public static CircleSimple createCircleSimple(float x_center, float y_center, float radius) {
        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new CircleSimple(x_center,
                y_center, 0, radius);
    }

    /** Фабричный метод для создания простого круга с шагом 5 (по умолчанию),
     *  z_center произвольная
     */
    public static CircleSimple createCircleSimple(
            float x_center, float y_center, float z_center, float radius) {

        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new CircleSimple(x_center,
                y_center, z_center, radius);
    }

    /** Фабричный метод для создания цветного круга с шагом 5 (по умолчанию), z_center = 0 */
    public static CircleColor createCircleColor(
            float x_center, float y_center,
            float r_center, float g_center, float b_center,
            float radius,
            float r_edge, float g_edge, float b_edge) {

        if (!context.getPrograms().containsKey("color"))
            GraphicTricks.createProgramSimple();

        return new CircleColor(
                x_center, y_center, 0, r_center, g_center, b_center,
                radius, r_edge, g_edge, b_edge);
    }

    /** Фабричный метод для создания цветного круга с шагом 5 (по умолчанию),
     *  z_center произвольная
     */
    public static CircleColor createCircleColor(
            float x_center, float y_center, float z_center,
            float r_center, float g_center, float b_center,
            float radius,
            float r_edge, float g_edge, float b_edge) {

        if (!context.getPrograms().containsKey("color"))
            GraphicTricks.createProgramSimple();

        return new CircleColor(
                x_center, y_center, z_center, r_center, g_center, b_center,
                radius, r_edge, g_edge, b_edge);
    }

    /** Фабричный метод для создания простого кольца с шагом 5 (по умолчанию),
     *  z_center = 0
     */
    public static RingSimple createRingSimple(
            float x_center, float y_center,
            float r_small, float r_large) {

        if (!context.getPrograms().containsKey("simple"))
            GraphicTricks.createProgramSimple();


        return new RingSimple(x_center, y_center, 0f,
        r_small, r_large);
    }

    /** Фабричный метод для создания цветного кольца с шагом 5 (по умолчанию),
     *  z_center = 0
     */
    public static RingColor createRingColor(
            float x_center, float y_center,
            float r_small, float g_small, float b_small,
            float r_large, float g_large, float b_large,
            float radius_small, float radius_large) {

        if (!context.getPrograms().containsKey("color"))
            GraphicTricks.createProgramSimple();

        return new RingColor(
                x_center, y_center, 0,
                r_small, g_small, b_small,
                r_large, g_large, b_large,
                radius_small, radius_large);
    }


    /**
     * Рисует простую точку
     * @param point - точка, которую нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(PointSimple point) {

        glUseProgram(context.getPrograms().get("simple"));
        bindMatrixForSimpleShader();
        glVertexAttribPointer(context.getAPositionLocationSimple(), 3, GL_FLOAT, false, 0,
                point.getVertexData());
        glEnableVertexAttribArray(context.getAPositionLocationSimple());
        glDrawArrays(GL_POINTS, 0, 1);

        return 1;
    }

    /**
     * Рисует простую линию
     * @param line - линия, которую нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(LineSimple line) {

        glUseProgram(context.getPrograms().get("simple"));
        bindMatrixForSimpleShader();
        glVertexAttribPointer(context.getAPositionLocationSimple(), 3, GL_FLOAT, false, 0,
                line.getVertexData());
        glEnableVertexAttribArray(context.getAPositionLocationSimple());
        glDrawArrays(GL_LINES, 0, 2);

        return 1;
    }

    /**
     * Рисует цветную линию
     * @param line - линия, которую нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(LineColor line) {

        glUseProgram(context.getPrograms().get("color"));
        bindMatrixForColorShader();
        glVertexAttribPointer(context.getAPositionLocationColor(), 3, GL_FLOAT, false, 24,
                line.getVertexData().position(0));
        glEnableVertexAttribArray(context.getAPositionLocationColor());

        glVertexAttribPointer(context.getAColorLocationColor(), 3, GL_FLOAT, false, 24,
                line.getVertexData().position(3));
        glEnableVertexAttribArray(context.getAColorLocationColor());
        glDrawArrays(GL_LINES, 0, 2);

        return 1;
    }

    /**
     * Рисует простую непрерывную линию
     * @param line - линия, которую нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(LineContinuousSimple line) {

        glUseProgram(context.getPrograms().get("simple"));
        bindMatrixForSimpleShader();
        glVertexAttribPointer(context.getAPositionLocationSimple(), 3, GL_FLOAT, false, 0,
                line.getVertexData());
        glEnableVertexAttribArray(context.getAPositionLocationSimple());
        glDrawArrays(GL_LINE_STRIP, 0, line.getNumberOfVertices());

        return 1;
    }

    /**
     * Рисует цветную непрерывную линию
     * @param line - линия, которую нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(LineContinuousColor line) {

        glUseProgram(context.getPrograms().get("color"));
        bindMatrixForColorShader();
        glVertexAttribPointer(context.getAPositionLocationColor(), 3, GL_FLOAT, false, 24,
                line.getVertexData().position(0));
        glEnableVertexAttribArray(context.getAPositionLocationColor());

        glVertexAttribPointer(context.getAColorLocationColor(), 3, GL_FLOAT, false, 24,
                line.getVertexData().position(3));
        glEnableVertexAttribArray(context.getAColorLocationColor());
        glDrawArrays(GL_LINE_STRIP, 0, line.getNumberOfVertices());

        return 1;
    }

    /**
     * Рисует простую непрерывную линию, начало которой соединяется с концом
     * @param line - линия, которую нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(LineContinuousLoopSimple line) {

        glUseProgram(context.getPrograms().get("simple"));
        bindMatrixForSimpleShader();
        glVertexAttribPointer(context.getAPositionLocationSimple(), 3, GL_FLOAT, false, 0,
                line.getVertexData());
        glEnableVertexAttribArray(context.getAPositionLocationSimple());
        glDrawArrays(GL_LINE_LOOP, 0, line.getNumberOfVertices());

        return 1;
    }

    /**
     * Рисует цветную непрерывную линию, начало которой соединяется с концом
     * @param line - линия, которую нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(LineContinuousLoopColor line) {

        glUseProgram(context.getPrograms().get("color"));
        bindMatrixForColorShader();
        glVertexAttribPointer(context.getAPositionLocationColor(), 3, GL_FLOAT, false, 24,
                line.getVertexData().position(0));
        glEnableVertexAttribArray(context.getAPositionLocationColor());

        glVertexAttribPointer(context.getAColorLocationColor(), 3, GL_FLOAT, false, 24,
                line.getVertexData().position(3));
        glEnableVertexAttribArray(context.getAColorLocationColor());
        glDrawArrays(GL_LINE_LOOP, 0, line.getNumberOfVertices());

        return 1;
    }


    /**
     * Рисует простой треугольник
     * @param triangle - треугольник, который нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(TriangleSimple triangle) {

        glUseProgram(context.getPrograms().get("simple"));
        bindMatrixForSimpleShader();
        glVertexAttribPointer(context.getAPositionLocationSimple(), 3, GL_FLOAT, false, 0,
                triangle.getVertexData());
        glEnableVertexAttribArray(context.getAPositionLocationSimple());
        glDrawArrays(GL_TRIANGLES, 0, 3);

        return 1;
    }

    /**
     * Рисует цветной треугольник
     * @param triangle - треугольник, который нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(TriangleColor triangle) {

        glUseProgram(context.getPrograms().get("color"));

        bindMatrixForColorShader();
        glVertexAttribPointer(context.getAPositionLocationColor(), 3, GL_FLOAT, false, 24,
                triangle.getVertexData().position(0));
        glEnableVertexAttribArray(context.getAPositionLocationColor());

        glVertexAttribPointer(context.getAColorLocationColor(), 3, GL_FLOAT, false, 24,
                triangle.getVertexData().position(3));
        glEnableVertexAttribArray(context.getAColorLocationColor());
        glDrawArrays(GL_TRIANGLES, 0, 3);

        return 1;
    }

    /**
     * Рисует простой четырехугольник, состоящий из двух треугольников
     * @param quadrangle - четырехугольник, который нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(QuadrangleSimple quadrangle) {

        glUseProgram(context.getPrograms().get("simple"));
        bindMatrixForSimpleShader();
        glVertexAttribPointer(context.getAPositionLocationSimple(), 3, GL_FLOAT, false, 0,
                quadrangle.getVertexData());
        glEnableVertexAttribArray(context.getAPositionLocationSimple());
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

        return 1;
    }

    /**
     * Рисует простой прямоугольник со скругленными углами
     * @param rect - прямоугольник, который нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(RoundRectSimple rect) {

        glUseProgram(context.getPrograms().get("simple"));
        bindMatrixForSimpleShader();
        glVertexAttribPointer(context.getAPositionLocationSimple(), 2, GL_FLOAT, false, 0,
                rect.getVertexData().position(0));
        glEnableVertexAttribArray(context.getAPositionLocationSimple());

        glDrawArrays(GL_TRIANGLE_STRIP, 0, 8);
        glDrawArrays(GL_TRIANGLE_STRIP, 8, 4);
        glDrawArrays(GL_TRIANGLE_STRIP, 12, 4);

        glDrawArrays(GL_TRIANGLE_FAN,   16, 11);
        glDrawArrays(GL_TRIANGLE_FAN,   27, 11);
        glDrawArrays(GL_TRIANGLE_FAN,   38, 11);
        glDrawArrays(GL_TRIANGLE_FAN,   49, 11);

        return 1;
    }

    /**
     * Рисует четырехугольник, на который накладывается текстура
     * @param quadrangle - четырехугольник, который нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(QuadrangleTexture quadrangle, int textureObject) {

        glUseProgram(context.getPrograms().get("texture"));
        bindMatrixForTextureShader();

        glVertexAttribPointer(context.getAPositionLocationTexture(), 3, GL_FLOAT, false, 20,
                quadrangle.getVertexData().position(0));
        glEnableVertexAttribArray(context.getAPositionLocationTexture());

        glVertexAttribPointer(context.getATextureLocation(), 2, GL_FLOAT, false, 20,
                quadrangle.getVertexData().position(3));
        glEnableVertexAttribArray(context.getATextureLocation());

        // Делаем активным 0-й юнит
        glActiveTexture(GL_TEXTURE0);
        // Передаем объект текстуры в таргет
        glBindTexture(GL_TEXTURE_2D, textureObject);

        // юнит текстуры
        glUniform1i(context.getUTextureUnitLocation(), 0);

        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

        return 1;
    }

    /**
     * Рисует цветной четырехугольник
     * @param quadrangle - треугольник, который нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(QuadrangleColor quadrangle) {

        glUseProgram(context.getPrograms().get("color"));
        bindMatrixForColorShader();

        glVertexAttribPointer(context.getAPositionLocationColor(), 3, GL_FLOAT, false, 24,
                quadrangle.getVertexData().position(0));
        glEnableVertexAttribArray(context.getAPositionLocationColor());

        glVertexAttribPointer(context.getAColorLocationColor(), 3, GL_FLOAT, false, 24,
                quadrangle.getVertexData().position(3));
        glEnableVertexAttribArray(context.getAColorLocationColor());

        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
        return 1;
    }

    public static float[] getMLightPosInEyeSpace() {
        return context.getMLightPosInEyeSpace();
    }

    /**
     * Рисует цветной четырехугольник, можно добавить источник света
     * @param quadrangle - треугольник, который нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(QuadrangleColor quadrangle, Lighted l) {

        if (l == Lighted.TRUE) {
            glUseProgram(context.getPrograms().get("light"));
            GraphicTricks.on();

            glVertexAttribPointer(context.getAPositionLocationLight(), 3, GL_FLOAT, false, 24,
                    quadrangle.getVertexData().position(0));

            glEnableVertexAttribArray(context.getAPositionLocationLight());

            glVertexAttribPointer(context.getAColorLocationLight(), 3, GL_FLOAT, false, 24,
                    quadrangle.getVertexData().position(3));

            glEnableVertexAttribArray(context.getAColorLocationLight());

            glVertexAttribPointer(context.getANormalLocationLight(), 3, GL_FLOAT, false, 0,
                    quadrangle.getNormalData().position(0));

            glEnableVertexAttribArray(context.getANormalLocationLight());

            Matrix.multiplyMM(context.getMatrix(), 0, context.getViewMatrix(), 0, context.getModelMatrix(), 0);
            glUniformMatrix4fv(context.getUMVMatrixLocationLight(), 1, false, context.getMatrix(), 0);
            Matrix.multiplyMM(context.getMatrix(), 0, context.getProjectionMatrix(), 0, context.getMatrix(), 0);
            glUniformMatrix4fv(context.getUMatrixLocationLight(), 1, false, context.getMatrix(), 0);
            glUniform3f(context.getALightLocationLight(),
                    context.getMLightPosInEyeSpace()[0],
                    context.getMLightPosInEyeSpace()[1],
                    context.getMLightPosInEyeSpace()[2]);

            glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

        }
        else {
            GraphicTricks.draw(quadrangle);
        }

        return 1;
    }

    /**
     * Рисует простую окружность
     * @param circle - окружность, которую нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(CircleEmptySimple circle) {

        glUseProgram(context.getPrograms().get("simple"));
        bindMatrixForSimpleShader();
        glVertexAttribPointer(context.getAPositionLocationSimple(), 3, GL_FLOAT, false, 0,
                circle.getVertexData());
        glEnableVertexAttribArray(context.getAPositionLocationSimple());

        glDrawArrays(GL_LINE_LOOP, 1, 74);

        return 1;
    }

    /**
     * Рисует сектор круга
     * @param circle_s - сектор круга, который нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(CircleSectorSimple circle_s) {

        glUseProgram(context.getPrograms().get("simple"));
        bindMatrixForSimpleShader();
        glVertexAttribPointer(context.getAPositionLocationSimple(), 3, GL_FLOAT, false, 0,
                circle_s.getVertexData());
        glEnableVertexAttribArray(context.getAPositionLocationSimple());
        glDrawArrays(GL_TRIANGLE_FAN, 0, circle_s.getNumberVertices());

        return 1;
    }

    /**
     * Рисует простой круг
     * @param circle - круг, который нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(CircleSimple circle) {

        glUseProgram(context.getPrograms().get("simple"));
        bindMatrixForSimpleShader();
        glVertexAttribPointer(context.getAPositionLocationSimple(), 3, GL_FLOAT, false, 0,
                circle.getVertexData());
        glEnableVertexAttribArray(context.getAPositionLocationSimple());
        glDrawArrays(GL_TRIANGLE_FAN, 0, 74);

        return 1;
    }


    public static int draw(CylinderSimple cylinderSimple) {

        glUseProgram(context.getPrograms().get("simple"));
        bindMatrixForSimpleShader();
        glVertexAttribPointer(context.getAPositionLocationSimple(), 3, GL_FLOAT, false, 0,
                cylinderSimple.getVertexData().position(0));
        glEnableVertexAttribArray(context.getAPositionLocationSimple());
        //glDrawArrays(GL_TRIANGLE_FAN, 0, 74);
        //glDrawArrays(GL_TRIANGLE_FAN, 74, 74);
        glDrawArrays(GL_TRIANGLE_STRIP, 148, 145);

        return 1;
    }

    public static int draw(TetrahedronSimple tetrahedronSimple) {

        glUseProgram(context.getPrograms().get("simple"));
        bindMatrixForSimpleShader();
        glVertexAttribPointer(context.getAPositionLocationSimple(), 3, GL_FLOAT, false, 0,
                tetrahedronSimple.getVertexData().position(0));
        glEnableVertexAttribArray(context.getAPositionLocationSimple());

        glDrawArrays(GL_TRIANGLE_FAN, 0, 5);

        return 1;
    }

    public static int draw(SphereSimple sphereSimple) {

        glUseProgram(context.getPrograms().get("simple"));
        bindMatrixForSimpleShader();
        glVertexAttribPointer(context.getAPositionLocationSimple(), 3, GL_FLOAT, false, 0,
                sphereSimple.getVertexData().position(0));
        glEnableVertexAttribArray(context.getAPositionLocationSimple());

        glDrawArrays(GL_POINTS, 0, sphereSimple.getAmountOfPoints());

        return 1;
    }

    public static int draw(OctahedronSimple octahedronSimple) {

        glUseProgram(context.getPrograms().get("simple"));
        bindMatrixForSimpleShader();
        glVertexAttribPointer(context.getAPositionLocationSimple(), 3, GL_FLOAT, false, 0,
                octahedronSimple.getVertexData().position(0));
        glEnableVertexAttribArray(context.getAPositionLocationSimple());

        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
        glDrawArrays(GL_TRIANGLE_FAN, 6, 6);

        return 1;
    }

    public static int draw(CubeSimple cubeSimpleSimple) {

        glUseProgram(context.getPrograms().get("simple"));
        bindMatrixForSimpleShader();
        glVertexAttribPointer(context.getAPositionLocationSimple(), 3, GL_FLOAT, false, 0,
                cubeSimpleSimple.getVertexData().position(0));
        glEnableVertexAttribArray(context.getAPositionLocationSimple());

        glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_BYTE, cubeSimpleSimple.getIndexBuffer());

        return 1;
    }

    public static int draw(OctahedronColor octahedronColor) {

        glUseProgram(context.getPrograms().get("color"));
        bindMatrixForColorShader();
        glVertexAttribPointer(context.getAPositionLocationColor(), 3, GL_FLOAT, false, 0,
                octahedronColor.getVertexData().position(0));
        glEnableVertexAttribArray(context.getAPositionLocationColor());

        glVertexAttribPointer(context.getAColorLocationColor(), 3, GL_FLOAT, false, 0,
                octahedronColor.getVertexColorBuffer().position(0));
        glEnableVertexAttribArray(context.getAColorLocationColor());

        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
        glDrawArrays(GL_TRIANGLE_FAN, 6, 6);

        return 1;
    }

    public static int draw(TetrahedronColor tetrahedronColor) {

        glUseProgram(context.getPrograms().get("color"));
        bindMatrixForColorShader();
        glVertexAttribPointer(context.getAPositionLocationColor(), 3, GL_FLOAT, false, 0,
                tetrahedronColor.getVertexData().position(0));
        glEnableVertexAttribArray(context.getAPositionLocationColor());

        glVertexAttribPointer(context.getAColorLocationColor(), 3, GL_FLOAT, false, 0,
                tetrahedronColor.getVertexColorBuffer().position(0));
        glEnableVertexAttribArray(context.getAColorLocationColor());
        glDrawArrays(GL_TRIANGLE_FAN, 0, 5);
        glDrawArrays(GL_TRIANGLES, 1, 3);

        return 1;
    }

    /**
     * Рисует цветной круг
     * @param circle - круг, который нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(CircleColor circle) {

        glUseProgram(context.getPrograms().get("color"));
        bindMatrixForColorShader();
        glVertexAttribPointer(context.getAPositionLocationColor(), 3, GL_FLOAT, false, 0,
                circle.getVertexData().position(0));
        glEnableVertexAttribArray(context.getAPositionLocationColor());

        glVertexAttribPointer(context.getAColorLocationColor(), 3, GL_FLOAT, false, 0,
                circle.getVertexColorBuffer().position(0));
        glEnableVertexAttribArray(context.getAColorLocationColor());
        glDrawArrays(GL_TRIANGLE_FAN, 0, 74);

        return 1;
    }

    /**
     * Рисует простое кольцо
     * @param ring - кольцо, которое нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(RingSimple ring) {

        glUseProgram(context.getPrograms().get("simple"));
        bindMatrixForSimpleShader();
        glVertexAttribPointer(context.getAPositionLocationSimple(), 3, GL_FLOAT, false, 0,
                ring.getVertexData());
        glEnableVertexAttribArray(context.getAPositionLocationSimple());
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 74);

        return 1;
    }

    /**
     * Рисует цветное кольцо
     * @param ring - кольцо, которое нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(RingColor ring) {

        glUseProgram(context.getPrograms().get("color"));
        bindMatrixForColorShader();
        glVertexAttribPointer(context.getAPositionLocationColor(), 3, GL_FLOAT, false, 0,
                ring.getVertexData().position(0));
        glEnableVertexAttribArray(context.getAPositionLocationColor());

        glVertexAttribPointer(context.getAColorLocationColor(), 3, GL_FLOAT, false, 0,
                ring.getVertexColorBuffer().position(0));
        glEnableVertexAttribArray(context.getAColorLocationColor());
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 74);

        return 1;
    }

    /**
     * Рисует простой полигон
     * @param polygon - многоугольник, который нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(PolygonSimple polygon) {

        glUseProgram(context.getPrograms().get("simple"));
        bindMatrixForSimpleShader();
        glVertexAttribPointer(context.getAPositionLocationSimple(), 3, GL_FLOAT, false, 0,
                polygon.getVertexData());
        glEnableVertexAttribArray(context.getAPositionLocationSimple());
        glDrawArrays(GL_TRIANGLE_FAN, 0, polygon.getNumberOfVertices());

        return 1;
    }

    /**
     * Рисует цветной полигон
     * @param polygon - полигон, который нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(PolygonColor polygon) {

        glUseProgram(context.getPrograms().get("color"));
        bindMatrixForColorShader();
        glVertexAttribPointer(context.getAPositionLocationColor(), 3, GL_FLOAT, false, 24,
                polygon.getVertexData().position(0));
        glEnableVertexAttribArray(context.getAPositionLocationColor());

        glVertexAttribPointer(context.getAColorLocationColor(), 3, GL_FLOAT, false, 24,
                polygon.getVertexData().position(3));
        glEnableVertexAttribArray(context.getAColorLocationColor());
        glDrawArrays(GL_TRIANGLE_FAN, 0, polygon.getNumberOfVertices());

        return 1;
    }


    /**
     * Задаёт контекст для доступа к ресурсам
     * @param context - контекст, содержащий ресурсы
     */
    public static void setContext(Context context) {
        GraphicTricks.context.setContext(context);
    }

    /**
     * Изменяет размер окна
     * @param width - ширина окна
     * @param height - высота окна
     */
    public static void setViewPort(int x_left, int y_left, int width, int height) {
        glViewport(x_left, y_left, width, height);
    }

    /**
     * Изменяет цвет очистки экрана
     * @param red   - компонента красного цвета
     * @param green - компонента зеленого цвета
     * @param blue  - компонента синего цвета
     * @param alpha - альфа канал
     */
    public static void changeClearColor(float red, float green, float blue, float alpha) {
        context.setClearRed(red);
        context.setClearGreen(green);
        context.setClearBlue(blue);
        context.setClearAlpha(alpha);
    }

    /** Очищает экран используемым цветом */
    public static void clearScreen() {

        glClearColor(
                context.getClearRed(), context.getClearGreen(),
                context.getClearBlue(), context.getClearAlpha()
        );

        glClear(GL_COLOR_BUFFER_BIT);
    }

    /** Очищает экран указанным цветом
     *  Используется в начале метода onDrawFrame()
     */
    public static void clearScreen(float red, float green, float blue, float alpha) {
        glClearColor(red, green, blue, alpha);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    /** Изменяет цвет кисти */
    public static void changeBrushColor(float red, float green, float blue, float alpha) {
        glUniform4f(context.getUColorLocationSimple(), red, green, blue, alpha);
    }

    public static void changeBrushColor(Color color) {
        glUniform4f(context.getUColorLocationSimple(),
                color.getR(), color.getG(), color.getB(), color.getA());
    }

    /** Инициализирует frustum матрицу
     *  Данная матрица создает перспективу, может быть использована для работы с 3D
     *  @param width - ширина экрана для сохранения пропорций
     *  @param height - высота экрана для сохранения пропорций
     */
    public static void initializeFrustumMatrix(int width, int height) {
        // Отношение большей стороны телефона к меньшей
        float ratio = 1.0f;
        // Координаты сторон near границы
        // Ширина и высота near границы равна 2
        float left = -1.0f;
        float right = 1.0f;
        float bottom = -1.0f;
        float top = 1.0f;
        // Расстояние от камеры до ближайшей границы
        float near = 1f;
        // Расстояние от камеры до дальнейшей границы
        float far = 10.0f;

        if (width > height) {
            ratio = (float) width / height;
            left *= ratio;
            right *= ratio;
        } else {
            ratio = (float) height / width;
            bottom *= ratio;
            top *= ratio;
        }

        Matrix.frustumM(context.getProjectionMatrix(), 0, left, right, bottom, top, near, far);
    }

    /** Инициализирует ortho матрицу
     *  Данная матрица не создает перспективы, может быть использована для работы с 2D
     *  @param width - ширина экрана для сохранения пропорций
     *  @param height - высота экрана для сохранения пропорций
     */
    public static void initializeOrthoMatrix(int width, int height) {
        // Отношение большей стороны телефона к меньшей
        float ratio = 1.0f;
        // Координаты сторон near границы
        // Ширина и высота near границы равна 2
        float left = -1.0f;
        float right = 1.0f;
        float bottom = -1.0f;
        float top = 1.0f;
        // Расстояние от камеры до ближайшей границы
        float near = 1.0f;
        // Расстояние от камеры до дальнейшей границы
        float far = 5.0f;

        if (width > height) {
            ratio = (float) width / height;
            left *= ratio;
            right *= ratio;
        } else {
            ratio = (float) height / width;
            bottom *= ratio;
            top *= ratio;
        }

        // Передаем в матрицу проекции все данные
        // Второй параметр - с какого элемента матрицы записывать в нее данные
        Matrix.orthoM(context.getProjectionMatrix(), 0, left, right, bottom, top, near, far);
    }

    /**
     * Инициализирует матрицу вида начальными значениями
     */
    public static void initializeViewMatrix() {
        // Положение камеры
        float eyeX = 0;
        float eyeY = 0;
        float eyeZ = 1;

        // Куда смотрим
        float centerX = 0;
        float centerY = 0;
        float centerZ = -10;

        // up-вектор
        float upX = 0;
        float upY = 1;
        float upZ = 0;

        Matrix.setLookAtM(context.getViewMatrix(), 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    public static void setViewMatrix(
            float eyeX, float eyeY, float eyeZ,
            float centerX, float centerY, float centerZ,
            float upX, float upY, float upZ) {

        Matrix.setLookAtM(context.getViewMatrix(), 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    /**
     * Приводит матрицу модели к единичному виду
     */
    public static void dropModelMatrix() {
        Matrix.setIdentityM(context.getModelMatrix(), 0);
    }

    /**
     * Приводит матрицу модели света к единичному виду
     */
    public static void dropLightMatrix() {
        Matrix.setIdentityM(context.getMLightModelMatrix(), 0);
    }

    public static void init() {
        glEnable(GL_DEPTH_TEST);
        updatePrograms();
    }

    public static void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    /**
     *  Связывает матрицы вида, модели и проекции в одной матрице, которую передаем в шейдер
     */
    public static void bindMatrixForColorShader() {
        Matrix.multiplyMM(
                context.getMatrix(), 0,
                context.getViewMatrix(), 0,
                context.getModelMatrix(), 0
        );

        Matrix.multiplyMM(
                context.getMatrix(), 0,
                context.getProjectionMatrix(), 0,
                context.getMatrix(), 0
        );

        glUniformMatrix4fv(context.getUMatrixLocationColor(), 1, false, context.getMatrix(), 0);
    }

    /**
     *  Связывает матрицы вида, модели и проекции в одной матрице, которую передаем в шейдер
     */
    public static void bindMatrixForSimpleShader() {
        Matrix.multiplyMM(
                context.getMatrix(), 0,
                context.getViewMatrix(), 0,
                context.getModelMatrix(), 0
        );

        Matrix.multiplyMM(
                context.getMatrix(), 0,
                context.getProjectionMatrix(), 0,
                context.getMatrix(), 0
        );

        glUniformMatrix4fv(context.getUMatrixLocationSimple(), 1, false, context.getMatrix(), 0);
    }

    /**
     *  Связывает матрицы вида, модели и проекции в одной матрице, которую передаем в шейдер
     */
    public static void bindMatrixForTextureShader() {
        Matrix.multiplyMM(
                context.getMatrix(), 0,
                context.getViewMatrix(), 0,
                context.getModelMatrix(), 0
        );

        Matrix.multiplyMM(
                context.getMatrix(), 0,
                context.getProjectionMatrix(), 0,
                context.getMatrix(), 0
        );

        glUniformMatrix4fv(context.getUMatrixLocationTexture(), 1, false, context.getMatrix(), 0);
    }

    /**
     * Реализует смещение модели за счет вызова метода translateM()
     * @param dx - смещение по X
     * @param dy - смещение по Y
     * @param dz - смещение по Z
     */
    public static void translateModelMatrix(float dx, float dy, float dz) {
        Matrix.translateM(context.getModelMatrix(), 0, dx, dy, dz);
    }

    /**
     * Реализует смещение источника света за счет вызова метода translateM()
     * @param dx - смещение по X
     * @param dy - смещение по Y
     * @param dz - смещение по Z
     */
    public static void translateLightMatrix(float dx, float dy, float dz) {
        Matrix.translateM(context.getMLightModelMatrix(), 0, dx, dy, dz);
    }

    /**
     * Реализует масштабирование модели по x, y и z за счет вызова scaleM()
     * @param kx - коэффициент масштабирования по X
     * @param ky - коэффициент масштабирования по Y
     * @param kz - коэффициент масштабирования по Z
     */
    public static void scaleModelMatrix(float kx, float ky, float kz) {
        Matrix.scaleM(context.getModelMatrix(), 0, kx, ky, kz);
    }

    /**
     * Реализует вращение модели на угол вокруг оси, выходящей из (0,0,0) в (x,y,z)
     * @param angle - угол поворота в градусах
     * @param x - X второй точки оси вращения
     * @param y - Y второй точки оси вращения
     * @param z - Z второй точки оси вращения
     */
    public static void rotateModelMatrix(float angle, float x, float y, float z) {
        Matrix.rotateM(context.getModelMatrix(), 0, angle, x, y, z);
    }

    /**
     * Реализует вращение источника света на угол вокруг оси, выходящей из (0,0,0) в (x,y,z)
     * @param angle - угол поворота в градусах
     * @param x - X второй точки оси вращения
     * @param y - Y второй точки оси вращения
     * @param z - Z второй точки оси вращения
     */
    public static void rotateLightMatrix(float angle, float x, float y, float z) {
        Matrix.rotateM(context.getMLightModelMatrix(), 0, angle, x, y, z);
    }

    public static void on() {
        Matrix.multiplyMV(
                context.getMLightPosInWorldSpace(), 0,
                context.getMLightModelMatrix(), 0,
                context.getMLightPosInModelSpace(), 0
        );

        Matrix.multiplyMV(
                context.getMLightPosInEyeSpace(), 0,
                context.getViewMatrix(), 0,
                context.getMLightPosInWorldSpace(), 0
        );
    }

    /**
     * Метод, который служит для создания программы, связывающей два простейших шейдера,
     * позволяющих рисовать Simple примитивы
     */
    private static void createProgramSimple() {

        int vertexShaderId = ShaderUtils.createShader(context.getContext(), GL_VERTEX_SHADER,
                R.raw.vertex_shader_matrix_simple);
        int fragmentShaderId = ShaderUtils.createShader(context.getContext(), GL_FRAGMENT_SHADER,
                R.raw.fragment_shader_matrix_simple);
        int programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId);
        context.getPrograms().put("simple", programId);

        glUseProgram(programId);

        context.setUColorLocationSimple(glGetUniformLocation(programId, "u_Color"));
        context.setAPositionLocationSimple(glGetAttribLocation(programId, "a_Position"));
        context.setUMatrixLocationSimple(glGetUniformLocation(programId, "u_Matrix"));
    }

    private static void createProgramLight() {

        int vertexShaderId = ShaderUtils.createShader(context.getContext(), GL_VERTEX_SHADER,
                R.raw.vertex_shader_llighting);
        int fragmentShaderId = ShaderUtils.createShader(context.getContext(), GL_FRAGMENT_SHADER,
                R.raw.fragment_shader_lighting);
        int programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId);
        context.getPrograms().put("light", programId);

        glUseProgram(programId);

        context.setAPositionLocationLight(glGetAttribLocation(programId,  "a_Position"));
        context.setAColorLocationLight(glGetAttribLocation(programId,     "a_Color"));
        context.setANormalLocationLight(glGetAttribLocation(programId,    "a_Normal"));

        context.setULightLocationLight(glGetUniformLocation(programId,    "u_LightPos"));
        context.setUMatrixLocationLight(glGetUniformLocation(programId,   "u_MVPMatrix"));
        context.setUMVMatrixLocationLight(glGetUniformLocation(programId, "u_MVMatrix"));
    }

    private static void createProgramColor() {

        int vertexShaderId = ShaderUtils.createShader(context.getContext(), GL_VERTEX_SHADER,
                R.raw.vertex_shader_matrix_color);
        int fragmentShaderId = ShaderUtils.createShader(context.getContext(), GL_FRAGMENT_SHADER,
                R.raw.fragment_shader_matrix_color);
        int programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId);
        context.getPrograms().put("color", programId);

        glUseProgram(programId);
        context.setAColorLocationColor(glGetAttribLocation(programId, "a_Color"));
        context.setAPositionLocationColor(glGetAttribLocation(programId, "a_Position"));
        context.setUMatrixLocationColor(glGetUniformLocation(programId, "u_Matrix"));
    }

    /**
     * Метод, который служит для создания программы, связывающей два шейдера,
     * позволяющих рисовать текстуры
     */
    private static void createProgramTexture() {

        int vertexShaderId = ShaderUtils.createShader(context.getContext(), GL_VERTEX_SHADER,
                R.raw.vertex_shader_texture);
        int fragmentShaderId = ShaderUtils.createShader(context.getContext(), GL_FRAGMENT_SHADER,
                R.raw.fragment_shader_texture);
        int programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId);
        context.getPrograms().put("texture", programId);

        glUseProgram(programId);
        // Позиция координат текстуры
        context.setATextureLocation(glGetAttribLocation(programId, "a_Texture"));
        // Позиция кооринат примитива
        context.setAPositionLocationTexture(glGetAttribLocation(programId, "a_Position"));
        // Позиция комбинированной матрицы
        context.setUMatrixLocationTexture(glGetUniformLocation(programId, "u_Matrix"));
        // Позиция unit-a
        context.setUTextureUnitLocation(glGetUniformLocation(programId, "u_TextureUnit"));
    }

    /**
     * Метод, обновляющий программы при смене ориантации экрана, либо при восстановлении
     * приложения.
     */
    private static void updatePrograms() {
        if (context.getPrograms().containsKey("color"))
            createProgramColor();
        if (context.getPrograms().containsKey("simple")) {
            createProgramSimple();
        }
        if (context.getPrograms().containsKey("texture")) {
            createProgramTexture();
        }
        if (context.getPrograms().containsKey("light")) {
            createProgramLight();
        }

    }
}
