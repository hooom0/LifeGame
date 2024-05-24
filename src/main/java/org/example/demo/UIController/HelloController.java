package org.example.demo.UIController;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.example.demo.modelController.modelController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Objects;

public class HelloController extends Thread {

    Logger logger = LoggerFactory.getLogger(HelloController.class);

    @FXML
    private GridPane placeholderGridPane;

    @FXML
    private Button ButtonStart;

    @FXML
    private Button ButtonStop;

    private modelController modelcontroller = new modelController();

    Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> {
                // 更新操作
                modelcontroller.updateGridData();
                updateGridPane();
                logger.info("更新一次");
            })
    );



    @FXML
    public void initialize() {
        timeline.setCycleCount(Timeline.INDEFINITE);
        GridPane gridPane = new GridPane();
        for(int i=0;i<100;i++)
        {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPercentWidth(1);  // 每列占总宽度的 33%
            gridPane.getColumnConstraints().add(colConstraints);
        }
        for(int j=0;j<50;j++)
        {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(2);  // 每行占总高度的 33%
            gridPane.getRowConstraints().add(rowConstraints);

        }
        //modelcontroller.Reset();
        int[][] gridData = modelcontroller.getGridData();
        for(int i=0;i<100;i++)
            for(int j=0;j<50;j++){
                Pane pane = new Pane();
                pane.setBackground(Background.EMPTY);
                if(gridData[i][j]==0)
                    pane.setStyle("-fx-background-color: #333333;");
                else
                    pane.setStyle("-fx-background-color:255,255,102 ");

                pane.setOnMouseClicked(mouseEvent -> {

                    Integer xIndex = (int)((mouseEvent.getSceneX())/16);
                    Integer yIndex = (int)((mouseEvent.getSceneY())/18);

                    modelcontroller.setGridData(xIndex,yIndex);

                    logger.info("点击X像素位置:"+mouseEvent.getSceneX());
                    logger.info("点击Y像素位置:"+mouseEvent.getSceneY());
                    logger.info("点击位置："+xIndex+","+yIndex);

                    if(Objects.equals(pane.getStyle(), "-fx-background-color: #333333;"))
                        pane.setStyle("-fx-background-color:255,255,102 ;");
                    else
                        pane.setStyle("-fx-background-color: #333333;");
                });
                gridPane.add(pane,i,j);
            }

        gridPane.setGridLinesVisible(true);
        placeholderGridPane.getChildren().add(gridPane);
    }


    public void updateGridPane(){
        int[][] gridData = modelcontroller.getGridData();

        GridPane childrens = (GridPane) placeholderGridPane.getChildren().get(0);
        ObservableList<Node> children = childrens.getChildren();
        for(Node node : children){
            if(node instanceof Group)continue;
            int i = GridPane.getColumnIndex(node);
            int j = GridPane.getRowIndex(node);
            Pane pane = (Pane)node;
            if(gridData[i][j]==0)
                pane.setStyle("-fx-background-color: #333333;");
            else
                pane.setStyle("-fx-background-color:255,255,102 ");
        }
    }




    public void StartClick(ActionEvent mouseDragEvent)  {
        ButtonStart.setDisable(true);
        ButtonStop.setDisable(false);
        timeline.play();
    }

    public void StopClick(ActionEvent mouseDragEvent)  {
        ButtonStop.setDisable(true);
        ButtonStart.setDisable(false);
        timeline.stop();
    }

}