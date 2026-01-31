package minesweeper;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class HexTile extends Polygon{
    public int locationMetric1;
    public int locationMetric2;

    private Mine m;
    private boolean reveal = false;

    public HexTile(double xCenter, double yCenter, double size) {
        for(int i = 0; i<6; i++) {
            double angle1 = Math.toRadians(60*i);
            double x = xCenter + size*Math.cos(angle1);
            double y = yCenter + size*Math.sin(angle1);

            getPoints().addAll(x,y);
        }

        this.setFill(Color.BEIGE);
        this.setStroke(Color.BLUE);

        // clicking will reveal whether safe or not
        this.setOnMouseClicked(e -> reveal());
    }
    // All of these methods pertain to the function of mines, (grey is safe/ red is "exploding")
    public void reveal() {
        if (reveal) return;
        reveal = true;

        if (hasMine()) {
            setFill(Color.RED);       
        } else {
            setFill(Color.LIGHTGRAY); 
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
}
