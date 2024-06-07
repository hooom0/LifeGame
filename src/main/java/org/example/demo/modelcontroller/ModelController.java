package org.example.demo.modelcontroller;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ModelController {

    private int hang = 50;
    private int lie = 100;
    private int[][] gridData = new int[lie][hang];
    private final int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}};

    public ModelController() {

    }

    public void setHang(int hang) {
        this.hang = hang;
    }

    public void setLie(int lie) {
        this.lie = lie;
    }

    public int getHang() {
        return hang;
    }

    public int getLie() {
        return lie;
    }

    public int[][] getGridData() {
        return gridData;
    }

    public void setGridData(Integer i, Integer j, int flag) {
        if (flag == 1) {
            gridData[i][j] = 0;
        }
        gridData[i][j] = gridData[i][j] ^ 1;
    }

    public void reset() {
        gridData = new int[lie][hang];
        for (int i = 0; i < lie; i++) {
            for (int j = 0; j < hang; j++) {
                int randomInt = ThreadLocalRandom.current().nextInt(100);
                gridData[i][j] = randomInt / 85;
            }
        }
    }

    public void updateGridData() {
        int[][] gridDataNew = new int[lie][hang];
        for (int i = 0; i < lie; i++) {
            for (int j = 0; j < hang; j++) {
                if (checkLifeGame(i, j)) {
                    gridDataNew[i][j] = 1;
                } else {
                    gridDataNew[i][j] = 0;
                }
            }
        }
        gridData = gridDataNew;
    }

    private boolean checkLifeGame(int x, int y) {
        int add = getAdd(x, y);
        boolean result;
        if (gridData[x][y] == 0) {
            result = (add == 3);
        } else {
            result = add > 1 && add < 4;
        }
        return result;
    }


    public int getAdd(int x, int y) {
        int add = 0;
        for (int i = 0; i < 8; i++) {
            int nx = (x + dir[i][0] + lie) % lie;
            int ny = (y + dir[i][1] + hang) % hang;
            add += gridData[nx][ny];
        }
        return add;
    }

    public void allClear() {
        IntStream.range(0, gridData.length).forEach(i -> {
            Arrays.fill(gridData[i], 0);
        });
    }
}
