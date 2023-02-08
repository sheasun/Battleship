package edu.duke.ss1316.battleship;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;


public class TextPlayer {
    private final String name;
    private final Board<Character> theBoard;
    private final BoardTextView view;
    private final BufferedReader inputReader;
    private final PrintStream out;
    private final AbstractShipFactory<Character> shipFactory;

    final ArrayList<String> shipsToPlace;
    final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;


    private int move, scan;

    public TextPlayer(String name, Board<Character> theBoard, 
    BufferedReader inputSource, PrintStream out, AbstractShipFactory<Character> factory) {
        this.name = name;
        this.theBoard = theBoard;
        this.view = new BoardTextView(theBoard);
        this.inputReader = inputSource;
        this.out = out;
        this.shipFactory = factory;
        this.shipsToPlace = new ArrayList<String>();
        this.shipCreationFns = new HashMap<String, Function<Placement, Ship<Character>>>();
        setupShipCreationList();
        setupShipCreationMap();
        this.move = 3;
        this.scan = 1;
    }

    public String getName() {
        return this.name;
    }

    public BoardTextView getView() {
        return this.view;
    }

    // remember to check if the input is valid
    public Placement readPlacement(String prompt) throws IOException {
        while (true) {
            out.print(prompt);
            String s = inputReader.readLine();
            try {
                Placement p = new Placement(s);
                return p;
            }
            catch (IllegalArgumentException e) {
                out.println(e);
            }
        }
    }

