package org.example.demo.modelcontroller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ModelControllerTest {
    private ModelController controller;
    @BeforeEach
    public void setUp() {
        controller = new ModelController();
    }
    @Test
    public void testInitialGridState() {
        int[][] gridData = controller.getGridData();
        for (int i = 0; i < controller.getLie(); i++) {
            for (int j = 0; j < controller.getHang(); j++) {
                assertEquals(0, gridData[i][j]);
            }
        }
    }
    @Test
    public void testSetAndGetGridData() {
        controller.setGridData(1, 1, 1);
        assertEquals(1, controller.getGridData()[1][1]);

        controller.setGridData(1, 1, 1);
        assertEquals(1, controller.getGridData()[1][1]);
    }
    @Test
    public void testReset() {
        controller.reset();
        int[][] gridData = controller.getGridData();
        boolean allZeroOrOne = true;
        for (int i = 0; i < controller.getLie(); i++) {
            for (int j = 0; j < controller.getHang(); j++) {
                if (gridData[i][j] != 0 && gridData[i][j] != 1) {
                    allZeroOrOne = false;
                    break;
                }
            }
        }
        assertTrue(allZeroOrOne);
    }
    @Test
    public void testUpdateGridData() {
        controller.setGridData(1, 0, 1);
        controller.setGridData(1, 1, 1);
        controller.setGridData(1, 2, 1);
        controller.updateGridData();
        int[][] gridData = controller.getGridData();
        assertEquals(1, gridData[0][1]);
        assertEquals(1, gridData[1][1]);
        assertEquals(1, gridData[2][1]);
        assertEquals(0, gridData[1][0]);
        assertEquals(0, gridData[1][2]);
    }
    @Test
    public void testAllClear() {
        controller.setGridData(1, 1, 1);
        controller.setGridData(2, 2, 1);
        controller.allClear();
        int[][] gridData = controller.getGridData();
        for (int i = 0; i < controller.getLie(); i++) {
            for (int j = 0; j < controller.getHang(); j++) {
                assertEquals(0, gridData[i][j]);
            }
        }
    }
    @Test
    public void testGetAdd(){
        controller.setGridData(8,9,1);
        controller.setGridData(9,9,1);
        controller.setGridData(9,10,1);
        controller.setGridData(10,9,1);
        controller.setGridData(10,10,1);
        int add2 = controller.getAdd(9,9);
        int add = controller.getAdd(10, 10);
        assertEquals(3,add);
        assertEquals(4,add2);
    }
}
