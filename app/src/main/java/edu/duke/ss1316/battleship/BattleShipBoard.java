package edu.duke.ss1316.battleship;

import java.util.ArrayList;
import java.util.HashSet;

public class BattleShipBoard<T> implements Board<T> {
    private final int width;
    private final int height;

    private final ArrayList<Ship<T>> myShips;
    // private HashMap<Coordinate, Ship<T>> map;

    private final PlacementRuleChecker<T> placementChecker;
    HashSet<Coordinate> enemyMisses;
    final T missInfo;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BattleShipBoard(int w, int h, PlacementRuleChecker<T> placementChecker, T missInfo) {
        if (w <= 0) {
            throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
          }
          if (h <= 0) {
            throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
          }
        this.width = w;
        this.height = h;
        this.myShips = new ArrayList<>();
        this.placementChecker = placementChecker;
        this.enemyMisses = new HashSet<>();
        this.missInfo = missInfo;
    }

    public BattleShipBoard(int width, int height, T missInfo) {
      this(width, height, new InBoundRuleChecker<T>(new NoCollisionRuleChecker<>(null)), missInfo);
    }
  

    public String tryAddShip(Ship<T> toAdd) {
      String ans = this.placementChecker.checkPlacement(toAdd, this);
        if (ans == null) {
          myShips.add(toAdd);
        }
        return ans;
    }
    public T whatIsAtForSelf(Coordinate where) {
      return whatIsAt(where, true);
    }

    protected T whatIsAt(Coordinate where, boolean isSelf) {
        for (Ship<T> s: myShips) {
          if (s.occupiesCoordinates(where)){
            return s.getDisplayInfoAt(where, isSelf);
          }
        }
        if (!isSelf && enemyMisses.contains(where)) {
          return missInfo;
        }
      return null;
    }
    
      public T whatIsAtForEnemy(Coordinate where) {
        return whatIsAt(where, false);
      }
    
    // private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected) {

    // }

    public Ship<T> fireAt(Coordinate c) {
      for (Ship<T> s : this.myShips) {
        if (s.occupiesCoordinates(c)) {
          s.recordHitAt(c);
          return s;
        }
      }
      this.enemyMisses.add(c);
      return null;
    }

    public ArrayList<Ship<T>> getShips() {
      return this.myShips;
    }
}
