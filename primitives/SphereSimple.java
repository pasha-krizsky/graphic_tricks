package com.gamedev.dreamteam.graphicTricks.primitives;

public class SphereSimple extends Sphere {

    private int amountOfPoints;

    public SphereSimple(float center_x, float center_y, float center_z, float radius) {

        // 6 + 37 * 3 * 35
        amountOfPoints = 1297;
        int ALL_COORDS = amountOfPoints * 3;

        vertices = new float[ALL_COORDS];
        vertices[0] = center_x;
        vertices[1] = center_y + radius;
        vertices[2] = center_z;

        int step = 10;
        int index = 3;

        // 35 окружностей
        for (int teta = 10; teta <= 350; teta+=step) {

            float tetaRad = (float) teta * (float) Math.PI / 180;
            float sinTeta = (float) Math.sin(tetaRad);
            float cosTeta = (float) Math.cos(tetaRad);

            // 0 10 20 30 40 50 60 70 80 90 100 110 120 130 140 150 160 170 180 190 200 210 220 230
            // 240 250 260 270 280 290 300 310 320 330 340 350 360
            // 37 точек
            for (int phi = 0; phi <= 360; phi+=step) {

                float phiRad = (float) phi * (float) Math.PI / 180;
                vertices[index] = center_x + radius * (float) Math.sin(phiRad) * sinTeta;
                index++;
                vertices[index] = center_y + radius * cosTeta;
                index++;
                vertices[index] = center_z + radius * (float) Math.cos(phiRad) * sinTeta;
                index++;
            }
        }

        vertices[index] = center_x;
        index++;
        vertices[index] = center_y - radius;
        index++;
        vertices[index] = center_z;

        prepareBuffer();

    }

    public int getAmountOfPoints() {
        return amountOfPoints;
    }


}
