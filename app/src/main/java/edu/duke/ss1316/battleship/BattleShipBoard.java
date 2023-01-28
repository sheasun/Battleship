package edu.duke.ss1316.battleship;

public class BattleShipBoard implements Board {
    private final int width;
    public int getWidth() {
        return width;
    }
    private final int height;
    public int getHeight() {
        return height;
    }
    public BattleShipBoard(int w, int h) {
        if (w <= 0) {
            throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
          }
          if (h <= 0) {
            throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
          }
        this.width = w;
        this.height = h;
    }
}
