package edu.duke.ss1316.battleship;

public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {
    public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
      super(next);
    }

    // check if all the squares that theShip needs are empty
    // if so, return true
    @Override
    protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        Iterable<Coordinate> myPieces = theShip.getCoordinates();
        String s = "The coordinate is taken by other ship!";
        for (Coordinate c : myPieces) {
          if (theBoard.whatIsAtForSelf(c) != null) {
            return s;
          }
        }
        return null;
    }
}