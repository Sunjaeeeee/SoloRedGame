package cs3500.solored.model;

import java.util.List;
import java.util.Random;

import cs3500.solored.model.RedGameCard;
import cs3500.solored.model.RedGameModel;
import cs3500.solored.model.SoloRedGameModel;

/**
 * Represents an advanced version of the Solo Red Game Model which extends the
 * functionalities of the SoloRedGameModel. This model incorporates additional
 * game logic for drawing cards based on the current game state.
 */
public class AdvancedSoloRedGameModel extends SoloRedGameModel
        implements RedGameModel<RedGameCard> {
  private int drawNum;

  /**
   * Initializes a new AdvancedSoloRedGameModel.
   */
  public AdvancedSoloRedGameModel() {
    super();
  }

  /**
   * Initializes a new AdvancedSoloRedGameModel with a specified random number generator.
   *
   * @param  r the random number generator to use for shuffling the deck.
   */
  public AdvancedSoloRedGameModel(Random r) {
    super(r);
  }

  @Override
  public void playToCanvas(int cardIdxInHand) {
    super.playToCanvas(cardIdxInHand);
    if (getCanvas().getNumber().getInt() > getPalette(winningPaletteIndex()).size()) {
      drawNum = 2;
    }
  }

  @Override
  public void drawForHand() {
    // Validate game state
    if (!gameStarted || gameOver) {
      throw new IllegalStateException("Game has not started or is already over.");
    }
    drawNum = Math.min(Math.min(handSize - hand.size(), deck.size()), drawNum);
    for (int i = 0; i < drawNum; i++) {
      hand.add(deck.get(0));
      deck.remove(0);
    }
    canvasPlayAllowed = true;
    drawNum = 1;
  }

  @Override
  public void startGame(List<RedGameCard> deck, boolean shuffle, int numPalettes, int handSize) {
    drawNum = handSize;
    super.startGame(deck, shuffle, numPalettes, handSize);
    drawNum = 1;
  }
}
