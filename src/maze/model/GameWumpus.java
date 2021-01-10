package maze.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents the implementation for for Hunt the Wumpus game.
 */
public class GameWumpus extends AbstractGame {
  private boolean playerDead;
  private boolean playerWon;
  private int randomSeed;

  /**
   * Constructor for game class.
   *
   * @param maze       in the game
   * @param player     player in the game
   * @param start      start room position
   * @param randomSeed random seed
   */
  public GameWumpus(InterfaceMaze maze, Player player, int start, int randomSeed) {
    super(maze, player, start);
    this.randomSeed = randomSeed;
    playerDead = false;
    playerWon = false;
  }

  /**
   * Constructor for game class.
   *
   * @param maze       in the game
   * @param players    list of player in the game
   * @param randomSeed random seed
   */
  public GameWumpus(InterfaceMaze maze, List<InterfacePlayer> players, ThemeName theme,
                    int randomSeed) {
    super(maze, players, theme, randomSeed);
    this.randomSeed = randomSeed;
  }

  @Override
  public Map<Room, Direction> getValidPositions(Room playerRoomPosition) {
    Map<Room, Direction> validNextPositions = new HashMap<>();
    Map<Direction, Room> possibleMoves = playerRoomPosition.getAdjacentCaves();

    for (Direction direction : possibleMoves.keySet()) {
      validNextPositions.put(possibleMoves.get(direction), direction);
    }
    return validNextPositions;
  }

  @Override
  public boolean isOver() {
    for (InterfacePlayer player : getAllPlayers()) {
      if (!player.isDead()) { //if any player is alive return true
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean hasWon() {
    for (InterfacePlayer player : getAllPlayers()) {
      if (player.isHasWon()) { //if any player is alive return true
        return true;
      }
    }
    return false;
  }

  @Override
  public void useArrow(Direction direction, int distance) {
    if (distance < 0) {
      setErrorMessage("Distance cannot be negative");
    }
    distance++; //distance of 0 would indicate your own cell
    String arrow = getTheme().getFeatureMappingForTheme().get(FeatureType.ARROW).toString();
    String wumpus = getTheme().getFeatureMappingForTheme().get(FeatureType.WUMPUS).toString();
    if (getPlayerById(getActivePlayerNumber()).useArrow(direction, distance)) {
      playerWon = true;
      getPlayerById(getActivePlayerNumber()).setHasWon(true);
      setLatestMessageForPlayer("You successfully destroyed the " + wumpus + ", you won!!!");
    } else {
      String message = arrow + " was unsuccessful. ";
      if (getPlayerById(getActivePlayerNumber()).getArrowCount() == 0) {
        message += arrow + "s are over, you lost";
        playerDead = true;
        getPlayerById(getActivePlayerNumber()).setIsDead(true);
      }
      setLatestMessageForPlayer(message);
    }
    shiftTurnToNextPlayer();
  }

  @Override
  public void enterRoom(Room room, Random random) {

    String wumpus = getTheme().getFeatureMappingForTheme().get(FeatureType.WUMPUS).toString();
    setLatestMessageForPlayer("Let us try to find " + wumpus);
    if (room.getFeatures().get(FeatureType.WUMPUS)) {
      enterRoomWithWumpus();
      return;
    }

    if (room.getFeatures().get(FeatureType.PIT) && room.getFeatures().get(FeatureType.BAT)) {
      Boolean transportFlag = enterRoomWithBat(random);
      if (!transportFlag) {
        enterRoomWithPit();
      }
      return;
    }

    if (room.getFeatures().get(FeatureType.BAT)) {
      enterRoomWithBat(random);
    }

    if (room.getFeatures().get(FeatureType.PIT)) {
      enterRoomWithPit();
    }

  }

  private Boolean enterRoomWithBat(Random random) {
    Boolean transportFlag = random.nextBoolean();
    String bat = getTheme().getFeatureMappingForTheme().get(FeatureType.BAT).toString();

    if (transportFlag) {
      boolean foundRoom = false;
      int rand;

      while (!foundRoom) {
        rand = random.nextInt(getMaze().getAllCaves().size());
        Room randomCave = getMaze().getAllCaves().get(rand);

        // if the same room is not selected
        if (randomCave != getPlayerById(getActivePlayerNumber()).getRoom()) {
          //getRoomNumbersToUpdate().add(randomCave.getRoomId());
          getPlayerById(getActivePlayerNumber()).setRoom(randomCave);
          getBatRoomsVisited().add(getPlayerById(getActivePlayerNumber()).getRoom().getRoomId());
          getBatRoomsVisited().add(randomCave.getRoomId());
          enterRoom(randomCave, random);
          setLatestMessageForPlayer(bat + "s transported you to " + randomCave.getRoomId());
          foundRoom = true;
        }
      }
    } else {
      setLatestMessageForPlayer(bat + "s seem lazy, they did not transport you");
    }
    return transportFlag;
  }

  private void enterRoomWithPit() {
    String pit = getTheme().getFeatureMappingForTheme().get(FeatureType.PIT).toString();
    setLatestMessageForPlayer("You have fallen into a " + pit + ", you lost");
    getPlayerById(getActivePlayerNumber()).setIsDead(true);
    playerDead = true;
  }

  private void enterRoomWithWumpus() {
    String wumpus = getTheme().getFeatureMappingForTheme().get(FeatureType.WUMPUS).toString();
    setLatestMessageForPlayer("You have come across a " + wumpus + ", you lost");
    getPlayerById(getActivePlayerNumber()).setIsDead(true);
    playerDead = true;
  }

  @Override
  public int getRandomSeed() {
    return randomSeed;
  }
}
