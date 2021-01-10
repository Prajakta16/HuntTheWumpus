package maze.controller;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import maze.listener.ButtonListener;
import maze.listener.KeyboardListener;
import maze.model.AbstractGame;
import maze.model.Direction;
import maze.model.FeatureType;
import maze.model.GameWumpus;
import maze.model.IGameInput;
import maze.model.IMazeCreator;
import maze.model.IThemeFeatures;
import maze.model.IViewModel;
import maze.model.InterfaceGame;
import maze.model.InterfaceMaze;
import maze.model.InterfacePlayer;
import maze.model.MazeCreator;
import maze.model.Player;
import maze.model.ThemeForest;
import maze.model.ThemeMarine;
import maze.model.ThemeName;
import maze.model.ThemeSpace;
import maze.model.ThemeWumpus;
import maze.view.GameInputView;
import maze.view.IMazeView;
import maze.view.IThemeSelectorView;
import maze.view.IView;
import maze.view.MazeView;
import maze.view.ThemeSelectorView;

/**
 * Implementation of our controller.
 */
public class GUIGameController {
  //  private IModel model;
  private IView gameInputView;
  private IThemeSelectorView themeSelectorView;
  private IMazeView mazeView;
  private InterfaceGame gameModel;
  private int randomSeed;
  private IViewModel gameReadOnlyModel;
  private IGameInput gameInput;
  private Map<Character, Runnable> keyTypes;
  private Map<Integer, Runnable> keyPresses;
  private Map<Integer, Runnable> keyReleases;
  private Map<String, Runnable> mazeViewButtonClickedMap;
  private Map<String, Runnable> inputViewButtonClickedMap;
  private Map<String, Runnable> themeViewButtonClickedMap;

  /**
   * Constructor.
   */
  public GUIGameController(IThemeSelectorView themeSelectorView, int randomSeed) {
    this.themeSelectorView = themeSelectorView;
    this.randomSeed = randomSeed;
    configureButtonListenerForTheme();
  }

  public GUIGameController(int randomSeed) {
    this.randomSeed = randomSeed;
  }

  public void setModel(InterfaceGame gameModel) {
    this.gameModel = gameModel;
  }

  /**
   * Sets the maze view to theme selector.
   *
   * @param themeSelectorView view for theme selector
   */
  private void setThemeView(IThemeSelectorView themeSelectorView) {
    this.themeSelectorView = themeSelectorView;
    configureButtonListenerForTheme();
  }

  /**
   * Sets the maze view to use while playing.
   *
   * @param v the view
   */
  public void setMazeView(IMazeView v) {
    mazeView = v;
    // create and set the keyboard listener
    configureKeyBoardListener();
    configureButtonListenerForMazeView();
    // set focus back to main frame so that keyboard events work
    mazeView.resetFocus();
  }

  /**
   * Sets the view to use.
   *
   * @param v the view
   */
  public void setInputView(IView v) {
    gameInputView = v;
    // create and set the button listener
    configureButtonListenerForInputView();

  }


  /**
   * Creates and sets a keyboard listener for the view In effect it creates snippets of code as
   * Runnable object, one for each time a key is typed, pressed and released, only for those that
   * the program needs. In this example, we need to toggle color when user TYPES 'd', make the
   * message all caps when the user PRESSES 'c' and reverts to the original string when the user
   * RELEASES 'c'. Thus we create three snippets of code and put them in the appropriate map. This
   * example shows how to create these snippets of code using lambda functions, a new feature in
   * Java 8. For more information on Java 8's syntax of lambda expressions, go here:
   * https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html The above tutorial
   * has specific example for GUI listeners. Last we create our KeyboardListener object, set all its
   * maps and then give it to the view.
   */
  private void configureKeyBoardListener() {
    keyTypes = new HashMap<>();
    keyPresses = new HashMap<>();
    keyReleases = new HashMap<>();

    keyPresses.put(KeyEvent.VK_UP, new Runnable() {
      public void run() {
        gameModel.movePlayer(Direction.NORTH, new Random(randomSeed));
      }
    });
    keyReleases.put(KeyEvent.VK_UP, new Runnable() {
      public void run() {
        refreshMazeView();
      }
    });

    keyPresses.put(KeyEvent.VK_DOWN, new Runnable() {
      public void run() {
        gameModel.movePlayer(Direction.SOUTH, new Random(randomSeed));
      }
    });
    keyReleases.put(KeyEvent.VK_DOWN, new Runnable() {
      public void run() {
        refreshMazeView();
      }
    });

    keyPresses.put(KeyEvent.VK_LEFT, new Runnable() {
      public void run() {
        gameModel.movePlayer(Direction.WEST, new Random(randomSeed));
      }
    });
    keyReleases.put(KeyEvent.VK_LEFT, new Runnable() {
      public void run() {
        refreshMazeView();
      }
    });

    keyPresses.put(KeyEvent.VK_RIGHT, new Runnable() {
      public void run() {
        gameModel.movePlayer(Direction.EAST, new Random(randomSeed));
      }
    });
    keyReleases.put(KeyEvent.VK_RIGHT, new Runnable() {
      public void run() {
        refreshMazeView();
      }
    });


    KeyboardListener kbd = new KeyboardListener();
    kbd.setKeyTypedMap(keyTypes);
    kbd.setKeyPressedMap(keyPresses);
    kbd.setKeyReleasedMap(keyReleases);

    mazeView.addKeyListener(kbd);
  }

