package minesweeper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.*;
import javafx.scene.shape.*;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        int sceneWidth = 1720;
        int sceneHeight = 1080;
        HexGrid grid = new HexGrid(5, sceneWidth/2, sceneHeight/2);

        Scene scene = new Scene(grid, sceneWidth,sceneHeight);

        Rectangle border = new Rectangle(sceneWidth*2/3, sceneHeight*2/3);
        border.setX(sceneWidth*1/6);
        border.setY(sceneHeight*1/6);

        border.setArcWidth(90.0);
        border.setArcHeight(60.0);

        border.setFill(null);
        border.setStroke(Color.BLUEVIOLET);
        border.setStrokeWidth(10);


        grid.getChildren().add(border);
        
        scene.setFill(Color.rgb(108,187,60));
        stage.setScene(scene);
        stage.setTitle("Hex Grid");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}