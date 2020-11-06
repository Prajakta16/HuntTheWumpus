package maze;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for testing various scenarios is game play.
 */
public class GameTest {

  private Maze imperfectWrappingMaze;
  private int randomSeed;

  @Before
  public void setUp() throws Exception {
    randomSeed = 15000;
  }

  @Test
  public void testCorrectStartPosition() {
    imperfectWrappingMaze = (Maze) new MazeBuilder(3, 4, MazeType.IMPERFECT, true, randomSeed)
                                           .removeInsideWalls(3)
                                           .removeWrappingWalls(2)
                                           .addGold()
                                           .addThieves()
                                           .build();

    Game game = new Game(imperfectWrappingMaze, new Player("PD"), 4, 7);
    assertEquals(4, game.getMaze().getPlayer().getPosition());
  }

  @Test
  public void testGoalReachedForImperfectWrapping() {
    imperfectWrappingMaze = (Maze) new MazeBuilder(3, 4, MazeType.IMPERFECT, true, randomSeed)
                                           .removeInsideWalls(3)
                                           .removeWrappingWalls(2)
                                           .addGold()
                                           .addThieves()
                                           .build();
    Game game = new Game(imperfectWrappingMaze, new Player("PD"), 4, 7);
    assertFalse(game.isGoalReached());

    game.movePlayer(Direction.WEST);
    assertTrue(game.isGoalReached());
  }

  @Test
  public void testGoalReachedForImperfectNonWrapping() {
    Maze imperfectNonWrappingMaze = (Maze) new MazeBuilder(3, 4, MazeType.IMPERFECT,
            false, randomSeed)
                                                   .removeInsideWalls(3)
                                                   .addGold()
                                                   .addThieves()
                                                   .build();

    Game game = new Game(imperfectNonWrappingMaze, new Player("PD"), 1, 3);
    assertFalse(game.isGoalReached());

    game.movePlayer(Direction.EAST);
    assertFalse(game.isGoalReached());

    game.movePlayer(Direction.EAST);
    assertTrue(game.isGoalReached());
  }

  @Test
  public void testGoalReachedForPerfectWrapping() {
    Maze perfectWrappingMaze = (Maze) new MazeBuilder(3, 4, MazeType.PERFECT, true, randomSeed)
                                              .removeInsideWalls()
                                              .addGold()
                                              .addThieves()
                                              .build();

    Game game = new Game(perfectWrappingMaze, new Player("PD"), 3, 7);
    assertFalse(game.isGoalReached());

    game.movePlayer(Direction.NORTH);
    assertFalse(game.isGoalReached());

    game.movePlayer(Direction.NORTH);
    assertTrue(game.isGoalReached());
  }

  @Test
  public void testGoalReachedForPerfectNonWrapping() {
    Maze perfectNonWrappingMaze = (Maze) new MazeBuilder(3, 4, MazeType.PERFECT, false, randomSeed)
                                                 .removeInsideWalls()
                                                 .addGold()
                                                 .addThieves()
                                                 .build();

    Game game = new Game(perfectNonWrappingMaze, new Player("PD"), 0, 4);
    assertFalse(game.isGoalReached());

    game.movePlayer(Direction.SOUTH);
    assertTrue(game.isGoalReached());
  }

  @Test
  public void testGoldValueChangesOnEnteringRoomWithGold() {
    Maze maze = (Maze) new MazeBuilder(3, 4, MazeType.IMPERFECT, false, randomSeed)
                               .removeInsideWalls(3)
                               .addGold()
                               .addThieves()
                               .build();
    Game game = new Game(maze, new Player("PD"), 5, 11);

    game.movePlayer(Direction.SOUTH);
    assertEquals(100, game.getMaze().getPlayer().getGold());
    game.movePlayer(Direction.EAST);
    assertEquals(200, game.getMaze().getPlayer().getGold());
  }

  @Test
  public void testGoldValueRemainsUnChangedOnEnteringRoomWithoutGoldWithoutThief() {
    Maze maze = (Maze) new MazeBuilder(3, 4, MazeType.IMPERFECT, false, randomSeed)
                               .removeInsideWalls(3)
                               .addGold()
                               .addThieves()
                               .build();
    Game game = new Game(maze, new Player("PD"), 4, 6);

    assertEquals(0, game.getMaze().getPlayer().getGold());
    game.movePlayer(Direction.EAST);
    assertEquals(0, game.getMaze().getPlayer().getGold());
  }

  @Test
  public void testGoldValueChangesOnEnteringRoomWithThief() {
    Maze maze = (Maze) new MazeBuilder(3, 4, MazeType.IMPERFECT, false, randomSeed)
                               .removeInsideWalls(3)
                               .addGold()
                               .addThieves()
                               .build();
    Game game = new Game(maze, new Player("PD"), 9, 3);

    assertEquals(100, game.getMaze().getPlayer().getGold());

    game.movePlayer(Direction.NORTH);
    assertEquals(100, game.getMaze().getPlayer().getGold());

    game.movePlayer(Direction.WEST);
    assertEquals(100, game.getMaze().getPlayer().getGold());

    game.movePlayer(Direction.SOUTH);
    assertEquals(90, game.getMaze().getPlayer().getGold());
  }

