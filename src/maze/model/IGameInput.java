package maze.model;

/**
 * Interface for game input object.
 */
public interface IGameInput {

  /**
   * Get game theme.
   * @return game theme
   */
  ThemeName getThemeName();

  /**
   * Returns true if maze is wrapping.
   * @return true if maze is wrapping
   */
  boolean isWrapping();

  /**
   * Returns true if maze is perfect.
   * @return true if maze is perfect
   */
  boolean isPerfect();


  /**
   * Get number of rows in input.
   * @return number of rows in input
   */
  int getRows();

  /**
   * Get number of columns in input.
   * @return number of columns in input
   */
  int getColumns();

  /**
   * Return number of remaining walls.
   * @return number of remaining walls
   */
  int getRemainingWalls();

  /**
   * Return percent of bats in the input.
   * @return percent of bats in the input
   */
  int getPercentBats();

  /**
   * Return percent of pits in the input.
   * @return percent of pits in the input
   */
  int getPercentPits();

  /**
   * Return percent of gold in the input.
   * @return percent of gold in the input
   */
  int getPercentGold();

  /**
   * Return percent of Thieves in the input.
   * @return percent of Thieves in the input
   */
  int getPercentThieves();

  /**
   * Sets remaining walls.
   * @param remainingWalls remaining walls
   */
  void setRemainingWalls(int remainingWalls);

  /**
   * Sets input's percent of bats.
   * @param percentBats percent of bats
   */
  void setPercentBats(int percentBats);

  /**
   * Sets input's percent of pits.
   * @param percentPits percent of pits
   */
  void setPercentPits(int percentPits);

  /**
   * Sets input's percent of gold.
   * @param percentGold percent of gold
   */
  void setPercentGold(int percentGold);

  /**
   * Sets input's percent of thieves.
   * @param percentThieves percent of thieves
   */
  void setPercentThieves(int percentThieves);

  /**
   * Returns the number of players in the game.
   * @return number of players in the game
   */
  int getCountPlayers();

  /**
   * Returns name of player 1.
   * @return name of player 1.
   */
  String getPlayer1Name();

  /**
   * Returns name of player 2.
   * @return name of player 2.
   */
  String getPlayer2Name();

  /**
   * Returns count of arrows with player 1.
   * @return count of arrows with player 1
   */
  int getPlayer1ArrowCount();

  /**
   * Returns count of arrows with player 2.
   * @return count of arrows with player 2
   */
  int getPlayer2ArrowCount();
}
