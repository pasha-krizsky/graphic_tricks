package com.gamedev.dreamteam.graphicTricks.activities;

import android.graphics.Point;
import android.os.Bundle;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.view.Display;

import com.gamedev.dreamteam.graphicTricks.renderers.OpenGLRendererLightCube;

/**
 * Класс, описывающий главную активность. Хранит ссылку на GLSurfaceView,
 * проверяет версию OpenGL ES, назначает объект для рендера.
 */
public class MainActivity extends Activity
{
    /** Ссылка на GLSurfaceView */
    private GLSurfaceView mGLSurfaceView;

    /**
     * Метод, вызывающийся каждый раз при создании активности
     * @param savedInstanceState - сохраненное состояние
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        //setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);

        // Вьюшка для рисования
        mGLSurfaceView = new GLSurfaceView(this);

        // Проверка на поддержку OpenGL ES 2.0
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

        if (supportsEs2)
        {
            // Получаем ширину и высоту экрана и передаем их в рендерер
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            mGLSurfaceView.setEGLContextClientVersion(2);
            //mGLSurfaceView.setRenderer(new OpenGLRendererCubesAndPiramides(this, width, height));
            //mGLSurfaceView.setRenderer(new LessonTwoRenderer());
            mGLSurfaceView.setRenderer(new OpenGLRendererLightCube(this, width, height));
        }
        else return;

        setContentView(mGLSurfaceView);
    }

    /**
     * Активность снова в фокусе
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        mGLSurfaceView.onResume();
    }

    /**
     * Метод, вызывающийся, когда активность вне фокуса
     */
    @Override
    protected void onPause()
    {
        super.onPause();
        mGLSurfaceView.onPause();
    }
}