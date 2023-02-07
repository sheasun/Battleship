package edu.duke.ss1316.battleship;

public class V2ShipFactory implements AbstractShipFactory<Character> {
    protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name) {
        Coordinate c = where.getWhere();
        Character o = where.getOrientation();
        RectangleShip<Character> rs;
        NonRectangleShip<Character> nrs;
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
}