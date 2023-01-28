package edu.duke.ss1316.battleship;

public class Coordinate {
    private final int row;
    private final int column;

    public Coordinate(int r, int c) {
        this.row = r;
        this.column = c;
    }

    public Coordinate(String descr) {
        int len = descr.length();
        if (len != 2) {
            throw new IllegalArgumentException("Input length must be equal to 2!");
        }
        char rindex = Character.toUpperCase(descr.charAt(0));
        char cindex = descr.charAt(1);
        if (rindex < 'A' || rindex > 'Z') {
            throw new IllegalArgumentException("Input row format is invalid!");
        }
        if (!Character.isDigit(cindex)) {
            throw new IllegalArgumentException("Input column format is invalid!");
        }
        this.row = rindex - 'A';
        this.column = cindex - '0';
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
      if (o.getClass().equals(getClass())) {
        Coordinate c = (Coordinate) o;
        return row == c.row && column == c.column;
      }
      return false;
    }

    @Override
    public String toString() {
      return "("+row+", " + column+")";
    }
    @Override
    public int hashCode() {
      return toString().hashCode();
    }
}