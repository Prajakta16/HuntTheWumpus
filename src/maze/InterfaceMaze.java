package maze;

import java.util.Random;
import java.util.Set;

/**
 * Represents a Maze in a game which has one or many paths from any point in the maze to any other
 * point in the maze.
 */
public interface InterfaceMaze {


  /**
   * Returns true if room has gold in it.
   *
   * @param roomNumber room number
   * @return true if gold, else false
   */
  boolean hasGold(int roomNumber);

  /**
   * Returns true if room has thief in it.
   *
   * @param roomNumber room number
   * @return true if thief, else false
   */
  boolean hasThief(int roomNumber);

  /**
   * Returns the presence of gold for all rooms.
   *
   * @return the gold for all rooms
   */
  boolean[] getHasGold();

  /**
   * Returns the presence of thieves for all rooms.
   *
   * @return the presence of  thief for all rooms
   */
  boolean[] getHasThief();

  /**
   * Demolishes wall between two rooms.
   *
   * @param room1 first room
   * @param room2 second room
   */
  void wallDemolish(int room1, int room2);

  /**
   * Returns whether this maze is perfect.
   *
   * @return whether this maze is perfect
   */
  boolean equalsPerfect();

  /**
   * Returns whether this maze is imperfect.
   *
   * @return whether this maze is imperfect
   */
  boolean equalsImperfect();

  /**
   * Adds gold to 20% of the rooms.
   *
   * @param random random object to randomly add gold to 20% rooms
   */
  void addGold(Random random);

  /**
   * Adds thieves to 10% of the rooms.
   *
   * @param random random object to randomly add thief to 10% rooms
   */
  void addThieves(Random random);

  /**
   * Removes inside walls to create a path.
   *
   * @param random random object to randomly remove walls using Prim's algorithm
   */
  void removeInsideWalls(Random random);

  /**
   * Removes additional inside walls to create a path in imperfect maze.
   *
   * @param more more edges to remove to match remaining
   */
  void removeAdditionalInsideWalls(int more);

  /**
   * Removes wrapping walls to create a wrapping maze.
   *
   * @param random                   random object to randomly remove specified number of wrapping
   *                                 walls
   * @param numWrappingWallsToRemove number of boundary walls to remove
   */
  void removeWrappingWalls(Random random, int numWrappingWallsToRemove);

  /**
   * Returns count of rooms in the maze.
   *
   * @return count of rooms in the maze
   */
  int getCountRooms();

  /**
   * Returns adjacency matrix of all rooms where 1 indicates a path and 0 indicates a wall.
   *
   * @return adjacency matrix of all rooms
   */
  int[][] getGridAdjMatrix();

  /**
   * Returns total rows in the maze.
   *
   * @return total rows in the maze
   */
  int getRow();

  /**
   * Returns total columns in the maze.
   *
   * @return total columns in the maze
   */
  int getCol();

  /**
   * Returns whether this maze is wrapping.
   *
   * @return whether this maze is wrapping
   */
  boolean getWrappingFlag();

  /**
   * Returns player playing the maze.
   *
   * @return player playing the maze
   */
  Player getPlayer();

  /**
   * Assigns a player to the maze.
   *
   * @param player player object who will play the maze
   * @param start  start position of the game
   */
  void assignPlayer(Player player, int start);

  /**
   * Returns all the existing walls between the rooms.
   *
   * @return all the existing walls between the rooms
   */
  Set<int[]> getAllWallsInMaze();

  /**
   * Verifies if all the required constraints in the maze have been created.
   */
  void finishMazeCreation();

  /**
   * Removes gold from specified room.
   *
   * @param position of the room
   */
  void removeGold(Integer position);
}
