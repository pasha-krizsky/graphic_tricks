uniform mat4 u_MVPMatrix;      // Константа содержащая комбинированную матрицу model/view/projection.
uniform mat4 u_MVMatrix;       // Константа содержащая комбинированную матрицу model/view.

attribute vec4 a_Position;     // Передаем сюда информацию о положении вершин.
attribute vec4 a_Color;        // Передаем сюда информацию о цвете.
attribute vec3 a_Normal;       // Передаем информацию о нормализованных вершинах.

varying vec3 v_Position;       // Передаем данные в фрагментный шейдер.
varying vec4 v_Color;          // Передаем данные в фрагментный шейдер.
varying vec3 v_Normal;         // Передаем данные в фрагментный шейдер.

// Начало программы вершинного шейдера.
void main()
{
    // Преобразуем вершины к видимому пространству.
    v_Position = vec3(u_MVMatrix * a_Position);

    // Передаем цвет.
    v_Color = a_Color;

    // Преобразуем нормализованное пространство к видимому пространству.
    v_Normal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));

    // gl_Position переменная содержащая окончательное значение о положении.
    // Перемножаем вершины и матрицу для получения конечных точек в нормализованном пространстве экрана.
    gl_Position = u_MVPMatrix * a_Position;
}