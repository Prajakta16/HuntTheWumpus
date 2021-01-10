package maze.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Theme class for forest mode.
 */
public class ThemeForest implements IThemeFeatures {
  @Override
  public List<FeatureType> getFeaturesForTheme() {
    List<FeatureType> gameFeatures = new ArrayList<>();
    gameFeatures.add(FeatureType.LION);
    gameFeatures.add(FeatureType.ELEPHANT);
    gameFeatures.add(FeatureType.SWAMP);
    return gameFeatures;
  }

  @Override
  public Map<FeatureType, FeatureType> getFeatureMappingForTheme() {
    Map<FeatureType, FeatureType> gameFeatures = new HashMap();
    gameFeatures.put(FeatureType.WUMPUS, FeatureType.LION);
    gameFeatures.put(FeatureType.BAT, FeatureType.ELEPHANT);
    gameFeatures.put(FeatureType.PIT, FeatureType.SWAMP);
    gameFeatures.put(FeatureType.ARROW, FeatureType.GUN);
    return gameFeatures;
  }

  @Override
  public ThemeName getThemeName() {
    return ThemeName.SHOOT_THE_LION;
  }

  @Override
  public String getBigName() {
    return "Catch the lion!";
  }

  @Override
  public String getDescription() {
    return "As a hunter your biggest dream is to hunt the lion. Elephants can pick you up and "
                   + "can move you from through different connected areas. You have a gun"
                   + " which can be used to shoot the lion! ";
  }
}
