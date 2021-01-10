package maze.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface depicting a room inside of a maze.
 */
public interface Room {

  /**
   * Sets all the allowed moves to another room in a specific direction.
   *
   * @param possibleMoves mapping between room and another room in given direction
   */
  public void setPossibleMoves(Map<Direction, Room> possibleMoves);

  /**
   * Gets all possible moves from a room.
   *
   * @return mapping between room and another room in given direction
   */
  public Map<Direction, Room> getPossibleMoves();

  /**
   * Returns ID of a room.
   *
   * @return ID of a room
   */
  public int getRoomId();

  /**
   * Returns ID of a cave.
   *
   * @return ID of a cave
   */
  public String getCaveId();

  /**
   * Returns whether room is a cave.
   *
   * @return true if room is a cave
   */
  public boolean isCave();

  /**
   * Adds another room in a given direction.
   *
   * @param direction direction in which room needs to be added
   * @param room      room that needs to be added
   */
  public void addRoomInDirection(Direction direction, Room room);

  /**
   * Adds specified feature to the room.
   *
   * @param featureType to be added to the room
   */
  public void addNewFeature(FeatureType featureType);

  /**
   * Gets information about all the features in the room.
   *
   * @return map containing information about existence of all the features in the room
   */
  public Map<FeatureType, Boolean> getFeatures();

  /**
   * Find cave at the given distance for this room.
   *
   * @param inverseIncomingDirection Direction of the last room through which this room is entered
   * @param distanceForCave          distance for the cave in the given direction
   * @return
   */
  Room findCaveAtDistance(Direction inverseIncomingDirection, int distanceForCave);

  /**
   * Adds a cave adjacent to a given cave.
   *
   * @param direction direction in which given cave is adjacent
   * @param cave      cave that is adjacent
   */
  public void addAdjacentCave(Direction direction, Room cave);


  /**
   * Gets mapping of adjacent cave sin different directions.
   *
   * @return Map of directional mappings
   */
  public Map<Direction, Room> getAdjacentCaves();

  /**
   * Returns the smell object having information about smell in the room.
   *
   * @return smell object
   */
  public Smell getSmellDetails();


  /**
   * Set a room as visited.
   */
  void setVisited();

  /**
   * Returns whether room is visited during the game play.
   * @return true if room is visited during the game play
   */
  boolean isVisited();

  /**
   * Get set of all smells in the room.
   * @return set of all smells in the room
   */
  Set<SmellName> getSmellIconSet();

  /**
   * Adds a connecting tunnel in a given direction.
   * @param direction direction
   * @param tunnel tunnel
   */
  void addAdjacentTunnel(Direction direction, Room tunnel);

  /**
   * Returns list of all adjacent tunnels in a given direction.
   * @return list of all adjacent tunnels
   */
  Map<Direction, List<Room>> getConnectingTunnels();

  /**
   * Returns list of all adjacent tunnels in a given direction.
   *
   * @param direction of incoming room
   * @return list of all adjacent tunnels
   */
  List<Room> findAllConnectingTunnels(Direction direction);
}
