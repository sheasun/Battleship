package edu.duke.ss1316.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  @Test
  public void test_display_empty_2by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(2, 2, 'X');
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader= "  0|1\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected =
      expectedHeader+
      "A  |  A\n"+
      "B  |  B\n"+
      expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }
 
  @Test
  public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11,20,'X');
    Board<Character> tallBoard = new BattleShipBoard<Character>(10,27,'X');
    //you should write two assertThrows here
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
  }

  @Test
  public void test_display_empty_3by5() {
    Board<Character> b1 = new BattleShipBoard<Character>(3, 5,'X');
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader= "  0|1|2\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected=
      expectedHeader+
      "A  | |  A\n"+
      "B  | |  B\n"+
      "C  | |  C\n"+
      "D  | |  D\n"+
      "E  | |  E\n"+
      expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
    assertEquals(3, view.getBoard().getWidth());
  }

  @Test
  public void test_displayMyBoardWithEnemyNextToIt() {
    Board<Character> myBoard = new BattleShipBoard<Character>(10, 20,'X');
    Board<Character> enemyBoard = new BattleShipBoard<Character>(10, 20,'X');
    BoardTextView myView = new BoardTextView(myBoard);
    BoardTextView enemyView = new BoardTextView(enemyBoard);
    String myHeader = "Your ocean";
    String enemyHeader = "Player B's ocean";
    String expected = 
      "     Your ocean                           Player B's ocean\n" + 
      "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n"+ 
      "A  | | | | | | | | |  A                A  | | | | | | | | |  A\n"+ 
      "B  | | | | | | | | |  B                B  | | | | | | | | |  B\n"+ 
      "C  | | | | | | | | |  C                C  | | | | | | | | |  C\n"+
      "D  | | | | | | | | |  D                D  | | | | | | | | |  D\n"+
      "E  | | | | | | | | |  E                E  | | | | | | | | |  E\n"+
      "F  | | | | | | | | |  F                F  | | | | | | | | |  F\n"+
      "G  | | | | | | | | |  G                G  | | | | | | | | |  G\n"+
      "H  | | | | | | | | |  H                H  | | | | | | | | |  H\n"+
      "I  | | | | | | | | |  I                I  | | | | | | | | |  I\n"+
      "J  | | | | | | | | |  J                J  | | | | | | | | |  J\n"+
      "K  | | | | | | | | |  K                K  | | | | | | | | |  K\n"+
      "L  | | | | | | | | |  L                L  | | | | | | | | |  L\n"+
      "M  | | | | | | | | |  M                M  | | | | | | | | |  M\n"+
      "N  | | | | | | | | |  N                N  | | | | | | | | |  N\n"+
      "O  | | | | | | | | |  O                O  | | | | | | | | |  O\n"+
      "P  | | | | | | | | |  P                P  | | | | | | | | |  P\n"+
      "Q  | | | | | | | | |  Q                Q  | | | | | | | | |  Q\n"+
      "R  | | | | | | | | |  R                R  | | | | | | | | |  R\n"+
      "S  | | | | | | | | |  S                S  | | | | | | | | |  S\n"+
      "T  | | | | | | | | |  T                T  | | | | | | | | |  T\n"+
      "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n";

    assertEquals(expected, 
    myView.displayMyBoardWithEnemyNextToIt(enemyView, myHeader, enemyHeader));
  }

}
