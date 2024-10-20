package solored.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Represents the model for the Solo Red Game. This class manages the game state,
 * including the deck of cards, the player's hand, palettes, and the canvas.
 * It provides methods to start the game, play cards, and check the game status.
 */
public class SoloRedGameModel implements RedGameModel<RedGameCard> {
  protected List<RedGameCard> deck;
  protected List<RedGameCard> hand;
  protected List<Palette> palettes;
  protected RedGameCard canvas;
  protected boolean gameStarted;
  protected boolean gameOver;
  protected boolean canvasPlayAllowed;
  protected int handSize;
  protected int lastPlayedPaletteIndex;
  private Random random;

  /**
   * Initializes a new SoloRedGameModel.
   */
  public SoloRedGameModel() {
    gameStarted = false;
    gameOver = false;
    palettes = new ArrayList<>();
    hand = new ArrayList<>();
  }

  /**
   * Initializes a new SoloRedGameModel with a specified random number generator.
   *
   * @param  r the random number generator to use for shuffling the deck.
   */
  public SoloRedGameModel(Random r) {
    if (r == null) {
      throw new IllegalArgumentException("Random object cannot be null.");
    }
    random = r;
    gameStarted = false;
    gameOver = false;
    palettes = new ArrayList<>();
    hand = new ArrayList<>();
  }

  @Override
  public void playToPalette(int paletteIdx, int cardIdxInHand) {
    // Validate game state
    if (!gameStarted || gameOver) {
      throw new IllegalStateException("Game has not started or is already over.");
    }

    // Validate palette index
    if (paletteIdx < 0 || paletteIdx >= palettes.size()) {
      throw new IllegalArgumentException("Invalid palette index.");
    }

    // Validate hand index
    if (cardIdxInHand < 0 || cardIdxInHand >= hand.size()) {
      throw new IllegalArgumentException("Invalid card index in hand.");
    }

    if (paletteIdx == winningPaletteIndex()) {
      throw new IllegalStateException("Can't play on the winning palette.");
    }

    palettes.get(paletteIdx).addCard(hand.get(cardIdxInHand));
    hand.remove(cardIdxInHand);
    lastPlayedPaletteIndex = paletteIdx;
  }

  @Override
  public void playToCanvas(int cardIdxInHand) {
    // Validate game state
    if (!gameStarted || gameOver) {
      throw new IllegalStateException("Game has not started or is already over.");
    }
    // Validate hand index
    if (cardIdxInHand < 0 || cardIdxInHand >= hand.size()) {
      throw new IllegalArgumentException("Invalid card index in hand.");
    }
    if (!canvasPlayAllowed) {
      throw new IllegalStateException("Canvas play not allowed.");
    }
    if (hand.size() == 1) {
      throw new IllegalStateException("There is only one card in the hand.");
    }
    canvas = hand.get(cardIdxInHand);
    for (Palette palette : palettes) {
      palette.setCanvas(canvas.getColor());
    }
    hand.remove(cardIdxInHand);
    canvasPlayAllowed = false;
    lastPlayedPaletteIndex = -1;
  }

  @Override
  public void drawForHand() {
    // Validate game state
    if (!gameStarted || gameOver) {
      throw new IllegalStateException("Game has not started or is already over.");
    }

    int numCards = Math.min(handSize - hand.size(), deck.size());
    for (int i = 0; i < numCards; i++) {
      hand.add(deck.get(0));
      deck.remove(0);
    }
    canvasPlayAllowed = true;
  }

  @Override
  public void startGame(List<RedGameCard> deck, boolean shuffle, int numPalettes, int handSize) {
    if (gameStarted || gameOver) {
      throw new IllegalStateException("Game has already started or is over.");
    }
    if (deck == null) {
      throw new IllegalArgumentException("Deck cannot be null.");
    }
    if (numPalettes < 2) {
      throw new IllegalArgumentException("Number of palettes must be at least 2.");
    }
    if (handSize <= 0) {
      throw new IllegalArgumentException("Hand size must be greater than 0.");
    }
    if (deck.size() < numPalettes + handSize) {
      throw new IllegalArgumentException("Deck is too small to start a game.");
    }
    HashSet<RedGameCard> uniqueCards = new HashSet<>();
    for (RedGameCard card : deck) {
      if (card == null) {
        throw new IllegalArgumentException("Deck contains a null card.");
      }
      if (!uniqueCards.add(card)) {
        throw new IllegalArgumentException("Deck contains duplicate cards.");
      }
    }
    this.deck = new ArrayList<>(deck);
    if (shuffle) {
      if (random == null) {
        Collections.shuffle(this.deck, new Random());
      } else {
        Collections.shuffle(this.deck, random);
      }
    }
    for (int i = 0; i < numPalettes; i++) { // Initialize the Palettes
      palettes.add(new Palette(this.deck.get(0)));
      this.deck.remove(0);
    }
    gameStarted = true;
    gameOver = false;
    lastPlayedPaletteIndex = -1;
    this.handSize = handSize;
    canvas = new RedGameCard(Color.RED, Number.ONE); // Set the canvas rule to Red (initial state)
    for (Palette palette : palettes) {
      palette.setCanvas(canvas.getColor());
    }
    drawForHand(); // Draw cards from the deck to the hand until the hand is full
  }

  @Override
  public int numOfCardsInDeck() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    return deck.size();
  }

  @Override
  public int numPalettes() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    return palettes.size();
  }

  @Override
  public int winningPaletteIndex() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    int idx = -1;
    int maxWinFactor = 0;
    for (int i = 0; i < palettes.size(); i++) {
      if (palettes.get(i).getWinFactor() > maxWinFactor) {
        maxWinFactor = palettes.get(i).getWinFactor();
        idx = i;
      }
    }
    return idx;
  }

  @Override
  public boolean isGameOver() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    if (winningPaletteIndex() != lastPlayedPaletteIndex && lastPlayedPaletteIndex != -1) {
      gameOver = true;
      return true;
    }
    if (hand.isEmpty() && deck.isEmpty()) {
      gameOver = true;
      return true;
    }
    gameOver = false;
    return false;
  }

  @Override
  public boolean isGameWon() {
    if (!gameStarted || !gameOver) {
      throw new IllegalStateException("Game has not started or the game is not over.");
    }
    if (winningPaletteIndex() != lastPlayedPaletteIndex && lastPlayedPaletteIndex != -1) {
      return false;
    }
    return hand.isEmpty() && deck.isEmpty();
  }

  @Override
  public List<RedGameCard> getHand() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    return new ArrayList<>(hand);
  }

  @Override
  public List<RedGameCard> getPalette(int paletteNum) {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    if (paletteNum < 0 || paletteNum >= palettes.size()) {
      throw new IllegalArgumentException("Invalid palette index.");
    }
    return new ArrayList<>(palettes.get(paletteNum).getCards());
  }

  @Override
  public RedGameCard getCanvas() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    return new RedGameCard(canvas.getColor(), canvas.getNumber());
  }

  @Override
  public List<RedGameCard> getAllCards() {
    List<RedGameCard> allCards = new ArrayList<>();
    for (Color c : Color.values()) {
      for (Number n : Number.values()) {
        allCards.add(new RedGameCard(c, n));
      }
    }
    return new ArrayList<>(allCards);
  }
}
