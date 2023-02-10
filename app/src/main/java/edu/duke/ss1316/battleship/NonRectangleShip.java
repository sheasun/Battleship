package edu.duke.ss1316.battleship;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class NonRectangleShip<T> extends BasicShip<T> {
    private final String name;

    public String getName() {
        return this.name;
    }

    public NonRectangleShip(String name, NonRectanglePlacement p, int width, int height, ShipDisplayInfo<T> myInfo, ShipDisplayInfo<T> enemyInfo){
        super(makeCoords(name, p, width, height), myInfo, enemyInfo);
        this.name = name;
      }
    public NonRectangleShip(String name, NonRectanglePlacement p, int width, int height, T data, T onHit) {
        this(name, p, width, height, new SimpleShipDisplayInfo<T>(data, onHit), new SimpleShipDisplayInfo<T>(null, data));
      }

    static HashSet<Coordinate> makeCoords(String name, NonRectanglePlacement p, int width, int height) {
        if (name == "Battleship") {
            return makeBattleshipCoords(name, p, width, height);
        } else {
            return makeCarrierCoords(name, p, width, height);
        }
      }

    static LinkedHashSet<Coordinate> makeBattleshipCoords(String name, NonRectanglePlacement p, int width, int height) {
        LinkedHashSet<Coordinate> set = new LinkedHashSet<Coordinate>();
        char o = p.getOrientation();
        int row = p.getWhere().getRow();
        int col = p.getWhere().getColumn();
        if (o == 'U') {
            set.add(new Coordinate(row, col + 1));
            set.add(new Coordinate(row + 1,col));
            set.add(new Coordinate(row + 1,col + 1));
            set.add(new Coordinate(row + 1,col + 2));
        } else if (o == 'D') {
            set.add(new Coordinate(row + 1,col + 1));
            set.add(new Coordinate(row, col + 2));
            set.add(new Coordinate(row, col + 1));
            set.add(new Coordinate(row, col));
        } else if (o == 'R') {
            set.add(new Coordinate(row + 1, col + 1));
            set.add(new Coordinate(row, col));
            set.add(new Coordinate(row + 1, col));
            set.add(new Coordinate(row + 2, col));
        } else {
            set.add(new Coordinate(row + 1, col));
            set.add(new Coordinate(row + 2, col + 1));
            set.add(new Coordinate(row + 1, col + 1));
            set.add(new Coordinate(row, col + 1));
        }
        return set;
    }

    static LinkedHashSet<Coordinate> makeCarrierCoords(String name, NonRectanglePlacement p, int width, int height) {
        LinkedHashSet<Coordinate> set = new LinkedHashSet<Coordinate>();
        char o = p.getOrientation();
        int row = p.getWhere().getRow();
        int col = p.getWhere().getColumn();
        if (o == 'U') {
            set.add(new Coordinate(row, col));
            set.add(new Coordinate(row + 1, col));
            set.add(new Coordinate(row + 2, col));
            set.add(new Coordinate(row + 3, col));
            set.add(new Coordinate(row + 2, col + 1));
            set.add(new Coordinate(row + 3, col + 1));
            set.add(new Coordinate(row + 4, col + 1));
        } else if (o == 'D') {
            set.add(new Coordinate(row + 4, col + 1));
            set.add(new Coordinate(row + 3, col + 1));
            set.add(new Coordinate(row + 2, col + 1));
            set.add(new Coordinate(row + 1, col + 1));
            set.add(new Coordinate(row + 2, col));
            set.add(new Coordinate(row + 1, col));
            set.add(new Coordinate(row, col));
        } else if (o == 'R') {
            set.add(new Coordinate(row, col + 4));
            set.add(new Coordinate(row, col + 3));
            set.add(new Coordinate(row, col + 2));
            set.add(new Coordinate(row, col + 1));
            set.add(new Coordinate(row + 1, col + 2));
            set.add(new Coordinate(row + 1, col + 1));
            set.add(new Coordinate(row + 1, col));
        } else {
            set.add(new Coordinate(row + 1, col));
            set.add(new Coordinate(row + 1, col + 1));
            set.add(new Coordinate(row + 1, col + 2));
            set.add(new Coordinate(row + 1, col + 3));
            set.add(new Coordinate(row, col + 2));
            set.add(new Coordinate(row, col + 3));
            set.add(new Coordinate(row, col + 4));
        }
        return set;
    }
}