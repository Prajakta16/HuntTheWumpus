package maze.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Interface depicting view for maze and game play.
 */
public interface IMazeView {
  /**
   * Reset the focus on the appropriate part of the view that has the keyboard listener attached to
   * it, so that keyboard events will still flow through.
   */
  void resetFocus();

  /**
   * This is to force the view to have a method to set up the buttons. The name has been chosen
   * deliberately. This is the same method signature to add an action listener in Java Swing.
   * Thus our Swing-based implementation of this interface will already have such a method.
   *
   * @param listener the listener to add
   */
  void addActionListener(ActionListener listener);

  /**
   * this is to force the view to have a method to set up the keyboard. The name
   * has been chosen deliberately. This is the same method signature to add a
   * key listener in Java Swing.
   * Thus our Swing-based implementation of this interface will already have
   * such a method.
   *
   * @param listener the listener to add
   */
  void addKeyListener(KeyListener listener);

  /**
   * Closes the view.
   */
  void closeView();

  /**
   * Returns the panel for game view.
   * @return  panel for game
   */
  JPanel getGamePanel();

  /**
   * Method that the controller will call asking the view to refresh itself after some mutation
   * in the model. A read-only method in a IViewModel will be exposed to the view
   */
  void refreshViewWithModelState();

  /**
   * Method that the controller will call asking the view to refresh itself after some mutation
   * in the model. A read-only method in a IViewModel will be exposed to the view
   */
  void refreshErrorMessage();

  /**
   * Sets up the game view at the start of the game.
   */
  void setUpGameView();

  /**
   * Updates player status based on player moves.
   */
  public void updatePlayerStatusPanel();

  /**
   * Returns the available moves combobox. This is useful for the controller to retrieve selection
   * on an action listener for player movement.
   * @return available moves combobox
   */
  JComboBox getAvailablePlayerMoves();

  /**
   * Returns the available moves combobox. This is useful for the controller to retrieve selection
   * on an action listener for shooting an arrow.
   * @return available moves combobox
   */
  JComboBox getAvailableArrowMoves();

  /**
   * Returns distance field for shooting the arrow.
   * @return distance field for shooting the arrow
   */
  JTextField getDistanceField();

  /**
   * Sets error message in case of user input error.
   * @param errorMessage error message.
   */
  void setErrorMessage(String errorMessage);

  /**
   * Verifies whether the game is over and updates the game panel.
   */
  void checkGameFinished();
}


