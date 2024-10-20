package solored.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a single palette in the Solo Red Card Game.
 */
public class Palette {
  private final List<RedGameCard> cards;
  private Color canvas;
  private final String canvasList = "ROBIV";

  public Palette(RedGameCard card) {
    this.cards = new ArrayList<>();
    this.cards.add(card);
  }

  public void addCard(RedGameCard card) {
    cards.add(card);
  }

  public List<RedGameCard> getCards() {
    return cards;
  }

  public void setCanvas(Color canvas) {
    this.canvas = canvas;
  }

  private int redCalc(List<RedGameCard> deck) {
    int maxNumber = 0;
    for (RedGameCard card : deck) {
      if (card.getNumber().getInt() > maxNumber) {
        maxNumber = card.getNumber().getInt();
      }
    }
    int maxNumber1 = 0;
    for (RedGameCard card : deck) {
      if (card.getNumber().getInt() == maxNumber) {
        if (9 - card.getColor().getIndex() > maxNumber1) {
          maxNumber1 = 9 - card.getColor().getIndex();
        }
      }
    }
    return (maxNumber * 10 + maxNumber1);
  }

  private int orangeCalc(List<RedGameCard> deck) {
    // Map to store the frequency of each card number
    Map<Integer, Integer> numberFrequency = new HashMap<>();

    // Populate the map with the frequency of each number
    for (RedGameCard card : deck) {
      int number = card.getNumber().getInt();
      numberFrequency.put(number, numberFrequency.getOrDefault(number, 0) + 1);
    }

    // Find the number with the highest frequency
    int maxFrequency = 0;
    int mostFrequentNumber = 0;
    for (Map.Entry<Integer, Integer> entry : numberFrequency.entrySet()) {
      if (entry.getValue() >= maxFrequency) {
        maxFrequency = entry.getValue();
        mostFrequentNumber = entry.getKey();
      }
    }

    // Collect all cards with the most frequent number
    List<RedGameCard> mostFrequentCards = new ArrayList<>();
    for (RedGameCard card : deck) {
      if (card.getNumber().getInt() == mostFrequentNumber) {
        mostFrequentCards.add(card);
      }
    }

    return maxFrequency * 100 + redCalc(mostFrequentCards);
  }

  private int countDistinctColors(List<RedGameCard> deck) {
    // Set to store distinct colors
    Set<Color> uniqueColors = new HashSet<>();

    // Add each card's color to the set
    for (RedGameCard card : deck) {
      uniqueColors.add(card.getColor());
    }

    // Return the size of the set, which represents the number of distinct colors
    return uniqueColors.size();
  }

  private int indigoCalc(List<RedGameCard> deck) {
    List<RedGameCard> cards = new ArrayList<>(deck);
    cards.sort(Comparator.comparingInt(card -> card.getNumber().getInt())); // Sort the cards by num
    List<List<RedGameCard>> longestRuns = new ArrayList<>();
    List<RedGameCard> currentRun = new ArrayList<>();
    int currentRunCounter = 1;
    int maxRunLength = 1;
    for (int i = 0; i < cards.size(); i++) {
      if (currentRun.isEmpty()) {
        currentRun.add(cards.get(i)); // Add the first card to start the run
      } else {
        RedGameCard lastCard = currentRun.get(currentRun.size() - 1);
        RedGameCard currentCard = cards.get(i);
        if (currentCard.getNumber().getInt() == lastCard.getNumber().getInt() + 1) {
          currentRun.add(currentCard); // Check if the current card continues the run
          currentRunCounter++;
        } else if (currentCard.getNumber() == lastCard.getNumber()) {
          currentRun.add(currentCard);
        } else {
          if (currentRunCounter > maxRunLength) { // If not consecutive, start a new run
            longestRuns.clear();
            maxRunLength = currentRunCounter;
            longestRuns.add(new ArrayList<>(currentRun));
          } else if (currentRunCounter == maxRunLength) {
            longestRuns.add(new ArrayList<>(currentRun));
          }
          currentRun.clear();
          currentRun.add(currentCard);
          currentRunCounter = 1;
        }
      }
    }
    if (currentRunCounter > maxRunLength) { // Handle the last run at the end of the loop
      longestRuns.clear();
      maxRunLength = currentRunCounter;
      longestRuns.add(new ArrayList<>(currentRun));
    } else if (currentRunCounter == maxRunLength) {
      longestRuns.add(new ArrayList<>(currentRun));
    }
    int maxRedCalc = 0;
    for (List<RedGameCard> run : longestRuns) {
      if (redCalc(run) > maxRedCalc) {
        maxRedCalc = redCalc(run);
      }
    }
    return (maxRunLength * 100) + maxRedCalc;
  }

  private int violetCalc(List<RedGameCard> deck) {
    if (deck == null || deck.isEmpty()) {
      throw new IllegalArgumentException("deck is null or empty");
    }

    List<RedGameCard> subList = new ArrayList<>();

    // Iterate through the deck and collect cards with numbers less than 4
    for (RedGameCard card : deck) {
      if (card.getNumber().getInt() < 4) {
        subList.add(card);
      }
    }
    //if there is no element below 4, just return redCalc to prevent ties
    if (subList.isEmpty()) {
      return redCalc(deck);
    }
    return subList.size() * 100 + redCalc(subList);
  }

  /**
   * Uses the helper methods to calculate a win factor that is comparable as long its under the
   * same ruleset.
   * The hundreds digit is used to compare the rule specific factors
   * The tens digit is used to represent the number of the highest card by red rules out of the
   * relevant cards
   * The ones digit is used to represent the color of the same card in the tens digit
   * 9 = R, 8 = O, 7 = B, 6 = I, 5 = V
   *
   * @return the win factor as int.
   */
  public int getWinFactor() {
    if (canvas == Color.RED) {
      return redCalc(cards);
    }
    if (canvas == Color.ORANGE) {
      return orangeCalc(cards);
    }
    if (canvas == Color.BLUE) {
      return countDistinctColors(cards) * 100 + redCalc(cards);
    }
    if (canvas == Color.INDIGO) {
      return indigoCalc(cards);
    }
    if (canvas == Color.VIOLET) {
      return violetCalc(cards);
    }
    throw new IllegalArgumentException("canvas is not valid");
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (RedGameCard card : cards) {
      sb.append(" " + card.toString());
    }
    return sb.toString();
  }
}
