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
import com.gamedev.dreamteam.graphicTricks.primitives.QuadrangleColor;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * Класс, в котором производится рендеринг. Пример использования движка GraphicTricks
 */
public class OpenGLRendererCubesFromLargeToSmall implements Renderer {

    // 5 секунд
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

    private QuadrangleColor near3;
    private QuadrangleColor far3;
    private QuadrangleColor bottom3;
    private QuadrangleColor top3;
    private QuadrangleColor left3;
    private QuadrangleColor right3;

    private QuadrangleColor near4;
    private QuadrangleColor far4;
    private QuadrangleColor bottom4;
    private QuadrangleColor top4;
    private QuadrangleColor left4;
    private QuadrangleColor right4;

    private QuadrangleColor near5;
    private QuadrangleColor far5;
    private QuadrangleColor bottom5;
    private QuadrangleColor top5;
    private QuadrangleColor left5;
    private QuadrangleColor right5;

    private QuadrangleColor near6;
    private QuadrangleColor far6;
    private QuadrangleColor bottom6;
    private QuadrangleColor top6;
    private QuadrangleColor left6;
    private QuadrangleColor right6;

    private LineSimple lineX;

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
    public OpenGLRendererCubesFromLargeToSmall(Context context, int width, int height) {

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

        lineX = GraphicTricks.createLineSimple(
                -1f, 0f, 0f,
                1f, 0f, 0f
        );

        near = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, 0f, 0.6f, 0f, 0f,
                0.5f,  -0.5f, 0f, 0.6f, 0f, 0f,
                -0.5f,  0.5f, 0f, 0.6f, 0f, 0f,
                0.5f,   0.5f, 0f, 0.6f, 0f, 0f
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

        near2 = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, 0f, 0.3f, 0f, 0.6f,
                0.5f,  -0.5f, 0f, 0.3f, 0f, 0.6f,
                -0.5f,  0.5f, 0f, 0.3f, 0f, 0.6f,
                0.5f,   0.5f, 0f, 0.3f, 0f, 0.6f
        );

