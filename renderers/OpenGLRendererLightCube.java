package com.gamedev.dreamteam.graphicTricks.renderers;

import android.content.Context;
import android.content.res.Configuration;
import android.opengl.GLSurfaceView.Renderer;
import android.os.SystemClock;

import com.gamedev.dreamteam.graphicTricks.engine.Camera;
import com.gamedev.dreamteam.graphicTricks.engine.GraphicTricks;
import com.gamedev.dreamteam.graphicTricks.engine.Light;
import com.gamedev.dreamteam.graphicTricks.engine.Lighted;
import com.gamedev.dreamteam.graphicTricks.engine.Model;
import com.gamedev.dreamteam.graphicTricks.primitives.Point;
import com.gamedev.dreamteam.graphicTricks.primitives.PointSimple;
import com.gamedev.dreamteam.graphicTricks.primitives.QuadrangleColor;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.glUniform4f;

public class OpenGLRendererLightCube implements Renderer {

    private final static long TIME = 10000;

    private Camera camera;
    private Model model;
    private Light light;


    private Context context;
    private int width;
    private int height;

    private QuadrangleColor near;
    private QuadrangleColor far;
    private QuadrangleColor bottom;
    private QuadrangleColor top;
    private QuadrangleColor left;
    private QuadrangleColor right;

    PointSimple p;

    private float MAX_X;
    private float MAX_Y;
    private float MIN_X;
    private float MIN_Y;

    public OpenGLRendererLightCube(Context context, int width, int height) {

        GraphicTricks.setContext(context);
        this.context = context;
        this.width = width;
        this.height = height;
    }

    @Override
    public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {

        // Новая камера и модель
        camera = new Camera();
        model = new Model();
        light = new Light();

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

        GraphicTricks.init();
        GraphicTricks.changeClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GraphicTricks.clearScreen();

        camera.initialize();
        model.drop();

        near = GraphicTricks.createQuadrangleColor(
                -1f, -1f, 1f, 0.6f, 0f, 0f,
                 1f, -1f, 1f, 0.6f, 0f, 0f,
                -1f,  1f, 1f, 0.6f, 0f, 0f,
                 1f,  1f, 1f, 0.6f, 0f, 0f
        );

        near.addNormal(0f, 0f, 1f);

        far = GraphicTricks.createQuadrangleColor(
                -1f, -1f, -1f, 0f, 1f, 0f,
                 1f, -1f, -1f, 0f, 1f, 0f,
                -1f,  1f, -1f, 0f, 1f, 0f,
                 1f,  1f, -1f, 0f, 1f, 0f
        );

        far.addNormal(0f, 0f, -1f);

        bottom = GraphicTricks.createQuadrangleColor(
                -1f, -1f,  1f, 0f, 0f, 1f,
                 1f, -1f,  1f, 0f, 0f, 1f,
                -1f, -1f, -1f, 0f, 0f, 1f,
                 1f, -1f, -1f, 0f, 0f, 1f
        );

        bottom.addNormal(0f, -1f, 0f);

        top = GraphicTricks.createQuadrangleColor(
                -1f, 1f,  1f,  1f, 1f, 0f,
                 1f, 1f,  1f,  1f, 1f, 0f,
                -1f, 1f, -1f,  1f, 1f, 0f,
                 1f, 1f, -1f,  1f, 1f, 0f
        );

        top.addNormal(0f, 1f, 0f);

        right = GraphicTricks.createQuadrangleColor(
                1f, -1f,  1f, 1f, 0f, 1f,
                1f, -1f, -1f, 1f, 0f, 1f,
                1f,  1f,  1f, 1f, 0f, 1f,
                1f,  1f, -1f, 1f, 0f, 1f
        );

        right.addNormal(1f, 0f, 0f);

        left = GraphicTricks.createQuadrangleColor(
                -1f, -1f,  1f, 0f, 1f, 1f,
                -1f, -1f, -1f, 0f, 1f, 1f,
                -1f,  1f,  1f, 0f, 1f, 1f,
                -1f,  1f, -1f, 0f, 1f, 1f
        );

        left.addNormal(-1f, 0f, 0f);

        p = GraphicTricks.createPointSimple(
                0,
                0,
                0);
    }

    @Override
    public void onSurfaceChanged(GL10 arg0, int width, int height) {

        GraphicTricks.setViewPort(0, 0, width, height);
        GraphicTricks.initializeFrustumMatrix(width, height);
        GraphicTricks.changeClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GraphicTricks.clearScreen();

        camera.initialize();


    }

    @Override
    public void onDrawFrame(GL10 arg0) {
        GraphicTricks.clear();
        GraphicTricks.changeBrushColor(1.0f, 0.0f, 0.0f, 0.0f);

        float angle =  (float)(SystemClock.uptimeMillis() % TIME)  / TIME  * 360;

        light.drop();
        light.move(0f, 0f, -5f);
        light.rotate(angle, 0f, 1f, 0f);
        light.move(0f, 0f, -2.5f);

        GraphicTricks.changeBrushColor(1f, 0f, 0f, 0f);

        System.out.println(GraphicTricks.getMLightPosInEyeSpace()[0] + " " +
                GraphicTricks.getMLightPosInEyeSpace()[1] + " " +
                GraphicTricks.getMLightPosInEyeSpace()[2]);



        model.drop();
        model.move(0f, 0f, -5f);
        model.rotate(angle, 0f, 1f, 0f);
        model.move(0f, 0f, -2.5f);
        GraphicTricks.changeBrushColor(1f, 0f, 0f, 0f);
        GraphicTricks.draw(p);

        model.drop();
        model.move(0f, 0f, -5f);
        model.rotate(angle, 1f, 1f, 0f);

        GraphicTricks.draw(near, Lighted.TRUE);
        GraphicTricks.draw(far, Lighted.TRUE);
        GraphicTricks.draw(top, Lighted.TRUE);
        GraphicTricks.draw(bottom, Lighted.TRUE);
        GraphicTricks.draw(right, Lighted.TRUE);
        GraphicTricks.draw(left, Lighted.TRUE);
    }
}