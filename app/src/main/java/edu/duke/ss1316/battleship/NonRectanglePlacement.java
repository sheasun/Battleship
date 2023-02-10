package edu.duke.ss1316.battleship;

public class NonRectanglePlacement extends AbstractPlacement {
    public NonRectanglePlacement(Coordinate c, char orientation) {
        super(c, checkOrientation(orientation));
    }

    static char checkOrientation(char orientation) {
        orientation = Character.toUpperCase(orientation);
        if (orientation == 'U' || orientation == 'D' 
        || orientation == 'R' || orientation == 'L') {
            return orientation;
        } else {
            throw new IllegalArgumentException("Please input correct orientation");
        }
    }

    public NonRectanglePlacement(String str) {
        this(parseCoordinate(str), parseOrientation(str));
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