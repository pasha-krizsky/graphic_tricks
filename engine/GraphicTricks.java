package com.gamedev.dreamteam.graphicTricks.engine;

import android.content.Context;
import android.opengl.Matrix;

// Импорт примитивов
import com.gamedev.dreamteam.graphicTricks.R;
import com.gamedev.dreamteam.graphicTricks.primitives.*;

// Импорт утилиты для чтения шейдеров
import com.gamedev.dreamteam.graphicTricks.utils.ShaderUtils;

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
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
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
     * Рисует цветной четырехугольник
     * @param quadrangle - треугольник, который нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(QuadrangleColor quadrangle) {

        glUseProgram(context.getPrograms().get("color"));

        glVertexAttribPointer(context.getAPositionLocationColor(), 3, GL_FLOAT, false, 24,
                quadrangle.getVertexData().position(0));
        glEnableVertexAttribArray(context.getAPositionLocationColor());

        glVertexAttribPointer(context.getAColorLocationColor(), 3, GL_FLOAT, false, 24,
                quadrangle.getVertexData().position(3));
        glEnableVertexAttribArray(context.getAColorLocationColor());

        bindMatrixForColorShader();
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

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
     * Приводит матрицу модели к единичному виду, что никак не влияет на положение модели
     */
    public static void dropModelMatrix() {
        Matrix.setIdentityM(context.getModelMatrix(), 0);
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
     * Реализует смещение модели за счет вызова метода translateM()
     * @param dx - смещение по X
     * @param dy - смещение по Y
     * @param dz - смещение по Z
     */
    public static void translateModelMatrix(float dx, float dy, float dz) {
        Matrix.translateM(context.getModelMatrix(), 0, dx, dy, dz);
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

    /**
     * Метод, который служит для создания программы, связывающей два шейдера,
     * позволяющих рисовать Color-примитивы
     */
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
     * Метод, обновляющий программы при смене ориантации экрана, либо при восстановлении
     * приложения.
     */
    private static void updatePrograms() {
        if (context.getPrograms().containsKey("color"))
            createProgramColor();
        if (context.getPrograms().containsKey("simple")) {
            createProgramSimple();
        }

    }
}
