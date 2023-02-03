package edu.duke.ss1316.battleship;

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
        StringBuilder sb = new StringBuilder();
        sb.append(makeHeader());
        for (int row = 0; row < toDisplay.getHeight(); ++row) {
            char c = (char)(row + 'A');
            String s = c + "  |  " + c;
            sb.append(s);
            sb.append("\n");
        }
        sb.append(makeHeader());
        return sb.toString(); //this is a placeholder for the moment
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

  }
  
