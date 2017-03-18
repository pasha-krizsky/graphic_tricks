precision mediump float;

// В данную переменную получаем номер юнита с текстурой
uniform sampler2D u_TextureUnit;
// В данную переменную приходят интерполированные данные из
// вершинного шейдера
varying vec2 v_Texture;

void main() {
    // Получаем цвет фрагмента
    gl_FragColor = texture2D(u_TextureUnit, v_Texture);
}
