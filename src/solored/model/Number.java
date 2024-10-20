package solored.model;

/**
 * Enum Class representing a number on a card.
 */
public enum Number {
  ONE {
    @Override
    public int getInt() {
      return 1;
    }
  },
  TWO {
    @Override
    public int getInt() {
      return 2;
    }
  },
  THREE {
    @Override
    public int getInt() {
      return 3;
    }
  },
  FOUR {
    @Override
    public int getInt() {
      return 4;
    }
  },
  FIVE {
    @Override
    public int getInt() {
      return 5;
    }
  },
  SIX {
    @Override
    public int getInt() {
      return 6;
    }
  },
  SEVEN {
    @Override
    public int getInt() {
      return 7;
    }
  };
  public abstract int getInt();
}
