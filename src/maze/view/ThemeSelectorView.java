package maze.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import maze.model.IThemeFeatures;
import maze.model.ThemeForest;
import maze.model.ThemeMarine;
import maze.model.ThemeSpace;
import maze.model.ThemeWumpus;

/**
 * Class for theme selector view.
 */
public class ThemeSelectorView extends JFrame implements IThemeSelectorView {
  private static String ICON_PATH = "res/icons/";
  private JRadioButton goldRadio;
  public ButtonGroup gameThemeButtonGroup;
  private JButton nextButton;

  /**
   * Constructor for view.
   * @param caption caption for view
   * @throws IOException when file is not found
   */
  public ThemeSelectorView(String caption) throws IOException {
    super("Choose game theme");

    setSize(600, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel radioPanel = new JPanel();
    radioPanel.setLayout(new GridLayout(4, 2, 10, 10));
    mainPanel.add(radioPanel);
    gameThemeButtonGroup = new ButtonGroup();

    ArrayList<IThemeFeatures> allThemes = new ArrayList<>();
    allThemes.add(new ThemeWumpus());
    allThemes.add(new ThemeMarine());
    allThemes.add(new ThemeSpace());
    allThemes.add(new ThemeForest());
    for (IThemeFeatures theme : allThemes) {
      JRadioButton themeRadio = new JRadioButton(theme.getBigName(), false);
      themeRadio.setActionCommand(theme.getThemeName().toString());
      if (theme.getThemeName().toString().equals("HUNT_THE_WUMPUS")) {
        themeRadio.setSelected(true);
      }

      radioPanel.add(themeRadio);
      gameThemeButtonGroup.add(themeRadio);

      BufferedImage img = ImageIO.read(
              new File(ICON_PATH + theme.getThemeName().toString().toLowerCase()
                               + "/wumpus.png"));
      ImageIcon icon = new ImageIcon(img);

      JLabel lbl = new JLabel();
      lbl.setSize(100, 100);
      //lbl.setIcon(new ImageIcon((img)));
      lbl.setIcon(resizeIcon(icon, lbl.getWidth(), lbl.getHeight()));
      radioPanel.add(lbl);
    }
    nextButton = new JButton("Next");
    nextButton.setActionCommand("Confirm theme");
    mainPanel.add(nextButton, BorderLayout.PAGE_END);
    add(mainPanel);

    setVisible(true);
    pack();
  }

  private Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
    Image img = icon.getImage();
    Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,
            java.awt.Image.SCALE_SMOOTH);
    return new ImageIcon(resizedImage);
  }

  @Override
  public void closeView() {
    dispose();
  }

  @Override
  public ButtonGroup getGameThemeButtonGroup() {
    return gameThemeButtonGroup;
  }

  @Override
  public void addActionListener(ActionListener actionListener) {
    nextButton.addActionListener(actionListener);
  }

  /**
   * Returns the next button.
   * @return the next button
   */
  public JButton getNextButton() {
    return nextButton;
  }

}
