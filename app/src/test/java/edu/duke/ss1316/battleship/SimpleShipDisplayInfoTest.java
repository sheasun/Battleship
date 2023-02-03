package edu.duke.ss1316.battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleShipDisplayInfoTest {
    @Test
    public void test_getInfo() {
        SimpleShipDisplayInfo<String> s = new SimpleShipDisplayInfo<String>("myData", "onHit");
        assertEquals("onHit", s.getInfo(new Coordinate(1, 2), true));
        assertEquals("myData", s.getInfo(new Coordinate(1, 2), false));
    }
}