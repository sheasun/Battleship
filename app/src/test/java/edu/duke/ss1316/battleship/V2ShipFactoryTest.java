package edu.duke.ss1316.battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class V2ShipFactoryTest {
    private void checkShip(Ship<Character> testShip, String expectedName,
    char expectedLetter, Coordinate... expectedLocs) {
        assertEquals(expectedName, testShip.getName());
        for(Coordinate c : expectedLocs){
          assertEquals(expectedLetter, testShip.getDisplayInfoAt(c, true));
        }
    }

    @Test
    public void test_makeSubmarine() {
        V2ShipFactory sf = new V2ShipFactory();
        Placement p1 = new Placement(new Coordinate(2, 3), 'h');
        Placement p2 = new Placement(new Coordinate(2, 3), 'v');
        Ship<Character> ship1 = sf.makeSubmarine(p1);
        Ship<Character> ship2 = sf.makeSubmarine(p2);
        checkShip(ship1, "Submarine", 's', new Coordinate(2, 3), new Coordinate(2, 4));
        checkShip(ship2, "Submarine", 's', new Coordinate(2, 3), new Coordinate(3, 3));
    }

    @Test
    public void test_makeDestroyer() {
        V2ShipFactory sf = new V2ShipFactory();
        Placement p1 = new Placement(new Coordinate(2, 3), 'h');
        Placement p2 = new Placement(new Coordinate(2, 3), 'v');
        Ship<Character> ship1 = sf.makeDestroyer(p1);
        Ship<Character> ship2 = sf.makeDestroyer(p2);
        checkShip(ship2, "Destroyer", 'd', new Coordinate(2, 3), new Coordinate(3, 3), new Coordinate(4, 3));
        checkShip(ship1, "Destroyer", 'd', new Coordinate(2, 3), new Coordinate(2, 4), new Coordinate(2, 5));
    }

    @Test
    public void test_makeBattleship() {
        V2ShipFactory factory = new V2ShipFactory();
        Placement p1 = new Placement(new Coordinate(2, 3), 'u');
        Placement p2 = new Placement(new Coordinate(1, 2), 'd');
        Placement p3 = new Placement(new Coordinate(1, 2), 'r');
        Placement p4 = new Placement(new Coordinate(1, 2), 'l');
        Ship<Character> ship1 = factory.makeBattleship(p1);
        Ship<Character> ship2 = factory.makeBattleship(p2);
        Ship<Character> ship3 = factory.makeBattleship(p3);
        Ship<Character> ship4 = factory.makeBattleship(p4);
        checkShip(ship1, "Battleship", 'b', 
        new Coordinate(2, 4), 
        new Coordinate(3, 3), 
        new Coordinate(3, 4), 
        new Coordinate(3, 5));
        checkShip(ship2, "Battleship", 'b', 
        new Coordinate(1, 2),
        new Coordinate(1, 3),
        new Coordinate(1, 4),
        new Coordinate(2, 3));
        checkShip(ship3, "Battleship", 'b', 
        new Coordinate(1, 2),
        new Coordinate(2, 2),
        new Coordinate(3, 2),
        new Coordinate(2, 3));
        checkShip(ship4, "Battleship", 'b', 
        new Coordinate(2, 2),
        new Coordinate(1, 3),
        new Coordinate(2, 3),
        new Coordinate(3, 3));
    }

    @Test
    public void test_makeCarrier() {
        V2ShipFactory factory = new V2ShipFactory();
        Placement p1 = new Placement(new Coordinate(1, 2), 'u');
        Placement p2 = new Placement(new Coordinate(1, 2), 'd');
        Placement p3 = new Placement(new Coordinate(1, 2), 'r');
        Placement p4 = new Placement(new Coordinate(2, 3), 'l');
        Ship<Character> ship1 = factory.makeCarrier(p1);
        Ship<Character> ship2 = factory.makeCarrier(p2);
        Ship<Character> ship3 = factory.makeCarrier(p3);
        Ship<Character> ship4 = factory.makeCarrier(p4);
        checkShip(ship1, "Carrier", 'c', 
        new Coordinate(1, 2),
        new Coordinate(2, 2),
        new Coordinate(3, 2),
        new Coordinate(4, 2),
        new Coordinate(3, 3),
        new Coordinate(4, 3),
        new Coordinate(5, 3));
        checkShip(ship2, "Carrier", 'c', 
        new Coordinate(1, 2),
        new Coordinate(2, 2),
        new Coordinate(3, 2),
        new Coordinate(2, 3),
        new Coordinate(3, 3),
        new Coordinate(4, 3),
        new Coordinate(5, 3));
        checkShip(ship3, "Carrier", 'c', 
        new Coordinate(1, 3),
        new Coordinate(1, 4),
        new Coordinate(1, 5),
        new Coordinate(1, 6),
        new Coordinate(2, 2),
        new Coordinate(2, 3),
        new Coordinate(2, 4));
        checkShip(ship4, "Carrier", 'c', 
        new Coordinate(2, 5),
        new Coordinate(2, 6),
        new Coordinate(2, 7),
        new Coordinate(3, 3),
        new Coordinate(3, 4),
        new Coordinate(3, 5),
        new Coordinate(3, 6));
    }
}