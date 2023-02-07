package edu.duke.ss1316.battleship;

public class V1ShipFactory implements AbstractShipFactory<Character> {
    protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name) {
        Coordinate c = where.getWhere();
        Character o = where.getOrientation();
        RectangleShip<Character> rs;
        if (o == 'H') {
            rs = new RectangleShip<Character>(name, c, h, w, letter, '*');
        } else {
            rs = new RectangleShip<Character>(name, c, w, h, letter, '*');
        }
        return rs;
      }

    @Override
    public Ship<Character> makeSubmarine(Placement where) {
      return createShip(where, 1, 2, 's', "Submarine");
    }

    @Override
    public Ship<Character> makeDestroyer(Placement where) {
      return createShip(where, 1, 3, 'd', "Destroyer");
    }
  
    @Override
    public Ship<Character> makeBattleship(Placement where) {
      return createShip(where, 1, 4, 'b', "Battleship");
    }
  
    @Override
    public Ship<Character> makeCarrier(Placement where) {
      return createShip(where, 1, 6, 'c', "Carrier");
    }
}