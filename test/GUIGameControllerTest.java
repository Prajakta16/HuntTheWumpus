import org.junit.Before;
import org.junit.Test;

import maze.controller.GUIGameController;
import maze.model.IViewModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test class for GUIGameController.
 */
public class GUIGameControllerTest {


  private MockView mockView;
  private StringBuilder inputLog;
  private StringBuilder outputLog;

  @Before
  public void setUp() throws Exception {
    inputLog = new StringBuilder();
    outputLog = new StringBuilder();
    mockView = new MockView(inputLog, outputLog);
  }

  @Test
  public void setModel() {
    GUIGameController guiGameController = new GUIGameController(15000);
    guiGameController.setMazeView(mockView);
    assertEquals("Adding listeners\n"
                         + "Adding listeners\n", inputLog.toString());
    assertEquals("Added listeners\n"
                         + "Resetting focus\n", outputLog.toString());
  }

  @Test
  public void setMazeView() {
    GUIGameController guiGameController = new GUIGameController(15000);
    guiGameController.setMazeView(mockView);
    assertEquals("Adding listeners\n"
                         + "Adding listeners\n", inputLog.toString());
    assertEquals("Added listeners\n"
                         + "Resetting focus\n", outputLog.toString());
  }

  @Test
  public void getGameReadOnlyModel() {
    GUIGameController guiGameController = new GUIGameController(15000);
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertFalse(readOnlyModel.isOver());
  }

  @Test
  public void verifyPlayerMovement() {
    GUIGameController guiGameController = new GUIGameController(15000);
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    readOnlyModel.getPlayerGameStatusAndInfo(1);
    assertEquals("Player moved", outputLog.toString());
  }

}