  @Test
  public void testEnteringRoomWithThiefWhenZeroGold() {
    Maze maze = (Maze) new MazeBuilder(3, 4, MazeType.IMPERFECT, false, randomSeed)
                               .removeInsideWalls(3)
                               .addGold()
                               .addThieves()
                               .build();
    Game game = new Game(maze, new Player("PD"), 4, 3);

    game.movePlayer(Direction.SOUTH);
    assertEquals(0, game.getMaze().getPlayer().getGold());

  }

  @Test
  public void testGoldValueRemainsUnchangedOnRenteringRoomWithGold() {
    Maze maze = (Maze) new MazeBuilder(3, 4, MazeType.IMPERFECT, false, randomSeed)
                               .removeInsideWalls(3)
                               .addGold()
                               .addThieves()
                               .build();
    Game game = new Game(maze, new Player("PD"), 5, 3);

    game.movePlayer(Direction.SOUTH);
    assertEquals(100, game.getMaze().getPlayer().getGold());
    game.movePlayer(Direction.NORTH);
    assertEquals(100, game.getMaze().getPlayer().getGold());
    game.movePlayer(Direction.SOUTH);
    assertEquals(100, game.getMaze().getPlayer().getGold());
  }

  @Test
  public void testReachingAllRooms() {
    Maze maze = (Maze) new MazeBuilder(2, 3, MazeType.IMPERFECT, true, randomSeed)
                               .removeInsideWalls(2)
                               .addGold()
                               .addThieves()
                               .build();
    Game game = new Game(maze, new Player("PD"), 4, 3);
    game.movePlayer(Direction.EAST);
    game.movePlayer(Direction.SOUTH);
    game.movePlayer(Direction.WEST);
    game.movePlayer(Direction.WEST);
    game.movePlayer(Direction.NORTH);
    assertTrue(game.isGoalReached());
  }

  @Test
  public void testExceptionOnInvalidMove() {
    Maze maze = (Maze) new MazeBuilder(3, 4, MazeType.PERFECT, false, randomSeed)
                               .removeInsideWalls()
                               .addGold()
                               .addThieves()
                               .build();
    Game game = new Game(maze, new Player("PD"), 3, 5);

    try {
      game.movePlayer(Direction.EAST);
      fail("Should have thrown an exception");
    } catch (IllegalArgumentException e) {
      //
    }
  }

  @Test
  public void testMovingAcrossBoundaryInWrappingMaze() {
    Maze maze = (Maze) new MazeBuilder(3, 3, MazeType.IMPERFECT, true, randomSeed)
                               .removeInsideWalls(2)
                               .removeWrappingWalls(3)
                               .addGold()
                               .addThieves()
                               .build();
    Game game = new Game(maze, new Player("PD"), 5, 3);
    game.movePlayer(Direction.EAST);
    assertEquals(3, game.getMaze().getPlayer().getPosition());
  }

  @Test
  public void printAvailableDirectionsImperfectNonWrapping() {
    Maze maze = (Maze) new MazeBuilder(3, 4, MazeType.IMPERFECT, false, randomSeed)
                               .removeInsideWalls(3)
                               .addGold()
                               .addThieves()
                               .build();
    Game game = new Game(maze, new Player("PD"), 5, 3);
    assertEquals("1 NORTH 4 WEST 6 EAST 9 SOUTH ", game.printAvailableDirections().toString());
  }

  @Test
  public void printAvailableDirectionsImperfectWrapping() {
    Maze maze = (Maze) new MazeBuilder(3, 4, MazeType.IMPERFECT, true, randomSeed)
                               .removeInsideWalls(3)
                               .removeWrappingWalls(4)
                               .addGold()
                               .addThieves()
                               .build();
    Game game = new Game(maze, new Player("PD"), 7, 11);
    assertEquals("3 NORTH 4 EAST 6 WEST 11 SOUTH ", game.printAvailableDirections().toString());
  }

  @Test
  public void printPlayerStatus() {
    Maze maze = (Maze) new MazeBuilder(3, 4, MazeType.IMPERFECT, false, randomSeed)
                               .removeInsideWalls(3)
                               .addGold()
                               .addThieves()
                               .build();
    Game game = new Game(maze, new Player("PD"), 5, 3);

    game.movePlayer(Direction.SOUTH);
    assertEquals("Player name PD\tCurrent position 9\tGold 100", game.printPlayerStatus().trim());

    game.movePlayer(Direction.NORTH);
    assertEquals("Player name PD\tCurrent position 5\tGold 100", game.printPlayerStatus().trim());
  }
}
