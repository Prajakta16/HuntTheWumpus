import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import maze.model.FeatureType;
import maze.model.InterfaceMaze;
import maze.model.Maze;
import maze.model.MazeBuilder;
import maze.model.MazeType;
import maze.model.PerfectMaze;
import maze.model.Player;
import maze.model.Room;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test class for perfect maze.
 */
public class PerfectMazeTest {
  private PerfectMaze perfectMaze;
  private MazeBuilder mazeBuilder;
  private static int PERCENT_ROOMS_WITH_GOLD = 20;
  private static int PERCENT_ROOMS_WITH_THIEF = 10;
  private Random random;
  private int randomSeed;
  private Map<FeatureType, Integer> featureMap;


  @Before
  public void setUp() throws Exception {
    perfectMaze = new PerfectMaze(4, 5, false);
    random = new Random(15000);
    randomSeed = 15000;

    featureMap = new HashMap<>();
    featureMap.put(FeatureType.PIT, 25);
    featureMap.put(FeatureType.BAT, 40);
    featureMap.put(FeatureType.WUMPUS, 12);
    featureMap.put(FeatureType.GOLD, 60);
    featureMap.put(FeatureType.THIEF, 10);
  }

  @Test
  public void wallDemolish() {
    PerfectMaze perfectMaze = new PerfectMaze(4, 5, false);
    perfectMaze.wallDemolish(1, 2);
    assertEquals(perfectMaze.getGridAdjMatrix()[1][2], 1);
  }

  @Test
  public void equalsPerfect() {
    assertTrue(perfectMaze.equalsPerfect());
  }

  @Test
  public void equalsImperfect() {
    assertFalse(perfectMaze.equalsImperfect());
  }

  @Test
  public void removeInsideWalls() {
    InterfaceMaze maze = new MazeBuilder(3, 4, MazeType.PERFECT, false, randomSeed)
                                 .removeInsideWalls()
                                 .addFeatures(featureMap)
                                 .build();
    assertEquals(6, maze.getAllWallsInMaze().size());
  }

  @Test
  public void removeWrappingWalls() {
    InterfaceMaze maze = new MazeBuilder(3, 4, MazeType.PERFECT, true, randomSeed)
                                 .removeInsideWalls()
                                 .addFeatures(featureMap)
                                 .build();
    assertEquals(7, maze.getAllWallsInMaze().size());
  }

  @Test
  public void assignPlayer() {
    Maze perfectWrappingMaze = (Maze) new MazeBuilder(3, 4, MazeType.PERFECT, true, randomSeed)
                                              .removeInsideWalls()
                                              .addFeatures(featureMap)
                                              .build();
    perfectWrappingMaze.assignPlayer(new Player("Happy"), 1);

    assertEquals(perfectWrappingMaze.getPlayer(), new Player("Happy"));
  }

  @Test
  public void verifyGoldFeature() {
    PerfectMaze perfectMaze = new PerfectMaze(4, 5, false);
    featureMap = new HashMap<>();
    featureMap.put(FeatureType.GOLD, 5);
    perfectMaze.addFeatures(featureMap, random);

    List<Room> rooms = perfectMaze.getRooms();
    for (Room room : rooms) {
      if (room.getFeatures().get(FeatureType.GOLD)) {
        assertEquals(13, room.getRoomId());
      }
    }
  }

  @Test
  public void verifyThiefFeature() {
    PerfectMaze perfectMaze = new PerfectMaze(7, 8, false);
    featureMap = new HashMap<>();
    featureMap.put(FeatureType.THIEF, 2);
    perfectMaze.addFeatures(featureMap, random);

    List<Room> rooms = perfectMaze.getRooms();
    for (Room room : rooms) {
      if (room.getFeatures().get(FeatureType.THIEF)) {
        assertEquals(13, room.getRoomId());
      }
    }
  }

  @Test
  public void verifyWumpusFeature() {
    PerfectMaze perfectMaze = new PerfectMaze(7, 8, false);
    featureMap = new HashMap<>();
    featureMap.put(FeatureType.WUMPUS, 2);
    perfectMaze.addFeatures(featureMap, random);

    List<Room> rooms = perfectMaze.getRooms();
    for (Room room : rooms) {
      if (room.getFeatures().get(FeatureType.WUMPUS)) {
        assertEquals(13, room.getRoomId());
      }
    }
  }

  @Test
  public void verifyBatsFeature() {
    PerfectMaze perfectMaze = new PerfectMaze(3, 4, false);
    featureMap = new HashMap<>();
    featureMap.put(FeatureType.BAT, 20);
    perfectMaze.addFeatures(featureMap, random);

    List<Room> rooms = perfectMaze.getRooms();
    for (Room room : rooms) {
      if (room.getFeatures().get(FeatureType.BAT)) {
        if (room.getRoomId() != 1 && room.getRoomId() != 7) {
          fail("Correct rooms do not have a bat");
        }
      }
    }
  }

  @Test
  public void verifyPitsFeature() {
    PerfectMaze perfectMaze = new PerfectMaze(3, 4, false);
    featureMap = new HashMap<>();
    featureMap.put(FeatureType.PIT, 20);
    perfectMaze.addFeatures(featureMap, random);

    List<Room> rooms = perfectMaze.getRooms();
    for (Room room : rooms) {
      if (room.getFeatures().get(FeatureType.PIT)) {
        if (room.getRoomId() != 1 && room.getRoomId() != 7) {
          fail("Correct rooms do not have a pits");
        }
      }
    }
  }

  @Test
  public void getRow() {
    assertEquals(4, perfectMaze.getRow());
  }

  @Test
  public void getCol() {
    assertEquals(5, perfectMaze.getCol());
  }

  @Test
  public void getCountRooms() {
    assertEquals(20, perfectMaze.getCountRooms());
  }

  @Test
  public void getPlayer() {
    Maze perfectWrappingMaze = (Maze) new MazeBuilder(3, 4, MazeType.PERFECT, true, randomSeed)
                                              .removeInsideWalls()
                                              .addFeatures(featureMap)
                                              .build();
    perfectWrappingMaze.assignPlayer(new Player("Happy"), 1);

    assertEquals(perfectWrappingMaze.getPlayer(), new Player("Happy"));
  }

  @Test
  public void getAllWalls() {
    perfectMaze.removeInsideWalls(random);
    Set<int[]> walls = perfectMaze.getAllWallsInMaze();
    assertEquals(11, walls.size());
  }
}
