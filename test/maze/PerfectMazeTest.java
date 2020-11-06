package maze;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.Set;

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


  @Before
  public void setUp() throws Exception {
    perfectMaze = new PerfectMaze(4, 5, false);
    random = new Random(15000);
    randomSeed = 15000;
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
  public void addGold() {
    PerfectMaze perfectMaze = new PerfectMaze(4, 5, false);
    perfectMaze.addGold(random);
    int countGold = 0;
    int expectedRoomsWithGold = (int) Math.round(perfectMaze.getCountRooms()
                                                         * (float) PERCENT_ROOMS_WITH_GOLD
                                                         / 100);
    for (int i = 0; i < perfectMaze.getCountRooms(); i++) {
      if (perfectMaze.hasGold(i)) {
        countGold++;
      }
    }
    assertEquals(countGold, expectedRoomsWithGold);
  }

  @Test
  public void addThieves() {
    PerfectMaze perfectMaze = new PerfectMaze(4, 5, false);
    perfectMaze.addThieves(random);
    int countThieves = 0;
    int expectedRoomsWithThieves = (int) Math.round(perfectMaze.getCountRooms()
                                                            * (float) PERCENT_ROOMS_WITH_THIEF
                                                            / 100);
    for (int i = 0; i < perfectMaze.getCountRooms(); i++) {
      if (perfectMaze.hasThief(i)) {
        countThieves++;
      }
    }

    assertEquals(countThieves, expectedRoomsWithThieves);
  }

  @Test
  public void removeInsideWalls() {
    InterfaceMaze maze = new MazeBuilder(3, 4, MazeType.PERFECT, false, randomSeed)
                                 .removeInsideWalls()
                                 .addGold()
                                 .addThieves()
                                 .build();
    assertEquals(6, maze.getAllWallsInMaze().size());
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
    perfectMaze.assignPlayer(player, 0);
    assertEquals(player, perfectMaze.getPlayer());
  }

  @Test
  public void hasGold() {
    PerfectMaze perfectMaze = new PerfectMaze(4, 5, false);
    perfectMaze.addGold(random);

    for (int i = 0; i < perfectMaze.getCountRooms(); i++) {
      if (i == 6 || i == 13 || i == 15 || i == 17) {
        assertTrue(perfectMaze.hasGold(i));
      } else {
        assertFalse(perfectMaze.hasGold(i));
      }
    }
  }

  @Test
  public void hasThief() {
    PerfectMaze perfectMaze = new PerfectMaze(4, 5, false);
    perfectMaze.addThieves(random);

    for (int i = 0; i < perfectMaze.getCountRooms(); i++) {
      if (i == 13 || i == 15) {
        assertTrue(perfectMaze.hasThief(i));
      } else {
        assertFalse(perfectMaze.hasThief(i));
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
    PerfectMaze perfectMaze = new PerfectMaze(4, 5, false);
    perfectMaze.assignPlayer(new Player("Happy"), 0);

    assertEquals(perfectMaze.getPlayer(), new Player("Happy"));
  }

  @Test
  public void getAllWalls() {
    perfectMaze.removeInsideWalls(random);
    Set<int[]> walls = perfectMaze.getAllWallsInMaze();
    assertEquals(11, walls.size());
  }
}
