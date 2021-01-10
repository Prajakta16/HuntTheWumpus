package maze.controller;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import maze.model.Direction;
import maze.model.IViewModel;
import maze.model.InterfaceGame;
import maze.model.Room;


/**
 * A controller for our game play. This receives all the inputs from the user from a Readable ojbect
 * and transmits all outputs to an Appendable object. The Appendable object would be provided by a
 * view (not shown here). This design allows us to test.
 */
public class TextBasedGameController {
  private final Readable in;
  private final Appendable out;

  /**
   * Constructor for the controller.
   *
   * @param in  input
   * @param out output
   */
  public TextBasedGameController(Readable in, Appendable out) {
    this.in = in;
    this.out = out;
  }

  /**
   * Method that gives control to the controller.
   *
   * @param game the model to use.
   * @throws java.io.IOException if something goes wrong appending to out
   */
  public void start(InterfaceGame game) throws IOException {
    Random random = new Random();
    Objects.requireNonNull(game);
    IViewModel viewModel = (IViewModel) game;

    Scanner scanner = new Scanner(this.in);

    while (true) {
      this.out.append("Current turn: PLAYER")
              .append(String.valueOf(game.getActivePlayerNumber())).append(" ");
      this.out.append(game.printPlayerStatus(game.getActivePlayerNumber()).toString());
      this.out.append("Enter move, shoot or q/quit");

      String command = scanner.next();
      switch (command) {
        case "move":
          this.out.append("Available next steps - ");
          this.out.append(printNextSteps(game).toString());
          this.out.append("Enter next direction for player");
          Direction direction = scanDirection(game, scanner);
          game.movePlayer(direction, random);
          this.out.append(viewModel.getLatestMessageForPlayer(game.getActivePlayerNumber()));
          if (game.isOver()) {
            this.out.append("Game is over!!!");
            return;
          }
          break;

        case "shoot":
          this.out.append("Enter the direction of the arrow");
          this.out.append(printNextSteps(game).toString());
          Direction arrowDirection = scanDirection(game, scanner);
          assert arrowDirection != null;
          this.out.append("Enter distance to be travelled");
          int distance = scanInteger(scanner);
          game.useArrow(arrowDirection, distance);
          this.out.append(viewModel.getLatestMessageForPlayer(game.getActivePlayerNumber()));
          if (game.hasWon()) {
            this.out.append("Game is won, come back next time to play more games!!!");
            return;
          }
          if (game.isOver()) {
            this.out.append("Game is over!!!");
            return;
          }
          break;

        case "q":
        case "quit":
          return;

        default:
          this.out.append("Please enter correct input");
      }
    }
  }

  private int scanInteger(Scanner scanner) throws IOException {
    boolean foundCorrectNumber = false;
    int n = 0;
    String inputNumber;

    while (!foundCorrectNumber) {
      inputNumber = scanner.next();

      try {
        n = Integer.parseInt(inputNumber);
        if (n < 0) {
          this.out.append("Incorrect direction specified, please enter correct one");
        } else {
          foundCorrectNumber = true;
        }
      } catch (IllegalArgumentException | IOException e) {
        this.out.append("Incorrect mumber specified, please enter correct one");
      }
    }
    return n;
  }

  private Direction scanDirection(InterfaceGame game, Scanner scanner) throws IOException {
    boolean foundCorrectDirn = false;
    Direction direction = null;
    String inputDirection;

    while (!foundCorrectDirn) {
      inputDirection = scanner.next();

      try {
        direction = Direction.valueOf(inputDirection.toUpperCase());
        if (!game.printAvailableDirections().containsValue(direction)) {
          this.out.append("Incorrect direction specified, please enter correct one");
        } else {
          foundCorrectDirn = true;
        }
      } catch (IllegalArgumentException | IOException e) {
        this.out.append("Incorrect direction specified, please enter correct one");
      }
    }
    return direction;
  }

  private static StringBuilder printNextSteps(InterfaceGame game) {
    StringBuilder stringBuilder = new StringBuilder();
    for (Room room : game.printAvailableDirections().keySet()) {
      //stringBuilder.append(room.getRoomId()).append(": ");
      stringBuilder.append(game.printAvailableDirections().get(room)).append(" ");
    }
    return stringBuilder;
  }
}

