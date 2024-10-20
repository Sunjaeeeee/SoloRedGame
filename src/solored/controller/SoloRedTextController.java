package solored.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import solored.model.Card;
import solored.model.RedGameModel;
import solored.view.RedGameView;
import solored.view.SoloRedGameTextView;

/**
 * The {@code SoloRedTextController} class implements the {@link RedGameController} interface,
 * providing a text-based controller for the Solo Red game. This controller handles user input,
 * game state updates, and communication with the game model and view.
 */
public class SoloRedTextController implements RedGameController {
  private final Readable rd;
  private final Appendable ap;

  /**
   * Constructs a {@code SoloRedTextController} with the specified input and output streams.
   *
   * @param rd the {@link Readable} source for user input; must not be null
   * @param ap the {@link Appendable} target for output; must not be null
   * @throws IllegalArgumentException if either the {@code rd} or {@code ap} is null
   */
  public SoloRedTextController(Readable rd, Appendable ap) throws IllegalArgumentException {
    // Check if either parameter is null
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Readable and Appendable cannot be null.");
    }

    // Initialize the Readable and Appendable fields
    this.rd = rd;
    this.ap = ap;
  }

  boolean quit = false;

  @Override
  public <C extends Card> void playGame(
          RedGameModel<C> model, List<C> deck, boolean shuffle, int numPalettes, int handSize) {
    if (model == null || deck == null) {
      throw new IllegalArgumentException("Model or Deck cannot be null.");
    }

    try {
      model.startGame(deck, shuffle, numPalettes, handSize);
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }

    RedGameView view = new SoloRedGameTextView(model, ap);
    Scanner scanner = new Scanner(rd);


    mainLoop(model, view, scanner);

    if (model.isGameOver()) {
      handleGameOver(model, view);
    } else {
      handleQuit(model, view);
    }
  }

  private void mainLoop(RedGameModel<?> model, RedGameView view, Scanner scanner) {
    while (!model.isGameOver()) {
      transmitGameState(model, view);
      String input = validatedNext(scanner, model, view);
      switch (input) {
        case "q":
        case "Q":
          return;
        case "palette":
          if (handlePaletteInput(scanner, model, view)) {
            return;
          }
          break;
        case "canvas":
          if (handleCanvasInput(scanner, model, view)) {
            return;
          }
          break;
        default:
          transmitInvalidCommand("Not q, palette, or canvas");
          break;
      }
    }
  }

  private void appendln(String message) {
    try {
      ap.append(message).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Unable to transmit.");
    }
  }

  private void transmitGameState(RedGameModel<?> model, RedGameView view) {
    try {
      // Transmit game state
      view.render();
      ap.append("\n");
      // Transmit "Number of cards in deck: N"
      appendln("Number of cards in deck: " + model.numOfCardsInDeck());
    } catch (IOException e) {
      throw new IllegalStateException("Unable to transmit game state.");
    }
  }

  private int nextValidInput(Scanner scanner, RedGameModel<?> model, RedGameView view) {
    while (true) {
      String input = validatedNext(scanner, model, view);
      if (input.equalsIgnoreCase("q")) {
        return -1;
      }
      try {
        int number = Integer.parseInt(input);
        if (number >= 0) {
          return number; // Return the valid natural number
        }
      } catch (NumberFormatException ignored) {
        //Ignore NumberFormatException as if it gets triggered input was not an int.
      }
    }
  }

  private boolean handlePaletteInput(Scanner scanner, RedGameModel<?> model, RedGameView view) {
    int paletteIdx = nextValidInput(scanner, model, view);
    if (paletteIdx == -1) {
      return true;
    }

    int cardIdxInHand = nextValidInput(scanner, model, view);
    if (cardIdxInHand == -1) {
      return true;
    }

    try {
      model.playToPalette(paletteIdx - 1, cardIdxInHand - 1);
      if (!model.isGameOver()) {
        model.drawForHand();
      }
    } catch (IllegalArgumentException | IllegalStateException e) {
      transmitInvalidMove(e.getMessage());
    }
    return false;
  }

  private boolean handleCanvasInput(Scanner scanner, RedGameModel<?> model, RedGameView view) {
    int cardIdxInHand = nextValidInput(scanner, model, view);
    if (cardIdxInHand == -1) {
      return true;
    }
    try {
      model.playToCanvas(cardIdxInHand - 1);
    } catch (IllegalArgumentException | IllegalStateException e) {
      transmitInvalidMove(e.getMessage());
    }
    return false;
  }

  private String validatedNext(Scanner scanner, RedGameModel<?> model, RedGameView view) {
    if (scanner.hasNext()) {
      return scanner.next();
    }
    if (!model.isGameOver()) {
      throw new IllegalStateException("No new input.");
    }
    return null;
  }

  private void handleQuit(RedGameModel<?> model, RedGameView view) {
    appendln("Game quit!");
    appendln("State of game when quit:");
    transmitGameState(model, view);
  }

  private void transmitInvalidCommand(String message) {
    appendln("Invalid command. Try again. " + message);
  }

  private void transmitInvalidMove(String message) {
    appendln("Invalid move. Try again. " + message);
  }

  private void handleGameOver(RedGameModel<?> model, RedGameView view) {
    if (!model.isGameOver()) {
      throw new IllegalStateException("Game is not over. IDK how you managed to run this");
    }
    if (model.isGameWon()) {
      appendln("Game won.");
    } else {
      appendln("Game lost.");
    }
    transmitGameState(model, view);
  }
}
