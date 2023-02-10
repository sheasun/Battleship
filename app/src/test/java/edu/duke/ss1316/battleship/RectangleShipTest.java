package edu.duke.ss1316.battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class RectangleShipTest {
    @Test
    public void test_constructer() {
        Coordinate upperLeft = new Coordinate(2, 3);
        RectangleShip<Character> ship = new RectangleShip<Character>(upperLeft, 's', '*');
        assertEquals(true, ship.occupiesCoordinates(upperLeft));
        assertEquals(false, ship.occupiesCoordinates(new Coordinate(0, 0)));
    }

    @Test
    public void test_recordHitAt_and_wasHitAt() {
        Coordinate where = new Coordinate(2, 3);
        RectangleShip<Character> rs = new RectangleShip<>("submarine", where, 3, 2, 's', '*');
        for (int r = 2; r < 2 + 2; ++r) {
            for (int c = 3; c < 3 + 3; ++c) {
                assertEquals(false, rs.wasHitAt(new Coordinate(r, c)));
            }
        }
        for (int r = 2; r < 2 + 2; ++r) {
            for (int c = 3; c < 3 + 3; ++c) {
                rs.recordHitAt(new Coordinate(r, c));
            }
        }
        rs.recordHitAt(new Coordinate(2, 3));
        for (int r = 2; r < 2 + 2; ++r) {
            for (int c = 3; c < 3 + 3; ++c) {
                assertEquals(true, rs.wasHitAt(new Coordinate(r, c)));
            }
        }
        assertThrows(IllegalArgumentException.class, ()->rs.wasHitAt(new Coordinate(4, 6)));
    }

    @Test
    public void test_isSunk() {
        Coordinate where = new Coordinate(2, 3);
        RectangleShip<Character> rs = new RectangleShip<>("submarine", where, 3, 2, 's', '*');
        assertEquals(false, rs.isSunk());
        for (int r = 2; r < 2 + 2; ++r) {
            for (int c = 3; c < 3 + 3; ++c) {
                rs.recordHitAt(new Coordinate(r, c));
            }
        }
        assertEquals(true, rs.isSunk());
    }

    @Test
    public void test_getDisplayInfoAt() {
        Coordinate where = new Coordinate(2, 3);
        RectangleShip<Character> rs = new RectangleShip<>("submarine", where, 3, 2, 's', '*');
        rs.recordHitAt(new Coordinate(3, 3));
        for (int r = 2; r < 2 + 2; ++r) {
            for (int c = 3; c < 3 + 3; ++c) {
                if ((r == 3 && c == 3)) {
                    assertEquals('*', rs.getDisplayInfoAt(new Coordinate(r, c), true));
                    assertEquals('s', rs.getDisplayInfoAt(new Coordinate(r, c), false));
                } else {
                    assertEquals('s', rs.getDisplayInfoAt(new Coordinate(r, c), true));
                    assertEquals(null, rs.getDisplayInfoAt(new Coordinate(r, c), false));
                }
            }
        }
    }


    @Test
    public void test_getCoordinates() {
        Coordinate c = new Coordinate(2, 3);
        RectangleShip<Character> rs = new RectangleShip<Character>("Submarine", c, 3, 2, 's', '*');
        Iterable<Coordinate> myPieces = rs.getCoordinates();
        for (Coordinate m : myPieces) {
            assertEquals('s', rs.getDisplayInfoAt(m, true));
        }
    }

    @Test
    public void test_getShips() {
        Board<Character> board = new BattleShipBoard<Character>(10, 20, 'X');
        V1ShipFactory factory = new V1ShipFactory();
        Ship<Character> ship1 = factory.makeSubmarine(new RectanglePlacement(new Coordinate(2, 3), 'h'));
        Ship<Character> ship2 = factory.makeSubmarine(new RectanglePlacement(new Coordinate(5, 6), 'v'));
        board.tryAddShip(ship1);
        board.tryAddShip(ship2);
        ArrayList<Ship<Character>> ships = new ArrayList<>();
        ships.add(ship1);
        ships.add(ship2);
        assertEquals(ships, board.getShips());
    }
}