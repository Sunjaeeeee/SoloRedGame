package solored.view;

import java.io.IOException;
import java.util.List;

import solored.model.RedGameCard;
import solored.model.RedGameModel;

/**
 * A text based view for the Solo Red Game. This view displays the game state
 * including the canvas, palettes, and hand of cards in a human-readable format.
 */
public class SoloRedGameTextView implements RedGameView {
  private final RedGameModel<?> model;
  private Appendable appendable;

  /**
   * Constructs a {@code SoloRedGameTextView} with the specified model.
   *
   * @param model the {@link RedGameModel} to be used by this view; must not be null
   * @throws IllegalArgumentException if the {@code model} is null
   */
  public SoloRedGameTextView(RedGameModel<?> model) {
    this.model = model;
  }

  /**
   * Constructs a {@code SoloRedGameTextView} with the specified model and appendable.
   *
   * @param model the {@link RedGameModel} to be used by this view; must not be null
   * @param appendable the {@link Appendable} to which the view will write output; must not be null
   * @throws IllegalArgumentException if either the {@code model} or {@code appendable} is null
   */
  public SoloRedGameTextView(RedGameModel<?> model, Appendable appendable) {
    if (model == null || appendable == null) {
      throw new IllegalArgumentException("Readable and Appendable cannot be null.");
    }
    this.model = model;
    this.appendable = appendable;
  }

  @Override
  public void render() {
    try {
      appendable.append(toString());
    } catch (IOException e) {
      throw new RuntimeException("Failed to append to the output", e);
    }
  }

  private String cardListToString(List<RedGameCard> cards) {
    StringBuilder sb = new StringBuilder();
    for (RedGameCard card : cards) {
      sb.append(card.toString() + " ");
    }
    if (cards.size() > 0) {
      sb.deleteCharAt(sb.length() - 1);
    }
    return sb.toString();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Canvas: " + model.getCanvas().toString().charAt(0));
    for (int i = 0; i < model.numPalettes(); i++) {
      sb.append("\n");
      if (i == model.winningPaletteIndex()) {
        sb.append("> ");
      }
      sb.append("P" + (i + 1) + ": " + cardListToString((List<RedGameCard>) model.getPalette(i)));
    }
    sb.append("\nHand: " + cardListToString((List<RedGameCard>) model.getHand()));
    return sb.toString();
  }
}