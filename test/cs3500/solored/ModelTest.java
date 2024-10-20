package cs3500.solored;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import cs3500.solored.model.Color;
import cs3500.solored.model.Number;
import cs3500.solored.model.Palette;
import cs3500.solored.model.RedGameCard;
import cs3500.solored.model.RedGameModel;
import cs3500.solored.model.SoloRedGameModel;
import cs3500.solored.view.SoloRedGameTextView;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for methods in SoloRedGameModel and Palette.
 */
public class ModelTest {
  protected Palette palette1;
  protected Palette palette2;
  protected Palette palette3;
  protected Palette palette4;
  protected RedGameModel game;
  protected List<RedGameCard> deck;
  protected Random rand = new Random(3);

  @Before
  public void setUp() throws Exception {
    palette1 = new Palette(new RedGameCard(Color.INDIGO, Number.FIVE));
    palette1.addCard(new RedGameCard(Color.ORANGE, Number.SEVEN));
    palette1.addCard(new RedGameCard(Color.BLUE, Number.FIVE));
    palette1.addCard(new RedGameCard(Color.BLUE, Number.SEVEN));

    palette2 = new Palette(new RedGameCard(Color.INDIGO, Number.SIX));
    palette2.addCard(new RedGameCard(Color.ORANGE, Number.TWO));
    palette2.addCard(new RedGameCard(Color.RED, Number.THREE));
    palette2.addCard(new RedGameCard(Color.INDIGO, Number.FOUR));

    palette3 = new Palette(new RedGameCard(Color.ORANGE, Number.FOUR));
    palette3.addCard(new RedGameCard(Color.BLUE, Number.FIVE));
    palette3.addCard(new RedGameCard(Color.RED, Number.THREE));
    palette3.addCard(new RedGameCard(Color.VIOLET, Number.TWO));

    palette4 = new Palette(new RedGameCard(Color.VIOLET, Number.FIVE));
    palette4.addCard(new RedGameCard(Color.BLUE, Number.ONE));
    palette4.addCard(new RedGameCard(Color.BLUE, Number.SIX));
    palette4.addCard(new RedGameCard(Color.BLUE, Number.SEVEN));

    deck = new ArrayList<>(asList(
            new RedGameCard(Color.RED, Number.ONE), new RedGameCard(Color.RED, Number.TWO),
            new RedGameCard(Color.RED, Number.THREE), new RedGameCard(Color.ORANGE, Number.ONE),
            new RedGameCard(Color.ORANGE, Number.TWO), new RedGameCard(Color.ORANGE, Number.THREE),
            new RedGameCard(Color.BLUE, Number.ONE), new RedGameCard(Color.BLUE, Number.TWO),
            new RedGameCard(Color.BLUE, Number.THREE), new RedGameCard(Color.VIOLET, Number.ONE),
            new RedGameCard(Color.VIOLET, Number.TWO), new RedGameCard(Color.VIOLET, Number.THREE)
    ));
    game = new SoloRedGameModel(rand);
  }

