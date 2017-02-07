// устанавливаем точность вычислений для float переменных
// доступны три режима: lowp, mediump, highp
precision mediump float;

// переменная u_Color содержит цвет: RGBA
// uniform - значение одинаково для всех фрагментов
// vec4 - вектор из 4-х float элементов
uniform vec4 u_Color;

// Основной метод шейдера
void main() {
    // gl_FragColor - специальная переменная шейдера, в которую помещается значение
    // цвета для текущего фрагмента
    gl_FragColor = u_Color;
}