  private void refreshMazeView() {
    mazeView.refreshViewWithModelState();
    mazeView.refreshErrorMessage();
    mazeView.updatePlayerStatusPanel();
    mazeView.checkGameFinished();
  }

  /**
   * Setting up the button listeners for maze view.
   */
  private void configureButtonListenerForMazeView() {
    mazeViewButtonClickedMap = new HashMap<>();
    ButtonListener buttonListener = new ButtonListener();

    mazeViewButtonClickedMap.put("Restart Game", new RefreshGameView());
    mazeViewButtonClickedMap.put("New Game", new GenerateNewGameView());
    mazeViewButtonClickedMap.put("Quit Game", () -> System.exit(0));
    mazeViewButtonClickedMap.put("Pressed the game panel", () -> mazeView.resetFocus());
    mazeViewButtonClickedMap.put("Move", new Runnable() {
      @Override
      public void run() {
        JComboBox movementDirectionSelection = mazeView.getAvailablePlayerMoves();
        Direction selectedDirection = (Direction) movementDirectionSelection.getSelectedItem();
        gameModel.movePlayer(selectedDirection, new Random(randomSeed));
        refreshMazeView();
      }
    });

    mazeViewButtonClickedMap.put("Shoot", new Runnable() {
      @Override
      public void run() {
        JComboBox movementDirectionSelection = mazeView.getAvailableArrowMoves();
        Direction selectedDirection = (Direction) movementDirectionSelection.getSelectedItem();
        JTextField distanceField = mazeView.getDistanceField();
        int distance;
        try {
          distance = Integer.parseInt(distanceField.getText());
          gameModel.useArrow(selectedDirection, distance);
          refreshMazeView();
        } catch (NumberFormatException e) {
          mazeView.setErrorMessage("Enter numeric distance");
        }
      }
    });

    buttonListener.setButtonClickedActionMap(mazeViewButtonClickedMap);
    this.mazeView.addActionListener(buttonListener);
  }

  /**
   * Setting up the button listeners for input view.
   */
  private void configureButtonListenerForInputView() {
    inputViewButtonClickedMap = new HashMap<>();

    inputViewButtonClickedMap.put("Start Game", new GenerateMazeView());
    inputViewButtonClickedMap.put("imperfect", () -> {
      gameInputView.toggleDisableInputForRemainingWalls();
    });
    inputViewButtonClickedMap.put("perfect", () -> {
      gameInputView.toggleDisableInputForRemainingWalls();
    });
    inputViewButtonClickedMap.put("1", () -> {
      gameInputView.addPlayerInfoPanel(1);
    });
    inputViewButtonClickedMap.put("2", () -> {
      System.out.println("pressed 2");
      gameInputView.addPlayerInfoPanel(2);
    });

    inputViewButtonClickedMap.put("Exit Button", () -> System.exit(0));

    ButtonListener buttonListener = new ButtonListener();
    buttonListener.setButtonClickedActionMap(inputViewButtonClickedMap);
    this.gameInputView.addActionListener(buttonListener);
  }

  /**
   * Setting up the button listeners for theme selection view.
   */
  private void configureButtonListenerForTheme() {
    themeViewButtonClickedMap = new HashMap<>();
    ButtonListener buttonListener = new ButtonListener();

    themeViewButtonClickedMap.put("Confirm theme", new GenerateInputView());

    buttonListener.setButtonClickedActionMap(themeViewButtonClickedMap);
    themeSelectorView.addActionListener(buttonListener);
  }


  /**
   * Implementation of action to generate input view according to the theme.
   */
  class GenerateInputView implements Runnable {
    @Override
    public void run() {
      String themeSelected = themeSelectorView.getGameThemeButtonGroup().getSelection()
                                     .getActionCommand();
      themeSelectorView.closeView();

      ThemeName specifiedTheme = null;
      for (ThemeName themeName : ThemeName.values()) {
        if (themeSelected.equalsIgnoreCase(themeName.toString())) {
          specifiedTheme = themeName;
        }
      }
      gameInputView = new GameInputView("Input for game - " + themeSelected, specifiedTheme);
      setInputView(gameInputView);

      generateInputForm(themeSelected);
    }

