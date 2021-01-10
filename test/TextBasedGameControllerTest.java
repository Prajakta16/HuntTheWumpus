import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.NoSuchElementException;

import maze.controller.TextBasedGameController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test class for text based controller.
 */
public class TextBasedGameControllerTest {
  private MockModel mockModel;
  private StringBuilder inputLog;
  private StringBuilder outputLog;

  @Before
  public void setUp() throws Exception {
    inputLog = new StringBuilder();
    outputLog = new StringBuilder();
    mockModel = new MockModel(inputLog, outputLog);
  }

  @Test
  public void testMovePlayer() {
    Readable in = new StringReader("move west q");
    TextBasedGameController controller = new TextBasedGameController(in, new StringBuffer());
    try {
      controller.start(mockModel);
      String expectedIn = "Moving WEST\n";
      assertEquals(expectedIn, inputLog.toString());
      assertEquals("Active player is player 1\n"
                           + "Active player is player 1\n"
                           + "CurrentPlayerInfo{gold=10, name='playerName', "
                           + "room=2, weapons={Arrows: 5}\n"
                           + "Valid position for are\n"
                           + "{maze.model.Cave@721e0f4f=SOUTH, maze.model.Cave@546a03af=EAST, "
                           + "maze.model.Cave@7907ec20=WEST}\n"
                           + "Valid position for are\n"
                           + "{maze.model.Cave@28864e92=WEST, maze.model.Cave@6ea6d14e=EAST, "
                           + "maze.model.Cave@6ad5c04e=SOUTH}\n"
                           + "Valid position for are\n"
                           + "{maze.model.Cave@725bef66=EAST, maze.model.Cave@2aaf7cc2=SOUTH, "
                           + "maze.model.Cave@6833ce2c=WEST}\n"
                           + "Valid position for are\n"
                           + "{maze.model.Cave@35851384=SOUTH, maze.model.Cave@1888ff2c=EAST, "
                           + "maze.model.Cave@6e3c1e69=WEST}\n"
                           + "Valid position for are\n"
                           + "{maze.model.Cave@649d209a=WEST, maze.model.Cave@6adca536=EAST, "
                           + "maze.model.Cave@357246de=SOUTH}\n"
                           + "Moving player in direction WEST\n"
                           + "Active player is player 1\n"
                           + "Game not over\n"
                           + "Active player is player 1\n"
                           + "Active player is player 1\n"
                           + "CurrentPlayerInfo{gold=10, name='playerName', room=2, "
                           + "weapons={Arrows: 5}\n", outputLog.toString());
      System.out.println(outputLog.toString());
    } catch (IOException e) {
      fail("Input was correct, exception should not have thrown");
    }
  }

  @Test
  public void testMovePlayerStatus() {
    Readable in = new StringReader("move west q");
    TextBasedGameController controller = new TextBasedGameController(in, new StringBuffer());
    try {
      controller.start(mockModel);
      String expectedIn = "Moving WEST\n";
      assertEquals(expectedIn, inputLog.toString());
      assertEquals("Active player is player 1\n"
                           + "Active player is player 1\n"
                           + "CurrentPlayerInfo{gold=10, name='playerName', "
                           + "room=2, weapons={Arrows: 5}\n"
                           + "Valid position for are\n"
                           + "{maze.model.Cave@721e0f4f=SOUTH, maze.model.Cave@546a03af=EAST, "
                           + "maze.model.Cave@7907ec20=WEST}\n"
                           + "Valid position for are\n"
                           + "{maze.model.Cave@28864e92=WEST, maze.model.Cave@6ea6d14e=EAST, "
                           + "maze.model.Cave@6ad5c04e=SOUTH}\n"
                           + "Valid position for are\n"
                           + "{maze.model.Cave@725bef66=EAST, maze.model.Cave@2aaf7cc2=SOUTH, "
                           + "maze.model.Cave@6833ce2c=WEST}\n"
                           + "Valid position for are\n"
                           + "{maze.model.Cave@35851384=SOUTH, maze.model.Cave@1888ff2c=EAST, "
                           + "maze.model.Cave@6e3c1e69=WEST}\n"
                           + "Valid position for are\n"
                           + "{maze.model.Cave@649d209a=WEST, maze.model.Cave@6adca536=EAST, "
                           + "maze.model.Cave@357246de=SOUTH}\n"
                           + "Moving player in direction WEST\n"
                           + "Active player is player 1\n"
                           + "Game not over\n"
                           + "Active player is player 1\n"
                           + "Active player is player 1\n"
                           + "CurrentPlayerInfo{gold=10, name='playerName', room=2, "
                           + "weapons={Arrows: 5}\n", outputLog.toString());
      System.out.println(outputLog.toString());
    } catch (IOException e) {
      fail("Input was correct, exception should not have thrown");
    }
  }

  @Test
  public void testMovePlayerWithInvalidMove() {
    Readable in = new StringReader("move westt q");
    TextBasedGameController controller = new TextBasedGameController(in, new StringBuffer());
    try {
      controller.start(mockModel);
      fail("Input was incorrect, exception should have thrown");
    } catch (NoSuchElementException | IOException e) {
      //
    }
  }

