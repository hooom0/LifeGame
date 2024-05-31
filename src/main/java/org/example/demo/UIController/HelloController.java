package org.example.demo.UIController;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import org.example.demo.modelController.modelController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Objects;

import static java.lang.Integer.parseInt;

public class HelloController{
    @FXML
    public Circle ifShiftOrNot;
    public TextField widthText;
    public TextField highText;
    public Button ButtonChange;
    Logger logger = LoggerFactory.getLogger(HelloController.class);
    @FXML
    public Button ButtonClear;

    @FXML
    public VBox VBox;

    @FXML
    public Button ButtonRunOnce;

    @FXML
    GridPane placeholderGridPane;

    @FXML
    public Button ButtonStart;

    @FXML
    public Button ButtonStop;

    @FXML
    public Button ButtonRefresh;

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
        placeholderGridPane.getChildren().add(0,initGridPane());

        widthText.setText(String.valueOf(modelcontroller.getLie()));
        highText.setText(String.valueOf(modelcontroller.getHang()));
    }

    private GridPane initGridPane(){
        modelcontroller.Reset();
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
                    pane.setStyle("-fx-background-color:#CCFFFF ");

                pane.setOnMouseClicked(mouseEvent -> {

                    FindIndexAndChange(mouseEvent,0);
                    if(Objects.equals(pane.getStyle(), "-fx-background-color: #333333;"))
                        pane.setStyle("-fx-background-color:#CCFFFF ;");
                    else
                        pane.setStyle("-fx-background-color: #333333;");
                });

                pane.setOnMouseMoved(mouseEvent -> {
                    if(mouseEvent.isShiftDown()){
                        FindIndexAndChange(mouseEvent,1);
                        pane.setStyle("-fx-background-color:#CCFFFF ;");
                    }
                });
                gridPane.add(pane,i,j);
            }
        gridPane.setGridLinesVisible(true);
        return gridPane;
    }

    private void FindIndexAndChange(MouseEvent mouseEvent,int flag) {
        Integer xIndex = (int)((mouseEvent.getSceneX())/(placeholderGridPane.getWidth()/modelcontroller.getLie()));
        Integer yIndex = (int)((mouseEvent.getSceneY())/(placeholderGridPane.getHeight()/modelcontroller.getHang()));

        modelcontroller.setGridData(xIndex,yIndex,flag);
        if(flag == 0){
            logger.info("点击X像素位置:"+mouseEvent.getSceneX()+":"+placeholderGridPane.getWidth());
            logger.info("点击Y像素位置:"+mouseEvent.getSceneY()+":"+placeholderGridPane.getHeight());
            logger.info("点击位置："+xIndex+","+yIndex);
        }else{
            logger.info("画线位置："+xIndex+","+yIndex);
        }

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
                pane.setStyle("-fx-background-color: #333333");
            else
                pane.setStyle("-fx-background-color: #CCFFFF");
        }
    }






    public void StartClick(ActionEvent mouseDragEvent)  {
        ButtonStart.setDisable(true);
        ButtonStop.setDisable(false);
        ButtonRefresh.setDisable(true);
        ButtonRunOnce.setDisable(true);
        ButtonClear.setDisable(true);
        timeline.play();
    }

    public void StopClick(ActionEvent mouseDragEvent)  {
        ButtonStop.setDisable(true);
        ButtonStart.setDisable(false);
        ButtonRefresh.setDisable(false);
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

    public void ShiftPush(KeyEvent keyEvent){
        if(keyEvent.isShiftDown()){
            ifShiftOrNot.setFill(Paint.valueOf("BLACK"));
        }
    }

    public void ShiftRelease(KeyEvent keyEvent){
        if(!keyEvent.isShiftDown()){
            ifShiftOrNot.setFill(Paint.valueOf("WHITE"));
        }
    }

    public void ClickChange(){
        logger.info(String.valueOf(parseInt(highText.getText())));
        modelcontroller.setHang(parseInt(highText.getText()));
        modelcontroller.setLie(parseInt(widthText.getText()));

        placeholderGridPane.getChildren().clear();
        placeholderGridPane.getChildren().add(0,initGridPane());
        updateGridPane();
    }

}