    private void generateInputForm(String themeSelected) {
      Map<FeatureType, FeatureType> themeFeatures;
      IThemeFeatures theme = null;
      if (themeSelected.equals(ThemeName.HUNT_THE_WUMPUS.toString())) {
        theme = new ThemeWumpus();
      } else if (themeSelected.equals(ThemeName.CATCH_THE_SHARK.toString())) {
        theme = new ThemeMarine();
      } else if (themeSelected.equals(ThemeName.DESTROY_ASTEROID.toString())) {
        theme = new ThemeSpace();
      } else if (themeSelected.equals(ThemeName.SHOOT_THE_LION.toString())) {
        theme = new ThemeForest();
      }

      //      else if (themeSelected.equals(ThemeName.GET_GOLD_REACH_GOAL.toString())) {
      //        theme = new Game();
      //      }

      assert theme != null;
      gameInputView.setThemeName(theme);
      themeFeatures = theme.getFeatureMappingForTheme();

      gameInputView.getInputPanel().setLayout(new GridLayout(13, 3, 1, 1));
      gameInputView.addInputForRows();
      gameInputView.addInputForColumns();
      gameInputView.addInputForMazePerfectType();
      gameInputView.addInputForMazeWrappingType();
      gameInputView.addInputForRemainingWalls();

      for (FeatureType featureType : themeFeatures.keySet()) {
        switch (featureType.toString()) {
          case "BAT":
            gameInputView.addInputForBats();
            break;
          case "PIT":
            gameInputView.addInputForPits();
            break;
          case "WUMPUS":
            gameInputView.addInputForWumpus();
            break;
          default:

        }
      }
      gameInputView.addInputFoNumPlayers();
    }
  }

  class GenerateMazeView implements Runnable {
    @Override
    public void run() {
      String message = "";
      IMazeCreator mazeCreator = new MazeCreator();
      InterfaceMaze maze;

      try {
        gameInput = gameInputView.getGameInput(); //read input from view
        System.out.println(gameInput.toString());

        //get appropriate message from model
        message = mazeCreator.verifyInputForMazeCreation(gameInput);

        if (message.equals("success")) {
          generateMaze();
        }
      } catch (NumberFormatException e) { //error while reading from view
        message = "An input field is empty";
      }

      gameInputView.setErrorMessage(gameInputView.getErrorMessageLabel(), message);
    }

  }

  /**
   * Returns read-only model.
   *
   * @return read-only model
   */
  public IViewModel getGameReadOnlyModel() {
    return gameReadOnlyModel;
  }

  /**
   * Returns input form view.
   *
   * @return input form view
   */
  public IView getGameInputView() {
    return gameInputView;
  }

  /**
   * Return maze view.
   * @return maze view
   */
  public IMazeView getMazeView() {
    return mazeView;
  }

  private void generateMaze() {
    IMazeCreator mazeCreator = new MazeCreator();
    InterfaceMaze maze = mazeCreator.generateMazeFromCorrectInput(gameInput);
    if (maze != null) {
      //generate the maze and navigate to the game play view
      gameInputView.closeView(); //close the input view

      List<InterfacePlayer> players = new ArrayList<>();

      InterfacePlayer player1 = new Player(gameInput.getPlayer1Name());
      player1.addArrow(gameInput.getPlayer1ArrowCount());
      players.add(player1);

      if (!gameInput.getPlayer2Name().equalsIgnoreCase("dummy")) {
        InterfacePlayer player2 = new Player(gameInput.getPlayer2Name());
        player2.addArrow(gameInput.getPlayer2ArrowCount());
        players.add(player2);
      }

      //gameModel = new GameWumpus(maze, players, gameInput.getThemeName(), randomSeed);
      AbstractGame abstractGame = new GameWumpus(maze, players, gameInput.getThemeName(),
              randomSeed);
      gameModel = abstractGame; //read-write model
      gameReadOnlyModel = abstractGame; //read-only model

      setModel(gameModel);
      mazeView = new MazeView("Here is your maze! Happy playing!", gameReadOnlyModel);

      mazeView.setUpGameView();
      setMazeView(mazeView);

    }

  }

  class GenerateNewGameView implements Runnable {
    @Override
    public void run() {
      try {
        mazeView.closeView();
        mazeView = null;
        IThemeSelectorView themeSelectorView = new ThemeSelectorView("Theme Selector");
        setThemeView(themeSelectorView);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  class RefreshGameView implements Runnable {
    @Override
    public void run() {
      mazeView.closeView();
      generateMaze();
    }
  }

  public Map<Character, Runnable> getKeyTypes() {
    return keyTypes;
  }

  public Map<Integer, Runnable> getKeyPresses() {
    return keyPresses;
  }

  public Map<Integer, Runnable> getKeyReleases() {
    return keyReleases;
  }

  public Map<String, Runnable> getMazeViewButtonClickedMap() {
    return mazeViewButtonClickedMap;
  }

  public Map<String, Runnable> getInputViewButtonClickedMap() {
    return inputViewButtonClickedMap;
  }

  public Map<String, Runnable> getThemeViewButtonClickedMap() {
    return themeViewButtonClickedMap;
  }

}
