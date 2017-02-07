package com.gamedev.dreamteam.graphicTricks.engine;

import android.content.Context;

// Импорт примитивов
import com.gamedev.dreamteam.graphicTricks.R;
import com.gamedev.dreamteam.graphicTricks.primitives.*;

// Импорт утилиты для чтения шейдеров
import com.gamedev.dreamteam.graphicTricks.utils.ShaderUtils;

// Импорт используемых коллекций
import java.util.HashMap;
import java.util.Map;

// Импорт методов OpenGL
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
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
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;



/**
 * Класс, предоставляющий удобный интерфейс для создания объектов и их отрисовки.
 */
public class GraphicTricks {

    /** Контекст, передаваемый из Android */
    private static Context context;
    /** Положение в шейдере uniform переменной u_Color */
    private static int uColorLocationSimple;
    /** Положение в шейдере attribute переменной a_Color */
    private static int aColorLocationColor;
    /** Положение в шейдере attribute переменной a_Position */
    private static int aPositionLocationSimple;
    /** Положение в шейдере attribute переменной a_Position */
    private static int aPositionLocationColor;
    /** Компонента цвета очистки RED */
    private static float clearRed = 0f;
    /** Компонента цвета очистки GREEN */
    private static float clearGreen = 0f;
    /** Компонента цвета очистки BLUE */
    private static float clearBlue = 0f;
    /** Компонента цвета очистки ALPHA */
    private static float clearAlpha = 1f;
    /** Все используемые программы */
    private static Map<String, Integer> programs = new HashMap<>();
    /** Ширина экрана */
    private static int width;
    /** Высота экрана */
    private static int height;


