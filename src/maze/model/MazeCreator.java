package maze.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for creating a maze given the inputs.
 */
public class MazeCreator implements IMazeCreator {

  @Override
  public String verifyInputForMazeCreation(IGameInput gameInput) {
    String message = "success";

    int minWallsRequiredForAccessingAllRooms = gameInput.getRows() * (gameInput.getColumns() - 1)
                                               + gameInput.getColumns() * (gameInput.getRows() - 1)
                                               - (gameInput.getRows() * gameInput.getColumns() - 1);

    if (gameInput.getRows() <= 2 && gameInput.getColumns() <= 2) {
      message = "Minimum configuration is 3*3";
    } else if (gameInput.getRows() > 100 || gameInput.getRows() <= 0) {
      message = "Rows should be 1 - 100";
    } else if (gameInput.getColumns() > 100 || gameInput.getColumns() <= 0) {
      message = "Columns should be 1 - 100";
    } else if (!gameInput.isPerfect()) {
      if (gameInput.getRemainingWalls() > minWallsRequiredForAccessingAllRooms) {
        message = "Remaining walls should be <= " + minWallsRequiredForAccessingAllRooms
                          + " for path to exist";
      }
    } else {
      if (gameInput.getPercentBats() > 100 || gameInput.getPercentBats() <= 0) {
        message = "% should be 1 - 100";
      }
      if (gameInput.getPercentPits() > 100 || gameInput.getPercentPits() <= 0) {
        message = "% should be 1 - 100";
      }
    }
    //    else if (gameInput.getThemeName() == ThemeName.GET_GOLD_REACH_GOAL) {
    //      if (gameInput.getPercentGold() > 100 || gameInput.getPercentGold() <= 0) {
    //        message = "% Gold should be 1 - 100";
    //      }
    //      if (gameInput.getPercentThieves() > 100 || gameInput.getPercentThieves() <= 0) {
    //        message = "% Thieves should be 1 - 100";
    //      }
    //    }
    return message;
  }

  @Override
  public InterfaceMaze generateMazeFromCorrectInput(IGameInput gameInput) {
    if (!verifyInputForMazeCreation(gameInput).equals("success")) {
      throw new IllegalArgumentException();
    }

    //prepping the features associated with maze
    Map<FeatureType, Integer> featureMap = new HashMap<>();

    featureMap.put(FeatureType.BAT, gameInput.getPercentBats());
    featureMap.put(FeatureType.PIT, gameInput.getPercentPits());
    featureMap.put(FeatureType.WUMPUS, 1);

    InterfaceMaze maze = null;
    int randomSeed = 15000;
    int countRemoveWrappingWalls = 2;

    if (gameInput.isPerfect()) {
      try {
        maze = new MazeBuilder(gameInput.getRows(), gameInput.getColumns(), MazeType.PERFECT,
                gameInput.isWrapping(), randomSeed)
                       .removeInsideWalls()
                       .addFeatures(featureMap)
                       .build();
      } catch (IllegalArgumentException | IllegalStateException e) {
        System.out.println("Invalid arguments");
      }
    } else {
      if (gameInput.isWrapping()) {
        try {
          maze = new MazeBuilder(gameInput.getRows(), gameInput.getColumns(), MazeType.IMPERFECT,
                  gameInput.isWrapping(), randomSeed)
                         .removeInsideWalls(gameInput.getRemainingWalls())
                         .removeWrappingWalls(countRemoveWrappingWalls)
                         .addFeatures(featureMap)
                         .build();
        } catch (IllegalArgumentException | IllegalStateException e) {
          System.out.println("Invalid arguments");
        }
      } else {
        try {
          maze = new MazeBuilder(gameInput.getRows(), gameInput.getColumns(), MazeType.IMPERFECT,
                  gameInput.isWrapping(), randomSeed)
                         .removeInsideWalls(gameInput.getRemainingWalls())
                         .addFeatures(featureMap)
                         .build();
        } catch (IllegalArgumentException | IllegalStateException e) {
          System.out.println("Invalid arguments");
        }
      }
    }
    return maze;
  }
}
