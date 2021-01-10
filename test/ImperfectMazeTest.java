import maze.model.FeatureType;
import maze.model.ImperfectMaze;
import maze.model.InterfaceMaze;
import maze.model.MazeBuilder;
import maze.model.MazeType;
import maze.model.Player;
import maze.model.Room;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test class for imperfect maze.
 */
public class ImperfectMazeTest {
  private ImperfectMaze imperfectMaze;
  private MazeBuilder mazeBuilder;
  private static int PERCENT_ROOMS_WITH_GOLD = 20;
  private static int PERCENT_ROOMS_WITH_THIEF = 10;
  private Random random;
  private int randomSeed;
  private Map<FeatureType, Integer> featureMap;


  @Before
  public void setUp() throws Exception {
    imperfectMaze = new ImperfectMaze(4, 5, false);
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
    ImperfectMaze imperfectMaze = new ImperfectMaze(4, 5, false);
    imperfectMaze.wallDemolish(1, 2);
    assertEquals(imperfectMaze.getGridAdjMatrix()[1][2], 1);
  }

  @Test
  public void equalsPerfect() {
    assertFalse(imperfectMaze.equalsPerfect());
  }

  @Test
  public void equalsImperfect() {
    assertTrue(imperfectMaze.equalsImperfect());
  }

  @Test
  public void removeInsideWalls() {
    InterfaceMaze maze = new MazeBuilder(3, 4, MazeType.IMPERFECT, false, randomSeed)
                                 .removeInsideWalls(3)
                                 .addFeatures(featureMap)
                                 .build();
    assertEquals(3, maze.getAllWallsInMaze().size());
  }

  @Test
  public void removeAdditionalInsideWalls() {
    InterfaceMaze maze = new MazeBuilder(3, 4, MazeType.IMPERFECT, false, randomSeed)
                                 .removeInsideWalls(3)
                                 .addFeatures(featureMap)
                                 .build();
    assertEquals(3, maze.getAllWallsInMaze().size());
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
    InterfaceMaze maze = new MazeBuilder(3, 4, MazeType.PERFECT, true, randomSeed)
                                 .removeInsideWalls()
                                 .addFeatures(featureMap)
                                 .build();
    Player player = new Player("Happy");
    maze.assignPlayer(player, 1);
    assertEquals(player, maze.getPlayer());
  }


  @Test
  public void verifyGoldFeature() {
    ImperfectMaze imperfectMaze = new ImperfectMaze(4, 5, false);
    featureMap = new HashMap<>();
    featureMap.put(FeatureType.GOLD, 5);
    imperfectMaze.addFeatures(featureMap, random);

    List<Room> rooms = imperfectMaze.getRooms();
    for (Room room : rooms) {
      if (room.getFeatures().get(FeatureType.GOLD)) {
        assertEquals(13, room.getRoomId());
      }
    }
  }

  @Test
  public void verifyThiefFeature() {
    ImperfectMaze imperfectMaze = new ImperfectMaze(4, 5, false);
    featureMap = new HashMap<>();
    featureMap.put(FeatureType.THIEF, 2);
    imperfectMaze.addFeatures(featureMap, random);

    List<Room> rooms = imperfectMaze.getRooms();
    for (Room room : rooms) {
      if (room.getFeatures().get(FeatureType.THIEF)) {
        assertEquals(13, room.getRoomId());
      }
    }
  }

  @Test
  public void verifyWumpusFeature() {
    ImperfectMaze imperfectMaze = new ImperfectMaze(4, 5, false);
    featureMap = new HashMap<>();
    featureMap.put(FeatureType.WUMPUS, 2);
    imperfectMaze.addFeatures(featureMap, random);

    List<Room> rooms = imperfectMaze.getRooms();
    for (Room room : rooms) {
      if (room.getFeatures().get(FeatureType.WUMPUS)) {
        assertEquals(13, room.getRoomId());
      }
    }
  }

  @Test
  public void verifyBatsFeature() {
    ImperfectMaze imperfectMaze = new ImperfectMaze(3, 4, false);
    featureMap = new HashMap<>();
    featureMap.put(FeatureType.BAT, 20);
    imperfectMaze.addFeatures(featureMap, random);

    List<Room> rooms = imperfectMaze.getRooms();
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
    ImperfectMaze imperfectMaze = new ImperfectMaze(3, 4, false);
    featureMap = new HashMap<>();
    featureMap.put(FeatureType.PIT, 20);
    imperfectMaze.addFeatures(featureMap, random);

    List<Room> rooms = imperfectMaze.getRooms();
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
    assertEquals(4, imperfectMaze.getRow());
  }

  @Test
  public void getCol() {
    assertEquals(5, imperfectMaze.getCol());
  }

  @Test
  public void getCountRooms() {
    assertEquals(20, imperfectMaze.getCountRooms());
  }

  @Test
  public void getPlayer() {
    InterfaceMaze maze = new MazeBuilder(3, 4, MazeType.PERFECT, true, randomSeed)
                                 .removeInsideWalls()
                                 .addFeatures(featureMap)
                                 .build();
    Player player = new Player("Happy");
    maze.assignPlayer(player, 1);
    assertEquals(player, maze.getPlayer());
  }

  @Test
  public void getAllWalls() {
    ImperfectMaze maze = new ImperfectMaze(3, 4, false);

    maze.removeInsideWalls(random);
    Set<int[]> walls = maze.getAllWallsInMaze();
    assertEquals(6, walls.size());

    maze.removeAdditionalInsideWalls(2);
    walls = maze.getAllWallsInMaze();
    assertEquals(4, walls.size());
  }
}
