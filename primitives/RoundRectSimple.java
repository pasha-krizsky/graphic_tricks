package com.gamedev.dreamteam.graphicTricks.primitives;

public class RoundRectSimple extends RoundRect {

    public RoundRectSimple(float x_top_left, float y_top_left,
                           float x_bottom_right, float y_bottom_right,
                           float round_radius) {

        float x1 = x_top_left + round_radius;
        float y1 = y_bottom_right;

        float x2 = x_bottom_right - round_radius;
        float y2 = y_bottom_right;

        float x3 = x_top_left + round_radius;
        float y3 = y_bottom_right + round_radius;

        float x4 = x_bottom_right - round_radius;
        float y4 = y_bottom_right + round_radius;

        float x5 = x_top_left + round_radius;
        float y5 = y_top_left - round_radius;

        float x6 = x_bottom_right - round_radius;
        float y6 = y_top_left - round_radius;

        float x7 = x_bottom_right - round_radius;
        float y7 = y_top_left;

        float x8 = x_top_left + round_radius;
        float y8 = y_top_left;

        float x9 = x_top_left;
        float y9 = y_top_left - round_radius;

        float x10 = x_top_left;
        float y10 = y_bottom_right + round_radius;

        float x11 = x_bottom_right;
        float y11 = y_top_left - round_radius;

        float x12 = x_bottom_right;
        float y12 = y_bottom_right + round_radius;

        vertices = new float[144];

        vertices[0]  = x1;  vertices[1]  = y1;  vertices[2]  = x2;  vertices[3]  = y2;  vertices[4]  = x3;
        vertices[5]  = y3;  vertices[6]  = x4;  vertices[7]  = y4;  vertices[8]  = x5;  vertices[9]  = y5;
        vertices[10] = x6;  vertices[11] = y6;  vertices[12] = x8;  vertices[13] = y8;  vertices[14] = x7;
        vertices[15] = y7;  vertices[16] = x9;  vertices[17] = y9;  vertices[18] = x10; vertices[19] = y10;
        vertices[20] = x5;  vertices[21] = y5;  vertices[22] = x3;  vertices[23] = y3;  vertices[24] = x11;
        vertices[25] = y11; vertices[26] = x12; vertices[27] = y12; vertices[28] = x6;  vertices[29] = y6;
        vertices[30] = x4;  vertices[31] = y4;  vertices[32] = x6;  vertices[33] = y6;

        float angle = 0;
        float step  = 10;
        int   index = 34;

        while (angle <= 90) {

            float angleRad = (float) angle * (float) Math.PI / 180;
            vertices[index] = x6 + round_radius * (float) Math.cos(angleRad);
            index++;
            vertices[index] = y6 + round_radius * (float) Math.sin(angleRad);
            index++;
            angle += step;
        }

        vertices[index] = x5;  vertices[index+1] = y5;

        index += 2;
        angle -= step;
        while (angle <= 180) {

            float angleRad = (float) angle * (float) Math.PI / 180;
            vertices[index] = x5 + round_radius * (float) Math.cos(angleRad);
            index++;
            vertices[index] = y5 + round_radius * (float) Math.sin(angleRad);
            index++;
            angle += step;
        }

        vertices[index] = x3;  vertices[index+1] = y3;

        index += 2;
        angle -= step;
        while (angle <= 270) {

            float angleRad = (float) angle * (float) Math.PI / 180;
            vertices[index] = x3 + round_radius * (float) Math.cos(angleRad);
            index++;
            vertices[index] = y3 + round_radius * (float) Math.sin(angleRad);
            index++;
            angle += step;
        }

        vertices[index] = x4;  vertices[index+1] = y4;

        index += 2;
        angle -= step;
        while (angle <= 360) {

            float angleRad = (float) angle * (float) Math.PI / 180;
            vertices[index] = x4 + round_radius * (float) Math.cos(angleRad);
            index++;
            vertices[index] = y4 + round_radius * (float) Math.sin(angleRad);
            index++;
            angle += step;
        }


        prepareBuffer();

    }
}
