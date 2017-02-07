package com.gamedev.dreamteam.graphicTricks;

import android.os.Bundle;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.util.DisplayMetrics;

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
        super.onCreate(savedInstanceState);

        // Вьюшка для рисования
        mGLSurfaceView = new GLSurfaceView(this);

        // Проверка на поддержку OpenGL ES 2.0
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

        // Получение размеров экрана в пикселях и передача в рендер
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels;

        if (supportsEs2)
        {
            mGLSurfaceView.setEGLContextClientVersion(2);
            mGLSurfaceView.setRenderer(new OpenGLRenderer(this, width, height));
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