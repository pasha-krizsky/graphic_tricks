package com.gamedev.dreamteam.graphicTricks.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDeleteTextures;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glTexParameteri;
import static android.opengl.GLUtils.texImage2D;

/**
 * Класс, предоставляющий методы для работы с текстурой.
 */
public class TextureUtils {

    /**
     * Данный метод загружает текстуру из ресурсов в объект текстуры
     * @param context - контекст Android для доступа к картинке
     * @param resourceId - ID ресурса картинки
     * @return - ID созданного объекта текстуры
     */
    public static int loadTexture(Context context, int resourceId) {
        // Массив ID всех объектов текстур
        // В данном случае создаем одну текстуру
        final int[] textureIds = new int[1];

        // Создаем пустой объект текстуры
        // Параметры:
        // 1. - Сколько пустых объектов создаем
        // 2. - Куда записывать ID всех созданных объектов (в данном случае - одного объекта)
        // 3. - offset массива, с которого начинаем заполнять. 0, т.к. заполняем с 0
        glGenTextures(1, textureIds, 0);

        // Если ID = 0, то объект текстуры не создан
        if (textureIds[0] == 0) {
            return 0;
        }

        // Получение bitmap из ресурса
        // ...
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        // Чтение bitmap
        final Bitmap bitmap = BitmapFactory.decodeResource(
                context.getResources(), resourceId, options);

        // Проверка на корректное создание
        if (bitmap == null) {
            // Если bitmap получить не удалось, то удаляем объект текстуры и возвращаем 0
            // Параметры:
            // 1. Сколько объектов текстур удаляем
            // 2. Массив с ID объектов текстур
            // 3. offset массива
            glDeleteTextures(1, textureIds, 0);
            return 0;
        }

        // Настройка объекта текстуры
        // Делаем активным 0-й unit. Далее все операции адресуются этому unit-у.
        glActiveTexture(GL_TEXTURE0);
        // Помещаем пустой объект текстуры в target GL_TEXTURE_2D 0-го unit-a
        glBindTexture(GL_TEXTURE_2D, textureIds[0]);

        // Задаем параметры объекта текстуры
        // Параметры:
        // 1. target
        // 2. Какой параметр будем менять
        // 3. Значение, которое хотим присвоить этому параметру

        // В данном случае задаем параметры фильтрации
        // GL_TEXTURE_MIN_FILTER - какой режим фильтрации будет применен при сжатии объекта
        // GL_TEXTURE_MAG_FILTER - какой режим фильтрации будет применен при растяг. объекта
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        // Передаем bitmap в объект текстуры
        // Параметры:
        // 1. target
        // 2. Созданный bitmap из прочитанного ресурса
        texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);

        // Освобождаем bitmap, больше не нужен
        bitmap.recycle();

        // Отвязываем объект текстуры от таргета
        glBindTexture(GL_TEXTURE_2D, 0);

        // ID созланного объекта текстуры
        return textureIds[0];
    }

}
