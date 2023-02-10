package edu.duke.ss1316.battleship;

public class Placement {
    private final Coordinate where;
    private final char orientation;

    // private final boolean rectangle;

    public Placement(Coordinate c, char o) {
      // this.rectangle = rectangle;
      this.orientation = Character.toUpperCase(o);
      this.where = c;
      checkOrientation();
    }

    public Placement(String descr) {
      if(descr.length() != 3){
        throw new IllegalArgumentException("Please input correct format placement info (3 characters)");
      }
      String substr = descr.substring(0, 2);
      char o = Character.toUpperCase(descr.charAt(2));
      // this.rectangle = rectangle;
      this.where = new Coordinate(substr);
      this.orientation = o;
      checkOrientation();
    }

    public Coordinate getWhere() {
        return where;
    }
    public char getOrientation() {
        return orientation;
    }

    public void checkOrientation() {
      if (this.orientation == 'H' || this.orientation == 'V' || 
      this.orientation == 'U' || this.orientation == 'D' 
      || this.orientation == 'R' || this.orientation == 'L') {
        return;
      } else {
        throw new IllegalArgumentException("Please input correct orientation");
      }
    }
    @Override
    public boolean equals(Object o) {
      if (o.getClass().equals(getClass())) {
        Placement p = (Placement) o;
        return this.where.equals(p.where) && this.orientation == p.orientation;
      }
      return false;
    }

    @Override
    public String toString() {
      return "("+ this.where.toString() + ", " + orientation +")";
    }
    @Override
    public int hashCode() {
      return toString().hashCode();
    }
}