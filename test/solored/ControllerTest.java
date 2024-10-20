package solored;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import solored.controller.RedGameController;
import solored.controller.SoloRedTextController;
import solored.model.Color;
import solored.model.Number;
import solored.model.RedGameCard;
import solored.model.RedGameModel;
import solored.model.SoloRedGameModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * A series of tests to test the functionality of SoloRedTextController.
 */
public class ControllerTest {
  protected SoloRedTextController controller;
  protected RedGameModel<RedGameCard> model;
  protected List<RedGameCard> deck;
  protected StringWriter output;
  protected StringReader input;

  @Before
  public void setUp() {
    output = new StringWriter();
    input = new StringReader("palette 0 1 q");
    model =  new SoloRedGameModel(new Random(10));
    deck = model.getAllCards(); // Populate with mock Card objects
    controller = new SoloRedTextController(input, output);
  }

  @Test
  public void testConstructorWithNullReadable() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new SoloRedTextController(null, output);
    });
    assertEquals("Readable and Appendable cannot be null.", exception.getMessage());
  }

  @Test
  public void testConstructorWithNullAppendable() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new SoloRedTextController(input, null);
    });
    assertEquals("Readable and Appendable cannot be null.", exception.getMessage());
  }

  @Test
  public void testPlayGameWithNullModel() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      controller.playGame(null, deck, false, 2, 3);
    });
    assertEquals("Model or Deck cannot be null.", exception.getMessage());
  }

  @Test
  public void testPlayGameWithNullDeck() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      controller.playGame(model, null, false, 2, 3);
    });
    assertEquals("Model or Deck cannot be null.", exception.getMessage());
  }

  @Test
  public void testPlayGameWithInvalidPalettes() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      controller.playGame(model, deck, false, -2, 3);
    });
    assertEquals("Number of palettes must be at least 2.", exception.getMessage());
  }

  @Test
  public void testPlayGameWithInvalidHandSize() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      controller.playGame(model, deck, false, 2, 0);
    });
    assertEquals("Hand size must be greater than 0.", exception.getMessage());
  }


  @Test
  public void testControllerRecognizesGameIsLost() {
    StringBuilder log = new StringBuilder();
    RedGameController controller = new SoloRedTextController(
            new StringReader("palette 2 3"), log
    );
    List<RedGameCard> deck = model.getAllCards();
    controller.playGame(model, deck.subList(0, 5), true, 2, 3);
    String[] lines = log.toString().split("\n");
    assertEquals("Game lost.", lines[5]);
  }

  @Test
  public void testControllerRecognizesGameIsWon() {
    StringBuilder log = new StringBuilder();
    RedGameController controller = new SoloRedTextController(
            new StringReader("canvas 2 palette 1 1 palette 2 1 q"), log
    );
    List<RedGameCard> deck = model.getAllCards();
    Collections.shuffle(deck, new Random(10));
    controller.playGame(model, deck.subList(0, 5), true, 2, 3);
    System.out.print(log);
    String[] lines = log.toString().split("\n");
    assertEquals("Game won.", lines[15]);
  }

  @Test
  public void testControllerRecognizesGameIsLostBadInput() {
    StringBuilder log = new StringBuilder();
    RedGameController controller = new SoloRedTextController(
            new StringReader("Palette P PP 2 3 palette 2 3"), log
    );
    List<RedGameCard> deck = model.getAllCards();
    controller.playGame(model, deck.subList(0, 5), true, 2, 3);
    String[] lines = log.toString().split("\n");
    assertEquals("Game lost.", lines[35]);
  }

  @Test
  public void testControllerRecognizesGameIsWonBadInput() {
    StringBuilder log = new StringBuilder();
    RedGameController controller = new SoloRedTextController(
            new StringReader("canvas 2 palette P PP 1 1 palette P PP 2 1 q"), log
    );
    List<RedGameCard> deck = model.getAllCards();
    Collections.shuffle(deck, new Random(10));
    controller.playGame(model, deck.subList(0, 5), true, 2, 3);
    String[] lines = log.toString().split("\n");
    assertEquals("Game won.", lines[15]);
  }

  @Test
  public void testQuitPalette() {
    StringBuilder log = new StringBuilder();
    RedGameController controller = new SoloRedTextController(
            new StringReader("palette q"), log
    );
    List<RedGameCard> deck = model.getAllCards();
    Collections.shuffle(deck, new Random(10));
    controller.playGame(model, deck.subList(0, 5), true, 2, 3);
    String[] lines = log.toString().split("\n");
    assertEquals("Game quit!", lines[5]);
  }

  @Test
  public void testQuitPaletteInd1() {
    StringBuilder log = new StringBuilder();
    RedGameController controller = new SoloRedTextController(
            new StringReader("palette 1 q"), log
    );
    List<RedGameCard> deck = model.getAllCards();
    Collections.shuffle(deck, new Random(10));
    controller.playGame(model, deck.subList(0, 5), true, 2, 3);
    String[] lines = log.toString().split("\n");
    assertEquals("Game quit!", lines[5]);
  }

  @Test
  public void testQuitPaletteInd2() {
    StringBuilder log = new StringBuilder();
    RedGameController controller = new SoloRedTextController(
            new StringReader("palette 1 1 q"), log
    );
    List<RedGameCard> deck = model.getAllCards();
    controller.playGame(model, deck, true, 2, 3);
    String[] lines = log.toString().split("\n");
    assertEquals("Game quit!", lines[10]);
  }

  @Test
  public void testPaletteInvalidInx() {
    StringBuilder log = new StringBuilder();
    RedGameController controller = new SoloRedTextController(
            new StringReader("palette 1 4 q"), log
    );
    List<RedGameCard> deck = model.getAllCards();
    Collections.shuffle(deck, new Random(10));
    controller.playGame(model, deck.subList(0, 5), true, 2, 3);
    String[] lines = log.toString().split("\n");
    assertEquals("Invalid move. Try again. Invalid card index in hand.", lines[5]);
  }

  @Test
  public void testQuitCanvasInvalidCardIdx() {
    StringBuilder log = new StringBuilder();
    RedGameController controller = new SoloRedTextController(
            new StringReader("canvas q"), log
    );
    List<RedGameCard> deck = model.getAllCards();
    Collections.shuffle(deck, new Random(10));
    controller.playGame(model, deck.subList(0, 5), true, 2, 3);
    String[] lines = log.toString().split("\n");
    assertEquals("Game quit!", lines[5]);
  }

  @Test
  public void testQuit() {
    StringBuilder log = new StringBuilder();
    RedGameController controller = new SoloRedTextController(
            new StringReader("q"), log
    );
    List<RedGameCard> deck = model.getAllCards();
    Collections.shuffle(deck, new Random(10));
    controller.playGame(model, deck.subList(0, 5), true, 2, 3);
    System.out.println(log);
    String[] lines = log.toString().split("\n");
    assertEquals("Game quit!", lines[5]);
  }

  @Test
  public void testNoMoreReads() {
    StringBuilder log = new StringBuilder();
    RedGameController controller = new SoloRedTextController(
            new StringReader("palette 1 1"), log
    );
    List<RedGameCard> deck = model.getAllCards();
    Exception exception = assertThrows(IllegalStateException.class, () -> {
      controller.playGame(model, deck, true, 2, 3);
    });
    assertEquals("No new input.", exception.getMessage());
  }

  @Test
  public void testIntegrationForWinWithStudentModel() {
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
    assertEquals("Game won.", lines[119]);
  }
}
