package solored.model;

/**
 * Enum Class representing a color on a card.
 */
public enum Color {
  RED() {
    @Override
    public char getChar() {
      return 'R';
    }

    @Override
    public int getIndex() {
      return 0;
    }
  },
  ORANGE() {
    @Override
    public char getChar() {
      return 'O';
    }

    @Override
    public int getIndex() {
      return 1;
    }
  },
  BLUE() {
    @Override
    public char getChar() {
      return 'B';
    }

    @Override
    public int getIndex() {
      return 2;
    }
  },
  INDIGO() {
    @Override
    public char getChar() {
      return 'I';
    }

    @Override
    public int getIndex() {
      return 3;
    }
  },
  VIOLET() {
    @Override
    public char getChar() {
      return 'V';
    }

    @Override
    public int getIndex() {
      return 4;
    }
  };

  /**
   * Returns the character associated with the color.
   * @return color in char.
   */
  public abstract char getChar();

  /**
   * Returns the index of the color based on ROBIV order.
   * @return returns index as int.
   */
  public abstract int getIndex();
}
