package maze.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import maze.model.FeatureType;
import maze.model.GameInput;
import maze.model.IGameInput;
import maze.model.IThemeFeatures;
import maze.model.ThemeForest;
import maze.model.ThemeMarine;
import maze.model.ThemeName;
import maze.model.ThemeSpace;
import maze.model.ThemeWumpus;

/**
 * Class for the input view. This takes input from the user for game creation.
 */
public class GameInputView extends JFrame implements IView {
  private ThemeName themeName;
  private IThemeFeatures theme;
  private JPanel panel;

  private JTextField numRowsField;

  private JTextField numColsField;
  private JTextField percentBatsField;
  private JTextField percentPitsField;
  private JTextField numRemainingWallsField;
  private JLabel emptyFieldMessage;
  private JTextField player1Name;
  private JTextField player2Name;

  private JComboBox player1ArrowCount;
  private JComboBox player2ArrowCount;

  private ButtonGroup mazeTypeButtonGroup;
  private JRadioButton perfectRadio;
  private JRadioButton imperfectRadio;

  private ButtonGroup wrappingTypeButtonGroup;

  private ButtonGroup playerCountButtonGroup;
  private JRadioButton count1Radio;
  private JRadioButton count2Radio;

  private JButton submitButton;

  /**
   * Constructor.
   *
   * @param caption the caption for the view
   */
  public GameInputView(String caption, ThemeName themeName) {
    super(caption);
    this.themeName = themeName;

    setSize(700, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    panel = new JPanel();

    add(panel);

    Container container = getContentPane();

    emptyFieldMessage = new JLabel();
    //panel.add(emptyFieldMessage);

    submitButton = new JButton("Start Game!!!");
    submitButton.setBounds(10, 80, 150, 25);
    submitButton.setActionCommand("Start Game");
    container.add(submitButton, BorderLayout.PAGE_END);
    container.add(emptyFieldMessage, BorderLayout.PAGE_START);

    perfectRadio = new JRadioButton("Perfect", true);
    imperfectRadio = new JRadioButton("Imperfect", false);
    count1Radio = new JRadioButton("1", true);
    count2Radio = new JRadioButton("2", false);

    setVisible(true);
  }

  @Override
  public void setModeTo2Player() {
    count1Radio.setSelected(false);
    count2Radio.setSelected(true);
  }

  @Override
  public void addActionListener(ActionListener actionListener) {
    submitButton.addActionListener(actionListener);
    imperfectRadio.addActionListener(actionListener);
    perfectRadio.addActionListener(actionListener);
    count1Radio.addActionListener(actionListener);
    count2Radio.addActionListener(actionListener);
  }


  @Override
  public void addInputForBats() {
    panel.add(new JLabel("Enter % " + theme.getFeatureMappingForTheme()
                                              .get(FeatureType.BAT)));
    percentBatsField = new JTextField();
    percentBatsField.setText("10");
    panel.add(percentBatsField);
    JLabel percentBatsMessage = new JLabel("% of " + theme.getFeatureMappingForTheme()
                                .get(FeatureType.BAT) + " should be 1 - 100");
    panel.add(percentBatsMessage);
  }

  @Override
  public void addInputForPits() {
    panel.add(new JLabel("Enter % " + theme.getFeatureMappingForTheme()
                                              .get(FeatureType.PIT)));
    percentPitsField = new JTextField();
    percentPitsField.setText("10");
    panel.add(percentPitsField);
    JLabel percentPitsMessage = new JLabel("% of " + theme.getFeatureMappingForTheme()
                                .get(FeatureType.PIT) + " should be 1 - 100");
    panel.add(percentPitsMessage);
  }

  @Override
  public void closeView() {
    dispose();
  }

  @Override
  public JPanel getInputPanel() {
    return panel;
  }

  @Override
  public void addInputForWumpus() {
    panel.add(new JLabel("Enter % " + theme.getFeatureMappingForTheme()
                                              .get(FeatureType.WUMPUS)));
    JTextField numWumpusField = new JTextField("1");
    numWumpusField.setEnabled(false);
    panel.add(numWumpusField);
    panel.add(new JLabel("Only 1 enemy supported"));
  }

  @Override
  public void addInputForGold() {
    panel.add(new JLabel("Enter % gold"));
    JTextField percentGoldField = new JTextField();
    panel.add(percentGoldField);
    JLabel percentGoldMessage = new JLabel();
    panel.add(percentGoldMessage);
  }

  @Override
  public void addInputForThieves() {
    panel.add(new JLabel("Enter % thieves"));
    JTextField percentThievesField = new JTextField();
    panel.add(percentThievesField);
    JLabel percentThievesMessage = new JLabel();
    panel.add(percentThievesMessage);
  }

  @Override
  public IGameInput getGameInput() {
    boolean wrappingFlag = false;
    if (wrappingTypeButtonGroup.getSelection().getActionCommand().equals("wrapping")) {
      wrappingFlag = true;
    }
    boolean perfectFlag = false;
    if (mazeTypeButtonGroup.getSelection().getActionCommand().equals("perfect")) {
      perfectFlag = true;
    }

    int playerCount = Integer.parseInt(playerCountButtonGroup.getSelection().getActionCommand());
    if (playerCount == 1) {
      player2Name = new JTextField("dummy");
    }
    //creation of gameInput object!
    IGameInput gameInput = new GameInput(themeName, wrappingFlag, perfectFlag,
            Integer.parseInt(numRowsField.getText()), Integer.parseInt(numColsField.getText()),
            playerCount,
            player1Name.getText(), player2Name.getText(),
            (int) player1ArrowCount.getSelectedItem(),
            (int) player2ArrowCount.getSelectedItem());

    if (!perfectFlag) {
      gameInput.setRemainingWalls(Integer.parseInt(numRemainingWallsField.getText()));
    }

    Map<FeatureType, FeatureType> themeFeatures;
    IThemeFeatures theme = null;
    if (themeName.equals(ThemeName.HUNT_THE_WUMPUS)) {
      theme = new ThemeWumpus();
    } else if (themeName.equals(ThemeName.CATCH_THE_SHARK)) {
      theme = new ThemeMarine();
    } else if (themeName.equals(ThemeName.DESTROY_ASTEROID)) {
      theme = new ThemeSpace();
    } else if (themeName.equals(ThemeName.SHOOT_THE_LION)) {
      theme = new ThemeForest();
    }
    //    else if (themeName.equals(ThemeName.GET_GOLD_REACH_GOAL)) {
    //      theme = new Game();
    //    }
    assert theme != null;
    themeFeatures = theme.getFeatureMappingForTheme();

    for (FeatureType featureType : themeFeatures.keySet()) {
      switch (featureType.toString()) {
        case "BAT":
          gameInput.setPercentBats(Integer.parseInt(percentBatsField.getText()));
          break;
        case "PIT":
          gameInput.setPercentPits(Integer.parseInt(percentPitsField.getText()));
          break;
        case "WUMPUS":
          break;
        default:
        //        case "GOLD":
        //          gameInput.setPercentGold(Integer.parseInt(percentGoldField.getText()));
        //          break;
        //        case "THIEF":
        //          gameInput.setPercentThieves(Integer.parseInt(percentThievesField.getText()));
        //          break;
      }
    }
    return gameInput;
  }

  @Override
  public void addInputForMazeWrappingType() {
    panel.add(new JLabel("Choose wrapping type"));
    JPanel radioPanel = new JPanel();
    radioPanel.setLayout(new BorderLayout());
    wrappingTypeButtonGroup = new ButtonGroup();

    JRadioButton wrappingRadio;
    radioPanel.add(wrappingRadio = new JRadioButton("Wrapping", true), BorderLayout.WEST);
    wrappingRadio.setActionCommand("wrapping");
    wrappingTypeButtonGroup.add(wrappingRadio);

    JRadioButton nonWrappingRadio;
    radioPanel.add(nonWrappingRadio = new JRadioButton("Non-Wrapping", false), BorderLayout.EAST);
    nonWrappingRadio.setActionCommand("non-wrapping");
    wrappingTypeButtonGroup.add(nonWrappingRadio);

    panel.add(radioPanel);
    panel.add(new JLabel());
  }

  @Override
  public void addPlayerInfoPanel(int countPlayers) {
    if (countPlayers == 2) {
      if (player2Name != null) {
        player2Name.setEnabled(true);
        player2ArrowCount.setEnabled(true);
      }
    } else {
      if (player2Name != null) {
        player2Name.setEnabled(false);
        player2ArrowCount.setEnabled(false);
      }
    }
    repaint();
  }

  @Override
  public void addInputFoNumPlayers() {
    panel.add(new JLabel("Choose number of players"));
    JPanel radioPanel = new JPanel();
    radioPanel.setLayout(new BorderLayout());
    playerCountButtonGroup = new ButtonGroup();

    radioPanel.add(count1Radio, BorderLayout.WEST);
    count1Radio.setActionCommand("1");
    playerCountButtonGroup.add(count1Radio);

    radioPanel.add(count2Radio, BorderLayout.EAST);
    count2Radio.setActionCommand("2");
    playerCountButtonGroup.add(count2Radio);

    panel.add(radioPanel);
    panel.add(new JLabel());

    panel.add(new JLabel("Enter custom player1 name:"));
    player1Name = new JTextField();
    player1Name.setText("PLAYER 1");
    panel.add(player1Name);
    panel.add(new JLabel(""));

    panel.add(new JLabel("Choose " + theme.getFeatureMappingForTheme().get(FeatureType.ARROW)
                                 + " for Player 1:"));
    player1ArrowCount = new JComboBox();
    for (int i = 1; i < 11; i++) {
      player1ArrowCount.addItem(i);
    }
    player1ArrowCount.setSelectedIndex(0);
    panel.add(player1ArrowCount);
    panel.add(new JLabel(""));

    panel.add(new JLabel("Enter custom player2 name:"));
    player2Name = new JTextField();
    player2Name.setText("PLAYER 2");
    player2Name.setEnabled(false);
    panel.add(player2Name);
    panel.add(new JLabel(""));
    panel.add(new JLabel("Choose " + theme.getFeatureMappingForTheme().get(FeatureType.ARROW)
                                 + " for Player 2:"));
    player2ArrowCount = new JComboBox();
    for (int i = 1; i < 11; i++) {
      player2ArrowCount.addItem(i);
    }
    player2ArrowCount.setSelectedIndex(0);
    panel.add(player2ArrowCount);
    panel.add(new JLabel(""));
    player2ArrowCount.setEnabled(false);
  }

  @Override
  public void addInputForMazePerfectType() {
    panel.add(new JLabel("Choose maze type"));
    JPanel radioPanel = new JPanel();
    radioPanel.setLayout(new BorderLayout());
    mazeTypeButtonGroup = new ButtonGroup();

    radioPanel.add(perfectRadio, BorderLayout.WEST);
    perfectRadio.setActionCommand("perfect");
    mazeTypeButtonGroup.add(perfectRadio);

    radioPanel.add(imperfectRadio, BorderLayout.EAST);
    imperfectRadio.setActionCommand("imperfect");
    mazeTypeButtonGroup.add(imperfectRadio);

    panel.add(radioPanel);
    panel.add(new JLabel());
  }

  /**
   * Toggles display of remaining walls field.
   */
  @Override
  public void toggleDisableInputForRemainingWalls() {
    if (numRemainingWallsField.isEnabled()) {
      numRemainingWallsField.setEnabled(false);
    } else {
      numRemainingWallsField.setEnabled(true);
    }
    repaint();
  }

  @Override
  public void addInputForRemainingWalls() {
    panel.add(new JLabel("Enter remaining walls"));
    numRemainingWallsField = new JTextField();

    panel.add(numRemainingWallsField);
    JLabel numWallsMessage = new JLabel("Only applicable for imperfect");
    panel.add(numWallsMessage);

    numRemainingWallsField.setEnabled(false);
  }

  @Override
  public void addInputForRows() {
    panel.add(new JLabel("Enter rows"));
    numRowsField = new JTextField();
    numRowsField.setText("4");
    panel.add(numRowsField);
    JLabel numRowsMessage = new JLabel();
    panel.add(numRowsMessage);
  }

  @Override
  public void addInputForColumns() {
    panel.add(new JLabel("Enter columns"));
    numColsField = new JTextField();
    numColsField.setText("5");
    panel.add(numColsField);
    JLabel numColsMessage = new JLabel();
    panel.add(numColsMessage);
  }


  @Override
  public JLabel getErrorMessageLabel() {
    return emptyFieldMessage;
  }

  @Override
  public void setErrorMessage(JLabel label, String errorMessage) {
    label.setText(errorMessage);
  }

  @Override
  public void setThemeName(IThemeFeatures theme) {
    this.theme = theme;
  }
}
