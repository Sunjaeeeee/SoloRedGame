package solored;

import org.junit.Test;

import solored.model.RedGameModel;
import solored.model.SoloRedGameModel;
import solored.model.AdvancedSoloRedGameModel;
import solored.model.RedGameCreator;

import static solored.model.RedGameCreator.createGame;
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
