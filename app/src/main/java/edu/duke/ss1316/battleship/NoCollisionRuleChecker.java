package edu.duke.ss1316.battleship;

public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {
    public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
      super(next);
    }

    // check if all the squares that theShip needs are empty
    // if so, return true
    @Override
    protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        Iterable<Coordinate> myPieces = theShip.getCoordinates();
        for (Coordinate c : myPieces) {
          if (theBoard.whatIsAt(c) != null) {
            return false;
          }
        }
        return true;
    }
}