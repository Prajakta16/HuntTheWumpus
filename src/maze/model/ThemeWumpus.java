package maze.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Theme class for Wumpus mode.
 */
public class ThemeWumpus implements IThemeFeatures {
  @Override
  public List<FeatureType> getFeaturesForTheme() {
    List<FeatureType> wumpusGameFeatures = new ArrayList<>();
    wumpusGameFeatures.add(FeatureType.WUMPUS);
    wumpusGameFeatures.add(FeatureType.BAT);
    wumpusGameFeatures.add(FeatureType.PIT);
    return wumpusGameFeatures;
  }

  @Override
  public Map<FeatureType, FeatureType> getFeatureMappingForTheme() {
    Map<FeatureType, FeatureType> wumpusGameFeatures = new HashMap();
    wumpusGameFeatures.put(FeatureType.WUMPUS, FeatureType.WUMPUS);
    wumpusGameFeatures.put(FeatureType.BAT, FeatureType.BAT);
    wumpusGameFeatures.put(FeatureType.PIT, FeatureType.PIT);
    wumpusGameFeatures.put(FeatureType.ARROW, FeatureType.ARROW);
    return wumpusGameFeatures;
  }

  @Override
  public ThemeName getThemeName() {
    return ThemeName.HUNT_THE_WUMPUS;
  }

  @Override
  public String getBigName() {
    return "Hunt the Wumpus!";
  }

  @Override
  public String getDescription() {
    return "As the hunter your biggest dream is to hunt the wumpus. Bats can pick you up and "
                   + "can move you from through different connected caves. You have a arrow"
                   + " which can be used to shoot the wumpus! ";
  }
}
