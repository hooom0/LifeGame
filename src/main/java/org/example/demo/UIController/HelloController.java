package org.example.demo.UIController;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.example.demo.modelController.modelController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Objects;

public class HelloController extends Thread {
    Logger logger = LoggerFactory.getLogger(HelloController.class);
    @FXML
    public Button ButtonClear;

    @FXML
    public VBox VBox;

    @FXML
    public Button ButtonRunOnce;

    @FXML
    private GridPane placeholderGridPane;

    @FXML
    private Button ButtonStart;

    @FXML
    private Button ButtonStop;
    
    @FXML
    private Button ButtonRefresh;

    private modelController modelcontroller = new modelController();

    Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0.5), event -> {
                // 更新操作
                modelcontroller.updateGridData();
                updateGridPane();
                logger.info("更新一次");
            })
    );



    @FXML
    public void initialize() {
        timeline.setCycleCount(Timeline.INDEFINITE);
        modelcontroller.Reset();
        placeholderGridPane.getChildren().add(0,initGridPane());
    }

    private GridPane initGridPane(){
        GridPane gridPane = new GridPane();
        for(int i=0;i< modelcontroller.getLie();i++)//lie
        {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPercentWidth((double) 100 /modelcontroller.getLie());  // 每列占总宽度的 33%
            gridPane.getColumnConstraints().add(colConstraints);
        }
        for(int j=0;j< modelcontroller.getHang();j++)//hang
        {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight((double) 100 /modelcontroller.getHang());  // 每行占总高度的 33%
            gridPane.getRowConstraints().add(rowConstraints);
        }
        int[][] gridData = modelcontroller.getGridData();
        for(int i=0;i<modelcontroller.getLie();i++)
            for(int j=0;j< modelcontroller.getHang();j++){
                Pane pane = new Pane();
                pane.setBackground(Background.EMPTY);
                if(gridData[i][j]==0)
                    pane.setStyle("-fx-background-color: #333333;");
                else
                    pane.setStyle("-fx-background-color:255,255,102 ");

                pane.setOnMouseClicked(mouseEvent -> {

                    FindIndexAndChange(mouseEvent);
                    if(Objects.equals(pane.getStyle(), "-fx-background-color: #333333;"))
                        pane.setStyle("-fx-background-color:255,255,102 ;");
                    else
                        pane.setStyle("-fx-background-color: #333333;");
                });

                pane.setOnMouseMoved(mouseEvent -> {
                    if(mouseEvent.isShiftDown()){
                        FindIndexAndChange(mouseEvent);
                        pane.setStyle("-fx-background-color:255,255,102 ;");
                    }
                });
                gridPane.add(pane,i,j);
            }
        gridPane.setGridLinesVisible(true);
        return gridPane;
    }

    private void FindIndexAndChange(MouseEvent mouseEvent) {
        Integer xIndex = (int)((mouseEvent.getSceneX())/(placeholderGridPane.getWidth()/modelcontroller.getLie()));
        Integer yIndex = (int)((mouseEvent.getSceneY())/(placeholderGridPane.getHeight()/modelcontroller.getHang()));

        modelcontroller.setGridData(xIndex,yIndex);

        logger.info("点击X像素位置:"+mouseEvent.getSceneX()+":"+placeholderGridPane.getWidth());
        logger.info("点击Y像素位置:"+mouseEvent.getSceneY()+":"+placeholderGridPane.getHeight());
        logger.info("点击位置："+xIndex+","+yIndex);
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
        ButtonRefresh.setDisable(false);
        ButtonRunOnce.setDisable(true);
        ButtonClear.setDisable(true);
        timeline.play();
    }

    public void StopClick(ActionEvent mouseDragEvent)  {
        ButtonStop.setDisable(true);
        ButtonStart.setDisable(false);
        ButtonRefresh.setDisable(true);
        ButtonRunOnce.setDisable(false);
        ButtonClear.setDisable(false);
        timeline.stop();
    }

    public void runOnceClick(ActionEvent mouseDragEvent) {
        Timeline timeline1 = new Timeline(
                new KeyFrame(Duration.seconds(0.001), event -> {
                    modelcontroller.updateGridData();
                    updateGridPane();
                    logger.info("更新一次");
                })
        );
        timeline1.play();
    }
    public void RefreshClick(ActionEvent actionEvent) {
        modelcontroller.Reset();
        updateGridPane();
        logger.info("刷新初始界面");
    }
    public void ClearClick(ActionEvent actionEvent) {

        logger.info("清零");
        modelcontroller.allClear();
        updateGridPane();
    }


}
