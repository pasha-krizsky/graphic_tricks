package com.gamedev.dreamteam.graphicTricks.renderers;

import android.content.Context;
import android.content.res.Configuration;
import android.opengl.GLSurfaceView.Renderer;
import android.os.SystemClock;

import com.gamedev.dreamteam.graphicTricks.engine.Camera;
import com.gamedev.dreamteam.graphicTricks.engine.GraphicTricks;
import com.gamedev.dreamteam.graphicTricks.engine.Model;
import com.gamedev.dreamteam.graphicTricks.primitives.LineColor;
import com.gamedev.dreamteam.graphicTricks.primitives.LineSimple;
import com.gamedev.dreamteam.graphicTricks.primitives.OctahedronColor;
import com.gamedev.dreamteam.graphicTricks.primitives.QuadrangleColor;
import com.gamedev.dreamteam.graphicTricks.primitives.TetrahedronColor;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * Класс, в котором производится рендеринг. Пример использования движка GraphicTricks
 */
public class OpenGLRendererCubesAndPiramides implements Renderer {

    private final static long TIME = 10000;
    private final static long TIME2 = 9000;
    private final static long TIME3 = 8000;
    private final static long TIME4 = 7000;
    private final static long TIME5 = 6000;
    private final static long TIME6 = 5000;
    private final static long TIME7 = 4000;

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

    private QuadrangleColor near2;
    private QuadrangleColor far2;
    private QuadrangleColor bottom2;
    private QuadrangleColor top2;
    private QuadrangleColor left2;
    private QuadrangleColor right2;

    private OctahedronColor octahedronColor;
    private OctahedronColor octahedronColor2;
    private TetrahedronColor tetrahedronColor;
    private TetrahedronColor tetrahedronColor2;
    private TetrahedronColor tetrahedronColor3;

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
    public OpenGLRendererCubesAndPiramides(Context context, int width, int height) {

        GraphicTricks.setContext(context);
        this.context = context;
        this.width   = width;
        this.height  = height;
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
        model  = new Model();

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
                -0.5f, -0.5f, 0f, 1f, 1f, 0.4f,
                 0.5f, -0.5f, 0f, 1f, 1f, 0.4f,
                -0.5f,  0.5f, 0f, 1f, 1f, 0.4f,
                 0.5f,  0.5f, 0f, 1f, 1f, 0.4f
        );

