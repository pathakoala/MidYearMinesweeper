package minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import javafx.scene.layout.Pane;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class HexGrid extends Pane {
    private static final double size = 25;
    private List<HexTile> tiles = new ArrayList<>();
    private boolean firstClick = true;
    private App app;
    private boolean gameEnded = false;

    public HexGrid(int radius, double xCenter, double yCenter, App app) {
        this.app = app;

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
                if (Math.abs(tile.xCenter-sixCords[i][0]) < 1 && Math.abs(tile.yCenter - sixCords[i][1]) < 1){
                    if (tile.hasMine()) {
                        ans.add(tile);
                    }
                }
            }
        }
        return ans.size();
    }

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
        if (gameEnded) return;
        gameEnded = true;

        // shuffle mine tiles in order to reveal them randomly (like the google one)
        List<HexTile> mineTiles = new ArrayList<>();
        for (HexTile t : tiles) {
            if (t.hasMine()) {
                mineTiles.add(t);
            }
        }

        Collections.shuffle(mineTiles);

        // Animate reveal of each mine with delay
        for (int i = 0; i < mineTiles.size(); i++) {
            HexTile mineTile = mineTiles.get(i);
            PauseTransition delay = new PauseTransition(Duration.millis(100 * (i + 1)));
            delay.setOnFinished(e -> {
                mineTile.revealMine();
            });
            delay.play();
        }

        
        double totalAnimationTime = mineTiles.size() * 100 + 300; // Add buffer
        PauseTransition showOverlay = new PauseTransition(Duration.millis(totalAnimationTime));
        showOverlay.setOnFinished(e -> {
            app.showGameOverOverlay(false);
        });
        showOverlay.play();
    }

    public void checkWinCondition() {
        if (gameEnded) return;

        // Count the safe (non-flagged) tiles. 
        int safeTiles = 0;
        for (HexTile t : tiles) {
            if (!t.isRevealed() && !t.hasMine() && !t.isFlagged()) {
                safeTiles++;
            }
        }

        // If all safe tiles are revealed, the user wins!
        if (safeTiles == 0) {
            onWin();
        }
    }

    public void onWin() {
        if (gameEnded) return;
        gameEnded = true;

        // animate reveal of unrevealed mines (the ones with flags)
        List<HexTile> unrevealedMines = new ArrayList<>();
        for (HexTile t : tiles) {
            if (t.hasMine() && !t.isRevealed()) {
                unrevealedMines.add(t);
            }
        }

        Collections.shuffle(unrevealedMines);

        // Animated reveal of each mine with delay
        for (int i = 0; i < unrevealedMines.size(); i++) {
            HexTile mineTile = unrevealedMines.get(i);
            PauseTransition delay = new PauseTransition(Duration.millis(100 * (i + 1)));
            delay.setOnFinished(e -> { mineTile.revealMine(); });
            delay.play();
        }

        // Show win text after all mines are revealed
        double totalAnimationTime = unrevealedMines.size() * 100 + 300;
        PauseTransition showOverlay = new PauseTransition(Duration.millis(totalAnimationTime));
        showOverlay.setOnFinished(e -> {
            app.showGameOverOverlay(true);
        });
        showOverlay.play();
    }
}