package maze.model;

/**
 * Game input object. It will be useful for creating the mza. Instead of giving all fields
 * separately, wrapping it in an object.
 */
public class GameInput implements IGameInput {
  private ThemeName themeName;
  private boolean wrapping;
  private boolean perfect;
  private int rows;
  private int columns;
  private int randomSeed = 15000;
  private int remainingWalls = 0;
  private int percentBats = 0;
  private int percentPits = 0;
  private int percentGold = 0;
  private int percentThieves = 0;
  private int countPlayers = 0;
  private String player1Name = "";
  private String player2Name = "";
  private int player1ArrowCount = 0;
  private int player2ArrowCount = 0;

  /**
   * Constructor for Fame Input.
   *
   * @param themeName    theme of game
   * @param wrapping     true if maze is wrapping
   * @param perfect      true if maze is perfect
   * @param rows         rows in maze
   * @param columns      columns in maze
   * @param countPlayers count of players
   * @param player1Name  name of player 1
   * @param player2Name  name of player 2
   */
  public GameInput(ThemeName themeName, boolean wrapping, boolean perfect, int rows, int columns,
                   int countPlayers, String player1Name, String player2Name, int player1ArrowCount,
                   int player2ArrowCount) {
    this.themeName = themeName;
    this.wrapping = wrapping;
    this.perfect = perfect;
    this.rows = rows;
    this.columns = columns;
    this.countPlayers = countPlayers;
    this.player1Name = player1Name;
    this.player2Name = player2Name;
    this.player1ArrowCount = player1ArrowCount;
    this.player2ArrowCount = player2ArrowCount;
  }

  @Override
  public String toString() {
    return "GameInput{" + "gameTheme=" + themeName + ", players=" + countPlayers + ", wrapping="
                   + wrapping + ", perfect=" + perfect + ", rows=" + rows + ", columns=" + columns
                   + ", remainingWalls=" + remainingWalls
                   + ", percentBats=" + percentBats + ", percentPits=" + percentPits
                   + ", percentGold=" + percentGold + ", percentThieves=" + percentThieves
                   + ", player1Name=" + player1Name + ", player2Name=" + player2Name
                   + ", player1Arrows=" + player1ArrowCount
                   + ", player2Arrows=" + player2ArrowCount
                   + ", randomSeed=" + randomSeed + '}';
  }

  @Override
  public ThemeName getThemeName() {
    return themeName;
  }

  @Override
  public boolean isWrapping() {
    return wrapping;
  }

  @Override
  public boolean isPerfect() {
    return perfect;
  }

  @Override
  public int getRows() {
    return rows;
  }

  @Override
  public int getColumns() {
    return columns;
  }

  @Override
  public int getRemainingWalls() {
    return remainingWalls;
  }

  @Override
  public int getPercentBats() {
    return percentBats;
  }

  @Override
  public int getPercentPits() {
    return percentPits;
  }

  @Override
  public int getPercentGold() {
    return percentGold;
  }

  @Override
  public int getPercentThieves() {
    return percentThieves;
  }

  @Override
  public void setRemainingWalls(int remainingWalls) {
    this.remainingWalls = remainingWalls;
  }

  @Override
  public void setPercentBats(int percentBats) {
    this.percentBats = percentBats;
  }

  @Override
  public void setPercentPits(int percentPits) {
    this.percentPits = percentPits;
  }

  @Override
  public void setPercentGold(int percentGold) {
    this.percentGold = percentGold;
  }

  @Override
  public void setPercentThieves(int percentThieves) {
    this.percentThieves = percentThieves;
  }

  @Override
  public int getCountPlayers() {
    return countPlayers;
  }

  @Override
  public String getPlayer1Name() {
    return player1Name;
  }

  @Override
  public String getPlayer2Name() {
    return player2Name;
  }

  @Override
  public int getPlayer1ArrowCount() {
    return player1ArrowCount;
  }

  @Override
  public int getPlayer2ArrowCount() {
    return player2ArrowCount;
  }
}
