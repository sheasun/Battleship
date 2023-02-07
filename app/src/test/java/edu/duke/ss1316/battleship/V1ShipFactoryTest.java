package edu.duke.ss1316.battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class V1ShipFactoryTest {
    private void checkShip(Ship<Character> testShip, String expectedName,
    char expectedLetter, Coordinate... expectedLocs) {
        assertEquals(expectedName, testShip.getName());
        for(Coordinate c : expectedLocs){
          assertEquals(expectedLetter, testShip.getDisplayInfoAt(c, true));
        }
    }

    @Test
    public void test_makeSubmarine() {
        V1ShipFactory sf = new V1ShipFactory();
        Placement p1 = new Placement(new Coordinate(2, 3), 'h');
        Placement p2 = new Placement(new Coordinate(2, 3), 'v');
        Ship<Character> ship1 = sf.makeSubmarine(p1);
        Ship<Character> ship2 = sf.makeSubmarine(p2);
        checkShip(ship1, "Submarine", 's', new Coordinate(2, 3), new Coordinate(2, 4));
        checkShip(ship2, "Submarine", 's', new Coordinate(2, 3), new Coordinate(3, 3));
    }

    @Test
    public void test_makeDestroyer() {
        V1ShipFactory sf = new V1ShipFactory();
        Placement p1 = new Placement(new Coordinate(2, 3), 'h');
        Placement p2 = new Placement(new Coordinate(2, 3), 'v');
        Ship<Character> ship1 = sf.makeDestroyer(p1);
        Ship<Character> ship2 = sf.makeDestroyer(p2);
        checkShip(ship2, "Destroyer", 'd', new Coordinate(2, 3), new Coordinate(3, 3), new Coordinate(4, 3));
        checkShip(ship1, "Destroyer", 'd', new Coordinate(2, 3), new Coordinate(2, 4), new Coordinate(2, 5));
    }

    @Test
    public void test_makeBattleship() {
        V1ShipFactory sf = new V1ShipFactory();
        Placement p1 = new Placement(new Coordinate(2, 3), 'h');
        Placement p2 = new Placement(new Coordinate(2, 3), 'v');
        Ship<Character> ship1 = sf.makeBattleship(p1);
        Ship<Character> ship2 = sf.makeBattleship(p2);
        checkShip(ship1, "Battleship", 'b', new Coordinate(2, 3),
        new Coordinate(2, 4), 
        new Coordinate(2, 5), 
        new Coordinate(2, 6));
        checkShip(ship2, "Battleship", 'b', new Coordinate(2, 3), 
        new Coordinate(3, 3), 
        new Coordinate(4, 3), 
        new Coordinate(5, 3));
    }

    @Test
    public void test_makeCarrier() {
        V1ShipFactory sf = new V1ShipFactory();
        Placement p1 = new Placement(new Coordinate(2, 3), 'h');
        Placement p2 = new Placement(new Coordinate(2, 3), 'v');
        Ship<Character> ship1 = sf.makeCarrier(p1);
        Ship<Character> ship2 = sf.makeCarrier(p2);
        checkShip(ship1, "Carrier", 'c', new Coordinate(2, 3),
        new Coordinate(2, 4), 
        new Coordinate(2, 5), 
        new Coordinate(2, 6), 
        new Coordinate(2, 7), 
        new Coordinate(2, 8));
        checkShip(ship2, "Carrier", 'c', new Coordinate(2, 3),
        new Coordinate(3, 3), 
        new Coordinate(4, 3), 
        new Coordinate(5, 3), 
        new Coordinate(6, 3), 
        new Coordinate(7, 3));
    }

}