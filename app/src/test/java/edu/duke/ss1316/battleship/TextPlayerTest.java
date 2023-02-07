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
        V1ShipFactory shipFactory = new V1ShipFactory();
        return new TextPlayer("A", board, input, output, shipFactory);
      }
    
    @Test
    public void test_read_placement() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\nb2j\n", bytes);
        String prompt = "Please enter a location for a ship:\n";
        Placement[] expected = new Placement[3];
        expected[0] = new Placement(new Coordinate(1, 2), 'V');
        expected[1] = new Placement(new Coordinate(2, 8), 'H');
        expected[2] = new Placement(new Coordinate(0, 4), 'V');
        for (int i = 0; i < expected.length; i++) {
          Placement p = player.readPlacement(prompt);
          assertEquals(p, expected[i]);
          assertEquals(prompt, bytes.toString());
          bytes.reset();
        }
        // assertThrows(IllegalArgumentException.class, () -> player.readPlacement(prompt));
    }

    @Disabled
    @Test
    public void test_do_one_placement() throws IOException {
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      TextPlayer player = createTextPlayer(5, 3, "B0H\nA3V\nA3V\n", bytes);
      String prompt = "Player A where do you want to place a Destroyer?\n";
  
      player.doOnePlacement("Destroyer", player.shipCreationFns.get("Destroyer"));
      String expectedBoard = 
      "  0|1|2|3|4\n"+
      "A  | | | |  A\n"+
      "B d|d|d| |  B\n"+
      "C  | | | |  C\n"+
      "  0|1|2|3|4\n";
  
      assertEquals(prompt + expectedBoard, bytes.toString());
      bytes.reset();
  
      player.doOnePlacement("Destroyer", player.shipCreationFns.get("Destroyer"));
      expectedBoard = 
      "  0|1|2|3|4\n"+
      "A  | | |d|  A\n"+
      "B d|d|d|d|  B\n"+
      "C  | | |d|  C\n"+
      "  0|1|2|3|4\n";
  
      assertEquals(prompt + expectedBoard, bytes.toString());
      bytes.reset();
      String warning = "The coordinate is taken by other ship!";
      assertEquals(prompt + warning, bytes.toString());
      bytes.reset();
    }
  
    @Test
    public void test_getName(){
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      TextPlayer player = createTextPlayer(5, 3, "B0H\nA3V\n", bytes);
      assertEquals("A", player.getName());
    }
    
}