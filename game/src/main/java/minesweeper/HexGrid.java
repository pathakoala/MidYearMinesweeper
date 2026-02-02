package minesweeper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.layout.Pane;

public class HexGrid extends Pane {
    private static final double size = 25;
    private List<HexTile> tiles = new ArrayList<>();

    public HexGrid(int radius, double xCenter, double yCenter) {
        HexTile tile = new HexTile(xCenter, yCenter, size);
        tiles.add(tile);
        getChildren().add(tile);

        for (int i = 0; i <= radius; i++) {
            double X = xCenter;
            double Y = yCenter + Math.sqrt(3) * i * size;
            double angle = 330;

            int sideNum = 1;
            // the following loop essentially constrcuts a hexagonal grid by creating rings
            // of hexagons around a central point.
            while (sideNum <= 6 * i) {
                X = X + Math.sqrt(3) * size * Math.cos(Math.toRadians(angle));
                Y = Y + Math.sqrt(3) * size * Math.sin(Math.toRadians(angle));

                HexTile tiler = new HexTile(X, Y, size);
                tiles.add(tiler);
                getChildren().add(tiler);

                if (sideNum % i == 0) {
                    angle -= 60;
                    // decreases angle appropriately when we have reached i "hextiles" on the side
                    // of the larger tiles
                }

                sideNum++;
            }
        }

        Collections.shuffle(tiles);
        int mineCount = 10;
        for (int i = 0; i < mineCount && i < tiles.size(); i++) {
            tiles.get(i).placeMine(new Mine());
        } // this can be changed later depending on difficulty
    }
    // random addition of mines
    
}
