package maze;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Class for testing the player playing the maze.
 */
public class PlayerTest {
  private Player player;
  private int randomSeed = 10000;

  @Before
  public void setUp() {
    player = new Player("PD");
  }

  @Test
  public void getGold() {
    assertEquals(0, player.getGold());
  }

  @Test
  public void getPosition() {
    Maze maze = (Maze) new MazeBuilder(3, 4, MazeType.IMPERFECT, false, randomSeed)
                               .removeInsideWalls(3)
                               .addGold()
                               .addThieves()
                               .build();
    Game game = new Game(maze, new Player("PD"), 5, 3);
    assertEquals(5, game.getMaze().getPlayer().getPosition());
    game.movePlayer(Direction.SOUTH);
    assertEquals(9, game.getMaze().getPlayer().getPosition());
  }

  @Test
  public void setPosition() {
    player.setPosition(3);
    assertEquals(3, player.getPosition());
  }

  @Test
  public void collectGold() {
    int currentGold = player.getGold();
    player.collectGold();
    assertEquals(currentGold + 100, player.getGold());
  }

  @Test
  public void loseGoldToThief() {
    int currentGold = player.getGold();
    player.loseGoldToThief();
    assertEquals((int) (currentGold * (90 / 100.0f)), player.getGold());
  }

  @Test
  public void testEquals() {
    Player player1 = new Player("ABC");
    Player player2 = new Player("ABC");
    assertEquals(player1, player2);
  }

  @Test
  public void getPlayerInfo() {
    Maze maze = (Maze) new MazeBuilder(3, 4, MazeType.IMPERFECT, false, randomSeed)
                               .removeInsideWalls(3)
                               .addGold()
                               .addThieves()
                               .build();
    Game game = new Game(maze, new Player("PD"), 5, 3);
    assertEquals("Player name PD\tCurrent position 5\tGold 0\t",
            game.getMaze().getPlayer().getPlayerInfo());
  }
}