        far2 = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, -1f, 0f, 1f, 0f,
                0.5f,  -0.5f, -1f, 0f, 1f, 0f,
                -0.5f,  0.5f, -1f, 0f, 1f, 0f,
                0.5f,   0.5f, -1f, 0f, 1f, 0f
        );

        bottom2 = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, 0f,   0f, 0f, 1f,
                0.5f,  -0.5f, 0f,   0f, 0f, 1f,
                -0.5f,  -0.5f, -1f, 0f, 0f, 1f,
                0.5f,   -0.5f, -1f, 0f, 0f, 1f
        );

        top2 = GraphicTricks.createQuadrangleColor(
                -0.5f, 0.5f, 0f,  1f, 1f, 0f,
                0.5f,  0.5f, 0f,  1f, 1f, 0f,
                -0.5f, 0.5f, -1f, 1f, 1f, 0f,
                0.5f,  0.5f, -1f, 1f, 1f, 0f
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

        near3 = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, 0f, 1f, 0f, 0f,
                0.5f,  -0.5f, 0f, 1f, 0f, 0f,
                -0.5f,  0.5f, 0f, 1f, 0f, 0f,
                0.5f,   0.5f, 0f, 1f, 0f, 0f
        );

        far3 = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, -1f, 0f, 1f, 0f,
                0.5f,  -0.5f, -1f, 0f, 1f, 0f,
                -0.5f,  0.5f, -1f, 0f, 1f, 0f,
                0.5f,   0.5f, -1f, 0f, 1f, 0f
        );

        bottom3 = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, 0f,   0f, 0f, 1f,
                0.5f,  -0.5f, 0f,   0f, 0f, 1f,
                -0.5f,  -0.5f, -1f, 0f, 0f, 1f,
                0.5f,   -0.5f, -1f, 0f, 0f, 1f
        );

        top3 = GraphicTricks.createQuadrangleColor(
                -0.5f, 0.5f, 0f,  1f, 1f, 0f,
                0.5f,  0.5f, 0f,  1f, 1f, 0f,
                -0.5f, 0.5f, -1f, 1f, 1f, 0f,
                0.5f,  0.5f, -1f, 1f, 1f, 0f
        );

        right3 = GraphicTricks.createQuadrangleColor(
                0.5f, -0.5f, 0f,  1f, 0f, 1f,
                0.5f, -0.5f, -1f, 1f, 0f, 1f,
                0.5f, 0.5f, 0f,   1f, 0f, 1f,
                0.5f, 0.5f, -1f,  1f, 0f, 1f
        );

        left3 = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, 0f,  0f, 1f, 1f,
                -0.5f, -0.5f, -1f, 0f, 1f, 1f,
                -0.5f, 0.5f, 0f,   0f, 1f, 1f,
                -0.5f, 0.5f, -1f,  0f, 1f, 1f
        );

        near4 = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, 0f, 0.3f, 0f, 0.6f,
                0.5f,  -0.5f, 0f, 0.3f, 0f, 0.6f,
                -0.5f,  0.5f, 0f, 0.3f, 0f, 0.6f,
                0.5f,   0.5f, 0f, 0.3f, 0f, 0.6f
        );

        far4 = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, -1f, 0f, 1f, 0f,
                0.5f,  -0.5f, -1f, 0f, 1f, 0f,
                -0.5f,  0.5f, -1f, 0f, 1f, 0f,
                0.5f,   0.5f, -1f, 0f, 1f, 0f
        );

        bottom4 = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, 0f,   0f, 0.6f, 0.6f,
                0.5f,  -0.5f, 0f,   0f, 0.6f, 0.6f,
                -0.5f,  -0.5f, -1f, 0f, 0.6f, 0.6f,
                0.5f,   -0.5f, -1f, 0f, 0.6f, 0.6f
        );

        top4 = GraphicTricks.createQuadrangleColor(
                -0.5f, 0.5f, 0f,  1f, 1f, 0f,
                0.5f,  0.5f, 0f,  1f, 1f, 0f,
                -0.5f, 0.5f, -1f, 1f, 1f, 0f,
                0.5f,  0.5f, -1f, 1f, 1f, 0f
        );

        right4 = GraphicTricks.createQuadrangleColor(
                0.5f, -0.5f, 0f,  1f, 0f, 1f,
                0.5f, -0.5f, -1f, 1f, 0f, 1f,
                0.5f, 0.5f, 0f,   1f, 0f, 1f,
                0.5f, 0.5f, -1f,  1f, 0f, 1f
        );

        left4 = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, 0f,  0f, 1f, 1f,
                -0.5f, -0.5f, -1f, 0f, 1f, 1f,
                -0.5f, 0.5f, 0f,   0f, 1f, 1f,
                -0.5f, 0.5f, -1f,  0f, 1f, 1f
        );

        near5 = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, 0f, 1f, 0f, 0f,
                0.5f,  -0.5f, 0f, 1f, 0f, 0f,
                -0.5f,  0.5f, 0f, 1f, 0f, 0f,
                0.5f,   0.5f, 0f, 1f, 0f, 0f
        );

        far5 = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, -1f, 0f, 1f, 0f,
                0.5f,  -0.5f, -1f, 0f, 1f, 0f,
                -0.5f,  0.5f, -1f, 0f, 1f, 0f,
                0.5f,   0.5f, -1f, 0f, 1f, 0f
        );

        bottom5 = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, 0f,   0f, 0f, 1f,
                0.5f,  -0.5f, 0f,   0f, 0f, 1f,
                -0.5f,  -0.5f, -1f, 0f, 0f, 1f,
                0.5f,   -0.5f, -1f, 0f, 0f, 1f
        );

        top5 = GraphicTricks.createQuadrangleColor(
                -0.5f, 0.5f, 0f,  1f, 1f, 0f,
                0.5f,  0.5f, 0f,  1f, 1f, 0f,
                -0.5f, 0.5f, -1f, 1f, 1f, 0f,
                0.5f,  0.5f, -1f, 1f, 1f, 0f
        );

        right5 = GraphicTricks.createQuadrangleColor(
                0.5f, -0.5f, 0f,  1f, 0f, 1f,
                0.5f, -0.5f, -1f, 1f, 0f, 1f,
                0.5f, 0.5f, 0f,   1f, 0f, 1f,
                0.5f, 0.5f, -1f,  1f, 0f, 1f
        );

        left5 = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, 0f,  0f, 1f, 1f,
                -0.5f, -0.5f, -1f, 0f, 1f, 1f,
                -0.5f, 0.5f, 0f,   0f, 1f, 1f,
                -0.5f, 0.5f, -1f,  0f, 1f, 1f
        );

        near6 = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, 0f, 1f, 0f, 0f,
                0.5f,  -0.5f, 0f, 1f, 0f, 0f,
                -0.5f,  0.5f, 0f, 1f, 0f, 0f,
                0.5f,   0.5f, 0f, 1f, 0f, 0f
        );

        far6 = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, -1f, 0f, 1f, 0f,
                0.5f,  -0.5f, -1f, 0f, 1f, 0f,
                -0.5f,  0.5f, -1f, 0f, 1f, 0f,
                0.5f,   0.5f, -1f, 0f, 1f, 0f
        );

        bottom6 = GraphicTricks.createQuadrangleColor(
                -0.5f, -0.5f, 0f,   0f, 0f, 1f,
                0.5f,  -0.5f, 0f,   0f, 0f, 1f,
                -0.5f,  -0.5f, -1f, 0f, 0f, 1f,
                0.5f,   -0.5f, -1f, 0f, 0f, 1f
        );

        top6 = GraphicTricks.createQuadrangleColor(
                -0.5f, 0.5f, 0f,  1f, 1f, 0f,
                0.5f,  0.5f, 0f,  1f, 1f, 0f,
                -0.5f, 0.5f, -1f, 1f, 1f, 0f,
                0.5f,  0.5f, -1f, 1f, 1f, 0f
        );

        right6 = GraphicTricks.createQuadrangleColor(
                0.5f, -0.5f, 0f,  1f, 0f, 1f,
                0.5f, -0.5f, -1f, 1f, 0f, 1f,
                0.5f, 0.5f, 0f,   1f, 0f, 1f,
                0.5f, 0.5f, -1f,  1f, 0f, 1f
        );

        left6 = GraphicTricks.createQuadrangleColor(
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
        GraphicTricks.changeClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GraphicTricks.clearScreen();

        camera.move(1f, 1f, 2.5f);
        camera.look(0f, 0f, 0);
    }

    /**
     * Метод, реализующий отрисовку.
     * @param arg0 - не используемый параметр для реализации совместимости с OpenGL ES 1
     */
    @Override
    public void onDrawFrame(GL10 arg0) {
        GraphicTricks.clear();
        GraphicTricks.changeBrushColor(1.0f, 0.0f, 0.0f, 0.0f);

        float angle =  (float)(SystemClock.uptimeMillis() % TIME)  / TIME  * 360;
        float angle2 = (float)(SystemClock.uptimeMillis() % TIME2) / TIME2 * 360;
        float angle3 = (float)(SystemClock.uptimeMillis() % TIME3) / TIME3 * 360;
        float angle4 = (float)(SystemClock.uptimeMillis() % TIME4) / TIME4 * 360;
        float angle5 = (float)(SystemClock.uptimeMillis() % TIME5) / TIME5 * 360;
        float angle6 = (float)(SystemClock.uptimeMillis() % TIME6) / TIME6 * 360;
        float angle7 = (float)(SystemClock.uptimeMillis() % TIME7) / TIME7 * 360;

        model.drop();
        model.move(0.4f, 0.3f, 1.2f);
        model.rotateAroundZ(angle);

        GraphicTricks.draw(near);
        GraphicTricks.draw(far);
        GraphicTricks.draw(top);
        GraphicTricks.draw(bottom);
        GraphicTricks.draw(right);
        GraphicTricks.draw(left);

        model.drop();
        model.move(-2f, -4f, 0f);
        model.rotateAroundZ(angle4);

        GraphicTricks.draw(near2);
        GraphicTricks.draw(far2);
        GraphicTricks.draw(top2);
        GraphicTricks.draw(bottom2);
        GraphicTricks.draw(right2);
        GraphicTricks.draw(left2);

        model.drop();
        model.move(-2f, 2f, -1f);
        model.rotateAroundZ(angle7);

        GraphicTricks.draw(near3);
        GraphicTricks.draw(far3);
        GraphicTricks.draw(top3);
        GraphicTricks.draw(bottom3);
        GraphicTricks.draw(right3);
        GraphicTricks.draw(left3);

        model.drop();
        model.move(1f, -4f, -1f);
        model.rotateAroundZ(angle4);

        GraphicTricks.draw(near4);
        GraphicTricks.draw(far4);
        GraphicTricks.draw(top4);
        GraphicTricks.draw(bottom4);
        GraphicTricks.draw(right4);
        GraphicTricks.draw(left4);

        model.drop();
        model.move(0.8f, 2f, -2.2f);
        model.rotateAroundZ(angle6);

        GraphicTricks.draw(near5);
        GraphicTricks.draw(far5);
        GraphicTricks.draw(top5);
        GraphicTricks.draw(bottom5);
        GraphicTricks.draw(right5);
        GraphicTricks.draw(left5);

        /*model.drop();
        model.move(-6.5f, -4f, -2f);
        model.rotateAroundZ(angle6);

        GraphicTricks.draw(near6);
        GraphicTricks.draw(far6);
        GraphicTricks.draw(top6);
        GraphicTricks.draw(bottom6);
        GraphicTricks.draw(right6);
        GraphicTricks.draw(left6);*/
    }
}