      public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
        while (true) {
            Placement p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?\n");
            Ship<Character> s = createFn.apply(p);
            String ans = theBoard.tryAddShip(s);
            if (ans == null) {
                break;
            }
            out.println(ans);
        }
        out.print(view.displayMyOwnBoard());
      }

    public void doPlacementPhase() throws IOException {
        String instruction = 
        "---------------------------------------------------------------------------\n" 
        + "Player " + this.name + ": you are going to place the following ships (which are all\n"
        + "rectangular). For each ship, type the coordinate of the upper left\n"
        + "side of the ship, followed by either H (for horizontal) or V (for\n"
        + "vertical).  For example M4H would place a ship horizontally starting\n"
        + "at M4 and going to the right.  You have\n\n" +
        "2 \"Submarines\" ships that are 1x2\n" + "3 \"Destroyers\" that are 1x3\n" + "3 \"Battleships\" that are 1x4\n"
        + "2 \"Carriers\" that are 1x6\n" 
        + "---------------------------------------------------------------------------\n";

        out.print(this.view.displayMyOwnBoard());
        for (int i = 0; i < this.shipsToPlace.size(); ++i) {
            out.print(instruction);
            doOnePlacement(this.shipsToPlace.get(i), this.shipCreationFns.get(this.shipsToPlace.get(i)));
        }
    }

    protected void setupShipCreationMap() {
        this.shipCreationFns.put("Submarine", (p)->shipFactory.makeSubmarine(p));
        this.shipCreationFns.put("Destroyer", (p)->shipFactory.makeDestroyer(p));
        this.shipCreationFns.put("Battleship", (p)->shipFactory.makeBattleship(p));
        this.shipCreationFns.put("Carrier", (p)->shipFactory.makeCarrier(p));
    }

    protected void setupShipCreationList() {
        shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
        shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
        shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
        shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
    }

    public boolean isLost() {
        ArrayList<Ship<Character>> ships = theBoard.getShips();
        for (Ship<Character> ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }
    public boolean checkIfCoordinateInBound(Coordinate c) {
        int row = c.getRow();
        int col = c.getColumn();
        int width = theBoard.getHeight();
        int height = theBoard.getWidth();
        if (row > height || col > width) {
            return false;
        }
        return true;
    }

    public Coordinate attackOneCoordinate(Board<Character> enemyBoard) throws IOException {
        String prompt = "Please input a coordinate to attack\n";
        while (true) {
            out.print(prompt);
            String s = inputReader.readLine();
            try {
                Coordinate c = new Coordinate(s);
                if (checkIfCoordinateInBound(c)) {
                    return c;
                } else {
                    out.println("Please input a coordinate in bound!");
                    continue;
                }
            }
            catch (IllegalArgumentException e) {
                out.println(e);
            }
        }
    }

    /* 
     * taking the action that moving one ship to a new coordinate
     */
    // public Coordinate moveToNewCoordinate(Board<Character> enemyBoard) throws IOException {
    //     String prompt = "Please input a coordinate to move\n";
    //     while (true) {
    //         out.print(prompt);
    //         String s = inputReader.readLine();
    //         try {
    //             Coordinate c = new Coordinate(s);
    //             if (attackedSet.contains(c)) {
    //                 out.print("This coordinate has been attacked, please input another one!\n");
    //                 continue;
    //             } else {
    //                 attackedSet.add(c);
    //                 return c;
    //             }
    //         }
    //         catch (IllegalArgumentException e) {
    //             out.println(e);
    //         }
    //     }
    // }

    /* 
     * the action each player takes
     * there are three options for each player
     * F Fire at a square
     * M Move a ship to another square (2 remaining)
     * S Sonar scan (1 remaining)
     */
    // public void chooseFromOptions(BoardTextView enemyView) throws IOException {
    //     String prompt = "Possible actions for Player " + this.name + "\n" 
    //     + "F Fire at a square\n";
    //     if (this.move > 0) {
    //         prompt += "M Move a ship to another square (" + this.move + " remaining\n";
    //     }
    //     if (this.scan > 0) {
    //         prompt += "S Sonar scan (" + this.scan + " remaining\n";
    //     }
    //     prompt += "Player " + this.name + ", what would you like to do?\n";
    //     prompt += "---------------------------------------------------------------------------\n";
    //     while (true) {
    //         out.print(prompt);
    //         String s = inputReader.readLine();
    //         if (s == "F") {
    //             if (!takeAttackingAction(enemyView)) {
    //                 continue;
    //             } else {
    //                 break;
    //             }
    //         } else if (s == "M") {
    //             if (!takeMovingAction()) {
    //                 continue;
    //             } else {
    //                 break;
    //             }
    //         } else if (s == "S") {
    //             if (!takeScanningAction()) {
    //                 continue;
    //             } else {
    //                 break;
    //             }
    //         } else {
    //             continue;
    //         }
    //     }
    // }

    // public boolean takeAttackingAction(BoardTextView enemyView) throws IOException {
    //     Board<Character> enemyBoard = enemyView.getBoard();
    //     Coordinate attacked = attackOneCoordinate(enemyBoard);
    //     if (attacked == null) {
    //         return false;
    //     }
    //     enemyBoard.fireAt(attacked);
    //     out.print(attackMessage(enemyBoard.whatIsAtForEnemy(attacked)));
    //     return true;
    // }

    // public void takeMovingAction(BoardTextView enemyView) throws IOException {
    //     Board<Character> enemyBoard = enemyView.getBoard();
    //     Coordinate newCoor = moveToNewCoordinate(enemyBoard);
    // }

    /* 
     * the function includes all printed messages and input part from the player
     * in the each turn
     */
    public void playOneTurn(String enemyName, BoardTextView enemyView) throws IOException {
        String myHeader = "Your ocean";
        String enemyHeader = "Player " + enemyName + "'s ocean";
        out.print("---------------------------------------------------------------------------\n");
        out.print("Player " + this.name + "'s turn\n");
        out.print(this.view.displayMyBoardWithEnemyNextToIt(enemyView, myHeader, enemyHeader));
        out.print("---------------------------------------------------------------------------\n");
        // chooseFromOptions(enemyView);
        Board<Character> enemyBoard = enemyView.getBoard();
        Coordinate attacked = attackOneCoordinate(enemyBoard);
        enemyBoard.fireAt(attacked);
        out.print(attackMessage(enemyBoard.whatIsAtForEnemy(attacked)));
        // out.print("---------------------------------------------------------------------------\n");
    }

    public String attackMessage(char c) {
        String ans = "You ";
        if (c == 's' || c == 'd' || c == 'b' || c == 'c') {
            ans += "hit a ";
            switch(c) {
                case 's':
                ans += "submarine";
                break;
                case 'd':
                ans += "destroyer";
                break;
                case 'b':
                ans += "battleship";
                break;
                default:
                ans += "carrier";
                break;
            }
            ans += "!\n";
        } else {
            ans += "missed!\n";
        }
        return ans;
    }
}