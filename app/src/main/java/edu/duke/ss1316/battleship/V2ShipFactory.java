package edu.duke.ss1316.battleship;

public class V2ShipFactory implements AbstractShipFactory<Character> {
      @Override
      public Ship<Character> makeSubmarine(Placement where) {
        if (where.getOrientation() == 'V') {
            return createShip(where, 1, 2, 's', "Submarine");
        } else {
            return createShip(where, 2, 1, 's', "Submarine");
        }
      }
  
      @Override
      public Ship<Character> makeDestroyer(Placement where) {
        if (where.getOrientation() == 'V') {
            return createShip(where, 1, 3, 'd', "Destroyer");
        } else {
            return createShip(where, 3, 1, 'd', "Destroyer");
        }
      }
  
      @Override
      public Ship<Character> makeBattleship(Placement where) {
        return createShip(where, 3, 2, 'b', "Battleship");
      }
  
      @Override
      public Ship<Character> makeCarrier(Placement where) {
        return createShip(where, 2, 5, 'c', "Carrier");
      }

      protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name) {
        if (name.equals("Submarine") || name.equals("Destroyer")) {
          return new RectangleShip<Character>(name, where.getWhere(), w, h, letter, '*');
        } else {
            return new NonRectangleShip<Character>(name, where, w, h, letter, '*');
        }
      }
}