package minesweeper;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;

public class HexGrid extends Pane {
    private static final double size = 25;
    private List<HexTile> tiles = new ArrayList<>();
    private boolean firstClick = true;

    public HexGrid(int radius, double xCenter, double yCenter) {
        HexTile tile = new HexTile(xCenter, yCenter, size, this);
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

                HexTile tiler = new HexTile(X, Y, size, this);

                double rand = Math.random() * 1000;
                if (rand < 330) {
                    tiler.placeMine(new Mine());
                }

                if (sideNum % i == 0) {
                    angle -= 60;
                    // decreases angle appropriately when we have reached i "hextiles" on the side
                    // of the larger tiles
                }

                sideNum++;
                tiles.add(tiler);
                getChildren().add(tiler);
            }
        }

        for (HexTile tile1 : tiles) {
            tile1.adjacentMines = hexAdjacents(tile1);
        }
    }

    public int hexAdjacents(HexTile tile1) {
        ArrayList<HexTile> ans = new ArrayList<HexTile>();
        double[][] sixCords = new double[6][2];
        int degrees = 30;
        for (int i = 0; i < 6; i++) {
            sixCords[i][0] = tile1.xCenter + Math.sqrt(3) * size * Math.cos(Math.toRadians(degrees));
            sixCords[i][1] = tile1.yCenter + Math.sqrt(3) * size * Math.sin(Math.toRadians(degrees));
            degrees += 60;
        }

        for (HexTile tile : tiles) {
            for (int i = 0; i < 6; i++) {
                if ((int) tile.xCenter == (int) sixCords[i][0] && (int) tile.yCenter == (int) sixCords[i][1]) {
                    if (tile.hasMine()) {
                        ans.add(tile);
                    }
                }
            }
        }
        return ans.size();
    }
    // this method stores all adjacent tiles
    public List<HexTile> getAdjacent(HexTile tile1) {
        List<HexTile> touching = new ArrayList<>();

        double[][] sixCords = new double[6][2];
        int degrees = 30;

        for (int i = 0; i < 6; i++) {
            sixCords[i][0] = tile1.xCenter + Math.sqrt(3) * size * Math.cos(Math.toRadians(degrees));
            sixCords[i][1] = tile1.yCenter + Math.sqrt(3) * size * Math.sin(Math.toRadians(degrees));
            degrees += 60;
        }

        for (HexTile tile : tiles) {
            for (int i = 0; i < 6; i++) {
                if (Math.abs(tile.xCenter - sixCords[i][0]) < 1.0 &&
                        Math.abs(tile.yCenter - sixCords[i][1]) < 1.0) {
                    touching.add(tile);
                }
            }
        }

        return touching;
    }

    public void handleFirstClick(HexTile clicked) {
        if (!firstClick)
            return;

        firstClick = false;

        // remove all existing mines
        for (HexTile t : tiles) {
            t.placeMine(null);
        }

        // replace mines
        for (HexTile t : tiles) {
            if (t == clicked)
                continue;
            if (getAdjacent(clicked).contains(t))
                continue;

            double rand = Math.random() * 1000;
            if (rand < 330) {
                t.placeMine(new Mine());
            }
        }

        // recompute adjacent mine counts
        for (HexTile t : tiles) {
            t.adjacentMines = hexAdjacents(t);
        }
    }

    public void onDefeat() {
        App.showDefeatScreen();
    }

}
