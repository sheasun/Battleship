package edu.duke.ss1316.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
    @Test
    public void test_equals() {
        Coordinate c1 = new Coordinate(3, 2);
        Coordinate c2 = new Coordinate(3, 2);
        Placement p1 = new Placement(c1, 'v');
        Placement p2 = new Placement(c2, 'V');
        assertEquals(p1.getCoordinate(), p2.getCoordinate());
        assertEquals(p1.getOrientation(), p2.getOrientation());
    }
}