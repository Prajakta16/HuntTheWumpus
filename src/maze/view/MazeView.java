package maze.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import maze.model.CurrentPlayerInfo;
import maze.model.Direction;
import maze.model.FeatureType;
import maze.model.IViewModel;
import maze.model.InterfaceMaze;
import maze.model.SmellName;

/**
 * Class depicting view for maze and game play.
 */
public class MazeView extends JFrame implements IMazeView {
  private static String ICON_PATH = "res/icons/";
  private InterfaceMaze maze;
  private JPanel gamePanel;
  private JPanel playerPanel;
  private JPanel playerStatusPanel;
  private JPanel activityPanel;
  private JPanel endGamePanel;
  private JButton exitButton;
  private JButton restartButton;
  private JButton newGameButton;
  private JLabel errorMessage;
  private IViewModel gameReadOnlyModel;
  private JLabel player1Message;
  private JLabel player2Message;
  private JLabel player1ArrowCount;
  private JLabel player2ArrowCount;
  private JLabel player1Position;
  private JLabel player2Position;
  private JLabel activePlayer;
  private JButton moveButton;
  private JButton shootButton;
  private JTextField distanceField;
  private JComboBox availablePlayerMoves;
  private JComboBox availableArrowMoves;
  private JPanel currentTurnPanel;
  private JLabel currentPlayerImageLabel;

