package maze.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Smell details of adjacent cells.
 */
public class Smell {

  Set<FeatureType> adjFeatures;
  Set<SmellName> smellIconSet;
  Map<FeatureType, String> smellIndications;

  /**
   * Constructor for smell object.
   *
   * @param adjFeatures set of unique adjacent features
   */
  public Smell(Set<FeatureType> adjFeatures) {
    this.adjFeatures = adjFeatures;
    this.smellIndications = new HashMap<>();
    smellIndications.put(FeatureType.WUMPUS, "You smell a Wumpus!");
    smellIndications.put(FeatureType.PIT, "You feel a draft");
    smellIndications.put(FeatureType.BAT, "You smell a something terrible, could be bats!");

    smellIconSet = new HashSet<>();
    for (FeatureType featureType : adjFeatures) {
      if (featureType == FeatureType.WUMPUS) {
        smellIconSet.add(SmellName.breeze);
      } else if (featureType == FeatureType.PIT) {
        smellIconSet.add(SmellName.stench);
      }
    }
  }

  public Set<SmellName> getSmellIconSet() {
    return smellIconSet;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();

    for (FeatureType featureType : adjFeatures) {
      stringBuilder.append(featureType).append("-");
      stringBuilder.append(smellIndications.get(featureType)).append("\n");
    }
    return stringBuilder.toString();
  }
}
