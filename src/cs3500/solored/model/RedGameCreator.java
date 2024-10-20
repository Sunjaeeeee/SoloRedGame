package cs3500.solored.model;


/**
 * The RedGameCreator class is responsible for creating instances of RedGameModel
 * based on the specified game type. It supports different variations of the game.
 */
public class RedGameCreator {

  /**
   * Enum representing the different types of games that can be created.
   */
  public enum GameType {
    BASIC,
    ADVANCED
  }

  /**
   * Creates a new instance of a RedGameModel based on the provided game type.
   *
   * @param gameType the type of game to create, which can be either BASIC or ADVANCED.
   * @return an instance of RedGameModel corresponding to the specified game type.
   * @throws IllegalArgumentException if the provided game type is unsupported.
   */
  public static RedGameModel<RedGameCard> createGame(GameType gameType) {
    if (gameType == GameType.BASIC) {
      return new SoloRedGameModel();
    } else if (gameType == GameType.ADVANCED) {
      return new AdvancedSoloRedGameModel();
    }
    throw new IllegalArgumentException("Unsupported game type: " + gameType);
  }
}
