package edu.duke.ss1316.battleship;

public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
    private T myData;
    private T onHit;
  
    public SimpleShipDisplayInfo(T myData, T onHit) {
      this.myData = myData;
      this.onHit = onHit;
    }
  
    public T getInfo(Coordinate where, boolean hit) {
      if(hit) {
        return this.onHit;
      } else {
        return this.myData;
      }
    }
}