  @Test
  public void testInstructionSection27() {
    RedGameModel<RedGameCard> model = new SoloRedGameModel();
    List<RedGameCard> deck = new ArrayList<RedGameCard>(asList(
            new RedGameCard(Color.BLUE,Number.TWO),
            new RedGameCard(Color.RED, Number.ONE),
            new RedGameCard(Color.ORANGE, Number.ONE),
            new RedGameCard(Color.ORANGE, Number.FOUR),
            new RedGameCard(Color.BLUE, Number.FIVE),
            new RedGameCard(Color.VIOLET, Number.SEVEN),
            new RedGameCard(Color.RED, Number.TWO),
            new RedGameCard(Color.BLUE, Number.FOUR),
            new RedGameCard(Color.VIOLET, Number.SIX),
            new RedGameCard(Color.BLUE, Number.SEVEN),
            new RedGameCard(Color.INDIGO,Number.ONE),
            new RedGameCard(Color.ORANGE, Number.THREE),
            new RedGameCard(Color.RED, Number.THREE),
            new RedGameCard(Color.RED, Number.FOUR)
    ));
    SoloRedGameTextView view = new SoloRedGameTextView(model);
    model.startGame(deck, false, 4,7);
    model.playToPalette(1,0);
    model.drawForHand();
    model.playToPalette(2,0);
    model.drawForHand();
    assertEquals("Canvas: R\n" +
            "P1: B2\n" +
            "P2: R1 B5\n" +
            "> P3: O1 V7\n" +
            "P4: O4\n" +
            "Hand: R2 B4 V6 B7 I1 O3 R3", view.toString());
    model.playToPalette(0,3);
    model.drawForHand();
    assertEquals("Canvas: R\n" +
            "> P1: B2 B7\n" +
            "P2: R1 B5\n" +
            "P3: O1 V7\n" +
            "P4: O4\n" +
            "Hand: R2 B4 V6 I1 O3 R3 R4", view.toString());
    model.playToPalette(2,0);
    model.drawForHand();
    assertEquals("Canvas: R\n" +
            "> P1: B2 B7\n" +
            "P2: R1 B5\n" +
            "P3: O1 V7 R2\n" +
            "P4: O4\n" +
            "Hand: B4 V6 I1 O3 R3 R4", view.toString());
  }

  @Test
  public void testInstructionSection28() {
    RedGameModel<RedGameCard> model = new SoloRedGameModel();
    List<RedGameCard> deck = new ArrayList<RedGameCard>(asList(
            new RedGameCard(Color.BLUE, Number.TWO),
            new RedGameCard(Color.RED, Number.ONE),
            new RedGameCard(Color.ORANGE, Number.ONE),
            new RedGameCard(Color.ORANGE, Number.FOUR),

            new RedGameCard(Color.BLUE, Number.SEVEN),
            new RedGameCard(Color.RED, Number.SEVEN),
            new RedGameCard(Color.VIOLET, Number.SEVEN),

            new RedGameCard(Color.RED, Number.TWO),
            new RedGameCard(Color.BLUE, Number.FOUR),
            new RedGameCard(Color.VIOLET, Number.SIX),
            new RedGameCard(Color.INDIGO, Number.ONE),
            new RedGameCard(Color.ORANGE, Number.THREE),
            new RedGameCard(Color.RED, Number.THREE),
            new RedGameCard(Color.RED, Number.FOUR),
            new RedGameCard(Color.BLUE, Number.ONE),
            new RedGameCard(Color.INDIGO, Number.FIVE)
    ));
    model.startGame(deck, false, 4,7);
    model.playToPalette(0,0);
    model.drawForHand();
    model.playToPalette(1,0);
    model.drawForHand();
    model.playToPalette(2,0);
    model.drawForHand();
    SoloRedGameTextView view = new SoloRedGameTextView(model);
    assertEquals("Canvas: R\n" +
            "P1: B2 B7\n" +
            "> P2: R1 R7\n" +
            "P3: O1 V7\n" +
            "P4: O4\n" +
            "Hand: R2 B4 V6 I1 O3 R3 R4", view.toString());
    model.playToCanvas(3);
    assertEquals("Canvas: I\n" +
            "P1: B2 B7\n" +
            "> P2: R1 R7\n" +
            "P3: O1 V7\n" +
            "P4: O4\n" +
            "Hand: R2 B4 V6 O3 R3 R4", view.toString());
    model.playToPalette(3,4);
    model.drawForHand();
    assertEquals("Canvas: I\n" +
            "P1: B2 B7\n" +
            "P2: R1 R7\n" +
            "P3: O1 V7\n" +
            "> P4: O4 R3\n" +
            "Hand: R2 B4 V6 O3 R4 B1 I5", view.toString());
  }

