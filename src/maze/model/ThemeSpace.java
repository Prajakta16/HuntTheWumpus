package maze.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Theme class for Space mode.
 */
public class ThemeSpace implements IThemeFeatures {
  @Override
  public List<FeatureType> getFeaturesForTheme() {
    List<FeatureType> gameFeatures = new ArrayList<>();
    gameFeatures.add(FeatureType.ASTEROID);
    gameFeatures.add(FeatureType.ALIEN);
    gameFeatures.add(FeatureType.BLACK_HOLE);
    return gameFeatures;
  }

  @Override
  public Map<FeatureType, FeatureType> getFeatureMappingForTheme() {
    Map<FeatureType, FeatureType> gameFeatures = new HashMap();
    gameFeatures.put(FeatureType.WUMPUS, FeatureType.ASTEROID);
    gameFeatures.put(FeatureType.BAT, FeatureType.ALIEN);
    gameFeatures.put(FeatureType.PIT, FeatureType.BLACK_HOLE);
    gameFeatures.put(FeatureType.ARROW, FeatureType.SUPER_WEAPON);
    return gameFeatures;
  }

  @Override
  public ThemeName getThemeName() {
    return ThemeName.DESTROY_ASTEROID;
  }

  @Override
  public String getBigName() {
    return "Earth protector";
  }

  @Override
  public String getDescription() {
    return "You are set on a mission to save Earth from an asteroid coming its way. Aliens "
                   + "can move you from through different sections in space. You have a magic"
                   + " weapon which can be used to destroy the asteroid! ";
  }
}
