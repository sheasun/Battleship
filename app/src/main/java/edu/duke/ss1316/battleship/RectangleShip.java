package edu.duke.ss1316.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T> {
    public RectangleShip(Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> info){
        super(makeCoords(upperLeft, width, height), info);
      }
    public RectangleShip(Coordinate upperLeft, int width, int height, T data, T onHit) {
        this(upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit));
      }
      public RectangleShip(Coordinate upperLeft, T data, T onHit) {
        this(upperLeft, 1, 1, data, onHit);
      }
       

    static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
        HashSet<Coordinate> set = new HashSet<Coordinate>();
        int row = upperLeft.getRow();
        int column = upperLeft.getColumn();
    
        for(int r = row; r < row + height; r++) {
          for(int  c = column; c < column + width; c++) {
            set.add(new Coordinate(r, c));
          }
        }
        return set;
      }
}