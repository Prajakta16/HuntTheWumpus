package maze.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Class representing a cave in the maze.
 */
public class Tunnel extends AbstractRoom {
  /**
   * Constructor for the Tunnel.
   *
   * @param roomId Room id in the maze
   */
  public Tunnel(int roomId) {
    super(roomId);
  }

  @Override
  public boolean isCave() {
    return false;
  }

  @Override
  public Set<SmellName> getSmellIconSet() {
    throw new IllegalArgumentException("No smells in tunnel");
  }

  @Override
  public List<Room> findAllConnectingTunnels(Direction direction) {
    List<Room> connectingTunnels = new ArrayList<>();
    Direction incoming = getInverseDirection(direction);
    for (Direction d : getPossibleMoves().keySet()) { //check between the 2 possible dirn
      if (d != incoming) { //take the outgoing direction
        if (getPossibleMoves().get(d).isCave()) {
          connectingTunnels.add(this);
          return connectingTunnels;
        } else {
          List<Room> found = getPossibleMoves().get(d).findAllConnectingTunnels(d);
          if (found != null && found.size() != 0) {
            connectingTunnels = found;
          }
        }
      }
    }
    connectingTunnels.add(this);
    return connectingTunnels;
  }

}
