package edu.duke.ss1316.battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NoCollisionRuleCheckerTest {
    @Test
    public void test_checkMyRule() {
        BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 20, new NoCollisionRuleChecker<Character>(null));
        V1ShipFactory sf = new V1ShipFactory();
        InBoundRuleChecker<Character> rc = new InBoundRuleChecker<Character>(new NoCollisionRuleChecker<>(null));

        Ship<Character> ship1 = sf.makeSubmarine(new Placement(new Coordinate(0, 0), 'h'));
        assertEquals(true, rc.checkPlacement(ship1, b));
        b.tryAddShip(ship1);
        assertEquals(false, rc.checkPlacement(ship1, b));
        Ship<Character> ship2 = sf.makeSubmarine(new Placement(new Coordinate(2, 3), 'h'));
        assertEquals(true, rc.checkPlacement(ship2, b));
        Ship<Character> ship3 = sf.makeSubmarine(new Placement(new Coordinate(19, 0), 'v'));
        assertEquals(false, rc.checkPlacement(ship3, b));
    }
}