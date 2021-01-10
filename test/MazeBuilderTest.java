import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import maze.model.FeatureType;
import maze.model.Maze;
import maze.model.MazeBuilder;
import maze.model.MazeType;

import static org.junit.Assert.fail;

/**
 * Test class for Maze Builder.
 */
public class MazeBuilderTest {
  private int randomSeed = 15000;
  private Map<FeatureType, Integer> featureMap;

  @Before
  public void setUp() {
    featureMap = new HashMap<>();
    featureMap.put(FeatureType.PIT, 25);
    featureMap.put(FeatureType.BAT, 40);
    featureMap.put(FeatureType.WUMPUS, 12);
    featureMap.put(FeatureType.GOLD, 60);
    featureMap.put(FeatureType.THIEF, 10);
  }

  @Test
  public void testValidPerfectNonWrappingMazeCreation() {
    try {
      Maze perfectNonWrappingMaze = (Maze) new MazeBuilder(3, 4, MazeType.PERFECT,
              false, randomSeed)
                                                   .removeInsideWalls()
                                                   .addFeatures(featureMap)
                                                   .build();
    } catch (IllegalArgumentException e) {
      fail("Above statement should have thrown an exception");
    }
  }

  @Test
  public void testValidPerfectWrappingMazeCreation() {
    try {
      Maze perfectWrappingMaze = (Maze) new MazeBuilder(3, 4, MazeType.PERFECT, true, randomSeed)
                                                .removeInsideWalls()
                                                .addFeatures(featureMap)
                                                .build();
    } catch (IllegalArgumentException e) {
      fail("Above statement should not have thrown an exception");
    }
  }

  @Test
  public void testValidImperfectNonWrappingMazeCreation() {
    try {
      Maze imperfectNonWrappingMaze = (Maze) new MazeBuilder(3, 4, MazeType.IMPERFECT,
              false, randomSeed)
                                                     .removeInsideWalls(3)
                                                     .addFeatures(featureMap)
                                                     .build();
    } catch (IllegalArgumentException e) {
      fail("Above statement should have thrown an exception");
    }
  }

  @Test
  public void testValidImperfectWrappingMazeCreation() {
    try {
      Maze imperfectWrappingMaze = (Maze) new MazeBuilder(3, 4, MazeType.IMPERFECT,
              true, randomSeed)
                                                  .removeInsideWalls(3)
                                                  .removeWrappingWalls(2)
                                                  .addFeatures(featureMap)
                                                  .build();
    } catch (IllegalArgumentException e) {
      fail("Above statement should have thrown an exception");
    }
  }


  @Test
  public void testInvalidRows() {
    try {
      Maze maze = (Maze) new MazeBuilder(0, 3, MazeType.IMPERFECT, true, randomSeed)
                                 .removeInsideWalls(2)
                                 .removeWrappingWalls(3)
                                 .addFeatures(featureMap)
                                 .build();
      fail("Above statement should have thrown an exception");
    } catch (IllegalArgumentException e) {
      //
    }
  }

  @Test
  public void testInvalidColumns() {
    try {
      Maze maze = (Maze) new MazeBuilder(4, -2, MazeType.IMPERFECT, true, randomSeed)
                                 .removeInsideWalls(2)
                                 .removeWrappingWalls(3)
                                 .addFeatures(featureMap)
                                 .build();
      fail("Above statement should have thrown an exception");
    } catch (IllegalArgumentException e) {
      //
    }
  }

  //  @Test
  //  public void testGeneratingMazeWithoutAddingGold() {
  //    try {
  //      Map<FeatureType, Integer> featureMap;
  //      featureMap = new HashMap<>();
  //      featureMap.put(FeatureType.PIT, 25);
  //      featureMap.put(FeatureType.BAT, 40);
  //      featureMap.put(FeatureType.WUMPUS, 12);
  //      featureMap.put(FeatureType.THIEF, 10);
  //
  //      Maze maze = (Maze) new MazeBuilder(4, 5, MazeType.PERFECT, false, randomSeed)
  //                                 .removeInsideWalls()
  //                                 .addFeatures(featureMap)
  //                                 .build();
  //      fail("Above statement should have thrown an exception");
  //    } catch (IllegalStateException e) {
  //      //
  //    }
  //  }
  //
  //  @Test
  //  public void testGeneratingMazeWithoutAddingThieves() {
  //    try {
  //      Maze maze = (Maze) new MazeBuilder(4, 2, MazeType.IMPERFECT, true, randomSeed)
  //                                 .removeInsideWalls(2)
  //                                 .removeWrappingWalls(3)
  //                                 .addFeatures(featureMap)
  //                                 .build();
  //      fail("Above statement should have thrown an exception");
  //    } catch (IllegalStateException e) {
  //      //
  //    }
  //  }

  @Test
  public void testGeneratingMazeWithoutRemovingWalls() {
    try {
      Maze maze = (Maze) new MazeBuilder(4, 2, MazeType.PERFECT, false, randomSeed)
                                 .addFeatures(featureMap)
                                 .build();
      fail("Above statement should have thrown an exception");
    } catch (IllegalStateException e) {
      //
    }
  }

  @Test
  public void testRemovingWrappingWallsForNonWrappingMaze() {
    try {
      Maze maze = (Maze) new MazeBuilder(4, 3, MazeType.IMPERFECT, false, randomSeed)
                                 .removeInsideWalls(3)
                                 .removeWrappingWalls(2)
                                 .addFeatures(featureMap)
                                 .build();
      fail("Above statement should have thrown an exception");
    } catch (IllegalStateException e) {
      //
    }
  }


}


