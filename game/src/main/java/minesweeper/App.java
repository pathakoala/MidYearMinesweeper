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
    private StackPane gameOverlay;

    private int sceneWidth = 1720;
    private int sceneHeight = 720;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        // Main layout
        rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(10));

        // restart button
        Button restartButton = new Button("Restart Game");
        restartButton.setOnAction(e -> restartGame());
        restartButton.setPrefHeight(40);
        restartButton.setPrefWidth(180);
        restartButton.setStyle("-fx-font-size: 16px;");

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(20, 20, 0, 20)); 
        topBar.getChildren().add(restartButton);

        rootLayout.setTop(topBar);

        // Center grid pane with overlay
        centerpane = new StackPane();
        centerpane.setAlignment(Pos.CENTER);
        centerpane.setStyle("-fx-background-color: transparent;");

        gameOverlay = new StackPane();
        gameOverlay.setAlignment(Pos.CENTER);
        gameOverlay.setStyle("-fx-background-color: transparent;");
        gameOverlay.setVisible(false);

        centerpane.getChildren().add(gameOverlay); // Add overlay on top

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
        grid = new HexGrid(2, sceneWidth / 2+100, sceneHeight /2 +50, this);
        centerpane.getChildren().add(grid);

        //re-add the overlay
        gameOverlay = new StackPane();
        gameOverlay.setAlignment(Pos.CENTER);
        gameOverlay.setStyle("-fx-background-color: transparent;");
        gameOverlay.setVisible(false);
        centerpane.getChildren().add(gameOverlay);
    }

    private void restartGame() {
        createNewGame();
    }

    public void showGameOverOverlay(boolean won) {
        gameOverlay.getChildren().clear();

        VBox overlayContent = new VBox(20);
        overlayContent.setAlignment(Pos.CENTER_RIGHT);
        overlayContent.setStyle("-fx-padding: 50;");

        Label messageLabel = new Label(won ? "You Won!" : "Game Over");
        messageLabel.setFont(Font.font("Arial", FontWeight.BOLD, 72));
        messageLabel.setTextFill(won ? Color.GREEN : Color.RED);

        Label subLabel = new Label(won ? "Congratulations!" : "Better luck next time!");
        subLabel.setFont(Font.font("Arial", 24));
        subLabel.setTextFill(Color.BLACK);

        overlayContent.getChildren().addAll(messageLabel, subLabel);

        gameOverlay.getChildren().add(overlayContent);
        gameOverlay.setVisible(true);
    }

    public static void main(String[] args) {
        launch();
    }
}