  /**
   * Constructor.
   *
   * @param caption the caption for the view
   */
  public MazeView(String caption, IViewModel gameReadOnlyModel) {
    super(caption);
    this.gameReadOnlyModel = gameReadOnlyModel;
    Set<Integer> visitedTunnels = new HashSet<>();

    setSize(900, 900);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel mainPanel = new JPanel(new BorderLayout());
    add(mainPanel);

    gamePanel = new JPanel();
    JScrollPane pane = new JScrollPane(gamePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    pane.getVerticalScrollBar().setForeground(Color.red);
    pane.getVerticalScrollBar().setBackground(Color.BLACK);
    pane.getHorizontalScrollBar().setBackground(Color.BLACK);
    mainPanel.add(pane, BorderLayout.CENTER);

    playerPanel = new JPanel();
    activityPanel = new JPanel();
    playerStatusPanel = new JPanel();
    currentTurnPanel = new JPanel(new GridLayout(5, 1, 0, 0));
    //mainPanel.add(activityPanel, BorderLayout.PAGE_END);
    //mainPanel.add(playerStatusPanel, BorderLayout.EAST);
    addPlayerStatusPanel();
    mainPanel.add(playerPanel, BorderLayout.PAGE_END);
    addCurrentTurnPanel();
    mainPanel.add(currentTurnPanel, BorderLayout.EAST);

    Container container = getContentPane();

    errorMessage = new JLabel();
    //panel.add(emptyFieldMessage);

    endGamePanel = new JPanel(new BorderLayout());
    addEndGamePanel();

    container.add(endGamePanel, BorderLayout.PAGE_END);
    container.add(errorMessage, BorderLayout.PAGE_START);

    pack();
    setVisible(true);
  }

  private void addCurrentTurnPanel() {
    currentTurnPanel.add(new JLabel("Weapon"));
    try {
      addImageLabel("arrow");
    } catch (IOException e) {
      activityPanel.add(new JLabel());
    }
    activePlayer = new JLabel();
    currentTurnPanel.add(activePlayer);
    currentTurnPanel.add(new JLabel());
    try {
      addImageLabel("player1");
    } catch (IOException e) {
      currentTurnPanel.add(new JLabel());
    }
  }

  private void addEndGamePanel() {
    exitButton = new JButton("Quit");
    exitButton.setSize(40, 25);
    exitButton.setActionCommand("Quit Game");
    exitButton.setOpaque(true);
    exitButton.setBackground(Color.RED);
    endGamePanel.add(exitButton, BorderLayout.WEST);

    restartButton = new JButton("Restart");
    restartButton.setSize(40, 25);
    restartButton.setActionCommand("Restart Game");
    restartButton.setOpaque(true);
    restartButton.setBackground(Color.BLUE);
    endGamePanel.add(restartButton, BorderLayout.CENTER);

    newGameButton = new JButton("New Game");
    newGameButton.setSize(40, 25);
    newGameButton.setActionCommand("New Game");
    newGameButton.setOpaque(true);
    newGameButton.setBackground(Color.GREEN);
    endGamePanel.add(newGameButton, BorderLayout.EAST);
  }

  @Override
  public JPanel getGamePanel() {
    return gamePanel;
  }

  private void addPlayerStatusPanel() {
    Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
    availablePlayerMoves = new JComboBox(directions);
    availablePlayerMoves.setSize(30, 20);
    availablePlayerMoves.setSelectedIndex(1);

    availableArrowMoves = new JComboBox(directions);
    availableArrowMoves.setSize(30, 20);
    availableArrowMoves.setSelectedIndex(1);

    player1ArrowCount = new JLabel();
    player2ArrowCount = new JLabel();

    moveButton = new JButton("Move");
    moveButton.setSize(20, 20);
    moveButton.setActionCommand("Move");
    moveButton.setOpaque(true);
    moveButton.setBackground(Color.BLUE);

    shootButton = new JButton("Shoot");
    shootButton.setSize(20, 20);
    shootButton.setActionCommand("Shoot");
    shootButton.setOpaque(true);
    shootButton.setBackground(Color.BLUE);

    distanceField = new JTextField();
    distanceField.setText("1");
    shootButton.setSize(12, 20);

    activityPanel.setLayout(new GridLayout(1, 2, -2, 1));
    activityPanel.setSize(150, 100);

    JPanel panelMove = new JPanel(new BorderLayout());
    panelMove.add(new JLabel("Move in direction"), BorderLayout.PAGE_START);
    panelMove.add(availablePlayerMoves, BorderLayout.CENTER);
    panelMove.add(moveButton, BorderLayout.PAGE_END);
    activityPanel.add(panelMove);
    //activityPanel.add(new JLabel("Select direction and click move"));

    JPanel panelShoot = new JPanel(new BorderLayout());
    panelShoot.add(new JLabel("Enter distance and direction to shoot"),
            BorderLayout.PAGE_START);
    panelShoot.add(distanceField, BorderLayout.CENTER);
    panelShoot.add(availableArrowMoves, BorderLayout.LINE_END);
    panelShoot.add(shootButton, BorderLayout.PAGE_END);
    activityPanel.add(panelShoot);
    //activityPanel.add(new JLabel("Select distance, direction and click shoot"));

    playerStatusPanel.setLayout(new GridLayout(6, 2, 0, 0));
    player1Message = new JLabel("You can win!");
    player1Message.setSize(30, 20);
    player1Position = new JLabel();
    playerStatusPanel.add(new JLabel(gameReadOnlyModel.getPlayerGameStatusAndInfo(1)
                                             .getName()));
    playerStatusPanel.add(player1Message);
    playerStatusPanel.add(new JLabel("POSITION: "));
    playerStatusPanel.add(player1Position);
    playerStatusPanel.add(new JLabel("WEAPONS: "));
    playerStatusPanel.add(player1ArrowCount);

    if (gameReadOnlyModel.getPlayerCount() > 1) {

      player2Message = new JLabel("Let's go champ!");
      player2Message.setSize(30, 20);
      player2Position = new JLabel();

      playerStatusPanel.add(new JLabel(gameReadOnlyModel.getPlayerGameStatusAndInfo(2)
                                               .getName()));
      playerStatusPanel.add(player2Message);
      playerStatusPanel.add(new JLabel("POSITION: "));
      playerStatusPanel.add(player2Position);
      playerStatusPanel.add(new JLabel("WEAPONS: "));
      playerStatusPanel.add(player2ArrowCount);

    }

    playerPanel.setLayout(new BorderLayout());
    playerPanel.add(activityPanel, BorderLayout.PAGE_START);
    playerPanel.add(playerStatusPanel, BorderLayout.PAGE_END);
  }

  private void addImageLabel(String imageName) throws IOException {
    BufferedImage img = ImageIO.read(
            new File(ICON_PATH + gameReadOnlyModel.getThemeName().toString().toLowerCase()
                             + "/" + imageName + ".png"));
    ImageIcon icon = new ImageIcon(img);

    if (imageName.equals("arrow")) {
      JLabel weaponImg = new JLabel();
      weaponImg.setSize(50, 50);
      weaponImg.setIcon(resizeIcon(icon, weaponImg.getWidth(),
              weaponImg.getHeight()));
      currentTurnPanel.add(weaponImg);
    } else {
      currentPlayerImageLabel = new JLabel();
      currentPlayerImageLabel.setSize(50, 50);
      currentPlayerImageLabel.setIcon(resizeIcon(icon, currentPlayerImageLabel.getWidth(),
              currentPlayerImageLabel.getHeight()));
      currentTurnPanel.add(currentPlayerImageLabel);
    }
  }

  private void updateImageLabel(String imageName) throws IOException {
    BufferedImage img = ImageIO.read(
            new File(ICON_PATH + gameReadOnlyModel.getThemeName().toString().toLowerCase()
                             + "/" + imageName + ".png"));
    ImageIcon icon = new ImageIcon(img);

    currentPlayerImageLabel.setIcon(resizeIcon(icon, currentPlayerImageLabel.getWidth(),
            currentPlayerImageLabel.getHeight()));
    currentTurnPanel.add(currentPlayerImageLabel);
  }

  private ImageIcon getIconForRoom(int roomId) {
    ImageIcon icon = null;
    try {
      BufferedImage combined = ImageIO.read(
              new File(gameReadOnlyModel.getImagePathForRoom(roomId)));

      if (gameReadOnlyModel.isVisited(roomId)) {
        if (gameReadOnlyModel.isCave(roomId)) {
          combined = addImageLayer(combined,
                  gameReadOnlyModel.getImagePathForTheme(), 15);
          int offset = 15;

          Set<SmellName> smells = gameReadOnlyModel.getSmellsOfVisitedRoom(roomId);
          for (SmellName smell : smells) {
            combined = addImageLayer(combined,
                    gameReadOnlyModel.getImagePath(smell), offset);
          }
          offset = 12;

          Map<FeatureType, Boolean> roomFeatures =
                  gameReadOnlyModel.getFeaturesOfVisitedRoom(roomId);
          for (FeatureType featureType : roomFeatures.keySet()) {
            if (roomFeatures.get(featureType)) {
              System.out.println(gameReadOnlyModel.getImagePath(featureType));
              combined = addImageLayer(combined,
                      gameReadOnlyModel.getImagePath(featureType), offset);
              offset += 10;
            }
          }

          for (int j = 1; j <= gameReadOnlyModel.getPlayerCount(); j++) {
            if (gameReadOnlyModel.playerInRoom(roomId, j)) {
              combined = addImageLayer(combined,
                      gameReadOnlyModel.getImagePathForPlayer(j), offset);
            }
          }

        }
      }
      assert combined != null;
      icon = new ImageIcon(combined.getSubimage(0, 0,
              combined.getWidth(), combined.getHeight()));
    } catch (IOException e) {
      System.out.println("Image not found, check if you have downloaded all game files");
    }
    return icon;
  }

  private BufferedImage addImageLayer(BufferedImage imagePathForRoom, String overlayImgPath,
                                      int offset) {
    BufferedImage combined = null;
    try {
      combined = overlay(imagePathForRoom, overlayImgPath, offset);
    } catch (IOException e) {
      System.out.println("Unable to fetch image file: IO Exception");
    }
    return combined;
  }

  @Override
  public void refreshErrorMessage() {
    this.errorMessage.setText(gameReadOnlyModel.getErrorMessage());
    repaint();
  }

  @Override
  public void refreshViewWithModelState() {
    for (int roomNumber : gameReadOnlyModel.getRoomNumbersToUpdate()) {
      JButton button = (JButton) getGamePanel().getComponent(roomNumber);
      button.setSize(80, 80);

      ImageIcon newIcon = getIconForRoom(roomNumber);
      assert newIcon != null;
      button.setIcon(resizeIcon(newIcon, button.getWidth(), button.getHeight()));
    }
  }

  @Override
  public void setUpGameView() {
    int countAllRooms = gameReadOnlyModel.getRowsInGrid()
                                * gameReadOnlyModel.getColumnsInGrid();
    int[] allRooms = new int[countAllRooms];
    for (int i = 0; i < countAllRooms; i++) {
      allRooms[i] = i;
    }

    GridLayout gridLayout = new GridLayout(
            gameReadOnlyModel.getRowsInGrid(),
            gameReadOnlyModel.getColumnsInGrid(),
            -7, -7);
    gamePanel.setLayout(gridLayout);

    gamePanel.setOpaque(true);
    gamePanel.setBackground(Color.BLACK);

    if (gameReadOnlyModel.getRowsInGrid() > 7 && gameReadOnlyModel.getColumnsInGrid() > 7) {
      setFullScreen();
    }

    for (int roomNumber : allRooms) {
      //System.out.println("Image for room " + roomNumber);
      //System.out.println(gameReadOnlyModel.getImagePathForRoom(roomNumber));
      JButton button = new JButton();
      button.setSize(80, 80);
      button.setActionCommand("Pressed the game panel");

      ImageIcon icon = getIconForRoom(roomNumber);
      assert icon != null;
      button.setIcon(resizeIcon(icon, button.getWidth(), button.getHeight()));

      // to remote the spacing between the image and button's borders
      button.setMargin(new Insets(-3, -3, -3, -3));
      //button.setContentAreaFilled(true);

      getGamePanel().add(button); //add the button to the grid
    }
    updatePlayerStatusPanel();
    pack();
  }

  @Override
  public void updatePlayerStatusPanel() {
    int activePlayerNumber = gameReadOnlyModel.getActivePlayerNumber();
    activePlayer.setText("Turn: Player" + activePlayerNumber);
    availablePlayerMoves.removeAllItems();
    availableArrowMoves.removeAllItems();
    for (Direction d : gameReadOnlyModel.getValidMoveForPlayer(activePlayerNumber)) {
      availablePlayerMoves.addItem(d);
      availableArrowMoves.addItem(d);
    }

    try {
      updateImageLabel("player" + activePlayerNumber);
    } catch (IOException e) {
      currentTurnPanel.add(new JLabel());
    }

    if (gameReadOnlyModel.getPlayerLastMoved() == 1) {
      CurrentPlayerInfo infoPlayer = gameReadOnlyModel.getPlayerGameStatusAndInfo(1);
      player1Position.setText("" + infoPlayer.getRoomId());
      player1ArrowCount.setText("" + infoPlayer.getArrowCount());
      player1Message.setText(gameReadOnlyModel.getLatestMessageForPlayer(1));
    } else if (gameReadOnlyModel.getPlayerLastMoved() == 2) {
      CurrentPlayerInfo infoPlayer = gameReadOnlyModel.getPlayerGameStatusAndInfo(2);
      player2Position.setText("" + infoPlayer.getRoomId());
      player2ArrowCount.setText("" + infoPlayer.getArrowCount());
      player2Message.setText(gameReadOnlyModel.getLatestMessageForPlayer(2));
    }
  }

  private BufferedImage overlay(BufferedImage starting, String fpath, int offset)
          throws IOException {
    BufferedImage overlay = ImageIO.read(new File(fpath));
    int w = starting.getWidth();
    int h = starting.getWidth();
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(starting, 0, 0, null);
    g.drawImage(overlay, offset, offset, (int) w / 2, (int) h / 2, null);
    return combined;
  }

  private void setFullScreen() {
    setExtendedState(JFrame.MAXIMIZED_BOTH);
  }

  private Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
    Image img = icon.getImage();
    Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,
            java.awt.Image.SCALE_SMOOTH);
    return new ImageIcon(resizedImage);
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void addActionListener(ActionListener listener) {
    exitButton.addActionListener(listener);
    restartButton.addActionListener(listener);
    newGameButton.addActionListener(listener);
    int countRooms = gameReadOnlyModel.getRowsInGrid() * gameReadOnlyModel.getColumnsInGrid();
    for (int roomNumber = 0; roomNumber < countRooms; roomNumber++) {
      JButton button = (JButton) getGamePanel().getComponent(roomNumber);
      button.addActionListener(listener);
    }
    moveButton.addActionListener(listener);
    shootButton.addActionListener(listener);
  }

  @Override
  public void closeView() {
    dispose();
  }

  @Override
  public JComboBox getAvailablePlayerMoves() {
    return availablePlayerMoves;
  }

  @Override
  public JComboBox getAvailableArrowMoves() {
    return availableArrowMoves;
  }

  @Override
  public JTextField getDistanceField() {
    return distanceField;
  }

  //  @Override
  //  public JLabel getErrorMessage() {
  //    return errorMessage;
  //  }

  @Override
  public void setErrorMessage(String errorMessage) {
    //
  }

  @Override
  public void checkGameFinished() {
    if (gameReadOnlyModel.isOver()) {
      applyGameOverImage(ICON_PATH + "gameOver.gif");
    } else if (gameReadOnlyModel.hasWon()) {
      System.out.println("Game is finished, one player won");
      applyGameOverImage(ICON_PATH + "winnerIs.gif");
    }
  }

  private void applyGameOverImage(String imageFile) {
    gamePanel.removeAll();
    JLabel gameOverImage = new JLabel("GameOver");
    gameOverImage.setSize(gamePanel.getWidth(), gamePanel.getHeight());
    gameOverImage.setIcon(new ImageIcon(imageFile));
    gamePanel.add(gameOverImage);
    repaint();
    pack();
  }
}
