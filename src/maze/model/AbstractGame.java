package maze.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Abstract game class containing common methods of different games that we can have.
 */
public abstract class AbstractGame implements InterfaceGame, IViewModel {
  private static String ICON_PATH = "res/icons/";
  private InterfaceMaze maze;
  private List<InterfacePlayer> players;
  private int activePlayerNumber;
  private int playerNumberLastMoved;
  private ThemeName themeName;
  private IThemeFeatures theme;
  private String notificationMessage;
  private Set<Integer> visitedTunnelsRoomNumbers;
  private Set<Integer> roomNumbersToUpdate;
  private Set<Integer> batRoomsVisited;
  private String errorMessage = "";

  /**
   * Constructor for Abstract game class.
   *
   * @param maze   in the game
   * @param player player in the game
   * @param start  start room position
   */
  public AbstractGame(InterfaceMaze maze, Player player, int start) {
    if (player == null) {
      throw new IllegalArgumentException("No player provided");
    }
    if (start < 0 || start >= maze.getCountRooms()) {
      throw new IllegalArgumentException("Incorrect start");
    }

    this.maze = maze;
    this.maze.assignPlayer(player, start);
    this.activePlayerNumber = 1;
    this.playerNumberLastMoved = 1;
    this.roomNumbersToUpdate = new HashSet<>();
    this.visitedTunnelsRoomNumbers = new HashSet<>();
  }


  /**
   * Constructor for Abstract game class when game is played in multi-player mode.
   *
   * @param maze    maze for the game
   * @param players list of players playing the game
   */
  public AbstractGame(InterfaceMaze maze, List<InterfacePlayer> players, ThemeName themeName,
                      int randomSeed) {
    if (players == null || players.size() == 0) {
      throw new IllegalArgumentException("No player provided");
    }
    if (maze == null) {
      throw new IllegalArgumentException("No maze provided");
    }
    if (themeName == null) {
      throw new IllegalArgumentException("No theme provided");
    }
    this.players = players;
    this.maze = maze;
    this.themeName = themeName;
    this.maze.assignMultiplePlayersRandomStartPositions(players, randomSeed);
    this.activePlayerNumber = 1;
    this.playerNumberLastMoved = 1;
    this.roomNumbersToUpdate = new HashSet<>();
    this.visitedTunnelsRoomNumbers = new HashSet<>();

    if (themeName == ThemeName.HUNT_THE_WUMPUS) {
      theme = new ThemeWumpus();
    } else if (themeName == ThemeName.SHOOT_THE_LION) {
      theme = new ThemeForest();
    }
    if (themeName == ThemeName.DESTROY_ASTEROID) {
      theme = new ThemeSpace();
    }
    if (themeName == ThemeName.CATCH_THE_SHARK) {
      theme = new ThemeMarine();
    }
  }

  @Override
  public IThemeFeatures getTheme() {
    return theme;
  }

  @Override
  public void useArrow(Direction direction, int distance) {
    if (distance < 0) {
      setErrorMessage("Distance cannot be negative");
    }
    if (this instanceof Game) {
      throw new IllegalArgumentException("Only applicable for wumpus game, not gold game");
    }
  }

  protected void setErrorMessage(String message) {
    errorMessage = message;
  }

  @Override
  public Set<Integer> getVisitedTunnelsRoomNumbers() {
    return visitedTunnelsRoomNumbers;
  }

  public Set<Integer> getBatRoomsVisited() {
    return batRoomsVisited;
  }

  @Override
  public void movePlayer(Direction direction, Random random) {
    batRoomsVisited = new HashSet<>();
    setErrorMessage("");
    InterfacePlayer playerMoving = getPlayerById(getActivePlayerNumber());
    int oldRoom = playerMoving.getRoom().getRoomId();

    Room originalRoom = this.getPlayerById(getActivePlayerNumber()).getRoom();
    Map<Room, Direction> validNextPositions =
            getValidPositions(this.getPlayerById(getActivePlayerNumber()).getRoom());
    for (Room nextRoomPosition : validNextPositions.keySet()) {
      if (validNextPositions.get(nextRoomPosition).equals(direction)) {
        for (Room room : this.getMaze().getRooms()) {
          if (room == nextRoomPosition) {
            this.getPlayerById(getActivePlayerNumber()).setRoom(room);
            room.setVisited();
            enterRoom(nextRoomPosition, random);
            if (getAllPlayers().size() > 1) {
              shiftTurnToNextPlayer();
            }
            int newRoom = playerMoving.getRoom().getRoomId();
            updateRooms(oldRoom, newRoom, direction);
          }
        }
      }
    }
  }