  @Test
  public void testInvalidCommand() {
    Readable in = new StringReader("movea q");
    TextBasedGameController controller = new TextBasedGameController(in, new StringBuffer());
    try {
      controller.start(mockModel);
      assertEquals("Active player is player 1\n"
                           + "Active player is player 1\n"
                           + "CurrentPlayerInfo{gold=10, name='playerName', room=2, "
                           + "weapons={Arrows: 5}\n"
                           + "Active player is player 1\n"
                           + "Active player is player 1\n"
                           + "CurrentPlayerInfo{gold=10, name='playerName', room=2, "
                           + "weapons={Arrows: 5}\n", outputLog.toString());
    } catch (NoSuchElementException | IOException e) {
      fail("Incorrect input should have been handled properly");
    }
  }

  @Test
  public void testshootArrow() {
    Reader in = new StringReader("shoot south 1 q");
    TextBasedGameController controller = new TextBasedGameController(in, new StringBuffer());

    try {
      controller.start(mockModel);
      assertEquals("Command for shooting arror in direction SOUTHfor "
                           + "distance 1\n",
              inputLog.toString());
      assertEquals("Active player - player1\n"
                           + "Active player - player1\n"
                           + "CurrentPlayerInfo{gold=10, name='playerName', room=2, "
                           + "weapons={Arrows: 5}\n"
                           + "Valid position for player are\n"
                           + "[SOUTH, EAST, WEST]\n"
                           + "Valid position for player are\n"
                           + "[WEST, EAST, SOUTH]\n"
                           + "Valid position for player are\n"
                           + "[EAST, SOUTH, WEST]\n"
                           + "Valid position for player are\n"
                           + "[SOUTH, EAST, WEST]\n"
                           + "Valid position for player are\n"
                           + "[WEST, EAST, SOUTH]\n"
                           + "Shooting arrrow in SOUTHfor distance SOUTH\n"
                           + "Active player - player1\n"
                           + "Game is not yet won\n"
                           + "Game not over\n"
                           + "Active player - player1\n"
                           + "Active player - player1\n"
                           + "CurrentPlayerInfo{gold=10, name='playerName', room=2, "
                           + "weapons={Arrows: 5}\n",
              outputLog.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testNegativeShootDistance() {
    Reader in = new StringReader("shoot south 0.25 q");
    TextBasedGameController controller = new TextBasedGameController(in, new StringBuffer());

    try {
      controller.start(mockModel);
      assertEquals("Command for shooting arror in direction "
                           + "SOUTHfor distance -1\n",
              inputLog.toString());
      assertEquals("Active player - player1\n"
                           + "Active player - player1\n"
                           + "CurrentPlayerInfo{gold=10, name='playerName', room=2, "
                           + "weapons={Arrows: 5}\n"
                           + "Valid position for player are\n"
                           + "[SOUTH, EAST, WEST]\n"
                           + "Valid position for player are\n"
                           + "[WEST, EAST, SOUTH]\n"
                           + "Valid position for player are\n"
                           + "[EAST, SOUTH, WEST]\n"
                           + "Valid position for player are\n"
                           + "[SOUTH, EAST, WEST]\n"
                           + "Valid position for player are\n"
                           + "[WEST, EAST, SOUTH]\n"
                           + "Shooting arrrow in SOUTHfor distance SOUTH\n"
                           + "Active player - player1\n"
                           + "Game is not yet won\n"
                           + "Game not over\n"
                           + "Active player - player1\n"
                           + "Active player - player1\n"
                           + "CurrentPlayerInfo{gold=10, name='playerName', room=2, "
                           + "weapons={Arrows: 5}\n",
              outputLog.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testNeonNumericShootDistance() {
    Reader in = new StringReader("shoot south b q");
    TextBasedGameController controller = new TextBasedGameController(in, new StringBuffer());

    try {
      controller.start(mockModel);
      assertEquals("Command for shooting arror in direction "
                           + "SOUTHfor distance -1\n", inputLog.toString());
      assertEquals("Active player - player1\n"
                           + "Active player - player1\n"
                           + "CurrentPlayerInfo{gold=10, name='playerName', room=2, "
                           + "weapons={Arrows: 5}\n"
                           + "Valid position for player are\n"
                           + "[SOUTH, EAST, WEST]\n"
                           + "Valid position for player are\n"
                           + "[WEST, EAST, SOUTH]\n"
                           + "Valid position for player are\n"
                           + "[EAST, SOUTH, WEST]\n"
                           + "Valid position for player are\n"
                           + "[SOUTH, EAST, WEST]\n"
                           + "Valid position for player are\n"
                           + "[WEST, EAST, SOUTH]\n"
                           + "Shooting arrrow in SOUTHfor distance SOUTH\n"
                           + "Active player - player1\n"
                           + "Game is not yet won\n"
                           + "Game not over\n"
                           + "Active player - player1\n"
                           + "Active player - player1\n"
                           + "CurrentPlayerInfo{gold=10, name='playerName', room=2, "
                           + "weapons={Arrows: 5}\n",
              outputLog.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
