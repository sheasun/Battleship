package edu.duke.ss1316.battleship;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;
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

    // true: human; false: computer
    private final boolean human;
    private final ArrayList<Placement> computerShips;

    private int move, scan;
    // private Random random;

    private int computerHitRow, computerHitCol;

    public TextPlayer(String name, Board<Character> theBoard, 
    BufferedReader inputSource, PrintStream out, AbstractShipFactory<Character> factory, boolean human) {
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
        this.scan = 3;
        this.human = human;
        computerShips = new ArrayList<>();
        this.computerHitRow = 0;
        this.computerHitCol = 0;
    }

    public String getName() {
        return this.name;
    }

    public BoardTextView getView() {
        return this.view;
    }

    public Placement readPlacement(String prompt, String shipName) throws IOException {
        while (true) {
            out.print(prompt);
            String s = inputReader.readLine();
            try {
                Placement p;
                if (shipName.equals("Submarine") ||shipName.equals("Destroyer")) {
                    p = new RectanglePlacement(s);
                } else {
                    p = new NonRectanglePlacement(s);
                }
                return p;
            }
            catch (IllegalArgumentException e) {
                out.println(e);
            }
        }
    }

      public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
        while (true) {
            Placement p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?\n", shipName);
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

    public Coordinate attackOneCoordinate(Board<Character> enemyBoard) throws IOException {
        String prompt = "Player " + this.name + ": where would you want to attack\n";
        // while (true) {
            out.print(prompt);
            String s = inputReader.readLine();
            try {
                Coordinate c = new Coordinate(s);

                if (theBoard.checkIfInBound(c.getRow(), c.getColumn())) {
                    return c;
                } else {
                    out.println("Please input a coordinate in bound!");
                    return null;
                    // continue;
                }
            }
            catch (IllegalArgumentException e) {
                out.println(e);
            }
            return null;
        // }
    }

    /* 
     * do attacking action
     * if successfully done, return true
     * else return false
     */
    public boolean takeAttackingAction(BoardTextView enemyView) throws IOException {
        Board<Character> enemyBoard = enemyView.getBoard();
        Coordinate attacked = attackOneCoordinate(enemyBoard);
        if (attacked == null) {
            return false;
        }
        enemyBoard.fireAt(attacked);
        out.print(attackMessage(enemyBoard.whatIsAtForEnemy(attacked)));
        return true;
    }

    public Ship<Character> readCoordinate(String prompt) throws IOException {
            out.print(prompt);
            String s = inputReader.readLine();
            try {
                Coordinate c = new Coordinate(s);
                if (theBoard.checkIfInBound(c.getRow(), c.getColumn())) {
                    Ship<Character> ship = theBoard.getShip(c);
                    return ship;
                } else {
                    return null;
                }
            }
            catch (IllegalArgumentException e) {
                out.println(e);
            }
            return null;
    }

    /* 
     * in the moving action
     * read the new placement from the player
     * if it is valid, return the placement
     * else return null
     */
    public Placement readNewPlacement(String shipName) throws IOException {
        String prompt = "Please input the new placement\n";
        out.print(prompt);
        String s = inputReader.readLine();
        try{
            Placement p;
            if (shipName.equals("Submarine") || shipName.equals("Destroyer")) {
                p = new RectanglePlacement(s);
            } else {
                p = new NonRectanglePlacement(s);
            }
            return p;
          }
          catch(IllegalArgumentException e){
            this.out.print(e);
          }
        return null;
    }

    public boolean moveToNewPlacement(Ship<Character> ship, Placement p) throws IOException {
        this.theBoard.removeShip(ship);
        String shipName = ship.getName();
        Ship<Character> newShip = this.shipCreationFns.get(shipName).apply(p);
        LinkedHashMap<Coordinate, Boolean> map = ship.getMyPieces();
        newShip.updatePieces(map);
        String ans = theBoard.tryAddShip(newShip);
        if (ans != null) {
            out.println(ans);
            theBoard.tryAddShip(ship);
            return false;
        }
        return true;
    }

    /* 
     * NOT DONE
     * readCoordinate: the player input one coordinate
     * check if the coordinate has been taken by any ship
     * readNewPlacement: if so, the player input the new placement
     * check if the new placement is valid
     * moveToNewPlacement: if so, remove the current ship from the ship list and 
     * add the new ship into the ship list (if succeeded, return true; else false)
     */
    public boolean takeMovingAction(BoardTextView enemyView) throws IOException {
        String prompt = "Player " + this.name + " which ship do you want to move (input any coordiate of the ship)\n";
        Ship<Character> ship = readCoordinate(prompt);
        if (ship == null) {
            return false;
        }
        Placement newPlacement = readNewPlacement(ship.getName());
        if (newPlacement == null) {
            return false;
        }
        return moveToNewPlacement(ship, newPlacement);
    }

    /* 
     * in the sonar scanning action
     * read the coordinate of the sonar scan
     */
    public Coordinate readSonarCoordinate(String prompt) throws IOException {
        out.print(prompt);
        String s = inputReader.readLine();
        try {
            Coordinate c = new Coordinate(s);
            if (theBoard.checkIfInBound(c.getRow(), c.getColumn())) {
                return c;
            } else {
                return null;
            }
        }
        catch (IllegalArgumentException e) {
            out.println(e);
        }
        return null;
}

    /* 
     * read the coordinate from the player
     * if it is valid, go to the enemy's board to calculate the squares of each type of ship
     */
    public boolean takeScanningAction(BoardTextView enemyView) throws IOException {
        String prompt = "Player " + this.name + ": please input the coordinate of the sonar scan\n";
        Coordinate c = readSonarCoordinate(prompt);
        if (c == null) {
            return false;
        }
        Board<Character> enemyBoard = enemyView.getBoard();
        HashMap<String, Integer> map = enemyBoard.findShipsBySonar(c);
        String ans = "";
        ans += "Submarines occupy " + Integer.toString(map.get("Submarine")) + " squares\n";
        ans += "Destroyers occupy " + Integer.toString(map.get("Destroyer")) + " squares\n";
        ans += "Battleships occupy " + Integer.toString(map.get("Battleship")) + " squares\n";
        ans += "Carriers occupy " + Integer.toString(map.get("Carrier")) + " squares\n";
        out.print(ans);
        return true;
    }

    /* 
     * the action each player takes
     * there are three options for each player
     * F Fire at a square
     * M Move a ship to another square (2 remaining)
     * S Sonar scan (1 remaining)
     */
    public void chooseFromOptions(BoardTextView enemyView) throws IOException {
        String prompt = "---------------------------------------------------------------------------\n"
        + "Possible actions for Player " + this.name + "\n\n" 
        + "F Fire at a square\n";
        if (this.move > 0) {
            prompt += "M Move a ship to another square (" + this.move + " remaining)\n";
        }
        if (this.scan > 0) {
            prompt += "S Sonar scan (" + this.scan + " remaining)\n";
        }
        prompt += "Player " + this.name + ", what would you like to do?\n";
        prompt += "---------------------------------------------------------------------------\n";
        while (true) {
            out.print(prompt);
            String s = inputReader.readLine();
            s = s.toUpperCase();
            if (s.equals("F")) {
                if (!takeAttackingAction(enemyView)) {
                    continue;
                } else {
                    break;
                }
            } else if (s.equals("M") && this.move > 0) {
                if (!takeMovingAction(enemyView)) {
                    continue;
                } else {
                    --this.move;
                    break;
                }
            } else if (s.equals("S") && this.scan > 0) {
                if (!takeScanningAction(enemyView)) {
                    continue;
                } else {
                    --this.scan;
                    break;
                }
            } else {
                continue;
            }
        }
    }
    
    /* 
     * the function includes all printed messages and input part from the player
     * in the each turn
     */
    public void playOneTurn(String enemyName, BoardTextView enemyView) throws IOException {
        if (human) {
            String myHeader = "Your ocean";
            String enemyHeader = "Player " + enemyName + "'s ocean";
            out.print("---------------------------------------------------------------------------\n");
            out.print("Player " + this.name + "'s turn\n");
            out.print(this.view.displayMyBoardWithEnemyNextToIt(enemyView, myHeader, enemyHeader));
            chooseFromOptions(enemyView);
            out.print(this.view.displayMyBoardWithEnemyNextToIt(enemyView, myHeader, enemyHeader));
        } else {
            actionByComputer(enemyView);
        }

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

    public void checkBeforeDoPlacement() throws IOException {
        if (human) {
            doPlacementPhase();
        } else {
            // computer action
            actionWhenDoPlacement();
        }
    }

    /* 
     * actions taken by the computer
     */
    public void actionByComputer(BoardTextView enemyView) throws IOException {
        actionWhenDoAttacking(enemyView);
    }
    public void createComputerShips() {
        // #1
        computerShips.add(new RectanglePlacement("a0h"));
        // #2
        computerShips.add(new RectanglePlacement("a2h"));
        // #3
        computerShips.add(new RectanglePlacement("a4h"));
        // #4
        computerShips.add(new RectanglePlacement("a7h"));
        // #5
        computerShips.add(new RectanglePlacement("b0h"));
        // #6
        computerShips.add(new NonRectanglePlacement("b3d"));
        // #7
        computerShips.add(new NonRectanglePlacement("b5l"));
        // #8
        computerShips.add(new NonRectanglePlacement("b7r"));
        // #9
        computerShips.add(new NonRectanglePlacement("c0u"));
        // #10
        computerShips.add(new NonRectanglePlacement("c2d"));
    }

    /* 
     * computer player
     * do placement phase
     */
    public void actionWhenDoPlacement() throws IOException {
        createComputerShips();
        for(int i = 0; i < this.shipsToPlace.size(); ++i){
            String shipName = this.shipsToPlace.get(i);
            Ship<Character> ship = this.shipCreationFns.get(shipName).apply(this.computerShips.get(i));
            this.theBoard.tryAddShip(ship);
        }
    }

    /* 
     * computer action
     * do attack
     */
    public void actionWhenDoAttacking(BoardTextView enemyView) throws IOException {
        Board<Character> enemyBoard = enemyView.getBoard();
        if (this.computerHitCol == enemyBoard.getWidth()) {
            this.computerHitCol = 0;
            this.computerHitRow = (this.computerHitRow + 1) % enemyBoard.getHeight();
        }
        Coordinate coor = new Coordinate(this.computerHitRow, this.computerHitCol);
        enemyBoard.fireAt(coor);
        ++this.computerHitCol;
        char c = enemyBoard.whatIsAtForEnemy(coor);
        printActionMessage(coor, c);
    }

    /* 
     * computer player
     * action message
     */
    public void printActionMessage(Coordinate coor, char c) {
        String ans = "Player " + this.name + " hit your ";
        boolean hit = true;
        switch(c) {
            case 's':
            ans += "submarine at ";
            break;
            case 'd':
            ans += "destroyer at ";
            break;
            case 'b':
            ans += "battleship at ";
            break;
            case 'c':
            ans += "carrier at ";
            break;
            default:
            ans = "Player " + this.name + " missed\n";
            hit = false;
        }
        if (hit) {
            ans += coor + "!\n";
        }
        out.print(ans);
    }
}