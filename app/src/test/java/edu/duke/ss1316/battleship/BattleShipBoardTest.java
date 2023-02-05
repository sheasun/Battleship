package edu.duke.ss1316.battleship;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard(10, 0));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard(0, 20));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard(10, -5));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard(-8, 20));
  }

  @Test
  public void test_tryAddShip() {
    Board<Character> b = new BattleShipBoard<>(10, 20, new InBoundRuleChecker<>(new NoCollisionRuleChecker<>(null)));
    V1ShipFactory sf = new V1ShipFactory();

    Ship<Character> ship1 = sf.makeSubmarine(new Placement(new Coordinate(0, 8), 'h'));
    assertEquals(true, b.tryAddShip(ship1));

    Ship<Character> ship2 = sf.makeSubmarine(new Placement(new Coordinate(0, 9), 'h'));
    assertEquals(false, b.tryAddShip(ship2));
    b.tryAddShip(ship1);
    assertEquals(false, b.tryAddShip(ship1));

    Ship<Character> ship3 = sf.makeSubmarine(new Placement(new Coordinate(18, 0), 'v'));
    assertEquals(true, b.tryAddShip(ship3));
    b.tryAddShip(ship3);
    assertEquals(false, b.tryAddShip(ship3));

    Ship<Character> ship4 = sf.makeSubmarine(new Placement(new Coordinate(19, 0), 'v'));
    assertEquals(false, b.tryAddShip(ship4));
  }

}
