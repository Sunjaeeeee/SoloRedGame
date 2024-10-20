package solored.controller;

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
public class SoloRedTextController2 implements RedGameController {
  private final Readable rd;
  private final Appendable ap;

  /**
   * Constructs a {@code SoloRedTextController} with the specified input and output streams.
   *
   * @param rd the {@link Readable} source for user input; must not be null
   * @param ap the {@link Appendable} target for output; must not be null
   * @throws IllegalArgumentException if either the {@code rd} or {@code ap} is null
   */
  public SoloRedTextController2(Readable rd, Appendable ap) throws IllegalArgumentException {
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

    while (!model.isGameOver() && !quit) {
      transmitGameState(model, view);
      String input = validatedNext(scanner, model, view);
      switch (input) {
        case "q":
        case "Q":
          quit = true;
          handleQuit(model, view);
          break;
        case "palette":
          handlePaletteInput(scanner, model, view);
          break;
        case "canvas":
          handleCanvasInput(scanner, model, view);
          break;
        default:
          transmitInvalidCommand("Not q, palette, or canvas");
          break;
      }
    }
    if (model.isGameOver()) {
      handleGameOver(model, view);
    }
  }

  private void transmitGameState(RedGameModel<?> model, RedGameView view) {
    try {
      // Transmit game state
      view.render();
      ap.append("\n");
      // Transmit "Number of cards in deck: N"
      ap.append("Number of cards in deck: " + model.numOfCardsInDeck() + "\n");
    } catch (Exception e) {
      throw new IllegalStateException("Unable to transmit game state.");
    }
  }

  private int nextValidInput(Scanner scanner, RedGameModel<?> model, RedGameView view) {
    while (true) {
      String input = validatedNext(scanner, model, view);
      if (input.equalsIgnoreCase("q")) {
        quit = true;
        handleQuit(model, view);
      }
      try {
        int number = Integer.parseInt(input);
        if (number >= 0) {
          return number; // Return the valid natural number
        }
      } catch (NumberFormatException ignored) {
        //Ignore NumberFormatException
      }
    }
  }

  private void handlePaletteInput(Scanner scanner, RedGameModel<?> model, RedGameView view) {
    boolean draw = true;
    if (!quit) {
      try {
        model.playToPalette(nextValidInput(scanner, model, view) - 1,
                nextValidInput(scanner, model, view) - 1);
      } catch (IllegalArgumentException | IllegalStateException e) {
        draw = false;
        if (!quit) {
          transmitInvalidMove(e.getMessage());
        }
      }
      if (draw) {
        if (!model.isGameOver()) {
          model.drawForHand();
        }
      }
    } else if (model.isGameOver()) {
      handleGameOver(model, view);
    }
  }

  private void handleCanvasInput(Scanner scanner, RedGameModel<?> model, RedGameView view) {
    if (!quit) {
      try {
        model.playToCanvas(nextValidInput(scanner, model, view) - 1);
        if (model.isGameOver()) {
          handleGameOver(model, view);
        }
      } catch (IllegalArgumentException | IllegalStateException e) {
        if (!quit) {
          transmitInvalidMove(e.getMessage());
        }
      }
    }
  }

  private String validatedNext(Scanner scanner, RedGameModel<?> model, RedGameView view) {
    if (scanner.hasNext()) {
      return scanner.next();
    }
    if (!model.isGameOver()) {
      throw new IllegalStateException("No new input.");
    } else {
      handleGameOver(model, view);
    }
    return null;
  }

  private void handleQuit(RedGameModel<?> model, RedGameView view) {
    quit = true;
    if (model.isGameOver()) {
      handleGameOver(model, view);
    } else {
      try {
        ap.append("Game quit!\nState of game when quit:\n");
        transmitGameState(model, view);
      } catch (Exception e) {
        throw new IllegalStateException("Unable to transmit quit state.");
      }
    }
  }

  private void transmitInvalidCommand(String message) {
    try {
      ap.append("Invalid command. Try again. ").append(message).append("\n");
    } catch (Exception e) {
      throw new IllegalStateException("Unable to transmit invalid command message.");
    }
  }

  private void transmitInvalidMove(String message) {
    try {
      ap.append("Invalid move. Try again. ").append(message).append("\n");
    } catch (Exception e) {
      throw new IllegalStateException("Unable to transmit invalid command message.");
    }
  }

  private void handleGameOver(RedGameModel<?> model, RedGameView view) {
    if (!model.isGameOver()) {
      throw new IllegalStateException("Game over.");
    }
    try {
      if (model.isGameWon()) {
        ap.append("Game won.\n");
      } else {
        ap.append("Game lost.\n");
      }
      transmitGameState(model, view);
    } catch (Exception e) {
      throw new IllegalStateException("Unable to transmit game over state.");
    }
  }
}
