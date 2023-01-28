package edu.duke.ss1316.battleship;

public class Placement {
    private final Coordinate coordinate;
    private final char orientation;
    public Placement(Coordinate c, char o) {
        this.coordinate = c;
        this.orientation = Character.toUpperCase(o);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
    public char getOrientation() {
        return orientation;
    }
}