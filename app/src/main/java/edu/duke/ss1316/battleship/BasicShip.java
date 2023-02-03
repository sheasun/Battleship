package edu.duke.ss1316.battleship;

import java.util.HashMap;

public abstract class BasicShip<T> implements Ship<T> {
    protected HashMap<Coordinate, Boolean> myPieces;
    protected ShipDisplayInfo<T> myDisplayInfo;

    public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo) {
        this.myDisplayInfo = myDisplayInfo;
        this.myPieces = new HashMap<Coordinate, Boolean>();
        for (Coordinate c : where) {
            myPieces.put(c, false);
        }
    }

    @Override
    public boolean isSunk() {
        // TODO Auto-generated method stub
        for (Boolean val : this.myPieces.values()) {
            if (val == false) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void recordHitAt(Coordinate where) {
        // TODO Auto-generated method stub
        checkCoordinateInThisShip(where);
        if (!this.myPieces.get(where)) {
            this.myPieces.put(where, true);
        }
    }

    @Override
    public boolean wasHitAt(Coordinate where) {
        // TODO Auto-generated method stub
        checkCoordinateInThisShip(where);
        if (this.myPieces.get(where)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean occupiesCoordinates(Coordinate where) {
        return this.myPieces.containsKey(where);
    }

    @Override
    public T getDisplayInfoAt(Coordinate where) {
      //TODO this is not right.  We need to
      //look up the hit status of this coordinate
      checkCoordinateInThisShip(where);
      return myDisplayInfo.getInfo(where, wasHitAt(where));
    }

    protected void checkCoordinateInThisShip(Coordinate c) {
        if (!this.myPieces.containsKey(c)) {
            throw new IllegalArgumentException("This coordinate is not in this ship!");
        }
    }
}