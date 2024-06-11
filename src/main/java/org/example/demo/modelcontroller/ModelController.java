package org.example.demo.modelcontroller;


import java.util.Arrays;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ModelController {

    private int hang = 50;
    private int lie = 100;
    private int[][] gridData = new int[lie][hang];
    private final int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}};
    private int[][] gridDataNew = new int[lie][hang];
    private boolean[][] ifChangeOrNot = new boolean[lie][hang];

    public ModelController() {

    }

    public Boolean getChange(int x, int y) {
        if (x < 0 || y < 0 || x >= lie || y >= hang) {
            return false;
        }
        return ifChangeOrNot[x][y];
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

    @SuppressWarnings("checkstyle:Regexp")
    public void updateGridData() {
        if (gridDataNew == null || gridDataNew.length != lie || gridDataNew[0].length != hang) {
            gridDataNew = new int[lie][hang];
            ifChangeOrNot = new boolean[lie][hang];
        }
        for (int i = 0; i < lie; i++) {
            for (int j = 0; j < hang; j++) {
                gridDataNew[i][j] = checkLifeGame(i, j) ? 1 : 0;
                ifChangeOrNot[i][j] = gridData[i][j] != gridDataNew[i][j];
            }
        }
        int[][] temp = gridData;
        gridData = gridDataNew;
        gridDataNew = temp;
    }

    private boolean checkLifeGame(int x, int y) {
        int add = getAdd(x, y);
        if (gridData[x][y] == 0) {
            return (add == 3);
        } else {
            return add > 1 && add < 4;
        }
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
        IntStream.range(0, gridData.length).forEach(i -> Arrays.fill(gridData[i], 0));
    }

    public int getGridData(int i, int j) {
        return gridData[i][j];
    }
}

class Pair<A, B> {
    private A first;
    private B second;

    Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public B getSecond() {
        return second;
    }

    public void setSecond(B second) {
        this.second = second;
    }
}
