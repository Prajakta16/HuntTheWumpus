package maze;

import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Test class for Maze Builder.
 */
public class MazeBuilderTest {
  private int randomSeed = 15000;

  @Test
  public void testValidPerfectNonWrappingMazeCreation() {
    try {
      Maze perfectNonWrappingMaze = (Maze) new MazeBuilder(3, 4, MazeType.PERFECT,
              false, randomSeed)
                                              .removeInsideWalls()
                                              .addGold()
                                              .addThieves()
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
                                           .addGold()
                                           .addThieves()
                                           .build();
    } catch (IllegalArgumentException e) {
      fail("Above statement should have thrown an exception");
    }
  }

  @Test
  public void testValidImperfectNonWrappingMazeCreation() {
    try {
      Maze imperfectNonWrappingMaze = (Maze) new MazeBuilder(3, 4, MazeType.IMPERFECT,
              false, randomSeed)
                                                .removeInsideWalls(3)
                                                .addGold()
                                                .addThieves()
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
                                             .addGold()
                                             .addThieves()
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
                                 .addGold()
                                 .addThieves()
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
                                 .addGold()
                                 .addThieves()
                                 .build();
      fail("Above statement should have thrown an exception");
    } catch (IllegalArgumentException e) {
      //
    }
  }

  @Test
  public void testGeneratingMazeWithoutAddingGold() {
    try {
      Maze maze = (Maze) new MazeBuilder(4, 2, MazeType.PERFECT, false, randomSeed)
                                 .removeInsideWalls()
                                 .addThieves()
                                 .build();
      fail("Above statement should have thrown an exception");
    } catch (IllegalStateException e) {
      //
    }
  }

  @Test
  public void testGeneratingMazeWithoutAddingThieves() {
    try {
      Maze maze = (Maze) new MazeBuilder(4, 2, MazeType.IMPERFECT, true, randomSeed)
                                 .removeInsideWalls(2)
                                 .removeWrappingWalls(3)
                                 .addGold()
                                 .build();
      fail("Above statement should have thrown an exception");
    } catch (IllegalStateException e) {
      //
    }
  }

  @Test
  public void testGeneratingMazeWithoutRemovingWalls() {
    try {
      Maze maze = (Maze) new MazeBuilder(4, 2, MazeType.PERFECT, false, randomSeed)
                                 .addGold()
                                 .addThieves()
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
                                 .addGold()
                                 .addThieves()
                                 .build();
      fail("Above statement should have thrown an exception");
    } catch (IllegalStateException e) {
      //
    }
  }

}


