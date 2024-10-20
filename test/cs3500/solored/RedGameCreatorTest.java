package cs3500.solored;

import org.junit.Test;

import cs3500.solored.model.RedGameModel;
import cs3500.solored.model.SoloRedGameModel;
import cs3500.solored.model.AdvancedSoloRedGameModel;
import cs3500.solored.model.RedGameCreator;

import static cs3500.solored.model.RedGameCreator.createGame;
import static org.junit.Assert.assertTrue;

/**
 * Test Class for RedGameCreator, Makes sure all the models are created as it should.
 */
public class RedGameCreatorTest {
  @Test
  public void createsBasicModel() {
    RedGameModel model = createGame(RedGameCreator.GameType.BASIC);
    assertTrue(model instanceof SoloRedGameModel);
  }

  @Test
  public void createsAdvancedModel() {
    RedGameModel model = createGame(RedGameCreator.GameType.ADVANCED);
    assertTrue(model instanceof AdvancedSoloRedGameModel);
  }

  @Test(expected = IllegalArgumentException.class)
  public void inputInvalid() {
    createGame(null);
  }
}
