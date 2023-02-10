package edu.duke.ss1316.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20, 'X'));
  }

  @Test
  public void test_tryAddShip() {
    Board<Character> b = new BattleShipBoard<>(10, 20, new InBoundRuleChecker<>(new NoCollisionRuleChecker<>(null)), 'X');
    V2ShipFactory factory = new V2ShipFactory();
    // InBoundRuleChecker
    String s1 = "The coordinate is taken by other ship!";
    // NoCollisionRuleChecker
    String s2 = "Out of bounds!";
    Ship<Character> ship1 = factory.makeSubmarine(new RectanglePlacement(new Coordinate(0, 8), 'h'));
    assertEquals(null, b.tryAddShip(ship1));

    Ship<Character> ship2 = factory.makeSubmarine(new RectanglePlacement(new Coordinate(0, 9), 'h'));
    assertEquals(s2, b.tryAddShip(ship2));
    b.tryAddShip(ship1);
    assertEquals(s1, b.tryAddShip(ship1));

    Ship<Character> ship3 = factory.makeSubmarine(new RectanglePlacement(new Coordinate(18, 0), 'v'));
    assertEquals(null, b.tryAddShip(ship3));
    b.tryAddShip(ship3);
    assertEquals(s1, b.tryAddShip(ship3));

    Ship<Character> ship4 = factory.makeSubmarine(new RectanglePlacement(new Coordinate(19, 0), 'v'));
    assertEquals(s2, b.tryAddShip(ship4));
  }

  @Test
  public void test_whatIsAt() {
    Board<Character> board = new BattleShipBoard<Character>(10, 20, 'X');
    V2ShipFactory factory = new V2ShipFactory();
    Ship<Character> ship1 = factory.makeSubmarine(new RectanglePlacement(new Coordinate(2, 3), 'h'));
    Ship<Character> ship2 = factory.makeSubmarine(new RectanglePlacement(new Coordinate(0, 0), 'v'));
    board.tryAddShip(ship1);
    board.tryAddShip(ship2);
    board.fireAt(new Coordinate(3, 3));
    assertEquals('X', board.whatIsAtForEnemy(new Coordinate(3, 3)));
  }

  @Test
  public void test_fireAt() {
    Board<Character> board = new BattleShipBoard<Character>(10, 20, new InBoundRuleChecker<>(new NoCollisionRuleChecker<>(null)), 'X');
    V2ShipFactory factory = new V2ShipFactory();
    Ship<Character> ship1 = factory.makeSubmarine(new RectanglePlacement(new Coordinate(2, 3), 'h'));
    Ship<Character> ship2 = factory.makeSubmarine(new RectanglePlacement(new Coordinate(0, 0), 'v'));
    assertSame(null, board.fireAt(new Coordinate(2, 3)));
    board.tryAddShip(ship1);
    assertSame(ship1, board.fireAt(new Coordinate(2, 3)));
    board.tryAddShip(ship2);
    assertSame(ship2, board.fireAt(new Coordinate(0, 0)));
    board.removeShip(ship2);
    assertSame(null, board.fireAt(new Coordinate(0, 0)));
  }

  @Test
  public void test_findShipsBySonar() {
    Board<Character> board = new BattleShipBoard<Character>(10, 20, new InBoundRuleChecker<>(new NoCollisionRuleChecker<>(null)), 'X');
    V2ShipFactory factory = new V2ShipFactory();
    Ship<Character> ship1 = factory.makeSubmarine(new RectanglePlacement(new Coordinate(0, 0), 'h'));
    HashMap<String, Integer> map = new HashMap<>();
    map.put("Submarine", 0);
    map.put("Destroyer", 0);
    map.put("Battleship", 0);
    map.put("Carrier", 0);
    
    assertEquals(map, board.findShipsBySonar(new Coordinate(1, 1)));
    assertEquals(map, board.findShipsBySonar(new Coordinate(19, 0)));
    map.put("Submarine", 2);
    board.tryAddShip(ship1);
    assertEquals(map, board.findShipsBySonar(new Coordinate(1, 1)));
    map.put("Submarine", 1);
    assertEquals(map, board.findShipsBySonar(new Coordinate(1, 3)));
  }

  @Test
  public void test_checkIfInBound() {
    Board<Character> board = new BattleShipBoard<Character>(10, 20, 'X');
    assertEquals(false, board.checkIfInBound(20, 0));
    assertEquals(false, board.checkIfInBound(0, 10));
    assertEquals(false, board.checkIfInBound(-1, 2));
    assertEquals(false, board.checkIfInBound(2, -1));
    assertEquals(true, board.checkIfInBound(5, 5));


  }

}
