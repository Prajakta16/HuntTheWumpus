package maze.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Game class that represents player playing a game of finding a path through the maze from start to
 * goal.
 */
public class Game extends AbstractGame {

  private int goal;
  private boolean goalReached;

  /**
   * Constructor for game class.
   *
   * @param maze   maze involved in the game
   * @param player player involved in the game
   * @param start  starting location in the game
   * @param goal   goal location in the game
   * @throws IllegalArgumentException if start or goal values are incorrect
   */
  public Game(InterfaceMaze maze, Player player, int start, int goal) {
    super(maze, player, start);

    if (goal < 0 || goal >= maze.getCountRooms()) {
      throw new IllegalArgumentException();
    }

    if (start == goal) {
      throw new IllegalArgumentException();
    }

    this.goal = goal;
    this.goalReached = false;
  }


  public boolean isGoalReached() {
    return goalReached;
  }

  private void isGoal(int nextPosition) {
    if (nextPosition == goal) {
      goalReached = true;
      //System.out.println("!!!!!Reached the gaol!!!!");
      //System.out.println(printPlayerStatus(getActivePlayerNumber()));
      printPlayerStatus(getActivePlayerNumber());
    }
  }

  public int getGoal() {
    return goal;
  }

  @Override
  public Map<Room, Direction> getValidPositions(Room playerRoomPosition) {
    Map<Room, Direction> validNextPositions = new HashMap<>();
    int[][] grid = this.getMaze().getGridAdjMatrix();
    int position = playerRoomPosition.getRoomId();

    for (int i = 0; i < this.getMaze().getCountRooms(); i++) {
      if (grid[position][i] == 1) {
        if (i == position + 1) {
          validNextPositions.put(getMaze().getRoomById(i, getMaze().getRooms()), Direction.EAST);
        } else if (i == position - 1) {
          validNextPositions.put(getRoom(i), Direction.WEST);
        } else if (i == position + this.getMaze().getCol()) {
          validNextPositions.put(getRoom(i), Direction.SOUTH);
        } else if (i == position - this.getMaze().getCol()) {
          validNextPositions.put(getRoom(i), Direction.NORTH);
        }


        if (this.getMaze().getWrappingFlag()) {
          if (position < this.getMaze().getCol()) { //first row
            if (i == position + (this.getMaze().getRow() - 1) * this.getMaze().getCol()) {
              validNextPositions.put(getRoom(i), Direction.NORTH);
            }
          } else if (position > (this.getMaze().getRow() - 1) * this.getMaze().getCol()) {
            //last row
            if (i == position - ((this.getMaze().getRow() - 1) * this.getMaze().getCol())) {
              validNextPositions.put(getRoom(i), Direction.SOUTH);
            }
          } else if (position % this.getMaze().getCol() == 0) { //leftmost col
            if (i == position + this.getMaze().getCol() - 1) {
              validNextPositions.put(getRoom(i), Direction.WEST);
            }
          } else if ((position + 1) % this.getMaze().getCol() == 0) { //rightmost col
            if (i == position - (this.getMaze().getCol() - 1)) {
              validNextPositions.put(getRoom(i), Direction.EAST);
            }
          }
        }

      }
    }
    return validNextPositions;


  }

  @Override
  public boolean isOver() {
    return goalReached;
  }

  @Override
  public void enterRoom(Room room, Random random) {
    //
  }

  @Override
  public boolean hasWon() {
    return goalReached;
  }

  private Room getRoom(int i) {
    return getMaze().getRoomById(i, getMaze().getRooms());
  }
}

