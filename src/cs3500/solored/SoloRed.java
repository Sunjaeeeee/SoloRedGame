package cs3500.solored;

import java.io.InputStreamReader;

import cs3500.solored.controller.RedGameController;
import cs3500.solored.controller.SoloRedTextController;
import cs3500.solored.model.RedGameCard;
import cs3500.solored.model.RedGameModel;
import cs3500.solored.model.RedGameCreator;

import static cs3500.solored.model.RedGameCreator.createGame;

/**
 * The {@code SoloRed} class is the entry point for the Solo Red card game application.
 * It initializes the game based on command-line arguments and starts the game loop.
 *
 * <p>The game can be configured to use either a basic or advanced game type, and
 * allows the user to specify the number of palettes and hand size through command-line
 * arguments.</p>
 *
 * <p>Usage:
 * <pre>
 * java SoloRed &lt;gameType&gt; &lt;numPalettes&gt; &lt;handSize&gt;
 * </pre>
 * Where:
 * <ul>
 * <li>{@code gameType} can be either "basic" or "advanced".</li>
 * <li>{@code numPalettes} is an integer that specifies the number of palettes.</li>
 * <li>{@code handSize} is an integer that specifies the size of the hand.</li>
 * </ul>
 * </p>
 *
 * <p>Defaults are set for {@code numPalettes} (4) and {@code handSize} (7) if not provided
 * or if invalid values are given.</p>
 */
public final class SoloRed {
  /**
   * The main method that starts the Solo Red card game.
   *
   * <p>This method processes command-line arguments to configure the game type,
   * number of palettes, and hand size. It creates an instance of the game model
   * and controller, then starts the game.</p>
   *
   * @param args command-line arguments for configuring the game
   *             <ul>
   *             <li>args[0]: game type ("basic" or "advanced")</li>
   *             <li>args[1]: number of palettes (integer, at least 2)</li>
   *             <li>args[2]: hand size (integer, greater than 0)</li>
   *             </ul>
   */
  public static void main(String[] args) {
    int numPalettes = 4;
    int handSize = 7;

    RedGameCreator.GameType gameType = null;
    try {
      if (args[0].equalsIgnoreCase("basic")) {
        gameType = RedGameCreator.GameType.BASIC;
      } else if (args[0].equalsIgnoreCase("advanced")) {
        gameType = RedGameCreator.GameType.ADVANCED;
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println(e.getMessage());
    }

    try {
      if (Integer.parseInt(args[1]) >= 2) {
        numPalettes = Integer.parseInt(args[1]);
      }
      if (Integer.parseInt(args[2]) > 0) {
        handSize = Integer.parseInt(args[2]);
      }
    } catch (NumberFormatException | ArrayIndexOutOfBoundsException ignored) {
      //Ignore NumberFormatException and ArrayIndexOutOfBoundsException
    }

    RedGameModel<RedGameCard> model = createGame(gameType);
    RedGameController controller = new SoloRedTextController(
            new InputStreamReader(System.in), System.out);
    try {
      controller.playGame(model, model.getAllCards(), true, numPalettes, handSize);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

}