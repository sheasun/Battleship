package edu.duke.ss1316.battleship;

public class BattleShipBoard {
    private final int width;
    public int getWidth() {
        return width;
    }
    private final int height;
    public int getHeight() {
        return height;
    }
    public BattleShipBoard(int w, int h) {
        this.width = w;
        this.height = h;
    }
}
