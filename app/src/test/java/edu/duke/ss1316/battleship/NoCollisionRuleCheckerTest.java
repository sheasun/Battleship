package edu.duke.ss1316.battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NoCollisionRuleCheckerTest {
    @Test
    public void test_checkMyRule() {
        BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 20, new NoCollisionRuleChecker<Character>(null), 'X');
        V2ShipFactory factory = new V2ShipFactory();
        InBoundRuleChecker<Character> rc = new InBoundRuleChecker<Character>(new NoCollisionRuleChecker<>(null));
        String s1 = "The coordinate is taken by other ship!";
        String s2 = "Out of bounds!";
        Ship<Character> ship1 = factory.makeSubmarine(new RectanglePlacement(new Coordinate(0, 0), 'h'));
        assertEquals(null, rc.checkPlacement(ship1, b));
        b.tryAddShip(ship1);
        assertEquals(s1, rc.checkPlacement(ship1, b));
        Ship<Character> ship2 = factory.makeSubmarine(new RectanglePlacement(new Coordinate(2, 3), 'h'));
        assertEquals(null, rc.checkPlacement(ship2, b));
        Ship<Character> ship3 = factory.makeSubmarine(new RectanglePlacement(new Coordinate(19, 0), 'v'));
        assertEquals(s2, rc.checkPlacement(ship3, b));
    }
}