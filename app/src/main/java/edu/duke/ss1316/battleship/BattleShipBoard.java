package edu.duke.ss1316.battleship;

import java.util.ArrayList;
import java.util.HashMap;

public class BattleShipBoard<T> implements Board<T> {
    private final int width;
    private final int height;

    private final ArrayList<Ship<T>> myShips;
    private HashMap<Coordinate, Ship<T>> map;

    public int getWidth() {
        return width;
    }

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
        this.myShips = new ArrayList<>();
    }

    public boolean tryAddShip(Ship<T> toAdd) {
        this.myShips.add(toAdd);
        return true;
    }
    public T whatIsAt(Coordinate where) {
        for (Ship<T> s: myShips) {
          if (s.occupiesCoordinates(where)){
            return s.getDisplayInfoAt(where);
          }
        }
        return null;
      }

    // private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected) {

    // }
}
