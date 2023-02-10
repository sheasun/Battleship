package edu.duke.ss1316.battleship;

public class RectanglePlacement extends AbstractPlacement {
    // private final char orientation;
    public RectanglePlacement(Coordinate c, char orientation) {
        super(c, checkOrientation(orientation));
        orientation = Character.toUpperCase(orientation);
    }
    public RectanglePlacement(String str) {
        this(parseCoordinate(str), parseOrientation(str));
    }

    static char checkOrientation(char orientation) {
        orientation = Character.toUpperCase(orientation);
        if (orientation == 'V' || orientation == 'H') {
            return orientation;
        } else {
            throw new IllegalArgumentException("Please input correct orientation");
        }
    }

    public char getOrientation() {
        return this.orientation;
    }

    static Coordinate parseCoordinate(String str) {
        if (str.length() != 3) {
            throw new IllegalArgumentException("Please input correct format placement info (3 characters)");
        }
        String substr = str.substring(0, 2);
        return new Coordinate(substr);
    }
    
    static char parseOrientation(String str) {
        char o = Character.toUpperCase(str.charAt(2));
        return o;
    }
}