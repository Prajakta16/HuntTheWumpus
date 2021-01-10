import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import maze.model.Cave;
import maze.model.CurrentPlayerInfo;
import maze.model.Direction;
import maze.model.FeatureType;
import maze.model.IThemeFeatures;
import maze.model.IViewModel;
import maze.model.InterfaceGame;
import maze.model.InterfacePlayer;
import maze.model.Player;
import maze.model.Room;
import maze.model.SmellName;
import maze.model.ThemeName;
import maze.model.ThemeWumpus;

/**
 * Mock model for testing the controller.
 */
public class MockModel implements InterfaceGame, IViewModel {
  private StringBuilder inputLog;
  private StringBuilder outputLog;
  private Player player1 = new Player("Player1");
  private Player player2 = new Player("Player2");

  /**
   * Constructor for mock model.
   *
   * @param inputLog  input log to add input commands
   * @param outputLog output log to add output commands
   */
  public MockModel(StringBuilder inputLog, StringBuilder outputLog) {
    this.inputLog = inputLog;
    this.outputLog = outputLog;
  }

  @Override
  public Map<Room, Direction> printAvailableDirections() {
    addToOutputLog("Valid position for player are");
    Map<Room, Direction> mapping = new HashMap<>();
    mapping.put(new Cave(1, "C1"), Direction.WEST);
    mapping.put(new Cave(2, "C2"), Direction.EAST);
    mapping.put(new Cave(3, "C3"), Direction.SOUTH);
    addToOutputLog(mapping.values().toString());
    return mapping;
  }

  @Override
  public CurrentPlayerInfo printPlayerStatus(int playerNumber) {
    Room room = new Cave(2, "C1");
    CurrentPlayerInfo currentPlayerInfo = new CurrentPlayerInfo(10, "playerName",
            room, 5);
    addToOutputLog(currentPlayerInfo.toString());
    return currentPlayerInfo;
  }

  private void addToOutputLog(String message) {
    this.outputLog.append(message).append("\n");
  }


  private void addToInputLog(String message) {
    this.inputLog.append(message).append("\n");
  }

  @Override
  public void movePlayer(Direction direction, Random random) {
    addToInputLog("Moving " + direction.toString());
    List<Direction> validDirections = new ArrayList<>();
    validDirections.add(Direction.WEST);

    if (validDirections.contains(direction)) {
      addToOutputLog("Moving player in direction " + direction.toString());
    }
  }

  @Override
  public InterfacePlayer getPlayerById(int playerNumber) {
    addToInputLog(playerNumber + "");
    addToOutputLog("Adding player to game " + playerNumber);
    return player1;
  }

  @Override
  public List<InterfacePlayer> getAllPlayers() {
    List<InterfacePlayer> players = new ArrayList<>();
    players.add(player1);
    players.add(player2);
    addToOutputLog("Retrieving player1 and player2 information");
    return players;
  }

  @Override
  public ThemeName getGameTheme() {
    addToOutputLog(ThemeName.HUNT_THE_WUMPUS.toString());
    return ThemeName.HUNT_THE_WUMPUS;
  }

  @Override
  public Map<Room, Direction> getValidPositions(Room playerRoomPosition) {
    addToInputLog(playerRoomPosition.toString());
    addToOutputLog("Valid position for " + playerRoomPosition.getRoomId() + "are");
    Map<Room, Direction> mapping = new HashMap<>();
    mapping.put(new Cave(1, "C1"), Direction.WEST);
    mapping.put(new Cave(2, "C2"), Direction.EAST);
    mapping.put(new Cave(3, "C3"), Direction.SOUTH);
    addToOutputLog(mapping.values().toString());
    return mapping;
  }

  @Override
  public boolean isOver() {
    addToOutputLog("Game not over");
    return false;
  }


  @Override
  public void useArrow(Direction direction, int distance) {
    addToInputLog("Command for shooting arror in direction " + direction + "for distance "
                          + distance);
    addToOutputLog("Shooting arrrow in " + direction.toString() + "for distance " + direction);
  }

  @Override
  public boolean hasWon() {
    addToOutputLog("Game is not yet won");
    return false;
  }

  @Override
  public ThemeName getThemeName() {
    addToOutputLog(ThemeName.HUNT_THE_WUMPUS.toString());
    return ThemeName.HUNT_THE_WUMPUS;
  }