  private Set<Integer> enterTunnels(Room startingRoom, Direction direction) {
    List<Room> connectingTunnels = startingRoom.getConnectingTunnels().get(direction);
    Set<Integer> newlyExploredTunnels = new HashSet<>();

    if (connectingTunnels != null && connectingTunnels.size() != 0) {
      for (Room tunnel : connectingTunnels) {
        tunnel.setVisited();

        int tunnelId = tunnel.getRoomId();
        if (!visitedTunnelsRoomNumbers.contains(tunnelId)) {
          visitedTunnelsRoomNumbers.add(tunnelId);
          newlyExploredTunnels.add(tunnelId);
        }
      }
    }
    return newlyExploredTunnels;
  }

  private void updateRooms(int oldRoom, int newRoom, Direction direction) {
    roomNumbersToUpdate = new HashSet<>();
    roomNumbersToUpdate.addAll(batRoomsVisited);
    if (oldRoom != newRoom) {
      roomNumbersToUpdate.add(oldRoom);
      roomNumbersToUpdate.add(newRoom);
      Set<Integer> newTunnels = enterTunnels(maze.getRoomById(oldRoom, maze.getRooms()), direction);
      roomNumbersToUpdate.addAll(newTunnels);
    } else {
      setErrorMessage("No room in direction");
    }
  }

  @Override
  public Set<Integer> getRoomNumbersToUpdate() {
    return roomNumbersToUpdate;
  }

  @Override
  public String getErrorMessage() {
    return errorMessage;
  }

  protected void shiftTurnToNextPlayer() {
    int currentPlayerNumber = getActivePlayerNumber();
    playerNumberLastMoved = currentPlayerNumber;

    //check if all are dead, if not only then find the next player
    boolean gameOn = false;
    for (InterfacePlayer player : getAllPlayers()) {
      if (!player.isDead()) { // if atleast one player is alive.
        gameOn = true;
        break;
      }
    }

    if (gameOn) {
      boolean foundNext = false;
      while (!foundNext) {
        currentPlayerNumber++;
        if (currentPlayerNumber > getPlayerCount()) {
          currentPlayerNumber = 1;
        }
        if (!getPlayerById(currentPlayerNumber).isDead()) {
          activePlayerNumber = currentPlayerNumber;
          foundNext = true;
        }
      }
    }
  }

  protected InterfaceMaze getMaze() {
    return maze;
  }

  /**
   * Player enter the room and necessary action is performed depending on the feature of the room.
   * @param room to be entered
   * @param random seed
   */
  protected abstract void enterRoom(Room room, Random random);

  @Override
  public List<InterfacePlayer> getAllPlayers() {
    return this.players;
  }

  @Override
  public InterfacePlayer getPlayerById(int playerNumber) {
    return getAllPlayers().get(playerNumber - 1);
  }

  @Override
  public CurrentPlayerInfo printPlayerStatus(int playerNumber) {
    return getPlayerById(playerNumber).getPlayerInfo();
  }

  @Override
  public Map<Room, Direction> printAvailableDirections() {
    Room currentRoomPosition = getPlayerById(activePlayerNumber).getRoom();
    return getValidPositions(currentRoomPosition);
  }

  @Override
  public int getRowsInGrid() {
    return maze.getRow();
  }

  @Override
  public int getColumnsInGrid() {
    return maze.getCol();
  }

  protected int getRandomSeed() {
    if (this instanceof Game) {
      throw new IllegalArgumentException("Only applicable for wumpus game, not gold game");
    }
    return -1;
  }


  private List<Room> getAllVisitedRooms() {
    List<Room> visited = new ArrayList<>();
    for (Room room : maze.getRooms()) {
      if (room.isVisited()) {
        visited.add(room);
      }
    }
    return visited;
  }

