package maze.view;

import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import maze.model.IGameInput;
import maze.model.IThemeFeatures;

/**
 * Interface for view which takes user input.
 */
public interface IView {

  /**
   * This is to force the view to have a method to set up the buttons. The name has been chosen
   * deliberately. This is the same method signature to add an action listener in Java Swing. Thus
   * our Swing-based implementation of this interface will already have such a method.
   *
   * @param listener the listener to add
   */
  void addActionListener(ActionListener listener);

  /**
   * Adds section for remaining walls in the input view.
   */
  void addInputForRemainingWalls();

  /**
   * Toggles enable/disable for remaining fields.
   */
  void toggleDisableInputForRemainingWalls();

  /**
   * Adds section for rows in the input view.
   */
  void addInputForRows();

  /**
   * Sets mode to 2 player.
   */
  void setModeTo2Player();

  /**
   * Adds section for rows in the input view.
   */
  void addInputForColumns();

  /**
   * Adds section for maze type in the input view.
   */
  void addInputForMazePerfectType();

  /**
   * Adds section for maze type in the input view.
   */
  void addInputForMazeWrappingType();

  /**
   * Adds section for bats in the input view during.
   */
  void addInputForBats();

  /**
   * Adds section for pits in the input view during.
   */
  void addInputForPits();

  /**
   * Closes the view.
   */
  void closeView();

  /**
   * Returns the panel for the view.
   *
   * @return the panel for the view
   */
  JPanel getInputPanel();

  /**
   * Adds section for wumpus in the input view during.
   */
  void addInputForWumpus();

  /**
   * Adds section for gold in the input view during.
   */
  void addInputForGold();

  /**
   * Adds section for thieves in the input view during.
   */
  void addInputForThieves();

  /**
   * Adds section for player selection in the input view during.
   */
  void addInputFoNumPlayers();

  /**
   * Adds section for getting the player info.
   *
   * @param countPlayers number of players in the game
   */
  void addPlayerInfoPanel(int countPlayers);

  /**
   * Returns the input values which will be used for creation of the game.
   *
   * @return input values wrapped as an IGameObject
   */
  IGameInput getGameInput();

  /**
   * Sets the error message.
   *
   * @param label        label on view that would be updated
   * @param errorMessage message
   */
  void setErrorMessage(JLabel label, String errorMessage);

  /**
   * Returns label for empty field.
   *
   * @return label for empty field
   */
  JLabel getErrorMessageLabel();

  /**
   * Sets theme in the view.
   *
   * @param theme theme
   */
  void setThemeName(IThemeFeatures theme);

  //  /**
  //   * Returns combo box for arrow count which will be utilized by the controller
  //   to provide input to
  //   * the model.
  //   *
  //   * @return combo box for arrow count for player 1
  //   */
  //  JComboBox getPlayer1ArrowCount();
  //
  //  /**
  //   * Returns combo box for arrow count which will be utilized by the controller to provide
  //   input to
  //   * the model.
  //   *
  //   * @return combo box for arrow count for player 2
  //   */
  //  JComboBox getPlayer2ArrowCount();
}
