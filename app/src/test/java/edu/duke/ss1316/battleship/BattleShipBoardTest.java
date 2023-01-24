package edu.duke.ss1316.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    BattleShipBoard b1 = new BattleShipBoard(10, 20);
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());

  }

}
