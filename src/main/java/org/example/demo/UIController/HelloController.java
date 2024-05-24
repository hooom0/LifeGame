package org.example.demo.UIController;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.layout.*;
import org.example.demo.modelController.modelController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Objects;

public class HelloController {

    Logger logger = LoggerFactory.getLogger(HelloController.class);

    @FXML
    private GridPane placeholderGridPane;

    private modelController modelcontroller = new modelController();



    @FXML
    public void initialize() {
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
        for(int i=0;i<100;i++)
            for(int j=0;j<50;j++){
                Pane pane = new Pane();
                pane.setBackground(Background.EMPTY);
                pane.setStyle("-fx-background-color: #333333;");

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

}