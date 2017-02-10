package com.gamedev.dreamteam.graphicTricks;

import android.content.Context;
import android.content.res.Configuration;
import android.opengl.GLSurfaceView.Renderer;
import android.os.SystemClock;

import com.gamedev.dreamteam.graphicTricks.engine.Camera;
import com.gamedev.dreamteam.graphicTricks.engine.GraphicTricks;
import com.gamedev.dreamteam.graphicTricks.engine.Model;
import com.gamedev.dreamteam.graphicTricks.primitives.QuadrangleColor;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * Класс, в котором производится рендеринг. Пример использования движка GraphicTricks
 */
public class OpenGLRendererCube implements Renderer {

    // 10 секунд
    private final static long TIME = 10000;

    // Ссылка на камеру
    private Camera camera;
    // Ссылка на модель
    private Model model;

    // Ссылка на контекст андроида
    private Context context;
    // Ширина экрана
    private int width;
    // Высота экрана
    private int height;

    // Примитивы, которые будем отрисовывать - грани куба
    private QuadrangleColor near;
    private QuadrangleColor far;
    private QuadrangleColor bottom;
    private QuadrangleColor top;
    private QuadrangleColor left;
    private QuadrangleColor right;

    // Максимальные координаты по X и Y. Координата по меньшей стороне телефона изменяется
    // от -1 до 1, а по большей - от -height/width до height/width - для портретной ориентации
    // экрана и от -width/height до width/height для альбомной ориентации
    private float MAX_X;
    private float MAX_Y;
    private float MIN_X;
    private float MIN_Y;

    /**
     * Конструктор рендера.
     * @param context - класс android.content.Context для передачи в движок для чтения ресурсов
     */
    public OpenGLRendererCube(Context context, int width, int height) {

        GraphicTricks.setContext(context);
        this.context = context;
        this.width = width;
        this.height = height;
    }

    /**
     * Метод, вызывающийся при старте рендеринга
     * @param arg0 - не используемый параметр для реализации совместимости с OpenGL ES 1
     * @param arg1 - конфигурация
     */
    @Override
    public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {

        // Новая камера и модель
        camera = new Camera();
        model = new Model();

        // Проверка текущей ориентации телефонв
        if (context.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {

            MAX_X = (float) width/height;
            MAX_Y = 1;
            MIN_X = (float) -width/height;
            MIN_Y = -1;
        }
        else {

            MAX_X = 1;
            MAX_Y = (float) height/width;
            MIN_X = -1;
            MIN_Y = (float) -height/width;
        }

        // Задаем начальные настройки
        GraphicTricks.init();
        GraphicTricks.changeClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GraphicTricks.clearScreen();

        // Инициализируем камеру и модель
        camera.initialize();
        model.drop();

        near = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, 0f, 1f, 0f, 0f,
                0.5f,  -0.5f, 0f, 1f, 0f, 0f,
                -0.5f,  0.5f, 0f, 1f, 0f, 0f,
                0.5f,   0.5f, 0f, 1f, 0f, 0f
        );

        far = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, -1f, 0f, 1f, 0f,
                0.5f,  -0.5f, -1f, 0f, 1f, 0f,
                -0.5f,  0.5f, -1f, 0f, 1f, 0f,
                0.5f,   0.5f, -1f, 0f, 1f, 0f
        );

        bottom = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, 0f,   0f, 0f, 1f,
                0.5f,  -0.5f, 0f,   0f, 0f, 1f,
                -0.5f,  -0.5f, -1f, 0f, 0f, 1f,
                0.5f,   -0.5f, -1f, 0f, 0f, 1f
        );

        top = GraphicTricks.createQuadrangleColor(
                -0.5f, 0.5f, 0f,  1f, 1f, 0f,
                0.5f,  0.5f, 0f,  1f, 1f, 0f,
                -0.5f, 0.5f, -1f, 1f, 1f, 0f,
                0.5f,  0.5f, -1f, 1f, 1f, 0f
        );

        right = GraphicTricks.createQuadrangleColor(
                0.5f, -0.5f, 0f,  1f, 0f, 1f,
                0.5f, -0.5f, -1f, 1f, 0f, 1f,
                0.5f, 0.5f, 0f,   1f, 0f, 1f,
                0.5f, 0.5f, -1f,  1f, 0f, 1f
        );

        left = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, 0f,  0f, 1f, 1f,
                -0.5f, -0.5f, -1f, 0f, 1f, 1f,
                -0.5f, 0.5f, 0f,   0f, 1f, 1f,
                -0.5f, 0.5f, -1f,  0f, 1f, 1f
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
        GraphicTricks.setViewPort(0, 0, width, height);
        GraphicTricks.initializeFrustumMatrix(width, height);
        camera.move(1f, 0.5f, 1f);
        camera.look(0f, 0f, 0);
    }

    /**
     * Метод, реализующий отрисовку.
     * @param arg0 - не используемый параметр для реализации совместимости с OpenGL ES 1
     */
    @Override
    public void onDrawFrame(GL10 arg0) {
        GraphicTricks.clear();

        model.drop();
        // Анимация
        float time = (float)(SystemClock.uptimeMillis() % TIME) / TIME;
        float angle = time  *  2 * 3.1415926f;
        float eyeX = (float) (Math.cos(angle) * 3f);
        camera.move(eyeX, 1f, 1.0f);

        GraphicTricks.draw(near);
        GraphicTricks.draw(far);
        GraphicTricks.draw(top);
        GraphicTricks.draw(bottom);
        GraphicTricks.draw(right);
        GraphicTricks.draw(left);
    }
}