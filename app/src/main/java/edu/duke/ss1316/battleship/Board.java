package edu.duke.ss1316.battleship;

import java.util.ArrayList;
import java.util.HashMap;

public interface Board<T> {
  public int getWidth();

  public int getHeight();

  public String tryAddShip(Ship<T> toAdd);

  public T whatIsAtForSelf(Coordinate where);

  public T whatIsAtForEnemy(Coordinate where);

  public Ship<T> fireAt(Coordinate c);

  public ArrayList<Ship<T>> getShips();

  public Ship<T> getShip(Coordinate c);

  public void removeShip(Ship<Character> ship);

  public HashMap<String, Integer> findShipsBySonar(Coordinate c);

  public boolean checkIfInBound(int x, int y);
}
