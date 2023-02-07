package edu.duke.ss1316.battleship;

public class Placement {
    private final Coordinate where;
    private final char orientation;
    public Placement(Coordinate c, char o) {
        checkOrientation(o);
        this.where = c;
        this.orientation = Character.toUpperCase(o);
    }

    public Placement(String descr) {
      if(descr.length() != 3){
        throw new IllegalArgumentException("Please input correct format placement info (3 characters)");
      }
      String substr = descr.substring(0, 2);
      char o = Character.toUpperCase(descr.charAt(2));
      checkOrientation(o);
      this.where = new Coordinate(substr);
      this.orientation = o;
    }

    public Coordinate getWhere() {
        return where;
    }
    public char getOrientation() {
        return orientation;
    }

    public void checkOrientation(char o) {
      if (o == 'h' || o == 'H' || o == 'v' || o == 'V') {
      } else {
        throw new IllegalArgumentException("Please input correct orientation ('h'/'H'/'v'/'V')");
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