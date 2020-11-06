package maze;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.Set;

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


  @Before
  public void setUp() throws Exception {
    imperfectMaze = new ImperfectMaze(4, 5, false);
    random = new Random(15000);
    randomSeed = 15000;
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
  public void addGold() {
    ImperfectMaze maze = new ImperfectMaze(4, 5, false);
    maze.addGold(random);
    int countGold = 0;
    int expectedRoomsWithGold = (int) Math.round(maze.getCountRooms()
                                                         * (float) PERCENT_ROOMS_WITH_GOLD
                                                         / 100);
    for (int i = 0; i < maze.getCountRooms(); i++) {
      if (maze.hasGold(i)) {
        countGold++;
      }
    }
    assertEquals(countGold, expectedRoomsWithGold);
  }

  @Test
  public void addThieves() {
    ImperfectMaze maze = new ImperfectMaze(4, 5, false);
    maze.addThieves(random);
    int countThieves = 0;
    int expectedRoomsWithThieves = (int) Math.round(maze.getCountRooms()
                                                            * (float) PERCENT_ROOMS_WITH_THIEF
                                                            / 100);
    for (int i = 0; i < maze.getCountRooms(); i++) {
      if (maze.hasThief(i)) {
        countThieves++;
      }
    }

    assertEquals(countThieves, expectedRoomsWithThieves);
  }

  @Test
  public void removeInsideWalls() {
    InterfaceMaze maze = new MazeBuilder(3, 4, MazeType.IMPERFECT, false, randomSeed)
                                 .removeInsideWalls(3)
                                 .addGold()
                                 .addThieves()
                                 .build();
    assertEquals(3, maze.getAllWallsInMaze().size());
  }

  @Test
  public void removeAdditionalInsideWalls() {
    InterfaceMaze maze = new MazeBuilder(3, 4, MazeType.IMPERFECT, false, randomSeed)
                                 .removeInsideWalls(3)
                                 .addGold()
                                 .addThieves()
                                 .build();
    assertEquals(3, maze.getAllWallsInMaze().size());
  }

  @Test
  public void removeWrappingWalls() {
    InterfaceMaze maze = new MazeBuilder(3, 4, MazeType.PERFECT, true, randomSeed)
                                 .removeInsideWalls()
                                 .addGold()
                                 .addThieves()
                                 .build();
    assertEquals(7, maze.getAllWallsInMaze().size());
  }

  @Test
  public void assignPlayer() {
    Player player = new Player("Happy");
    imperfectMaze.assignPlayer(player, 0);
    assertEquals(player, imperfectMaze.getPlayer());
  }

  @Test
  public void hasGold() {
    ImperfectMaze maze = new ImperfectMaze(3, 4, false);
    maze.addGold(random);

    for (int i = 0; i < maze.getCountRooms(); i++) {
      if (i == 1 || i == 7) {
        assertTrue(maze.hasGold(i));
      } else {
        assertFalse(maze.hasGold(i));
      }
    }
  }

  @Test
  public void hasThief() {
    ImperfectMaze maze = new ImperfectMaze(3, 4, false);
    maze.addThieves(random);

    for (int i = 0; i < maze.getCountRooms(); i++) {
      if (i == 1) {
        assertTrue(maze.hasThief(i));
      } else {
        assertFalse(maze.hasThief(i));
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
    ImperfectMaze maze = new ImperfectMaze(3, 4, false);
    maze.assignPlayer(new Player("Happy"), 0);

    assertEquals(maze.getPlayer(), new Player("Happy"));
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
