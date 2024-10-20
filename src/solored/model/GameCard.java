package solored.model;

/**
 * Represents a game card in the SoloRed game model.
 */
public interface GameCard extends Card {
  /**
   * Gets the color of the card.
   *
   * @return color value of card in char
   */
  public Color getColor();

  /**
   * Gets the number value of the card.
   *
   * @return number value of card in int
   */
  public Number getNumber();
}
