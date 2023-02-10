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
    HashMap<Coordinate, T> enemyHits;
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
        this.enemyHits = new HashMap<>();
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
        // for (Ship<T> s: myShips) {
        //   if (s.occupiesCoordinates(where)){
        //     return s.getDisplayInfoAt(where, isSelf);
        //   }
        // }
        // if (!isSelf && enemyMisses.contains(where)) {
        //   return missInfo;
        // }
        if (isSelf) {
          for (Ship<T> s: myShips) {
            if (s.occupiesCoordinates(where)){
              return s.getDisplayInfoAt(where, isSelf);
            }
          }
        } else {
          if (enemyMisses.contains(where)) {
            return missInfo;
          } else if (enemyHits.containsKey(where)) {
            return enemyHits.get(where);
          } else {
            return null;
          }
        }
        return null;
    }
    
      public T whatIsAtForEnemy(Coordinate where) {
        return whatIsAt(where, false);
      }

      //
    public Ship<T> fireAt(Coordinate c) {
      for (Ship<T> s : this.myShips) {
        if (s.occupiesCoordinates(c)) {
          s.recordHitAt(c);
          if (enemyMisses.contains(c)) {
            enemyMisses.remove(c);
          }
          enemyHits.put(c, s.getType(c));
          return s;
        }
      }
      enemyMisses.add(c);
      if (enemyHits.containsKey(c)) {
        enemyHits.remove(c);
      }
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

    public boolean checkIfInBound(int x, int y) {
      if (x >= 0 && x < this.height && y >= 0 && y < this.width) {
        return true;
      } else {
        return false;
      }
    }

    /* 
     * return hashmap of all coordinates that occupied by a ship
     */
    public HashMap<String, Integer> findShipsBySonar(Coordinate coor) {
      HashMap<String, Integer> map = new HashMap<>();
      map.put("Submarine", 0);
      map.put("Destroyer", 0);
      map.put("Battleship", 0);
      map.put("Carrier", 0);
      int row = coor.getRow();
      int col = coor.getColumn();

      int len = 4;
      int scope = 7;
      for(int i = 0; i < len; ++i) {
        for(int j = 0; j < 2 * i + 1; ++j){
          int r = row + i - (len - 1);
          int c = col + j - i;
          if (checkIfInBound(r, c)) {
            Ship<T> ship = getShip(new Coordinate(r, c));
            if (ship != null) {
              String name = ship.getName();
              map.put(name, map.get(name) + 1);
            }
          }
        }
      }

      for(int i = len; i < scope; ++i) {
        for(int j = 0; j < (scope - 1 - i) * 2 + 1; ++j) {
          int r = row + i - (len - 1);
          int c = j + col + i + 1 - scope;
          if (checkIfInBound(r, c)) {
            Ship<T> ship = getShip(new Coordinate(r, c));
            if (ship != null) {
              String name = ship.getName();
              map.put(name, map.get(name) + 1);
            }
        }
      }
    }
    return map;
  }
}
