import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import maze.controller.GUIGameController;
import maze.controller.TextBasedGameController;
import maze.model.FeatureType;
import maze.model.GameWumpus;
import maze.model.InterfaceGame;
import maze.model.InterfaceMaze;
import maze.model.InterfacePlayer;
import maze.model.MazeBuilder;
import maze.model.MazeType;
import maze.model.Player;
import maze.model.ThemeName;
import maze.view.IThemeSelectorView;
import maze.view.ThemeSelectorView;

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
  public static void main(String[] args) throws IOException {
    int randomSeed = 15000;

    Scanner scanner = new Scanner(System.in);

    System.out.println("Enter 1 for GUI game, any other number for text-based game");

    int gameMode = 0;
    if (args.length > 0) {
      if (args[0].equals("--gui") || args[0].equals("--config=themeConfig.txt")) {
        gameMode = 1;
      } else if (args[0].equals("--text")) {
        gameMode = 2;
      }
    } else {
      try {
        gameMode = scanner.nextInt();
      } catch (InputMismatchException e) {
        System.out.println("Please rerun and enter correct input");
      }
    }

    if (gameMode == 1) {
      // Create the view
      IThemeSelectorView themeSelectorView = new ThemeSelectorView("Theme Selector");

      // Create the controller with the view, model will be assigned later
      GUIGameController guiGameController = new GUIGameController(themeSelectorView, randomSeed);
    } else {
      InterfaceGame game = getModelForGame(randomSeed);
      assert game != null;

      // create the controller
      Readable reader = new InputStreamReader(System.in);
      TextBasedGameController textBasedGameController =
              new TextBasedGameController(reader, System.out);

      // Start game
      textBasedGameController.start(game);
    }
  }

  private static InterfaceGame getModelForGame(int randomSeed) throws IOException {
    InterfaceMaze maze = null;
    int row;
    int col;
    int remaining = Integer.MAX_VALUE;
    MazeType mazeType;
    boolean isPerfect;
    boolean isWrapping;
    int countRemoveWrappingWalls = Integer.MAX_VALUE;
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
      row = scanInteger(scanner);
      System.out.println("How many columns should the maze comprise of?");
      col = scanInteger(scanner);

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
        remaining = scanInteger(scanner);
      }

      System.out.println("Is your maze wrapping? Enter true or false:");
      isWrapping = scanner.nextBoolean();
      if (!isPerfect) {
        System.out.println("How many wrapping walls should be removed in the maze? Max of"
                                   + (row + col) + " can be removed to make it fully wrapping");
        countRemoveWrappingWalls = scanInteger(scanner);
      }
    }

    Map<FeatureType, Integer> featureMap = new HashMap<>();
    System.out.println("Provide % of BATS");
    int percent = scanInteger(scanner);
    featureMap.put(FeatureType.BAT, percent);
    System.out.println("Provide % of PITS");
    percent = scanInteger(scanner);
    featureMap.put(FeatureType.PIT, percent);
    System.out.println("There will be one wumpus in the game");
    featureMap.put(FeatureType.WUMPUS, 1);
    System.out.println("Generating your maze!!");

    if (isPerfect) {
      try {
        maze = new MazeBuilder(row, col, mazeType, isWrapping, randomSeed)
                       .removeInsideWalls()
                       .addFeatures(featureMap)
                       .build();
      } catch (IllegalArgumentException | IllegalStateException e) {
        System.out.println("Invalid arguments for game");
      }
    } else {
      if (isWrapping) {
        try {
          maze = new MazeBuilder(row, col, mazeType, isWrapping, randomSeed)
                         .removeInsideWalls(remaining)
                         .removeWrappingWalls(countRemoveWrappingWalls)
                         .addFeatures(featureMap)
                         .build();
        } catch (IllegalArgumentException | IllegalStateException e) {
          System.out.println("Invalid arguments for game");
        }
      } else {
        try {
          maze = new MazeBuilder(row, col, mazeType, isWrapping, randomSeed)
                         .removeInsideWalls(remaining)
                         .addFeatures(featureMap)
                         .build();
        } catch (IllegalArgumentException | IllegalStateException e) {
          System.out.println("Invalid arguments for game");
        }
      }
    }

    assert maze != null;

    System.out.println("Enter number of players");
    int numPlayers = scanInteger(scanner);
    if (numPlayers <= 0 || numPlayers > 2) {
      System.out.println("Entered incorrect players, only 2 players are allowed, starting the "
                                 + "game in 2 player mode.");
      numPlayers = 1;
    }

    // Get player and game information
    List<InterfacePlayer> players = new ArrayList<>();
    for (int i = 1; i <= numPlayers; i++) {
      System.out.println("Enter name for player" + i);
      String playerName = scanner.next();
      InterfacePlayer player = new Player(playerName);

      System.out.println("Enter the number of arrows with player" + i);
      int numArrows = scanInteger(scanner);
      if (numArrows <= 0) {
        System.out.println("Entered incorrect arrows, randomly assigning arrows");
        player.addArrow(new Random(randomSeed).nextInt(5));
      } else {
        player.addArrow(numArrows);
      }
      players.add(player);
    }

    InterfaceGame game = null;
    try {
      game = new GameWumpus(maze, players, ThemeName.HUNT_THE_WUMPUS, randomSeed);
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid game arguments");
    }
    return game;
  }

  private static int scanInteger(Scanner scanner) throws IOException {
    boolean foundCorrectNumber = false;
    int n = 0;
    String inputNumber;

    while (!foundCorrectNumber) {
      inputNumber = scanner.next();

      try {
        n = Integer.parseInt(inputNumber);
        if (n < 0) {
          System.out.println("Incorrect number specified, please enter correct one");
        } else {
          foundCorrectNumber = true;
        }
      } catch (IllegalArgumentException e) {
        System.out.println("Incorrect mumber specified, please enter correct one");
      }
    }
    return n;
  }

}