  //Palette Logic Tests
  @Test
  public void testRed() {
    palette1.setCanvas(Color.RED);
    palette2.setCanvas(Color.RED);
    palette3.setCanvas(Color.RED);
    palette4.setCanvas(Color.RED);
    assertEquals(78, palette1.getWinFactor());
    assertEquals(66, palette2.getWinFactor());
    assertEquals(57, palette3.getWinFactor());
    assertEquals(77, palette4.getWinFactor());
  }

  @Test
  public void testOrange() {
    palette1.setCanvas(Color.ORANGE);
    palette2.setCanvas(Color.ORANGE);
    palette3.setCanvas(Color.ORANGE);
    palette4.setCanvas(Color.ORANGE);
    assertEquals(278, palette1.getWinFactor());
    assertEquals(166, palette2.getWinFactor());
    assertEquals(157, palette3.getWinFactor());
    assertEquals(177, palette4.getWinFactor());
  }

  @Test
  public void testBlue() {
    palette1.setCanvas(Color.BLUE);
    palette2.setCanvas(Color.BLUE);
    palette3.setCanvas(Color.BLUE);
    palette4.setCanvas(Color.BLUE);
    assertEquals(378, palette1.getWinFactor());
    assertEquals(366, palette2.getWinFactor());
    assertEquals(457, palette3.getWinFactor());
    assertEquals(277, palette4.getWinFactor());
  }

  @Test
  public void testIndigo() {
    palette1.setCanvas(Color.INDIGO);
    palette2.setCanvas(Color.INDIGO);
    palette3.setCanvas(Color.INDIGO);
    palette4.setCanvas(Color.INDIGO);
    assertEquals(178, palette1.getWinFactor());
    assertEquals(346, palette2.getWinFactor());
    assertEquals(457, palette3.getWinFactor());
    assertEquals(377, palette4.getWinFactor());
  }

  @Test
  public void testViolet() {
    palette1.setCanvas(Color.VIOLET);
    palette2.setCanvas(Color.VIOLET);
    palette3.setCanvas(Color.VIOLET);
    palette4.setCanvas(Color.VIOLET);
    assertEquals(78, palette1.getWinFactor());
    assertEquals(239, palette2.getWinFactor());
    assertEquals(239, palette3.getWinFactor());
    assertEquals(117, palette4.getWinFactor());
  }

