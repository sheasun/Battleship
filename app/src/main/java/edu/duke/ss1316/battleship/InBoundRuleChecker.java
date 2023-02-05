package edu.duke.ss1316.battleship;

public class InBoundRuleChecker<T> extends PlacementRuleChecker<T> {
    public InBoundRuleChecker(PlacementRuleChecker<T> next) {
        super(next);
    }

    @Override
    protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
      Iterable<Coordinate> myPieces = theShip.getCoordinates();
      for (Coordinate c : myPieces) {
        if (c.getRow() < 0 || c.getColumn() < 0) {
            return false;
        } else if (c.getRow() >= theBoard.getHeight()) {
            return false;
        } else if (c.getColumn() >= theBoard.getWidth()) {
            return false;
        }
      }
      return true;
    }
}