package org.example.demo.modelController;

public class modelController {

    private int[][] gridData = new int[100][50];

    public modelController() {

    }

    public int[][] getGridData() {
        return gridData;
    }

    public void setGridData(Integer i,Integer j) {
        gridData[i][j] = gridData[i][j] ^ 1 ;
    }





}
