import org.junit.Before;
import org.junit.Test;

import java.awt.Component;
import java.awt.event.KeyEvent;

import maze.controller.GUIGameController;
import maze.model.IViewModel;
import maze.model.InterfaceGame;
import maze.view.IMazeView;
import maze.view.IView;
import maze.view.ThemeSelectorView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test class for Game play using GUI controllers.
 */
public class GamePlayTestViaGUIControllers {
  private IMazeView mazeView;
  private IView inputView;
  private GUIGameController guiGameController;
  private InterfaceGame gameModel;
  private IViewModel gameReadOnlyModel;
  private int randomSeed = 15000;

  @Before
  public void setUp() throws Exception {

    // Create the view
    ThemeSelectorView themeSelectorView = new ThemeSelectorView("Theme Selector");

    // Create the controller with the view, model will be assigned later
    guiGameController = new GUIGameController(themeSelectorView, randomSeed);
    guiGameController.getThemeViewButtonClickedMap().get("Confirm theme").run();

  }

  @Test
  public void testStartNewGame() {
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=12, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
  }

  @Test
  public void testStartNewGame2Player() {
    guiGameController.getGameInputView().setModeTo2Player();
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals(2, readOnlyModel.getPlayerCount());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=12, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
    assertEquals("assertEquals(\"CurrentPlayerInfo{gold=0, name='PLAYER 1', \"\n"
                         + "                         + \"room=12, weapons={Arrows: 1}\",\n"
                         + "            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());",
            readOnlyModel.getPlayerGameStatusAndInfo(2).toString());
  }

  @Test
  public void testGameRestart() {
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=12, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
    guiGameController.getMazeViewButtonClickedMap().get("Restart Game").run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=12, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
  }

  @Test
  public void testEndGame() {
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=12, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
    try {
      guiGameController.getMazeViewButtonClickedMap().get("Quit Game").run();
    } catch (Exception e) {
      fail("Game should have been successfully closed");
    }
  }

  @Test
  public void movePlayerUp() {
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=12, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
    guiGameController.getKeyPresses().get(KeyEvent.VK_UP).run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', room=1, "
                         + "weapons={Arrows: 1}WUMPUS-You smell a Wumpus!\n"
                         + "PIT-You feel a draft\n",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
  }

  @Test
  public void movePlayerDown() {
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=12, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
    guiGameController.getKeyPresses().get(KeyEvent.VK_DOWN).run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', room=1, "
                         + "weapons={Arrows: 1}WUMPUS-You smell a Wumpus!\n"
                         + "PIT-You feel a draft\n",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
  }

  @Test
  public void movePlayerEast() {
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=12, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
    guiGameController.getKeyPresses().get(KeyEvent.VK_RIGHT).run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', room=1, "
                         + "weapons={Arrows: 1}WUMPUS-You smell a Wumpus!\n"
                         + "PIT-You feel a draft\n",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
  }

  @Test
  public void movePlayerWest() {
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=12, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
    guiGameController.getKeyPresses().get(KeyEvent.VK_LEFT).run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', room=17, "
                         + "weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
  }

  @Test
  public void movePlayerThroughHallway() {
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=12, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
    guiGameController.getKeyPresses().get(KeyEvent.VK_LEFT).run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', room=9, "
                         + "weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
  }

  @Test
  public void moveThroughWrappingMaze() {
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=6, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
    guiGameController.getKeyPresses().get(KeyEvent.VK_RIGHT).run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', room=0, "
                         + "weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
  }

  @Test
  public void shootsThroughWrappingMaze() {
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=6, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
    guiGameController.getMazeViewButtonClickedMap().get("Shoot").run();
    assertEquals("Arrow was lost, you lose",
            gameReadOnlyModel.getLatestMessageForPlayer(1));
  }

  @Test
  public void testNextPossibleMoves() {
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=12, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
    guiGameController.getKeyPresses().get(KeyEvent.VK_LEFT).run();
    assertEquals("[WEST, SOUTH, EAST]",
            readOnlyModel.getValidMoveForPlayer(1).toString());
  }

  @Test
  public void testShootUnsuccessful() {
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=12, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());

    guiGameController.getMazeViewButtonClickedMap().get("Shoot").run();

