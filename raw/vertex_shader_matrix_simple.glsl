// Информация о вершине
attribute vec4 a_Position;
// Информация о комбинированной матрице
uniform mat4 u_Matrix;
 
void main()
{
    // Размер точки
    gl_PointSize = 5.0;
    // Итоговое положение вершины
    gl_Position = u_Matrix * a_Position;
}