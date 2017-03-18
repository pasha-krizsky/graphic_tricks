package com.gamedev.dreamteam.graphicTricks.renderers;

import android.content.Context;
import android.content.res.Configuration;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;

import com.gamedev.dreamteam.graphicTricks.R;
import com.gamedev.dreamteam.graphicTricks.engine.Camera;
import com.gamedev.dreamteam.graphicTricks.engine.GraphicTricks;
import com.gamedev.dreamteam.graphicTricks.engine.Model;
import com.gamedev.dreamteam.graphicTricks.primitives.LineColor;
import com.gamedev.dreamteam.graphicTricks.primitives.QuadrangleTexture;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLRendererCubesFromLargeToSmallTexture implements GLSurfaceView.Renderer {
    // 10 секунд
    private final static long TIME = 4000;

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
    private QuadrangleTexture boxNear;
    private QuadrangleTexture boxFar;
    private QuadrangleTexture boxTop;
    private QuadrangleTexture boxBottom;
    private QuadrangleTexture boxLeft;
    private QuadrangleTexture boxRight;

    private QuadrangleTexture boxNear2;
    private QuadrangleTexture boxFar2;
    private QuadrangleTexture boxTop2;
    private QuadrangleTexture boxBottom2;
    private QuadrangleTexture boxLeft2;
    private QuadrangleTexture boxRight2;

    private QuadrangleTexture boxNear3;
    private QuadrangleTexture boxFar3;
    private QuadrangleTexture boxTop3;
    private QuadrangleTexture boxBottom3;
    private QuadrangleTexture boxLeft3;
    private QuadrangleTexture boxRight3;

    private QuadrangleTexture boxNear4;
    private QuadrangleTexture boxFar4;
    private QuadrangleTexture boxTop4;
    private QuadrangleTexture boxBottom4;
    private QuadrangleTexture boxLeft4;
    private QuadrangleTexture boxRight4;

    private QuadrangleTexture boxNear5;
    private QuadrangleTexture boxFar5;
    private QuadrangleTexture boxTop5;
    private QuadrangleTexture boxBottom5;
    private QuadrangleTexture boxLeft5;
    private QuadrangleTexture boxRight5;

    // Оси
    private LineColor lineX;
    private LineColor lineY;
    private LineColor lineZ;

    private int texture;
    private int texture2;
    private int texture3;
    private int texture4;
    private int texture5;
    private int texture6;

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
    public OpenGLRendererCubesFromLargeToSmallTexture(Context context, int width, int height) {

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

        // Минимальное и максимальное значение по X и Y
        setMinAndMax();

        // Новая камера и модель
        camera = new Camera();
        model  = new Model();

        // Задаем начальные настройки
        GraphicTricks.init();
        // Очищаем экран черным цветом
        GraphicTricks.changeClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GraphicTricks.clearScreen();

        // Инициализируем камеру и модель
        camera.initialize();
        model.drop();

        // Создаем оси
        lineX = GraphicTricks.createLineColor(
                -1.5f, 0f, 0f, 1.0f, 0.0f, 0.0f,
                1.5f, 0f, 0f, 1.0f, 0.0f, 0.0f
        );

        lineY = GraphicTricks.createLineColor(
                0f, -1.5f, 0f, 0.0f, 1.0f, 0.0f,
                0f, 1.5f, 0f, 0.0f, 1.0f, 0.0f
        );

        lineZ = GraphicTricks.createLineColor(
                0f, 0f, -1.5f, 0.0f, 0.0f, 1.0f,
                0f, 0f, 1.5f, 0.0f, 0.0f, 1.0f
        );

        // Создаем грани
        boxNear = GraphicTricks.createQuadrangleTexture(
                -0.5f, -0.5f, 0f,
                 0.5f, -0.5f, 0f,
                -0.5f,  0.5f, 0f,
                 0.5f,  0.5f, 0f
        );

        boxFar = GraphicTricks.createQuadrangleTexture(
                -0.5f, -0.5f, -1f,
                0.5f,  -0.5f, -1f,
                -0.5f,  0.5f, -1f,
                0.5f,   0.5f, -1f
        );

        boxTop = GraphicTricks.createQuadrangleTexture(
                -0.5f, 0.5f, 0f,
                0.5f,  0.5f, 0f,
                -0.5f, 0.5f, -1f,
                0.5f,  0.5f, -1f
        );

        boxBottom = GraphicTricks.createQuadrangleTexture(
                -0.5f, -0.5f,  0f,
                 0.5f, -0.5f,  0f,
                -0.5f, -0.5f, -1f,
                 0.5f, -0.5f, -1f
        );

        boxLeft = GraphicTricks.createQuadrangleTexture(
                -0.5f, -0.5f,  0f,
                -0.5f, -0.5f, -1f,
                -0.5f,  0.5f,  0f,
                -0.5f,  0.5f, -1f
        );

        boxRight = GraphicTricks.createQuadrangleTexture(
                0.5f, -0.5f,  0f,
                0.5f, -0.5f, -1f,
                0.5f,  0.5f,  0f,
                0.5f,  0.5f, -1f
        );

        boxNear2 = GraphicTricks.createQuadrangleTexture(
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                -0.5f,  0.5f, 0f,
                0.5f,  0.5f, 0f
        );

        boxFar2 = GraphicTricks.createQuadrangleTexture(
                -0.5f, -0.5f, -1f,
                0.5f,  -0.5f, -1f,
                -0.5f,  0.5f, -1f,
                0.5f,   0.5f, -1f
        );

        boxTop2 = GraphicTricks.createQuadrangleTexture(
                -0.5f, 0.5f, 0f,
                0.5f,  0.5f, 0f,
                -0.5f, 0.5f, -1f,
                0.5f,  0.5f, -1f
        );

        boxBottom2 = GraphicTricks.createQuadrangleTexture(
                -0.5f, -0.5f,  0f,
                0.5f, -0.5f,  0f,
                -0.5f, -0.5f, -1f,
                0.5f, -0.5f, -1f
        );

        boxLeft2 = GraphicTricks.createQuadrangleTexture(
                -0.5f, -0.5f,  0f,
                -0.5f, -0.5f, -1f,
                -0.5f,  0.5f,  0f,
                -0.5f,  0.5f, -1f
        );

        boxRight2 = GraphicTricks.createQuadrangleTexture(
                0.5f, -0.5f,  0f,
                0.5f, -0.5f, -1f,
                0.5f,  0.5f,  0f,
                0.5f,  0.5f, -1f
        );

        boxNear3 = GraphicTricks.createQuadrangleTexture(
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                -0.5f,  0.5f, 0f,
                0.5f,  0.5f, 0f
        );

        boxFar3 = GraphicTricks.createQuadrangleTexture(
                -0.5f, -0.5f, -1f,
                0.5f,  -0.5f, -1f,
                -0.5f,  0.5f, -1f,
                0.5f,   0.5f, -1f
        );

        boxTop3 = GraphicTricks.createQuadrangleTexture(
                -0.5f, 0.5f, 0f,
                0.5f,  0.5f, 0f,
                -0.5f, 0.5f, -1f,
                0.5f,  0.5f, -1f
        );

        boxBottom3 = GraphicTricks.createQuadrangleTexture(
                -0.5f, -0.5f,  0f,
                0.5f, -0.5f,  0f,
                -0.5f, -0.5f, -1f,
                0.5f, -0.5f, -1f
        );

        boxLeft3 = GraphicTricks.createQuadrangleTexture(
                -0.5f, -0.5f,  0f,
                -0.5f, -0.5f, -1f,
                -0.5f,  0.5f,  0f,
                -0.5f,  0.5f, -1f
        );

        boxRight3 = GraphicTricks.createQuadrangleTexture(
                0.5f, -0.5f,  0f,
                0.5f, -0.5f, -1f,
                0.5f,  0.5f,  0f,
                0.5f,  0.5f, -1f
        );

        boxNear4 = GraphicTricks.createQuadrangleTexture(
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                -0.5f,  0.5f, 0f,
                0.5f,  0.5f, 0f
        );

        boxFar4 = GraphicTricks.createQuadrangleTexture(
                -0.5f, -0.5f, -1f,
                0.5f,  -0.5f, -1f,
                -0.5f,  0.5f, -1f,
                0.5f,   0.5f, -1f
        );

        boxTop4 = GraphicTricks.createQuadrangleTexture(
                -0.5f, 0.5f, 0f,
                0.5f,  0.5f, 0f,
                -0.5f, 0.5f, -1f,
                0.5f,  0.5f, -1f
        );

        boxBottom4 = GraphicTricks.createQuadrangleTexture(
                -0.5f, -0.5f,  0f,
                0.5f, -0.5f,  0f,
                -0.5f, -0.5f, -1f,
                0.5f, -0.5f, -1f
        );

        boxLeft4 = GraphicTricks.createQuadrangleTexture(
                -0.5f, -0.5f,  0f,
                -0.5f, -0.5f, -1f,
                -0.5f,  0.5f,  0f,
                -0.5f,  0.5f, -1f
        );

        boxRight4 = GraphicTricks.createQuadrangleTexture(
                0.5f, -0.5f,  0f,
                0.5f, -0.5f, -1f,
                0.5f,  0.5f,  0f,
                0.5f,  0.5f, -1f
        );

        boxNear5 = GraphicTricks.createQuadrangleTexture(
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                -0.5f,  0.5f, 0f,
                0.5f,  0.5f, 0f
        );

        boxFar5 = GraphicTricks.createQuadrangleTexture(
                -0.5f, -0.5f, -1f,
                0.5f,  -0.5f, -1f,
                -0.5f,  0.5f, -1f,
                0.5f,   0.5f, -1f
        );

        boxTop5 = GraphicTricks.createQuadrangleTexture(
                -0.5f, 0.5f, 0f,
                0.5f,  0.5f, 0f,
                -0.5f, 0.5f, -1f,
                0.5f,  0.5f, -1f
        );

        boxBottom5 = GraphicTricks.createQuadrangleTexture(
                -0.5f, -0.5f,  0f,
                0.5f, -0.5f,  0f,
                -0.5f, -0.5f, -1f,
                0.5f, -0.5f, -1f
        );

        boxLeft5 = GraphicTricks.createQuadrangleTexture(
                -0.5f, -0.5f,  0f,
                -0.5f, -0.5f, -1f,
                -0.5f,  0.5f,  0f,
                -0.5f,  0.5f, -1f
        );

        boxRight5 = GraphicTricks.createQuadrangleTexture(
                0.5f, -0.5f,  0f,
                0.5f, -0.5f, -1f,
                0.5f,  0.5f,  0f,
                0.5f,  0.5f, -1f
        );

        // Одна текстура на все грани
        texture = GraphicTricks.loadTexture(context, R.drawable.box);
        texture2 = GraphicTricks.loadTexture(context, R.drawable.box2);
        texture3 = GraphicTricks.loadTexture(context, R.drawable.box3);
        texture4 = GraphicTricks.loadTexture(context, R.drawable.box4);
        texture5 = GraphicTricks.loadTexture(context, R.drawable.box5);
        texture6 = GraphicTricks.loadTexture(context, R.drawable.box6);
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
        camera.move(1f, 1f, 2f);
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
        GraphicTricks.changeBrushColor(1.0f, 1.0f, 0.0f, 1.0f);
        /*GraphicTricks.draw(lineX);
        GraphicTricks.draw(lineY);
        GraphicTricks.draw(lineZ);*/

        float angle = (float)(SystemClock.uptimeMillis() % TIME) / TIME * 360;

        //model.move(0f, 0f, -0.5f);
        model.rotateAroundZ(angle);

        GraphicTricks.draw(boxNear,   texture2);
        GraphicTricks.draw(boxFar,    texture2);
        GraphicTricks.draw(boxTop,    texture2);
        GraphicTricks.draw(boxBottom, texture2);
        GraphicTricks.draw(boxLeft,   texture2);
        GraphicTricks.draw(boxRight,  texture2);

        model.drop();
        model.move(-3f, -3f, -0.5f);
        model.rotateAroundZ(angle);

        GraphicTricks.draw(boxNear2,   texture3);
        GraphicTricks.draw(boxFar2,    texture3);
        GraphicTricks.draw(boxTop2,    texture3);
        GraphicTricks.draw(boxBottom2, texture3);
        GraphicTricks.draw(boxLeft2,   texture3);
        GraphicTricks.draw(boxRight2,  texture3);

        model.drop();
        model.move(0.8f, 0.8f, -5f);
        model.rotateAroundZ(angle);

        GraphicTricks.draw(boxNear3,   texture6);
        GraphicTricks.draw(boxFar3,    texture6);
        GraphicTricks.draw(boxTop3,    texture6);
        GraphicTricks.draw(boxBottom3, texture6);
        GraphicTricks.draw(boxLeft3,   texture6);
        GraphicTricks.draw(boxRight3,  texture6);

        model.drop();
        model.move(0.8f, -2.5f, -1.5f);
        model.rotateAroundZ(angle);

        GraphicTricks.draw(boxNear4,   texture2);
        GraphicTricks.draw(boxFar4,    texture2);
        GraphicTricks.draw(boxTop4,    texture2);
        GraphicTricks.draw(boxBottom4, texture2);
        GraphicTricks.draw(boxLeft4,   texture2);
        GraphicTricks.draw(boxRight4,  texture2);

        model.drop();
        model.move(-4f, 1.5f, -3.5f);
        model.rotateAroundZ(angle);

        GraphicTricks.draw(boxNear5,   texture3);
        GraphicTricks.draw(boxFar5,    texture3);
        GraphicTricks.draw(boxTop5,    texture3);
        GraphicTricks.draw(boxBottom5, texture3);
        GraphicTricks.draw(boxLeft5,   texture3);
        GraphicTricks.draw(boxRight5,  texture3);
    }

    private void setMinAndMax() {
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
    }
}
