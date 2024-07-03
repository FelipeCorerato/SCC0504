
// GameTest.java
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {
  private Game game;
  private Room outside, theater, pub, lab, office;

  @BeforeEach
  public void setUp() {
    game = new Game();
    createRooms();
  }

  private void createRooms() {
    outside = new Room("outside the main entrance of the university");
    theater = new Room("in a lecture theater");
    pub = new Room("in the campus pub");
    lab = new Room("in a computing lab");
    office = new Room("in the computing admin office");

    outside.setExit("east", theater);
    outside.setExit("south", lab);
    outside.setExit("west", pub);

    theater.setExit("west", outside);

    pub.setExit("east", outside);

    lab.setExit("north", outside);
    lab.setExit("east", office);

    office.setExit("west", lab);

    outside.setItem("a rock");
    theater.setItem("a projector");
    pub.setItem("a beer");
    lab.setItem("a computer");
    office.setItem("a desk");

    game.setCurrentRoom(outside);
  }

  @Test
  public void testLook() {
    assertEquals("You are outside the main entrance of the university.\nExits: east south west\nYou see here: a rock",
        game.getCurrentRoom().getLongDescription());
    game.processCommand(new Command(CommandWord.LOOK, null));
  }

  @Test
  public void testBack() {
    game.processCommand(new Command(CommandWord.GO, "east"));
    assertEquals(theater, game.getCurrentRoom());
    game.processCommand(new Command(CommandWord.BACK, null));
    assertEquals(outside, game.getCurrentRoom());
  }
}
