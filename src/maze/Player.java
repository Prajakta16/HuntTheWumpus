package maze;

import java.util.Objects;

/**
 * Player class represents the player playing the maze. The player can make a move by specifying a
 * direction The player should be able to collect gold such that 1. a player "collects" gold by
 * entering a room that has gold which is then removed from the room 2. a player "loses" 10% of
 * their gold by entering a room with a thief
 */
public class Player {
  private int gold;
  private int position;
  private final String name;
  private static float PERCENT_GOLD_AFTER_ROBBED_BY_THIEF = 90;

  /**
   * Constructor for the player object.
   *
   * @param name name of the player
   */
  public Player(String name) {
    this.name = name;
    this.gold = 0;
  }

  public int getGold() {
    return gold;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int start) {
    this.position = start;
  }

  /**
   * Updates this player's gold value by 100.
   */
  public void collectGold() {
    this.gold += 100;
  }

  /**
   * Updates this player's gold value to 90% of current value. Thief robs 10%.
   */
  public void loseGoldToThief() {
    this.gold = (int) (this.gold * (PERCENT_GOLD_AFTER_ROBBED_BY_THIEF / 100.0f));
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

  /**
   * Returns the info of the player status in the game.
   *
   * @return player status in string format
   */
  public String getPlayerInfo() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Player name ").append(this.name).append("\t");
    stringBuilder.append("Current position ").append(this.position).append("\t");
    stringBuilder.append("Gold ").append(this.gold).append("\t");
    return stringBuilder.toString();
  }
}
