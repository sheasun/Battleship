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
}