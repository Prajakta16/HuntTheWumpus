package maze.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface with read-only methods of the model class. This follows an Adapter pattern to help
 * reuse the existing code. Rather than having the controller push changes to the view, the view can
 * now pull changes from the model.
 */
public interface IViewModel {

  /**
   * Returns rows in the game.
   *
   * @return rows in the game
   */
  int getRowsInGrid();

  /**
   * Returns columns in the game.
   *
   * @return columns in the game
   */
  int getColumnsInGrid();

  /**
   * Returns features in the room.
   *
   * @param roomId room for which features are requested
   * @return hashMap with feature and its existence
   */
  Map<FeatureType, Boolean> getFeaturesOfVisitedRoom(int roomId);

  /**
   * Returns smells in the room.
   *
   * @param roomId room for which smells are requested
   * @return list with smells
   */
  Set<SmellName> getSmellsOfVisitedRoom(int roomId);

  /**
   * Returns the path of the smell image for specified theme. Allowed smells are STENCH, BREEZE
   *
   * @param smellName for which image is required
   * @return image path
   */
  String getImagePath(SmellName smellName);

  /**
   * Returns the path of the base image for specified feature for specified theme. Allowed features
   * are BAT, WUMPUS
   *
   * @param featureType for which image is required
   * @return image path
   */
  String getImagePath(FeatureType featureType);

  /**
   * Returns the path of the player for specified theme.
   *
   * @param playerNumber for which image is required
   * @return image path
   */
  String getImagePathForPlayer(int playerNumber);

  /**
   * Returns the path of the background image for specified theme.
   *
   * @return image path
   */
  String getImagePathForTheme();

  /**
   * Returns the path of the base image for specified room for specified theme.
   *
   * @param roomId room for which image is required
   * @return image path
   */
  String getImagePathForRoom(int roomId);

  /**
   * Returns the message after player takes an action.
   *
   * @return message after player takes an action
   */
  String getLatestMessageForPlayer(int playerNumber);

  /**
   * Returns all the valid directions that the player can move to.
   *
   * @param playerNumber player number for whom valid directions are to be fetched
   * @return list of available directions
   */
  List<Direction> getValidMoveForPlayer(int playerNumber);

  /**
   * Gets info of the given player.
   *
   * @param playerNumber number of the player for which info is requested.
   * @return player info wrapped in a CurrentPlayerInfo object
   */
  CurrentPlayerInfo getPlayerGameStatusAndInfo(int playerNumber);

  /**
   * Returns the player number whose turn is to play the game.
   *
   * @return player number whose turn is to play the game
   */
  int getActivePlayerNumber();

  /**
   * Return true if room is visited.
   *
   * @param roomId roomId
   * @return true if room is visited
   */
  boolean isVisited(int roomId);

  /**
   * Returns true if room is a cave.
   * @param roomId roomId
   * @return true if room is a cave.
   */
  boolean isCave(int roomId);


  /**
   * Returns number of players in the game.
   * @return number of players in the game
   */
  int getPlayerCount();

  /**
   * Returns true if playerId is present in roomId.
   * @param roomId room id
   * @param playerId player id
   * @return true if playerId is present in roomId
   */
  boolean playerInRoom(int roomId, int playerId);

  /**
   * Returns the player number who last moved in the maze.
   * @return the player number who last moved in the maze
   */
  int getPlayerLastMoved();

  /**
   * Returns true if game is lost by both players.
   * @return true if game is lost by both players
   */
  boolean isOver();

  /**
   * Returns playerNumber who won the game.
   * @return playerNumber who won the game
   */
  boolean hasWon();

  /**
   * Returns game theme.
   * @return game theme
   */
  ThemeName getThemeName();

  /**
   * Returns the set of visited tunnel room numbers.
   * @return the set of visited tunnel room numbers
   */
  Set<Integer> getVisitedTunnelsRoomNumbers();

  /**
   * Returns the room numbers to be updated on the maze view.
   * @return room numbers to be updated on the maze view
   */
  Set<Integer> getRoomNumbersToUpdate();

  /**
   * Returns the error message to be updated on the maze view.
   * @return error message to be updated on the maze view
   */
  String getErrorMessage();
}
