package org.example.demo.modelController;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

public class modelController {

    private int[][] gridData = new int[100][50];
    private final int[][] dir = {{0,1},{1,0},{0,-1},{-1,0},{1,1},{-1,-1},{-1,1},{1,-1}};
    public modelController() {

    }

    public int[][] getGridData() {
        return gridData;
    }

    public void setGridData(Integer i,Integer j) {
        gridData[i][j] = gridData[i][j] ^ 1 ;
    }

    public void Reset(){
        for(int i=0;i<100;i++){
            for(int j=0;j<50;j++){
                int randomInt = ThreadLocalRandom.current().nextInt(100);
                gridData[i][j] = randomInt/70;
            }
        }
    }


    public void updateGridData() {
        int [][] gridDataNew = new int[100][50];
        for(int i=0;i<100;i++){
            for(int j=0;j<50;j++){
                if(checkLifeGame(i,j))
                    gridDataNew[i][j] = 1;
                else
                    gridDataNew[i][j] = 0;
            }
        }
        gridData = gridDataNew;
    }

    private boolean checkLifeGame(int x, int y) {

        int add = 0;
        for(int i=0;i<8;i++){
            int nx = x + dir[i][0];
            int ny = y + dir[i][1];
            if(nx<0 || nx>=100 || ny<0 || ny>=50)continue;
            add+= gridData[nx][ny];
        }
        if(gridData[x][y]==0){
            if(add==3)
                return true;
            return false;
        }else{
            if(add<=1)return false;
            else if(add>=4)return false;
            else return true;
        }
    }
}
