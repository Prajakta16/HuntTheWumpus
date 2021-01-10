package maze.model;

/**
 * Interface for the player playing the maze game.
 */
public interface InterfacePlayer {

  /**
   * Gets gold with the player.
   *
   * @return gold with the player
   */
  public int getGold();

  /**
   * Adds specified weapon to the player.
   */
  public void addArrow(int quantity);

  /**
   * Uses a arrow to shoot the wumpus.
   *
   * @param direction direction in which weapon is thrown
   * @param distance  distance for which the weapon will travel
   * @return true if weapon usage was successful
   */
  public boolean useArrow(Direction direction, int distance);

  /**
   * Updates this player's gold value by 100.
   */
  public void collectGold();

  /**
   * Updates this player's gold value to 90% of current value. Thief robs 10%.
   */
  public void loseGoldToThief();

  /**
   * Returns the info of the player status in the game.
   *
   * @return CurrentPlayerInfo which has various attributes about player
   */
  public CurrentPlayerInfo getPlayerInfo();

  /**
   * Updates the room for the player.
   *
   * @param room new room for the player
   */
  void setRoom(Room room);

  /**
   * Returns the room the player currently resides in.
   *
   * @return room the player currently resides in
   */
  public Room getRoom();

  /**
   * Get the arrow count with the player.
   *
   * @return the arrow count
   */
  public int getArrowCount();

  /**
   * Sets the player to either dead or alive.
   */
  void setIsDead(boolean value);

  /**
   * Returns true if player is dead.
   * @return true if player is dead
   */
  boolean isDead();

  /**
   * Returns true if player has won.
   * @return true if player has won
   */
  boolean isHasWon();

  /**
   * Sets the player's winning status.
   */
  void setHasWon(boolean value);
}
