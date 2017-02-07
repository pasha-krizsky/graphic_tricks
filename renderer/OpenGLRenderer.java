package com.gamedev.dreamteam.graphicTricks;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;

import com.gamedev.dreamteam.graphicTricks.engine.GraphicTricks;
import com.gamedev.dreamteam.graphicTricks.primitives.CircleColor;
import com.gamedev.dreamteam.graphicTricks.primitives.QuadrangleColor;
import com.gamedev.dreamteam.graphicTricks.primitives.TriangleColor;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Класс, в котором производится рендеринг. Пример использования движка GraphicTricks
 */
public class OpenGLRenderer implements Renderer {

    // Примитивы, которые будем отрисовывать
    private TriangleColor sail1;
    private TriangleColor sail2;
    private QuadrangleColor board;
    private QuadrangleColor water;
    private QuadrangleColor sky;
    private CircleColor window;

    /**
     * Конструктор рендера.
     * @param context - класс android.content.Context для передачи в движок для чтения ресурсов
     * @param width - ширина экрана - используется в движке
     * @param height - высота экрана - используется в движке
     */
    public OpenGLRenderer(Context context, int width, int height) {
        GraphicTricks.setContext(context);
        GraphicTricks.setScreenSize(width, height);
    }

    /**
     * Метод, вызывающийся при старте рендеринга
     * @param arg0 - не используемый параметр для реализации совместимости с OpenGL ES 1
     * @param arg1 - конфигурация
     */
    @Override
    public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {

        // Пример отрисовки корабля
        GraphicTricks.changeClearColor(0.5f, 0.5f, 0.5f, 0.5f);
        GraphicTricks.clearScreen();

        sail1 = GraphicTricks.createTriangleColor(
                -0.5f, 0.1f, 0.46f, 0.76f, 0.98f,
                0.1f, 0.1f, 0.46f, 0.76f, 0.98f,
                0.1f, 0.6f, 0.78f, 0.90f, 0.99f
        );

        sail2 = GraphicTricks.createTriangleColor(
                0.15f, 0.55f, 0.65f, 0.74f, 0.80f,
                0.15f, 0.1f, 0.33f, 0.59f, 0.79f,
                0.6f, 0.1f, 0.33f, 0.59f, 0.79f
        );

        board = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.1f, 0.64f, 0.63f, 0.44f,
                0.7f, -0.1f, 0.64f, 0.63f, 0.44f,
                -0.6f, 0.05f, 0.74f, 0.73f, 0.64f,
                0.8f, 0.05f, 0.74f, 0.73f, 0.64f
        );

        window = GraphicTricks.createCircleColor(
                0.4f, -0.035f, 0.86f, 0.65f, 0.22f,
                0.085f, 0.89f, 0.70f, 0.31f
                );

        water = GraphicTricks.createQuadrangleColor(
                -1.0f, -1.0f, 0.42f, 0.60f, 0.74f,
                -1.0f, 0.0f, 0.60f, 0.72f, 0.81f,
                1.0f, -1.0f, 0.42f, 0.60f, 0.74f,
                1.0f, 0.0f, 0.60f, 0.72f, 0.81f
        );

        sky = GraphicTricks.createQuadrangleColor(
                -1.0f, 0.0f, 0.93f, 0.96f, 0.99f,
                -1.0f, 1.0f, 0.90f, 0.91f, 0.93f,
                1.0f, 0.0f, 0.93f, 0.96f, 0.99f,
                1.0f, 1.0f, 0.90f, 0.91f, 0.93f
        );

    }

    /**
     * Метод, вызывающийся при старте рендеринга, а так же всякий раз, когда меняем ориентацию
     * экрана
     * @param arg0 - не используемый параметр для реализации совместимости с OpenGL ES 1
     * @param width - ширина экрана
     * @param height - высота экрана
     */
    @Override
    public void onSurfaceChanged(GL10 arg0, int width, int height) {
        GraphicTricks.changeSizeSurface(width, height);
    }

    /**
     * Метод, реализующий отрисовку.
     * @param arg0 - не используемый параметр для реализации совместимости с OpenGL ES 1
     */
    @Override
    public void onDrawFrame(GL10 arg0) {
        GraphicTricks.draw(sky);
        GraphicTricks.draw(water);
        GraphicTricks.draw(board);
        GraphicTricks.draw(sail1);
        GraphicTricks.draw(sail2);
        GraphicTricks.draw(window);
    }
}