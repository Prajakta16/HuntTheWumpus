package maze;

import java.util.HashMap;
import java.util.Map;

/**
 * Game class that represents player playing a game of finding a path through the maze from start to
 * goal.
 */
public class Game {
  private final InterfaceMaze maze;
  private final int goal;
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
    if (start < 0 || start >= maze.getCountRooms()) {
      throw new IllegalArgumentException();
    }
    if (goal < 0 || goal >= maze.getCountRooms()) {
      throw new IllegalArgumentException();
    }

    if (start == goal) {
      throw new IllegalArgumentException();
    }

    this.maze = maze;
    this.goal = goal;
    this.maze.assignPlayer(player, start);
    this.goalReached = false;
  }

  public boolean isGoalReached() {
    return goalReached;
  }

  private void isGoal(int nextPosition) {
    if (nextPosition == goal) {
      goalReached = true;
      System.out.println("!!!!!Reached the gaol!!!!");
      System.out.println(printPlayerStatus());
      printPlayerStatus();
    }
  }

  /**
   * Moves the player in the specified direction. If the goal is reached, prints victory message.
   *
   * @param direction for the player to move
   * @throws IllegalArgumentException if direction is invalid
   */
  public void movePlayer(Direction direction) {
    System.out.println("Moving " + direction.toString());
    Map<Integer, Direction> validNextPositions = getValidPositions(this.maze.getPlayer()
                                                                           .getPosition());
    for (Integer nextPosition : validNextPositions.keySet()) {
      if (validNextPositions.get(nextPosition).equals(direction)) {
        this.maze.getPlayer().setPosition(nextPosition);

        if (maze.hasGold(nextPosition)) {
          this.maze.getPlayer().collectGold();
          this.maze.removeGold(nextPosition);
        }
        if (maze.hasThief(nextPosition)) {
          this.maze.getPlayer().loseGoldToThief();
        }

        isGoal(nextPosition);
        return;
      }
    }

    throw new IllegalArgumentException();
  }

  public InterfaceMaze getMaze() {
    return maze;
  }

  public int getGoal() {
    return goal;
  }

  /**
   * Prints possible directions that the user is allowed to move in.
   *
   * @return string for available directions
   */
  public StringBuilder printAvailableDirections() {
    StringBuilder stringBuilder = new StringBuilder();
    int position = maze.getPlayer().getPosition();
    Map<Integer, Direction> validNextPositions = getValidPositions(position);
    for (Integer nextPosition : validNextPositions.keySet()) {
      stringBuilder.append(nextPosition)
              .append(" ")
              .append(validNextPositions.get(nextPosition))
              .append(" ");
    }
    return stringBuilder;
  }

  /**
   * Prints status of player in the game.
   *
   * @return string version of status of player in the game
   */
  public String printPlayerStatus() {
    return maze.getPlayer().getPlayerInfo();
  }

  private Map<Integer, Direction> getValidPositions(int position) {
    Map<Integer, Direction> validNextPositions = new HashMap<>();
    int[][] grid = maze.getGridAdjMatrix();


    for (int i = 0; i < maze.getCountRooms(); i++) {
      if (grid[position][i] == 1) {
        if (i == position + 1) {
          validNextPositions.put(i, Direction.EAST);
        } else if (i == position - 1) {
          validNextPositions.put(i, Direction.WEST);
        } else if (i == position + maze.getCol()) {
          validNextPositions.put(i, Direction.SOUTH);
        } else if (i == position - maze.getCol()) {
          validNextPositions.put(i, Direction.NORTH);
        }


        if (maze.getWrappingFlag()) {
          if (position < this.maze.getCol()) { //first row
            if (i == position + (maze.getRow() - 1) * maze.getCol()) {
              validNextPositions.put(i, Direction.NORTH);
            }
          } else if (position > (this.maze.getRow() - 1) * this.maze.getCol()) { //last row
            if (i == position - ((maze.getRow() - 1) * maze.getCol())) {
              validNextPositions.put(i, Direction.SOUTH);
            }
          } else if (position % this.maze.getCol() == 0) { //leftmost col
            if (i == position + maze.getCol() - 1) {
              validNextPositions.put(i, Direction.WEST);
            }
          } else if ((position + 1) % this.maze.getCol() == 0) { //rightmost col
            if (i == position - (maze.getCol() - 1)) {
              validNextPositions.put(i, Direction.EAST);
            }
          }
        }

      }
    }

    return validNextPositions;
  }

}
