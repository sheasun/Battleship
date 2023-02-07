package edu.duke.ss1316.battleship;

import java.util.ArrayList;

public interface Board<T> {
  public int getWidth();

  public int getHeight();

  public String tryAddShip(Ship<T> toAdd);

  public T whatIsAtForSelf(Coordinate where);

  public T whatIsAtForEnemy(Coordinate where);

  public Ship<T> fireAt(Coordinate c);

  public ArrayList<Ship<T>> getShips();
}
