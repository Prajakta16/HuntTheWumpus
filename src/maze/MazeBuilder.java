package maze;

import java.util.Random;

/**
 * Maze Builder class that builds the maze according to the specified parameters.
 */
public class MazeBuilder {
  private static int PERCENT_ROOMS_WITH_GOLD = 20;
  private static int PERCENT_ROOMS_WITH_THIEF = 10;
  private final int minWallsRemovalRequired;
  private InterfaceMaze maze;
  private final boolean wrappingFlag;
  private final int maxEdges;
  public Random random;

  /**
   * Constructor for the maze builder class.
   *
   * @param row          number of rows in the required maze
   * @param column       number of columns in the required maze
   * @param mazeType     mazeType of the required maze
   * @param wrappingFlag true if maze is expected to be wrapping, false otherwise
   */
  public MazeBuilder(int row, int column, MazeType mazeType, boolean wrappingFlag, int seed) {
    if (row < 0 || column < 0) {
      throw  new IllegalArgumentException();
    }

    this.random = new Random(seed);
    int countRooms = row * column;
    this.minWallsRemovalRequired = countRooms - 1; //MST requires n-1 edges for n nodes
    this.wrappingFlag = wrappingFlag;
    this.maxEdges = row * (column - 1) + column * (row - 1); //might change if wrapping is applied

    if (mazeType == MazeType.PERFECT) {
      this.maze = new PerfectMaze(row, column, wrappingFlag);
    } else if (mazeType == MazeType.IMPERFECT) {
      this.maze = new ImperfectMaze(row, column, wrappingFlag);
    }
  }

  /**
   * Builds the maze. Should be called when walls are removed, gold and thieves are assigned.
   *
   * @return the well-built maze.
   * @throws IllegalStateException when maze exists without removing walls, assigning gold or thief
   */
  public InterfaceMaze build() {
    maze.finishMazeCreation(); //checks if maze is crated properly and prints a maze on the screen
    return maze;
  }

  /**
   * Removes boundary walls to make the maze a wrapping one.
   *
   * @param numWrappingWallsToRemove number of boundary walls to remove
   * @return this object
   */
  public MazeBuilder removeWrappingWalls(int numWrappingWallsToRemove) {
    if (!wrappingFlag) {
      throw new IllegalStateException();
    }

    maze.removeWrappingWalls(random, numWrappingWallsToRemove);
    return this;
  }

  /**
   * Removes inside walls to make path in the maze. RemoveWalls without specifying  remaining walls
   * is only  allowed for Perfect maze
   *
   * @return this object
   */
  public MazeBuilder removeInsideWalls() {
    if (!maze.equalsPerfect()) {
      throw new IllegalStateException();
    }
    this.maze.removeInsideWalls(random);
    return this;
  }

  /**
   * Removes inside walls to make path in the maze.
   *
   * @param remainingWalls number of internal walls that should remain in the maze
   * @return this object
   */
  public MazeBuilder removeInsideWalls(int remainingWalls) {

    if (maze.equalsPerfect() && remainingWalls != maxEdges - minWallsRemovalRequired) {
      throw new IllegalArgumentException();
    }

    // Remaining edges should always be less than maxEdges - minWallsRemovalRequired. If it is more
    // than that then few rooms might be unreachable, which is not expected
    if (remainingWalls > maxEdges - minWallsRemovalRequired) {
      throw new IllegalArgumentException();
    }

    //Removes minimum edges for a path to exist.
    this.maze.removeInsideWalls(random);

    //Removes additional edges so that we only have remainingWalls in the maze.
    int more = maxEdges - minWallsRemovalRequired - remainingWalls;
    if (more > 0) {
      this.maze.removeAdditionalInsideWalls(more);
    }

    return this;
  }

  /**
   * Adds gold to the maze.
   *
   * @return this object
   */
  public MazeBuilder addGold() {
    this.maze.addGold(random);
    return this;
  }

  /**
   * Adds thieves to the maze.
   *
   * @return this object
   */
  public MazeBuilder addThieves() {
    this.maze.addThieves(random);
    return this;
  }
}