        far = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, -1f, 0.5f, 0.5f, 0f,
                0.5f,  -0.5f, -1f, 0.5f, 0.5f, 0f,
                -0.5f,  0.5f, -1f, 0.5f, 0.5f, 0f,
                0.5f,   0.5f, -1f, 0.5f, 0.5f, 0f
        );

        bottom = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, 0f,   0.8f, 0.2f, 0.2f,
                0.5f,  -0.5f, 0f,   0.8f, 0.2f, 0.2f,
                -0.5f,  -0.5f, -1f, 0.8f, 0.2f, 0.2f,
                0.5f,   -0.5f, -1f, 0.8f, 0.2f, 0.2f
        );

        top = GraphicTricks.createQuadrangleColor(
                -0.5f, 0.5f, 0f,  1f, 0.5f, 0.5f,
                0.5f,  0.5f, 0f,  1f, 0.5f, 0.5f,
                -0.5f, 0.5f, -1f, 1f, 0.5f, 0.5f,
                0.5f,  0.5f, -1f, 1f, 0.5f, 0.5f
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

        near2 = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, 0f, 1f, 1f, 0.4f,
                0.5f, -0.5f, 0f, 1f, 1f, 0.4f,
                -0.5f,  0.5f, 0f, 1f, 1f, 0.4f,
                0.5f,  0.5f, 0f, 1f, 1f, 0.4f
        );

        far2 = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, -1f, 0.5f, 0.5f, 0f,
                0.5f,  -0.5f, -1f, 0.5f, 0.5f, 0f,
                -0.5f,  0.5f, -1f, 0.5f, 0.5f, 0f,
                0.5f,   0.5f, -1f, 0.5f, 0.5f, 0f
        );

        bottom2 = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, 0f,   0.8f, 0.2f, 0.2f,
                0.5f,  -0.5f, 0f,   0.8f, 0.2f, 0.2f,
                -0.5f,  -0.5f, -1f, 0.8f, 0.2f, 0.2f,
                0.5f,   -0.5f, -1f, 0.8f, 0.2f, 0.2f
        );

        top2 = GraphicTricks.createQuadrangleColor(
                -0.5f, 0.5f, 0f,  1f, 0.5f, 0.5f,
                0.5f,  0.5f, 0f,  1f, 0.5f, 0.5f,
                -0.5f, 0.5f, -1f, 1f, 0.5f, 0.5f,
                0.5f,  0.5f, -1f, 1f, 0.5f, 0.5f
        );

        right2 = GraphicTricks.createQuadrangleColor(
                0.5f, -0.5f, 0f,  1f, 0f, 1f,
                0.5f, -0.5f, -1f, 1f, 0f, 1f,
                0.5f, 0.5f, 0f,   1f, 0f, 1f,
                0.5f, 0.5f, -1f,  1f, 0f, 1f
        );

        left2 = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, 0f,  0f, 1f, 1f,
                -0.5f, -0.5f, -1f, 0f, 1f, 1f,
                -0.5f, 0.5f, 0f,   0f, 1f, 1f,
                -0.5f, 0.5f, -1f,  0f, 1f, 1f
        );

        octahedronColor  = GraphicTricks.createOctahedronColor
                (0f, 0f, 0f, 0.4f, 0.0f, 0.8f, 0.6f, 0.8f, 1f, 0.4f, 0.0f, 0.8f, 1.0f, 0.7f);

        octahedronColor2  = GraphicTricks.createOctahedronColor
                (0f, 0f, 0f, 0.4f, 0.0f, 0.8f, 0.6f, 0.8f, 1f, 0.4f, 0.0f, 0.8f, 1.0f, 0.7f);

        tetrahedronColor = GraphicTricks.createTetrahedronColor(
                0f, 0f, 0f,
                1.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f,
                0.6f, 0.5f);

        tetrahedronColor2 = GraphicTricks.createTetrahedronColor(
                0f, 0f, 0f,
                0.6f, 1f, 0.2f,
                1f, 0.2f, 0.6f,
                0.6f, 0.5f);

        tetrahedronColor3 = GraphicTricks.createTetrahedronColor(
                0f, 0f, 0f,
                1.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f,
                0.6f, 0.5f);
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
        GraphicTricks.changeClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GraphicTricks.clearScreen();

        camera.move(0.5f, 0.2f, 2f);
        camera.look(0f, 0f, 0);
        model.drop();
    }

    /**
     * Метод, реализующий отрисовку.
     * @param arg0 - не используемый параметр для реализации совместимости с OpenGL ES 1
     */
    @Override
    public void onDrawFrame(GL10 arg0) {
        GraphicTricks.clear();
        GraphicTricks.changeBrushColor(1.0f, 0.0f, 0.0f, 0.0f);

        float angle  = (float)(SystemClock.uptimeMillis() % TIME)  / TIME  * 360;
        float angle2 = (float)(SystemClock.uptimeMillis() % TIME2) / TIME2 * 360;
        float angle3 = (float)(SystemClock.uptimeMillis() % TIME3) / TIME3 * 360;
        float angle4 = (float)(SystemClock.uptimeMillis() % TIME4) / TIME4 * 360;
        float angle5 = (float)(SystemClock.uptimeMillis() % TIME5) / TIME5 * 360;
        float angle6 = (float)(SystemClock.uptimeMillis() % TIME6) / TIME6 * 360;
        float angle7 = (float)(SystemClock.uptimeMillis() % TIME7) / TIME7 * 360;

        model.drop();
        //model.move(1f, 0f, 0f);
        model.rotateAroundY(angle);

        GraphicTricks.draw(octahedronColor);

        model.drop();
        model.move(-1.5f, -3f, -0.5f);
        model.rotateAroundY(angle2);
        GraphicTricks.draw(tetrahedronColor);

        model.drop();
        model.move(1f, 1.5f, -1.5f);
        model.rotateAroundZ(angle4);

        GraphicTricks.draw(near);
        GraphicTricks.draw(far);
        GraphicTricks.draw(top);
        GraphicTricks.draw(bottom);
        GraphicTricks.draw(right);
        GraphicTricks.draw(left);

        model.drop();
        model.move(-1.5f, 2.2f, -1f);
        model.rotateAroundY(angle2);
        GraphicTricks.draw(tetrahedronColor2);

        model.drop();
        model.move(-2.5f, 0f, -1.5f);
        model.rotateAroundZ(angle4);

        GraphicTricks.draw(near2);
        GraphicTricks.draw(far2);
        GraphicTricks.draw(top2);
        GraphicTricks.draw(bottom2);
        GraphicTricks.draw(right2);
        GraphicTricks.draw(left2);

        model.drop();
        model.move(2f, -3.5f, -3.5f);
        model.rotateAroundY(angle4);

        GraphicTricks.draw(octahedronColor);

        model.drop();
        model.move(2f, -0.3f, -3.5f);
        model.rotateAroundY(angle4);

        GraphicTricks.draw(tetrahedronColor3);
    }
}