    /** Фабричный метод для создания простых точек */
    public static PointSimple createPointSimple(float x, float y) {
        if (!programs.containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new PointSimple(x, y);
    }


    /** Фабричный метод для создания простых линий */
    public static LineSimple createLineSimple(float x1, float y1, float x2, float y2) {
        if (!programs.containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new LineSimple(x1, y1, x2, y2);
    }

    /** Фабричный метод для создания разноцветных линий */
    public static LineColor createLineColor(
            float x1, float y1,
            float r1, float g1, float b1,
            float x2, float y2,
            float r2, float g2, float b2) {

        if (!programs.containsKey("color"))
            GraphicTricks.createProgramColor();

        return new LineColor(x1, y1, r1, g1, b1, x2, y2, r2, g2, b2);
    }

    /** Фабричный метод для создания простых непрерывных линий */
    public static LineContinuousSimple createLineContinuousSimple(float... coord) {
        if (!programs.containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new LineContinuousSimple(coord);
    }

    /** Фабричный метод для создания цветных непрерывных линий */
    public static LineContinuousColor createLineContinuousColor(float... coord) {
        if (!programs.containsKey("color"))
            GraphicTricks.createProgramSimple();

        return new LineContinuousColor(coord);
    }

    /** Фабричный метод для создания простых непрерывных зацикленных линий */
    public static LineContinuousLoopSimple createLineContinuousLoopSimple(float... coord) {
        if (!programs.containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new LineContinuousLoopSimple(coord);
    }

    /** Фабричный метод для создания цветных непрерывных зацикленных линий */
    public static LineContinuousLoopColor createLineContinuousLoopColor(float... coord) {
        if (!programs.containsKey("color"))
            GraphicTricks.createProgramSimple();

        return new LineContinuousLoopColor(coord);
    }


    /** Фабричный метод для создания простых треугольников */
    public static TriangleSimple createTriangleSimple(
            float x1, float y1,
            float x2, float y2,
            float x3, float y3) {

        if (!programs.containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new TriangleSimple(x1, y1, x2, y2, x3, y3);
    }

    /** Фабричный метод для создания разноцветных треугольников */
    public static TriangleColor createTriangleColor(
            float x1, float y1,
            float r1, float g1, float b1,
            float x2, float y2,
            float r2, float g2, float b2,
            float x3, float y3,
            float r3, float g3, float b3) {

        if (!programs.containsKey("color"))
            GraphicTricks.createProgramColor();

        return new TriangleColor(x1, y1, r1, g1, b1, x2, y2, r2, g2, b2, x3, y3, r3, g3, b3);
    }


    /** Фабричный метод для создания простых четырехугольников */
    public static QuadrangleSimple createQuadrangleSimple(
            float x1, float y1,
            float x2, float y2,
            float x3, float y3,
            float x4, float y4) {

        if (!programs.containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new QuadrangleSimple(x1, y1, x2, y2, x3, y3, x4, y4);
    }

    /** Фабричный метод для создания разноцветных четырехугольников */
    public static QuadrangleColor createQuadrangleColor(
            float x1, float y1,
            float r1, float g1, float b1,
            float x2, float y2,
            float r2, float g2, float b2,
            float x3, float y3,
            float r3, float g3, float b3,
            float x4, float y4,
            float r4, float g4, float b4) {

        if (!programs.containsKey("color"))
            GraphicTricks.createProgramColor();

        return new QuadrangleColor(
                x1, y1, r1, g1, b1,
                x2, y2, r2, g2, b2,
                x3, y3, r3, g3, b3,
                x4, y4, r4, g4, b4);
    }


    /** Фабричный метод для создания простых многоугольников */
    public static PolygonSimple createPolygonSimple(
            float x_center,
            float y_center,
            float... coords) {

        if (!programs.containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new PolygonSimple(x_center, y_center, coords);
    }

    /** Фабричный метод для создания цветных многоугольников */
    public static PolygonColor createPolygonColor(
            float x_center, float y_center,
            float r_center, float g_center, float b_center,
            float... coords) {

        if (!programs.containsKey("color"))
            GraphicTricks.createProgramColor();

        return new PolygonColor(x_center, y_center, r_center, g_center, b_center, coords);
    }

    /** Фабричный метод для создания простого круга с шагом 5 (по умолчанию) */
    public static CircleSimple createCircleSimple(float x_center, float y_center, float radius) {
        if (!programs.containsKey("simple"))
            GraphicTricks.createProgramSimple();

        return new CircleSimple(x_center,
                y_center, radius, GraphicTricks.width, GraphicTricks.height);
    }

    /** Фабричный метод для создания цветного круга с шагом 5 (по умолчанию) */
    public static CircleColor createCircleColor(
            float x_center, float y_center,
            float r_center, float g_center, float b_center,
            float radius,
            float r_edge, float g_edge, float b_edge) {

        if (!programs.containsKey("color"))
            GraphicTricks.createProgramSimple();

        return new CircleColor(
                x_center, y_center, r_center, g_center, b_center,
                radius, r_edge, g_edge, b_edge,
                GraphicTricks.width, GraphicTricks.height);
    }


    /**
     * Рисует простую точку
     * @param point - точка, которую нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(PointSimple point) {

        glUseProgram(programs.get("simple"));
        glVertexAttribPointer(aPositionLocationSimple, 2, GL_FLOAT, false, 0,
                point.getVertexData());
        glEnableVertexAttribArray(aPositionLocationSimple);
        glDrawArrays(GL_POINTS, 0, 1);

        return 1;
    }

    /**
     * Рисует простую линию
     * @param line - линия, которую нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(LineSimple line) {

        glUseProgram(programs.get("simple"));
        glVertexAttribPointer(aPositionLocationSimple, 2, GL_FLOAT, false, 0,
                line.getVertexData());
        glEnableVertexAttribArray(aPositionLocationSimple);
        glDrawArrays(GL_LINES, 0, 2);

        return 1;
    }

    /**
     * Рисует цветную линию
     * @param line - линия, которую нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(LineColor line) {

        glUseProgram(programs.get("color"));
        glVertexAttribPointer(aPositionLocationColor, 2, GL_FLOAT, false, 20,
                line.getVertexData().position(0));
        glEnableVertexAttribArray(aPositionLocationColor);

        glVertexAttribPointer(aColorLocationColor, 3, GL_FLOAT, false, 20,
                line.getVertexData().position(2));
        glEnableVertexAttribArray(aColorLocationColor);
        glDrawArrays(GL_LINES, 0, 2);

        return 1;
    }

    /**
     * Рисует простую непрерывную линию
     * @param line - линия, которую нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(LineContinuousSimple line) {

        glUseProgram(programs.get("simple"));
        glVertexAttribPointer(aPositionLocationSimple, 2, GL_FLOAT, false, 0,
                line.getVertexData());
        glEnableVertexAttribArray(aPositionLocationSimple);
        glDrawArrays(GL_LINE_STRIP, 0, line.getNumberOfVertices());

        return 1;
    }

    /**
     * Рисует цветную непрерывную линию
     * @param line - линия, которую нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(LineContinuousColor line) {

        glUseProgram(programs.get("color"));
        glVertexAttribPointer(aPositionLocationColor, 2, GL_FLOAT, false, 20,
                line.getVertexData().position(0));
        glEnableVertexAttribArray(aPositionLocationColor);

        glVertexAttribPointer(aColorLocationColor, 3, GL_FLOAT, false, 20,
                line.getVertexData().position(2));
        glEnableVertexAttribArray(aColorLocationColor);
        glDrawArrays(GL_LINE_STRIP, 0, line.getNumberOfVertices());

        return 1;
    }

    /**
     * Рисует простую непрерывную линию, начало которой соединяется с концом
     * @param line - линия, которую нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(LineContinuousLoopSimple line) {

        glUseProgram(programs.get("simple"));
        glVertexAttribPointer(aPositionLocationSimple, 2, GL_FLOAT, false, 0,
                line.getVertexData());
        glEnableVertexAttribArray(aPositionLocationSimple);
        glDrawArrays(GL_LINE_LOOP, 0, line.getNumberOfVertices());

        return 1;
    }

    /**
     * Рисует цветную непрерывную линию, начало которой соединяется с концом
     * @param line - линия, которую нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(LineContinuousLoopColor line) {

        glUseProgram(programs.get("color"));
        glVertexAttribPointer(aPositionLocationColor, 2, GL_FLOAT, false, 20,
                line.getVertexData().position(0));
        glEnableVertexAttribArray(aPositionLocationColor);

        glVertexAttribPointer(aColorLocationColor, 3, GL_FLOAT, false, 20,
                line.getVertexData().position(2));
        glEnableVertexAttribArray(aColorLocationColor);
        glDrawArrays(GL_LINE_LOOP, 0, line.getNumberOfVertices());

        return 1;
    }


    /**
     * Рисует простой треугольник
     * @param triangle - треугольник, который нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(TriangleSimple triangle) {

        glUseProgram(programs.get("simple"));
        glVertexAttribPointer(aPositionLocationSimple, 2, GL_FLOAT, false, 0,
                triangle.getVertexData());
        glEnableVertexAttribArray(aPositionLocationSimple);
        glDrawArrays(GL_TRIANGLES, 0, 3);

        return 1;
    }

    /**
     * Рисует цветной треугольник
     * @param triangle - треугольник, который нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(TriangleColor triangle) {

        glUseProgram(programs.get("color"));
        glVertexAttribPointer(aPositionLocationColor, 2, GL_FLOAT, false, 20,
                triangle.getVertexData().position(0));
        glEnableVertexAttribArray(aPositionLocationColor);

        glVertexAttribPointer(aColorLocationColor, 3, GL_FLOAT, false, 20,
                triangle.getVertexData().position(2));
        glEnableVertexAttribArray(aColorLocationColor);
        glDrawArrays(GL_TRIANGLES, 0, 3);

        return 1;
    }


    /**
     * Рисует простой четырехугольник, состоящий из двух треугольников
     * @param quadrangle - четырехугольник, который нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(QuadrangleSimple quadrangle) {

        glUseProgram(programs.get("simple"));
        glVertexAttribPointer(aPositionLocationSimple, 2, GL_FLOAT, false, 0,
                quadrangle.getVertexData());
        glEnableVertexAttribArray(aPositionLocationSimple);
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

        return 1;
    }

    /**
     * Рисует цветной четырехугольник
     * @param quadrangle - треугольник, который нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(QuadrangleColor quadrangle) {

        glUseProgram(programs.get("color"));
        glVertexAttribPointer(aPositionLocationColor, 2, GL_FLOAT, false, 20,
                quadrangle.getVertexData().position(0));
        glEnableVertexAttribArray(aPositionLocationColor);

        glVertexAttribPointer(aColorLocationColor, 3, GL_FLOAT, false, 20,
                quadrangle.getVertexData().position(2));
        glEnableVertexAttribArray(aColorLocationColor);
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

        return 1;
    }


    /**
     * Рисует простой круг
     * @param circle - круг, который нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(CircleSimple circle) {

        glUseProgram(programs.get("simple"));
        glVertexAttribPointer(aPositionLocationSimple, 2, GL_FLOAT, false, 0,
                circle.getVertexData());
        glEnableVertexAttribArray(aPositionLocationSimple);
        glDrawArrays(GL_TRIANGLE_FAN, 0, 74);

        return 1;
    }

    /**
     * Рисует цветной круг
     * @param circle - круг, который нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(CircleColor circle) {

        glUseProgram(programs.get("color"));
        glVertexAttribPointer(aPositionLocationColor, 2, GL_FLOAT, false, 0,
                circle.getVertexData().position(0));
        glEnableVertexAttribArray(aPositionLocationColor);

        glVertexAttribPointer(aColorLocationColor, 3, GL_FLOAT, false, 0,
                circle.getVertexColorBuffer().position(0));
        glEnableVertexAttribArray(aColorLocationColor);
        glDrawArrays(GL_TRIANGLE_FAN, 0, 74);

        return 1;
    }


    /**
     * Рисует простой полигон
     * @param polygon - многоугольник, который нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(PolygonSimple polygon) {

        glUseProgram(programs.get("simple"));
        glVertexAttribPointer(aPositionLocationSimple, 2, GL_FLOAT, false, 0,
                polygon.getVertexData());
        glEnableVertexAttribArray(aPositionLocationSimple);
        glDrawArrays(GL_TRIANGLE_FAN, 0, polygon.getNumberOfVertices());

        return 1;
    }

    /**
     * Рисует цветной полигон
     * @param polygon - полигон, который нужно нарисовать
     * @return - статус выполнения операции рисования
     */
    public static int draw(PolygonColor polygon) {

        glUseProgram(programs.get("color"));
        glVertexAttribPointer(aPositionLocationColor, 2, GL_FLOAT, false, 20,
                polygon.getVertexData().position(0));
        glEnableVertexAttribArray(aPositionLocationColor);

        glVertexAttribPointer(aColorLocationColor, 3, GL_FLOAT, false, 20,
                polygon.getVertexData().position(2));
        glEnableVertexAttribArray(aColorLocationColor);
        glDrawArrays(GL_TRIANGLE_FAN, 0, polygon.getNumberOfVertices());

        return 1;
    }


    /**
     * Задаёт контекст для доступа к ресурсам
     * @param context - контекст, содержащий ресурсы
     */
    public static void setContext(Context context) {
        GraphicTricks.context = context;
    }

    /**
     * Изменяет размер окна
     * @param width - ширина окна
     * @param height - высота окна
     */
    public static void changeSizeSurface(int width, int height) {
        glViewport(0, 0, width, height);
    }

    /**
     * Изменяет цвет очистки экрана
     * @param red   - компонента красного цвета
     * @param green - компонента зеленого цвета
     * @param blue  - компонента синего цвета
     * @param alpha - альфа канал
     */
    public static void changeClearColor(float red, float green, float blue, float alpha) {
        GraphicTricks.clearRed = red;
        GraphicTricks.clearGreen = green;
        GraphicTricks.clearBlue = blue;
        GraphicTricks.clearAlpha = alpha;
    }

    /** Очищает экран используемым цветом */
    public static void clearScreen() {
        glClearColor(clearRed, clearGreen, clearBlue, clearAlpha);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    /** Очищает экран указанным цветом */
    public static void clearScreen(float red, float green, float blue, float alpha) {
        glClearColor(red, green, blue, alpha);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    /** Изменяет цвет кисти */
    public static void changeBrushColor(float red, float green, float blue, float alpha) {
        glUniform4f(uColorLocationSimple, red, green, blue, alpha);
    }

    /** Устанавливает размер экрана */
    public static void setScreenSize(int width, int height) {
        GraphicTricks.width = width;
        GraphicTricks.height = height;
    }

    /**
     * Метод, который служит для создания программы, связывающей два простейших шейдера,
     * позволяющих рисовать Simple примитивы
     */
    private static void createProgramSimple() {

        int vertexShaderId = ShaderUtils.createShader(context, GL_VERTEX_SHADER,
                R.raw.vertex_shader);
        int fragmentShaderId = ShaderUtils.createShader(context, GL_FRAGMENT_SHADER,
                R.raw.fragment_shader);
        int programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId);
        programs.put("simple", programId);

        glUseProgram(programId);
        uColorLocationSimple = glGetUniformLocation(programId, "u_Color");
        aPositionLocationSimple = glGetAttribLocation(programId, "a_Position");
    }

    /**
     * Метод, который служит для создания программы, связывающей два шейдера,
     * позволяющих рисовать Color-примитивы
     */
    private static void createProgramColor() {

        int vertexShaderId = ShaderUtils.createShader(context, GL_VERTEX_SHADER,
                R.raw.vertex_shader_color);
        int fragmentShaderId = ShaderUtils.createShader(context, GL_FRAGMENT_SHADER,
                R.raw.fragment_shader_color);
        int programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId);
        programs.put("color", programId);

        glUseProgram(programId);
        aColorLocationColor = glGetAttribLocation(programId, "a_Color");
        aPositionLocationColor = glGetAttribLocation(programId, "a_Position");
    }
}
