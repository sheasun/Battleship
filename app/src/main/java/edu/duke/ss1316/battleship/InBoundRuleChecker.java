package edu.duke.ss1316.battleship;

public class InBoundRuleChecker<T> extends PlacementRuleChecker<T> {
    public InBoundRuleChecker(PlacementRuleChecker<T> next) {
        super(next);
    }

    @Override
    protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
      Iterable<Coordinate> myPieces = theShip.getCoordinates();
      String warning = "Out of bounds!";
      for (Coordinate c : myPieces) {
        if (c.getRow() < 0 || c.getColumn() < 0) {
            return warning;
        } else if (c.getRow() >= theBoard.getHeight()) {
            return warning;
        } else if (c.getColumn() >= theBoard.getWidth()) {
            return warning;
        }
      }
      return null;
    }
}