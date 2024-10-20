package cs3500.solored.controller;

import java.util.List;

import cs3500.solored.model.Card;
import cs3500.solored.model.RedGameModel;

/**
 * The {@code RedGameController} interface defines the contract for controlling
 * the gameplay of Solo Red. It provides methods to start and manage the game
 * using a specified game model and deck of cards.
 */
public interface RedGameController  {
  /**
   * Plays a new game of Solo Red using the provided game model and deck. This method
   * utilizes the {@code startGame} method from the model to begin the game with the
   * specified configurations, including deck shuffling, number of palettes, and hand size.
   *
   * @param model      the game model for Solo Red (cannot be null)
   * @param deck       the deck of cards to be used in the game
   * @param shuffle    if {@code true}, shuffles the deck before starting the game
   * @param numPalettes the number of palettes in the game
   * @param handSize   the number of cards each player receives at the start of the game
   * @throws IllegalArgumentException if {@code model} is null, or if the game cannot be started
   * @throws IllegalStateException if the controller fails to receive input or transmit output
   */
  <C extends Card> void playGame(RedGameModel<C> model, List<C> deck,
                                 boolean shuffle, int numPalettes, int handSize);
}
