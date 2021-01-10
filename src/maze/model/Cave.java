package maze.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class representing a cave in the maze.
 */
public class Cave extends AbstractRoom {
  private String caveId;
  //Cave could have such as thief, gold, wumpus, bats, or pit
  private Map<FeatureType, Boolean> features;
  private Map<Direction, Room> adjacentCaves;
  private Map<Direction, List<Room>> connectingTunnels;

  /**
   * Constructor for the cave.
   *
   * @param roomId Room id in the maze
   * @param caveId Cave id among all the caves
   */
  public Cave(int roomId, String caveId) {
    super(roomId);
    this.caveId = caveId;
    this.features = new HashMap<>();
    this.features.put(FeatureType.WUMPUS, false);
    this.features.put(FeatureType.BAT, false);
    this.features.put(FeatureType.PIT, false);
    this.features.put(FeatureType.GOLD, false);
    this.features.put(FeatureType.THIEF, false);
    adjacentCaves = new HashMap<>();
    connectingTunnels = new HashMap<>();
  }

  @Override
  public Map<Direction, Room> getAdjacentCaves() {
    return adjacentCaves;
  }

  @Override
  public void addAdjacentCave(Direction direction, Room cave) {
    this.adjacentCaves.put(direction, cave);
  }

  @Override
  public void addAdjacentTunnel(Direction direction, Room tunnel) {
    List<Room> tunnels = new ArrayList<>();
    if (connectingTunnels.get(direction) == null || connectingTunnels.get(direction).size() == 0) {
      connectingTunnels.put(direction, tunnels);
    }
    connectingTunnels.get(direction).add(tunnel);
  }

  @Override
  public Map<Direction, List<Room>> getConnectingTunnels() {
    return connectingTunnels;
  }

  @Override
  public Map<FeatureType, Boolean> getFeatures() {
    return features;
  }


  @Override
  public void addNewFeature(FeatureType featureType) {
    features.put(featureType, true);
  }

  @Override
  public String getCaveId() {
    return caveId;
  }

  @Override
  public boolean isCave() {
    return true;
  }

  @Override
  public Smell getSmellDetails() {
    Set<FeatureType> adjFeatures = new HashSet<>();
    Map<Direction, Room> adjCaves = getAdjacentCaves();
    for (Direction direction : adjCaves.keySet()) {
      if (adjCaves.get(direction).getFeatures().get(FeatureType.PIT)) {
        adjFeatures.add(FeatureType.PIT);
      }
      //      if (adjCaves.get(direction).getFeatures().get(FeatureType.BAT)) {
      //        adjFeatures.add(FeatureType.BAT);
      //      }
      if (adjCaves.get(direction).getFeatures().get(FeatureType.WUMPUS)) {
        adjFeatures.add(FeatureType.WUMPUS);
      }
    }
    return new Smell(adjFeatures);
  }

  public Set<SmellName> getSmellIconSet() {
    return getSmellDetails().getSmellIconSet();
  }

  @Override
  public List<Room> findAllConnectingTunnels(Direction direction) {
    return new ArrayList<>();
  }
}
