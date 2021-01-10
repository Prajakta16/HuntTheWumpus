package maze.model;

/**
 * Player information during his game play.
 */
public class CurrentPlayerInfo {
  private int gold;
  private final String name;
  private Room room;
  private int weapons;

  /**
   * Constructor for the player info.
   *
   * @param gold       with the player
   * @param name       name of player
   * @param room       room of player
   * @param arrowCount weapons with player
   */
  public CurrentPlayerInfo(int gold, String name, Room room, int arrowCount) {
    this.gold = gold;
    this.name = name;
    this.room = room;
    this.weapons = arrowCount;
  }

  public int getGold() {
    return gold;
  }

  public String getName() {
    return name;
  }

  public Room getRoom() {
    return room;
  }

  public int getRoomId() {
    return room.getRoomId();
  }

  public int getArrowCount() {
    return weapons;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("CurrentPlayerInfo{" + "gold=").append(gold).append(", name='")
            .append(name).append('\'').append(", room=").append(room.getRoomId())
            .append(", weapons={");

    stringBuilder.append("Arrows: " + weapons);
    stringBuilder.append("}");

    stringBuilder.append(room.getSmellDetails().toString());

    return stringBuilder.toString();
  }
}
