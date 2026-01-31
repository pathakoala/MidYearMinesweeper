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
        HexGrid grid = new HexGrid(3, 300, 300);

        Scene scene = new Scene(grid, 600, 600);

        Rectangle border = new Rectangle();
        border.setWidth(600);
        border.setHeight(600);
        border.setFill(null);
        border.setStroke(Color.RED);
        border.setStrokeWidth(2);

        grid.getChildren().add(border);
        
        stage.setScene(scene);
        stage.setTitle("Hex Grid");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}