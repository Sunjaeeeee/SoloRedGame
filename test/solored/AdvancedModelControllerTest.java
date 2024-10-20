package solored;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import solored.controller.RedGameController;
import solored.controller.SoloRedTextController;
import solored.model.Color;
import solored.model.Number;
import solored.model.RedGameCard;
import solored.model.AdvancedSoloRedGameModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for controller testing with AdvancedSoloRedGameModel.
 */
public class AdvancedModelControllerTest extends ControllerTest {

  @Before
  public void setUp() {
    output = new StringWriter();
    input = new StringReader("palette 0 1 q");
    model =  new AdvancedSoloRedGameModel(new Random(10));
    deck = model.getAllCards(); // Populate with mock Card objects
    controller = new SoloRedTextController(input, output);
  }

  @Ignore
  public void testIntegrationForWinWithStudentModel() {
    // Test made specifically for the basic model.
    assertTrue(true);
  }

  @Test
  public void testIntegrationForLossWithAdvancedModel() {
    StringBuilder log = new StringBuilder();
    RedGameController controller = new SoloRedTextController(new StringReader("canvas 6 " +
            "palette 2 1 palette 3 1 palette 1 7 canvas 7 palette 2 6 canvas 3 palette 3 3 " +
            "canvas 2 palette 2 6 canvas 3 palette 2 1 palette 1 5 palette 2 2 palette 1 3 " +
            "palette 2 1 palette 1 1 q"), log);
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
    controller.playGame(model, deck1, false, 4, 7);
    String[] lines = log.toString().split("\n");
    assertEquals("Game lost.", lines[44]);
  }
}
