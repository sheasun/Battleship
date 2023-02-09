package edu.duke.ss1316.battleship;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class NonRectangleShipTest {
    @Test
    public void test_constructer() {
        Coordinate upperLeft = new Coordinate(2, 3);
        RectangleShip<Character> ship = new RectangleShip<Character>(upperLeft, 's', '*');
        assertEquals(true, ship.occupiesCoordinates(upperLeft));
        assertEquals(false, ship.occupiesCoordinates(new Coordinate(0, 0)));
    }

    @Test
    public void test_battleship(){
        Coordinate c = new Coordinate(2, 3);
        Placement p = new Placement(c,'u');
        ShipDisplayInfo<Character> myDisplay = new SimpleShipDisplayInfo<>('s', '*');
        ShipDisplayInfo<Character> enemyDisplay = new SimpleShipDisplayInfo<>(null, 's');
        NonRectangleShip<Character> ship = new NonRectangleShip("Battleship", p, 3, 2, myDisplay, enemyDisplay);

        Coordinate c1 = new Coordinate(3, 3);
        Coordinate c2 = new Coordinate(3, 4);
        Coordinate c3 = new Coordinate(2, 4);
        Coordinate c4 = new Coordinate(3, 5);
        ArrayList<Coordinate> expected = new ArrayList<Coordinate>();
        expected.add(c1);
        expected.add(c2);
        expected.add(c3);
        expected.add(c4);

        Iterable<Coordinate> set = ship.getCoordinates();
        for(Coordinate coor : set){
            assertEquals(true, expected.contains(coor));
        }
    }

    @Test
    public void test_carrier(){
        Coordinate c = new Coordinate(2, 3);
        Placement p = new Placement(c,'u');
        ShipDisplayInfo<Character> myDisplay = new SimpleShipDisplayInfo<>('s', '*');
        ShipDisplayInfo<Character> enemyDisplay = new SimpleShipDisplayInfo<>(null, 's');
        NonRectangleShip<Character> ship = new NonRectangleShip("Carrier", p, 2, 5, myDisplay, enemyDisplay);

        Coordinate c1 = new Coordinate(2, 3);
        Coordinate c2 = new Coordinate(3, 3);
        Coordinate c3 = new Coordinate(4, 3);
        Coordinate c4 = new Coordinate(5, 3);
        Coordinate c5 = new Coordinate(4, 4);
        Coordinate c6 = new Coordinate(5, 4);
        Coordinate c7 = new Coordinate(6, 4);
        ArrayList<Coordinate> expected = new ArrayList<Coordinate>();
        expected.add(c1);
        expected.add(c2);
        expected.add(c3);
        expected.add(c4);
        expected.add(c5);
        expected.add(c6);
        expected.add(c7);
        Iterable<Coordinate> set = ship.getCoordinates();
        for(Coordinate coor : set){
            assertEquals(true, expected.contains(coor));
        }
    }

    @Test
    public void test_left_carrier() {
        Coordinate c = new Coordinate(2, 3);
        Placement p = new Placement(c,'l');
        ShipDisplayInfo<Character> myDisplay = new SimpleShipDisplayInfo<>('s', '*');
        ShipDisplayInfo<Character> enemyDisplay = new SimpleShipDisplayInfo<>(null, 's');
        NonRectangleShip<Character> ship = new NonRectangleShip("Carrier", p, 2, 5, myDisplay, enemyDisplay);

        Coordinate c1 = new Coordinate(3, 3);
        Coordinate c2 = new Coordinate(3, 4);
        Coordinate c3 = new Coordinate(3, 5);
        Coordinate c4 = new Coordinate(3, 6);
        Coordinate c5 = new Coordinate(2, 5);
        Coordinate c6 = new Coordinate(2, 6);
        Coordinate c7 = new Coordinate(2, 7);
        ArrayList<Coordinate> expected = new ArrayList<Coordinate>();
        expected.add(c1);
        expected.add(c2);
        expected.add(c3);
        expected.add(c4);
        expected.add(c5);
        expected.add(c6);
        expected.add(c7);
        Iterable<Coordinate> set = ship.getCoordinates();
        for(Coordinate coor : set){
            assertEquals(true, expected.contains(coor));
        }
    }

}