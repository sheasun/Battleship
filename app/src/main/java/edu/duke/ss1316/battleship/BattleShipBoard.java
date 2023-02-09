package edu.duke.ss1316.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

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

    public Ship<T> getShip(Coordinate c) {
      for (Ship<T> ship : myShips) {
        if (ship.occupiesCoordinates(c)) {
          return ship;
        }
      }
      return null;
    }

    public void removeShip(Ship<Character> ship) {
      this.myShips.remove(ship);
    }

    private boolean checkIfInBound(int x, int y) {
      if (x >= 0 && x < this.height && y >= 0 && y < this.width) {
        return true;
      } else {
        return false;
      }
    }
    public HashMap<String, Integer> findShipsBySonar(Coordinate coor) {
      HashMap<String, Integer> map = new HashMap<>();
      map.put("Submarine", 0);
      map.put("Destroyer", 0);
      map.put("Battleship", 0);
      map.put("Carrier", 0);
      int row = coor.getRow();
      int col = coor.getColumn();
      // Ship<T> ship = getShip(new Coordinate(r, c));
      // if (ship != null) {
      //   String name = ship.getName();
      //   map.put(name, map.get(name) + 1);
      // }
      int startRow = row - 3;
      int startCol = col - 3;
      for (int r = startRow; r <= startRow + 3; ++r) {
        int num = 2 * (r - startRow) + 1;
        int c = startCol + 3 - (r - startRow);
        while (num > 0) {
          // must check it, else it would throw argument
          if (checkIfInBound(r, c)) {
            Ship<T> ship = getShip(new Coordinate(r, c));
            if (ship != null) {
              String name = ship.getName();
              map.put(name, map.get(name) + 1);
            }
          }
          --num;
          ++c;
        }
      }
      for (int r = row + 3; r >= row + 1; r--) {
        int num = 2 * (3 - (r - row)) + 1;
        int c = startCol + r - row;
        while (num > 0) {
          if (checkIfInBound(r, c)) {
            Ship<T> ship = getShip(new Coordinate(r, c));
            if (ship != null) {
              String name = ship.getName();
              map.put(name, map.get(name) + 1);
          }
          --num;
          ++c;
        }
      }
    }
    return map;
  }
}
