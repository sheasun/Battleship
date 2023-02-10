package edu.duke.ss1316.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
    @Test
    public void test_equals() {
        Placement p1 = new RectanglePlacement("b2v");
        Placement p2 = new RectanglePlacement(new Coordinate(1, 2), 'v');
        Placement p3 = new RectanglePlacement("b2V");
        Placement p4 = new RectanglePlacement("b2h");
        Placement p5 = new RectanglePlacement("b3H");
    
        assertEquals(p1, p1);
        assertEquals(p1, p2);
        assertEquals(p2, p1);
        assertEquals(p1, p3);
        assertEquals(p3, p1);
        assertEquals(p2, p3);
        assertEquals(p3, p2);
    
        assertNotEquals(p1, p4);
        assertNotEquals(p4, p5);
        assertNotEquals(p3, new Coordinate(2, 3));
    }

    @Test
    public void test_invalid_input() {
        assertThrows(IllegalArgumentException.class, () -> new RectanglePlacement("B2J"));
        assertThrows(IllegalArgumentException.class, () -> new RectanglePlacement("B2hh"));
        assertThrows(IllegalArgumentException.class, () -> new NonRectanglePlacement("B2h"));
        assertThrows(IllegalArgumentException.class, () -> new NonRectanglePlacement("B2hh"));
    }

    @Test
    public void test_hashCode(){
      Placement p1 = new RectanglePlacement("B2H");
      Placement p2 = new RectanglePlacement("B2h");
      Placement p3 = new RectanglePlacement("b2H");
      Placement p4 = new RectanglePlacement("b2h");
      Placement p5 = new RectanglePlacement("b2v");
  
      assertEquals(p1.hashCode(), p1.hashCode());
      assertEquals(p1.hashCode(), p2.hashCode());
      assertEquals(p2.hashCode(), p1.hashCode());
      assertEquals(p1.hashCode(), p3.hashCode());
      assertEquals(p3.hashCode(), p1.hashCode());
      assertEquals(p2.hashCode(), p3.hashCode());
      assertEquals(p3.hashCode(), p2.hashCode());
      assertEquals(p1.hashCode(), p4.hashCode());
      assertEquals(p4.hashCode(), p1.hashCode());
      assertNotEquals(p1.hashCode(), p5.hashCode());
      assertNotEquals(p5.hashCode(), p1.hashCode());
    }
  
    @Test
    public void test_toString(){
      Placement p1 = new RectanglePlacement("c1v");
      Placement p2 = new RectanglePlacement("c1V");
      Placement p3 = new RectanglePlacement("d1h");
      Placement p4 = new RectanglePlacement("f2H");
  
      assertEquals("((2, 1), V)", p1.toString());
      assertEquals("((2, 1), V)", p2.toString());
      assertEquals("((3, 1), H)", p3.toString());
      assertEquals("((5, 2), H)", p4.toString());
    }

}