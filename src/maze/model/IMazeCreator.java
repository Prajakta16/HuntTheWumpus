package maze.model;

/**
 * Interface for creating a maze given the inputs.
 */
public interface IMazeCreator {

  /**
   * Verifies the input and returns either an appropriate exception or success message.
   * @return message as either exception or success
   */
  public String verifyInputForMazeCreation(IGameInput gameInput);

  /**
   * Generates the maze using Maze Builder methods and supplied game input.
   * @param gameInput input for the game
   * @return the created maze
   */
  public InterfaceMaze generateMazeFromCorrectInput(IGameInput gameInput);
}