  //Model tests
  @Test
  public void testStartGame() {
    game.startGame(deck, false, 2, 3);
    assertEquals(7, game.numOfCardsInDeck());
    assertEquals(2, game.numPalettes());
    assertEquals(3, game.getHand().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNullDeck() {
    game.startGame(null, false, 2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNumPalettes() {
    game.startGame(deck, false, 1, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameHandSize() {
    game.startGame(deck, false, 2, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNotEnoughCardsInDeck() {
    game.startGame(deck, false, 5, 10);
  }

  @Test(expected = IllegalStateException.class)
  public void testStartGameAlreadyStarted() {
    game.startGame(deck, false, 4, 7);
    game.startGame(deck, false, 4, 7);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayToPaletteBeforeGameStarted() {
    game.playToPalette(0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayToCanvasBeforeGameStarted() {
    game.playToCanvas(0);
  }

  @Test
  public void testPlayToPalette() {
    game.startGame(deck, false, 2, 3);
    game.playToPalette(0, 0);
    assertEquals(2, game.getHand().size());
    assertEquals(2, game.getPalette(0).size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayToPaletteInvalidPaletteIndex() {
    game.startGame(deck, false, 2, 3);
    game.playToPalette(5, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayToPaletteInvalidCardIndex() {
    game.startGame(deck, false, 2, 3);
    game.playToPalette(0, 5);
  }

  @Test
  public void testDrawForHand() {
    game.startGame(deck, false, 2, 3);
    game.drawForHand();
    assertEquals(3, game.getHand().size());
  }

  @Test
  public void testWinningPaletteIndex() {
    game.startGame(deck, false, 2, 3);
    assertEquals(1, game.winningPaletteIndex());
  }

  @Test
  public void testGetAllCards() {
    List<RedGameCard> allCards = game.getAllCards();
    assertEquals(35, allCards.size());
  }

  @Test(expected = IllegalStateException.class)
  public void testIsGameOverBeforeGameStarted() {
    game.isGameOver();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCanvasBeforeGameStarted() {
    game.getCanvas();
  }

  @Test
  public void testGameOverConditions() {
    game.startGame(deck, false, 2, 3);
    // Play all cards and verify game is over
    game.playToPalette(0, 0);
    game.playToPalette(1, 0);
    game.playToPalette(1, 0);
    assertTrue(game.isGameOver());
  }

  @Test(expected = IllegalStateException.class)
  public void testIsGameWonBeforeGameOver() {
    game.startGame(deck, false, 2, 3);
    game.isGameWon();
  }

  @Test
  public void testRedGameCardEquals() {
    RedGameCard card1 = new RedGameCard(Color.RED, Number.THREE);
    RedGameCard card2 = new RedGameCard(Color.RED, Number.THREE);
    assertEquals(card1, card2);
  }

  @Test
  public void testRandom() {
    SoloRedGameModel model1 = new SoloRedGameModel(new Random(3));
    SoloRedGameModel model2 = new SoloRedGameModel(new Random(3));
    model1.startGame(deck, true, 2, 3);
    model2.startGame(deck, true, 2, 3);
    SoloRedGameTextView view1 = new SoloRedGameTextView(model1);
    SoloRedGameTextView view2 = new SoloRedGameTextView(model2);
    assertEquals(view1.toString(), view2.toString());
  }

  @Test
  public void getHandModificationTest() {
    game.startGame(deck, false, 2, 3);
    List<RedGameCard> hand = game.getHand();
    List<RedGameCard> hand1 = new ArrayList<>(hand);
    hand.add(new RedGameCard(Color.RED, Number.THREE));
    List<RedGameCard> hand2 = game.getHand();
    assertEquals(hand1, hand2);
  }

  @Test
  public void getPaletteModificationTest() {
    game.startGame(deck, false, 2, 3);
    List<RedGameCard> palette = game.getHand();
    List<RedGameCard> palette1 = new ArrayList<>(palette);
    palette.add(new RedGameCard(Color.RED, Number.THREE));
    List<RedGameCard> palette2 = game.getHand();
    assertEquals(palette1, palette2);
  }

  @Test
  public void runThrough() {
    List<RedGameCard> deck1 = new ArrayList<>(List.of(
            new RedGameCard(Color.ORANGE, Number.THREE),
            new RedGameCard(Color.INDIGO, Number.SIX),
            new RedGameCard(Color.BLUE, Number.SIX),
            new RedGameCard(Color.BLUE, Number.FOUR),
            new RedGameCard(Color.BLUE, Number.TWO),
            new RedGameCard(Color.INDIGO, Number.FOUR),
            new RedGameCard(Color.VIOLET, Number.ONE),
            new RedGameCard(Color.INDIGO, Number.FIVE),
            new RedGameCard(Color.ORANGE, Number.SEVEN),
            new RedGameCard(Color.BLUE, Number.ONE),
            new RedGameCard(Color.RED, Number.SEVEN),
            new RedGameCard(Color.RED, Number.ONE),
            new RedGameCard(Color.BLUE, Number.FIVE),
            new RedGameCard(Color.INDIGO, Number.SEVEN),
            new RedGameCard(Color.INDIGO, Number.TWO),
            new RedGameCard(Color.VIOLET, Number.SEVEN),
            new RedGameCard(Color.BLUE, Number.THREE),
            new RedGameCard(Color.RED, Number.TWO),
            new RedGameCard(Color.ORANGE, Number.FOUR),
            new RedGameCard(Color.INDIGO, Number.ONE),
            new RedGameCard(Color.ORANGE, Number.ONE)
    ));
    game.startGame(deck1, false, 4, 7);
    game.playToCanvas(5);
    assertFalse(game.isGameOver());
    game.playToPalette(1, 0);
    game.drawForHand();
    assertFalse(game.isGameOver());
    game.playToPalette(2, 0);
    game.drawForHand();
    assertFalse(game.isGameOver());
    game.playToPalette(0, 6);
    game.drawForHand();
    assertFalse(game.isGameOver());
    game.playToCanvas(6);
    assertFalse(game.isGameOver());
    game.playToPalette(1, 5);
    game.drawForHand();
    assertFalse(game.isGameOver());
    game.playToCanvas(2);
    assertFalse(game.isGameOver());
    game.playToPalette(2,2);
    game.drawForHand();
    assertFalse(game.isGameOver());
    game.playToCanvas(1);
    assertFalse(game.isGameOver());
    game.playToPalette(1,5);
    game.drawForHand();
    assertFalse(game.isGameOver());
    game.playToCanvas(2);
    game.playToPalette(1,0);
    game.drawForHand();
    assertFalse(game.isGameOver());
    game.playToPalette(0,3);
    game.drawForHand();
    assertFalse(game.isGameOver());
    game.playToPalette(1,1);
    game.drawForHand();
    assertFalse(game.isGameOver());
    game.playToPalette(0,2);
    game.drawForHand();
    assertFalse(game.isGameOver());
    game.playToPalette(1,0);
    game.drawForHand();
    assertFalse(game.isGameOver());
    game.playToPalette(0,0);
    assertTrue(game.isGameOver());
    assertTrue(game.isGameWon());
  }

  @Test
  public void startGameInitCorrectly() {
    List<RedGameCard> deck1 = new ArrayList<>(List.of(
            new RedGameCard(Color.ORANGE, Number.THREE),
            new RedGameCard(Color.INDIGO, Number.SIX),
            new RedGameCard(Color.BLUE, Number.SIX),
            new RedGameCard(Color.BLUE, Number.FOUR),
            new RedGameCard(Color.BLUE, Number.TWO),
            new RedGameCard(Color.INDIGO, Number.FOUR),
            new RedGameCard(Color.VIOLET, Number.ONE),
            new RedGameCard(Color.INDIGO, Number.FIVE),
            new RedGameCard(Color.ORANGE, Number.SEVEN),
            new RedGameCard(Color.BLUE, Number.ONE),
            new RedGameCard(Color.RED, Number.SEVEN),
            new RedGameCard(Color.RED, Number.ONE),
            new RedGameCard(Color.BLUE, Number.FIVE),
            new RedGameCard(Color.INDIGO, Number.SEVEN),
            new RedGameCard(Color.INDIGO, Number.TWO),
            new RedGameCard(Color.VIOLET, Number.SEVEN),
            new RedGameCard(Color.BLUE, Number.THREE),
            new RedGameCard(Color.RED, Number.TWO),
            new RedGameCard(Color.ORANGE, Number.FOUR),
            new RedGameCard(Color.INDIGO, Number.ONE),
            new RedGameCard(Color.ORANGE, Number.ONE)
    ));
    game.startGame(deck1, false, 4, 7);

    assertEquals(4, game.numPalettes());
    assertEquals(10, game.numOfCardsInDeck());
    assertEquals(2, game.winningPaletteIndex());
    assertFalse(game.isGameOver());
    assertEquals(
            List.of(
            new RedGameCard(Color.BLUE, Number.TWO),
            new RedGameCard(Color.INDIGO, Number.FOUR),
            new RedGameCard(Color.VIOLET, Number.ONE),
            new RedGameCard(Color.INDIGO, Number.FIVE),
            new RedGameCard(Color.ORANGE, Number.SEVEN),
            new RedGameCard(Color.BLUE, Number.ONE),
            new RedGameCard(Color.RED, Number.SEVEN))
            ,game.getHand());
    assertEquals(new RedGameCard(Color.RED, Number.ONE), game.getCanvas());
  }
}
