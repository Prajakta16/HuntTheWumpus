package maze.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing a room in the maze.
 */
public abstract class AbstractRoom implements Room {
  private int roomId;
  private Map<Direction, Room> possibleMoves;
  private boolean isVisited;

  /**
   * Constructor for Room.
   *
   * @param roomId ID of the room
   */
  public AbstractRoom(int roomId) {
    this.roomId = roomId;
    possibleMoves = new HashMap<>();
    isVisited = false;
  }

  @Override
  public void setVisited() {
    isVisited = true;
  }

  @Override
  public boolean isVisited() {
    return isVisited;
  }

  @Override
  public void addAdjacentCave(Direction direction, Room room) {
    if (!room.isCave()) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public void addAdjacentTunnel(Direction direction, Room room) {
    if (!room.isCave()) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public Map<Direction, List<Room>> getConnectingTunnels() {
    if (!this.isCave()) {
      throw new IllegalArgumentException();
    }
    return null;
  }

  @Override
  public Map<Direction, Room> getAdjacentCaves() {
    if (!this.isCave()) {
      throw new IllegalArgumentException();
    }
    return null;
  }

  @Override
  public void setPossibleMoves(Map<Direction, Room> possibleMoves) {
    this.possibleMoves = possibleMoves;
  }

  @Override
  public Map<Direction, Room> getPossibleMoves() {
    return possibleMoves;
  }

  @Override
  public int getRoomId() {
    return roomId;
  }

  @Override
  public String getCaveId() {
    if (!this.isCave()) {
      System.out.println("Room is a tunnel, not a cave");
      throw new IllegalArgumentException();
    }
    return "";
  }

  @Override
  public void addNewFeature(FeatureType featureType) {
    if (!this.isCave()) {
      throw new IllegalArgumentException("Room is a tunnel, not a cave and features cannot "
                                                 + "be added");
    }
  }

  @Override
  public Map<FeatureType, Boolean> getFeatures() {
    if (!this.isCave()) {
      throw new IllegalArgumentException("Room is a tunnel, not a cave and features don't exist");
    }
    return null;
  }

  @Override
  public Smell getSmellDetails() {
    if (!this.isCave()) {
      throw new IllegalArgumentException("Tunnel does not have smell");
    }
    return null;
  }

  @Override
  public boolean isCave() {
    return false;
  }

  @Override
  public void addRoomInDirection(Direction direction, Room room) {
    possibleMoves.put(direction, room);
  }

  Direction getInverseDirection(Direction direction) {
    HashMap<Direction, Direction> complimentaryDirections = new HashMap<>();
    complimentaryDirections.put(Direction.EAST, Direction.WEST);
    complimentaryDirections.put(Direction.WEST, Direction.EAST);
    complimentaryDirections.put(Direction.NORTH, Direction.SOUTH);
    complimentaryDirections.put(Direction.SOUTH, Direction.NORTH);
    return complimentaryDirections.get(direction);
  }

  @Override
  public Room findCaveAtDistance(Direction inverseIncomingDirection, int distanceForCave) {
    if (this.isCave()) {
      if (distanceForCave == 1) {
        return (Cave) this;
      } else {
        if (getPossibleMoves().containsKey(inverseIncomingDirection)) {
          distanceForCave--;
          return getPossibleMoves().get(inverseIncomingDirection)
                         .findCaveAtDistance(inverseIncomingDirection, distanceForCave);
        } else {
          //throw new IllegalArgumentException("Wall encountered on straight path");
          return null;
        }
      }
    } else {
      Direction incoming = getInverseDirection(inverseIncomingDirection);
      for (Direction direction : getPossibleMoves().keySet()) { //check between the 2 possible dirn
        if (direction != incoming) { //take the outgoing direction
          return getPossibleMoves().get(direction).findCaveAtDistance(direction, distanceForCave);
        }
      }
      throw new IllegalArgumentException("Maze incorrectly formed, tunnel cannot find a door out");
    }
  }

}
