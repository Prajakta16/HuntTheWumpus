package maze.model;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents the model for the game play.
 */
public interface InterfaceGame {

  /**
   * Prints possible directions that the user is allowed to move in.
   *
   * @return string for available directions
   */
  public Map<Room, Direction> printAvailableDirections();

  /**
   * Returns the info status of player in the game.
   *
   * @param playerNumber number for whom info is requested
   * @return status of player in the game
   */
  public CurrentPlayerInfo printPlayerStatus(int playerNumber);

  /**
   * Moves player in the given direction.
   *
   * @param direction direction in which player can move
   * @param random seed
   */
  public void movePlayer(Direction direction, Random random);

  /**
   * Returns the player object according to the player number.
   *
   * @param playerNumber number of the player
   * @return player object
   */
  InterfacePlayer getPlayerById(int playerNumber);

  /**
   * Returns all the players in the game.
   *
   * @return list of active players
   */
  List<InterfacePlayer> getAllPlayers();

  /**
   * Returns the theme of the game.
   * @return the theme of the game
   */
  ThemeName getGameTheme();

  /**
   * Gets list of all possible valid rooms from the current player position.
   * @param playerRoomPosition position of the player
   * @return mapping of direction and next room
   */
  public Map<Room, Direction> getValidPositions(Room playerRoomPosition);

  /**
   * Specifies whether the game is over.
   * @return true if game is over. It will be if arrows are over or wumpus eats the player.
   */
  boolean isOver();


  /**
   * Arrow to be used in the given direction for the specific distance.
   * @param direction direction of the weapon
   * @param distance distance of the weapon
   */
  public void useArrow(Direction direction, int distance);

  /**
   * Returns whether player has won the game.
   * @return true if game is won, false if still continuing
   */
  public boolean hasWon();

  /**
   * Returns the player number whose turn is to play the game.
   *
   * @return player number whose turn is to play the game
   */
  int getActivePlayerNumber();

  /**
   * Sets notification message for the player.
   * @param message message for the player
   */
  void setLatestMessageForPlayer(String message);

  /**
   * Gets the theme object of the game.
   * @return theme object of the game
   */
  IThemeFeatures getTheme();
}
