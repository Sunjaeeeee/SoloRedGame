package solored.model;

/**
 * Represents a card in the RedSoloGame.
 * Each card has a color and a number. The color is represented by
 * an instance of the {@link Color} class, while the number is represented
 * by an instance of the {@link Number} class. This class implements the
 * {@link GameCard} interface.
 */
public class RedGameCard implements GameCard {
  private final Color color;
  private final Number number;

  public RedGameCard(Color color, Number number) {
    this.color = color;
    this.number = number;
  }

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public Number getNumber() {
    return number;
  }

  @Override
  public String toString() {
    return ("" + color.getChar() + number.getInt());
  }

  @Override
  public boolean equals(Object anotherObject) {
    if (anotherObject instanceof RedGameCard) {
      return
              this.color.getChar() == ((RedGameCard) anotherObject).getColor().getChar() &&
              this.number.getInt() == ((RedGameCard) anotherObject).getNumber().getInt();
    }
    return false;
  }

  @Override
  public int hashCode() {
    return color.getIndex() * 31 + number.getInt();
  }
}