  @Override
  public Set<Integer> getVisitedTunnelsRoomNumbers() {
    Set<Integer> toBeUpdated = new HashSet<>();
    toBeUpdated.add(1);
    toBeUpdated.add(2);
    addToOutputLog(toBeUpdated.toString());
    return toBeUpdated;
  }

  @Override
  public Set<Integer> getRoomNumbersToUpdate() {
    Set<Integer> toBeUpdated = new HashSet<>();
    toBeUpdated.add(1);
    toBeUpdated.add(2);
    addToOutputLog(toBeUpdated.toString());
    return toBeUpdated;
  }

  @Override
  public String getErrorMessage() {
    addToOutputLog("error");
    return "error";
  }

  @Override
  public int getRowsInGrid() {
    addToInputLog("rows are 10");
    return 10;
  }

  @Override
  public int getColumnsInGrid() {
    addToInputLog("Columns are 10");
    return 10;
  }

  @Override
  public Map<FeatureType, Boolean> getFeaturesOfVisitedRoom(int roomId) {
    addToInputLog("Features requested for " + roomId);
    Map<FeatureType, Boolean> features = new HashMap<>();
    features.put(FeatureType.WUMPUS, true);
    addToOutputLog(features.toString());
    return features;
  }

  @Override
  public Set<SmellName> getSmellsOfVisitedRoom(int roomId) {
    addToInputLog("Smells requested for " + roomId);
    Set<SmellName> smells = new HashSet<>();
    smells.add(SmellName.breeze);
    addToOutputLog(smells.toString());
    return smells;
  }

  @Override
  public String getImagePath(SmellName smellName) {
    addToInputLog("Image for smellName " + smellName);
    addToOutputLog("icons/themeName/" + smellName);
    return "icons/themeName/" + smellName;
  }

  @Override
  public String getImagePath(FeatureType featureType) {
    addToInputLog("Image for featureType " + featureType);
    addToOutputLog("icons/themeName/" + featureType);
    return "icons/themeName/" + featureType;
  }

  @Override
  public String getImagePathForPlayer(int playerNumber) {
    addToInputLog("Image for player " + playerNumber);
    addToOutputLog("icons/themeName" + playerNumber);
    return "icons/themeName";
  }

  @Override
  public String getImagePathForTheme() {
    return "icons/themeName";
  }

  @Override
  public String getImagePathForRoom(int roomId) {
    addToInputLog("Image for player " + roomId);
    addToOutputLog("icons/themeName/" + roomId);
    return "icons/themeName/" + roomId;
  }

  @Override
  public String getLatestMessageForPlayer(int playerNumber) {
    addToInputLog("Image for player " + playerNumber);
    return "message";
  }

  @Override
  public List<Direction> getValidMoveForPlayer(int playerNumber) {
    addToInputLog("Image for player " + playerNumber);
    List<Direction> dirs = new ArrayList<>();
    dirs.add(Direction.WEST);
    return dirs;
  }

  @Override
  public CurrentPlayerInfo getPlayerGameStatusAndInfo(int playerNumber) {
    Room room = new Cave(1, "C1");
    CurrentPlayerInfo currentPlayerInfo = new CurrentPlayerInfo(10, "playerName",
            room, 5);
    addToOutputLog(currentPlayerInfo.toString());
    return currentPlayerInfo;
  }

  @Override
  public int getActivePlayerNumber() {
    addToOutputLog("Active player - player1");
    return 1;
  }

  @Override
  public boolean isVisited(int roomId) {
    addToOutputLog(roomId + "is visited");
    return true;
  }

  @Override
  public boolean isCave(int roomId) {
    addToOutputLog(roomId + "is cave");
    return true;
  }

  @Override
  public int getPlayerCount() {
    addToOutputLog("Total players - 2");
    return 2;
  }

  @Override
  public boolean playerInRoom(int roomId, int playerId) {
    addToOutputLog("Player " + playerId + "in room " + roomId);
    return true;
  }

  @Override
  public int getPlayerLastMoved() {
    addToOutputLog("Last moved player: Player 1");
    return 1;
  }

  @Override
  public void setLatestMessageForPlayer(String message) {
    addToInputLog("Setting msg for player - " + message);
    addToOutputLog("Message for player: " + message);
  }

  @Override
  public IThemeFeatures getTheme() {
    addToOutputLog("theme is " + ThemeName.HUNT_THE_WUMPUS);
    return new ThemeWumpus();
  }
}
