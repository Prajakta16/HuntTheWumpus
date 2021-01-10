package maze.model;

import java.util.List;
import java.util.Map;

/**
 * Interface giving methods for identifying features of a theme. Eg For Gold game, we would have
 * gold and thieves randomly assigned to different caves.
 */
public interface IThemeFeatures {

  /**
   * Gets all associated features with the theme.
   * @return list of features
   */
  public List<FeatureType> getFeaturesForTheme();

  /**
   * Gets all associated features with respect to original wumpus, bats and pits.
   * @return list of features
   */
  public Map<FeatureType, FeatureType> getFeatureMappingForTheme();

  /**
   * Returns name of the theme.
   * @return name of the theme.
   */
  public ThemeName getThemeName();

  /**
   * Returns the big name of the theme.
   * @return big name of the theme.
   */
  public String getBigName();

  /**
   * Returns the description of the theme.
   * @return description of the theme.
   */
  public String getDescription();
}