    assertEquals("ARROW was unsuccessful. ARROWs are over, you lost",
            readOnlyModel.getLatestMessageForPlayer(1));
  }

  @Test
  public void testOnePlayerWinsIn2PlayerMode() {
    guiGameController.getGameInputView().setModeTo2Player();
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals(2, readOnlyModel.getPlayerCount());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=12, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
    assertEquals("assertEquals(\"CurrentPlayerInfo{gold=0, name='PLAYER 1', \"\n"
                         + "                         + \"room=12, weapons={Arrows: 1}\",\n"
                         + "            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());",
            readOnlyModel.getPlayerGameStatusAndInfo(2).toString());

    assertEquals(1, gameReadOnlyModel.getActivePlayerNumber());
    guiGameController.getKeyPresses().get(KeyEvent.VK_UP).run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', room=1, "
                         + "weapons={Arrows: 1}WUMPUS-You smell a Wumpus!\n"
                         + "PIT-You feel a draft\n",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());

    assertEquals(2, gameReadOnlyModel.getActivePlayerNumber());
    guiGameController.getKeyPresses().get(KeyEvent.VK_UP).run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 2', room=11, "
                         + "weapons={Arrows: 1}\n",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());

    assertEquals(1, gameReadOnlyModel.getActivePlayerNumber());
    guiGameController.getMazeViewButtonClickedMap().get("Shoot").run();

    assertEquals("You successfully killed wumpus!! Player 1 wins",
            readOnlyModel.getLatestMessageForPlayer(1));

    assertTrue(gameReadOnlyModel.hasWon());

  }

  @Test
  public void tesActivePlayerIsToggledIn2PlayerMode() {
    guiGameController.getGameInputView().setModeTo2Player();
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals(2, readOnlyModel.getPlayerCount());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=12, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
    assertEquals("assertEquals(\"CurrentPlayerInfo{gold=0, name='PLAYER 1', \"\n"
                         + "                         + \"room=16, weapons={Arrows: 1}\",\n"
                         + "            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());",
            readOnlyModel.getPlayerGameStatusAndInfo(2).toString());

    assertEquals(1, gameReadOnlyModel.getActivePlayerNumber());
    guiGameController.getKeyPresses().get(KeyEvent.VK_UP).run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', room=1, "
                         + "weapons={Arrows: 1}WUMPUS-You smell a Wumpus!\n"
                         + "PIT-You feel a draft\n",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());

    assertEquals(2, gameReadOnlyModel.getActivePlayerNumber());
    guiGameController.getKeyPresses().get(KeyEvent.VK_RIGHT).run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 2', room=11, "
                         + "weapons={Arrows: 1}\n",
            readOnlyModel.getPlayerGameStatusAndInfo(2).toString());


    assertEquals(1, gameReadOnlyModel.getActivePlayerNumber());
    guiGameController.getKeyPresses().get(KeyEvent.VK_DOWN).run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', room=12, "
                         + "weapons={Arrows: 1}WUMPUS-You smell a Wumpus!\n"
                         + "PIT-You feel a draft\n",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());

    assertEquals(2, gameReadOnlyModel.getActivePlayerNumber());
    guiGameController.getKeyPresses().get(KeyEvent.VK_LEFT).run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 2', room=11, "
                         + "weapons={Arrows: 1}\n",
            readOnlyModel.getPlayerGameStatusAndInfo(2).toString());

  }

  @Test
  public void testShootWumpusAndWin() {
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=12, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());

    guiGameController.getKeyPresses().get(KeyEvent.VK_UP).run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', room=1, "
                         + "weapons={Arrows: 1}WUMPUS-You smell a Wumpus!\n"
                         + "PIT-You feel a draft\n",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
    guiGameController.getMazeViewButtonClickedMap().get("Shoot").run();

    assertEquals("You successfully killed wumpus!!",
            readOnlyModel.getLatestMessageForPlayer(1));
  }

  @Test
  public void testShootWumpusAndWinArrowPassingThroughHallway() {
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=10, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());

    guiGameController.getKeyPresses().get(KeyEvent.VK_UP).run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', room=10, "
                         + "weapons={Arrows: 1}WUMPUS-You smell a Wumpus!\n"
                         + "PIT-You feel a draft\n",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
    guiGameController.getMazeViewButtonClickedMap().get("Shoot").run();

    assertEquals("You successfully killed wumpus!!",
            readOnlyModel.getLatestMessageForPlayer(1));
  }

  @Test
  public void testShootWumpusAndWinArrowPassingThroughMultipleRooms() {
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=9, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());

    guiGameController.getMazeViewButtonClickedMap().get("Shoot").run();

    assertEquals("You successfully killed wumpus!!",
            readOnlyModel.getLatestMessageForPlayer(1));
  }

  @Test
  public void testOnePlayerLosesIn2PlayerMode() {
    guiGameController.getGameInputView().setModeTo2Player();
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals(2, readOnlyModel.getPlayerCount());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=12, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
    assertEquals("assertEquals(\"CurrentPlayerInfo{gold=0, name='PLAYER 1', \"\n"
                         + "                         + \"room=12, weapons={Arrows: 1}\",\n"
                         + "            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());",
            readOnlyModel.getPlayerGameStatusAndInfo(2).toString());

    assertEquals(1, gameReadOnlyModel.getActivePlayerNumber());
    guiGameController.getKeyPresses().get(KeyEvent.VK_UP).run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', room=1, "
                         + "weapons={Arrows: 1}WUMPUS-You smell a Wumpus!\n"
                         + "PIT-You feel a draft\n",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());

    assertEquals(2, gameReadOnlyModel.getActivePlayerNumber());
    guiGameController.getKeyPresses().get(KeyEvent.VK_UP).run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 2', room=11, "
                         + "weapons={Arrows: 1}\n",
            readOnlyModel.getPlayerGameStatusAndInfo(2).toString());

    assertEquals(1, gameReadOnlyModel.getActivePlayerNumber());
    guiGameController.getMazeViewButtonClickedMap().get("Shoot").run();

    assertEquals("You missed an arrow. All arrows are lost, you lose the game",
            readOnlyModel.getLatestMessageForPlayer(1));

    assertEquals(2, gameReadOnlyModel.getActivePlayerNumber());
    guiGameController.getKeyPresses().get(KeyEvent.VK_DOWN).run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 2', room=11, "
                         + "weapons={Arrows: 1}\n",
            readOnlyModel.getPlayerGameStatusAndInfo(2).toString());
    assertEquals(2, gameReadOnlyModel.getActivePlayerNumber());

  }

  @Test
  public void testBothPlayersLoseIn2PlayerMode() {
    guiGameController.getGameInputView().setModeTo2Player();
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals(2, readOnlyModel.getPlayerCount());


    assertEquals(1, gameReadOnlyModel.getActivePlayerNumber());
    guiGameController.getKeyPresses().get(KeyEvent.VK_UP).run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', room=1, "
                         + "weapons={Arrows: 1}WUMPUS-You smell a Wumpus!\n"
                         + "PIT-You feel a draft\n",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());

    assertEquals(2, gameReadOnlyModel.getActivePlayerNumber());
    guiGameController.getKeyPresses().get(KeyEvent.VK_UP).run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', room=1, "
                         + "weapons={Arrows: 1}WUMPUS-You smell a Wumpus!\n"
                         + "PIT-You feel a draft\n",
            readOnlyModel.getPlayerGameStatusAndInfo(2).toString());

    assertEquals(1, gameReadOnlyModel.getActivePlayerNumber());
    guiGameController.getKeyPresses().get(KeyEvent.VK_RIGHT).run();
    assertEquals("You were eaten by a wumpus, game lost",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());

    assertFalse(gameReadOnlyModel.isOver());

    assertEquals(2, gameReadOnlyModel.getActivePlayerNumber());
    guiGameController.getKeyPresses().get(KeyEvent.VK_RIGHT).run();
    assertEquals("You were eaten by a wumpus, game lost",
            readOnlyModel.getPlayerGameStatusAndInfo(2).toString());

    assertTrue(gameReadOnlyModel.isOver());

  }

  @Test
  public void testPlayerLosesByWumpusEating() {
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=12, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());

    guiGameController.getKeyPresses().get(KeyEvent.VK_UP).run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', room=1, "
                         + "weapons={Arrows: 1}WUMPUS-You smell a Wumpus!\n"
                         + "PIT-You feel a draft\n",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());

    guiGameController.getKeyPresses().get(KeyEvent.VK_DOWN).run();
    assertTrue(readOnlyModel.isOver());
    assertEquals("You were eaten by a wumpus, game lost",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
  }

  @Test
  public void testPlayerTransportedbyBat() {
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=12, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());

    guiGameController.getKeyPresses().get(KeyEvent.VK_UP).run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', room=1, "
                         + "weapons={Arrows: 1}WUMPUS-You smell a Wumpus!\n"
                         + "PIT-You feel a draft\n",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());

    guiGameController.getKeyPresses().get(KeyEvent.VK_UP).run();
    assertEquals("You were transported by bats",
            readOnlyModel.getLatestMessageForPlayer(1));
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', room=17, "
                         + "weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
  }

  @Test
  public void testPlayerLosesByFallingInAPit() {
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=12, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());

    guiGameController.getKeyPresses().get(KeyEvent.VK_UP).run();
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', room=1, "
                         + "weapons={Arrows: 1}WUMPUS-You smell a Wumpus!\n"
                         + "PIT-You feel a draft\n",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());

    guiGameController.getKeyPresses().get(KeyEvent.VK_RIGHT).run();
    assertTrue(readOnlyModel.isOver());
    assertEquals("You fell in a pit, game lost",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());
  }

  @Test
  public void testPlayerLosesByArrowDepletion() {
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=12, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());

    guiGameController.getMazeViewButtonClickedMap().get("Shoot").run();

    assertEquals("ARROW was unsuccessful. ARROWs are over, you lost",
            readOnlyModel.getLatestMessageForPlayer(1));
  }


  @Test
  public void keyBoardInputs() {
    guiGameController.getInputViewButtonClickedMap().get("Start Game").run();
    IViewModel readOnlyModel = guiGameController.getGameReadOnlyModel();
    assertEquals(6, readOnlyModel.getColumnsInGrid());
    assertEquals(5, readOnlyModel.getRowsInGrid());
    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', "
                         + "room=12, weapons={Arrows: 1}",
            readOnlyModel.getPlayerGameStatusAndInfo(1).toString());

    KeyEvent key = new KeyEvent((Component) mazeView, KeyEvent.KEY_PRESSED,
            System.currentTimeMillis(), 0, KeyEvent.VK_UP, 'Z');
    ((Component) mazeView).dispatchEvent(key);

    assertEquals("CurrentPlayerInfo{gold=0, name='PLAYER 1', \"\n"
                         + "                         + \"room=6, weapons={Arrows: 1}",
            readOnlyModel.getLatestMessageForPlayer(1));


  }
}
