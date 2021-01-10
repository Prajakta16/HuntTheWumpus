package maze.model;

import java.util.Objects;

/**
 * Player class represents the player playing the maze. The player can make a move by specifying a
 * direction The player should be able to collect gold such that 1. a player "collects" gold by
 * entering a room that has gold which is then removed from the room 2. a player "loses" 10% of
 * their gold by entering a room with a thief
 */
public class Player implements InterfacePlayer {
  private int gold;
  private final String name;
  private Room room;
  private boolean isDead;
  private boolean hasWon;

  private int arrowCount;
  private static float PERCENT_GOLD_AFTER_ROBBED_BY_THIEF = 90;

  /**
   * Constructor for the player object.
   *
   * @param name name of the player
   */
  public Player(String name) {
    this.name = name;
    this.gold = 0;
    this.arrowCount = 0;
  }

  @Override
  public int getGold() {
    return gold;
  }

  @Override
  public void addArrow(int quantity) {
    this.arrowCount = quantity;
  }

  @Override
  public int getArrowCount() {
    return arrowCount;
  }

  @Override
  public void setIsDead(boolean value) {
    isDead = value;
  }

  @Override
  public boolean isDead() {
    return isDead;
  }

  @Override
  public boolean isHasWon() {
    return hasWon;
  }


  @Override
  public void setHasWon(boolean value) {
    hasWon = value;
  }

  @Override
  public boolean useArrow(Direction direction, int distance) {
    this.arrowCount--; //reduce the quantity of available weapons
    return moveArrow(room, direction, distance);
  }

  /**
   * Shoots the weapon in the given direction.
   *
   * @param room      in which the player shoots the weapon
   * @param direction Direction in which weapon needs to be thrown
   * @param distance  distance that needs to be traveled
   * @return true if weapon successfully kills the wumpus
   */
  private boolean moveArrow(Room room, Direction direction, int distance) {
    Room arrowLandingRoom = room.findCaveAtDistance(direction, distance);
    if (arrowLandingRoom == null) { //Arrow throw was blocked by wall
      return false;
    } else {
      //killed the wumpus
      //Arrow landed into a non-wumpus cave
      return arrowLandingRoom.getFeatures().get(FeatureType.WUMPUS);
    }
  }

  @Override
  public void collectGold() {
    this.gold += 100;
  }

  @Override
  public void loseGoldToThief() {
    this.gold = (int) (this.gold * (PERCENT_GOLD_AFTER_ROBBED_BY_THIEF / 100.0f));
  }

  @Override
  public CurrentPlayerInfo getPlayerInfo() {
    CurrentPlayerInfo currentPlayerInfo = new CurrentPlayerInfo(this.gold, this.name, this.room,
            arrowCount);
    return currentPlayerInfo;
  }

  @Override
  public void setRoom(Room room) {
    this.room = room;
  }

  @Override
  public Room getRoom() {
    return room;
  }

  @Override
  public boolean equals(Object otherPlayer) {
    if (this == otherPlayer) {
      return true;
    }
    if (!(otherPlayer instanceof Player)) {
      return false;
    }
    Player player = (Player) otherPlayer;
    return name.equals(player.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

}
