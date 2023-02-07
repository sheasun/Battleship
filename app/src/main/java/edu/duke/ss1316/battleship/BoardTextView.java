package edu.duke.ss1316.battleship;

import java.util.function.Function;

/**
 * This class handles textual display of
 * a Board (i.e., converting it to a string to show
 * to the user).
 * It supports two ways to display the Board:
 * one for the player's own board, and one for the 
 * enemy's board.
 */
public class BoardTextView {
    /**
     * The Board to display
     */
    private final Board<Character> toDisplay;
 /**
   * Constructs a BoardView, given the board it will display.
   * 
   * @param toDisplay is the Board to display
   * @throws IllegalArgumentException if the board is larger than 10x26.  
   */
  public BoardTextView(Board<Character> toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException(
          "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }
  public String displayMyOwnBoard() {
    return displayAnyBoard((c)->toDisplay.whatIsAtForSelf(c));
  }

  public String displayEnemyBoard() {
    return displayAnyBoard((c)->toDisplay.whatIsAtForEnemy(c));
  }

  public Board<Character> getBoard() {
    return this.toDisplay;
  }

    protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeHeader());
        char r = 'A';
        for (int row = 0; row < toDisplay.getHeight(); ++row) {
          sb.append((char)(r+ row));
          sb.append(" ");
          for (int col = 0; col < toDisplay.getWidth(); ++col) {
            Coordinate c = new Coordinate(row, col);
            if (getSquareFn.apply(c) == null) {
              sb.append(" ");
            } else {
              sb.append(getSquareFn.apply(c));
            }
            if (col != toDisplay.getWidth() - 1) {
              sb.append("|");
            }
          }
          sb.append(" ");
          sb.append((char)(r + row));
          sb.append("\n");
        }
        sb.append(makeHeader());
        return sb.toString();
     }

/**
   * This makes the header line, e.g. 0|1|2|3|4\n
   * 
   * @return the String that is the header line for the given board
   */
  public String makeHeader() {
    StringBuilder ans = new StringBuilder("  "); // README shows two spaces at
    String sep = ""; //start with nothing to separate, then switch to | to separate
    for (int column = 0; column < toDisplay.getWidth(); column++) {
      ans.append(sep);
      ans.append(column);
      sep = "|";
    }
    ans.append("\n");
    return ans.toString();
  }

  public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader) {
    String[] myLines = displayMyOwnBoard().split("\n");
    String[] enemyLines = enemyView.displayEnemyBoard().split("\n");

    int len = myLines.length;

    StringBuilder sb = new StringBuilder();
    sb.append(makeHeaderHelper(myHeader, enemyHeader));
    sb.append(makeBoundHelper(myLines[0], enemyLines[0]));
    for (int i = 1; i < len - 1; ++i) {
      sb.append(makeNewLineHelper(myLines[i], enemyLines[i]));
    }
    sb.append(makeBoundHelper(myLines[len - 1], enemyLines[len - 1]));
    return sb.toString();
  }

  // boards' header, e.g. "Your ocean"  "Player B's ocean"
  public String makeHeaderHelper(String myHeader, String enemyHeader) {
    StringBuilder sb = new StringBuilder();
    sb.append(makeSpaceHelper(5));
    sb.append(myHeader);
    int width = this.toDisplay.getWidth();
    sb.append(makeSpaceHelper((2 * width + 19 + 3) - myHeader.length() - 5));
    sb.append(enemyHeader);
    sb.append("\n");
    return sb.toString();
  }

  public String makeBoundHelper(String myLine, String enemyLine) {
    StringBuilder sb = new StringBuilder();
    sb.append(myLine);
    sb.append(makeSpaceHelper(18));
    sb.append(enemyLine);
    sb.append("\n");
    return sb.toString();
  }

  // add needed number of space
  public String makeSpaceHelper(int num) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < num; ++i) {
      sb.append(" ");
    }
    return sb.toString();
  }

  public String makeNewLineHelper(String myLine, String enemyLine) {
    StringBuilder sb = new StringBuilder();
    sb.append(myLine);
    sb.append(makeSpaceHelper(16));
    sb.append(enemyLine);
    sb.append("\n");
    return sb.toString();
  }

  }
  
