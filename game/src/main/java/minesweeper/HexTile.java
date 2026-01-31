package minesweeper;
import javafx.scene.shape.*;
import javafx.scene.paint.*;

public class HexTile extends Polygon{
    public int locationMetric1;
    public int locationMetric2;

    public HexTile(double xCenter, double yCenter, double size) {
        for(int i = 0; i<6; i++) {
            double angle1 = Math.toRadians(60*i);
            double x = xCenter + size*Math.cos(angle1);
            double y = yCenter + size*Math.sin(angle1);

            getPoints().addAll(x,y);
        }

        this.setFill(Color.BEIGE);
        this.setStroke(Color.BLUE);
    }
}
