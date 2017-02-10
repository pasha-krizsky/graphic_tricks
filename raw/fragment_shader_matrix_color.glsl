// Точность вычислений float-значений
precision mediump float;
// Цвет, получаемый из фрагментного шейдера
varying vec4 v_Color;

void main() {
    // Передача интерполированного цвета в специальную переменную
    gl_FragColor = v_Color;
}