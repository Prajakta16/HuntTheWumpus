package maze;

import java.util.Scanner;

/**
 * Driver class showcasing sample use cases that uses command line arguments to: 1. specify the size
 * of the maze 2. specify whether the maze is perfect or a non-perfect maze, and whether it is
 * wrapping or non-wrapping 3. if it is non-perfect, specify the number of walls remaining 4.
 * specify the starting point of the player 5. specify the goal location Demonstrate a player
 * navigating within the maze, collecting gold, being robbed, and reaching the goal location (see
 * details below for specific scenarios)
 */
public class Driver {

  /**
   * Main method of the driver class.
   *
   * @param args command line arguments arg1
   */
  public static void main(String[] args) {

    InterfaceMaze maze = null;
    int row;
    int col;
    int remaining = Integer.MAX_VALUE;
    MazeType mazeType;
    boolean isPerfect;
    boolean isWrapping;
    int countRemoveWrappingWalls = Integer.MAX_VALUE;
    int randomSeed = 15000;

    Scanner scanner = new Scanner(System.in);

    String run;
    System.out.println("To generate sample perfect maze enter p, \n"
                               + "to generate sample perfect wrapping maze enter pw \n"
                               + "to generate sample imperfect maze enter i \n"
                               + "to generate sample imperfect wrapping maze enter iw \n"
                               + "to generate custom maze enter any key \n");
    run = scanner.next();

    // Generating maze
    if (run.startsWith("p")) {
      row = 3;
      col = 4;
      isPerfect = true;
      mazeType = MazeType.PERFECT;
      if (run.equalsIgnoreCase("pw")) {
        isWrapping = true;
      } else {
        isWrapping = false;
      }
    } else if (run.startsWith("i")) {
      row = 3;
      col = 4;
      isPerfect = false;
      mazeType = MazeType.IMPERFECT;
      remaining = 3;
      if (run.equalsIgnoreCase("iw")) {
        isWrapping = true;
      } else {
        isWrapping = false;
      }
      if (isWrapping) {
        countRemoveWrappingWalls = 2;
      }

    } else {
      System.out.println("How many rows should the maze comprise of?");
      row = scanner.nextInt();
      System.out.println("How many columns should the maze comprise of?");
      col = scanner.nextInt();

      System.out.println("Is your maze perfect? Enter true or false:");
      isPerfect = scanner.nextBoolean();

      if (isPerfect) {
        mazeType = MazeType.PERFECT;
      } else {
        mazeType = MazeType.IMPERFECT;
      }

      int maxRemaining;
      maxRemaining = row * (col - 1) + col * (row - 1) - (row * col - 1);

      if (!isPerfect) {
        System.out.println("How many internal walls should remain in the maze? "
                                   + "Note that remaining walls should be less than or equals "
                                   + maxRemaining + " for a path to exist between any two rooms");
        remaining = scanner.nextInt();
      }

      System.out.println("Is your maze wrapping? Enter true or false:");
      isWrapping = scanner.nextBoolean();
      if (!isPerfect) {
        System.out.println("How many wrapping walls should be removed in the maze? Max of"
                                   + (row + col) + " can be removed to make it fully wrapping");
        countRemoveWrappingWalls = scanner.nextInt();
      }
    }

    System.out.println("Generating your maze!! Gold and thieves will be added randomly");

    if (isPerfect) {
      try {
        maze = new MazeBuilder(row, col, mazeType, isWrapping, randomSeed)
                       .removeInsideWalls()
                       .addGold()
                       .addThieves()
                       .build();
      } catch (IllegalArgumentException | IllegalStateException e) {
        System.out.println("Invalid arguments");
      }
    } else {
      if (isWrapping) {
        try {
          maze = new MazeBuilder(row, col, mazeType, isWrapping, randomSeed)
                         .removeInsideWalls(remaining)
                         .removeWrappingWalls(countRemoveWrappingWalls)
                         .addGold()
                         .addThieves()
                         .build();
        } catch (IllegalArgumentException | IllegalStateException e) {
          System.out.println("Invalid arguments");
        }
      } else {
        try {
          maze = new MazeBuilder(row, col, mazeType, isWrapping, randomSeed)
                         .removeInsideWalls(remaining)
                         .addGold()
                         .addThieves()
                         .build();
        } catch (IllegalArgumentException | IllegalStateException e) {
          System.out.println("Invalid arguments");
        }
      }
    }

    assert maze != null;
    //maze.printMaze();

    // Get player and game information
    System.out.println("Enter the player name:");
    String playerName = scanner.next();
    Player player = new Player(playerName);

    System.out.println("Enter starting position between 0 and " + (col * row - 1));
    int start = scanner.nextInt();
    System.out.println("Enter goal position between 0 and " + (col * row - 1));
    int goal = scanner.nextInt();

    Game game = null;
    try {
      game = new Game(maze, player, start, goal);
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid start and goal positions");
    }

    assert game != null;

    // Start game
    boolean quitFlag = false;
    String quit;
    while (!quitFlag) {
      System.out.println(game.printPlayerStatus());
      System.out.println("Available next steps");
      System.out.println(game.printAvailableDirections());

      System.out.println("Enter direction");
      Direction direction;
      String inputDirection = scanner.next();

      try {
        direction = Direction.valueOf(inputDirection.toUpperCase());
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException();
      }

      game.movePlayer(direction);
      if (game.isGoalReached()) {
        break;
      }

      System.out.println("Enter Q to quit, else press any other key");
      quit = scanner.next();

      if (quit.equalsIgnoreCase("q")) {
        quitFlag = true;
      }
    }
  }
}
