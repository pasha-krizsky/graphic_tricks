attribute vec4 a_Position;
uniform mat4 u_Matrix;
// Данные по координатам текстуры
attribute vec2 a_Texture;
// Передаем в фрагментный шейдер интерполированные координаты текстуры
varying vec2 v_Texture;

void main() {
    // Итоговые координаты
    gl_Position = u_Matrix * a_Position;
    // Передаем координаты текстуры в фрагментный шейдер
    v_Texture = a_Texture;
}
