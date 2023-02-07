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

    public TextPlayer(String name, Board<Character> theBoard, 
    BufferedReader inputSource, PrintStream out, AbstractShipFactory<Character> factory) {
        this.name = name;
        this.theBoard = theBoard;
        this.view = new BoardTextView(theBoard);
        this.inputReader = new BufferedReader(inputSource);
        this.out = out;
        this.shipFactory = factory;
        this.shipsToPlace = new ArrayList<String>();
        this.shipCreationFns = new HashMap<String, Function<Placement, Ship<Character>>>();
        setupShipCreationList();
        setupShipCreationMap();
    }

    public String getName() {
        return this.name;
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
}