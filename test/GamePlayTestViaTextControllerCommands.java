import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import maze.controller.TextBasedGameController;
import maze.model.FeatureType;
import maze.model.GameWumpus;
import maze.model.InterfaceGame;
import maze.model.InterfaceMaze;
import maze.model.MazeBuilder;
import maze.model.MazeType;
import maze.model.Player;

import static org.junit.Assert.assertEquals;

/**
 * Test class for game controller.
 */
public class GamePlayTestViaTextControllerCommands {
  private InterfaceGame gameWumpus;


  @Before
  public void setUp() throws Exception {
    int randomSeed = 15000;
    Map<FeatureType, Integer> featureMap = new HashMap<>();
    featureMap.put(FeatureType.PIT, 25);
    featureMap.put(FeatureType.BAT, 40);
    featureMap.put(FeatureType.WUMPUS, 12);
    InterfaceMaze perfectNonWrappingMaze = new MazeBuilder(3, 4, MazeType.PERFECT,
            false, randomSeed)
                                                   .removeInsideWalls()
                                                   .addFeatures(featureMap)
                                                   .build();
    gameWumpus = new GameWumpus(perfectNonWrappingMaze, new Player("PD"), 6, randomSeed);
  }

  @Test
  public void testEnteringRoomWIthWumpus() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("move north");

    TextBasedGameController controller = new TextBasedGameController(in, out);

    try {
      controller.start(gameWumpus);
      assertEquals("CurrentPlayerInfo{gold=0, name='PD', room=6, weapons={Arrows: 0}"
                           + "WUMPUS-You smell a Wumpus!\nPIT-You feel a draft\n"
                           + "BAT-You smell a something terrible, could be bats!\n"
                           + "Available next steps1: NORTH 5: WEST 3: SOUTH Enter move, "
                           + "shoot or q/quitCurrentPlayerInfo{gold=0, name='PD', room=6, "
                           + "weapons={Arrows: 0}WUMPUS-You smell a Wumpus!\n"
                           + "PIT-You feel a draft\n"
                           + "BAT-You smell a something terrible, could be bats!\n"
                           + "Available next steps1: NORTH 5: WEST 3: SOUTH Enter next direction "
                           + "for playerGame is over!!!", out.toString());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testEnteringRoomWithPit() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("move south");

    TextBasedGameController controller = new TextBasedGameController(in, out);

    try {
      controller.start(gameWumpus);
      assertEquals("CurrentPlayerInfo{gold=0, name='PD', room=6, weapons={Arrows: 0}"
                           + "WUMPUS-You smell a Wumpus!\nPIT-You feel a draft\n"
                           + "BAT-You smell a something terrible, could be bats!\n"
                           + "Available next steps1: NORTH 5: WEST 3: SOUTH Enter move, "
                           + "shoot or q/quitCurrentPlayerInfo{gold=0, name='PD', room=6, "
                           + "weapons={Arrows: 0}WUMPUS-You smell a Wumpus!\n"
                           + "PIT-You feel a draft\n"
                           + "BAT-You smell a something terrible, could be bats!\n"
                           + "Available next steps1: NORTH 5: WEST 3: SOUTH Enter next direction "
                           + "for playerGame is over!!!", out.toString());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testMovingToNextCaveThroughHallway() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("move south");

    TextBasedGameController controller = new TextBasedGameController(in, out);

    try {
      controller.start(gameWumpus);
      assertEquals("CurrentPlayerInfo{gold=0, name='PD', room=6, weapons={Arrows: 0}"
                           + "WUMPUS-You smell a Wumpus!\nPIT-You feel a draft\n"
                           + "BAT-You smell a something terrible, could be bats!\n"
                           + "Available next steps1: NORTH 5: WEST 3: SOUTH Enter move, "
                           + "shoot or q/quitCurrentPlayerInfo{gold=0, name='PD', room=6, "
                           + "weapons={Arrows: 0}WUMPUS-You smell a Wumpus!\n"
                           + "PIT-You feel a draft\n"
                           + "BAT-You smell a something terrible, could be bats!\n"
                           + "Available next steps1: NORTH 5: WEST 3: SOUTH Enter next direction "
                           + "for playerGame is over!!!", out.toString());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testEnteringRoomWithBats() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("move west q");

    TextBasedGameController controller = new TextBasedGameController(in, out);

    try {
      controller.start(gameWumpus);
      assertEquals("CurrentPlayerInfo{gold=0, name='PD', room=6, "
                           + "weapons={Arrows: 0}WUMPUS-You smell a Wumpus!\n"
                           + "PIT-You feel a draft\n"
                           + "BAT-You smell a something terrible, could be bats!\n"
                           + "Available next steps1: NORTH 5: WEST 3: SOUTH Enter move, s"
                           + "hoot or q/quitCurrentPlayerInfo{gold=0, name='PD', room=6, we"
                           + "apons={Arrows: 0}WUMPUS-You smell a Wumpus!\n"
                           + "PIT-You feel a draft\n"
                           + "BAT-You smell a something terrible, could be bats!\n"
                           + "Available next steps1: NORTH 5: WEST 3: SOUTH Enter next "
                           + "direction for playerEnter move, shoot or q/quit", out.toString());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testshootArrowToWumpus() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("shoot north 1");

    TextBasedGameController controller = new TextBasedGameController(in, out);

    try {
      controller.start(gameWumpus);
      assertEquals("CurrentPlayerInfo{gold=0, name='PD', room=6, weapons={Arrows: 0}WUMPUS-"
                           + "You smell a Wumpus!\n"
                           + "PIT-You feel a draft\n"
                           + "BAT-You smell a something terrible, could be bats!\n"
                           + "Available next steps1: NORTH 5: WEST 3: SOUTH Enter move, shoot or "
                           + "q/quitEnter the direction of the arrowEnter distance to be "
                           + "travelledGame is won, come back next time to play more games!!!",
              out.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
