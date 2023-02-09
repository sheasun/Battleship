package edu.duke.ss1316.battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class InBoundRuleCheckerTest {
    @Test
    public void test_checkMyRule() {
        BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 20, new InBoundRuleChecker<Character>(null), 'X');
        V2ShipFactory factory = new V2ShipFactory();
        InBoundRuleChecker<Character> rc = new InBoundRuleChecker<Character>(null);
        String s = "Out of bounds!";

        Ship<Character> ship1 = factory.makeSubmarine(new Placement(new Coordinate(0, 0), 'h'));
        Ship<Character> ship2 = factory.makeSubmarine(new Placement(new Coordinate(0, 9), 'h'));

        Ship<Character> ship3 = factory.makeSubmarine(new Placement(new Coordinate(18, 0), 'v'));
        Ship<Character> ship4 = factory.makeSubmarine(new Placement(new Coordinate(19, 0), 'v'));

        Ship<Character> ship5 = factory.makeSubmarine(new Placement(new Coordinate(-1, 0), 'h'));
        Ship<Character> ship6 = factory.makeSubmarine(new Placement(new Coordinate(0, -1), 'h'));
        Ship<Character> ship7 = factory.makeSubmarine(new Placement(new Coordinate(0, -1), 'v'));
        
        assertEquals(null, rc.checkPlacement(ship1, b));
        assertEquals(s, rc.checkPlacement(ship2, b));
        assertEquals(null, rc.checkPlacement(ship3, b));
        assertEquals(s, rc.checkPlacement(ship4, b));
        assertEquals(s, rc.checkPlacement(ship5, b));
        assertEquals(s, rc.checkPlacement(ship6, b));
        assertEquals(s, rc.checkPlacement(ship7, b));
    }
}