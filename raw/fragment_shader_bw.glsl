// Точность вычислений float-значений
precision mediump float;
// Цвет, получаемый из фрагментного шейдера
varying vec4 v_Color;

void main() {
    float intensity = dot(v_Color.rgb, vec3(0.29, 0.59, 0.12));
    gl_FragColor = vec4(intensity, intensity, intensity, 1.0);
}