package edu.duke.ss1316.battleship;

import java.io.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextPlayerTest {
      private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
        V2ShipFactory shipFactory = new V2ShipFactory();
        return new TextPlayer("A", board, input, output, shipFactory, true);
      }
    
    @Test
    public void test_read_placement() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(10, 20, "b2v\nc8h\na4v\nb2j\nb2h\n", bytes);
        String prompt = "Please enter a location for a ship:\n";
        Placement[] expected = new Placement[4];
        expected[0] = new RectanglePlacement(new Coordinate(1, 2), 'V');
        expected[1] = new RectanglePlacement(new Coordinate(2, 8), 'H');
        expected[2] = new RectanglePlacement(new Coordinate(0, 4), 'V');
        // expected[3] = new Placement(new Coordinate(0, 4), 'j');
        for (int i = 0; i < expected.length - 1; i++) {
          Placement p = player.readPlacement(prompt, "Submarine");
          assertEquals(p, expected[i]);
          assertEquals(prompt, bytes.toString());
          bytes.reset();
        }
        String prompt2 = prompt + "java.lang.IllegalArgumentException: Please input correct orientation\n" + prompt;
        player.readPlacement(prompt, "Submarine");
        assertEquals(prompt2, bytes.toString());
        bytes.reset();
        // assertThrows(IllegalArgumentException.class, () -> player.readPlacement(prompt));
    }

    @Test
    public void test_do_one_placement() throws IOException {
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      TextPlayer player = createTextPlayer(5, 5, "B0H\nA3V\nA4V\n", bytes);
      String prompt = "Player A where do you want to place a Destroyer?\n";
      V2ShipFactory factory = new V2ShipFactory();
      assertEquals(5, player.getView().getBoard().getWidth());
      player.doOnePlacement("Destroyer", (p) -> factory.makeDestroyer(p));
      String expectedBoard = 
      "  0|1|2|3|4\n"+
      "A  | | | |  A\n"+
      "B d|d|d| |  B\n"+
      "C  | | | |  C\n"+
      "D  | | | |  D\n"+
      "E  | | | |  E\n"+
      "  0|1|2|3|4\n";
  
      assertEquals(prompt + expectedBoard, bytes.toString());
      bytes.reset();
      player.doOnePlacement("Destroyer", (p) -> factory.makeDestroyer(p));
      expectedBoard = 
      "  0|1|2|3|4\n"+
      "A  | | |d|  A\n"+
      "B d|d|d|d|  B\n"+
      "C  | | |d|  C\n"+
      "D  | | | |  D\n"+
      "E  | | | |  E\n"+
      "  0|1|2|3|4\n";
      assertEquals(prompt + expectedBoard, bytes.toString());
      bytes.reset();
    }
  
    @Test
    public void test_getName(){
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      TextPlayer player = createTextPlayer(5, 3, "B0H\nA3V\n", bytes);
      assertEquals("A", player.getName());
    }

    @Test
    public void test_isLost() throws IOException {
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      BufferedReader input = new BufferedReader(new StringReader("b2h\n"));
      PrintStream output = new PrintStream(bytes, true);
      Board<Character> board = new BattleShipBoard<Character>(10, 20, 'X');
      V2ShipFactory factory = new V2ShipFactory();
      TextPlayer player =  new TextPlayer("A", board, input, output, factory, true);
      player.doOnePlacement("Submarine", player.shipCreationFns.get("Submarine"));
      board.fireAt(new Coordinate("b2"));
      assertEquals(false, player.isLost());
      board.fireAt(new Coordinate("b3"));
      assertEquals(true, player.isLost());
    }

    @Test
    public void test_attackOneCoordinate() throws IOException {
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      TextPlayer player = createTextPlayer(10, 20, "22\nu0\nb2\n", bytes);
      String prompt = "Player " + player.getName() + ": where would you want to attack\njava.lang.IllegalArgumentException: Input row format is invalid!\n";
      Board<Character> board = new BattleShipBoard<Character>(10, 20, 'X');
      player.attackOneCoordinate(board);
      assertEquals(prompt, bytes.toString());
      bytes.reset();
      player.attackOneCoordinate(board);
      String prompt2 = "Player " + player.getName() + ": where would you want to attack\n" + "Please input a coordinate in bound!\n";
      assertEquals(prompt2, bytes.toString());
    }

    @Test
    public void test_readCoordinate() throws IOException {
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      TextPlayer player = createTextPlayer(10, 20, "z0\nb2\n", bytes);
      String prompt = "Player " + player.getName() + " which ship do you want to move (input any coordiate of the ship)\n";
      prompt += "Please input a coordinate in bound!\n";
      player.readCoordinate(prompt);
      
      assertEquals(prompt, bytes.toString());
    }

    @Test
    public void test_readSonarCoordinate() throws IOException {
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      TextPlayer player = createTextPlayer(10, 20, "z0\nb2\n", bytes);
      String prompt = "Player " + player.getName() + ": please input the coordinate of the sonar scan\n";
      prompt += "Please input a coordinate in bound!\n";
      player.readSonarCoordinate(prompt);
      
      assertEquals(prompt, bytes.toString());
    }

    // @Test
    // public void test_checkOrientation() {
    //   String shipName = "Battlehsip";
    //   RectanglePlacement p = new RectanglePlacement("b0h");
    //   NonRectanglePlacement p2 = new NonRectanglePlacement("b0u");
    //   ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    //   TextPlayer player = createTextPlayer(10, 20, "b0h\nb0u\nb0d\n", bytes);
    //   assertEquals(false, player.checkOrientation(shipName, p));
    //   bytes.reset();
    //   shipName = "Submarine";
    //   assertEquals(false, player.checkOrientation(shipName, p2));
    // }

    @Test
    public void test_readNewPlacement() throws IOException {
      String prompt = "Please input the new placement\n";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      TextPlayer player = createTextPlayer(10, 20, "b0u\nb0h\nb0h\n", bytes);
      player.readNewPlacement("Battleship");
      assertEquals(prompt, bytes.toString());
    }
}