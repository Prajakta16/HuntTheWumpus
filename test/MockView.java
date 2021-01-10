import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import maze.view.IMazeView;

/**
 * Mock view for testing GUI controller.
 */
public class MockView implements IMazeView {
  private StringBuilder inputLog;
  private StringBuilder outputLog;

  /**
   * Constructor for mock view.
   * @param inputLog input
   * @param outputLog output
   */
  public MockView(StringBuilder inputLog, StringBuilder outputLog) {
    this.inputLog = inputLog;
    this.outputLog = outputLog;
  }

  private void addToOutputLog(String message) {
    this.outputLog.append(message).append("\n");
  }


  private void addToInputLog(String message) {
    this.inputLog.append(message).append("\n");
  }

  @Override
  public void resetFocus() {
    addToOutputLog("Resetting focus");
  }

  @Override
  public void addActionListener(ActionListener listener) {
    addToInputLog("Adding listeners");
  }

  @Override
  public void addKeyListener(KeyListener listener) {
    addToInputLog("Adding listeners");
    addToOutputLog("Added listeners");
  }

  @Override
  public void closeView() {
    addToOutputLog("Closing view");
  }

  @Override
  public JPanel getGamePanel() {
    addToOutputLog("Retrieving jPanel");
    return null;
  }

  @Override
  public void refreshViewWithModelState() {
    addToOutputLog("Refreshing view with model state");
  }

  @Override
  public void refreshErrorMessage() {
    addToOutputLog("Refresh the error message");
  }

  @Override
  public void setUpGameView() {
    addToOutputLog("Setting up game");
  }

  @Override
  public void updatePlayerStatusPanel() {
    addToOutputLog("Updating player status");
  }

  @Override
  public JComboBox getAvailablePlayerMoves() {
    addToOutputLog("Getting available player moves");
    return null;
  }

  @Override
  public JComboBox getAvailableArrowMoves() {
    addToOutputLog("Getting available arrow moves");

    return null;
  }

  @Override
  public JTextField getDistanceField() {
    addToOutputLog("Getting distance field");

    return null;
  }

  @Override
  public void setErrorMessage(String errorMessage) {
    addToOutputLog("Setting error message");

  }

  @Override
  public void checkGameFinished() {
    addToOutputLog("Checking game is finished");

  }
}
