package maze;

/**
 * A perfect maze is the simplest type of maze for a computer to generate and solve. It is defined
 * as a maze which has one and only one path from any point in the maze to any other point in the
 * maze. This means that the maze has no inaccessible sections, no circular paths, no open areas:
 */
public class PerfectMaze extends Maze {

  /**
   * Constructor for the perfect maze that constructs a maze grid of dimensions row*column.
   *
   * @param row number of rows for the maze
   * @param col number of columns for the maze
   */
  public PerfectMaze(int row, int col, boolean wrappingFlag) {
    super(row, col, wrappingFlag);
  }

  @Override
  public boolean equalsPerfect() {
    return true;
  }


}
