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

    /** Положение комбинированной матрицы в шейдере для рисования текстур */
    private int uMatrixLocationTexture;
    /** Положение attribute переменной a_Position в шейдере для рисования текстур */
    private int aPositionLocationTexture;
    /** Положение uniform переменной типа sampler2D u_TextureUnit в шейдере для рисования текстур */
    private int uTextureUnitLocation;
    /** Положение attribute переменной типа vec2 a_Texture в шейдере для хранения коорд. текстуры */
    private int aTextureLocation;

    /** Положение комбинированной матрицы в шейдере для рисования примитивов с освещением */
    private int uMatrixLocationLight;
    /** Положение матрицы вида и модели в шейдере для рисования примитивов с освещением */
    private int uMVMatrixLocationLight;
    /** Положение attribute переменной a_Position в шейдере для рисования примитивов с освещ. */
    private int aPositionLocationLight;
    /** Положение uniform переменной u_LightPos в фраг. шейдере для рисования примитивов с освещ. */
    private int aLightLocationLight;
    /** Положение attribute переменной a_Color в шейдере для рисования примитивов с освещ.*/
    private int aColorLocationLight;
    /** Положение attribute переменной a_Normal в шейдере для рисования примитивов с освещ.*/
    private int aNormalLocationLight;
    /** Исходное положение источника света */
    private float[] mLightPosInModelSpace;
    /** Положение источника света, перемноженное с матрицей модели */
    private float[] mLightPosInWorldSpace;
    /** Положение источника света, перемноженное с матрицей модели и матрицей вида */
    private float[] mLightPosInEyeSpace;

    /** Матрица модели для вершинного шейдера, имитирующего освещение */
    private float[] mLightModelMatrix;

    /** Матрица проекции */
    private float[] mProjectionMatrix;

    /** Матрица вида */
    private float[] mViewMatrix;

    /** Матрица модели */
    private float[] mModelMatrix;

    /** Комбинированная матрица (перемножение трех матриц, которые выше)
     *  Ее передаем в шейдер. */
    private float[] mMatrix;

    /** Компонента цвета очистки RED */
    private float clearRed;
    /** Компонента цвета очистки GREEN */
    private float clearGreen;
    /** Компонента цвета очистки BLUE */
    private float clearBlue;
    /** Компонента цвета очистки ALPHA */
    private float clearAlpha;

    /** Хэш-таблица всех программ */
    private Map<String, Integer> programs;

    public GraphicContext() {
        programs = new HashMap<>();
        mProjectionMatrix = new float[16];
        mViewMatrix = new float[16];
        mModelMatrix = new float[16];
        mMatrix = new float[16];
        mLightModelMatrix = new float[16];

        mLightPosInModelSpace = new float[] {0.0f, 0.0f, 0.0f, 1.0f};
        mLightPosInWorldSpace = new float[4];
        mLightPosInEyeSpace   = new float[4];
    }


    public void setUMatrixLocationLight(int uMatrixLocationLight) {
        this.uMatrixLocationLight = uMatrixLocationLight;
    }

    public void setUMVMatrixLocationLight(int uMVMatrixLocationLight) {
        this.uMVMatrixLocationLight = uMVMatrixLocationLight;
    }

    public void setAPositionLocationLight(int aPositionLocationLight) {
        this.aPositionLocationLight = aPositionLocationLight;
    }

    public void setULightLocationLight(int aLightLocationLight) {
        this.aLightLocationLight = aLightLocationLight;
    }

    public void setAColorLocationLight(int aColorLocationLight) {
        this.aColorLocationLight = aColorLocationLight;
    }

    public void setANormalLocationLight(int aNormalLocationLight) {
        this.aNormalLocationLight = aNormalLocationLight;
    }

    public int getUMatrixLocationLight() {
        return uMatrixLocationLight;
    }

    public int getUMVMatrixLocationLight() {
        return uMVMatrixLocationLight;
    }

    public int getAPositionLocationLight() {
        return aPositionLocationLight;
    }

    public int getALightLocationLight() {
        return aLightLocationLight;
    }

    public int getAColorLocationLight() {
        return aColorLocationLight;
    }

    public int getANormalLocationLight() {
        return aNormalLocationLight;
    }

    public float[] getMLightModelMatrix() {
        return mLightModelMatrix;
    }

    public float[] getMLightPosInEyeSpace() {
        return mLightPosInEyeSpace;
    }

    public float[] getMLightPosInWorldSpace() {
        return mLightPosInWorldSpace;
    }

    public float[] getMLightPosInModelSpace() {
        return mLightPosInModelSpace;
    }

    public Context getContext() {
        return context;
    }

    public int getUMatrixLocationTexture() {
        return uMatrixLocationTexture;
    }

    public void setUMatrixLocationTexture(int uMatrixLocationTexture) {
        this.uMatrixLocationTexture = uMatrixLocationTexture;
    }

    public int getAPositionLocationTexture() {
        return aPositionLocationTexture;
    }

    public void setAPositionLocationTexture(int aPositionLocationTexture) {
        this.aPositionLocationTexture = aPositionLocationTexture;
    }

    public int getUTextureUnitLocation() {
        return uTextureUnitLocation;
    }

    public void setUTextureUnitLocation(int uTextureUnitLocation) {
        this.uTextureUnitLocation = uTextureUnitLocation;
    }

    public int getATextureLocation() {
        return aTextureLocation;
    }

    public void setATextureLocation(int aTextureLocation) {
        this.aTextureLocation = aTextureLocation;
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
