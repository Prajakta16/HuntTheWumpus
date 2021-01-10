package maze.model;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * In the non-perfect maze, each cell in the grid also represent a location in the maze, but there
 * can be multiple paths between any two cells. This form is useful in several applications.
 * Computer games, for instance, use this kind of maze to create a map of the world by giving
 * locations in the maze different characteristics
 */
public class ImperfectMaze extends Maze {

  /**
   * Constructor for the imperfect maze that constructs a maze grid of dimensions row*column.
   *
   * @param row number of rows for the maze
   * @param col number of columns for the maze
   */
  public ImperfectMaze(int row, int col, boolean wrappingFlag) {
    super(row, col, wrappingFlag);
  }

  @Override
  public boolean equalsImperfect() {
    return true;
  }

  @Override
  public void removeWrappingWalls(Random random, int numWrappingWallsToRemove) {
    if (!this.getWrappingFlag()) {
      throw  new IllegalStateException();
    }

    int[][] gridAdjMatrix = this.getGridAdjMatrix();

    int horizontalWraps = (int) numWrappingWallsToRemove / 2;
    int verticalWraps = numWrappingWallsToRemove - horizontalWraps;

    int rowNumber;
    int colNumber;
    int room1;
    int room2;

    while (horizontalWraps > 0) {
      rowNumber = random.nextInt(this.getRow());
      room1 = rowNumber * this.getCol();
      room2 = room1 + this.getCol() - 1;

      if (gridAdjMatrix[room1][room2] == 0) {
        this.wallDemolish(room1, room2);
        // System.out.println("Removed wrapping wall between" + room1 + " " + room2);
        horizontalWraps--;
      }
    }

    while (verticalWraps > 0) {
      colNumber = random.nextInt(this.getCol());
      room1 = colNumber;
      room2 = room1 + (this.getRow() - 1) * this.getCol();

      if (gridAdjMatrix[room1][room2] == 0) {
        this.wallDemolish(room1, room2);
        // System.out.println("Removed wrapping wall between" + room1 + " " + room2);
        verticalWraps--;
      }
    }
  }

  @Override
  public void removeAdditionalInsideWalls(int numWallsToRemove) {
    Set<int[]> walls = getAllWallsInMaze();

    Iterator<int[]> it = walls.iterator();
    int[] edge;
    while (it.hasNext() && numWallsToRemove > 0) {
      edge = it.next();
      wallDemolish(edge[0], edge[1]);
      numWallsToRemove--;
    }
  }

}
