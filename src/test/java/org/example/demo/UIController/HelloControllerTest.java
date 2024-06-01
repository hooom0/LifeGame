package org.example.demo.UIController;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class HelloControllerTest extends ApplicationTest {

    private HelloController controller = new HelloController();
    private FxRobot robot;

    Logger logger = LoggerFactory.getLogger("");

    @Override
    public void start(Stage stage) throws Exception {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(contextClassLoader);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        controller = loader.getController();
        robot = new FxRobot();
        logger.info("url:{}",loader);
        stage.show();
    }

    @BeforeEach
    public void setUp() throws Exception {
        FxToolkit.registerPrimaryStage();
    }

    @Test
    public void testInitialize() throws Exception {
        assertEquals("100", controller.widthText.getText());
        assertEquals("50", controller.highText.getText());
    }

    @Test
    public void testStartAndStopButtons() {
        Button buttonStart = controller.ButtonStart;
        Button buttonStop = controller.ButtonStop;

        robot.clickOn(buttonStart);
        assertTrue(buttonStart.isDisabled());
        robot.clickOn(buttonStop);
        assertTrue(buttonStop.isDisabled());
    }

    @Test
    public void testButtonRunOnce() {
        Button buttonRunOnce = controller.ButtonRunOnce;
        // Click Run Once
        robot.clickOn(buttonRunOnce);
        // Validate some state changes
    }

    @Test
    public void testButtonClear() {
        Button buttonClear = controller.ButtonClear;
        GridPane gridPane = (GridPane) controller.placeholderGridPane.getChildren().get(0);

        // Click Clear
        robot.clickOn(buttonClear);

        // Validate grid is cleared
        gridPane.getChildren().forEach(node -> {
            if(node instanceof Pane){
                assertEquals("-fx-background-color: #333333", node.getStyle());
            }
        });
    }

    @Test
    public void testShiftPushAndRelease() {
        Circle ifShiftOrNot = controller.ifShiftOrNot;

        // Simulate shift key press
        robot.press(KeyCode.SHIFT);
        assertEquals(Paint.valueOf("BLACK").toString(), ifShiftOrNot.getFill().toString());

        // Simulate shift key release
        robot.release(KeyCode.SHIFT);
        assertEquals(Paint.valueOf("WHITE").toString(), ifShiftOrNot.getFill().toString());
    }
}
