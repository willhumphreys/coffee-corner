package coffeecorner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoffeeCornerTest {

  private CoffeeCorner coffeeCorner;

  @BeforeEach
  void setUp() {
    coffeeCorner = new CoffeeCorner();
  }

  @Test
  public void testCoffeeCorner() {

    List<String> order = List.of(
      "Small coffee",
      "Medium coffee",
      "Small coffee",
      "Large coffee",
      "Orange juice",
      "Extra milk",
      "Special roast",
      "Bacon roll"
    );

    String receipt = coffeeCorner.submitOrder(order);

    assertEquals("2 Small coffee 5.0\n" +
      "1 Medium coffee 3.0\n" +
      "1 Large coffee 3.5\n" +
      "1 Orange juice 3.95\n" +
      "1 Extra milk 0.3\n" +
      "1 Special roast 0.9\n" +
      "1 Bacon roll 4.5\n" +
      "---- Bonuses ----\n" +
      "1 FREE -> Extra milk -0.3\n" +
      "1 5th Drink Free Small coffee -2.5\n" +
      "---- Stamp Balance ----\n" +
      "0\n" +
      "TOTAL: 18.35 CHF", receipt);

  }
}