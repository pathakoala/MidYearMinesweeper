package minesweeper;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.input.MouseButton;

public class HexTile extends Group {
    private HexGrid grid;

    public int locationMetric1;
    public int locationMetric2;

    public double xCenter;
    public double yCenter;

    public int adjacentMines;

    private Mine m;
    private boolean reveal = false;
    private boolean flagged = false;

    public Polygon hex;
    public Text label;
    private ImageView flagImage;

    public HexTile(double xCenter, double yCenter, double size, HexGrid grid) {
        this.grid = grid;
        this.xCenter = xCenter;
        this.yCenter = yCenter;

        hex = new Polygon();

        for (int i = 0; i < 6; i++) {
            double angle1 = Math.toRadians(60 * i);
            double x = xCenter + size * Math.cos(angle1);
            double y = yCenter + size * Math.sin(angle1);

            hex.getPoints().addAll(x, y);
        }

        hex.setFill(Color.BEIGE);
        hex.setStroke(Color.BLUE);

        label = new Text();
        label.setFont(Font.font(14));
        label.setFill(Color.BLACK);

        label.setX(xCenter - 4);
        label.setY(yCenter + 4);

        getChildren().addAll(hex, label);


        
        Image flagImg = new Image(getClass().getResourceAsStream("/minesweeper/flag.png"));
        flagImage = new ImageView(flagImg);
        flagImage.setFitWidth(20);  // Adjust size as needed
        flagImage.setFitHeight(20);
        flagImage.setLayoutX(xCenter - 10);
        flagImage.setLayoutY(yCenter - 10);
        flagImage.setVisible(false);
        getChildren().add(flagImage);

        // clicking will reveal whether safe or not
        this.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                makeFlag();
            } else if (e.getButton() == MouseButton.PRIMARY) {
                reveal();
            }
        });
    }

    // All of these methods pertain to the function of mines, (grey is safe/ red is
    // "exploding")
    public void reveal() {
        grid.handleFirstClick(this);
        if (reveal || flagged) return;
        reveal = true;

        if (hasMine()) {
            hex.setFill(Color.RED);
            grid.onDefeat();
            return;
        }

        hex.setFill(Color.LIGHTGRAY);

        if (adjacentMines > 0) {
            label.setText("" + adjacentMines);
        } else {
            zeroReveal();
        }
    }

    public void placeMine(Mine m) {
        this.m = m;
    }

    public boolean hasMine() {
        return m != null;
    }

    public boolean isRevealed() {
        return reveal;
    }

    // reveal of zeroes
    private void zeroReveal() {
        for (HexTile aTile : grid.getAdjacent(this)) {
            if (!aTile.isRevealed() && !aTile.hasMine() && !aTile.isFlagged()) {
                aTile.reveal();
            }
        }
    }

    public void makeFlag() {
        if (reveal)
            return;

        flagged = !flagged;
        flagImage.setVisible(flagged);
    }

    public boolean isFlagged() {
        return flagged;
    }
}
