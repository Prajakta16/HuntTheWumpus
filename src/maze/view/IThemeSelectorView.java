package maze.view;

import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;

/**
 * Interface for Theme selector view.
 */
public interface IThemeSelectorView {

  /**
   * This is to force the view to have a method to set up the buttons. The name has been chosen
   * deliberately. This is the same method signature to add an action listener in Java Swing. Thus
   * our Swing-based implementation of this interface will already have such a method.
   *
   * @param listener the listener to add
   */
  void addActionListener(ActionListener listener);

  /**
   * Returns the button group on the view.
   *
   * @return button group on the view
   */
  ButtonGroup getGameThemeButtonGroup();

  /**
   * Closes the view.
   */
  void closeView();

  /**
   * Returns the next button.
   * @return the next button
   */
  JButton getNextButton();
}
