package minesweeper;

public class Mine {
    private boolean explode = true;

    public boolean willExplode() {
        return explode;
    }

    public void disarm() {
        explode = false;
    }
}
