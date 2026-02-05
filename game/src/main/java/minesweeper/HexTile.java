package minesweeper;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HexTile extends Group {
    private HexGrid grid;

    public int locationMetric1;
    public int locationMetric2;

    public double xCenter;
    public double yCenter;

    public int adjacentMines;

    private Mine m;
    private boolean reveal = false;

    public Polygon hex;
    public Text label;

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

        // clicking will reveal whether safe or not
        this.setOnMouseClicked(e -> reveal());
    }

    // All of these methods pertain to the function of mines, (grey is safe/ red is
    // "exploding")
    public void reveal() {
        if (reveal)
            return;
        reveal = true;

        if (hasMine()) {
            hex.setFill(Color.RED);
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
            if (!aTile.isRevealed() && !aTile.hasMine()) {
                aTile.reveal();
            }
        }
    }
}
