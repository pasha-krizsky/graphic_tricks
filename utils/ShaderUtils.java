package com.gamedev.dreamteam.graphicTricks.utils;

// Импорт контекста и ресурсов для чтения шейдеров
import android.content.Context;
import android.content.res.Resources;

// Импорт буферов и I/O потоков
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

// Импорт методов OpenGL
import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;

/**
 * Класс, позволяющий читать шейдеры из файлов, компилировать шейдеры,
 * создавать из шейдеров программу
 */
public class ShaderUtils {

    /**
     * Читает текст из ресурсов (код, написанный на glsl) в строку, которую возвращает
     * @param context - класс android.content.Context для получения ресурсов
     * @param resourceId - идентификатор ресурса
     * @return - строка с исходным кодом прочитанного шейдера
     */
    public static String readTextFromRaw(Context context, int resourceId) {

        // Объект, в который записываем строки из файла
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = null;
            try {
                // Входной поток, принимающий файл
                InputStream inputStream = context.getResources().openRawResource(resourceId);
                // Объект, читающий входной поток
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                // Построчное чтение строк
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append("\r\n");
                }
            } finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Resources.NotFoundException nfex) {
            nfex.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * Создаёт программу. Программа - пара шейдеров: вершинный + фрагментный
     * @param vertexShaderId - id вершинного шейдера
     * @param fragmentShaderId - id фрагментного шейдера
     * @return - id созданной программы
     */
    public static int createProgram(int vertexShaderId, int fragmentShaderId) {
        // Создаём пустую программу и сохраняем её id
        final int programId = glCreateProgram();
        // Вместо id получаем 0, ошибка
        if (programId == 0)
            return 0;
        // Аттачим шейдеры к программе
        glAttachShader(programId, vertexShaderId);
        glAttachShader(programId, fragmentShaderId);
        // Формируем программу из приаттаченных шейдеров
        glLinkProgram(programId);
        // Проверяем статус формирования программы
        final int[] linkStatus = new int[1];
        glGetProgramiv(programId, GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0) {
            // Удаляем программу в случае неудачи
            glDeleteProgram(programId);
            return 0;
        }

        return programId;
    }

    /**
     * Читает содержимое шейдера в строку, вызывает перегруженный метод
     * @param context - класс android.content.Context для получения ресурсов
     * @param type - тип шейдера GL_VERTEX_SHADER - вершинный
     *               или GL_FRAGMENT_SHADER - фрагментный
     * @param shaderRawId - id raw-ресурса
     * @return - id шейдера, получаемый из перегруженного метода, или 0
     */
    public static int createShader(Context context, int type, int shaderRawId) {
        String shaderText = readTextFromRaw(context, shaderRawId);
        return ShaderUtils.createShader(type, shaderText);
    }

    /**
     *
     * @param type - тип шейдера
     * @param shaderText - код шейдера, записанный в String
     * @return - id шейдера или 0
     */
    public static int createShader(int type, String shaderText) {
        final int shaderId = glCreateShader(type);
        if (shaderId == 0) {
            return 0;
        }
        // Тут все ясно
        glShaderSource(shaderId, shaderText);
        glCompileShader(shaderId);
        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderId, GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            glDeleteShader(shaderId);
            return 0;
        }
        return shaderId;
    }
}
