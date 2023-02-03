package edu.duke.ss1316.battleship;

public class Placement {
    private final Coordinate coordinate;
    private final char orientation;
    public Placement(Coordinate c, char o) {
        this.coordinate = c;
        this.orientation = Character.toUpperCase(o);
    }

    public Placement(String descr) {
      if(descr.length() != 3){
        throw new IllegalArgumentException("Please input correct format placement info (3 characters)");
      }
      String substr = descr.substring(0, 2);
      this.coordinate = new Coordinate(substr);
      this.orientation = Character.toUpperCase(descr.charAt(2));
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
    public char getOrientation() {
        return orientation;
    }

    @Override
    public boolean equals(Object o) {
      if (o.getClass().equals(getClass())) {
        Placement p = (Placement) o;
        return this.coordinate.equals(p.coordinate) && this.orientation == p.orientation;
      }
      return false;
    }

    @Override
    public String toString() {
      return "("+ this.coordinate.toString() + ", " + orientation +")";
    }
    @Override
    public int hashCode() {
      return toString().hashCode();
    }
}