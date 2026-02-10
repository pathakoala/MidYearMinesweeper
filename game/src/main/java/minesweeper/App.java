package minesweeper;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


/**
 * JavaFX App
 */
public class App extends Application {
    private static Stage primaryStage;
    private HexGrid grid;
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        int sceneWidth = 1720;
        int sceneHeight = 720;
        HexGrid grid = new HexGrid(5, sceneWidth/2, sceneHeight/2);

        Scene scene = new Scene(grid, sceneWidth,sceneHeight);

        Rectangle border = new Rectangle(sceneWidth*2/3, sceneHeight*3/4);
        border.setX(sceneWidth*1/6);
        border.setY(sceneHeight*1/8);

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

    public static void showDefeatScreen() {
        Stage defeatStage = new Stage();
        defeatStage.initModality(Modality.APPLICATION_MODAL);
        defeatStage.initOwner(primaryStage);
        defeatStage.setTitle("Game Over");
        
        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 50; -fx-background-color: #2a2a2a;");
        
        Label defeatLabel = new Label("You Lost!");
        defeatLabel.setFont(Font.font("Arial", FontWeight.BOLD, 96));
        defeatLabel.setTextFill(Color.RED);

        root.getChildren().add(defeatLabel);

        Scene scene = new Scene(root, 600,400);
        defeatStage.setScene(scene);
        defeatStage.showAndWait();
    }
    public static void main(String[] args) {
        launch();
    }

}