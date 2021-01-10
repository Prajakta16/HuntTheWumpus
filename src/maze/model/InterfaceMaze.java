package maze.model;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Represents a Maze in a game which has one or many paths from any point in the maze to any other
 * point in the maze.
 */
public interface InterfaceMaze {

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
   * Adds all the specified features to the maze.
   *
   * @param random   random object to randomly add the features
   * @param features the features which needs to be added to the maze
   */
  void addFeatures(Map<FeatureType, Integer> features, Random random);

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
   * Returns list of rooms in the maze.
   *
   * @return list of rooms in the maze
   */
  List<Room> getRooms();

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
   * Updates this player's cave position to a random cave in the maze.
   *
   * @param random cave that will be chosen for transporting
   */
  public void transportPlayerByBats(Random random);


  /**
   * Returns the room with specified ID.
   *
   * @param roomId    room ID
   * @param roomsList list of rooms
   * @return room object
   */
  Room getRoomById(int roomId, List<Room> roomsList);

  /**
   * Returns list of all caves.
   *
   * @return list of all caves
   */
  List<Room> getAllCaves();


  /**
   * In a multi-player mode, assigns all the players randomly in the maze.
   *
   * @param players    list of players in the game
   * @param randomSeed randomSeed
   */
  void assignMultiplePlayersRandomStartPositions(List<InterfacePlayer> players, int randomSeed);

}
