package maze;

/**
 * Enum for different Maze Types. Useful in MazeBuilder class when we are building the maze. As maze
 * is still under consideration we cannot use dynamic dispatch to identify the maze type.
 */
public enum MazeType {
  PERFECT,
  IMPERFECT
}
