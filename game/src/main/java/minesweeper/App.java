package minesweeper;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.VBox;

public class App extends Application {

    private static Stage primaryStage;
    private HexGrid grid;
    private BorderPane rootLayout;
    private StackPane centerpane;

    private int sceneWidth = 1720;
    private int sceneHeight = 720;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        // Main layout
        rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(10));

        // Top-left restart button
        Button restartButton = new Button("Restart Game");
        restartButton.setOnAction(e -> restartGame());
        restartButton.setPrefHeight(40);
        restartButton.setPrefWidth(180);
        restartButton.setStyle("-fx-font-size: 16px;");

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(20, 20, 0, 20)); // top, right, bottom, left
        topBar.getChildren().add(restartButton);

rootLayout.setTop(topBar);


        rootLayout.setTop(topBar);

        // Center grid pane
        centerpane = new StackPane();
        centerpane.setAlignment(Pos.CENTER);
        centerpane.setStyle("-fx-background-color: transparent;");

        rootLayout.setCenter(centerpane);

        createNewGame();

        Scene scene = new Scene(rootLayout, sceneWidth, sceneHeight);
        scene.setFill(Color.rgb(108, 187, 60));

        stage.setScene(scene);
        stage.setTitle("Hex Grid Minesweeper");
        stage.show();
    }

    private void createNewGame() {
        centerpane.getChildren().clear();
        grid = new HexGrid(5, sceneWidth / 2, sceneHeight / 2);
        centerpane.getChildren().add(grid);
    }

    private void restartGame() {
        createNewGame();
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

        Scene scene = new Scene(root, 600, 400);
        defeatStage.setScene(scene);
        defeatStage.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}