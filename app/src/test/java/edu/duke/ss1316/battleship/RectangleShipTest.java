package edu.duke.ss1316.battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

class RectangleShipTest {
    @Test
    public void test_makeCoords() {
        Coordinate c = new Coordinate(3, 4);
        HashSet<Coordinate> set = RectangleShip.makeCoords(c, 2, 3);
        HashSet<Coordinate> expected = new HashSet<>();
        for (int i = 3; i < 3 + 3; ++i) {
            for (int j = 4; j < 4 + 2; ++j) {
                expected.add(new Coordinate(i, j));
            }
        }
        assertEquals(expected, set);
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
                    assertEquals('*', rs.getDisplayInfoAt(new Coordinate(r, c)));
                } else {
                    assertEquals('s', rs.getDisplayInfoAt(new Coordinate(r, c)));
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
            assertEquals('s', rs.getDisplayInfoAt(m));
        }
    }
}