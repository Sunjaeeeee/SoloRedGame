package cs3500.solored;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.solored.model.Color;
import cs3500.solored.model.Number;
import cs3500.solored.model.Palette;
import cs3500.solored.model.RedGameCard;
import cs3500.solored.model.AdvancedSoloRedGameModel;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class that verifies the AdvancedModel is working as it should.
 * Extends the ModelTest Class to run a lot of the same tests.
 */
public class AdvancedModelTest extends ModelTest {

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
    game = new AdvancedSoloRedGameModel(rand);
  }

  @Ignore
  public void testInstructionSection27() {
    // Test made specifically for the basic model.
    assertTrue(true);
  }

  @Ignore
  public void testInstructionSection28() {
    // Test made specifically for the basic model.
    assertTrue(true);
  }

  @Override
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
    game.playToPalette(0, 4);
    game.drawForHand();
    assertTrue(game.isGameOver());
    assertFalse(game.isGameWon());
  }

  @Test
  public void draw1AfterPalettePlay() {
    game.startGame(deck, false, 2,3);
    game.playToPalette(0,0);
    int temp = game.getHand().size();
    game.drawForHand();
    assertEquals(temp + 1, game.getHand().size());
  }

  @Test
  public void draw1AfterInvalidCanvasPlay() {
    game.startGame(deck, false, 2,3);
    game.playToCanvas(1);
    game.playToPalette(0,0);
    int temp = game.getHand().size();
    game.drawForHand();
    assertEquals(temp + 1, game.getHand().size());
  }

  @Test
  public void draw2AfterValidCanvasPlay() {
    game.startGame(deck, false, 2,3);
    game.playToCanvas(2);
    game.playToPalette(0,0);
    int temp = game.getHand().size();
    game.drawForHand();
    assertEquals(temp + 2, game.getHand().size());
  }
}
