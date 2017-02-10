package com.gamedev.dreamteam.graphicTricks.engine;


import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Данный класс хранит состояние, которое используется в GraphicTricks
 */
public class GraphicContext {

    /** Контекст, передаваемый из Android */
    private Context context;

    /** Положение комбинированной матрицы в шейдере для рисования simple-примитивов */
    private int uMatrixLocationSimple;
    /** Положение uniform переменной u_Color в шейдере для рисования simple-примитивов */
    private int uColorLocationSimple;
    /** Положение attribute переменной a_Position в шейдере для рисования simple-примитивов */
    private int aPositionLocationSimple;
    /** Положение комбинированной матрицы в шейдере для рисования color-примитивов */
    private int uMatrixLocationColor;
    /** Положение attribute переменной a_Color в шейдере для рисования color-примитивов */
    private int aColorLocationColor;
    /** Положение attribute переменной a_Position в шейдере для рисования color-примитивов */
    private int aPositionLocationColor;

    /** Компонента цвета очистки RED */
    private float clearRed;
    /** Компонента цвета очистки GREEN */
    private float clearGreen;
    /** Компонента цвета очистки BLUE */
    private float clearBlue;
    /** Компонента цвета очистки ALPHA */
    private float clearAlpha;

    /** Матрица проекции */
    private float[] mProjectionMatrix;
    /** Матрица вида */
    private float[] mViewMatrix;
    /** Матрица модели */
    private float[] mModelMatrix;
    /** Комбинированная матрица (перемножение трех матриц, которые выше)
     *  Ее передаем в шейдер. */
    private float[] mMatrix;

    /** Хэш-таблица всех программ */
    private Map<String, Integer> programs;

    public GraphicContext() {
        programs = new HashMap<>();
        mProjectionMatrix = new float[16];
        mViewMatrix = new float[16];
        mModelMatrix = new float[16];
        mMatrix = new float[16];

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getUMatrixLocationSimple() {
        return uMatrixLocationSimple;
    }

    public void setUMatrixLocationSimple(int uMatrixLocationSimple) {
        this.uMatrixLocationSimple = uMatrixLocationSimple;
    }

    public int getUColorLocationSimple() {
        return uColorLocationSimple;
    }

    public void setUColorLocationSimple(int uColorLocationSimple) {
        this.uColorLocationSimple = uColorLocationSimple;
    }

    public int getAPositionLocationSimple() {
        return aPositionLocationSimple;
    }

    public void setAPositionLocationSimple(int aPositionLocationSimple) {
        this.aPositionLocationSimple = aPositionLocationSimple;
    }

    public int getUMatrixLocationColor() {
        return uMatrixLocationColor;
    }

    public void setUMatrixLocationColor(int uMatrixLocationColor) {
        this.uMatrixLocationColor = uMatrixLocationColor;
    }

    public int getAColorLocationColor() {
        return aColorLocationColor;
    }

    public void setAColorLocationColor(int aColorLocationColor) {
        this.aColorLocationColor = aColorLocationColor;
    }

    public int getAPositionLocationColor() {
        return aPositionLocationColor;
    }

    public void setAPositionLocationColor(int aPositionLocationColor) {
        this.aPositionLocationColor = aPositionLocationColor;
    }

    public float getClearRed() {
        return clearRed;
    }

    public void setClearRed(float clearRed) {
        this.clearRed = clearRed;
    }

    public float getClearGreen() {
        return clearGreen;
    }

    public void setClearGreen(float clearGreen) {
        this.clearGreen = clearGreen;
    }

    public float getClearBlue() {
        return clearBlue;
    }

    public void setClearBlue(float clearBlue) {
        this.clearBlue = clearBlue;
    }

    public float getClearAlpha() {
        return clearAlpha;
    }

    public void setClearAlpha(float clearAlpha) {
        this.clearAlpha = clearAlpha;
    }

    public float[] getProjectionMatrix() {
        return mProjectionMatrix;
    }

    public void setProjectionMatrix(float[] mProjectionMatrix) {
        this.mProjectionMatrix = mProjectionMatrix;
    }

    public float[] getViewMatrix() {
        return mViewMatrix;
    }

    public void setViewMatrix(float[] mViewMatrix) {
        this.mViewMatrix = mViewMatrix;
    }

    public float[] getModelMatrix() {
        return mModelMatrix;
    }

    public void setModelMatrix(float[] mModelMatrix) {
        this.mModelMatrix = mModelMatrix;
    }

    public float[] getMatrix() {
        return mMatrix;
    }

    public void setMatrix(float[] mMatrix) {
        this.mMatrix = mMatrix;
    }

    public Map<String, Integer> getPrograms() {
        return programs;
    }

    public void setPrograms(Map<String, Integer> programs) {
        this.programs = programs;
    }
}