  @Override
  public boolean isVisited(int roomId) {
    Room room = maze.getRoomById(roomId, maze.getRooms());
    return room.isVisited();
  }

  @Override
  public boolean isCave(int roomId) {
    return maze.getRoomById(roomId, maze.getRooms()).isCave();
  }

  @Override
  public ThemeName getGameTheme() {
    return themeName;
  }

  @Override
  public Map<FeatureType, Boolean> getFeaturesOfVisitedRoom(int roomId) {
    Room room = maze.getRoomById(roomId, maze.getRooms());
    if (!room.isVisited()) {
      throw new IllegalArgumentException("The room is still not visited and the view cannot get "
                                                 + "info about the features in the room");
    }

    return room.getFeatures();
  }

  @Override
  public Set<SmellName> getSmellsOfVisitedRoom(int roomId) {
    Room room = maze.getRoomById(roomId, maze.getRooms());
    return room.getSmellIconSet();
  }

  protected String surroundWithBasePath(String gameObjectName) {
    StringBuilder imagePath = new StringBuilder();
    imagePath.append(ICON_PATH);
    imagePath.append(getGameTheme().toString().toLowerCase()).append('/');
    imagePath.append(gameObjectName);
    imagePath.append(".png");
    return imagePath.toString();
  }

  @Override
  public String getImagePathForRoom(int roomId) {
    Room room = maze.getRoomById(roomId, maze.getRooms());
    StringBuilder imagePath = new StringBuilder();
    imagePath.append(ICON_PATH);
    imagePath.append(getGameTheme().toString().toLowerCase()).append('/');
    if (room.isVisited()) {
      Direction[] priority = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

      for (Direction direction : priority) {
        if (room.getPossibleMoves().keySet().contains(direction)) {
          imagePath.append(direction.toString().substring(0, 1));
        }
      }
    } else {
      imagePath.append("black");
    }
    imagePath.append(".png");
    return imagePath.toString();
  }

  public String getImagePathForTheme() {
    return surroundWithBasePath("background");
  }

  @Override
  public String getImagePath(SmellName smellName) {
    if (smellName != SmellName.breeze && smellName != SmellName.stench) {
      throw new IllegalArgumentException("Feature currently not supported");
    }
    return surroundWithBasePath(smellName.toString().toLowerCase());
  }

  @Override
  public String getImagePath(FeatureType featureType) {
    if (featureType != FeatureType.WUMPUS && featureType != FeatureType.BAT
                && featureType != FeatureType.PIT) {
      throw new IllegalArgumentException("Feature currently not supported");
    }
    return surroundWithBasePath(featureType.toString().toLowerCase());
  }

  @Override
  public String getImagePathForPlayer(int playerNumber) {
    if (playerNumber > getAllPlayers().size()) {
      throw new IllegalArgumentException("Player not in the game, incorrect player number");
    }
    return surroundWithBasePath("player" + playerNumber);
  }

  @Override
  public String getLatestMessageForPlayer(int playerNumber) {
    return notificationMessage;
  }

  @Override
  public void setLatestMessageForPlayer(String message) {
    notificationMessage = message;
  }

  @Override
  public List<Direction> getValidMoveForPlayer(int playerNumber) {
    CurrentPlayerInfo currentPlayerInfo = getPlayerGameStatusAndInfo(playerNumber);
    Map<Room, Direction> validPositions = getValidPositions(currentPlayerInfo.getRoom());

    return new ArrayList<>(validPositions.values());
  }

  @Override
  public CurrentPlayerInfo getPlayerGameStatusAndInfo(int playerNumber) {
    InterfacePlayer player = getAllPlayers().get(playerNumber - 1);
    return player.getPlayerInfo();
  }

  @Override
  public int getActivePlayerNumber() {
    return activePlayerNumber;
  }

  @Override
  public int getPlayerCount() {
    return getAllPlayers().size();
  }

  @Override
  public boolean playerInRoom(int roomId, int playerId) {
    InterfacePlayer player = getPlayerById(playerId);
    //System.out.println(player.getPlayerInfo());
    return player.getRoom().getRoomId() == roomId;
  }

  @Override
  public int getPlayerLastMoved() {
    return playerNumberLastMoved;
  }

  @Override
  public ThemeName getThemeName() {
    return themeName;
  }
}
