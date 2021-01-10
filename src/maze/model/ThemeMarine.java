package maze.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Theme class for marine mode.
 */
public class ThemeMarine implements IThemeFeatures {
  @Override
  public List<FeatureType> getFeaturesForTheme() {
    List<FeatureType> gameFeatures = new ArrayList<>();
    gameFeatures.add(FeatureType.SHARK);
    gameFeatures.add(FeatureType.SUBMARINE);
    gameFeatures.add(FeatureType.FISHNET);
    return gameFeatures;
  }

  @Override
  public Map<FeatureType, FeatureType> getFeatureMappingForTheme() {
    Map<FeatureType, FeatureType> gameFeatures = new HashMap();
    gameFeatures.put(FeatureType.WUMPUS, FeatureType.SHARK);
    gameFeatures.put(FeatureType.BAT, FeatureType.SUBMARINE);
    gameFeatures.put(FeatureType.PIT, FeatureType.FISHNET);
    gameFeatures.put(FeatureType.ARROW, FeatureType.TRANQUILIZER);
    return gameFeatures;
  }

  @Override
  public ThemeName getThemeName() {
    return ThemeName.CATCH_THE_SHARK;
  }

  @Override
  public String getBigName() {
    return "Save the fishes!";
  }

  @Override
  public String getDescription() {
    return "You are set on a mission to save fishes from a mad Shark whose purpose is to "
                   + "destroy all the fishes. Submarine can pick you up and "
                   + "can move you from through different connected seas. You have a tranquilizer"
                   + " which can be used to tranquilize the shark! ";
  }
}
