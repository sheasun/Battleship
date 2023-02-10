package edu.duke.ss1316.battleship;

public abstract class AbstractPlacement implements Placement {
    protected Coordinate where;
    protected char orientation;
    public AbstractPlacement(Coordinate c, char orientation) {
        this.where = c;
        this.orientation = orientation;
    }
    public Coordinate getWhere() {
        return this.where;
    }

    @Override
    public boolean equals(Object o) {
      if (o.getClass().equals(getClass())) {
        Placement p = (Placement) o;
        return this.where.equals(p.getWhere()) && this.orientation == p.getOrientation();
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